<!--main-->
<div class="col-sm-7 main main2 general_cred">
    <div class="box">
          
        <form method="post">

          <div class="box_header">
            <h3>General Credencials</h3>
          </div>

          <div class="box_body">  
              
              <div class="form-group three_inputs select">
                  <label>Select School </label>
                  <select class="form-control js-example-basic-single" name="school_id">
                     <option selected value=""> Select School</option>
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
                  <a href="#" class="icon icon_add_small"></a>
                  <?php echo myform_error('school_id'); ?>
              </div>

              <div class="form-group three_inputs select" name="role_id">
                  <label>Role</label>
                  <select class="form-control " name="role_id">
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
                  <?php echo myform_error('role_id'); // create custom helper function in cms_helper.php  ?> 
              </div>
              <div class="clearfix"></div>
           </div>
           
           <div class="box_body">
                <div class="form-group three_inputs select">
                      <label>Course </label>
                      <select class="form-control " name="course_id">
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
                      <a href="#" class="icon icon_add_small"></a>
                      <?php echo myform_error('course_id'); ?>
                  </div>
                  <div class="form-group three_inputs select">
                      <label>Classroom</label>
                      <select class="form-control" name="classroom_id">
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
                      <a href="#" class="icon icon_add_small"></a>
                      <?php echo myform_error('classroom_id'); ?>
                  </div>
                  <div class="form-group three_inputs select">
                      <label>Year</label>
                      <select class="form-control">
                          <option value="<?php echo $cur_year; ?>"><?php echo $cur_year; ?></option>
                          <option value="<?php echo $next_year; ?>"><?php echo $next_year; ?></option>
                      </select>
                      <a href="#" class="icon icon_add_small"></a>
                  </div>
                  <div class="clearfix"></div>
           </div>

          <div class="box_header">
            <h3>How many users to generate for selected criteria?</h3>
          </div>

          <div class="box_body">  
              <div class="form-group col-sm-5">
                <label>Enter Number of Users</label>
                <input type="text" name="no_of_credentials" value="<?php echo set_value('no_of_credentials'); ?>" class="form-control" id="">
                <?php echo myform_error('no_of_credentials'); ?>
              </div>
              <div class="clearfix"></div>
          </div>
          
          <div class="box_header">
            <div class="confirmation">
                <p>You have requested <span class="txt_red">50</span> credencials for <span class="txt_blue">Students</span> of <span class="txt_blue">St. Xevier's School</span> belong to first academic year in <span class="txt_green">Computer Science Course</span></p>
                <button class="btn btn_red">Confirm & Generate</button>
                <button class="btn btn_black_normal">Cancle</button>
            </div>
          </div>
        
        </form>

      </div>
</div>
<!--//main-->

<script type="text/javascript">
    $(document).ready(function() {
      $(".js-example-basic-single").select2({ placeholder: "Select a school"});
    });
</script>