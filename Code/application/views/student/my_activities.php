<!--main-->
<div class="col-sm-7 main main2">
    <div class="box activities">
        <div class="box_body">
            <div class="col-sm-12 border-left">
                <?php
                    if(isset($my_month)){
                        for ($i = count($my_month)-1;$i >= 0;$i--) {
                            $value = $my_month[$i];
                            ?>
                            <div class="divide_discussion">                    
                                <hr><h4><?php echo date("F Y",strtotime($value));?></h4>
                            </div>
                            <div class="clearfix"></div>
                            <?php
                                if(isset($my_activities['topic_allcated']) && sizeof($my_activities['topic_allcated'])>0){
                                    $t = 0;
                                    foreach ($my_activities['topic_allcated'] as $key => $topic_allcated_value) {
                                        if($value ==  date('Y-m',strtotime($topic_allcated_value['created_date']))){
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
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>
                                        </div>
                                <?php
                                        $t++;
                                        }
                                    }
                                }   
                            if(isset($my_activities['studymates']) && sizeof($my_activities['studymates']) > 0){
                                $s = 0;
                                foreach ($my_activities['studymates'] as $key => $studymate_value) {
                                    if($value ==  date('Y-m',strtotime($studymate_value['created_date']))){
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
                                            <p>Live in Ghana</p>
                                            <a href="#">Following 34 Authers</a>
                                        </div>
                                    </div>   
                                <?php
                                        $s++;
                                    }
                                }
                            }
                            if(isset($my_activities['like']) && sizeof($my_activities['like']) > 0){
                                foreach ($my_activities['like'] as $key => $like_value) {
                                    if($value ==  date('Y-m',strtotime($like_value['created_date']))){
                                ?>
                                <div class="status_like">
                                    <h4 class="activity_heading">Liked status of <span class="txt_green"><?php echo $like_value['post_username'];?></span></h4>
                                    <div class="feed_text">                                               
                                        <p><?php echo $like_value['feed_text'];?></p>
                                        <!-- <a href="#" class="like_btn"><span class="icon icon_thumb_0"></span>
                                            <?php echo $like_value['totlike'];?>
                                        </a>
                                        <a href="#" class="comment_btn"><span class="icon icon_comment"></span>
                                            <?php echo $like_value['totcomment'];?>
                                        </a> -->
                                        <!-- <a <hr>ef="#">View All</a> -->
                                        <!-- <div class="dropdown tag_user" style="display: inline-block;">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>
                                            <ul class="dropdown-menu">
                                                <li><a href="#">Emma Mall</a></li>
                                                <li><a href="#">Gill Christ</a></li>
                                                <li><a href="#">Adam Stranger</a></li>
                                            </ul>
                                        </div> -->
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                                <?php
                                    }
                                }   
                            }
                            if(isset($my_activities['comment']) && sizeof($my_activities['comment'])>0){
                                foreach ($my_activities['comment'] as $key => $comment_value) {
                                    if($value ==  date('Y-m',strtotime($comment_value['created_date']))){
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
                                                <div class="shared_images">
                                                    <div><img src="assets/images/shared1.jpg"></div>
                                                    <div><img src="assets/images/shared2.jpg"></div>
                                                </div>
                                                <div class="clearfix"></div>
                                               <!--  <a href="#" class="like_btn"><span class="icon icon_thumb"></span>
                                                    <?php echo $comment_value['totlike'];?>
                                                </a>
                                                <a href="#" class="comment_btn"><span class="icon icon_comment"></span>
                                                    <?php echo $comment_value['totcomment'];?>
                                                </a> -->
                                                <a href="javascript:void(0);" data-type="view-all-comment-activities" data-id="<?php echo $comment_value['id'];?>">View All</a>
                                                <!-- <div class="dropdown tag_user" style="display: inline-block;">
                                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="#">Emma Mall</a></li>
                                                        <li><a href="#">Gill Christ</a></li>
                                                        <li><a href="#">Adam Stranger</a></li>
                                                    </ul>
                                                </div> -->
                                            </div>
                                            <div class="clearfix"></div>
                                            <!--comment-->
                                            <div class="comment" data-id="<?php echo $comment_value['id'];?>">
                                                <div class="user_small_img user_comment">
                                                    <img src="<?php echo UPLOAD_URL.'/'.$this->session->userdata('user')['profile_pic'];?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                                </div>
                                                <div class="notification_txt">
                                                    <p><a href="#" class="noti_username"><?php echo $this->session->userdata('user')['full_name'];?></a> <?php echo $comment_value['comment'];?></p>
                                                    <span class="noti_time">1 Day</span>                            
                                                </div>
                                                <div class="clearfix"></div>

                                            </div>
                                        </div>
                                    </div>
                                <?php
                                    }
                                }
                            }
                            if(isset($my_activities['post']) && sizeof($my_activities['post'])>0){
                                $p = 0;
                                foreach ($my_activities['post'] as $key => $post_value) {
                                    if($value ==  date('Y-m',strtotime($post_value['created_date']))){
                                ?>
                                    <div class="status_like">
                                        <?php if($p==0){ ?>
                                        <h4 class="activity_heading">Status updated</h4>
                                        <?php } ?>
                                        <span class="date"><?php $old_date = strtotime($post_value['created_date']);echo date("M j, Y",$old_date);?></span>
                                        <div class="feed_text">                                               
                                            <p><?php echo $post_value['feed_text'];?></p>
                                            <!-- <a href="#" class="like_btn"><span class="icon icon_thumb_0"></span><?php echo $post_value['totlike'];?></a>
                                            <a href="#" class="comment_btn"><span class="icon icon_comment"></span><?php echo $post_value['totcomment'];?></a> -->
                                           <!--  <a href="#">View All</a>
                                            <div class="dropdown tag_user" style="display: inline-block;">
                                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>
                                                <ul class="dropdown-menu">
                                                    <li><a href="#">Emma Mall</a></li>
                                                    <li><a href="#">Gill Christ</a></li>
                                                    <li><a href="#">Adam Stranger</a></li>
                                                </ul>
                                            </div>-->
                                        </div> 
                                    </div>                
                                <?php
                                        $p++;
                                    }
                                }
                            }
                        }
                    }
                ?>
                
            </div>
            <div class="clearfix"></div>
            <div class="text-center">
                <form action="" method="post">
                    <input type="hidden" name="load_more" value="<?php echo isset($value)?$value:'';?>"> 
                    <!-- <a href="#" class="search_result_label">View More</a> -->
                    <input type="submit" class="btn btn_green no-margin" value="View More">
                </form>
            </div>

        </div>
     </div>
</div>
<!--//main-->
