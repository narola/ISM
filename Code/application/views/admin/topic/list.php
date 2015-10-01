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
                    	<button class="btn btn_green add_topic">Add New Topic</button>
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
                                	<p><?php echo word_limiter($topic['topic_description'],50); ?></p>
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
                                    </div>
                                
                                    
                                </div>
                            </div>
                            <div class="topic_action">
                           		<a data-toggle="tooltip" data-placement="right" data-original-title="Edit" class="icon icon_edit"></a>
                                <a data-toggle="tooltip" data-status="<?php echo $topic['is_archived']; ?>" id="archive_<?php echo $topic['id']; ?>" data-placement="right" data-original-title="Archive" class="archive icon icon_zip"></a>
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
        $.ajax({
           url:'<?php echo base_url()."admin/topic/set_topic_status"; ?>',
           dataType: "JSON",
           type:'POST',
           data:{status:id, topic_id:topic_id},
           success:function(data){
                $("button."+data.html).html(data.topic_status);
            }
        });
    });

    $("a.archive").click(function(){
        var str_id = $(this).attr('id');
        var split_id = str_id.split("_");
        var is_archive = $(this).data['status'];
        var topic_id = split_id[1];
        $.ajax({
           url:'<?php echo base_url()."admin/topic/archive_topic"; ?>',
           dataType: "JSON",
           type:'POST',
           data:{is_archive:is_archive, topic_id:topic_id},
           success:function(data){
                console.log(data);
            }
        });
    })
</script>