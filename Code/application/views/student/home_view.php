 <script>
    function showall(id){
        $('.post'+id).show();
    }
    $(document).ready(function () {
        $('#selection-box').css("display","none");
    });
    $(document).on('click','#show-tag-user',function(){
        val = $('#tag_or_not').val();
        if(val == 'yes'){
            $('#selection-box').css("display","none");
            $('#tag_or_not').val('no');  
        }
        else{
            $('#selection-box').css("display","block");
            $('#tag_or_not').val('yes');                
        }

    });

    $(document).on('click','a[data-type="tag-again"]',function(e){
        
        show = $('#all_feed .box div[data-id="'+$(this).data('id')+'"]').is(":visible"); 
        
        if(show)
            $('#all_feed .box div[data-id="'+$(this).data('id')+'"]').hide();
        else
            $('#all_feed .box div[data-id="'+$(this).data('id')+'"]').show();
    });

    $('#element').popover('show');

        
    $(document).on('click','[data-toggle="popover1"]',function(){
        $('[data-toggle="popover1"]').popover('show');
    });

    $(document).on('click','[data-toggle="popover2"]',function(){
        $('[data-toggle="popover2"]').popover('show');
    });

    $(document).ready(function(){
    $('[data-toggle="popover"]').popover();   
});

</script>
 <!--main-->
<div class="col-sm-7 main">
    <div class="box">
        <textarea id="feed_post" type="text" class="form-control post_input" placeholder="SAY IT"></textarea>
        <a href="#" class="icon icon_emoji"></a>
        <div class="form-group" id="tag-box">
            <div id="tagged-users" style="margin-bottom:10px;">
            </div>
            <input type="hidden" id="tagged-users-id">
            <input type="hidden" id="tag_or_not" value="">
            <div id="selection-box">
                <select name="all_users[]" id="select-tag-user" class="js-example-basic-single form-control" multiple="multiple">
                    <?php
                    if(!empty($my_studymates)) {
                        foreach($my_studymates as $list){
                         ?>
                            <option value="<?php echo $list['id'] ?>">
                                <?php echo ucfirst($list['full_name']); ?>
                            </option> 
                    <?php } } ?>
                </select>
            </div>
        </div>
        <div class="box_header">
            <a href="javascript:void(0);" class="icon icon_pin">
                <input  id="feed_file_share" type="file" data-id="feed">
            </a>
            <div class="dropdown" style="display: inline-block;">
                <a href="javascript:void(0);" id="show-tag-user" class="dropdown-toggle icon icon_user" aria-haspopup="true" aria-expanded="true"><span class="caret"></span></a>
            </div>
            <button data-type="post" class="btn btn_post">Post<span class="fa fa-chevron-right"></span></button>
        </div>
    </div>
    
    <!--feed box-->
    <div id="all_feed">
        <?php 
            if(isset($feed)){
                $j = 1;
                foreach ($feed as $key => $value) {
        ?>
                <div class="box feeds" data-id="<?php echo $value['fid'];?>">
                    <div class="user_small_img">
                        <img src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                    </div>
                    <div class="feed_text">
                        <h4><?php echo $value['full_name'];?></h4>
                        <span data-id="<?php echo $value['fid'];?>">
                            <?php
                                if(isset($value['tagged']) && sizeof($value['tagged'])>0){
                                    $t_j = 0; 
                                    $k = 0; 
                                    $t_i = 0; 
                                    $other_name ='';
                                    $t_count = count($value['tagged']);
                                    foreach ($value['tagged'] as $t_key => $t_value) {
                                        if($t_count == 1){
                                            echo '&nbsp;tagged <label class="label label_name">'.$t_value['full_name'].'</label>';
                                        }
                                        if($t_count == 2){
                                            if($t_j == 0){
                                                echo '&nbsp;tagged <label class="label label_name">'.$t_value['full_name'].'</label>';    
                                            }
                                            else{
                                                echo 'and <label class="label label_name">'.$t_value['full_name'].'</label>';
                                            } 
                                            $t_j++;  
                                        }
                                        if($t_count > 2){
                                            if($t_i == 0){
                                                echo '&nbsp;tagged <label class="label label_name">'.$t_value['full_name'].'</label>';   
                                            }
                                            else{
                                                $l = $t_count - 1;
                                                $other_name .= $t_value['full_name'].'<br>'; 
                                                if($k == $l){
                                                    echo 'and <label class="label label_name"><a href="javascript:void(0);" data-html="true" data-placement="bottom" data-trigger="focus" data-toggle="popover" title="Other Tagged" data-content="'.$other_name.'" onclick="$(\'[data-toggle="popover"]\').popover(\'show\');">'.$l.' more</a></label>';
                                                }
                                            }
                                            $k++;
                                            $t_i++;
                                        }
                                    }
                                }
                            ?>
                        </span>
                        <span class="date"><?php $old_date = strtotime($value['posted_on']);echo date("M j, Y",$old_date);?></span>
                        <div class="clearfix"></div>
                        <p><?php echo $value['feed_text'];?></p>
                        <a href="javascript:void(0);" data-id="<?php echo $value['fid'];?>" data-type="feed-like" class="like_btn">
                            <?php 
                                if($value['my_like'] == 1 || $value['my_like'] == ''){
                                ?>
                                    <span class="icon icon_thumb_0"></span>
                                <?php
                                }
                                else{
                                ?>
                                    <span class="icon icon_thumb"></span>
                                <?php
                                }
                            ?>    
                            <span><?php echo $value['tot_like'];?></span></a>
                        <a href="javascript:void(0);" class="comment_btn" data-id="<?php echo $value['fid'];?>"><span class="icon icon_comment"></span>
                            <span><?php echo $value['tot_comment'];?></span></a>
                        <a href="javascript:void(0);" onclick="showall(<?= $j; ?>);">View All</a>
                        <div class="dropdown tag_user" style="display: inline-block;">
                            <a href="javascript:void(0);" class="dropdown-toggle" data-type="tag-again" data-id="<?php echo $value['fid'];?>" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>
                        </div>
                    </div>
                    <div style="float:right;display:none;" id="show-again" data-id="<?php echo $value['fid'];?>">
                        <select style="width:200px;"name="all_users_again[]" id="select-tag-user-again" data-id="<?php echo $value['fid'];?>" class="js-example-basic-single form-control" multiple="multiple">
                            <?php
                            if(!empty($my_studymates)) {
                                foreach($my_studymates as $list){
                                 ?>
                                    <option value="<?php echo $list['id'] ?>">
                                        <?php echo ucfirst($list['full_name']); ?>
                                    </option> 
                            <?php } } ?>
                        </select>
                        <a href="javascript:void(0);" class="btn btn_black_normal" data-type="tag-user-again" data-id="<?php echo $value['fid'];?>">Tag New</a>
                    </div>
                    <div class="clearfix"></div>
                    <!--comment-->
                    <div id="feed_comments">
                    <?php 
                        if(sizeof($value['comment'])>0 && isset($value['comment'])){
                            $i = 1;
                            foreach ($value['comment'] as $key => $com) {
                                if($value['fid'] == $com['feed_id']){
                                    if($i > 3)
                                        $display = 'none';
                                    else
                                        $display = '';
                                ?>

                                <div class="comment <?= 'post'.$j;?>" style="display:<?= $display;?>">
                                    <div class="user_small_img user_comment">
                                        <img src="<?php echo UPLOAD_URL.'/'.$com['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                    </div>
                                    <div class="notification_txt">
                                        <p><a href="#" class="noti_username"><?php echo $com['full_name'];?></a> <?php echo $com['comment'];?></p>
                                        <span class="noti_time">1 Day</span>                            
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                
                                <?php
                                    $i++;
                                }
                            }
                        }
                    ?>
                    </div>
                    <div class="write_comment box_body">
                        <input type="text" class="form-control" placeholder="Write Your Comment Here" data-type="feed_comment" data-id="<?php echo $value['fid']; ?>">                  
                        <a class="icon icon_image"></a>
                        <input type="file">
                    </div>
                </div>
                <?php
                $j++;
                }
            }
        ?>
    </div>

    <!--//feed box-->
    
    <div class="box text-center load_more mate_descrip">
        <button href="javascript:void(0);" data-type="load_more" data-start="4" class="btn btn_green no-margin">Load More</button>
    </div>
