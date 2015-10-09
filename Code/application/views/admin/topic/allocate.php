<!--main-->
            <div class="col-sm-7 main main2 main_wide">
            	<!--breadcrumb-->
           		<div class="row page_header">
                	<div class="col-sm-12">
                    	<ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>                          
                          <li><a href="#">Topics</a></li>
                          <li class="active">Allocate Topic</li>
                        </ol>                  
                    </div>
                </div>
                <!--//breadcrumb-->
                <!--filter-->
                <div class="row filter group_filter">
                	<div class="col-sm-12">
                    	<div class="form-group">
                            <select class="form-control">
                                <option>Select Topic</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select Group</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Sort By</option>
                            </select>
                        </div>
                        <div class="form-group no_effect text-right">
                        	<button class="btn btn_green add_topic no-margin">Add New Topic</button>
                        </div>
                    </div>
                </div>
                <!--//filter--> 
                <!--row-->
                <div class="allocate_topic">
                	<div class="col-sm-6">
                    	<div class="box general_cred">
                        	<div class="box_header">
                            	<h3>Allocate Topic for Groups</h3>
                            </div>
                            <div class="box_body topic_groups">
                            	<table class="table table-striped table_group">
                                    <?php
                                    if(!empty($groups)){ 
                                        foreach ($groups as $group) { ?>
                                            <tr>
                                        <td class="username">
                                            <div class="chat_img_holder"><img src="../images/group1.jpg"></div>
                                            <a href="<?php echo 'admin/topic/allocate/'.$group['id'] ?>"><h4><span>Group Name : </span> <?php echo $group['group_name']; ?> <span> [<?php echo $group['course_name']; ?>]</span></h4></a>
                                            <table class="group_members">
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                            <div class="chat_img_holder"><img src="../images/user1.jpg"></div>
                                                            <p>Mary Watson</p>
                                                            <span>St. Xeviers</span>
                                                        </td>
                                                        <td>
                                                            <div class="chat_img_holder"><img src="../images/user2.jpg"></div>
                                                            <p>Matt Lerner</p>
                                                            <span>LMSH B.</span>
                                                        </td>
                                                        <td>
                                                            <div class="chat_img_holder"><img src="../images/user3.jpg"></div>
                                                            <p>Adam Stranger</p>
                                                            <span>SD Public C</span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="chat_img_holder"><img src="../images/user4.jpg"></div>
                                                            <p>Matt Lerner</p>
                                                            <span>LMSH B.</span>
                                                        </td>
                                                        <td>
                                                            <div class="chat_img_holder"><img src="../images/user5.jpg"></div>
                                                            <p>Adam Stranger</p>
                                                            <span>SD Public C</span>
                                                        </td>
                                                        <td></td>
                                                    </tr>
                                                </tbody>
                                            </table> 
                                            <div class="group_score">
                                                <hr>
                                                <div>
                                                    <h2 class="group_total_points">5000</h2>
                                                    <p>Score</p>
                                                </div>
                                                <div>
                                                    <h2 class="group_rank">04</h2>
                                                    <p>Exams</p>
                                                </div>
                                                <div class="clearfix"></div>
                                            </div>                                        
                                        </td>
                                    </tr>
                                   <?php     }
                                    }
                                     ?>

                                
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                    	<div class="box general_cred">
                        	<div class="box_header">
                            	<h3>Recommended Topics</h3>
                            </div>
                             <div class="box_body topic_groups">
                            	<table class="table table-striped table_group">
                                    <?php if(!empty($recommended_topics)){
                                        foreach ($recommended_topics as $topic) { ?>
                                            <tr>
                                        <td class="recommended">
                                            <h4><?php echo $topic['topic_name']; ?><span>Subject : <?php echo $topic['subject_name']; ?></span></h4>
                                            <ul>
                                                <li><p><?php echo $topic['topic_description']; ?></p></li>
                                            </ul>
                                            <div class="recom_action">
                                                <a href="#" class="icon icon_delete_color"></a>
                                                <form action="admin/topic/allocate">
                                                    <input type="hidden" name="group_id">
                                                    <input type="hidden" name="topic_id" value="<?php echo $topic['id']; ?>">
                                                <button class="btn btn_blue">Allocate</button>
                                            </form>
                                            </div>
                                        </td>
                                    </tr>
                                     <?php   }
                                    }
                                     ?>
                                	
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div> 
                <!--//row-->              
			</div>
            <!--//main-->