<!--main-->
            <div class="col-sm-7 main main2 general_cred">
                <!--breadcrumb-->
                <div class="row page_header">
                    <div class="col-sm-12">
                        <ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>                          
                          <li><a href="#">Topic</a></li>
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
                        <div class="form-group select col-sm-6 no-padding half_size">
                            <select class="form-control">
                                <option>Year</option>
                                <option>2015</option>
                            </select>
                            <a href="#" class="icon icon_add_small"></a>
                        </div>
                        <div class="form-group select col-sm-6 no-padding half_size">
                            <select class="form-control">
                                <option>Subject</option>
                                <option>2015</option>
                            </select>
                            <a href="#" class="icon icon_add_small"></a>
                        </div>
                        <div class="form-group select col-sm-6 no-padding half_size">
                            <select class="form-control">
                                <option>Course</option>
                                <option>2015</option>
                            </select>
                            <a href="#" class="icon icon_add_small"></a>
                        </div>
                        <div class="form-group col-sm-6 no-padding half_size">
                            <input type="text" class="form-control" placeholder="Topic">
                        </div>
                        <div class="form-group col-sm-6 no-padding half_size">
                            <label>Text Description</label>
                            <textarea name="editor1" id="editor1" class="form-control"></textarea>
                        </div>
                        <div class="form-group col-sm-6 no-padding half_size">
                            <label>Keywords for Evaluations</label>
                            <textarea class="form-control"></textarea>
                        </div>
                        <div class="col-sm-12 text-center btn_group">
                            <button class="btn btn_green">Save</button>
                            <button class="btn btn_red">Save & Add More</button>
                            <button class="btn btn_black_normal">Cancle</button>
                        </div>
                        <div class="clearfix"></div>
                     </div>
                     
                    
                    
                </div>
                <!--//main box-->
            </div>
            <!--//main-->
                <script src="http://cdn.ckeditor.com/4.5.3/standard/ckeditor.js"></script>
    
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