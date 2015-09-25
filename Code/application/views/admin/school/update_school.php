<?php $this->load->view('admin/include/header'); ?>
  
  <h3>Add Student </h3>     

  <hr/> 

  <form role="form" method="POST">
      
      <h5>School Name</h5>
      <input type="text" name="school_name" value="<?php echo  $school_data['school_name']; ?>" class="form-control" />

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('school_name'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('school_name'),'') ; ?>
      </div>

      <h5>Principal Name</h5>
      <input type="text" name="principal_name" value="<?php echo  $school_data['principal_name']; ?>" class="form-control" />

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('principal_name'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('principal_name'),'') ; ?>
      </div>

      <h5>Home address</h5>
      <textarea name="address"><?php echo  $school_data['address']; ?></textarea>

      <h5>School Grade</h5>
      <select name="school_grade" id="school_grade" >
        <option selected disabled>Select School Grade</option> 
        <option  value="A" > A </option> 
        <option  value="B" > B </option> 
        <option  value="C" > C </option> 
        <option  value="D" > D </option> 
        <option  value="E" > E </option> 
      </select>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('school_grade'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('school_grade'),'') ; ?>
      </div>

      <h5>Country</h5>
      <select name="country" onchange="get_states(this.value)" id="country_id" >
        <option selected disabled>Select Country</option> 
        <?php 
          if(!empty($countries)){ 
            foreach($countries as $country) {
          ?> 
        <option value="<?php echo $country['id']; ?>"> <?php echo $country['country_name']; ?></option>
        <?php }  }else{ ?>
        <option > No Country</option>
        <?php } ?>
      </select>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('country'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('country'),'') ; ?>
      </div>

      <h5>State</h5>
      <select name="state" id="states_id" onchange="get_cities(this.value)">
        <option selected disabled>Select State</option> 
        <?php 
          if(!empty($states)){ 
            foreach($states as $state) {
          ?> 
        <option value="<?php echo $state['id']; ?>"> <?php echo $state['state_name']; ?></option>
        <?php }  }else{ ?>
        <option > No States</option>
        <?php } ?>
      </select>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('state'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('state'),'') ; ?>
      </div>

      <h5>City</h5>
      <select name="city" id="city_id" onchange="get_districts(this.value)" >
        <option selected disabled>Select City</option>
        <?php 
          if(!empty($cities)){ 
            foreach($cities as $city) {
          ?> 
        <option value="<?php echo $city['id']; ?>"> <?php echo $city['city_name']; ?></option>
        <?php }  }else{ ?>
        <option > No City</option>
        <?php } ?>
      </select>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('city'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('city'),'') ; ?>
      </div>

      <h5>District</h5>
      <select name="district" id="district_id">
        <option selected disabled>Select District</option>
        <?php 
          if(!empty($districts)){ 
            foreach($districts as $district) {
          ?> 
        <option value="<?php echo $district['id']; ?>"> <?php echo $district['district_name']; ?></option>
        <?php }  }else{ ?>
        <option > No City</option>
        <?php } ?>
      </select>

      <div class="alert alert-danger <?php if(empty(strip_tags(form_error('district'),''))){ echo 'hide';} ?>">
          <?php echo strip_tags(form_error('district'),'') ; ?>
      </div>

      <br/><br/>

    <button type="submit" class="btn btn-default">Submit</button>
  
  </form>

<script type="text/javascript">
  
  $('#country_id').val('<?php echo $school_data["country_id"]; ?>');
  $('#states_id').val('<?php echo $school_data["state_id"]; ?>');
  $('#city_id').val('<?php echo $school_data["city_id"]; ?>');
  $('#district_id').val('<?php echo $school_data["district_id"]; ?>');  
  $('#school_grade').val('<?php echo $school_data["school_grade"]; ?>');

  function get_states(country_id){
    $.ajax({
       url:'<?php echo base_url()."admin/ajax_get_states"; ?>',
       type:'POST',
       data:{country_id:country_id},
       success:function(data){

          $('#states_id').html('');
          $('#states_id').html(data);
          $('#city_id').html('<option selected disabled >Select City</option>');
          $('#district_id').html('<option selected disabled >Select District</option>');
       }
    });
   }

   function get_cities(state_id){
    $.ajax({
       url:'<?php echo base_url()."admin/ajax_get_cities"; ?>',
       type:'POST',
       data:{state_id:state_id},
       success:function(data){

          $('#city_id').html(data);
          $('#district_id').html('<option selected disabled >Select District</option>');
       }
    });
   }

  function get_districts(city_id){
    $.ajax({
       url:'<?php echo base_url()."admin/ajax_get_districts"; ?>',
       type:'POST',
       data:{city_id:city_id},
       success:function(data){
          $('#district_id').html(data);
       }
    });
  }

</script>


<?php $this->load->view('admin/include/footer'); ?>
