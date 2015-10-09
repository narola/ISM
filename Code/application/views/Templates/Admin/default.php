<?php
    $url = uri_string();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM Admin | <?php if(!empty($page_title)){ echo $page_title; } ?></title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="<?php echo base_url();?>" >
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <!--custom css-->
    <link href="assets/css/ism_style.css" rel="stylesheet">
    <link href="assets/css/ism_admin_style.css" rel="stylesheet">
    <link href="assets/css/responsive.css" rel="stylesheet">
    <link href="assets/css/icon.css" rel="stylesheet">
    <!--scroll-->
    <link href="assets/css/jquery.mCustomScrollbar.css" rel="stylesheet">
    <!-- Select2 CSS Start -->
    <link href="assets/css/select2-bootstrap.css" rel="stylesheet">    
    <link href="assets/css/select2.css" rel="stylesheet">
    <!-- Select2 CSS END -->

    <link rel="stylesheet" href="assets/css/jquery-ui.css">
    <!--fonts-->
    <link href='assets/css/fonts.css' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">

    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <script src="assets/js/jquery-ui.min.js"></script> <!-- jQuery UI JS -->
    <script src="assets/js/bootstrap.min.js"></script> <!-- Bootstap JS -->
    <script src="assets/js/select2.min.js"></script> <!-- Select2 JS -->
    <script src="assets/js/ZeroClipboard.min.js"></script>  <!-- For Copy to Clipboard Functionality Refer:http://zeroclipboard.org/ -->

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="assets/images/graduate_admin.png" sizes="32x32" />
</head>
<body>
    <nav class="navbar navbar-default navbar-fixed-top navbar_admin">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><img src="assets/images/site_admin_logo.png"></a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><a href="#"><span class="icon icon_menu_home"></span> Home</a></li>
            <li class="dropdown">
            	<a href="Tutorial.html" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_assess"></span> Assessment</a>
                <ul class="dropdown-menu">
                    <li><a href="#">Question Bank</a></li>
                    <li><a href="#">Exams</a></li>
                </ul>
            </li>
            <li class="<?php if(in_array($url,array('admin/group'))){ echo 'active'; }?>" ><a href="admin/group"><span class="icon icon_menu_group"></span> Groups</a></li>
            <li><a href="#"><span class="icon icon_menu_organize"></span> Organize</a></li>
            <li class="
                <?php 
                    if(in_array($url,array('admin/user','admin/topic/allocate','admin/topic/lists')))
                        { 
                            echo 'active'; 
                        }else{

                           if (strlen(strstr($url,'admin/user'))>0) {
                                echo 'active';
                            }
                        }
                    ?> dropdown">
               <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                    <span class="icon icon_menu_manage"></span> Manage
               </a>
              <ul class="dropdown-menu">
                <li><a href="#">Book</a></li>
                <li><a href="#">Auther</a></li>
                <li><a href="#">School</a></li>
                <li><a href="#">Classroom</a></li>
                <li><a href="#">Subject</a></li>
                <li><a href="#">Badges</a></li>
                <li><a href="admin/user">User</a></li>
                <li class="dropdown sub_menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Topics</a>
                    <ul class="dropdown-menu">
                        <li><a href="admin/topic/allocate">Allocate Topics</a></li>
                        <li><a href="admin/topic/lists">List of Topics</a></li>
                        <li><a href="#">Add New Topic</a></li>
                    </ul>
                </li>
              </ul>
            </li>
            <li><a href="#"><span class="icon icon_menu_report"></span> Reports</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
          	<li><a class="" href="#"><span class="icon icon_search"></span></a></li>
            <li><a class="" href="#"><span class="icon icon_grid"></span></a></li>
            <li><a class="" href="#"><span class="icon icon_list"></span></a></li>
            <li><a class="" href="#"><span class="icon icon_refresh"></span></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
    <!--body-->
    <div class="container-fluid">
    	<div class="row">
        	<!--side left-->
            <div class="sidebar_left_container inner_pages inner_pages_admin text-center mCustomScrollbar" data-mcs-theme="minimal"><!-- scrollbar" id="style-3-->
                <div class="user_profile_img">
                    <img src="assets/images/user1.jpg">
                </div>
                <h4>Adam Ross</h4>
                <a href="#">View Profile</a>
                <div class="clearfix"></div>
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
                    
                </ul>
                <!--//notification-->

				<ul class="personal_menu">
                	<li>
                        <a href="#"><span class="icon icon_feed"></span>Feeds</a>
                    </li><!-- class="active"-->
                	<li>
                        <a href="#"><span class="icon icon_spam"></span>Spam<span class="badge message_badge">12</span></a>
                    </li>
                	<li>
                        <a href="#"><span class="icon icon_banner"></span>Banners</a>
                    </li>                	
                	<li class="<?php if(in_array($url,array('admin/notice'))){ echo 'active'; }?>">
                        <a href="admin/notice"><span class="icon icon_notice"></span>Notice Board</a>
                    </li>
                    <li>
                        <a href="#"><span class="icon icon_ques"></span>Questionaries</a>
                    </li>
                    <li class="<?php if(in_array($url,array('admin/auto_generated_credentials'))){ echo 'active'; }?>" >
                        <a href="admin/auto_generated_credentials"><span class="icon icon_credencial"></span>Generate Credentials</a>
                    </li>
                </ul>
                <a href="admin/logout" class="logout"><span class="icon icon_logout"></span>LogOut</a>
                <p class="copyright">©2015 ISM | All Rights Reserved.</p>
            </div>
            <!--//side left-->
               

               <?php echo $body; ?> 
            
            
            <!--side right-->
            <div class="sidebar_right_container sidebar_right_container2 sidebar_right_admin mCustomScrollbar" data-mcs-theme="minimal-dark"><!--scrollbar" id="style-3-->
            	
                <!--high score board-->
                <div class="score box">
                	<div class="box_header">
                    	<h3>High Scores</h3>
                    </div>
                    <div class="score_div">
                    	<h5>Science</h5>
                        <!--item1-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/user5.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Adam Stranger</p>
                                <p>St. Xeviers F.Y. CS</p>
                                <p>Score</p>
                                <p class="score_number">500</p>
                            </div>
                            <div class="score_rank">
                            	<p>1<sup>st</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--item2-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/user3.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Matt Larner</p>
                                <p>St. Mary F.Y. CS</p>
                                <p>Score</p>
                                <p class="score_number">487</p>
                            </div>
                            <div class="score_rank">
                            	<p>2<sup>nd</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                       
                    </div>
                    <div class="score_div">
                    	<h5>Arts</h5>
                        <!--item1-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/user5.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Adam Stranger</p>
                                <p>St. Xeviers F.Y. CS</p>
                            	<p>Score</p>
                                <p class="score_number">500</p>
                            </div>
                            <div class="score_rank">
                            	<p>1<sup>st</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--item2-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/user3.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Matt Larner</p>
                                <p>St. Mary F.Y. CS</p>
                                <p>Score</p>
                                <p class="score_number">487</p>
                            </div>
                            <div class="score_rank">
                            	<p>2<sup>nd</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!--//high score board-->
                <!--Suggested Studymates-->                
                <!--//suggested Studymates-->
                <!--STM-->
                <div class="stm">
                	<div class="box_header">
                    	<h3>Top Groups</h3>
                    </div>
                    <!--item1-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/group1.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Venice Beauty</p>
                                <p>St. Xeviers F.Y. CS</p>
                            	<p>Score</p>
                                <p class="score_number">5000</p>
                            </div>
                            <div class="score_rank">
                            	<p>1<sup>st</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--item2-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/group2.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Happy Club</p>
                                <p>St. Mary F.Y. CS</p>
                            	<p>Score</p>
                                <p class="score_number">4870</p>
                            </div>
                            <div class="score_rank">
                            	<p>2<sup>nd</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--item1-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/group3.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Rankers</p>
                                <p>St. Xeviers F.Y. CS</p>
                            	<p>Score</p>
                                <p class="score_number">4700</p>
                            </div>
                            <div class="score_rank">
                            	<p>3<sup>rd</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--item2-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/avatar_group.png">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Allrounders</p>
                                <p>St. Mary F.Y. CS</p>
                            	<p>Score</p>
                                <p class="score_number">4690</p>
                            </div>
                            <div class="score_rank">
                            	<p>4<sup>th</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--item2-->
                        <div class="score_item">
                        	<div class="score_img">
                            	<img src="assets/images/group1.jpg">
                            </div>
                            <div class="score_descrip">
                            	<p class="score_name">Cool Group</p>
                                <p>St. Mary F.Y. CS</p>
                            	<p>Score</p>
                                <p class="score_number">4650</p>
                            </div>
                            <div class="score_rank">
                            	<p>5<sup>th</sup></p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                                
                </div>
                <!--//STM-->
                
            </div>
            <!--//side right-->
            <!--chat-->
            <!--//chat-->
        </div>
    </div>
    <!--//body-->

    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
   
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    

    <script src="assets/js/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="assets/js/bootstrap-datepicker.js"></script> 

    <script type="text/javascript">
        
        $('#birthdate input').datepicker({
            format: 'yyyy-mm-dd'
        });

        $(function () {
          $('[data-toggle="tooltip"]').tooltip()
        })
    </script>

</body>
</html>
