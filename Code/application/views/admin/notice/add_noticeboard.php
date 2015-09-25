<?php $this->load->view('admin/include/header'); ?>
	
  <h3>Add Student </h3>			

  <hr/>	

  <form role="form" method="POST">
      
      <h5>Notice Title</h5>
      <input type="text" name="notice_title" value="<?php echo set_value("notice_title"); ?>" class="form-control" />

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice_title'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('notice_title'),'') ; ?>
      </div>

      <h5>Notice Description</h5>
      <textarea name="notice"><?php echo set_value('notice'); ?> </textarea>
      <br/><br/>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('notice'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('notice'),'') ; ?>
      </div>

      <?php 
        if(!empty($users)) {
        foreach($users as $user) {  
      ?>

      <input type="checkbox" name="users[]" value="<?php echo $user['id']; ?>" id="<?php echo $user['id']; ?>">
      <label for="<?php echo $user['id']; ?>"> <?php echo $user['username']."<br/>"; ?> </label> <br/>

      <?php } } ?>

      <br/>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('users'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('users'),'') ; ?>
      </div>

    <button type="submit" class="btn btn-default">Submit</button>
  
  </form>

<?php $this->load->view('admin/include/footer'); ?>
