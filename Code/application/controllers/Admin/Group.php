<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Group extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();
        $this->load->helper(array('file'));
        $this->load->library(array('zip', 'pagination'));
        $this->data['cur_url'] = $this->session->userdata('cur_url');
        $this->data['prev_url'] = $this->session->userdata('prev_url');
    }

    // ---------------------------- Group Module Start --------------------------------------------

    /**
     * function index() have all Group listing using codeigniter pagination library limit of 10 groups per page
     *
     * */
    public function index() {

        $this->data['page_title'] = 'Groups';
        $where['where'][TBL_TUTORIAL_GROUPS . '.is_completed'] = 1;
        $where['where'][TBL_TUTORIAL_GROUPS . '.is_delete'] = 0;
        if (!empty($_GET['course']) || !empty($_GET['year']) || !empty($_GET['q'])) {

            if (!empty($_GET['course'])) {
                $course = $this->input->get('course');
            }
            if (!empty($_GET['q'])) {
                $q = $this->input->get('q');
            }
            if (!empty($_GET['year'])) {
                $year = $this->input->get('year');
            }

            $str = '';

            if (!empty($course)) {
                $where['where'][TBL_STUDENT_ACADEMIC_INFO . '.course_id'] = $course;
                $str .='&course=' . $course;
            }
            if (!empty($q)) {
                $where['like'][TBL_TUTORIAL_GROUPS . '.group_name'] = $q;
                $where['or_like'][TBL_USERS . '.username'] = $q;
                $str .='&q=' . $q;
            }
            if (!empty($year)) {
                $next_year = $year + 1;
                $academic_year = "$year-$next_year";    // find next year and create string like 2015-2016
                $where['where'][TBL_STUDENT_ACADEMIC_INFO . '.academic_year'] = $academic_year;
                $str .='&year=' . $year;
            }

            $str = trim($str, '&');

            $config['base_url'] = base_url() . 'admin/group/index?' . $str;
            $config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
            $offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
        } else {
            $config['base_url'] = base_url() . 'admin/group/index';
            $offset = $this->uri->segment(4);
        }

        $config['uri_segment'] = 4;
        $config['num_links'] = 5;
        $config['total_rows'] = count(select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUPS . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.group_type,' .
                        TBL_TUTORIAL_GROUPS . '.group_status,' . TBL_TUTORIAL_GROUPS . '.is_completed,' . TBL_COURSES . '.course_name,' .
                        TBL_COURSES . '.id as course_id', $where, array(
            'group_by' => array(TBL_TUTORIAL_GROUP_MEMBER . '.group_id'),
            'join' => array(
                array(
                    'table' => TBL_TUTORIAL_GROUP_MEMBER,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.group_id',
                    'join' => 'right'
                ),
                array(
                    'table' => TBL_USERS,
                    'condition' => TBL_USERS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id',
                ),
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO,
                    'condition' => TBL_USERS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.user_id',
                ),
                array(
                    'table' => TBL_COURSES,
                    'condition' => TBL_COURSES . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.course_id',
                )
            )
                        )
        ));

        $config['per_page'] = 12;

        $config['full_tag_open'] = '<ul class="pagination pagination_admin">';
        $config['full_tag_close'] = '</ul>';

        $config['num_tag_open'] = '<li>';
        $config['num_tag_close'] = '</li>';

        $config['first_link'] = 'First';
        $config['first_tag_open'] = '<li>';
        $config['first_tag_close'] = '</li>';

        $config['cur_tag_open'] = '<li style="display:none"></li><li class="active"><a>';
        $config['cur_tag_close'] = '</a></li><li style="display:none"></li>';

        $config['prev_link'] = '&laquo;';
        $config['prev_tag_open'] = '<li>';
        $config['prev_tag_close'] = '</li>';

        $config['next_link'] = '&raquo;';
        $config['next_tag_open'] = '<li>';
        $config['next_tag_close'] = '</li>';

        $config['last_link'] = 'Last';
        $config['last_tag_open'] = '<li>';
        $config['last_tag_close'] = '</li>';

        $this->data['all_groups'] = select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUPS . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.group_type,' .
                TBL_TUTORIAL_GROUPS . '.group_status,' . TBL_TUTORIAL_GROUPS . '.is_completed,'. TBL_TUTORIAL_GROUPS . '.is_delete,' . TBL_COURSES . '.course_name,' .
                TBL_COURSES . '.id as course_id,' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_score', $where, array(
            'limit' => $config['per_page'],
            'offset' => $offset,
            'group_by' => array(TBL_TUTORIAL_GROUP_MEMBER . '.group_id'),
            'order_by' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_score DESC',
            'join' => array(
                array(
                    'table' => TBL_TUTORIAL_GROUP_MEMBER,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.group_id',
                    'join' => 'right'
                ),
                array(
                    'table' => TBL_USERS,
                    'condition' => TBL_USERS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id',
                ),
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO,
                    'condition' => TBL_USERS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.user_id',
                ),
                array(
                    'table' => TBL_COURSES,
                    'condition' => TBL_COURSES . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.course_id',
                ),
                array(
                    'table' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id'
                )
            )
                )
        );

        //fetch all data of group right joins with tutorial group members
        $this->data['all_groups_members'] = select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUP_MEMBER . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.id as gid,' .
                TBL_USERS . '.username,' . TBL_SCHOOLS . '.school_name,' . TBL_CLASSROOMS . '.class_name,' . TBL_USER_PROFILE_PICTURE . '.profile_link,' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id', FALSE, array(
            'join' => array(
                array(
                    'table' => TBL_TUTORIAL_GROUP_MEMBER,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.group_id',
                ),
                array(
                    'table' => TBL_USERS,
                    'condition' => TBL_USERS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id',
                ),
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO,
                    'condition' => TBL_USERS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.user_id',
                ),
                array(
                    'table' => TBL_SCHOOLS,
                    'condition' => TBL_SCHOOLS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.school_id',
                ),
                array(
                    'table' => TBL_CLASSROOMS,
                    'condition' => TBL_CLASSROOMS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.classroom_id',
                ),
                array(
                    'table' => TBL_USER_PROFILE_PICTURE,
                    'condition' => TBL_USER_PROFILE_PICTURE . '.user_id=' . TBL_USERS . '.id'
                )
            )
                )
        );

        $this->pagination->initialize($config);
        $this->data['courses'] = select(TBL_COURSES, FALSE, array('where' => array('is_delete' => FALSE)), array('limit' => 10));
        $this->template->load('admin/default', 'admin/group/view_group', $this->data);
    }

    public function performance($gid) {

        $where['where'][TBL_TUTORIAL_GROUPS . '.id'] = $gid;

        $this->data['all_groups'] = select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUPS . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.group_type,' .
                TBL_TUTORIAL_GROUPS . '.group_status,' . TBL_TUTORIAL_GROUPS . '.is_completed,' . TBL_COURSES . '.course_name,' .
                TBL_COURSES . '.id as course_id', $where, array(
            'single' => TRUE,
            'group_by' => array(TBL_TUTORIAL_GROUP_MEMBER . '.group_id'),
            'join' => array(
                array(
                    'table' => TBL_TUTORIAL_GROUP_MEMBER,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.group_id',
                    'join' => 'right'
                ),
                array(
                    'table' => TBL_USERS,
                    'condition' => TBL_USERS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id',
                ),
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO,
                    'condition' => TBL_USERS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.user_id',
                ),
                array(
                    'table' => TBL_COURSES,
                    'condition' => TBL_COURSES . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.course_id',
                )
            )
                )
        );

        //fetch all data of group right joins with tutorial group members
        $this->data['all_groups_members'] = select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUP_MEMBER . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.id as gid,' .
                TBL_USERS . '.username,' . TBL_SCHOOLS . '.school_name,' . TBL_CLASSROOMS . '.class_name,' . TBL_USER_PROFILE_PICTURE . '.profile_link,' .
                TBL_TUTORIAL_GROUP_MEMBER . '.user_id,' . TBL_USERS . '.created_date,' . TBL_TUTORIAL_GROUP_MEMBER_SCORE . '.score', $where, array(
            'group_by' => array(TBL_USERS . '.id'),
            'join' => array(
                array(
                    'table' => TBL_TUTORIAL_GROUP_MEMBER,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.group_id',
                ),
                array(
                    'table' => TBL_USERS,
                    'condition' => TBL_USERS . '.id = ' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id',
                ),
                array(
                    'table' => TBL_STUDENT_ACADEMIC_INFO,
                    'condition' => TBL_USERS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.user_id',
                ),
                array(
                    'table' => TBL_SCHOOLS,
                    'condition' => TBL_SCHOOLS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.school_id',
                ),
                array(
                    'table' => TBL_CLASSROOMS,
                    'condition' => TBL_CLASSROOMS . '.id = ' . TBL_STUDENT_ACADEMIC_INFO . '.classroom_id',
                ),
                array(
                    'table' => TBL_USER_PROFILE_PICTURE,
                    'condition' => TBL_USER_PROFILE_PICTURE . '.user_id=' . TBL_TUTORIAL_GROUP_MEMBER . '.user_id'
                ),
                array(
                    'table' => TBL_TUTORIAL_GROUP_MEMBER_SCORE,
                    'condition' => TBL_TUTORIAL_GROUP_MEMBER_SCORE . '.member_id=' . TBL_TUTORIAL_GROUP_MEMBER . '.id'
                ),
            )
                )
        );


        $this->data['all_groups_topics'] = select(TBL_TUTORIAL_GROUPS, TBL_TUTORIAL_GROUPS . '.id,' . TBL_TUTORIAL_GROUPS . '.group_name,' . TBL_TUTORIAL_GROUPS . '.group_type,' .
                TBL_TUTORIAL_GROUPS . '.group_status,' . TBL_TUTORIAL_GROUPS . '.is_completed,' .
                TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_score,' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.topic_id,' .
                TBL_TUTORIAL_TOPIC . '.topic_name,COUNT(' . TBL_TUTORIAL_GROUP_DISCUSSION . '.id) as total', $where, array(
            'group_by' => array(TBL_TUTORIAL_TOPIC . '.id'),
            'join' => array(
                array(
                    'table' => TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,
                    'condition' => TBL_TUTORIAL_GROUPS . '.id = ' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.group_id',
                    'join' => 'right'
                ),
                array(
                    'table' => TBL_TUTORIAL_TOPIC,
                    'condition' => TBL_TUTORIAL_TOPIC . '.id = ' . TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION . '.topic_id',
                ),
                array(
                    'table' => TBL_TUTORIAL_GROUP_DISCUSSION,
                    'condition' => TBL_TUTORIAL_GROUP_DISCUSSION . '.topic_id=' . TBL_TUTORIAL_TOPIC . '.id',
                    'join' => 'right'
                )
            )
                )
        );
