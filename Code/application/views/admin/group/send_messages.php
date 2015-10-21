<!--main-->
<div class="col-sm-7 main main2">
	<!--breadcrumb-->
		<div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="Group.html">Group</a></li>                          
              <li><a href="#">Manage Group</a></li>
              <li class="active">Send Message</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
       
    <!--message-->
   	<div class="row">
    	<div class="col-sm-12 new_message">
        	<div class="box exam_card">
                <form method="POST">
                	<div class="box_header">
                    	<h3>Send New Message</h3>
                    </div>
                    <div class="box_body">
                    	<div class="form-group">
                            <label>Select Template</label>
                            <select class="form-control js-example-basic-single" onclick="get_message_template(this.value)" >
                                <option value="" > No Template </option>
                                <?php  
                                    if(!empty($templates)){
                                        foreach($templates as $template) {
                                ?>
                                <option value="<?php echo $template['id']; ?>"><?php echo $template['message_title']; ?></option>    
                                <?php } }else{ ?>
                                <option value="" disabled>No Templates Found.</option>
                                <?php } ?>
                            </select>

                        </div>

                        <div class="form-group">
                            <label> Select Users </label>

                            <select name="all_users[]" class="js-example-basic-single form-control" multiple="multiple">

                                <?php
                                if(!empty($roles)) {
                                    foreach($roles as $role){
                                     ?>
                                     <optgroup label="<?php echo ucfirst($role['role_name']); ?>">
                                        <?php 
                                            if(!empty($users)){ foreach($users as $user) {
                                                  if($user['rid']==$role['id']) {  
                                         ?>
                                           <option value="<?php echo $user['id'] ?>" 
                                            <?php echo set_select('all_users', $user['id']); ?> 
                                             <?php 
                                                if(isset($post_users) && !empty($post_users)){ 
                                                    if(in_array($user['id'],$post_users)){ echo "selected='selected'"; } else { echo "disabled='disabled'"; }
                                                } ?>                                                       
                                            >
                                                <?php echo ucfirst($user['username']); ?>
                                            </option> 
                                        <?php } } }?>
                                     </optgroup>            
                                <?php } } ?>

                             </select>
                            
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('all_users'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('all_users'),'') ; ?>
                        </div>
                        <?php $error = $this->session->flashdata('error'); ?>
  
                        <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
                            <?php echo strip_tags($error) ; ?>
                        </div>

                        <div class="form-group">
                        	<label>Title </label>
                            <input type="text" class="form-control" name="message_title" id="message_title" value="<?php echo set_value('message_title'); ?>" >
                        </div>
    
                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('message_title'),'')) || $my_cnt == 0 ){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('message_title'),'') ; ?>
                        </div>  
                        <div class="form-group">
                        	<label>Message</label>
                            <textarea class="form-control" name="message_desc" id="message_desc"><?php echo set_value('message_desc'); ?></textarea>
                            <label class="notify"><input type="checkbox" name="notify_sms">Notify Student Via SMS</label><br/>
                        </div>
                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('message_desc'),'')) || $my_cnt == 0 ){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('message_desc'),'') ; ?>
                        </div>
                    </div>
                    <div class="box_footer">
                    	<button class="btn btn_green" type="submit">Send</button>
                        <input type="checkbox" name="save_template" value="1" id="save_template">
                        <label class="save_box"></label>
                        <label for="save_template">Save in Templates</label>
                        <a href="<?php echo base_url().'admin/user'; ?>" class='btn btn_black'>Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->

<script type="text/javascript">
    
  $(document).ready(function() {
      $(".js-example-basic-single").select2();
    });

    function get_message_template(msg_id){
        
        if(msg_id != ''){
            $.ajax({
                url:'<?php echo base_url()."common/template_message";?>',
                type:'post',
                data:{msg_id:msg_id},
                success:function(data){
                    data = data.split('###');
                    $('#message_title').val(data[0]);
                    $('#message_desc').val(data[1]); 
                }
            });
        }else{
            $('#message_title').val('');
            $('#message_desc').val(''); 
        }
    
    }

</script>