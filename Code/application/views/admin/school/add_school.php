
<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="javascript:void(0)">Manage</a></li>                          
                <li><a href="admin/school">Schools</a></li>
                <li class="active">Add School</li>
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
                    <h3>Add School</h3>
                </div>
                <form method="post">
                    <div class="box_body ">

                        <div class="form-group">
                            <label>School name</label>
                            <input type="text" class="form-control" name="schoolname" value="<?php echo set_value("schoolname"); ?>">
                        </div>
                        <?php echo myform_error('schoolname'); ?>

                        <div class="form-group">
                            <label>School Nickname</label>
                            <input type="text" class="form-control" name="school_nickname" value="<?php echo set_value("school_nickname"); ?>">
                        </div>

                        <div class="form-group">
                            <label>School Code</label>
                            <input type="text" class="form-control" name="school_code" value="<?php echo set_value("school_code"); ?>" >
                        </div>
                        <?php echo myform_error('school_code'); ?>

                        <div class="form-group">
                            <label>School Type</label>
                            <select class="form-control " name="school_type" id="school_type">
                                <option value="co-education" <?php echo set_select("school_type", "co-education"); ?>>Co-Education</option>
                                <option value="girls" <?php echo set_select("school_type", "girls"); ?>>Girls</option>
                                <option value="boys" <?php echo set_select("school_type", "boys"); ?>>Boys</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Principal Name</label>
                            <input type="text" class="form-control" name="principal_name" value="<?php echo set_value("principal_name"); ?>">
                        </div>
                        <?php echo myform_error('principal_name'); ?>

                        <div class="form-group">
                            <label>School Email Id</label>
                            <input type="text" class="form-control" name="school_email_id" value="<?php echo set_value("school_email_id"); ?>">
                        </div>
                        <?php echo myform_error('school_email_id'); ?>

                        <div class="form-group">
                            <label>Contact Number-1</label>
                            <input type="text" class="form-control" name="contact_1" value="<?php echo set_value("contact_1"); ?>">
                        </div>
                        <?php echo myform_error('contact_1'); ?>

                        <div class="form-group">
                            <label>Contact Number-2</label>
                            <input type="text" class="form-control" name="contact_2" value="<?php echo set_value("contact_2"); ?>">
                        </div>
                        <?php echo myform_error('contact_2'); ?>

                        <div class="form-group">
                            <label>School Grade</label>
                            <select class="form-control " name="grade" id="grade">
                                <option value="A" <?php echo set_select("grade", "A"); ?>>A</option>
                                <option value="B" <?php echo set_select("grade", "B"); ?>>B</option>
                                <option value="C" <?php echo set_select("grade", "C"); ?>>C</option>
                                <option value="D" <?php echo set_select("grade", "D"); ?>>D</option>
                                <option value="E" <?php echo set_select("grade", "E"); ?>>E</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>School Mode</label>
                            <select class="form-control " name="mode" id="mode">
                                <option value="day" <?php echo set_select("mode", "day"); ?>>Day</option>
                                <option value="day/boarding" <?php echo set_select("mode", "day/boarding"); ?>>Day/Boarding</option>
                                <option value="day/hostel" <?php echo set_select("grade", "day/hostel"); ?>>Day/Hostel</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Address</label>
                            <input type="text" class="form-control" name="address" value="<?php echo set_value("address"); ?>">
                        </div>

                        <!-- <div class="row filter"> -->
                        <div class="form-group ">
                            <select class="form-control " name="country" onchange="get_states(this.value)" id="country_id">
                                <option selected disabled>Select Country</option> 
                                <?php
                                if (!empty($countries)) {
                                    foreach ($countries as $country) {
                                        ?> 
                                        <option value="<?php echo $country['id']; ?>"> <?php echo $country['country_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option value="0"> No Country</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('country'); ?>
                        <!-- </div> -->

                        <div class="form-group" >
                            <label>State</label>
                            <select class="form-control" name="state" id="states_id" onchange="get_cities(this.value)" >
                                <option selected disabled>Select State</option> 
                            </select>
                        </div>
                        <?php echo myform_error('state'); ?>

                        <div class="form-group">
                            <label>City</label>
                            <select class="form-control" name="city" id="city_id" onchange="get_districts(this.value)">
                                <option selected disabled>Select City</option> 
                            </select>
                        </div>
                        <?php echo myform_error('city'); ?>

                        <div class="form-group">
                            <label>District</label>
                            <select class="form-control" name="district" id="district_id">
                                <option selected disabled>Select District</option> 
                            </select>
                        </div>

                    </div>

                    <div class="box_footer">
                        <button type="submit" class="btn btn_green">Save</button>
                        <a href="<?php echo base_url() . $prev_url; ?>" class='btn btn_black'>Cancel</a>
                    </div>

                </form>   

            </div>
        </div>
    </div>
    <!--//mesage-->
</div>
<!--//main-->

<script type="text/javascript">


    function get_states(country_id) {
        $.ajax({
            url: '<?php echo base_url() . "common/ajax_get_states"; ?>',
            type: 'POST',
            data: {country_id: country_id},
            success: function (data) {
                $('#states_id').html(data);
                $('#city_id').html('<option> Select City </option>');
            }
        });
    }

    function get_cities(state_id) {
        $.ajax({
            url: '<?php echo base_url() . "common/ajax_get_cities"; ?>',
            type: 'POST',
            data: {state_id: state_id},
            success: function (data) {
                $('#city_id').html(data);
            }
        });
    }

    function get_districts(city_id) {
        $.ajax({
            url: '<?php echo base_url() . "common/ajax_get_districts"; ?>',
            type: 'POST',
            data: {city_id: city_id},
            success: function (data) {
                $('#district_id').html(data);
            }
        });
    }

</script>         