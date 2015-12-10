<?php
/**
 * Created by Kinjal Textwrangler.
 * User: c33
 * Date: 23/11/15
 * Time: 05:13 PM
 * To manage security related functions.
 */
include_once 'ApiCrypter.php';
class SecurityFunctions {

 //============================================== Generate Random Unique Token Number =============================
     
	public function crypto_random_secure($min, $max)
	{
    $range = $max - $min;
    if ($range < 1) return $min; // not so random...
    $log = ceil(log($range, 2));
    $bytes = (int) ($log / 8) + 1; // length in bytes
    $bits = (int) $log + 1; // length in bits
    $filter = (int) (1 << $bits) - 1; // set all lower bits to 1
    do {
        $rnd = hexdec(bin2hex(openssl_random_pseudo_bytes($bytes)));
        $rnd = $rnd & $filter; // discard irrelevant bits
    } while ($rnd >= $range);
  
    return $min + $rnd;
	}


	public function generateToken($length)
	{
    	$token = "";
    	$codeAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	$codeAlphabet.= "abcdefghijklmnopqrstuvwxyz";
    	$codeAlphabet.= "0123456789";
    	$max = strlen($codeAlphabet) - 1;
    	for ($i=0; $i < $length; $i++) {
     	   $token .= $codeAlphabet[$this->crypto_random_secure(0, $max)];
   	 }
   
    return $token;
	}
 
