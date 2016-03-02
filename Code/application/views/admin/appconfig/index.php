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
      <li <?php echo ($tab == 'author')  ? 'class="active"' : ''; ?> ><a data-toggle="tab" href="#author">Author</a></li>
      <li <?php echo ($tab == 'student') ? 'class="active"' : ''; ?>><a data-toggle="tab" href="#student">Student</a></li>
      <li <?php echo ($tab == 'teacher') ? 'class="active"' : ''; ?>><a data-toggle="tab" href="#teacher">Teacher</a></li>
    </ul>

  <div class="tab-content">
    <div id="author" class="tab-pane fade <?php echo ($tab == 'author')  ? 'in active' : ''; ?>">

          <div class="col-sm-12 ">
            <div class="col-sm-6">
            <div class="form-group select">
                    <select class="form-control" name="bulk_action" onchange="submit_bulk_form(this.value)"> 
                        <option value="">Bulk Action</option>
                        <option value="delete" >Delete</option>
                    </select>
                </div>
            </div>
            
      <div class="col-sm-6">
      <form method="post" id="author_filter">
        <div class="filter">

            <div class="form-group pull-right">
                <select class="form-control" name="author_status" id="author_status">
                    <option <?php if(isset($author_status)) echo ($author_status == '') ? 'selected' : ''; ?> value="">Both</option>
                    <option <?php if(isset($author_status)) echo ($author_status == 'active') ? 'selected' : ''; ?> value="active">Active</option>
                    <option <?php if(isset($author_status)) echo ($author_status == 'archive') ? 'selected' : ''; ?> value="archive">Inactive</option>
                </select>
            </div>
        </div>
      </form>
          </div>
          </div>

      <form id="author_frm" method="post" enctype="multipart/form-data">
      <div id="author_thumbnails" class="thumbnails">
        <ul class="clearfix">
        <?php foreach ($author_images as $image) { ?>
          <li class="col-md-3 text-center">
            <div class="row">
              <input type="checkbox" name="author_multiple[]" value="<?php echo $image['id']; ?>" />
            </div>
            <div class="row">
              <a href="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>"><img height="130" src="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>" alt="turntable"></a>
            </div>
             <div class="row make-switch">
                <input class="status" type="checkbox" name="author[]" value="<?php echo $image['id']; ?>"
                        <?php echo ($image['status']=='active') ? 'checked' : ''; ?> data-handle-width="50" data-size="mini" ><!--data-size="mini"-->
            </div>
          </li>
        <?php } ?>
        </ul>
      </div>
      <button name="author_btn" type="submit" class="btn btn_red">Save</button>
      <button data-appuser="Author" type="button" data-toggle="modal" data-target="#myModal" class="upload_author_btn upload_btn btn btn_red">Upload Images</button>
    </form>
    </div>
    <div id="student" class="tab-pane fade <?php echo ($tab == 'student')  ? 'in active' : ''; ?>">

      <form method="post" id="student_filter">
        <div class="filter">
          <div class="col-sm-12">
            <div class="form-group pull-right">
                <select class="form-control" name="student_status" id="student_status">
                    <option <?php if(isset($student_status)) echo ($student_status == '') ? 'selected' : ''; ?> value="">Both</option>
                    <option <?php if(isset($student_status)) echo ($student_status == 'active') ? 'selected' : ''; ?> value="active">Active</option>
                    <option <?php if(isset($student_status)) echo ($student_status == 'archive') ? 'selected' : ''; ?> value="archive">Inactive</option>
                </select>
            </div>
          </div>
        </div>
      </form>

      <form id="student_frm" method="post" enctype="multipart/form-data">
      <div id="student_thumbnails" class="thumbnails">
        <ul class="clearfix">
        <?php foreach ($student_images as $image) { ?>
          <li class="col-md-3 text-center">
            <div class="row">
              <input type="checkbox" name="student_multiple[]" value="<?php echo $image['id']; ?>" />
            </div>
            <div class="row">
              <a href="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>"><img height="130" src="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>" alt="turntable"></a>
            </div>
             <div class="row make-switch ">
                <input class="status" type="checkbox" name="student[]" value="<?php echo $image['id']; ?>"
                        <?php echo ($image['status']=='active') ? 'checked' : ''; ?> data-handle-width="50" data-size="mini" ><!--data-size="mini"-->
            </div>
          </li>
        <?php } ?>
      </ul>
      </div>
      <button name="student_btn" type="submit" class="btn btn_red">Save</button>
      <button data-appuser="Student" type="button" data-toggle="modal" data-target="#myModal" class="upload_student_btn upload_btn btn btn_red">Upload Images</button>
    </form>
    </div>
    <div id="teacher" class="tab-pane fade <?php echo ($tab == 'teacher')  ? 'in active' : ''; ?>">

      <form method="post" id="teacher_filter">
        <div class="filter">
          <div class="col-sm-12">
            <div class="form-group pull-right">
                <select class="form-control" name="teacher_status" id="teacher_status">
                    <option <?php if(isset($teacher_status)) echo ($teacher_status == '') ? 'selected' : ''; ?> value="">Both</option>
                    <option <?php if(isset($teacher_status)) echo ($teacher_status == 'active') ? 'selected' : ''; ?> value="active">Active</option>
                    <option <?php if(isset($teacher_status)) echo ($teacher_status == 'archive') ? 'selected' : ''; ?> value="archive">Inactive</option>
                </select>
            </div>
          </div>
        </div>
      </form>
      <form id="teacher_frm" method="post" enctype="multipart/form-data">
      <div id="teacher_thumbnails" class="thumbnails">
        <ul class="clearfix">
        <?php foreach ($teacher_images as $image) { ?>
          <li class="col-md-3 text-center">
            <div class="row">
              <input type="checkbox" name="teacher_multiple[]" value="<?php echo $image['id']; ?>" />
            </div>
            <div class="row">
              <a href="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>"><img height="130" src="<?php echo UPLOAD_URL ?>/images/<?php echo $image['image_url']; ?>" alt="turntable"></a>
            </div>
            <div class="row make-switch ">
                <input class="status" type="checkbox" name="teacher[]" value="<?php echo $image['id']; ?>"
                        <?php echo ($image['status']=='active') ? 'checked' : ''; ?> data-handle-width="50" data-size="mini" ><!--data-size="mini"-->
            </div>
          </li>
        <?php } ?>
      </ul>
      </div>
      <button name="teacher_btn" type="submit" class="btn btn_red">Save</button>
      <button data-appuser="Teacher" type="button" data-toggle="modal" data-target="#myModal" class="upload_teacher_btn upload_btn btn btn_red">Upload Images</button>
    </form>
    </div>
    
  </div>
    </div>
  </div>
  </div>
