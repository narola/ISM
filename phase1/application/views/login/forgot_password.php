<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>ISM Login</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
	<link href="<?php echo base_url();?>assets/css/bootstrap.min.css" rel="stylesheet">
    <!--custom css-->
    <link href="<?php echo base_url();?>assets/css/ism_style.css" rel="stylesheet">
    <link href="<?php echo base_url();?>assets/css/responsive.css" rel="stylesheet">
    <link href="<?php echo base_url();?>assets/css/icon.css" rel="stylesheet">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="<?php echo base_url();?>assets/images/graduate.png" sizes="32x32" />
</head>
	
<body class="login_background">
	<div class="container-fluid">
    	<div class="row">        	
            <div class="login_board text-center">
                <div class="col-sm-12">
                	<img src="<?php echo base_url();?>assets/images/logo.png" class="logo">
                    <form class="login_form" method="post">
                    	<div class="form-group">
                    		<input placeholder="Enter Your Email" class="form-control" type="email" id="emailid" name="emailid">
                        </div>
                        <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('emailid'),''))){ echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('emailid'),''); ?>
                        </div>
                        <div class=" alert alert-danger <?php if(empty($this->session->flashdata('msg'))){ echo 'hide';} ?>">
                            <?php echo $this->session->flashdata('error');?>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn_black">Submit
                                <span class="fa fa-chevron-right"></span>
                            </button>
                        </div>
                    </form>
                    <label class="">If Still not Registerd, Please Click <a href="#sample" data-toggle="modal"><b>HERE</b></a></label>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
    <!-- Modal -->
        <div class="modal fade" id="sample" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header notice_header text-center">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">REQUEST FORM</h4>
                        <small><?php echo date("d F Y",strtotime(date('Y-m-d')));?></small>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="send_request" method="post">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">Name :</label>
                                <div class="col-sm-7">
                                    <input type="text" required class="form-control" id="request_name" name="request_name" placeholder="Name">
                                    
                                    <div class="alert alert-danger" style="display:none" id="err3">
                                        Name field is required
                                    </div>
                                    <div class="alert alert-danger" style="display:none" id="err4">
                                        Invalid name
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-3 control-label">Email :</label>
                                <div class="col-sm-7">
                                    <input type="email" required class="form-control" id="request_email" name="request_email" placeholder="Email">
                                    
                                    <div class="alert alert-danger" style="display:none" id="err1">
                                        Email field is required
                                    </div>
                                </div>
                            </div>

                            <div class="form-group" >
                              <label class="col-sm-3 control-label">School Grade :</label>
                              <div class="col-sm-7">
                              <select class="form-control" name="school_grade" 
                                  onchange="fetch_school_from_grade(this.value)" id="school_grade">
                                    <option selected value=""> Select School Grade</option>
                                      <option value="A" <?php echo set_select('school_grade', 'A'); ?>>A</option>
                                      <option value="B" <?php echo set_select('school_grade', 'B'); ?>>B</option>
                                      <option value="C" <?php echo set_select('school_grade', 'C'); ?>>C</option>
                                      <option value="D" <?php echo set_select('school_grade', 'D'); ?>>D</option>
                                      <option value="E" <?php echo set_select('school_grade', 'E'); ?>>E</option>
                                  </select>
                                </div>
                            </div>

                          <div class="form-group">
                              <label class="col-sm-3 control-label">Select School :</label>
                              <div class="col-sm-7">
                              <select class="form-control js-example-basic-single" id="school_id" name="school_id" onchange="school_id_error()">
                                 <option  selected value=""> Select School</option>
                                  <?php 
                                      if(!empty($schools)) {
                                        foreach($schools as $school) { 
                                      ?>
                                      <option value="<?php echo $school['id']; ?>" <?php echo set_select('school_id', $school['id']); ?> >
                                            <?php echo $school['school_name']; ?>
                                      </option>
                                  <?php } }else{ ?>
                                      <option disabled > No Schools Found</option>  
                                  <?php } ?> 
                              </select>
                              </div>
                              <?php echo form_error('school_id','<div class="alert alert-danger school_id_error">','</div>'); ?>
                          </div>

              
                <div class="form-group">
                      <label class="col-sm-3 control-label">Course :</label>
                      <div class="col-sm-7">
                      <select class="form-control " name="course_id" id="course_id"
                        onchange="fetch_classroom(this.value)" >
                          <option selected disabled> Select Course</option>
                          <?php 
                              if(!empty($courses)) {
                                foreach($courses as $course) { 
                              ?>
                              <option value="<?php echo $course['id']; ?>" <?php echo set_select('course_id', $course['id']); ?>>
                                   <?php echo $course['course_name']; ?>
                              </option>
                          <?php } }else{ ?>
                              <option disabled > No Course Found</option>  
                          <?php } ?>      
                      </select>
                      </div>
                      <?php echo form_error('course_id','<div class="alert alert-danger course_id_error">','</div>'); ?>
                  </div>

                  <div class="form-group">
                      <label class="col-sm-3 control-label">Classroom :</label>
                      <div class="col-sm-7">
                      <select class="form-control" name="classroom_id" id="classroom_id" onchange="classroom_id_error()">
                          <option selected disabled> Select Classroom</option>
                          <?php 
                              if(!empty($classrooms)) {
                                foreach($classrooms as $classroom) { 
                              ?>
                              <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('classroom_id', $classroom['id']); ?>>
                                   <?php echo $classroom['class_name']; ?>
                              </option>
                          <?php } }else{ ?>
                              <option disabled > No Classroom Found</option>  
                          <?php } ?>
                      </select>
                      </div>
                      <?php echo form_error('classroom_id','<div class="alert alert-danger classroom_id_error">','</div>'); ?>
                  </div>

                  <div class="form-group">
                      <label class="col-sm-3 control-label">Year :</label>
                      <div class="col-sm-7">
                      <select class="form-control" name="year_id" id="year_id">
                          <option value="<?php echo $cur_year; ?>"><?php echo $cur_year; ?></option>
                          <option value="<?php echo $next_year; ?>"><?php echo $next_year; ?></option>
                      </select>
                      </div>
                  </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-10">
                                    <input type="hidden" name="send_request" value="new_credentials">
                                    <input type="submit" class="btn btn_black_normal" value="SEND REQUEST">
                                </div>
                            </div>
                        </form>
                        
                        <div class="clearfix"></div>
                  </div>
                </div>
            </div>
        </div>
    <!-- /.modal -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<?php echo base_url();?>assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<?php echo base_url();?>assets/js/bootstrap.min.js"></script> 
    <script type="text/javascript">

     $( "#emailid" ).click(function() {
          $('.alert').addClass("hide");
      });

    </script>
</body>
</html>
