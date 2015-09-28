<!--main-->
            <div class="col-sm-7 main main2">
            	<!--breadcrumb-->
           		<div class="row page_header">
                	<div class="col-sm-8">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>                          
                          <li><a href="#">Topics</a></li>
                          <li class="active">List of Topics</li>
                        </ol>
                    </div>
                    <div class="col-sm-4 text-right">
                    	<button class="btn btn_green add_topic">Add New Topic</button>
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <div class="row filter group_filter">
                	<div class="col-sm-12">
                    	<div class="form-group">
                            <select class="form-control">
                                <option>Select Subject</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select year</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select Role</option>
                            </select>
                        </div>
                        <div class="form-group no_effect search_input">
                        	<input class="form-control" type="text" placeholder="Search">
                            <a href="#" class="fa fa-search"></a>
                        </div>
                    </div>
                </div>
                <!--//filter-->
                <!--topics-->
                <div class="row">
                	<div class="col-sm-12 topic_container">
                    	<!--topic1-->
                        <?php 
                            if(!empty($all_topics)) {

                              foreach($all_topics as $topic) {
                            ?>
                    	<div class="box">
                        	<div class="topic_content">
                           		<div class="col-md-6">
                                	<h3><?php echo $topic['topic_name'] ?></h3><p>(Subject : <span><?php echo $topic['subject_name'] ?></span>)</p>
                                	<h3 class="class"><span>Class : </span>First Year Science</h3> 
                                </div>
                                <div class="col-md-6 text-right">
                                	<h3>Submitted By : <span>Ben Jass Teacher</span></h3>
                                </div>
                                <div class="col-sm-12 topic_description">
                                	<p><?php echo $topic['topic_description']; ?></p>
                                </div>
                                <div class="col-sm-12">
                                	<span class="label label_black">Allocated <?php echo $topic['allocation_count']; ?> times</span>
                                    <span class="label label_red">550 Question</span>
                                
                               		<!-- Split button -->
                                    <div class="btn-group">
                                      <button type="button" class="btn btn-default">Approve</button>
                                      <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="caret"></span>
                                      </button>
                                      <ul class="dropdown-menu">
                                        <li><a href="#">Inappropriate</a></li>
                                        <li><a href="#">Appropriate</a></li>
                                      </ul>
                                    </div>
                                    
                                </div>
                            </div>
                            <div class="topic_action">
                           		<a href="#" class="icon icon_edit"></a>
                                <a href="#" class="icon icon_zip"></a>
                                <a href="#" class="icon icon_delete"></a>
                                <a href="#" class="fa fa-angle-double-down"></a>                                
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <!--//topic1-->
                        <?php } 
                    }
                        ?>
                       
                        
                    </div>
                </div>
                <!--//topics-->
			</div>
            <!--//main-->