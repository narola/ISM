<?php

//include 'config.php';   
include 'class.phpmailer.php';   


class SendEmail 
{
    //put your code here
    // constructor
    function __construct() 
    {
         
    }
    
	function sendemail($sender_email_id,$message, $Mailsubject, $userEmailId)
	{

		date_default_timezone_set('Asia/Calcutta');
		$headers = 'MIME-Version: 1.0' . "\r\n";
		$headers .= 'Content-type: text/plain; charset=iso-8859-1' . "\r\n";
		$headers .= 'From: ISM App' . "\r\n";
	
		$subject = $Mailsubject; //'Welcome from NextDoorMenu App';
	
		$mail = new PHPMailer();
		$mail->IsSMTP(); // telling the class to use SMTP
		//$mail->Host = "mail.yourdomain.com"; // SMTP server
		$mail->SMTPDebug = false; // enables SMTP debug information (for testing)
		// 1 = errors and messages
		// 2 = messages only
		$mail->SMTPAuth = true; // enable SMTP authentication
		$mail->SMTPSecure = "ssl"; // sets the prefix to the servier
		$mail->Host = "smtp.gmail.com"; // sets GMAIL as the SMTP server
		$mail->Port = 465; // set the SMTP port for the GMAIL server

	    $mail->Username = "demo.narolainfotech@gmail.com"; // GMAIL username
	    $mail->Password = "Narola21"; // GMAIL password
		
		$from = $sender_email_id; //'demo.narola@gmail.com';
		$to = $userEmailId;
		$mail->SetFrom($from, 'ISM Admin');
		$mail->Subject = $subject;
		//$mail->MsgHTML($content);
		$mail->IsHTML(false);
		$mail->Body = $message;
	
		$address = $to;
	
		$mail->AddAddress($address);
		$mail->Send();
	}
}

?>