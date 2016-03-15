<script>
	function showall(id) {
	$('.post' + id).show();
    }
    $(document).on('click', 'a[data-type="showall"]', function() {
	if ($(this).html() == 'Hide')
	{
	    $('#feed_comments div[data-id="' + $(this).data('id') + '"]').hide();
	    $('#feed_comments div[data-first="true"]').show();
	    $(this).html('View All');

	} else {
	    $('#feed_comments div[data-id="' + $(this).data('id') + '"]').show();
	    $(this).html('Hide');
	}
    });
    $(document).ready(function() {
	$('#selection-box').css("display", "none");
    });
    
    $(document).on('click', '#show-tag-user', function() {
	val = $('#tag_or_not').val();
	if (val == 'yes') {
	    $('#selection-box').css("display", "none");
	    $('#tag_or_not').val('no');
	}
	else {
	    $('#selection-box').css("display", "block");
	    $('#tag_or_not').val('yes');
	}

    });

    $(document).on('click', 'a[data-type="tag-again"]', function(e) {
	show = $('#all_feed .box div[data-id="' + $(this).data('id') + '"]').is(":visible");

	if (show)
	    $('#all_feed .box div[data-id="' + $(this).data('id') + '"]').hide();
	else
	    $('#all_feed .box div[data-id="' + $(this).data('id') + '"]').show();
    });
    
    $('#element').popover('show');


    $(document).on('click', '[data-toggle="popover1"]', function() {
	$('[data-toggle="popover1"]').popover('show');
    });

    $(document).on('click', '[data-toggle="popover2"]', function() {
    	var d_id = $(this).data("id");
	$('[data-id="'+d_id+'"]').popover('show');
    });

    $(document).ready(function() {
	$('[data-toggle="popover"]').popover();
    });

