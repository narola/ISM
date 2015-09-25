<?php  $this->load->view('admin/include/header');  ?>
	

  <?php $error = $this->session->flashdata('error'); ?>
  
  <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($error) ; ?>
  </div>

  <?php $success = $this->session->flashdata('success'); ?>
  
  <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($success) ; ?>
  </div> 

  <a href="<?php echo base_url().'admin/add_school'; ?>" class="btn btn-info">  Add School</a>
  <h3> All Schools </h3>			

  
  <hr/>	
  
  <table class="table table-hover">
    <thead>
      <tr>
        <th>School Name</th>
        <th>School Grade</th>
        <th>Principal Name</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <?php
        if(!empty($all_schools)){
           foreach($all_schools as $school) { 
       ?>
      <tr>
        <td><?php echo $school['school_name']; ?></td>
        <td><?php echo $school['school_grade']; ?></td>
        <td><?php echo $school['principal_name']; ?></td>
        <td>
            <a href="<?php echo base_url().'admin/update_school/'.$school['id']; ?>" class="btn btn-success"> Edit </a>
            <a href="<?php echo base_url().'admin/delete_school/'.$school['id']; ?>" class="btn btn-danger" 
              onclick="return confirm('Are you sure to delete this data ?')" > Delete </a>
        </td>
      </tr>

      <?php } } ?>

    </tbody>
  </table>


<?php $this->load->view('admin/include/footer'); ?>
