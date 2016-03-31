<!--
<script type="text/javascript" src="assets/lib/jquery.1.10.2.min.js"></script> -->

<script type="text/javascript" src="assets/lib/jquery.ui.core.1.10.3.min.js"></script>

<script type="text/javascript" src="assets/lib/jquery.ui.widget.1.10.3.min.js"></script>
<script type="text/javascript" src="assets/lib/jquery.ui.mouse.1.10.3.min.js"></script>
<script type="text/javascript" src="assets/lib/jquery.ui.draggable.1.10.3.min.js"></script>


<link rel="Stylesheet" type="text/css" href="assets/lib/wColorPicker.min.css" />
<script type="text/javascript" src="assets/lib/wColorPicker.min.js"></script>


<link rel="Stylesheet" type="text/css" href="assets/css/wPaint.min.css" />
<script type="text/javascript" src="assets/js/wPaint.min.js"></script>
<script type="text/javascript" src="assets/plugins/main/wPaint.menu.main.min.js"></script>
<script type="text/javascript" src="assets/plugins/text/wPaint.menu.text.min.js"></script>
<script type="text/javascript" src="assets/plugins/shapes/wPaint.menu.main.shapes.min.js"></script>
<script type="text/javascript" src="assets/plugins/file/wPaint.menu.main.file.min.js"></script>
<style>
    
