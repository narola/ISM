<!--main-->
<div class="col-sm-7 main main2 main mCustomScrollbar" data-mcs-theme="minimal-dark">
     <div class="exam_banner">
        <div class="col-sm-12 text-center">
            <div class="banner_text">
                <h3>My Exams</h3>
                <h3><?php echo $my_course_name;?></h3>
            </div>
        </div>
     </div>  
	 <div class="col-sm-12">
        <?php foreach ($subject_list as $key => $value){ ?>
            <!--exam card 1-->
            <div class="col-sm-12 col-md-6 col-lg-4">
                <div class="box exam_card">
                    <div class="box_header">
                        <h3><?php echo $value['subject_name'];?></h3>                            
                        <!-- <a href="#" class="icon icon_option_dark"></a> -->
                        <span><?php if($value['cnt'] == '') echo 0; echo $value['cnt'];?> Exams</span>    
                        <div class="clearfix"></div>                    
                    </div>
                    <ul class="exams_holder mCustomScrollbar" data-mcs-theme="minimal-dark">
                        <?php 
                            if(isset($my_exam) && sizeof($my_exam)>0 && $value['cnt'] > 0){
                                foreach ($my_exam as $key => $e_value){ 
                                    if($e_value['id'] == $value['id']){ ?>
                                        <li><a href="student/my_scoreboard/index/<?php echo $e_value['exam_id'];?>">
                                        <?php 
                                            if(strlen($e_value['exam_name']) > 30)
                                                echo substr($e_value['exam_name'],0, 30).'.....';
                                            else
                                                echo $e_value['exam_name'];
                                        ?>  
                                        <span class="result"><?php echo $e_value['percentage'];?>%</span></a>
                                        </li>
                                    <?php }
                                } 
                            }else{
                                echo '<li><center><label class="txt_grey txt_red">no exams</label></center></li>';
                            }
                        ?>
                    </ul>
                </div>
            </div>
    <?php } ?>
	</div>
</div>
<!--//main-->
         