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
                    <div class="box_body">	
                        <div class="form-group col-sm-12 col-md-6 col-lg-8 padding_r15_">
                            <input type="text" class="form-control" placeholder="Exam Name">
                        </div>                     
                        <div class="form-group col-sm-12 col-md-6 col-lg-4 select no-padding">
                            <select class="form-control">
                                <option>Course Name</option>
                                <option>MS</option>
                            </select>
                        </div>
                        <div class="form-group col-sm-12 col-md-6 col-lg-4 select padding_r15_">
                            <select class="form-control">
                                <option>Subject Name</option>
                                <option>Chemistry</option>
                                <option>Physics</option>
                            </select>
                        </div>
                        <div class="form-group col-sm-12 col-md-6 col-lg-4 select padding_r15_">
                            <select class="form-control">
                                <option>Passing Percentage</option>
                                <option>80%</option>
                            </select>
                        </div>   
                        <div class="form-group col-sm-12 col-md-6 col-lg-4 btn_switch no-padding">
                        	<label>Exam For : </label>
                            <div class="switch_btns">
                            	<button class="btn btn_red">Subject</button>                                
                            	<button class="btn no_btn">Topic</button>
                            </div>
                        </div> 
                        <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
                            <select class="form-control">
                                <option>Academic Year</option>
                                <option>2015</option>
                            </select>
                        </div>
                        <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
                            <select class="form-control">
                                <option>Exam Type</option>
                                <option>Subject</option>
                                <option>Topic</option>
                            </select>
                        </div>                    
                        <div class="form-group col-sm-12 col-md-6 col-lg-3 select padding_r15_">
                            <select class="form-control">
                                <option>Exam Duration (MIN)</option>
                                <option>30 min</option>
                                <option>1 Hour</option>
                            </select>
                        </div>  
                        <div class="form-group col-sm-12 col-md-6 col-lg-3 select no-padding">
                            <select class="form-control">
                                <option>Attemp Count</option>
                                <option>1</option>
                                <option>2</option>
                            </select>
                        </div>  
                        <div class="clearfix"></div>
                    </div>
                    <div class="box_header">
                        <h3><span class="icon icon_info"></span>Exam Schedule</h3>
                    </div>
                    <div class="box_body admin_controls">	
                        <div class="form-group dob col-sm-6 padding_r15_">
                            <input type="text" class="form-control" placeholder="DD-MM-YYYY">
                            <label><input type="checkbox">Notify Student Via SMS</label>
                        </div>
                        <div class="form-group dob col-sm-6 no-padding">
                            <input type="text" class="form-control" placeholder="DD-MM-YYYY">
                            <label><input type="checkbox">Notify Student Via SMS</label>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="box_header">
                        <h3><span class="icon icon_info"></span>Exam Instruction</h3>
                    </div>
                    <div class="box_body">
                    	<div class="form-group col-md-6 col-lg-8 padding_r15_">
                        	<textarea name="editor2" id="editor2" class="form-control"></textarea>
                    	</div>
                        <div class="form-group col-md-6 col-lg-4 option_radio">
                        	<div>
                            	<label>Declare Results</label>
                                <div class="check_div">
                                    <label><input type="radio" name="1"> Yes</label>
                                    <label><input type="radio" name="1"> No</label>
								</div>
                            </div>
                            <div>
                            	<label>Negative Marking</label>
                                <div class="check_div">
                                    <label><input type="radio" name="2"> Yes</label>
                                    <label><input type="radio" name="2"> No</label>
								</div>
                            </div>
                            <div>
                            	<label>Random Questions</label>
                                <div class="check_div">
                                    <label><input type="radio" name="3"> Yes</label>
                                    <label><input type="radio" name="3"> No</label>
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
                </div>
                <!--//exam box-->
			</div>
            <!--//main-->