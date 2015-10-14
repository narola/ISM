<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="admin/user">Admin</a></li>                          
                <li><a href="admin/course/lists">Courses</a></li>
                <li class="active">Update Course</li>
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
                    <h3>Update Course</h3>
                </div>
                <form method="post">
                    <div class="box_body ">
                        <div class="form-group">
                            <label>Course Name</label>
                            <input type="text" class="form-control" name="course_name"  value="<?php  echo set_value("course_name") == false ? $course["course_name"] : set_value("course_name"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_name'), ''))) { echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_name'), ''); ?>
                        </div>

                        <div class="form-group">
                            <label>Course Nickname</label>
                            <input type="text" class="form-control" name="course_nickname" value="<?php  echo set_value("course_nickname") == false ? $course["course_nickname"] : set_value("course_nickname"); ?>"
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_nickname'), ''))) { echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_nickname'), ''); ?>
                        </div>

                        <div class="form-group">
                            <label>Course Details</label>
                            <textarea class="form-control" name="course_details"><?php echo (set_value("course_details") == false) ? $course["course_details"] : set_value("course_details"); ?></textarea>
                        </div>

                        <div class="form-group">
                            <label>Course Type</label>
                             <select class="form-control " name="course_type" id="course_category_id">
                                <option selected disabled>Select course Type</option> 
                                 <?php 
                                  if(!empty($course_types)){ 
                                    foreach($course_types as $type) {
                                  ?> 
                                <option <?php if ($type == $course['course_type'] ) echo 'selected="selected" '; ?>  value="<?php echo $type; ?>"> <?php echo $type; ?></option>
                                <?php }  }else{ ?>
                                <option > No Course Type</option>
                                <?php } ?>
                            </select>
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_type'), ''))) { echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_type'), ''); ?>
                        </div>
                        
                        <div class="form-group">
                            <label>Course Duration</label>
                            <input type="text" class="form-control" name="course_duration" value="<?php  echo set_value("course_duration") == false ? $course["course_duration"] : set_value("course_duration"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_duration'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_duration'), ''); ?>
                        </div>
                        
                        <div class="form-group">
                            <label>Course Degree</label>
                            <input type="text" class="form-control" name="course_degree" value="<?php  echo set_value("course_degree") == false ? $course["course_degree"] : set_value("course_degree"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_degree'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_degree'), ''); ?>
                        </div>

                        <div class="form-group ">
                            <select class="form-control " name="course_category_id" id="course_category_id">
                                <option selected disabled>Select course Category</option> 
                                 <?php 
                                  if(!empty($course_category)){ 
                                    foreach($course_category as $category) {?>                                    
                                    <option <?php if ($category['id'] == $course['course_category_id'] ) echo 'selected="selected" '; ?> value="<?php echo $category['id']; ?>"> <?php echo $category['course_category_name']; ?></option>
                                <?php }  }else{ ?>
                                <option > No Course Category</option>
                                <?php } ?>
                            </select>
                        </div>                        
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_category_id'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_category_id'), ''); ?>
                        </div>
                        
                        <div class="form-group">
                            <label>Is Semester</label>
                            <input type="radio" name="is_semester"  value="1" <?php if($course['is_semester'] == '1'){ echo 'checked'; } ?> >Yes
                            <input type="radio" name="is_semester" value="0" <?php if($course['is_semester'] == '0'){ echo 'checked'; } ?>>No
                        </div>
                    </div>

                    <div class="box_footer">
                        <button type="submit" class="btn btn_green">Update</button>
                         <a href="<?php echo base_url().$prev_url; ?>" class='btn btn_black'>Cancel</a>
                    </div>

                </form>   

            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->