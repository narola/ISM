<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark" data-type="activity-main">
<div class="col-sm-12" data-type="activity-sub-main">
    <div class="box activities" data-type="activity">
        <div class="box_body" data-type="activity-body">
            <div class="col-sm-12 border-left" data-type="activity-sub-body">
                <?php
                    // p($my_month,true);
                    if(isset($my_month)){
                        // for ($i = count($my_month)-1;$i >= 0;$i--) {
                            // $value = $my_month[$i];
                            ?>
                            <div class="divide_discussion">                    
                                <hr><h4><?php echo date("F Y",strtotime($my_month));?></h4>
                            </div>
                            <div class="clearfix"></div>
                            <?php
                                if(isset($my_activities['topic_allcated']) && sizeof($my_activities['topic_allcated'])>0){
                                    $t = 0;
                                    foreach ($my_activities['topic_allcated'] as $key => $topic_allcated_value) {
                                        // if($value ==  date('Y-m',strtotime($topic_allcated_value['created_date']))){
                                ?>
                                        <div class="topic_allocated">
                                            <?php if($t == 0) { ?>
                                                <h4 class="activity_heading">Topic Allocated</h4>
                                            <?php } ?>
                                            <div class="topic_div">
                                                <h4><?php echo $topic_allcated_value['topic_name'];?></h4>
                                                <div>
                                                    <div><strong>Discussion</strong><p>29 Comments</p></div>
                                                    <div><strong>Examination - Quiz</strong><p>Score :  215</p></div>
                                                    <div><strong>Examination - Quiz</strong><p>Score :  215</p></div>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                <?php
                                        $t++;
                                        // }
                                    }
                                }   
                            if(isset($my_activities['studymates']) && sizeof($my_activities['studymates']) > 0){
                                $s = 0;
                                foreach ($my_activities['studymates'] as $key => $studymate_value) {
                                    // if($value ==  date('Y-m',strtotime($studymate_value['created_date']))){
                                ?>
                                    <div class="studymate_with">
                                        <?php if($s == 0) { ?>
                                            <h4 class="activity_heading">Became studymate with</h4>
                                        <?php } ?>
                                        <span class="date"><?php $old_date = strtotime($studymate_value['created_date']);echo date("M j, Y",$old_date);?></span>
                                        <div class="study_mate">
                                            <div class="mate_user_img">
                                                <img src="<?php echo UPLOAD_URL.'/'.$studymate_value['profile_link'];?>" class="mCS_img_loaded" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                            </div>
                                            <h4><?php echo $studymate_value['full_name'];?></h4>
                                            <p><?php echo $studymate_value['school_name'];?></p>
                                            <p class="txt_grey"><?php echo $studymate_value['course_name'];?></p>
                                        </div>
                                    </div>   
                                <?php
                                        $s++;
                                    // }
                                }
                            }
                            if(isset($my_activities['like']) && sizeof($my_activities['like']) > 0){
                                foreach ($my_activities['like'] as $key => $like_value) {
                                    // if($value ==  date('Y-m',strtotime($like_value['created_date']))){
                                ?>
                                <div class="status_like">
                                    <h4 class="activity_heading">Liked status of <span class="txt_green"><?php echo $like_value['post_username'];?></span></h4>
                                    <div class="feed_text">                                               
                                        <p><?php echo $like_value['feed_text'];?></p>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <?php
                                    // }
                                }   
                            }
                            if(isset($my_activities['comment']) && sizeof($my_activities['comment'])>0){
                                foreach ($my_activities['comment'] as $key => $comment_value) {
                                    // if($value ==  date('Y-m',strtotime($comment_value['created_date']))){
                                ?>
                                     <div class="commented_on">
                                        <h4 class="activity_heading">Commented on</h4>
                                        <div class="feeds">
                                            <div class="user_small_img">
                                                <img src="<?php echo UPLOAD_URL.'/'.$comment_value['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                            </div>
                                            <div class="feed_text">
                                                <h4><?php echo $comment_value['full_name'];?></h4>
                                                <span class="date"><?php $old_date = strtotime($comment_value['created_date']);echo date("M j, Y",$old_date);?></span>
                                                <div class="clearfix"></div>
                                                <p><?php echo $comment_value['feed_text'];?></p>
                                                <?php
                                                    if($comment_value['image_link'] != ''){
                                                ?>
                                                <div class="shared_images">
                                                    <div><img src="<?php echo UPLOAD_URL.'/'.$comment_value['image_link'];?>"></div>
                                                </div>
                                                <?php } ?>
                                                <div class="clearfix"></div>
                                                <?php 
                                                    if($comment_value['totcomment'] > 1){
                                                ?>
                                                <a href="javascript:void(0);" data-type="view-all-comment-activities" data-id="<?php echo $comment_value['id'];?>">View All</a>
                                                <?php 
                                                    }
                                                ?>
                                            </div>
                                            <div class="clearfix"></div>
                                            <!--comment-->
                                            <div class="comment" data-id="<?php echo $comment_value['id'];?>">
                                                <div class="user_small_img user_comment">
                                                    <img src="<?php echo UPLOAD_URL.'/'.$this->session->userdata('user')['profile_pic'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                                </div>
                                                <div class="notification_txt">
                                                    <p><a href="#" class="noti_username"><?php echo $this->session->userdata('user')['full_name'];?></a> <?php echo $comment_value['comment'];?></p>
                                                    <span class="noti_time"><?php echo get_time_format($comment_value['created_date']);?></span>                            
                                                </div>
                                                <div class="clearfix"></div>

                                            </div>
                                        </div>
                                    </div>
                                <?php
                                    // }
                                }
                            }
                            if(isset($my_activities['post']) && sizeof($my_activities['post'])>0){
                                $p = 0;
                                foreach ($my_activities['post'] as $key => $post_value) {
                                    // if($value ==  date('Y-m',strtotime($post_value['created_date']))){
                                ?>
                                    <div class="status_like">
                                        <?php if($p==0){ ?>
                                        <h4 class="activity_heading">Status updated</h4>
                                        <?php } ?>
                                        <span class="date"><?php $old_date = strtotime($post_value['created_date']);echo date("M j, Y",$old_date);?></span>
                                        <div class="feed_text border_bottom">                                               
                                            <p><?php echo $post_value['feed_text'];?></p>
                                            <?php 
                                                if($post_value['image_link'] != ''){
                                            ?>
                                            <div class="shared_images">
                                                <div><img src="<?php echo UPLOAD_URL.'/'.$post_value['image_link'];?>" class="mCS_img_loaded"></div>
                                            </div>
                                            <?php } ?>
                                        </div> 
                                        
                                    </div>                
                                <?php
                                        $p++;
                                    // }
                                }
                            }
                        // }
                    }
                ?>
            </div>
            <div class="clearfix"></div>
            <div class="text-center" data-type="no-more">
                <input type="hidden" name="load_more" value="<?php echo isset($value)?$value:'';?>"> 
                <a href="javascript:void(0);" data-month="<?php echo isset($new_my_month)?$new_my_month:'';?>" class="search_result_label" data-type="load-activity-more">View More</a>
            </div>
        </div>
     </div>
	 </div>
</div>
<!--//main-->
