<div class="col-sm-7 main main2 stydymates">  
                <!--search bar-->
                <div class="row search_bar">
                    <input type="text" class="form-control" placeholder="Type here to search you mate">
                    <a href="#" class="icon icon_search"></a>
                    <button class="btn btn_green">View Recommendation</button>
                </div>
                <!--//search bar-->
                <!--filterbar-->
                <div class="row filter_bar">
                    <ul>
                        <li><a href="#">People</a></li>
                        <li><a href="#">School</a></li>
                        <li><a href="#">Area</a></li>
                        <li><a href="#">Course</a></li>
                    </ul>
                </div>
                <!--//filterbar-->
                <!--search result-->
                <h5 class="search_result_label">Search Result for</h5>
                <div class="box general_cred">
                    <div class="box_body studyamte_list studymate_request mCustomScrollbar" data-mcs-theme="minimal-dark">
                        <!--item1-->
                        <div class="study_mate">
                            <div class="col-lg-9 col-md-8 col-sm-7">
                                <div class="mate_user_img">
                                    <img src="assets/images/user1.jpg">
                                </div>
                                <h4>Adam Rose</h4>
                                <p>Student from Lourdes convents</p>
                                <p>Live in Ghana</p>
                                <a href="#">Following 34 Authers</a>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-5">
                                <button class="btn btn_green btn-block">Add Studymate</button>
                                
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--//item1-->
                        <!--item1-->
                        <div class="study_mate">
                            <div class="col-lg-9 col-md-8 col-sm-7">
                                <div class="mate_user_img">
                                    <img src="assets/images/user4.jpg">
                                </div>
                                <h4>Adam Rose</h4>
                                <p>Student from Lourdes convents</p>
                                
                                <a href="#">Following 34 Authers</a>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-5">
                                <button class="btn btn_green btn-block">Add Studymate</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--//item1-->
                        <!--item1-->
                        <div class="study_mate">
                            <div class="col-lg-9 col-md-8 col-sm-7">
                                <div class="mate_user_img">
                                    <img src="assets/images/user2.jpg">
                                </div>
                                <h4>Adam Rose</h4>
                                <p>Student from Lourdes convents</p>
                                
                                <a href="#">Following 34 Authers</a>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-5">
                                <button class="btn btn_black_normal btn-block">Respond to Request</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--//item1-->
                        <!--item1-->
                        <div class="study_mate">
                            <div class="col-lg-9 col-md-8 col-sm-7">
                                <div class="mate_user_img">
                                    <img src="assets/images/user3.jpg">
                                </div>
                                <h4>Adam Rose</h4>
                                <p>Student from Lourdes convents</p>
                                
                                <a href="#">Following 34 Authers</a>
                            </div>
                            <div class="col-lg-3 col-md-4 col-sm-5">
                                <button class="btn btn_green btn-block">Add Studymate</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--//item1-->
                        <div class="text-center">
                            <a href="#" class="search_result_label">View More</a>
                        </div>
                    </div>
                </div>
                <!--//search result-->
<div class="box general_cred">
    <div class="box_header">
        <h3>Studymate Requests</h3>
    </div>
    <div id="my_request_box" class="box_body studyamte_list studymate_request mCustomScrollbar" data-mcs-theme="minimal-dark">
       <?php
        if(isset($studymate_request) && sizeof($studymate_request) > 0){
            foreach ($studymate_request as $key => $value) {
             
       ?>
            <!--item1-->
            <div class="study_mate" id="my_request" data-id="<?php echo $value['id'];?>">
                <div class="col-lg-9 col-md-8 col-sm-7">
                    <div class="mate_user_img">
                        <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                    </div>
                    <h4><?php echo $value['full_name'];?><?php echo $value['id'];?></h4>
                    <p><?php echo $value['school_name'];?></p>
                    <p>Live in Ghana</p>
                    <a href="#">Following 34 Authers</a>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-5" id="action-box">
                    <button class="btn btn_green btn-block" data-subtype="accept-request" data-type="decline-request" data-id="<?php echo $value['id'];?>">Confirm Request</button>
                    <button class="btn btn_black_normal btn-block" data-subtype="decline-request" data-type="decline-request" data-id="<?php echo $value['id'];?>">Decline Request</button>
                </div>
                <div class="clearfix"></div>
            </div>
            <!--//item1-->
        <?php
                }
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
                <?php 
                    if(isset($recommended_studymates)){
                ?>
                    <div class="item active">
                    <?php
                        $i = 1;
                        foreach ($recommended_studymates as $key => $value) {
                        if($i/6==1){
                            echo '</div><div class="item">';     
                        }
                    ?>
                        <!--card-->
                        <div class="suggested_mates_card">
                            <div class="mate_user_img">
                                <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                            </div>
                            <div class="mate_descrip">
                                <p class="mate_name"><?php echo $value['full_name'];?></p>
                                <p class="mate_following">Folowing 34 Authers</p>
                                <p><?php echo $value['school_name'];?></p>
                                <p><?php echo $value['course_name'];?></p>
                                <?php if($value['srid'] != ''){?>
                                <button class="btn btn_black_normal" data-type="studyment-request" data-id="<?php echo $value['user_id'];?>" disabled>Request Already Sent</button>
                                <?php }else{ ?>
                                <button class="btn btn_green" data-type="studyment-request" data-id="<?php echo $value['user_id'];?>">Add Studymates</button>
                                <?php } ?>

                            </div>
                        </div>
                        <!--//card-->
                    <?php
                        $i++;
                        }
                    ?>
                    </div>
                <?php
                    }
                    else{
                         echo '<h3>No Studymate Found...</h3>';
                    }
                ?>            
            </div>
            <?php 
                if(isset($recommended_studymates)){
            ?>
                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-studymate" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-studymate" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
                <!-- //Controls -->
          <?php
            }
          ?>
        </div>       
        <!--//carousel-->
    </div>
</div>
<!--//suggestion-->
</div>
