<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ISM | Group Allocation</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
        <base href="<?php echo base_url();?>" >

        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
        <!--custom css-->
        <link href="assets/css/ism_style.css" rel="stylesheet">
        <link href="assets/css/responsive.css" rel="stylesheet">
        <link href="assets/css/icon.css" rel="stylesheet">
        <!--scroll-->
        <link href="assets/css/jquery.mCustomScrollbar.css" rel="stylesheet">
        <!--fonts-->
        <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Arvo:400,400italic,700italic,700' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <link rel="icon" type="image/png" href="assets/images/graduate.png" sizes="32x32" />
    </head>
    
    <body>
    	<div class="container-fluid">
            <!--row1-->
            <div class="row reg_header">
                <div class="col-sm-12">
                    <img src="assets/images/site_logo.png">
                </div>
            </div>
            <!--row2-->
            <div class="row group_profile">
            	<div class="col-sm-12 text-center">
                	<div class="user_container">
                        <div class="user_profile_img">
                            <img src="assets/images/user1.jpg">
                        </div>
                        <div class="user_profile_info">
                            <h1>Welcome To ISM</h1>
                            <h2>Adam Rose</h2>
                            <h4>St.Xaviers High School</h4>
                            <h4>First Year, Computer Science</h4>
                        </div>
                    </div>
                </div>
            </div>
            <!--row3-->
            <div class="row background">
            	<div class="container">
                	<div class="row">
                    	<div class="col-sm-12 text-center">
                        	<h2 class="noti_username">Your Tutorial group is as per below</h2>
                        </div>
                    	<!--item1-->
                        <?php 
                        foreach($users as $user){
                            ?>
                            <div class="col-sm-6">
                                <div class="col-sm-12 group_member">
                                    <div class="col-sm-4 member text-center">
                                        <div class="user_profile_img">
                                            <img src="<?php echo UPLOAD_URL.'/'.$user['profile_pic']; ?>">
                                        </div>
                                        <h3><?php echo $user['full_name']; ?></h3>
                                        <p class="active">Active Today</p>
                                    </div>
                                    <div class="col-sm-8 member_info">
                                        <h3><?php echo $user['school_name']; ?></h3>
                                        <p><?php echo $user['school_address'];  ?></p>
                                        <p><?php echo $user['city_name'].', '. $user['state_name'].', '.$user['country_name'];  ?></p>
                                    </div>
                                </div>
                            </div>
                            <?php
                        }
                        ?>
                        <div class="col-sm-12 text-center accept-bar">
                        	<form name="accept" action="" method="POST">
                                <?php 
                                    $disabled = '';
                                    if(isset($disable_accept_button)  && $disable_accept_button == true){
                                        $disabled = 'disabled';
                                    } 
                                ?>
                                <button class="btn btn_green" <?php echo $disabled; ?> >I accept Group</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--footer-->
            <footer class="row reg_footer">
                <div class="col-sm-12">
                    <p>©2015 ISM. All Rights Reserved.</p>
                </div>
            </footer>
		</div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="assets/js/jquery-1.11.3.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="assets/js/bootstrap.min.js"></script> 
        <!--scroll-->
        <script src="assets/js/jquery.mCustomScrollbar.concat.min.js"></script>
         <?php flashMessage($this->session->flashdata('success'),$this->session->flashdata('error')); ?>
         <script>setTimeout(
              function() 
              {  $(".alert-dismissible").hide(500);
              }, 3000);
         </script>
    </body>
</html>
