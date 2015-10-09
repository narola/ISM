<!--main-->
<div class="col-sm-7 main main2 general_cred">
    <div class="box">
          
        <form method="post" onsubmit="return success_credential()" >

          <div class="box_header">
            <h3>General Credencials</h3>
          </div>

          <div class="box_body">  
              
              <div class="form-group three_inputs select">
                  <label>Select School </label>
                  <select class="form-control js-example-basic-single" id="school_id" name="school_id">
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
                  <a href="#" class="icon icon_add_small"></a>
                  <?php echo myform_error('school_id'); ?>
              </div>

              <div class="form-group three_inputs select" >
                  <label>Role</label>
                  <select class="form-control " name="role_id" id="role_id">
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
                      <select class="form-control " name="course_id" id="course_id" >
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
                      <select class="form-control" name="classroom_id" id="classroom_id">
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
                      <select class="form-control" name="year_id" id="year_id">
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
                <input type="text" name="no_of_credentials" id="noc" value="<?php echo set_value('no_of_credentials'); ?>" class="form-control" id="">
                <?php echo myform_error('no_of_credentials'); ?>
              </div>
              <div class="clearfix"></div>
          </div>
          
          <div class="box_header">
            <div class="confirmation">
                <p id="dialog_box" class="hide">You have requested <span class="txt_red" id="noc_new">50</span> credencials for <span id="role_id_new" class="txt_blue">Students</span> of <span class="txt_blue" id="school_id_new">St. Xevier's School</span> belong to first academic year in <span class="txt_green" id="course_id_new">Computer Science Course</span></p>

                <?php echo flashMessage(TRUE); ?>

                <?php 
                    $success = $this->session->flashdata('success'); 
                ?>
                
                <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
                    <?php echo strip_tags($success) ; ?>
                </div>

                <button class="btn btn_red" type="submit">Confirm & Generate</button>
                <button class="btn btn_black_normal" type="reset" onclick="reset_form()">Cancel</button>

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

    function reset_form(){
            $('#school_id').val('');
    }

    function success_credential(){
        
        var school_id = $('#school_id').val();
        var noc = $('#noc').val();  
        var course_id = $('#course_id').val();
        var role_id = $('#role_id').val();
        var year_id = $('#year_id').val();

        var error_count = 0;

        if(school_id == '' || $.isNumeric(school_id) == false){   error_count++; }
        if(noc == '' || $.isNumeric(noc) == false){  error_count++; }
        if(course_id == '' || $.isNumeric(course_id) == false){  error_count++; }
        if(role_id == '' || $.isNumeric(role_id) == false){  error_count++; }
        if(year_id == '' || $.isNumeric(year_id) == false){  error_count++; }

        if(error_count == 0){

            var school_name = $('#school_id option:selected').text();
            var course_name = $('#course_id option:selected').text();
            var role_name = $('#role_id option:selected').text();    
            
            $('#dialog_box').removeClass('hide');
            $('#noc_new').html(noc);
            $('#role_id_new').html(role_name);
            $('#course_id_new').html(course_name);
            $('#school_id_new').html(school_name);

        }
        
    }

</script>