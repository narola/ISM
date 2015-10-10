<!--main-->
            <div class="col-sm-7 main main2">
            	<!--breadcrumb-->
           		<div class="row page_header">
                	<div class="col-sm-8">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>                          
                          <li><a href="#">Topics</a></li>
                          <li class="active">List of Topics</li>
                        </ol>
                    </div>
                    <div class="col-sm-4 text-right">
                    	<a href="admin/topic/add" class="btn btn_green add_topic">Add New Topic</a>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <form method="get" id="filter">
                <div class="row filter group_filter">
                	<div class="col-sm-12">
                    	<div class="form-group">
                            <select class="form-control" name="subject" onchange="filter_data()" id="subject">
                                <option value="">Select Subject</option>
                                    <?php 
                                      if(!empty($subjects)){ 
                                        foreach($subjects as $subject) {
                                        ?>
                                        <option value="<?php echo $subject['id']; ?>"><?php echo $subject['subject_name']; ?></option>  
                                    <?php }  } ?>
                            </select>
                        </div>
                        <div class="form-group">
                        <select class="form-control" name="role" onchange="filter_data()" id="role">
                                    <option value="">Select Role</option>
                                    <?php 
                                      if(!empty($roles)){ 
                                        foreach($roles as $role) {
                                        ?>
                                        <option value="<?php echo $role['id']; ?>"><?php echo $role['role_name']; ?></option>  
                                    <?php }  } ?>
                                </select>
                    </div>
                        <div class="form-group no_effect search_input">
                        	<input class="form-control" type="text" placeholder="Search">
                            <a href="#" class="fa fa-search"></a>
                        </div>
                    </div>
                </div>
            </form>
            <?php echo flashMessage(true,false); ?>
                <!--//filter-->
                <!--topics-->
                <div class="row">
                	<div class="col-sm-12 topic_container">
                    	<!--topic1-->
                        <?php 

                            if(!empty($all_topics)) {

                              foreach($all_topics as $topic) {
                            ?>
                    	<div class="box">
                        	<div class="topic_content">
                           		<div class="col-md-6">
                                	<h3><?php echo $topic['topic_name'] ?></h3><p>(Subject : <span><?php echo $topic['subject_name'] ?></span>)</p>
                                	<h3 class="class"><span>Class : </span><?php echo $topic['class_name']; ?> </h3>
                                </div>
                                <div class="col-md-6 text-right">
                                	<h3>Assigned By : <span><?php echo ucfirst($topic['first_name']).' '.ucfirst($topic['last_name']); ?></span></h3>
                                </div>
                                <div class="col-sm-12 topic_description">
                                	<p><?php echo word_limiter(htmlentities($topic['topic_description'],50)); ?></p>
                                </div>
                                <div class="col-sm-12">
                                	<span class="label label_black">Allocated <?php echo $topic['allocation_count']; ?> times</span>
                                    <span class="label label_red"><?php echo $topic['questions_count']; ?> Question<?php echo ($topic['questions_count'] > 1) ? 's' : ''; ?></span>
                                
                               		<!-- Split button -->
                                    
                                    <div class="btn-group">
                                      <button type="button" class="set_status_<?php echo $topic['id']; ?> btn btn-default"><?php echo ($topic['status']) ? $topic['status'] : 'Select Status'; ?></button>
                                      <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="caret"></span>
                                      </button>                                    
                                      <ul class="dropdown-menu" data-topic="<?php echo $topic['id']; ?>">
                                        <li><a class="status" id="Approve">Approve</a></li>
                                        <li><a class="status" id="Inappropriate">Inappropriate</a></li>
                                        <li><a class="status" id="Pending">Pending</a></li>
                                      </ul>
                                      <i class="fa fa-refresh fa-spin status_loader" id="status_loader_<?php echo $topic['id'];?>" style="display:none"></i>
                                    </div>
                                
                                    
                                </div>
                            </div>
                            <div class="topic_action">
                           		<a data-toggle="tooltip" data-placement="right" data-original-title="Edit" class="icon icon_edit"></a>
                                <a data-toggle="tooltip" status="<?php echo $topic['is_archived']; ?>" id="archive_<?php echo $topic['id']; ?>" data-placement="right" data-original-title="Archive" class="archive icon <?php echo ($topic['is_archived']==0) ? 'icon_zip' : 'icon_zip_active'; ?>"> <i id="archived_loader_<?php echo $topic['id']; ?>" class="fa fa-refresh fa-spin topic_loader" style="display:none;"></i></a>
                                <a data-toggle="tooltip" id="delete_<?php echo $topic['id']; ?>" data-placement="right" data-original-title="Delete" class="delete icon icon_delete"></a>
                                <a data-toggle="tooltip" class="fa fa-angle-double-down"></a>                                
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--//topic1-->
                        <?php } 
                    }
                        ?>
                       
                        <nav  class="text-center">
       
                <?php  echo $this->pagination->create_links();  ?>

                </nav>
                    </div>
                </div>
                <!--//topics-->
                
                <!-- Modal -->
                <div class="modal fade" id="close_mate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document" style="width:500px;margin-top:220px;">
                        <div class="modal-content">
                            <div class="modal-header notice_header text-center">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">CONFIRMATION FORM</h4>
                                <small>Sep 7, 2015</small>
                            </div>
                            <div class="modal-body">
                                <p><code><h4>Are sure for want to remove from studymates list?</h4></code></p>
                                    <h4 class="notice_by"><button class="btn btn_black_normal" data-type="close-studymate">OK</button></h4>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
			</div>
            <!--//main-->
            <script type="text/javascript">
    
    function filter_data(){

        var role = $('#role').val();
        var subject = $('#subject').val();
        
        if(role == '' ){ $('#role').removeAttr('name'); }
        if(subject == '' ){ $('#subject').removeAttr('name'); }
        
        $('#filter').submit();

    }
    <?php if(!empty($_GET['role'])) { ?>
        $('#role').val('<?php echo $_GET["role"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['subject'])) { ?>
        $('#subject').val('<?php echo $_GET["subject"];?>');  
    <?php } ?>
    
    $("a.status").click(function(){
        var id = $(this).attr('id');
        var topic_id = $(this).parents('ul').data('topic');        
        $("#status_loader_"+topic_id).show();
        $.ajax({
           url:'<?php echo base_url()."admin/topic/set_topic_status"; ?>',
           dataType: "JSON",
           type:'POST',
           data:{status:id, topic_id:topic_id},
           success:function(data){
                $("button."+data.html).html(data.topic_status);
                $("#status_loader_"+topic_id).hide();
            }
        });
    });

    $("a.archive").click(function(){ 
        var str_id = $(this).attr('id');
        var split_id = str_id.split("_");
        var is_archive = $(this).attr('status');
        var topic_id = split_id[1];
        $("#archived_loader_" + topic_id).show();
        $.ajax({
           url:'<?php echo base_url()."admin/topic/archive_topic"; ?>',
           dataType: "JSON",
           type:'POST',
           data:{is_archive:is_archive, topic_id:topic_id},
           success:function(response){
                
                $(".topic_action a#"+response.id).attr('status', response.status);
                if(response.status == 1)
                { 
                  $(".topic_action a#"+response.id).removeClass("icon_zip").addClass("icon_zip_active");
                  $(".topic_action a#"+response.id).attr('data-original-title', 'Archived');
                }
                else
                {
                    $(".topic_action a#"+response.id).removeClass("icon_zip_active").addClass("icon_zip");
                    $(".topic_action a#"+response.id).attr('data-original-title', 'Archive');
                }
                $("#archived_loader_" + topic_id).hide();
            }
        });
    });

     $("a.delete").click(function(){
        var str_id = $(this).attr('id');
        var split_id = str_id.split("_");
        var topic_id = split_id[1];
      $("#delete_loader_" + topic_id).show();
         //$('#close_mate').modal('show');
        $.ajax({
           url:'<?php echo base_url()."admin/topic/delete_topic"; ?>',
           dataType: "JSON",
           type:'POST',
           data:{topic_id:topic_id},
           success:function(data){
               $('#'+data.id).closest('div[class^="box"]').slideUp("slow", function() { $('#'+data.id).closest('div[class^="box"]')});               
            }
        });
    });
</script>