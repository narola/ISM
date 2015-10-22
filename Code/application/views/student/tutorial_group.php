<div class="col-sm-7 main main_tut">
    <?php 	if(isset($topic) && !empty($topic)){ ?>
            <div class="fixed_comment">
                <div class="row discussion_header">
                	<div class="col-sm-12">
                        <h3><span>Topic - </span><?php echo $topic['topic_name']; ?></h3>
                        <a href="#" class="icon icon_expand"></a>
                    </div>
                </div>
                <div class="row top_question">
                	<div class="col-sm-12">
                    	<div class="mate_user_img">
                        	<img src="/<?php echo UPLOAD_URL.'/'.$topic['profile_link']; ?>">
                        </div>
                        <div class="admin_question">
                        	<h4><?php echo $topic['full_name']; ?><span><?php echo date_format( date_create($topic['created_date']), 'M d, Y g:i a'); ?></span></h4>
                        	<p><?php echo $topic['topic_description']; ?></p>
                        </div>
                    </div>
                </div>
            </div>
                <div class="row discussion">
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
                                <img src="/<?php echo UPLOAD_URL.'/'.$v['profile_link']; ?>">
                                </div>
                                <div class="admin_question">
                                    <h4><?php echo $v['full_name']; ?><span><?php echo date_format( date_create($v['created_date']), 'M d, Y g:i a'); ?></span></h4>
                                    <p><?php 
                                      $check_type = array(
                                        'image/png',
                                        'image/jpg',
                                        'image/jpeg',
                                        'image/gif'
                                    );
                                      if($v['message'] == '' || $v['message'] == null){
                                        if (in_array($v['media_type'], $check_type)) {
                                     echo '<a href="uploads/' . $v['media_link'] . '"  target="_BLANK"><img src="uploads/' . $v['media_link'] . '" width="50" height="50"></a>';
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
                <?php //if($current_weekday <= 3){ ?>           
                <div class="row">
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
                    	<textarea placeholder="SAY IT" data-type="discussion"></textarea>
                        <a href="#" class="icon icon_emoji"></a>
                                             
                    </div>
                    <!--//input-->
                </div>
                <?php 
              //  }
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
                <div class="calculator row " id="accordian_calc">
                </div>
                <!--white board-->
                <div class="white_board">
                    <ul class="board_tools">
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
                    </ul>
                </div>
                <!--//white board-->
                <!--Explore-->
                <div class="explore">
                    <input class="form-control" type="text" placeholder="Search Dictionary.." data-type="Explore.." name="q">
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
                </div>
                <!--//Explore-->
                <!--dictionary-->
                <div class="dictionary">
                    <div class="dictionary_div">
                        <div class="form-group no_effect search_input">
                            <input class="form-control" type="text" placeholder="Search Dictionary.." data-type="search-dictionary">
                            <a href="javascript:void(0);" class="fa fa-search" data-type="search-dictionary"></a>
                        </div>
                        <div class="dictionary_result mCustomScrollbar" data-mcs-theme="minimal-dark">
                        sdfsd
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
            <div class="sidebar_right_container sidebar_right_container2 tut_right_bar mCustomScrollbar" data-mcs-theme="minimal-dark">
                <div class="col-sm-12 time_chart">
                    <div class="box">
                        <div class="box_body">
                            <div class="item_chart css">
                                <h2 id="time_counter">00:00:00</h2>
                                <span id="remain_id" style="display:none">Remaining</span>
                                <div id="circle_process"></div>
                                <h4 class="group_score">Group Score : <span id="group_score_count" ><?php echo $topic['group_score']; ?></span></h4>
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
                            <h5>My Score : <span id="my_score_count" ><?php echo $topic['my_score']; ?></span></h5>
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
                                <img src="/<?php echo UPLOAD_URL.'/'.$v['profile_link']; ?>">
                            </div>
                            <h4><?php echo $v['full_name']; ?></h4>
                            <p><?php echo $v['school_name']; ?></p>
                            <a href="#" class="icon <?php echo in_array($v['id'], $online)? 'icon_call_user_disable': 'icon_call_user'; ?>"></a>
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
</script>
