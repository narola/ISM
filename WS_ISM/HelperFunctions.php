<?php
/**
 * Created by PhpStorm.
 * User: c119
 * Date: 03/03/15
 * Time: 5:10 PM
 */

// print array with format
function pr($arr = null, $exit = 1, $append_text = null) {
    if ($arr != null) {
        echo "<pre>";
        if ($arr != null)
            echo $append_text;

        print_r($arr);

        if ($exit == 1)
            exit;
    }
}


 function validateValue($value, $placeHolder) {
    $value = strlen($value) > 0 ? $value : $placeHolder;
    return $value;
}

function validateObject($object, $keyWord, $placeHolder) {

    if(isset($object -> $keyWord))
    {
//        $value = validateValue($object->$key, "");
        return $object->$keyWord;
    }
    else
    {
        return $placeHolder;
    }
}

function json_validate($string) {
    if (is_string($string)) {
        @json_decode($string);
        return (json_last_error() === JSON_ERROR_NONE);
    }
    return false;
}

function getDefaultDate()
{
    return date("Y-m-d H:i:s");
}

function generatePassword($password)
{
    $cost = 10;

    $saltPassword = strtr(base64_encode(mcrypt_create_iv(16, MCRYPT_DEV_URANDOM)), '+', '.');
    $saltPassword = sprintf("$2a$%02d$", $cost). $saltPassword;

    $finalHashPassword = crypt($password, $saltPassword);

    return $finalHashPassword;
}

function matchPassword($userPassword, $dbPassword)
{
    if (crypt($userPassword, $dbPassword) == $dbPassword)
        return 1;
    else
        return 0;
}

function matchStringValue($str1, $str2)
{
    if (strcmp($str1, $str2))
        return 1;
    else
        return 0;
}

function encryptPassword( $str ) {
//    $qEncoded      = base64_encode( mcrypt_encrypt( MCRYPT_RIJNDAEL_256, md5( ENCRYPTION_KEY ), $str, MCRYPT_MODE_CBC, md5( md5( ENCRYPTION_KEY ) ) ) );

    $qEncoded      = md5($str );

    return( $qEncoded );
}

function decryptPassword( $str ) {
    $qDecoded      = rtrim( mcrypt_decrypt( MCRYPT_RIJNDAEL_256, md5( ENCRYPTION_KEY ), base64_decode( $str ), MCRYPT_MODE_CBC, md5( md5( ENCRYPTION_KEY ) ) ), "\0");
    return( $qDecoded );
}

function gen_random_string()
{
    $chars ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";//length:36
    $final_rand='';
    for($i=0;$i<8; $i++)
    {
        $final_rand .= $chars[ rand(0,strlen($chars)-1)];

    }
    return $final_rand;
}

?>