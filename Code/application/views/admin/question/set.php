<!--main-->
            <div class="col-sm-7 main main2 main_wide">
            	<!--breadcrumb-->
           		<div class="row page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Assessment</a></li> 
                          <li class="active">Question Bank</li>
                        </ol>                 
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--main content-->
                <div class="question_bank">
                	<div class="col-sm-6 general_cred">
                    	<!--box-->
                        <div class="box">
                            <div class="box_header filter">
                                <form method="post">
                                <div class="form-group">
                                    <select class="form-control" name="course_id" onchange="get_classes(this.value)" id="course_id">
                                        <option value=''>Course</option>
                                        <?php if(!empty($courses)){ 
                                            foreach ($courses as $course) { ?>
                                                <option value="<?php echo $course['id']; ?>"><?php echo $course['course_name']; ?></option>        
                                        <?php } 
                                    }?>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <select class="form-control" name="classroom_id" onchange="get_subjects(this.value)" id="classroom_id">
                                        <option value=''>Classroom</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <select class="form-control" name="subject_id" onchange="get_topics(this.value)" id="subject_id">
                                        <option value=''>Subject</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <select class="form-control" name="topic_id" id="topic_id" >
                                        <option value=''>Topic</option>
                                    </select>
                                </div>
                                <div class="form-group no_effect search_link text-right">
                                    <button class="btn btn-link icon icon_search" type="submit"></button>
                                </div>
                                <div class="clearfix"></div>
                            </form>
                            </div>
                            <!--box_body-->
                            <div class="box_body">
                           		<div class="black_header">	
                                	<h4>Question Bank</h4>
                                </div>
                                <div class="question_list">

                                    <?php if(!empty($questions)){
                                        $i=1;
                                        foreach ($questions as $question) { ?>
                                            


                                    <!--q1-->
                                    <div class="question_wrapper">
                                        <h5 class="txt_red">Question <span><?php echo $i ?></span></h5>
                                        <p class="q_info txt_blue">Category : <span class="txt_green"><?php echo $question['subject_name']; ?></span></p>
                                        <p class="q_info pull-right txt_blue">Created By : <span class="txt_green"><?php echo $question['full_name']; ?></span></p>
                                        <p class="ques"><?php echo $question['question_text']; ?></p>
                                        <div class="answer_options_div">
                                            <ol>
                                                <?php foreach ($question['choices'] as $choice) { ?>
                                                    <li><?php echo $choice; ?></li>
                                                <?php } ?>
                                            </ol>
                                        </div>
                                        <a class="fa fa-angle-double-down"></a>
                                        <div class="notice_action">                                            
                                            <a href="#" class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Edit"></a>
                                            <a href="#" class="icon icon_copy_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Copy"></a>
                                            <a href="#" class="icon fa fa-heart" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Delete"></a>
                                            <input type="checkbox"><label class="save_box"></label>
                                        </div>
                                        
                                    </div>
                                    <!--//q1-->
                                    
                                    <?php   
                                    $i++;
                                     }

                                    } ?>
                                </div>
                           	</div>
                            <!--//box-body-->
                            <div class="box_footer">
                            	<button class="btn btn_red">Add New Question</button>
                                <button class="btn btn_green pull-right no-margin">Add to Preview</button>
                            </div>
                        </div>
                        <!--//box-->
                    </div>
                    <div class="col-sm-6 general_cred preview">
                    	<!--box-->
                        <div class="box">
                            <div class="box_header">
                               <h3>Preview</h3>
                            </div>
                            <!--box_body-->
                            <div class="box_body">
                           		
                                <div class="question_list">
                                	<?php /*<!--q1-->
                                    <div class="question_wrapper">
                                    	<div class="question_left">                                    
                                            <h5 class="txt_red">Question <span>1</span></h5>                                       
                                            <p class="ques">In which of the following forms do the cells in Clamydomonas, Volvox and Spirogyra exist respectively?</p>
                                            <div class="answer_options_div">
                                                <ol>
                                                    <li>Single, colony and filament</li>
                                                    <li>Single, filament and colony</li>
                                                    <li>Filament, part of living organism and filament</li>
                                                    <li>Single, part of living organism and filament</li>
                                                </ol>
                                            </div>
                                        </div>
                                        <div class="notice_action">  
                                        	<a href="#" class="icon icon_hand" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Move"></a>                                          
                                            <a href="#" class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Edit"></a>
                                            <a href="#" class="icon icon_copy_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Copy"></a>
                                            <a href="#" class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Delete"></a>
                                            
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                    <!--//q1-->
                                    <!--q2-->
                                    <div class="question_wrapper">
                                    	<div class="question_left">
                                            <h5 class="txt_red">Question <span>2</span></h5>                                        
                                            <p class="ques">In which of the following forms do the cells in Clamydomonas, Volvox and Spirogyra exist respectively?</p>
                                            <div class="answer_options_div">
                                                <ol>
                                                    <li>Single, colony and filament</li>
                                                    <li>Single, filament and colony</li>
                                                    <li>Filament, part of living organism and filament</li>
                                                    <li>Single, part of living organism and filament</li>
                                                </ol>
                                            </div>
                                        </div>
                                        <div class="notice_action">                                            
                                            <a href="#" class="icon icon_hand" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Move"></a>
                                            <a href="#" class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Edit"></a>
                                            <a href="#" class="icon icon_copy_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Copy"></a>
                                            <a href="#" class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="Delete"></a>
                                            
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                    <!--//q2-->
                                    */ ?>
                                </div>
                           	</div>
                            <!--//box-body-->
                            
                        </div>
                        <!--//box-->
                    </div>
                    <div class="clearfix"></div>
                </div>
                <!--main content-->                
			</div>
            <!--//main-->
            <script type="text/javascript">
            function get_classes(course_id){
                $.ajax({
                   url:'<?php echo base_url()."admin/question/ajax_get_classrooms"; ?>',
                   type:'POST',
                   data:{course_id:course_id},
                   success:function(data){
                      $("#classroom_id").html(data);
                      $('#subject_id').val('');
                      $('#topic_id').val('');
                   }
                });
          }

          function get_subjects(classroom_id){
                $.ajax({
                   url:'<?php echo base_url()."admin/question/ajax_get_subjects"; ?>',
                   type:'POST',
                   data:{classroom_id:classroom_id},
                   success:function(data){
                      $("#subject_id").html(data);
                      $('#topic_id').val('');
                   }
                });
          }

          function get_topics(subject_id){
                $.ajax({
                   url:'<?php echo base_url()."admin/question/ajax_get_topics"; ?>',
                   type:'POST',
                   data:{subject_id:subject_id},
                   success:function(data){
                      $("#topic_id").html(data);
                   }
                });
          }

        <?php if(!empty($_GET['course'])) { ?>
            $('#course_id').val('<?php echo $_GET["course"];?>');  
        <?php } ?>

        <?php if(!empty($_GET['class'])) { ?>
            $('#classroom_id').val('<?php echo $_GET["class"];?>');  
        <?php } ?>

        <?php if(!empty($_GET['subject'])) { ?>
            $('#subject_id').val('<?php echo $_GET["subject"];?>');    
        <?php } ?>

        jQuery(document).ready(function() {
            jQuery('.question_wrapper .fa-angle-double-down').click(function(){
                if(jQuery(this).parent('.question_wrapper').children('.answer_options_div').css('display')=='none'){
                    jQuery(this).parent('.question_wrapper').children('.answer_options_div').slideDown('fast');
                    jQuery(this).addClass('fa-angle-double-up');
                    jQuery(this).removeClass('fa-angle-double-down');
                }
                else{
                    jQuery(this).parent('.question_wrapper').children('.answer_options_div').slideUp('fast');
                    jQuery(this).removeClass('fa-angle-double-up');
                    jQuery(this).addClass('fa-angle-double-down');
                };              
            });
        });
        </script>