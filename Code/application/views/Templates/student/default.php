<!DOCTYPE html>
<html lang="en">
<head>
    <title><?php if(isset($title)) echo $title;?></title>
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
    <link href='assets/css/fonts.css' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
      <!-- Select2 CSS Start -->
    <link href="assets/css/select2-bootstrap.css" rel="stylesheet">    
    <link href="assets/css/select2.css" rel="stylesheet">

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
    <script>
    var wp = "<?php echo $this->session->userdata('user')['id']; ?>";
    var start_timer = false;
    var is_exam_finished = false;
    <?php if(isset($exam_status) && $exam_status == 2){
        ?>
        is_exam_finished = true;
        <?php
    }  ?>


/* Convert Seconds into toHHMMSS */
function toHHMMSS (sec) {

    var sec_num = parseInt(sec, 10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}

    var time  = hours+':'+minutes+':'+seconds;
    return time;
}
    </script>
     <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script> 
    <script src="assets/js/circle-progress.js"></script> 
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
    <script src="assets/js/jquery.cookie.js"></script>
    <script src="assets/js/ws.js"></script>
    <script>
        /*----show all comment[student]----*/
        function showall(id){
            $('.post'+id).show();
        }
    </script>
    <script>
        /*---noticeboard filter[student]---*/
        function search_noticeboard () {
                
            txt_value = $('#txt_search').val();
            if(txt_value != ''){
                $('#frm_notice_board').submit();
            }
            sort_value = $('#sort_by').val();
            if(sort_value != ''){
                $('#frm_notice_board').submit();
            }
            else{
                return false;
            }
        }
