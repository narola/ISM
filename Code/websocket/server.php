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
                foreach ($Server->wsClients as $id => $client) {
                    if (in_array($Server->wsClients[$id][12], $responce['classmates'])) {
                        $res = array(
                            'type' => 'notification',
                            'status' => 'available',
                            'live_status' => true,
                            'user_id' => $user_info['id'],
                            'profile_link' => $user_info['profile_link'],
                            'message' => "<b>" . $user_info['full_name'] . "</b> is now online!"
                        );
                        $Server->log("Online");
                        $Server->wsSend($id, json_encode($res));
                    }
                }
                $Server->wsSend($id, json_encode(array('type' => 'online_users', 'message' => $Server->check_online_classmate($Server->wsClients[$clientID][12]))));
            }
        }
    } else if ($data['type'] == 'get_latest_message') {
        $responce = $Server->get_latest_msg($data,$Server->wsClients[$clientID][12]);
    }

    if ($responce['to'] == 'self') {
        $Server->wsSend($clientID, json_encode($responce));
    } else {
        if ($responce['type'] == 'studymate') {
            
            /* Send to Receiver.. */
            foreach ($Server->wsClients as $id => $client) {
                if ($responce['to'] == $Server->wsClients[$id][12]) {
                    $Server->wsSend($id, json_encode($responce));
                }
            }
            /* Send to self. */
            $Server->wsSend($clientID, json_encode($responce));
        } else {
            foreach ($Server->wsClients as $id => $client)
                $Server->wsSend($id, json_encode($responce));
        }
    }
}

// when a client connects
function wsOnOpen($clientID) {
    global $Server;
}

// when a client closes or lost connection
function wsOnClose($clientID, $status) {
    global $Server;
    $user_info = $Server->get_client_info($Server->wsClients[$clientID][12]);
    $classMate = $Server->class_mate_list($Server->wsClients[$clientID][12]);
    if ($user_info != null) {
        foreach ($Server->wsClients as $id => $client) {
            if (in_array($Server->wsClients[$id][12], $classMate)) {
                $res = array(
                    'type' => 'notification',
                    'status' => 'available',
                    'live_status' => false,
                    'user_id' => $user_info['id'],
                    'profile_link' => $user_info['profile_link'],
                    'message' => "<b>" . $user_info['full_name'] . "</b> is now offline!",
                    'online_users' => $Server->check_online_classmate($Server->wsClients[$clientID][12])
                );
                $Server->log("Offline");
                $Server->wsSend($id, json_encode($res));
            }
        }
    } else {
        $Server->log("Null got");
    }
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
