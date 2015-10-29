<!--main-->
<div class="col-sm-7 main main2 general_cred">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
              <li><a href="admin/user">Manage</a></li>                          
              <li><a href="admin/topic/lists">Topic</a></li>
              <li class="active">Add Topic</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--main box-->
    <div class="box">
        <div class="box_header">
            <h3>Update Topic</h3>
        </div>
        <div class="box_body">  
            <form method="post">

                <div class="form-group select col-sm-6 no-padding half_size">
                    
                    <select class="form-control myselect2" name="course_id" onchange="get_classes(this.value)" id="course_id">
                        <option value="">Select Course</option>
                        <?php 
                          if(!empty($courses)){ 
                            foreach($courses as $course) {
                          ?> 
                        <option value="<?php echo $course['id']; ?>" <?php echo set_select('course_id', $course['id']); ?>> 
                                <?php echo $course['course_name']; ?>
                        </option>

                        <?php }  }else{ ?>
                        <option > No Course</option>
                        <?php } ?>
                    </select>
                    <a href="#" class="icon icon_add_small"></a>
                    <?php echo myform_error('course_id'); ?>
                </div>
                
                <div class="form-group select col-sm-6 no-padding half_size">
                    <select class="form-control" name="classrooms" id="classroom_id" onchange="get_subjects(this.value)">
                        <option value="">Select Classroom</option>
                        <?php if(!empty($classrooms)){ 
                        foreach ($classrooms as $classroom) { ?>
                            <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('classrooms',$classroom['id']); ?>>
                                    <?php echo $classroom['class_name']; ?>
                            </option>        
                        <?php } 
                        }?>
                    </select>
                    <a href="#" class="icon icon_add_small"></a>
                    <?php echo myform_error('classrooms'); ?>
                </div>

                <div class="form-group select col-sm-6 no-padding half_size">

                    <select class="form-control" name="subjects" id="subject_id" onchange="get_topic(this.value)">
                        <option value="">Select Subject</option>
                        
                        <?php if(!empty($subjects)){ 
                        foreach ($subjects as $subject) { ?>
                            <option value="<?php echo $subject['id']; ?>" <?php echo set_select('subjects',$subject['id']); ?>>
                                    <?php echo $subject['subject_name']; ?>
                            </option>        
                        <?php } 
                        }?>

                    </select>
                    <a href="#" class="icon icon_add_small"></a>
                    <?php echo myform_error('subjects'); ?>
                </div>

                <div class="form-group select col-sm-6 no-padding half_size">
                    <select class="form-control" name="topic_id" id="topic_id">
                        <option value="">Select Main Topic</option>
                        <?php if(!empty($topics)){ 
                        foreach ($topics as $topic) { ?>
                            <option value="<?php echo $topic['id']; ?>" <?php echo set_select('topic_id',$topic['id']); ?> >
                                    <?php echo $topic['topic_name']; ?>
                            </option>        
                        <?php } 
                        }?>
                    </select>
                    <a href="#" class="icon icon_add_small"></a>
                    <?php echo myform_error('topic_id'); ?> 
                </div>

                <div class="form-group col-sm-6 no-padding half_size">
                    <div class="form-group col-sm-12 no-padding half_size">
                        <input name="topic_name" type="text" class="form-control" 
                               value="<?php  echo set_value("topic_name") == false ? $tutorial_topic["topic_name"] : set_value("topic_name"); ?>"
                               placeholder="Topic">
                        <?php echo myform_error('topic_name'); ?>
                    </div>
                </div>
                <div class="form-group col-sm-6 no-padding half_size">
                    <div class="form-group col-sm-12 no-padding half_size">
                        <label>Keywords for Evaluations</label>
                        <!-- <textarea name="keywords" class="form-control"></textarea> -->
                       <input type="text" data-role="tagsinput" 
                       value="<?php  echo set_value("evaluation_keywords") == false ? $tutorial_topic["evaluation_keywords"] : set_value("evaluation_keywords"); ?>"
                       name="keywords" id="keywords">
                       <?php echo myform_error('keywords'); ?>
                    </div>
                </div>

                <div class="form-group col-sm-6 no-padding half_size">
                    <label>Text Description</label>
                    <textarea name="topic_desc" id="editor1" class="form-control"><?php echo $tutorial_topic['topic_description']; ?></textarea>
                </div>
               
                <div class="col-sm-12 text-center btn_group">
                    <button class="btn btn_green" type="submit" name="save">Update</button>
                    <a href="<?php echo $prev_url; ?>" class="btn btn_black_normal">Cancel</a>
                </div>
                <div class="clearfix"></div>
            </form>
         </div>

    </div>
    <!--//main box-->
