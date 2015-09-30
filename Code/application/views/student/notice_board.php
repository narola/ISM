<style type="text/css">
    .active{
        background-color:#323941;
    border: 1px solid #f5f5f5;
    }
</style>
<!--main-->
<div class="col-sm-7 main main2">
    <!--filter-->
    <div class="row filter group_filter small_group">
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
    <div class="row">
        <div class="col-sm-12 notice_container">
            <?php 
                if(sizeof($notice_list)>0){
                    foreach ($notice_list as $key => $value) {
            ?>
            <div class="box">
                <div class="notice_content">
                    <div class="col-md-12">
                        <h3><?php echo $value['notice_title'];?><span>Date:<?php $old_date = strtotime($value['created_date']);echo date("M j, Y",$old_date);?></span></h3>
                    </div>
                    <div class="col-sm-12 notice_description">
                        <p><?php echo $value['notice'];?></p>
                    </div>
                    <div class="col-sm-12">
                        <span class="label label_name">Adam Ross</span>
                        <span class="label label_name">John Stranger</span>
                        <span class="label label_name">Ron Weasley</span>   
                        <a href="#" class="more_tagged">12 more</a> 
                        <a href="#" class="fa fa-angle-double-down"></a>                            
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
                        <h3>No records found...</h3>
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
 <!--side right-->
<div class="sidebar_right_container sidebar_right_container2 mCustomScrollbar" data-mcs-theme="minimal-dark">
    <!--scrollbar" id="style-3-->
    <!--high score board-->
    <div class="score box">
        <div class="box_header">
            <h3>High Scores</h3>
        </div>
        <div class="score_div">
            <h5>Science</h5>
            <!--item1-->
            <div class="score_item">
                <div class="score_img">
                    <img src="assets/images/user5.jpg">
                </div>
                <div class="score_descrip">
                    <p class="score_name">Adam Stranger</p>
                    <p>St. Xeviers</p>
                    <p>F.Y. CS</p>
                </div>
                <div class="score_points">
                    <p>Score</p>
                    <p class="score_number">500</p>
                </div>
                <div class="clearfix"></div>
            </div>
            <!--item2-->
            <div class="score_item">
                <div class="score_img">
                    <img src="assets/images/user3.jpg">
                </div>
                <div class="score_descrip">
                    <p class="score_name">Matt Larner</p>
                    <p>St. Mary</p>
                    <p>F.Y. CS</p>
                </div>
                <div class="score_points">
                    <p>Score</p>
                    <p class="score_number">487</p>
                </div>
                <div class="clearfix"></div>
            </div>
           
        </div>
        <div class="score_div">
            <h5>Arts</h5>
            <!--item1-->
            <div class="score_item">
                <div class="score_img">
                    <img src="assets/images/user5.jpg">
                </div>
                <div class="score_descrip">
                    <p class="score_name">Adam Stranger</p>
                    <p>St. Xeviers</p>
                    <p>F.Y. CS</p>
                </div>
                <div class="score_points">
                    <p>Score</p>
                    <p class="score_number">500</p>
                </div>
                <div class="clearfix"></div>
            </div>
            <!--item2-->
            <div class="score_item">
                <div class="score_img">
                    <img src="assets/images/user3.jpg">
                </div>
                <div class="score_descrip">
                    <p class="score_name">Matt Larner</p>
                    <p>St. Mary</p>
                    <p>F.Y. CS</p>
                </div>
                <div class="score_points">
                    <p>Score</p>
                    <p class="score_number">487</p>
                </div>
                <div class="clearfix"></div>
            </div>
            
        </div>
        <div class="clearfix"></div>
    </div>
    <!--//high score board-->
    <!--Suggested Studymates-->                
    <!--//suggested Studymates-->
    <!--STM-->
    <div class="stm">
        <div class="box_header">
            <h3>Studymates <span> STM</span></h3>
        </div>
        <div class="stm_list mCustomScrollbar" data-mcs-theme="minimal-dark">
            <!--stm1-->
            <div class="stm_item online">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user1.jpg">
                </div>
                <p>Mary Watson</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item online">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user2.jpg">
                </div>
                <p>Adam Rose</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item online">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user3.jpg">
                </div>
                <p>John Smith</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item online">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user4.jpg">
                </div>
                <p>Adam Stranger</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item online">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user1.jpg">
                </div>
                <p>Mary Watson</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item online">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user2.jpg">
                </div>
                <p>Adam Rose</p>
                </a>
                <div class="clearfix"></div>
            </div>                    
            <!--stm1-->
            <div class="stm_item offline">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user5.jpg">
                </div>
                <p>Dean Winchester</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item offline">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user6.jpg">
                </div>
                <p>Sam Winchester</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item offline">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/user7.jpg">
                </div>
                <p>Lucifer Dey</p>
                </a>
                <div class="clearfix"></div>
            </div>
            <!--stm1-->
            <div class="stm_item offline">
                <a href="#">
                <div class="stm_user_img">
                    <img src="assets/images/avatar.png">
                </div>
                <p>Ronald Weasley</p>
                </a>
                <div class="clearfix"></div>
            </div> 
         </div>       
                    
    </div>
    <!--//STM-->
    
</div>
<!--//side right-->
