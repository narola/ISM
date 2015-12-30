<?php
/**
 * Created by PhpStorm.
 * User: Prerna
 * Date: 18/10/15
 * Time: 12:10 PM
 */

include_once 'Logger.php';

//ini_set('display_errors', 1);

$logger = new Logger();

date_default_timezone_set('UTC');


$server = "192.168.1.201";
$user = "ISM";
$password = "69NC52j07761uvw";
//$server = "52.28.165.231";
//$user = "root";
//$password = "ism4all";
$dbname = "ism";

global $con;
$con = "";
$con = mysqli_connect($server, $user, $password, $dbname);


mysqli_set_charset($con , 'utf8' );

if (!$con) {
    die('Database does not connect: ' . mysqli_error($con));
}
else {
    mysqli_select_db($con , $dbname);

}

?>