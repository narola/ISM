
<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="admin/user">Admin</a></li>                          
                <li><a href="admin/course/lists">Subjects</a></li>
                <li class="active">Add Subject</li>
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
                    <h3>Add Subject</h3>
                </div>
                <form method="post" enctype='multipart/form-data'>
                    <div class="box_body ">
                        <div class="form-group">
                            <label>Subject Image</label>
                            <input type="file" class="form-control" name="subject_image">
                        </div>
                    </div>
                    <div class="box_body ">
                        <div class="form-group">
                            <label>Subject Name</label>
                            <input type="text" class="form-control" name="subject_name" value="<?php echo set_value("subject_name"); ?>">
                        </div>                        
                        <?php echo myform_error('subject_name'); ?>
                    </div>
                    <div class="box_footer">
                         <button type="submit" class="btn btn_green">Save</button>
                        <button type="reset" class="btn btn_red">Reset</button>
                        <a href="<?php echo base_url().$prev_url; ?>" class='btn btn_black'>Cancel</a>
                    </div>
                </form>   
            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->        