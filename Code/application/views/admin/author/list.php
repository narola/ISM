<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
            	<!--breadcrumb-->
           		<div class="page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>
                          <li><a href="#">book</a></li>
                          <li class="active">Authors</li>
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
                        
                    </div>
                </div>
                <!--//row table-->
</div>