<link rel="stylesheet" type="text/css" media="all" href="assets/css/jquery.lightbox-0.5.css">
<div class="col-sm-7 main main2 add_book_page general_cred mCustomScrollbar" data-mcs-theme="minimal-dark">
	<!--breadcrumb-->
	 <div class="row page_header">
    	<div class="col-sm-12">
        	<ol class="breadcrumb">
              <li><a href="#">Manage</a></li>
              <li class="active">App Config</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <div class="appconfig_tabs">
    <ul class="nav nav-tabs">
      <li class="active"><a data-toggle="tab" href="#login_bgs">Login Backgrounds</a></li>
    </ul>
    <div class="tab-content">
    <div id="login_bgs" class="tab-pane fade in active">
      
   

    <ul class="nav nav-tabs">
      <li class="active"><a data-toggle="tab" href="#author">Author</a></li>
      <li><a data-toggle="tab" href="#student">Student</a></li>
      <li><a data-toggle="tab" href="#teacher">Teacher</a></li>
    </ul>

  <div class="tab-content">
    <div id="author" class="tab-pane fade in active">
      <form id="author_frm" method="post" enctype="multipart/form-data">
      <div id="author_thumbnails" class="thumbnails">
        <ul class="clearfix">
        <?php foreach ($author_images as $image) { ?>
          <li class="col-md-3 text-center">
            <div class="row">
              <a href="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>"><img height="130" src="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>" alt="turntable"></a>
            </div>
            <div class="row">
              <input type="checkbox" name="author[]" value="<?php echo $image['id']; ?>" />
            </div>
          </li>
        <?php } ?>
        </ul>
      </div>
      <button name="author_btn" type="submit" class="btn btn_red">Save</button>
    </form>
    </div>
    <div id="student" class="tab-pane fade">
      <form id="student_frm" method="post" enctype="multipart/form-data">
      <div id="student_thumbnails" class="thumbnails">
        <ul class="clearfix">
        <?php foreach ($student_images as $image) { ?>
          <li class="col-md-3 text-center">
            <div class="row">
              <a href="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>"><img height="130" src="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>" alt="turntable"></a>
            </div>
            <div class="row">
              <input type="checkbox" name="student[]" value="<?php echo $image['id']; ?>" />
            </div>
          </li>
        <?php } ?>
      </ul>
      </div>
      <button name="student_btn" type="submit" class="btn btn_red">Save</button>
    </form>
    </div>
    <div id="teacher" class="tab-pane fade">
      <form id="teacher_frm" method="post" enctype="multipart/form-data">
      <div id="teacher_thumbnails" class="thumbnails">
        <ul class="clearfix">
        <?php foreach ($teacher_images as $image) { ?>
          <li class="col-md-3 text-center">
            <div class="row">
              <a href="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>"><img height="130" src="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>" alt="turntable"></a>
            </div>
            <div class="row">
              <input type="checkbox" name="teacher[]" value="<?php echo $image['id']; ?>" />
            </div>
          </li>
        <?php } ?>
      </ul>
      </div>
      <button name="teacher_btn" type="submit" class="btn btn_red">Save</button>
    </form>
    </div>
    
  </div>
    </div>
  </div>
  </div>
</div>
<script type="text/javascript" src="assets/js/jquery.lightbox-0.5.min.js"></script>
  <script type="text/javascript">
  $(function() {
    $('#author_thumbnails a').lightBox();
    $('#student_thumbnails a').lightBox();
    $('#teacher_thumbnails a').lightBox();
  });
</script>
