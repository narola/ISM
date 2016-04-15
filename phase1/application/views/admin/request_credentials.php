<!--main-->
    <div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
      <!--breadcrumb-->
      <form method="get" id="filter">
        <div class="page_header">
          <div class="col-sm-12">
              <ol class="breadcrumb col-sm-6">
                  <li><a style="text-decoration: none;">Request Credentials</a></li>
                </ol>
        
                
            </div>
        </div>
        <!--//breadcrumb-->
        <!--filter-->
        
	        <!-- <div class="filter">
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
                      <select class="form-control" name="role" id="role" onchange="filter_data()">
                                      <option value="">Select Role</option>
                                      <?php 
                                        if(!empty($roles)){ 
                                          foreach($roles as $role) {
                                          ?>
                                          <option value="<?php echo $role['id']; ?>"><?php echo ucfirst($role['role_name']); ?></option>  
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
                              <select class="form-control" name="order" id="order" onchange="filter_data()">
                                  <option value="">Sort By</option>
                                  <option value="name_asc">Name Ascending</option>
                                  <option value="name_desc">Name Descending</option>
                                  <option value="latest">Latest First</option>
                                  <option value="older">Older First</option>
                              </select>
                          </div>
                              
             </div>
          </div> -->
            
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
    
       
        
        <!--//button-div-->
        <!--row table-->
        <div class="tabel_view">
          <div class="col-sm-12">
              <div class="table-responsive">
                  <table class="table table-striped table-bordered table_cred">
                      <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>School</th>
                                <th>Course</th>
                                <th>Classroom</th>
                                <th>Year</th>
                                <th>Status</th>
                                <th>Requested Date</th>
                            </tr>
                        </thead>
                        <tbody>

                          <?php 
                            if(!empty($requests)){
                              foreach ($requests as $request) { ?>
                              <tr>
                                <td><?php echo $request['name']; ?></td>
                                <td><?php echo $request['email']; ?></td>
                                <td><?php echo $request['school_name']; ?></td>
                                <td><?php echo $request['course_name']; ?></td>
                                <td><?php echo $request['class_name']; ?></td>
                                <td><?php echo $request['academic_year']; ?></td>
                                <td class="request_<?php echo $request["id"]; ?>"><?php 
                                if ($request['is_created']==0){ ?>
                                    <button data-grade="<?php echo $request["school_grade"]; ?>" 
                                            data-sname="<?php echo $request["school_id"]; ?>" 
                                            data-course="<?php echo $request["course_id"]; ?>" 
                                            data-class="<?php echo $request["classroom_id"]; ?>" 
                                            data-year="<?php echo $request["academic_year"]; ?>" 
                                            data-id="<?php echo $request["id"]; ?>" 
                                            data-toggle="modal" 
                                            data-target="#myModal" 
                                            class="btn generate_btn btn-xs btn-success">Generate Credentials
                                    </button>
                                 <?php } else{ 
                                    echo 'Generated';  
                                    } ?>
                                </td>
                                <td><?php $created_time = $request['created_time'];
                                       echo date("M d,  Y", strtotime($created_time)); ?></td>
                                       </tr>
                            <?php }

                            }
                          ?>
                        </tbody>
                    </table>
                </div>
                
            </div>
        </div>

       
        <!--//row table-->
    </div>
    <!--//main-->
    <!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content request_cred general_cred">
      <div class="modal-header box_header text-center">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Generate Credentials</h4>
        
      </div>
      <div class="modal-body">
        <form method="post">

          <div class="box_body">  
              
              <div class="form-group col-md-offset-2 three_inputs select" >
                  <label>School Grade</label>
                  <select class="form-control " name="school_grade" 
                    onchange="fetch_school_from_grade(this.value)" id="school_grade">
                    <option selected value=""> Select Grade</option>
                    <option value="A" <?php echo set_select('school_grade', 'A'); ?>>A</option>
                    <option value="B" <?php echo set_select('school_grade', 'B'); ?>>B</option>
                    <option value="C" <?php echo set_select('school_grade', 'C'); ?>>C</option>
                    <option value="D" <?php echo set_select('school_grade', 'D'); ?>>D</option>
                    <option value="E" <?php echo set_select('school_grade', 'E'); ?>>E</option>
                  </select>
                  
              </div>

              <div class="form-group three_inputs select">
                  <label>Select School </label>
                  <select class="form-control js-example-basic-single" id="school_id" name="school_id" onchange="school_id_error()">
                     <option  selected value=""> Select School</option>
                      <?php 
                          if(!empty($schools)) {
                            foreach($schools as $school) { 
                          ?>
                          <option value="<?php echo $school['id']; ?>" <?php echo set_select('school_id', $school['id']); ?> >
                                <?php echo $school['school_name']; ?>
                          </option>
                      <?php } }else{ ?>
                          <option disabled > No Schools Found</option>  
                      <?php } ?> 
                  </select>
                  <?php echo form_error('school_id','<div class="alert alert-danger school_id_error">','</div>'); ?>
              </div>

              
              <div class="clearfix"></div>
           </div>
           
           <div class="box_body">
                <div class="form-group three_inputs select">
                      <label>Course </label>
                      <select class="form-control " name="course_id" id="course_id"
                        onchange="fetch_classroom(this.value)" >
                          <option selected disabled> Select Course</option>
                          <?php 
                              if(!empty($courses)) {
                                foreach($courses as $course) { 
                              ?>
                              <option value="<?php echo $course['id']; ?>" <?php echo set_select('course_id', $course['id']); ?>>
                                   <?php echo $course['course_name']; ?>
                              </option>
                          <?php } }else{ ?>
                              <option disabled > No Course Found</option>  
                          <?php } ?>      
                      </select>
                      <?php echo form_error('course_id','<div class="alert alert-danger course_id_error">','</div>'); ?>
                  </div>

                  <div class="form-group three_inputs select">
                      <label>Classroom</label>
                      <select class="form-control" name="classroom_id" id="classroom_id" onchange="classroom_id_error()">
                          <option selected disabled> Select Classroom</option>
                          <?php 
                              if(!empty($classrooms)) {
                                foreach($classrooms as $classroom) { 
                              ?>
                              <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('classroom_id', $classroom['id']); ?>>
                                   <?php echo $classroom['class_name']; ?>
                              </option>
                          <?php } }else{ ?>
                              <option disabled > No Classroom Found</option>  
                          <?php } ?>
                      </select>
                      <?php echo form_error('classroom_id','<div class="alert alert-danger classroom_id_error">','</div>'); ?>
                  </div>

                  <div class="form-group three_inputs select">
                      <label>Year</label>
                      <select class="form-control" name="year_id" id="year_id">
                          <option value="<?php echo $cur_year; ?>"><?php echo $cur_year; ?></option>
                          <option value="<?php echo $next_year; ?>"><?php echo $next_year; ?></option>
                      </select>
                  </div>
                  <div class="clearfix"></div>
           </div>

          <div class="box_header">
            <div class="confirmation">
                <p id="dialog_box" class="hide">You have requested <span class="txt_red" id="noc_new">50</span> credentials for <span id="role_id_new" class="txt_blue">Students</span> of <span class="txt_blue" id="school_id_new">St. Xevier's School</span> belong to first academic year in <span class="txt_green" id="course_id_new">Computer Science Course</span></p>

                <?php echo flashMessage(TRUE); ?>

                <?php $success = $this->session->flashdata('success'); ?>
                
                <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
                    <?php echo strip_tags($success) ; ?>
                </div>
                <input type="hidden" name="request_id" id="request_id"/>
                <button class="btn btn_red" id="generate" name="generate" type="submit">Confirm & Generate</button>
            </div>
          </div>
        
        </form>
        <div class="clearfix"></div>
      </div>
      
    </div>
  </div>
</div>
    <!-- /.modal -->
<script type="text/javascript">
    
    $(document).ready(function(){
        $(".generate_btn").click(function(){
            var grade = $(this).data('grade');
            var sname = $(this).data('sname');
            var course = $(this).data('course');
            var class_id = $(this).data('class');
            var year = $(this).data('year');
            var request_id = $(this).data('id');

            $("#school_grade").val(grade);
            $("#school_id").val(sname);
            $("#course_id").val(course);
            $("#classroom_id").val(class_id);
            $("#year_id").val(year);
            $("#request_id").val(request_id);
        });
        $("#generate").click(function(){
            var req = $("#request_id").val();
            $("table.table_cred td.request_"+req).html('Generated');
            $("#myModal").modal('hide');
        })
    });


  

    

    function filter_data_reverse(){
        $('#q').removeAttr('name');
        $('#filter').submit();        
    }

    function filter_data(){
    	
    	var role = $('#role').val();
    	var school = $('#school').val();
    	var course = $('#course').val();
    	var classroom = $('#classroom').val();
        var q = $('#q').val();
        var order = $('#order').val();

        if(role == '' ){ $('#role').removeAttr('name'); }
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
 