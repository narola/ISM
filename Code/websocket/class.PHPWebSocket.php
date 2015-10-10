<?php

/*
  Handle persistance connection between client and server.
 */

class PHPWebSocket {

    // maximum amount of clients that can be connected at one time
    const WS_MAX_CLIENTS = 100;
    // maximum amount of clients that can be connected at one time on the same IP v4 address
    const WS_MAX_CLIENTS_PER_IP = 15;
    // amount of seconds a client has to send data to the server, before a ping request is sent to the client,
    // if the client has not completed the opening handshake, the ping request is skipped and the client connection is closed
    const WS_TIMEOUT_RECV = 10;
    // amount of seconds a client has to reply to a ping request, before the client connection is closed
    const WS_TIMEOUT_PONG = 5;
    // the maximum length, in bytes, of a frame's payload data (a message consists of 1 or more frames), this is also internally limited to 2,147,479,538
    const WS_MAX_FRAME_PAYLOAD_RECV = 10000000000;
    // the maximum length, in bytes, of a message's payload data, this is also internally limited to 2,147,483,647
    const WS_MAX_MESSAGE_PAYLOAD_RECV = 50000000000;
    // internal
    const WS_FIN = 128;
    const WS_MASK = 128;
    const WS_OPCODE_CONTINUATION = 0;
    const WS_OPCODE_TEXT = 1;
    const WS_OPCODE_BINARY = 2;
    const WS_OPCODE_CLOSE = 8;
    const WS_OPCODE_PING = 9;
    const WS_OPCODE_PONG = 10;
    const WS_PAYLOAD_LENGTH_16 = 126;
    const WS_PAYLOAD_LENGTH_63 = 127;
    const WS_READY_STATE_CONNECTING = 0;
    const WS_READY_STATE_OPEN = 1;
    const WS_READY_STATE_CLOSING = 2;
    const WS_READY_STATE_CLOSED = 3;
    const WS_STATUS_NORMAL_CLOSE = 1000;
    const WS_STATUS_GONE_AWAY = 1001;
    const WS_STATUS_PROTOCOL_ERROR = 1002;
    const WS_STATUS_UNSUPPORTED_MESSAGE_TYPE = 1003;
    const WS_STATUS_MESSAGE_TOO_BIG = 1004;
    const WS_STATUS_TIMEOUT = 30000000;

    // global vars
    public $wsClients = array();
    public $wsRead = array();
    public $wsClientCount = 0;
    public $wsClientIPCount = array();
    public $wsOnEvents = array();

    /*
      $this->wsClients[ integer ClientID ] = array(
      0 => resource  Socket,                            // client socket
      1 => string    MessageBuffer,                     // a blank string when there's no incoming frames
      2 => integer   ReadyState,                        // between 0 and 3
      3 => integer   LastRecvTime,                      // set to time() when the client is added
      4 => int/false PingSentTime,                      // false when the server is not waiting for a pong
      5 => int/false CloseStatus,                       // close status that wsOnClose() will be called with
      6 => integer   IPv4,                              // client's IP stored as a signed long, retrieved from ip2long()
      7 => int/false FramePayloadDataLength,            // length of a frame's payload data, reset to false when all frame data has been read (cannot reset to 0, to allow reading of mask key)
      8 => integer   FrameBytesRead,                    // amount of bytes read for a frame, reset to 0 when all frame data has been read
      9 => string    FrameBuffer,                       // joined onto end as a frame's data comes in, reset to blank string when all frame data has been read
      10 => integer  MessageOpcode,                     // stored by the first frame for fragmented messages, default value is 0
      11 => integer  MessageBufferLength                // the payload data length of MessageBuffer
      12 => integer  User_id;
      13 => integer  message no.
      )

      $wsRead[ integer ClientID ] = resource Socket         // this one-dimensional array is used for socket_select()
      // $wsRead[ 0 ] is the socket listening for incoming client connections

      $wsClientCount = integer ClientCount                  // amount of clients currently connected

      $wsClientIPCount[ integer IP ] = integer ClientCount  // amount of clients connected per IP v4 address
     */

    // server state functions
    function wsStartServer($host, $port) {
        if (isset($this->wsRead[0]))
            return false;

        if (!$this->wsRead[0] = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) {
            return false;
        }
        if (!socket_set_option($this->wsRead[0], SOL_SOCKET, SO_REUSEADDR, 1)) {
            socket_close($this->wsRead[0]);
            return false;
        }
        if (!socket_bind($this->wsRead[0], $host, $port)) {
            socket_close($this->wsRead[0]);
            return false;
        }
        if (!socket_listen($this->wsRead[0], 10)) {
            socket_close($this->wsRead[0]);
            return false;
        }

        $write = array();
        $except = array();

        $nextPingCheck = time() + 1;
        while (isset($this->wsRead[0])) {
            $changed = $this->wsRead;
            $result = socket_select($changed, $write, $except, 1);

            if ($result === false) {
                socket_close($this->wsRead[0]);
                return false;
            } elseif ($result > 0) {
                foreach ($changed as $clientID => $socket) {
                    if ($clientID != 0) {
                        // client socket changed
                        $buffer = '';
                        $bytes = @socket_recv($socket, $buffer, 4096, 0);

                        if ($bytes === false) {
                            // error on recv, remove client socket (will check to send close frame)
                            $this->wsSendClientClose($clientID, self::WS_STATUS_PROTOCOL_ERROR);
                        } elseif ($bytes > 0) {
                            // process handshake or frame(s)
                            if (!$this->wsProcessClient($clientID, $buffer, $bytes)) {
                                $this->wsSendClientClose($clientID, self::WS_STATUS_PROTOCOL_ERROR);
                            }
                        } else {
                            // 0 bytes received from client, meaning the client closed the TCP connection
                            $this->wsRemoveClient($clientID);
                        }
                    } else {
                        // listen socket changed
                        $client = socket_accept($this->wsRead[0]);
                        if ($client !== false) {
                            // fetch client IP as integer
                            $clientIP = '';
                            $result = socket_getpeername($client, $clientIP);
                            $clientIP = ip2long($clientIP);

                            if ($result !== false && $this->wsClientCount < self::WS_MAX_CLIENTS && (!isset($this->wsClientIPCount[$clientIP]) || $this->wsClientIPCount[$clientIP] < self::WS_MAX_CLIENTS_PER_IP)) {
                                $this->wsAddClient($client, $clientIP);
                            } else {
                                socket_close($client);
                            }
                        }
                    }
                }
            }

            if (time() >= $nextPingCheck) {
                $this->wsCheckIdleClients();
                $nextPingCheck = time() + 1;
            }
        }

        return true; // returned when wsStopServer() is called
    }

    function wsStopServer() {
        // check if server is not running
        if (!isset($this->wsRead[0]))
            return false;

        // close all client connections
        foreach ($this->wsClients as $clientID => $client) {
            // if the client's opening handshake is complete, tell the client the server is 'going away'
            if ($client[2] != self::WS_READY_STATE_CONNECTING) {
                $this->wsSendClientClose($clientID, self::WS_STATUS_GONE_AWAY);
            }
            socket_close($client[0]);
        }

        // close the socket which listens for incoming clients
        socket_close($this->wsRead[0]);

        // reset variables
        $this->wsRead = array();
        $this->wsClients = array();
        $this->wsClientCount = 0;
        $this->wsClientIPCount = array();

        return true;
    }

    // client timeout functions
    function wsCheckIdleClients() {
        $time = time();
        foreach ($this->wsClients as $clientID => $client) {
            if ($client[2] != self::WS_READY_STATE_CLOSED) {
                // client ready state is not closed
                if ($client[4] !== false) {
                    // ping request has already been sent to client, pending a pong reply
                    if ($time >= $client[4] + self::WS_TIMEOUT_PONG) {
                        // client didn't respond to the server's ping request in self::WS_TIMEOUT_PONG seconds
                        $this->wsSendClientClose($clientID, self::WS_STATUS_TIMEOUT);
                        $this->wsRemoveClient($clientID);
                    }
                } elseif ($time >= $client[3] + self::WS_TIMEOUT_RECV) {
                    // last data was received >= self::WS_TIMEOUT_RECV seconds ago
                    if ($client[2] != self::WS_READY_STATE_CONNECTING) {
                        // client ready state is open or closing
                        $this->wsClients[$clientID][4] = time();
                        $this->wsSendClientMessage($clientID, self::WS_OPCODE_PING, '');
                    } else {
                        // client ready state is connecting
                        $this->wsRemoveClient($clientID);
                    }
                }
            }
        }
    }

