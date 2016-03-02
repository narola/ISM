<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Assessment</a></li>
              <li class="active">Exams</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->
    <form method="get" id="filter">
        <div class="filter exam_filter">
        	<div class="col-sm-12">
            	<div class="form-group">
                    <select class="form-control" name="exam_type" id="exam_type" onchange="filter_data()">
                        <option value="">Exam Type</option>
                        <option value="topic">Topic</option>
                        <option value="subject">Subject</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control" onchange="filter_data()" name="subject" id="subject">
                        <option value="" >Select Subject</option>
                        <?php if(!empty($all_subjects)) { foreach($all_subjects as $subject) { ?>
                          <option value="<?php echo $subject['id']; ?>"><?php echo ucfirst($subject['subject_name']); ?></option>  
                        <?php } }?>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control" name="topic" id="topic" onchange="filter_data()">
                        <option value="" >Select Topic</option>
                        <?php if(!empty($all_topics)) { foreach($all_topics as $topic) { ?>
                          <option value="<?php echo $topic['id']; ?>"><?php echo ucfirst($topic['topic_name']); ?></option>  
                        <?php } }?>
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
                    <input type="text" name="q" id="q" class="form-control" placeholder="Type Exam name." >
                    <?php if(!empty($_GET['q'])) { ?>
                        <a onclick="filter_data_reverse()" style="cursor:pointer">X</a>
                    <?php }else { ?>
                        <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
                    <?php } ?>
                </div>
                
                

            </div>

            <br/>
            <br/>
            <div class="col-sm-10 text-left">
                <?php echo flashMessage(TRUE,TRUE); ?>
            </div>

        </div>
    </form>
    
    <!--//filter-->
    <!--exam box-->	
    <div class="padding_b30 col-sm-12">
    	<div class="col-sm-12 col-md-6 col-lg-3">
         <div class="box exam_card add_exam text-center">
         	<a href="admin/exam/add">
             	<span class="icon icon_add"></span><br>
                Add New Exam
            </a>
         </div>
        </div>	
        <!--Exam List Start-->
        <?php 
        if(!empty($all_exams)) {
            foreach($all_exams as $exam) {
         ?>
    	<div class="col-sm-12 col-md-6 col-lg-3">
         <div class="box exam_card admin_exam">
            <!--  class="maths" -->
            <div class="box_header text-center" 
                <?php if(!empty($exam['subject_image'])) {?>
                style="background-image: url(<?php echo base_url().'uploads/'.$exam['subject_image'];?>);"
                <?php }else{?>
                style="background-image: url(<?php echo base_url().'uploads/subjects/_dev_8GKA-Objectives.png';?>);"
                <?php }?>
                
                >
                <div class="color_wrapper"></div>
                <h3><?php echo ucfirst($exam['exam_name']); ?></h3>                            
            </div>
            <div class="exams_holder">
            	<div class="exam_year">
                	<p>Year : <span><?php echo $exam['class_name']; ?></span></p>
                    <a style="cursor:pointer;" class="icon icon_option_dark"></a>
                    <div class="popover bottom" role="tooltip">
                        <div class="arrow"></div>
                        <div class="popover-content">
                            <ul>
                                <li><a href="admin/exam/copy/<?php echo $exam['id']; ?>">Copy Exam</a></li>
                                <li><a href="admin/exam/update/<?php echo $exam['id']; ?>">Edit Exam</a></li>
                            </ul>
                        </div>
                    </div>                                
                </div>
                <div class="type_holder">
                	<div class="exam_type"><p>Exam Type</p><p><?php echo ucfirst($exam['exam_type']); ?></p></div>
                	<div class="exam_cat"><p>Category</p><p><?php echo ucfirst($exam['exam_category']); ?></p></div>
                    <div class="clearfix"></div>
                </div>
                <div class="exam_attempted"><p><?php
                $count = $exam['students_attempted'];
                    if($count > 1){
                        echo $count.' Students '; 
                    }else {
                        if($count == 0)
                            echo 'Not Yet ';
                        else{
                            echo $count.' Student ';
                        }
                    } ?>
                    Attempted
                </p></div>
            </div>
         </div>
     	</div>
        <?php } } ?>

        <!--Exam List END -->
        <div class="clearfix"></div>
    </div>

    <nav  class="text-center">
       
        <?php  echo $this->pagination->create_links();  ?>

    </nav>
    <!--//exam box-->
</div>
<!--//main-->


<script type="text/javascript">

function filter_data_reverse(){
        $('#q').removeAttr('name');
        $('#filter').submit();        
    }

    
	$(document).ready(function(){
		$('.exam_year a').click(function(){	
			$('.popover').css('display','none');
			// if($(this).parent('.exam_year').children('.popover').css('display')=='block'){				
				// $(this).parent('.exam_year').children('.popover').css('display','none');
			// }		
		});
	});
    
    function filter_data(){
        
        var exam_type = $('#exam_type').val();
        var subject = $('#subject').val();
        var topic = $('#topic').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(exam_type == '' ){ $('#exam_type').removeAttr('name'); }
        if(subject == '' ){ $('#subject').removeAttr('name'); }
        if(topic == '' ){ $('#topic').removeAttr('name'); }
        if(q == '') { $('#q').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }

        $('#filter').submit();
    }


    $( "#filter" ).submit(function( event ) {
      
        var exam_type = $('#exam_type').val();
        var subject = $('#subject').val();
        var topic = $('#topic').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(exam_type == '' ){ $('#exam_type').removeAttr('name'); }
        if(subject == '' ){ $('#subject').removeAttr('name'); }
        if(topic == '' ){ $('#topic').removeAttr('name'); }
        if(q == '') { $('#q').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }
        
    });



    <?php if(!empty($_GET['exam_type'])) { ?>
        $('#exam_type').val('<?php echo $_GET["exam_type"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['subject'])) { ?>
        $('#subject').val('<?php echo $_GET["subject"];?>');  
    <?php } ?>

     <?php if(!empty($_GET['topic'])) { ?>
        $('#topic').val('<?php echo $_GET["topic"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['q'])) { ?>
        $('#q').val('<?php echo $_GET["q"];?>');    
    <?php } ?> 

    <?php if(!empty($_GET['order'])) { ?>
        $('#order').val('<?php echo $_GET["order"];?>');    
    <?php } ?>          
 

</script>
