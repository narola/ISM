<!--main-->
<div class="col-sm-7 main main2 mscroll_custom">
    <!--breadcrumb-->
    <div class="page_header">
        <div class="col-sm-12">
            <fieldset>
              <legend><?php echo $my_scoreboard['exam_category'];?></legend>                          
              <h4 class="txt_green"><?php echo $my_scoreboard['exam_name'];?></h4>
            </fieldset>
        </div>
    </div>
    <!--//breadcrumb-->
    <div class="col-sm-12 text-right">
        <button class="btn btn_green btn_back" onclick="window.location.href = 'student/my_classroom_exam'"><span class="fa fa-backward"></span> Back</button>
    </div>
    <!--score-->
    <div class="reg_bg">
        <div class="col-sm-12 text-center">
            <h1 class="big_score">Your Score Is : <span><?php echo $my_scoreboard['percentage'];?>%</span></h1>
        </div>
        <div class="evaluation">
        <div class="col-sm-12 col-md-6 col-md-offset-3">
            <div class="box">
                <div>
                    <p>Correnct Answers :  <span class="txt_green"><?php echo $my_scoreboard['correct_answers'];?></span></p>
                </div>
                <div>
                    <p>Incorrenct Answers :  <span class="txt_brightred"><?php echo $my_scoreboard['incorrect_answers'];?></span></p>
                </div>
                <div>
                    <p>Unattempted Questions :  <span class="txt_yellow"><?php echo $my_scoreboard['unattampt'];?></span></p>
                </div>
                <div>
                    <p>Total Time Spent :  <span class="txt_grey"><span class="fa fa-clock-o"></span><?php echo $my_scoreboard['totmin'];?> min</span></p>
                </div>
       
                <!--1-->
                <div>
                    <!-- <p>Organic Chemistry - I</p> -->
                    <div class="progress">
                      <div id="test" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 0">
                        <span class="sr-only">80% Complete (success)</span>
                      </div>
                    </div>
                </div>
               
                <div>
                    <a href="student/my_evaluation" class="btn btn-block btn_black_normal">View Evaluation</a> 
                </div>
            </div>
        </div>
    </div>
   
    </div>
</div>
<script>
    $(".progress-bar").animate({
    width: "<?php echo $my_scoreboard['percentage'];?>%"
}, 500);
</script>