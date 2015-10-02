<div class="col-sm-7 main main_tut">
    <?php 	if(isset($topic) && !empty($topic)){ ?>
                <div class="row discussion_header">
                	<div class="col-sm-12">
                        <h3><span>Topic - </span><?php echo $topic['topic_name']; ?></h3>
                        <a href="#" class="icon icon_expand"></a>
                    </div>
                </div>
                <div class="row top_question">
                	<div class="col-sm-12">
                    	<div class="mate_user_img">
                        	<img src="/<?php echo UPLOAD_URL.'/'.$topic['profile_link']; ?>">
                        </div>
                        <div class="admin_question">
                        	<h4><?php echo $topic['full_name']; ?><span><?php echo date_format( date_create($topic['created_date']), 'M d Y g:i a'); ?></span></h4>
                        	<p><?php echo $topic['topic_description']; ?></p>
                        </div>
                    </div>
                </div>
                <div class="row discussion">
                <?php 
                if(isset($discussion) && count($discussion) > 0){
                    $i = 0;
                    $week = null;
                    foreach($discussion as $k => $v){
                       $clss_me = '';
                       if($v['sender_id'] == $user_id)
                        $clss_me = 'me';
                      // if($v['week_day'] <= 2){
                            if($week !== $v['week_day']){
                                $week = $v['week_day'];
                            ?>
                            <div class="divide_discussion col-sm-12" id="<?php echo $weekday[$v['week_day']-1]; ?>">                    
                                <div class="clearfix"></div>                    
                                <hr><h4><?php echo $weekday[$v['week_day']-1]; ?></h4>
                            </div>
                            <?php
                            }
                            ?>
                            <div class="col-sm-12 <?php echo $clss_me; ?>" data-id="<?php echo $v['id']; ?>">
                                <div class="mate_user_img">
                                <img src="/<?php echo UPLOAD_URL.'/'.$v['profile_link']; ?>">
                                </div>
                                <div class="admin_question">
                                    <h4><?php echo $v['full_name']; ?><span><?php echo date_format( date_create($v['created_date']), 'M d Y g:i a'); ?></span></h4>
                                    <p><?php echo $v['message']; ?></p>
                                </div>
                            </div>
                            <?php
                          //  }
                        $i++;
                    }
                }
                ?> 
                </div>
                <?php //if($current_weekday <= 3){ ?>           
                <div class="row">
               		<!--input-->
                    <div class="col-sm-12 input">
                    	<?php if($time == 0){ ?>
                        <div class="alert alert-danger alert-dismissible" role="alert">
                          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                          Active hours are finished!
                        </div>
                        <?php } ?>
                    	<div class="option_bar" data-type="discussion-submit">
                        	<a href="#" class="icon icon_pin"></a>
                        	<a href="#" class="icon icon_image"></a>
                            <a href="#" class="icon icon_mic"></a>
                            <a href="#" class="icon icon_link"></a>
                        </div>
                    	<textarea placeholder="SAY IT" data-type="discussion"></textarea>
                        <a href="#" class="icon icon_emoji"></a>
                                             
                    </div>
                    <!--//input-->
                </div>
                <?php 
              //  }
                 }else{ 
?>
<h1>No topic allocated for this week!</h1>
<?php
                } ?>  
            </div>


            <div class="toolkit">
                <div class="row toolkit_header">
                    <div class="col-sm-12">
                        <h3><span>My Toolkit</span></h3>
                    </div>
                </div>
                <!--calc-->
                <div class="calculator row mCustomScrollbar" data-mcs-theme="minimal-dark" id="accordian_calc">
                    <div class="col-sm-12 textarea">
                        <span>23 + 12000 /1000 -</span>
                        <h1>2500</h1>
                    </div>
                    <div class="calc_header">
                        <a class="collapsed" data-toggle="collapse" data-parent="#accordion_calc" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">Scientific <span class="fa fa-angle-down"></span></a>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="calc_btns">
                            <button class="btn">2<sup>nd</sup></button>
                            <button class="btn">x<sup>2</sup></button>
                            <button class="btn">x<sup>3</sup></button>
                            <button class="btn">x<sup>y</sup></button>
                            <button class="btn">e<sup>x</sup></button>
                            <button class="btn">1/x</button>
                            <button class="btn">2<sup>nd</sup></button>
                            <button class="btn">2<sup>nd</sup></button>
                            <button class="btn">2<sup>nd</sup></button>
                            <button class="btn">log<sub>10</sub></button>
                            <button class="btn">1</button>
                            <button class="btn">x</button>
                            <button class="btn">10<sup>x</sup></button>
                            <button class="btn">In</button>
                            <button class="btn">log</button>
                            <button class="btn">x!</button>
                            <button class="btn">sin</button>
                            <button class="btn">cos</button>
                            <button class="btn">tan</button>
                            <button class="btn">e</button>
                            <button class="btn">Rad</button>
                            <button class="btn">sinh</button>
                            <button class="btn">cosh</button>
                            <button class="btn">tanh</button>
                            <button class="btn">Rand</button>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="calc_header">
                        <a>Basic</a>
                    </div>
                    <div class="calc_btns basic_btns">
                        <button class="btn btn_blue">C</button>
                        <button class="btn btn_red">+ _</button>
                        <button class="btn btn_red">%</button>
                        <button class="btn btn_green">+</button>
                        <button class="btn btn_white">7</button>
                        <button class="btn btn_white">8</button>
                        <button class="btn btn_white">9</button>
                        <button class="btn btn_green">-</button>
                        <button class="btn btn_white">4</button>
                        <button class="btn btn_white">5</button>
                        <button class="btn btn_white">6</button>
                        <button class="btn btn_green">x</button>
                        <button class="btn btn_white">1</button>
                        <button class="btn btn_white">2</button>
                        <button class="btn btn_white">3</button>
                        <button class="btn btn_green">/</button>
                        <button class="btn btn_white btn_0">0</button>
                        <button class="btn btn_white">.</button>
                        <button class="btn btn_white">=</button>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <!--//calc-->
                <!--white board-->
                <div class="white_board">
                    <ul class="board_tools">
                        <li class="paint"><a href="#" class="icon icon_paint"></a></li>
                        <li class="pen">
                            <a href="#" class="icon icon_pen"></a>
                            <ul class="pen_size">
                                <li><a href="#" class="s"></a></li>
                                <li><a href="#" class="m"></a></li>
                                <li><a href="#" class="l"></a></li>
                                <li><a href="#" class="xl"></a></li>
                            </ul>
                        </li>
                        <li class="erase"><a href="#" class="icon icon_erase"></a></li>
                        <li class="clear"><a href="#" class="icon icon_clear"></a></li>
                    </ul>
                </div>
                <!--//white board-->
                <!--Explore-->
                <div class="explore">
                </div>
                <!--//Explore-->
                <!--dictionary-->
                <div class="dictionary">
                </div>
                <!--//dictionary-->
                
                <!--tabs-->
                <div class="toolkit_tabs">
                    <ul>
                        <li id="calc_tab" class="active"><a href="#">Calculator</a></li>
                        <li id="white_board_tab"><a href="#">White board</a></li>
                        <li id="explore_tab"><a href="#">Explore</a></li>
                        <li id="dictionary_tab"><a href="#">Dictionary</a></li>
                    </ul>
                </div>
                <!--//tabs-->
            </div>

            <div class="sidebar_right_container sidebar_right_container2 tut_right_bar mCustomScrollbar" data-mcs-theme="minimal-dark">
                <div class="col-sm-12 time_chart">
                    <div class="box">
                        <div class="box_body">
                            <div class="item_chart css">
                                
                                <h2>2:15<small>min</small><span>Remaining</span></h2>
                                <svg width="160" height="160" xmlns="http://www.w3.org/2000/svg">
                                 <g>
                                  <title>Layer 1</title>
                                  <circle id="circle" r="69.85699" cy="81" cx="81" stroke-width="15" stroke="#333" fill="none"></circle>
                                  <circle id="circle" class="circle_animation" r="69.85699" cy="81" cx="81" stroke-width="15" stroke="#1bc4a3" fill="none"></circle>
                                 </g>
                                </svg>
                                <h4 class="group_score">Group Score : <span id="group_score_count" ><?php echo $topic['group_score']; ?></span></h4>
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box_body">
                            <h5>Active Comments : <span id="active_comment_count"><?php echo $active_comment; ?></span></h5>
                        </div>
                    </div>                    
                    <div class="box">
                        <div class="box_body">
                            <h5>My Score : <span id="my_score_count" ><?php echo $topic['my_score']; ?></span></h5>
                        </div>
                    </div>
                    <!--group-->
                    <div class="box tut_group">
                        <div class="box_header">
                            <h4>Tutorial Group</h4>
                        </div>

                        <?php foreach($member as $k =>$v){
                        ?>
                            <div class="box_body" data-id="<?php echo $v['id']; ?>">
                            <div class="mate_user_img">
                                <img src="/<?php echo UPLOAD_URL.'/'.$v['profile_link']; ?>">
                            </div>
                            <h4><?php echo $v['full_name']; ?></h4>
                            <p><?php echo $v['school_name']; ?></p>
                            <a href="#" class="icon <?php echo in_array($v['id'], $online)? 'icon_call_user_disable': 'icon_call_user'; ?>"></a>
                        </div>
                        <div class="box_footer" data-id="<?php echo $v['id']; ?>">
                            <p><?php echo in_array($v['id'], $online)? 'Online': 'Offline'; ?></p>
                        </div>
                        <?php

                        }  ?>
                    </div>
                    <!--//group--> 
                </div>
            </div>