</div>
<!--//main-->
            
<script src="http://cdn.ckeditor.com/4.5.3/standard/ckeditor.js"></script>
<script>
        
    
    $('#course_id').val('<?php echo $tutorial_topic["course_id"] ?>');        
    $('#classroom_id').val('<?php echo $tutorial_topic["classroom_id"] ?>');        
    $('#subject_id').val('<?php echo $tutorial_topic["subject_id"] ?>');        
    $('#topic_id').val('<?php echo $tutorial_topic["topic_id"] ?>');        

    function reset_topic(){
        $('input#keywords').tagsinput('removeAll');
        CKEDITOR.instances.editor1.setData('');
    }

    $(document).ready(function() {
        $("select").select2();
    });
    


    // Replace the <textarea id="editor1"> with a CKEditor
  //   // instance, using default configuration.
        CKEDITOR.replace( 'editor1',{removePlugins : "a11yhelp,about,bidi,blockquote,clipboard," +
"contextmenu,dialogadvtab,div,elementspath,enterkey,entities,popup," +
"filebrowser,find,fakeobjects,flash,floatingspace,listblock,richcombo," +
"forms,horizontalrule,htmlwriter,iframe,image,indent," +
"link,maximize," +
"newpage,pagebreak,pastefromword,pastetext,preview,print," +
"resize,save,menubutton,scayt,selectall,showblocks," +
"smiley,sourcearea,specialchar,tab,table," +
"tabletools,templates,undo,wsc"} );

        
 

    function get_classes(course_id){
        $.ajax({
           url:'<?php echo base_url()."admin/question/ajax_get_classrooms"; ?>',
           type:'POST',
           data:{course_id:course_id},
           success:function(data){
              $("#classroom_id").html(data);
              
              $('#subject_id').val('');
              $('#topic_id').val('');
              
              $('#subject_id').html('');
              $('#topic_id').html('');

              $('#select2-chosen-2').html('Select Classroom');
              $('#select2-chosen-3').html('Select Subject');
              $('#select2-chosen-4').html('Select Main Topic');
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
              $('#topic_id').val('');$('#subject_id').val('');
              $('#topic_id').html('');
              
              $('#select2-chosen-3').html('Select Subject');
              $('#select2-chosen-4').html('Select Main Topic');
           }
        });
  }

  function get_topic(subject_id){
        
        //Fetch Topics from topics table not from tutorial topics
        $.ajax({
           url:'<?php echo base_url()."admin/topic/ajax_get_topics"; ?>',
           type:'POST',
           data:{subject_id:subject_id},
           success:function(data){
              $("#topic_id").html(data);
           }
        });

  }

    // $(function () {
    //   $('[data-toggle="popover"]').popover();
    // });

    // jQuery(document).ready(function(){

    //     jQuery('.exam_year .icon_option_dark').click(function(){
    //         if(jQuery(this).parent().children('.popover').css('display')=='block'){
    //             jQuery(this).parent().children('.popover').css('display','none');
    //         }
    //         else{
    //             jQuery(this).parent().children('.popover').css('display','block');
    //         };
    //     });         

    // });
    
</script>