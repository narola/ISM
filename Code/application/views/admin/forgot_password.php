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
                <h3>Admin <span>Login</span></h3>
            </div>  
            <div class="login_board text-center">
                <div class="col-sm-12">
                    <img src="<?php echo base_url().'assets'; ?>/images/logo_login_admin.png" class="logo">

                    <?php $error = $this->session->flashdata('error'); ?>
                    <form class="login_form" method="POST">
                        <div class="alert alert-danger <?php if(empty(strip_tags($error,''))){ echo 'hide';} ?>">
                                  <?php echo strip_tags($error) ; ?>
                          </div>
                        <div class="form-group">
                            <input placeholder="Enter Email Address" class="form-control" type="email" name="emailid">
                        </div>

                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('emailid'),''))){ echo 'hide';} ?>">
                              <?php echo strip_tags(form_error('emailid'),'') ; ?>
                        </div>
                        <div class="form-group">    
                            <button type="submit" class="btn btn_black" >Login<span class="fa fa-chevron-right"></span></button>
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
