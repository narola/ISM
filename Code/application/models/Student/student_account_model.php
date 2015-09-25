<?php
/*kap---------------22-09-2015--*/
defined('BASEPATH') OR exit('No direct script access allowed');

class student_account_model extends CI_Model {
	public function __construct()
	{

	}

	public function get_school_info($username)
	{
		$this->db->select('s.id as school_id,c.id as class,cs.id as program,s.district_id');
		$this->db->join('schools s','s.id = a.school_id','left');
		$this->db->join('school_classroom c','c.school_id = s.id','left');
		$this->db->join('school_course sc','sc.school_id = s.id','left');
		$this->db->join('courses cs','cs.id = sc.course_id','left');
		$this->db->join('classrooms cl','cs.id = cl.course_id','left');
		// $this->db->join('years y','y.id = cl.year_id','left');
		$this->db->where('a.username',$username);
		$query = $this->db->get('auto_generated_credential a');
		return $query->result_array();
	}
	   
}