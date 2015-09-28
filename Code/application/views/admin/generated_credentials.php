<!--main-->
<div class="col-sm-7 main main2">
  <!--breadcrumb-->
  <div class="row page_header">
      <div class="col-sm-12">
          <ol class="breadcrumb">
              <li><a href="#">Admin</a></li>                          
              <li class="active"><a href="#">Manage Credentials</a></li>
          </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--message-->
    <div class="row">
      <div class="col-sm-12 new_message">
        <form method="post">  
          <div class="box exam_card">
              <div class="box_header">
                  <h3> Generate Credentials </h3>
                </div>
                <div class="box_body">
                  
                  <div class="form-group">
                      <label> Select School </label>
                       <select name="school_id" class="form-control">
                          <option selected disabled> Select School</option>
                          <?php 
                              if(!empty($schools)) {
                                foreach($schools as $school) { 
                              ?>
                              <option value="<?php echo $school['id']; ?>" <?php echo set_select('school_id', $school['id']); ?> ><?php echo $school['school_name']; ?></option>
                          <?php } }else{ ?>
                              <option disabled > No Schools Found</option>  
                          <?php } ?>
                       </select> 
                  </div>

                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('school_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('school_id'),'') ; ?>
                  </div>

                  <div class="form-group">
                      <label> Select Role </label>
                       <select name="role_id" class="form-control">
                          <option selected disabled> Select Role</option>
                          <?php 
                              if(!empty($roles)) {
                                foreach($roles as $role) { 
                              ?>
                              <option value="<?php echo $role['id']; ?>" <?php echo set_select('role_id', $role['id']); ?>>
                                   <?php echo $role['role_name']; ?>
                              </option>
                          <?php } }else{ ?>
                              <option disabled > No Roles Found</option>  
                          <?php } ?>
                       </select> 
                  </div>

                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('role_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('role_id'),'') ; ?>
                  </div>

                  <div class="form-group">
                      <label> Select Course </label>
                       <select name="course_id" class="form-control">
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

                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('course_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('course_id'),'') ; ?>
                  </div>

                  <div class="form-group">
                      <label> Select Classroom </label>
                       <select name="classroom_id" class="form-control">
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

                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('classroom_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('classroom_id'),'') ; ?>
                  </div>
                  
                </div>

               <div class="box_header">
                  <h3> HOW MANY USERS TO GENERATE FOR SELECTED CRITERIA? </h3>
               </div>

               <div class="box_body">
                 <div class="form-group">
                      <label> Number of Credentials </label>
                        <input type="text" class="form-control" name="no_of_credentials">
                  </div>
                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('no_of_credentials'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('no_of_credentials'),'') ; ?>
                  </div>
               </div>

               <!-- <div class="box_header"  >
                  <h3> 
                         You have requested <span id="no_of_ids"> 50  </span>  for <span id="role_name">Student</span> of <span id="school_name"> St.xavier  </span>
                         Belongs to first academic year in Computer Science.
                 </h3>
               </div> -->

               <div class="box_footer">
                  <button type="submit" class="btn btn_green">Generate</button>
                  <button class="btn btn_black">Cancel</button>
               </div>
            </div>
          </form>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->