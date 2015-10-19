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

//$route['admin/dashboard'] = "admin/admin/dashboard";

$route['admin'] = "admin/dashboard";
$route['admin/dashboard'] = "admin/dashboard/dashboard";
$route['admin/auto_generated_credentials'] = "admin/dashboard/auto_generated_credentials";
$route['admin/logout'] = "admin/dashboard/logout"; 

//routs for next phase
$route['admin/feeds'] = "admin/topic/next_phase";
$route['admin/spam'] = "admin/topic/next_phase";
$route['admin/banners'] = "admin/topic/next_phase";
$route['admin/books'] = "admin/topic/next_phase";
$route['admin/organize'] = "admin/topic/next_phase";
$route['admin/author'] = "admin/topic/next_phase";
$route['admin/badges'] = "admin/topic/next_phase";
$route['admin/questionaries'] = "admin/topic/next_phase";
$route['admin/notifications'] = "admin/topic/next_phase";
$route['admin/messages'] = "admin/topic/next_phase";
$route['admin/user/performance'] = "admin/topic/next_phase";
$route['admin/user/books'] = "admin/topic/next_phase";

$route['admin/(:any)'] = "admin/$1"; 
$route['admin/(:any)/(:any)'] = "admin/$1/$2"; 
$route['admin/(:any)/(:any)/(:any)'] = "admin/$1/$2/$3"; 

$route['student/tutorial'] = "student/tutorial";
$route['student/exam-instruction'] = "student/tutorial/exam";
$route['student/class-exam-instruction/(:any)'] = "student/My_classroom_exam/exam_instruction/$1";
$route['student/exam'] = "student/tutorial/exam_start";
$route['student/class_exam'] = "student/My_classroom_exam/exam_start";
$route['student/group_allocation'] = "student/home/group_allocation";
$route['student/(:any)'] = "student/$1";
$route['teacher/(:any)'] = "teacher/$1";
$route['author/(:any)'] = "author/$1";
$route['404_override'] = '';
$route['translate_uri_dashes'] = FALSE;
