<!--main-->
    <div class="col-sm-7 main main2">
      <!--breadcrumb-->
      <div class="row page_header">
          <div class="col-sm-12">
              <ol class="breadcrumb">
                  <li><a href="#">Manage</a></li>
                  <li class="active">User</li>
                </ol>
            </div>
        </div>
        <!--//breadcrumb-->
        <!--filter-->
        <form method="get" id="filter">
	        <div class="row filter">
	          <div class="col-sm-12">
	              <div class="form-group">
	                    <select class="form-control" name="school" onchange="filter_data()" id="school">
	                          <option value="">Select School</option>
	                          <?php 
	                            if(!empty($schools)){ 
	                              foreach($schools as $school) {
	                              ?>
	                              <option value="<?php echo $school['id']; ?>" ><?php echo $school['school_name']; ?></option>  
	                          <?php }  } ?>
	                      </select>
	                </div>
	                <div class="form-group">
	                   <select class="form-control" name="course" onchange="filter_data()" id="course" >
	                                <option value="">Select Course</option>
	                                <?php 
	                                  if(!empty($courses)){ 
	                                    foreach($courses as $course) {
	                                    ?>
	                                    <option value="<?php echo $course['id']; ?>"><?php echo $course['course_name']; ?></option>  
	                                <?php }  } ?>
	                            </select>
	                </div>
	                <div class="form-group">
	                    <select class="form-control" name="year" onchange="filter_data()" id="year">
	                        <option value="">Select Year</option>
	                        <option value="2015">2015</option>
	                        <option value="2016">2016</option>
	                    </select>
	                </div>
	                <div class="form-group">
	                    <select class="form-control" name="role" id="role" onchange="filter_data()">
                            <option value="">Select Role</option>
                            <?php 
                              if(!empty($roles)){ 
                                foreach($roles as $role) {
                                ?>
                                <option value="<?php echo $role['id']; ?>"><?php echo ucfirst($role['role_name']); ?></option>  
                            <?php }  } ?>
                        </select>
	                </div>
	                <div class="form-group">
	                    <select class="form-control" name="classroom" id="classroom" onchange="filter_data()">
                            <option value="">Select Classroom</option>
                            <?php 
                              if(!empty($classrooms)){ 
                                foreach($classrooms as $classroom) {
                                ?>
                                <option value="<?php echo $classroom['id']; ?>"><?php echo $classroom['class_name']; ?></option>  
                            <?php }  } ?>
                        </select>
	                </div>

                    <!-- <div class="form-group">
                        <input type="text" name="q" onkeyup="filter_data()" id="q" id="">
                    </div> -->

	            </div>
	        </div>
            
    	</form>	

    	<?php $success = $this->session->flashdata('success'); ?>
  
		<div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
		    <?php echo strip_tags($success) ; ?>
		</div>
		
		<?php $error = $this->session->flashdata('error'); ?>
  
		<div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
		    <?php echo strip_tags($error) ; ?>
		</div>

        <!--//filter-->
        <!--button div-->
    
        <form method="post" action="<?php echo base_url().'admin/user/send_messages'; ?>">  <!-- Form Start -->
    
        <div class="row div_buttons">
          <div class="col-sm-6">
              <button class="btn btn_black" type="submit">Send Message</button>
              <!-- <a class="btn btn_green" href="<?php echo base_url().'admin/user/add';?>" >Add User</a> -->
            </div>

          <div class="col-sm-6 text-right">
              <button class="btn btn_green">Invite Mate</button>
            </div>
        </div>
        <!--//button-div-->
        <!--row table-->
        <div class="row tabel_view">
          <div class="col-sm-12">
              <div class="table-responsive">
                  <table class="table table-striped table-bordered table_user">
                      <thead>
                            <tr>
                                <th></th>
                                <th style="width: 240px;">Username</th>
                                <th>Class</th>
                                <th>Course</th>
                                <th>City</th>
                                <th>Role</th>
                                <th style="width:180px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                          <?php 
                            if(!empty($all_users)) {

                              foreach($all_users as $user) {
                            ?>
                          <tr>
                              <td class="checkbox_td">
                                <div class="squaredThree">
                                    <input type="checkbox" value="<?php echo $user['id']; ?>" id="squaredThree_<?php echo $user['id']; ?>" name="users[]"> 
                                    <label for="squaredThree_<?php echo $user['id']; ?>"></label>
                                </div>
                              </td>
                              <td class="username">
                                  <div class="chat_img_holder">
                                        <img src="<?php echo base_url().'assets'; ?>/uploads/$user['profile_link']"
                                        onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                  </div>
                                  <h4><?php echo ucfirst($user['username']); ?></h4>
                                  <?php if($user['user_status']=='active'){ 
                                        echo '<p class="active">Active Today</p>'; 
                                    }elseif($user['user_status']=='blocked'){ 
                                        echo '<p style="color:red">Blocked</p>';
                                    } ?>
                              </td>

                              <td><?php echo ucfirst($user['class_name']); ?></td>
                              <td> <?php echo ucfirst($user['course_name']); ?> </td>
                              <td> <?php echo ucfirst($user['city_name']); ?> </td>
                              <td><?php echo ucfirst($user['role_name']); ?></td>
                              <td>
                                  <a href="#" class="icon icon_timeline" data-toggle="tooltip" data-placement="bottom" title="Timeline"></a>
                                  <a href="#" class="icon icon_books" data-toggle="tooltip" data-placement="bottom" title="Books"></a>
                                  <a href="admin/user/activity/<?php echo $user['id'];?>" class="icon icon_performance" data-toggle="tooltip" data-placement="bottom" title="Performance"></a>
                                  <?php if($user['user_status'] == 'blocked') { ?>  
                                  <a href="<?php echo base_url().'admin/user/active/'.$user['id']; ?>" 
                                    onclick="return confirm('Activate User ?');" class="icon icon_user" data-toggle="tooltip" data-placement="bottom" title="Active" ></a>
                                  <?php }else{ ?>   
                                  <a href="<?php echo base_url().'admin/user/blocked/'.$user['id']; ?>" 
                                    onclick="return confirm('Blocked User ?');" class="icon icon_blockuser" data-toggle="tooltip" data-placement="bottom" title="Block"></a>  
                                  <?php } ?>
                                  <a href="#" class="icon icon_mail" data-toggle="tooltip" data-placement="bottom"  
                                  data-toggle="tooltip" data-placement="bottom" title="Mail"></a>
                                  <a href="<?php echo base_url().'admin/user/send_message/'.$user['id']; ?>" class="icon icon_chat" 
                                    data-toggle="tooltip" data-placement="bottom" title="Message"></a>
                                  <a href="<?php echo base_url().'admin/user/update/'.$user['id']; ?>" class="icon icon_edit"
                                    data-toggle="tooltip" data-placement="bottom" title="Edit"> </a>
                              </td>
                            </tr>
                            <?php } }else{ ?>
							
							<tr> <td colspan="7" class="text-center"><strong>No Data Found. </strong> </td> </tr>		
							
                            <?php } ?>
                        </tbody>
                    </table>
                </div>
                <nav  class="text-center">
       
			    <?php  echo $this->pagination->create_links();  ?>

                </nav>
            </div>
        </div>

       </form> <!-- Form END  -->
        <!--//row table-->
    </div>
    <!--//main-->

