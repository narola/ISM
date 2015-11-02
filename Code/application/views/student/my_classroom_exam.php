<script>
    $(document).on('change', 'select[data-type="exam-type"]', function(){
        val = $(this).val();
        if(val != ''){
           
            $('#frm_class_exam').submit();
        }
    });
</script>
<!--main-->
<div class="col-sm-7 main main2 mscroll_custom">
    <!--banner-->
     <div class="exam_banner">
        <div class="col-sm-12 text-center">
            <div class="banner_text">
                <h3>Class</h3>
                <h3><?php echo $user_class_name;?></h3>
            </div>
        </div>
     </div>   
     <!--//banner-->
     <!--filter-->
     <div class="">
        <div class="col-sm-12 filter exams_filter">

            <form action="" method="post" id="frm_class_exam" class="form-inline">
                <!-- <div class="form-group">
                    <select class="form-control">
                        <option>Subject</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control">
                        <option>Topic</option>
                    </select>
                </div> -->
                <div class="form-group">
                    <select class="form-control" data-type="exam-type" name="exam_type" id="exam-type">
                        <option value="">Exam Type</option>
                        <option value="0">All</option>
                        <option value="ISM_Mock">ISM Mock</option>
                        <option value="WASSCE">WASSCE</option>
                        <option value="EndOfTerm">End Of Term</option>
                    </select>
                    <script>
                        sort_by = '<?php echo $exam_type;?>';
                        document.getElementById('exam-type').value = sort_by;
                    </script>
                </div>
                <!-- <div class="form-group">
                    <select class="form-control">
                        <option>WASSCE Year</option>
                    </select>
                </div> -->
            </form>

        </div>
     </div> 
     <!--//filetr-->
     <!--exams-->
     <div  class="">
        <div class="col-sm-12 exams_wrapper">
            <?php
                if(isset($my_subject)){
                    foreach ($my_subject as $key => $value) {
                        
            ?>
            <!--card1-->
            <div class="col-sm-12 col-md-6 col-lg-4">
                 <div class="box exam_card">
                    <div class="box_header exam_chem">
                        <div class="exam_img_holder">
                            <img src="<?php echo UPLOAD_URL.'/'.$value['subject_image'];?>" onerror="this.src='<?php echo UPLOAD_URL;?>/subjects/_dev_6GKA-Objectives-copy.png'">
                        </div>
                        <h3><?php echo $value['subject_name'];?></h3>                            
                        <!-- <a href="#" class="icon icon_option no-margin"></a>   -->
                        <label class="label label_black"><?php echo $value['tot_exam'];?> Exams</label>
                        <!-- <div class="user_profile_img">
                            <img src="images/user7.jpg">
                        </div>   -->                                   
                        <div class="clearfix"></div>                    
                    </div>
                    <ul class="exams_holder mscroll_custom">
                        <?php 
                            foreach ($my_exam as $exam_value) {
                                if($exam_value['subject_id'] == $value['subject_id']){
                                    $is_running_cls = '';
                                    if($exam_value['exam_status'] == 'started' && $exam_value['remaining_time'] > 0){
                                        $is_running_cls = '<img src="assets/images/clock.GIF">&nbsp;';
                                    }

                                    if($exam_value['id'] != '' && $exam_value['exam_status'] == 'finished'){
                                        $url = 'student/my_scoreboard/index/'.$exam_value['exam_id'];
                                        $percentage = $exam_value['per'];
                                    }
                                    else{

                                        $url = 'student/class-exam-instruction/'.$exam_value['exam_id'];   
                                        $percentage = '';
                                    }
                        ?>
                            <li><a href="<?php echo $url;?>"><?php echo $is_running_cls; ?>
                                <?php 
                                    if(strlen($exam_value['exam_name']) > 29)
                                        echo substr($exam_value['exam_name'],0, 29).'.....';
                                    else
                                        echo $exam_value['exam_name'];
                                ?>
                                <span class="result"><?php if($percentage != '')echo $percentage.'%';?></span>
                            </a></li>
                        <?php 
                                }
                            }
                            if($value['tot_exam'] == 0){
                                echo '<li><center><label class="txt_grey txt_red">no exams</label></center></li>';
                            }
                        ?>

                    </ul>
                 </div>
             </div>
            <!--//card1-->
            <?php 
                    }
                }
            ?>
        </div>
     </div>
     <!--//exams-->
</div>
<!--//main