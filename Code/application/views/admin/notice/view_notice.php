<!--main-->
<div class="col-sm-7 main main2 noticeboard">
  <!--filter-->
    <form method="get" id="filter">
            <div class="row filter">
              <div class="col-sm-12">
                  <div class="form-group">
                        <select class="form-control"   onchange="filter_data()" id="school">
                              <option value="">Select School</option>
                          </select>
                    </div>
                    <div class="form-group">
                       <select class="form-control" name="status" onchange="filter_data()" id="status" >
                            <option value="">Select Status</option>
                            <option value="active">Active</option>
                            <option value="inactive">Inactive</option>
                            <option value="archive">Archive</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control"   onchange="filter_data()" id="year">
                            <option value="">Select Year</option>
                            <option value="2015">2015</option>
                            <option value="2016">2016</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" name="role" id="role" onchange="filter_data()">
                            <option value="">School Role</option>
                            <?php 
                              if(!empty($roles)){ 
                                foreach($roles as $role) {
                                ?>
                                <option value="<?php echo $role['id']; ?>"><?php echo $role['role_name']; ?></option>  
                            <?php }  } ?>
                        </select>
                    </div>
                    <div class="form-group">
                        <select class="form-control" id="classroom" onchange="filter_data()">
                            <option value="">School Classroom</option>
                            <?php 
                              if(!empty($classrooms)){ 
                                foreach($classrooms as $classroom) {
                                ?>
                                <option value="<?php echo $classroom['id']; ?>"><?php echo $classroom['class_name']; ?></option>  
                            <?php }  } ?>
                        </select>
                    </div>
                </div>
            </div>

        </form> 

    <!--//filter-->
    <!--noticeboard-->
    <div class="padding_b30">
    	<div class="col-sm-2 col-sm-offset-10">
            <div class="box_body bulk_action">
                <div class="form-group select">
                    <select class="form-control">
                        <option>Bulk Action</option>
                        <option>Delete</option>
                    </select>
                </div>
            </div>
        </div>
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
                              onclick="return confirm('Are you sure to add this data to archive?')" 
                              data-toggle="tooltip" data-placement="bottom" title="Archive"></a> 
                            <a href="<?php echo base_url().'admin/notice/update/'.$notice['id']; ?>" 
                                class="icon icon_edit_color" data-toggle="tooltip" data-placement="bottom" title="Edit"></a>
                           <!-- To use ZeroClipboard set parameter for button  data-clipboard-target="ID TO COPY TEXT"  -->
                            <button id="notice_<?php echo $notice['id']; ?>" class="btn btn-link icon icon_copy_color"  
                                data-clipboard-target="notice_text_<?php echo $notice['id']; ?>" 
                                data-toggle="tooltip" data-placement="bottom" title="Copy">
                            </button>
                            <a href="<?php echo base_url().'admin/notice/delete/'.$notice['id']; ?>" 
                                onclick="return confirm('Are you sure to delete this data ?')" 
                                class="icon icon_delete_color" data-toggle="tooltip" data-placement="bottom" title="Delete" ></a>
                            <input type="checkbox"><label class="save_box"></label>
                        </div>
                    </div>
                </a>
            </div>                        
        </div>
       
       <?php } } ?> 

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
                   echo  $newDate = date("M d,  Y", strtotime($originalDate));
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

   <?php } } ?> 


<script type="text/javascript">
    
     <?php 
    if(!empty($notices)) { 
        foreach($notices as $notice) {
        ?> 
        var clientTarget = new ZeroClipboard( $("#notice_<?php echo $notice['id']; ?>") );
    <?php } } ?>

    function filter_data(){
        
        var role = $('#role').val();
        var status = $('#status').val();
        var classroom = $('#classroom').val();
        
        if(role == '' ){ $('#role').removeAttr('name'); }
        if(status == '' ){ $('#status').removeAttr('name'); }
        if(classroom == ''){ $('#classroom').removeAttr('name'); }

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

</script>
