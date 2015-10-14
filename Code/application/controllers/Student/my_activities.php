<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class My_activities extends ISM_Controller {

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
		$created_date = $this->session->userdata('user')['created_date'];

		/*------ Get current month to user registration month-------*/
		$begin = new DateTime($created_date);
		$end = new DateTime(date('Y-m-d H:i:s'));
		$date_array = array();
		while ($begin <= $end) {
			$date_array[] = $begin->format('Y-m');
			$begin->modify('first day of next month');
		}
		$month = array();

		/*----find current month and if request to view more append one month in descending form---*/
		$month[] = date('m',strtotime(date('Y-m-d')));
		$load_more = $this->input->post('load_more');
		if($load_more != '')
			$month[] = date('m',strtotime($load_more));
		
		/*---------Get topic allocated------*/
		$where = array('where' => array('ga.group_id' => $user_group_id),'where_in' => array('date_format(ga.created_date,"%m")' => $month));
		$option= array('join' =>
					array(
						array(
							'table' => TBL_TOPICS.' t',
							'condition' => 't.id = ga.topic_id'
						)
					)
				);
		$select = 't.topic_name,ga.created_date';
		$data['my_activities']['topic_allcated'] = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' ga',$select,$where,$option);

		/*--------Get my studymates---------*/
		$studymate = studymates($user_id,false);
		$where = array('where_in'=>array('u.id'=>$studymate),'where_in' => array('date_format(sm.created_date,"%m")' => $month));
		$option = array('join'=>
					array(
						array(
							'table' => TBL_STUDYMATES.' sm',
							'condition' => 'u.id = sm.mate_of and sm.mate_id ='.$user_id
						),
						array(
							'table' => TBL_STUDYMATES.' sm2',
							'condition' => 'u.id = sm2.mate_id and sm2.mate_of ='.$user_id
						),
						array(
							'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
							'condition' => 'u.id = in.user_id'
						),
						array(
							'table' => TBL_SCHOOLS.' s',
							'condition' => 's.id = in.school_id'
						),
						array(
							'table' => TBL_USER_PROFILE_PICTURE.' p',
							'condition' => 'u.id = p.user_id'
						)
					),
				);
		$select='u.full_name,sm.mate_of ,sm2.mate_id,if(sm.created_date is null,sm2.created_date,sm.created_date) as created_date,s.school_name,p.profile_link';
		$data['my_activities']['studymates'] = select(TBL_USERS.' u',$select,$where,$option);

		/*--------Get my like feed----------*/
		$where = array('where' => array('like.like_by'=>$user_id),'where_in' => array('date_format(like.created_date,"%m")' => $month));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEEDS.' like_feed',
								'condition' => 'like_feed.id = like.feed_id'
							),
							array(
								'table' => TBL_USERS.' upost',
								'condition' => 'upost.id = like_feed.feed_by'
							)
							
						),
						'order_by' => 'like.created_date DESC'
					);
		$select = 'upost.full_name as post_username,like_feed.feed_text,like.created_date,(select count(*) from feed_like where feed_id = like_feed.id) as totlike,(select count(*) from feed_comment where feed_id = like_feed.id) as totcomment';
		$data['my_activities']['like'] = select(TBL_FEED_LIKE.' like',$select,$where,$options);		

		/*-------Get my comment----------*/
		$where = array('where' => array('comment.comment_by'=>$user_id),'where_in' => array('date_format(comment.created_date,"%m")' => $month));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEEDS.' comment_feed',
								'condition' => 'comment_feed.id = comment.feed_id'
							),
							array(
								'table' => TBL_USERS.' u',
								'condition' => 'u.id = comment_feed.feed_by'
							),
							array(
								'table' => TBL_USER_PROFILE_PICTURE.' p',
								'condition' => 'p.user_id = u.id'
							)
							
						),
						'order_by' => 'comment.created_date DESC',
						'group_by' => 'comment_feed.id'
					);
		$select = 'u.full_name,u.id,comment_feed.feed_text,p.profile_link,comment.comment,comment.created_date,(select count(*) from feed_like where feed_id = comment_feed.id) as totlike,(select count(*) from feed_comment where feed_id = comment_feed.id) as totcomment,comment_feed.id';
		$data['my_activities']['comment'] = select(TBL_FEED_COMMENT.' comment',$select,$where,$options);

		/*-------Get my post--------*/
		$where = array('where' => array('post.feed_by'=>$user_id),'where_in' => array('date_format(post.created_date,"%m")' => $month));
		$options = array('order_by' => 'post.created_date DESC');
		$select = 'post.feed_text,(select count(*) from feed_like where feed_id = post.id) as totlike,(select count(*) from feed_comment where feed_id = post.id) as totcomment,post.created_date';
		$data['my_activities']['post'] = select(TBL_FEEDS.' post',$select,$where,$options);
		
		$data['my_month'] = $date_array;
		$this->template->load('student/default','student/my_activities',$data);
	}
}
