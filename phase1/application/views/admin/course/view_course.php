<?php  $this->load->view('admin/include/header');  ?>
	

  <?php $error = $this->session->flashdata('error'); ?>
  
  <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($error) ; ?>
  </div>

  <?php $success = $this->session->flashdata('success'); ?>
  
  <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($success) ; ?>
  </div> 

  <a href="<?php echo base_url().'admin/add_course'; ?>" class="btn btn-info">  Add Course</a>
  <h3> All Courses </h3>			

  
  <hr/>	
  
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Course Name</th>
        <th>Course Details</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <?php
        if(!empty($all_courses)){
           foreach($all_courses as $course) { 
       ?>
      <tr>
        <td><?php echo $course['course_name']; ?></td>
        <td><?php echo $course['course_details']; ?></td>
        <td>
            <a href="<?php echo base_url().'admin/update_course/'.$course['id']; ?>" class="btn btn-success"> Edit </a>
            <a href="<?php echo base_url().'admin/delete_course/'.$course['id']; ?>" class="btn btn-danger" 
              onclick="return confirm('Are you sure to delete this data ?')" > Delete </a>
        </td>
      </tr>

      <?php } } ?>

    </tbody>
  </table>


<?php $this->load->view('admin/include/footer'); ?>
