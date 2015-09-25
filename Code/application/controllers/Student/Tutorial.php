
<?php
defined('BASEPATH') OR exit('No direct script access allowed');


/**
*	Handle group discussion
*   @Author = Sandip Gopani (SAG)
*/
class Tutorial extends ISM_Controller {
	
	public function __construct()
	{
	    parent::__construct();
	}
	

	public function index(){
			p(getdate(),true);
		if(is_active_hours() == true){
			
		}	
	}	


}
