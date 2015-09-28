<div class="col-sm-7 main">
<i>(Note : Demo Notice Board)</i>

    <?php 
        if(isset($notice_list)){
            foreach ($notice_list as $key => $value) {
            ?>
            <div class="box feeds">
                <div class="feed_text">
                    <h4><?php echo $value['notice_title'];?></h4>
                        <span class="date">Date:<?php $old_date = strtotime($value['created_date']);echo date("M j, Y",$old_date);?></span>
                        <div class="clearfix"></div>
                            <p><?php echo $value['notice'];?></p> 
                        </div>
                <div class="clearfix"></div>
            </div>  
                <?php
            }
        }
    ?>
</div>
           