
<!--main-->
<div class="col-sm-7 main main2">
	<!--breadcrumb-->
		<div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Admin</a></li>                          
              <li><a href="#">Users</a></li>
              <li class="active">Update User</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->
    
    <!--//filter-->
    
    <!--message-->
   	<div class="row">
    	<div class="col-sm-12 new_message">
        	<div class="box exam_card">
            	<div class="box_header">
                	<h3>Add User</h3>
                </div>
                <form method="post">
                    <div class="box_body">
                    	
                        <div class="form-group">
                        	<label>Username</label>
                            <input type="text" class="form-control" name="username" value="<?php echo $user['username']; ?>" >
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('username'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('username'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>First Name</label>
                            <input type="text" class="form-control" name="first_name" value="<?php echo $user['first_name']; ?>" >
                        </div>

                        <div class="form-group">
                            <label>Last Name</label>
                            <input type="text" class="form-control" name="last_name"  value="<?php echo $user['last_name']; ?>" >
                        </div>

                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" class="form-control" name="full_name"  value="<?php echo $user['full_name']; ?>" >
                        </div>

                        <div class="form-group">
                            <label>Email ID</label>
                            <input type="text" class="form-control" name="email_id" value="<?php echo $user['email_id']; ?>"  >
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('email_id'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('email_id'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>Contact Number</label>
                            <input type="text" class="form-control" name="contact_number" value="<?php echo $user['contact_number']; ?>" >
                        </div>

                        <div class="form-group">
                            <label>Home Address</label>
                            <textarea class="form-control" name="home_address"><?php echo $user['home_address']; ?></textarea>
                        </div>

                        <div class="form-group">
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

                        <div class="form-group">
                            <label>Birth Date</label>
                            <input type="text" name="birthdate" id="datepicker" value="<?php echo $user['birthdate']; ?>"  >
                        </div>

                        <div class="form-group">
                            <label>Gender</label>
                            <input type="radio" name="gender" <?php if($user['gender'] == 'male'){ echo 'checked'; } ?> value="male">Male
                            <input type="radio" name="gender" <?php if($user['gender'] == 'female'){ echo 'checked'; } ?> value="female">Female
                        </div>
                        
                        <div class="form-group" >
                            <label>Roles</label>
                            <select class="form-control" name="roles" id="role_id" >
                                <?php 
                                  if(!empty($roles)){ 
                                    foreach($roles as $role) {
                                  ?> 
                                <option value="<?php echo $role['id']; ?>"> <?php echo $role['role_name']; ?></option>
                                <?php }  }else{ ?>
                                <option > No Country</option>
                                <?php } ?>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label>User Status</label>
                            <select name="user_status" class="form-control" id="user_status" >
                                <option value="active" >Active</option>
                                <option value="block" >Block</option>
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
                        <button class="btn btn_black">Cancel</button>
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