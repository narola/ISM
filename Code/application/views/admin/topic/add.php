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
                        <h3>Add New Topic</h3>
                    </div>
                    <div class="box_body">  
                        <form method="post">
                        <div class="form-group select col-sm-6 no-padding half_size">
                            <select class="form-control" name="course_id" onchange="get_subjects(this.value)" id="course_id">
                                <option>Select Course</option>
                                <?php 
                                  if(!empty($courses)){ 
                                    foreach($courses as $course) {
                                  ?> 
                                <option value="<?php echo $course['id']; ?>"> <?php echo $course['course_name']; ?></option>
                                <?php }  }else{ ?>
                                <option > No Course</option>
                                <?php } ?>
                            </select>
                            <a href="#" class="icon icon_add_small"></a>
                        </div>
                        <div class="form-group select col-sm-6 no-padding half_size">
                            <select class="form-control" name="subjects" id="subject_id">
                                <option>Select Subject</option>
                            </select>
                            <a href="#" class="icon icon_add_small"></a>
                        </div>
                        <div class="form-group col-sm-6 no-padding half_size">
                            <div class="form-group col-sm-12 no-padding half_size"><input name="topic_name" type="text" class="form-control" placeholder="Topic"></div>
                            <div class="form-group col-sm-12 no-padding half_size"><label>Keywords for Evaluations</label>
                            <textarea name="keywords" class="form-control"></textarea></div>
                        </div>
                        <div class="form-group col-sm-6 no-padding half_size">
                            <label>Text Description</label>
                            <textarea name="topic_desc" id="editor1" class="form-control"></textarea>
                        </div>
                       
                        <div class="col-sm-12 text-center btn_group">
                            <button class="btn btn_green" type="submit" name="save">Save</button>
                            <button class="btn btn_red" type="submit" name="save_more">Save & Add More</button>
                            <button class="btn btn_black_normal" type="reset">Cancel</button>
                        </div>
                        <div class="clearfix"></div>
                    </form>
                     </div>
                     
                    
                    
                </div>
                <!--//main box-->
            </div>
            <!--//main-->
                <script src="assets/js/ckeditor.js"></script>
    
    <script>
        // Replace the <textarea id="editor1"> with a CKEditor
        // instance, using default configuration.
        CKEDITOR.replace( 'editor1' );
        <!--ckeditor-->
            CKEDITOR.editorConfig = function( config ) {
            config.toolbarGroups = [
                { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
                { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
                { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
                { name: 'forms', groups: [ 'forms' ] },
                '/',
                { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
                { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
                { name: 'links', groups: [ 'links' ] },
                { name: 'insert', groups: [ 'insert' ] },
                { name: 'styles', groups: [ 'styles' ] },
                { name: 'colors', groups: [ 'colors' ] },
                { name: 'tools', groups: [ 'tools' ] },
                { name: 'others', groups: [ 'others' ] },
                { name: 'about', groups: [ 'about' ] }
            ];
        
            config.removeButtons = 'Source,Save,Templates,Cut,Undo,Find,SelectAll,Scayt,Form,NewPage,Preview,Print,PasteText,Paste,Copy,Redo,Replace,PasteFromWord,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Strike,Subscript,Superscript,RemoveFormat,NumberedList,BulletedList,Outdent,Indent,Blockquote,CreateDiv,BidiLtr,BidiRtl,Language,Anchor,Unlink,Link,Image,Flash,Table,HorizontalRule,Smiley,SpecialChar,PageBreak,Iframe,Format,Styles,TextColor,BGColor,ShowBlocks,Maximize,About';
        };

        function get_subjects(course_id){
            $.ajax({
               url:'<?php echo base_url()."admin/topic/ajax_get_subjects"; ?>',
               type:'POST',
               data:{course_id:course_id},
               success:function(data){
                  $('#subject_id').html(data);
               }
            });
      }
    </script>
    <script>
        $(function () {
          $('[data-toggle="popover"]').popover()
        })
        jQuery(document).ready(function(){
            jQuery('.exam_year .icon_option_dark').click(function(){
                if(jQuery(this).parent().children('.popover').css('display')=='block'){
                    jQuery(this).parent().children('.popover').css('display','none');
                }
                else{
                    jQuery(this).parent().children('.popover').css('display','block');
                };
            });         
            
        });
        
    </script>