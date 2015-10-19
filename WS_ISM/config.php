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
$dbname = 'ism';

$con = "";
$con = mysql_connect($server, $user, $password);

mysql_set_charset('utf8', $con);

if (!$con) {
    die('Database does not connect: ' . mysql_error());
}
else {
    mysql_select_db($dbname, $con);
}

?>