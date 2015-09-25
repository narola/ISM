<!--main-->
            <div class="col-sm-7 main main2 noticeboard">
              <!--breadcrumb-->
              <!--<div class="row page_header">
                  <div class="col-sm-12">
                      <ol class="breadcrumb">
                          <li><a href="#">Manage</a></li>
                          <li class="active">User</li>
                        </ol>
                    </div>
                </div>-->
                <!--//breadcrumb-->
                <!--filter-->
                <div class="row filter group_filter">
                  <div class="col-sm-12">
                      <div class="form-group">
                            <select class="form-control">
                                <option>Select School</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select Course</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select Year</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>Select Area</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control">
                                <option>School Role</option>
                            </select>
                        </div>
                    </div>
                </div>
                <!--//filter-->
                <!--noticeboard-->
                <div class="padding_b30">
                  <div class="col-lg-4 col-md-6">
                      <div class="box add_notice shadow_effect text-center">
                            <a href="<?php echo base_url().'admin/notice/add'; ?>">
                                <span class="icon icon_add"></span><br>
                                Add New Notice
                            </a>
                        </div>
                    </div>
                 
                 <?php 
                    if(!empty($notices)) { 
                        foreach($notices as $notice) {
                        ?>   

                  <div class="col-lg-4 col-md-6">                     
                      <div class="box notice shadow_effect">
                            <a href="#myModal" data-toggle="modal">
                                <div class="notice_header">
                                    <h3><?php echo $notice['notice_title']; ?><span> <?php echo $notice['created_date']; ?> </span></h3>
                                </div>
                                <div class="notice_body">
                                    <p><?php echo $notice['notice']; ?></p>
                                    <div class="notice_action">
                                        <a href="#" class="icon icon_zip_color"></a>
                                        <a href="#" class="icon icon_edit_color"></a>
                                        <a href="#" class="icon icon_copy_color"></a>
                                        <a href="#" class="icon icon_delete_color"></a>
                                        <input type="checkbox"><label class="save_box"></label>
                                    </div>
                                </div>
                            </a>
                        </div>                        
                    </div>
                   
                   <?php } } ?> 

                    <div class="clearfix"></div>
                </div>
                <!--//noticeboard-->
                
      </div>
            <!--//main-->