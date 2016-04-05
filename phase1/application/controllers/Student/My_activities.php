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
		/*------ page title-----*/
		$data['title'] = 'ISM - MY Activities';

		/*------ user details---*/
		$user_group_id = $this->session->userdata('user')['group_id'];
		$user_id = $this->session->userdata('user')['id'];
		$created_date = $this->session->userdata('user')['created_date'];

		/*------ Get current month -------*/
		$month = array();
		$month[] = date('m',strtotime(date('Y-m-d')));
		
		/*---------Get topic allocated with discussion score + percentage of topic exam------*/
		$where = array('where' => array('ga.group_id' => $user_group_id,'ga.is_delete' => '0'),'where_in' => array('date_format(ga.created_date,"%m")' => $month));
		$option= array('join' =>
					array(
						array(
							'table' => TBL_TOPICS.' t',
							'condition' => 't.id = ga.topic_id'
						),
						array(
							'table' => '(SELECT COUNT(*) AS cnt,topic_id FROM '. TBL_TUTORIAL_GROUP_DISCUSSION.' td WHERE is_delete = 0 group by topic_id) topic_count',
							'condition' => 'topic_count.topic_id = t.id'
						),
						array(
							'table' => TBL_TUTORIAL_GROUP_MEMBER_SCORE.' s',
							'condition' => 't.id = s.topic_id and s.member_id ='.$user_id 
						),
						array(
							'table' => TBL_TUTORIAL_TOPIC_EXAM.' e',
							'condition' => 'e.tutorial_topic_id = t.id'
						),
						array(
							'table' => '(SELECT COUNT(*) AS total_question,exam_id FROM '.TBL_EXAM_QUESTION.' group by exam_id) eq',
							'condition' => 'eq.exam_id = e.exam_id'
						),
						array(
							'table' => TBL_STUDENT_EXAM_SCORE.' st_s',
							'condition' => 'st_s.exam_id = e.exam_id and st_s.user_id ='.$user_id
						)

					)
				);
		$select = 't.topic_name,ga.created_date,IF(topic_count.cnt IS NULL,0,topic_count.cnt) AS total_discussion,IF(s.score IS NULL,0,s.score) AS discussion_score,IF(TRUNCATE((st_s.correct_answers * 100)/eq.total_question,2) IS NULL,"0.00",TRUNCATE((st_s.correct_answers * 100)/eq.total_question,2)) AS per';
		$data['my_activities']['topic_allcated'] = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.' ga',$select,$where,$option);

		/* Changed time formate */
		for ($i=0; $i < count($data['my_activities']['topic_allcated']) ; $i++) { 
			$data['my_activities']['topic_allcated'][$i]['created_date'] = get_time_format(date("M d, Y, g:i:s a", strtotime($data['my_activities']['topic_allcated'][$i]['created_date'])));
		}

		/*--------Get my studymates---------*/

		// $studymate = studymates($user_id,false);
		// $where = array('where' => array('u.is_delete' => 0),'where_in'=>array('u.id' => $studymate),'where_in' => array('date_format(sm.created_date,"%m")' => $month));
		// $option = array('join'=>
		// 			array(
		// 				array(
		// 					'table' => TBL_STUDYMATES.' sm',
		// 					'condition' => 'u.id = sm.mate_of and sm.mate_id ='.$user_id
		// 				),
		// 				array(
		// 					'table' => TBL_STUDYMATES.' sm2',
		// 					'condition' => 'u.id = sm2.mate_id and sm2.mate_of ='.$user_id
		// 				),
		// 				array(
		// 					'table' => TBL_STUDENT_ACADEMIC_INFO.' in',
		// 					'condition' => 'u.id = in.user_id'
		// 				),
		// 				array(
		// 					'table' => TBL_SCHOOLS.' s',
		// 					'condition' => 's.id = in.school_id'
		// 				),
		// 				array(
		// 					'table' => TBL_USER_PROFILE_PICTURE.' p',
		// 					'condition' => 'u.id = p.user_id'
		// 				),
		// 				array(
		// 					'table' => TBL_COURSES.' c',
		// 					'condition' => 'c.id = in.course_id'
		// 				)
		// 			),
		// 		);

		$studymate = studymates($user_id,false);
		$where = array('where_in' => array('date_format(sm.created_date,"%m")' => $month),'or_where_in' => array('date_format(sm2.created_date,"%m")' => $month),'where' => array('u.is_delete' => 0));

		$option = array('join'=>
					array(
						array(
							'table' => TBL_STUDYMATES.' sm',
							'condition' => 'u.id = sm.mate_of and sm.is_delete = 0 and sm.mate_id ='.$user_id 
						),
						array(
							'table' => TBL_STUDYMATES.' sm2',
							'condition' => 'u.id = sm2.mate_id and sm2.is_delete = 0 and sm2.mate_of ='.$user_id
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
						),
						array(
							'table' => TBL_COURSES.' c',
							'condition' => 'c.id = in.course_id'
						)
					),
				);
		
		$select ='u.full_name,sm.mate_of,sm2.mate_id,if(sm.created_date is null,sm2.created_date,sm.created_date) as created_date,s.school_name,p.profile_link,c.course_name';
		$data['my_activities']['studymates'] = select(TBL_USERS.' u',$select,$where,$option);

		/* Changed time formate */
		for ($i=0; $i < count($data['my_activities']['studymates']) ; $i++) { 
			$data['my_activities']['studymates'][$i]['created_date'] = get_time_format(date("M d, Y, g:i:s a", strtotime($data['my_activities']['studymates'][$i]['created_date'])));
		}


		/*--------Get my like feed----------*/
		$where = array('where' => array('like.like_by' => $user_id,'like.is_delete' => 0),'where_in' => array('date_format(like.created_date,"%m")' => $month));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEEDS.' like_feed',
								'condition' => 'like_feed.id = like.feed_id'
							),
							array(
								'table' => TBL_USERS.' upost',
								'condition' => 'upost.id = like_feed.feed_by'
							),
							array(
								'table' => TBL_FEED_IMAGE.' fimage',
								'condition' => 'fimage.feed_id = like_feed.id'
							)
							
						),
						'order_by' => 'like.created_date DESC'
					);
		$select = 'upost.full_name AS post_username,upost.id,like_feed.feed_text,fimage.image_link,like.created_date,(SELECT COUNT(*) FROM feed_like where feed_id = like_feed.id) AS totlike,(SELECT COUNT(*) FROM feed_comment WHERE feed_id = like_feed.id) AS totcomment';
		$data['my_activities']['like'] = select(TBL_FEED_LIKE.' like',$select,$where,$options);		
		
		/* Changed time formate */
		for ($i=0; $i < count($data['my_activities']['like']) ; $i++) { 
			$data['my_activities']['like'][$i]['created_date'] = get_time_format(date("M d, Y, g:i:s a", strtotime($data['my_activities']['like'][$i]['created_date'])));
		}



		/*-------Get my comment----------*/
		$where = array('where' => array('comment.comment_by'=>$user_id,'comment.is_delete' => 0),'where_in' => array('date_format(comment.created_date,"%m")' => $month));
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
							),
							array(
								'table' => TBL_FEED_IMAGE.' fimage',
								'condition'=>'comment_feed.id = fimage.feed_id'
							),
							
						),
						'order_by' => 'comment.created_date DESC',
						'group_by' => 'comment_feed.id'
					);
		//$select = 'u.full_name,u.id,comment_feed.feed_text,p.profile_link,fimage.image_link,comment.comment,comment.created_date,(SELECT COUNT(*) FROM feed_like WHERE feed_id = comment_feed.id) AS totlike,(select COUNT(*) FROM feed_comment WHERE feed_id = comment_feed.id AND comment_by = '.$user_id.') AS totcomment,comment_feed.id';
		$select = 'u.full_name,u.id,comment_feed.feed_by,comment.comment_by,comment_feed.feed_text,p.profile_link,fimage.image_link,comment.comment,comment.created_date,(SELECT COUNT(*) FROM feed_like WHERE feed_id = comment_feed.id) AS totlike,(select COUNT(*) FROM feed_comment WHERE feed_id = comment_feed.id AND comment_by = '.$user_id.') AS totcomment,comment_feed.id';
		$data['my_activities']['comment'] = select(TBL_FEED_COMMENT.' comment',$select,$where,$options);

		/* Changed time formate */
		for ($i=0; $i < count($data['my_activities']['comment']) ; $i++) { 
			$data['my_activities']['comment'][$i]['created_date'] = get_time_format(date("M d, Y, g:i:s a", strtotime($data['my_activities']['comment'][$i]['created_date'])));
		}


		/*-------Get my post--------*/
		$where = array('where' => array('post.feed_by' => $user_id,'post.is_delete' => 0),'where_in' => array('date_format(post.created_date,"%m")' => $month));
		$options = array('join' =>
						array(
							array(
								'table' => TBL_FEED_IMAGE.' fimage',
								'condition' => 'fimage.feed_id = post.id'
							)
						),
					'order_by' => 'post.created_date DESC'
				);
		$select = 'fimage.image_link,post.feed_text,(SELECT COUNT(*) FROM feed_like WHERE feed_id = post.id) AS totlike,(SELECT COUNT(*) FROM feed_comment WHERE feed_id = post.id) AS totcomment,post.created_date';
		$data['my_activities']['post'] = select(TBL_FEEDS.' post',$select,$where,$options);
		/* Changed time formate */
		for ($i=0; $i < count($data['my_activities']['post']) ; $i++) { 
			$data['my_activities']['post'][$i]['created_date'] = get_time_format(date("M d, Y, g:i:s a", strtotime($data['my_activities']['post'][$i]['created_date'])));
		}

		
		$data['my_month'] 		=	date('Y-m'); 
		$data['new_my_month'] 	= 	date('Y-m',strtotime('-1 month',strtotime(date('Y-m'))));

		$this->template->load('student/default','student/my_activities',$data);

	}
}
