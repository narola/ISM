<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class School extends ADMIN_Controller {

    public $data = array();

    public function __construct() {
        parent::__construct();

        $this->load->helper(array('csv', 'file'));
        $this->load->library(array('zip'));
        $this->data['cur_url'] = $this->session->userdata('cur_url');
        $this->data['prev_url'] = $this->session->userdata('prev_url');
    }

    /* ---------------------------- School Module Start -------------------------------------------- */

    /**
     * function index() have all users listing using codeigniter pagination limit of 15 users per page
     *
     * */
    public function index() {

        $order = '';
        $where['where'][TBL_SCHOOLS . '.is_delete'] = FALSE;
        
        $this->data['page_title'] = 'Schools';

        if (!empty($_GET['q']) || !empty($_GET['school_grade']) || !empty($_GET['order'])) {
            
            if (!empty($_GET['q'])) {$q = $this->input->get('q');}
            if (!empty($_GET['school_grade'])) {$school_grade = $this->input->get('school_grade');}
            if( !empty($_GET['order']) ) { $order = $this->input->get('order'); }       
            
            $str = '';
            if (!empty($school_grade)) {
                $where['where'][TBL_SCHOOLS . '.school_grade'] = $school_grade;
                $str .='&school_grade=' . $school_grade;
            }
            if (!empty($q)) {
                $where['like'][TBL_SCHOOLS . '.school_name'] = $q;
                $str.='&q=' . $q;
            }

            if($order == 'name_asc'){ $order = TBL_SCHOOLS.".school_name asc"; $str.='&order=name_asc';  }
            if($order == 'name_desc'){ $order = TBL_SCHOOLS.".school_name desc"; $str.='&order=name_desc'; }
            if($order == 'latest'){ $order = TBL_SCHOOLS.".created_date desc"; $str.='&order=latest'; }
            if($order == 'older'){ $order = TBL_SCHOOLS.".created_date asc"; $str.='&order=older'; }

            $str = trim($str, '&');

            if (!empty($str)) {
                $config['base_url'] = base_url() . 'admin/school/index?' . $str;
            } else {
                $config['base_url'] = base_url() . 'admin/school/index';
            }

            $config['page_query_string'] = TRUE;   // Set pagination Query String to TRUE 
            $offset = $this->input->get('per_page');  // Set Offset from GET method id of 'per_page'    

        } else {

            $config['base_url'] = base_url() . 'admin/school/index';
            $offset = $this->uri->segment(4);
            
        }

        $config['uri_segment'] = 4;
        $config['num_links'] = 5;
        $config['total_rows'] = select(TBL_SCHOOLS, FALSE, $where, array(
            'count' => TRUE,
            'join' => array(
                array(
                    'table' => TBL_CITIES,
                    'condition' => TBL_CITIES . '.id = ' . TBL_SCHOOLS . '.city_id'
                ),
                array(
                    'table' => TBL_STATES,
                    'condition' => TBL_STATES . '.id = ' . TBL_SCHOOLS . '.state_id'
                ),
                array(
                    'table' => TBL_COUNTRIES,
                    'condition' => TBL_COUNTRIES . '.id = ' . TBL_SCHOOLS . '.country_id'
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
        $this->data['all_schools'] = select(TBL_SCHOOLS, TBL_SCHOOLS . '.id,'
                . TBL_SCHOOLS . '.school_name,'
                . TBL_SCHOOLS . '.principal_name,'
                . TBL_SCHOOLS . '.school_type,'
                . TBL_SCHOOLS . '.school_grade,'
                . TBL_SCHOOLS . '.address,'
                . TBL_SCHOOLS . '.school_contact_no1,'
                . TBL_SCHOOLS . '.school_contact_no2,'
                . TBL_SCHOOLS . '.is_delete,'
                . TBL_CITIES . '.city_name,'
                . TBL_STATES . '.state_name,'
                . TBL_COUNTRIES . '.country_name', $where, array(
            'order_by'=>$order,
            'limit' => $config['per_page'],
            'offset' => $offset,
            'join' => array(
                array(
                    'table' => TBL_CITIES,
                    'condition' => TBL_CITIES . '.id = ' . TBL_SCHOOLS . '.city_id'
                ),
                array(
                    'table' => TBL_STATES,
                    'condition' => TBL_STATES . '.id = ' . TBL_SCHOOLS . '.state_id'
                ),
                array(
                    'table' => TBL_COUNTRIES,
                    'condition' => TBL_COUNTRIES . '.id = ' . TBL_SCHOOLS . '.country_id'
                )
            )
                )
        );

        $this->db->distinct('school_grade');
        $this->data['school_grade'] = select(TBL_SCHOOLS, 'school_grade', array('where' => array('is_delete' => FALSE)), array('order_by' => 'school_grade'));

        $this->pagination->initialize($config);

        $this->template->load('admin/default', 'admin/school/view_school', $this->data);
    }

    /**
     * function add() have School Registration form to Add user by Admin using codeigniter validation
     *
     * */
    public function add() {

        $this->data['page_title'] = 'School Add';

        $this->data['countries'] = select(TBL_COUNTRIES, "", array('order_by' => 'country_name'));
        $this->data['states'] = select(TBL_STATES, FALSE, array('order_by' => 'state_name'));
        $this->data['cities'] = select(TBL_CITIES, FALSE, array('order_by' => 'city_name'));
        $this->data['districts'] = select(TBL_DISTRICTS, FALSE, array('order_by' => 'district_name'));

        $this->form_validation->set_rules('schoolname', 'School Name', 'trim|required|is_unique[schools.school_name]');
        $this->form_validation->set_rules('school_code', 'School Code', 'trim|required|numeric');
        $this->form_validation->set_rules('school_email_id', 'Email', 'trim|required|valid_email|is_unique[schools.school_email_id]');
        $this->form_validation->set_rules('contact_1', 'School contact no-1', 'trim|required|regex_match[/[0-9-]$/]', array('regex_match' => 'The {field} should have only numbers and - special character only.'));
        $this->form_validation->set_rules('contact_2', 'School contact no-2', 'trim|regex_match[/[0-9-]$/]', array('regex_match' => 'The {field} should have only numbers and - special character only.'));
        $this->form_validation->set_rules('country', 'Country', 'trim|required');
        $this->form_validation->set_rules('state', 'State', 'trim|required');
        $this->form_validation->set_rules('city', 'City', 'trim|required');

        if ($this->form_validation->run() == FALSE) {

            $this->template->load('admin/default', 'admin/school/add_school', $this->data);
        } else {

            $data = array(
                "school_name" => $this->input->post("schoolname"),
                "school_nickname" => $this->input->post("school_nickname"),
                "principal_name" => $this->input->post("principal_name"),
                "school_code" => $this->input->post("school_code"),
                "school_type" => $this->input->post("school_type"),
                "school_email_id" => $this->input->post("school_email_id"),
                "school_contact_no1" => $this->input->post("contact_1"),
                "school_contact_no2" => $this->input->post("contact_2"),
                "school_grade" => $this->input->post("grade"),
                "school_mode" => $this->input->post("mode"),
                "address" => $this->input->post("address"),
                "district_id" => $this->input->post("district"),
                "city_id" => $this->input->post("city"),
                "state_id" => $this->input->post("state"),
                "country_id" => $this->input->post("country"),
                "created_date" => date('Y-m-d H:i:s'),
                "modified_date" => date('Y-m-d H:i:s'),
                "is_delete" => 0,
            );

            insert(TBL_SCHOOLS, $data);  // insert data into database using common_model.php and cms_helper.php

            $this->session->set_flashdata('success', 'Record is Successfully created.');
            redirect('admin/school');
        }
    }

    /**
     * function update() have Form of user rgisteration with fill up data with userdata of given ID
     * 	@param School ID
     * @author Virendra Patel - Spark ID- VPA
     * */
    public function update($id) {

        $this->data['page_title'] = 'School Update';

        if (empty($id) && !is_numeric($id)) {
            redirect('admin');
        }

        $this->data['school'] = select(TBL_SCHOOLS, FALSE, array('where' => array('id' => $id)), array('single' => TRUE));
        $this->data['countries'] = select(TBL_COUNTRIES, "", array('order_by' => 'country_name'));
        $this->data['states'] = select(TBL_STATES, FALSE, array('order_by' => 'state_name'));
        $this->data['cities'] = select(TBL_CITIES, FALSE, array('order_by' => 'city_name'));
        $this->data['districts'] = select(TBL_DISTRICTS, FALSE, array('order_by' => 'district_name'));

        if ($_POST) {
            $schoolname = $this->input->post('schoolname');
            $school_email_id = $this->input->post('school_email_id');


            if ($this->data['school']['school_name'] !== $schoolname) {
                $school_rule = 'trim|required|is_unique[schools.school_name]';
            } else {
                $school_rule = 'trim|required';
            }

            if ($this->data['school']['school_email_id'] !== $school_email_id) {
                $email_rule = 'trim|required|is_unique[schools.school_email_id]|valid_email';
            } else {
                $email_rule = 'trim|required|valid_email';
            }
        } else {
            $school_rule = 'trim|required|alpha_numeric';
            $email_rule = 'trim|valid_email';
        }

        $this->form_validation->set_rules('schoolname', 'School Name', $school_rule);
        $this->form_validation->set_rules('school_code', 'School Code', 'trim|required|numeric');
        $this->form_validation->set_rules('school_email_id', 'Email', $email_rule);
        $this->form_validation->set_rules('contact_1', 'School contact no-1', 'trim|required|regex_match[/[0-9-]$/]', array('regex_match' => 'The {field} should have only numbers and - special character only.'));
        $this->form_validation->set_rules('contact_2', 'School contact no-2', 'trim|regex_match[/[0-9-]$/]', array('regex_match' => 'The {field} should have only numbers and - special character only.'));
        $this->form_validation->set_rules('country', 'Country', 'trim|required');
        $this->form_validation->set_rules('state', 'State', 'trim|required');
        $this->form_validation->set_rules('city', 'City', 'trim|required');

        if ($this->form_validation->run() == FALSE) {

            $this->template->load('admin/default', 'admin/school/update_school', $this->data);
        } else {

            $data = array(
                "school_name" => $this->input->post("schoolname"),
                "school_nickname" => $this->input->post("school_nickname"),
                "principal_name" => $this->input->post("principal_name"),
                "school_code" => $this->input->post("school_code"),
                "school_type" => $this->input->post("school_type"),
                "school_email_id" => $this->input->post("school_email_id"),
                "school_contact_no1" => $this->input->post("contact_1"),
                "school_contact_no2" => $this->input->post("contact_2"),
                "school_grade" => $this->input->post("grade"),
                "school_mode" => $this->input->post("mode"),
                "address" => $this->input->post("address"),
                "district_id" => $this->input->post("district"),
                "city_id" => $this->input->post("city"),
                "state_id" => $this->input->post("state"),
                "country_id" => $this->input->post("country"),
                "modified_date" => date('Y-m-d H:i:s'),
                "is_delete" => 0,
            );

            update(TBL_SCHOOLS, $id, $data); // Update data using common_model.php and cms_helper.php
            $this->session->set_flashdata('success', 'Record is Successfully updated.');
            redirect($this->data['prev_url']); // Redirect to previous page set in ADMIN_Controller.php
        }
    }

    // ---------------------------- School Module END --------------------------------------------
}

/* End of file School.php */
/* Location: ./application/controllers/Admin/School.php */