</script>
<!--main-->
<div class="col-sm-7 main mscroll_custom">
    <div class="col-sm-12">
	<div class="box">
	    <textarea id="feed_post" type="text" class="form-control post_input" placeholder="SAY IT"></textarea>
	    <div class="upload_loader"></div>
	    <!-- <a href="#" class="icon icon_emoji"></a> -->
	    <div class="form-group" id="tag-box">
		<div id="tagged-users" style="margin-bottom:10px;">
		</div>
		<input type="hidden" id="tagged-users-id">
		<input type="hidden" id="tag_or_not" value="">
		<div id="selection-box">
		    <select name="all_users[]" id="select-tag-user" class="js-example-basic-single form-control" multiple="multiple">
			<?php
			if (!empty($my_studymates)) {
			    foreach ($my_studymates as $list) {
				?>
				<option value="<?php echo $list['id'] ?>">
				    <?php echo ucfirst($list['full_name']); ?>
				</option> 
			    <?php
			    }
			}
			?>
		    </select>
		</div>
	    </div>
	    <div class="box_header">
		<a href="javascript:void(0);" class="icon icon_pin">
		    <input title="Attach file" id="feed_file_share" data-type="feed_file_share" type="file" data-id="feed">
		</a>
		<div class="dropdown" style="display: inline-block;">
		    <a data-toggle="tooltip" title="Tag mates" href="javascript:void(0);" id="show-tag-user" class="dropdown-toggle icon icon_user" aria-haspopup="true" aria-expanded="true"><span class="caret"></span></a>
		</div>
		<button data-type="post" class="btn btn_post">Post<span class="fa fa-chevron-right"></span></button>
	    </div>
	</div>

	<!--feed box-->
	<div id="all_feed">
	    <?php
	    if (isset($feed)) {
		$j = 1;
		foreach ($feed as $key => $value) {
		    if (count($value['images']) > 0) {
			foreach ($value['images'] as $v) {
			    $value['feed_text'] .= '<a href="' . base_url() . 'uploads/' . $v . '"  target="_BLANK"><img src="uploads/' . $v . '" width="100" height="70"></a>';
			}
		    }
		    ?>
		    <div class="box feeds" data-id="<?php echo $value['fid']; ?>">
			<div class="user_small_img">
			    <img style="cursor:pointer;" src="<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>" data-type="show-profile" data-id="<?php echo $value['feed_by']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
			</div>
			<div class="feed_text">
			    <h4 style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['feed_by']; ?>"><?php echo $value['full_name']; ?></h4>
			    <span data-id="<?php echo $value['fid']; ?>">
				<?php
				if (isset($value['tagged']) && sizeof($value['tagged']) > 0) {
				    $t_j = 0;
				    $k = 0;
				    $t_i = 0;
				    $other_name = '';
				    $t_count = count($value['tagged']);
				    foreach ($value['tagged'] as $t_key => $t_value) {
					if ($t_count == 1) {
					    echo '&nbsp;tagged : <label class="label label_name">' . $t_value['full_name'] . '</label>';
					}
					if ($t_count == 2) {
					    if ($t_j == 0) {
						echo '&nbsp;tagged : <label class="label label_name">' . $t_value['full_name'] . '</label>';
					    } else {
						echo 'and <label class="label label_name">' . $t_value['full_name'] . '</label>';
					    }
					    $t_j++;
					}
					if ($t_count > 2) {
					    if ($t_i == 0) {
						echo '&nbsp;tagged : <label class="label label_name">' . $t_value['full_name'] . '</label>';
					    } else {
						$l = $t_count - 1;
						$other_name .= $t_value['full_name'] . '<br>';
						if ($k == $l) {
						    echo 'and <label class="label label_name"><a href="javascript:void(0);" data-html="true" data-placement="bottom" data-trigger="focus" data-toggle="popover" title="Other Tagged" data-content="' . $other_name . '" >' . $l . ' more</a></label>';
						}
					    }
					    $k++;
					    $t_i++;
					}
				    }
				}
				?>
			    </span>
			    <span class="date">
			    	<script type="text/javascript">
			    		var dd = date_to_day('<?php echo $value['posted_on'] ?>');
			    		document.write(dd);
			    	</script>
			 <?php
			 	
				 $tagged_users = array();
				foreach ($value['tagged'] as $value1) {
					array_push($tagged_users, $value1['id']);
				}

				// $old_date = strtotime($value['posted_on']);
				// echo date("M j, Y", $old_date);
				?></span>
			    <div class="clearfix"></div>
			    <p><?php echo $value['feed_text']; ?></p>
			    <a href="javascript:void(0);" data-id="<?php echo $value['fid']; ?>" data-type="feed-like" class="like_btn">
				<?php
				if ($value['my_like'] == 1 || $value['my_like'] == '') {
				    ?>
	    			<span data-toggle="tooltip" title="Like" class="icon icon_thumb_0"></span>
				    <?php
				} else {
				    ?>
	    			<span data-toggle="tooltip" title="Unlike" class="icon icon_thumb"></span>
				    <?php
				}
				?>    
				<span><?php echo $value['tot_like']; ?></span></a>
			    <a href="javascript:void(0);" class="comment_btn" data-id="<?php echo $value['fid']; ?>"><span data-toggle="tooltip" title="Comment" class="icon icon_comment"></span>
				<span><?php echo $value['tot_comment']; ?></span></a>
			    <?php if ($value['tot_comment'] > 3) { ?>
	    		    <a href="javascript:void(0);" data-type="showall" data-id="<?php echo $value['fid']; ?>">View All</a>
		<?php } ?>

				<?php
				/* Remove tag option after complete tagging */
				$tot_count = 0;
				if (!empty($my_studymates)) {
				    foreach ($my_studymates as $list) {
						if(!in_array($list['id'], $tagged_users)){
							$tot_count = $tot_count + 1;
						}
				    }
				}
				?>

				<?php 
				if($tot_count == 0){
				  	$tag_show = "none";
				  }else{
				  	$tag_show = "inline-block";
				  }	
				?>


			    <div class="dropdown tag_user user_<?php echo $value['fid']; ?>"  style="display: <?php echo $tag_show;?>;">
				<a href="javascript:void(0);" class="dropdown-toggle" data-type="tag-again" data-id="<?php echo $value['fid']; ?>" aria-haspopup="true" aria-expanded="true"><span data-toggle="tooltip" title="Tag mates" class="icon icon_user_2"></span><span class="caret"></span></a>
			    </div>
			</div>
			<div style="float:right;display:none;" id="show-again" data-id="<?php echo $value['fid']; ?>">
			    <select style="width:200px;"name="all_users_again[]" id="select-tag-user-again" data-id="<?php echo $value['fid']; ?>" class="js-example-basic-single form-control" multiple="multiple">
				<?php
				if (!empty($my_studymates)) {
				    foreach ($my_studymates as $list) {
						if(!in_array($list['id'], $tagged_users)){ ?>
						<option value="<?php echo $list['id'] ?>">
							<?php echo ucfirst($list['full_name']); 
							?>
						</option> 
			    		
			    		<?php }
				    }
				}
				?>
			    </select>
			    <a href="javascript:void(0);" class="btn btn_black_normal" data-type="tag-user-again" data-id="<?php echo $value['fid']; ?>">Tag New</a>
			</div>
			<div class="clearfix"></div>


			<!--comment-->
			<div id="feed_comments">
			    <?php
			    $total_comments = sizeof($value['comment']);
			    if ($total_comments > 0 && isset($value['comment'])) {
				$i = 1;
				foreach ($value['comment'] as $key => $com) {
				    if ($value['fid'] == $com['feed_id']) {
					if (($total_comments - $i) > 3) {
					    $first_three = '';
					    $display = 'none';
					} else {
					    $first_three = 'true';
					    $display = '';
					}
					?>

		    		    <div class="comment" data-first="<?php echo $first_three; ?>" data-id="<?php echo $value['fid']; ?>" style="display:<?php echo $display; ?>">
		    			<div class="user_small_img user_comment">
		    			    <img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo /*$value['feed_by'];*/ $com['comment_by']; ?>" src="<?php echo UPLOAD_URL . '/' . $com['profile_link']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
		    			</div>
		    			<div class="notification_txt">

		    			    <p style="cursor:pointer;" data-type="show-profile" data-id="<?php echo /*$value['feed_by'];*/ $com['comment_by']; ?>"><a class="noti_username"><?php echo $com['full_name']; ?></a> <?php echo $com['comment']; ?></p>
		    			    <span class="noti_time"><?php echo get_time_format($com['created_date']); ?></span>                            
		    			</div>
		    			<div class="clearfix"></div>
		    		    </div>

					<?php
					$i++;
				    }
				}
			    }
			    ?>
			</div>
			<div class="write_comment box_body">
			    <input type="text" class="form-control" placeholder="Write Your Comment Here" data-type="feed_comment" data-id="<?php echo $value['fid']; ?>">                  

			</div>
		    </div>
		    <?php
		    $j++;
		}
	    }
	    ?>
	</div>

	<!--//feed box-->
	<?php if (isset($feed) && count($feed) >= 4) { ?>
    	<div class="box text-center load_more mate_descrip">
    	    <button href="javascript:void(0);" data-type="load_more" data-start="4" class="btn btn_green no-margin">Load More</button>
    	</div>
<?php } ?>
    </div>
