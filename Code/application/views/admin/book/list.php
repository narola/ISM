<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
            	<!--breadcrumb-->
           		<div class="page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>
                          <li><a href="#">book</a></li>
                          <li class="active">List Of Books</li>
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
                        <div class="pull-right">
                          <a href="admin/book/add" class="btn btn_green add_topic">Add New Book</a>
                        </div>
                        
                    </div>
                </div>
             

                <!--//filter-->
                <!--button div-->
                
                <!--//button-div-->
                <!--row table-->
               <?php if(!empty($author_books)){
               $i=1;
                    foreach ($author_books as $authorbook) {
                      
                ?>
                <div class="title"><h3><?php echo $authorbook['author']['full_name']; ?></h3></div>
                <div class="authorbook">
                	<div class="col-md-12">
                 
                <!-- -->
                 
                <!-- Carousel items -->

                <?php 
                $f = 0;
                if(!empty($authorbook['books'])){ ?>
                <div id="Carousel_<?php echo $i; ?>" class="carousel slide">
                <div class="carousel-inner">
                   <?php $books_cnt = sizeof($authorbook['books']);
                  foreach ($authorbook['books'] as $books) { ?>
                    <!-- <div class="item active"> -->
                  <?php 
                  if($f == 0){
                         echo '<div class="item active">';
                     }
                    
                     if ($f%4 == 0 && $f != 0){
                         echo '</div>';
                         echo '<div class="item">';
                     }
                      $f++;

                ?>    
                <div class="col-md-3">
                  <div class="thumbnail">
                    <a href="admin/book/book_detail/<?php echo $books['id']; ?>">
                      <div class="book_img_holder">
                        <img class="img-responsive" src="assets/<?php echo $books['front_cover_image']; ?>" onerror="this.src='assets/images/books/dev_PlaceholderBook.png'" alt="Image" style="max-width:100%;">
                      </div>
                    </a>

                       <a href="admin/book/book_detail/<?php echo $books['id']; ?>"><h4><?php echo word_limiter($books['book_name'], 4); ?></h4></a>
                       
                      </div>
                    </div>
                    
                   <?php if($f == $books_cnt){
                    echo '</div>';
                  } ?>
                  <?php } ?>
                </div>

                  <!--.row-->
                 <a data-slide="prev" href="#Carousel_<?php echo $i; ?>" class="left carousel-control">‹</a>
                  <a data-slide="next" href="#Carousel_<?php echo $i; ?>" class="right carousel-control">›</a>
                </div><!--.Carousel-->
                <a href="admin/book/view_all/<?php echo $authorbook['author']['id']; ?>" class="view_books pull-right btn btn-xs">View All</a>
             <?php } ?>
                 
		      </div>
        </div>

               
               <div class="clearfix"></div>
    <?php 
    $i++;
  } ?>
<?php } ?>

            
            <script>
    $(document).ready(function() {

    $('.carousel').carousel({
        interval: 5000
    })
});
  </script>
  </div>