</script>
    <script src="assets/js/select2.min.js"></script> <!-- Select2 JS -->

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
            <li class="active"><a href="/student/home"><span class="icon icon_menu_home"></span> Home</a></li>
            <li><a href="student/tutorial"><span class="icon icon_menu_tut"></span> Tutorial</a></li>
            <li><a href="#"><span class="icon icon_menu_class"></span> Classroom</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_assess"></span> Assessment</a>
              <ul class="dropdown-menu">
                <li><a href="/student/my_exam">My Exam</a></li>
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
                    <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$this->session->userdata['user']['profile_pic'];?>">
                </div>
                <h4><?php echo $this->session->userdata['user']['full_name'];?></h4>
                <a href="student/user_account">View Profile</a>
                <!--notification-->
                <ul class="three_tabs">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_bell"></span>
                        <?php $noti_cnt = count_notification_list($this->session->userdata('user')['id']);
                            //if($noti_cnt > 0){
                        ?>
                            <span class="badge bell_badge"><?php echo $noti_cnt;?></span>
                        <?php //} ?>
                        </a>
                        <ul class="dropdown-menu" id="notification-panel">
                            <?php 
                                $notification_list = notification_list($this->session->userdata('user')['id']);
                                if(sizeof($notification_list) > 0 ){
                                    foreach ($notification_list as $key => $value) {
                                        ?>
                                        <li>
                                            <a href="#">
                                            <div class="user_small_img">
                                                <img onerror="this.src='assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                                            </div>
                                            <div class="notification_txt">
                                                <p><span class="noti_username"><?php echo $value['full_name']?></span> tagged you in a post</p>
                                                <span class="noti_time">1 hour ago</span>
                                            </div>
                                            <div class="clearfix"></div>
                                            </a>
                                        </li>
                                        <?php
                                    }
                                }
                                else{
                                    ?>
                                    <li id="no-more-notification">
                                        <div class="notification_txt">
                                            No more notification
                                        </div>
                                    </li>
                                    <?php
                                }
                            ?>
                        </ul>
                    </li>
                    <li><a href="#">
                        <span class="icon icon_message"></span>
                        <span class="badge message_badge" id="my_message_cnt">0</span></a></li>
                    <li><a href="/student/studymates_request"><span class="icon icon_request"></span>
                        <?php 
                            $request_cnt = count_studymate_request($this->session->userdata('user')['id']);
                            //if($request_cnt > 0){
                        ?>
                            <span class="badge request_badge" id="study_request_cnt"><?php echo $request_cnt;?></span>
                        <?php //} ?>
                        </a>
                    </li>
                </ul>
                <!--//notification-->
                    
                <?php 
                if(isset($menu)){
                    if($menu == 'week'){
                    ?>
                    <div class="tut_week">
                        <div class="tut_week_heading">
                            <p>Week 1</p>                   
                        </div>
                        <ul class="tut_weekdays">
                        <?php
                            foreach ($weekday as $key => $value) {
                            if($key <= 5 ){
                                $url = 'javascript:void(0);';
                                $active = '';
                               if($key+1 <= $current_weekday){
                                 $url = '#'.$value;
                               }else{

                               }
                               if($key+1 == $current_weekday){
                                $active = 'class="active"';
                               }
                               
                               echo '<li><a href="'.$url.'" '.$active.'>'.$value.'</a></li>';
                                }
                            }

                         ?>


                           
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                <?php
                    }
                }
                else{
                ?>
                 <ul class="personal_menu">
                    <li><a href="/student/home">My Feeds</a></li>
                    <li><a href="/student/my_exam">My Exams</a></li>
                    <li><a href="/student/studymates">Studymates</a></li>
                    <li><a href="/student/my_activities">My Activities</a></li>
                    <li><a href="/student/notice_board">Notice Board</a></li>
                </ul>
                <?php
                    } 
                ?>

                <a href="login/logout" class="logout"><span class="icon icon_logout"></span>LogOut</a>
                <p class="copyright">©2015 ISM | All Rights Reserved.</p>
            </div>
            <!--//side left-->
            <?php echo $body; ?>

            <?php if(!isset($hide_right_bar)){  ?>
            <!-- Right Bar -->
             <div class="sidebar_right_container sidebar_right_container2 mCustomScrollbar" data-mcs-theme="minimal-dark"><!--scrollbar" id="style-3-->
                
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
                                <p>St. Xeviers</p>
                                <p>F.Y. CS</p>
                            </div>
                            <div class="score_points">
                                <p>Score</p>
                                <p class="score_number">500</p>
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
                                <p>St. Mary</p>
                                <p>F.Y. CS</p>
                            </div>
                            <div class="score_points">
                                <p>Score</p>
                                <p class="score_number">487</p>
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
                                <p>St. Xeviers</p>
                                <p>F.Y. CS</p>
                            </div>
                            <div class="score_points">
                                <p>Score</p>
                                <p class="score_number">500</p>
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
                                <p>St. Mary</p>
                                <p>F.Y. CS</p>
                            </div>
                            <div class="score_points">
                                <p>Score</p>
                                <p class="score_number">487</p>
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
                        <h3>Studymates <span> STM</span></h3>
                    </div>
                    <div class="stm_list mCustomScrollbar" data-mcs-theme="minimal-dark">
                        <?php
                        $class = studymates_info();
                       // p($classmates,true);
                        if(isset($class) > 0){
                            foreach($class as $key => $value){
                                $u = 'offline';
                                
                                ?>
                                <div class="stm_item <?php echo $u; ?>" data-id="<?php echo $value['id']; ?>">
                                    <a id="mate_list" href="javascript:void(0);" data-id="<?php echo $value['id']; ?>">

                                    <div class="stm_user_img">
                                        <img src="/<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" >
                                    </div>
                                    <span class="badge message_badge"><?php if($value['unread_msg'] > 0) echo $value['unread_msg']; ?></span>
                                    <p><?php echo $value['full_name']; ?></p>
                                    </a>
                                    <div class="clearfix"></div>
                                </div>
                                <?php 
                            }  
                        }?>
                     </div>       
                     <div class="text-center">
                        <button class="btn_find_studymates btn btn_blue">Find more Studymates</button>
                    </div>            
                </div>
                <!--//STM-->
                
            </div>
                <!--//STM-->
                
            </div>
            <!-- //Right Bar -->
            <?php  } ?>
        </div>
    </div>

<div class="alert alert_notification alert-dismissible" role="alert" style="display: none;">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
      <p></p>
    </div>

