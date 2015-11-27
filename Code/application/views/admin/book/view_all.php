<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
            	<!--breadcrumb-->
           		<div class="page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>
                          <li><a href="#">book</a></li>
                          <li class="active">Add New Book</li>
                        </ol>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <div class="filter  group_filter">
                	<div class="col-sm-12">
                    	<div class="form-group">
                            <select class="form-control">
                                <option>Select Author</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select tags</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Sort by</option>
                            </select>
                        </div>
                        
                        
                    </div>
                </div>
                <!--//filter-->
                <!--button div-->
                
                <!--//button-div-->
                <!--row table-->
                <div class="title"><h3> <?php echo $author_name['full_name'].'\'s Books'  ?></h3></div>
              <div class="padding_b30 col-sm-12">

                <?php 
                    if(!empty($books)){
                        foreach ($books as $book) { ?>

                	<div class="col-sm-12 col-md-6 col-lg-3">
                     <div class="box bookview">
                      <div class="book_img_holder">
                        <img src="assets/<?php echo $book['image_link']; ?>" class="img-responsive" onerror="this.src='assets/images/books/dev_PlaceholderBook.png'">
                      </div>
                      <!-- <h4><?php echo character_limiter($book['book_name'], 20); ?></h4> -->
                      <a href="admin/book/book_detail/<?php echo $book['id']; ?>"><h4><?php echo word_limiter($book['book_name'], 3); ?></h4></a>
                     
                     </div>
                    </div>	
                           
                       <?php }
                    }

                 ?>
                    <div class="clearfix"></div>
                    <div class="text-center ">
        <?php  echo $this->pagination->create_links();  ?>
        </div>
                </div>
               <!--//row table-->
            </div>