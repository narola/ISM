<!DOCTYPE html>
<html lang="en">
<head>
    <title><?php echo $title;?></title>
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
    <!-- <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'> -->
    <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"> -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="assets/images/graduate.png" sizes="32x32" />
    <!--<script>
        (function($){
            $(window).load(function(){
                $(".sidebar_right_container").mCustomScrollbar();
            });
        })(jQuery);
    </script>-->
    <script>var wp = "<?php echo $this->session->userdata('user')['id']; ?>";</script>
     <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script> 
    <!--scroll-->
    <script src="assets/js/jquery.mCustomScrollbar.concat.min.js"></script>
     <?php flashMessage($this->session->flashdata('success'),$this->session->flashdata('error')); ?>
    <script>setTimeout(
              function(){
               $(".alert-dismissible").hide(500);
              }, 3000);
        $(document).ready(function(){
            $('.chat_text').mCustomScrollbar("scrollTo","bottom");    
        })
        
    </script>
    <script src="assets/js/jquery.cookie.js"></script>s
    <script src="assets/js/ws.js"></script>
    <script>
        // set_status(7,true);
     

    </script>
</head>

<body>
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><img src="assets/images/site_logo.png"></a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li class="active"><a href="Home.html"><span class="icon icon_menu_home"></span> Home</a></li>
            <li><a href="Tutorial.html"><span class="icon icon_menu_tut"></span> Tutorial</a></li>
            <li><a href="#"><span class="icon icon_menu_class"></span> Classroom</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_assess"></span> Assessment</a>
              <ul class="dropdown-menu">
                <li><a href="#">My Assessment</a></li>
                <li><a href="#">My Scoreboard</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-right">
            <li><a class="" href="#"><span class="icon icon_search"></span></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
    <!--body-->
    <div class="container-fluid">
        <div class="row">
            <!--side left-->
            <div class="sidebar_left_container text-center mCustomScrollbar" data-mcs-theme="minimal"><!-- scrollbar" id="style-3-->
                <div class="user_profile_img">
                    <img src="assets/images/user1.jpg">
                </div>
                <h4>Adam Ross</h4>
                <a href="student/user_account">View Profile</a>
                <!--notification-->
                <ul class="three_tabs">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_bell"></span><span class="badge bell_badge">03</span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">
                                <div class="user_small_img"><img src="assets/images/user2.jpg"></div>
                                <div class="notification_txt">
                                    <p><span class="noti_username">Adam Stranger</span> tagged you in a post</p>
                                    <span class="noti_time">1 hour ago</span>
                                </div>
                                <div class="clearfix"></div>
                            </a></li>
                            <li><a href="#">
                                <div class="user_small_img"><img src="assets/images/user3.jpg"></div>
                                <div class="notification_txt">
                                    <p><span class="noti_username">Matt Larner</span> sent you studymate request</p>
                                    <span class="noti_time">1 hour ago</span>
                                </div>
                                <div class="clearfix"></div>
                            </a></li>
                            <li><a href="#">
                                <div class="user_small_img"><img src="assets/images/user4.jpg"></div>
                                <div class="notification_txt">
                                    <p><span class="noti_username">Mary Watson</span> tagged you in a post</p>
                                    <span class="noti_time">1 hour ago</span>
                                </div>
                                <div class="clearfix"></div>
                            </a></li>
                            <li><a href="#">
                                <div class="user_small_img"><img src="assets/images/ISM.jpg"></div>
                                <div class="notification_txt">
                                    <p>Please check the published notice</p>
                                    <span class="noti_time">1 hour ago</span>
                                </div>
                                <div class="clearfix"></div>
                            </a></li>
                        </ul>
                    </li>
                    <li><a href="#"><span class="icon icon_message"></span><span class="badge message_badge">12</span></a></li>
                    <li><a href="#"><span class="icon icon_request"></span><span class="badge request_badge">07</span></a></li>
                </ul>
                <!--//notification-->
                <ul class="personal_menu">
                    <li><a href="My_feeds.html">My Feeds</a></li>
                    <li><a href="My_exam.html">My Exams</a></li>
                    <li><a href="#">Studymates</a></li>
                    <li><a href="#">My Activities</a></li>
                    <li><a href="#">Notice Board</a></li>
                </ul>
                <a href="login/logout" class="logout"><span class="icon icon_logout"></span>LogOut</a>
                <p class="copyright">©2015 ISM | All Rights Reserved.</p>
            </div>
            <!--//side left-->
                
            <?php echo $body; ?>



        </div>
    </div>

<div class="alert alert_notification alert-dismissible" role="alert" style="display: none;">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
      <p></p>
    </div>


    <!--//body-->
   
</body>
</html>
