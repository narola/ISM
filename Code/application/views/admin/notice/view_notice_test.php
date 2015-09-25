<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM Admin | Notice Board</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
    <link href="<?php echo base_url().'assets'; ?>/css/bootstrap.min.css" rel="stylesheet">
    <!--custom css-->
    <link href="<?php echo base_url().'assets'; ?>/css/ism_style.css" rel="stylesheet">
    <link href="<?php echo base_url().'assets'; ?>/css/ism_admin_style.css" rel="stylesheet">
    <link href="<?php echo base_url().'assets'; ?>/css/responsive.css" rel="stylesheet">
    <link href="<?php echo base_url().'assets'; ?>/css/icon.css" rel="stylesheet">
    <!--scroll-->
    <link href="<?php echo base_url().'assets'; ?>/css/jquery.mCustomScrollbar.css" rel="stylesheet">
    <!--fonts-->
    <link href='https://fonts.googleapis.com/css?family=Raleway:400,100,300,200,500,600,700,800,900' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="icon" type="image/png" href="<?php echo base_url().'assets'; ?>/images/graduate_admin.png" sizes="32x32" />
</head>

<body>
                
    <nav class="navbar navbar-default navbar-fixed-top navbar_admin">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#"><img src="../images/site_admin_logo.png"></a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><a href="Home.html"><span class="icon icon_menu_home"></span> Home</a></li>
            <li class="dropdown">
                <a href="Tutorial.html" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_assess"></span> Assessment</a>
                <ul class="dropdown-menu">
                    <li><a href="#">Question Bank</a></li>
                    <li><a href="Exam.html">Exams</a></li>
                </ul>
            </li>
            <li><a href="Manage_group.html"><span class="icon icon_menu_group"></span> Groups</a></li>
            <li><a href="#"><span class="icon icon_menu_organize"></span> Organize</a></li>
            <li class="active dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="icon icon_menu_manage"></span> Manage</a>
              <ul class="dropdown-menu">
                <li><a href="#">Book</a></li>
                <li><a href="#">Auther</a></li>
                <li><a href="#">School</a></li>
                <li><a href="#">Classroom</a></li>
                <li><a href="#">Subject</a></li>
                <li><a href="#">Badges</a></li>
                <li><a href="Manage_user.html">User</a></li>
                <li class="dropdown sub_menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Topics</a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Allocate Topics</a></li>
                        <li><a href="List_of_topics.html">List of Topics</a></li>
                        <li><a href="#">Add New Topic</a></li>
                    </ul>
                </li>
              </ul>
            </li>
            <li><a href="#"><span class="icon icon_menu_report"></span> Reports</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a class="" href="#"><span class="icon icon_search"></span></a></li>
            <li><a class="" href="#"><span class="icon icon_grid"></span></a></li>
            <li><a class="" href="#"><span class="icon icon_list"></span></a></li>
            <li><a class="" href="#"><span class="icon icon_refresh"></span></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
    <!--body-->
    <div class="container-fluid">
        <div class="row">
            <!--side left-->
            <div class="sidebar_left_container inner_pages inner_pages_admin text-center mCustomScrollbar" data-mcs-theme="minimal"><!-- scrollbar" id="style-3-->
                <div class="user_profile_img">
                    <img src="../images/user1.jpg">
                </div>
                <h4>Adam Ross</h4>
                <a href="#">View Profile</a>
                <div class="clearfix"></div>
                 
                <!--//notification-->
                <ul class="personal_menu">
                    <li><a href="#"><span class="icon icon_feed"></span>Feeds</a></li><!-- class="active"-->
                    <li><a href="#"><span class="icon icon_spam"></span>Spam<span class="badge message_badge">12</span></a></li>
                    <li><a href="#"><span class="icon icon_banner"></span>Banners</a></li>                  
                    <li class="active"><a href="Notice_board.html"><span class="icon icon_notice"></span>Notice Board</a></li>
                    <li><a href="#"><span class="icon icon_ques"></span>Questionaries</a></li>
                    <li><a href="#"><span class="icon icon_credencial"></span>General Credencials</a></li>
                </ul>
                <a href="Login.html" class="logout"><span class="icon icon_logout"></span>LogOut</a>
                <p class="copyright">©2015 ISM | All Rights Reserved.</p>
            </div>
            <!--//side left-->
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
                            <a href="#">
                                <span class="icon icon_add"></span><br>
                                Add New Exam
                            </a>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">                     
                        <div class="box notice shadow_effect">
                            <a href="#myModal" data-toggle="modal">
                                <div class="notice_header">
                                    <h3>Picnic Notice<span>Sep 7, 2015</span></h3>
                                </div>
                                <div class="notice_body">
                                    <p>Lorem Ipsum is simply dummy text of the prining and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.</p>
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
                    <div class="col-lg-4 col-md-6">
                        <div class="box notice shadow_effect">
                            <a href="#myModal" data-toggle="modal">
                                <div class="notice_header">
                                    <h3>Picnic Notice<span>Sep 7, 2015</span></h3>
                                </div>
                                <div class="notice_body">
                                    <p>Lorem Ipsum is simply dummy text of the prining and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.</p>
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
                    <div class="clearfix"></div>
                </div>
                <!--//noticeboard-->
               
                
                
                
            </div>
            <!--//main-->
            
        </div>
    </div>
    <!--//body-->
                <!-- Modal -->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header notice_header text-center">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Picnic Notice</h4>
                        <small>Sep 7, 2015</small>
                      </div>
                      <div class="modal-body">
                        <p>Lorem Ipsum is simply dummy text of the prining and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.</p>
                        <p>Lorem Ipsum is simply dummy text of the prining and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting.</p>
                        <h4 class="notice_by">Gilbert Addoh<span>ISM Admin</span></h4>
                        <div class="clearfix"></div>
                      </div>
                      
                    </div>
                  </div>
                </div>
                <!-- /.modal -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<?php echo base_url().'assets'; ?>/js/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<?php echo base_url().'assets'; ?>/js/bootstrap.min.js"></script> 
    <!--scroll-->
    <script src="<?php echo base_url().'assets'; ?>/js/jquery.mCustomScrollbar.concat.min.js"></script>

</body>
</html>