   //============================================== For AES Encryption ========================================
	public function functionToEncrypt($sValue, $sSecretKey) {
    	global $iv;
    	
    	return rtrim(base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_256, $sSecretKey, $sValue, MCRYPT_MODE_CBC, $iv)), "\0\3");
	}
   
   
   //============================================== For AES Decryption ==========================================
    public	function functionToDecrypt($sValue,$aes256Key) {
   		 global $iv;
   		 
        $value= rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_256, $aes256Key, base64_decode($sValue), MCRYPT_MODE_CBC, $iv), "\0\3");
        return $value;
	}
     
     
     //============================================== Check For Security ==========================================
     public function checkForSecurity1($accessvalue,$secretvalue)
     {

             //For Decrpt Access Key=================================================================================

             $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
             $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
             $masterKey = mysqli_fetch_row($result);


             // 32 byte binary blob
             $aes256Key = hash("SHA256", $masterKey[0], true);

             // for good entropy (for MCRYPT_RAND)
             srand((double)microtime() * 1000000);

             // generate random iv
             $iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_CBC), MCRYPT_RAND);

             $decrypted_access_key = $this->functionToDecrypt($accessvalue, $aes256Key);


             //For Decrpt Secret Key=================================================================================


             // 32 byte binary blob
             $aes256Key1 = hash("SHA256", $decrypted_access_key, true);

             // for good entropy (for MCRYPT_RAND)
             srand((double)microtime() * 1000000);

             // generate random iv
             $iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_CBC), MCRYPT_RAND);

             $decrypted_secret_key = $this->functionToDecrypt($secretvalue, $aes256Key1);


             //To Compare Decrypted Secret key in Token table=======================================================

             $queryToken = "SELECT token FROM " . TABLE_TOKENS . " WHERE is_delete=0";
             $resultToken = mysqli_query($GLOBALS['con'], $queryToken) or $message = mysqli_error($GLOBALS['con']);


             if (mysqli_num_rows($resultToken)) {

                 while ($row = mysqli_fetch_assoc($resultToken)) {

                     $found = in_array($decrypted_secret_key, $row, true);

                     if ($found) {
                         $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='userAgent' AND is_delete=0";
                         $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                         $user_agent = mysqli_fetch_row($result);
                         $separateKey = (explode(',', $user_agent[0]));

                         //echo $found1 = in_array($_SERVER ['HTTP_USER_AGENT'],$separateKey,true); exit;


                         if ((strpos($_SERVER ['HTTP_USER_AGENT'], $separateKey[0]) !== false) || (strpos($_SERVER ['HTTP_USER_AGENT'], $separateKey[1]) !== false))
                             //echo "found";
                             return yes;
                         else
                             //echo "nt found";
                             return yes;

                     }
                 }

             }
             //return no;
         return yes;
     }



    public function checkForSecurity($accessvalue,$secretvalue)
    {

        //For Decrpt Access Key=================================================================================

        $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
        $masterKey = mysqli_fetch_row($result);

        //For Decrpt Access Key=================================================================================
        $security=new Security();
        $decrypted_access_key=$security->decrypt($accessvalue,$masterKey[0]);


        //For Decrpt Secret Key=================================================================================

        $decrypted_secret_key = $security->decrypt($secretvalue, $decrypted_access_key);


        //To Compare Decrypted Secret key in Token table=======================================================

        $queryToken = "SELECT token FROM " . TABLE_TOKENS . " WHERE is_delete=0";
        $resultToken = mysqli_query($GLOBALS['con'], $queryToken) or $message = mysqli_error($GLOBALS['con']);


        if (mysqli_num_rows($resultToken)) {

            while ($row = mysqli_fetch_assoc($resultToken)) {

                $found = in_array($decrypted_secret_key, $row, true);

                if ($found) {
                    $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='userAgent' AND is_delete=0";
                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                    $user_agent = mysqli_fetch_row($result);
                    $separateKey = (explode(',', $user_agent[0]));

                    //echo $found1 = in_array($_SERVER ['HTTP_USER_AGENT'],$separateKey,true); exit;


                    if ((strpos($_SERVER ['HTTP_USER_AGENT'], $separateKey[0]) !== false) || (strpos($_SERVER ['HTTP_USER_AGENT'], $separateKey[1]) !== false))
                        //echo "found";
                        return yes;
                    else
                        //echo "nt found";
                       // return no;
                    return yes;

                }
            }

        }
        //return no;
        return yes;
    }

    public function checkForSecurityTest($accessvalue,$secretvalue)
    {
        $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='tempToken' AND is_delete=0";
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
        $tempToken = mysqli_fetch_row($result);

        if($accessvalue=="nousername")
        {
            if($secretvalue==NULL)
            {
                return $tempToken[0];
            }
            else {
                if ($secretvalue == $tempToken[0]) {
                    return yes;
                }
                else{
                    return no;
                }
            }
        }

        else
        {
            if($secretvalue==$tempToken[0])
            {

                $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                $masterKey = mysqli_fetch_row($result);

                //step1= decrpt accesskey with global password
                //For Decrpt Access Key=================================================================================
                $security=new Security();
                $decrypted_access_key=$security->decrypt($accessvalue,$masterKey[0]);

                //step2= Check access key in user table
                $queryToCheckAccessKeyExist="SELECT * FROM ". TABLE_USERS." WHERE username='".$decrypted_access_key."'";
                $resultToCheckAccessKeyExist=mysqli_query($GLOBALS['con'], $queryToCheckAccessKeyExist) or $message = mysqli_error($GLOBALS['con']);

                if(mysqli_num_rows($resultToCheckAccessKeyExist)>0)
                {
                    return yes;
                }
                else{
                    //step3= Check access key in auto generated table
                    $queryToCheckAccessKeInAuto="SELECT * FROM ". TABLE_AUTO_GENERATED_CREDENTIAL." WHERE username='".$decrypted_access_key."'";
                    $resultToCheckAccessKeyInAuto=mysqli_query($GLOBALS['con'], $queryToCheckAccessKeInAuto) or $message = mysqli_error($GLOBALS['con']);
                    if(mysqli_num_rows($resultToCheckAccessKeyInAuto)>0)
                    {
                        return $tempToken[0];
                    }
                    else
                    {
                        return no;
                    }
                }
            }
            else{

                $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
                $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                $masterKey = mysqli_fetch_row($result);

                //step1= decrpt accesskey with global password
                //For Decrpt Access Key=================================================================================
                $security=new Security();
                $decrypted_access_key=$security->decrypt($accessvalue,$masterKey[0]);

                //step2= Check access key in user table
                echo $queryToCheckAccessKeyExist="SELECT * FROM ". TABLE_USERS." WHERE username='".$decrypted_access_key."' AND is_delete=0";
                $resultToCheckAccessKeyExist=mysqli_query($GLOBALS['con'], $queryToCheckAccessKeyExist) or $message = mysqli_error($GLOBALS['con']);

                if(mysqli_num_rows($resultToCheckAccessKeyExist)>0)
                {
                    $user_id = mysqli_fetch_row($resultToCheckAccessKeyExist);


                    $queryToCheckRecordExist = "SELECT * FROM " . TABLE_TOKENS . " WHERE user_id=" . $user_id[0] . " AND is_delete=0";
                    $resultToCheckRecordExist = mysqli_query($GLOBALS['con'], $queryToCheckRecordExist) or $message = mysqli_error($GLOBALS['con']);

                    if(mysqli_num_rows($resultToCheckAccessKeyExist)>0) {
                        $rowRecord = mysqli_fetch_row($resultToCheckRecordExist);
                        $tokenName = $rowRecord[2];


                        //For Decrpt Secret Key=================================================================================

                        echo $encrypted_token = $security->encrypt($tokenName, $user_id[1]);

                        //return $encrypted_token;
                        return yes;
                    }
                    else{
                        $generateToken = $security->generateToken(8);

                        $insertTokenField = "`user_id`, `token`";
                        $insertTokenValue = "" . $user_id[0] . ",'" . $generateToken . "'";

                        $queryAddToken = "INSERT INTO " . TABLE_TOKENS . "(" . $insertTokenField . ") values (" . $insertTokenValue . ")";
                        $resultAddToken = mysqli_query($GLOBALS['con'], $queryAddToken) or $message = mysqli_error($GLOBALS['con']);
                        $tokenName = $security->encrypt($generateToken, $user_id[1]);
                        return $tokenName;
                    }
                }
                else{
                    //step3= Check access key in auto generated table
                    $queryToCheckAccessKeInAuto="SELECT * FROM ". TABLE_AUTO_GENERATED_CREDENTIAL." WHERE username='".$decrypted_access_key."'";
                    $resultToCheckAccessKeyInAuto=mysqli_query($GLOBALS['con'], $queryToCheckAccessKeInAuto) or $message = mysqli_error($GLOBALS['con']);
                    if(mysqli_num_rows($resultToCheckAccessKeyInAuto)>0)
                    {
                        return $tempToken[0];
                    }
                    else
                    {
                        return no;
                    }
                }
            }
        }


    }

    public function normalSecurity($access_key,$secret_key)
    {
        //For Decrpt Access Key=================================================================================

        $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
        $masterKey = mysqli_fetch_row($result);

        //For Decrpt Access Key=================================================================================
        $security=new Security();
        $decrypted_access_key=$security->decrypt($access_key,$masterKey[0]);


        //For Decrpt Secret Key=================================================================================

        $decrypted_secret_key = $security->decrypt($secret_key, $decrypted_access_key);


        //To Compare Decrypted Secret key in Token table=======================================================

        $queryToken = "SELECT token FROM " . TABLE_TOKENS . " WHERE is_delete=0";
        $resultToken = mysqli_query($GLOBALS['con'], $queryToken) or $message = mysqli_error($GLOBALS['con']);


        if (mysqli_num_rows($resultToken)) {

            while ($row = mysqli_fetch_assoc($resultToken)) {

                $found = in_array($decrypted_secret_key, $row, true);

                if ($found) {
                    $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='userAgent' AND is_delete=0";
                    $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
                    $user_agent = mysqli_fetch_row($result);
                    $separateKey = (explode(',', $user_agent[0]));

                    //echo $found1 = in_array($_SERVER ['HTTP_USER_AGENT'],$separateKey,true); exit;


                    if ((strpos($_SERVER ['HTTP_USER_AGENT'], $separateKey[0]) !== false) || (strpos($_SERVER ['HTTP_USER_AGENT'], $separateKey[1]) !== false))
                        //echo "found";
                        return yes;
                    else
                        //echo "nt found";
                        // return no;
                        return yes;

                }
            }

        }
        //return no;
        return yes;
    }


    public function  checkForFistTimeLoginSecurity($access_key,$secret_key)
    {
        //Step=1 For Decrpt Access Key With Global Pasword=================================================================================

        $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
        $masterKey = mysqli_fetch_row($result);

           //For Decrpt Access Key=================================================================================
        $security=new Security();
        $decrypted_access_key=$security->decrypt($access_key,$masterKey[0]);

        //Step=2 Check if access key is in users table

        $queryToCheckAccessKeyExist="SELECT * FROM ". TABLE_USERS." WHERE username='".$access_key."'";
        $resultToCheckAccessKeyExist=mysqli_query($GLOBALS['con'], $queryToCheckAccessKeyExist) or $message = mysqli_error($GLOBALS['con']);

        if(mysqli_num_rows($resultToCheckAccessKeyExist)>0)
        {
            $this->normalSecurity($decrypted_access_key,$secret_key);
        }
        else{
            //Check in Auto Generated table


            $queryToCheckAccessKeInAuto="SELECT * FROM ". TABLE_AUTO_GENERATED_CREDENTIAL." WHERE username='".$decrypted_access_key."'";
            $resultToCheckAccessKeyInAuto=mysqli_query($GLOBALS['con'], $queryToCheckAccessKeInAuto) or $message = mysqli_error($GLOBALS['con']);
            if(mysqli_num_rows($resultToCheckAccessKeyInAuto)>0)
            {
                $this->normalSecurity($access_key,$secret_key);
            }
            else
            {
                return no;
            }
        }

    }
    public function decryptUsername($username)
    {
        $query = "SELECT config_value FROM " . TABLE_ADMIN_CONFIG . " WHERE config_key='globalPassword' AND is_delete=0";
        $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
        $masterKey = mysqli_fetch_row($result);


        // 32 byte binary blob2
        $aes256Key = hash("SHA256", $masterKey[0], true);
        //$aes256Key = hash("SHA1PRNG", $masterKey[0], true);


        // for good entropy (for MCRYPT_RAND)
        srand((double)microtime() * 1000000);

        // generate random iv
        $iv = mcrypt_create_iv(mcrypt_get_iv_size(MCRYPT_RIJNDAEL_256, MCRYPT_MODE_CBC), MCRYPT_RAND);


        $decrypted_access_key = $this->functionToDecrypt($username, $aes256Key);

        return$decrypted_access_key;
    }
}


?>