</div>
<!--//main-->

<!--side right-->
<div class="sidebar_right_container mCustomScrollbar" data-mcs-theme="minimal-dark"><!--scrollbar" id="style-3-->
    
    
      <!-- --------------------Here you slider--------------------------- -->
    <!--notice board-->
    <div id="carousel-noticeboard" class="carousel slide" data-ride="carousel">
      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#carousel-noticeboard" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-noticeboard" data-slide-to="1"></li>
        <li data-target="#carousel-noticeboard" data-slide-to="2"></li>
      </ol>
<!-- Wrapper for slides -->
      <div class="carousel-inner" role="listbox">
        <?php 
            if(isset($my_latest_notice) && sizeof($my_latest_notice)>0){
                $i = 0;
                foreach ($my_latest_notice as $key => $value) {
                    if($i == 0){
                        echo '<div class="item active">';
                    }
                    else{
                        echo '<div class="item">';
                    }
        ?>
            <img src="<?php echo base_url();?>assets/images/blackboard.jpg" alt="blackboard">
                <div class="carousel-caption mCustomScrollbar" data-mcs-theme="minimal-dark">
                    <p class="noti_username" style="font-size:large;"><?php echo $value['notice_title'];?></p>
                    <p style="txt_white"><?php echo $value['notice'];?><br><span style="float:right">-ISM Admin</span></p>
                </div>
            </div>
        <?php
                $i++;
                }
            }
            else{
        ?>
        <div class="item active">
          <img src="<?php echo base_url();?>assets/images/blackboard.jpg" alt="blackboard">
          <div class="carousel-caption">
            <p class="noti_username">No notice for you</p>
            <p style="color:#fff;font-size:large">- ISM Admin,  </p>
          </div>
        </div>
        <?php } ?>
      </div>
       <!-- Controls -->
      <a class="left carousel-control" href="#carousel-noticeboard" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#carousel-noticeboard" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
    <!--//notice board-->
     
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
                    <img src="<?php echo base_url();?>assets/images/user5.jpg">
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
                    <img src="<?php echo base_url();?>assets/images/user3.jpg">
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
            <!--item3-->
            <div class="score_item">
                <div class="score_img">
                    <img src="<?php echo base_url();?>assets/images/user2.jpg">
                </div>
                <div class="score_descrip">
                    <p class="score_name">Mary Watson</p>
                    <p>St. Mary</p>
                    <p>F.Y. CS</p>
                </div>
                <div class="score_points">
                    <p>Score</p>
                    <p class="score_number">470</p>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="score_div">
            <h5>Arts</h5>
            <!--item1-->
            <div class="score_item">
                <div class="score_img">
                    <img src="<?php echo base_url();?>assets/images/user5.jpg">
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
                    <img src="<?php echo base_url();?>assets/images/user3.jpg">
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
            <!--item3-->
            <div class="score_item">
                <div class="score_img">
                    <img src="<?php echo base_url();?>assets/images/user2.jpg">
                </div>
                <div class="score_descrip">
                    <p class="score_name">Mary Watson</p>
                    <p>St. Mary</p>
                    <p>F.Y. CS</p>
                </div>
                <div class="score_points">
                    <p>Score</p>
                    <p class="score_number">462</p>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
    <!--//high score board-->
   
    <!--Suggested Studymates-->
    <div class="suggested_mates box">
        <div class="box_header">
            <h3>Suggested Studymates</h3>
        </div>
        <div class="suggested_mates_holder text-center">
            <div id="carousel-studymate" class="carousel slide" data-ride="carousel">
                <?php if(isset($suggested_studymates) && sizeof($suggested_studymates) > 0){ ?>

              <!-- Indicators -->
              <ol class="carousel-indicators">
                <li data-target="#carousel-studymate" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-studymate" data-slide-to="1"></li>
                <li data-target="#carousel-studymate" data-slide-to="2"></li>
                <!-- <li data-target="#carousel-studymate" data-slide-to="3"></li> -->
              </ol>
            
              <!-- Wrapper for slides -->
              <div class="carousel-inner" role="listbox">
                
                <?php 
                    
                        $i = 1;
                        foreach ($suggested_studymates as $key => $value) {
                            if($i == 1)
                                $class = ' active';
                            else
                                $class = '';

                        ?>
                            <div class="item<?php echo $class;?>">
                              <!--card-->
                                <div class="suggested_mates_card">
                                    <div class="mate_user_img">
                                        <img src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                    </div>
                                    <div class="mate_descrip">
                                        <p class="mate_name"><?php echo $value['full_name'];?></p>
                                        <p class="mate_following">Folowing 34 Authers</p>
                                        <p>Live in Ghana</p>
                                        <p>Student from <?php echo $value['school_name'];?></p>
                                        <p><?php echo $value['course_name'];?></p>
                                        <?php if($value['srid'] != '' && $value['is_delete'] == 0){?>
                                        <button class="btn btn_black_normal" data-type="studyment-request" data-id="<?php echo $value['user_id'];?>" disabled>Request Already Sent</button>
                                        <?php }else{ ?>
                                        <button class="btn btn_green" data-type="studyment-request" data-id="<?php echo $value['user_id'];?>">Add Studymates</button>
                                        <?php } ?>
                                    </div>
                                </div>
                                <!--//card-->
                            </div>
                        <?php
                        $i++;
                        }
                ?>
              </div>
            
              <!-- Controls -->
              <a class="left carousel-control" href="#carousel-studymate" role="button" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
              </a>
              <a class="right carousel-control" href="#carousel-studymate" role="button" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
              </a>
              <?php } else { echo '<code>No more studymates</code>';} ?>
            </div>                        
            
            <a href="student/studymates_request" class="btn btn_blue">Find More studymates</a>
        </div>
    </div>
    <!--//suggested Studymates-->
    <!--STM-->
    <div class="stm">
        <div class="box_header">
            <h3>Studymates <span> STM</span></h3>
        </div>
        <div class="stm_list mCustomScrollbar" data-mcs-theme="minimal-dark">
            <?php
            if(isset($classmates) > 0){
                foreach($classmates as $key => $value){
                    $u = 'offline';
                    if(in_array($value['id'], $online)){
                        $u = 'online';
                    }
                    ?>
                    <div class="stm_item <?php echo $u; ?>" data-id="<?php echo $value['id']; ?>">
                        <a id="mate_list" href="javascript:void(0);" data-id="<?php echo $value['id']; ?>">

                        <div class="stm_user_img">
                            <img src="/<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
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
<!--//side right-->
<script type="text/javascript">
 
 $(document).ready(function() {
    $(".js-example-basic-single").select2();
});

</script>

