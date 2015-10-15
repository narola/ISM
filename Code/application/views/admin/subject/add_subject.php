
<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="admin/user">Admin</a></li>                          
                <li><a href="admin/subject/lists">Subjects</a></li>
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
                <?php //p($courses); exit;?>
                <form method="post" enctype='multipart/form-data'>
                    <div class="box_body ">
                        <div class="form-group ">
                            <label>Select Course</label>
                            <select class="form-control " name="course" onchange="get_classroom(this.value)" id="course_id">
                                <option selected disabled>Select Course</option> 
                                <?php
                                if (!empty($courses)) {
                                    foreach ($courses as $course) {
                                        ?> 
                                        <option value="<?php echo $course['id']; ?>" <?php echo set_select('course', $course['id']); ?>> 
                                            <?php echo $course['course_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option value="0"> No Course</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('course'); ?>

                        <div class="form-group ">
                            <label>Select Classroom</label>
                            <select class="form-control " name="classroom" id="classroom_id">
                                <option selected disabled>Select Classroom</option> 
                                <?php
                                if (!empty($classrooms)) {
                                    foreach ($classrooms as $classroom) {
                                        ?> 
                                        <option value="<?php echo $classroom['id']; ?>" <?php echo set_select('classroom', $classroom['id']); ?>> 
                                            <?php echo $classroom['class_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option value="0"> No Classroom</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('classroom'); ?>

                        <div class="form-group">
                            <label>Subject Image</label>
                            <input type="file"  name="subject_image">
                        </div>

                        <div class="form-group">
                            <label>Subject Name</label>
                            <input type="text" class="form-control" name="subject_name" maxlength="30"
                                   value="<?php echo set_value("subject_name"); ?>">
                        </div>                        
                        <?php echo myform_error('subject_name'); ?>
                        <div class="box_footer">
                            <button type="submit" class="btn btn_green">Save</button>
                            <button type="reset" class="btn btn_red">Reset</button>
                            <a href="<?php echo base_url() . $prev_url; ?>" class='btn btn_black'>Cancel</a>
                        </div>
                    </div>
                </form>   
            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->        

<script>
    function get_classroom(course_id) {
        $.ajax({
            url: '<?php echo base_url() . "common/ajax_get_classrooms"; ?>',
            type: 'POST',
            data: {course_id: course_id},
            success: function (data) {
                $('#classroom_id').html(data);
            }
        });
    }
</script>