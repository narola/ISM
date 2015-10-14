<!--main-->
<div class="col-sm-7 main main2 general_cred">
	<!--breadcrumb-->
		<div class="row page_header">
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
    <div class="box add_exam_form">
        <div class="box_header">
            <h3><span class="icon icon_info"></span>Exam Details</h3>
        </div>
        <form method="post">    
            <div class="box_body">

                <div class="form-group col-sm-12 col-md-6 col-lg-8 padding_r15_">
                    <input type="text" class="form-control" name="exam_name" placeholder="Exam Name">
                </div>                     

                <div class="form-group col-sm-12 col-md-6 col-lg-4 select no-padding">
                    <select class="form-control" name="course_id">
                        <option value="">Course Name</option>
                        <?php 
                          if(!empty($all_courses)){ 
                            foreach($all_courses as $course) {
                          ?> 
                        <option value="<?php echo $course['id']; ?>" <?php echo set_select('course_id', $course['id']); ?>> <?php echo $course['course_name']; ?></option>
                        <?php }  }else{ ?>
                        <option > No Course</option>
                        <?php } ?>    
                    </select>
                </div>
                
                <div class="form-group col-sm-12 col-md-6 col-lg-4 select padding_r15_">
                    <select class="form-control" name="subject_id" >
                        <option>Subject Name</option>
                         <?php 
                          if(!empty($all_subjects)){ 
                            foreach($all_subjects as $subject) {
                          ?> 
                        <option value="<?php echo $subject['id']; ?>" <?php echo set_select('subject_id', $subject['id']); ?>> 
                            <?php echo $subject['subject_name']; ?>
                        </option>
                        <?php }  }else{ ?>
                        <option > No Subjects</option>
                        <?php } ?>  
                    </select>
                </div>
                <div class="form-group col-sm-12 col-md-6 col-lg-4 select padding_r15_">
                    <select class="form-control" name="pass_percentage">
                        <option value="">Passing Percentage</option>
                        <option value="30">30%</option>
                        <option value="40">40%</option>
                        <option value="50">50%</option>
                        <option value="60">60%</option>
                        <option value="70">70%</option>
                    </select>
                </div>   
                <div class="form-group col-sm-12 col-md-6 col-lg-4 btn_switch no-padding">
                	<label>Exam Type : </label>
                    <div class="switch_btns">
                    	<button class="btn btn_red" onclick="return false;" value="subject">Subject</button>                                
                    	<button class="btn no_btn" onclick="return false;" value="topic">Topic</button>
                    </div>
                </div> 
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
                    <select name="classroom_id" class="form-control">
                        <option value="">Select Classroom</option>
                         <?php 
                          if(!empty($all_classrooms)){ 
                            foreach($all_classrooms as $classroom) {
                          ?> 
                        <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('subject_id', $classroom['id']); ?>> 
                            <?php echo $classroom['class_name']; ?>
                        </option>
                        <?php }  }else{ ?>
                        <option > No Subjects</option>
                        <?php } ?>
                    </select>
                </div>
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
                    <select class="form-control" name="exam_category">
                        <option value="">Exam Category</option>
                        <option value="ISM_Mock">ISM_Mock</option>
                        <option value="WASSCE">WASSCE</option>
                        <option value="EndOfTerm">End Of Term</option>
                        <option value="Tutorial">Tutorial</option>
                    </select>
                </div>                    
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
                    <select class="form-control" name="duration">
                        <option value="">Exam Duration (MIN)</option>
                        <option value="30">30 min</option>
                        <option value="60">1 Hour</option>
                        <option value="90">1.5 Hour</option>
                        <option value="120">2 Hour</option>
                        <option value="150">2.5 Hour</option>
                        <option value="180">3 Hour</option>
                    </select>
                </div>  
                <div class="form-group col-sm-12 col-md-6 col-lg-3 select no-padding">
                    <select class="form-control" name="attempt_count">
                        <option value="">Attemp Count</option>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </div>  
                <div class="clearfix"></div>
            </div>

            <div class="box_header">
                <h3><span class="icon icon_info"></span>Exam Schedule</h3>
            </div>
            <div class="box_body admin_controls">	

                <div class="form-control">
                   <div data-date-format="dd-mm-yyyy" data-date="12-02-2012"  class="form-group dob col-sm-6 padding_r15_">
                        <input type="text" name="start_date" id="start_date" placeholder="Exam Start"  class="form-control ">
                        <label><input type="checkbox">Notify Student Via SMS</label>
                    </div> 
                </div>

                 <div class="form-control">
                   <div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="end_date" class="form-group dob col-sm-6 padding_r15_">
                        <input type="text" name="end_date" placeholder="Exam End" 
                        value="<?php  echo set_value("end_date");?>" class="form-control">
                        <label><input type="checkbox">Notify Student Via SMS</label>
                    </div> 
                </div>

                <div class="clearfix"></div>
            </div>
            <div class="box_header">
                <h3><span class="icon icon_info"></span>Exam Instruction</h3>
            </div>
            <div class="box_body">
            	<div class="form-group col-md-6 col-lg-8 padding_r15_">
                	<textarea name="editor1" id="editor1" class="form-control"></textarea>
            	</div>
                <div class="form-group col-md-6 col-lg-4 option_radio">
                	<div>
                    	<label>Declare Results</label>
                        <div class="check_div">
                            <label><input type="radio" name="declare_results" value="yes"> Yes</label>
                            <label><input type="radio" name="declare_results" value="no" > No</label>
    					</div>
                    </div>
                    <div>
                    	<label>Negative Marking</label>
                        <div class="check_div">
                            <label><input type="radio" name="negative_marking" value="yes" > Yes</label>
                            <label><input type="radio" name="negative_marking" value="no" > No</label>
    					</div>
                    </div>
                    <div>
                    	<label>Random Questions</label>
                        <div class="check_div">
                            <label><input type="radio" name="random_question" value="yes" > Yes</label>
                            <label><input type="radio" name="random_question" value="no" > No</label>
    					</div>
                    </div>
                </div>
                <div class="col-sm-12 text-center btn_group">
                	<button class="btn btn_green">Save</button>
                	<button class="btn btn_red">Set Question</button>
                	<button class="btn btn_black_normal">Cancle</button>
                </div>
                <div class="clearfix"></div>
            </div>
        </form>
    </div>
    <!--//exam box-->
</div>
<!--//main-->
<script src="http://cdn.ckeditor.com/4.5.3/standard/ckeditor.js"></script>

<script type="text/javascript">
    
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