/*$where = "`".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION."`.`group_id` = $gid AND `".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION."`.`created_date` >= DATE_SUB(NOW(), INTERVAL 6 month)";
                                
        $group_performance = select(TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION,TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".group_id,".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".topic_id,".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".group_score,".TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION.".created_date,",$where,FALSE);
           foreach ($group_performance as $group) {
              
           }
    p($performance,true);   */   
        $this->template->load('admin/default', 'admin/group/performance', $this->data);
    }

    /**
     * function Blocked will block user for temporary set database field 'user_status' of `users` table set to 
     * 'blocked' and redirect to user listing page
     *
     * */

    public function blocked($id) {
        update(TBL_TUTORIAL_GROUPS, $id, array('group_status' => 'blocked', 'modified_date' => date('Y-m-d H:i:s', time())));
        $this->session->set_flashdata('success', 'Group is Successfully Blocked.');
        redirect('admin/group');
    }

    /**
     * function active will activate user for temporary set database field 'user_status' of `users` table set to 
     * 'active' and redirect to user listing page
     *
     *
    */

    public function active($id) {
        update(TBL_TUTORIAL_GROUPS, $id, array('group_status' => 'active', 'modified_date' => date('Y-m-d H:i:s', time())));
        $this->session->set_flashdata('success', 'Group is Successfully Blocked.');
        redirect('admin/group');
    }

    /**
     * function send_message will used to send message from admin to other user tables-(messages,messages_receiver) 
     * 	admin can send messages to one or more than one users at a time
     * */
    public function send_message($id) {

        $this->data['page_title'] = 'User Send Message';

        if (empty($id) && !is_numeric($id)) {
            redirect('admin');
        }

        $group_members = select(TBL_TUTORIAL_GROUP_MEMBER, FALSE, array('where' => array('group_id' => $id)));

        $my_users = array();

        foreach ($group_members as $members) {
            array_push($my_users, $members['user_id']);
        }

        $this->data['group_members'] = $my_users;

        $this->data['templates'] = select(TBL_MESSAGES, FALSE, array('where' => array('is_template' => '1')));
        $this->data['users'] = select(TBL_USERS, TBL_USERS . '.username,' . TBL_USERS . '.id,' . TBL_ROLES . '.role_name,' . TBL_ROLES . '.id as rid', array(
            'where' => array(TBL_USERS . '.is_delete' => FALSE),
            'where_not_in' => array(TBL_USERS . '.user_status' => array('blocked'))
                ), array(
            'order_by' => TBL_USERS . '.username',
            'join' => array(
                array(
                    'table' => 'roles',
                    'condition' => TBL_USERS . '.role_id=' . TBL_ROLES . '.id'
                )
            )
                )
        );

        if (count($this->input->post('all_users')) == 0) {
            $this->form_validation->set_rules('all_users', 'Users', 'trim|required');
        }

        $this->data['roles'] = select(TBL_ROLES, FALSE, array('where' => array('is_delete' => FALSE)));

        $this->form_validation->set_rules('message_title', 'Message Title', 'trim|required|alpha_numeric_spaces');
        $this->form_validation->set_rules('message_desc', 'Message', 'trim|required');

        if ($this->form_validation->run() == FALSE) {

            $this->template->load('admin/default', 'admin/group/send_message', $this->data);
        } else {

            $all_users = $this->input->post('all_users');

            $msg_title = $this->input->post('message_title');
            $msg_text = $this->input->post('message_desc');

            $db_template = select(TBL_MESSAGES, FALSE, array('where' => array('is_template' => '1')));


            $cnt = 0;
            if (isset($_POST['save_template'])) {
                foreach ($db_template as $db_temp) {

                    if ($db_temp['message_title'] === $msg_title) {
                        $cnt++;
                    }
                }
            }

            if ($cnt != 0) {
                $this->session->set_flashdata('msgerror', 'Message template should be Unique.');
                redirect('admin/user/send_message/' . $id);
            }

            $template_counter = 0;

            if (!empty($all_users)) {

                foreach ($all_users as $user) {

                    if (isset($_POST['save_template']) && $template_counter != 1) {
                        $template_counter = 1;
                        $template = '1';
                    } else {
                        $template = '0';
                    }
                    $data = array(
                        'message_text' => $msg_text,
                        'sender_id' => $this->session->userdata('id'),
                        'message_title' => $msg_title,
                        'status' => '1',
                        'reply_for' => '0',
                        'created_date' => date('Y-m-d H:i:s', time()),
                        'modified_date' => '0000-00-00 00:00:00',
                        'is_template' => $template,
                        'is_delete' => '0',
                        'is_testdata' => 'yes'
                    );

                    //insert data into messages table
                    $message_id = insert(TBL_MESSAGES, $data);

                    $data_message_receiver = array(
                        'message_id' => $message_id,
                        'receiver_id' => $user,
                        'created_date' => date('Y-m-d H:i:s', time()),
                        'modified_date' => '0000-00-00 00:00:00',
                        'is_delete' => '0',
                        'is_testdata' => 'yes'
                    );

                    // insert data into messages_receiver table using message id from message table
                    insert(TBL_MESSAGE_RECEIVER, $data_message_receiver);

                    $user_mail = select(TBL_USERS, 'email_id', array('where' => array('id' => $user)), array('single' => TRUE));

                    if (!empty($user_mail['email_id'])) {

                        $config = mail_config(); // set configuration for email from email_helper.php

                        $this->email->initialize($config);
                        $this->load->library('email', $config);
                        $this->email->from('admin@admin.com', 'Admin');
                        $this->email->to($user_mail['email_id']);

                        $this->email->subject($msg_title);
                        $this->email->message($msg_text);

                        $this->email->send();
                    }
                }
            }

            $this->session->set_flashdata('success', 'Message has been Successfully sent.');
            redirect($this->data['prev_url']);
        }
    }

    public function send_messages() {

        $this->data['page_title'] = 'Users Send Messages';

        if ($_POST) {

            if (isset($_POST['all_users'])) {
                $this->data['post_users'] = $this->input->post('all_users[]');
                $this->data['my_cnt'] = 1;
                $this->form_validation->set_rules('all_users[]', 'Users', 'trim|required');
            } elseif (isset($_POST['message_title'])) {
                $this->data['my_cnt'] = 1;
                $this->form_validation->set_rules('all_users[]', 'Users', 'trim|required');
            } else {

                $this->data['post_users'] = array();
                $groups = $this->input->post('group_messages');

                $my_users = array();

                if (!empty($groups)) {
                    foreach ($groups as $group) {
                        $group_members = select(TBL_TUTORIAL_GROUP_MEMBER, FALSE, array('where' => array('group_id' => $group)));

                        foreach ($group_members as $members) {
                            array_push($my_users, $members['user_id']);
                        }
                    }
                }

                $this->data['post_users'] = $my_users;
                $this->data['my_cnt'] = 0;
                $this->form_validation->set_rules('all_users[]', 'Users', 'trim');
            }
        } else {
            $this->data['post_users'] = array();
        }

        $this->data['templates'] = select(TBL_MESSAGES, FALSE, array('where' => array('is_template' => '1')));
        $this->data['users'] = select(TBL_USERS, TBL_USERS . '.username,' . TBL_USERS . '.id,' . TBL_ROLES . '.role_name,' . TBL_ROLES . '.id as rid', array(
            'where' => array(TBL_USERS . '.is_delete' => FALSE),
            'where_not_in' => array(TBL_USERS . '.user_status' => array('blocked'))
                ), array(
            'order_by' => TBL_USERS . '.username',
            'join' => array(
                array(
                    'table' => 'roles',
                    'condition' => TBL_USERS . '.role_id=' . TBL_ROLES . '.id'
                )
            )
                )
        );

        $this->data['roles'] = select(TBL_ROLES, FALSE, array('where' => array('is_delete' => FALSE)));

        $this->form_validation->set_rules('message_title', 'Message Title', 'trim|required|alpha_numeric_spaces');
        $this->form_validation->set_rules('message_desc', 'Message', 'trim|required');

        if ($this->form_validation->run() == FALSE) {

            $this->template->load('admin/default', 'admin/group/send_messages', $this->data);
        } else {

            $all_users = $this->input->post('all_users');

            $msg_title = $this->input->post('message_title');
            $msg_text = $this->input->post('message_desc');

            $db_template = select(TBL_MESSAGES, FALSE, array('where' => array('is_template' => '1')));


            $cnt = 0;
            if (isset($_POST['save_template'])) {
                foreach ($db_template as $db_temp) {
                    echo $db_temp['message_title'] . '<br/>';
                    if ($db_temp['message_title'] === $msg_title) {
                        $cnt++;
                    }
                }
            }

            if ($cnt != 0) {
                $this->session->set_flashdata('msgerror', 'Message template should be Unique.');
                redirect('admin/user/send_messages');
            }

            $template_counter = 0;

            if (!empty($all_users)) {

                foreach ($all_users as $user) {

                    if (isset($_POST['save_template']) && $template_counter != 1) {
                        $template_counter = 1;
                        $template = '1';
                    } else {
                        $template = '0';
                    }

                    $data = array(
                        'message_text' => $msg_text,
                        'sender_id' => $this->session->userdata('id'),
                        'message_title' => $msg_title,
                        'status' => '1',
                        'reply_for' => '0',
                        'created_date' => date('Y-m-d H:i:s', time()),
                        'modified_date' => '0000-00-00 00:00:00',
                        'is_template' => $template,
                        'is_delete' => '0',
                        'is_testdata' => 'yes'
                    );

                    //insert data into messages table
                    $message_id = insert(TBL_MESSAGES, $data);

                    $data_message_receiver = array(
                        'message_id' => $message_id,
                        'receiver_id' => $user,
                        'created_date' => date('Y-m-d H:i:s', time()),
                        'modified_date' => '0000-00-00 00:00:00',
                        'is_delete' => '0',
                        'is_testdata' => 'yes'
                    );

                    // insert data into messages_receiver table using message id from message table
                    insert(TBL_MESSAGE_RECEIVER, $data_message_receiver);

                    $user_mail = select(TBL_USERS, 'email_id', array('where' => array('id' => $user)), array('single' => TRUE));

                    if (!empty($user_mail['email_id'])) {

                        $config = mail_config();

                        $this->email->initialize($config);
                        $this->load->library('email', $config);
                        $this->email->from('admin@admin.com', 'Admin');
                        $this->email->to($user_mail['email_id']);

                        $this->email->subject($msg_title);
                        $this->email->message($msg_text);

                        $this->email->send();
                    }
                }

                $this->session->set_flashdata('success', 'Messages has been Successfully sent.');
                redirect($this->data['prev_url']);
            }
        }
    }

    // ---------------------------- Group Module END --------------------------------------------
}

/* End of file Group.php */
/* Location: ./application/controllers/Admin/Group.php */