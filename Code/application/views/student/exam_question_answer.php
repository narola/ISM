<div class="row">
        	
            <!--main-->
            <div class="col-sm-7 main main_wide_2">
            	<!--breadcrumb-->
                <div class="row page_header">
                	<div class="col-sm-12">
                    	<fieldset>
                          <legend>ISM Mock Test</legend>                          
                          <h4 class="txt_green">Organic Chemistry Test - 133</h4>
                        </fieldset>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--question-->
                <div class="row">
                	<div class="col-sm-12">
                    	<div class="box question_box">
                        	<div class="box_header">
                            	<p>Question Number : <strong id="q_no"><?php echo $current_no; ?></strong></p>
                                <p>Time Spent : <strong id="time_spent">00:00</strong></p>
                                <div class="clearfix"></div>
                            </div>
                            <!--box_body-->
                            <div class="box_body">
                                <div class="question text-center">
                                    <p><?php echo $current_question['question_text']; ?></p>
                                </div>
                                <div class="ans_option_div">
                                	<p><strong>Options :</strong></p>
                                    <div class="ans_options">
                                        <?php foreach ($question_choices as $key => $value) {
                                            ?>
                                            <label><input type="radio" name="option" data-id="<?php echo $value['id']; ?>"><?php echo $value['choice_text']; ?></label>
                                            <?php
                                        } ?>                                    	
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <!--box_body-->
                            <div class="box_footer">
                            	<button class="btn btn_green" data-type="question_responce" data-status="R" data-id="<?php echo $current_question['id'] ?>">Review Later</button>
                                <button class="btn btn_green" data-type="clear_responce">Clear Responce</button>
                                <button class="btn btn_green" data-type="question_responce" data-status="S" data-id="<?php echo $current_question['id'] ?>" >Skip</button>
                                <button class="btn btn_green" data-type="question_responce" data-status="next" data-id="<?php echo $current_question['id'] ?>" >Next<span class="fa fa-chevron-right"></span></button>
                                
                            </div>
                        </div>                        
                    </div>
                </div>
                <!--//question-->
			</div>
            <!--//main-->
            <!--side right-->
            <div class="sidebar_right_container test_sidebar mCustomScrollbar" data-mcs-theme="minimal-dark"><!--scrollbar" id="style-3-->
            	<div class="sidebar_profile">
                	<div class="user_profile_img">
                       <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL.'/'.$this->session->userdata['user']['profile_pic'];?>">                       
                    </div>
                    <h4><?php echo $this->session->userdata['user']['full_name'];?></h4>
	                <a href="student/user_account">View Profile</a>
                    <div class="clearfix"></div>
                </div>
                <!--test counting-->
                <div class="test_counting text-center">
                	<!--test-finished-->
                	<div class="test_finished">
                    	<h5>Test Finished</h5>
                        <div class="item_chart css">

                            <h2 class="txt_green">10%</h2>
                            <div id="test_finished"></div>
                        </div>
                    </div>
                    <!--time-remainig-->
                    <div class="time_remaining">
                    	<h5>Time Remaining</h5>
                        <!--wrapper-->
                        <div class="clock_wrapper">
                        	<div class="clock">
                            	<div class="clock_block">
	                                <h1>00</h1>
                                </div>
                                <p>Min</p>
                            </div>
                            <div class="colon">:</div>
                            <div class="clock">
                            	<div class="clock_block">
	                                <h1>00</h1>
                                </div>
                                <p>Sec</p>
                            </div>
                        </div>
                        <!--//wrapper-->
                    </div>
                    <!--time-remaining-->
                    <div class="clearfix"></div>
                </div>
                <!--//test couonting-->
                <!--question pallete-->
                <div class="box">
                    <div class="box_header">
                        <h3>Question Pallete</h3>
                    </div>
                    <div class="box_body">
                        <ul class="ques_numbers">
                            <?php foreach($question_id as $key => $value){

                                $data_cls = $cls = '';
                                    foreach($attempted_question as $k => $v){
                                        if($value == $v['question_id']){
                                            $estatus = $v['answer_status'];
                                            if($v['answer_status'] == 'A'){
                                               $data_cls =  $cls = 'answered';
                                            }else if($v['answer_status'] == 'R'){
                                               $data_cls = $cls = 'review_later';
                                            }else if($v['answer_status'] == 'S'){
                                               $data_cls = $cls = 'skipped';
                                            }
                                            break;
                                        }
                                    }
                                    if($key+1 == $current_no){
                                        $cls = 'current';
                                        $data_cls = '';
                                    }
                                ?>
                                <li data-class = "<?php echo $data_cls; ?>" class="<?php echo $cls; ?>" data-id="<?php echo $value; ?>"><a data-no="<?php echo $key+1; ?>" data-type="get_question" href="javascript:void(0)" data-id="<?php echo $value; ?>"><span><?php echo $key+1; ?></span></a></li>
                                <?php
                            } ?>                            

                        </ul>
                        <div class="clearfix"></div>
                        <!--legends-->
						<div class="legends">
                            <h4>Legends :</h4>
                            <ul class="ques_numbers">
                                <li class="answered"><a href="#"></a>Answered</li>
                                <li><a href="#"></a>Not Answered</li>
                                <li class="skipped"><a href="#"></a>Skipped</li>
                                <li class="review_later"><a href="#"></a>Review Later</li>
                                <li class="current"><a href="#"></a>Current Question</li>
                            </ul>
                        </div>
                        <!--//legends-->
                        <div class="text-center">
                        	<button data-type="end_exam" class="btn btn_black" >End Test</button>
                        </div>
                    </div>
                </div>
                <!--//question pallete-->
                
            </div>
            <!--//side right-->
		</div>
        <script>

        $(document).ready(function () {

            $('#test_finished').circleProgress({
                value: 0.20,
                size: 140,
                fill: {
                    color: '#1bc4a3'
                },
                emptyFill : '#000',
                thickness : 15,
                animation:{ duration: 500 } 
            });
            });

        </script>