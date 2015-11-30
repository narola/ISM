<?php
$url = uri_string();
?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>ISM Admin | <?php
            if (!empty($page_title)) {
                echo $page_title;
            }
            ?></title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <base href="<?php echo base_url(); ?>" >
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
        <!--custom css-->
        <link href="assets/css/bootstrap-switch.css" rel="stylesheet">
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
        <!-- Tagging Input CSS Start -->
        <link rel="stylesheet" href="assets/css/bootstrap-tagsinput.css">
        <!-- Tagging Input CSS END -->

        <link rel="stylesheet" href="assets/css/jquery-ui.css">
        <!--fonts-->
        <link href='assets/css/fonts.css' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/css/datepicker.css"> <!-- Bootstrap DatePicker CSS -->
        <link rel="stylesheet" href="assets/css/bootstrap-timepicker.min.css"> <!-- Bootstrap TimePicker CSS -->
    
        <script src="assets/js/jquery-1.11.3.min.js"></script>
        <script src="assets/js/jquery-ui.min.js"></script> <!-- jQuery UI JS -->
        <script src="assets/js/bootstrap.min.js"></script> <!-- Bootstap JS -->
        <script src="assets/js/select2.min.js"></script> <!-- Select2 JS -->
        <script src="assets/js/bootstrap-tagsinput.js"></script> <!-- Input Tagging Feature -->
        <script src="assets/js/bootstrap-datepicker.js"></script>  <!-- Bootstrap DatePicker Script  -->
        <script src="assets/js/bootstrap-timepicker.min.js"></script>  <!-- Bootstrap TimePicker Script  -->
        <script src="assets/js/bootbox.min.js"></script> <!-- Bootstrap Alert,Dialog Box Script Ref: http://bootboxjs.com/  -->
        <script src="assets/js/bootstrap-switch.js"></script>  <!-- Bootstrap Switch Script  -->
        <script src="assets/js/jquery.validate.js"></script>  <!-- Jquery Validate Plugin Script  -->

        <!-- Highchart JS Start -->
        <script src="http://code.highcharts.com/highcharts.js"></script>
        <script src="http://code.highcharts.com/modules/data.js"></script>
        <script src="http://code.highcharts.com/modules/drilldown.js"></script>
        <!-- Highchart JS END -->

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

                        <!-- ============================================================= -->
                        <?php /*
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_home"></span> Tutorial Group</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Groups</a></li>
                                <li><a href="#">Topics</a></li>
                                <li><a href="#">Performance</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_home"></span> Resources</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Books</a></li>
                                <li><a href="#">Badges</a></li>
                                <li><a href="#">Activities</a></li>
                                <li><a href="#">Questionnaires</a></li>
                                <li><a href="#">Banners</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_home"></span> Security</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Spam</a></li>
                                <li><a href="#">Word Watch</a></li>
                                <li><a href="#">Permissions</a></li>
                                <li><a href="#">Roles</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_home"></span> System</a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Notice board</a></li>
                                <li><a href="#">Feeds</a></li>
                                <li><a href="#">Features</a></li>
                            </ul>
                        </li>

                        <li><a href="#"><span class="icon icon_menu_home"></span> Examination</a></li>
                        <li><a href="#"><span class="icon icon_menu_home"></span> Reports</a></li>

                       */ ?>

                        <!-- ============================================================= -->
                        
                        <?php /* */ ?>
                        <li><a href="#"><span class="icon icon_menu_home"></span> Home</a></li>
                        <li class="
                        <?php
                        if (in_array($url, array('admin/exam', 'admin/exam/add','admin/question/set'))) {
                            echo 'active';
                        }
                        ?>
                            dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_assess"></span> Assessment</a>
                            <ul class="dropdown-menu">
                                <li <?php echo ($url == 'admin/question/set') ? 'class="active"' : ''; ?> ><a href="admin/question/set">Question Bank</a></li>
                                <li <?php echo ($url == 'admin/exam') ? 'class="active"' : ''; ?>><a href="admin/exam">Exams</a></li>
                            </ul>
                        </li>
                        <li class="<?php
                        if (in_array($url, array('admin/group'))) {
                            echo 'active';
                        }
                        ?>" ><a href="admin/group"><span class="icon icon_menu_group"></span> Groups</a></li>
                        <li><a href="admin/organize"><span class="icon icon_menu_organize"></span> Organize</a></li>
                        <li class="
                        <?php
                        if (in_array($url, array('admin/user', 'admin/topic/allocate',
                                    'admin/topic/lists', 'admin/school', 'admin/school/add',
                                    'admin/classroom', 'admin/classroom/add',
                                    'admin/subject/lists', 'admin/subject/add_subject','admin/question/add'
                                ))) {
                            echo 'active';
                        } else {

                            if (strlen(strstr($url, 'admin/user')) > 0) {
                                echo 'active';
                            }
                        }
                        ?> dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                <span class="icon icon_menu_manage"></span> Manage
                            </a>
                            <ul class="dropdown-menu">
                                <li <?php echo ($url == 'admin/book') ? 'class="active"' : ''; ?>><a href="admin/book">Book</a></li>
                                <li <?php echo ($url == 'admin/author') ? 'class="active"' : ''; ?>><a href="admin/author">Author</a></li>
                                <li <?php echo ($url == 'admin/school') ? 'class="active"' : ''; ?>><a href="admin/school">School</a></li>                                
                                <li class="<?php echo ($url == 'admin/subjects/lists' || $url == 'admin/subjects/add_subject' ) ? 'active' : ''; ?> dropdown sub_menu">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Subjects</a>
                                    <ul class="dropdown-menu">
                                        <li <?php echo ($url == 'admin/subjects/lists') ? 'class="active"' : ''; ?>><a href="admin/subject/lists">List of Subjects</a></li>
                                        <li <?php echo ($url == 'admin/subjects/add_subject') ? 'class="active"' : ''; ?>><a href="admin/subject/add_subject">Add New Subject</a></li>
                                    </ul>
                                </li>
                                <li class="<?php echo ($url == 'admin/course/lists' || $url == 'admin/course/add_course' ) ? 'active' : ''; ?> dropdown sub_menu">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Courses</a>
                                    <ul class="dropdown-menu">
                                        <li <?php echo ($url == 'admin/course/lists') ? 'class="active"' : ''; ?>><a href="admin/course/lists">List of Courses</a></li>
                                        <li <?php echo ($url == 'admin/course/add_course') ? 'class="active"' : ''; ?>><a href="admin/course/add_course">Add New Course</a></li>
                                    </ul>
                                </li>
                                <li <?php echo ($url == 'admin/classroom') ? 'class="active"' : ''; ?>><a href="admin/classroom">Classroom</a></li>
                                <li <?php echo ($url == 'admin/badges') ? 'class="active"' : ''; ?>><a href="admin/badges">Badges</a></li>
                                <li <?php echo ($url == 'admin/user') ? 'class="active"' : ''; ?>><a href="admin/user">User</a></li>
                                
                                <li class="dropdown sub_menu">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Question</a>
                                    <ul class="dropdown-menu">
                                        <li <?php echo ($url == 'admin/question/add') ? 'class="active"' : ''; ?>><a href="admin/question/add">Add Question</a></li>
                                    </ul>
                                </li>

                                <li class="<?php echo ($url == 'admin/topic/allocate' || $url == 'admin/topic/lists' || $url == 'admin/topic/add') ? 'active' : ''; ?> dropdown sub_menu">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Topics</a>
                                    <ul class="dropdown-menu">
                                        <li <?php echo ($url == 'admin/topic/allocate') ? 'class="active"' : ''; ?>><a href="admin/topic/allocate">Allocate Topics</a></li>
                                        <li <?php echo ($url == 'admin/topic/lists') ? 'class="active"' : ''; ?>><a href="admin/topic/lists">List of Topics</a></li>
                                        <li <?php echo ($url == 'admin/topic/add') ? 'class="active"' : ''; ?>><a href="admin/topic/add">Add New Topic</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                        <li class="<?php
                        if (in_array($url, array('admin/report'))) {
                            echo 'active';
                        }
                        ?>"><a href="admin/report"><span class="icon icon_menu_report"></span> Reports</a></li>


                       <?php /* */ ?>
                    </ul>
                    <!-- <ul class="nav navbar-nav navbar-right">
                        <li><a class="" href="#"><span class="icon icon_search"></span></a></li>
                        <li><a class="" href="#"><span class="icon icon_grid"></span></a></li>
                        <li><a class="" href="#"><span class="icon icon_list"></span></a></li>
                        <li><a class="" href="#"><span class="icon icon_refresh"></span></a></li>
                    </ul> -->
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
        <!--body-->
        <div class="container-fluid">
            <div class="row">
                <!--side left-->
                <div class="sidebar_left_container inner_pages inner_pages_admin text-center mCustomScrollbar" data-mcs-theme="minimal"><!-- scrollbar" id="style-3-->
                    <div class="user_profile_img">
                        <img src="<?php echo base_url().'uploads/admin/'.$this->session->userdata('profile_pic');?>" onerror="this.src='assets/images/avatar.png'">
                    </div>
                    <h4>Admin</h4>
                    <a href="admin/dashboard/profile_edit">View Profile</a>
                    <div class="clearfix"></div>
                    <!--notification-->
                    <ul class="three_tabs">
                        <li class="dropdown">
                            <a href="admin/notifications" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_bell"></span><span class="badge bell_badge">03</span></a>
                            <ul class="dropdown-menu">
                            	<div class="noti_inner mCustomScrollbar" data-mcs-theme="minimal-dark">
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
                                </div>
                            </ul>
                        </li>
                        <li><a href="admin/messages"><span class="icon icon_message"></span><span class="badge message_badge">12</span></a></li>

                    </ul>
                    <!--//notification-->

                    <ul class="personal_menu">
                        <li class="<?php
                            if (in_array($url, array('admin/feeds'))) {
                                echo 'active';
                            }
                        ?>">
                            <a href="admin/feeds"><span class="icon icon_feed"></span>Feeds</a>
                        </li><!-- class="active"-->
                        <li class="<?php
                            if (in_array($url, array('admin/spam'))) {
                                echo 'active';
                            }
                        ?>">
                            <a href="admin/spam"><span class="icon icon_spam"></span>Spam<span class="badge message_badge">12</span></a>
                        </li>
                        <li class="<?php
                            if (in_array($url, array('admin/banners'))) {
                                echo 'active';
                            }
                        ?>">
                            <a href="admin/banners"><span class="icon icon_banner"></span>Banners</a>
                        </li>                   
                        <li class="<?php
                            if (in_array($url, array('admin/notice', 'admin/notice/index'))) {
                                echo 'active';
                            }
                        ?>">
                            <a href="admin/notice"><span class="icon icon_notice"></span>Notice Board</a>
                        </li>
                        <li class="<?php
                            if (in_array($url, array('admin/questionaries'))) {
                                echo 'active';
                            }
                        ?>">
                            <a href="admin/questionaries"><span class="icon icon_ques"></span>Questionaries</a>
                        </li>
                        <li class="<?php
                        if (in_array($url, array('admin/auto_generated_credentials'))) {
                            echo 'active';
                        }
                        ?>" >
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
                        <?php $top_group = group_high_score();  
                        if(!empty($top_group)){
                            $i = 0;
                            foreach ($top_group as $k => $v) {
                                $i++;
                                ?>
                                <div class="score_item">
                                    <div class="score_img">
                                        <?php
                                         if(empty($v['group_profile_pic'])){
                                            $v['group_profile_pic'] = 'assets/images/avatar_group.png';
                                        }else{
                                            $v['group_profile_pic'] = UPLOAD_URL.'/'.$v['group_profile_pic'];
                                        }
                                         ?>
                                        <img src="<?php echo $v['group_profile_pic']; ?>">
                                    </div>
                                    <div class="score_descrip">
                                        <p class="score_name"><?php echo $v['group_name']; ?></p>
                                        <p><?php echo $v['class_name']; ?></p>
                                        <p>Score</p>
                                        <p class="score_number"><?php echo $v['total_score']; ?></p>
                                    </div>
                                    <div class="score_rank">
                                        <p><?php echo $i; ?><sup>st</sup></p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>

                                <?php
                            }
                        }
                        ?>
                    </div>
                    <!--//STM-->

                </div>
                <!--//side right-->
                <!--chat-->
                <!--//chat-->
            </div>
        </div>
        <!--//body-->

        <div class="alert alert_notification alert-dismissible" role="alert" style="display: none;">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
          <p></p>
        </div>
        
        <script type="text/javascript">
            // $(".alert_notification p").html("Exam time will <b>finish</b> within <b>1 minute.</b>");
            // $(".alert_notification").show().delay(5000).fadeOut();
         </script>    

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

        <!-- Include all compiled plugins (below), or include individual files as needed -->


        <script src="assets/js/jquery.mCustomScrollbar.concat.min.js"></script>


        <script type="text/javascript">

            $(function () {
                $('[data-toggle="popover"]').popover();
                $('[data-toggle="tooltip"]').tooltip();
            });


                //  jQuery(document).ready(function(){
            
                // jQuery('.exam_year .icon_option_dark').click(function(){
                //     if(jQuery(this).parent().children('.popover').css('display')=='block'){
                //         jQuery(this).parent().children('.popover').css('display','none');
                //     }
                //     else{
                //         jQuery(this).parent().children('.popover').css('display','block');
                //     };
                // });

                // jQuery('.switch_btns .btn').click(function(){
                //     if(jQuery(this).hasClass('no_btn')){
                //         jQuery('.switch_btns .btn').removeClass('btn_red');                 
                //         jQuery('.switch_btns .btn').addClass('no_btn');
                //         jQuery(this).addClass('btn_red');
                //         jQuery(this).removeClass('no_btn');                 
                //     };
                // });

              
            jQuery(document).ready(function () {
                jQuery('.exam_year .icon_option_dark').click(function () {
                    if (jQuery(this).parent().children('.popover').css('display') == 'block') {
                        jQuery(this).parent().children('.popover').css('display', 'none');
                    }
                    else {
                        jQuery(this).parent().children('.popover').css('display', 'block');
                    }
                    ;
                });
                jQuery('.switch_btns .btn').click(function () {
                    if (jQuery(this).hasClass('no_btn')) {
                        jQuery('.switch_btns .btn').removeClass('btn_red');
                        jQuery('.switch_btns .btn').addClass('no_btn');
                        jQuery(this).addClass('btn_red');
                        jQuery(this).removeClass('no_btn');
                    }
                    ;
                });
            });

            $('#birthdate input').datepicker({
                format: 'yyyy-mm-dd'
            });

        </script>

    </body>
</html>
