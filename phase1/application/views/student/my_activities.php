<!--main-->
<div class="col-sm-7 main main2 mscroll_custom" data-type="activity-main">
    <div class="col-sm-12" data-type="activity-sub-main">
        <div class="box activities" data-type="activity">
            <div class="box_body" data-type="activity-body">
                <div class="col-sm-12 border-left" data-type="activity-sub-body">
                    <?php
                        
                      $gender = "";
                        if(strtolower($this->session->userdata['user']['gender']) =="male")
                        {
                            $gender = "his";
                        }else
                        {
                            $gender = "her";
                        }

                        if(isset($my_month)){
                            ?>
                            <div class="divide_discussion">                    
                                <hr><h4><?php echo date("F Y",strtotime($my_month));?></h4>
                            </div>
                            <div class="clearfix"></div>
                            <?php
                                if(isset($my_activities['topic_allcated']) && sizeof($my_activities['topic_allcated'])>0){
                                    $t = 0;
                                    foreach ($my_activities['topic_allcated'] as $key => $topic_allcated_value) {
                                        if($topic_allcated_value['topic_name'] != ''){
                                ?>
                                    <div class="topic_allocated">
                                        <?php if($t == 0) { ?>
                                            <h4 class="activity_heading box_header">Topic Allocated</h4>
                                        <?php } ?>
                                        <div class="topic_div">
                                            <h4><?php echo $topic_allcated_value['topic_name'];?></h4>
                                            <div>
                                                <div><strong>Discussion</strong><p><?php echo $topic_allcated_value['total_discussion'];?> Comments</p></div>
                                                <div><strong>Discussion - Score</strong><p>Score :  <?php echo isset($topic_allcated_value['discussion_score'])?$topic_allcated_value['discussion_score']:'0';?></p></div>
                                                <div><strong>Examination - Quiz</strong><p>Percentage :  <?php echo $topic_allcated_value['per'];?>%</p></div>
                                            </div>
                                            <div class="clearfix"></div>
                                        </div>
                                    </div>
                                <?php
                                        }
                                    $t++;
                                    }
                                }   
                                if(isset($my_activities['studymates']) && sizeof($my_activities['studymates']) > 0){
                                $s = 0;
                                    foreach ($my_activities['studymates'] as $key => $studymate_value) {
                                ?>
                                    <div class="studymate_with">
                                        <?php if($s == 0) { ?>
                                            <h4 class="box_header">Became studymate with</h4>
                                        <?php } ?>
                                         <span class="date noti_time just_now">
                                         <!--
                                             <script type="text/javascript">
                                                        var old =  '<?php //echo $studymate_value['created_date']; ?>';
                                                        var ndt =  old.substr(0, 4) + "/" + old.substr(5, 2) + "/" + old.substr(8, 2);
                                                        var d = new  Date(ndt);
                                                        var dd = date_to_day(d);
                                                        document.write(dd);
                                             </script>
                                            <?php //$old_date = strtotime($studymate_value['created_date']);echo date("M j, Y",$old_date);?>
                                       --> </span>
                                         <script type="text/javascript">
                                             $(".just_now").timestatus("<?php echo $studymate_value['created_date']; ?>");
                                         </script>

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
                                }
                            }

                            ?>
                            <div class="status_like">
                            <?php 
                            if(isset($my_activities['like']) && sizeof($my_activities['like']) > 0){
                                ?>
                                <h4 class="box_header">Status liked</h4>
                                <?php 

                                foreach ($my_activities['like'] as $key => $like_value) {
                                ?>
                                    <div class="status_like">
                                        <?php
                                           
                                            if($like_value['id'] == $this->session->userdata['user']['id'])
                                            { ?>

                                               <h4 class="activity_heading"> <span class="txt_green" style="cursor:pointer;display: -webkit-inline-box;" data-type="show-profile" data-id="<?php echo $like_value['id']; ?>"><?php echo $like_value['post_username'];?></span> Liked <?php echo $gender;?> own status</h4>
                                                 <span class="date noti_time just_now">
                                                 </span>
                                                    <script type="text/javascript">
                                                     $(".just_now").timestatus("<?php echo $like_value['created_date']; ?>");
                                                    </script>  
                                                     <div class="clearfix"></div>
                                                <div class="feed_text">                                               
                                                    <p><?php echo $like_value['feed_text'];?></p>
                                                       <?php 
                                                            if($like_value['image_link'] != ''){
                                                        ?>
                                                        <div class="shared_images">
                                                            <div>
                                                               <a href="<?php echo base_url(); ?><?php echo UPLOAD_URL.'/'.$like_value['image_link'];?>" class="fancybox"> <img src="<?php echo UPLOAD_URL.'/'.$like_value['image_link'];?>" width="100" height="70" class="mCS_img_loaded"></a>
                                                            </div>
                                                        </div>
                                                        <?php } ?>
                                                </div>
                                                <div class="clearfix"></div>   

                                        <?php }else{ ?>

                                             <h4 class="activity_heading">Liked status of <span style="cursor:pointer;display: -webkit-inline-box;" data-type="show-profile" data-id="<?php echo $like_value['id']; ?>" class="txt_green"><?php echo $like_value['post_username'];?></span></h4>
                                                 <span class="date noti_time just_now">
                                                 </span>
                                                    <script type="text/javascript">
                                                     $(".just_now").timestatus("<?php echo $like_value['created_date']; ?>");
                                                    </script>  
                                                     <div class="clearfix"></div>
                                                <div class="feed_text">                                               
                                                    <p><?php echo $like_value['feed_text'];?></p>
                                                      <?php 
                                                            if($like_value['image_link'] != ''){
                                                        ?>
                                                        <div class="shared_images">
                                                            <div>
                                                                 <a href="<?php echo base_url(); ?><?php echo UPLOAD_URL.'/'.$like_value['image_link'];?>" class="fancybox"> <img src="<?php echo UPLOAD_URL.'/'.$like_value['image_link'];?>" width="100" height="70" class="mCS_img_loaded"></a>
                                                            </div>
                                                        </div>
                                                        <?php } ?>
                                                </div>
                                                <div class="clearfix"></div> 
                                       <?php }
                                        ?>
                                        
                                    </div>
                                <?php
                                }   
                            }

                            ?>
                        </div>
                            <div class="commented_on">
                               
                            <?php

                            if(isset($my_activities['comment']) && sizeof($my_activities['comment'])>0){
                                ?>
                                     <h4 class="box_header">Commented on</h4>
                                <?php
                                    foreach ($my_activities['comment'] as $key => $comment_value) {

                                ?>
                                    <div class="commented_on">
                                          <?php
                                            if($comment_value['feed_by'] == $this->session->userdata['user']['id'])
                                            { ?>
                                                 <h4 class="activity_heading">Commented on <?php echo $gender; ?> own post</h4>
                                            <?php 
                                              }else{
                                               ?>
                                                 <h4 class="activity_heading">Commented on</h4>
                                            <?php } ?>

                                        <div class="feeds">
                                            <div class="user_small_img">
                                                <img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $comment_value['feed_by']; ?>" src="<?php echo UPLOAD_URL.'/'.$comment_value['profile_link'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                            </div>
                                            <div class="feed_text">
                                                <h4 style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $comment_value['feed_by']; ?>"><?php echo $comment_value['full_name'];?></h4>
                                                 <span class="date noti_time just_now">
                                                 </span>
                                                    
                                                <div class="clearfix"></div>
                                                    <script type="text/javascript">
                                                     $(".just_now").timestatus("<?php echo $comment_value['created_date']; ?>");
                                                    </script>  
                                                <p><?php echo $comment_value['feed_text'];?></p>
                                                <?php
                                                    if($comment_value['image_link'] != ''){
                                                ?>
                                                <div class="shared_images">
                                                    <div>
                                                         <a href="<?php echo base_url(); ?><?php echo UPLOAD_URL.'/'.$comment_value['image_link'];?>" class="fancybox"> <img src="<?php echo UPLOAD_URL.'/'.$comment_value['image_link'];?>" width="100" height="70" class="mCS_img_loaded"></a>
                                                    </div>
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
                                                    <img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $comment_value['comment_by']; ?>" src="<?php echo UPLOAD_URL.'/'.$this->session->userdata('user')['profile_pic'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                                </div>
                                                <div class="notification_txt">
                                                    <p><a class="noti_username" style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $comment_value['comment_by']; ?>"><?php echo $this->session->userdata('user')['full_name'];?></a> <?php echo $comment_value['comment'];?></p>
                                                    <span class="noti_time just_now"></span>
                                                    <script type="text/javascript">
                                                        $(".just_now").timestatus("<?php echo $comment_value['created_date']; ?><?php echo $comment_value['comment'];?>");
                                                   </script>    

                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                    </div>
                                <?php
                                }
                            }
                            ?>
                            </div>
                            <?php
                            if(isset($my_activities['post']) && sizeof($my_activities['post'])>0){
                                $p = 0;
                                foreach ($my_activities['post'] as $key => $post_value) {

                                ?>
                                    <div class="status_like">
                                        <?php if($p==0){ ?>
                                        <h4 class="box_header">Status updated</h4>
                                        <?php } ?>
                                                 <span class="date noti_time just_now">
                                                 </span>
                                                    <script type="text/javascript">
                                                     $(".just_now").timestatus("<?php echo $post_value['created_date']; ?>");
                                                    </script>  
                                        <div class="feed_text border_bottom"> 

                                            <p><a class="noti_username" style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $this->session->userdata['user']['id'];?>"><?php echo $this->session->userdata['user']['full_name'];?></a><strong>updated <?php echo $gender; ?> status: </strong><?php echo $post_value['feed_text'];?></p>
                                            <?php 
                                                if($post_value['image_link'] != ''){
                                            ?>
                                            <div class="shared_images">
                                                <div>
                                                    <a href="<?php echo base_url(); ?><?php echo UPLOAD_URL.'/'.$post_value['image_link'];?>" class="fancybox"> <img src="<?php echo UPLOAD_URL.'/'.$post_value['image_link'];?>" width="100" height="70" class="mCS_img_loaded"></a>
                                                </div>
                                            </div>
                                            <?php } ?>
                                        </div> 
                                    </div>                
                                <?php
                                    $p++;
                                }
                            }
                        }
                    ?>
                </div>
                <div class="clearfix"></div>
                <div class="text-center" data-type="no-more">
                    <input type="hidden" name="load_more" value="<?php echo isset($value)?$value:'';?>"> 
                    <a style="color:white" href="javascript:void(0);" data-month="<?php echo isset($new_my_month)?$new_my_month:'';?>" class="btn btn_green no-margin" data-type="load-activity-more">View More</a>
                </div>
            </div>
        </div>
	</div>
</div>
<!--//main-->
