
<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="admin/user">Admin</a></li>                          
                <li><a href="admin/course/lists">Courses</a></li>
                <li class="active">Add Course</li>
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
                    <h3>Add Course</h3>
                </div>
                <form method="post">
                    <div class="box_body ">

                        <div class="form-group">
                            <label>Course Name</label>
                            <input type="text" class="form-control" name="course_name" value="<?php echo set_value("course_name"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_name'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_name'), ''); ?>
                        </div>

                        <div class="form-group">
                            <label>Course Nickname</label>
                            <input type="text" class="form-control" name="course_nickname" value="<?php echo set_value("course_nickname"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_nickname'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_nickname'), ''); ?>
                        </div>

                        <div class="form-group">
                            <label>Course Details</label>
                            <textarea class="form-control" name="course_details"></textarea>
                        </div>


                        <div class="form-group">
                            <label>Course Type</label>
                             <select class="form-control " name="course_category_id" id="course_category_id">
                                <option selected disabled>Select course Type</option> 
                                 <?php 
                                  if(!empty($course_type)){ 
                                    foreach($course_type as $type) {
                                  ?> 
                                <option value="<?php echo $type; ?>"> <?php echo $type; ?></option>
                                <?php }  }else{ ?>
                                <option > No Course Type</option>
                                <?php } ?>
                            </select>
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_type'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_type'), ''); ?>
                        </div>
                        
                        <div class="form-group">
                            <label>Course Duration</label>
                            <input type="text" class="form-control" name="course_duration" value="<?php echo set_value("course_duration"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_duration'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_duration'), ''); ?>
                        </div>
                        
                        <div class="form-group">
                            <label>Course Degree</label>
                            <input type="text" class="form-control" name="course_degree" value="<?php echo set_value("course_degree"); ?>">
                        </div>
                        <div class="alert alert-danger <?php if (empty(strip_tags(form_error('course_degree'), ''))) {echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('course_degree'), ''); ?>
                        </div>

                        <div class="form-group ">
                            <select class="form-control " name="course_category_id" id="course_category_id">
                                <option selected disabled>Select course Category</option> 
                                 <?php 
                                  if(!empty($course_category)){ 
                                    foreach($course_category as $category) {
                                  ?> 
                                <option value="<?php echo $category['id']; ?>"> <?php echo $category['course_category_name']; ?></option>
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
                            <input type="radio" name="is_semester" <?php echo set_radio('is_semester', 'male', TRUE); ?> value="1">Yes
                            <input type="radio" name="is_semester" <?php echo set_radio('is_semester', 'female'); ?> value="0">No
                        </div>
                    </div>

                    <div class="box_footer">
                        <button type="submit" class="btn btn_green">Save</button>
                        <button class="btn btn_black">Cancel</button>
                    </div>
                </form>   
            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->        