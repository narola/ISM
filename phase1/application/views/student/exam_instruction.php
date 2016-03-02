<!--main-->
            <div class="col-sm-7 main main2 mscroll_custom">
            	<!--breadcrumb-->
                <div class="page_header">
                	<div class="col-sm-12 text-center">
                    	<h4>Please Read Instructions Carefully</h4>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--instruction-->
                <div class="col-sm-12">
                <div class="box instruction">
                	<div class="box_header">	
                    	<h3><span class="assign_no">Topic: </span><span><?php echo $exam['topic_name']; ?></span></h3>
                    </div>
                    <div class="box_body">
                        <div class="col-md-8 col-xs-12">
                            <h3 class="txt_green no-margin">General Instruction</h3>
                            <ol class="instru_list">
                                <li>1 This is linear text.</li>
                                <li>2 You cannot skip any question.</li>
                                <li>3 There is no negative marking.</li>
                                <li>4 You must score 80% to pass this test.</li>
                            </ol>
                        </div>
                        <?php
                        $x = '<span id="msgs"></span> <span class="txt_green"></span>';
                           if($exam_status == 2){
                            $x = '';
                           }
                            ?>
                            <div class="col-md-4 col-xs-12 text-center" id="exam_status">
                                <h4 data-type="exam_sts_msg"><?php echo $x; ?></h4>
                                <h4 class="txt_red" data-type="exam_message"></h4>
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
                                <button data-type="exam_start_request" class="btn btn_green" <?php echo $dis; ?> >Start Exam <span class="fa fa-chevron-right"></span></button>
                            <?php
                            //}
                            ?>
                        </div>
                    </div>
                </div>
                </div>
                <!--//Instruction-->
			</div>
            <!--//main-->