<!DOCTYPE html>
<html lang="en">
<head>
    <title>ISM Admin | Manage User</title>
  <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1"><title>Login</title>
  <link href="<?php echo base_url().'assets'; ?>/css/bootstrap.min.css" rel="stylesheet">

    <!--scroll-->
    <link href="<?php echo base_url().'assets'; ?>/css/select2-bootstrap.css" rel="stylesheet">    
    <link href="<?php echo base_url().'assets'; ?>/css/select2.css" rel="stylesheet">    

    <script src="<?php echo base_url().'assets'; ?>/js/jquery-1.11.3.min.js"></script>
    <script src="<?php echo base_url().'assets'; ?>/js/bootstrap.min.js"></script> <!-- Bootstap JS -->
    <script src="<?php echo base_url().'assets'; ?>/js/select2.min.js"></script> <!-- Select2 JS -->

</head>
<body>

<div class="container-fluid">
  <h3>Bootstrap Select2 Example</h3>
</div>
<hr>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-6">
      <h4>Bootstrap Select2</h4>
        <select class="form-control" id="select2">
          <option value="1">Cats</option>
          <option value="2">Dogs</option>
          <option value="3">Fish</option>
          <option value="4">Reptiles</option>
          <option value="5">Equine</option>
          <option value="6">Aviary</option>
          <option value="7">Insects</option>
        </select>
    </div> 
    <div class="col-md-6">
      <h4>Tag Picker</h4>
        <select class="form-control" id="tagPicker" multiple="multiple">
          <option value="1">Cats</option>
          <option value="2">Dogs</option>
          <option selected="selected" value="3">Fish</option>
          <option value="4">Reptiles</option>
          <option selected="selected" value="5">Equine</option>
          <option value="6">Aviary</option>
          <option value="7">Insects</option>
        </select>
    </div> 
  </div><!--/row-->
  <hr>
</div>

<script type="text/javascript">
  
$(document).ready(function() {

  /* Select2 plugin as tagpicker */
  $("#tagPicker").select2({
    closeOnSelect:false
  });

});

</script>
</body>
</html>