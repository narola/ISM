<!--main-->
            <div class="col-sm-7 main main2 noticeboard">
              <!--breadcrumb-->
              <!--<div class="row page_header">
                  <div class="col-sm-12">
                      <ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>
                          <li class="active">User</li>
                        </ol>
                    </div>
                </div>-->
                <!--//breadcrumb-->
                <!--filter-->
                <div class="row filter group_filter">
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
                                <option>Select Area</option>
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
                <!--noticeboard-->
                <div class="padding_b30">
                  <div class="col-lg-4 col-md-6">
                      <div class="box add_notice shadow_effect text-center">
                            <a href="<?php echo base_url().'admin/notice/add'; ?>">
                                <span class="icon icon_add"></span><br>
                                Add New Notice
                            </a>
                        </div>
                    </div>
                 
                 <?php 
                    if(!empty($notices)) { 
                        foreach($notices as $notice) {
                        ?>   

                  <div class="col-lg-4 col-md-6">                     
                      <div class="box notice shadow_effect">
                            <a href="#myModal_<?php echo $notice['id']; ?>" data-toggle="modal">
                                <div class="notice_header">
                                    <h3>
                                        <?php echo ucfirst(character_limiter($notice['notice_title'],15)); ?>
                                        <span> <?php 
                                                    $originalDate = $notice['created_date'];
                                                   echo  $newDate = date("M d,  Y", strtotime($originalDate));
                                                ?> 
                                        </span>
                                    </h3>
                                </div>
                                <div class="notice_body">
                                    <p><?php echo character_limiter($notice['notice'],300); ?></p>
                                    <div class="notice_action">
                                        <a href="<?php echo base_url().'admin/notice/archive/'.$notice['id']; ?>" class="icon icon_zip_color"
                                          onclick="return confirm('Are you sure to add this data to archive?')" ></a>
                                        <a href="<?php echo base_url().'admin/notice/update/'.$notice['id']; ?>" class="icon icon_edit_color"></a>
                                        <a href="#" class="icon icon_copy_color"></a>
                                        <a href="<?php echo base_url().'admin/notice/delete/'.$notice['id']; ?>" 
                                          onclick="return confirm('Are you sure to delete this data ?')" class="icon icon_delete_color"></a>
                                        <input type="checkbox"><label class="save_box"></label>
                                    </div>
                                </div>
                            </a>
                        </div>                        
                    </div>
                   
                   <?php } } ?> 

                    <div class="clearfix"></div>
                </div>
                <!--//noticeboard-->
                <?php  echo $this->pagination->create_links();  ?>

                <button id="copy-button" data-clipboard-text="Copy Me!" title="Click to copy me.">Copy to Clipboard</button>
      </div>
            <!--//main-->

   <?php 
        if(!empty($notices)) { 
            foreach($notices as $notice) {
            ?>  

         <!-- Modal -->
    <div class="modal fade" id="myModal_<?php echo $notice['id']; ?>" tabindex="-1" role="dialog" aria-labelledby="myModal_<?php echo $notice['id']; ?>Label">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header notice_header text-center">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel"> <?php echo ucfirst($notice['notice_title']); ?></h4>
            <small><?php 
                        $originalDate = $notice['created_date'];
                       echo  $newDate = date("M d,  Y", strtotime($originalDate));
                    ?> 
            </small>
          </div>
          <div class="modal-body">
             <p><?php echo $notice['notice']; ?></p>
            <h4 class="notice_by"><?php echo ucfirst($this->session->userdata('username')); ?><span>ISM Admin</span></h4>
            <div class="clearfix"></div>
          </div>
          
        </div>
      </div>
    </div>
    <!-- /.modal -->

   <?php } } ?> 


<script type="text/javascript">
  var client = new ZeroClipboard( document.getElementById("copy-button") );

  client.on( "ready", function( readyEvent ) {
     alert( "ZeroClipboard SWF is ready!" );

    client.on( "aftercopy", function( event ) {
      // `this` === `client`
      // `event.target` === the element that was clicked
      event.target.style.display = "none";
      alert("Copied text to clipboard: " + event.data["text/plain"] );
    } );
  } );
</script>
