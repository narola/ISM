<!--main-->
    <div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
      <!--breadcrumb-->
      <form method="get" id="filter">
        <div class="page_header">
          <div class="col-sm-12">
              <ol class="breadcrumb col-sm-6">
                  <li><a href="#">Manage</a></li>
                  <li><a href="#">users</a></li>
                  <li class="active">Year upgradation <?php //if($_GET){ if(!empty($_GET['q'])){ echo 'IF'; }else{ echo 'ELSE'; } } ?></li>
                </ol>
               
            </div>
        </div>
        <!--//breadcrumb-->
        <!--filter-->
        
	        <div class="filter">
	          <div class="col-sm-12">
	              <div class="form-group">
	                    <select class="form-control" name="school" onchange="filter_data()" id="school">
	                          <option value="">Select School</option>
	                          <?php 
	                            if(!empty($schools)){ 
	                              foreach($schools as $school) {
	                              ?>
	                              <option value="<?php echo $school['id']; ?>" ><?php echo $school['school_name']; ?></option>  
	                          <?php }  } ?>
	                      </select>
	                </div>
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
	                    <select class="form-control" name="classroom" id="classroom" onchange="filter_data()">
                            <option value="">Select Classroom</option>
                            <?php 
                              if(!empty($classrooms)){ 
                                foreach($classrooms as $classroom) {
                                ?>
                                <option value="<?php echo $classroom['id']; ?>"><?php echo $classroom['class_name']; ?></option>  
                            <?php }  } ?>
                        </select>
	                </div>

                    <div class="form-group">
                      <select class="form-control" name="acadamic_year" id="acadamic_year" onchange="filter_data()">
                            <option value="">Select Year</option>
                            <?php 
                              if(!empty($academic_years)){ 
                                foreach($academic_years as $academic_year) {
                                ?>
                                <option value="<?php echo $academic_year['academic_year']; ?>"><?php echo $academic_year['academic_year']; ?></option>  
                            <?php }  } ?>
                        </select>
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
    
      <form method="post" action="<?php echo base_url().'admin/user/send_messages'; ?>">  <!-- Form Start -->
        <div class="div_buttons">
          <div class="col-sm-6 text-left">
              <input squaredThree type="checkbox" id="checkAll"> Check all
          </div>
         <!--  <div class="col-sm-6 text-right">
              <a href="javascript:void(0);" class="btn btn_green" data-toggle="modal" data-target="#year_updradation">Upgrade to next year</a>
          </div> -->
        </div>
        <!--//button-div-->
        <!--row table-->
        <div class="tabel_view">
          <div class="col-sm-12">
              <div class="table-responsive">
                  <table class="table table-striped table-bordered table_user">
                      <thead>
                            <tr>
                                <th></th>
                                <th style="width: 240px;">Username</th>
                                <th>Class</th>
                                <th>Course</th>
                                <th>City</th>
                                <th>Role</th>
                                <th>Acadamic year</th>
                            </tr>
                        </thead>
                        <tbody>
                          <?php 
                            if(!empty($all_users)) {

                              foreach($all_users as $user) {
                            ?>
                          <tr>
                              <td class="checkbox_td">
                                <div class="squaredThree">
                                    <input type="checkbox" data-name="<?php echo $user['username']; ?>" data-classid="<?php echo $user['class_id']; ?>" data-class="<?php echo $user['class_name']; ?>" data-course="<?php echo $user['course_name']; ?>" data-year="<?php echo $user['academic_year']; ?>" value="<?php echo $user['id']; ?>" id="squaredThree_<?php echo $user['id']; ?>" name="users[]"> 
                                    <label for="squaredThree_<?php echo $user['id']; ?>"></label>
                                </div>
                              </td>
                              
                              <td class="username">
                                  <div class="chat_img_holder">
                                     <?php if($user['role_id']!= '1') { ?>
                                        <img src="<?php echo base_url().'uploads/'.$user['profile_link']; ?>"
                                       onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                     <?php }else{ ?>   
                                        <img src="<?php echo base_url().'uploads/'.$user['profile_pic']; ?>"
                                       onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                                     <?php } ?>
                                       
                                  </div>
                                  <h4><?php echo ucfirst($user['username']); ?></h4>
                                  <?php if($user['user_status']=='active'){ 
                                        echo '<p class="active">Active</p>'; 
                                    }elseif($user['user_status']=='blocked'){ 
                                        echo '<p style="color:red">Blocked</p>';
                                    } ?>
                              </td>

                              <td><?php echo ucfirst($user['class_name']); ?></td>
                              <td> <?php echo ucfirst($user['course_name']); ?> </td>
                              <td> <?php echo ucfirst($user['city_name']); ?> </td>
                              <td><?php echo ucfirst($user['role_name']); ?></td>
                              <td><?php echo ucfirst($user['academic_year']); ?></td>
                            </tr>
                            <?php } }else{ ?>
							
							<tr> <td colspan="7" class="text-center"><strong>No Data Found. </strong> </td> </tr>		
							
                            <?php } ?>
                        </tbody>
                    </table>
                </div>

          <div class="div_buttons">
           <!--  <div class="col-sm-6 text-left">
                <input type="checkbox" id="checkAll"> Check all
            </div> -->
            <div class="col-sm-12 text-right">
                <a href="javascript:void(0);" id="upgrade_next" class="btn btn_green" data-toggle="modal" data-target="#year_updradation">Upgrade to next year</a>
                <a href="javascript:void(0);" id="failed" class="btn btn_red" data-toggle="modal" data-target="#fail_student">Fail student</a>
            </div>
          </div>

            </div>
        </div>

       </form> <!-- Form END  -->
        <!--//row table-->
    </div>
    <!--//main-->

    <!-- model for upgrade -->
    <div class="modal fade" id="year_updradation" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document" style="width:600px;margin-top:220px;">
            <div class="modal-content">
                <div class="modal-header notice_header text-center">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Next year upgradation</h4>
                    <small><?php echo date("d F Y",strtotime(date('Y-m-d')));?></small>
                </div>
                <div class="modal-body">
                   

                      <form action="" method="post">
                        <div class="tabel_view">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table_user">
                                <thead>
                                      <tr>
                                          <th style="width: 240px;">Username</th>
                                          <th>Class</th>
                                          <th>Course</th>
                                          <th>Acadamic year</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                              </table>
                          </div>
                    </div>
                        <button type="submit" class="btn btn_red" data-type="close-studymate" style="float:right;">Upgrade</button></h4>
                        <div class="clearfix"></div>
                    </form>


                </div>
            </div>
        </div>
    </div>


    <!-- model for fail -->
    
     <div class="modal fade" id="fail_student" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document" style="width:600px;margin-top:220px;">
            <div class="modal-content">
                <div class="modal-header notice_header text-center">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Failed students</h4>
                    <small><?php echo date("d F Y",strtotime(date('Y-m-d')));?></small>
                </div>
                <div class="modal-body">
                   

                      <form action="" method="post">
                        <div class="tabel_view">
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table_user">
                                <thead>
                                      <tr>
                                          <th style="width: 240px;">Username</th>
                                          <th>Class</th>
                                          <th>Course</th>
                                          <th>Acadamic year</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  </tbody>
                              </table>
                          </div>
                    </div>
                        <button type="submit" class="btn btn_red" data-type="close-studymate" style="float:right;">Fail</button></h4>
                        <input type="hidden" name="fail" value="yes"/>
                        <div class="clearfix"></div>
                    </form>


                </div>
            </div>
        </div>
    </div>


