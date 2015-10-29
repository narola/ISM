<?php

/* DB credential */


$db = array(
    'host' => '192.168.1.201',
    'username' => 'ism_V1',
    'password' => '7!#11<D#Kgj!q<d',
    'database' => 'ism_V1'
);

/**
 * Print data
 * @param Mixed $message
 * @param Mixed $status
 */
function pr($message, $status = 0) {
    $re = 'Request';
    if ($status == 1) {
        $re = 'Responce';
    } else if ($status == 2) {
        $re = 'System Info';
    }

    echo "\n---------------------------------------- Time: " . date("H:m:s") . " ($re)\n\n";
    if (is_array($message)) {
        print_r($message);
    } else {
        echo $message;
    }
}

function replace_invalid_chars($data = null) {
    if (is_array($data) && $data != null) {
        foreach ($data as $key => $value) {
            if (is_array($value)) {
                $data[$key] = replace_invalid_chars($value);
            } else {
                $data[$key] = htmlspecialchars($value, ENT_QUOTES);
            }
        }
    }
    return $data;
}

/* DB table name constants */
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
define('TBL_STUDENT_ACADEMIC_INFO','student_academic_info');
define('TBL_STUDENT_EXAM_RESPONSE','student_exam_response');
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
?>