</div>
<!-- Upload Images Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header notice_header text-center">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"> Upload Login Backgrounds for <span class="app_user"></span></h4>
        <small>
        </small>
      </div>
      <div class="modal-body">
        <form method="post" action="admin/appconfig/upload_images" enctype="multipart/form-data">
          <input type="file" name="app_images[]" multiple="multiple"/>
          <input type="hidden" name="app_user" value=""/>
          <button type="submit">Save</button> 
        </form>
        <div class="clearfix"></div>
      </div>
      
    </div>
  </div>
</div>
    <!-- /Upload Images Modal-->
<script type="text/javascript" src="assets/js/jquery.lightbox-0.5.min.js"></script>
  <script type="text/javascript">
  jQuery(document).ready(function($) {

    $(".upload_btn").click(function(){
      var appuser = $(this).data('appuser');
      $("span.app_user").text(appuser);
      $("input[name=app_user]").val(appuser);

    })
    $("input.status").bootstrapSwitch();
    $('.bootstrap-switch-handle-on').text('Active');
    $('.bootstrap-switch-handle-off').text('Inactive');

    $("#author_status").change(function(){
      $("#author_filter").submit();
    });
    $("#student_status").change(function(){
      $("#student_filter").submit();
    });
    $("#teacher_status").change(function(){
      $("#teacher_filter").submit();
    });    


  });
  $(function() {
   
    $('#author_thumbnails a').lightBox();
    $('#student_thumbnails a').lightBox();
    $('#teacher_thumbnails a').lightBox();
  });
</script>
