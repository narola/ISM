<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="admin/user">Manage</a></li>                          
              <li><a href="admin/user">Users</a></li>
              <li class="active">Update User</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->
    
    <!--//filter-->
    
    <!--message-->
   	<div class="">
    	<div class="col-sm-12 new_message">
        	<div class="box exam_card">
            	<div class="box_header">
                	<h3>Update User</h3>
                </div>
                <form method="post">
                    <div class="box_body admin_controls with_labels">
                    	
                        <div class="form-group">
                        	<label>Username</label>
                            <input type="text" class="form-control" name="username" 
                                   value="<?php  echo set_value("username") == false ? $user["username"] : set_value("username"); ?>" >
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('username'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('username'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>First Name</label>
                            <input type="text" class="form-control" name="first_name" 
                            value="<?php  echo set_value("first_name") == false ? $user["first_name"] : set_value("first_name"); ?>" >
                        </div>

                        <?php echo myform_error('first_name'); ?>

                        <div class="form-group">
                            <label>Last Name</label>
                            <input type="text" class="form-control" name="last_name"  
                            value="<?php  echo set_value("last_name") == false ? $user["last_name"] : set_value("last_name"); ?>" >
                        </div>

                        <?php echo myform_error('last_name'); ?>

                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" class="form-control" name="full_name"  
                            value="<?php  echo set_value("full_name") == false ? $user["full_name"] : set_value("full_name"); ?>" >
                        </div>

                        <?php echo myform_error('full_name'); ?>

                        <div class="form-group">
                            <label>Email ID</label>
                            <input type="text" class="form-control" name="email_id" 
                            value="<?php  echo set_value("email_id") == false ? $user["email_id"] : set_value("email_id"); ?>"  >
                        </div>

                        <?php echo myform_error('email_id'); ?>

                        <div class="form-group">
                            <label>Contact Number</label>
                            <input type="text" class="form-control" name="contact_number" 
                            value="<?php  echo set_value("contact_number") == false ? $user["contact_number"] : set_value("contact_number"); ?>" >
                        </div>

                        <?php echo myform_error('contact_number');  ?>

                        <div class="form-group">
                            <label>Home Address</label>
                            <textarea class="form-control" name="home_address"><?php echo $user['home_address']; ?></textarea>
                        </div>

                        <div class="form-group select">
                            <label>Country</label>
                            <select class="form-control " name="country" onchange="get_states(this.value)" id="country_id">
                                <option selected disabled>Select Country</option> 
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

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('country'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('country'),'') ; ?>
                        </div>

                        <div class="form-group" >
                            <label>State</label>
                            <select class="form-control" name="state" id="states_id" onchange="get_cities(this.value)" >
                                <option selected disabled>Select State</option>
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

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('state'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('state'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>City</label>
                            <select class="form-control" name="city" id="city_id">
                                <option disabled>Select City</option>
                                <?php 
                                  if(!empty($cities)){ 
                                    foreach($cities as $city) {
                                  ?> 
                                <option value="<?php echo $city['id']; ?>"> <?php echo $city['city_name']; ?></option>
                                <?php }  }else{ ?>
                                <option > No City</option>
                                <?php } ?>
                            </select>
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('city'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('city'),'') ; ?>
                        </div>

                        <!-- <div class="form-group">
                            <label>Birth Date</label>
                            <input type="text" name="birthdate" id="datepicker" value="<?php //echo $user['birthdate']; ?>"  >
                        </div> -->

                        <div class="form-group dob">
							<label>Date of Birth</label>
                           <div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="birthdate" class="input-append date">
                                <input type="text" name="birthdate" placeholder="Date of Birth" 
                                value=" <?php  echo set_value("birthdate") == false ? $user["birthdate"] : set_value("birthdate"); ?>" class="form-control">
                            </div> 

                        </div>

                        <?php echo myform_error('birthdate'); ?>

                        <div class="circleThree">
                            <input type="radio" name = "gender" value="male" <?php if($user['gender'] == 'male'){ echo 'checked'; } ?> 
                            id="circleThree" name="check">
                            <label for="circleThree"></label>
                            <span>Male</span>
                       
                            <input type="radio" name="gender" value="female" <?php if($user['gender'] == 'female'){ echo 'checked'; } ?> 
                            id="circleThree1" name="check">
                            <label for="circleThree1"></label>
                            <span>Female</span>
                        </div>

                        
                        <div class="form-group" >
                            <label>Roles</label>
                            <select class="form-control" name="roles" id="role_id" >
                                <?php 
                                  if(!empty($roles)){ 
                                    foreach($roles as $role) {
                                  ?> 
                                <option value="<?php echo $role['id']; ?>"> <?php echo ucfirst($role['role_name']); ?></option>
                                <?php }  }else{ ?>
                                <option > No Country</option>
                                <?php } ?>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label>User Status</label>
                            <select name="user_status" class="form-control" id="user_status" >
                                <option value="active" >Active</option>
                                <option value="blocked" >Block</option>
                                <option value="inactive">Inactive</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>About Me </label>
                            <textarea class="form-control" name="about_me"><?php echo $user['about_me']; ?></textarea>
                        </div>

                        <div class="form-group" >
                            <label>Packages</label>
                            <select class="form-control"name="package" >
                               <?php 
                                  if(!empty($packages)){ 
                                    foreach($packages as $package) {
                                  ?> 
                                <option value="<?php echo $package['id']; ?>"> <?php echo $package['package_name']; ?></option>
                                <?php }  }else{ ?>
                                <option value="0" > No Packagses</option>
                                <?php } ?>  
                            </select>
                        </div>

                    </div>

                    <div class="box_footer">
                    	<button type="submit" class="btn btn_green">Save</button>

                        <a href="<?php echo base_url().$prev_url; ?>" class="btn btn_black">Cancel</a>
                    </div>

                </form>   

            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->
            
<script type="text/javascript">
  
  $('#country_id').val('<?php echo $user["country_id"]; ?>');
  $('#states_id').val('<?php echo $user["state_id"]; ?>');
  $('#city_id').val('<?php echo $user["city_id"]; ?>');
  
  $('#role_id').val('<?php echo $user["role_id"]; ?>');
  $('#user_status').val('<?php echo $user["user_status"]; ?>');
  $('#package_id').val('<?php echo $user["package_id"]; ?>'); 

    $(function() {
        $( "#datepicker" ).datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: '1990:' + new Date().getFullYear(),
            dateFormat: 'yy-mm-dd'
        });
    });
    
  function get_states(country_id){
    $.ajax({
       url:'<?php echo base_url()."common/ajax_get_states"; ?>',
       type:'POST',
       data:{country_id:country_id},
       success:function(data){
          $('#states_id').html(data);
          $('#city_id').html('<option> Select City </option>');
       }
    });
  }

  function get_cities(state_id){
    $.ajax({
       url:'<?php echo base_url()."common/ajax_get_cities"; ?>',
       type:'POST',
       data:{state_id:state_id},
       success:function(data){
          $('#city_id').html(data);
       }
    });
  }

</script>         