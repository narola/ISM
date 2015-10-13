<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
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
        <div class="row filter">
            <div class="col-sm-12">
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
    <div class="row tabel_view">
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
                            <th style="width:110px;">Actions</th>
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

                                        <?php if ($school['is_delete'] == '0') { ?>  
                                            <a href="<?php echo base_url() . 'admin/school/active/' . $school['id']; ?>" 
                                               onclick="return confirm('Activate User ?');" class="icon icon_user" data-toggle="tooltip" data-placement="bottom" title="Active" ></a>
                                           <?php } else { ?>   
                                            <a href="<?php echo base_url() . 'admin/school/blocked/' . $school['id']; ?>" 
                                               onclick="return confirm('Blocked User ?');" class="icon icon_blockuser" data-toggle="tooltip" data-placement="bottom" title="Block"></a>  
                                           <?php } ?>

                                        <a href="<?php echo base_url() . 'admin/school/update/' . $school['id']; ?>" class="icon icon_edit"
                                           data-toggle="tooltip" data-placement="bottom" title="Edit"> </a>
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
        if (q == '') {
            $('#q').removeAttr('name');
        }
        $('#filter').submit();
    }

    $("#filter").submit(function (event) {
        var q = $('#q').val();
        if (q == '') {
            $('#q').removeAttr('name');
        }
    });

<?php if (!empty($_GET['q'])) { ?>
        $('#q').val('<?php echo $_GET["q"]; ?>');
<?php } ?>

</script>
