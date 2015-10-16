<!--main-->
    <div class="col-sm-7 main main2">
      <!--breadcrumb-->
      <div class="row page_header">
          <div class="col-sm-12">
              <ol class="breadcrumb">
                  <li><a href="admin/group">Manage</a></li>
                  <li class="active">Group</li>
                </ol>
            </div>
        </div>
        <!--//breadcrumb-->
        <!--filter-->
        <form method="get" id="filter">
	        <div class="row filter">
	          <div class="col-sm-12">
	              
	                <div class="form-group">
	                   <select class="form-control" name="course" onchange="filter_data()" id="course" >
                            <option value="">Select Course</option>
                            <?php 
                              if(!empty($courses)){ 
                                foreach($courses as $course) {
                                ?>
                                <option value="<?php echo $course['id']; ?>"><?php echo $course['course_name']; ?></option>  
                            <?php }  } ?>
                        </select>
	                </div>
	                <div class="form-group">
	                    <select class="form-control" onchange="filter_data()" name="year" id="year">
	                        <option value="">Select Year</option>
	                        <option value="2015">2015</option>
	                        <option value="2016">2016</option>
	                    </select>
	                </div>
	                
                    <div class="form-group no_effect search_input">
                        <input type="text" name="q" id="q" class="form-control" placeholder="Type Group/Member Name." >
                        <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
                    </div>

	            </div>
	        </div>
            
    	</form>	

    	<?php $success = $this->session->flashdata('success'); ?>
  
		<div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
		    <?php echo strip_tags($success) ; ?>
		</div>
		
		<?php $error = $this->session->flashdata('error'); ?>
  
		<div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
		    <?php echo strip_tags($error) ; ?>
		</div>

        <!--//filter-->
        <!--button div-->
    
        <form method="post" action="<?php echo base_url().'admin/group/send_messages'; ?>"  >  <!-- Form Start -->
            
            <div class="row div_buttons">
                 <div class="col-sm-6" style="z-index:9999">
                    <input type="submit" class="btn btn_black" value="Send Messages">    
                </div> 
            </div>

            <!-- <input type="text" name="test">  -->
            
            
        <!--//button-div-->
        <!--row table-->
        <div class="row tabel_view">
          <div class="col-sm-12">
              <div class="table-responsive">
                  <table class="table table-striped table-bordered table_user">
                      <thead>
                            <tr>
                                <th></th>
                                <th style="width: 550px;">Group Detail</th>
                                <th>Rank</th>
                                <th>Score</th>
                                <th style="width:180px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                          <?php 
                            if(!empty($all_groups)) {
                            
                              foreach($all_groups as $group) {
                            ?>
                                <tr>
                                    <td class="checkbox_td">
                                        <div class="squaredThree">
                                            <input type="checkbox" value="<?php echo $group['id']; ?>" 
                                            id="squaredThree_<?php echo $group['id']; ?>" name="group_messages[]" >
                                            <label for="squaredThree_<?php echo $group['id']; ?>"></label>
                                        </div>
                                    </td>
                                    <td class="username">
                                        <div class="chat_img_holder">
                                            <!-- <img src="../images/group1.jpg"> -->
                                            <img src="<?php echo 'uploads/user_141/user6_14436733csdfs32.jpg'; ?>" 
                                            onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" >
                                        </div>
                                        <h4><span>Group Name : </span> <?php echo $group['group_name'] ?> <span> [<?php echo $group['course_name'] ?>]</span></h4>
                                        <table class="group_members">
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
                                        </table>                                            
                                    </td>
                                    <td class="group_rank">01</td>
                                    <td class="group_points"><p><?php if(!empty($group['group_score'])) {echo $group['group_score']; }else{ echo 0; } ?></p><p>4 Assignments</p><p>10 Exams</p></td>
                                    <td>
                                        <a href="#" data-toggle="tooltip" data-placement="bottom" title="Timeline" class="icon icon_timeline"></a>
                                        
                                        <a href="#" data-toggle="tooltip" data-placement="bottom" title="Books" class="icon icon_books"></a>
                                        
                                        <a href="<?php echo base_url().'admin/group/performance/'.$group['id']; ?>" data-toggle="tooltip" data-placement="bottom" title="Performance" class="icon icon_performance"></a>
                                        
                                        <a href="#" data-toggle="tooltip" data-placement="bottom" title="Block Group"  class="icon icon_blockuser"></a>
                                        
                                        <a href="#" data-toggle="tooltip" data-placement="bottom" title="Main"  class="icon icon_mail"></a>
                                        
                                        <a href="<?php echo base_url().'admin/group/send_message/'.$group['id']; ?>" title="Message"
                                        data-toggle="tooltip" data-placement="bottom"  class="icon icon_chat"></a>
                                    </td>
                                </tr>    
                            <?php } }else{ ?>
							
							<tr> <td colspan="7" class="text-center"><strong>No Group Found. </strong> </td> </tr>		
							
                            <?php } ?>
                        </tbody>
                    </table>
                </div>
                <nav  class="text-center">
       
			    <?php echo $this->pagination->create_links(); ?>

                </nav>
            </div>
        </div>

       </form> <!-- Form END  -->
        <!--//row table-->
    </div>
    <!--//main-->

<script type="text/javascript">
	
	function filter_data(){
		
		var course = $('#course').val();
        var q = $('#q').val();
        var year = $('#year').val();
		
		if(course == '' ){ $('#course').removeAttr('name'); }
		if(q == ''){ $('#q').removeAttr('name'); }
        if(year == '' ){ $('#year').removeAttr('name'); }

		$('#filter').submit();
	}

    $( "#filter" ).submit(function( event ) {
      
        var course = $('#course').val();
        var q = $('#q').val();
        var year = $('#year').val();
        
        if(course == '' ){ $('#course').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name'); }
        if(year == '' ){ $('#year').removeAttr('name'); }
        
    });
    


	<?php if(!empty($_GET['course'])) { ?>
		$('#course').val('<?php echo $_GET["course"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['q'])) { ?>
		$('#q').val('<?php echo $_GET["q"];?>');	
	<?php } ?>			

    <?php if(!empty($_GET['year'])) { ?>
        $('#year').val('<?php echo $_GET["year"];?>');    
    <?php } ?>          

</script>
 