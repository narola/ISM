<div class="col-sm-7 main main2 stydymates mscroll_custom">  
    <div class="col-sm-12">
        <div class="box general_cred">
            <div class="box_header">
                <h3>Studymate Requests</h3>
            </div>
            <div id="my_request_box" class="box_body studyamte_list studymate_request mscroll_custom">
		<?php
		if (isset($studymate_request) && sizeof($studymate_request) > 0) {
		    foreach ($studymate_request as $key => $value) {
			?>
			<div class="study_mate" id="my_request" data-id="<?php echo $value['id']; ?>">
			    <div class="col-lg-9 col-md-8 col-sm-7">
				<div class="mate_user_img">
				    <img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['id']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'" src="<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>">
				</div>
				<h4 style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['id']; ?>"><?php echo $value['full_name']; ?></h4>
				<p><?php echo $value['school_name']; ?></p>
				<p class="txt_green"><?php echo $value['course_name']; ?></p>
			    </div>
			    <div class="col-lg-3 col-md-4 col-sm-5" id="action-box">
				<button class="btn btn_green btn-block" data-subtype="accept-request" data-type="decline-request" data-id="<?php echo $value['id']; ?>">Confirm Request</button>
				<button class="btn btn_black_normal btn-block" data-subtype="decline-request" data-type="decline-request" data-id="<?php echo $value['id']; ?>">Decline Request</button>
			    </div>
			    <div class="clearfix"></div>
			</div>
			<?php
		    }
		} else {
		    ?>
    		<div class="study_mate"><center><label class="txt_grey txt_red">no more studymate request</label><center></div>
				<?php
			    }
			    ?>
			    </div>
			    </div>
			    </div>
			    <!--suggestion-->
			    <div class="col-sm-12">
				<div class="box general_cred margin_b30">
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
					<p class="txt_green"><?php echo $value['course_name']; ?></p>
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
						    echo '<label class="txt_grey txt_red">no studymate</label>';
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
			    </div>
			    <!--//suggestion-->
			    </div>
<!--			     Modal 
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