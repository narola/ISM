<!--main-->
            <div class="col-sm-7 main main2">
                 <div class="box activities">
                    <div class="box_body">
                        <div class="col-sm-12 border-left">
                            <!--october-->
                            <?php
                                if(isset($my_month)){
                                    ;
                                    for ($i = count($my_month)-1;$i >= 0;$i--) {
                                        $value = $my_month[$i];
                                        // exit;
                                    ?>
                                        <div class="divide_discussion">                    
                                            <hr><h4><?php echo date("F Y",strtotime($value));?></h4>
                                        </div>
                                        <div class="clearfix"></div>
                                        <?php
                                        if(isset($my_activities['studymates']) && sizeof($my_activities['studymates']) > 0){
                                            foreach ($my_activities['studymates'] as $key => $studymate_value) {
                                                if($value ==  date('Y-m',strtotime($studymate_value['created_date']))){
                                            ?>
                                                <div class="studymate_with">
                                                    <h4 class="activity_heading">Became studymate with</h4>
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
                                                            <span class="date">Sept 1, 2015</span>
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
                                            foreach ($my_activities['post'] as $key => $post_value) {
                                                if($value ==  date('Y-m',strtotime($post_value['created_date']))){
                                            ?>
                                                <div class="status_like">
                                                    <h4 class="activity_heading">Status updated</h4>
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
            
            <!--side right-->
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
                        <!--stm1-->
                        <div class="stm_item online">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user1.jpg">
                            </div>
                            <p>Mary Watson</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item online">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user2.jpg">
                            </div>
                            <p>Adam Rose</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item online">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user3.jpg">
                            </div>
                            <p>John Smith</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item online">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user4.jpg">
                            </div>
                            <p>Adam Stranger</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item online">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user1.jpg">
                            </div>
                            <p>Mary Watson</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item online">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user2.jpg">
                            </div>
                            <p>Adam Rose</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>                    
                        <!--stm1-->
                        <div class="stm_item offline">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user5.jpg">
                            </div>
                            <p>Dean Winchester</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item offline">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user6.jpg">
                            </div>
                            <p>Sam Winchester</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item offline">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/user7.jpg">
                            </div>
                            <p>Lucifer Dey</p>
                            </a>
                            <div class="clearfix"></div>
                        </div>
                        <!--stm1-->
                        <div class="stm_item offline">
                            <a href="#">
                            <div class="stm_user_img">
                                <img src="assets/images/avatar.png">
                            </div>
                            <p>Ronald Weasley</p>
                            </a>
                            <div class="clearfix"></div>
                        </div> 
                     </div>       
                                
                </div>
                <!--//STM-->                
            </div>
            <!--//side right-->  