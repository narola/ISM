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
    $data['error'] = 'skip';
    $Server->log($data);
    /* For individual chat */
    if ($data['type'] == 'studymate') {
        $responce = $Server->single_chat($clientID, $data);
    } else if ($data['type'] == 'con') {
        $responce = $Server->sync($clientID, $data);
        $classMate = $Server->class_mate_list($Server->wsClients[$clientID][12]);
        foreach ($Server->wsClients as $id => $client) {
            if (in_array($Server->wsClients[$id][12], $classMate)) {
                $res = array(
                    'type' => 'notification',
                    'error' => 'skip',
                    'live_status' => true,
                    'user_id' => $Server->wsClients[$clientID][12]
                ); 
                $Server->wsSend($id, json_encode($res));
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
                                <h5>' . $value['term'][0] . '<span>: ' . $value['partofspeech'][0] . '</span></h5>'
                        . '<strong>Definition:</strong><span>' . $value['definition'][0] . '</span>
                                <h5><span><strong>Example: </strong>' . $value['definition'][0] . '</span></h5>
                            </div>';
            }
        }
    } else if ($data['type'] == 'close_studymate') {
        $responce = $Server->close_studymate($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'send_studymate_request') {
        $responce = $Server->send_studymate_request($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'view-all-comment-activities') {
        $responce = $Server->view_all_comment_activities($Server->wsClients[$clientID][12], $data);
    } else if ($data['type'] == 'time_request' || $data['type'] == 'time_start_request') {
        $responce = $data;
    } else if($data['type'] == 'set_unread'){
        $Server->set_unread($data);
    }

    $check = array('feed_comment', 'like');
    if (isset($responce)) {

        $responce['time_to_left'] = $Server->active_hours();
        $responce['total_active_time'] = $Server->active_hours(2);
        $responce['total_deactive_time'] = $Server->active_hours(3);
        $responce['time_to_start'] = 0;
        if ($responce['time_to_left'] == 0) {
            $responce['time_to_start'] = $Server->active_hours(1);
        }
        $Server->log($responce, 1);
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
            } else if ($responce['type'] == 'discussion') {
                $classmates = $Server->class_mate_list($Server->wsClients[$clientID][12]);
                $my_score = $responce['my_score'];
                $responce['my_score'] = 'skip';
                foreach ($Server->wsClients as $id => $client) {
                    if (in_array($Server->wsClients[$id][12], $classmates)) {
                        if ($id == $clientID) {
                            $responce['my_score'] = $my_score;
                        } else {
                            $responce['my_score'] = 'skip';
                        }
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
    $classMate = $Server->class_mate_list($Server->wsClients[$clientID][12],false);
    foreach ($Server->wsClients as $id => $client) {
        if (in_array($Server->wsClients[$id][12], $classMate)) {
            $res = array(
                'type' => 'notification',
                'error' => 'skip',
                'live_status' => false,
                'user_id' => $Server->wsClients[$clientID][12]
            );
            $Server->wsSend($id, json_encode($res));
        }
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
