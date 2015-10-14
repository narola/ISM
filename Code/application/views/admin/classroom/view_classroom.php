<!--main-->
<div class="col-sm-7 main main2">
    <!--breadcrumb-->
    <div class="row page_header">
        <div class="col-sm-8">
            <ol class="breadcrumb">
                <li><a href="#">Manage</a></li>
                <li class="active">Classroom</li>
            </ol>
        </div>
        <div class="col-sm-4 text-right">
            <a class="btn btn_green add_topic" href="<?php echo base_url() . 'admin/classroom/add'; ?>" >Add New Classroom</a>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->
    <form method="get" id="filter">
        <div class="row filter">

            <div class="col-sm-12">
                <div class="form-group">
                    <select class="form-control" name="course_name" onchange="filter_data()" id="course_name">
                        <option value="">Select Course</option>
                        <?php
                        if (!empty($courses)) {
                            foreach ($courses as $c1) {
                                ?>
                                <option value="<?php echo $c1['id']; ?>" ><?php echo $c1['course_name']; ?></option>  
                                <?php
                            }
                        }
                        ?>
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
    <div class="row tabel_view">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table_user">
                    <thead>
                        <tr>
                            <th style="width: 240px;">Class Name</th>
                            <th>Course Name</th>
                            <th>Class Nickname</th>
                            <th style="width:70px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        if (!empty($all_classrooms)) {

                            foreach ($all_classrooms as $classroom) {
                                ?>
                                <tr>
                                    <td class="username">
                                        <h4><?php echo ucfirst($classroom['class_name']); ?></h4>
                                    </td>

                                    <td><?php echo ucfirst($classroom['course_name']); ?></td>
                                    <td> <?php echo ucfirst($classroom['class_nickname']); ?> </td>
                                    <td>
                                        <a href="<?php echo base_url() . 'admin/classroom/update/' . $classroom['id']; ?>" class="icon icon_edit"
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
        var course_name = $('#course_name').val();
        if (q == '') {
            $('#q').removeAttr('name');
        }

        if (course_name == '') {
            $('#course_name').removeAttr('name');
        }
        $('#filter').submit();
    }

    $("#filter").submit(function (event) {
        var q = $('#q').val();
        var course_name = $('#course_name').val();
        if (q == '') {
            $('#q').removeAttr('name');
        }
        if (course_name == '') {
            $('#course_name').removeAttr('name');
        }
    });

<?php if (!empty($_GET['q'])) { ?>
        $('#q').val('<?php echo $_GET["q"]; ?>');
<?php } ?>

<?php if (!empty($_GET['course_name'])) { ?>
        $('#course_name').val('<?php echo $_GET["course_name"]; ?>');
<?php } ?>


</script>
