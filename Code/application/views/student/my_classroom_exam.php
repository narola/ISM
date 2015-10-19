<!--main-->
<div class="col-sm-7 main main2">
    <!--banner-->
     <div class="row exam_banner">
        <div class="col-sm-12 text-center">
            <div class="banner_text">
                <h3>Class</h3>
                <h3>XII<br>Science</h3>
            </div>
        </div>
     </div>   
     <!--//banner-->
     <!--filter-->
     <div class="row">
        <div class="col-sm-12 filter exams_filter">
            <div class="form-group">
                <select class="form-control">
                    <option>Subject</option>
                </select>
            </div>
            <div class="form-group">
                <select class="form-control">
                    <option>Topic</option>
                </select>
            </div>
            <div class="form-group">
                <select class="form-control">
                    <option>Exam Type</option>
                </select>
            </div>
            <div class="form-group">
                <select class="form-control">
                    <option>WASSCE Year</option>
                </select>
            </div>
        </div>
     </div> 
     <!--//filetr-->
     <!--exams-->
     <div  class="row">
        <div class="col-sm-12 exams_wrapper">
            <?php
                if(isset($my_subject)){
                    foreach ($my_subject as $key => $value) {
                        
            ?>
            <!--card1-->
            <div class="col-sm-12 col-md-6 col-lg-4">
                 <div class="box exam_card">
                    <div class="box_header exam_chem">
                        <div class="color_wrapper"></div>
                        <h3><?php echo $value['subject_name'];?></h3>                            
                        <a href="#" class="icon icon_option no-margin"></a>  
                        <label class="label label_black">15 Exams</label>
                        <!-- <div class="user_profile_img">
                            <img src="images/user7.jpg">
                        </div>   -->                                   
                        <div class="clearfix"></div>                    
                    </div>
                    <ul class="exams_holder mCustomScrollbar" data-mcs-theme="minimal-dark">
                        <?php 
                            foreach ($my_exam as $exam_value) {
                                if($exam_value['subject_id'] == $value['subject_id']){
                                    if($exam_value['per'] != ''){
                                        $url = '/evaluation';
                                        $percentage = $exam_value['per'];
                                    }
                                    else{
                                        $url = '/evaluation';   
                                        $percentage = '';
                                    }
                        ?>
                            <li><a href="#">
                                <?php 
                                    if(strlen($exam_value['exam_name']) > 30)
                                        echo substr($exam_value['exam_name'],0, 30).'.....';
                                    else
                                        echo $exam_value['exam_name'];
                                ?>
                                <span class="result"><?php if($percentage != '')echo $percentage.'%';?></span>
                            </a></li>
                        <?php 
                                }
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
<!--//main-->