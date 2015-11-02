<!--main-->
<div class="col-sm-7 main main2 noticeboard mCustomScrollbar" data-mcs-theme="minimal-dark">
  <!--filter-->
    <form method="get" id="filter">
            <div class="filter group_filter">
              <div class="col-sm-12">

                    <div class="form-group">
                       <select class="form-control" name="status" onchange="filter_data()" id="status" >
                            <option value="">Select Status</option>
                            <option value="active">Active</option>
                            <option value="inactive">Inactive</option>
                            <option value="archive">Archive</option>
                            <option value="template">Template</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <select class="form-control" name="role" id="role" onchange="filter_data()">
                            <option value="">School Role</option>
                            <?php 
                              if(!empty($roles)){ 
                                foreach($roles as $role) {
                                ?>
                                <option value="<?php echo $role['id']; ?>"><?php echo ucfirst($role['role_name']); ?></option>  
                            <?php }  } ?>
                        </select>
                    </div>

                    <div class="form-group">
                        <select class="form-control" name="classroom" id="classroom" onchange="filter_data()">
                            <option value="">School Classroom</option>
                            <?php 
                              if(!empty($classrooms)){ 
                                foreach($classrooms as $classroom) {
                                ?>
                                <option value="<?php echo $classroom['id']; ?>"><?php echo $classroom['class_name']; ?></option>  
                            <?php }  } ?>
                        </select>
                    </div>

                <div class="form-group">
                    <select class="form-control" name="order" id="order" onchange="filter_data()">
                        <option value="">Sort By</option>
                        <option value="name_asc">Name Ascending</option>
                        <option value="name_desc">Name Descending</option>
                        <option value="latest">Latest First</option>
                        <option value="older">Older First</option>
                    </select>
                </div>

                </div>
            </div>

        </form> 

    <!--//filter-->
    <!--noticeboard-->

    <div class="padding_b30 col-sm-12">
     
     <form method="post" id="bulk_notice" >
        
        <div class="col-sm-offset-2 col-sm-8 text-left margin_t15">
            <?php echo flashMessage(TRUE,TRUE); ?> 
        </div> 

    	<div class="col-sm-2">
            <div class="box_body bulk_action">
                <div class="form-group select">
                    <select class="form-control" name="bulk_action" onchange="submit_bulk_form(this.value)"> 
                        <option value="">Bulk Action</option>
                        <option value="delete" >Delete</option>
                    </select>
                </div>
            </div>
        </div>
		<div class="clearfix"></div>
      <?php 
        // START To check weather page is on 1st page or not ? if it is on first page do not show add notice field
        if(empty($page_number)) { ?> 
          <div class="col-lg-4 col-md-6">
              <div class="box add_notice shadow_effect text-center">
                    <a href="<?php echo base_url().'admin/notice/add'; ?>">
                        <span class="icon icon_add"></span><br>
                        Add New Notice
                    </a>
                </div>
            </div>

       <?php } // END ?> 
     
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
                            <?php if(in_array($notice['status'],array('active','inactive'))) { ?>
                            <a href="<?php echo base_url().'admin/notice/archive/'.$notice['id']; ?>" class="icon icon_zip_color"
                              onclick="return archive_add_notice(this.href,event)" 
                              data-toggle="tooltip" data-placement="bottom" title="Archive"></a> 
                            <?php }else{ ?>
                            <a href="<?php echo base_url().'admin/notice/archive/'.$notice['id'].'/1'; ?>" class="icon icon_zip_active"
                              onclick="return archive_remove_notice(this.href,event)" 
                              data-toggle="tooltip" data-placement="bottom" title="Archived"></a>
                            <?php } ?>
                            <a href="<?php echo base_url().'admin/notice/update/'.$notice['id']; ?>" 
                                class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="Edit"></a>

                            <a href="<?php echo base_url().'admin/notice/copy/'.$notice['id']; ?>" 
                                class="btn btn-link icon icon_copy_color" data-toggle="tooltip" data-placement="bottom" title="Copy"></a>    
                                    
                            <a href="<?php echo base_url().'admin/notice/delete/'.$notice['id']; ?>" 
                                 onclick="return delete_notice(this.href,event)"
                                class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="Delete" ></a>
                            
                            <input type="checkbox" name="notices_bulk[]" value="<?php echo $notice['id']; ?>"><label class="save_box"></label>
                        </div>
                    </div>
                </a>
            </div>                        
         </div>
       
       <?php } }else{ ?> 
        
        <div class="col-lg-4 col-md-6">
          <div class="box add_notice shadow_effect text-center">
                <a href="#">
                   <h2>  No Notice Found. </h2>
                </a>
            </div>
        </div>

       <?php } ?>
    
    </form>

        <div class="clearfix"></div>
        <div class="text-center ">
        <?php  echo $this->pagination->create_links();  ?>
        </div>
    </div>
        <!--//noticeboard-->        
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
                   echo $newDate = date("M d,  Y", strtotime($originalDate));
                ?> 
        </small>
      </div>
      <div class="modal-body">
         <p id="notice_text_<?php echo $notice['id']; ?>"><?php echo $notice['notice']; ?></p>
        <h4 class="notice_by"><?php echo ucfirst($this->session->userdata('username')); ?><span>ISM Admin</span></h4>
        <div class="clearfix"></div>
      </div>
      
    </div>
  </div>
</div>
    <!-- /.modal -->

   <?php } }else{ ?> 

   <?php  } ?>


<script type="text/javascript">
        
    function delete_notice(href,event){
         event.preventDefault();
         bootbox.confirm("Delete Notice?", function(confirmed) {
            
            if(confirmed){
                window.location.href=href;
            }
            
        });
    }  

    function archive_add_notice(href,event){
         event.preventDefault();
         bootbox.confirm("Add to Archive this Notice?", function(confirmed) {
            
            if(confirmed){
                window.location.href=href;
            }
            
        });
    }  

    function archive_remove_notice(href,event){
         event.preventDefault();
         bootbox.confirm("Delete Notice from Archive?", function(confirmed) {
            
            if(confirmed){
                window.location.href=href;
            }
            
        });
    }  

    function submit_bulk_form(data){
        
        if(data == 'delete'){
            $('#bulk_notice').submit();
        }

        $( "#bulk_notice" ).submit(function( event ) {
          event.preventDefault();
        });
    }     

    function filter_data(){
        
        var role = $('#role').val();
        var status = $('#status').val();
        var classroom = $('#classroom').val();
        var order = $('#order').val();

        if(role == '' ){ $('#role').removeAttr('name'); }
        if(status == '' ){ $('#status').removeAttr('name'); }
        if(classroom == ''){ $('#classroom').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }

        $('#filter').submit();
    }

    <?php if(!empty($_GET['role'])) { ?>
        $('#role').val('<?php echo $_GET["role"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['status'])) { ?>
        $('#status').val('<?php echo $_GET["status"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['classroom'])) { ?>
        $('#classroom').val('<?php echo $_GET["classroom"];?>');    
    <?php } ?>

    <?php if(!empty($_GET['order'])) { ?>
        $('#order').val('<?php echo $_GET["order"];?>');    
    <?php } ?> 

</script>
