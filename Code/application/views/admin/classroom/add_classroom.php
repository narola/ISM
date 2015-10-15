
<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="javascript:void(0)">Manage</a></li>                          
                <li><a href="admin/classroom">Classroom</a></li>
                <li class="active">Add Classroom</li>
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
                    <h3>Add Classroom</h3>
                </div>
                <form method="post">
                    <div class="box_body ">

                        <div class="form-group">
                            <label>Class name</label>
                            <input type="text" class="form-control" maxlength="15" name="class_name" value="<?php echo set_value("class_name"); ?>">
                        </div>
                        <?php echo myform_error('class_name'); ?>

                        <div class="form-group">
                            <label>Class Nickname</label>
                            <input type="text" class="form-control" name="class_nickname" maxlength="10" value="<?php echo set_value("class_nickname"); ?>">
                        </div>

                        <div class="form-group ">
                            <select class="form-control " name="course_id" onchange="get_states(this.value)" id="course_id">
                                <option selected disabled>Select Course</option> 
                                <?php
                                if (!empty($courses)) {
                                    foreach ($courses as $course) {
                                        ?> 
                                        <option value="<?php echo $course['id']; ?>"> <?php echo $course['course_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option value="0"> No Course</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('course_id'); ?>

                    </div>

                    <div class="box_footer">
                        <button type="submit" class="btn btn_green">Save</button>
                        <button type="reset" class="btn btn_red">Reset</button>
                        <a href="<?php echo base_url() . $prev_url; ?>" class='btn btn_black'>Cancel</a>
                    </div>

                </form>   

            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->
