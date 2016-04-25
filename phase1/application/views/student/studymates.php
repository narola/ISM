<script>
    $(document).on('click', 'li[data-type="search-type"]', function() {
		$('.row.filter_bar ul li').removeClass('active');
		$(this).addClass('active');
		var tab = $(this).data('id');
		if(tab == 'people'){
			$("div.search_bar input").attr("placeholder", "Type here to search your studymate by name");
		}else if(tab=='school'){
			$("div.search_bar input").attr("placeholder", "Type here to search your studymate by school");
		}else if(tab == 'course'){
			$("div.search_bar input").attr("placeholder", "Type here to search your studymate by course");
		}
    });


    $(document).ready(function() {
	$('#mCSB_3_scrollbar_vertical').css("margin-top", "10%");
    });
    
</script>
<!--main-->
<div class="col-sm-7 main main2 stydymates mscroll_custom"> 
    <!--tabs-->
    <div class="tabs_studymate">
        <button class="btn col-sm-6 no-padding <?php echo ($tab=='my_studymates') ? 'active' : ''; ?>" id="ms">My Studymates</button>
        <button class="btn col-sm-6 no-padding <?php echo ($tab=='find_studymates') ? 'active' : ''; ?>" id="fs">Find Studymates</button>
    </div>
    <!--//tabs-->
    <!--my studymates-->
    <div class="my_studymates">
        <div class="box general_cred">
	    <div class="box_body studyamte_list mscroll_custom">
		<?php
		if (isset($my_studymates)) {
		    foreach ($my_studymates as $key => $value) {
			?>
			<!--item1-->
			<div class="study_mate of" data-id="<?php echo $value['user_id']; ?>">
			    <div class="col-lg-9 col-md-8 col-sm-7">
				<div class="mate_user_img">
				    <img onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>">
				</div>
				<h4 class="no_hover1"><?php echo $value['full_name']; ?></h4>
				<p>Student from <?php echo $value['school_name']; ?></p>
				<p class="txt_green no_hover1"><?php echo $value['course_name']; ?></p>
			    </div>
			    <div class="col-lg-3 col-md-4 col-sm-5">
				<button class="btn btn_green btn-block" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>">View Profile</button>
				<div class="form-group select">
				    <select class="form-control" name="action" id="action_studymate" data-name="<?php echo $value['full_name']; ?>" data-id="<?php echo $value['user_id']; ?>" data-school="<?php echo $value['full_name']; ?>" data-profile="<?php echo $value['profile_link']; ?>" data-course="<?php echo $value['course_name']; ?>">
					<option value="0">Studymate</option>
					<option value="1">Remove Studymate</option>
				    </select>
				</div>
			    </div>
			    <div class="clearfix"></div>
			</div>
			<!--//item1-->
			<?php
		    }
		} else {
		    ?>
    		<div class="study_mate">
    		    <center><label class="txt_grey txt_red">no studymate found...</label></center>
    		</div>
		    <?php
		}
		?>
	    </div>
        </div>
    </div>
    <!--//my studymates-->
    <!--search-->
    <div class="search_studymate">
        <!--filterbar-->
        <div class="row filter_bar">
            <ul>
                <li class="active" data-type="search-type" data-id="people"><a href="javascript:void(0);">Students</a></li>
                <li data-type="search-type" data-id="school"><a href="javascript:void(0);">School</a></li>
                <!-- <li data-type="search-type" data-id="area"><a href="javascript:void(0);">Area</a></li> -->
                <li data-type="search-type" data-id="course"><a href="javascript:void(0);">Course</a></li>
            </ul>
        </div>
        <!--//filterbar-->
        <!--search bar-->
        <div class="row search_bar">
            <input type="text" class="form-control" placeholder="Type here to search your studymate by name" data-type="study_mate_search">
            <span class="icon icon_search" style="float:right;"></span>
        </div>
        <!--//search bar-->
        <!--search result-->
        <h5 class="search_result_label">Search Result for</h5>
        <div class="box general_cred" data-type="search_result">
            <div class="box_body studyamte_list studymate_request mscroll_custom" id="search_result" >
		<?php
		if (isset($find_studymates) && sizeof($find_studymates) > 0) {
		    $i = 1;
		    foreach ($find_studymates as $key => $value) {
			?>
	                <!--item1-->
	                <div class="study_mate">
	                    <div class="col-lg-9 col-md-8 col-sm-7">
	                        <div class="mate_user_img">
	                            <img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>">
	                        </div>
	                        <h4 style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>"><?php echo $value['full_name']; ?></h4>
	                        <p><?php echo $value['school_name']; ?></p>
	                        <p class="txt_green no_hover1"><?php echo $value['course_name']; ?></p>
	                    </div>
	                    <div class="col-lg-3 col-md-4 col-sm-5">
				<?php if ($value['srid'] != '') { ?>
	                            <button class="btn btn_black_normal btn-block" data-type="studyment-request" data-id="<?php echo $value['user_id']; ?>" disabled>Request Already Sent</button>
				<?php } else { ?>
	                            <button class="btn btn_green btn-block" data-type="studyment-request" data-id="<?php echo $value['user_id']; ?>">Add Studymate</button>
				<?php } ?>
	                    </div>
	                    <div class="clearfix"></div>
	                </div>
	                <!--//item1-->
			<?php
		    }
		    ?>
                    <div class="text-center">
                        <a href="javascript:void(0);" data-start="4" data-type="load-studymate-more"  style="color:white;" class="btn btn-xs btn_green no-margin">View More</a>
                    </div>
		    <?php
		}
		?>

            </div>
        </div>
        <!--//search result-->

    </div>
    <!--//search-->
    <!--suggestion-->
    <div class="box general_cred margin_15">
        <div class="box_header">
            <h3>Recommended Studymates</h3>
           
        </div>
        <div class="box_body studymate_recom text-center">
            <!--carousel-->
            <div id="carousel-studymate" class="carousel slide" data-ride="carousel">
                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
		    <?php
		    if (isset($recommended_studymates) && sizeof($recommended_studymates) > 0) {
			?>
			    <?php
			    $i = 1;
			    $cnt = sizeof($recommended_studymates);
			    
			    foreach ($recommended_studymates as $key => $value) {
			    	if($i==1){
    		    echo '<div class="item active" id="active-recomonded">';
    		}
				
				?>
				<!--card-->
				<div class="suggested_mates_card">
				    <div class="mate_user_img">
					<img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>">
				    </div>
				    <div class="mate_descrip">
					<p class="mate_name" style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>"><?php echo $value['full_name']; ?></p>
					<p>
	<?php
	if (strlen($value['school_name']) > 30)
	    echo substr($value['school_name'], 0, 29) . '.....';
	else
	    echo $value['school_name'];
	?> 
					</p>
					<p class="no_hover"><?php echo $value['course_name']; ?></p>
	<?php if ($value['srid'] != '') { ?>
	    				<button class="btn btn_black_normal" data-type="studyment-request" data-id="<?php echo $value['user_id']; ?>" disabled>Request Already Sent</button>
					<?php } else { ?>
	    				<button class="btn btn_green" data-type="studyment-request" data-id="<?php echo $value['user_id']; ?>">Add Studymate</button>
					<?php } ?>

				    </div>
				</div>
				<!--//card-->
	<?php
	if ($i!=1 && $i % 4 == 0 && $i!=$cnt) {
				    echo '</div><div class="item">';
				    //$i = 1;
				}
	if($i==$cnt){
    		    echo '</div>';
    		}
	$i++;
    }
    ?>
			    <?php
			} else {
			    echo '<label class="txt_grey txt_red">No studymate found</label>';
			}
			?>            
                </div>
		    <?php
		    if (isset($recommended_studymates) && sizeof($recommended_studymates) > 0) {
			?>
    		<!-- Controls -->
    		<a class="left carousel-control" href="#carousel-studymate" role="button" data-slide="prev">
    		    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    		    <span class="sr-only">Previous</span>
    		</a>
    		<a class="right carousel-control" href="#carousel-studymate" role="button" data-slide="next">
    		    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    		    <span class="sr-only">Next</span>
    		</a>
    		<!-- //Controls -->
    <?php
}
?>
            </div>       
            <!--//carousel-->
        </div>
    </div>
    <!--//suggestion-->
