<!--feed box-->
<div class="col-md-7 main main2">
	<div class="all_feed">
	 <?php
	    if (isset($feed)) {
		$j = 1;
		$check_type = array(
			'image/png',
			'image/jpg',
			'image/jpeg',
			'image/gif'
			);


				foreach ($feed as $key => $value) {
				if (count($value['images']) > 0) {
					foreach ($value['images'] as $v) {
						if (in_array(mime_content_type('uploads/'.$v), $check_type)) {
							$value['feed_text'] .= '<a href="' . base_url() . 'uploads/' . $v . '" class="fancybox"><img src="uploads/' . $v . '" width="100" height="70"></a>';
						} else {
							$value['feed_text'] .= '<a href="' . base_url() . 'uploads/' . $v . '" target="_BLANK"><img src="assets/images/default_chat.png" width="100" height="70"></a>';
						}
						}
					/*$value['feed_text'] .= '<a class="fancybox" href="' . base_url() . 'uploads/' . $v . '" ><img src="uploads/' . $v . '" width="100" height="70"></a>';
					}*/
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
					    echo '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id="'. $t_value['id'] .'" class="label label_name">' . $t_value['full_name'] . '</label>';
					}
					if ($t_count == 2) {
					    if ($t_j == 0) {
						echo '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id="'. $t_value['id'] .'" class="label label_name">' . $t_value['full_name'] . '</label>';
					    } else {
						echo 'and <label style="cursor:pointer;" data-type="show-profile" data-id="'. $t_value['id'] .'" class="label label_name">' . $t_value['full_name'] . '</label>';
					    }
					    $t_j++;
					}
					if ($t_count > 2) {
					    if ($t_i == 0) {
						echo '&nbsp;tagged : <label style="cursor:pointer;" data-type="show-profile" data-id="'. $t_value['id'] .'" class="label label_name">' . $t_value['full_name'] . '</label>';
					    } else {
						$l = $t_count - 1;
						$other_name .="<label style='cursor:pointer;' data-type='show-profile' data-id='". $t_value['id'] ."' class='label label_name'>" . $t_value["full_name"] . "</label><br/>";
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
			    <span class="date noti_time just_now"><?php echo $value['posted_on'] ?></span>
				<script type="text/javascript">
					 $(".just_now").timestatus('<?php echo $value['posted_on'] ?>');
				</script>
				 <?php
				 $tagged_users = array();
				foreach ($value['tagged'] as $value1) {
					array_push($tagged_users, $value1['id']);
				}

				// $old_date = strtotime($value['posted_on']);
				// echo date("M j, Y", $old_date);
				?>
			    <div class="clearfix"></div>
			    <textarea id="edit_feed_post" type="text" data-feed="<?php echo $value['fid']; ?>" class="form-control post_input" style="display:none" placeholder="SAY IT"><?php echo $value['feed_text']; ?></textarea>
			    <div style="float:right;display:none;color:white; margin-top:5px;" id="save_edited_feed" data-id="<?php echo $value['fid']; ?>">
					<a style="color:white" href="javascript:void(0);" class="btn btn-xs btn_black_normal" data-type="cancel-edited-feed" data-id="<?php echo $value['fid']; ?>">Cancel</a>
				    <a style="color:white" href="javascript:void(0);" class="btn btn-xs btn_green" data-type="save-edited-feed" data-id="<?php echo $value['fid']; ?>">Save</a>
				</div>
				 <div class="clearfix"></div>
			    <p><?php echo nl2br($value['feed_text']); ?></p>
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
					<span>
							<?php
							if($value['tot_comment'] > 4)
							{
							 echo "4 of " . $value['tot_comment'];
							}else
							{
							 echo $value['tot_comment'];
							}
							 ?>
					</span>
				</a>
			    <?php if ($value['tot_comment'] > 4) { ?>
	    		    <a href="javascript:void(0);" class="comment_showall" data-type="showall" data-id="<?php echo $value['fid']; ?>">View All</a>
				<?php }else{ ?>
					<a href="javascript:void(0);" class="comment_showall" style="display:none" data-type="showall" data-id="<?php echo $value['fid']; ?>">View All</a>
				<?php
			}
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

				<?php 
					$arr_posted_on = explode(' ',trim($value['posted_on']));
					$is_editable = "";
					
				if($this->session->userdata('user')['id'] == $value['feed_by'] && preg_match("/[0-9]+/", $arr_posted_on[0]) || $this->session->userdata('user')['id'] == $value['feed_by'] && $arr_posted_on[0] == 'Just'){
						$is_editable = "yes";
						if (count($value['images']) > 0)
							{
								$is_editable = "";
							}
						}

				?>

			    <div class="dropdown tag_user user_<?php echo $value['fid']; ?>"  style="display: <?php echo $tag_show;?>;">
				<a href="javascript:void(0);" class="dropdown-toggle" data-type="tag-again" data-id="<?php echo $value['fid']; ?>" aria-haspopup="true" aria-expanded="true"><span data-toggle="tooltip" title="Tag mates" class="icon icon_user_2"></span><span class="caret"></span></a>
			    </div>
				<?php if($is_editable == "yes"){ ?>
			    <a href="javascript:void(0);" data-feed="<?php echo $value['fid']; ?>" class="edit_post_icon"><i title="Edit Post" class="fa fa-pencil-square-o"></i></a>
			    <?php } ?>

			</div>

			<!-- Edit post save optino -->
			

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
			    <span> &nbsp;</span>
			</div>


			<div class="clearfix"></div>


			<!--comment-->
			<div id="feed_comments" data-id="<?php echo $value['fid']; ?>">
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
		    			    <p><a class="noti_username"  style="cursor:pointer;" data-type="show-profile" data-id="<?php echo /*$value['feed_by'];*/ $com['comment_by']; ?>"><?php echo $com['full_name']; ?></a> <?php echo $com['comment']; ?></p>

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
</div>
<!--//feed box-->