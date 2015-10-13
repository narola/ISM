<!--main-->
            <div class="col-sm-7 main main2">
            	<!--breadcrumb-->
           		<div class="row page_header">
                	<div class="col-sm-8">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>                          
                          <li><a href="#">Courses</a></li>
                          <li class="active">List of Courses</li>
                        </ol>
                    </div>
                    <div class="col-sm-4 text-right">
                    	<a href="admin/course/add_course" class="btn btn_green add_topic">Add New Course</a>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <form method="get" id="filter">
                <div class="row filter group_filter">
                	<div class="col-sm-12">
                    	<div class="form-group">
                            <select class="form-control" name="category" onchange="filter_data()" id="category">
                                <option value="">Select Category</option>
                                    <?php 
                                      if(!empty($course_category)){ 
                                        foreach($course_category as $category) {
                                        ?>
                                        <option value="<?php echo $category['id']; ?>"><?php echo $category['course_category_name']; ?></option>  
                                    <?php }  } ?>
                            </select>
                        </div>
                        <div class="form-group no_effect search_input">
                        	<input class="form-control" name="q" id="q" type="text" placeholder="Search">
                            <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
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

                            if(!empty($all_courses)) {

                              foreach($all_courses as $course) {
                            ?>
                    	<div class="box">
                        	<div class="topic_content">
                           		<div class="col-md-6">
                                	<h3><?php echo $course['course_name'] ?></h3><p>(Course Nickname : <span><?php echo $course['course_nickname'] ?></span>)</p>
                                	<h3 class="class"><span>Category : </span><?php echo ucfirst($course['course_category_name']).' '.ucfirst($course['course_category_name']); ?></h3>
                                	<h3 class="class"><span>Degree : </span><?php echo ucfirst($course['course_degree']); ?></h3>
                                </div>
                                <div class="col-md-6 text-right">
                                	<h3><span>Type : </span><?php echo $course['course_type']; ?> </h3>
                                </div>
                                <div class="col-sm-12 topic_description">
                                	<p><?php echo word_limiter(htmlentities($course['course_details'],50)); ?></p>
                                </div>   
                                <div class="col-sm-12">
                                    <span class="label label_red"><?php echo $course['course_duration']; ?> <?php echo ($course['course_duration'] > 1) ? 'Years' : 'Year'; ?> Duration </span>
                                </div>
                            </div>
                            <div class="topic_action">
                           		<a href="<?php echo '/admin/course/update_course/'.$course['id'];?>" data-toggle="tooltip" data-placement="right" data-original-title="Edit" class="icon icon_edit"></a>
                                <a data-toggle="tooltip" id="delete_<?php echo $course['id']; ?>" data-placement="right" data-original-title="Delete" class="delete icon icon_delete"></a>
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
            <!-- Modal -->
            <div class="modal fade" id="close_mate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document" style="width:500px;margin-top:220px;">
                    <div class="modal-content">
                        <div class="modal-header notice_header text-center">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">CONFIRMATION FORM</h4>                           
                        </div>
                        <div class="modal-body">
                            <p><code><h4>Are sure for want to remove from Course list?</h4></code></p>
                                <h4 class="notice_by"><button class="btn btn_black_normal" data-id="" data-type="close-course">OK</button></h4>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
<script type="text/javascript">
    
    function filter_data(){
        var category = $('#category').val();
        var q = $('#q').val();        
        
        if(category == '' ){ $('#category').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        
        $('#filter').submit();
    }
    $( "#filter" ).submit(function() {       
        var category = $('#category').val();
        var q = $('#q').val();
        
        if(category == '' ){ $('#category').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        
    });

    <?php if(!empty($_GET['category'])) { ?>
        $('#category').val('<?php echo $_GET["category"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['q'])) { ?>
		$('#q').val('<?php echo $_GET["q"];?>');	
    <?php } ?>	
   
    $("a.delete").click(function(){
        var str_id = $(this).attr('id');
        var split_id = str_id.split("_");
        var course_id = split_id[1];        
        $('button[data-type="close-course"]').attr('data-id',course_id); 
        $('#close_mate').modal('show');      
    });
    
    $(document).on('click','button[data-type="close-course"]',function(e){
        var course_id = $(this).attr('data-id');
        $.ajax({
               url:'<?php echo base_url()."admin/course/delete_course"; ?>',
               dataType: "JSON",
               type:'POST',
               data:{course_id:course_id},
               success:function(data){
                   $('#close_mate').modal('hide');      
                   $('#'+data.id).closest('div[class^="box"]').slideUp("slow", function() { $('#'+data.id).closest('div[class^="box"]')});               
                }
       });
    });
</script>