 <!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header exam_evalue_header">
        <!--header -left-->
        <div class="col-md-6 col-sm-12">
            <div class="row">
                <div class="col-sm-12 text-center">
                    <h3 class="txt_green text-uppercase"><strong><?php echo $my_scoreboard['exam_name'];?></strong></h3>
                </div>
                <div class="col-sm-6"><p class="txt_grey"><i>Duration : <?php echo $my_scoreboard['totmin'];?></i></p></div>
                <div class="col-sm-6 text-right"><p class="txt_grey"><i>Exam Type : <?php echo $my_scoreboard['exam_category'];?></i></p></div>
            </div>
        </div>
        <!--header -right-->
        <div class="col-md-6 col-sm-12 mark_scored">
            <div class="row">
                <div class="col-sm-6 text-center">
                    <h4>Mark Scored : <span class="txt_green"><?php echo $my_scoreboard['percentage'];?>%</span></h4>
                </div>
                <div class="col-sm-6 text-right no-padding">
                    <div><p>Correct Answers : <span class="txt_blue"><?php echo $my_scoreboard['correct_answers'];?></span></p></div>
                    <div><p>Incorrect Answers : <span class="txt_brightred"><?php echo $my_scoreboard['incorrect_answers'];?></span></p></div>
                </div>
            </div>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--answers-->
    <div class="row answers">
        <?php 
            if(isset($my_evaluation)){
                $cnt = count($my_evaluation)/2;
                $i = 0;
                $k = 0;
                foreach ($my_evaluation as $key => $value) {
                    if($i == 0){
                    ?>
                        <div class="col-md-6 col-sm-12">
                        <div class="box">
                    <?php
                    }
                    if($i == $cnt && $k == 0){
                    ?>
                        <div class="col-md-6 col-sm-12">
                        <div class="box">
                    <?php
                    }
                    if($i < $cnt){
                    ?>       
                        <div class="ques_num">
                            <div class="box_header">
                                <h5 class="txt_green">Question: <span>1</span></h5>
                            </div>
                            <div class="box_body">
                                <p class="ques">When parallel meet which effect occurs?</p>
                                <!--answers-->
                                <div class="row">
                                    <div class="col-lg-4 col-sm-6">
                                        <p>Your Answer:</p>
                                    </div>
                                    <div class="col-lg-8 col-sm-6">
                                        <p>Raywander Effect</p>
                                    </div>
                                    <div class="col-lg-4 col-sm-6">
                                        <p>Correct Answer:</p>
                                    </div>
                                    <div class="col-lg-8 col-sm-6">
                                        <p>Tindal Effect</p>
                                    </div>
                                </div>
                                <h5>Solution:</h5>
                                <div class="solution no_solution">
                                </div>
                            </div>
                        </div>  
                    <?php
                        $i++;
                    }
                    if($i == $cnt){
                        echo '</div></div>';
                    }

                    if($i == $cnt && $k <= $cnt){
                        echo 'hi';
                    }

                        
                }
            } 
        ?>
    </div>
    <!--//answers-->
</div>
<!--//main-->