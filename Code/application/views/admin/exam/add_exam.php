<!--main-->
<div class="col-sm-7 main main2 general_cred mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Assessment</a></li>
              <li><a href="#">Exams</a></li>
              <li class="active">Add Exam</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
   
    <!--exam box-->
	<div class="col-sm-12">
    <div class="box add_exam_form">
        <div class="box_header">
            <h3><span class="icon icon_info"></span>Exam Details</h3>
        </div>

        <div class="alert alert-danger<?php if(empty(strip_tags(validation_errors(),''))){ echo ' hide';} ?> ">
            <?php echo validation_errors('',''); ?>
        </div>

        <form method="post">    
            <div class="box_body">

                <div class="form-group col-sm-12 col-md-6 col-lg-8 padding_r15_">
					<label>Exam Name</label>
                    <input type="text" class="form-control" name="exam_name" value="<?php echo set_value('exam_name'); ?>" placeholder="Exam Name">
                </div>                     
				<div class="form-group col-sm-12 col-md-6 col-lg-4 btn_switch no-padding">
                    <label>Exam Type : </label>
					<div>
                    <input type="checkbox" name="exam_type" id="exam_type"
                        <?php if($_POST){ echo set_checkbox('exam_type','on'); }else{ echo 'checked'; } ?> data-handle-width="100" ><!--data-size="mini"-->
					</div>
                </div>
				
                <div class="form-group col-sm-12 col-md-6 col-lg-4 select padding_r15_">
					<label>Course Name</label>
                    <select class="form-control myselect" name="course_id" onchange="fetch_classroom(this.value)">
                        <option value="">Course Name</option>
                        <?php 
                          if(!empty($all_courses)){ 
                            foreach($all_courses as $course) {
                          ?> 
                        <option value="<?php echo $course['id']; ?>" <?php echo set_select('course_id',$course['id']); ?> > 
                                <?php echo $course['course_name']; ?>
                        </option>

                        <?php }  }else{ ?>
                        <option > No Course</option>
                        <?php } ?>    
                    </select>
                </div>
                
                <div class="form-group col-sm-12 col-md-6 col-lg-4 select padding_r15_">

                    <label>Classroom</label>
                    <select name="classroom_id" class="form-control" onchange="fetch_subject(this.value)" id="classroom_id">
                        <option value="">Select Classroom</option>
                        <?php 
                          if(!empty($all_classrooms)){ 
                            foreach($all_classrooms as $classroom) {
                          ?> 
                        <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('classroom_id',$classroom['id']); ?> > 
                                <?php echo $classroom['class_name']; ?>
                        </option>

                        <?php }  }else{ ?>
                        <option > No Classroom </option>
                        <?php } ?>
                    </select>

                </div>
                
                <div class="form-group col-sm-12 col-md-6 col-lg-4 select no-padding">
					<label>Subject</label>
                    <select class="form-control" onchange="get_topics(this.value)" name="subject_id" id="subject_id" >
                        <option value="">Select Subject</option>
                        <?php 
                          if(!empty($all_subjects)){ 
                            foreach($all_subjects as $subject) {
                          ?> 
                        <option value="<?php echo $subject['id']; ?>" <?php echo set_select('subject_id',$subject['id']); ?> > 
                                <?php echo $subject['subject_name']; ?>
                        </option>

                        <?php }  }else{ ?>
                        <option > No Subjects </option>
                        <?php } ?>
                    </select>    
                </div>

                <div class="form-group col-sm-12 tutorial_topic 
                <?php if($_POST){ echo isset($_POST['exam_type']) ? 'hide':''; } else{ echo 'hide'; } ?> select no-padding topic">

                    <label>Topic</label>
                    <select class="form-control" name="topic_id" id="topic_id" >
                        <option value="">Select Topic</option>
                        <?php 
                          if(!empty($all_topics)){ 
                            foreach($all_topics as $topic) {
                          ?> 
                        <option value="<?php echo $topic['id']; ?>" <?php echo set_select('topic_id',$topic['id']); ?> > 
                                <?php echo $topic['topic_name']; ?>
                        </option>

                        <?php }  }else{ ?>
                        <option > No Topics </option>
                        <?php } ?>
                    </select>    
                </div>   
                
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
					<label>Passing Percentage</label>
                    <select class="form-control" name="pass_percentage">
                        <option value="">Passing Percentage</option>
                        <option value="30" <?php echo set_select('pass_percentage','30'); ?> >30%</option>
                        <option value="40" <?php echo set_select('pass_percentage','40'); ?> >40%</option>
                        <option value="50" <?php echo set_select('pass_percentage','50'); ?> >50%</option>
                        <option value="60" <?php echo set_select('pass_percentage','60'); ?> >60%</option>
                        <option value="70" <?php echo set_select('pass_percentage','70'); ?> >70%</option>
                    </select>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
					<label>Exam Category</label>
                    <select class="form-control" name="exam_category" id="exam_category">
                        <option value="">Exam Category</option>
                        <option value="ISM_Mock" <?php echo set_select('exam_category','ISM_Mock'); ?> >ISM_Mock</option>
                        <option value="WASSCE" <?php echo set_select('exam_category','WASSCE'); ?> >WASSCE</option>
                        <option value="EndOfTerm" <?php echo set_select('exam_category','EndOfTerm'); ?> >End Of Term</option>
                        <?php if(isset($_POST['exam_type']) || !$_POST) { ?>
                            <option value="Tutorial" <?php echo set_select('exam_category','Tutorial'); ?> >Tutorial</option>
                        <?php } ?>
                    </select>
                </div>                    
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
					<label>Exam Duration</label>
                    <select class="form-control" name="duration">
                        <option value="">Exam Duration (MIN)</option>
                        <option value="30" <?php echo set_select('duration','30');?> >30 min</option>
                        <option value="60" <?php echo set_select('duration','60');?> >1 Hour</option>
                        <option value="90" <?php echo set_select('duration','90');?> >1.5 Hour</option>
                        <option value="120" <?php echo set_select('duration','120');?> >2 Hour</option>
                        <option value="150" <?php echo set_select('duration','150');?> >2.5 Hour</option>
                        <option value="180" <?php echo set_select('duration','180');?> >3 Hour</option>
                    </select>
                </div>  
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select no-padding">
					<label>Attemp Count</label>
                    <select class="form-control" name="attempt_count">
                        <option value="">Attemp Count</option>
                        <option value="0" <?php echo set_select('attempt_count','0');?> >0</option>
                        <option value="1" <?php echo set_select('attempt_count','1');?> >1</option>
                        <option value="2" <?php echo set_select('attempt_count','2');?> >2</option>
                    </select>
                </div>  
                <div class="clearfix"></div>
            </div>

            <div class="box_header">
                <h3><span class="icon icon_info"></span>Exam Schedule</h3>
            </div>
            <div class="box_body admin_controls">	

               <div data-date-format="dd-mm-yyyy" data-date="12-02-2012"  class="form-group dob col-sm-6 padding_r15_">
					<label>Exam Start Date</label>
                    <input type="text" name="start_date" id="start_date" placeholder="Exam Start"  class="form-control ">
                    <!-- <label><input type="checkbox">Notify Student Via SMS</label> -->
                </div> 

               <div class="form-group col-sm-6 padding_r15_">
					<label>Exam Time</label>
					<div class="input-group bootstrap-timepicker">
                        <input id="timepicker1" name="start_time" type="text" class="form-control input-small">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
                    </div>
                </div> 

                <div class="clearfix"></div>
            </div>
            <div class="box_header">
                <h3><span class="icon icon_info"></span>Exam Instruction</h3>
            </div>
            <div class="box_body">
            	<div class="form-group col-md-6 col-lg-8 padding_r15_">
                	<textarea name="instructions" id="editor1" class="form-control"></textarea>
            	</div>
                <div class="form-group col-md-6 col-lg-4 option_radio">
                	<div>
                    	<label>Declare Results</label>
                        <div class="check_div">
                            <label><input type="radio" name="declare_results" <?php echo set_radio('declare_results','yes'); ?> value="yes"> Yes</label>
                            <label><input type="radio" name="declare_results" <?php echo set_radio('declare_results','no',TRUE); ?> value="no" > No</label>
    					</div>
                    </div>
                    <div>
                    	<label>Negative Marking</label>
                        <div class="check_div">
                            <label><input type="radio" name="negative_marking" <?php echo set_radio('negative_marking','yes'); ?> value="yes" > Yes</label>
                            <label><input type="radio" name="negative_marking" <?php echo set_radio('negative_marking','no',TRUE); ?> value="no" > No</label>
    					</div>
                    </div>
                    <div>
                    	<label>Random Questions</label>
                        <div class="check_div">
                            <label><input type="radio" name="random_question" <?php echo set_radio('random_question','yes'); ?> value="yes" > Yes</label>
                            <label><input type="radio" name="random_question" <?php echo set_radio('random_question','no',TRUE); ?> value="no" > No</label>
    					</div>
                    </div>
                </div>
                <div class="col-sm-12 text-center btn_group">
                    <input type="hidden" value="save" id="button_type" name="button_type">
                	<button class="btn btn_green" onclick="set_hidden('save')" >Save</button>
                	<button class="btn btn_red" onclick="set_hidden('set_ques')">Save & Set Question</button>
                	<a href="<?php echo $prev_url; ?>" class="btn btn_black_normal">Cancel</a>
                </div>
                <div class="clearfix"></div>
            </div>
        </form>
    </div>
	</div>
    <!--//exam box-->
