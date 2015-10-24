
<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    <!--breadcrumb-->
    <div class="page_header">
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
   	<div class="col-sm-12">
        <div class="col-sm-12 new_message">
            <div class="box exam_card">
                <div class="box_header">
                    <h3>Add Course</h3>
                </div>
                <form method="post">
                    <div class="box_body admin_controls with_labels">

                        <div class="form-group">
                            <label>Course Name</label>
                            <input type="text" class="form-control" name="course_name" value="<?php echo set_value("course_name"); ?>">
                        </div>                        
                        <?php echo myform_error('course_name'); ?>

                        <div class="form-group">
                            <label>Course Nickname</label>
                            <input type="text" class="form-control" name="course_nickname" value="<?php echo set_value("course_nickname"); ?>">
                        </div>
                        <?php echo myform_error('course_nickname'); ?>

                        <div class="form-group">
                            <label>Course Details</label>
                            <textarea class="form-control" name="course_details"></textarea>
                        </div>

                        <div class="form-group select">
                            <label>Course Type</label>
                             <select class="form-control " name="course_type" id="course_category_id">
                                <option selected disabled>Select course Type</option> 
                                 <?php 
                                  if(!empty($course_types)){ 
                                    foreach($course_types as $type) {
                                  ?> 
                                <option value="<?php echo $type; ?>" <?php echo set_select('course_type',$type); ?>> <?php echo $type; ?></option>
                                <?php }  }else{ ?>
                                <option > No Course Type</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('course_type'); ?>
                        
                        <div class="form-group">
                            <label>Course Duration</label>
                            <input type="text" class="form-control" name="course_duration" value="<?php echo set_value("course_duration"); ?>">
                        </div>                        
                        <?php echo myform_error('course_duration'); ?>
                        
                        <div class="form-group">
                            <label>Course Degree</label>
                            <input type="text" class="form-control" name="course_degree" value="<?php echo set_value("course_degree"); ?>">
                        </div>                       
                        <?php echo myform_error('course_degree'); ?>

                        <div class="form-group select">
							<label>Select Course</label>
                            <select class="form-control " name="course_category_id" id="course_category_id">
                                <option selected disabled>Select course Category</option> 
                                 <?php 
                                  if(!empty($course_category)){ 
                                    foreach($course_category as $category) {
                                  ?> 
                                <option value="<?php echo $category['id']; ?>" <?php echo set_select('course_category_id',$category['id']); ?>> <?php echo $category['course_category_name']; ?></option>
                                <?php }  }else{ ?>
                                <option > No Course Category</option>
                                <?php } ?>
                            </select>
                        </div>                        
                        <?php echo myform_error('course_category_id'); ?>
                        
                        <div class="form-group">
                            <label>Is Semester</label>
                            <input type="radio" name="is_semester" <?php echo set_radio('is_semester', '1', TRUE); ?> value="1">Yes
                            <input type="radio" name="is_semester" <?php echo set_radio('is_semester', '0'); ?> value="0">No                          
                        </div>
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