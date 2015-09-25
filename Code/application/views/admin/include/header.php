<!DOCTYPE html>
<html lang="en">
<head>
  <title>ISM</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>

	  
<div class="container">

	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">Welcome , Admin</a>
	    </div>
	    <div>
	      <ul class="nav navbar-nav">
	        <li class="active"><a href="#">Home</a></li>
	        <li><a href="<?php echo base_url().'admin/add_user'; ?>">Create User</a></li>
	        <li><a href="#">Page 2</a></li>
	        <li><a href="<?php echo base_url().'admin/logout'; ?>">Logout</a></li>
	      </ul>
	    </div>
	  </div>
	</nav>