</div>
<!--//main-->

<!--side right-->
<div class="sidebar_right_container mscroll_custom">
    <!--notice board-->
    <div id="carousel-noticeboard" class="carousel slide" data-ride="carousel">
	<!-- Indicators -->
	<ol class="carousel-indicators">
	    <?php
	    if (isset($my_latest_notice) && sizeof($my_latest_notice) > 0) {
		if (count($my_latest_notice) == 1)
		    echo '<li data-target="#carousel-noticeboard" data-slide-to="0" class="active"></li>';
		else if (count($my_latest_notice) == 2) {
		    echo '<li data-target="#carousel-noticeboard" data-slide-to="0" class="active"></li>';
		    echo '<li data-target="#carousel-noticeboard" data-slide-to="1"></li>';
		} else {
		    echo '<li data-target="#carousel-noticeboard" data-slide-to="0" class="active"></li>';
		    echo '<li data-target="#carousel-noticeboard" data-slide-to="1"></li>';
		    echo '<li data-target="#carousel-noticeboard" data-slide-to="2"></li>';
		}
	    }
	    ?>
	</ol>
	<!-- Wrapper for slides -->
	<div class="carousel-inner" role="listbox">
	    <?php
	    if (isset($my_latest_notice) && sizeof($my_latest_notice) > 0) {
		$i = 0;
		foreach ($my_latest_notice as $key => $value) {
		    if ($i == 0) {
			echo '<div class="item active">';
		    } else {
			echo '<div class="item">';
		    }
		    ?>
		    <img src="<?php echo base_url(); ?>assets/images/blackboard.jpg" alt="blackboard">
		    <div class="carousel-caption mscroll_custom">
			<p class="noti_username" style="font-size:large;"><?php echo $value['notice_title']; ?></p>
			<p style="txt_white"><?php echo $value['notice']; ?><br><span style="float:right">-ISM Admin</span></p>
		    </div>
		</div>
		<?php
		$i++;
	    }
	} else {
	    ?>
    	<div class="item active">
    	    <img src="<?php echo base_url(); ?>assets/images/blackboard.jpg" alt="blackboard">
    	    <div class="carousel-caption">
    		<p class="noti_username">WELCOME TO ISM</p>
    		<p class="txt_grey">- ISM Admin.</p>
    	    </div>
    	</div>
    <?php } ?>
    </div>
    <?php
    if (isset($my_latest_notice) && sizeof($my_latest_notice) > 0) {
	if (count($my_latest_notice) > 1) {
	    ?>
	    <!-- Controls -->
	    <a class="left carousel-control" href="#carousel-noticeboard" role="button" data-slide="prev">
		<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
		<span class="sr-only">Previous</span>
	    </a>
	    <a class="right carousel-control" href="#carousel-noticeboard" role="button" data-slide="next">
		<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
		<span class="sr-only">Next</span>
	    </a>
	    <?php
	}
    }
    ?>
