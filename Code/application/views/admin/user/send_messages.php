<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="admin/user">Manage</a></li>                          
              <li><a href="admin/user">User</a></li>
              <li class="active">Send Message</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
       
    <!--message-->
   	<div class="col-sm-12">
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

                        <div class="alert alert-danger hide" id="msg_temp">Message Template should be unique. </div>

                        <?php $msgerror = $this->session->flashdata('msgerror'); ?>
  
                        <div class="alert alert-danger <?php if(empty(strip_tags($msgerror,''))){ echo 'hide';} ?>">
                            <?php echo strip_tags($msgerror) ; ?>
                        </div>
                        
                        <div class="form-group">
                            <label> Select Role </label>
                            <select name="role_id" id="role_id" class="form-control" onchange="fetch_users(this.value)">
                                   <option value="0"> Select Role </option> 
                                   <?php if(!empty($roles)) { foreach($roles as $role) { ?>
                                    <option value="<?php echo $role['id']; ?>" <?php echo set_select('role_id',$role['id']); ?> >
                                        <?php echo ucfirst($role['role_name']); ?>
                                    </option>
                                   <?php } }?>
                            </select>    
                        </div>

                        <div class="form-group">
                            <label> Select Users   </label>

                            <select name="all_users[]" class="js-example-basic-single form-control" multiple="multiple" id="all_users">

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
                                                    if(in_array($user['id'],$post_users)){ echo "selected='selected'"; } 
                                                } ?>                                                       
                                            >
                                                <?php echo ucfirst($user['username']); ?>
                                            </option> 
                                        <?php } } }?>
                                     </optgroup>            
                                <?php } } ?>

                             </select>
                            
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('all_users[]'),''))){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('all_users[]'),'') ; ?>
                        </div>

                        <?php echo flashMessage(TRUE,FALSE); ?>

                        <div class="form-group">
                        	<label>Title </label>
                            <input type="text" class="form-control" name="message_title" onkeyup="check_template_unique()"
                            id="message_title" value="<?php echo set_value('message_title'); ?>" >
                        </div>
    
                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('message_title'),'')) || $my_cnt == 0 ){ echo 'hide';} ?>">
                          <?php echo strip_tags(form_error('message_title'),'') ; ?>
                        </div>  
						
                        <div class="form-group">
                        	<label>Message</label>
                            <textarea class="form-control" name="message_desc" id="message_desc"><?php echo set_value('message_desc'); ?></textarea>
							<div class="alert alert-danger <?php if(empty(strip_tags(form_error('message_desc'),'')) || $my_cnt == 0 ){ echo 'hide';} ?>">
							  <?php echo strip_tags(form_error('message_desc'),'') ; ?>
							</div>
                            <!-- <label class="notify"><input type="checkbox" name="notify_sms">Notify Student Via SMS</label> --><br/>
                        </div>
                        
                    </div>
                    <div class="box_footer">
                    	<button class="btn btn_green" type="submit">Send</button>
                        <input type="checkbox" name="save_template" value="1" id="save_template">
                        <label class="save_box"></label>
                        <label for="save_template">Save in Templates</label>
                        <a href="<?php echo base_url().$prev_url; ?>" class='btn btn_black'>Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--//message-->
</div>
<!--//main-->

<script type="text/javascript">
    
  $(document).ready(function() {
      $(".js-example-basic-single").select2();
    });

  $('#save_template').change(function() {
        
        var msg_title = $('#message_title').val();

        $.ajax({
            url: '<?php echo base_url()."common/check_template_unique";?>',
            type:'post',
            data:{msg_title:msg_title},
            success:function(data){
                
                if(data == 0){
                    $('#save_template').attr('checked',true);
                }else{
                     $("#msg_temp").removeClass('hide');
                    $('#save_template').attr('checked',false);
                }
            }
        });

    });

    function check_template_unique(){

        var msg_title = $('#message_title').val();

        if($('#save_template').attr('checked')){

            $.ajax({
                url: '<?php echo base_url()."common/check_template_unique";?>',
                type:'post',
                data:{msg_title:msg_title},
                success:function(data){
                    
                    if(data == 0){
                        $("#msg_temp").addClass('hide');
                        $('#save_template').attr('checked',true);
                    }else{
                        $("#msg_temp").removeClass('hide');
                        $('#save_template').attr('checked',false);
                    }
                }
            });

        }else{
            $("#msg_temp").addClass('hide'); 
        }
    }

    function fetch_users(role_id){
        $.ajax({
            url:'<?php echo base_url()."common/fetch_users"; ?>',
            type:'POST',
            data:{role_id:role_id},
            success:function(data){
                $('#all_users').html(data);
            }   
        });
    }

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
                    $('#save_template').attr('checked',false);
                }
            });
        }else{
            $('#message_title').val('');
            $('#message_desc').val(''); 
        }
    
    }

</script>