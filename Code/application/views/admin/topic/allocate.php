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
                            <a href="admin/topic/add" class="btn btn_green add_topic">Add New Topic</a>
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
                                        <td class="username <?php echo ($unallocated_group == $group['id']) ? 'active' : ''; ?>">
                                            <div class="chat_img_holder"><img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="../images/group1.jpg"></div>
                                            <a href="<?php echo 'admin/topic/allocate/'.$group['id'] ?>"><h4><span>Group Name : </span> <?php echo $group['group_name']; ?> <span> [<?php echo $group['course_name']; ?>]</span></h4></a>
                                            <table class="group_members">
                                                <tbody>
                                                   <?php
                                            
                                                    if(!empty($all_groups_members)) {
                                                        $cnt = 0;
                                                        foreach($all_groups_members as $member) {
                                                            if( $member['gid'] == $group['id']){
                                                                if($cnt == 0){ echo '<tr>'; }
                                                                if($cnt == 3){ echo '<tr>'; }
                                                     ?>

                                                        <td>
                                                            <div class="chat_img_holder">
                                                                <img src="<?php echo 'uploads/'.$member['profile_link']; ?>" 
                                                                onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                                            </div>
                                                            <p><?php echo character_limiter(ucfirst($member['username']),5); ?> </p>
                                                            <span><?php echo character_limiter(ucfirst($member['school_name']),5); ?></span>
                                                        </td>

                                                        <?php 
                                                            if($cnt == 2){ echo '</tr>'; } 
                                                            if($cnt == 4){ echo '<td></td></tr>'; } 
                                                            $cnt++;
                                                        } 
                                                    } // End of foreach Loop
                                                    
                                                }  // End of If condition ?>
                                                    <?php if($cnt == 0) { ?>
                                                    <tr>
                                                        <td> No Members Found. </td>
                                                    </tr>    
                                                    <?php } ?>
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
                                                <form method="post" action="admin/topic/allocate">
                                                        <input type="hidden" name="group_id" value="<?php echo $unallocated_group; ?>">
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