</div>
<!--//main-->
<script src="assets/ckeditor_std/ckeditor.js"></script>

<script type="text/javascript">

    function set_hidden(button_data){

        if(button_data == 'save'){
            $('#button_type').val('save');
        }else{
            $('#button_type').val('set');
        }
    }

    $(document).ready(function() {

        $(".myselect").select2();

         $('#timepicker1').timepicker({ 
            defaultTime: 'value',
            minuteStep: 1,
            disableFocus: true,
            template: 'dropdown',
            showMeridian:false    
         });

        $("[name='exam_type']").bootstrapSwitch();
        $('.bootstrap-switch-handle-on').text('Subject');
        $('.bootstrap-switch-handle-off').text('Topic');

       $('input[name="exam_type"]').on('switchChange.bootstrapSwitch', function(event, state) {
          // console.log(this); // DOM element
          // console.log(event); // jQuery event
          console.log(state); // true | false

            if(state == true){
                
                $('.tutorial_topic').addClass('hide');

                $('#exam_category').append($('<option>', {
                    value: 'Tutorial',
                    text: 'Tutorial'
                }));
            }else{

                $('.tutorial_topic').removeClass('hide');

                $("#exam_category option[value='Tutorial']").each(function() {
                    $(this).remove();
                });
            }

        });

    });

    function fetch_classroom(course_id){
        $.ajax({

            url:'<?php echo base_url()."common/fetch_classroom"; ?>',
            type:'post',
            data:{course_id:course_id},
            success:function(data){
                $('#classroom_id').html(data);
                $('#subject_id').html('<option value="">Select Subject</option>');
                $('#topic_id').html('<option value="">Select Topic</option>');
            }
            
        });
    }

    function fetch_subject(class_id){
        $.ajax({

            url:'<?php echo base_url()."common/fetch_subject"; ?>',
            type:'post',
            data:{class_id:class_id},
            success:function(data){
                $('#subject_id').html(data);
                $('#topic_id').html('<option value="">Select Topic</option>');
            }
        });
    }

    function get_topics(subject_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_topics_tutorials"; ?>',
           type:'POST',
           data:{subject_id:subject_id},
           success:function(data){
              $("#topic_id").html(data);
           }
        });
    }

    $(document).ready(function(){

        $('#start_date').datepicker({
            format: 'yyyy-mm-dd'
        });

        $('#end_date input').datepicker({
            format: 'yyyy-mm-dd'
        });

    });
    
     CKEDITOR.replace( 'editor1',{removePlugins : "a11yhelp,about,bidi,blockquote,clipboard," +
"contextmenu,dialogadvtab,div,elementspath,enterkey,entities,popup," +
"filebrowser,find,fakeobjects,flash,floatingspace,listblock,richcombo," +
"forms,horizontalrule,htmlwriter,iframe,image,indent," +
"link,maximize," +
"newpage,pagebreak,pastefromword,pastetext,preview,print," +
"resize,save,menubutton,scayt,selectall,showblocks," +
"smiley,sourcearea,specialchar,tab,table," +
"tabletools,templates,undo,wsc"} );

</script>