</div>
<!--//main-->
<!-- Modal -->
<div class="modal fade" id="close_mate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:600px;margin-top:220px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">CONFIRMATION FORM</h4>
                <small><?php echo date("d F Y", strtotime(date('Y-m-d'))); ?></small>
            </div>
            <div class="modal-body">
                <code style="font-size:large;">Are sure for want to remove <b data-type="close-studymate-name" data-id="remove-name"></b> from studymates list?</code>
		<h4 class="notice_by"><button class="btn btn_black_normal" data-type="close-studymate">OK</button></h4>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</div>
<!-- /.modal -->
<!-- Modal 
<div class="modal fade" id="view_profile_model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:600px;margin-top:120px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">STUDYMATE PROFILE</h4>
                <small><?php //echo date("d F Y", strtotime(date('Y-m-d'))); ?></small>
            </div>
            <div class="modal-body">
                <div data-type="profile_pic" class="avatar1">
                </div>
                <div class="basic_info">
                    <h3 data-type="user-name" class="txt_green text-uppercase"></h3>
                    <p data-type="email"></p>
                    <p data-type="course-name"></p>
		    <p data-type="school"></p>
                    <p data-type="birth"></p>
		    <button class="btn btn_green pull-right">Add Studymate</button>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>
</div>
 /.modal -->
<script type="text/javascript">
    $(document).ready(function() {
	$('#ms').click(function() {
	    $('.my_studymates').css('display', 'block');
	    $('.search_studymate').css('display', 'none');
	    $(this).addClass('active');
	    $('#fs').removeClass('active');
	});
	$('#fs').click(function() {
	    $('.my_studymates').css('display', 'none');
	    $('.search_studymate').css('display', 'block');
	    $(this).addClass('active');
	    $('#ms').removeClass('active');
	});
	var active_tab = $(".tabs_studymate .active").attr("id");
	$("#"+active_tab).click();


    });
</script>