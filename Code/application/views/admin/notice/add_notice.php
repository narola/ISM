<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
  <!--breadcrumb-->
  <div class="page_header">
      <div class="col-sm-12">
          <ol class="breadcrumb">
              <li><a href="admin/user">Admin</a></li>                          
              <li><a href="admin/notice">Manage Notices</a></li>
              <li class="active">Add Notice</li>
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
                  <h3>Add New Notice</h3>
                </div>
                <div class="box_body admin_controls with_labels">
                  
                  <div class="form-group select">
                      <label> Templates </label>
                       <select name="roles" class="form-control js-example-basic-single" onchange="get_notice_template(this.value)">
                          <option selected value="">No Template</option>
                          <?php 
                              if(!empty($templates)) {
                                foreach($templates as $template) { 
                              ?>
                              <option value="<?php echo $template['id']; ?>"><?php echo word_limiter($template['notice_title'],7); ?></option>
                          <?php } } ?>
                              
                       </select> 
                  </div>

                  <div class="alert alert-danger hide" id="notice_temp">Notice Template should be unique. </div>
                  
                    <?php $msgerror = $this->session->flashdata('msgerror'); ?>
  
                    <div class="alert alert-danger <?php if(empty(strip_tags($msgerror,''))){ echo 'hide';} ?>">
                        <?php echo strip_tags($msgerror) ; ?>
                    </div>  
                          
                  <div class="form-group select">
                      <label> Roles </label>
                       <select name="role_id" class="form-control">
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
                        <input type="text" class="form-control" id="notice_title" 
                        value="<?php echo set_value('notice_title'); ?>" name="notice_title"
                        onkeyup="check_template_unique()" >
                  </div>

                  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice_title'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice_title'),'') ; ?>
                  </div>

                  <div class="form-group">
                      <label>Notice</label>
                      <textarea class="form-control" name="notice" id="notice"><?php echo set_value('notice'); ?></textarea>
                  </div>
				  <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice'),''))){ echo 'hide';} ?>">
                    <?php echo strip_tags(form_error('notice'),'') ; ?>
                  </div>
					
					<div class="form-group text-left">               
						<div class="circleThree">
							<input type="radio" value="active" <?php echo set_radio('status','active'); ?> id="circleThree" 
                            checked="checked"  name="status">
							<label for="circleThree"></label>
							<span> Active </span>
					   
							<input type="radio" value="inactive" <?php echo set_radio('status','inactive'); ?> id="circleThree1" name="status">
							<label for="circleThree1"></label>
							<span>Inactive</span>

							<input type="radio" value="archive" <?php echo set_radio('status','archive'); ?> id="circleThree2" name="status">
							<label for="circleThree2"></label>
							<span>Archive</span>
						</div>
					</div>
                </div>

                <div class="box_footer">
                  <button type="submit" class="btn btn_green">Save</button>
                  <input type="checkbox" name="is_template" id="is_template" value="1">
                    <label class="save_box"></label>
                    <label for="is_template">Save in Templates</label>
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


    $('#is_template').change(function() {
        
        var notice_title = $('#notice_title').val();

        $.ajax({
            url: '<?php echo base_url()."common/check_template_notice_unique";?>',
            type:'post',
            data:{notice_title:notice_title},
            success:function(data){
                
                if(data == 0){
                    $('#is_template').attr('checked',true);
                }else{
                     $("#notice_temp").removeClass('hide');
                    $('#is_template').attr('checked',false);
                }
            }
        });

    });


    function check_template_unique(){

        var notice_title = $('#notice_title').val();

        if($('#is_template').attr('checked')){

            $.ajax({
                url: '<?php echo base_url()."common/check_template_notice_unique";?>',
                type:'post',
                data:{notice_title:notice_title},
                success:function(data){
                    
                    if(data == 0){
                        $("#notice_temp").addClass('hide');
                        $('#is_template').attr('checked',true);
                    }else{
                        $("#notice_temp").removeClass('hide');
                        $('#is_template').attr('checked',false);
                    }

                }
            });

        }else{
            $("#notice_temp").addClass('hide'); 
        }
    }


    function get_notice_template(notice_id){
        
        if(notice_id != ''){
            $.ajax({
                url:'<?php echo base_url()."common/template_notice" ?>',    
                type:'post',
                data:{notice_id:notice_id},
                success:function(data){
                    data = data.split('###');
                    $('#notice_title').val(data[1]);
                    $('#notice').val(data[0]); 
                }
            });
        }else{
            $('#notice_title').val('');
            $('#notice').val(''); 
        }        
    }
    
</script>

