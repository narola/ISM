<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM Registration</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
	<base href="<?php echo base_url();?>">
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <!--date_picker-->
    <link rel="stylesheet" href="assets/css/jquery-ui.css">
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
            border-radius: 50%;
            height: 220px;
            margin: 75px auto 15px;
            overflow: hidden;
            width: 220px;
        }
		
    </style>
    <script type="text/javascript">
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                if (!input.files[0].name.match(/\.(jpg|jpeg|png|gif)$/)){
                    alert("not image");
                }else
                {
                    alert("image");
                }


                reader.onload = function (e) {
                    $('#blah')
                        .attr('src', e.target.result)
                        // .attr('style','heigth:162px;width:220px;');
                        // .attr('style','border-radius:50%;width:220px;height:220px;');
                    $('#blah_after')
                        .attr('src', e.target.result)
                        // .attr('style','height:162px;width:220px;');
                        // .attr('style','border-radius:50%;width:220px;height:220px;');
                    $('#aftr_select').show();
                    $('#before_select').hide();
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
                <a href="student/home"><img src="assets/images/site_logo.png"></a>
            </div>
        </div>
        <div class="row reg_bg with_labels">
            <div class="col-md-8 col-md-offset-2 col-sm-10 col-sm-offset-1">
                <form action="" id="user_account_frm" method="post" enctype="multipart/form-data">
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
                            <!-- <div class="col-sm-4 text-center visible-xs">
                                <div class="avatar">               
                                    <img src="<?php //echo base_url() ?>assets/images/avatar.png">
                                </div>
                                <div class="upload">
                                    <input type="file" name="profile_image">
                                    <span>Upload Profile Picture</span>
                                </div>
                            </div> -->
                            <!--//avatar-->
                            
                            <div class="col-sm-8">
                                <div class="form-group col-md-6 col-sm-12" style="padding-left: 0px;">
                                    <!-- <label>Full Name</label>
                                    <input type="text" class="form-control" placeholder="Full Name" name="full_name" value="<?php // echo isset($full_name)?$full_name : set_value('full_name');?>"> -->
                                    <label>First Name</label>
                                    <input type="text" class="form-control" placeholder="First Name" name="first_name" id="first_name" value="<?php echo isset($first_name)?$first_name : set_value('first_name');?>">
                                    <div id="first_name_error" class="alert alert-danger <?php if(empty(strip_tags(form_error('first_name'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('first_name'),'') ; ?>
                                </div>
                                </div>
                                <div class="form-group col-md-6 col-sm-12"  style="padding-right: 0px;">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control" placeholder="Last Name" name="last_name"  id="last_name"  value="<?php echo isset($last_name)?$last_name : set_value('last_name');?>">
                                <div id="last_name_error" class="alert alert-danger <?php if(empty(strip_tags(form_error('last_name'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('last_name'),'') ; ?>
                                </div>
                                </div>
                                <!-- <div class="alert alert-danger <?php  //if(empty(strip_tags(form_error('full_name'),''))){ echo 'hide';} ?>">
                                    <?php // echo strip_tags(form_error('full_name'),'') ; ?>
                                </div> -->
                                
                                <div class="clearfix"></div>
                                <div class="form-group">
                                    <label>Email Id</label>
                                    <input type="email" class="form-control" placeholder="Email Address" name="email_id" id="email_id" value="<?php echo isset($email_id)?$email_id : set_value('email_id');?>">
                                </div>
                                <div id="email_error" class="alert alert-danger <?php if(empty(strip_tags(form_error('email_id'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('email_id'),'') ; ?>
                                </div>
                                <div class="form-group select">
                                    <label>Gender</label>
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
                                    <label>Date of Birth</label>
                                    <div class="input-append date" id="birthdate" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
                                        <input type="text" class="form-control" placeholder="dd-mm-yyyy" data-masked-input="99-99-9999" name="birthdate" id='dp' value="<?php echo isset($birthdate)? $birthdate:set_value('birthdate');?>">
                                        <input id="thealtdate" name="thealtdate" type="hidden" value="<?php echo (isset($birthdate)) ? date("Y-m-d", strtotime($birthdate)) : ''; ?>" />
                                    </div>
                                </div>
                                <div class="form-group age">
                                    <label>Age</label>
                                    <input type="text" class="form-control" placeholder="Age" disabled >
                                </div>
                                <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('birthdate'),''))){ echo 'hide';} ?>">
                                        <?php echo strip_tags(form_error('birthdate'),''); ?>
                                </div>
                                <div class="form-group">
                                    <label>Contact Number</label>
                                    <input id="contact_number" type="text" class="form-control" placeholder="(XXX) XXX-XXXX" data-masked-input="(999) 999-9999" name="contact_number" value="<?php echo isset($contact_number)?$contact_number:set_value('contact_number');?>">
                                </div>
                                <div  id="contact_error" class=" alert alert-danger <?php if(empty(strip_tags(form_error('contact_number'),''))){ echo 'hide';} ?>">
                                        <?php echo strip_tags(form_error('contact_number'),''); ?>
                                </div>
                                <div class="form-group">
                                    <label>Home Address</label>
                                    <textarea class="form-control" placeholder="Home Address" name="home_address"><?php echo isset($home_address)?$home_address:set_value('home_address');?></textarea>
                                </div>                         
                            </div>
                            <!--avatar-->
                            <div class="col-sm-4 text-center hidden-xs">

                                <?php 
                                    if(isset($profile_pic)){
                                ?>
                                <div class="avatar" id="before_select">
                                    <img src="<?php echo UPLOAD_URL.'/'.$profile_pic;?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                </div>
                                <div class="avatar" id="aftr_select" style="display:none;">
                                    <img id="blah_after" src="<?php echo base_url() ?>assets/images/avatar.png">
                                </div>
                                <?php 
                                    }
                                    else{
                                ?>
                                <div class="avatar">
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
                                       <label>Country</label>
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
                                    <label>State</label>
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
                                    <label>City</label>
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
                    <!-- model for image errors -->
                    
                    
                    <div class="modal fade in" id="image_upload_model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="display:block">
                    <div class="modal-dialog" role="document" style="width:600px;margin-top:120px;">
                        <div class="modal-content">
                            <div class="modal-header notice_header text-center">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="close_model()"><span aria-hidden="true">×</span></button>
                                <h4 class="modal-title" id="myModalLabel">Invalid uploading</h4>
                            </div>
                            <div class="modal-body">
                                Your image couldn't be uploaded. Photo should be saved as JPG, PNG, GIF or TIFF
                                <div class="basic_info">
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

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
                                    <!-- <div class="form-group small_input">
                                        <label>Region of School</label>
                                        <label class="label_form">&nbsp;</label>
                                        
                                    </div> -->
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
                                    <label>Username</label>
                                    <input type="text" class="form-control" placeholder="Username" name="username" value="<?php echo isset($username)?$username:set_value('username');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('username'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('username'),'') ; ?>
                                </div>
                                <?php if (isset($this->session->userdata('user')['id'])){ ?>
                                    <div class="form-group">
                                        <label>Current Password</label><span class="pull-right txt_grey"><input type="checkbox" id="show_password"> Show password</span>
                                        <input type="password" class="form-control" placeholder="Current Password" name="dis_cur_password" id="dis_cur_password" value="<?php echo isset($password)?$password:set_value('cur_password');?>" disabled>
                                        <input type="hidden" name="cur_password" value="<?php echo isset($password)?$password:set_value('cur_password');?>">
                                    </div>
                                <?php }else{ ?>
                                <div class="form-group">
                                    <label>Current Password</label>
                                    <input type="password" class="form-control" placeholder="Current Password" name="cur_password" value="<?php echo isset($password)?$password:set_value('cur_password');?>">
                                </div>
                                <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('cur_password'),''))){ echo 'hide';} ?>">
                                    <?php echo strip_tags(form_error('cur_password'),''); ?>
                                </div>
                                <?php } ?>
                                <div class="form-group">
                                    <label>New Password</label>
                                    <input type="password" class="form-control" placeholder="New Password" name="new_password" value="<?php echo set_value('new_password');?>">
                                </div>
                                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('new_password'),''))){ echo 'hide';}?>">
                                    <?php echo strip_tags(form_error('new_password'),'') ; ?>
                                </div>  
                                <div class="form-group">
                                    <label>Confirm Password</label>
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
                        <!-- <a href="<?php echo site_url();?>/student/home" class="btn_black btn">Cancel</a> -->
                        <input type="button" onclick="window.history.back();" value="Cancel" class="btn_black btn">
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
                        <small><?php echo date("d F Y",strtotime(date('d-m-Y')));?></small>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="" onsubmit="return send_email();" method="post">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Name</label>
                                <div class="col-sm-7">
                                    <input type="text" required class="form-control" id="request_name" name="request_name" placeholder="Name">
                                    <br>
                                    <div class="alert alert-danger" style="display:none" id="err3">
                                        Name field is required
                                    </div>
                                    <div class="alert alert-danger" style="display:none" id="err4">
                                        Invalid name
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input type="email" required class="form-control" id="request_email" name="request_email" placeholder="Email">
                                    <br>
                                    <div class="alert alert-danger" style="display:none" id="err1">
                                        Email field is required
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="col-sm-2 control-label">Message</label>
                                <div class="col-sm-10">
                                   <textarea required class="form-control" placeholder="Write school information..." name="message" id="message" rows="7"></textarea>
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
                        <h4 class="notice_by">ISM Admin<span></span></h4>
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
    <script src="assets/js/jquery-ui.min.js"></script> 
    <!-- // <script src="assets/js/bootstrap-datepicker.js"></script>  -->
    <script src="assets/js/jquery.masked-input.js"></script>
     <script src="assets/js/jquery.validate.js"></script>
    <script type="text/javascript">

    /**
     * Function for inline validation   of form
     *
     * @param  -
     * @return - 
     * @author - Pankaj(pv)
     */
    
    
    $(document).ready( function()
    {
        jQuery.validator.addMethod("accept", function(value, element, param) {
          return value.match(new RegExp("." + param + "$"));
        });
        jQuery.validator.addMethod("phoneNo", function(phone_number, element) {
           return phone_number.match(/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})?([ .-]?)([0-9]{4})/);
        });
       

        $("#user_account_frm").validate({
            // Specify the validation rules
             rules: {
                "first_name": { 
                    required: true,
                    accept: "[a-zA-Z ]+" 
                },
                "last_name": { 
                    required: true,
                    accept: "[a-zA-Z ]+" 
                },
                "email_id": {
                    required: true,
                    email: true
                },
                "gender":"required",
                "birthdate" : "required",
                "contact_number" : {
                    required: true,
                    phoneNo: true
                },
                "home_address" : "required",
                "country_id" : "required",
                "state_id" : "required",
                "city_id" : "required",
                "username" : "required"
            },
             messages: {
                "first_name": {
                    required: "Please provide First Name",
                    accept: "Please enter alphabets only"
                },
                "last_name": {
                    required: "Please provide Last Name",
                    accept: "Please enter alphabets only"
                },
                "email_id": {
                    required: "Please provide email address",
                    email: "Invalid email address"
                },
                "gender":"Please select Gender",
                "birthdate" : "Please select birth date",
                "contact_number" : {
                    required: "Please provide Contact Number",
                    phoneNo: "Invalid Contact Number" 
                },
                "home_address" : "Please provide home address",
                "country_id" : "Please select country",
                "state_id" : "Please select state",
                "city_id" : "Please select city",
                "username" : "Please provide Username"
            },
            submitHandler: function(form) {
                form.submit();
            }
            /*errorElement: 'span',
            errorClass: 'help-block',
            rules: {
                "data[Category][category_name]": "required"
            },
            messages: {
                "data[Category][category_name]": "Please provide Category Name",
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function(element) { // revert the change done by hightlight
                $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            submitHandler: function(form) {
                form.submit();
            }*/
        });
            
            $("#contact_number").blur(function (){
                var cno = $("#contact_number").val();
                if(cno=='(000) 000-0000'){
                    $("#contact_number").addClass('error');
                    $('<label id="contact_number-error" class="error" for="contact_number">Invalid Contact Number</label>').insertAfter($("#contact_number"));
                }
             });

               /* $("#first_name").focusout(function (){
                    var fname = $("#first_name").val();
                        if(/^[a-zA-Z -]{1,16}$/i.test(fname)){
                            $("#first_name_error").removeClass("show");
                                $("#first_name_error").addClass("hide");
                                }                            
                            else
                            {
                                $("#first_name_error").removeClass("hide");
                                $("#first_name_error").addClass("show");
                                $("#first_name_error").html("Invalid First Name");
                            }

                         });*/

                 /*$("#last_name").focusout(function (){
                    var lname = $("#last_name").val();
                            if(/^[a-zA-Z -]{1,16}$/i.test(lname)){
                                $("#last_name_error").removeClass("show");
                                $("#last_name_error").addClass("hide");
                                }                            
                            else
                            {
                                $("#last_name_error").removeClass("hide");
                                $("#last_name_error").addClass("show");
                                $("#last_name_error").html("Invalid Last Name");
                            }

                         });

                 $("#email_id").focusout(function (){
                    var email = $("#email_id").val();
                            if(/^[a-zA-Z0-9-._]+@[a-zA-Z0-9-_]+\.[a-zA-Z]{1,}$/i.test(email)){
                                $("#email_error").removeClass("show");
                                $("#email_error").addClass("hide");
                                }                            
                            else
                            {
                                $("#email_error").removeClass("hide");
                                $("#email_error").addClass("show");
                                $("#email_error").html("The Email field must contain a valid email address.");

                         });*/
    });



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
        // $('#birthdate input').datepicker({
        //     format: 'yyyy-mm-dd'
        // });
        $("#birthdate input").datepicker({
            dateFormat: 'dd-mm-yy',
            altField: '#thealtdate',
             altFormat: 'yy-mm-dd',
             maxDate: '0'
          }).on("select", function(dateText) {
             // console.log("Selected date: " + dateText + "; input's current value: " + this.value);
            }).on("change", function() {
                var dob = $("#thealtdate").val();
                dob = new Date(dob);
                var today = new Date();
                var age = Math.floor((today-dob) / (365.25 * 24 * 60 * 60 * 1000));
                $('.age input').val(age+' years');
          });

        // $('#birthdate input').datepicker({
        //     format: 'yyyy-mm-dd',
        // });

      
            var dob = $("#thealtdate").val();
            //console.log(dob);
            dob = new Date(dob);
            var today = new Date();
            var age = Math.floor((today-dob) / (365.25 * 24 * 60 * 60 * 1000));
            $('.age input').val(age+' years');
      


        $('#request_name').keypress(function (e) {
            var regex = new RegExp("^[a-zA-Z\\b ]+$");
            var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
            if (regex.test(str) || e.keyCode === 9) {
                $('#err4').hide();
                return true;
            }

            e.preventDefault();
            return false;
        });

        function close_model()
        {  
             $('#image_upload_model').removeClass('in');
        }

       function send_email(){

            email = $('#request_email').val();
            message = $('#message').val();
            name = $('#request_name').val();
            if(name != ''){
                var regex = new RegExp("^[a-zA-Z0-9\\b ]+$");
                if (regex.test(name)) {
                    return true;
                }
                else{
                    $('#err4').show();
                    return false;
                }
            }
            if(email == '' && message == '' && name == ''){
                $('#err2').show();
                $('#err1').show();   
                $('#err3').show();   
            }
            else if(message == '' && email != '' && name != ''){
                $('#err2').show();
                $('#err1').hide();
                $('#err3').hide();
            }
            else if(email == '' && message != '' && name != ''){
                $('#err1').show();
                $('#err2').hide();
                $('#err3').hide();
            }
            else if(name == '' && email != '' && message !=''){
                $('#err3').show();
                $('#err2').hide();
                $('#err1').hide();   
                
            }
            else {
          
                return true;
            } 
            return false;
        }

        $(function () {
          $('[data-toggle="tooltip"]').tooltip()
        })

        $('#show_password').click(function(){
            if($(this).prop("checked") == true){
                $('#dis_cur_password').attr('type','text');
            }
            else if($(this).prop("checked") == false){
                 $('#dis_cur_password').attr('type','password');
            }
        })

</script>
</body>
</html>