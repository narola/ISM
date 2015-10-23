<script>
    $(document).on('click','li[data-type="search-type"]',function(){
        $('.row.filter_bar ul li').removeClass('active');
        $(this).addClass('active');
    });
</script>
<style>
        .avatar1 {
            border: 6px solid rgba(255, 255, 255, 0.1);
            border-radius: 70%;
            height: 200px;
            margin: 0 auto 15px;
            overflow: hidden;
            width: 200px;
        }
    </style>
<!--main-->
<div class="col-sm-7 main main2 stydymates"> 
    <!--tabs-->
    <div class="tabs_studymate row">
        <button class="btn col-sm-6 no-padding active" id="ms">My Studymates</button>
        <button class="btn col-sm-6 no-padding" id="fs">Find Studymates</button>
    </div>
    <!--//tabs-->
    <!--my studymates-->
    <div class="my_studymates">
        <div class="box general_cred">
           <div class="box_body studyamte_list mCustomScrollbar" data-mcs-theme="minimal-dark">
            <?php
                if(isset($my_studymates)){
                    foreach ($my_studymates as $key => $value) {
                ?>
                    <!--item1-->
                    <div class="study_mate ol" data-id="<?php echo $value['user_id'];?>">
                        <div class="col-lg-9 col-md-8 col-sm-7">
                            <div class="mate_user_img">
                                <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                            </div>
                            <h4><?php echo $value['full_name'];?></h4>
                            <p>Student from <?php echo $value['school_name'];?></p>
                            <p class="txt_green"><?php echo $value['course_name'];?></p>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-5">
                            <button class="btn btn_green btn-block" data-name="<?php echo $value['full_name'];?>" data-id="<?php echo $value['user_id'];?>" data-school="<?php echo $value['full_name'];?>" data-profile="<?php echo $value['profile_link'];?>" data-course="<?php echo $value['course_name'];?>" id="view_profile">View Profile</button>
                            <div class="form-group select">
                                <select class="form-control" name="action" id="action_studymate" data-name="<?php echo $value['full_name'];?>" data-id="<?php echo $value['user_id'];?>" data-school="<?php echo $value['full_name'];?>" data-profile="<?php echo $value['profile_link'];?>" data-course="<?php echo $value['course_name'];?>">
                                    <option value="0">Studymates</option>
                                    <option value="1">Remove Studymate</option>
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
    </div>
    <!--//my studymates-->
    <!--search-->
    <div class="search_studymate">
        <!--search bar-->
        <div class="row search_bar">
            <input type="text" class="form-control" placeholder="Type here to search you mate" data-type="study_mate_search">
            <a href="#" class="icon icon_search" style="float:right;"></a>
        </div>
        <!--//search bar-->
        <!--filterbar-->
        <div class="row filter_bar">
            <ul>
                <li class="active" data-type="search-type" data-id="people"><a href="javascript:void(0);">People</a></li>
                <li data-type="search-type" data-id="school"><a href="javascript:void(0);">School</a></li>
                <!-- <li data-type="search-type" data-id="area"><a href="javascript:void(0);">Area</a></li> -->
                <li data-type="search-type" data-id="course"><a href="javascript:void(0);">Course</a></li>
            </ul>
        </div>
        <!--//filterbar-->
        <!--search result-->
        <h5 class="search_result_label">Search Result for</h5>
        <div class="box general_cred">
            <div class="box_body studyamte_list studymate_request mCustomScrollbar" data-mcs-theme="minimal-dark" id="search_result">
                <?php 
                    if(isset($find_studymates) && sizeof($find_studymates) > 0){ 
                    $i = 1;
                    foreach ($find_studymates as $key => $value) {
                ?>
                <!--item1-->
                <div class="study_mate">
                    <div class="col-lg-9 col-md-8 col-sm-7">
                        <div class="mate_user_img">
                            <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                        </div>
                        <h4><?php echo $value['full_name'];?></h4>
                        <p><?php echo $value['school_name'];?></p>
                        <p class="txt_green"><?php echo $value['course_name'];?></p>
                    </div>
                    <div class="col-lg-3 col-md-4 col-sm-5">
                        <?php if($value['srid'] != ''){?>
                        <button class="btn btn_black_normal btn-block" data-type="studyment-request" data-id="<?php echo $value['user_id'];?>" disabled>Request Already Sent</button>
                        <?php }else{ ?>
                        <button class="btn btn_green btn-block" data-type="studyment-request" data-id="<?php echo $value['user_id'];?>">Add Studymates</button>
                        <?php } ?>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!--//item1-->
                <?php 
                    } 
                ?>
                <div class="text-center">
                    <a href="javascript:void(0);" data-start="4" data-type="load-studymate-more" class="search_result_label">View More</a>
                </div>
                <?php
                }
                ?>
                
            </div>
        </div>
        <!--//search result-->
        
    </div>
    <!--//search-->
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
                        if(isset($recommended_studymates) && sizeof($recommended_studymates) > 0){
                    ?>
                        <div class="item active" id="active-recomonded">
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
                                    <p>
                                    <?php 
                                        if(strlen($value['school_name']) > 30)
                                            echo substr($value['school_name'],0, 29).'.....';
                                        else
                                            echo $value['school_name'];
                                    ?> 
                                    </p>
                                    <p class="txt_green"><?php echo $value['course_name'];?></p>
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
                            echo '<label class="txt_grey txt_red">No studymate found</label>';
                        }
                    ?>            
                </div>
                <?php 
                    if(isset($recommended_studymates) && sizeof($recommended_studymates) > 0){
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
<!--//main-->
<!-- Modal -->
<div class="modal fade" id="close_mate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:600px;margin-top:220px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">CONFIRMATION FORM</h4>
                <small><?php echo date("d F Y",strtotime(date('Y-m-d')));?></small>
            </div>
            <div class="modal-body">
                <code style="font-size:large;">Are sure for want to remove <b data-type="close-studymate-name" data-id="remove-name"></b> from studymates list?</code>
                    <h4 class="notice_by"><button class="btn btn_black_normal" data-type="close-studymate">OK</button></h4>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<!-- /.modal -->
<!-- Modal -->
<div class="modal fade" id="view_profile_model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:600px;margin-top:120px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">STUDYMATE PROFILE</h4>
                <small><?php echo date("d F Y",strtotime(date('Y-m-d')));?></small>
            </div>
            <div class="modal-body">
                <div data-type="profile_pic" class="avatar1">
                </div>
                <div class="basic_info">
                    <h3 data-type="user-name" class="txt_green text-uppercase"></h3>
                    <p>
                    <span class="fa fa-map-marker"></span>
                    From Ghana
                    </p>
                    <p data-type="course-name">
                        <span class="fa fa-graduation-cap"></span>
                    </p>
                    <p>
                    <span class="fa fa-birthday-cake"></span>
                    March 21, 1992
                    </p>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.modal -->
<script type="text/javascript">
    $(document).ready(function() {
        $('#ms').click(function(){
            $('.my_studymates').css('display','block');
            $('.search_studymate').css('display','none');
            $(this).addClass('active');
            $('#fs').removeClass('active');
        }); 
        $('#fs').click(function(){
            $('.my_studymates').css('display','none');
            $('.search_studymate').css('display','block');
            $(this).addClass('active');
            $('#ms').removeClass('active');
        });   
    });
</script>