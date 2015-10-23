<!--main-->
<div class="col-sm-7 main main2">
	<!--breadcrumb-->
    <div class="row page_header">
    	<div class="col-sm-12 text-center">
        	<h4>Please Read Instructions Carefully</h4>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--instruction-->
    <div class="box instruction">
    	<div class="box_header">	
        	<h3><span class="assign_no">Assignment No.:33</span><span>Organic Chemistry</span></h3>
        </div>
        <div class="box_body">
            <div class="col-sm-8">
                <h3 class="txt_green no-margin">General Instruction</h3>
                <ol class="instru_list">
                    <li>1 This is linear text.</li>
                    <li>2 You cannot skip any question.</li>
                    <li>3 There is no negative marking.</li>
                    <li>4 You will receive an email with final score.</li>
                    <li>5 You must score 80% to pass this test.</li>
                </ol>
            </div>
            <?php
            //$x = '<span id="msgs"></span> <span class="txt_green"></span>';
              // if($exam_status == 2){
                //$x = '';
               //}
                ?>
                <div class="col-sm-4 text-right" id="exam_status">
                    <h4 data-type="exam_sts_msg"><?php //echo $x; ?></h4>
                </div>
                                      
            <div class="clearfix"></div>
            <div class="text-center">
                <?php
                $dis = ''; 
                if($this->active_h['exam_st'] != 'started' ){
                    $dis = 'disabled';
                }
                //if($exam_status !== 2){
                ?>
                    <button data-id="<?php echo $exam_id;?>"data-type="class_exam_start_request" class="btn btn_green" <?php echo $dis; ?> >Start Exam <span class="fa fa-chevron-right"></span></button>
                <?php
                //}
                ?>
            </div>
        </div>
    </div>
    <!--//Instruction-->
</div>
<!--//main-->