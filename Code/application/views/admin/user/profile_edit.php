<!--main-->
            <div class="col-sm-7 main main2 profile profile_admin admin_controls mCustomScrollbar" data-mcs-theme="minimal-dark">
            	<div class="box margin_15 ">
                    <div class="box_header text-center">
                    	<h3>Your Profile</h3>
                    </div>
                    <form class="basic_info" method="post">
                        <div class="box_body">
                            <div class="change_dp">
                            	<div class="user_profile_img">
                                    <img src="../images/user1.jpg">
                                </div>
                                <div class="upload">
                                    <input type="file">
                                    <span>Change Profile Picture</span>
                                </div>
                            </div>
                                <div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>First Name</label>
                                    </div>
                                    <div class="col-lg-4 col-md-6 col-sm-8">
                                        <input type="text" class="form-control" name="fname" value="<?php echo set_value('fname'); ?>">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>Last Name</label>
                                    </div>
                                    <div class="col-lg-4  col-md-6 col-sm-8">
                                        <input type="text" class="form-control" name="lname" value="<?php echo set_value('lname'); ?>">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>Username</label>
                                    </div>
                                    <div class="col-lg-4 col-md-6 col-sm-8">
                                        <input type="text" class="form-control" name="username" value="<?php echo set_value('username'); ?>">
                                    </div>
                                </div>
								<div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>Email</label>
                                    </div>
                                    <div class="col-lg-4 col-md-6 col-sm-8">
                                        <input type="email" class="form-control" name="email_id" value="<?php echo set_value('email_id'); ?>">
                                    </div>
                                </div>
                           
                        </form>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <div class="col-sm-12 ">
                    <div class="box">
                    	<div class="box_header text-center">
                        	<h3 class="text-uppercase txt_brightred">Change Password</h3>
                        </div>
                        <div class="box_body">
                            <form>
                                <div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>Current Password</label>
                                    </div>
                                    <div class="col-lg-4 col-md-6 col-sm-8">
                                        <input type="password" class="form-control" name="old">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>New Password</label>
                                    </div>
                                    <div class="col-lg-4  col-md-6 col-sm-8">
                                        <input type="password" class="form-control" name="new">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                        <label>Confirm Password</label>
                                    </div>
                                    <div class="col-lg-4 col-md-6 col-sm-8">
                                        <input type="password" class="form-control" name="repeat">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!--buttons-->
                <div class="col-sm-12 text-center">
                	<button class="btn btn_red" type="submit">Update</button>
                	<a href="admin/user" class="btn btn_black_normal"> Cancel </a>
                </div>
                <!--//buttons-->
			</div>
            <!--//main-->