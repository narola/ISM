<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
   <!--breadcrumb-->
   <div class="page_header">
       <div class="col-sm-12">
           <ol class="breadcrumb">
              <li><a href="#">Manage</a></li>
              <li class="active">Authors</li>
          </ol>
      </div>
  </div>
  <!--//breadcrumb-->
  <!--filter-->
  <form method="get" id="filter">
    <div class="filter order_authors admin_controls box_body">
       <div class="col-sm-12">
           <div class="form-group select">
            <select onchange="filter_data()" class="form-control js-example-basic-single" id="author" name="author">
                <option selected value=""> Select Author</option>
                <?php 
                if(!empty($authors_list)) {
                    foreach($authors_list as $author) { 
                      ?>
                      <option value="<?php echo $author['id']; ?>" <?php echo set_select('author', $author['id']); ?> >
                        <?php echo $author['full_name']; ?>
                    </option>
                    <?php } }else{ ?>
                    <option disabled > No Author Found</option>  
                    <?php } ?> 
                </select>


            </div>
                        <!-- <div class="form-group">
                            <select class="form-control">
                                <option>Select tags</option>
                            </select>
                        </div> -->
                        <div class="form-group">
                            <select class="form-control" name="order" id="order" onchange="filter_data()">
                                <option value="">Sort By</option>
                                <option value="name_asc">Name Ascending</option>
                                <option value="name_desc">Name Descending</option>
                                <option value="latest">Latest First</option>
                                <option value="older">Older First</option>
                            </select>
                        </div>
                        
                        <div class="form-group no_effect search_input search_author">
                            <input type="text" name="q" id="q" class="form-control" placeholder="Type Author Name/Education" >
                            
                            <?php if(!empty($_GET['q'])) { ?>
                            <a onclick="filter_data_reverse()" style="cursor:pointer">X</a>
                            <?php }else { ?>
                            <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
                            <?php } ?>

                        </div>
                        
                    </div>
                </div>
            </form>
            <!--//filter-->
            <!--button div-->

            <!--//button-div-->
            <!--row table-->
            <div class="tabel_view">
               <div class="col-sm-12">
                   <div class="table-responsive">
                       <table id="table_author" class="table table-striped table-bordered table_user">
                           <thead>
                            <tr>
                                <th style="width: 50px;"></th>
                                <th style="">Author Name</th>
                                <th>Education</th>
                                <th style="width:350px;">Books</th>
                                <th>Status</th>
                                <th style="width:180px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>

                            <?php if(!empty($author_books)){
                                foreach ($author_books as $author) { ?>

                                <tr>
                                    <td class="checkbox_td">
                                        <div class="squaredThree">
                                            <input type="checkbox" name="check[]" value="<?php echo $author['author']['id']; ?>">
                                            <label for="squaredThree"></label>
                                        </div>
                                    </td>
                                    <td class="username">
                                        <div class="chat_img_holder">
                                            <img class="mCS_img_loaded" src="uploads/<?php echo $author['author']['profile_pic']; ?>" 
                                            onerror="this.src='assets/images/avatar.png'">
                                        </div>
                                        <h4><?php echo $author['author']['full_name']; ?></h4>

                                    </td>
                                    <td><?php echo $author['author']['education']; ?></td>
                                    <td>
                                        <div class="booklist_tab clearfix">
                                            <ul>
                                                <?php if(!empty($author['books'])){ 
                                                    foreach ($author['books'] as $book){ ?>

                                                    <li>
                                                        <div class="book_holder">
                                                            <a href="admin/book/book_detail/<?php echo $book['id']; ?>"><img class="img-responsive" data-toggle="tooltip" data-placement="bottom" title="<?php echo $book['book_name']; ?>" src="assets/<?php echo $book['front_cover_image']; ?>" onerror="this.src='assets/images/books/dev_PlaceholderBook.png'"></a>
                                                        </div>
                                                    </li>

                                                    <?php } ?>
                                                    <a class="view_books pull-right btn-xs btn" href="admin/book/view_all/<?php echo $author['author']['id']; ?>">View All</a>
                                                    <?php } else{
                                                        echo 'No books belongs to this author.';
                                                    }

                                                    ?>
                                                </ul>

                                            </div>
                                        </td>
                                        <td>
                                            <?php if($author['author']['user_status']=='active'){ 
                                                echo '<p class="active">Active</p>'; 
                                            }elseif($author['author']['user_status']=='blocked'){ 
                                                echo '<p style="color:red">Blocked</p>';
                                            } ?>
                                        </td>
                                        <td>
                                            <a class="icon icon_timeline" href="#"></a>
                                            <a class="icon icon_books" href="#"></a>
                                            <a class="icon icon_performance" href="#"></a>
                                            <a class="icon icon_blockgroup" href="#"></a>
                                            <a class="icon icon_mail" href="#"></a>
                                            <a class="icon icon_chat" href="#"></a>
                                        </td>
                                    </tr>
                                    <?php } ?>
                                    <?php } ?>
                                </tbody>
                            </table>
                        </div>
                        <nav  class="text-center">

                            <?php echo $this->pagination->create_links(); ?>

                        </nav>
                    </div>
                </div>
                <!--//row table-->
            </div>
            <script type="text/javascript">
            jQuery(document).ready(function($) {
                $(".js-example-basic-single").select2({ placeholder: "Select Author"});   
            });
            function filter_data_reverse(){
                $('#q').removeAttr('name');
                $('#filter').submit();        
            }
            function filter_data() {

                var q = $('#q').val();
                var author = $('#author').val();
                var order = $('#order').val();

                if (q == '') {$('#q').removeAttr('name');}
                if (author == '') {$('#author').removeAttr('name');}
                if(order == ''){  $('#order').removeAttr('name'); }

                $('#filter').submit();
            }

            $("#filter").submit(function (event) {

                var q = $('#q').val();
                var author = $('#author').val();
                var order = $('#order').val();

                if (q == '') {$('#q').removeAttr('name');}
                if (author == '') { $('#author').removeAttr('name'); }
                if(order == ''){  $('#order').removeAttr('name'); }

            });

            <?php if(!empty($_GET['order'])) { ?>
                $('#order').val('<?php echo $_GET["order"];?>');    
                <?php } ?>

                <?php if (!empty($_GET['q'])) { ?>
                    $('#q').val('<?php echo $_GET["q"]; ?>');
                    <?php } ?>

                    <?php if (!empty($_GET['author'])) { ?>
                        $('#author').val('<?php echo $_GET["author"]; ?>');
                        <?php } ?>
                        </script>