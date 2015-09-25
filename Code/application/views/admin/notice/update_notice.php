<!--main-->
<div class="col-sm-7 main main2">
  <!--breadcrumb-->
  <div class="row page_header">
      <div class="col-sm-12">
          <ol class="breadcrumb">
              <li><a href="#">Admin</a></li>                          
              <li><a href="#">Manage Notices</a></li>
              <li class="active">Update Notice</li>
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
                  <h3> Update Notice</h3>
                </div>
                <div class="box_body">
                  
                  <div class="form-group">
                      <label> Roles </label>
                       <select name="role_id" id="role_id" class="form-control">
                          <option selected disabled> Select Role </option>
                          <?php 
                              if(!empty($roles)) {
                                foreach($roles as $role) { 
                                   if($role['role_name'] != 'admin') { 
                              ?>
                              <option value="<?php echo $role['id']; ?>"><?php echo $role['role_name']; ?></option>
                          <?php }  } } ?>
                       </select> 
                  </div>
                  
                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('role_id'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('role_id'),'') ; ?>
                  </div>

                  <div class="form-group">
                      <label> Classroom </label>
                       <select name="classroom_id" id="classroom_id" class="form-control">
                          <option selected value=" "> Select Classroom </option>
                          <?php 
                              if(!empty($classrooms)) {
                                foreach($classrooms as $classroom) { 
                              ?>
                              <option value="<?php echo $classroom['id']; ?>"><?php echo $classroom['class_name']; ?></option>
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
                      <label>Status</label>
                      
                      <input type="radio" name="status" value="active" <?php if($notice['status']=='active'){ echo "checked"; } ?>   > Active
                      <input type="radio" name="status" value="inactive"  <?php if($notice['status']=='inactive'){ echo "checked"; } ?>> Inactive
                      <input type="radio" name="status" value="archive" <?php if($notice['status']=='archive'){ echo "checked"; } ?> > Archive

                  </div>
                </div>
                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice'),'') ; ?>
                </div>
                <div class="box_footer">
                  <button type="submit" class="btn btn_green">Update</button>
                  <input type="checkbox" name="is_template" id="is_template" <?php if(!empty($notice['is_template'])){ echo "checked"; }?> value="1">
                  <label class="save_box"></label><label for="is_template">Save in Templates</label>
                  <button class="btn btn_black">Cancel</button>
                </div>
            </div>
          </form>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->

<script type="text/javascript">
    $('#role_id').val('<?php echo $notice["role_id"] ?>');
    $('#classroom_id').val('<?php echo $notice["classroom_id"] ?>');
</script>