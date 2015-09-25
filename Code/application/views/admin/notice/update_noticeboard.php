<?php $this->load->view('admin/include/header'); ?>
	
  <h3>Add Student </h3>			

  <hr/>	

  <form role="form" method="POST">
      
      <h5>Notice Title</h5>
      <input type="text" name="notice_title" value="<?php echo $notice['notice_title']; ?>" class="form-control" />

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice_title'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('notice_title'),'') ; ?>
      </div>

      <h5>Notice Description</h5>
      <textarea name="notice"><?php echo $notice['notice']; ?></textarea>
      <br/><br/>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('notice'),'') ; ?>
      </div>

      <?php 
        if(!empty($users)) {
        foreach($users as $user) {    
      ?>
  
      <input type="checkbox" name="users[]" value="<?php echo $user['id']; ?>" 
      <?php  if(in_array($user['id'],$viewers_array)){ echo "checked"; } ?> id="<?php echo $user['id']; ?>">
      <label for="<?php echo $user['id']; ?>"> <?php echo $user['username']."<br/>"; ?> </label> <br/>

      <?php } } ?>

      <br/>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('users'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('users'),'') ; ?>
      </div>
      
      <h5>Notice Title</h5>
      <select name="status">
        <option value="1" <?php if($notice['status']==TRUE){ echo "selected"; } ?> >Active</option>
        <option value="0" <?php if($notice['status']==FALSE){ echo "selected"; } ?> >De-active</option>
      </select>

      <br/><br/>
      
    <button type="submit" class="btn btn-default">Submit</button>
  
  </form>

<?php $this->load->view('admin/include/footer'); ?>
