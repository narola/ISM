<?php
/**
 * Created by JetBrains PhpStorm.
 * User: c33
 * Date: 12/09/14
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
define("AWS_BUCKET_NAME", "wispersingaserver");
define("AWS_BUCKET_TYPE", "WisperSinga");
define("AWS_ACCESS_KEY", "AKIAJ5GIFWS4ZQ4Q6E2Q");
define("AWS_SECRET_KEY", "qlBUqsc009fgYTqGa5tx+O4fosNG1lvUNyS7FgnK");
class Media {

    //put your code here
    // constructor

    function __construct()
    {
    }

    public function call_service($service, $postData)
    {

        switch($service)
        {


            case "UploadMedia":
            {
                return $this->uploadMedia($_POST, $_FILES['mediaData']['name']);
            }
                break;

            case "UploadLyf":
            {
                return $this->uploadLyf($_POST, $_FILES['mediaData']['name']);
                //  return $this->uploadTestVideoOnS3($_POST, $_FILES['mediaData']['name']);

            }
                break;



            case "BroadcastPushNotificationV3":
            {
                return $this->broadcastPushNotification($postData);


            }
                break;

            default:
                break;
        }
    }

    /**
     * Test Upload Video
     * @param $userImage
     * @return mixed
     */

    public function uploadTestVideoOnS3($data)
    {
        $status = 2;
        $posts = array();
        $user_id = $data['UserID'];
        $mediaType = $data['mediaType'];
        $posts = array();

        if ($user_id != '') {

            if ($_FILES["mediaData"]["error"] > 0)
            {
                $errorMsg = $_FILES["mediaData"]["error"];
                $posts['Image'] = null;
            }
            else {
                $fileName = $_FILES['mediaData']['name'];
                //  $size = $_FILES['userUploadVideo']['size'];

                $thumbTempName = nil;
                $thumbName = nil;
                if ($mediaType != '6') {

                    $thumbTempName = $_FILES['thumbData']['tmp_name'];
                    $thumbName = $_FILES['thumbData']['name'];
                }
                $fileTempName = $_FILES['mediaData']['tmp_name'];
                //  $ext = $this->getExtension($fileName);

                //    $valid_formats = array("mp4", "mov", "avi", "MP4", "MOV", "AVI", "jpg", "png");



                $dbcon = new Database();
                $created_date = date("Y-m-d H:i:s");
                //create Random String.
                $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                //generate random string with minimum 5 and maximum of 10 characters
                $str = substr(str_shuffle($chars), 0, 8);
                //add extension to file
                $name = $fileName;//$str;
                $mediaType = $data['mediaType'];
                //  echo "file name :".$name;

                //check media type
                $dir='';
                //      $mediaName= $fileName;
//                if ($mediaType == '6') {
//                    $mediaName = $name . '.mp3';
//                    $dir ="upload/Audio/";
//
//                }
//                else if ($mediaType == '4')
//                {
//                    $mediaName = $name . '.png';
//                    $dir ="upload/Image/";
//                }
//                else if ($mediaType == '5')
//                {
//                    $mediaName = $name . '.mp4';
//                    $dir ="upload/Video/";
//                }
//                if (!is_dir($dir)) {
//                    mkdir($dir, 0777);
//                }



//                if (strlen($fileName) > 0) {
//
//                    if (in_array($ext, $valid_formats)) {

                if (!class_exists('S3')) require_once('S3.php');

                if (!defined('awsAccessKey')) define('awsAccessKey', AWS_ACCESS_KEY);
                if (!defined('awsSecretKey')) define('awsSecretKey', AWS_SECRET_KEY);

                $s3 = new S3(awsAccessKey, awsSecretKey);

                // $s3->putBucket(AWS_BUCKET_NAME, S3::ACL_PUBLIC_READ);
                $uploadFile = AWS_BUCKET_TYPE . "/" . $fileName;
                $uploadThumb = AWS_BUCKET_TYPE . "/" . $thumbName;


                if ($mediaType == '6') {

                    if ($s3->putObjectFile($fileTempName, AWS_BUCKET_NAME, $uploadFile, S3::ACL_PUBLIC_READ)) {

//                            $status = 1;
//                            $errorMsg = "Successfully uploaded.";
//                            $posts['video_name'] = $fileName;

                        $image_details = array(
                            "media_name" => "'" . $fileName . "'",
                            "type" => "'" . $mediaType . "'",
                            "created_date" => "'" . $created_date . "'",
                            "user_id" => "'" . $user_id . "'",
                        );

                        $insert_image_details = $dbcon->insert($image_details, 'media');
                        $image_detail = $dbcon->select('media', 'id=' . $insert_image_details);

                        $posts = $image_detail['media'];
                        $status = 1;
                        $errorMsg = "Successfully changed.";

                    } else {

                        $status = 2;
                        $errorMsg = "Failed to upload media on server.";
                        $posts = null;
                    }

                }
                else {
                    if ($s3->putObjectFile($thumbTempName, AWS_BUCKET_NAME, $uploadThumb, S3::ACL_PUBLIC_READ)) {
                        if ($s3->putObjectFile($fileTempName, AWS_BUCKET_NAME, $uploadFile, S3::ACL_PUBLIC_READ)) {

//                            $status = 1;
//                            $errorMsg = "Successfully uploaded.";
//                            $posts['video_name'] = $fileName;

                            $image_details = array(
                                "media_name" => "'" . $fileName . "'",
                                "type" => "'" . $mediaType . "'",
                                "created_date" => "'" . $created_date . "'",
                                "user_id" => "'" . $user_id . "'",
                            );

                            $insert_image_details = $dbcon->insert($image_details, 'media');
                            $image_detail = $dbcon->select('media', 'id=' . $insert_image_details);

                            $posts = $image_detail['media'];
                            $status = 1;
                            $errorMsg = "Successfully changed.";

                        } else {

                            $status = 2;
                            $errorMsg = "Failed to upload media on server.";
                            $posts = null;
                        }


                    } else {
                        $status = 2;
                        $errorMsg = "Failed to upload thumbnail on server.";
                        $posts = null;
                    }
                }
//                    } else {
//
//                        $status = 2;
//                        $errorMsg = "Invalid file, please upload video file.";
//                        $posts['video_name'] = null;
//                    }
//                } else {
//                    $status = 2;
//                    $errorMsg = "Invalid file, please upload video file.";
//                    $posts['video_name'] = null;
//                }
            }
        }
        else
        {
            $errorMsg = "Invalid uploder identity";
            $posts = null;
            $status = 2;
        }

        $data['status'] = $status;
        $data['message'] = $errorMsg;
        $data['data'] = $posts;

        return $data;
    }

    public function uploadMedia($data, $userImage)
    {

        $status = 2;
        $posts = array();
        $user_id = $data['UserID'];

        if ($user_id != '')
        {
            if ($_FILES["mediaData"]["error"] > 0)
            {
                $errorMsg = $_FILES["mediaData"]["error"];
                $posts['Image'] = null;
            }
            else
            {
                // Image 5 = Video 6 = Audio 7

                $dbcon = new Database();
                $created_date = date("Y-m-d H:i:s");
                //create Random String.
                $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                //generate random string with minimum 5 and maximum of 10 characters
                $str = substr(str_shuffle($chars), 0, 8);
                //add extension to file
                $name = $str;
                $mediaType = $data['mediaType'];


                //check media type
                $dir='';
                $mediaName= '';
                if ($mediaType == '6' || $mediaType == '11') {
                    $mediaName = $name . '.mp3';
                    $dir ="upload/Audio/";

                }
                else if ($mediaType == '4' || $mediaType == '9')
                {
                    $mediaName = $name . '.png';
                    $dir ="upload/Image/";
                }
                else if ($mediaType == '5' || $mediaType == '10')
                {
                    $mediaName = $name . '.mp4';
                    $dir ="upload/Video/";
                }
                if (!is_dir($dir)) {
                    mkdir($dir, 0777);
                }

                $uploadDir = $dir;
                $uploadFile = $uploadDir . $mediaName;

                if (move_uploaded_file($_FILES['mediaData']['tmp_name'], $uploadFile))
                {

                    //store image data.
                    $image_details = array(
                        "media_name" => "'" . $mediaName . "'",
                        "type" => "'" . $mediaType . "'",
                        "created_date" => "'" . $created_date . "'",
                        "user_id" => "'" . $user_id . "'",
                    );

                    $insert_image_details = $dbcon->insert($image_details, 'media');
                    $image_detail = $dbcon->select('media', 'id=' . $insert_image_details);

                    $posts = $image_detail['media'];
                    $status = 1;
                    $errorMsg = "Successfully changed.";


                }
                else
                {
                    $status = 2;
                    $errorMsg = "Failed to upload media on server.";
                    $posts = null;
                }
            }
        }
        else {
            $errorMsg = "Invalid uploder identity";
            $posts = null;
            $status = 2;
        }


        $data['status'] = $status;
        $data['message'] = $errorMsg;
        $data['data'] = $posts;

        return $data;
    }



    /** Get Extension
     * @param $str
     * @return string
     */
    private function getExtension($str)
    {
        $i = strrpos($str,".");
        if (!$i) { return ""; }

        $l = strlen($str) - $i;
        $ext = substr($str,$i+1,$l);
        return $ext;
    }


    public function uploadLyf($data, $userImage)
    {

        // echo "coming";
        $status = 2;
        $posts = array();
        $user_id = $data['UserID'];

        if ($user_id != '')
        {
            // echo $begin = microtime(true); 
            if ($_FILES["mediaData"]["error"] > 0)
            {
                echo "coming ".$_FILES["mediaData"]["error"];
                $errorMsg = $_FILES["mediaData"]["error"];
                $posts['Image'] = null;
            }
            else
            {
                // Image 5 = Video 6 = Audio 7

                $dbcon = new Database();
                $created_date = date("Y-m-d H:i:s");
                $name = $data['mediaName'];
                $mediaType = $data['mediaType'];


                //check media type
                $dir='';
                $mediaName= '';
                if ($mediaType == '4' || $mediaType == '8') {
                    $mediaName = $name ;
                    $dir ="upload/Audio/";
                }
                else if ($mediaType == '1' || $mediaType == '6')
                {
                    $mediaName = $name ;
                    $dir ="upload/Image/";
                }
                else if ($mediaType == '3' || $mediaType == '7')
                {
                    $mediaName = $name ;
                    $dir ="upload/Video/";
                }
                if (!is_dir($dir)) {
                    mkdir($dir, 0777);
                }

                $uploadDir = $dir;
                $uploadFile = $uploadDir . $mediaName;

                $name = explode('.', $mediaName);
                $thumbuploadFile = $uploadDir ."th_".$name[0].".png";

                if(isset($_FILES['thumbData']['tmp_name']))
                {

                    if (!move_uploaded_file($_FILES['thumbData']['tmp_name'], $thumbuploadFile))
                    {

                    }


                }


                if (move_uploaded_file($_FILES['mediaData']['tmp_name'], $uploadFile))
                {

                    //store image data.
                    $image_details = array(
                        "media_name" => "'" . $mediaName . "'",
                        "type" => "'" . $mediaType . "'",
                        "created_date" => "'" . $created_date . "'",
                        "user_id" => "'" . $user_id . "'",
                    );

                    $insert_image_details = $dbcon->insert($image_details, 'media');
                    $image_detail = $dbcon->select('media', 'id=' . $insert_image_details);

                    $posts = $image_detail['media'];
                    $status = 1;
                    $errorMsg = "Successfully changed.";


                }
                else
                {
                    $status = 2;
                    $errorMsg = "Failed to upload media on server.";
                    $posts = null;
                }
            }
        }
        else {
            $errorMsg = "Invalid uploder identity";
            $posts = null;
            $status = 2;
        }


        $data['status'] = $status;
        $data['message'] = $errorMsg;
        $data['data'] = $posts;

        //echo "<hr><br><br>Upload took: ", round((microtime(true) - $begin),5), " MS";
        return $data;
    }


    public function broadcastPushNotification($postData)
    {

        $status = 1;
        $posts = array();
        $msgType = $postData -> MessageType;
        $errorMsg = "";
        $msg ="";
        if($msgType == 0){
            $msg  = "new message from wisper crew";
        }
        elseif($msgType == 1){
            $msg  = "new lyfe from wisper crew";
        }
        $data['status'] = $status;
        $data['message'] = $errorMsg;
        $data['data'] = $posts;

        include_once 'RemotePushManager.php';

        $pushNotification = new RemotePushManager();


        $queryToPlayer = ""."Select id,device_token from user where device_token IS NOT NULL AND LENGTH(device_token) <> 0 AND device_token <> '(null)'";
        $resToPlayer = mysql_query($queryToPlayer) or $errorMsg =  mysql_error();


        if(mysql_num_rows($resToPlayer) != 0) {

            $pushNotification->sendBroadcastPushToIOS($resToPlayer, $msg);
        }


        return $data;

    }


}


?>