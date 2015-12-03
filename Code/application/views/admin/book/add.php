<div class="col-sm-7 main main2 general_cred">
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
        <form name="book_frm" method="post" enctype='multipart/form-data'>
            <div class="box_body">	
                <div class="form-group col-sm-12 col-md-6 col-lg-12 padding_r15_">
                	<label>Book Name</label>
                    <input type="text" class="form-control" name="book_name" placeholder="Book Name">
                </div>                     
               
                <!-- <div class="form-group  col-sm-12 col-md-6 col-lg-8 padding_r15_">
                	<label>Author Name</label>
                    <input type="text" placeholder="Author Name"  class="form-control smallinput">
                    <a class="icon icon_add_small" href="#"></a>
                </div> -->

            <div class="form-group authors col-sm-12 col-md-6 col-lg-8 padding_r15_" >
                <label> Select Author </label>

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
                <a class="icon icon_add_small" href="#"></a>
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
                        <input name="publication_date" type="text" placeholder="Publisher Date" class="form-control ">
                    </li>
                    <li>
                    	<label>Books Description</label>
                        <textarea name="book_desc" class="form-control"></textarea>
                    </li>
                </ul>
               </div>
                   
                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                	<div class="upload_ques_img ">
                    	<input name="book_images[]" type="file">
                    </div>
                </div>  
                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                	<div class="upload_ques_img">
                    	<input name="book_images[]" type="file">
                    </div>
                    <div class="clearfix"></div>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                	<div class="upload_ques_img pdf">
                    	<input name="book_pdf" type="file">
                    </div>
                </div>

                <div class="form-group col-sm-12 col-md-6 col-lg-2 padding_r15_ ">
                	<div class="upload_ques_img epub">
                    	<input name="book_epub" type="file">
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
      $(".js-example-basic-single").select2();
    });
    </script>