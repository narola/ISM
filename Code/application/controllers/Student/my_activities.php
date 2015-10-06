<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class my_activities extends ISM_Controller {

	/*	Student My Activities.
	*	@Auther : Kamlesh Pokiya (KAP). 
	*/

	public function __construct()
	{
	    parent::__construct();
	}

	public function index()
	{
		$data['title'] = 'ISM - MY Activities';
		$user_group_id = $this->session->userdata('user')['group_id'];
		$user_id = $this->session->userdata('user')['id'];

		/*--------Get my activities---------*/
		$studymate = studymates($user_id,false);
		
		$where = array('where' => array('u.id'=>$user_id));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEED_LIKE.' like',//--my like
								'condition' => 'like.like_by = u.id',
							),
							array(
								'table' => TBL_FEEDS.' like_feed',
								'condition' => 'like_feed.id = like.feed_id'
							)
						),
						'order_by' => 'like.created_date DESC'
					);
		$select = 'u.full_name,u.id,like_feed.feed_text,like.created_date';
		$data['my_activities'] = select(TBL_USERS.' u',$select,$where,$options);
		qry();
		p($data['my_activities'],true);
		$this->template->load('student/default','student/my_activities',$data);
	}
}
