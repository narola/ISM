<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
  <!--breadcrumb-->
  <div class="page_header">
      <div class="col-sm-12">
          <ol class="breadcrumb">
              <li><a href="admin/notice">Admin</a></li>                          
              <li><a href="admin/notice">Manage Notices</a></li>
              <li class="active">Update Notice</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--message-->

    <div class="col-sm-12">
      <div class="col-sm-12 new_message">
        <form method="post">  
          <div class="box exam_card">
              <div class="box_header">
                  <h3> Update Notice</h3>
                </div>
                <div class="box_body admin_controls with_labels">
                  
                  <div class="form-group select">
                      <label> Roles </label>
                       <select name="role_id" id="role_id" class="form-control">
                          <option selected disabled> Select Role </option>
                          <?php 
                              if(!empty($roles)) {
                                foreach($roles as $role) { 
                                   if($role['role_name'] != 'admin') { 
                              ?>
                              <option value="<?php echo $role['id']; ?>"><?php echo ucfirst($role['role_name']); ?></option>
                          <?php }  } } ?>
                       </select> 
                  </div>
                  
                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('role_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('role_id'),'') ; ?>
                  </div>

                  <div class="form-group select">
                      <label> Classroom </label>
                       <select name="classroom_id" id="classroom_id" class="form-control">
                          <option selected value=" "> Select Classroom </option>
                          <?php 
                              if(!empty($classrooms)) {
                                foreach($classrooms as $classroom) { 
                              ?>
                              <option value="<?php echo $classroom['id']; ?>"><?php echo ucfirst($classroom['class_name']); ?></option>
                          <?php } } ?>
                       </select> 
                  </div>
                    
                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('classroom_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('classroom_id'),'') ; ?>
                  </div>
                    
                  <div class="form-group">
                      <label>Notice Title</label>
                        <input type="text" class="form-control" name="notice_title" value="<?php echo $notice['notice_title']; ?>" >
                  </div>
                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice_title'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice_title'),'') ; ?>
                  </div>
                  <div class="form-group">
                      <label>Notice</label>
                      <textarea class="form-control" name="notice"><?php echo $notice['notice']; ?></textarea>
                  </div>
                  
                  <div class="form-group">
                        <div class="circleThree">
                            <input type="radio" value="active" id="circleThree"  <?php if($notice['status']=='active'){ echo "checked='checked'"; } ?> name="status">
                            <label for="circleThree"></label>
                            <span> Active </span>
                       
                            <input type="radio" value="inactive" id="circleThree1" <?php if($notice['status']=='inactive'){ echo "checked='checked'";  } ?> name="status">
                            <label for="circleThree1"></label>
                            <span>Inactive</span>

                            <input type="radio" value="archive" id="circleThree2" <?php if($notice['status']=='archive'){ echo "checked='checked'"; } ?> name="status">
                            <label for="circleThree2"></label>
                            <span>Archive</span>
                        </div>
                  </div>  

                </div>
                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice'),'') ; ?>
                </div>
                <div class="box_footer">
                  <button type="submit" class="btn btn_green">Update</button>
                  <!-- <input type="checkbox" name="is_template" id="is_template" <?php if(!empty($notice['is_template'])){ echo "checked"; }?> value="1">
                  <label class="save_box"></label><label for="is_template">Save in Templates</label> -->
                  <a href="<?php echo $prev_url; ?>" class="btn btn_black">Cancel</a>
                </div>
            </div>
          </form>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->

<script type="text/javascript">

  $(document).ready(function() {
      $(".js-example-basic-single").select2();
    });


  
    $('#role_id').val('<?php echo $notice["role_id"] ?>');
    $('#classroom_id').val('<?php echo $notice["classroom_id"] ?>');
</script>