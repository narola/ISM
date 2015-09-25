<?php $this->load->view('admin/include/header'); ?>
	
  <h3>Add Student </h3>			

  <hr/>	

  <form role="form" method="POST">
      
      <h5>Course Name</h5>
      <input type="text" name="course_name" value="<?php echo set_value("course_name"); ?>" class="form-control" />

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('course_name'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('course_name'),'') ; ?>
      </div>

      <h5>Course Details </h5>
      <textarea name="course_details"></textarea>
      
      <br/><br/>

    <button type="submit" class="btn btn-default">Submit</button>
  
  </form>

<?php $this->load->view('admin/include/footer'); ?>
