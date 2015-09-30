<!--main-->
            <div class="col-sm-7 main main2">
                <!--filter-->
                <div class="row filter group_filter small_group">
                    <div class="col-sm-12">
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select Subject</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select year</option>
                            </select>
                        </div>
                        
                        <div class="form-group no_effect search_input">
                            <input class="form-control" type="text" placeholder="Search">
                            <a href="#" class="fa fa-search"></a>
                        </div>
                    </div>
                </div>
                <!--//filter-->
                <!--topics-->
                <div class="row">
                    <div class="col-sm-12 notice_container">
                        <?php 
        if(isset($notice_list)){
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
    ?>
                </div>
                <!--//topics-->
            </div>
            <!--//main-->

