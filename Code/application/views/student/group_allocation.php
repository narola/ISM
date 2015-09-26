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
                    <a href="login/logout"  class="btn btn_green" style="float:right;padding-left:26px;">
                        <span class="icon icon_logout"></span>
                    </a>
                </div>
            </div>
            <!--row2-->
            <div class="row group_profile">
            	<div class="col-sm-12 text-center">
                	<div class="user_container">
                        <div class="user_profile_img">
                            <img src="uploads/<?php echo $this->session->userdata('user')['profile_pic'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">


                        </div>
                        <div class="user_profile_info">
                            <h1>Welcome To ISM</h1>
                            <h2><?php echo $this->session->userdata('user')['first_name'].' '.$this->session->userdata('user')['last_name']; ?></h2>
                            <h4><?php echo $this->session->userdata('user')['school_name'];?></h4>
                            <h4><?php echo $this->session->userdata('user')['academic_year'].', '.$this->session->userdata('user')['course_name'];?></h4>
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
                        <?php
                        for ($i=0; $i < 4; $i++) { 
                        ?>  
                        <div class="col-sm-6">
                            <div class="col-sm-12 group_member">
                                <div class="col-sm-4 member text-center">
                                    <div class="user_profile_img">
                                        <img src="<?php echo base_url()?>uploads/<?php echo isset($users[$i]['profile_pic'])?$users[$i]['profile_pic']:''?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                    </div>
                                    <h3><?php echo isset($users[$i]['full_name'])?$users[$i]['full_name']:'';?></h3>
                                    <p class="active">Member Since 2 Weeks</p>
                                </div>
                                <div class="col-sm-8 member_info">
                                    <h3><?php echo isset($users[$i]['school_name'])?$users[$i]['school_name']:'';?></h3>
                                    <p><?php echo isset($users[$i]['school_address'])?$users[$i]['school_address']:'';?></p>
                                    <p>
                                        <?php echo isset($users[$i]['city_name'])?$users[$i]['city_name'].',':'';?>
                                        <?php echo isset($users[$i]['state_name'])?$users[$i]['state_name'].',':'';?>
                                        <?php echo isset($users[$i]['country_name'])?$users[$i]['country_name']:'';?>
                                    </p>
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