.wPaint-theme-classic .wPaint-menu-icon { float: left; height: 30px; margin: 0 3px !important; width: 30px;}
.wPaint-menu { left: 0 !important; position: relative !important; top: -130px !important; width: auto !important;}
.wPaint-menu-holder { height: auto; display: inline-block; padding: 8px ;}
.gsib_a{padding : 0px !important;}
.gsc-search-button{height: 23px;width: 30px;padding: 6px;}
</style>
<div class="col-sm-7 main main_tut tut_dis"><!--mCustomScrollbar" data-mcs-theme="minimal-dark-->
    <?php 	if(isset($topic) && !empty($topic)){ ?>
            <div class="fixed_comment">
                <div class="discussion_header">
                	<div class="col-sm-10 col-md-11">
                        <h3><span>Topic - </span><?php //echo $topic['topic_name']; ?></h3>

                        <!-- <a href="javascript:void(0);" class="icon icon_expand" id="expand_q"></a> -->

                       <!-- <a href="#" class="icon icon_expand"></a> -->

                    </div>
                    <div class="col-sm-1 col-md-1 notice_content">
                        <a href="javascript:void(0);" id="expand_topic" class="fa fa-angle-double-down"></a>                            
                    </div>
                </div>
                <div class="top_question">
                	<div class="col-sm-12">
                    	<div class="mate_user_img">
                            <?php 
                           /* $topic_img  =  UPLOAD_URL.'/'.$topic['profile_link'];
                                if($topic['profile_link'] == ''){
                                    $topic_img = 'assets/images/avatar.png';
                                }*/
                            ?>
                        	<img src="<?php //echo $topic_img ; ?>">
                        </div>
                        <div class="admin_question">
                        	<h4><?php //echo $topic['full_name']; ?><span><?php //echo date_format( date_create($topic['created_date']), 'M d, Y g:i a'); ?></span></h4>
                            <div style="max-height: 100px;" class="mscroll_custom"><p><?php //echo $topic['topic_description']; ?></p></div>
                        	<!-- <p><span><?php //echo $topic['topic_description']; ?></span></p> -->
                        </div>
                    </div>
                </div>
            </div>
                <div class="discussion" data-mcs-theme="minimal-dark">
                    <div id="inner_x">
                <?php 

                if(isset($discussion) && count($discussion) > 0){
                    $i = 0;
                    $week = null;
                    foreach($discussion as $k => $v){
                       $clss_me = '';
                       if($v['sender_id'] == $user_id)
                        $clss_me = 'me';
                      // if($v['week_day'] <= 2){
                            if($week !== $v['week_day']){
                                $week = $v['week_day'];
                            ?>
                            <div class="divide_discussion col-sm-12" id="<?php echo $weekday[$v['week_day']-1]; ?>">                    
                                <div class="clearfix"></div>                    
                                <hr><h4><?php echo $weekday[$v['week_day']-1]; ?></h4>
                            </div>
                            <?php
                            }
                            ?>
                            <div class="col-sm-12 <?php echo $clss_me; ?>" data-id="<?php echo $v['id']; ?>">
                                <div class="mate_user_img">
                                <?php 
                                $imgs = '/assets/images/avatar.png';
                                if($v['profile_link'] != ''){
                                    $imgs = UPLOAD_URL.'/'.$v['profile_link']; 
                                }

                                ?>

                                <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo $imgs; ?>">
                                </div>
                                <div class="admin_question">
                                    <h4><?php echo $v['full_name']; ?><span><?php echo date_format( date_create($v['created_date']), 'M d, Y, g:i a'); ?></span></h4>
                                    <p <?php echo ($v['is_active']==1) ? 'class="active"' : ''; ?>><?php 
                                      $check_type = array(
                                        'image/png',
                                        'image/jpg',
                                        'image/jpeg',
                                        'image/gif'
                                    );
                                      if($v['message'] == '' || $v['message'] == null){
                                        if (in_array($v['media_type'], $check_type)) {
                                     echo '<a href="uploads/' . $v['media_link'] . '"  class="fancybox"><img src="uploads/' . $v['media_link'] . '" width="50" height="50"></a>';
                                    } else {
                                        echo '<a href="uploads/' . $v['media_link'] . '"  target="_BLANK"><img src="assets/images/default_chat.png" width="50" height="50"></a>';
                                        }
                                      }else{
                                        echo $v['message'];
                                      }
                                
                                      ?>
                                </p>
                                </div>
                            </div>
                            <?php
                          //  }
                        $i++;
                    }
                }
                ?> 
                </div>
            </div>
                <?php 

                    // $current_weekday = 2;
                if($current_weekday <= 3){ ?>           
                
               		<!--input-->
                    <div class="col-sm-12 input">
                    	<?php $cls = ""; if($time > 0){ $cls = "style='display:none'";  }?>
                        <div id="time_over" class="alert alert-danger alert-dismissible" role="alert" <?php echo $cls; ?> >
                          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                          Active hours are finished!
                        </div>
                    	<div class="option_bar" data-type="discussion-submit">
                        	<a href="javascript:void(0);" class="icon icon_pin"><input id="group_file_share" type="file" data-type="topic_file" data-id="topic"></a>
                            <button class="btn btn_post">Post<span class="fa fa-chevron-right"></span></button>
                        </div> 
                    	<textarea placeholder="SAY IT" data-type="discussion" class="mscroll_custom"></textarea>
                        <div class="upload_loader" style="top:58px"></div>
                        <!-- <a href="#" class="icon icon_emoji"></a> -->
                                             
                    </div>
                    <!--//input-->
              
                <?php 
                }
                 }else{ 
?>
<h1>No topic allocated for this week!</h1>
<?php
                } ?>  
            </div>
            <div class="toolkit">
                <div class="row toolkit_header">
                    <div class="col-sm-12">
                        <h3><span>My Toolkit</span></h3>
                    </div>
                </div>

                <div class="calculator row" id="accordian_calc">
                     <iframe  height="575" src="http://web2.0calc.com/widgets/vertical/?options=%7B%22angular%22%3A%22deg%22%2C%22options%22%3A%22show%22%2C%22menu%22%3A%22show%22%7D" scrolling="no" style="border: 1px solid silver;position:absolute;clip:rect(37px,1100px,800px,0px);"> 
                    </iframe>

                </div>
                <!--white board-->
                <div class="white_board">
                   <!--  <ul class="board_tools">
                        <li class="paint"><a href="#" class="icon icon_paint"></a></li>
                        <li class="pen">
                            <a href="#" class="icon icon_pen"></a>
                            <ul class="pen_size">
                                <li><a href="#" class="s"></a></li>
                                <li><a href="#" class="m"></a></li>
                                <li><a href="#" class="l"></a></li>
                                <li><a href="#" class="xl"></a></li>
                            </ul>
                        </li>
                        <li class="erase"><a href="#" class="icon icon_erase"></a></li>
                        <li class="clear"><a href="#" class="icon icon_clear"></a></li>
                    </ul> -->
                    <div class="tab-pane active" id="red">                                  
                  
                        <div id="wPaint" style="position:relative; width:280px; height:300px; background-color:#00000;border:1px solid black; margin:140px auto 20px;"></div>
                        <div class="upload_loader_whiteboard" style="top: 160px"></div>

                        <!-- <center style="margin-bottom: 50px;">
                            <input type="button" value="toggle menu" onclick="console.log($('#wPaint').wPaint('menuOrientation')); $('#wPaint').wPaint('menuOrientation', $('#wPaint').wPaint('menuOrientation') === 'vertical' ? 'horizontal' : 'vertical');"/>
                        </center> -->

                    <center id="wPaint-img"></center>
                  
                    <script type="text/javascript">
                    /*var images = [
                      'test/uploads/wPaint.png',
                    ];
*/
                    

                    function loadImgBg () {

                      // internal function for displaying background images modal
                      // where images is an array of images (base64 or url path)
                      // NOTE: that if you can't see the bg image changing it's probably
                      // becasue the foregroud image is not transparent.
                      this._showFileModal('bg', images);
                    }

                    function loadImgFg () {

                      // internal function for displaying foreground images modal
                      // where images is an array of images (base64 or url path)
                      this._showFileModal('fg', images);
                    }

                    // init wPaint
                    $('#wPaint').wPaint({
                      menuOffsetLeft: -35,
                      menuOffsetTop: -50,
                      fillStyle: 'red',
                      strokeStyle: 'blue',   
                      saveImg: saveImg,
                      loadImgBg: loadImgBg,
                      loadImgFg: loadImgFg
                      
                    });
                  </script>
                  
                    </div>
                </div>
                <!--//white board-->
                <!--Explore-->
                <div class="explore">
                    
                    <script>
  (function() {
    var cx = '007609917801302826443:7ujqmpmhwx8';
    var gcse = document.createElement('script');
    gcse.type = 'text/javascript';
    gcse.async = true;
    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
        '//cse.google.com/cse.js?cx=' + cx;
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(gcse, s);
  })();