<script type="text/javascript">
	
    function filter_data(){
    	
    	var role = $('#role').val();
    	var school = $('#school').val();
    	var year = $('#year').val();
    	var course = $('#course').val();
    	var classroom = $('#classroom').val();
        var q = $('#q').val();

    	if(role == '' ){ $('#role').removeAttr('name'); }
    	if(school == '' ){ $('#school').removeAttr('name'); }
    	if(year == '' ){ $('#year').removeAttr('name'); }
    	if(course == '' ){ $('#course').removeAttr('name'); }
    	if(classroom == ''){ $('#classroom').removeAttr('name'); }
        //if(q == ''){ $('#q').removeAttr('name');}else{  setTimeout(function() { $('#filter').submit(); }, 1000); }

    	$('#filter').submit();
    }


	<?php if(!empty($_GET['role'])) { ?>
		$('#role').val('<?php echo $_GET["role"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['school'])) { ?>
		$('#school').val('<?php echo $_GET["school"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['year'])) { ?>
		$('#year').val('<?php echo $_GET["year"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['course'])) { ?>
		$('#course').val('<?php echo $_GET["course"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['classroom'])) { ?>
		$('#classroom').val('<?php echo $_GET["classroom"];?>');	
	<?php } ?>

    <?php if(!empty($_GET['q'])) { ?>
        $('#q').val('<?php echo $_GET["q"];?>');    
    <?php } ?>			

</script>
 