<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    <!--breadcrumb-->
    <div class="page_header">
        <div class="col-sm-8">
            <ol class="breadcrumb">
                <li><a href="#">Manage</a></li>
                <li class="active">Schools</li>
            </ol>
        </div>
        <div class="col-sm-4 text-right">
            <a class="btn btn_green add_topic" href="<?php echo base_url() . 'admin/school/add'; ?>" >Add New School</a>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->
    <form method="get" id="filter">
        <div class="filter group_filter">

            <div class="col-sm-12">
                <div class="form-group">
                    <select class="form-control" name="school_grade" onchange="filter_data()" id="school_grade">
                        <option value="">Select Grade</option>
                        <?php
                        if (!empty($school_grade)) {
                            foreach ($school_grade as $grade) {
                                ?>
                                <option value="<?php echo $grade['school_grade']; ?>" ><?php echo $grade['school_grade']; ?></option>  
                                <?php
                            }
                        }
                        ?>
                    </select>
                </div>

                <div class="form-group">
                    <select class="form-control" name="order" id="order" onchange="filter_data()">
                        <option value="">Sort By</option>
                        <option value="name_asc">Name Ascending</option>
                        <option value="name_desc">Name Descending</option>
                        <option value="latest">Latest First</option>
                        <option value="older">Older First</option>
                    </select>
                </div>

                <div class="form-group no_effect search_input">
                    <input type="text" name="q" id="q" class="form-control" placeholder="Search" >
                    <a class="fa fa-search" onclick="filter_data()" style="cursor:pointer"></a>
                </div>
            </div>
        </div>
    </form>	

    <?php $success = $this->session->flashdata('success'); ?>

    <div class="alert alert-success <?php
    if (empty(strip_tags($success, ''))) {
        echo 'hide';
    }
    ?>">
             <?php echo strip_tags($success); ?>
    </div>

    <?php $error = $this->session->flashdata('error'); ?>

    <div class="alert alert-danger <?php
    if (empty(strip_tags($error, ''))) {
        echo 'hide';
    }
    ?>">
             <?php echo strip_tags($error); ?>
    </div>

    <!--//filter-->

    <!--row table-->
    <div class="tabel_view">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table_user">
                    <thead>
                        <tr>
                            <th style="width: 240px;">School Name</th>
                            <th>Principal Name</th>
                            <th>Grade</th>
                            <th>Address</th>
                            <th>Contacts</th>
                            <th style="width:100px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        if (!empty($all_schools)) {

                            foreach ($all_schools as $school) {
                                ?>
                                <tr>
                                    <td class="username">

                                        <h4><?php echo ucfirst($school['school_name']); ?></h4>
                                        <p class="active"><?php echo ucfirst($school['school_type']); ?></p>
                                    </td>

                                    <td><?php echo ucfirst($school['principal_name']); ?></td>
                                    <td> <?php echo ucfirst($school['school_grade']); ?> </td>
                                    <td> 
                                        <?php
                                        echo ucfirst($school['address']) . ", " .
                                        ucfirst($school['city_name']) . ", " .
                                        ucfirst($school['state_name']) . ", " .
                                        ucfirst($school['country_name']);
                                        ?> 
                                    </td>
                                    <td>
                                        <?php echo ucfirst($school['school_contact_no1']); ?>
                                        <br/>
                                        <?php echo ucfirst($school['school_contact_no2']); ?>
                                    </td>
                                    <td>
                                        <a href="<?php echo base_url() . 'admin/school/update/' . $school['id']; ?>" class="icon icon_edit"
                                           data-toggle="tooltip" data-placement="bottom" title="Edit"> </a>
										<a href="<?php echo base_url() . 'admin/school/update/' . $school['id']; ?>" class="icon icon_delete_color no-margin"
                                           data-toggle="tooltip" data-placement="bottom" title="Delete"> </a>
                                    </td>
                                </tr>
                                <?php
                            }
                        } else {
                            ?>

                            <tr> <td colspan="7" class="text-center"><strong>No Data Found. </strong> </td> </tr>		

                        <?php } ?>
                    </tbody>
                </table>
            </div>
            <nav  class="text-center">

                <?php echo $this->pagination->create_links(); ?>

            </nav>
        </div>
    </div>

    <!--//row table-->
</div>
<!--//main-->

<script type="text/javascript">

    function filter_data() {
        
        var q = $('#q').val();
        var school_grade = $('#school_grade').val();
        var order = $('#order').val();
        
        if (q == '') {$('#q').removeAttr('name');}
        if (school_grade == '') {$('#school_grade').removeAttr('name');}
        if(order == ''){  $('#order').removeAttr('name'); }

        $('#filter').submit();
    }

    $("#filter").submit(function (event) {
        
        var q = $('#q').val();
        var school_grade = $('#school_grade').val();
        var order = $('#order').val();

        if (q == '') { $('#q').removeAttr('name'); }
        if (school_grade == '') { $('#school_grade').removeAttr('name'); }
        if(order == ''){  $('#order').removeAttr('name'); }

    });

    <?php if(!empty($_GET['order'])) { ?>
        $('#order').val('<?php echo $_GET["order"];?>');    
    <?php } ?> 

    <?php if (!empty($_GET['q'])) { ?>
            $('#q').val('<?php echo $_GET["q"]; ?>');
    <?php } ?>

    <?php if (!empty($_GET['school_grade'])) { ?>
            $('#school_grade').val('<?php echo $_GET["school_grade"]; ?>');
    <?php } ?>


</script>