</script>


<gcse:search></gcse:search>

<script type="text/javascript">

    $( document ).ready(function() {
        $('.gsib_a').css("padding":"0px");
});
</script>
                </div>
                <!--//Explore-->
                <!--dictionary-->
                <div class="dictionary">
                    <div class="dictionary_div">
                        <div class="form-group no_effect search_input">
                            <input class="form-control" type="text" placeholder="Search Dictionary.." data-type="search-dictionary">
                            <a href="javascript:void(0);" class="fa fa-search" data-type="search-dictionary"></a>
                        </div>
                        <div class="dictionary_result mscroll_custom">
                        </div>
                    </div>
                </div>
                <!--//dictionary-->
                <!--tabs-->
                <div class="toolkit_tabs">
                    <ul>
                        <li id="calc_tab" class="active"><a href="javascript:void(0);">Calculator</a></li>
                        <li id="white_board_tab"><a href="javascript:void(0);">White board</a></li>
                        <li id="explore_tab"><a href="javascript:void(0);">Explore</a></li>
                        <li id="dictionary_tab"><a href="javascript:void(0);">Dictionary</a></li>
                    </ul>
                </div>
                <!--//tabs-->
            </div>
            <div class="sidebar_right_container sidebar_right_container2 tut_right_bar mscroll_custom">
                <div class="col-sm-12 time_chart">
                    <div class="box">
                        <div class="box_body">
                            <div class="item_chart css">
                                <h2 id="time_counter">00:00:00</h2>
                                <span id="remain_id" style="display:none">Remaining</span>
                                <div id="circle_process"></div>
                                <h4 class="group_score">Group Score : <span id="group_score_count" ><?php echo $topic[0]['group_score']; ?></span></h4>
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box_body">
                            <h5>Active Comments : <span id="active_comment_count"><?php echo $active_comment; ?></span></h5>
                        </div>
                    </div>                    
                    <div class="box">
                        <div class="box_body">
                            <h5>My Score : <span id="my_score_count" ><?php echo '0';//$topic['my_score']; ?></span></h5>
                        </div>
                    </div>
                    <!--group-->
                    <div class="box tut_group">
                        <div class="box_header">
                            <h4>Tutorial Group</h4>
                        </div>

                        <?php foreach($member as $k =>$v){
                        ?>
                            <div class="box_body" data-id="<?php echo $v['id']; ?>">
                            <div class="mate_user_img">
                                <?php 
                                $imgs = '/assets/images/avatar.png';
                                if($v['profile_link'] != ''){
                                    $imgs = UPLOAD_URL.'/'.$v['profile_link']; 
                                }

                                ?>
                                <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $v['id']; ?>" src="<?php echo $imgs; ?>">
                            </div>
                            <h4 style="cursor:pointer;" class="user_profile_hover" data-type="show-profile" data-id="<?php echo $v['id']; ?>"><?php echo $v['full_name']; ?></h4>
                            <p><?php echo $v['school_name']; ?></p>
                            <a data-toggle="tooltip" title="Notify" class="icon <?php echo in_array($v['id'], $online)? 'icon_call_user_disable': 'icon_call_user'; ?>"></a>
                        </div>
                        <div class="box_footer" data-id="<?php echo $v['id']; ?>">
                            <p><?php echo in_array($v['id'], $online)? 'Online': 'Offline'; ?></p>
                        </div>
                        <?php

                        }  ?>
                    </div>
                    <!--//group--> 
                </div>
            </div>

            <script>
