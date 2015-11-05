<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>ISM Login</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
	<link href="<?php echo base_url();?>assets/css/bootstrap.min.css" rel="stylesheet">
    <!--custom css-->
    <link href="<?php echo base_url();?>assets/css/ism_style.css" rel="stylesheet">
    <link href="<?php echo base_url();?>assets/css/responsive.css" rel="stylesheet">
    <link href="<?php echo base_url();?>assets/css/icon.css" rel="stylesheet">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="<?php echo base_url();?>assets/images/graduate.png" sizes="32x32" />
</head>
	
<body class="login_background">
	<div class="container-fluid">
    	<div class="row">        	
            <div class="login_board text-center">
                <div class="col-sm-12">
                	<img src="<?php echo base_url();?>assets/images/logo.png" class="logo">
                    <form class="login_form" method="post">
                    	<label class="form_title">Forgot your Password?</label>
                        <hr class="form_hr">
                        <p class="font-subtitle">Send Us Your Email Id</p>
                    	<div class="form-group">
                    		<input placeholder="Enter Your Email" class="form-control" type="email" name="emailid">
                        </div>
                        <div class=" alert alert-danger <?php if(empty(strip_tags(form_error('emailid'),''))){ echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('emailid'),''); ?>
                        </div>
                        <div class=" alert alert-danger <?php if(empty($this->session->flashdata('msg'))){ echo 'hide';} ?>">
                            <?php echo $this->session->flashdata('error');?>
                        </div>
                        <div class="form-group">
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
    <script src="<?php echo base_url();?>assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<?php echo base_url();?>assets/js/bootstrap.min.js"></script> 
</body>
</html>
