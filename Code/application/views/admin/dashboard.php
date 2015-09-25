<?php $this->load->view('admin/include/header'); ?>
  
  <?php $success = $this->session->flashdata('success'); ?>
  
  <div class="alert alert-success <?php if(empty(strip_tags($success,''))){ echo 'hide';} ?>">
        <?php echo strip_tags($success) ; ?>
  </div>

   <div class="jumbotron">
    <h1>My first Bootstrap website!</h1>      
    <p>This page will grow as we add more and more components from Bootstrap...</p>      
    <a href="#" class="btn btn-info btn-lg"><span class="glyphicon glyphicon-search"></span> Search</a>
  </div>

  <div class="row">
    <div class="col-md-3">
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
    </div>
    <div class="col-md-6">
      <p><strong>Note: On a large screen the content will be displayed as a three-column layout. (Just enlarge the browser window to see the effect). However, the content will automatically adjust itself into a single-column layout on a small screen. So, with Bootstrap, our page is now responsive to the screen size and adjusts itself accordingly.</strong></p>
    </div>
    <div class="col-md-3">
      <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam. Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p>
    </div>
  </div>


<?php $this->load->view('admin/include/footer'); ?>

