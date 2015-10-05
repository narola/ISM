
<!--main-->
<div class="col-sm-7 main main2">
	<!--breadcrumb-->
		<div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Admin</a></li>                          
              <li><a href="#">Users</a></li>
              <li class="active">Add User</li>
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
                    <div class="box_body ">
                    	
                        <div class="form-group">
                        	<label>Username</label>
                            <input type="text" class="form-control" name="username" value="<?php echo set_value("username"); ?>">
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('username'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('username'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" class="form-control" name="password" >
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('password'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('password'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>Retype password</label>
                            <input type="password" class="form-control" name="re_password"  >
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('re_password'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('re_password'),'') ; ?>
                        </div>
                
                        <div class="form-group">
                            <label>First Name</label>
                            <input type="text" class="form-control" name="first_name" value="<?php echo set_value("first_name"); ?>">
                        </div>

                        <div class="form-group">
                            <label>Last Name</label>
                            <input type="text" class="form-control" name="last_name" value="<?php echo set_value("last_name"); ?>">
                        </div>

                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" class="form-control" name="full_name" value="<?php echo set_value("full_name"); ?>">
                        </div>

                        <div class="form-group">
                            <label>Email ID</label>
                            <input type="text" class="form-control" name="email_id" value="<?php echo set_value("email_id"); ?>">
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('email_id'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('email_id'),'') ; ?>
                        </div>

                        <div class="form-group">
                            <label>Contact Number</label>
                            <input type="text" class="form-control" name="contact_number" value="<?php echo set_value("contact_number"); ?>">
                        </div>

                        <div class="form-group">
                            <label>Home Address</label>
                            <textarea class="form-control" name="home_address"></textarea>
                        </div>
                        
                        <!-- <div class="row filter"> -->
                        <div class="form-group ">
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
                        <!-- </div> -->

                        <div class="form-group" >
                            <label>State</label>
                            <select class="form-control" name="state" id="states_id" onchange="get_cities(this.value)" >
                                <option selected disabled>Select State</option> 
                            </select>
                        </div>

                        <div class="form-group">
                            <label>City</label>
                            <select class="form-control" name="city" id="city_id">
                                <option selected disabled>Select City</option> 
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Birth Date</label>
                            <input type="text" name="birthdate" id="datepicker"  >
                        </div>

                        <div class="form-group">
                            <label>Gender</label>
                            <input type="radio" name="gender" <?php echo set_radio('gender', 'male', TRUE); ?> value="male">Male
                            <input type="radio" name="gender" <?php echo set_radio('gender', 'female'); ?> value="female">Female
                        </div>
                        
                        <div class="form-group" >
                            <label>Roles</label>
                            <select class="form-control" name="roles" >
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
                            <select name="user_status" class="form-control">
                                <option value="active"> Active </option>
                                <option value="block"> Block </option>
                                <option value="inactive"> Inactive </option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>About Me </label>
                            <textarea class="form-control" name="about_me"></textarea>
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