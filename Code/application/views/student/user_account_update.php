`<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM Registration</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
	<base href="<?php echo base_url();?>">
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <!--date_picker-->
    <link rel="stylesheet" href="assets/css/datepicker.css">
    <!--custom css-->
    <link href="assets/css/ism_style.css" rel="stylesheet">
    <link href="assets/css/responsive.css" rel="stylesheet">
    <link href="assets/css/icon.css" rel="stylesheet">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="assets/images/graduate.png" sizes="32x32" />
    <style>
        .avatar1 {
            border: 6px solid rgba(255, 255, 255, 0.1);
            border-radius: 70%;
            height: 150px;
            margin: 75px auto 15px;
            overflow: hidden;
            width: 150px;
        }
    </style>
    <script type="text/javascript">
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah')
                        .attr('src', e.target.result)
                        .attr('style','border-radius:50%;width:200px;height:200px;');
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</head>

<body>
	<div class="container-fluid">
		<div class="row reg_header">
        	<div class="col-sm-12">
            	<img src="assets/images/site_logo.png">
            </div>
        </div>
        <div class="row reg_bg">
        	<div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
            	<form action="" method="post" enctype="multipart/form-data">
                    <!--box1-->
                    <div class="box">
                        <div class="box_header">
                            <h3><span class="icon icon_info"></span>Personal Information</h3>
                        </div>
                        <?php 
                            $error = $this->session->flashdata('error_cur_pass'); 
                            $success = $this->session->flashdata('success'); 
                        ?>
                        <div class="box_body">
                            <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
                                <?php echo strip_tags($error) ; ?>
                            </div>
                            <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
                                <?php echo strip_tags($success) ; ?>
                            </div>
                        	<!--avatar-->
                            <div class="col-sm-4 text-center visible-xs">
                                <div class="avatar">               
                                	<img src="<?php echo base_url() ?>assets/images/avatar.png">
                                </div>
                                <div class="upload">
                                	<input type="file" name="profile_image">
                                    <span>Upload Profile Picture</span>
                                </div>
                            </div>
                            <!--//avatar-->
                        	<div class="col-sm-8">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Full Name" name="full_name" value="<?php echo isset($full_name)?$full_name : set_value('full_name');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('full_name'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('full_name'),'') ; ?>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="Email Address" name="email_id" value="<?php echo isset($email_id)?$email_id : set_value('email_id');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('email_id'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('email_id'),'') ; ?>
                                </div>
                                <div class="form-group select">
                                    <select class="form-control" name="gender" id="gender">
                                    	<option value="">Gender</option>
                                        <option value="male">Male</option>
                                        <option value='female'>Female</option>
                                    </select>
                                </div>
                                <script>
                                    <?php $gender = isset($gender) ? $gender : set_value('gender');?>
                                    document.getElementById('gender').value = '<?= $gender; ?>';
                                </script>    
                                <div class="form-group dob">
                                    <div class="input-append date" id="birthdate" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
                                        <input type="text" class="form-control" placeholder="Date of Birth" name="birthdate" id='dp' value="<?php echo isset($birthdate)?$birthdate:set_value('birthdate');?>">
                                    </div>
                                </div>
                                <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('birthdate'),''))){ echo 'hide';} ?>">
                                        <?php echo strip_tags(form_error('birthdate'),''); ?>
                                </div>
                                <div class="form-group age">
                                    <input type="text" class="form-control" placeholder="Age" disabled >
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Contact Number" name="contact_number" value="<?php echo isset($contact_number)?$contact_number:set_value('contact_number');?>">
                                </div>
                                <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('contact_number'),''))){ echo 'hide';} ?>">
                                        <?php echo strip_tags(form_error('contact_number'),''); ?>
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Home Address" name="home_address"><?php echo isset($home_address)?$home_address:set_value('home_address');?></textarea>
                                </div>                         
                            </div>
                            <!--avatar-->
                            <div class="col-sm-4 text-center hidden-xs">

                                <?php 
                                    if(isset($profile_pic)){
                                ?>
                            	<div class="avatar1" >
                                	<img src="<?php echo UPLOAD_URL.'/'.$profile_pic;?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                </div>
                                <?php 
                                    }
                                    else{
                                ?>
                                <div class="avatar" >
                                    <img id="blah" src="<?php echo base_url() ?>assets/images/avatar.png">
                                </div>
                                <?php
                                    }
                                ?>
                                <div class="upload">
                                	<input type="file" name="profile_image_1" onchange="readURL(this);">
                                    <span>Upload Profile Picture</span>
                                </div>
                            </div>
                            <!--//avatar-->
                            <div class="col-sm-12"> 
                                       <div class="form-group small_input select">
                                    <select class="form-control" name="country_id" onchange="get_states(this.value)" id="country_id" >
                                        <option selected value="">Select Country</option> 
                                        <?php 
                                          if(!empty($countries)){ 
                                            foreach($countries as $country) {
                                          ?> 
                                        <option value="<?php echo $country['id']; ?>"> <?php echo $country['country_name']; ?></option>
                                        <?php }  }else{ ?>
                                        <option > No Country</option>
                                        <?php } ?>
                                    </select>
                                </div>
                                        
                                <div class="form-group small_input select">
                                    <select name="state_id" id="state_id" onchange="get_cities(this.value)" class="form-control">
                                        <option selected value="">Select State</option>
                                        <?php 
                                          if(!empty($states)){ 
                                            foreach($states as $state) {
                                          ?> 
                                        <option value="<?php echo $state['id']; ?>"> <?php echo $state['state_name']; ?></option>
                                        <?php }  }else{ ?>
                                        <option > No States</option>
                                        <?php } ?>
                                    </select>
                                </div>
                                
                                <div class="form-group small_input select">
                                    <select name="city_id" id="city_id" class="form-control">
                                        <option selected value="">Select City</option>
                                        <?php 
                                          if(!empty($cities)){ 
                                            foreach($cities as $city) {
                                          ?> 
                                        <option value="<?php echo $city['id']; ?>"> <?php echo $city['city_name']; ?></option>
                                        <?php }  }else{ ?>
                                        <option > No Country</option>
                                        <?php } ?>
                                    </select>
                                </div>
                                 <script>
                                    <?php $country = isset($country_id) ? $country_id : set_value('country_id');?>
                                    document.getElementById('country_id').value = '<?= $country; ?>';

                                    <?php $state = isset($state_id) ? $state_id : set_value('state_id');?>
                                    document.getElementById('state_id').value = '<?= $state; ?>';
                           
                                    <?php $city = isset($city_id) ? $city_id : set_value('city_id');?>
                                    document.getElementById('city_id').value = '<?= $city; ?>';
                                </script>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <!--//box1-->
                    <!--box1-->
                    <div class="box">
                        <div class="box_header">
                            <h3><span class="icon icon_school"></span>School Information</h3>                            
                        </div>
                        <div class="box_body">
                            <div class="col-sm-12">	
                            	<p  style='display:<?php echo isset($display)?$display:"";?>'>You are registered for following school, 
                                    <a href="#sample" data-toggle="modal">Click Here</a> if it’s not your school.</p>
                               
                                <div class="school_info">
                                    <div class="form-group small_input">
                                        <label>School Name</label>
                                        <label class="label_form"><?php echo isset($school_information['school_name'])?$school_information['school_name']:$school_name;?></label>
                                    </div>
                                    <div class="form-group small_input">
                                        <label>Class</label>
                                        <label class="label_form"><?php echo isset($school_information['class_name'])?$school_information['class_name']:$class_name;?></label>
                                    </div>
                                    <div class="form-group small_input">
                                        <label>Academic Year</label>
                                        <label class="label_form"><?php echo isset($school_information['academic_year'])?$school_information['academic_year']:$academic_year;?></label>
                                    </div>
                                    <div class="form-group small_input">
                                        <label>Region of School</label>
                                        <label class="label_form">&nbsp;</label>
                                        
                                    </div>
                                    <div class="form-group small_input">
                                        <label>District of School</label>
                                        <label class="label_form"><?php if(isset($school_information['district_name'])){ echo $school_information['district_name'];}else{if(isset($district_name)) echo $district_name; else echo '&nbsp;';}?></label>
                                    </div>
                                    <div class="form-group small_input ">
                                        <label>Program / Course</label>
                                        <label class="label_form"><?php echo isset($school_information['course_name'])?$school_information['course_name']:$course_name;?></label>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <!--//box1-->
                    <!--box1-->
                    <div class="box">
                        <div class="box_header">
                            <h3><span class="icon icon_lock"></span>Manage Account</h3>                            
                        </div>
                        <div class="box_body">
                            <div class="col-sm-8">	
                            	<div class="form-group">
                                	<input type="text" class="form-control" placeholder="Username" name="username" value="<?php echo isset($username)?$username:set_value('username');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('username'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('username'),'') ; ?>
                                </div>
                                <div class="form-group">
                                	<input type="password" class="form-control" placeholder="Current Password" name="cur_password" value="<?php echo isset($password)?$password:set_value('cur_password');?>">
                                </div>
                                <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('cur_password'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('cur_password'),''); ?>
                                </div>
                                <div class="form-group">
                                	<input type="password" class="form-control" placeholder="New Password" name="new_password" value="<?php echo set_value('new_password');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('new_password'),''))){ echo 'hide';}?>">
                                    <?php echo strip_tags(form_error('new_password'),'') ; ?>
                                </div>  
                                <div class="form-group">
                                	<input type="password" class="form-control" placeholder="Confirm Password" name="con_password" value="<?php echo set_value('con_password');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('con_password'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('con_password'),'') ; ?>
                                </div>  
	                        </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <!--//box1-->
                    <div class="col-sm-12 text-center reg_btns">
                    	<input type="hidden" value="" name="todo" id="todo">
                        <a href="<?php echo site_url();?>/student/home" class="btn_black btn">Cancel</a>
                        <?php if(isset($school_information['is_my_school']) && $school_information['is_my_school'] == 1){?>
                            <a disabled data-toggle="tooltip" data-placement="top" title="You have already request for change school" class="btn_black btn btn_green">submit</a>
                        <?php }else{ ?>
                            <input type="submit" name="btnsubmit" class="btn_black btn btn_green" value="Submit">
                        <?php } ?>
                    </div>
                </form>
            </div>
        </div>
        <footer class="row reg_footer">
        	<div class="col-sm-12">
            	<p>©2015 ISM. All Rights Reserved.</p>
            </div>
        </footer>
    </div>
     <!-- Modal -->
        <div class="modal fade" id="sample" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header notice_header text-center">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">REQUEST FORM</h4>
                        <small>Sep 7, 2015</small>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="" onsubmit="return send_email();" method="post">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input type="email" class="form-control" id="email" name="request_email" placeholder="Email">
                                    <br>
                                    <div class="alert alert-danger" style="display:none" id="err1">
                                        Email field is required
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="col-sm-2 control-label">Message</label>
                                <div class="col-sm-10">
                                   <textarea class="form-control" placeholder="Write school information..." name="message" id="message"></textarea>
                                   <br>
                                    <div class="alert alert-danger" style="display:none" id="err2">
                                        Message field is required
                                    </div> 
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <input type="hidden" name="send_request" value="change">
                                    <input type="submit" class="btn btn_black_normal" value="SEND REQUEST">
                                </div>
                            </div>
                        </form>
                        <h4 class="notice_by">Gilbert Addoh<span>ISM Admin</span></h4>
                        <div class="clearfix"></div>
                  </div>
                </div>
            </div>
        </div>
    <!-- /.modal -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script> 
    <script src="assets/js/bootstrap-datepicker.js"></script> 
    <script type="text/javascript">
        function get_states(country_id){
            $.ajax({
               url:'<?php echo base_url()."student/user_account/ajax_get_states"; ?>',
               type:'POST',
               data:{country_id:country_id},
               success:function(data){
                  $('#state_id').html(data);
               }
            });
        }

        function get_cities(state_id){
            $.ajax({
               url:'<?php echo base_url()."student/user_account/ajax_get_cities"; ?>',
               type:'POST',
               data:{state_id:state_id},
               success:function(data){
                  $('#city_id').html(data);
               }
            });
        }

        function enabled_all(){
            $('#school_id').prop('disabled', false);
            $('#class_id').prop('disabled', false);
            $('#year_id').prop('disabled', false);
            $('#district_id').prop('disabled', false);
            $('#program_id').prop('disabled', false);
            $('#todo').val('enabled');
        }
        $('#birthdate input').datepicker({
            format: 'yyyy-mm-dd'
        });

        function send_email(){
            email = $('#email').val();
            message = $('#message').val();
       
            if(email == '' && message == ''){
                $('#err2').show();
                $('#err1').show();   
            }
            else if(message == '' && email != ''){
                $('#err2').show();
                $('#err1').hide();
            }
            else if(email == '' && message != ''){
                $('#err1').show();
                $('#err2').hide();
            }
            else {
                return true;
            } 
            return false;
        }

        $(function () {
          $('[data-toggle="tooltip"]').tooltip()
        })


</script>


</body>
</html>