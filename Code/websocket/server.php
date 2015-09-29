<?php

// prevent the server from timing out
set_time_limit(0);

require 'config.php';
// include the web sockets server script (the server is started at the far bottom of this file)
require 'class.PHPWebSocket.php';

// when a client sends data to the server
function wsOnMessage($clientID, $message, $messageLength, $binary) {
    global $Server;
    $ip = long2ip($Server->wsClients[$clientID][6]);
    if ($messageLength == 0) {
        $Server->wsClose($clientID);
        return;
    }

    $data = json_decode($message, true);
    /* For individual chat */
    if ($data['type'] == 'studymate') {
        $responce = $Server->single_chat($clientID, $data);
    } else if ($data['type'] == 'post') {
        $responce = $Server->classmate_post($data);
    } else if ($data['type'] == 'con') {
        $responce = $Server->sync($clientID, $data);
        if ($responce['error'] == '') {
            $user_info = $Server->get_client_info($Server->wsClients[$clientID][12]);
            if ($user_info != null) {
                $Server->log(json_encode($user_info));
                foreach ($Server->wsClients as $id => $client) {
                    if (in_array($Server->wsClients[$id][12], $responce['classmates'])) {
                        $res = array(
                            'type' => 'notification',
                            'status' => 'available',
                            'message' => $user_info['full_name'] . " is online!!"
                        );
                        $Server->wsSend($id, json_encode($res));
                    }
                }
            }
        }
    }

    if ($responce['to'] == 'self') {
        $Server->wsSend($clientID, json_encode($responce));
    } else {
        foreach ($Server->wsClients as $id => $client)
            $Server->wsSend($id, json_encode($responce));
    }

    /*
      if (sizeof($Server->wsClients) == 1)
      $Server->wsSend($clientID, "There isn't anyone else in the room, but I'll still listen to you. --Your Trusty Server");
      else
      foreach ($Server->wsClients as $id => $client)
      if ($id != $clientID)
      $Server->wsSend($id, "Visitor $clientID ($ip) said \"$message\"");
     */
}

// when a client connects
function wsOnOpen($clientID) {
    global $Server;

    // $Server->wsSend($clientID, json_encode(array('type' => 'connect_check')));
    //Send a join notice to everyone but the person who joined
    foreach ($Server->wsClients as $id => $client)
        if ($id != $clientID)
            $Server->wsSend($id, "Visitor $clientID ($ip) has joined the room.");
}

// when a client closes or lost connection
function wsOnClose($clientID, $status) {
    global $Server;
    $ip = long2ip($Server->wsClients[$clientID][6]);

    $Server->log("$ip ($clientID) has disconnected.");

    //Send a user left notice to everyone in the room
    foreach ($Server->wsClients as $id => $client)
        $Server->wsSend($id, "Visitor $clientID ($ip) has left the room.");
}

// start the server
$Server = new PHPWebSocket();
$Server->bind('message', 'wsOnMessage');
$Server->bind('open', 'wsOnOpen');
$Server->bind('close', 'wsOnClose');

// for other computers to connect, you will probably need to change this to your LAN IP or external IP,
// alternatively use: gethostbyaddr(gethostbyname($_SERVER['SERVER_NAME']))
$Server->wsStartServer('192.168.1.124', 9300);
// C:\wamp\bin\php\php5.5.12\php.exe -f "C:\wamp\www\ISM\Code\websocket\server.php"
?>
