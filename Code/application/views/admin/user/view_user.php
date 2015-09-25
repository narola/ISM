<?php 
   /**
     * variable $error and $success are flash messages  
     *
     * @var $error & $success
     **/

?>

  <?php $error = $this->session->flashdata('error'); ?>
  
  <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($error) ; ?>
  </div>

  <?php $success = $this->session->flashdata('success'); ?>
  
  <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($success) ; ?>
  </div> 

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
        <div class="row filter">
          <div class="col-sm-12">
              <div class="form-group">
                    <select class="form-control">
                                <option>Select School</option>
                                <?php 
                                  if(!empty($schools)){ 
                                    foreach($schools as $school) {
                                    ?>
                                    <option><?php echo $school['school_name']; ?></option>  
                                <?php }  } ?>
                            </select>
                </div>
                <div class="form-group">
                   <select class="form-control">
                                <option>Select Course</option>
                                <?php 
                                  if(!empty($courses)){ 
                                    foreach($courses as $course) {
                                    ?>
                                    <option><?php echo $course['course_name']; ?></option>  
                                <?php }  } ?>
                            </select>
                </div>
                <div class="form-group">
                    <select class="form-control">
                        <option>Select Year</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control">
                                <option>School Role</option>
                                <?php 
                                  if(!empty($roles)){ 
                                    foreach($roles as $role) {
                                    ?>
                                    <option><?php echo $role['role_name']; ?></option>  
                                <?php }  } ?>
                            </select>
                </div>
                <div class="form-group">
                    <select class="form-control">
                                <option>School Role</option>
                                <?php 
                                  if(!empty($roles)){ 
                                    foreach($roles as $role) {
                                    ?>
                                    <option><?php echo $role['role_name']; ?></option>  
                                <?php }  } ?>
                            </select>
                </div>
            </div>
        </div>
        <!--//filter-->
        <!--button div-->
        <div class="row div_buttons">
          <div class="col-sm-6">
              <button class="btn btn_black">Send Message</button>
              <a class="btn btn_green" href="<?php echo base_url().'admin/user/add';?>" >Add User</a>
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
                                    <input type="checkbox" value="<?php echo $user['id']; ?>" id="squaredThree_<?php echo $user['id']; ?>" 
                                    name="users[]">
                                    <label for="squaredThree_<?php echo $user['id']; ?>"></label>
                                </div>
                              </td>
                              <td class="username">
                                  <div class="chat_img_holder"><img src="<?php echo base_url().'assets'; ?>/images/user3.jpg"></div>
                                  <h4><?php echo ucfirst($user['username']); ?></h4>
                                  <p class="active">Active Today</p>
                              </td>

                              <td>First Year</td>
                              <td>Computer Science</td>
                              <td> <?php echo ucfirst($user['city_name']); ?> </td>
                              <td><?php echo ucfirst($user['role_name']); ?></td>
                              <td>
                                  <a href="#" class="icon icon_timeline"></a>
                                  <a href="#" class="icon icon_books"></a>
                                  <a href="#" class="icon icon_performance"></a>
                                  <a href="#" class="icon icon_blockuser"></a>
                                  <a href="#" class="icon icon_mail"></a>
                                  <a href="#" class="icon icon_chat"></a>
                                  <a href="<?php echo base_url().'admin/user/update/'.$user['id']; ?>" class="icon icon_edit"> </a>
                              </td>
                            </tr>
                            <?php } } ?>
                        </tbody>
                    </table>
                </div>
                <nav  class="text-center">
       
			    <?php  echo $this->pagination->create_links();  ?>

                </nav>
            </div>
        </div>
        <!--//row table-->
    </div>
    <!--//main-->

 