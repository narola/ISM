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
                        <div class="pull-right">
                          <a class="btn btn_green add_topic">Add New Book</a>
                        </div>
                        
                    </div>
                </div>
                <!--//filter-->
                <!--button div-->
                
                <!--//button-div-->
                <!--row table-->
               <?php if(!empty($author_books)){
               
                    foreach ($author_books as $authorbook) {
                      
                ?>
                <div class="title"><h3><?php echo $authorbook['author']['full_name']; ?></h3></div>
                <div class="authorbook">
                	<div class="col-md-12">
                <div id="Carousel" class="carousel slide">
                 
                <!-- -->
                 
                <!-- Carousel items -->

                <div class="carousel-inner">
                <?php 
                $f = 0;
                if(!empty($authorbook['books'])){
                  foreach ($authorbook['books'] as $books) {
                   if($f == 0){
                         echo '<div class="item active">';
                     }
                    
                     if ($f%3 == 0 && $f != 0){
                         echo '</div>';
                         echo '<div class="item">';
                     }
                      $f++;
                ?>    
                <div class="col-md-3"><div class="thumbnail"><a href="#"><img src="assets/images/book_img2.jpg" alt="Image" style="max-width:100%;"></a>
                       <h4>Software Engineer</h4>
                       <h5>Roger  Pressman</h5>
                       
                      </div>
                    </div>
                      <?php
                  }

                }
                  
                
                  ?>

                    
                	  
                	<!--.row-->
                </div><!--.item-->
                 
                
                 
                </div><!--.carousel-inner-->
                  <a data-slide="prev" href="#Carousel" class="left carousel-control">‹</a>
                  <a data-slide="next" href="#Carousel" class="right carousel-control">›</a>
                </div><!--.Carousel-->
                 
		</div>
               
               <div class="clearfix"></div>
    <?php 
    
  } ?>
<?php } ?>

            </div>
            <script>
    $(document).ready(function() {
    $('#Carousel').carousel({
        interval: 5000
    })
});
  </script>
