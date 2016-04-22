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
	    $this->wsClientIPCount[$clientIP]++;
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
	    $this->wsClientIPCount[$clientIP]--;
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
	$this->wsClients[$clientID][13]++;
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

		    $query = "INSERT INTO `" . TBL_USER_CHAT . "` (`id`, `sender_id`, `receiver_id`, `message`, `media_link`, `media_type`, `received_status`, `created_date`, `is_delete`, `is_testdata`) "
			    . "VALUES (NULL, $from, $to, '$msg', NULL, NULL, $received_status, CURRENT_TIMESTAMP, '0', 'yes')";
		    $x = mysqli_query($link, $query);
		       $data['insert_id'] = mysqli_insert_id($link);

		    // Check wheather data saved in database or not.
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
	    $this->wsClients[$clientID][13]++;

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
	$query = "SELECT `mate_id`, `mate_of` "
		. "FROM `" . TBL_STUDYMATES . "` "
		. "WHERE ( `mate_id` = $user_id OR `mate_of` = $user_id ) AND is_delete = 0";
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
	$studymates = array();
	foreach ($all as $value) {
		if($value !== $user_id)
		{
			$studymates[] = $value;
		}
	}

	return $all;
    }


/**
     * Get array of all study mates in detail.
     * @param int $user_id
     * @return string
     * @author pankaj vasava
     */
    function class_mate_list_detail($user_id, $status = true) {

	$link = $this->db();
	// $query = "SELECT `mate_id`, `mate_of` "
	// 	. "FROM `" . TBL_STUDYMATES . "` "
	// 	. "WHERE ( `mate_id` = $user_id OR `mate_of` = $user_id ) AND is_delete = 0";
	$query = "SELECT `s`.`mate_id`, `s`.`mate_of`,`u`.`id` ,`u`.`full_name` "
		. "FROM `" . TBL_STUDYMATES . "` `s`, `" . TBL_USERS . "` `u`  "
		. "WHERE ( (`s`.`mate_id` = $user_id and `s`.`mate_of` = `u`.`id`) OR ( `s`.`mate_of` = $user_id and `s`.`mate_id` = `u`.`id`) ) AND `s`.is_delete = 0";

	$row = mysqli_query($link, $query);
	$all = array();
	$i = 0;
	while ($rows = mysqli_fetch_assoc($row)) {
	    if ($rows['mate_id'] !== $user_id) {
		$all[$i]['id'] = $rows['mate_id'];
		$all[$i]['full_name'] = $rows['full_name'];
	    }
	    if ($rows['mate_of'] !== $user_id) {
		$all[$i]['id'] = $rows['mate_of'];
		$all[$i]['full_name'] = $rows['full_name'];
	    }$i++;
	}

	if ($status) {
	    $all[] = $user_id;
	}
	if ($link != null) {
	    mysqli_close($link);
	}
	$studymates = array();
	foreach ($all as $value) {
		if($value !== $user_id)
		{
			$studymates[] = $value;
		}
	}
	return $all;
    }



    /**
     * Get list of group member.
     * @param type $userID
     * @param type $add_me
     */
    function get_group_member($userID, $add_me = true) {
	$link = $this->db();
	$query = "SELECT `t1`.`user_id` "
		. "FROM `" . TBL_TUTORIAL_GROUP_MEMBER . "` `t1` "
		. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `t2` ON `t1`.`group_id` = `t2`.`group_id` "
		. "WHERE `t2`.`user_id` = $userID";
	$row = mysqli_query($link, $query);
	$out = array();
	while ($rows = mysqli_fetch_assoc($row)) {
	    $out[] = $rows['user_id'];
	}

	if ($add_me == false) {
	    foreach ($out as $k => $v) {
		if ($v == $userID)
		    unset($out[$k]);
	    }
	}

	return $out;
    }

    /**
     * Get user info based on id passed.
     * @param int $id
     * @return array
     * @author Sandip Gopani (SAG)
     */
    function get_client_info($id) {
	$link = $this->db();
	$query = "SELECT `u`.`id`,`u`.`gender`,`u`.`full_name`, `upp`.`profile_link`, `u`.`created_date` "
		. "FROM `" . TBL_USERS . "` `u` "
		. "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `upp` ON `upp`.`user_id` = `u`.`id` "
		. "WHERE `u`.`id` = $id AND `u`.`is_delete` = 0 LIMIT 1";
	$row = mysqli_query($link, $query);
	$count = mysqli_num_rows($row);
	$rows = mysqli_fetch_assoc($row);
	mysqli_close($link);
	if ($count == 1) {
	    return array(
		'id' => $rows['id'],
		'gender' => $rows['gender'],
		'full_name' => $rows['full_name'],
		'profile_link' => $rows['profile_link'],
		'user_created_date' => $rows['created_date']
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

	// $query = "SELECT `uc`.`id`, `uc`.`sender_id`, `uc`.`receiver_id`, `uc`.`message`,`uc`.`media_link`,`uc`.`media_type`,`uc`.`created_date` "
	// 	. "FROM `" . TBL_USER_CHAT . "` `uc` "
	// 	. "WHERE (`uc`.`sender_id` = " . $data['my_id'] . " "
	// 	. "AND `uc`.`receiver_id` = $userID) OR (`uc`.`sender_id` = $userID AND `uc`.`receiver_id` = " . $data['my_id'] . ") "
	// 	. "AND `uc`.`is_delete` = 0 "
	// 	. "ORDER BY `uc`.`id` ASC LIMIT 10";

	 $query = "SELECT `temp`.`id`, `temp`.`sender_id`, `temp`.`receiver_id`, `temp`.`message`,`temp`.`media_link`,`temp`.`media_type`,`temp`.`created_date` FROM( "
	    . "SELECT `uc`.`id`, `uc`.`sender_id`, `uc`.`receiver_id`, `uc`.`message`,`uc`.`media_link`,`uc`.`media_type`,`uc`.`created_date` "
		. "FROM `" . TBL_USER_CHAT . "` `uc` "
		. "WHERE (`uc`.`sender_id` = " . $data['my_id'] . " "
		. "AND `uc`.`receiver_id` = $userID) OR (`uc`.`sender_id` = $userID AND `uc`.`receiver_id` = " . $data['my_id'] . ") "
		. "AND `uc`.`is_delete` = 0 "
		. "ORDER BY `uc`.`id` DESC LIMIT 10) temp"
		. " ORDER BY temp.`id` ASC";

	if(!empty($data['active_chat']))
	{
		 $query = "SELECT `temp`.`id`, `temp`.`sender_id`, `temp`.`receiver_id`, `temp`.`message`,`temp`.`media_link`,`temp`.`media_type`,`temp`.`created_date` FROM( "
	    . "SELECT `uc`.`id`, `uc`.`sender_id`, `uc`.`receiver_id`, `uc`.`message`,`uc`.`media_link`,`uc`.`media_type`,`uc`.`created_date` "
		. "FROM `" . TBL_USER_CHAT . "` `uc` "
		. "WHERE (`uc`.`sender_id` = " . $data['my_id'] . " "
		. "AND `uc`.`receiver_id` = $userID) OR (`uc`.`sender_id` = $userID AND `uc`.`receiver_id` = " . $data['my_id'] . ") "
		. "AND `uc`.`is_delete` = 0 "
		. "ORDER BY `uc`.`id` DESC LIMIT 0) temp"
		. " ORDER BY temp.`id` ASC";
	}


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
		'media_type' => $rows['media_type'],
		'cdate' => $this->get_time_format($rows['created_date'])
	    );
	}

	//  $result = array_reverse($result);
	$html = array();

	foreach ($result as $value) {
	    $in_h = array(
		'is_text' => 0,
		'a_link' => 'uploads/' . $value['media_link'],
		'img_link' => 'uploads/' . $value['media_link'],
		'text' => $value['message'],
		'to' => 1,
		'cdate' => $value['cdate']
	    );

	    if ($value['message'] == null) {
		if (!in_array($value['media_type'], $check_type))
		    $in_h['img_link'] = 'assets/images/default_chat.png';
	    }else {
		$in_h['is_text'] = 1;
	    }


	    if ($value['sender_id'] == $data['my_id']) {
		$in_h['to'] = 0;
	    }
	    $html[] = $in_h;
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
	$studymate_id = $this->class_mate_list($user_id);	
	$data['studymate_list'] = $studymate_id;
	if (is_array($data) && !empty($data)) {
	    $link = $this->db();
	    $msg = mysqli_escape_string($link, $data['message']); // Feed or comment
	    $query = "INSERT INTO `" . TBL_FEEDS . "`(`feed_by`, `feed_text`, `video_link`, `audio_link`, `posted_on`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
		    // . "VALUES ($user_id,'$msg','','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,NULL,0,'yes')";
		    . "VALUES ($user_id,'$msg','','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0,'yes')";
	    $x = mysqli_query($link, $query);
	    $data['post_id'] = mysqli_insert_id($link);
	    $data['tot_like'] = 0;
	    $data['tot_comment'] = 0;
	    $data['to'] = 'all';
	    if (!$x) {
		$data['error'] = 'Unable to save message.! Please try again.';
	    }
	    if ($data['post_id'] != '') {
		$data['tagged_id'] = (array) explode(',', $data['tagged_id']);
		if (is_array($data['tagged_id'])) {
		    $tagged_array = array();
		    $str = '';
		    $i = 0;

		    foreach ($data['tagged_id'] as $key => $value) {
			if (in_array($value, $studymate_id)) {
			    if ($i == 0)
				$str .= "(NULL," . $value . "," . $data['post_id'] . ",$user_id,CURRENT_TIMESTAMP,0,'yes')";
			    else
				$str .= ",(NULL," . $value . "," . $data['post_id'] . ",$user_id,CURRENT_TIMESTAMP,0,'yes')";
			    $tagged_array[] = $value;
			    $i++;
			}
		    }

		    if($str != "")
		    {
			    $query = "INSERT INTO `" . TBL_FEEDS_TAGGED_USER . "`(`id`, `user_id`, `feed_id`,`tagged_by`, `created_date`, `is_delete`, `is_testdata`)"."VALUES $str";
			      $data['str'] = $str; 
				  $data['qry'] = $query; 
			    $x = mysqli_query($link, $query);
			

			    if (is_array($tagged_array))
				$t = implode(',', $tagged_array);
			    else
				$t = '0';
			    $query = "SELECT `id`,`full_name` "
				    . "FROM `" . TBL_USERS . "` "
				    . "WHERE `id` in(" . $t . ") "
				    . "AND is_delete = 0";
			    $rows = mysqli_query($link, $query);
			    $tagged_detail = array();
			    $i = 0;

			    while ($row = mysqli_fetch_assoc($rows)) {
				$tagged_detail[$i]['full_name'] = $row['full_name'];
				$tagged_detail[$i]['id'] = $row['id'];
				$i++;
			    }

			    	/* Get notification on tag */
		
				$query = 'SELECT `u`.`id`,`u`.`full_name`,`p`.`profile_link` '
					. 'FROM `' . TBL_USERS . '` u, `'.TBL_USER_PROFILE_PICTURE.'` p '
					. 'WHERE `p`.`user_id` = `u`.`id` and `u`.`id` = ' .$data['user_iddd']. '';
				$rows = mysqli_query($link, $query);
				//$data['notification_detail'] = mysqli_num_rows($rows);

				$notification_for_tag = array();
				$i = 0;
				while ($row = mysqli_fetch_assoc($rows)) {
				    $notification_for_tag[$i]['full_name'] = $row['full_name'];
				    $notification_for_tag[$i]['id'] = $row['id'];
				     $notification_for_tag[$i]['profile_link'] = $row['profile_link'];
				    $notification_for_tag[$i]['created_date'] = $this->get_time_format(date("M d, Y, g:i:s a", strtotime($this->ctime())));
				    $i++;
				}


				 $data['notification_detail'] = $notification_for_tag;
				// select u.id,u.full_name,t.created_date
				// from users u,feeds_tagged_user t
				// where u.id = t.tagged_by and t.feed_id = '1089' and t.tagged_by = '138'; 

			}else
			{
				$tagged_detail="";
			}

		    $data['tagged_id'] = $tagged_array;
		    $data['tagged_detail'] = $tagged_detail;
		    $studymates = $this->class_mate_list($user_id);
		    if (is_array($studymates)) {
			$studymates = implode(',', $studymates);
			$query = 'SELECT u.id,u.full_name '
				. 'FROM  `' . TBL_USERS . '` u '
				. 'WHERE u.id in(' . $studymates . ') AND `u`.`is_delete` = 0';
			$rows = mysqli_query($link, $query);
			$i = 0;


			$studymates_detail = array();
			while ($row = mysqli_fetch_assoc($rows)) {
				if($row['id'] <> $user_id)
				{
				    $studymates_detail[$i]['full_name'] = $row['full_name'];
				    $studymates_detail[$i]['id'] = $row['id'];
				    $i++;
				}
			}
			$data['studymates_detail'] = $studymates_detail;
		    }
		    $data['posted_on'] = $this->get_time_format(date("M d, Y, g:i:s a", strtotime($this->ctime())));
		} else {
		    $data['error'] = 'Unable to save message.! Please try again.';
		}
	    }
	}
	//return $data;
	return array_merge($data, $this->get_client_info($user_id));
    }


    /**
     * Edit feed post 
     *
     * @param  - $user id as integer and $data as array
     * @return - array
     * @author - Pankaj(pv)
     */
    function classmate_edit_post($user_id, $data = null) {
    	if (is_array($data) && !empty($data)) {
	    $link = $this->db();
	    $msg = mysqli_escape_string($link, $data['message']); 
	    $query = "UPDATE " . TBL_FEEDS . " SET `feed_text` = '". $data['message'] ."' "
				. "WHERE `id` = " . $data['feed_id'] . " ";
	    $x = mysqli_query($link, $query);
	     // if (!$x) {
	     // 	echo "not inserted";
	     // }
		}
    }
    

    /**
     * Save feed comment
     * @param int $user_id
     * @param Array $data
     * @author Sandip Gopani (SAG)
     */
    function classmate_comment($user_id, $data) {
	$link = $this->db();
	$query = "SELECT `f`.`feed_by` "
		. "FROM `" . TBL_FEEDS . "` `f` "
		. "WHERE `f`.`id` = " . $data['to'] . "  "
		. "AND `f`.`is_delete` = 0 LIMIT 1";
	$row = mysqli_query($link, $query);

	// Check feed must exist on which comment is sent.
	if (mysqli_num_rows($row) == 1) {
	    $rows = mysqli_fetch_assoc($row);

	    $query = "SELECT * FROM " . TBL_FEEDS_TAGGED_USER . " WHERE user_id = " . $user_id . " AND feed_id =" . $data['to'];
	    $row = mysqli_query($link, $query);
	    $data['allStudyMate'] = $this->class_mate_list($rows['feed_by']);
	    if (mysqli_num_rows($row) > 0) {
		$data['allStudyMate'][] = $user_id;
	    }

	    // Check user must comment on those feed which is added by his/him classmates not to others.
	    if (in_array($user_id, $data['allStudyMate'])) {
		$query = "INSERT INTO `" . TBL_FEED_COMMENT . "` (`id`, `comment`, `comment_by`, `feed_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
			. "VALUES (NULL, '" . nl2br($data['message']) . "',$user_id, '" . $data['to'] . "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 'yes');";
		$x = mysqli_query($link, $query);
		$data['comment_date'] = 'Just Now';
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
			
		//$data['start'] += $limit;

		var_dump($data);
		if($data['start'] == "4")
		{
			$data['start'] = 4;
		}
			
		$query = "SELECT `f`.`id` as `post_id`, `f`.`feed_by`, `f`.`feed_text` as `message`, `f`.`created_date` as `posted_on`,"
			. " `u`.`full_name` , `l`.`is_delete` as my_like ,"
			// . " `u`.`full_name`,`u`.`id` , `l`.`is_delete` as my_like ,"
			. " (SELECT COUNT(*) FROM " . TBL_FEED_COMMENT . " WHERE feed_id = f.id AND `is_delete` = 0) AS tot_comment,"
			. " (SELECT COUNT(*) FROM " . TBL_FEED_LIKE . " WHERE feed_id = f.id AND `is_delete` = 0) AS tot_like,"
			. " `p`.`profile_link` FROM `" . TBL_FEEDS . "` `f` LEFT JOIN `" . TBL_USERS . "` `u` ON `u`.`id` = `f`.`feed_by`"
			. " LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `p` ON `u`.`id` = `p`.`user_id`"
			. " LEFT JOIN `feed_like` `l` ON `l`.`feed_id` = `f`.`id` AND `l`.`like_by` = $user_id "
			. " WHERE `f`.`is_delete` = 0 AND `f`.`feed_by` IN($ID_in) "
			. " ORDER BY `f`.`id` DESC LIMIT " . $data['start'] . "," . $limit;
		$row = mysqli_query($link, $query);
		$all = array();
		$feed_ids = array();
		while ($rows = mysqli_fetch_assoc($row)) {
			$rows['posted_on'] = $this->get_time_format(date("M d, Y, g:i:s a", strtotime($rows['posted_on'])));
		    $all[] = $rows;
		    $feed_ids[] = $rows['post_id'];
		}

		if (sizeof($feed_ids) > 0) {
		    $feed_ids = implode(',', $feed_ids);
		    $query = 'SELECT `u`.`id`, `u`.`full_name`, `t`.`feed_id` FROM `' . TBL_FEEDS_TAGGED_USER . '` `t` '
			    . 'LEFT JOIN `' . TBL_USERS . '` `u` ON `u`.`id` = `t`.`user_id` '
			    . 'WHERE `feed_id` IN(' . $feed_ids . ') AND `t`.`is_delete` = 0';
		    $tag_row = mysqli_query($link, $query);

		    /*$query = 'SELECT `feed_id` AS `to`,`comment` AS `message`, `u`.`full_name`, `p`.`profile_link`, fc.created_date'
			    . ' FROM `' . TBL_FEED_COMMENT . '` `fc` LEFT JOIN `' . TBL_USERS . '` `u` ON `u`.`id` = `fc`.`comment_by`'
			    . ' LEFT JOIN `' . TBL_USER_PROFILE_PICTURE . '` `p` ON `u`.`id` = `p`.`user_id`'
			    . ' WHERE `fc`.`is_delete` = 0 AND `feed_id` IN(' . $feed_ids . ')'; */

			  $query = 'SELECT `feed_id` AS `to`, `comment_by`,`comment` AS `message`, `u`.`full_name`, `p`.`profile_link`, `fc`.`created_date`'
			    . ' FROM `' . TBL_FEED_COMMENT . '` `fc` LEFT JOIN `' . TBL_USERS . '` `u` ON `u`.`id` = `fc`.`comment_by`'
			    . ' LEFT JOIN `' . TBL_USER_PROFILE_PICTURE . '` `p` ON `u`.`id` = `p`.`user_id`'
			    . ' WHERE `fc`.`is_delete` = 0 AND `feed_id` IN(' . $feed_ids . ')';
		    $comment_row = mysqli_query($link, $query);

		    $query = "SELECT `feed_id`, `image_link` "
			    . "FROM `" . TBL_FEED_IMAGE . "` "
			    . "WHERE `feed_id` IN(" . $feed_ids . ") "
			    . "AND `is_delete` = 0";
		    $img = mysqli_query($link, $query);
		    $feed_images = array();
		    $i = 0;
		    while ($img_list = mysqli_fetch_assoc($img)) {
			$feed_images[$i]['fid'] = $img_list['feed_id'];
			$feed_images[$i]['image_link'] = $img_list['image_link'];
			$i++;
		    }
		    $query = 'SELECT `u`.`id`,`u`.`full_name` '
			    . 'FROM  `' . TBL_USERS . '` `u` '
			    . 'WHERE `u`.`id` in(' . $ID_in . ') '
			    . 'AND `u`.`is_delete` = 0';
		    $rows = mysqli_query($link, $query);
		    $i = 0;
		    $studymates_detail = array();
		    while ($row = mysqli_fetch_assoc($rows)) {
		    	if($row['id'] <> $user_id)
			    {
					$studymates_detail[$i]['full_name'] = $row['full_name'];
					$studymates_detail[$i]['id'] = $row['id'];
					$i++;
				}
		    }

		    $final_feed = array();

		    $all_comment = $all_feed = array();
		    $i = 0;
		    while ($comment_rows = mysqli_fetch_assoc($comment_row)) {
			$all_comment[$i] = $comment_rows;
			$all_comment[$i]['comment_date'] = $this->get_time_format($comment_rows['created_date']);
			$i++;
		    }

		    while ($tagged_rows = mysqli_fetch_assoc($tag_row)) {
			$all_feed[] = $tagged_rows;
		    }

		    foreach ($all as $key => $value) {
			$final_feed[$key] = $value;
			$found_comment = $found_tagged = array();

			foreach ($all_comment as $k => $v) {
			    if ($v['to'] == $value['post_id']) {
				$found_comment[] = $v;
			    }
			}

			foreach ($all_feed as $k => $v) {
			    if ($v['feed_id'] == $value['post_id']) {
				$found_tagged[] = $v;
			    }
			}

			$final_feed[$key]['comment'] = $found_comment;
			$final_feed[$key]['tagged_detail'] = $found_tagged;
			
			$final_feed[$key]['studymates_detail'] = $studymates_detail;
		    }
		    $data['feed'] = $final_feed;
		  
		    foreach ($data['feed'] as $key => $value) {
			foreach ($feed_images as $k => $v) {

			    if ($v['fid'] == $value['post_id'] && $v['image_link'] != "") {
			    	 $ext = pathinfo($v['image_link'], PATHINFO_EXTENSION);
			    	if( $ext == 'jpeg' || $ext == 'gif' || $ext == 'png' || $ext == 'jpg' ) {
					    $data['feed'][$key]['feed_type'] = 'media';
						$data['feed'][$key]['message'] .= '<a href="uploads/' . $v['image_link'] . '"  class="fancybox"><img src="uploads/' . $v['image_link'] . '" width="100" height="70"></a>';
						unset($feed_images[$k]);
					}else
					{
						$data['feed'][$key]['feed_type'] = 'media';
						$data['feed'][$key]['message'] .= '<a href="uploads/' . $v['image_link'] . '" target="_BLANK"  class="fancybox"><img src="assets/images/default_chat.png" width="100" height="70"></a>';
						unset($feed_images[$k]);
					}


			    }


			}
		    }
		}
	    
		$data['start'] += $limit;
	    }
	}
	return $data;
    }

    /**
     * 
     * @param int $user_id
     * @param array $data
     * @return array
     */
    function post_like_unlike($user_id, $data) {
	$link = $this->db();
	$query = "SELECT `f`.`feed_by` "
		. "FROM `" . TBL_FEEDS . "` `f` "
		. "WHERE `f`.`id` = " . $data['fid'] . " "
		. "AND `f`.`is_delete` = 0  LIMIT 1";
	$row = mysqli_query($link, $query);

	// Check Feed must exist on which user is going to like.
	if (mysqli_num_rows($row) == 1) {
	    $rows = mysqli_fetch_assoc($row);

	    $query = "SELECT * FROM " . TBL_FEEDS_TAGGED_USER . " WHERE user_id = " . $user_id . " AND feed_id =" . $data['fid'];
	    $row = mysqli_query($link, $query);
	    $data['allStudyMate'] = $this->class_mate_list($rows['feed_by']);
	    if (mysqli_num_rows($row) > 0) {
		$data['allStudyMate'][] = $user_id;
	    }

	    // Check user can only like /dislike those post which is added by his classmates.
	    if (in_array($user_id, $data['allStudyMate'])) {
		$query = "SELECT * "
			. "FROM " . TBL_FEED_LIKE . " "
			. "WHERE feed_id =" . $data['fid'] . " "
			. "AND `like_by` = " . $user_id;
		$row = mysqli_query($link, $query);
		$row_cnt = $row->num_rows;

		// Check feed is already liked or not.
		if ($row_cnt > 0) {
		    $dt = mysqli_fetch_assoc($row);
		    if ($dt['is_delete'] == 0) {
			$query = "UPDATE " . TBL_FEED_LIKE . " SET `is_delete` = 1 "
				. "WHERE `feed_id` = " . $data['fid'] . " "
				. "AND `like_by` = " . $user_id;
			$data['message'] = 'unlike';
		    } else {
			$query = "UPDATE " . TBL_FEED_LIKE . " SET `is_delete` = 0 "
				. "WHERE `feed_id` = " . $data['fid'] . " AND `like_by` = " . $user_id;
			$data['message'] = 'like';
		    }
		} else {
		    // $query = "INSERT INTO `" . TBL_FEED_LIKE . "`(`id`, `like_by`, `feed_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
			   //  . "VALUES (NULL," . $user_id . "," . $data['fid'] . ",CURRENT_TIMESTAMP,NULL,0,'yes')";
		     $query = "INSERT INTO `" . TBL_FEED_LIKE . "`(`id`, `like_by`, `feed_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
			    . "VALUES (NULL," . $user_id . "," . $data['fid'] . ",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0,'yes')";
		    $data['message'] = 'like';
		}

		mysqli_query($link, $query);
		$query = 'SELECT COUNT(*) AS `cnt` '
			. 'FROM ' . TBL_FEED_LIKE . ' '
			. 'WHERE `is_delete` = 0  '
			. 'AND `feed_id` = ' . $data['fid'];
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
     * @author Sandip Gopani (SAG)
     */
    function discussion($userId, $data = null) {
	$data['active_count'] = $data['group_score'] = $data['my_score'] = 'skip';
	$data['message'] = preg_replace('!\s+!', ' ', $data['message']);
	$link = $this->db();
	$words = explode(' ', $data['message']);

	if (is_array($data) && !empty($data)) {

	    // Get  score related configuration from admin_config table. And stored into $config variable.
	    $query = "SELECT `ac`.`config_key`,`ac`.`config_value` "
		    . "FROM `" . TBL_ADMIN_CONFIG . "` `ac` "
		    . "WHERE `ac`.`config_key` "
		    . "IN('activeHrFirstCommentScore','activeHoursFirstCommentCount','nonActivehoursScore','spamWordDeduction','activeHoursCommentScore','groupScoreFromIndividual')";
	    $row = mysqli_query($link, $query);
	    $config = array();
	    while ($rows = mysqli_fetch_assoc($row)) {
		$config[$rows['config_key']] = $rows['config_value'];
	    }

	    $date = new DateTime($this->ctime());
	    $c_week = $date->format("W");
	    $year = $date->format("Y");
	    $current_date = $date->format("Y-m-d");

	    $query = "SELECT `tm`.`id` as member_id,`tg`.`topic_id`, `tm`.`group_id` "
		    . "FROM  `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `tg` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `tg`.`group_id` "
		    . "WHERE `tm`.`user_id` = $userId "
		    . "AND `tg`.`week_no` = $c_week "
		    . "AND YEAR(`tg`.`created_date`) = '$year' "
		    . "AND `tg`.`is_delete` = 0 LIMIT 1";
	    $row = mysqli_query($link, $query);

	    $score = 0;

	    // Check topic is must allocated to group with perticular week. 
	    if (mysqli_num_rows($row) == 1) {
		$rows = mysqli_fetch_assoc($row);
		$is_active = 0;
		$in_active = 0;
		$score = $config['nonActivehoursScore'];
		$query = "SELECT `td`.`comment_score`,`td`.`sender_id` FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` "
			. "WHERE `td`.`group_id` = " . $rows['group_id'] . " "
			. "AND `td`.`topic_id` = " . $rows['topic_id'] . " "
			. "AND `td`.`is_delete` = 0 "
			. "AND date(`td`.`created_date`) = '$current_date' "
			. "ORDER BY `td`.`id` DESC LIMIT 0,3";
		$row = mysqli_query($link, $query);
		$consecutvie = 0;
		$allow_score = true;
		$i = 1;

		// Check for consecutive post.
		while ($rowq = mysqli_fetch_assoc($row)) {
		    if ($rowq['sender_id'] == $userId) {
			if ($rowq['comment_score'] == 0) {
			    $consecutvie++;
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
		    if ($data['time_to_left'] > 0) {
		    	$in_active = 1;
		    }
		// Allowed only if post is not consecutive.
		if ($allow_score == true) {
		    // Check comment is in active hours.
		    if ($data['time_to_left'] > 0) {
			$is_active = 1;

			$query = "SELECT COUNT(`id`) AS `total_count` "
				. "FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` "
				. "WHERE date(`created_date`) = '$current_date' "
				. "AND `td`.`in_active_hours` = 1 ";
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
		$query = "SELECT * FROM `" . TBL_WORD_WATCH . "` `ww` "
			. "WHERE `ww`.`is_delete` = 0";
		$row = mysqli_query($link, $query);
		while ($rowq = mysqli_fetch_assoc($row)) {
		    // Check wherer spam word exist.
		    if (in_array($rowq['word'], $words)) {
			$score -= $config['spamWordDeduction'];
			$data['error'] = 'Spam words found. <b>' . $config['spamWordDeduction'] . ' points</b> diducted from your score!';
			break;
		    }
		}

		$query = "INSERT INTO `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` "
			. "(`id`, `group_id`, `topic_id`, `sender_id`, `comment_score`, `message`, `message_type`, `message_status`, `in_active_hours`, `is_active`, `media_link`, `media_type`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
			. "VALUES (NULL, '" . $rows['group_id'] . "', '" . $rows['topic_id'] . "', $userId, $score,'" . $data['message'] . "', '', '', $is_active, $in_active, '', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 'yes')";
		$x = mysqli_query($link, $query);
		$data['disscusion_id'] = mysqli_insert_id($link);
		$data['in_active'] = $in_active;
		// Update group score and student score.
		if ($x) {
		    $add_to_group = 0;
		    if ($score > 0) {
			// Get persentage of score to add in group score.
			$add_to_group = ceil(($score * $config['groupScoreFromIndividual']) / 100);
			$query = "UPDATE `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` "
				. "SET `group_score` = `group_score` + $add_to_group "
				. "WHERE `group_id` = " . $rows['group_id'] . " "
				. "AND `topic_id` = " . $rows['topic_id'] . " "
				. "AND `week_no` = " . $c_week . " "
				. "AND YEAR(`created_date`) = '$year'";
			mysqli_query($link, $query);
		    }

		  

		    // Update user score.
		    $query = "UPDATE `" . TBL_TUTORIAL_GROUP_MEMBER_SCORE . "` "
			    . "SET `score` = `score` + $score "
			    . "WHERE `topic_id` = " . $rows['topic_id'] . " "
			    . "AND member_id =" . $rows['member_id'];
		    mysqli_query($link, $query);
		} else {
		    $data['to'] = 'self';
		    $data['error'] = 'Unable to save your message! Try again!';
		}

		// Select latest user score and group score.
		// $query = "SELECT if(`ts`.`score`>0, `ts`.`score` ,0) AS `my_score`,(SELECT SUM(group_score)  "
		// 	. "FROM " . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . " "
		// 	. "WHERE group_id = " . $rows['group_id'] . ") AS group_score,(SELECT COUNT(`td`.`id`) "
		// 	. "FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` "
		// 	. "WHERE `td`.`group_id` = " . $rows['group_id'] . " "
		// 	. "AND `td`.`topic_id` = " . $rows['topic_id'] . " "
		// 	. "AND in_active_hours = 1) AS active_count  "
		// 	. "FROM `" . TBL_TUTORIAL_TOPIC . "` `t` "
		// 	. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` "
		// 	. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` "
		// 	. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER_SCORE . "` `ts` ON `ts`.`member_id` = `tm`.`id` and `ts`.`topic_id` = `ta`.`topic_id` "
		// 	. "LEFT JOIN `" . TBL_USERS . "` `u` ON `u`.`id` = `t`.`created_by` "
		// 	. "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `up` ON `up`.`user_id` = `u`.`id` "
		// 	. "WHERE `ta`.`group_id` = '" . $rows['group_id'] . "' "
		// 	. "AND `ta`.`week_no` = $c_week AND YEAR(`ta`.`created_date`) = '$year'  AND  `tm`.`user_id` = '$userId' AND `t`.`is_delete` = 0";
		$discussion_topic_id = 36;
		$query = "SELECT (SELECT SUM(score)"
			." FROM ". TBL_TUTORIAL_GROUP_MEMBER_SCORE . " WHERE member_id = `tm`.`id`) AS `my_score`,(SELECT SUM(group_score) "
			. "FROM " . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . " "
			. "WHERE group_id = " . $rows['group_id'] . ") AS group_score,(SELECT COUNT(`td`.`id`) "
			. "FROM `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` `td` "
			. "WHERE `td`.`group_id` = " . $rows['group_id'] . " "
			. "AND in_active_hours = 1 and `td`.`topic_id` = " . $discussion_topic_id .") AS active_count  "
			. "FROM `" . TBL_TUTORIAL_TOPIC . "` `t` "
			. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` "
			. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` "
			. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER_SCORE . "` `ts` ON `ts`.`member_id` = `tm`.`id` and `ts`.`topic_id` = `ta`.`topic_id` "
			. "LEFT JOIN `" . TBL_USERS . "` `u` ON `u`.`id` = `t`.`created_by` "
			. "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `up` ON `up`.`user_id` = `u`.`id` "
			. "WHERE `ta`.`group_id` = '" . $rows['group_id'] . "' "
			. "AND `ta`.`week_no` = $c_week AND YEAR(`ta`.`created_date`) = '$year'  AND  `tm`.`user_id` = '$userId' AND `t`.`is_delete` = 0";
		
		$row = mysqli_query($link, $query);
		$rows = mysqli_fetch_assoc($row);
		foreach ($rows as $k => $v) {
		    $data[$k] = $v;
		}	
	    } else {
		$data['error'] = 'No topic allocated! or Discussion time is over!';
	    }
	}
	$data['cdate'] = $this->get_time_format($this->ctime());
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
    function active_hours() {
	$link = $this->db();
	$starttime = $endtime = null;
	$output = array(
	    'time_to_start' => 0,
	    'total_active_time' => 0,
	    'total_deactive_time' => 0,
	    'time_to_left' => 0,
	    'system_time' => 0,
	    'exam_time_to_start' => 0,
	    'exam_total_active_time' => 0,
	    'exam_total_deactive_time' => 0,
	    'exam_time_to_left' => 0
	);
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
	$output['system_time'] = $c_full_time = $currenttime['hours'] . ':' . $currenttime['minutes'] . ':' . $currenttime['seconds'];
	$query = "SELECT `ac`.`config_value`, `ac`.`config_key` "
		. "FROM `" . TBL_ADMIN_CONFIG . "` `ac` "
		. "WHERE `ac`.`config_key` IN ('activeHoursStartTime','activeHoursEndTime','questionHrExamStartTime','questionHrExamEndTime')";
	$row = mysqli_query($link, $query);
	if (mysqli_num_rows($row) == 4) {
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
		} else if ($rows['config_key'] == 'activeHoursEndTime') {
		    $endtime = implode(':', $time_part);
		}

		if ($rows['config_key'] == 'questionHrExamStartTime') {
		    $examStart = implode(':', $time_part);
		} else if ($rows['config_key'] == 'questionHrExamEndTime') {
		    $examEnd = implode(':', $time_part);
		}
	    }
	    $cur = DateTime::createFromFormat('H:i:s', $c_full_time);
	    if ($starttime !== null && $endtime !== null) {
		// Convert to date time
		$start = DateTime::createFromFormat('H:i:s', $starttime);
		$end = DateTime::createFromFormat('H:i:s', $endtime);


		if ($cur < $start) {
		    $output['time_to_start'] = $this->dateDiff($c_full_time, $starttime);
		} else if ($cur > $end) {
		    $output['time_to_start'] = $this->dateDiff('00:00:00', $starttime) + $this->dateDiff($c_full_time, '24:00:00');
		}

		$output['total_active_time'] = $this->dateDiff($starttime, $endtime);
		$output['total_deactive_time'] = $this->dateDiff('00:00:00', $starttime) + $this->dateDiff($endtime, '24:00:00');


		// Check current time is between $starttime and $endtime
		if ($cur > $start && $cur < $end) {
		    $output['time_to_left'] = $this->dateDiff($endtime, $c_full_time);
		}
		// }
	    }

	    if ($examStart != null && $examEnd != null) {

		$Estart = DateTime::createFromFormat('H:i:s', $examStart);
		$Eend = DateTime::createFromFormat('H:i:s', $examEnd);

		if ($cur < $Estart) {
		    $output['exam_st'] = 'pending';
		    $output['exam_time_to_start'] = $this->dateDiff($c_full_time, $examStart);
		} else if ($cur > $Eend) {
		    $output['exam_st'] = 'finished';
		    $output['exam_time_to_start'] = $this->dateDiff('00:00:00', $examStart) + $this->dateDiff($c_full_time, '24:00:00');
		}

		$output['exam_total_active_time'] = $this->dateDiff($examStart, $examEnd);
		$output['exam_total_deactive_time'] = $this->dateDiff('00:00:00', $examStart) + $this->dateDiff($examEnd, '24:00:00');

		// Check current time is between $starttime and $endtime
		if ($cur > $Estart && $cur < $Eend) {
		    $output['exam_st'] = 'started';
		    $output['exam_time_to_left'] = $this->dateDiff($examEnd, $c_full_time);
		}
	    }
	}
	return $output;
    }

    /**
     * get information of word.
     * @param type $data
     * @return type
     * @author Kamlesh Pokiya (KAP), Sandip Gopani (SAG)
     */
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

    /**
     * Remove user from studymates
     * @param type $userid
     * @param type $data
     * @return array
     * @author kamlesh Pokiya (KAP), Sandip Gopani (SAG)
     */
    function close_studymate($userid, $data) {
	$link = $this->db();
	$query = "UPDATE " . TBL_STUDYMATES . " SET `is_delete` = 1 "
		. "WHERE (`mate_id` = " . $userid . " "
		. "AND `mate_of`=" . $data['studymate_id'] . ") "
		. "OR (`mate_of` = " . $userid . " "
		. "AND `mate_id` = " . $data['studymate_id'] . ")";
	$x = mysqli_query($link, $query);
	if (!$x) {
	    $data['to'] = 'self';
	    $data['error'] = 'Unable to Identify studymate. Please don\'t modify data manually.';
	}
	$data['result'] = 'Done';
	return $data;
    }

    /**
     * Send studymate request.
     * @param type $userid
     * @param type $data
     * @return string
     * @author Kamlesh Pokiya (KAP), Sandip Gopani(SAG)
     */
    function send_studymate_request($userid, $data) {

	$link = $this->db();
	$studymate = $this->class_mate_list($userid, false);
	// if (sizeof($studymate) > 0) {

	$query = "SELECT id "
		. "FROM " . TBL_STUDYMATES_REQUEST . " "
		. "WHERE `request_from_mate_id` = " . $userid . " "
		. "AND `request_to_mate_id` = " . $data['studymate_id'] . " "
		. "AND `status` IN (0,2) "
		. "AND `is_delete` = 0";
	$rows = mysqli_query($link, $query);
	$row_cnt = mysqli_num_rows($rows);
	// Studymates request is send or not.
	if ($row_cnt > 0) {
	    $data['to'] = 'self';
	    $data['error'] = 'Syudymates request already sent!. You can\'t send again';
	} else {
	    if (sizeof($studymate) > 0)
		$where = "`in1`.`user_id` NOT IN(" . implode(',', $studymate) . ")";
	    else
		$where = ' 1=1';
	    $query = "SELECT `group_id` "
		    . "FROM " . TBL_TUTORIAL_GROUP_MEMBER . " "
		    . "WHERE `user_id` = " . $userid . " "
		    . "AND `is_delete` = 0";
	    $rows = mysqli_query($link, $query);
	    $row = mysqli_fetch_assoc($rows);
	    $group_id = $row['group_id'];

	    $query = "INSERT INTO `" . TBL_STUDYMATES_REQUEST . "`"
		    . "(`id`, `request_from_mate_id`, `request_to_mate_id`, `status`, `created_date`, `is_delete`, `is_testdata`) "
		    . "VALUES (NULL,$userid," . $data['studymate_id'] . ",0,CURRENT_TIMESTAMP,0,'yes')";
	    $x = mysqli_query($link, $query);
	    if (!$x) {
		$data['to'] = 'self';
		$data['error'] = 'Unable to save message.! Please try again.';
	    } else {
		$query = "SELECT count(*) AS `cnt` "
			. "FROM " . TBL_STUDYMATES_REQUEST . " "
			. "WHERE `request_from_mate_id` = " . $data['studymate_id'] . " AND `status` = 0";

		$rows = mysqli_query($link, $query);
		$row = mysqli_fetch_assoc($rows);
		$data['count'] = $row['cnt'];
	    }
	}
	$data['user_id'] = $userid;
	return $data;
    }

    /**
     * 
     * @param type $user_id
     * @param type $data
     * @return array
     * @author Kamlesh Pokiya (KAP), Sandip Gopani (SAG)
     */
    function view_all_comment_activities($user_id, $data) {
	$link = $this->db();
	$query = "SELECT *,`u`.`full_name`,`com`.`created_date` AS `comment_date` "
		. "FROM " . TBL_FEED_COMMENT . " `com` "
		. "LEFT JOIN " . TBL_USER_PROFILE_PICTURE . " `p` ON `com`.`comment_by` = `p`.`user_id` "
		. "LEFT JOIN " . TBL_USERS . " `u` on `u`.`id` = `com`.`comment_by` "
		. "WHERE `comment_by` = " . $user_id . ' AND `com`.`feed_id` = ' . $data['comment_id'] . " AND `com`.`is_delete` = 0 ";
	$row = mysqli_query($link, $query);
	if (mysqli_num_rows($row) > 0) {
	    $all = array();
	    $i = 0;
	    while ($rows = mysqli_fetch_assoc($row)) {
		$all[$i]['comment'] = $rows['comment'];
		$all[$i]['date'] = $this->get_time_format($rows['comment_date']);
		$link = $rows['profile_link'];
		$name = $rows['full_name'];
		$i++;
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

    /**
     * Set message status as unread.
     * @param array $data
     * @author Sandip Gopani (SAG)
     */
    function set_unread($data = null) {
	if (is_array($data) && $data != null) {
	    $link = $this->db();
	    $query = "UPDATE `" . TBL_USER_CHAT . "` "
		    . "SET `received_status` = 0 "
		    . "WHERE `id` = " . $data['insert_id'];
	    mysqli_query($link, $query);
	}
    }

    /**
     * Accept or decline studymates request.
     * @param int $user_id
     * @param array $data
     * @return array
     */
    function accept_decline_request($user_id, $data) {
	$link = $this->db();
	$data['is_online'] = $this->online_status($data['studymate_id']);
	$query = 'SELECT * FROM ' . TBL_STUDYMATES_REQUEST . ' '
		. 'WHERE `request_to_mate_id` = ' . $user_id . ' '
		. 'AND `request_from_mate_id` = ' . $data['studymate_id'] . ' '
		. 'AND `status` IN (0,2)';
	$row = mysqli_query($link, $query);
	if (mysqli_num_rows($row) > 0) {
	    if ($data['sub_type'] == 'accept-request') {
		$query = 'INSERT INTO `' . TBL_STUDYMATES . '`'
			. '(`id`, `mate_id`, `mate_of`, `is_online`, `created_date`, `is_delete`, `is_testdata`) '
			. 'VALUES (NULL,' . $user_id . ',' . $data['studymate_id'] . ',0,CURRENT_TIMESTAMP,0,"yes")';
		$x = mysqli_query($link, $query);
		if (!$x) {
		    $data['to'] = 'self';
		    $data['error'] = 'Unable to save message.! Please try again.';
		}
		$query = 'UPDATE ' . TBL_STUDYMATES_REQUEST . ' '
			. 'SET `status` = 1, `is_delete` = 1 ,`modified_date` = NOW()'
			. 'WHERE `request_from_mate_id` = ' . $data['studymate_id'] . ' '
			. 'AND `request_to_mate_id` = ' . $user_id;
		$x = mysqli_query($link, $query);
		if (!$x) {
		    $data['to'] = 'self';
		    $data['error'] = 'Unable to save message.! Please try again.';
		}
		$data['message'] = 'accepted';
	    }

	    if ($data['sub_type'] == 'decline-request') {
		$query = 'UPDATE ' . TBL_STUDYMATES_REQUEST . ' '
			. 'SET `is_delete` = 1 '
			. 'WHERE `request_from_mate_id` = ' . $data['studymate_id'] . ' '
			. 'AND `request_to_mate_id` = ' . $user_id;
		$x = mysqli_query($link, $query);
		if (!$x) {
		    $data['to'] = 'self';
		    $data['error'] = 'Unable to save message.! Please try again.';
		}

		$data['message'] = 'decline';
	    }

	    $query = 'SELECT `u`.`full_name`,`s`.`school_name`,`c`.`course_name`,`p`.`profile_link` '
		    . 'FROM ' . TBL_USERS . ' `u` '
		    . 'LEFT JOIN ' . TBL_STUDENT_ACADEMIC_INFO . ' `info` ON `info`.`user_id` = `u`.`id` '
		    . 'LEFT JOIN ' . TBL_SCHOOLS . ' `s` ON `s`.`id` = `info`.`school_id` '
		    . 'LEFT JOIN ' . TBL_COURSES . ' `c` ON `c`.`id` = `info`.`course_id` '
		    . 'LEFT JOIN ' . TBL_USER_PROFILE_PICTURE . ' `p` ON `p`.`user_id` = `u`.`id` '
		    . 'WHERE `u`.`id` = ' . $data['studymate_id'] . " AND `u`.`is_delete` = 0";
	    $row = mysqli_query($link, $query);
	    $rows = mysqli_fetch_assoc($row);
	    $data['full_name'] = $rows['full_name'];
	    $data['school_name'] = $rows['school_name'];
	    $data['course_name'] = $rows['course_name'];
	    $data['profile'] = $rows['profile_link'];

	    $data['user_data'] = $this->get_client_info($user_id);
	} else {
	    $data['to'] = 'self';
	    $data['error'] = 'Please don\'t modify data manually.';
	}
	return $data;
    }

    /**
     * Check weather user is online or not.
     * @param int $user_id
     * @return boolean
     * @author Sandip Gopani (SAG)
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
     * @param int $user_id
     * @param array $data
     * @Author Sandip Gopani (SAG)
     */
    function save_sent_file($user_id, $data = null) {

	$time = time();
	$link = $this->db();
	$output_file = dirname(__DIR__) . "/uploads/user_" . $user_id;
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}
	$output_file .= '/sentFiles';
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}
	$data['name'] = preg_replace("/[^a-zA-Z.]+/", "", $data['name']);
	$data['webpath'] = 'user_' . $user_id . '/sentFiles/' . $time . '_' . $data['name'];
	$output_file .= '/' . $time . '_' . $data['name'];

	// User cannot send messages to self
	if ($user_id != $data['to']) {
	    $all = $this->class_mate_list($user_id);
	    if (in_array($data['to'], $all)) {
		$received_status = 0;
		foreach ($this->wsClients as $id => $client) {
		    if ($this->wsClients[$id][12] == $data['to']) {
			$received_status = 1;
			break;
		    }
		}

		$query = "INSERT INTO `" . TBL_USER_CHAT . "` (`id`, `sender_id`, `receiver_id`, `message`, `media_link`,"
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

	// If sent file is image than image is displayed otherwise default image is displayed for other file.
	if (in_array($data['data_type'], $check_type)) {
	    $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  class="fancybox"><img src="uploads/' . $data['webpath'] . '" width="50" height="50"></a>';
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
	$output_file = dirname(__DIR__) . "/uploads/user_" . $userId;
	// Check directory is exist. If not exist than created.
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}
	$output_file .= '/sentFiles';
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}
	$data['name'] = preg_replace("/[^a-zA-Z.]+/", "", $data['name']);
	$data['webpath'] = 'user_' . $userId . '/sentFiles/' . $time . '_' . $data['name'];
	$output_file .= '/' . $time . '_' . $data['name'];

	if (is_array($data) && !empty($data)) {


	    $data['message'] = null;
	    $link = $this->db();


	    $date = new DateTime($this->ctime());
	    $c_week = $date->format("W");
	    $year = $date->format("Y");

	    $query = "SELECT `tm`.`id` as member_id,`tg`.`topic_id`, `tm`.`group_id` "
		    . "FROM  `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `tg` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `tg`.`group_id` "
		    . "WHERE `tm`.`user_id` = $userId "
		    . "AND `tg`.`week_no` = $c_week "
		    . "AND YEAR(`tg`.`created_date`) = '$year' AND `tg`.`is_delete` = 0 LIMIT 1";


	    $row = mysqli_query($link, $query);

	    // Check topic must exist for send file.
	    if (mysqli_num_rows($row) == 1) {
		$rows = mysqli_fetch_assoc($row);

		$query = "INSERT INTO `" . TBL_TUTORIAL_GROUP_DISCUSSION . "` "
			. "(`id`, `group_id`, `topic_id`, `sender_id`, `comment_score`, `message`, `message_type`, "
			. "`message_status`, `in_active_hours`, `media_link`, `media_type`, `created_date`, `modified_date`, "
			. "`is_delete`, `is_testdata`) VALUES "
			. "(NULL, '" . $rows['group_id'] . "', '" . $rows['topic_id'] . "', $userId, 0,'', '', '', 0, '" . $data['webpath'] . "', '" . $data['data_type'] . "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 'yes')";
		$x = mysqli_query($link, $query);

		$data['disscusion_id'] = mysqli_insert_id($link);

		//save sent file.
		file_put_contents($output_file, base64_decode($data['data']));

		// Unset file data.
		unset($data['data']);

		$check_type = array(
		    'image/png',
		    'image/jpg',
		    'image/jpeg',
		    'image/gif'
		);

		// If sent file is image than image is displayed otherwise default image is displayed for other file.
		if (in_array($data['data_type'], $check_type)) {
		    $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  class="fancybox"><img src="uploads/' . $data['webpath'] . '" width="50" height="50"></a>';
		} else {
		    $data['message'] = '<a href="uploads/' . $data['webpath'] . '"  target="_BLANK"><img src="assets/images/default_chat.png" width="50" height="50"></a>';
		}
	    }
	}
	$data['type'] = 'discussion';
	$data['to'] = 'all';

	$data['cdate'] = $this->get_time_format(date("M d, Y, g:i:s a", strtotime($this->ctime()))); 
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

    /**
     * Check user is allowed to give exam or not. 
     * @param type $userID
     * @param type $data
     * @return string
     */
    function exam_request($userID, $data = null) {
	if (is_array($data) && $data != null) {
	    $link = $this->db();
	    $data['exam_status'] = 0;    // 0 = Not statred, 1 = started, 2 = finished
	    $date = new DateTime($this->ctime());
	    $c_week = $date->format("W");
	    $year = $date->format("Y");
	    $query = "SELECT `ta`.`topic_id`, `t`.`topic_name`, `t`.`topic_description`, `ta`.`created_date`, `te`.`exam_id`,`ss`.`created_date` "
		    . "FROM `" . TBL_TUTORIAL_TOPIC . "` `t` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_TOPIC_EXAM . "` `te` ON `te`.`tutorial_topic_id` = `ta`.`topic_id` "
		    . "LEFT JOIN `" . TBL_STUDENT_EXAM_SCORE . "` `ss` ON `ss`.`exam_id` = `te`.`exam_id` AND `ss`.`user_id` = $userID "
		    . "WHERE `ta`.`week_no` = '$c_week' AND `tm`.`user_id` = '$userID' AND YEAR(`ta`.`created_date`) = '$year' "
		    . "AND `t`.`is_delete` = 0 LIMIT 1";
	    $row = mysqli_query($link, $query);
	    if (mysqli_num_rows($row) == 1) {
		$data['exam'] = mysqli_fetch_assoc($row);

		$query = "INSERT INTO " . TBL_STUDENT_EXAM_SCORE . " (user_id, exam_id)"
			. " SELECT * "
			. "FROM (SELECT '" . $userID . "', '" . $data['exam']['exam_id'] . "') AS tmp "
			. " WHERE NOT EXISTS ( SELECT user_id,exam_id "
			. "FROM " . TBL_STUDENT_EXAM_SCORE . " "
			. "WHERE user_id = " . $userID . " "
			. "AND exam_id = " . $data['exam']['exam_id'] . ") LIMIT 1";

		mysqli_query($link, $query);
		$current_date = DateTime::createFromFormat('Y-m-d', date('Y-m-d'));
		if (isset($data['exam']) && !empty($data['exam'])) {
		    if (isset($data['exam']['created_date'])) {
			if ($data['exam']['created_date'] != '' && $data['exam']['created_date'] != null) {
			    $exam_start_date = DateTime::createFromFormat('Y-m-d', date('Y-m-d', strtotime($data['exam']['created_date'])));
			    if ($exam_start_date < $current_date) {
				$data['exam_status'] = 2;
			    } else if ($exam_start_date == $current_date) {
				if ($data['exam_st'] == 'finished') {
				    $data['exam_status'] = 2;
				} else if ($data['exam_st'] == 'started') {
				    $data['exam_status'] = 1;
				}
			    }
			}
		    }
		} else {
		    $data['error'] = 'Exam or Topic is not allocated for current week!';
		}
	    } else {
		
	    }
	}
	return $data;
    }

    /**
     * Save answer sent by user and give next question.
     * @param int $userID
     * @param array $data
     * @author Sandip Gopani(SAG)
     */
    function save_answer($userID, $data) {
	$left = false;
	$link = $this->db();
	$date = new DateTime($this->ctime());
	$c_week = $date->format("W");
	$year = $date->format("Y");

	$query = "SELECT `ta`.`topic_id`, `t`.`topic_name`, `t`.`topic_description`, `ta`.`created_date`, `te`.`exam_id`,`ss`.`created_date` "
		. "FROM `" . TBL_TUTORIAL_TOPIC . "` `t` "
		. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` "
		. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` "
		. "LEFT JOIN `" . TBL_TUTORIAL_TOPIC_EXAM . "` `te` ON `te`.`tutorial_topic_id` = `ta`.`topic_id` "
		. "LEFT JOIN `" . TBL_STUDENT_EXAM_SCORE . "` `ss` ON `ss`.`exam_id` = `te`.`exam_id` "
		. "AND `ss`.`user_id` = $userID "
		. "WHERE `ta`.`week_no` = '$c_week' "
		. "AND `tm`.`user_id` = '$userID' "
		. "AND YEAR(`ta`.`created_date`) = '$year' AND `t`.`is_delete` = 0 LIMIT 1";

	if ($data['exam_type'] == 'no') {
	    $query = " SELECT sc.exam_id,"
		    . "IF(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) as remaining_time,sc.created_date,sc.created_date + Interval e.duration minute,e.duration,NOW() "
		    . "FROM `" . TBL_STUDENT_EXAM_SCORE . "` `sc` "
		    . "LEFT JOIN `" . TBL_EXAMS . "` `e` ON `e`.`id` = `sc`.`exam_id` "
		    . "WHERE `sc`.`user_id` = $userID "
		    . "AND `sc`.`exam_status` = 'started' "
		    . "ORDER BY `sc`.`id` DESC LIMIT 1 ";
	}
	$row = mysqli_query($link, $query);
	$data['exam'] = mysqli_fetch_assoc($row);

	if ($data['exam_type'] == 'no') {
	    if ($data['exam']['remaining_time'] <= 0) {
		$data['exam_st'] = 'finished';
		$data['redirect'] = '/student/my_scoreboard/index/' . $data['exam_id'];
	    } else {
		$data['exam_st'] = 'started';
	    }
	}
	if ($data['exam_st'] == 'started') {
	    if ($data['status'] == 'next') {
		if ($data['answer'] == 0) {
		    $data['status'] = 'S';
		} else {
		    $data['status'] = 'A';
		}
	    }

	    if (mysqli_num_rows($row) == 1) {

		/* Check question exist in question id. */
		$query = "SELECT if(COUNT(`id`) > 0,1,0) as total FROM `" . TBL_EXAM_QUESTION . "` "
			. "WHERE `exam_id` = " . $data['exam']['exam_id'] . " "
			. "AND `question_id` = " . $data['question_id'] . " AND `is_delete` = 0";

		$row = mysqli_query($link, $query);
		if (mysqli_num_rows($row) == 0) {
		    $data['error'] = 'Please don\'t modify data manually!';
		    $data['reload'] = 'yes';
		    $left = true;
		}

		if ($left == false) {
		    $query = "SELECT id FROM `" . TBL_STUDENT_EXAM_RESPONSE . "` `sr` "
			    . "WHERE `sr`.`user_id` = $userID "
			    . "AND `sr`.`exam_id` = " . $data['exam']['exam_id'] . " "
			    . "AND `sr`.`question_id` = " . $data['question_id'] . " "
			    . "AND `sr`.`is_delete` = 0";
		    $row = mysqli_query($link, $query);
		    $query = "SELECT `is_right` "
			    . "FROM `" . TBL_ANSWER_CHOICES . "`  "
			    . "WHERE `id` = " . $data['answer'] . " "
			    . "AND `is_delete` = 0";
		    $roq = mysqli_query($link, $query);
		    $roqs = mysqli_fetch_assoc($roq);
		    if ($roqs['is_right'] != 1) {
			$roqs['is_right'] = 0;
		    }
		    /* Check answer id is within choices id or Not */
		    $query = "SELECT `id` FROM `" . TBL_ANSWER_CHOICES . "` "
			    . "WHERE `question_id` = " . $data['question_id'] . " "
			    . "AND `is_delete` = 0";
		    $rowz = mysqli_query($link, $query);
		    $all_choices = array();
		    while ($rowsz = mysqli_fetch_assoc($rowz)) {
			$all_choices[] = $rowsz['id'];
		    }

		    if (in_array($data['answer'], $all_choices) || $data['answer'] == 0) {
			if (mysqli_num_rows($row) == 0) {
			    $query = "INSERT INTO `" . TBL_STUDENT_EXAM_RESPONSE . "` "
				    . "(`id`, `user_id`, `exam_id`, `question_id`, `choice_id`, `answer_status`, `answer_text`, `is_right`, `response_duration`, `created_date`, `modified_date`, `is_delete`, `is_testdata`)"
				    . " VALUES (NULL, '$userID', " . $data['exam']['exam_id'] . ", '" . $data['question_id'] . "', '" . $data['answer'] . "', '" . $data['status'] . "', NULL, " . $roqs['is_right'] . ", " . $data['time'] . ", CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 'yes') ";
			} else {
			    $query = "UPDATE `" . TBL_STUDENT_EXAM_RESPONSE . "` `sr` "
				    . "SET `choice_id` = " . $data['answer'] . ", `answer_status` = '" . $data['status'] . "', `response_duration` = " . $data['time'] . ",`is_right` = " . $roqs['is_right'] . " "
				    . "WHERE  `sr`.`user_id` = $userID "
				    . "AND `sr`.`exam_id` = " . $data['exam']['exam_id'] . " "
				    . "AND `sr`.`question_id` = " . $data['question_id'] . " ";
			}
			$x = mysqli_query($link, $query);
		    } else {
			$data['error'] = "Please don't modify choices manually!!";
			$data['reload'] = 'yes';
		    }
		}

		if (isset($data['status'])) {
		    if ($data['status'] == 'A') {
			$data['status'] = 'answered';
		    } else if ($data['status'] == 'R') {
			$data['status'] = 'review_later';
		    } else if ($data['status'] == 'S') {
			$data['status'] = 'skipped';
		    } else {
			$data['status'] = '';
		    }
		}
	    } else {
		
	    }
	} else {
	    $data['error'] = 'You are not allowed to give answer. Exam is either finished or not started!!';
	}
	if ($data['next'] !== 0) {
	    $data['question_no'] = $data['next'];
	    $data = array_merge($data, $this->get_question($userID, $data, true));
	}

	/* Update total_score. */
	if ($left == false) {
	    $query = "SELECT COUNT(*) AS total,"
		    . " SUM(CASE WHEN `is_right` = 0 THEN 1 ELSE 0 end) `incorrect`,"
		    . " SUM(CASE WHEN `is_right` = 1 THEN 1 ELSE 0 end) `correct`,"
		    . " SUM(`response_duration`) `time_spent` "
		    . "FROM `" . TBL_STUDENT_EXAM_RESPONSE . "` "
		    . "WHERE `exam_id` = " . $data['exam']['exam_id'] . "  "
		    . "AND `user_id` = " . $userID . " "
		    . "AND `answer_status` = 'A' ";

	    $row = mysqli_query($link, $query);
	    $rows = mysqli_fetch_assoc($row);

	    $query = 'UPDATE `student_exam_score` '
		    . 'SET `attempt_count` = (SELECT COUNT(id) FROM `' . TBL_STUDENT_EXAM_RESPONSE . '` '
		    . 'WHERE `answer_status` = "A"  AND `exam_id` = ' . $data['exam']['exam_id'] . '  '
		    . 'AND `user_id` = ' . $userID . '), `correct_answers` = ' . $rows['correct'] . ' , `incorrect_answers`=  ' . $rows['incorrect'] . ', `total_time_spent` = ' . $rows['time_spent'] . ' '
		    . 'WHERE `user_id` = ' . $userID . ' '
		    . 'AND `exam_id` = ' . $data['exam']['exam_id'] . ' '
		    . 'AND `is_delete` = 0';
	    mysqli_query($link, $query);
	}
	return $data;
    }

    function get_question($userID, $data = null, $only_question = false) {
	if (is_array($data) && $data != null) {
	    $link = $this->db();
	    $data['new_question'] = 'skip';
	    $date = new DateTime($this->ctime());
	    $c_week = $date->format("W");
	    $year = $date->format("Y");

	    $result = array(
		'question' => null,
		'qid' => null,
		'answer' => null,
		'choice_id' => 'skip'
	    );

	    if ($data['exam_type'] == 'no') {
		$query = " SELECT sc.exam_id,"
			. "IF(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) as remaining_time,sc.created_date,sc.created_date + Interval e.duration minute,e.duration,NOW() "
			. "FROM `" . TBL_STUDENT_EXAM_SCORE . "` `sc` "
			. "LEFT JOIN `" . TBL_EXAMS . "` `e` ON `e`.`id` = `sc`.`exam_id` "
			. "WHERE `sc`.`user_id` = $userID "
			. "AND `sc`.`exam_status` = 'started' "
			. "ORDER BY `sc`.`id` DESC LIMIT 1 ";
	    } else {
		$query = "SELECT `ta`.`topic_id`, `t`.`topic_name`, `t`.`topic_description`, `ta`.`created_date`, `te`.`exam_id`,`ss`.`created_date` "
			. "FROM `" . TBL_TUTORIAL_TOPIC . "` `t` "
			. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` "
			. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` "
			. "LEFT JOIN `" . TBL_TUTORIAL_TOPIC_EXAM . "` `te` ON `te`.`tutorial_topic_id` = `ta`.`topic_id` "
			. "LEFT JOIN `" . TBL_STUDENT_EXAM_SCORE . "` `ss` ON `ss`.`exam_id` = `te`.`exam_id` AND `ss`.`user_id` = $userID "
			. "WHERE `ta`.`week_no` = '$c_week' AND `tm`.`user_id` = '$userID' "
			. "AND YEAR(`ta`.`created_date`) = '$year' AND `t`.`is_delete` = 0 LIMIT 1";
	    }


	    $row = mysqli_query($link, $query);
	    $data['exam'] = mysqli_fetch_assoc($row);
	    if (mysqli_num_rows($row) == 1) {


		$query = "SELECT `ser`.`choice_id` ,`q`.`id` as `qid`, `q`.`question_text`, `ac`.`id`, `ac`.`choice_text` "
			. " FROM `" . TBL_ANSWER_CHOICES . "` `ac` "
			. " LEFT JOIN `" . TBL_STUDENT_EXAM_RESPONSE . "` `ser` ON `ser`.`question_id` = `ac`.`question_id` "
			. "AND `ser`.`exam_id` = " . $data['exam']['exam_id'] . " AND `ser`.`user_id` = $userID "
			. " JOIN `" . TBL_QUESTIONS . "` `q` ON `ac`.`question_id` = `q`.`id` "
			. " WHERE `ac`.`question_id` = " . $data['question_no'] . " "
			. "AND `ac`.`is_delete` = 0 ORDER BY RAND()";
		$row = mysqli_query($link, $query);


		while ($rows = mysqli_fetch_assoc($row)) {
		    $result['question'] = htmlentities($rows['question_text']);
		    $result['qid'] = $rows['qid'];
		    if ($rows['choice_id'] != '' || $rows['choice_id'] != null) {

			$result['choice_id'] = $rows['choice_id'];
		    }
		    $result['answer'][] = array(
			'id' => $rows['id'],
			'choice' => htmlentities($rows['choice_text'])
		    );
		}
	    } else {
		$data['error'] = 'Not found proper data!';
	    }
	}

	$data['new_question'] = $result;
	if ($only_question == true) {
	    return $data['new_question'];
	} else {
	    return $data;
	}
    }

    function check_exam($userid, $data) {
	$query = "SELECT * "
		. "FROM `" . TBL_STUDENT_ACADEMIC_INFO . "` `si` "
		. "LEFT JOIN `" . TBL_EXAMS . "` `e` ON `e`.`classroom_id` = `si`.`classroom_id` "
		. "WHERE `e`.`id` = " . $data['exam_id'] . " "
		. "AND `si`.`user_id` =" . $userid . " AND `e`.`exam_type` != 'tutorial'";
	$link = $this->db();

	$rows = mysqli_query($link, $query);
	if (mysqli_num_rows($rows) > 0) {
	    $row = mysqli_fetch_assoc($rows);
	    $data['class_exam_status'] = 'started';
	    $query = "INSERT INTO `" . TBL_STUDENT_EXAM_SCORE . "`( `user_id`, `exam_id`, `attempt_count`, `correct_answers`, `incorrect_answers`, `total_time_spent`, `evaluation_privacy`, `exam_status`, `created_date`, `is_delete`, `is_testdata`) "
		    . "VALUES (" . $userid . "," . $data['exam_id'] . ",0,0,0,0,0,'started',CURRENT_TIMESTAMP,0,'yes')";
	    $x = mysqli_query($link, $query);
	    if (!$x) {
		$data['to'] = 'self';
		$data['error'] = 'Unable to save message.! Please try again.';
	    }
	} else {
	    $data['to'] = 'self';
	    $data['error'] = 'Please don\'t modify data manually.';
	}
	return $data;
    }

    function end_exam($userID, $data = null) {

	if ($data != null && is_array($data)) {
	    $link = $this->db();
	    $data['new_question'] = 'skip';
	    $date = new DateTime($this->ctime());
	    $c_week = $date->format("W");
	    $year = $date->format("Y");

	    $query = "SELECT `ta`.`topic_id`, `t`.`topic_name`, `t`.`topic_description`, `ta`.`created_date`, `te`.`exam_id`,`ss`.`created_date` "
		    . "FROM `" . TBL_TUTORIAL_TOPIC . "` `t` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ta` ON `ta`.`topic_id` = `t`.`id` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER . "` `tm` ON `tm`.`group_id` = `ta`.`group_id` "
		    . "LEFT JOIN `" . TBL_TUTORIAL_TOPIC_EXAM . "` `te` ON `te`.`tutorial_topic_id` = `ta`.`topic_id` "
		    . "LEFT JOIN `" . TBL_STUDENT_EXAM_SCORE . "` `ss` ON `ss`.`exam_id` = `te`.`exam_id` AND `ss`.`user_id` = $userID "
		    . "WHERE `ta`.`week_no` = '$c_week' "
		    . "AND `tm`.`user_id` = '$userID' AND YEAR(`ta`.`created_date`) = '$year' "
		    . "AND `t`.`is_delete` = 0 LIMIT 1";

	    if ($data['exam_type'] == 'no') {
		$query = " SELECT sc.exam_id,"
			. "IF(TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute) < 0,0,TIMESTAMPDIFF(SECOND,NOW(),sc.created_date + Interval e.duration minute)) as remaining_time,sc.created_date,sc.created_date + Interval e.duration minute,e.duration,NOW() "
			. "FROM `" . TBL_STUDENT_EXAM_SCORE . "` `sc` "
			. "LEFT JOIN `" . TBL_EXAMS . "` `e` ON `e`.`id` = `sc`.`exam_id` "
			. "WHERE `sc`.`user_id` = $userID "
			. "AND `sc`.`exam_status` = 'started' AND `sc`.`is_delete` = 0"
			. " ORDER BY sc.id DESC LIMIT 1 ";
	    }
	    $row = mysqli_query($link, $query);
	    if (mysqli_num_rows($row) == 1) {
		$exam = mysqli_fetch_assoc($row);
		$data['redirect'] = '/student/my_scoreboard/index/' . $exam['exam_id'];
		$query = "UPDATE `" . TBL_STUDENT_EXAM_SCORE . "` `te` "
			. "SET `te`.`exam_status` = 'finished', `te`.`exam_endtime` = CURRENT_TIMESTAMP "
			. "WHERE `te`.`user_id` = $userID "
			. "AND `te`.`exam_id` = " . $exam['exam_id'];
		mysqli_query($link, $query);
	    }
	}
	return $data;
    }

    function tag_again($user_id, $data) {
	$link = $this->db();
	$studymate_id = $this->class_mate_list($user_id);
	$data['studymate_list'] = $studymate_id;
	$query = "select * from feeds where id=" . $data['fid'];
	$rows = mysqli_query($link, $query);
	if (mysqli_num_rows($rows) > 0) {
	    $row = mysqli_fetch_assoc($rows);
	    if (in_array($row['feed_by'], $studymate_id)) {
		$tagged_array = array();
		$already_tagged_array = array();
		$str = '';
		$i = 0;
		foreach ($data['tagged_id'] as $key => $value) {
		    // $query = "SELECT * "
			   //  . "FROM `" . TBL_FEEDS_TAGGED_USER . "` "
			   //  . "WHERE `tagged_by` = " . $user_id . " "
			   //  . "AND `user_id` = " . $value . " AND `feed_id` = " . $data['fid'] . " AND `is_delete` = 0";

		    $query = "SELECT * "
			    . "FROM `" . TBL_FEEDS_TAGGED_USER . "` "
			    . "WHERE  `user_id` = " . $value . " AND `feed_id` = " . $data['fid'] . " AND `is_delete` = 0";
		    $rows = mysqli_query($link, $query);

		    if (mysqli_num_rows($rows) > 0) {
			$already_tagged_array[] = $value;
		    } else {
			if ($i == 0) {
			    $str .= '(' . $value . ',' . $data["fid"] . ',' . $user_id . ',CURRENT_TIMESTAMP,0,0,"yes")';
			} else {
			    $str .= ',(' . $value . ',' . $data["fid"] . ',' . $user_id . ',CURRENT_TIMESTAMP,0,0,"yes")';
			}
			$i++;
			$tagged_array[] = $value;
		    }
		}

		if ($str != '') {

		    $query = "INSERT INTO `" . TBL_FEEDS_TAGGED_USER . "`(`user_id`, `feed_id`, `tagged_by`, `created_date`, `is_delete`, `is_seen`, `is_testdata`) "
			    . "VALUES $str";
		    $x = mysqli_query($link, $query);
		}
		if (is_array($tagged_array) && sizeof($tagged_array) > 0) {
		    $t = implode(',', $tagged_array);
		}
		else
		    $t = '0';

		if (is_array($already_tagged_array) && sizeof($already_tagged_array) > 0)
		    $t2 = implode(',', $already_tagged_array);
		else
		    $t2 = '0';

		$query = "SELECT `id`, `full_name` "
			. "FROM `" . TBL_USERS . "`  "
			. "WHERE `id` IN(" . $t . ")";
		$rows = mysqli_query($link, $query);
		$tagged_detail = array();
		$i = 0;

		while ($row = mysqli_fetch_assoc($rows)) {
		    $tagged_detail[$i]['full_name'] = $row['full_name'];
		    $tagged_detail[$i]['id'] = $row['id'];
		    $i++;
		}

		$data['tagged_id'] = $tagged_array;
		$data['tagged_detail'] = $tagged_detail;

		$query = "SELECT `id`, `full_name` "
			. "FROM `" . TBL_USERS . "`  "
			. "WHERE `id` IN(" . $t2 . ")";
		$rows = mysqli_query($link, $query);
		$already_tagged_detail = array();
		$i = 0;

		while ($row = mysqli_fetch_assoc($rows)) {
		    $already_tagged_detail[$i]['full_name'] = $row['full_name'];
		    $already_tagged_detail[$i]['id'] = $row['id'];
		    $i++;
		}

		/* Get notification on tag */
		
		$query = 'SELECT `u`.`id`,`u`.`full_name`,`p`.`profile_link` '
			. 'FROM `' . TBL_USERS . '` u, `'.TBL_USER_PROFILE_PICTURE.'` p '
			. 'WHERE `p`.`user_id` = `u`.`id` and `u`.`id` = ' .$data['user_iddd']. '';
		$rows = mysqli_query($link, $query);
		//$data['notification_detail'] = mysqli_num_rows($rows);

		$notification_for_tag = array();
		$i = 0;
		while ($row = mysqli_fetch_assoc($rows)) {
		    $notification_for_tag[$i]['full_name'] = $row['full_name'];
		    $notification_for_tag[$i]['id'] = $row['id'];
		     $notification_for_tag[$i]['profile_link'] = $row['profile_link'];
		    $notification_for_tag[$i]['created_date'] = $this->get_time_format(date("M d, Y, g:i:s a", strtotime($this->ctime())));
		    $i++;
		}


		 $data['notification_detail'] = $notification_for_tag;
		// select u.id,u.full_name,t.created_date
		// from users u,feeds_tagged_user t
		// where u.id = t.tagged_by and t.feed_id = '1089' and t.tagged_by = '138'; 


		$data['already_tagged_id'] = $already_tagged_array;
		$data['already_tagged_detail'] = $already_tagged_detail;

		$query = 'SELECT `u`.`id`,`u`.`full_name` '
			. 'FROM `' . TBL_FEEDS_TAGGED_USER . '` f  '
			. 'LEFT JOIN `' . TBL_USERS . '` `u` ON `u`.`id` = `f`.`user_id` '
			. 'WHERE `f`.`feed_id` = ' . $data['fid'] . " "
			. "AND `f`.`is_delete` = 0";
		$rows = mysqli_query($link, $query);
		$available_user = array();
		$i = 0;

		while ($row = mysqli_fetch_assoc($rows)) {
		    $available_user[$i]['full_name'] = $row['full_name'];
		    $available_user[$i]['id'] = $row['id'];
		    $i++;
		}

		$data['already_available_tagged_detail'] = $available_user;
	    } else {

		$data['to'] = 'self';
		$data['error'] = 'Please don\'t modify data manually.';
	    }
	} else {
	    $data['to'] = 'self';
	    $data['error'] = 'Please don\'t modify data manually.';
	}

	return $data;

	//array_merge($data, $this->get_client_info($user_id));
    }

    /**
     * Save file sent from feed.
     * @param type $userID
     * @param type $data
     */
    function save_feed_file($user_id, $data) {
	$data['type'] = 'post';
	$data['to'] = 'all';
	$time = time();
	$link = $this->db();
	$output_file = dirname(__DIR__) . "/uploads/user_" . $user_id;
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}

	$check_type = array(
	    'image/png',
	    'image/jpg',
	    'image/jpeg',
	    'image/gif'
	);

	$output_file .= '/sentFiles';
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}

	$data['name'] = preg_replace("/[^a-zA-Z.]+/", "", $data['name']);

	$data['webpath'] = 'user_' . $user_id . '/sentFiles/' . $time . '_' . $data['name'];

	$output_file .= '/' . $time . '_' . $data['name'];
	$data['output_file'] = $output_file;

	$studymate_id = $this->class_mate_list($user_id);
	$studymates = implode(',', $studymate_id);
	$query = "SELECT `id`, `full_name` "
		. "FROM `" . TBL_USERS . "`  "
		. "WHERE `id` IN(" . $studymates . ") AND `is_delete` = 0";
	$rows = mysqli_query($link, $query);
	$i = 0;
	$studymates_detail = array();
	while ($row = mysqli_fetch_assoc($rows)) {
	    $studymates_detail[$i]['full_name'] = $row['full_name'];
	    $studymates_detail[$i]['id'] = $row['id'];
	    $i++;
	}
	$data['studymates_detail'] = $studymates_detail;
	$data['studymate_list'] = $studymate_id;

	if (is_array($data) && !empty($data)) {
	//    if (in_array($data['data_type'], $check_type)) {
		$query = "INSERT INTO `" . TBL_FEEDS . "`(`id`, `feed_by`, `feed_text`, `video_link`, `audio_link`, `posted_on`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
			. "VALUES (NULL,$user_id,'','','',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0,'yes')";
		$x = mysqli_query($link, $query);
		$data['post_id'] = mysqli_insert_id($link);
		$data['tot_like'] = 0;
		$data['tot_comment'] = 0;

		file_put_contents($output_file, base64_decode($data['data']));

		if ($x) {
		    $query = "INSERT INTO `" . TBL_FEED_IMAGE . "` (`id`, `feed_id`, `image_link`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) "
			    . "VALUES(NULL,  " . $data['post_id'] . ", '" . $data['webpath'] . "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 'yes')";
		    $y = mysqli_query($link, $query);
		    if ($y) {
		    if (in_array($data['data_type'], $check_type)) {	
			$data['message'] = '<a href="uploads/' . $data['webpath'] . '"  class="fancybox"><img src="uploads/' . $data['webpath'] . '" width="100" height="70"></a>';
			$data['feed_type'] = 'media'; 
			}else{
				$data['message'] = '<a href="uploads/' . $data['webpath'] . '" target="_BLANK"><img src="assets/images/default_chat.png" width="100" height="70"></a>';
				$data['feed_type'] = 'doc'; 
			}
		    } else {
			$data['to'] = 'self';
			$data['error'] = 'Unable to save image data! Please try again!';
		    }
		} else {
		    $data['to'] = 'self';
		    $data['error'] = 'Unable to save feed data! Please try again!';
		}


		if (isset($data['data'])) {
		    unset($data['data']);
		}
	    /*} else {
		$data['to'] = 'self';
		$data['error'] = 'Please select only Image!';
	    }*/
	}

	$data['posted_on'] = $this->get_time_format(date("M d, Y, g:i:s a", strtotime($this->ctime())));
	$data['tagged_id'] = $data['tagged_detail'] = array();
	//return $data;
	return array_merge($data, $this->get_client_info($user_id));
    }

    /**
     * Save file sent from feed comment.
     * @param type $userID
     * @param type $data
     */
    function save_feed_comment_file($userID, $data) {

	$data['type'] = 'feed_comment';
	$data['to'] = 'all';
	$time = time();
	$link = $this->db();
	$output_file = dirname(__DIR__) . "/uploads/user_" . $user_id;
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}

	$check_type = array(
	    'image/png',
	    'image/jpg',
	    'image/jpeg',
	    'image/gif'
	);

	$output_file .= '/sentFiles';
	if (!file_exists($output_file)) {
	    mkdir($output_file, 0777);
	}
	$data['name'] = preg_replace("/[^a-zA-Z.]+/", "", $data['name']);
	$data['webpath'] = 'user_' . $user_id . '/sentFiles/' . $time . '_' . $data['name'];
	$output_file .= '/' . $time . '_' . $data['name'];


	$query = "SELECT `f`.`feed_by` FROM `" . TBL_FEEDS . "` `f` WHERE `f`.`id` = " . $data['to'] . " AND `f`.`is_delete` = 0 LIMIT 1";
	$row = mysqli_query($link, $query);

	// Check feed must exist on which comment is sent.
	if (mysqli_num_rows($row) == 1) {
	    $rows = mysqli_fetch_assoc($row);
	    $data['allStudyMate'] = $this->class_mate_list($rows['feed_by']);

	    // Check user must comment on those feed which is added by his/him classmates not to others.
	    if (in_array($user_id, $data['allStudyMate'])) {
		$query = "INSERT INTO `" . TBL_FEED_COMMENT . "` (`id`, `comment`, `comment_by`, `feed_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES (NULL, '" . $data['message'] . "',$user_id, '" . $data['to'] . "', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 'yes');";
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
     * Return time based on creted date and current date.
     * @param date $t
     * @author Sandip Gopani (SAG)
     */
    function get_time_format($t) {
	$link = $this->db();
	$timeFirst = strtotime($t);
	$query = "SELECT NOW() AS ctime";
	$rows = mysqli_query($link, $query);
	$row = mysqli_fetch_array($rows);

	$timeSecond = strtotime($row['ctime']);
	$output = null;
	$diff = $timeSecond - $timeFirst;
	 if($diff < 5){
    $output = 'Just Now';
  }else if($diff < 60){
    $output = $diff. ' sec ago';
  } else if ($diff < 3600) {
	    $output = floor($diff / 60) . ' min ago';
	} else if ($diff < 86400) {
	    $output = floor($diff / 3600);
	    if ($output < 2) {
		$output .= ' hour ago';
	    } else {
		$output .= ' hours ago';
	    }
	} else if ($diff < 86400 * 2) {
	    $output = 'Yesterday, '.date('H:i a',strtotime($t)); 
	} else {
	    $output = date_format(date_create($t), 'M d, Y, g:i a');
	}
	return $output;
    }

    /**
     * studymate search.
     * @author KAMLESH POKIYA (KAP)
     */
    function studymate_search($userid, $data) {
	$link = $this->db();
	if ($data['search_type'] == 'people')
	    $where = "`u`.`full_name` LIKE '%" . $data['search_txt'] . "%'";
	elseif ($data['search_type'] == 'school')
	    $where = "`s`.`school_name` LIKE '" . $data['search_txt'] . "%'";
	elseif ($data['search_type'] == 'course')
	    $where = "`c`.`course_name` LIKE '" . $data['search_txt'] . "%'";
	else
	    $where = "1=1";

	if (isset($data['data_start'])) {
	    $limit = 4;
	    $d = $data['data_start'];
	    $data['data_start'] += $limit;
	} else {
	    $limit = 4;
	    $data['data_start'] = 0;
	    $d = 0;
	}
	$ID_in = implode(',', $this->class_mate_list($userid));
	$query = "SELECT `u`.`id` as `user_id`, `u`.`full_name`, `s`.`school_name`, `c`.`course_name`, `p`.`profile_link`, `sr`.`id` as `srid`, `sr`.`is_delete` "
		. "FROM `" . TBL_USERS . "` `u` "
		. "JOIN `" . TBL_STUDENT_ACADEMIC_INFO . "` `in` ON `in`.`user_id` = `u`.`id` "
		. "LEFT JOIN `" . TBL_SCHOOLS . "` `s` ON `s`.`id` = `in`.`school_id` "
		. "LEFT JOIN `" . TBL_COURSES . "` `c` ON `c`.`id` = `in`.`course_id` "
		. "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `p` ON `u`.`id` = `p`.`user_id` "
		. "LEFT JOIN `" . TBL_STUDYMATES_REQUEST . "` `sr` ON `sr`.`request_from_mate_id`=$userid "
		. "AND `sr`.`request_to_mate_id` = `u`.`id` AND `sr`.`is_delete` = 0 "
		. "WHERE `u`.`is_delete` = 0 AND `u`.`id` NOT IN($ID_in) AND $where LIMIT " . $data['data_start'] . ',' . $limit;
	$rows = mysqli_query($link, $query);
	$result = array();
	while ($row = mysqli_fetch_assoc($rows)) {
	    $result[] = $row;
	}
	
	$data['result'] = $result;
	$data['limit'] = $d + 4;
	return $data;
    }

    /**
     *   Load more activity.
     *   @author KAMLESH POKIYA (KAP)
     */
    function load_activity($user_id, $data) {
	$link = $this->db();
	$user_info = $this->get_client_info($user_id);
	$created_date = $user_info['user_created_date'];
	$begin = new DateTime($created_date);
	$end = new DateTime($this->ctime());
	$date_array = array();
	while ($begin <= $end) {
	    $date_array[] = $begin->format('Y-m');
	    $begin->modify('first day of next month');
	}
	$month = array();

	/* ----find current month and if request to view more append one month in descending form--- */
	$month[] = date('m', strtotime($this->ctime()));
	$m = date('m', strtotime($data['month']));

	$load_more = $data['month'];
	if ($load_more != '')
	    $month[] = date('m', strtotime($load_more));

	if (is_array($month))
	    $sep_month = implode(',', $month);

	$data['result'] = array();
	$data['result']['my_topic'] = array();
	$data['result']['my_studymate'] = array();
	$data['result']['my_like'] = array();
	$data['result']['my_comment'] = array();
	$data['result']['my_post'] = array();

	// topic allocation

	$query = "SELECT `group_id` FROM `" . TBL_TUTORIAL_GROUP_MEMBER . "` WHERE `user_id` = $user_id";
	$row = mysqli_query($link, $query);
	$rows = mysqli_fetch_array($row);
	$group_id = $rows['group_id'];
	if ($group_id == '')
	    $group_id = 0;
	$query = "SELECT `t`.`topic_name`, `ga`.`created_date`, IF(`topic_count`.`cnt` IS NULL,0,`topic_count`.`cnt`) AS `total_discussion`, IF(`s`.`score` IS NULL,0,`s`.`score`) AS `discussion_score`, IF(TRUNCATE((st_s.correct_answers * 100)/eq.total_question, 2) IS NULL,0.00,TRUNCATE((st_s.correct_answers * 100)/eq.total_question, 2)) AS per "
		. "FROM `" . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . "` `ga` "
		. "LEFT JOIN `" . TBL_TOPICS . "` `t` ON `t`.`id` = `ga`.`topic_id` "
		. "LEFT JOIN (SELECT COUNT(*) AS cnt,topic_id FROM " . TBL_TUTORIAL_GROUP_DISCUSSION . " td WHERE is_delete = 0 group by topic_id) topic_count ON `topic_count`.`topic_id` = `t`.`id` "
		. "LEFT JOIN `" . TBL_TUTORIAL_GROUP_MEMBER_SCORE . "` `s` ON `t`.`id` = `s`.`topic_id` AND `s`.`member_id` =$user_id  AND date_format(s.created_date,'%m') IN($m)"
		. "LEFT JOIN `" . TBL_TUTORIAL_TOPIC_EXAM . "` `e` ON `e`.`tutorial_topic_id` = `t`.`id` "
		. "LEFT JOIN (SELECT COUNT(*) AS total_question,exam_id FROM exam_question group by exam_id) eq ON `eq`.`exam_id` = `e`.`exam_id` "
		. "LEFT JOIN `" . TBL_STUDENT_EXAM_SCORE . "` `st_s` ON `st_s`.`exam_id` = `e`.`exam_id` AND `st_s`.`user_id` =$user_id AND date_format(st_s.created_date,'%m') IN($m)"
		. "WHERE `ga`.`group_id` = '$group_id' AND `ga`.`is_delete` = '0' "
		. "AND date_format(ga.created_date,'%m') IN($m)";
	$row = mysqli_query($link, $query);
	$i = 0;
	while ($rows = mysqli_fetch_assoc($row)) {
	    $data['result']['my_topic'][$i] = $rows;
	    $i++;
	}

	for ($i=0; $i <count($data['result']['my_topic']) ; $i++) { 
		$data['result']['my_topic'][$i]['created_date'] = $this->get_time_format($data['result']['my_topic'][$i]['created_date']);
	}


	//  became studymate with
	//$query = "SELECT `u`.`full_name`, `sm`.`mate_of`, `sm2`.`mate_id`, DATE_FORMAT(sm.created_date,'%b %d %Y') as created_date, `s`.`school_name`, `p`.`profile_link`, `c`.`course_name` "
	$query = "SELECT `u`.`full_name`,`u`.`id`, `sm`.`mate_of`, `sm2`.`mate_id`, DATE_FORMAT(sm.created_date,'%b %d %Y') as created_date, `s`.`school_name`, `p`.`profile_link`, `c`.`course_name` "
		. "FROM `" . TBL_USERS . "` `u` LEFT JOIN `studymates` `sm` ON `u`.`id` = `sm`.`mate_of` and `sm`.`is_delete` = 0 and `sm`.`mate_id` =$user_id "
		. "LEFT JOIN `" . TBL_STUDYMATES . "` `sm2` ON `u`.`id` = `sm2`.`mate_id` and `sm2`.`is_delete` = 0 and `sm2`.`mate_of` =$user_id "
		. "LEFT JOIN `" . TBL_STUDENT_ACADEMIC_INFO . "` `in` ON `u`.`id` = `in`.`user_id` "
		. "LEFT JOIN `" . TBL_SCHOOLS . "` `s` ON `s`.`id` = `in`.`school_id` "
		. "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `p` ON `u`.`id` = `p`.`user_id` "
		. "LEFT JOIN `" . TBL_COURSES . "` `c` ON `c`.`id` = `in`.`course_id` "
		. "WHERE date_format(sm.created_date,'%m') IN($m) AND  `u`.`is_delete` = 0 or date_format(sm2.created_date,'%m') IN($m) AND  `u`.`is_delete` = 0";
	$row = mysqli_query($link, $query);

	$i = 0;
	while ($rows = mysqli_fetch_assoc($row)) {
	    $data['result']['my_studymate'][$i] = $rows;
	    $i++;
	}
	for ($i=0; $i <count($data['result']['my_studymate']) ; $i++) { 
		$data['result']['my_studymate'][$i]['created_date'] = $this->get_time_format($data['result']['my_studymate'][$i]['created_date']);
	}



	// like feed
	//$query = "SELECT `upost`.`full_name` as `post_username`,`like_feed`.`feed_text`, DATE_FORMAT(`like`.`created_date`,'%b %d %Y') AS created_date, (select count(*) "
	$query = "SELECT `upost`.`full_name` as `post_username`,`fimage`.`image_link`,`upost`.`id` as `l_id`,`like_feed`.`feed_text`, DATE_FORMAT(`like`.`created_date`,'%b %d %Y') AS created_date, (select count(*) "
		. "FROM `" . TBL_FEED_LIKE . "` "
		. "WHERE `feed_id` = `like_feed`.`id`) AS `totlike`, (SELECT COUNT(*) "
		. "FROM `" . TBL_FEED_COMMENT . "` WHERE `feed_id` = `like_feed`.`id`) AS `totcomment` "
		. "FROM `" . TBL_FEED_LIKE . "` `like` LEFT JOIN `feeds` `like_feed` ON `like_feed`.`id` = `like`.`feed_id` "
		. "LEFT JOIN `" . TBL_USERS . "` `upost` ON `upost`.`id` = `like_feed`.`feed_by` "
		. "LEFT JOIN `" . TBL_FEED_IMAGE . "` `fimage` ON `fimage`.`feed_id` = `like_feed`.`id` "
		. "WHERE `like`.`like_by` = $user_id "
		. "AND date_format(like.created_date,'%m') IN($m) ORDER BY `like`.`created_date` DESC";
	$row = mysqli_query($link, $query);
	$i = 0;
	while ($rows = mysqli_fetch_assoc($row)) {

		if($rows["image_link"] != null)
		{
			$ext = pathinfo($rows["image_link"], PATHINFO_EXTENSION);
			 if( $ext == 'jpeg' || $ext == 'gif' || $ext == 'png' || $ext == 'jpg' ){
			 		$rows['feed_type'] = "media";
			 }else
			 {
			 	$rows['feed_type'] = "doc";
			 }
		}else
		{
			$rows['feed_type'] = "text";
		}

	    $data['result']['my_like'][$i] = $rows;
	    $i++;
	}
	for ($i=0; $i <count($data['result']['my_like']) ; $i++) { 
		$data['result']['my_like'][$i]['created_date'] = $this->get_time_format($data['result']['my_like'][$i]['created_date']);
	}



	// feed comment
	//$query = "SELECT `u`.`full_name`,`u`.`id` as `uid`, `u`.`id`, `comment_feed`.`feed_text`, `p`.`profile_link`, `fimage`.`image_link`, `comment`.`comment`, `comment`.`created_date`, "
	$query = "SELECT `u`.`full_name`,`u`.`id` as `uid`, `u`.`id`, `comment_feed`.`feed_text`, `p`.`profile_link`, `fimage`.`image_link`, `comment`.`comment`,`comment`.`comment_by`, `comment`.`created_date`, "
		. "(SELECT COUNT(*) FROM `" . TBL_FEED_LIKE . "` "
		. "WHERE `feed_id` = `comment_feed`.`id` ) AS `totlike`, "
		. "(SELECT COUNT(*) "
		. "FROM `" . TBL_FEED_COMMENT . "` "
		. "WHERE `feed_id` = `comment_feed`.`id` AND `comment_by` = $user_id) AS `totcomment`, `comment_feed`.`id` "
		. "FROM `" . TBL_FEED_COMMENT . "` `comment` "
		. "LEFT JOIN `" . TBL_FEEDS . "` `comment_feed` ON `comment_feed`.`id` = `comment`.`feed_id` "
		. "LEFT JOIN `" . TBL_USERS . "` `u` ON `u`.`id` = `comment_feed`.`feed_by` "
		. "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `p` ON `p`.`user_id` = `u`.`id` "
		. "LEFT JOIN `" . TBL_FEED_IMAGE . "` `fimage` ON `comment_feed`.`id` = `fimage`.`feed_id` "
		. "WHERE `comment`.`comment_by` = $user_id AND date_format(comment.created_date,'%m') IN($m) GROUP BY `comment_feed`.`id` "
		. "ORDER BY `comment`.`created_date` DESC";
	$row = mysqli_query($link, $query);
	$i = 0;
	while ($rows = mysqli_fetch_assoc($row)) {

		if($rows["image_link"] != null)
		{
			$ext = pathinfo($rows["image_link"], PATHINFO_EXTENSION);
			 if( $ext == 'jpeg' || $ext == 'gif' || $ext == 'png' || $ext == 'jpg' ){
			 		$rows['feed_type'] = "media";
			 }else
			 {
			 	$rows['feed_type'] = "doc";
			 }
		}else
		{
			$rows['feed_type'] = "text";
		}

	    $data['result']['my_comment'][$i] = $rows;
	    $data['result']['my_comment'][$i]['comment_date'] = $this->get_time_format($rows['created_date']);
	    $i++;
	}
	for ($i=0; $i <count($data['result']['my_comment']) ; $i++) { 
		$data['result']['my_comment'][$i]['created_date'] = $this->get_time_format($data['result']['my_comment'][$i]['created_date']);
	}



	// my feed
	$query = "SELECT `fimage`.`image_link`, `post`.`feed_text`, "
		. "(SELECT COUNT(*) FROM `" . TBL_FEED_LIKE . "` "
		. "WHERE `feed_id` = `post`.`id` ) As `totlike`, (SELECT COUNT(*) "
		. "FROM `" . TBL_FEED_COMMENT . "` "
		. "WHERE `feed_id` = `post`.`id` ) AS `totcomment`, `post`.`created_date` FROM `" . TBL_FEEDS . "` `post` "
		. "LEFT JOIN `" . TBL_FEED_IMAGE . "` `fimage` ON `fimage`.`feed_id` = `post`.`id` "
		. "WHERE `post`.`feed_by` = $user_id AND date_format(post.created_date,'%m') IN($m) "
		. "ORDER BY `post`.`created_date` DESC";
	$row = mysqli_query($link, $query);
	$i = 0;
	while ($rows = mysqli_fetch_assoc($row)) {
		if($rows["image_link"] != null)
		{
			$ext = pathinfo($rows["image_link"], PATHINFO_EXTENSION);
			 if( $ext == 'jpeg' || $ext == 'gif' || $ext == 'png' || $ext == 'jpg' ){
			 		$rows['feed_type'] = "media";
			 }else
			 {
			 	$rows['feed_type'] = "doc";
			 }
		}else
		{
			$rows['feed_type'] = "text";
		}

	    $data['result']['my_post'][$i] = $rows;
	    $i++;
	}
	for ($i=0; $i <count($data['result']['my_post']) ; $i++) { 
		$data['result']['my_post'][$i]['created_date'] = $this->get_time_format($data['result']['my_post'][$i]['created_date']);
	}


	$data['format_month'] = date('F Y', strtotime($data['month']));
	$data['new_month'] = date('Y-m', strtotime('-1 month', strtotime($data['month'])));
	return array_merge($data, $this->get_client_info($user_id));
    }

    function get_studymate_detail($user_id, $data) {
	$link = $this->db();
	if (isset($data['user_id'])) {
	    $query = "SELECT `u`.*,`p`.`profile_link`,`s`.`school_name`,`cs`.`course_name`,`c`.`class_name` "
	    . "FROM `" . TBL_USERS . "` `u` "
	    . "LEFT JOIN `" . TBL_STUDENT_ACADEMIC_INFO . "` `inf` ON `inf`.`user_id` = `u`.`id` "
	    . "LEFT JOIN `" . TBL_CLASSROOMS . "` `c` ON `c`.`id` = `inf`.`classroom_id` "
	    . "LEFT JOIN `" . TBL_SCHOOLS . "` `s` ON `s`.`id` = `inf`.`school_id` "
	    . "LEFT JOIN `" . TBL_COURSES . "` `cs` ON `cs`.`id` = `inf`.`course_id` "
	    . "LEFT JOIN `" . TBL_USER_PROFILE_PICTURE . "` `p` ON `p`.`user_id` = `u`.`id` "
	    . "WHERE `u`.`id` = " . $data['user_id'] . " AND `u`.`is_delete` = 0";
	    $row = mysqli_query($link, $query);
	    while ($rows = mysqli_fetch_assoc($row)) {
		$data['result'] = $rows;
	    }
	}
	$data['my_studymate_list'] = $this->class_mate_list($data['user_iddd']);
	$query = 'select * from studymates_request where request_from_mate_id = '.$data['user_iddd'].' AND request_to_mate_id = '.$data['user_id'].' and status = 0';
	$row = mysqli_query($link, $query);
	if(mysqli_num_rows($row) > 0){
	    $data['already'] = 'yes';
	}else{
	    $data['already'] = 'no';
	    $data['pv'] = "123";
	}
	return $data;
    }

    function ctime() {
	$link = $this->db();
	$query = "SELECT NOW() AS ctime";
	$rows = mysqli_query($link, $query);
	$row = mysqli_fetch_array($rows);
	return $row['ctime'];
	;
    }

}

?>
