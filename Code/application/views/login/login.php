<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>ISM Login</title>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
    <base href="<?php echo base_url();?>">

	<link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <!--custom css-->
    <link href="assets/css/ism_style.css" rel="stylesheet">
    <link href="assets/css/responsive.css" rel="stylesheet">
    <link href="assets/css/icon.css" rel="stylesheet">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="assets/images/graduate.png" sizes="32x32" />
   
</head>
	
<body class="login_background">
	<div class="container-fluid">
    	<div class="row">        	
            <div class="login_board text-center">
                <div class="col-sm-12">
                	<img src="assets/images/logo.png" class="logo">
                    <form class="login_form" method="post">
                         <div class=" alert alert-danger <?php if(empty($this->session->flashdata('error'))){ echo 'hide';} ?>">
                            <?php echo $this->session->flashdata('error');?>
                        </div>
                    	<div class="form-group">
                    		<input placeholder="Enter Email/Username" class="form-control" type="text" name="username">
                        </div>
                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('username'),''))){ echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('username'),'') ; ?>
                        </div>
                        <div class="form-group">
                        	<input placeholder="Enter Password" class="form-control" type="password" name="password">
                        </div>
                        <div class="alert alert-danger <?php if(empty(strip_tags(form_error('password'),''))){ echo 'hide';} ?>">
                            <?php echo strip_tags(form_error('password'),'') ; ?>
                        </div>
                        <div class="form-group">
                        	<div class="squaredThree">
                                <input type="checkbox" value="None" id="squaredThree" name="check" />
                                <label for="squaredThree"></label>
                                <span>Remember Me</span>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn_black">Login
                                <span class="fa fa-chevron-right"></span>
                            </button>
                        </div>
                    </form>
                    <div class="login_links">
                    	<a href="login/forgot_password">Forgot Password</a>
                        <p>Do Not Have Credenctials, <a href="#sample" data-toggle="modal">Click Here</a></p>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
    <!-- Modal -->
        <div class="modal fade" id="sample" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header notice_header text-center">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">REQUEST FORM</h4>
                        <small><?php echo date("d F Y",strtotime(date('Y-m-d')));?></small>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="" onsubmit="return send_email();" method="post">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Name :</label>
                                <div class="col-sm-7">
                                    <input type="text" required class="form-control" id="request_name" name="request_name" placeholder="Name">
                                    <br>
                                    <div class="alert alert-danger" style="display:none" id="err3">
                                        Name field is required
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Email :</label>
                                <div class="col-sm-7">
                                    <input type="email" required class="form-control" id="request_email" name="request_email" placeholder="Email">
                                    <br>
                                    <div class="alert alert-danger" style="display:none" id="err1">
                                        Email field is required
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="col-sm-2 control-label">Message :</label>
                                <div class="col-sm-10">
                                   <textarea class="form-control" required placeholder="Write school information..." name="message" id="message" rows="7"></textarea>
                                   <br>
                                    <div class="alert alert-danger" style="display:none" id="err2">
                                        Message field is required
                                    </div> 
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <input type="hidden" name="send_request" value="change">
                                    <input type="submit" class="btn btn_black_normal" value="SEND REQUEST">
                                </div>
                            </div>
                        </form>
                        <h4 class="notice_by">ISM Admin<span></span></h4>
                        <div class="clearfix"></div>
                  </div>
                </div>
            </div>
        </div>
    <!-- /.modal -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script> 
     <script>

        $('#request_name').keypress(function (e) {
            var regex = new RegExp("^[a-zA-Z0-9\\b ]+$");
            var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
            if (regex.test(str) || e.keyCode === 9) {
                return true;
            }

            e.preventDefault();
            return false;
        });
        function send_email(){

            email = $('#request_email').val();
            message = $('#message').val();
            name = $('#request_name').val();
       
            if(email == '' && message == '' && name == ''){
                $('#err2').show();
                $('#err1').show();   
                $('#err3').show();   
            }
            else if(message == '' && email != '' && name != ''){
                $('#err2').show();
                $('#err1').hide();
                $('#err3').hide();
            }
            else if(email == '' && message != '' && name != ''){
                $('#err1').show();
                $('#err2').hide();
                $('#err3').hide();
            }
            else if(name == '' && email != '' && message !=''){
                $('#err3').show();
                $('#err2').hide();
                $('#err1').hide();   
                
            }
            else {
          
                return true;
            } 
            return false;
        }
    </script>
</body>
</html>
