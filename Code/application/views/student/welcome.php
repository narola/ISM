<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM Welcomes You</title>
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
<body class="welcome_bg">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12 text-center welcome_content">
                <img src="assets/images/welcome.png">
                <p class="para_welcome1"> John, Your tutorial group is being processed and will be available to you for confirmation.</p>
                <div class="loader"></div>
                <p class="para_welcome2"> Tutorial group is 5 members group of randomly selected students, put together to <span style="color:#80d51e;">share ideas,</span> 
<span style="color:#e08bff;">collaborate</span> and <span style="color:#fea635;">do assignments.</span></p>
            </div>
        </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="assets/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/bootstrap.min.js"></script> 
    <script>
        jQuery(document).ready(function () {
            window.setTimeout(function () {
                location.href = "student/group_allocation";
            }, 4000);
        });
    </script>
</body>
</html>
