<?php

// prevent the server from timing out
set_time_limit(0);

require 'config.php';
require 'conertor.php';
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
    $Server->log($data);
    /* For individual chat */
    if ($data['type'] == 'studymate') {
        $responce = $Server->single_chat($clientID, $data);
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
                        $Server->wsSend($id, json_encode($res));
                    }
                }
                $Server->wsSend($id, json_encode(array('type' => 'online_users', 'message' => $Server->check_online_classmate($Server->wsClients[$clientID][12]))));
            }
        }
    } else if ($data['type'] == 'get_latest_message') {
        $responce = $Server->get_latest_msg($data, $Server->wsClients[$clientID][12]);
    } else if ($data['type'] == 'post') {
        $responce = $Server->classmate_post($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'feed_comment') {
        $responce = $Server->classmate_comment($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'load_more_feed') {
        $responce = $Server->load_more($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'like') {
        $responce = $Server->post_like_unlike($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'discussion') {
        $responce = $Server->discussion($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'discussion-type') {
        $classmates = $Server->class_mate_list($Server->wsClients[$clientID][12]);
        foreach ($Server->wsClients as $id => $client) {
            if (in_array($Server->wsClients[$id][12], $classmates) && $id != $clientID) {
                $data['type_id'] = $Server->wsClients[$clientID][12];
                $Server->wsSend($id, json_encode($data));
            }
        }
    } else if ($data['type'] == 'dictionary') {
        $responce = $Server->dictionary($data);
        $xml = new simpleXml2Array($responce['message'], null);
        $vals = $xml->arr;
        $responce['message'] = 'No result found!';
        if (isset($vals['result'])) {
            $responce['message'] = '';
            foreach ($vals['result'] as $key => $value) {
                $responce['message'] .= ' <div class="result_box">
                                <h5>'.$value['term'][0].'<span>: '.$value['partofspeech'][0].'</span></h5>'
                                .'<strong>Definition:</strong><span>' . $value['definition'][0] . '</span>
                                <h5><span><strong>Example: </strong>' . $value['definition'][0] . '</span></h5>
                            </div>';
                
            }
        }
    }else if($data['type'] == 'close_studymate'){
          $responce = $Server->close_studymate($Server->wsClients[$clientID][12], $data);
    }
    
    
    
    
    $check = array('feed_comment', 'like', 'discussion');
    if (isset($responce)) {
        $Server->log($responce, false);
        if ($responce['to'] == 'self') {           
            $Server->wsSend($clientID, json_encode($responce));
        } else {
            if ($responce['type'] == 'studymate') {
                foreach ($Server->wsClients as $id => $client) {
                    if ($responce['to'] == $Server->wsClients[$id][12]) {
                        $Server->wsSend($id, json_encode($responce));
                    }
                }
                $Server->wsSend($clientID, json_encode($responce));
            } else if ($responce['type'] == 'post') {
                $classmates = $Server->class_mate_list($Server->wsClients[$clientID][12]);
                foreach ($Server->wsClients as $id => $client) {
                    if (in_array($Server->wsClients[$id][12], $classmates)) {
                        $Server->wsSend($id, json_encode($responce));
                    }
                }
            } else if (in_array($responce['type'], $check)) {
                foreach ($Server->wsClients as $id => $client) {
                    if (in_array($Server->wsClients[$id][12], $responce['allStudyMate'])) {
                        $Server->wsSend($id, json_encode($responce));
                    }
                }
            }
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
