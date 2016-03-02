<!--main-->           
<?php echo flashMessage(true,false); ?>
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-8">
        	<ol class="breadcrumb">
              <li><a href="admin/user">Manage</a></li>                          
              <li><a href="admin/course/lists">Courses</a></li>
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
    <div class="filter group_filter">
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
            	<input class="form-control" name="q" id="q" type="text" placeholder="Type Course Name">
                <?php if(!empty($_GET['q'])) { ?>
                    <a onclick="filter_data_reverse()" style="cursor:pointer">X</a>
                <?php }else { ?>
                    <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
                <?php } ?>
            </div>
        </div>
    </div>
</form>           
    <!--//filter-->
    <!--topics-->
    <div class="">
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
               		<a href="admin/course/update_course/<?php echo $course['id'];?>" data-toggle="tooltip" 
                        data-placement="bottom" data-original-title="Edit" class="icon icon_edit"></a>
                    <a data-toggle="tooltip" id="delete_<?php echo $course['id']; ?>" data-placement="bottom" 
                        href="admin/course/delete_course/<?php echo $course['id']; ?>"
                        onclick="return delete_course(this.href,event)"
                        data-original-title="Delete" class="delete icon icon_delete_color" ></a>
                </div>
                <div class="clearfix"></div>
            </div>
            <!--//topic1-->
            <?php }  }else{ ?>
                
                    <div class="col-md-12">
                        <h3> No Courses Found.</h3>
                    </div>
            
            <?php } ?>
           
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
    
    function filter_data_reverse(){
        $('#q').removeAttr('name');
        $('#filter').submit();        
    }

    function filter_data(){
        var category = $('#category').val();
        var q = $('#q').val();        
        var order = $('#order').val();

        if(category == '' ){ $('#category').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }
        
        $('#filter').submit();
    }

    $( "#filter" ).submit(function() {       
        var category = $('#category').val();
        var q = $('#q').val();
        var order = $('#order').val();
        
        if(category == '' ){ $('#category').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }
        
    });

    <?php if(!empty($_GET['category'])) { ?>
        $('#category').val('<?php echo $_GET["category"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['q'])) { ?>
		$('#q').val('<?php echo $_GET["q"];?>');	
    <?php } ?>

    <?php if(!empty($_GET['order'])) { ?>
        $('#order').val('<?php echo $_GET["order"];?>');    
    <?php } ?> 	

    function delete_course(href,event){
         event.preventDefault();
         bootbox.confirm("Delete Course?", function(confirmed) {
            
            if(confirmed){
                window.location.href=href;
            }
            
        });
    } 

</script>