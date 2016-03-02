<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM | Admin Login</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
    <link href="<?php echo base_url().'assets'; ?>/css/bootstrap.min.css" rel="stylesheet">
    <!--custom css-->
    <link href="<?php echo base_url().'assets'; ?>/css/ism_style.css" rel="stylesheet">
    <link href="<?php echo base_url().'assets'; ?>/css/ism_admin_style.css" rel="stylesheet">
    <link href="<?php echo base_url().'assets'; ?>/css/responsive.css" rel="stylesheet">
    <link href="<?php echo base_url().'assets'; ?>/css/icon.css" rel="stylesheet">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="<?php echo base_url().'assets'; ?>/images/graduate_admin.png" sizes="32x32" />

</head>

<body class="login_background_admin">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12 text-center">
                <h3>Admin <span>Reset Password</span></h3>
            </div>  
            <div class="login_board text-center">
                <div class="col-sm-12">
                    <img src="<?php echo base_url().'assets'; ?>/images/logo_login_admin.png" class="logo">

                    <?php $error = $this->session->flashdata('error'); ?>
  
                     <form class="login_form" method="post" action="<?php echo site_url().'admin/reset_password'?>">
                        <div class="form-group">
                            <input placeholder="Enter Password" class="form-control" type="password" name="new_password">
                        </div>
                        <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('new_password'),''))){ echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('new_password'),''); ?>
                        </div>
                        <div class="form-group">
                            <input placeholder="Enter Confirm Password" class="form-control" type="password" name="con_password">
                        </div>
                        <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('con_password'),''))){ echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('con_password'),''); ?>
                        </div>
                        <div class="form-group">
                            <input type="hidden" name="token" value="<?php echo isset($token)?$token:'';?>">
                            <button type="submit" class="btn btn_black">Submit
                                <span class="fa fa-chevron-right"></span>
                            </button>
                        </div>
                    </form>
                   
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<?php echo base_url().'assets'; ?>/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<?php echo base_url().'assets'; ?>/js/bootstrap.min.js"></script> 
</body>
</html>
