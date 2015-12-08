<div class="col-sm-7 main main2 add_book_page general_cred mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
	<div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Manage</a></li>
              <li><a href="#">Books</a></li>
              <li class="active">Add Book</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
   
    <div class="box add_exam_form">
        <div class="box_header">
            <h3><span class="icon icon_info"></span>Add book detail</h3>
        </div>
        <form id="book_frm" method="post" enctype='multipart/form-data'>
            <div class="box_body">	
                <div class="form-group col-sm-12 col-md-6 col-lg-12 padding_r15_">
                	<label class="control-label">Book Name</label>
                    <input type="text" class="form-control" name="book_name" placeholder="Book Name">
                </div>                     
              
            <div class="form-group authors col-sm-12 col-md-6 col-lg-8 padding_r15_" >
                <label class="control-label"> Select Author </label>

                <select name="authors[]" class="js-example-basic-single form-control" multiple="multiple" id="authors">
                    
                    <?php
                       if(!empty($authors)){
                            foreach($authors as $author) {
                     ?>
                                <option value="<?php echo $author['id'] ?>" 
                                <?php echo set_select('authors', $author['id']); ?> 
                                <?php 
                                    if(isset($post_authors) && !empty($post_authors)){ 
                                        if(in_array($author['id'],$post_authors)){ echo "selected='selected'"; } 
                                    } 
                                ?>                                                       
                                > <!-- close of option start tag -->
                                <?php echo ucfirst($author['full_name']); ?>
                            </option> 
                            <?php } ?>
                    <?php } ?>
                 </select>
                <!-- <a <center></center>lass="icon icon_add_small" href="#"></a> -->
            </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-4 padding_r15_ ">
                	<label class="control-label">Price</label>
                    <input type="text" name="price" class="form-control" placeholder="Price">
                </div>   
                <div class="form-group list-input col-sm-12 col-md-6 col-lg-8 padding_r15_ ">
                <ul>
                    <li>
                    	<label class="control-label">ISBN NO</label>
                        <input name="isbn_no" type="text" placeholder="ISBN NO" class="form-control ">
                    </li>
                    <li>
                    	<label class="control-label">Publisher Name</label>
                        <input name="publisher_name" type="text" placeholder="Publisher Name" class="form-control ">
                    </li>
                    <li>
                        <label class="control-label">Publication Date</label>
                        <input name="publication_date" id="publication_date" type="text" class="form-control datepicker">
                    </li>
                    <li>
                    	<label class="control-label">Books Description</label>
                        <textarea name="book_desc" class="form-control book_desc"></textarea>
                    </li>
                </ul>
               </div>
                   
                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_">
                        <label class="control-label">Front Cover</label>
                    <div class="upload_ques_img ">
                    	<input class="form-control" name="front_cover" type="file">
                    </div>
                </div>  
                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                        <label class="control-label">Back Cover</label>
                    <div class="upload_ques_img">
                    	<input class="form-control" name="back_cover" type="file">
                    </div>
                    <div class="clearfix"></div>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                        <label class="control-label">pdf File</label>
                    <div class="upload_ques_img pdf">
                    	<input class="form-control" name="book_pdf" type="file">
                    </div>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                        <label class="control-label">epub File</label>
                    <div class="upload_ques_img epub">
                    	<input class="form-control" name="book_epub" type="file">
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            
            <div class="box_body">
            <div class="col-sm-12 text-center btn_group">
            	<button name="btn_save" type="submit" class="btn btn_green">Save</button>
            	<button type="reset" class="btn btn_black_normal">Cancel</button>
            </div>
            <div class="clearfix"></div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("#book_frm").validate({
            // Specify the validation rules
            errorElement: 'span',
            errorClass: 'help-block',
            rules: {
                "book_name": "required",
                "authors[]": "required",
                "price": "required",
                "isbn_no": "required",
                "publisher_name": "required",
                "publication_date": "required",
                "front_cover": "required",
                "back_cover": "required",
                "book_pdf": "required",
                "book_epub": "required"
            },
            messages: {
                "book_name": "Please provide Book Name",
                "authors[]": "Please Select Author",
                "price": "Please enter price",
                "isbn_no": "Please provide ISBN No.",
                "publisher_name": "Please provide Publisher Name",
                "publication_date": "Please provide Publisher Date",
                "back_cover": "Please select back cover",
                "book_pdf": "Please select pdf file",
                "book_epub": "Please select epub file"
            },
            highlight: function(element) { // hightlight error inputs
                $(element)
                        .closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function(element) { // revert the change done by hightlight
                $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            submitHandler: function(form) {
                form.submit();
            }
        });
        $(".js-example-basic-single").select2();

        $("#publication_date").datepicker({
            format: " yyyy",
            viewMode: "years", 
            minViewMode: "years"
        });

           $(".add_book_page").mCustomScrollbar({
                callbacks:{
                    whileScrolling:function(){
                        console.log('here');
                        var inp = $('input#publication_date');
                        $('.datepicker').css('top', inp.offset().top + inp.outerHeight());
                    }
                }
            }); 
    });
</script>