</div>
<!--//notice board-->



<!--Suggested Studymates-->
<div class="suggested_mates box">
    <div class="box_header">
	<h3>Suggested Studymates</h3>
    </div>
    <div class="suggested_mates_holder text-center">
	<div id="carousel-studymate" class="carousel slide" data-ride="carousel">
<?php if (isset($suggested_studymates) && sizeof($suggested_studymates) > 0) { ?>

    	    <!-- Indicators -->
    	    <ol class="carousel-indicators">
    		<li data-target="#carousel-studymate" data-slide-to="0" class="active"></li>
    		<li data-target="#carousel-studymate" data-slide-to="1"></li>
    		<li data-target="#carousel-studymate" data-slide-to="2"></li>
    		<!-- <li data-target="#carousel-studymate" data-slide-to="3"></li> -->
    	    </ol>

    	    <!-- Wrapper for slides -->
    	    <div class="carousel-inner" role="listbox">

		    <?php
		    $i = 1;
		    foreach ($suggested_studymates as $key => $value) {
			if ($i == 1)
			    $class = ' active';
			else
			    $class = '';
			?>
			<div class="item<?php echo $class; ?>">
			    <!--card-->
			    <div class="suggested_mates_card">
				<div class="mate_user_img">
				    <img style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>" src="<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
				</div>
				<div class="mate_descrip">
				    <p class="mate_name" style="cursor:pointer;" data-type="show-profile" data-id="<?php echo $value['user_id']; ?>"><?php echo $value['full_name']; ?></p>
				    <p><?php echo $value['school_name']; ?></p>
				    <p class="txt_green"><?php echo $value['course_name']; ?></p>
				    <?php if ($value['srid'] != '' && $value['is_delete'] == 0) { ?>
	    			    <button class="btn btn_black_normal" data-type="studyment-request" data-id="<?php echo $value['user_id']; ?>" disabled>Request Already Sent</button>
	<?php } else { ?>
	    			    <button class="btn btn_green" data-type="studyment-request" data-id="<?php echo $value['user_id']; ?>">Add Studymate</button>
	<?php } ?>
				</div>
			    </div>
			    <!--//card-->
			</div>
			<?php
			$i++;
		    }
		    ?>
    	    </div>

    	    <!-- Controls -->
    	    <a class="left carousel-control" href="#carousel-studymate" role="button" data-slide="prev">
    		<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    		<span class="sr-only">Previous</span>
    	    </a>
    	    <a class="right carousel-control" href="#carousel-studymate" role="button" data-slide="next">
    		<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    		<span class="sr-only">Next</span>
    	    </a>
		<?php
	    } else {
		echo '<label class="txt_grey txt_red">No more studymates</label>';
	    }
	    ?>
	</div>                        

	<form action="student/studymates" method="post" id='studymates_form'>
		<input type="hidden" name="find_studymates" value="1" />
		<button type="submit" class="btn btn_blue">Find More studymates</button>
	</form>
    </div>