    // client existence functions
    function wsAddClient($socket, $clientIP) {
        // increase amount of clients connected
        $this->wsClientCount++;

        // increase amount of clients connected on this client's IP
        if (isset($this->wsClientIPCount[$clientIP])) {
            $this->wsClientIPCount[$clientIP] ++;
        } else {
            $this->wsClientIPCount[$clientIP] = 1;
        }

        // fetch next client ID
        $clientID = $this->wsGetNextClientID();

        // store initial client data
        $this->wsClients[$clientID] = array($socket, '', self::WS_READY_STATE_CONNECTING, time(), false, 0, $clientIP, false, 0, '', 0, 0, 0, 0);

        // store socket - used for socket_select()
        $this->wsRead[$clientID] = $socket;
    }

    function wsRemoveClient($clientID) {
        // fetch close status (which could be false), and call wsOnClose
        $closeStatus = $this->wsClients[$clientID][5];
        if (array_key_exists('close', $this->wsOnEvents))
            foreach ($this->wsOnEvents['close'] as $func)
                $func($clientID, $closeStatus);

        // close socket
        $socket = $this->wsClients[$clientID][0];
        socket_close($socket);

        // decrease amount of clients connected on this client's IP
        $clientIP = $this->wsClients[$clientID][6];
        if ($this->wsClientIPCount[$clientIP] > 1) {
            $this->wsClientIPCount[$clientIP] --;
        } else {
            unset($this->wsClientIPCount[$clientIP]);
        }

        // decrease amount of clients connected
        $this->wsClientCount--;

        // remove socket and client data from arrays
        unset($this->wsRead[$clientID], $this->wsClients[$clientID]);
    }

    // client data functions
    function wsGetNextClientID() {
        $i = 1; // starts at 1 because 0 is the listen socket
        while (isset($this->wsRead[$i]))
            $i++;
        return $i;
    }

    function wsGetClientSocket($clientID) {
        return $this->wsClients[$clientID][0];
    }

    // client read functions
    function wsProcessClient($clientID, &$buffer, $bufferLength) {
        if ($this->wsClients[$clientID][2] == self::WS_READY_STATE_OPEN) {
            // handshake completed
            $result = $this->wsBuildClientFrame($clientID, $buffer, $bufferLength);
        } elseif ($this->wsClients[$clientID][2] == self::WS_READY_STATE_CONNECTING) {
            // handshake not completed
            $result = $this->wsProcessClientHandshake($clientID, $buffer);
            if ($result) {
                $this->wsClients[$clientID][2] = self::WS_READY_STATE_OPEN;

                if (array_key_exists('open', $this->wsOnEvents))
                    foreach ($this->wsOnEvents['open'] as $func)
                        $func($clientID);
            }
        }
        else {
            // ready state is set to closed
            $result = false;
        }

        return $result;
    }

    function wsBuildClientFrame($clientID, &$buffer, $bufferLength) {
        // increase number of bytes read for the frame, and join buffer onto end of the frame buffer
        $this->wsClients[$clientID][8] += $bufferLength;
        $this->wsClients[$clientID][9] .= $buffer;

        // check if the length of the frame's payload data has been fetched, if not then attempt to fetch it from the frame buffer
        if ($this->wsClients[$clientID][7] !== false || $this->wsCheckSizeClientFrame($clientID) == true) {
            // work out the header length of the frame
            $headerLength = ($this->wsClients[$clientID][7] <= 125 ? 0 : ($this->wsClients[$clientID][7] <= 65535 ? 2 : 8)) + 6;

            // check if all bytes have been received for the frame
            $frameLength = $this->wsClients[$clientID][7] + $headerLength;
            if ($this->wsClients[$clientID][8] >= $frameLength) {
                // check if too many bytes have been read for the frame (they are part of the next frame)
                $nextFrameBytesLength = $this->wsClients[$clientID][8] - $frameLength;
                if ($nextFrameBytesLength > 0) {
                    $this->wsClients[$clientID][8] -= $nextFrameBytesLength;
                    $nextFrameBytes = substr($this->wsClients[$clientID][9], $frameLength);
                    $this->wsClients[$clientID][9] = substr($this->wsClients[$clientID][9], 0, $frameLength);
                }

                // process the frame
                $result = $this->wsProcessClientFrame($clientID);

                // check if the client wasn't removed, then reset frame data
                if (isset($this->wsClients[$clientID])) {
                    $this->wsClients[$clientID][7] = false;
                    $this->wsClients[$clientID][8] = 0;
                    $this->wsClients[$clientID][9] = '';
                }

                // if there's no extra bytes for the next frame, or processing the frame failed, return the result of processing the frame
                if ($nextFrameBytesLength <= 0 || !$result)
                    return $result;

                // build the next frame with the extra bytes
                return $this->wsBuildClientFrame($clientID, $nextFrameBytes, $nextFrameBytesLength);
            }
        }

        return true;
    }

