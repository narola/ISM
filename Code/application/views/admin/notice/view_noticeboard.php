<?php  $this->load->view('admin/include/header');  ?>
	

  <?php $error = $this->session->flashdata('error'); ?>
  
  <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($error) ; ?>
  </div>

  <?php $success = $this->session->flashdata('success'); ?>
  
  <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
          <?php echo strip_tags($success) ; ?>
  </div> 

  <a href="<?php echo base_url().'admin/add_notice'; ?>" class="btn btn-info">  Add Notice</a>
  <h3> All Notices </h3>			

  
  <hr/>	
  
  <table class="table table-hover">
    <thead>
      <tr>
        <th>Notice Title</th>
        <th>Notice Details</th>
        <th>Status</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <?php
        if(!empty($all_notices)){
           foreach($all_notices as $notice) { 
       ?>
      <tr>
        <td><?php echo $notice['notice_title']; ?></td>
        <td><?php echo $notice['notice']; ?></td>
        <td><?php $status = $notice['status']; if($status == TRUE){ echo "Active"; }else{ echo "De-Active"; } ?></td>
        <td>
            <a href="<?php echo base_url().'admin/update_notice/'.$notice['id']; ?>" class="btn btn-success"> Edit </a>
            <a href="<?php echo base_url().'admin/delete_notice/'.$notice['id']; ?>" class="btn btn-danger" 
              onclick="return confirm('Are you sure to delete this data ?')" > Delete </a>
        </td>
      </tr>

      <?php } } ?>

    </tbody>
  </table>


<?php $this->load->view('admin/include/footer'); ?>
