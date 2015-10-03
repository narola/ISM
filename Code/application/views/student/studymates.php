<!--main-->
<div class="col-sm-7 main main2 stydymates">   
    <div class="box general_cred">
        <div class="box_header">
            <h3>My Studymates</h3>
        </div>
        <div class="box_body studyamte_list mCustomScrollbar" data-mcs-theme="minimal-dark">
            <?php
                if(isset($my_studymates)){
                    foreach ($my_studymates as $key => $value) {
                ?>
                    <!--item1-->
                    <div class="study_mate ol" data-id="<?php echo $value['user_id'];?>">
                        <div class="col-lg-9 col-md-8 col-sm-7">
                            <div class="mate_user_img">
                                <img src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                            </div>
                            <h4><?php echo $value['full_name'];?></h4>
                            <p>Student from <?php echo $value['school_name'];?></p>
                            
                            <a href="#">Following 34 Authers</a>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-5">
                            <button class="btn btn_green btn-block">View Profile</button>
                            <div class="form-group select">
                                <select class="form-control">
                                    <option>Studymates</option>
                                    <option>Remove Studymate</option>
                                </select>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <!--//item1-->
                <?php
                    }
                }
                else{
                ?>
                    <div class="study_mate">
                        <h4>No studymate found...</h4>
                    </div>
                <?php
                }
            ?>
        </div>
    </div>
    <!--suggestion-->
    <div class="box general_cred">
        <div class="box_header">
            <h3>Recommended Studymates</h3>
        </div>
        <div class="box_body studymate_recom text-center">
            <!--carousel-->
            <div id="carousel-studymate" class="carousel slide" data-ride="carousel">
              
              <!-- Wrapper for slides -->
              <div class="carousel-inner" role="listbox">
                <!--item-->
                <div class="item active">
                    <?php 
                        if(isset($recommended_studymates)){
                            foreach ($recommended_studymates as $key => $value) {
                    ?>
                  <!--card-->
                    <div class="suggested_mates_card">
                        <div class="mate_user_img">
                            <img src="assets/images/user6.jpg">
                        </div>
                        <div class="mate_descrip">
                            <p class="mate_name"><?php echo $value['full_name'];?></p>
                            <p class="mate_following">Folowing 34 Authers</p>
                            
                            <p><?php echo $value['school_name'];?></p>
                            <p><?php echo $value['course_name'];?></p>
                            <button class="btn btn_green">Add Studymates</button>
                        </div>
                    </div>
                    <!--//card-->
                    <?php
                            }
                        }
                    ?>                           
                   
                </div>
                <!--//item-->
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
            <!--//carousel-->
        </div>
    </div>
    <!--//suggestion-->
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