<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/*
|--------------------------------------------------------------------------
| File and Directory Modes
|--------------------------------------------------------------------------
|
| These prefs are used when checking and setting modes when working
| with the file system.  The defaults are fine on servers with proper
| security, but you may wish (or even need) to change the values in
| certain environments (Apache running a separate process for each
| user, PHP under CGI with Apache suEXEC, etc.).  Octal values should
| always be used to set the mode correctly.
|
*/
define('FILE_READ_MODE', 0644);
define('FILE_WRITE_MODE', 0666);
define('DIR_READ_MODE', 0755);
define('DIR_WRITE_MODE', 0755);

/*
|--------------------------------------------------------------------------
| File Stream Modes
|--------------------------------------------------------------------------
|
| These modes are used when working with fopen()/popen()
|
*/
define('FOPEN_READ', 'rb');
define('FOPEN_READ_WRITE', 'r+b');
define('FOPEN_WRITE_CREATE_DESTRUCTIVE', 'wb'); // truncates existing file data, use with care
define('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE', 'w+b'); // truncates existing file data, use with care
define('FOPEN_WRITE_CREATE', 'ab');
define('FOPEN_READ_WRITE_CREATE', 'a+b');
define('FOPEN_WRITE_CREATE_STRICT', 'xb');
define('FOPEN_READ_WRITE_CREATE_STRICT', 'x+b');

/*
|--------------------------------------------------------------------------
| Display Debug backtrace
|--------------------------------------------------------------------------
|
| If set to TRUE, a backtrace will be displayed along with php errors. If
| error_reporting is disabled, the backtrace will not display, regardless
| of this setting
|
*/
define('SHOW_DEBUG_BACKTRACE', TRUE);

/*
|--------------------------------------------------------------------------
| Exit Status Codes
|--------------------------------------------------------------------------
|
| Used to indicate the conditions under which the script is exit()ing.
| While there is no universal standard for error codes, there are some
| broad conventions.  Three such conventions are mentioned below, for
| those who wish to make use of them.  The CodeIgniter defaults were
| chosen for the least overlap with these conventions, while still
| leaving room for others to be defined in future versions and user
| applications.
|
| The three main conventions used for determining exit status codes
| are as follows:
|
|    Standard C/C++ Library (stdlibc):
|       http://www.gnu.org/software/libc/manual/html_node/Exit-Status.html
|       (This link also contains other GNU-specific conventions)
|    BSD sysexits.h:
|       http://www.gsp.com/cgi-bin/man.cgi?section=3&topic=sysexits
|    Bash scripting:
|       http://tldp.org/LDP/abs/html/exitcodes.html
|
*/
define('EXIT_SUCCESS', 0); // no errors
define('EXIT_ERROR', 1); // generic error
define('EXIT_CONFIG', 3); // configuration error
define('EXIT_UNKNOWN_FILE', 4); // file not found
define('EXIT_UNKNOWN_CLASS', 5); // unknown class
define('EXIT_UNKNOWN_METHOD', 6); // unknown class member
define('EXIT_USER_INPUT', 7); // invalid user input
define('EXIT_DATABASE', 8); // database error
define('EXIT__AUTO_MIN', 9); // lowest automatically-assigned error code
define('EXIT__AUTO_MAX', 125); // highest automatically-assigned error code
define('UPLOAD_URL','uploads'); // Path of uploaded media/document.

