
<!--main-->
            <div class="col-sm-7 main main2">
                <!--breadcrumb-->
                <div class="row page_header">
                    <div class="col-sm-12">
                        <fieldset>
                          <legend><?php echo $my_scoreboard['exam_category'];?></legend>                          
                          <h4 class="txt_green"><?php echo $my_scoreboard['exam_name'];?></h4>
                        </fieldset>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--score-->
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <h1 class="big_score">Your Score Is : <span><?php echo $my_scoreboard['percentage'];?>%</span></h1>
                    </div>
                    
                </div>
                <div class="row evaluation">
                    <div class="col-sm-12 col-md-6">
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
                            <div >
                                <button class="btn btn-block btn_green">Click Here to View Answers</button> 
                            </div>
                            <div class="score_publish">
                                <span>Make My Score Public?</span><span><button class="btn btn_green">Yes</button><button class="btn btn_black_normal">No</button></span>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-6">
                        <div class="box">
                            <!--1-->
                            <div>
                                <p>Organic Chemistry - I</p>
                                <div class="progress">
                                  <div id="test" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 0">
                                    <span class="sr-only">80% Complete (success)</span>
                                  </div>
                                </div>
                            </div>
                            <!--2-->
                            <div>
                                <p>Organic Chemistry - II</p>
                                <div class="progress">
                                  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                                    <span class="sr-only">75% Complete (success)</span>
                                  </div>
                                </div>
                            </div>
                            <!--3-->
                            <div>
                                <p>ELements</p>
                                <div class="progress">
                                  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0;">
                                    <span class="sr-only">60% Complete (success)</span>
                                  </div>
                                </div>
                            </div>
                            <!--4-->
                            <div>
                                <p>Nolecules</p>
                                <div class="progress">
                                  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="68" aria-valuemin="0" aria-valuemax="100" style="width: 0;">
                                    <span class="sr-only">68% Complete (success)</span>
                                  </div>
                                </div>
                            </div>
                            <div >
                                <button class="btn btn-block btn_black_normal">View Evaluation</button> 
                            </div>
                        </div>
                    </div>
                </div>
                <!--//score-->
                
            </div>
            <!--//main-->
            <script>
    $(".progress-bar").animate({
    width: "<?php echo $my_scoreboard['percentage'];?>%"
}, 2500);
</script>