<!--main-->
            <div class="col-sm-7 main main2 main_wide mCustomScrollbar" data-mcs-theme="minimal-dark">
            	<!--breadcrumb-->
           		<div class="page_header">
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
                <div class="filter group_filter">
                	<div class="col-sm-12">
                        <form method="get" id="filter">
                        	<div class="form-group">
                                <select class="form-control" name="course_id" id="course_id" onchange="get_classes(this.value)">
                                    <option>Select Course</option>
                                    <?php 
                                        if(!empty($courses)){
                                            foreach ($courses as $course) { ?>
                                                <option value="<?php echo $course['id']; ?>"><?php echo $course['course_name']; ?></option>
                                            <?php }
                                        }
                                    ?>
                                </select>
                            </div>
                            <div class="form-group">
                                <select class="form-control" name="classroom_id" onchange="filter_data()" id="classroom_id">
                                    <option value=''>Select Classroom</option>
                                    <?php 
                                        if(!empty($classrooms)){
                                            foreach ($classrooms as $classroom) { ?>
                                                <option value="<?php echo $classroom['id']; ?>"><?php echo $classroom['class_name']; ?></option>
                                            <?php }
                                        }
                                    ?>
                                </select>
                            </div>
                            <!-- <div class="form-group">
                                <select class="form-control" id="group_id" onchange="filter_data()" name="group_id">
                                    <option value="">Select Group</option>
                                    <?php /*
                                        if(!empty($all_groups)){
                                            foreach ($all_groups as $group) { ?>
                                                <option value="<?php echo $group['id']; ?>"><?php echo $group['group_name']; ?></option>
                                            <?php }
                                        }*/
                                    ?>
                                </select>
                            </div> -->
                            <!-- <div class="form-group">
                                <select class="form-control">
                                    <option>Sort By</option>
                                </select>
                            </div> -->
                        </form>
                        <div class="form-group no_effect text-right">
                            <a href="admin/topic/add" class="btn btn_green add_topic">Add New Topic</a>
                        </div>
                    </div>
                </div>
                <!--//filter--> 
                <!--row-->
                <div class="allocate_topic">
                	<div class="col-sm-12 col-lg-6">
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
                                            <a href="<?php echo 'admin/topic/allocate/'.$group['id'] ?><?php echo (isset($_GET['per_page'])) ? '?per_page='.$_GET['per_page'] : ''; ?>">
                                                    <h4><span>Group Name : </span> <?php echo $group['group_name']; ?> <span> [<?php echo $group['course_name']; ?>]- <?php echo $group['class_name']; ?> </span></h4>
                                            </a>
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
                                                    <h2 class="group_total_points"><?php echo $group['score']; ?></h2>
                                                    <p>Score</p>
                                                </div>
                                                <div>
                                                    <h2 class="group_rank"><?php if($group['score']!=0){ echo $group['exams']; }else{ echo 0; }  ?></h2>
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
                             <nav  class="text-center">
       
                <?php echo $this->pagination->create_links(); ?>

                </nav>
                        </div>
                    </div>
                    <div class="col-sm-12 col-lg-6">
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
                                                <li><p><?php echo html_entity_decode($topic['topic_description']); ?></p></li>
                                            </ul>
                                            <div class="recom_action">
                                                <a href="#" class="icon icon_delete_color"></a>
                                                <form method="post" action="admin/topic/allocate" class="pull-right">
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
<script type="text/javascript">

    function filter_data(){

        var course_id = $('#course_id').val();
        var classroom_id = $('#classroom_id').val();
        var group_id = $('#group_id').val();
        
        if(course_id == '' ){ $('#course_id').removeAttr('name'); }
        if(classroom_id == '' ){ $('#classroom_id').removeAttr('name'); }
        if(group_id == '' ){ $('#group_id').removeAttr('name'); }
        
        $('#filter').submit();
    }

    <?php if(!empty($_GET['course_id'])) { ?>
        $('#course_id').val('<?php echo $_GET["course_id"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['classroom_id'])) { ?>
        $('#classroom_id').val('<?php echo $_GET["classroom_id"];?>');  
    <?php } ?>

    <?php if(!empty($_GET['group_id'])) { ?>
        $('#group_id').val('<?php echo $_GET["group_id"];?>');  
    <?php } ?>

    function get_classes(course_id){
        $.ajax({
           url:'<?php echo base_url()."admin/topic/ajax_get_classrooms"; ?>',
           type:'POST',
           data:{course_id:course_id},
           success:function(data){
              $("#classroom_id").html(data);
              $('#group_id').html('<option value=""> Select Group </option>');
           }
        });
    }

    function get_groups(classroom_id){
        $.ajax({
           url:'<?php echo base_url()."admin/topic/ajax_get_groups"; ?>',
           type:'POST',
           data:{classroom_id:classroom_id},
           success:function(data){
              $("#group_id").html(data);
              filter_data();
           }
        });
    }



</script>