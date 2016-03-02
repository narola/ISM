
<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    <!--breadcrumb-->
    <div class="page_header">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="javascript:void(0)">Manage</a></li>                          
                <li><a href="admin/school">Schools</a></li>
                <li class="active">Edit School</li>
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
                    <h3>Edit School</h3>
                </div>
                <form method="post">
                    <div class="box_body admin_controls with_labels">

                        <div class="form-group">
                            <label>School name</label>
                            <input type="text" class="form-control" name="schoolname" maxlength="100" 
                                   value="<?php echo set_value("school_name") == false ? $school["school_name"] : set_value("school_name"); ?>">
                        </div>
                        <?php echo myform_error('schoolname'); ?>

                        <div class="form-group">
                            <label>School Nickname</label>
                            <input type="text" class="form-control" name="school_nickname" maxlength="20" 
                                   value="<?php echo set_value("school_nickname") == false ? $school["school_nickname"] : set_value("school_nickname"); ?>">
                        </div>

                        <div class="form-group">
                            <label>School Code</label>
                            <input type="school_code" class="form-control" name="school_code" maxlength="10" 
                                   value="<?php echo set_value("school_code") == false ? $school["school_code"] : set_value("school_code"); ?>">
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
                            <input type="text" class="form-control" name="principal_name" maxlength="50" 
                                   value="<?php echo set_value("principal_name") == false ? $school["principal_name"] : set_value("principal_name"); ?>">
                        </div>
                        <?php echo myform_error('principal_name'); ?>

                        <div class="form-group">
                            <label>School Email Id</label>
                            <input type="text" class="form-control" name="school_email_id" maxlength="100" 
                                   value="<?php echo set_value("school_email_id") == false ? $school["school_email_id"] : set_value("school_email_id"); ?>">
                        </div>
                        <?php echo myform_error('school_email_id'); ?>

                        <div class="form-group">
                            <label>Contact Number-1</label>
                            <input type="text" class="form-control" name="contact_1" maxlength="20" 
                                   value="<?php echo set_value("school_contact_no1") == false ? $school["school_contact_no1"] : set_value("school_contact_no1"); ?>">
                        </div>
                        <?php echo myform_error('contact_1'); ?>

                        <div class="form-group">
                            <label>Contact Number-2</label>
                            <input type="text" class="form-control" name="contact_2" maxlength="20" 
                                   value="<?php echo set_value("school_contact_no2") == false ? $school["school_contact_no2"] : set_value("school_contact_no2"); ?>">
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
                            <input type="text" class="form-control" name="address" 
                                   value="<?php echo set_value("address") == false ? $school["address"] : set_value("address"); ?>">
                        </div>

                        <div class="form-group select">
                            <label>Country</label>
                            <select class="form-control " name="country" onchange="get_states(this.value)" id="country_id">
                                <option selected disabled>Select Country</option> 
                                <?php
                                if (!empty($countries)) {
                                    foreach ($countries as $country) {
                                        ?> 
                                        <option value="<?php echo $country['id']; ?>" <?php echo set_select("country", $country['id']); ?>>
                                            <?php echo $country['country_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option > No Country</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('country'); ?>
                        <!-- </div> -->

                        <div class="form-group" >
                            <label>State</label>
                            <select class="form-control" name="state" id="states_id" onchange="get_cities(this.value)" >
                                <option selected disabled>Select State</option>
                                <?php
                                if (!empty($states)) {

                                    foreach ($states as $state) {
                                        ?> 
                                        <option value="<?php echo $state['id']; ?>" <?php echo set_select("state", $state['id']); ?>>
                                            <?php echo $state['state_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option > No States</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('state'); ?>

                        <div class="form-group">
                            <label>City</label>
                            <select class="form-control" name="city" id="city_id" onchange="get_districts(this.value)">
                                <option disabled>Select City</option>
                                <?php
                                if (!empty($cities)) {
                                    foreach ($cities as $city) {
                                        ?> 
                                        <option value="<?php echo $city['id']; ?>" <?php echo set_select("city", $city['id']); ?>>
                                            <?php echo $city['city_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option > No City</option>
                                <?php } ?>
                            </select>
                        </div>
                        <?php echo myform_error('city'); ?>

                        <div class="form-group">
                            <label>District</label>
                            <select class="form-control" name="district" id="district_id">
                                <option disabled>Select District</option>
                                <?php
                                if (!empty($districts)) {
                                    foreach ($districts as $district) {
                                        ?> 
                                        <option value="<?php echo $district['id']; ?>" <?php echo set_select("district", $district['id']); ?>>
                                            <?php echo $district['district_name']; ?></option>
                                        <?php
                                    }
                                } else {
                                    ?>
                                    <option value="0"> No District</option>
                                <?php } ?>
                            </select>
                        </div>

                    </div>

                    <div class="box_footer">
                        <button type="submit" class="btn btn_green">Save</button>
                        <button type="button" class="btn btn_red" onClick="window.location.reload();">Reset</button>
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
    $('#country_id').val('<?php echo $school["country_id"]; ?>');
    $('#states_id').val('<?php echo $school["state_id"]; ?>');
    $('#city_id').val('<?php echo $school["city_id"]; ?>');
    $('#district_id').val('<?php echo $school["district_id"]; ?>');
    $('#school_type').val('<?php echo $school["school_type"]; ?>');
    $('#grade').val('<?php echo $school["school_grade"]; ?>');
    $('#mode').val('<?php echo $school["school_mode"]; ?>');


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