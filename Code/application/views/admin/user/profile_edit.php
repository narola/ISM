<!--main-->
<div class="col-sm-7 main main2 profile profile_admin admin_controls mCustomScrollbar" data-mcs-theme="minimal-dark">
   <form class="basic_info" method="post" enctype="multipart/form-data">     
	   <div class="box margin_15 ">
            <div class="box_header text-center">
            	<h3>Your Profile</h3>
            </div>
            <div class="box_body">
                <div class="change_dp">
                	<div class="user_profile_img">
                        <img src="<?php echo base_url().'uploads/'.$user['profile_pic'];?>">
                    </div>
                    <div class="upload">
                        <input type="file" name="profile">
                        <span>Change Profile Picture</span>
                    </div>
                </div>
                    <div class="form-group row">
                        <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                            <label>First Name</label>
                        </div>
                        <div class="col-lg-4 col-md-6 col-sm-8">
                            <input type="text" class="form-control" name="first_name" 
                                   value="<?php  echo set_value("first_name") == false ? $user["first_name"] : set_value("first_name"); ?>" >
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                            <label>Last Name</label>
                        </div>
                        <div class="col-lg-4  col-md-6 col-sm-8">
                            <input type="text" class="form-control" name="last_name" 
                            value="<?php  echo set_value("last_name") == false ? $user["last_name"] : set_value("last_name"); ?>">
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                            <label>Username</label>
                        </div>
                        <div class="col-lg-4 col-md-6 col-sm-8">
                            <input type="text" class="form-control" name="username" 
                            value="<?php  echo set_value("username") == false ? $user["username"] : set_value("username"); ?>">
                            <?php echo form_error('username','<div class="alert alert-danger">','</div>'); ?>
                        </div>
                    </div>
					<div class="form-group row">
                        <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                            <label>Email</label>
                        </div>
                        <div class="col-lg-4 col-md-6 col-sm-8">
                            <input type="text" class="form-control" name="email_id" 
                            value="<?php  echo set_value("email_id") == false ? $user["email_id"] : set_value("email_id"); ?>">
                            <?php echo form_error('email_id','<div class="alert alert-danger">','</div>'); ?>
                        </div>

                    </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="col-sm-12 ">
            <div class="box">
            	<div class="box_header text-center">
                	<h3 class="text-uppercase txt_brightred">Change Password</h3>
                </div>
                <div class="box_body">
                    
                        <div class="form-group row">
                            <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                <label>Current Password</label>
                                <?php echo flashMessage(TRUE,TRUE); ?>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sm-8">
                                <input type="password" class="form-control" name="old">
                                <?php echo form_error('old','<div class="alert alert-danger">','</div>'); ?>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                <label>New Password</label>
                            </div>
                            <div class="col-lg-4  col-md-6 col-sm-8">
                                <input type="password" class="form-control" name="new">
                                <?php echo form_error('new','<div class="alert alert-danger">','</div>'); ?>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-lg-offset-3 col-lg-2 col-md-3 col-sm-4">
                                <label>Confirm Password</label>
                            </div>
                            <div class="col-lg-4 col-md-6 col-sm-8">
                                <input type="password" class="form-control" name="repeat">
                                <?php echo form_error('repeat','<div class="alert alert-danger">','</div>'); ?>
                            </div>
                        </div>

                </div>
            </div>
        </div>
        <!--buttons-->
        <div class="col-sm-12 text-center">
        	<button class="btn btn_red" type="submit">Update</button>
        	<a href="admin/user" class="btn btn_black_normal"> Cancel </a>
        </div>
    </form>
    <!--//buttons-->
</div>
<!--//main-->