<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="admin/user">Admin</a></li>                          
                <li><a href="admin/subject/lists">Subject</a></li>
                <li class="active">Update Subject</li>
            </ol>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->

    <!--//filter-->

    <!--message-->
   	<div class="row">
        <div class="col-sm-12 new_message">
            <div class="box exam_card">
                <div class="box_header">
                    <h3>Update Subject</h3>
                </div>
                <form method="post" enctype='multipart/form-data'>
                    <div class="box_body ">
                        <div class="form-group">
                            <div>
                                <?php 
                                if(!empty( $subject["subject_image"] )){ ?>
                                    <img height="80px" width="150px" src="<?php echo base_url(); ?>uploads/<?php echo $subject['subject_image']; ?>"
                                            onerror="this.src='<?php echo base_url() ?>uploads/subjects/No_image_available.jpg'"></a>
                                <?php }
                                ?>
                            </div>
                            <label>Subject Image</label>
                            <input type="file" class="form-control" name="subject_image">
                        </div>                        
                        <div class="form-group">
                            <label>Subject Name</label>
                            <input type="text" class="form-control" name="subject_name"  value="<?php  echo set_value("subject_name") == false ? $subject["subject_name"] : set_value("subject_name"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_name'), ''))) { echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_name'), ''); ?>
                        </div>
                    </div>
                    <div class="box_footer">
                        <button type="submit" class="btn btn_green">Update</button>
                        <button class="btn btn_black">Cancel</button>
                    </div>

                </form>   

            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->