    function wsCheckSizeClientFrame($clientID) {
        // check if at least 2 bytes have been stored in the frame buffer
        if ($this->wsClients[$clientID][8] > 1) {
            // fetch payload length in byte 2, max will be 127
            $payloadLength = ord(substr($this->wsClients[$clientID][9], 1, 1)) & 127;

            if ($payloadLength <= 125) {
                // actual payload length is <= 125
                $this->wsClients[$clientID][7] = $payloadLength;
            } elseif ($payloadLength == 126) {
                // actual payload length is <= 65,535
                if (substr($this->wsClients[$clientID][9], 3, 1) !== false) {
                    // at least another 2 bytes are set
                    $payloadLengthExtended = substr($this->wsClients[$clientID][9], 2, 2);
                    $array = unpack('na', $payloadLengthExtended);
                    $this->wsClients[$clientID][7] = $array['a'];
                }
            } else {
                // actual payload length is > 65,535
                if (substr($this->wsClients[$clientID][9], 9, 1) !== false) {
                    // at least another 8 bytes are set
                    $payloadLengthExtended = substr($this->wsClients[$clientID][9], 2, 8);

                    // check if the frame's payload data length exceeds 2,147,483,647 (31 bits)
                    // the maximum integer in PHP is "usually" this number. More info: http://php.net/manual/en/language.types.integer.php
                    $payloadLengthExtended32_1 = substr($payloadLengthExtended, 0, 4);
                    $array = unpack('Na', $payloadLengthExtended32_1);
                    if ($array['a'] != 0 || ord(substr($payloadLengthExtended, 4, 1)) & 128) {
                        $this->wsSendClientClose($clientID, self::WS_STATUS_MESSAGE_TOO_BIG);
                        return false;
                    }

                    // fetch length as 32 bit unsigned integer, not as 64 bit
                    $payloadLengthExtended32_2 = substr($payloadLengthExtended, 4, 4);
                    $array = unpack('Na', $payloadLengthExtended32_2);

                    // check if the payload data length exceeds 2,147,479,538 (2,147,483,647 - 14 - 4095)
                    // 14 for header size, 4095 for last recv() next frame bytes
                    if ($array['a'] > 2147479538) {
                        $this->wsSendClientClose($clientID, self::WS_STATUS_MESSAGE_TOO_BIG);
                        return false;
                    }

                    // store frame payload data length
                    $this->wsClients[$clientID][7] = $array['a'];
                }
            }

            // check if the frame's payload data length has now been stored
            if ($this->wsClients[$clientID][7] !== false) {

                // check if the frame's payload data length exceeds self::WS_MAX_FRAME_PAYLOAD_RECV
                if ($this->wsClients[$clientID][7] > self::WS_MAX_FRAME_PAYLOAD_RECV) {
                    $this->wsClients[$clientID][7] = false;
                    $this->wsSendClientClose($clientID, self::WS_STATUS_MESSAGE_TOO_BIG);
                    return false;
                }

                // check if the message's payload data length exceeds 2,147,483,647 or self::WS_MAX_MESSAGE_PAYLOAD_RECV
                // doesn't apply for control frames, where the payload data is not internally stored
                $controlFrame = (ord(substr($this->wsClients[$clientID][9], 0, 1)) & 8) == 8;
                if (!$controlFrame) {
                    $newMessagePayloadLength = $this->wsClients[$clientID][11] + $this->wsClients[$clientID][7];
                    if ($newMessagePayloadLength > self::WS_MAX_MESSAGE_PAYLOAD_RECV || $newMessagePayloadLength > 2147483647) {
                        $this->wsSendClientClose($clientID, self::WS_STATUS_MESSAGE_TOO_BIG);
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }

    function wsProcessClientFrame($clientID) {
        // store the time that data was last received from the client
        $this->wsClients[$clientID][3] = time();

        // fetch frame buffer
        $buffer = &$this->wsClients[$clientID][9];

        // check at least 6 bytes are set (first 2 bytes and 4 bytes for the mask key)
        if (substr($buffer, 5, 1) === false)
            return false;

        // fetch first 2 bytes of header
        $octet0 = ord(substr($buffer, 0, 1));
        $octet1 = ord(substr($buffer, 1, 1));

        $fin = $octet0 & self::WS_FIN;
        $opcode = $octet0 & 15;

        $mask = $octet1 & self::WS_MASK;
        if (!$mask)
            return false; // close socket, as no mask bit was sent from the client


            
// fetch byte position where the mask key starts
        $seek = $this->wsClients[$clientID][7] <= 125 ? 2 : ($this->wsClients[$clientID][7] <= 65535 ? 4 : 10);

        // read mask key
        $maskKey = substr($buffer, $seek, 4);

        $array = unpack('Na', $maskKey);
        $maskKey = $array['a'];
        $maskKey = array(
            $maskKey >> 24,
            ($maskKey >> 16) & 255,
            ($maskKey >> 8) & 255,
            $maskKey & 255
        );
        $seek += 4;

        // decode payload data
        if (substr($buffer, $seek, 1) !== false) {
            $data = str_split(substr($buffer, $seek));
            foreach ($data as $key => $byte) {
                $data[$key] = chr(ord($byte) ^ ($maskKey[$key % 4]));
            }
            $data = implode('', $data);
        } else {
            $data = '';
        }
        // check if this is not a continuation frame and if there is already data in the message buffer
        if ($opcode != self::WS_OPCODE_CONTINUATION && $this->wsClients[$clientID][11] > 0) {
            // clear the message buffer
            $this->wsClients[$clientID][11] = 0;
            $this->wsClients[$clientID][1] = '';
        }

        // check if the frame is marked as the final frame in the message
        if ($fin == self::WS_FIN) {
            // check if this is the first frame in the message
            if ($opcode != self::WS_OPCODE_CONTINUATION) {
                // process the message
                return $this->wsProcessClientMessage($clientID, $opcode, $data, $this->wsClients[$clientID][7]);
            } else {
                // increase message payload data length
                $this->wsClients[$clientID][11] += $this->wsClients[$clientID][7];

                // push frame payload data onto message buffer
                $this->wsClients[$clientID][1] .= $data;

                // process the message
                $result = $this->wsProcessClientMessage($clientID, $this->wsClients[$clientID][10], $this->wsClients[$clientID][1], $this->wsClients[$clientID][11]);

                // check if the client wasn't removed, then reset message buffer and message opcode
                if (isset($this->wsClients[$clientID])) {
                    $this->wsClients[$clientID][1] = '';
                    $this->wsClients[$clientID][10] = 0;
                    $this->wsClients[$clientID][11] = 0;
                }

                return $result;
            }
        } else {
            // check if the frame is a control frame, control frames cannot be fragmented
            if ($opcode & 8)
                return false;

            // increase message payload data length
            $this->wsClients[$clientID][11] += $this->wsClients[$clientID][7];

            // push frame payload data onto message buffer
            $this->wsClients[$clientID][1] .= $data;

            // if this is the first frame in the message, store the opcode
            if ($opcode != self::WS_OPCODE_CONTINUATION) {
                $this->wsClients[$clientID][10] = $opcode;
            }
        }

        return true;
    }

    function wsProcessClientMessage($clientID, $opcode, &$data, $dataLength) {
        // check opcodes
        if ($opcode == self::WS_OPCODE_PING) {
            // received ping message
            return $this->wsSendClientMessage($clientID, self::WS_OPCODE_PONG, $data);
        } elseif ($opcode == self::WS_OPCODE_PONG) {
            // received pong message (it's valid if the server did not send a ping request for this pong message)
            if ($this->wsClients[$clientID][4] !== false) {
                $this->wsClients[$clientID][4] = false;
            }
        } elseif ($opcode == self::WS_OPCODE_CLOSE) {
            // received close message
            if (substr($data, 1, 1) !== false) {
                $array = unpack('na', substr($data, 0, 2));
                $status = $array['a'];
            } else {
                $status = false;
            }

            if ($this->wsClients[$clientID][2] == self::WS_READY_STATE_CLOSING) {
                // the server already sent a close frame to the client, this is the client's close frame reply
                // (no need to send another close frame to the client)
                $this->wsClients[$clientID][2] = self::WS_READY_STATE_CLOSED;
            } else {
                // the server has not already sent a close frame to the client, send one now
                $this->wsSendClientClose($clientID, self::WS_STATUS_NORMAL_CLOSE);
            }

            $this->wsRemoveClient($clientID);
        } elseif ($opcode == self::WS_OPCODE_TEXT || $opcode == self::WS_OPCODE_BINARY) {
            if (array_key_exists('message', $this->wsOnEvents))
                foreach ($this->wsOnEvents['message'] as $func)
                    $func($clientID, $data, $dataLength, $opcode == self::WS_OPCODE_BINARY);
        }
        else {
            // unknown opcode
            return false;
        }

        return true;
    }

    function wsProcessClientHandshake($clientID, &$buffer) {
        // fetch headers and request line
        $sep = strpos($buffer, "\r\n\r\n");
        if (!$sep)
            return false;

        $headers = explode("\r\n", substr($buffer, 0, $sep));
        $headersCount = sizeof($headers); // includes request line
        if ($headersCount < 1)
            return false;

        // fetch request and check it has at least 3 parts (space tokens)
        $request = &$headers[0];
        $requestParts = explode(' ', $request);
        $requestPartsSize = sizeof($requestParts);
        if ($requestPartsSize < 3)
            return false;

        // check request method is GET
        if (strtoupper($requestParts[0]) != 'GET')
            return false;

        // check request HTTP version is at least 1.1
        $httpPart = &$requestParts[$requestPartsSize - 1];
        $httpParts = explode('/', $httpPart);
        if (!isset($httpParts[1]) || (float) $httpParts[1] < 1.1)
            return false;

        // store headers into a keyed array: array[headerKey] = headerValue
        $headersKeyed = array();
        for ($i = 1; $i < $headersCount; $i++) {
            $parts = explode(':', $headers[$i]);
            if (!isset($parts[1]))
                return false;

            $headersKeyed[trim($parts[0])] = trim($parts[1]);
        }

        // check Host header was received
        if (!isset($headersKeyed['Host']))
            return false;

        // check Sec-WebSocket-Key header was received and decoded value length is 16
        if (!isset($headersKeyed['Sec-WebSocket-Key']))
            return false;
        $key = $headersKeyed['Sec-WebSocket-Key'];
        if (strlen(base64_decode($key)) != 16)
            return false;

        // check Sec-WebSocket-Version header was received and value is 7
        if (!isset($headersKeyed['Sec-WebSocket-Version']) || (int) $headersKeyed['Sec-WebSocket-Version'] < 7)
            return false; // should really be != 7, but Firefox 7 beta users send 8


            
// work out hash to use in Sec-WebSocket-Accept reply header
        $hash = base64_encode(sha1($key . '258EAFA5-E914-47DA-95CA-C5AB0DC85B11', true));

        // build headers
        $headers = array(
            'HTTP/1.1 101 Switching Protocols',
            'Upgrade: websocket',
            'Connection: Upgrade',
            'Sec-WebSocket-Accept: ' . $hash
        );
        $headers = implode("\r\n", $headers) . "\r\n\r\n";

        // send headers back to client
        $socket = $this->wsClients[$clientID][0];

        $left = strlen($headers);
        do {
            $sent = @socket_send($socket, $headers, $left, 0);
            if ($sent === false)
                return false;

            $left -= $sent;
            if ($sent > 0)
                $headers = substr($headers, $sent);
        }
        while ($left > 0);

        return true;
    }

    // client write functions
    function wsSendClientMessage($clientID, $opcode, $message) {
        // check if client ready state is already closing or closed
        if ($this->wsClients[$clientID][2] == self::WS_READY_STATE_CLOSING || $this->wsClients[$clientID][2] == self::WS_READY_STATE_CLOSED)
            return true;

        // fetch message length
        $messageLength = strlen($message);

        // set max payload length per frame
        $bufferSize = 4096;

        // work out amount of frames to send, based on $bufferSize
        $frameCount = ceil($messageLength / $bufferSize);
        if ($frameCount == 0)
            $frameCount = 1;

        // set last frame variables
        $maxFrame = $frameCount - 1;
        $lastFrameBufferLength = ($messageLength % $bufferSize) != 0 ? ($messageLength % $bufferSize) : ($messageLength != 0 ? $bufferSize : 0);

        // loop around all frames to send
        for ($i = 0; $i < $frameCount; $i++) {
            // fetch fin, opcode and buffer length for frame
            $fin = $i != $maxFrame ? 0 : self::WS_FIN;
            $opcode = $i != 0 ? self::WS_OPCODE_CONTINUATION : $opcode;

            $bufferLength = $i != $maxFrame ? $bufferSize : $lastFrameBufferLength;

            // set payload length variables for frame
            if ($bufferLength <= 125) {
                $payloadLength = $bufferLength;
                $payloadLengthExtended = '';
                $payloadLengthExtendedLength = 0;
            } elseif ($bufferLength <= 65535) {
                $payloadLength = self::WS_PAYLOAD_LENGTH_16;
                $payloadLengthExtended = pack('n', $bufferLength);
                $payloadLengthExtendedLength = 2;
            } else {
                $payloadLength = self::WS_PAYLOAD_LENGTH_63;
                $payloadLengthExtended = pack('xxxxN', $bufferLength); // pack 32 bit int, should really be 64 bit int
                $payloadLengthExtendedLength = 8;
            }

            // set frame bytes
            $buffer = pack('n', (($fin | $opcode) << 8) | $payloadLength) . $payloadLengthExtended . substr($message, $i * $bufferSize, $bufferLength);

            // send frame
            $socket = $this->wsClients[$clientID][0];

            $left = 2 + $payloadLengthExtendedLength + $bufferLength;
            do {
                $sent = @socket_send($socket, $buffer, $left, 0);
                if ($sent === false)
                    return false;

                $left -= $sent;
                if ($sent > 0)
                    $buffer = substr($buffer, $sent);
            }
            while ($left > 0);
        }

        return true;
    }

    function wsSendClientClose($clientID, $status = false) {
        // check if client ready state is already closing or closed
        if ($this->wsClients[$clientID][2] == self::WS_READY_STATE_CLOSING || $this->wsClients[$clientID][2] == self::WS_READY_STATE_CLOSED)
            return true;

        // store close status
        $this->wsClients[$clientID][5] = $status;

        // send close frame to client
        $status = $status !== false ? pack('n', $status) : '';
        $this->wsSendClientMessage($clientID, self::WS_OPCODE_CLOSE, $status);

        // set client ready state to closing
        $this->wsClients[$clientID][2] = self::WS_READY_STATE_CLOSING;
    }

    // client non-internal functions
    function wsClose($clientID) {
        return $this->wsSendClientClose($clientID, self::WS_STATUS_NORMAL_CLOSE);
    }

    function wsSend($clientID, $message, $binary = false) {
        return $this->wsSendClientMessage($clientID, $binary ? self::WS_OPCODE_BINARY : self::WS_OPCODE_TEXT, $message);
    }

    function log($message, $status = 0) {
        $re = 'Request';
        if ($status == 1) {
            $re = 'Responce';
        } else if ($status == 2) {
            $re = 'System Info';
        }
        echo "\n---------------------------------------- Time: " . date("H:m:s") . " ($re)\n\n";
        if (is_array($message)) {
            print_r($message);
        } else {
            echo $message;
        }
    }

    function bind($type, $func) {
        if (!isset($this->wsOnEvents[$type]))
            $this->wsOnEvents[$type] = array();
        $this->wsOnEvents[$type][] = $func;
    }

    function unbind($type = '') {
        if ($type)
            unset($this->wsOnEvents[$type]);
        else
            $this->wsOnEvents = array();
    }

    /**
     * Connect with Database
     * @global mixed $db
     * @author Sandip Gopani (SAG)
     */
    function db() {
        global $db;
        $link = mysqli_connect($db['host'], $db['username'], $db['password'], $db['database']);
        if (mysqli_connect_error()) {
            return null;
        } else {
            return $link;
        }
    }

    /**
     * Mange individual text chat
     * @param int $clientID
     * @param Array $data
     * @author Sandip Gopani (SAG)
     */
    function single_chat($clientID, $data = null) {
        $this->wsClients[$clientID][13] ++;
        if (is_array($data) && !empty($data)) {
            $link = $this->db();
            $from = mysqli_escape_string($link, $data['from']);
            $to = mysqli_escape_string($link, $data['to']);
            $msg = mysqli_escape_string($link, $data['message']);

            // User cannot send messages to self
            if ($from != $to) {
                $all = $this->class_mate_list($from);

                // Check user can send messages to only his classmates.
                if (in_array($to, $all)) {
                    $received_status = 0;
                    foreach ($this->wsClients as $id => $client) {
                        // Check receiver is online or not.
                        if ($this->wsClients[$id][12] == $to) {
                            $received_status = 1;
                            break;
                        }
                    }

                    $query = "INSERT INTO `ism`.`" . TBL_USER_CHAT . "` (`id`, `sender_id`, `receiver_id`, `message`, `media_link`, `media_type`, `received_status`, `created_date`, `is_delete`, `is_testdata`) "
                            . "VALUES (NULL, $from, $to, '$msg', NULL, NULL, $received_status, CURRENT_TIMESTAMP, '0', 'yes')";
                    $x = mysqli_query($link, $query);
                    $data['insert_id'] = mysqli_insert_id($link);
                    if (!$x) {
                        $data['to'] = 'self';
                        $data['error'] = 'Unable to save message.! Please try again.';
                    }
                } else {
                    $data['to'] = 'self';
                    $data['error'] = 'Please do not modify things manually!';
                }
            } else {
                $data['to'] = 'self';
                $data['error'] = 'You cannot send messages to self!';
            }
            return $data;
        } else {
            return null;
        }
    }

    /**
     * Sync UserID with websocketID.
     * @param int $clientID
     * @param array $data
     * @return string
     * @author Sandip Gopani (SAG)
     */
    function sync($clientID, $data) {

        // Check userID is sync only one time.
        if ($this->wsClients[$clientID][13] == 0) {
            $this->wsClients[$clientID][12] = $data['from'];
            $this->wsClients[$clientID][13] ++;

            // Contains list of corrently online classmates.
            $data['online_user'] = $this->check_online_classmate($data['from']);
        } else {
            $data['to'] = 'self';
            $data['error'] = 'Manual Modification not allowed!';
        }
        return $data;
    }

    /**
     * Get array of all study mates.
     * @param int $user_id
     * @return string
     * @author Sandip Gopani (SAG)
     */
    function class_mate_list($user_id, $status = true) {

        $link = $this->db();
        $query = "SELECT mate_id, mate_of FROM `" . TBL_STUDYMATES . "` WHERE ( mate_id = $user_id OR mate_of= $user_id ) and is_delete = 0";
        $row = mysqli_query($link, $query);
        $all = array();
        while ($rows = mysqli_fetch_assoc($row)) {
            if ($rows['mate_id'] !== $user_id) {
                $all[] = $rows['mate_id'];
            }
            if ($rows['mate_of'] !== $user_id) {
                $all[] = $rows['mate_of'];
            }
        }
        if ($status) {
            $all[] = $user_id;
        }
        if ($link != null) {
            mysqli_close($link);
        }
        return $all;
    }

    /**
     * Get user info based on id passed.
     * @param int $id
     * @return array
     * @author Sandip Gopani (SAG)
     */
    function get_client_info($id) {
        $link = $this->db();
        $query = "SELECT `u`.`id`,`u`.`full_name`, `upp`.`profile_link`  FROM `" . TBL_USERS . "` `u` LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `upp` ON `upp`.`user_id` = `u`.`id` WHERE `u`.`id` = $id LIMIT 1";
        $row = mysqli_query($link, $query);
        $count = mysqli_num_rows($row);
        $rows = mysqli_fetch_assoc($row);
        mysqli_close($link);
        if ($count == 1) {
            return array(
                'id' => $rows['id'],
                'full_name' => $rows['full_name'],
                'profile_link' => $rows['profile_link']
            );
        } else {
            return null;
        }
    }

    /**
     * Get list current online studymates.
     * @param int $id
     * @return string
     * @author Sandip Gopani (SAG)
     */
    function check_online_classmate($id) {
        $all = $this->class_mate_list($id, false);
        $online = array();
        foreach ($this->wsClients as $id => $value) {
            if (in_array($value[12], $all)) {
                $online[] = $value[12];
            }
        }
        return implode('-', $online);
    }

    /**
     * Get list of latest message.
     * @param array $data
     * @param int $userID
     * @return string
     * @author Sandip Gopani (SAG)
     */
    function get_latest_msg($data = null, $userID) {

        $query = "SELECT `uc`.`id`, `uc`.`sender_id`, `uc`.`receiver_id`, `uc`.`message`,`uc`.`media_link`,`uc`.`media_type` FROM `" . TBL_USER_CHAT . "` `uc` WHERE (`uc`.`sender_id` = " . $data['my_id'] . " AND `uc`.`receiver_id` = $userID) OR (`uc`.`sender_id` = $userID AND `uc`.`receiver_id` = " . $data['my_id'] . ") ORDER BY `uc`.`id` DESC LIMIT 10";
        $link = $this->db();
        mysqli_query($link, "UPDATE `" . TBL_USER_CHAT . "` `uc` SET  `uc`.`received_status` = 1  WHERE `uc`.`received_status` = 0 AND `uc`.`sender_id` = " . $data['my_id'] . " AND `uc`.`receiver_id` =" . $userID);
        $row = mysqli_query($link, $query);
        $result = array();
        $check_type = array(
            'image/png',
            'image/jpg',
            'image/jpeg',
            'image/gif'
        );
        while ($rows = mysqli_fetch_assoc($row)) {

            $result[] = array(
                'sender_id' => $rows['sender_id'],
                'receiver_id' => $rows['receiver_id'],
                'message' => $rows['message'],
                'media_link' => $rows['media_link'],
                'media_type' => $rows['media_type']
            );
        }
        //  $result = array_reverse($result);
        $html = '';
        foreach ($result as $value) {
            $message = $value['message'];
            if ($message == null) {

                // If sent file is image than image is displayed otherwise default image is desplayed.
                if (in_array($value['media_type'], $check_type)) {
                    $message = '<a href="uploads/' . $value['media_link'] . '"  target="_BLANK"><img src="uploads/' . $value['media_link'] . '" width="50" height="50" /></a>';
                } else {
                    $message = '<a href="uploads/' . $value['media_link'] . '"  target="_BLANK"><img src="assets/images/default_chat.png" width="50" height="50" /></a>';
                }
            }
            // check current user is sender.
            if ($value['sender_id'] == $data['my_id']) {
                $html .= '<div class="from"><p>' . $message . '</p></div>';
            } else {
                $html .= '<div class="to"><p>' . $message . '</p></div>';
            }
        }
        $data['message'] = $html;
        return $data;
    }

    /**
     * Save New feed.
     * @param int $user_id
     * @param Array $data
     * @return Array
     * @author Sandip Gopani (SAG)
     */
    function classmate_post($user_id, $data = null) {
        if (is_array($data) && !empty($data)) {
            $link = $this->db();
            $msg = mysqli_escape_string($link, $data['message']); // Feed or comment
            $query = "INSERT INTO `" . TBL_FEEDS . "`(`id`, `feed_by`, `feed_text`, `video_link`, `audio_link`, `posted_on`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES (NULL,$user_id,'$msg','','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,0,'yes')";
            $x = mysqli_query($link, $query);
            $data['post_id'] = mysqli_insert_id($link);
            $data['tot_like'] = 0;
            $data['tot_comment'] = 0;
            if (!$x) {
                $data['to'] = 'self';
                $data['error'] = 'Unable to save message.! Please try again.';
            }
        }
        return array_merge($data, $this->get_client_info($user_id));
    }

    /**
     * Save feed comment
     * @param int $user_id
     * @param Array $data
     * @author Sandip Gopani (SAG)
     */
    function classmate_comment($user_id, $data) {
        $link = $this->db();
        $query = "SELECT `f`.`feed_by` FROM `" . TBL_FEEDS . "` `f` WHERE `f`.`id` = " . $data['to'] . " LIMIT 1";
        $row = mysqli_query($link, $query);

// Check feed must exist on which comment is sent.
        if (mysqli_num_rows($row) == 1) {
            $rows = mysqli_fetch_assoc($row);
            $data['allStudyMate'] = $this->class_mate_list($rows['feed_by']);

            // Check user must comment on those feed which is added by his/him classmates not to others.
            if (in_array($user_id, $data['allStudyMate'])) {
                $query = "INSERT INTO `ism`.`" . TBL_FEED_COMMENT . "` (`id`, `comment`, `comment_by`, `feed_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES (NULL, '" . $data['message'] . "',$user_id, '" . $data['to'] . "', CURRENT_TIMESTAMP, '0000-00-00 00:00:00', '0', 'yes');";
                $x = mysqli_query($link, $query);
                if (!$x) {
                    $data['to'] = "self";
                    $data['error'] = "Unable to save your comment! Please try again.";
                }
            } else {
                $data['to'] = "self";
                $data['error'] = "You are not authorized to commet on this post.";
            }
        } else {
            $data['to'] = 'self';
            $data['error'] = 'Unable to Identify post. Please don\'t modify data manually.';
        }
        return array_merge($data, $this->get_client_info($user_id));
    }

    /**
     * Load more feeds.
     * @param int $user_id
     * @param array $data
     * @return array
     */
    function load_more($user_id, $data = null) {
        $link = $this->db();
        if (is_array($data)) {
            if (!empty($data['start']) && is_numeric($data['start'])) {
                $limit = 4;
                $ID_in = implode(',', $this->class_mate_list($user_id));
                $data['start'] += $limit;
                $query = "SELECT `f`.`id` as `post_id`, `f`.`feed_by`, `f`.`feed_text` as `message`, `f`.`posted_on`, `u`.`full_name`, `l`.`is_delete` as my_like , (select count(*) from " . TBL_FEED_COMMENT . " where feed_id = f.id and is_delete = 0) as tot_comment, (select count(*) from " . TBL_FEED_LIKE . " where feed_id = f.id and is_delete = 0) as tot_like, `p`.`profile_link` FROM `feeds` `f` LEFT JOIN `users` `u` ON `u`.`id` = `f`.`feed_by` LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `p` ON `u`.`id` = `p`.`user_id` LEFT JOIN `feed_like` `l` ON `l`.`feed_id` = `f`.`id` AND `l`.`like_by` = $user_id  WHERE `f`.`is_delete` = 0 AND `f`.`feed_by` IN($ID_in) ORDER BY `f`.`id` DESC LIMIT " . $data['start'] . "," . $limit;
                $row = mysqli_query($link, $query);
                echo mysqli_error($link);
                $all = array();
                while ($rows = mysqli_fetch_assoc($row)) {
                    $all[] = $rows;
                }
                $data['feed'] = $all;
            }
        }
        return $data;
    }
/**
 * 
 * @param type $user_id
 * @param type $data
 * @return type
 */
    function post_like_unlike($user_id, $data) {
        $link = $this->db();
        $query = "SELECT `f`.`feed_by` FROM `" . TBL_FEEDS . "` `f` WHERE `f`.`id` = " . $data['fid'] . " LIMIT 1";
        $row = mysqli_query($link, $query);
        if (mysqli_num_rows($row) == 1) {
            $rows = mysqli_fetch_assoc($row);
            $data['allStudyMate'] = $this->class_mate_list($rows['feed_by']);
            if (in_array($user_id, $data['allStudyMate'])) {
                $query = "SELECT * FROM " . TBL_FEED_LIKE . " WHERE feed_id =" . $data['fid'] . " and like_by=" . $user_id;
                $row = mysqli_query($link, $query);
                $row_cnt = $row->num_rows;
                if ($row_cnt > 0) {
                    $dt = mysqli_fetch_assoc($row);
                    if ($dt['is_delete'] == 0) {
                        $query = "update " . TBL_FEED_LIKE . " set is_delete = 1 WHERE feed_id =" . $data['fid'] . " and like_by=" . $user_id;
                        $data['message'] = 'unlike';
                    } else {
                        $query = "update " . TBL_FEED_LIKE . " set is_delete = 0 WHERE feed_id =" . $data['fid'] . " and like_by=" . $user_id;
                        $data['message'] = 'like';
                    }
                } else {
                    $query = "INSERT INTO `" . TBL_FEED_LIKE . "`(`id`, `like_by`, `feed_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES (NULL," . $user_id . "," . $data['fid'] . ",CURRENT_TIMESTAMP,NULL,0,'yes')";
                    $data['message'] = 'like';
                }
                mysqli_query($link, $query);
                $query = 'SELECT count(*) cnt FROM ' . TBL_FEED_LIKE . ' where is_delete = 0  AND feed_id=' . $data['fid'];
                $row = mysqli_query($link, $query);
                $d = mysqli_fetch_assoc($row);
                $data['like_cnt'] = $d['cnt'];
            } else {
                $data['to'] = "self";
                $data['error'] = "You are not authorized to commet on this post.";
            }
        } else {
            $data['to'] = 'self';
            $data['error'] = 'Unable to Identify post. Please don\'t modify data manually.';
        }
        return array_merge($data, $this->get_client_info($user_id));
    }

    /**
     * Save Disscussion of tutorial group. 
     * @param int $userId
     * @param array $data
     */
    function discussion($userId, $data = null) {
        $data['active_count'] = $data['group_score'] = $data['my_score'] = 'skip';
        $data['time_to_left'] = 0;
        $data['message'] = preg_replace('!\s+!', ' ', $data['message']);
        $link = $this->db();
        $words = explode(' ', $data['message']);

        if (is_array($data) && !empty($data)) {


            // Get  score related configuration from admin_config table. And stored into $config variable.
            $query = "select `ac`.`config_key`,`ac`.`config_value` FROM `" . TBL_ADMIN_CONFIG . "` `ac` WHERE `ac`.`config_key` IN('activeHrFirstCommentScore','activeHoursFirstCommentCount','nonActivehoursScore','spamWordDeduction','activeHoursCommentScore','groupScoreFromIndividual')";
            $row = mysqli_query($link, $query);
            $config = array();
            while ($rows = mysqli_fetch_assoc($row)) {
                $config[$rows['config_key']] = $rows['config_value'];
            }

            $day = getdate();
            $c_week = ceil($day['yday'] / 7);
            $current_date = $day['year'] . '-' . $day['mon'] . '-' . $day['mday'];

            $query = "SELECT `tm`.`id` as member_id,`tg`.`topic_id`, `tm`.`group_id` FROM  `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `tg` "
                    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `tg`.`group_id` "
                    . "WHERE `tm`.`user_id` = $userId AND `tg`.`week_no` = $c_week LIMIT 1";
            $row = mysqli_query($link, $query);

            $score = 0;
            if (mysqli_num_rows($row) == 1) {
                $rows = mysqli_fetch_assoc($row);
                $is_active = 0;
                $data['time_to_left'] = $this->active_hours();
                $score = $config['nonActivehoursScore'];
                $query = "SELECT `td`.`comment_score`,`td`.`sender_id` FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` WHERE `td`.`group_id` = " . $rows['group_id'] . " AND `td`.`topic_id` = " . $rows['topic_id'] . " AND date(`td`.`created_date`) = '$current_date' ORDER BY `td`.`id` DESC LIMIT 0,3";
                $row = mysqli_query($link, $query);
                $consecutvie = 0;
                $allow_score = true;
                $i = 1;
                while ($rowq = mysqli_fetch_assoc($row)) {
                    if ($rowq['sender_id'] == $userId) {
                        if ($rowq['comment_score'] == 0) {
                            $consecutvie ++;
                        } else {
                            if ($consecutvie < 2) {
                                $allow_score = false;
                            }
                            break;
                        }
                    } else {
                        break;
                    }
                    $i++;
                }

                if ($allow_score == true) {
                    // Check comment is in active hours.
                    if ($data['time_to_left'] > 0) {
                        $is_active = 1;

                        $query = "SELECT count(id) as total_count FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` WHERE date(`created_date`) = '$current_date' AND `td`.`in_active_hours` = 1 ";
                        $row = mysqli_query($link, $query);
                        $rowp = mysqli_fetch_assoc($row);
                        $score = $config['activeHoursCommentScore'];

                        // Check total comment of the day is less than value of wesactiveHrsFirstCommentCount field in admin config.
                        if ($rowp['total_count'] <= $config['activeHoursFirstCommentCount']) {
                            if (count($words) >= 3) {
                                $score = $config['activeHrFirstCommentScore'];
                            }
                        }
                    } else {
                        $score = $config['nonActivehoursScore'];
                    }
                } else {
                    $score = 0;
                }
                // Check spam word exist or not. If spam word exist deduct points based on admin_config (spamWordDeduction) value. 
                $query = "SELECT * FROM `" . TBL_WORD_WATCH . "` `ww`";
                $row = mysqli_query($link, $query);
                while ($rowq = mysqli_fetch_assoc($row)) {
                    if (in_array($rowq['word'], $words)) {
                        $score -= $config['spamWordDeduction'];
                        $data['error'] = 'Spam words found. <b>' . $config['spamWordDeduction'] . ' points</b> diducted!';
                        break;
                    }
                }

                $query = "INSERT INTO `ism`.`" . TBL_TUTORIAL_GROUP_DISCUSSION . "` (`id`, `group_id`, `topic_id`, `sender_id`, `comment_score`, `message`, `message_type`, `message_status`, `in_active_hours`, `media_link`, `media_type`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES (NULL, '" . $rows['group_id'] . "', '" . $rows['topic_id'] . "', $userId, $score,'" . $data['message'] . "', '', '', $is_active, '', '', CURRENT_TIMESTAMP, '0000-00-00 00:00:00', '0', 'yes')";
                $x = mysqli_query($link, $query);
                $data['disscusion_id'] = mysqli_insert_id($link);

                // Update group score and student score.
                if ($x) {
                    $add_to_group = 0;
                    if ($score > 0) {
                        // Get persentage of score to add in group score.
                        $add_to_group = ceil(($score * $config['groupScoreFromIndividual']) / 100);
                        $query = "UPDATE `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` SET `group_score` = `group_score` + $add_to_group WHERE `group_id` = " . $rows['group_id'] . " AND `topic_id` = " . $rows['topic_id'] . " AND `week_no` = " . $c_week;
                        mysqli_query($link, $query);
                    }
                    $query = "UPDATE `" . TBL_TUTORIAL_GROUP_MEMBER_SCORE . "` SET `score` = `score` + $score WHERE `topic_id` = " . $rows['topic_id'] . " AND member_id =" . $rows['member_id'];
                    mysqli_query($link, $query);
                } else {
                    $data['to'] = 'self';
                    $data['error'] = 'Unable to save your message! Try again!';
                }

                $query = "SELECT `ts`.`score` as `my_score`,(SELECT SUM(group_score) FROM " . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . " WHERE group_id = " . $rows['group_id'] . ") as group_score,(SELECT count(`td`.`id`) FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` WHERE `td`.`group_id` = " . $rows['group_id'] . " AND `td`.`topic_id` = " . $rows['topic_id'] . " AND in_active_hours = 1) as active_count  FROM `" . TBL_TUTORIAL_TOPIC . "` `t` LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER_SCORE . "` `ts` ON `ts`.`member_id` = `tm`.`id` LEFT JOIN `" . TBL_USERS . "` `u` ON `u`.`id` = `t`.`created_by` LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `up` ON `up`.`user_id` = `u`.`id` WHERE `ta`.`group_id` = '" . $rows['group_id'] . "' AND `ta`.`week_no` = $c_week AND `tm`.`user_id` = '$userId'";
                $row = mysqli_query($link, $query);
                $rows = mysqli_fetch_assoc($row);

                foreach ($rows as $k => $v) {
                    $data[$k] = $v;
                }
            } else {
                $data['error'] = 'No topic allocated! or Discussion time is over!';
            }
        }
        $data['allStudyMate'] = $this->class_mate_list($userId);
        return array_merge($data, $this->get_client_info($userId));
    }

    /**
     * Return difference between two times in seconds.
     * @time1 = Basically End Time
     * @time2 = Basically Current Time
     * @Author = Sandip Gopani (SAG)
     */
    function dateDiff($time1, $time2) {
        // If not numeric then convert texts to unix timestamps
        if (!is_int($time1)) {
            $time1 = strtotime($time1);
        }
        if (!is_int($time2)) {
            $time2 = strtotime($time2);
        }

        // If time1 is bigger than time2
        // Then swap time1 and time2
        if ($time1 > $time2) {
            $ttime = $time1;
            $time1 = $time2;
            $time2 = $ttime;
        }

        // Set up intervals and diffs arrays
        $intervals = array('year', 'month', 'day', 'hour', 'minute', 'second');
        $diffs = array();

        // Loop thru all intervals
        foreach ($intervals as $interval) {
            // Create temp time from time1 and interval
            $ttime = strtotime('+1 ' . $interval, $time1);
            // Set initial values
            $add = 1;
            $looped = 0;
            // Loop until temp time is smaller than time2
            while ($time2 >= $ttime) {
                // Create new temp time from time1 and interval
                $add++;
                $ttime = strtotime("+" . $add . " " . $interval, $time1);
                $looped++;
            }

            $time1 = strtotime("+" . $looped . " " . $interval, $time1);
            $diffs[$interval] = $looped;
        }

        $count = 0;
        $times = array();
        // Loop thru all diffs
        foreach ($diffs as $interval => $value) {
            // Break if we have needed precission

            if ($count >= 6) {
                break;
            }
            // Add value and interval 
            if ($value > 0) {
                // Add value and interval to times array
                $times[$interval] = $value;
                $count++;
            }
        }
        $check = array('day' => 86400, 'hour' => 3600, 'minute' => 60, 'second' => 1);
        $seconds = 0;
        foreach ($times as $key => $value) {
            foreach ($check as $k => $v) {
                if ($k == $key) {
                    $seconds += $value * $check[$key];
                }
            }
        }
        return $seconds;
    }

    /**
     * 	This function will return remaining seconds of active hours.
     * 	return  int 
     * 	@author = Sandip Gopani (SAG)
     */
    function active_hours($status = 0) {
        $link = $this->db();
        $starttime = $endtime = null;
        $output = 0;
        $currenttime = getdate(); // Get an array of current time

        if ($currenttime['hours'] < 10) {
            $currenttime['hours'] = '0' . $currenttime['hours'];
        }
        if ($currenttime['minutes'] < 10) {
            $currenttime['minutes'] = '0' . $currenttime['minutes'];
        }

        if ($currenttime['seconds'] < 10) {
            $currenttime['seconds'] = '0' . $currenttime['seconds'];
        }

        // Store current hours and minutes
        $c_full_time = $currenttime['hours'] . ':' . $currenttime['minutes'] . ':' . $currenttime['seconds'];
        $query = "SELECT `ac`.`config_value`, `ac`.`config_key` FROM `" . TBL_ADMIN_CONFIG . "` `ac` WHERE `ac`.`config_key` = 'activeHoursStartTime' OR  `ac`.`config_key` = 'activeHoursEndTime'";
        $row = mysqli_query($link, $query);
        if (mysqli_num_rows($row) == 2) {
            while ($rows = mysqli_fetch_assoc($row)) {
                $time_part = explode(':', $rows['config_value']);
                if (!isset($time_part[2])) {
                    $time_part[2] = '00';
                } else if ($time_part[2] < 10 && strlen($time_part[2]) == 1) {
                    $time_part[2] = '0' . $time_part[2];
                }

                if ($time_part[0] < 10 && strlen($time_part[0]) == 1) {
                    $time_part[0] = '0' . $time_part[0];
                }

                if ($time_part[1] < 10 && strlen($time_part[1]) == 1) {
                    $time_part[1] = '0' . $time_part[1];
                }

                if ($rows['config_key'] == 'activeHoursStartTime') {

                    // Asign time and remove seconds from value incase added by admin ( e.g  11:30:54 will become 11:30 ). Same with else part
                    $starttime = implode(':', $time_part);
                } else {
                    $endtime = implode(':', $time_part);
                }
            }
            if ($status == 2)
                $this->log("Current Time: " . $c_full_time . "\n\nStart Time: " . $starttime . "\n\nEnd Time: " . $endtime, 2);
            if ($starttime !== null && $endtime !== null) {
                // Convert to date time
                $cur = DateTime::createFromFormat('H:i:s', $c_full_time);
                $start = DateTime::createFromFormat('H:i:s', $starttime);
                $end = DateTime::createFromFormat('H:i:s', $endtime);

                if ($status == 1) {
                    if ($cur < $start) {
                        $output = $this->dateDiff($c_full_time, $starttime);
                    } else if ($cur > $end) {
                        $output = $this->dateDiff('00:00:00', $starttime);
                        $output += $this->dateDiff($c_full_time, '24:00:00');
                    }
                } else if ($status == 2) {
                    $output = $this->dateDiff($starttime, $endtime);
                } else if ($status == 3) {
                    $output = $this->dateDiff('00:00:00', $starttime);
                    $output += $this->dateDiff($endtime, '24:00:00');
                } else {
                    // Check current time is between $starttime and $endtime
                    if ($cur > $start && $cur < $end) {
                        $output = $this->dateDiff($endtime, $c_full_time);
                    }
                }
            }
        }
        return $output;
    }

    function dictionary($data = null) {
        if (is_array($data)) {
            $curl_handle = curl_init();
            curl_setopt($curl_handle, CURLOPT_URL, 'http://www.stands4.com/services/v2/defs.php?uid=4350&tokenid=CL5ORBQQ9fbL7uno&word=' . $data['keyword']);
            curl_setopt($curl_handle, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($curl_handle, CURLOPT_CUSTOMREQUEST, "GET");
            $buffer = curl_exec($curl_handle);
            curl_close($curl_handle);
            $data['message'] = $buffer;

            return $data;
        }
    }

    function close_studymate($userid, $data) {
        $link = $this->db();
        $query = "UPDATE " . TBL_STUDYMATES . " SET is_delete = 1 where (mate_id = " . $userid . " and mate_of=" . $data['studymate_id'] . ") or (mate_of = " . $userid . " and mate_id = " . $data['studymate_id'] . ")";
        $x = mysqli_query($link, $query);
        if (!$x) {
            $data['to'] = 'self';
            $data['error'] = 'Unable to Identify studymate. Please don\'t modify data manually.';
        }
        $data['result'] = 'Done';
        return $data;
    }

    function send_studymate_request($userid, $data) {

        $link = $this->db();
        $studymate = $this->class_mate_list($userid, false);
        // if (sizeof($studymate) > 0) {

        $query = "SELECT id FROM " . TBL_STUDYMATES_REQUEST . " where request_from_mate_id=" . $userid . " and request_to_mate_id=" . $data['studymate_id'] . " and status IN (0,2)";
        $rows = mysqli_query($link, $query);
        $row_cnt = mysqli_num_rows($rows);
        if ($row_cnt > 0) {
            $data['to'] = 'self';
            $data['error'] = 'Syudymates request already sent!. You can\'t send again';
        } else {
            if (sizeof($studymate) > 0)
                $where = "`in1`.`user_id` NOT IN(" . implode(',', $studymate) . ")";
            else
                $where = ' 1=1';
            $query = "SELECT group_id FROM " . TBL_TUTORIAL_GROUP_MEMBER . " where user_id=" . $userid;
            $rows = mysqli_query($link, $query);
            $row = mysqli_fetch_assoc($rows);
            $group_id = $row['group_id'];

            $query = "SELECT `in1`.`user_id` FROM `" . TBL_TUTORIAL_GROUP_MEMBER . "` `m` JOIN `" . TBL_STUDENT_ACADEMIC_INFO . "` `in` ON `in`.`user_id` = `m`.`user_id` JOIN `" . TBL_STUDENT_ACADEMIC_INFO . "` `in1` ON `in`.`classroom_id` = `in1`.`classroom_id` and `in`.`course_id` = `in1`.`course_id` and `in`.`academic_year` = `in1`.`academic_year` and `in`.`school_id` = `in1`.`school_id` WHERE `m`.`group_id` = " . $group_id . " AND `in1`.`user_id` != " . $userid . " AND $where  GROUP BY `in1`.`user_id`";

            $rows = mysqli_query($link, $query);
            while ($row = mysqli_fetch_assoc($rows)) {
                $all[] = $row['user_id'];
            }

            if (in_array($data['studymate_id'], $all)) {
                $query = "INSERT INTO `" . TBL_STUDYMATES_REQUEST . "`(`id`, `request_from_mate_id`, `request_to_mate_id`, `status`, `created_date`, `is_delete`, `is_testdata`) VALUES (NULL,$userid," . $data['studymate_id'] . ",0,CURRENT_TIMESTAMP,0,'yes')";
                $x = mysqli_query($link, $query);
                if (!$x) {
                    $data['to'] = 'self';
                    $data['error'] = 'Unable to save message.! Please try again.';
                } else {
                    $query = "SELECT count(*) as cnt FROM " . TBL_STUDYMATES_REQUEST . " where request_from_mate_id =" . $data['studymate_id'] . " status = 0";
                    $rows = mysqli_query($link, $query);
                    $row = mysqli_fetch_assoc($rows);
                    $data['count'] = $row['cnt'];
                }
            } else {
                $data['to'] = 'self';
                $data['error'] = 'Unable to Identify post. Please don\'t modify data manually.';
            }
            // }
        }
        return $data;
    }

    function view_all_comment_activities($user_id, $data) {
        $link = $this->db();
        $query = "SELECT *,u.full_name FROM " . TBL_FEED_COMMENT . " left join " . TBL_USER_PROFILE_PICTURE . " on comment_by=user_id LEFT JOIN " . TBL_USERS . " u on u.id = comment_by where comment_by=" . $user_id . ' and feed_id=' . $data['comment_id'];
        $row = mysqli_query($link, $query);
        if (mysqli_num_rows($row) > 0) {
            $all = array();
            while ($rows = mysqli_fetch_assoc($row)) {
                $all[] = $rows['comment'];
                $link = $rows['profile_link'];
                $name = $rows['full_name'];
            }
            $data['comment'] = $all;
            $data['profile'] = $link;
            $data['name'] = $name;
        } else {
            $data['to'] = 'self';
            $data['error'] = 'Please don\'t modify data manually.';
        }
        return $data;
    }

    function set_unread($data = null) {
        if (is_array($data) && $data != null) {
            $link = $this->db();
            $query = "UPDATE `" . TBL_USER_CHAT . "` SET `received_status` = 0 WHERE `id` = " . $data['insert_id'];
            mysqli_query($link, $query);
        }
    }

    function accept_decline_request($user_id, $data) {
        $link = $this->db();
        $data['is_online'] = $this->online_status($data['studymate_id']);
        $query = 'SELECT * FROM ' . TBL_STUDYMATES_REQUEST . ' WHERE request_to_mate_id=' . $user_id . ' and request_from_mate_id=' . $data['studymate_id'] . ' and status in (0,2)';
        $row = mysqli_query($link, $query);
        if (mysqli_num_rows($row) > 0) {
            if ($data['sub_type'] == 'accept-request') {
                $query = 'INSERT INTO `' . TBL_STUDYMATES . '`(`id`, `mate_id`, `mate_of`, `is_online`, `created_date`, `is_delete`, `is_testdata`) VALUES (NULL,' . $user_id . ',' . $data['studymate_id'] . ',0,CURRENT_TIMESTAMP,0,"yes")';
                $x = mysqli_query($link, $query);
                if (!$x) {
                    $data['to'] = 'self';
                    $data['error'] = 'Unable to save message.! Please try again.';
                }
                $query = 'UPDATE ' . TBL_STUDYMATES_REQUEST . ' SET status = 1,is_delete = 1 WHERE request_from_mate_id=' . $data['studymate_id'] . ' and request_to_mate_id=' . $user_id;
                $x = mysqli_query($link, $query);
                if (!$x) {
                    $data['to'] = 'self';
                    $data['error'] = 'Unable to save message.! Please try again.';
                }
                $data['message'] = 'accepted';
            }

            if ($data['sub_type'] == 'decline-request') {
                $query = 'UPDATE ' . TBL_STUDYMATES_REQUEST . ' SET is_delete = 1 WHERE request_from_mate_id=' . $data['studymate_id'] . ' and request_to_mate_id=' . $user_id;
                $x = mysqli_query($link, $query);
                if (!$x) {
                    $data['to'] = 'self';
                    $data['error'] = 'Unable to save message.! Please try again.';
                }

                $data['message'] = 'decline';
            }

            $query = 'SELECT u.full_name,s.school_name,c.course_name,p.profile_link FROM ' . TBL_USERS . ' u left join ' . TBL_STUDENT_ACADEMIC_INFO . ' info on info.user_id = u.id left join ' . TBL_SCHOOLS . ' s on s.id = info.school_id left join ' . TBL_COURSES . ' c on c.id = info.course_id left join ' . TBL_USER_PROFILE_PICTURE . ' p on p.user_id = u.id where u.id=' . $data['studymate_id'];
            $row = mysqli_query($link, $query);
            $rows = mysqli_fetch_assoc($row);
            $data['full_name'] = $rows['full_name'];
            $data['school_name'] = $rows['school_name'];
            $data['course_name'] = $rows['course_name'];
            $data['profile'] = $rows['profile_link'];
        } else {
            $data['to'] = 'self';
            $data['error'] = 'Please don\'t modify data manually.';
        }
        return $data;
    }

    /**
     * Check weather user is online or not.
     * @param type $user_id
     * @return boolean
     */
    function online_status($user_id) {
        $status = false;
        foreach ($this->wsClients as $id => $client) {
            if ($this->wsClients[$id][12] == $user_id) {
                $status = true;
                break;
            }
        }
        return $status;
    }

    /**
     * Single user chat for images
     * @param type $user_id
     * @param type $data
     * @Author Sandip Gopani (SAG)
     */
    function save_sent_file($user_id, $data = null) {
        $time = time();
        $link = $this->db();
        $output_file = dirname(__DIR__) . "\\uploads\\user_" . $user_id;
        if (!file_exists($output_file)) {
            mkdir($output_file, 0777);
        }
        $output_file .= '\sentFiles';
        if (!file_exists($output_file)) {
            mkdir($output_file, 0777);
        }
        $data['webpath'] = 'user_' . $user_id . '/sentFiles/' . $time . '_' . $data['name'];
        $output_file .= '\\' . $time . '_' . $data['name'];


        if ($user_id != $data['to']) {  // User cannot send messages to self
            $all = $this->class_mate_list($user_id);
            if (in_array($data['to'], $all)) {
                $received_status = 0;
                foreach ($this->wsClients as $id => $client) {
                    if ($this->wsClients[$id][12] == $data['to']) {
                        $received_status = 1;
                        break;
                    }
                }

                $query = "INSERT INTO `ism`.`" . TBL_USER_CHAT . "` (`id`, `sender_id`, `receiver_id`, `message`, `media_link`,"
                        . " `media_type`, `received_status`, `created_date`, `is_delete`, `is_testdata`) "
                        . "VALUES (NULL, $user_id," . $data['to'] . ", NULL, '" . $data['webpath'] . "', '" . $data['data_type'] . "', $received_status, CURRENT_TIMESTAMP, '0', 'yes')";

                $x = mysqli_query($link, $query);
                $data['insert_id'] = mysqli_insert_id($link);

                file_put_contents($output_file, base64_decode($data['data']));
                if (!$x) {
                    $data['to'] = 'self';
                    $data['error'] = 'Unable to save message.! Please try again.';
                }
            } else {
                $data['to'] = 'self';
                $data['error'] = 'Please do not modify things manually!';
            }
        } else {
            $data['to'] = 'self';
            $data['error'] = 'You cannot send messages to self!';
        }

        if (isset($data['data'])) {
            unset($data['data']);
        }
        $check_type = array(
            'image/png',
            'image/jpg',
            'image/jpeg',
            'image/gif'
        );
        if (in_array($data['data_type'], $check_type)) {
            $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  target="_BLANK"><img src="uploads/' . $data['webpath'] . '" width="50" height="50"></a>';
        } else {
            $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  target="_BLANK"><img src="assets/images/default_chat.png" width="50" height="50"></a>';
        }
        $data['type'] = 'studymate';
        $data['from'] = $user_id;
        return $data;
    }

    /**
     * Save file send from Topic discussion
     * @param type $user_id
     * @param Array $data
     * @Author Sandip Gopani (SAG)
     */
    function save_sent_topic_file($userId, $data = null) {
        $data['active_count'] = $data['group_score'] = $data['my_score'] = 'skip';
        $time = time();
        $link = $this->db();
        $output_file = dirname(__DIR__) . "\\uploads\\user_" . $userId;
        if (!file_exists($output_file)) {
            mkdir($output_file, 0777);
        }
        $output_file .= '\sentFiles';
        if (!file_exists($output_file)) {
            mkdir($output_file, 0777);
        }
        $data['webpath'] = 'user_' . $userId . '/sentFiles/' . $time . '_' . $data['name'];
        $output_file .= '\\' . $time . '_' . $data['name'];

        if (is_array($data) && !empty($data)) {

            $data['time_to_left'] = $this->active_hours();
            $data['message'] = null;
            $link = $this->db();


            $day = getdate();
            $c_week = ceil($day['yday'] / 7);
            $current_date = $day['year'] . '-' . $day['mon'] . '-' . $day['mday'];

            $query = "SELECT `tm`.`id` as member_id,`tg`.`topic_id`, `tm`.`group_id` FROM  `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `tg` "
                    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `tg`.`group_id` "
                    . "WHERE `tm`.`user_id` = $userId AND `tg`.`week_no` = $c_week LIMIT 1";

            $row = mysqli_query($link, $query);
            if (mysqli_num_rows($row) == 1) {
                $rows = mysqli_fetch_assoc($row);

                $query = "INSERT INTO `ism`.`" . TBL_TUTORIAL_GROUP_DISCUSSION . "` "
                        . "(`id`, `group_id`, `topic_id`, `sender_id`, `comment_score`, `message`, `message_type`, "
                        . "`message_status`, `in_active_hours`, `media_link`, `media_type`, `created_date`, `modified_date`, "
                        . "`is_delete`, `is_testdata`) VALUES "
                        . "(NULL, '" . $rows['group_id'] . "', '" . $rows['topic_id'] . "', $userId, 0,'', '', '', 0, '" . $data['webpath'] . "', '" . $data['data_type'] . "', CURRENT_TIMESTAMP, '0000-00-00 00:00:00', '0', 'yes')";
                $x = mysqli_query($link, $query);

                $data['disscusion_id'] = mysqli_insert_id($link);
                file_put_contents($output_file, base64_decode($data['data']));
                unset($data['data']);
                $check_type = array(
                    'image/png',
                    'image/jpg',
                    'image/jpeg',
                    'image/gif'
                );
                if (in_array($data['data_type'], $check_type)) {
                    $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  target="_BLANK"><img src="uploads/' . $data['webpath'] . '" width="50" height="50"></a>';
                } else {
                    $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  target="_BLANK"><img src="assets/images/default_chat.png" width="50" height="50"></a>';
                }
            }
        }
        $data['type'] = 'discussion';
        $data['to'] = 'all';
        $data['allStudyMate'] = $this->class_mate_list($userId);
        return array_merge($data, $this->get_client_info($userId));
    }

    /**
     * Get lost of studymates for tag.
     * @param Array $data
     * @return string
     * @Author Kamlesh Pokiya(KAP), Sandip Gopani(SAG)
     */
    function get_studymate_name($data) {
        $link = $this->db();
        if (is_array($data['studymate_id'])) {
            $data['studymate_id'] = (string) implode(',', $data['studymate_id']);
        }
        $query = "SELECT * FROM users where id in (" . $data['studymate_id'] . ")";
        $row = mysqli_query($link, $query);
        if (mysqli_num_rows($row) > 0) {
            $all = array();
            $i = 0;
            while ($rows = mysqli_fetch_assoc($row)) {
                $all[$i]['name'] = $rows['full_name'];
                $all[$i]['id'] = $rows['id'];
                $i++;
            }
            $data['student_detail'] = $all;
        } else {
            $data['to'] = 'self';
            $data['error'] = 'Please don\'t modify data manually.';
        }
        return $data;
    }

}

?>
