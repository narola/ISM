<!--main-->
<div class="col-sm-7 main main2 main_wide">
	<!--breadcrumb-->
		<div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="admin/question/set">Assessment</a></li> 
              <li class="active">Question Bank</li>
            </ol>                 
        </div>
    </div>
    <!--//breadcrumb-->
    <!--main content-->
    <form method='post' id="question_add">
        <div class="question_bank">
        	<div class="col-sm-12 general_cred">
            	<!--box-->
                <div class="box">
                    
                    <div class="box_header filter">
                        <h3>Add New Question</h3>
                    </div>

                    <div class="alert alert-danger<?php if(empty(strip_tags(validation_errors(),''))){ echo ' hide';} ?> ">
                        <?php echo validation_errors('',''); ?>
                    </div>

                    <!--box_body-->
                    <div class="box_body add_question_wrapper">
                   		<div class="col-sm-12 padding_t15">
                        	<div class="form-group">
                                <label class="txt_red">Question</label>
                                <textarea class="form-control" name='question_text'><?php echo set_value("question_text") == false ? $question["question_text"] : set_value("question_text"); ?></textarea>
                            </div>
                            
                            <div class="form-group select">
                                <label class="txt_red">Course </label>
                                <select class="form-control" name="course_id" onchange="get_classes(this.value)" id="course_id">
                                <option value=''>Course</option>
                                <?php if(!empty($courses)){ 
                                    foreach ($courses as $course) { ?>
                                        <option value="<?php echo $course['id']; ?>" <?php echo set_select('course_id',$course['id']); ?> >
                                                <?php echo $course['course_name']; ?>
                                        </option>        
                                    <?php } 
                                } ?>
                                </select>
                            </div>

                            <div class="form-group select">
                                <label class="txt_red"> Classroom  </label>
                                <select class="form-control" name="classroom_id" onchange="get_subjects(this.value)" id="classroom_id">
                                    <option value=''>Classroom</option>
                                    <?php if(!empty($classrooms)){ 
                                    foreach ($classrooms as $classroom) { ?>
                                        <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('classroom_id',$classroom['id']); ?>>
                                                <?php echo $classroom['class_name']; ?>
                                        </option>        
                                    <?php } 
                                    }?>
                                </select>
                            </div>

                            <div class="form-group select">
                                <label class="txt_red"> Subject </label>
                                <select class="form-control" name="subject_id" onchange="get_topics(this.value)" id="subject_id">
                                    <option value=''>Subject</option>
                                    <?php if(!empty($subjects)){ 
                                    foreach ($subjects as $subject) { ?>
                                        <option value="<?php echo $subject['id']; ?>" <?php echo set_select('subject_id',$subject['id']); ?>>
                                                <?php echo $subject['subject_name']; ?>
                                        </option>        
                                    <?php } 
                                    }?>
                                </select>
                            </div>

                            <div class="form-group select">
                                <label class="txt_red"> Topic </label>
                                <select class="form-control" name="topic_id" id="topic_id" >
                                    <option value=''>Topic</option>
                                    <?php if(!empty($topics)){ 
                                    foreach ($topics as $topic) { ?>
                                        <option value="<?php echo $topic['id']; ?>" <?php echo set_select('topic_id',$topic['id']); ?> >
                                                <?php echo $topic['topic_name']; ?>
                                        </option>        
                                    <?php } 
                                    }?>
                                </select>
                            </div>

                            <div class="form-group select">
                            	<label class="txt_red">Question Type</label>
                                <select class="form-control" id="question_type" name='question_type'>
                                    <option  value="text">Text</option>
                                    <option  value="paragraph">Paragraph Text</option>
                                    <option selected="selected" value="mcq">Multiple Choice</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="col-sm-12" id="replacing_div1">
                        	<div class="form-group">
                                <input type="text" class="form-control add_answer" placeholder="Their Short Answer">
                            </div>
                            <button class="btn_green btn">Done</button>
                        </div>
                        <div class="col-sm-12" id="replacing_div2">
                        	<div class="form-group">
                                <textarea class="form-control add_answer" placeholder="Their Longer Answer"></textarea>
                            </div>
                            <button class="btn_green btn">Done</button>
                        </div>
                        <div class="col-sm-12" id="replacing_div3">

                            <?php if(!$_POST || $_POST['total_choices'] == 1) { ?>

                            <input type="hidden" name='total_choices' id="total_choices" value="1" <?php echo set_value('total_choices'); ?> >
                            <input type="hidden" name='correct_choice' id="correct_choice" <?php echo set_value('correct_choice'); ?> >
                            
                            <div class="form-group" id="div_1">
                                
                                <input type="radio" name="correct_ans" id="correct_ans" <?php echo set_radio('correct_ans',1); ?> 
                                onchange="correct_choice1(this.value)" value="1">

                                <input type="text" name="choices[]" class="form-control"
                                value="<?php if($_POST) { echo $_POST['choices'][0]; } ?>"   placeholder="Choice 1">

                                <a onclick="add_choices()" class="icon icon_add_small"></a>
                            </div>
                            
                            <?php

                                } else {  
                                 
                                 $post_total_choice = $_POST['total_choices'];
                                 //$post_correct_choice = $_POST[''];   
                            ?>
                            
                            <input type="hidden" name='total_choices' id="total_choices" value="<?php echo set_value('total_choices'); ?>"  >
                            <input type="hidden" name='correct_choice' id="correct_choice" value="<?php echo set_value('correct_choice'); ?>" >
                            
                            <?php for($i=1;$i<=$post_total_choice;$i++) { ?>
                                    
                                    <div class="form-group" id="div_<?= $i ?>">
                                        
                                        <input type="radio" name="correct_ans" id="correct_ans" <?php echo set_radio('correct_ans',$i); ?> 
                                        onchange="correct_choice1(this.value)" value="<?= $i ?>">

                                        <input type="text" name="choices[]" class="form-control" 
                                        value="<?php echo $_POST['choices'][$i-1]; ?>" placeholder="Choice <?= $i ?>">

                                        <?php if($i == $post_total_choice) { ?>
                                            <a onclick="add_choices()" class="icon icon_add_small"></a>
                                            <a onclick="remove_choice()" class="icon icon_subs_small"></a>
                                        <?php }?>

                                    </div>

                            <?php } ?>
                            

                            <?php } ?>

                            <div class="form-group" id="ques_tags">
                            	<label class="txt_red">Question Tags</label>
                                <div class="tag_container">
                                	<!-- <input type="text" data-role="tagsinput"  class="typeahead" name="tags" id="tags"> -->
                                    <select multiple="multiple" name="q_tags[]" id="my_select" class="form-control">
                                           <option value="" disabled>Select Tags</option> 
                                           <?php if(!empty($tags)) { foreach($tags as $tag) { ?>
                                                <option value="<?php echo $tag['id']; ?>" <?php echo set_select('q_tags[]',$tag['id']); ?> >
                                                    <?php echo ucfirst($tag['tag_name']); ?>
                                                </option>
                                           <?php } }else{ ?>
                                                <option value="">No Tag Found</option>
                                           <?php } ?>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 inner_txtarea">
                        	<div class="box_header">
                            	<h3>Evaluation Notes</h3>
                            </div>
                        	<div class="form-group">
                                <textarea class="form-control" name="evaluation_notes"></textarea>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 inner_txtarea">
                        	<div class="box_header">
                            	<h3>Solution</h3>
                            </div>
                        	<div class="form-group">
                                <textarea class="form-control" name="solution"></textarea>
                            </div>
                        </div>

                        <!-- <input class="typeahead" type="text" placeholder="numbers (1-10)" autocomplete="off"> -->

                        <div class="clearfix"></div>
                   	</div>
                    <!--//box-body-->
                    <div class="box_footer">
                    	<input type="checkbox"><label class="save_box"></label><label>Add Question to Preview</label>
                    	
                        <button class="btn btn_green pull-right no-margin">Save & Add More</button>
                        <button class="btn btn_red pull-right">Save</button>
                    </div>
                </div>
                <!--//box-->
            </div>
            <div class="clearfix"></div>
        </div>
    </form>
    <!--main content-->                
