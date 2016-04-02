<!--feed box-->
<div class="col-md-7 main main2">
<div class="all_feed">
                <div class="box feeds" data-id="<?php echo $result_feed['fid']; ?>">
                    <div class="user_small_img">
                        <img style="cursor:pointer;" src="<?php echo UPLOAD_URL . '/' . $result_feed['profile_link']; ?>" data-type="show-profile" data-id="<?php echo $result_feed['feed_by']; ?>" onerror="this.src='<?php echo base_url() ?>assets/images/avatar.png'">
                    </div>
                    <div class="feed_text">
                        <h4><?php echo $result_feed['full_name']; ?></h4>
                        <span class="date noti_time just_now"><?php echo $result_feed['posted_on'] ?></span>
						<script type="text/javascript">
							 $(".just_now").timestatus('<?php echo $result_feed['posted_on'] ?>');
						</script>
                        <div class="clearfix"></div>
                        <p><?php echo $result_feed['feed_text']; ?></p>
                        <a href="#" class="like_btn"><span class="icon icon_thumb_0"></span>23</a>
                        <a href="#" class="comment_btn"><span class="icon icon_comment"></span>07</a>
                        <a href="#">View All</a>
                        <!-- <div class="dropdown tag_user" style="display: inline-block;">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span class="icon icon_user_2"></span><span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Emma Mall</a></li>
                                <li><a href="#">Gill Christ</a></li>
                                <li><a href="#">Adam Stranger</a></li>
                            </ul>
                        </div> -->
                    </div>
                    <div class="clearfix"></div>
                    <!--comment-->
					
					<div id="feed_comments" data-id="<?php echo $result_feed['fid']; ?>">
			    <?php
			    $total_comments = sizeof($comment);
			    if ($total_comments > 0) {
				$i = 1;
				foreach ($comment as $key => $com) {
				    
					if (($total_comments - $i) > 3) {
					    $first_three = '';
					    $display = 'none';
					} else {
					    $first_three = 'true';
					    $display = '';
					}
					?>

		    		    <div class="comment" data-first="<?php echo $first_three; ?>" data-id="<?php echo $result_feed['fid']; ?>" style="display:<?php echo $display; ?>">
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
			    ?>
			</div>


            <div class="write_comment box_body">
            	<input type="text" class="form-control" placeholder="Write Your Comment Here">                        
                <a class="icon icon_image"></a>
                <input type="file">
            </div> 

                </div>
                </div>
                </div>
                <!--//feed box-->