<!--chat-->
            <div class="chat_container" id = 'chat_container'>
                 <?php 
                 $active_c = active_chat();
                 if(isset($active_c) && !empty($active_c) && $this->session->userdata('user')['id'] != get_cookie('active') && in_array( get_cookie('active'), studymates( $this->session->userdata('user')['id']))){
                ?>
                <div class="chat active" data-id="<?php echo $active_c['user']['id'] ?>">
                    <div class="chat_header">
                        <div class="chat_img_holder">
                            <img src="<?php echo UPLOAD_URL.'/'.$active_c['user']['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'"></div>
                            <p class="chat_name"><?php echo $active_c['user']['full_name'] ?></p>
                            <a href="#">
                                <span class="icon icon_option"></span>
                            </a>
                        </div>
                        <div class="chat_text mCustomScrollbar" data-mcs-position="bottom">
                            <?php
                            if(isset($active_c['comment'] ) && !empty($active_c['comment'] )){
                                $check_type = array(
                                    'image/png',
                                    'image/jpg',
                                    'image/jpeg',
                                    'image/gif'
                                    );
                            foreach($active_c['comment'] as $value){
                                $mess = $value['message'];
                            
                            if($mess == null || $mess == ''){
                                if(in_array($value['media_type'], $check_type)){
                                $mess = '<a href="uploads/'.$value['media_link'].'"  target="_BLANK"><img src="uploads/'.$value['media_link'].'" width="50" height="50" /></a>';
                                }else{
                                $mess = '<a href="uploads/'.$value['media_link'].'"  target="_BLANK"><img src="assets/images/default_chat.png" width="50" height="50" /></a>';
                                }
                            }
                                if($value['sender_id'] == $active_c['user']['id']){
                            ?>
                                <div class="from"><p><?php echo  $mess; ?></p></div>
                            <?php
                                }else{
                            ?>
                                <div class="to"><p><?php echo  $mess; ?></p></div>
                            <?php
                                }
                            }
                        }
                             ?>

                        </div>
                        <img class="chat_loading" src="assets/images/progress_bar_sm.gif" style="display:none">
                        <input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="<?php echo $active_c['user']['id'] ?>">
                        <a href="#" class="icon icon_emoji"></a>
                        <a href="#" class="icon icon_pin"></a>
                        <input type="file" id="chat_file_share" class="chat_pin" data-type="single_chat_file" data-id="<?php echo $active_c['user']['id'] ?>">
                    </div>
                <?php } ?>
            </div>
            <!--//chat-->

<?php if(isset($menu) && $menu == 'week'){ ?>
<script type="text/javascript">
        jQuery(document).ready(function() {
            $('.calc_header a').click(function(){
                if(!$('.calc_header a').hasClass('collapsed')){
                    $('.calc_header a span').removeClass('fa-angle-up');    
                    $('.calc_header a span').addClass('fa-angle-down');
                }
                else{
                    $('.calc_header a span').addClass('fa-angle-up');
                    $('.calc_header a span').removeClass('fa-angle-down');
                };
            });
            
            
            $('#calc_tab').click(function(){
                $(this).addClass('active');
                $('#white_board_tab, #explore_tab, #dictionary_tab').removeClass();
                $('.calculator').css('display','block');
                $('.white_board, .explore, .dictionary').css('display','none');
            });
            $('#white_board_tab').click(function(){
                $(this).addClass('active');
                $('#calc_tab, #explore_tab, #dictionary_tab').removeClass();
                $('.white_board').css('display','block');
                $('.calculator, .explore, .dictionary').css('display','none');
            });
            $('#explore_tab').click(function(){
                $(this).addClass('active');
                $('#white_board_tab, #calc_tab, #dictionary_tab').removeClass();
                $('.explore').css('display','block');
                $('.white_board, .calculator, .dictionary').css('display','none');
            });
            $('#dictionary_tab').click(function(){
                $(this).addClass('active');
                $('#white_board_tab, #calc_tab, #explore_tab').removeClass();
                $('.dictionary').css('display','block');
                $('.white_board, .calculator, .explore').css('display','none');
            });
            
            $('.board_tools>li>a').mouseover(function(){
                $(this).parent().css('background-color','#f2f2f2'); 
            });
            $('.board_tools>li>a').mouseout(function(){
                $(this).parent().css('background-color','#dedede'); 
            });
            
            $('.pen_size').mouseover(function(){
                $(this).parent().css('background-color','#f2f2f2');
            });
            $('.pen_size').mouseout(function(){
                $(this).parent().css('background-color','#dedede');
            });
            
        });
        
    </script>

<?php } ?>

    <!--//body-->
   
</body>
</html>