</div>
            <!--//main-->

<!-- <link rel="stylesheet" type="text/css" href="http://twitter.github.io/typeahead.js/css/examples.css">
<script type='text/javascript' src="http://twitter.github.io/typeahead.js/releases/latest/typeahead.bundle.js"></script> -->

<script>
    
    function get_classes(course_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_classrooms"; ?>',
           type:'POST',
           data:{course_id:course_id},
           success:function(data){
              $("#classroom_id").html(data);
              $('#subject_id').val('');
              $('#topic_id').val('');
           }
        });
    }

    function get_subjects(classroom_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_subjects"; ?>',
           type:'POST',
           data:{classroom_id:classroom_id},
           success:function(data){
              $("#subject_id").html(data);
              $('#topic_id').val('');
           }
        });
    }

    function get_topics(subject_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_topics"; ?>',
           type:'POST',
           data:{subject_id:subject_id},
           success:function(data){
              $("#topic_id").html(data);
           }
        });
    }


    function test(ch){
        console.log(ch)
    }
    function add_choices(){

        $('a.icon.icon_add_small').remove();
        $('a.icon.icon_subs_small').remove();
        
        var cnt = parseInt($('#total_choices').val())+1;
        $('#total_choices').val(cnt);    
        $('#ques_tags').before('<div id="div_'+cnt+'" class="form-group"><input type="radio" name="correct_ans" id="correct_ans"  onclick="correct_choice1(this.value)" value="'+cnt+'"><input type="text" name="choices[]" class="form-control" placeholder="Choice '+cnt+'"><a onclick="add_choices()" class="icon icon_add_small"></a> <a onclick="remove_choice()" class="icon icon_subs_small"></a></div>');
    }

    function correct_choice1(choice){
        $('#correct_choice').val(choice);
    }

    function remove_choice(){

        var my_cnt = parseInt($('#total_choices').val());
        var cnt = my_cnt-1;
        var correct_choice = $('#correct_choice').val();
        
        if(my_cnt == correct_choice){
            $('#correct_choice').val('');
        }
        
        $('#total_choices').val(cnt);

        if(cnt != 1){
            var append_link = '<a onclick="add_choices()" class="icon icon_add_small"></a><a onclick="remove_choice()" class="icon icon_subs_small"></a>';
            $('#div_'+cnt).append(append_link);
        }else{
            var append_link = '<a onclick="add_choices()" class="icon icon_add_small"></a>';
            $('#div_'+cnt).append(append_link);
        }

        $('#div_'+my_cnt).remove().fadeout();
    }    

    $(document).ready(function(){
        $('#my_select').select2();
    });

   // $('#question_type').val('msq') ;

    $('#replacing_div3').show();
    $('#replacing_div2').hide();
    $('#replacing_div1').hide();

    $(function() {       
        $('#question_type').change(function(){
            if($('#question_type').val() == 'text') {
                $('#replacing_div1').show();
                $('#replacing_div2').hide();
                $('#replacing_div3').hide();
            }
            if($('#question_type').val() == 'paragraph') {
                $('#replacing_div2').show();
                $('#replacing_div1').hide();
                $('#replacing_div3').hide();
            }
            if($('#question_type').val() == 'mcq') {
                $('#replacing_div3').show();
                $('#replacing_div2').hide();
                $('#replacing_div1').hide();
            }           
        });
    });

</script>