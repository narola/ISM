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
    <div class="question_bank">
    	<div class="col-sm-6 general_cred">
        	<!--box-->
            <div class="box">
                <div class="box_header filter">
                    <h3>Add New Question</h3>
                </div>
                <!--box_body-->
                <div class="box_body add_question_wrapper">
               		<div class="col-sm-12 padding_t15">
                    	<div class="form-group">
                            <label class="txt_red">Question</label>
                            <textarea class="form-control" name='question_text'></textarea>
                        </div>
                        <div class="form-group select">
                        	<label class="txt_red">Question Type</label>
                            <select class="form-control" id="question_type" name='question_type'>
                            	<option value="text">Text</option>
                            	<option selected  value="paragraph">Paragraph Text</option>
                                <option value="mcq">Multiple Choice</option>
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
                    	<div class="form-group">
                            <input type="radio"><input type="text" class="form-control" placeholder="Question 1">
                        </div>
                        <div class="form-group">
                            <input type="radio"><input type="text" class="form-control" placeholder="Question 1"><a href="#" class="icon icon_add_small"></a>
                        </div>                                    
                        <div class="form-group">
                        	<label class="txt_red">Question Tags</label>
                            <div class="tag_container">
                            	<h3><label class="label label-default">Biology</label></h3>
                                <h3><label class="label label-primary">Vertebrates</label></h3>
                                <input type="text">
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
        <div class="col-sm-6 general_cred preview">
        	<!--box-->
            <div class="box">
                <div class="box_header">
                   <h3>Preview</h3>
                </div>
                <!--box_body-->
                <div class="box_body">
                    <div class="question_list">
                    	<!--q1-->
                        
                        <!--//q1-->                        
                    </div>
               	</div>
                <!--//box-body-->
                
            </div>
            <!--//box-->
        </div>
        <div class="clearfix"></div>
    </div>
    <!--main content-->                
</div>
            <!--//main-->
<script>
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