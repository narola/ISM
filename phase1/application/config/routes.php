<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/*
| -------------------------------------------------------------------------
| URI ROUTING
| -------------------------------------------------------------------------
| This file lets you re-map URI requests to specific controller functions.
|
| Typically there is a one-to-one relationship between a URL string
| and its corresponding controller class/method. The segments in a
| URL normally follow this pattern:
|
|	example.com/class/method/id/
|
| In some instances, however, you may want to remap this relationship
| so that a different class/function is called than the one
| corresponding to the URL.
|
| Please see the user guide for complete details:
|
|	http://codeigniter.com/user_guide/general/routing.html
|
| -------------------------------------------------------------------------
| RESERVED ROUTES
| -------------------------------------------------------------------------
|
| There are three reserved routes:
|
|	$route['default_controller'] = 'welcome';
|
| This route indicates which controller class should be loaded if the
| URI contains no data. In the above example, the "welcome" class
| would be loaded.
|
|	$route['404_override'] = 'errors/page_missing';
|
| This route will tell the Router which controller/method to use if those
| provided in the URL cannot be matched to a valid route.
|
|	$route['translate_uri_dashes'] = FALSE;
|
| This is not exactly a route, but allows you to automatically route
| controller and method names that contain dashes. '-' isn't a valid
| class or method name character, so it requires translation.
| When you set this option to TRUE, it will replace ALL dashes in the
| controller and method URI segments.
|
| Examples:	my-controller/index	-> my_controller/index
|		my-controller/my-method	-> my_controller/my_method
*/
$route['default_controller'] = 'login';

/* For avery Admin Routes are change by Virendra patel Sparks ID-VPA */

//$route['admin/dashboard'] = "Admin/admin/dashboard";

$route['admin'] = "Admin/dashboard";
$route['admin/dashboard'] = "Admin/dashboard/dashboard";
$route['admin/auto_generated_credentials'] = "Admin/dashboard/auto_generated_credentials";
$route['admin/logout'] = "Admin/dashboard/logout"; 

//routs for next phase
$route['admin/feeds'] = "Admin/topic/next_phase";
$route['admin/spam'] = "Admin/topic/next_phase";
$route['admin/banners'] = "Admin/topic/next_phase";
$route['admin/books'] = "Admin/topic/next_phase";
$route['admin/organize'] = "Admin/topic/next_phase";
$route['admin/author'] = "Admin/topic/next_phase";
$route['admin/badges'] = "Admin/topic/next_phase";
$route['admin/questionaries'] = "Admin/topic/next_phase";
$route['admin/notifications'] = "Admin/topic/next_phase";
$route['admin/messages'] = "Admin/topic/next_phase";
$route['admin/user/performance'] = "Admin/topic/next_phase";
$route['admin/book'] = "Admin/topic/next_phase";
$route['admin/year_upgradation'] = "Admin/upgrade";


/* Route Set For Copy Functionality for Exam and Notices */

$route['admin/exam/copy/(:any)'] = "Admin/exam/update/$1";
$route['admin/notice/copy/(:any)'] = "Admin/notice/update/$1";
$route['admin/question/copy/(:any)'] = "Admin/question/update/$1";

$route['admin/(:any)'] = "Admin/$1"; 
$route['admin/(:any)/(:any)'] = "Admin/$1/$2"; 
$route['admin/(:any)/(:any)/(:any)'] = "Admin/$1/$2/$3"; 

$route['student/tutorial'] = "Student/tutorial";
$route['student/exam-instruction'] = "Student/tutorial/exam";
$route['student/class-exam-instruction/(:any)'] = "Student/My_classroom_exam/exam_instruction/$1";
$route['student/exam'] = "Student/tutorial/exam_start";
$route['student/class_exam'] = "Student/My_classroom_exam/exam_start";
$route['student/group_allocation'] = "Student/home/group_allocation";
$route['student/tagged_feed/(:any)'] = "Student/home/tagged_feed/$1";
$route['student/(:any)'] = "Student/$1";
$route['student/(:any)/(:any)'] = "Student/$1/$2";
$route['student/(:any)/(:any)/(:any)'] = "Student/$1/$2/$3";
$route['student/my_scoreboard/index/(:any)'] = "Student/My_scoreboard/index/$1";
$route['teacher/(:any)'] = "teacher/$1";
$route['author/(:any)'] = "author/$1";
$route['404_override'] = '';
$route['translate_uri_dashes'] = FALSE;
