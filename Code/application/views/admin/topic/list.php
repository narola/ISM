<!--main-->
    <div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    	<!--breadcrumb-->
   		<div class="page_header">
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
        <div class="filter group_filter">
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
                <div class="form-group">
                    <select class="form-control" name="order" id="order" onchange="filter_data()">
                        <option value="">Sort By</option>
                        <option value="name_asc">Name Ascending</option>
                        <option value="name_desc">Name Descending</option>
                        <option value="latest">Latest First</option>
                        <option value="older">Older First</option>
                    </select>
                </div>

                <div class="form-group no_effect search_input">
                	<input class="form-control" name="q" id="q" type="text" placeholder="Search">
                    <?php if(!empty($_GET['q'])) { ?>
                        <a onclick="filter_data_reverse()" style="cursor:pointer">X</a>
                    <?php }else { ?>
                        <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
                    <?php } ?>

                </div>
            </div>
        </div>
    </form>
    <?php echo flashMessage(true,false); ?>
        <!--//filter-->
        <!--topics-->
        <div class="">
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
                        	<p><?php echo html_entity_decode(word_limiter($topic['topic_description'],50)); ?></p>
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
                   		<a href="admin/topic/update/<?= $topic['id'] ?>" data-toggle="tooltip" data-placement="bottom" data-original-title="Edit" class="icon icon_edit"></a>
                        <a data-toggle="tooltip" status="<?php echo $topic['is_archived']; ?>" id="archive_<?php echo $topic['id']; ?>" 
                            data-placement="bottom" data-original-title="Archive" 
                            class="archive icon <?php echo ($topic['is_archived']==0) ? 'icon_zip' : 'icon_zip_active'; ?>"> 
                                <i id="archived_loader_<?php echo $topic['id']; ?>" class="fa fa-refresh fa-spin topic_loader" 
                                    style="display:none;"></i>
                        </a>
                        <a data-toggle="tooltip" id="delete_<?php echo $topic['id']; ?>" data-placement="bottom" 
                            href="<?php echo base_url().'admin/topic/delete/'.$topic['id']; ?>"
                            onclick="delete_topic(this.href,event)"
                            data-original-title="Delete" class="icon icon_delete_color">
                        </a>
                        <!-- <a class="fa fa-angle-double-down"></a>                                 -->
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
    </div>
    <!--//main-->
  
<script type="text/javascript">
    
    function delete_topic(href,event){
         event.preventDefault();
         bootbox.confirm("Delete Topic?", function(confirmed) {
            if(confirmed){
                window.location.href=href;
            }
        });
    }

    function filter_data_reverse(){
        $('#q').removeAttr('name');
        $('#filter').submit();        
    }

    function filter_data(){
        var role = $('#role').val();
        var subject = $('#subject').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(role == '' ){ $('#role').removeAttr('name'); }
        if(subject == '' ){ $('#subject').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }
        
        $('#filter').submit();
    }

    $( "#filter" ).submit(function() {
        var role = $('#role').val();
        var subject = $('#subject').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(role == '' ){ $('#role').removeAttr('name'); }
        if(subject == '' ){ $('#subject').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }
        
    });

    <?php if(!empty($_GET['role'])) { ?>
        $('#role').val('<?php echo $_GET["role"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['subject'])) { ?>
        $('#subject').val('<?php echo $_GET["subject"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['q'])) { ?>
		$('#q').val('<?php echo $_GET["q"];?>');	
    <?php } ?>

    <?php if(!empty($_GET['order'])) { ?>
        $('#order').val('<?php echo $_GET["order"];?>');    
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
        $('button[data-type="close-topic"]').attr('data-id',topic_id); 
        $('#close_mate').modal('show');      
    });
    
    $(document).on('click','button[data-type="close-topic"]',function(e){
        var topic_id = $(this).attr('data-id');
        $.ajax({
               url:'<?php echo base_url()."admin/topic/delete_topic"; ?>',
               dataType: "JSON",
               type:'POST',
               data:{topic_id:topic_id},
               success:function(data){
                   $('#close_mate').modal('hide');      
                   $('#'+data.id).closest('div[class^="box"]').slideUp("slow", function() { $('#'+data.id).closest('div[class^="box"]')});               
                }
       });
    });
</script>