<!--main-->
<div class="col-sm-7 main main2">
  <!--breadcrumb-->
  <div class="row page_header">
      <div class="col-sm-12">
          <ol class="breadcrumb">
              <li><a href="#">Admin</a></li>                          
              <li><a href="#">Manage Notices</a></li>
              <li class="active">Add Notice</li>
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
                  <h3>Add New Notice</h3>
                </div>
                <div class="box_body">
                  
                  <div class="form-group">
                      <label> Templates </label>
                       <select name="roles" class="form-control" onchange="get_template(this.value)">
                          <option selected disabled> Select Template</option>
                          <?php 
                              if(!empty($templates)) {
                                foreach($templates as $template) { 
                              ?>
                              <option value="<?php echo $template['id']; ?>"><?php echo word_limiter($template['notice_title'],7); ?></option>
                          <?php } }else{ ?>
                              <option disabled > No Templates </option>  
                          <?php } ?>
                       </select> 
                  </div>
                          
                  <div class="form-group">
                      <label> Roles </label>
                       <select name="role_id" class="form-control">
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
                       <select name="classroom_id" class="form-control">
                          <option selected disabled> Select Classroom </option>
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
                        <input type="text" class="form-control" name="notice_title">
                  </div>
                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice_title'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice_title'),'') ; ?>
                  </div>
                  <div class="form-group">
                      <label>Notice</label>
                      <textarea class="form-control" name="notice"></textarea>
                  </div>
                </div>
                <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice'),'') ; ?>
                </div>
                <div class="box_footer">
                  <button type="submit" class="btn btn_green">Save</button>
                  <input type="checkbox" name="is_template" value="1"><label class="save_box"></label><label>Save in Templates</label>
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
       
       function get_template(notice_id){

            $.ajax({
                url:'<?php echo base_url()."common/template_notice" ?>',    
                type:'POST',
                data:{notice_id:notice_id},
                success:function(data){
                    alert(data);
                }
            });
       } 
    
</script>