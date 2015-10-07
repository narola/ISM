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
                                <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$value['profile_link'];?>">
                            </div>
                            <h4><?php echo $value['full_name'];?></h4>
                            <p>Student from <?php echo $value['school_name'];?></p>
                            
                            <a href="#">Following 34 Authers</a>
                        </div>
                        <div class="col-lg-3 col-md-4 col-sm-5">
                            <button class="btn btn_green btn-block">View Profile</button>
                            <div class="form-group select">
                                <select class="form-control" name="action" id="action_studymate" data-id="<?php echo $value['user_id'];?>">
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
<!--//main-->


<!-- Modal -->
<div class="modal fade" id="close_mate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:500px;margin-top:220px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">CONFIRMATION FORM</h4>
                <small>Sep 7, 2015</small>
            </div>
            <div class="modal-body">
                <p><code><h4>Are sure for want to remove from studymates list?</h4></code></p>
                    <h4 class="notice_by"><button class="btn btn_black_normal" data-type="close-studymate">OK</button></h4>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<!-- /.modal -->