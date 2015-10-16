<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class Classroom extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();

        $this->load->helper(array('csv', 'file'));
        $this->load->library(array('zip'));
        $this->data['cur_url'] = $this->session->userdata('cur_url');
        $this->data['prev_url'] = $this->session->userdata('prev_url');
    }

    /* ---------------------------- Classroom Module Start -------------------------------------------- */

    /**
     * function index() have all users listing using codeigniter pagination limit of 15 users per page
     *
     * */
    public function index() {

        $this->data['page_title'] = 'Class Rooms';

        if (!empty($_GET['q']) || !empty($_GET['course_name'])) {
            if (!empty($_GET['q'])) {
                $q = $this->input->get('q');
            }

            if (!empty($_GET['course_name'])) {
                $course_name = $this->input->get('course_name');
            }

            $str = '';
            if (!empty($course_name)) {
                $where['where'][TBL_CLASSROOMS . '.course_id'] = $course_name;
                $str .='&course_name=' . $course_name;
            }
            if (!empty($q)) {

                $where['like'][TBL_CLASSROOMS . '.class_name'] = $q;
                $where['or_like'][TBL_CLASSROOMS . '.class_nickname'] = $q;
                $where['or_like'][TBL_COURSES . '.course_name'] = $q;
                $str.='&q=' . $q;
            }

            $str = trim($str, '&');

            if (!empty($str)) {
                $config['base_url'] = base_url() . 'admin/classroom/index?' . $str;
            } else {
                $config['base_url'] = base_url() . 'admin/classroom/index';
            }
            $config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
            $offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'	
        } else {
            $where = null;
            $where['where'][TBL_CLASSROOMS . '.is_delete'] = FALSE;

            $config['base_url'] = base_url() . 'admin/classroom/index';
            $offset = $this->uri->segment(4);
        }

        $config['uri_segment'] = 4;
        $config['num_links'] = 5;
        $config['total_rows'] = select(TBL_CLASSROOMS, FALSE, $where, array(
            'count' => TRUE,
            'join' => array(
                array(
                    'table' => TBL_COURSES,
                    'condition' => TBL_COURSES . '.id = ' . TBL_CLASSROOMS . '.course_id'
                )
            )
                )
        );

        $config['per_page'] = 15;

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

        //fetch all data of users joins with cities,states,countries 
        $this->data['all_classrooms'] = select(TBL_CLASSROOMS, TBL_CLASSROOMS . '.id,'
                . TBL_CLASSROOMS . '.class_name,'
                . TBL_CLASSROOMS . '.class_nickname,'
                . TBL_CLASSROOMS . '.is_delete,'
                . TBL_COURSES . '.course_name', $where, array(
            'limit' => $config['per_page'],
            'offset' => $offset,
            'join' => array(
                array(
                    'table' => TBL_COURSES,
                    'condition' => TBL_COURSES . '.id = ' . TBL_CLASSROOMS . '.course_id'
                )
            )
                )
        );

        $this->data['courses'] = select(TBL_COURSES, FALSE, array('where' => array('is_delete' => FALSE)));
        $this->pagination->initialize($config);

        $this->template->load('admin/default', 'admin/classroom/view_classroom', $this->data);
    }

    /**
     * function add() have Classroom Registration form to Add user by Admin using codeigniter validation
     *
     * */
    public function add() {

        $this->data['page_title'] = 'Class Room Add';

        $this->data['courses'] = select(TBL_COURSES, FALSE, array('where' => array('is_delete' => FALSE)), array('order_by' => 'course_name'));

        $this->form_validation->set_rules('class_name', 'Class Name', 'trim|required|is_unique[classrooms.class_name]');

        if ($this->form_validation->run() == FALSE) {

            $this->template->load('admin/default', 'admin/classroom/add_classroom', $this->data);
        } else {

            $data = array(
                "class_name" => $this->input->post("class_name"),
                "class_nickname" => $this->input->post("class_nickname"),
                "course_id" => $this->input->post("course_id"),
                "created_date" => date('Y-m-d H:i:s'),
                "modified_date" => date('Y-m-d H:i:s'),
                "is_delete" => 0,
            );

            insert(TBL_CLASSROOMS, $data);  // insert data into database using common_model.php and cms_helper.php

            $this->session->set_flashdata('success', 'Record is Successfully created.');
            redirect('admin/classroom');
        }
    }

    /**
     * function update() have Form of user rgisteration with fill up data with userdata of given ID
     * 	@param Classroom ID
     * @author Virendra Patel - Spark ID- VPA
     * */
    public function update($id) {

        $this->data['page_title'] = 'Class Room Update';

        if (empty($id) && !is_numeric($id)) {
            redirect('admin');
        }

        $this->data['classroom'] = select(TBL_CLASSROOMS, FALSE, array('where' => array('id' => $id)), array('single' => TRUE));
        $this->data['courses'] = select(TBL_COURSES, FALSE, array('where' => array('is_delete' => FALSE)), array('order_by' => 'course_name'));

        if ($_POST) {
            $class_name = $this->input->post('class_name');

            if ($this->data['classroom']['class_name'] !== $class_name) {
                $class_rule = 'trim|required|is_unique[classrooms.class_name]';
            } else {
                $class_rule = 'trim|required';
            }
        } else {
            $class_rule = 'trim|required|alpha_numeric';
        }

        $this->form_validation->set_rules('class_name', 'Class Name', $class_rule);

        if ($this->form_validation->run() == FALSE) {

            $this->template->load('admin/default', 'admin/classroom/update_classroom', $this->data);
        } else {

            $data = array(
                "class_name" => $this->input->post("class_name"),
                "class_nickname" => $this->input->post("class_nickname"),
                "course_id" => $this->input->post("course_id"),
                "modified_date" => date('Y-m-d H:i:s'),
            );

            update(TBL_CLASSROOMS, $id, $data); // Update data using common_model.php and cms_helper.php
            $this->session->set_flashdata('success', 'Record is Successfully updated.');
            redirect($this->data['prev_url']); // Redirect to previous page set in ADMIN_Controller.php
        }
    }

    // ---------------------------- Classroom Module END --------------------------------------------
}

/* End of file Classroom.php */
/* Location: ./application/controllers/Admin/Classroom.php */