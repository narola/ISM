 <!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    <!--breadcrumb-->
    <div class="page_header exam_evalue_header">
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
    <div class="col-sm-12 text-right">
        <button class="btn btn_green btn_back" onclick="window.location.href = 'student/my_scoreboard'"><span class="fa fa-backward"></span> Back</button>
    </div>
    <div class="answers">
        <?php 
            if(isset($my_evaluation)){
                $cnt = count($my_evaluation)/2;
                $i = 1;
                $k = 0;
                $total_rows = count($my_evaluation);
                $half = round($total_rows/2);
                foreach ($my_evaluation as $key => $value) {
                    if($i == 1){
                        echo '<div class="col-md-6 col-sm-12"><div class="box">';
                    }
                    ?>
                        <div class="ques_num">
                            <div class="box_header">
                                <h5 class="txt_green">Question:  <span><?= $i?></span></h5>
                            </div>
                            <div class="box_body">
                                <div class="q_div"><p class="ques"><?php echo $value['question_text'];?></p></div>
                                <a href="javascript:void(0);" class="expand_ques fa fa-angle-double-down"></a>
                                <!--answers-->
                                <div class="row">
                                    <div class="col-lg-4 col-sm-6">
                                        <p>Your Answer:</p>
                                    </div>
                                    <?php 
                                        if($value['your_ans'] == $value['correct_ans']){
                                            $class = 'txt_green';
                                        }
                                        else{
                                            $class = 'txt_red';
                                        }

                                    ?>
                                    <div class="col-lg-8 col-sm-6 <?php echo $class;?>">
                                        <p><?php if($value['your_ans'] == '')echo '-';else echo $value['your_ans'];?></p>
                                    </div>
                                    <div class="col-lg-4 col-sm-6">
                                        <p>Correct Answer:</p>
                                    </div>
                                    <div class="col-lg-8 col-sm-6">
                                        <p><?php echo $value['correct_ans'];?></p>
                                    </div>
                                </div>
                                <h5>Solution:</h5>
                                <?php 
                                    if($value['solution'] != ''){
                                        ?>
                                            <div class="solution no_solution">
                                                <?php echo $value['solution']; ?>
                                            </div>      
                                        <?php 
                                    }
                                    else{
                                        ?>
                                            <div class="solution no_solution">
                                            </div>
                                        <?php 
                                    }
                                ?>
                            </div>
                        </div>
                    <?php 
                    if($i == $half){
                        echo '</div></div><div class="col-md-6 col-sm-12"><div class="box">';
                    }
                    if($i == $total_rows){
                        echo '</div></div>';
                    }

                      $i++;      
                }
            } 
        ?>
    </div>
    <!--//answers-->
</div>
<!--//main-->
<script type="text/javascript">
        $(document).ready(function() {
            $('.expand_ques').click(function(){
                if($(this).hasClass('fa-angle-double-down')){
                    var ph = $(this).parent().children('.q_div').children('p.ques').height();
					if(ph>115){
						$(this).parent().children('.q_div').animate( 
							{ height:ph}, 
							{ queue:false, duration:300 });
						$(this).removeClass('fa-angle-double-down');
						$(this).addClass('fa-angle-double-up');	
					}								
                }
                else{
                    $(this).parent().children('.q_div').animate( 
                        { height: "115px"}, 
                        { queue:false, duration:300 });
                    $(this).removeClass('fa-angle-double-up');
                    $(this).addClass('fa-angle-double-down');
                }
            });
        });
</script>