<!--main-->
<div class="col-sm-7 main main2 main_wide mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="admin/question/set">Assessment</a></li> 
              <li class="active">Question Bank</li>
            </ol>                 
        </div>
    </div>
    <!--//breadcrumb-->
    <!--main content-->
	<div class="margin_15">
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
                    <?php echo flashMessage(TRUE,TRUE); ?>
                    <!--box_body-->
                    <div class="box_body add_question_wrapper admin_controls with_labels">
                   		<div class="col-sm-12 padding_t15">
                        	<div class="form-group">
                                <label class="txt_red">Question</label>
                                <textarea class="form-control" name='question_text' name='question_text'><?php echo set_value('question_text'); ?></textarea>
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
                                }?>
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
                                    <option disabled value="text">Text</option>
                                    <option disabled value="paragraph">Paragraph Text</option>
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

                            <input type="hidden" name='total_choices' id="total_choices" value="1"  >
                            <input type="hidden" name='correct_choice' id="correct_choice" >
                            <div class="choice_wrapper"> 
                                <div class="form-group" id="div_1">
                                    
                                    <input type="radio" name="correct_ans" id="correct_ans" <?php echo set_radio('correct_ans',1); ?> 
                                    onchange="correct_choice1(this.value)" value="1">

                                    <input type="text" name="choices[]" class="form-control choice_detail"
                                    value="<?php if($_POST) { echo $_POST['choices'][0]; } ?>"   placeholder="Choice 1">
                                    <!--<i class="fa fa-plus-circle font-21 component"></i>-->
                                    <i class="icon icon_add_small component"></i>
                                    <!-- <a onclick="add_choices()" class="icon icon_add_small"></a> -->
                                </div>
                            </div>
                            <?php

                                } else {  
                                
                                 $post_total_choice = $_POST['total_choices'];
                                 //$post_correct_choice = $_POST[''];   
                            ?>
                            
                            <input type="hidden" name='total_choices' id="total_choices" value="<?php echo set_value('total_choices'); ?>"  >
                            <input type="hidden" name='correct_choice' id="correct_choice" value="<?php echo set_value('correct_choice'); ?>" >

                            <div class="choice_wrapper">
                            <?php for($i=1;$i<=$post_total_choice;$i++) { ?>
                                    <?php $j = $i-1; ?>
                
                                    <div class="form-group">
                
                <input type="radio" name="correct_ans" id="correct_ans" 
                <?php if(!empty($_POST['correct_choice'])){ if($_POST['correct_choice'] == $i){ echo 'checked="checked"'; }} ?>
                onchange="correct_choice1(this.value)" value="<?php echo $i; ?>">

                <input type="text" name="choices[]" class="form-control choice_detail" value="<?php echo $_POST['choices'][$j]; ?>"
                       placeholder="Enter Choice">

                                        <?php if($i == 1) { ?>
                                            <!--<i class="fa fa-plus-circle font-21 component"></i>-->
                                            <i class="icon icon_add_small component"></i>
                                        <?php }else{ ?>
                                        	<i class="icon icon_subs_small component_close"></i>
                                            <!--<i class="fa fa-minus-circle font-21 component_close"></i>-->
                                        <?php } ?>

                                    </div>

                            <?php } ?>
                            </div>

                            <?php } ?>

                            <div class="form-group" id="ques_tags">
                            	<label class="txt_red">Question Tags</label>
                                <div class="tag_container">
                                	<!-- <input type="text" data-role="tagsinput"  class="typeahead" name="tags" id="tags"> -->
                                    <select multiple="multiple" name="q_tags[]" id="my_select" class="form-control">
                                           <option value="" disabled>Select Tags</option> 
                                           <?php if(!empty($tags)) { foreach($tags as $tag) { ?>
                                                <option value="<?php echo $tag['id']; ?>" 
                                                    <?php echo set_select('q_tags[]',$tag['id']); ?> >
                                                    
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
                                <textarea class="form-control" name="evaluation_notes" name="evaluation_notes"><?php echo set_value('evaluation_notes'); ?></textarea>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6 inner_txtarea">
                        	<div class="box_header">
                            	<h3>Solution</h3>
                            </div>
                        	<div class="form-group">
                                <textarea class="form-control" id="solution" name="solution"><?php echo set_value('solution'); ?></textarea>
                            </div>
                        </div>

                        <!-- <input class="typeahead" type="text" placeholder="numbers (1-10)" autocomplete="off"> -->

                        <div class="clearfix"></div>
                   	</div>
                    <!--//box-body-->
                    <div class="box_footer">
                        <input type="hidden" value="save" id="button_type" name="button_type">
                        <button class="btn btn_green pull-right no-margin" onclick="set_hidden('set_ques')" >Save & Add More</button>
                        <button class="btn btn_red pull-right" onclick="set_hidden('save')" >Save</button>
                        <a href="<?php echo base_url().'admin/question/set'; ?>" class="btn btn_black_normal">Cancel</a>
                    </div>
                </div>
                <!--//box-->
            </div>
            <div class="clearfix"></div>
        </div>
    </form>
	</div>
    <!--main content-->                
</div>
            <!--//main-->

<!-- <link rel="stylesheet" type="text/css" href="http://twitter.github.io/typeahead.js/css/examples.css">
<script type='text/javascript' src="http://twitter.github.io/typeahead.js/releases/latest/typeahead.bundle.js"></script> -->
<script src="assets/ckeditor_full/ckeditor.js"></script>
<script>
    // CKEDITOR.replace( 'solution', { toolbar : [ [ 'EqnEditor', 'Bold', 'Italic' ] ] });
    CKEDITOR.replace( 'solution',{removePlugins : "a11yhelp,about,bidi,blockquote,clipboard," +
"contextmenu,dialogadvtab,div,elementspath,enterkey,entities,popup," +
"filebrowser,find,fakeobjects,flash,floatingspace,listblock,richcombo," +
"forms,horizontalrule,htmlwriter,iframe,image,indent," +
"link,maximize," +
"newpage,pagebreak,pastefromword,pastetext,preview,print," +
"resize,save,menubutton,scayt,selectall,showblocks," +
"smiley,sourcearea,tab,table," +
"tabletools,templates,undo,wsc"} );
    CKEDITOR.replace( 'evaluation_notes',{removePlugins : "a11yhelp,about,bidi,blockquote,clipboard," +
"contextmenu,dialogadvtab,div,elementspath,enterkey,entities,popup," +
"filebrowser,find,fakeobjects,flash,floatingspace,listblock,richcombo," +
"forms,horizontalrule,htmlwriter,iframe,image,indent," +
"link,maximize," +
"newpage,pagebreak,pastefromword,pastetext,preview,print," +
"resize,save,menubutton,scayt,selectall,showblocks," +
"smiley,sourcearea,tab,table," +
"tabletools,templates,undo,wsc"} );
 CKEDITOR.replace( 'question_text',{removePlugins : "a11yhelp,about,bidi,blockquote,clipboard," +
"contextmenu,dialogadvtab,div,elementspath,enterkey,entities,popup," +
"filebrowser,find,fakeobjects,flash,floatingspace,listblock,richcombo," +
"forms,horizontalrule,htmlwriter,iframe,image,indent," +
"link,maximize," +
"newpage,pagebreak,pastefromword,pastetext,preview,print," +
"resize,save,menubutton,scayt,selectall,showblocks," +
"smiley,sourcearea,tab,table," +
"tabletools,templates,undo,wsc"} );

    function set_hidden(button_data){
        if(button_data == 'save'){
            $('#button_type').val('save');
        }else{
            $('#button_type').val('set');
        }
    }
    
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
        //$('a.icon.icon_subs_small').remove();
        
        var cnt = parseInt($('#total_choices').val())+1;
        console.log("add cnt: "+cnt);
        $('#total_choices').val(cnt);    
        $('#ques_tags').before('<div id="div_'+cnt+'"  class="form-group "><input type="radio" name="correct_ans" id="correct_ans"  onclick="correct_choice1(this.value)" value="'+cnt+'"><input type="text" name="choices[]" class="form-control" placeholder="Choice '+cnt+'"><a onclick="add_choices()" class="icon icon_add_small"></a> <a onclick="remove_choice('+cnt+')" class="icon icon_subs_small"></a></div>');
        
        var i=1;
        $($('#ques_tags').prevAll('div').get().reverse()).each(function(){
            var id = 'div_'+ i;
            $(this).attr('id',id);
            i++;
        });
        console.log($('#ques_tags').prevAll('div'));
        
    }

    function correct_choice1(choice){

        $('#correct_choice').val(choice);
    }

    function remove_choice(remove_id){

        console.log('remove_id: '+remove_id);
        var my_cnt = parseInt($('#total_choices').val());
        console.log("my_cnt: "+my_cnt);
        var cnt = my_cnt-1;
        console.log("cnt: "+cnt);
        var correct_choice = $('#correct_choice').val();
        
        $('#total_choices').val(cnt);
        
        if(remove_id == correct_choice){
            $('#correct_choice').val('');
        }

        var i=1;
        $($('#ques_tags').prevAll('div').get().reverse()).each(function(){
            var id = 'div_'+ i;
            $(this).attr('id',id);
            i++;
        });

        // for(var j=1;j<my_cnt;j++){
        //     $('#div_'+j+'>input.form-control').attr('placeholder','Choice '+j);
        // }
        
        if(cnt != 1){
            
            console.log('cnt-'+cnt+'my_cnt-'+my_cnt+'remove_id-'+remove_id);
            
            if(my_cnt != remove_id){
               console.log('IF1');
                $('#div_'+cnt+' > a').remove(); 
                $('#div_'+cnt+' > a > a').remove(); 
                var append_link = '<a onclick="remove_choice('+cnt+')" class="icon icon_subs_small"></a>';
                $('#div_'+cnt).append(append_link);

            }else{

                console.log('IF2--->'+cnt);
                $('#div_'+cnt+' > a').remove(); 
                $('#div_'+cnt+' > a > a').remove(); 
                var append_link = '<a onclick="add_choices()" class="icon icon_add_small"></a><a onclick="remove_choice('+cnt+')" class="icon icon_subs_small"></a>';
                $('#div_'+cnt).append(append_link);
            }

        }else{
            console.log('ELSE');
            var append_link = '<a onclick="add_choices()" class="icon icon_add_small"></a>';
            $('#div_'+cnt).append(append_link);
        }

        $('#div_'+remove_id).remove();
        $('#ques_tags div').each(function(){
            var id = 'div_'+i;
            $(this).attr('id',id);
            i++;
        });
    }    

    $(document).ready(function(){
        $('#my_select').select2();
        var blank_txt = "<span class='col-md-12 blank_error' style='color:red;'>Please Fill Existing Field.</span>";

        var total_choice = $('#total_choices').val();

        $('.component').on('click', function(){
            var firstText = $(this).prev('input').val();
            var last_val = $('.choice_wrapper div').last().find('.choice_detail').val();
            // console.log("firstText: "+firstText);
            // console.log("last_val: "+last_val);
            if( firstText !="" && last_val != ""){
                total_choice = parseInt(total_choice)+1; 
                var html = '<div class="form-group">';
                html += '<input type="radio" name="correct_ans" id="correct_ans" onchange="correct_choice1(this.value)" value="'+total_choice+'">';
                html += '<input type="text" name="choices[]" class="form-control choice_detail" value=""   placeholder="Enter Choice">';
                html += '<i class="icon icon_subs_small component_close"></i>';
                html += '</div>';
                $(this).parent().parent().append(html);
                $(this).parent().parent().find('span.blank_error').remove();
                $('#total_choices').val(total_choice);
            }
            else{
                if( $(this).parent().nextAll('span').hasClass('blank_error') == false){
                    $(this).parent().parent().append(blank_txt);
                }
            }
        });
        
        $('div.choice_wrapper').delegate('i.component_close', 'click', function(){
            var this_ans = $(this).parent().find('#correct_ans').val();
            if($("#correct_choice").val()==this_ans){
                $("#correct_choice").val('');
            }
            $(this).parent().remove();
            total_choice = total_choice-1;
            $('#total_choices').val(total_choice);
        });

        $(document).on('keyup','.choice_detail',function(){
            
            var txtValue = $(this).val();
            var this_ans = $(this).parent().find('#correct_ans').val();

            if(txtValue == ''){
                var nextVal = $(this).parent().next().find('.choice_detail').val();
                $(this).val(nextVal);
                $(this).parent().next().remove();
                
                total_choice = total_choice-1;
                $('#total_choices').val(total_choice);

                if($("#correct_choice").val()==this_ans){
                    $("#correct_choice").val('');
                }

            }else{
               $(this).parent().parent().find('span.blank_error').remove(); 
            }
        });
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