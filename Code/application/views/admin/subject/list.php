<!--main-->
<div class="col-sm-7 main main2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    <!--breadcrumb-->
    <div class="page_header">
        <div class="col-sm-8">
            <ol class="breadcrumb">
                <li><a href="#">Manage</a></li>
                <li class="active">Subjects</li>
            </ol>
        </div>
        <div class="col-sm-4 text-right">
            <a class="btn btn_green add_topic" href="<?php echo base_url() . 'admin/subject/add_subject'; ?>" >Add Subject</a>
        </div>
    </div>
    <!--//breadcrumb-->
    <!--filter-->
    <form method="get" id="filter">
        <div class="filter group_filter">
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
    <div class="tabel_view">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered table_user">
                    <thead>
                        <tr>
                            <th style="width: 150px;">Subject Image</th>
                            <th>Subject Name</th>
                            <th style="width:110px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        if (!empty($all_subjects)) {
                            foreach ($all_subjects as $subject) {?>
                                <tr>                                    
                                    <td>
                                        <div>
                                            <a target="_blank" href="<?php echo !empty($subject['subject_image']) ? base_url().'uploads/'.$subject['subject_image'] : ''; ?>">
                                            <img height="80px" width="150px" src="<?php echo base_url(); ?>uploads/<?php echo $subject['subject_image']; ?>"
                                            onerror="this.src='<?php echo base_url() ?>uploads/subjects/No_image_available.jpg'"></a>
                                        </div>
                                    </td>
                                    <td><?php echo ucfirst($subject['subject_name']); ?></td>
                                    <td>
                                        <a href="<?php echo '/admin/subject/update_subject/'.$subject['id'];?>" data-toggle="tooltip" data-placement="right" data-original-title="Edit" class="icon icon_edit"></a>
                                        <a data-toggle="tooltip" id="delete_<?php echo $subject['id']; ?>" data-placement="right" data-original-title="Delete" class="delete icon icon_delete"></a>                                        
                                    </td>
                                </tr>
                                <?php
                            }
                        } else {?>
                            <tr> <td colspan="7" class="text-center"><strong>No Data Found. </strong></td></tr>
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
<!-- Modal -->
<div class="modal fade" id="close_mate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:500px;margin-top:220px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">CONFIRMATION FORM</h4>                
            </div>
            <div class="modal-body">
                <p><code><h4>Are sure for want to remove from Subject list?</h4></code></p>
                    <h4 class="notice_by"><button class="btn btn_black_normal" data-id="" data-type="close-subject">OK</button></h4>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
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
    
    $("a.delete").click(function(){
        var str_id = $(this).attr('id');
        var split_id = str_id.split("_");
        var subject_id = split_id[1];        
        $('button[data-type="close-subject"]').attr('data-id',subject_id); 
        $('#close_mate').modal('show');      
    });
    
    $(document).on('click','button[data-type="close-subject"]',function(e){
        var subject_id = $(this).attr('data-id');
        $.ajax({
               url:'<?php echo base_url()."admin/subject/delete_subject"; ?>',
               dataType: "JSON",
               type:'POST',
               data:{subject_id:subject_id},
               success:function(data){
                   $('#close_mate').modal('hide');      
                   $('#'+data.id).closest('tr').slideUp("slow", function() { $('#'+data.id).closest('tr')});  
                }
       });
    });

<?php if (!empty($_GET['q'])) { ?>
        $('#q').val('<?php echo $_GET["q"]; ?>');
<?php } ?>

</script>