/*
| Constants For Table Names
*/
define('TBL_ADMIN_CONFIG','admin_config');
define('TBL_ANSWER_CHOICES','answer_choices');
define('TBL_ASSIGNMENT_SUBMISSION','assignment_submission');
define('TBL_ASSIGNMENTS','assignments');
define('TBL_AUTHOR_BOOK','author_book');
define('TBL_AUTHOR_PROFILE','author_profile');
define('TBL_AUTO_GENERATED_CREDENTIAL','auto_generated_credential');
define('TBL_BADGE_CATEGORY','badge_category');
define('TBL_BADGES','badges');
define('TBL_BANNER_CATEGORY','banner_category');
define('TBL_BANNERS','banners');
define('TBL_BOOK_ASSIGNMENT','book_assignment');
define('TBL_BOOKS','books');
define('TBL_CITIES','cities');
define('TBL_CLASSROOM_ASSIGNMENT','classroom_assignment');
define('TBL_CLASSROOMS','classrooms');
define('TBL_COUNTRIES','countries');
define('TBL_COURSE_CATEGORY','course_category');
define('TBL_CLASSROOM_SUBJECT','classroom_subject');
define('TBL_COURSES','courses');
define('TBL_DISTRICTS','districts');
define('TBL_EXAM_SCHEDULE','exam_schedule');
define('TBL_EXAMS','exams');
define('TBL_FEED_COMMENT','feed_comment');
define('TBL_FEED_IMAGE','feed_image');
define('TBL_FEED_LIKE','feed_like');
define('TBL_FEEDS','feeds');
define('TBL_FEEDS_TAGGED_USER','feeds_tagged_user');
define('TBL_FOLLOWERS','followers');
define('TBL_FORUM_QUESTION','forum_question');
define('TBL_FORUM_QUESTION_COMMENT','forum_question_comment');
define('TBL_GROUP_NAMES','group_names');
define('TBL_INVITED_USER','invited_user');
define('TBL_LECTURE_CATEGORY','lecture_category');
define('TBL_LECTURES','lectures');
define('TBL_MEMBERSHIP_PACKAGE','membership_package');
define('TBL_MESSAGE_RECEIVER','message_receiver');
define('TBL_MESSAGES','messages');
define('TBL_MOVIES','movies');
define('TBL_NOTES','notes');
define('TBL_NOTICEBOARD','noticeboard');
define('TBL_NOTICEBOARD_VIEWER','noticeboard_viewer');
define('TBL_PASTIMES','pastimes');
define('TBL_PERMISSION','permission');
define('TBL_PERMISSION_ACCESS','permission_access');
define('TBL_PREFERENCES','preferences');
define('TBL_PROMO_CODES','promo_codes');
define('TBL_QUESTION_RATINGS','question_ratings');
define('TBL_QUESTIONS','questions');
define('TBL_REPORTED_CONTENT','reported_content');
define('TBL_ROLES','roles');
define('TBL_SCHOOL_CLASSROOM','school_classroom');
define('TBL_SCHOOL_COURSE','school_course');
define('TBL_SCHOOLS','schools');
define('TBL_STATES','states');
define('TBL_STUDENT_ACADEMIC_INFO','student_profile');
define('TBL_STUDENT_ACADEMIC_DET','student_academic_info');
define('TBL_STUDENT_EXAM_RESPONSE','student_objective_response');
define('TBL_STUDENT_EXAM_SCORE','student_exam_score');
define('TBL_STUDENT_PROFILE','student_profile');
define('TBL_STUDENT_SUBJECTIVE_EVALUATION','student_subjective_evaluation');
define('TBL_STUDYMATES','studymates');
define('TBL_STUDYMATES_REQUEST','studymates_request');
define('TBL_SUBJECTS','subjects');
define('TBL_SURVEY_ANSWER_CHOICE','survey_answer_choice');
define('TBL_SURVEY_AUDIENCE','survey_audience');
define('TBL_SURVEY_QUESTION','survey_question');
define('TBL_SURVEY_RESPONSE','survey_response');
define('TBL_SURVEYS','surveys');
define('TBL_SYSTEM_MODULE','system_module');
define('TBL_TAG_BOOK_ASSIGNMENT','tag_book_assignment');
define('TBL_TAGS','tags');
define('TBL_TAGS_BOOK','tags_book');
define('TBL_TAGS_FORUM_QUESTION','tags_forum_question');
define('TBL_TAGS_LECTURE','tags_lecture');
define('TBL_TAGS_QUESTION','tags_question');
define('TBL_TEACHER_PROFILE','teacher_profile');
define('TBL_TEACHER_SUBJECT_INFO','teacher_subject_info');
define('TBL_TUTORIAL_TOPIC','tutorial_topic');
define('TBL_TOPICS','topics');
define('TBL_TUTORIAL_GROUP_DISCUSSION','tutorial_group_discussion');
define('TBL_TUTORIAL_GROUP_MEMBER','tutorial_group_member');
define('TBL_TUTORIAL_GROUP_QUESTION','tutorial_group_question');
define('TBL_TUTORIAL_GROUP_TOPIC_ALLOCATION','tutorial_group_topic_allocation');
define('TBL_TUTORIAL_GROUPS','tutorial_groups');
define('TBL_USER_ACCOUNT_PREFERENCE','user_account_preference');
define('TBL_USER_BADGE','user_badge');
define('TBL_USER_CHAT','user_chat');
define('TBL_USER_FAVORITE_AUTHOR','user_favorite_author');
define('TBL_USER_FAVORITE_BOOK','user_favorite_book');
define('TBL_USER_FAVORITE_MOVIE','user_favorite_movie');
define('TBL_USER_FAVORITE_PASTIME','user_favorite_pastime');
define('TBL_USER_FORGOT_PASSWORD','user_forgot_password');
define('TBL_USER_LIBRARY','user_library');
define('TBL_USER_NOTIFICATION','user_notification');
define('TBL_USER_PERMISSION','user_permission');
define('TBL_USER_PROFILE_PICTURE','user_profile_picture');
define('TBL_TUTORIAL_GROUP_MEMBER_SCORE','tutorial_group_member_score');
define('TBL_USERS','users');
define('TBL_WORD_WATCH','word_watch');
define('TBL_TUTORIAL_TOPIC_EXAM','tutorial_topic_exam');
define('TBL_EXAM_QUESTION','exam_question');

