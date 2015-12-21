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
                	<label>Book Name</label>
                    <input type="text" class="form-control" name="book_name" placeholder="Book Name">
                </div>                     

            <div class="form-group authors col-sm-12 col-md-6 col-lg-8 padding_r15_" >
                <label> Select Author </label>

                <select name="authors" class="js-example-basic-single form-control" multiple="multiple">
                    
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
                	<label>Price</label>
                    <input type="text" name="price" class="form-control" placeholder="Price">
                </div>   
                <div class="form-group list-input col-sm-12 col-md-6 col-lg-8 padding_r15_ ">
                <ul>
                    <li>
                    	<label>ISBN NO</label>
                        <input name="isbn_no" type="text" placeholder="ISBN NO" class="form-control ">
                    </li>
                    <li>
                    	<label>Publisher Name</label>
                        <input name="publisher_name" type="text" placeholder="Publisher Name" class="form-control ">
                    </li>
                    <li>
                        <label>Publication Date</label>
                        <input name="publication_date" id="publication_date" type="text" class="form-control datepicker">
                    </li>
                    <li>
                    	<label>Books Description</label>
                        <textarea name="book_desc" class="form-control book_desc"></textarea>
                    </li>
                </ul>
               </div>
                   
                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_">
                        <label>Front Cover</label>
                    <div class="upload_ques_img ">
                    	<input class="form-control"  name="front_cover" id="front_cover" type="file">
                    </div>
                </div>  
                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                        <label>Back Cover</label>
                    <div class="upload_ques_img">
                    	<input class="form-control" name="back_cover" id="back_cover" type="file">
                    </div>
                    <div class="clearfix"></div>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                        <label class="file_label">pdf File</label>
                    <div class="upload_ques_img pdf">
                    	<input class="form-control" name="book_pdf" id="book_pdf" type="file">
                    </div>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                        <label class="file_label">epub File</label>
                    <div class="upload_ques_img epub">
                    	<input class="form-control" name="book_epub" id="book_epub" type="file">
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
$(function() {
     $("input#front_cover").change(function (){
       var file_data = $("#front_cover").prop("files")[0];
       console.log('file_data: '+ file_data.type);
     });
  });
    $(document).ready(function() {
        $(".js-example-basic-single").select2();

        $("#book_frm").validate({
            // Specify the validation rules
            ignore: 'input[type=hidden]', // considers the hidden elements also
            errorElement: 'span',
            errorClass: 'help-block',
            rules: {
                "book_name": "required",
                "authors": "required",
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
                "authors": "Please Select Author",
                "price": "Please enter price",
                "isbn_no": "Please provide ISBN No.",
                "publisher_name": "Please provide Publisher Name",
                "publication_date": "Please provide Publisher Date"
                
            },
            highlight: function(element) { // hightlight error inputs
                var elem = $(element);
                if (elem.hasClass("select2-offscreen")) {
                    $("#s2id_" + elem.attr("id") + " ul").closest('.form-group').removeClass('has-success').addClass('has-error');
                } else {
                    elem.closest('.form-group').removeClass('has-success').addClass('has-error');
                }
            },
            unhighlight: function(element) { // revert the change done by hightlight
                var elem = $(element);
                if (elem.hasClass("select2-offscreen")) {
                    $("#s2id_" + elem.attr("id") + " ul").closest('.form-group').removeClass('has-error');
                } else {
                    elem.closest('.form-group').removeClass('has-error');
                }
            },
            submitHandler: function(form) {
                // event.preventDefault();
                // alert('here');
                // console.log('here');
                var file_data = $("#front_cover").prop("files")[0];
                upload_product_img(form, event, file_data);
                return false;
                // form.submit();
            }
        });

        $("#publication_date").datepicker({
            format: " yyyy",
            viewMode: "years", 
            minViewMode: "years"
        });

       $(".add_book_page").mCustomScrollbar({
            callbacks:{
                whileScrolling:function(){
                    var inp = $('input#publication_date');
                    $('.datepicker').css('top', inp.offset().top + inp.outerHeight());
                }
            }
        }); 
    });
    function upload_product_img(form, event) {
        console.log('here');
        return false;
        /*if (user_file) {
            var file_size = user_file.size / 1024;
            if (file_size > 1024)
            {
                alert('Please upload an image with size less than 1 MB.');
                return false;
            } else {
                form.submit();
            }
        } else {
            event.preventDefault();
        }*/
    }
</script>