</div>
<!--//suggested Studymates-->
<!--STM-->
<div class="stm home">
    <div class="box_header">
	<h3>Studymates <span> STM</span></h3>
    </div>
    <div class="stm_list mscroll_custom">
	<?php
	if (isset($my_studymates) > 0) {
	    foreach ($classmates as $key => $value) {
		$u = 'offline';
		if (in_array($value['id'], $online)) {
		    $u = 'online';
		}
		?>
		<div class="stm_item <?php echo $u; ?>" data-id="<?php echo $value['id']; ?>">
		    <a id="mate_list" href="javascript:void(0);" data-id="<?php echo $value['id']; ?>">

			<div class="stm_user_img">
			    <img src="/<?php echo UPLOAD_URL . '/' . $value['profile_link']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
			</div>
			<span class="badge message_badge"><?php if ($value['unread_msg'] > 0) echo $value['unread_msg']; ?></span>
			<p><?php echo $value['full_name']; ?></p>
		    </a>
		    <div class="clearfix"></div>
		</div>
		<?php
	    }
	}
	?>
    </div>       
    <div class="text-center">
	<a class="btn_find_studymates btn btn_blue" href="student/studymates">Find more Studymates</a>
    </div>            
</div>
<!--//STM-->
<div class="clearfix"></div>
<!--high score board-->
<div class="score box">
    <div class="box_header text-center">
	<h3>Distinction Wall</h3>
    </div>
    <div class="score_list mscroll_custom">
    <?php
    $high = get_highscore($this->session->userdata('user')['classroom_id']);

    if (!empty($high)) {
	foreach ($high as $k => $vx) {
	    ?>
	    <div class="score_div">
		<h5><?php echo $k; ?></h5>
	<?php foreach ($vx as $key => $v) { ?>
	    	<div class="score_item">
	    	    <div class="score_img" data-id="<?php echo $v['id']; ?>" data-type="show-profile" style="cursor:pointer;">
	    		<img src="<?php echo UPLOAD_URL . '/' . $v['profile_link']; ?>">
	    	    </div>
	    	    <div class="score_descrip" data-id="<?php echo $v['id']; ?>" data-type="show-profile" style="cursor:pointer;">
	    		<p class="score_name"><?php echo $v['full_name']; ?></p>
	    		<p><?php echo $v['school_name']; ?></p>
	    	    </div>
	    	    <div class="score_points">
	    		<p>Score</p>
	    		<p class="score_number"><?php echo $v['total_marks']; ?></p>
	    	    </div>
	    	    <div class="clearfix"></div>
	    	</div>
	    <?php } ?>
	    </div>
	    <?php
	}

    }
    ?>
</div>
    <div class="clearfix"></div>
    <div class="box_header text-center"></div>
</div>
<!--//high score board-->
</div>
<!-- Modal 
<div class="modal fade" id="view_profile_model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:600px;margin-top:120px;">
        <div class="modal-content">
            <div class="modal-header notice_header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">STUDYMATE PROFILE</h4>
                <small><?php //echo date("d F Y", strtotime(date('Y-m-d')));  ?></small>
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
<!--//side right-->
<script type="text/javascript">
    $(document).ready(function() {
	$(".js-example-basic-single").select2();
    });

</script>

