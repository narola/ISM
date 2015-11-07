<!--main-->
<div class="col-sm-7 main main2 mscroll_custom">
    <!--filter-->
    <div class="filter group_filter small_group">
        <div class="col-sm-12">
            <form action="" method="post" id="frm_notice_board">
                <div class="form-group">
                    <select class="form-control" name="sort_by" id="sort_by">
                        <option value="">Sort By</option>
                        <option value="date">Date</option>
                        <option value="title">Title</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control" name="sort_type" id="sort_type" onchange="search_noticeboard();">
                        <option value="">Order By</option>
                        <option value="ascending">Ascending</option>
                        <option value="descending">Descending</option>
                    </select>
                </div>
                
                <div class="form-group no_effect search_input">
                    <input class="form-control" type="text" placeholder="Search" name="txt_search" id="txt_search" value="<?php echo $txt_search;?>">
                    <a href="javascript:void(0);" class="fa fa-search" onclick="search_noticeboard();"></a>
                </div>
            </form>
        </div>
        <script> 
            sort_by = '<?php echo $sort_by;?>';
            document.getElementById('sort_by').value = sort_by;
            sort_type = '<?php echo $sort_type;?>';
            document.getElementById('sort_type').value = sort_type;
        </script>    

    </div>
    <!--//filter-->
    <!--topics-->
    <div class="">
        <div class="col-sm-12 notice_container">
            <?php 
                if(sizeof($notice_list)>0){
                    foreach ($notice_list as $key => $value) {
            ?>
            <div class="box">
                <div class="notice_content">
                    <div class="col-md-12">
                        <h3><?php echo $value['notice_title'];?><span><?php $old_date = strtotime($value['created_date']);echo date("M j, Y",$old_date);?></span></h3>
                    </div>
                    <div class="col-sm-12 notice_description">
                        <p><?php echo $value['notice'];?></p>
                    </div>
                    <div class="col-sm-12">
                        <a href="javascript:void(0);" id="expand_notice" class="fa fa-angle-double-down"></a>                            
                    </div>
                </div>                            
                <div class="clearfix"></div>
            </div>

            <?php
                    }
                }
                else{
            ?>
          <div class="box">
                <div class="notice_content">
                    <div class="col-md-12">
                        <label class="txt_grey txt_red">No records found...</label>
                    </div>
                </div>                            
                <div class="clearfix"></div>
            </div>
            <?php
                }
            ?>
            
            <nav class="text-center">
                <?php echo $pagination;?>
            </nav>
        </div>
    <!--//topics-->    
    </div>
</div>
<!--//main-->
<script type="text/javascript">
        $(document).ready(function() {
            $('.notice_content #expand_notice').click(function(){
                if($(this).hasClass('fa-angle-double-down')){
                    var ph = $(this).parent().parent('.notice_content').children('.notice_description').children().height();
                    /*$(this).parent().parent('.notice_content').children('.notice_description').css('height','auto');*/
                    $(this).parent().parent('.notice_content').children('.notice_description').animate( 
                        { height: ph}, 
                        { queue:false, duration:300 });
                    $(this).removeClass('fa-angle-double-down');
                    $(this).addClass('fa-angle-double-up');
                }
                else{
                    /*$(this).parent().parent('.notice_content').children('.notice_description').css('height','80px');*/
                    $(this).parent().parent('.notice_content').children('.notice_description').animate( 
                        { height: "64px"}, 
                        { queue:false, duration:300 });
                    $(this).removeClass('fa-angle-double-up');
                    $(this).addClass('fa-angle-double-down');
                }
            });
        });
    </script>