var time_count = "<?php echo $time; ?>";
var max_count = "<?php echo $time; ?>";
start_timer = true;
var counter;
if(time_count > 0){
    counter = setInterval(timer, 1000);
}

function timer()
{
  time_count=time_count-1;
  $('#time_counter').html(toHHMMSS(time_count));
  if(time_count >= 0){
        anima = (((max_count - time_count) * 100)/max_count)/100;
        $('#circle_process').circleProgress('value', anima);
  }
  if (time_count <= 0)
  { 
   // $('#circle_process').circleProgress({ value: 0.0 });
ws.send('{"type":"time_start_request","from":"' + wp + '","to":"self","error":""}');

    clearInterval(counter);
    $("#time_over").fadeIn(300).delay(2000).fadeOut(300);
    $("#remain_id").fadeOut(300);
    return;

  }else if($("#remain_id").is(":hidden")){
    $("#remain_id").fadeIn(300);
  }
    
}


$(document).ready(function(){
   /* var h = $(window).height();
    var fh = $('.discussion_header').height();
    var ih = $('.input').height();
    var height = h - (fh + ih);
    $('.discussion').css('height',height) ;*/
    
    $(".top_question").hide();
    $('.notice_content #expand_topic').click(function(){
        $( ".top_question" ).slideToggle( "slow" );
        if($(this).hasClass('fa-angle-double-down')){
            $(this).removeClass('fa-angle-double-down');
            $(this).addClass('fa-angle-double-up');
        }else{
            $(this).removeClass('fa-angle-double-up');
            $(this).addClass('fa-angle-double-down');
        }
    });
});
</script>
