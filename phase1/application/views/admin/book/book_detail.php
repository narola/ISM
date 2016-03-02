<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
		<div class="page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Manage</a></li>
              <li class="active">Book Detail</li>
            </ol>
        </div>
    </div>
        <!--//breadcrumb-->
      
         <!--//main -->
        
        <div class="row">
        	<div class="col-sm-12 topic_container bookdetail">
            	<!--topic1-->
            	<div class="box">
                	<div class="topic_content">
                   		<div class="col-sm-12">
                        	<h3 class="txt_blue"><?php echo $book_detail['book_name']; ?></h3>
                        </div>
                        <div class="col-sm-3"><img src="assets/<?php echo $book_detail['front_cover_image']; ?>" class="img-responsive" onerror="this.src='assets/images/books/dev_PlaceholderBook.png'"></div>
                        <div class="col-sm-9 topic_description">
                        	<p><?php echo $book_detail['book_description']; ?></p>
                        </div>
                        <div class="col-sm-12">
                        	
                            <a href="#" id="expand_message" class="fa fa-angle-double-down"></a>                            
                        </div>
                        <?php if(!empty($book_detail['authors'])){ ?>
                        <div class="col-sm-12">
                        	<h3 class="txt_blue">About the author</h3>
                        <?php foreach ($book_detail['authors'] as $author) { ?>
                                <p><?php echo $author['about_author']; ?></p>
                            
                        <?php } ?>
                        </div>
                        
                        <?php } ?>
                        <div class="col-sm-12">
                        <h3 class="txt_blue">Bibliographic information</h3>
                    <div class="detailtex">
                        <p>Title<span><?php echo $book_detail['book_name']; ?></span></p><br>
                        <?php if(!empty($book_detail['authors'])){ ?>
                        <p>Authors<span><?php 
                        $lastElement = end($book_detail['authors']);
                        foreach ($book_detail['authors'] as $key => $value) {
                           echo $value['full_name'];
                            if($value != $lastElement) {
                                 echo ', ';
                            }
                        } ?></span></p><br>
                        <?php } ?>
                        <p>Edition8,<span> illustrated</span></p><br>
                        <p>Publisher<span>	<?php echo $book_detail['publisher_name']; ?></span></p><br>
                        <p>ISBN	<span><?php echo $book_detail['isbn_no']; ?></p><br>
                        <!-- <p>Length	<span>976 pages</span></p><br> -->
                        <p>Subjects<span>	
                        Computers
                         › 
                        Software Development & Engineering
                         › 
                        General</span></p>
                    </div>
                        </div>
                        
                    <div class="col-sm-12  btn_group">
                	<button class="btn btn_blue">Add Assignment</button>
                	<button class="btn btn_black_normal">add Exam</button>
                	<button class="btn btn_red">Add Info </button>
                </div>
                    </div>
                    
                    <div class="clearfix"></div>
                </div>
                <!--//topic1-->
               
			</div>
        </div>
        
        
        
          <!--//main -->
       <!--//row table-->
    </div>