<script type="text/javascript">
    
    // $(document).ready(function(){
    //   $('select').select2();
    // });
      $("#checkAll").change(function () {
       // alert($(this).is(':checked'));
       var is_check = null;
       if($(this).attr('checked')) {
            is_check = 1;
            $(this).removeAttr("checked");
        } else {
            is_check = 0;
            $(this).attr("checked", "checked");
        }

        if(is_check == 1){
          $('input[name="users[]"]').each(function(i){
             // $('input[name="users[]"]').removeAttr("checked");
              $('input[name="users[]"]').prop('checked', false);
              $('.modal-content .modal-content').html("<h3 style='color:red'>Select atleast one user to upgrade next year !</h3>");
             // $('.modal-content .modal-body').html("<h3 style='color:red'>Select user to upgrade next year !</h3>");
            });

        }else
        {
           $('input[name="users[]"]').each(function(i){
             // $('input[name="users[]"]').attr("checked", "checked");
             $('input[name="users[]"]').prop('checked', true);
            });
        }
           // $("input:checkbox").prop('checked', $(this).prop("checked"));
    });

    /* Model for the selected student and error messag if not selected any student */
    $("#upgrade_next").click(function(){
      var str;

      var len = $('input[name="users[]"]:checked').length;
      
      if(len != 0){
        
        $('input[name="users[]"]:checked').each(function(i){
            str += '<tr>';
            str += '<input type="hidden" name="user_ids[]" value='+ parseInt($(this).val()) +' >';
            str += '<input type="hidden" name="ac_years[]" value="'+ $(this).attr("data-year") +'" >';
            str += '<td>'+ $(this).attr("data-name") +'</td>';
            str += '<td>'+ $(this).attr("data-class") +'</td>';
            str += '<td>'+ $(this).attr("data-course") +'</td>';
            str += '<td>'+ $(this).attr("data-year") +'</td>';
            str += '</tr>';
        });
       $('.modal-content .table_user tbody').html(str);
       $('button[data-type="close-studymate"]').show();
      }else
      {
       $('.modal-content .table_user tbody').html("<tr><td colspan='4'><h3 style='color:red'>Select atleast one user to upgrade next year !</h3></td></tr>");
       $('button[data-type="close-studymate"]').hide();
      }
    });

 /* Model for the selected student and error messag if not selected any student for failed student */
    $("#failed").click(function(){
      var str;

      var len = $('input[name="users[]"]:checked').length;
      
      if(len != 0){
        
        $('input[name="users[]"]:checked').each(function(i){
            str += '<tr>';
            str += '<input type="hidden" name="user_ids[]" value='+ parseInt($(this).val()) +' >';
            str += '<input type="hidden" name="ac_years[]" value="'+ $(this).attr("data-year") +'" >';
            str += '<input type="hidden" name="class_ids[]" value="'+ $(this).attr("data-classid") +'" >';
            str += '<td>'+ $(this).attr("data-name") +'</td>';
            str += '<td>'+ $(this).attr("data-class") +'</td>';
            str += '<td>'+ $(this).attr("data-course") +'</td>';
            str += '<td>'+ $(this).attr("data-year") +'</td>';
            str += '</tr>';
        });
       $('.modal-content .table_user tbody').html(str);
       $('button[data-type="close-studymate"]').show();
      }else
      {
       $('.modal-content .table_user tbody').html("<tr><td colspan='4'><h3 style='color:red'>Select atleast one user to procced!</h3></td></tr>");
       $('button[data-type="close-studymate"]').hide();
      }
    });



    function block_user(href,event){
         event.preventDefault();
         bootbox.confirm("Block User?", function(confirmed) {
            
            if(confirmed){
                window.location.href=href;
            }
            
        });
    } 

    function active_user(href,event){
         event.preventDefault();
         bootbox.confirm("Activate User?", function(confirmed) {
            
            if(confirmed){
                window.location.href=href;
            }

        });
    } 

    function filter_data_reverse(){
        $('#q').removeAttr('name');
        $('#filter').submit();        
    }

    function filter_data(){
    	
    	var role = $('#role').val();
    	var school = $('#school').val();
    	var course = $('#course').val();
    	var classroom = $('#classroom').val();
      var acadamic_year = $('#acadamic_year').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(role == '' ){ $('#role').removeAttr('name'); }
        if(acadamic_year == '' ){ $('#acadamic_year').removeAttr('name'); }
    	if(school == '' ){ $('#school').removeAttr('name'); }
    	if(course == '' ){ $('#course').removeAttr('name'); }
    	if(classroom == ''){ $('#classroom').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name');}
        if(order == ''){  $('#order').removeAttr('name'); }

    	$('#filter').submit();
    }
    
    $( "#filter" ).submit(function( event ) {
      
        var role = $('#role').val();
        var school = $('#school').val();
        var year = $('#year').val();
        var course = $('#course').val();
        var classroom = $('#classroom').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(role == '' ){ $('#role').removeAttr('name'); }
        if(school == '' ){ $('#school').removeAttr('name'); }
        if(year == '' ){ $('#year').removeAttr('name'); }
        if(course == '' ){ $('#course').removeAttr('name'); }
        if(classroom == ''){ $('#classroom').removeAttr('name'); }
        if(q == ''){ $('#q').removeAttr('name');}
        if(order == ''){  $('#order').removeAttr('name'); }

    });
    

	<?php if(!empty($_GET['role'])) { ?>
		$('#role').val('<?php echo $_GET["role"];?>');	
	<?php } ?>
  <?php if(!empty($_GET['acadamic_year'])) { ?>
    $('#acadamic_year').val('<?php echo $_GET["acadamic_year"];?>');  
  <?php } ?>

	<?php if(!empty($_GET['school'])) { ?>
		$('#school').val('<?php echo $_GET["school"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['course'])) { ?>
		$('#course').val('<?php echo $_GET["course"];?>');	
	<?php } ?>

	<?php if(!empty($_GET['classroom'])) { ?>
		$('#classroom').val('<?php echo $_GET["classroom"];?>');	
	<?php } ?>

    <?php if(!empty($_GET['q'])) { ?>
        $('#q').val('<?php echo $_GET["q"];?>');    
    <?php } ?>	

    <?php if(!empty($_GET['order'])) { ?>
        $('#order').val('<?php echo $_GET["order"];?>');    
    <?php } ?> 		

</script>
 