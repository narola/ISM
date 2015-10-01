 <script>
    function showall(id){
        $('.post'+id).show();
    }
</script>
 <!--main-->
            <div class="col-sm-7 main">
                <div class="box">
                    <textarea id="feed_post" type="text" class="form-control post_input" placeholder="SAY IT" ></textarea>
                    <a href="#" class="icon icon_emoji"></a>
                    <div class="box_header">
                        <a href="#" class="icon icon_pin"><input type="file"></a>
                        <a href="#" class="icon icon_image"><input type="file"></a>
                        <div class="dropdown" style="display: inline-block;">
                            <a href="#" class="dropdown-toggle icon icon_user" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Emma Mall</a></li>
                                <li><a href="#">Gill Christ</a></li>
                                <li><a href="#">Adam Stranger</a></li>
                            </ul>
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
                        <div class="box feeds" data-id="<?php echo $value['fid']; ?>">
                            <div class="user_small_img">
                                <img src="<?php echo base_url();?>assets/images/user2.jpg">
                            </div>
                            <div class="feed_text">
                                <h4><?php echo $value['full_name'];?></h4>
                                <span class="date"><?php $old_date = strtotime($value['posted_on']);echo date("M j, Y",$old_date);?></span>
                                <div class="clearfix"></div>
                                <p><?php echo $value['feed_text'];?></p>
                                <a href="#" class="like_btn"><span class="icon icon_thumb_0"></span><?php echo $value['tot_like'];?></a>
                                <a href="#" class="comment_btn"><span class="icon icon_comment"></span><?php echo $value['tot_comment'];?></a>
                                <a href="javascript:void(0);" onclick="showall(<?= $j; ?>);">View All</a>
                                <div class="dropdown tag_user" style="display: inline-block;">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Emma Mall</a></li>
                                        <li><a href="#">Gill Christ</a></li>
                                        <li><a href="#">Adam Stranger</a></li>
                                    </ul>
                                </div>
                            </div>
                        
                        <div class="clearfix"></div>
                        <!--comment-->
                        <div id="feed_comments">
                        <?php 
                            if(isset($comment)){
                                $i = 1;
                                foreach ($comment as $key => $com) {
                                    if($value['fid'] == $com['feed_id']){
                                        if($i > 3)
                                            $display = 'none';
                                        else
                                            $display = '';
                                    ?>

                                    <div class="comment <?= 'post'.$j;?>" style="display:<?= $display;?>">
                                        <div class="user_small_img user_comment">
                                            <img src="<?php echo UPLOAD_URL.'/'.$com['profile_link'];?>">
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
                                ?>
                                 </div>
                                <div class="write_comment box_body">
                                    <input type="text" class="form-control" placeholder="Write Your Comment Here" data-type="feed_comment" data-id="<?php echo $value['fid']; ?>">                  
                                    <a class="icon icon_image"></a>
                                    <input type="file">
                                </div>
                                <?php
                            }
                        echo '</div>';
                        $j++;
                        }
                    }
                ?>
            </div>

                <!--comment-->
                <!--//feed box-->
                
                <div class="box text-center load_more">
                    <a href="#">Load More</a>
                </div>
            </div>
            <!--//main-->
            
            <!--side right-->
            <div class="sidebar_right_container mCustomScrollbar" data-mcs-theme="minimal-dark"><!--scrollbar" id="style-3-->
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
                    <div class="item active">
                      <img src="<?php echo base_url();?>assets/images/blackboard.jpg" alt="blackboard">
                      <div class="carousel-caption">
                        <p class="noti_username">TEST BANNER</p>
                        <p style="color:#fff;">for Notice & ISM Ads</p>
                      </div>
                    </div>
                    <div class="item">
                      <img src="<?php echo base_url();?>assets/images/blackboard.jpg" alt="blackboard">
                      <div class="carousel-caption">
                        <p class="noti_username">TEST BANNER</p>
                        <p style="color:#fff;">for Notice & ISM Ads</p>
                      </div>
                    </div>
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
                          <!-- Indicators -->
                          <ol class="carousel-indicators">
                            <li data-target="#carousel-studymate" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-studymate" data-slide-to="1"></li>
                            <li data-target="#carousel-studymate" data-slide-to="2"></li>
                          </ol>
                        
                          <!-- Wrapper for slides -->
                          <div class="carousel-inner" role="listbox">
                            <div class="item active">
                              <!--card-->
                                <div class="suggested_mates_card">
                                    <div class="mate_user_img">
                                        <img src="<?php echo base_url();?>assets/images/user6.jpg">
                                    </div>
                                    <div class="mate_descrip">
                                        <p class="mate_name">Adam Stranger</p>
                                        <p class="mate_following">Folowing 34 Authers</p>
                                        <p>Live in Ghana</p>
                                        <p>Student from St.Xeviers</p>
                                        <p>F.Y. CS</p>
                                        <button class="btn btn_green">Add Studymates</button>
                                    </div>
                                </div>
                                <!--//card-->
                            </div>
                            <div class="item">
                              <!--card-->
                                <div class="suggested_mates_card">
                                    <div class="mate_user_img">
                                        <img src="<?php echo base_url();?>assets/images/user7.jpg">
                                    </div>
                                    <div class="mate_descrip">
                                        <p class="mate_name">Adam Stranger</p>
                                        <p class="mate_following">Folowing 34 Authers</p>
                                        <p>Live in Ghana</p>
                                        <p>Student from St.Xeviers</p>
                                        <p>F.Y. CS</p>
                                        <button class="btn btn_green">Add Studymates</button>
                                    </div>
                                </div>
                                <!--//card-->
                            </div>
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
                        </div>                        
                        
                        <button class="btn btn_blue">Find More studymates</button>
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
                        foreach($classmates as $key => $value){
                            $u = 'offline';
                            if(in_array($value['id'], $online)){
                                $u = 'online';
                            }
                            ?>
                            <div class="stm_item <?php echo $u; ?>" data-id="<?php echo $value['id']; ?>">
                                <a id="mate_list" href="javascript:void(0);" data-id="<?php echo $value['id']; ?>">

                                <div class="stm_user_img">
                                    <img src="/<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                                </div>
                                <span class="badge message_badge"><?php if($value['unread_msg'] > 0) echo $value['unread_msg']; ?></span>
                                <p><?php echo $value['full_name']; ?></p>
                                </a>
                                <div class="clearfix"></div>
                            </div>
                            <?php 
                        }  ?>
                     </div>       
                     <div class="text-center">
                        <button class="btn_find_studymates btn btn_blue">Find more Studymates</button>
                    </div>            
                </div>
                <!--//STM-->
                
            </div>
            <!--//side right-->
            <!--chat-->
            <div class="chat_container" id = 'chat_container'>
                 <?php
                 if(isset($active_chat) && !empty($active_chat)){
                ?>
                <div class="chat active" data-id="<?php echo $active_chat['user']['id'] ?>">
                    <div class="chat_header">
                        <div class="chat_img_holder">
                            <img src="<?php echo UPLOAD_URL.'/'.$active_chat['user']['profile_link']; ?>"></div>
                            <p class="chat_name"><?php echo $active_chat['user']['full_name'] ?></p>
                            <a href="#">
                                <span class="icon icon_option"></span>
                            </a>
                        </div>
                        <div class="chat_text mCustomScrollbar" data-mcs-position="bottom">
                            <?php
                            foreach($active_chat['comment'] as $value)
                                if($value['sender_id'] == $active_chat['user']['id']){
                            ?>
                                <div class="from"><p><?php echo $value['message']; ?></p></div>
                            <?php
                                }else{
                            ?>
                                <div class="to"><p><?php echo $value['message']; ?></p></div>
                            <?php
                                }
                             ?>

                        </div>
                        <input type="text" class="chat_input" placeholder="Say It" data-type="chat" data-id="<?php echo $active_chat['user']['id'] ?>">
                        <a href="#" class="icon icon_emoji"></a>
                        <a href="#" class="icon icon_pin"></a>
                        <input type="file" id="chat_upload" class="chat_pin" data-type="chat" data-id="<?php echo $active_chat['user']['id'] ?>">
                    </div>
                <?php } ?>
            </div>


            <!--//chat-->
