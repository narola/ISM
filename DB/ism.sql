-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 192.168.1.201
-- Generation Time: Sep 25, 2015 at 07:35 AM
-- Server version: 5.5.32
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ism`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_config`
--

CREATE TABLE IF NOT EXISTS `admin_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `config_key` varchar(20) NOT NULL,
  `config_value` varchar(20) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `admin_config`
--

INSERT INTO `admin_config` (`id`, `config_key`, `config_value`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'activeHoursStartTime', '14:40', '2015-09-24 05:03:37', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'activeHoursEndTime', '15:00', '2015-09-24 05:03:37', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'test', 'test', '2015-09-24 05:25:18', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `answer_choices`
--

CREATE TABLE IF NOT EXISTS `answer_choices` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(10) unsigned NOT NULL,
  `choice_text` varchar(100) NOT NULL,
  `is_right` int(10) unsigned NOT NULL,
  `image_link` tinytext NOT NULL,
  `audio_link` tinytext NOT NULL,
  `video_link` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  UNIQUE KEY `question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE IF NOT EXISTS `assignments` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `assignment_by` int(11) unsigned NOT NULL,
  `description` text NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `year_id` int(5) NOT NULL,
  `subject_id` int(11) unsigned NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assignment_by` (`assignment_by`),
  UNIQUE KEY `course_id` (`course_id`),
  UNIQUE KEY `year_id` (`year_id`),
  UNIQUE KEY `subject_id` (`subject_id`),
  UNIQUE KEY `topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `assignment_submission`
--

CREATE TABLE IF NOT EXISTS `assignment_submission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `assignment_id` int(11) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `assessment_status` int(10) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `assignment_id` (`assignment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `author_book`
--

CREATE TABLE IF NOT EXISTS `author_book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `book_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `author_profile`
--

CREATE TABLE IF NOT EXISTS `author_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `total_questions_answered` int(11) unsigned NOT NULL,
  `total_assignment` int(11) unsigned NOT NULL,
  `total_submission_received` int(11) unsigned NOT NULL,
  `total_posts` int(11) unsigned NOT NULL,
  `total_badges` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `auto_generated_credential`
--

CREATE TABLE IF NOT EXISTS `auto_generated_credential` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` tinytext NOT NULL,
  `password` tinytext NOT NULL,
  `school_id` int(11) unsigned NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `classroom_id` int(11) NOT NULL COMMENT 'class division as A,B,C',
  `course_id` int(11) unsigned NOT NULL,
  `academic_year` varchar(255) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `school_id` (`school_id`),
  KEY `role_id` (`role_id`),
  KEY `course_id` (`course_id`),
  KEY `year_id` (`academic_year`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `auto_generated_credential`
--

INSERT INTO `auto_generated_credential` (`id`, `username`, `password`, `school_id`, `role_id`, `status`, `created_date`, `modified_date`, `classroom_id`, `course_id`, `academic_year`, `is_delete`, `is_testdata`) VALUES
(7, 'test1', '5A2DUoO70Cm3UkFbrzOfwJTOYFif37xLL33BkKYFmQ/eK6NS+JZUorq3nib+JU4xg/San70bubMpOFGM/naQ6w==', 3, 2, '1', '2015-09-22 07:07:40', '0000-00-00 00:00:00', 1, 1, '1', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `badges`
--

CREATE TABLE IF NOT EXISTS `badges` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `badge_name` tinytext NOT NULL,
  `badge_description` varchar(100) NOT NULL,
  `badge_award` varchar(50) NOT NULL,
  `badge_category_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `badge_category_id` (`badge_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `badge_category`
--

CREATE TABLE IF NOT EXISTS `badge_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `badge_category_name` varchar(20) NOT NULL,
  `parent_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `banners`
--

CREATE TABLE IF NOT EXISTS `banners` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `banner_title` varchar(50) NOT NULL,
  `banner_category_id` int(11) unsigned NOT NULL,
  `total_views` int(20) unsigned NOT NULL,
  `total_clicks` int(20) unsigned NOT NULL,
  `added_by` int(11) unsigned NOT NULL,
  `added_on` date NOT NULL,
  `banner_validity` date NOT NULL,
  `banner_type` varchar(20) NOT NULL,
  `image_link` varchar(100) NOT NULL,
  `banner_status` varchar(20) NOT NULL,
  `banner_description` text NOT NULL,
  `owner_name` varchar(20) NOT NULL,
  `owner_email_id` varchar(50) NOT NULL,
  `owner_contact_no` varchar(15) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `banner_category_id` (`banner_category_id`),
  KEY `added_by` (`added_by`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `banner_category`
--

CREATE TABLE IF NOT EXISTS `banner_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_name` varchar(20) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) NOT NULL,
  `book_description` text NOT NULL,
  `ebook_link` varchar(100) NOT NULL,
  `image_link` varchar(100) NOT NULL,
  `publication_date` varchar(10) NOT NULL,
  `price` varchar(20) NOT NULL,
  `publisher_name` varchar(50) NOT NULL,
  `isbn_no` varchar(30) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `book_assignment`
--

CREATE TABLE IF NOT EXISTS `book_assignment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `assignment_name` varchar(50) NOT NULL,
  `assignment_type` varchar(20) NOT NULL,
  `assignment_description` text NOT NULL,
  `added_by` int(11) unsigned NOT NULL,
  `book_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `added_by` (`added_by`),
  KEY `book_id` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE IF NOT EXISTS `cities` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(30) NOT NULL,
  `state_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `state_id` (`state_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=117 ;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`id`, `city_name`, `state_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'surat', 1, '2015-09-22 06:11:01', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'ahmedabad', 1, '2015-09-22 06:11:01', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Hyderabad', 12, '2015-09-22 08:57:35', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Visakhapatnam', 12, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Vijayawada', 12, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(6, 'Warangal', 12, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(7, 'Guntur', 12, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(8, 'Nellore', 12, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(9, 'Itanagar', 13, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(10, 'Amravati', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(11, 'Aurangabad', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(12, 'Konkan', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(13, 'Nagpur', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(14, 'Nashik', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(15, 'Pune', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(16, 'Mumbai', 27, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(17, 'Jamshedpur', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(18, 'Dhanbad', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(19, 'Ranchi', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(20, 'Bokaro Steel City', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(21, 'Deoghar', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(22, 'Hazaribagh', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(23, 'Phusro', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(24, 'Giridih', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(25, 'Ramgarh', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(26, 'Medininagar', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(27, 'Chirkunda', 23, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(28, 'Bengaluru', 24, '2015-09-22 09:03:55', '0000-00-00 00:00:00', 0, 'yes'),
(29, 'Mysore', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(30, 'Hubli-Dharwar', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(31, 'Mangalore', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(32, 'Belgaum', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(33, 'Gulbarga', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(34, 'Davanagere', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(35, 'Bellary', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(36, 'Bijapur', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(37, 'Shimoga', 24, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(38, 'Thiruvananthapuram', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(39, 'Kochi', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(40, 'Kozhikode', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(41, 'Kollam', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(42, 'Thrissur', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(43, 'Kannur', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(44, 'Malappuram', 25, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(45, 'Bhopal', 26, '2015-09-22 09:03:56', '0000-00-00 00:00:00', 0, 'yes'),
(46, 'Indore', 26, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(47, 'Jabalpur', 26, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(48, 'Gwalior', 26, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(49, 'Ujjain', 26, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(50, 'Surat', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(51, 'Ahemdabad', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(52, 'Vadodara', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(53, 'Rajkot', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(54, 'Mehsana', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(55, 'Bhavnagar', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(56, 'Gandhinagar', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(57, 'Jamnagar', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(58, 'Bharuch', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(59, 'Ankleshwar', 18, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(60, 'Ambala', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(61, 'Sonipat', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(62, 'Panipat', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(63, 'Gurgaon', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(64, 'Bhiwani', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(65, 'Rohtak', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(66, 'Delhi', 19, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(67, 'Shimla', 21, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(68, 'Manali', 21, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(69, 'Kullu', 21, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(70, 'Kangra', 21, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(71, 'Srinagar', 22, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(72, 'Jammu', 22, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(73, 'Kashmir', 22, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(74, 'Raipur', 16, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(75, 'Bhilai', 16, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(76, 'Bilaspur', 16, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(77, 'Korba', 16, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(78, 'Guwahati', 14, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(79, 'Silchar', 14, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(80, 'Darjeeling', 14, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(81, 'Dibrugarh', 14, '2015-09-22 09:03:57', '0000-00-00 00:00:00', 0, 'yes'),
(82, 'Tezpur', 14, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(83, 'Patna', 15, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(84, 'Gaya', 15, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(85, 'Muzaffarpur', 15, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(86, 'Panaji', 17, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(87, 'Faridabad', 20, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(88, 'Imphal', 28, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(89, 'Shilong', 29, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(90, 'Mizoram', 30, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(91, 'Amritsar', 31, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(92, 'Chandigarh', 31, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(93, 'Jaipur', 32, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(94, 'Kota', 32, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(95, 'Udaipur', 32, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(96, 'Gangtok', 33, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(97, 'Chennai', 34, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(98, 'Madurai', 34, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(99, 'Agartala', 35, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(100, 'Amarpur', 35, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(101, 'Rishikesh', 36, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(102, 'Saharanpur', 36, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(103, 'Dehradun', 37, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(104, 'Roorkee', 37, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(105, 'Haridwar', 37, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(106, 'Kolkatta', 38, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(107, 'Howrah', 38, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(108, 'Kalimpong', 38, '2015-09-22 09:03:58', '0000-00-00 00:00:00', 0, 'yes'),
(114, 'Lucknow', 36, '2015-09-22 09:05:30', '0000-00-00 00:00:00', 0, 'yes'),
(115, 'Allahabad', 36, '2015-09-22 09:05:30', '0000-00-00 00:00:00', 0, 'yes'),
(116, 'Kanpur', 36, '2015-09-22 09:05:30', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `classrooms`
--

CREATE TABLE IF NOT EXISTS `classrooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `course_id` int(11) unsigned NOT NULL,
  `class_name` varchar(15) NOT NULL,
  `class_nickname` varchar(10) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `classrooms`
--

INSERT INTO `classrooms` (`id`, `course_id`, `class_name`, `class_nickname`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(2, 12, '11th HSC Sci', '', '2015-09-22 20:41:48', '0000-00-00 00:00:00', 0, 'yes'),
(3, 12, '12th HSC Sci', '', '2015-09-22 20:42:02', '0000-00-00 00:00:00', 0, 'yes'),
(4, 13, '11th HSC Comm', '', '2015-09-22 20:42:59', '0000-00-00 00:00:00', 0, 'yes'),
(5, 13, '12th HSC Comm', '', '2015-09-22 20:42:59', '0000-00-00 00:00:00', 0, 'yes'),
(6, 14, 'BScCompSci FY', 'FYCS', '2015-09-22 20:44:49', '0000-00-00 00:00:00', 0, 'yes'),
(7, 14, 'BScCompSci SY', 'SYCS', '2015-09-22 20:44:49', '0000-00-00 00:00:00', 0, 'yes'),
(8, 14, 'BScCompSci TY', 'TYCS', '2015-09-22 20:44:49', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `classroom_assignment`
--

CREATE TABLE IF NOT EXISTS `classroom_assignment` (
  `id` int(11) NOT NULL,
  `assignment_id` int(11) unsigned NOT NULL,
  `school_classroom_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  KEY `assignment_id` (`assignment_id`),
  KEY `school_classroom_id` (`school_classroom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE IF NOT EXISTS `countries` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `country_name` varchar(30) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`id`, `country_name`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'Ghana', '2015-09-22 05:57:34', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'India', '2015-09-22 05:57:34', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `course_name` varchar(50) NOT NULL,
  `course_nickname` varchar(15) NOT NULL,
  `course_details` varchar(200) NOT NULL,
  `course_type` set('University','Primary','Secondary','Higher Secondary') NOT NULL,
  `course_duration` int(5) NOT NULL COMMENT 'in years',
  `course_degree` varchar(30) NOT NULL,
  `course_category_id` int(11) unsigned NOT NULL,
  `is_semester` tinyint(1) NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `category_id` (`course_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `course_name`, `course_nickname`, `course_details`, `course_type`, `course_duration`, `course_degree`, `course_category_id`, `is_semester`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'test', '', 'test', 'Secondary', 4, '', 0, 0, '2015-09-22 07:06:53', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'Agricultural Engineering', 'HSC Agri', '', 'Higher Secondary', 3, 'HSC', 0, 0, '2015-09-22 17:55:50', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Bussiness', 'HSC Buss', '', 'Higher Secondary', 3, 'HSC', 0, 0, '2015-09-22 17:55:50', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Technology', 'HSC Tech', '', 'Higher Secondary', 3, 'HSC', 0, 0, '2015-09-22 17:56:33', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Home Economics', 'HSC HomEco', '', 'Higher Secondary', 3, 'HSC', 0, 0, '2015-09-22 17:56:33', '0000-00-00 00:00:00', 0, 'yes'),
(6, 'Visual Arts', 'HSC VisArts', '', 'Higher Secondary', 3, 'HSC', 0, 0, '2015-09-22 17:56:58', '0000-00-00 00:00:00', 0, 'yes'),
(7, 'General Arts', 'HSC G.Arts', '', 'Higher Secondary', 3, 'HSC', 0, 0, '2015-09-22 17:56:58', '0000-00-00 00:00:00', 0, 'yes'),
(12, 'Science', 'HSC Sci', '', 'Higher Secondary', 2, 'HSC', 0, 0, '2015-09-22 18:38:28', '0000-00-00 00:00:00', 0, 'yes'),
(13, 'Commerce', 'HSC Comm', '', 'Higher Secondary', 2, 'HSC', 0, 0, '2015-09-22 18:38:28', '0000-00-00 00:00:00', 0, 'yes'),
(14, 'BSc Computer Science', 'BSc CS', '', 'University', 3, 'BSc', 0, 0, '2015-09-22 19:30:54', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `course_category`
--

CREATE TABLE IF NOT EXISTS `course_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `course_category_name` varchar(30) NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL,
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `course_category`
--

INSERT INTO `course_category` (`id`, `course_category_name`, `parent_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'General', 0, '2015-09-22 17:33:23', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'Management', 0, '2015-09-22 17:33:23', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Science & Engineering', 0, '2015-09-22 17:34:23', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Banking & Finance', 0, '2015-09-22 17:34:23', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Animation & Design', 0, '2015-09-22 17:35:13', '0000-00-00 00:00:00', 0, 'yes'),
(6, 'Hospitality & Aviation', 0, '2015-09-22 17:35:13', '0000-00-00 00:00:00', 0, 'yes'),
(7, 'Media & Mass Communication', 0, '2015-09-22 17:36:01', '0000-00-00 00:00:00', 0, 'yes'),
(8, 'Arts, Law and Teaching', 0, '2015-09-22 17:36:01', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `course_subject`
--

CREATE TABLE IF NOT EXISTS `course_subject` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `classroom_id` int(11) unsigned NOT NULL COMMENT 'eg: 11th HSC Sci',
  `subject_id` int(11) unsigned NOT NULL COMMENT 'eg: physics',
  `course_id` int(11) unsigned NOT NULL COMMENT 'eg: HSc Sci',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `classroom_id` (`classroom_id`),
  KEY `subject_id` (`subject_id`),
  KEY `course_id` (`course_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `course_subject`
--

INSERT INTO `course_subject` (`id`, `classroom_id`, `subject_id`, `course_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 2, 1, 12, '2015-09-23 07:30:00', '0000-00-00 00:00:00', 0, 'yes'),
(2, 2, 2, 12, '2015-09-23 07:30:00', '0000-00-00 00:00:00', 0, 'yes'),
(3, 2, 3, 12, '2015-09-23 07:30:00', '0000-00-00 00:00:00', 0, 'yes'),
(4, 2, 4, 12, '2015-09-23 07:30:00', '0000-00-00 00:00:00', 0, 'yes'),
(5, 2, 8, 12, '2015-09-23 07:30:00', '0000-00-00 00:00:00', 0, 'yes'),
(6, 4, 5, 13, '2015-09-23 07:30:00', '0000-00-00 00:00:00', 0, 'yes'),
(7, 2, 1, 12, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(8, 2, 2, 12, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(9, 2, 3, 12, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(10, 2, 4, 12, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(11, 2, 8, 12, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(12, 4, 5, 13, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(13, 4, 6, 13, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(14, 4, 7, 13, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes'),
(15, 4, 8, 13, '2015-09-23 07:31:44', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `districts`
--

CREATE TABLE IF NOT EXISTS `districts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `district_name` varchar(30) NOT NULL,
  `city_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `districts`
--

INSERT INTO `districts` (`id`, `district_name`, `city_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'surat_district', 1, '2015-09-22 06:13:20', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'ahmedabad_district', 2, '2015-09-22 06:13:20', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE IF NOT EXISTS `exams` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `exam_name` tinytext NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `subject_id` int(11) unsigned NOT NULL,
  `year_id` int(5) NOT NULL,
  `exam_type` varchar(20) NOT NULL,
  `exam_category` varchar(20) NOT NULL,
  `exam_mode` varchar(15) NOT NULL,
  `duration` int(11) unsigned NOT NULL,
  `attempt_count` int(11) unsigned NOT NULL,
  `instructions` text NOT NULL,
  `negative_marking` varchar(5) NOT NULL DEFAULT 'no',
  `random_question` varchar(5) NOT NULL DEFAULT 'no',
  `declare_results` varchar(5) NOT NULL DEFAULT 'no',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  KEY `subject_id` (`subject_id`),
  KEY `year_id` (`year_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `exam_schedule`
--

CREATE TABLE IF NOT EXISTS `exam_schedule` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `exam_id` int(11) unsigned NOT NULL,
  `start_date` date NOT NULL,
  `start_time` time NOT NULL,
  `school_classroom_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `exam_id` (`exam_id`),
  KEY `school_classroom_id` (`school_classroom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `feeds`
--

CREATE TABLE IF NOT EXISTS `feeds` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `feed_by` int(11) unsigned NOT NULL,
  `feed_text` text NOT NULL,
  `video_link` text NOT NULL,
  `audio_link` text NOT NULL,
  `posted_on` date NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `feed_by` (`feed_by`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `feeds_tagged_user`
--

CREATE TABLE IF NOT EXISTS `feeds_tagged_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `feed_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `feed_id` (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `feed_comment`
--

CREATE TABLE IF NOT EXISTS `feed_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `comment` text NOT NULL,
  `comment_by` int(11) unsigned NOT NULL,
  `feed_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `comment_by` (`comment_by`),
  KEY `feed_id` (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `feed_image`
--

CREATE TABLE IF NOT EXISTS `feed_image` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `feed_id` int(11) unsigned NOT NULL,
  `image_link` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `feed_id` (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `feed_like`
--

CREATE TABLE IF NOT EXISTS `feed_like` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `like_by` int(11) unsigned NOT NULL,
  `feed_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `like_by` (`like_by`),
  KEY `feed_id` (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `followers`
--

CREATE TABLE IF NOT EXISTS `followers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `follower_id` int(11) unsigned NOT NULL,
  `follow_to` int(11) unsigned NOT NULL,
  `follow_status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `followerd_id` (`follower_id`),
  KEY `follow_to` (`follow_to`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `forum_question`
--

CREATE TABLE IF NOT EXISTS `forum_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_title` tinytext NOT NULL,
  `question_text` text NOT NULL,
  `video_link` tinytext NOT NULL,
  `posted_by` int(11) unsigned NOT NULL,
  `question_for` int(11) unsigned NOT NULL COMMENT 'user for whom question is posted. if its 0 then anyone can answer',
  `total_answers` int(11) unsigned NOT NULL,
  `total_upvotes` int(11) unsigned NOT NULL,
  `total_downvotes` int(11) unsigned NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `posted_by` (`posted_by`),
  KEY `question_for` (`question_for`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `forum_question_comment`
--

CREATE TABLE IF NOT EXISTS `forum_question_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `comment` text NOT NULL,
  `comment_by` int(11) unsigned NOT NULL,
  `forum_question_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `comment_by` (`comment_by`),
  KEY `forum_question_id` (`forum_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `group_names`
--

CREATE TABLE IF NOT EXISTS `group_names` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=57 ;

--
-- Dumping data for table `group_names`
--

INSERT INTO `group_names` (`id`, `group_name`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'Pirates', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'Road Runners', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Fantastic Five', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Master Minds ', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Technocrates', '0000-00-00 00:00:00', '0000-00-00 00:00:00', 0, 'yes'),
(6, 'Barcelona', '2015-09-24 17:07:32', '0000-00-00 00:00:00', 0, 'yes'),
(7, 'Bohr', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(8, 'Lovelace', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(9, 'Einstein', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(10, 'Volta', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(11, 'Graham Bell', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(12, 'Alfred Nobel', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(13, 'Avogadro', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(14, 'Celsius', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(15, 'Ampère', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(16, 'Archimedes', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(17, 'Aristotle', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(18, 'Sommerfeld', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(19, 'Eddington', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(20, 'Benjamin Franklin', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(21, 'Blaise Pascal', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(22, 'C. V. Raman', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(23, 'Charles Babbage', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(24, 'Charles Darwin', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(25, 'Charles-Augustin', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(26, 'Isaac Newton', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(27, 'Augustin Coulomb', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(28, 'Avogadro', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(29, 'Faraday', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(30, 'Mendeleev', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(31, 'Rutherford', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(32, 'Max Planck', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(33, 'Niels Bohr', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(34, 'Edwin Hubble', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(35, 'Schrodinger', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(36, 'Fibonacci', '2015-09-24 18:23:28', '0000-00-00 00:00:00', 0, 'yes'),
(37, 'Galileo Galilei', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(38, 'Hertz', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(39, 'Henry Ford', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(40, 'Maxwell', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(41, 'James Watson', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(42, 'James Watt', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(43, 'Vague Deers', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(44, 'Mute Leopards', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(45, 'Clever Foxes', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(46, 'Melted Snails', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(47, 'Placid Rabbits', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(48, 'Eager Alligators', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(49, 'Roomy Ducks', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(50, 'Eager Alligators', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(51, 'Teeny Tigers', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(52, 'Dazzling Seastars', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(53, 'Avengers', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(54, 'Fantastics Five', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(55, 'Lord of Rings', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes'),
(56, 'Golden ball', '2015-09-24 18:23:29', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `invited_user`
--

CREATE TABLE IF NOT EXISTS `invited_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `invited_email` tinytext NOT NULL,
  `invited_by_user` int(11) unsigned NOT NULL,
  `invitation_for_role` int(11) unsigned NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `invited_by_user` (`invited_by_user`),
  KEY `invitation_for_role` (`invitation_for_role`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `lectures`
--

CREATE TABLE IF NOT EXISTS `lectures` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `lecture_title` tinytext NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `subject_id` int(11) unsigned NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `year_id` int(11) NOT NULL,
  `description` text NOT NULL,
  `video_link` tinytext NOT NULL,
  `audio_link` tinytext NOT NULL,
  `chat_transcript` text NOT NULL,
  `lecture_by` int(11) unsigned NOT NULL,
  `difficulty_level` tinytext NOT NULL,
  `view_count` int(11) unsigned NOT NULL,
  `lecture_category_id` int(11) unsigned NOT NULL,
  `notes` text NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  KEY `subject_id` (`subject_id`),
  KEY `topic_id` (`topic_id`),
  KEY `lecture_by` (`lecture_by`),
  KEY `lecture_category_id` (`lecture_category_id`),
  KEY `year_id` (`year_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `lecture_category`
--

CREATE TABLE IF NOT EXISTS `lecture_category` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_name` tinytext NOT NULL,
  `parent_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `membership_package`
--

CREATE TABLE IF NOT EXISTS `membership_package` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `package_name` tinytext NOT NULL,
  `package_description` text NOT NULL,
  `package_value` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message_text` text NOT NULL,
  `sender_id` int(11) unsigned NOT NULL,
  `message_title` tinytext NOT NULL,
  `status` tinytext NOT NULL,
  `reply_for` int(11) unsigned NOT NULL COMMENT 'message_id to get thread',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_id`),
  KEY `reply_for` (`reply_for`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `message_receiver`
--

CREATE TABLE IF NOT EXISTS `message_receiver` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message_id` int(11) unsigned NOT NULL,
  `receiver_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `message_id` (`message_id`),
  KEY `receiver_id` (`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE IF NOT EXISTS `movies` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `movie_name` tinytext NOT NULL,
  `movie_image` tinytext NOT NULL,
  `director` tinytext NOT NULL,
  `screenplay` tinytext NOT NULL,
  `genres` tinytext NOT NULL,
  `description` tinytext NOT NULL,
  `movie_rating` text NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE IF NOT EXISTS `notes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `note_title` tinytext NOT NULL,
  `note` tinytext NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `noticeboard`
--

CREATE TABLE IF NOT EXISTS `noticeboard` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `notice_title` tinytext NOT NULL,
  `notice` text NOT NULL,
  `posted_by` int(11) unsigned NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_template` tinyint(1) DEFAULT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `posted_by` (`posted_by`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `noticeboard`
--

INSERT INTO `noticeboard` (`id`, `notice_title`, `notice`, `posted_by`, `status`, `created_date`, `modified_date`, `is_template`, `is_delete`, `is_testdata`) VALUES
(5, 'student notice', 'notice details', 1, '1', '2015-09-24 08:29:49', '0000-00-00 00:00:00', 1, 0, 'yes'),
(6, 'Teacher Notice', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\r\ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\r\nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\r\nconsequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\r\ncillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\r\nproident, sunt in culpa qui officia deserunt mollit anim id est laborum.', 1, '1', '2015-09-24 08:46:55', '0000-00-00 00:00:00', NULL, 0, 'yes'),
(7, 'school notice', 'School Notices', 1, '1', '2015-09-24 08:50:28', '0000-00-00 00:00:00', 1, 0, 'yes'),
(8, 'author test', 'author notice desc', 1, '1', '2015-09-24 09:09:58', '0000-00-00 00:00:00', NULL, 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `noticeboard_viewer`
--

CREATE TABLE IF NOT EXISTS `noticeboard_viewer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `notice_id` int(11) unsigned NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  `classroom_id` int(11) DEFAULT NULL COMMENT 'to define which class it should be shown. This can be null',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `notice_id` (`notice_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `noticeboard_viewer`
--

INSERT INTO `noticeboard_viewer` (`id`, `notice_id`, `role_id`, `classroom_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(2, 5, 2, NULL, '2015-09-24 08:29:49', '0000-00-00 00:00:00', 0, 'yes'),
(3, 6, 3, 4, '2015-09-24 08:46:55', '0000-00-00 00:00:00', 0, 'yes'),
(4, 7, 5, NULL, '2015-09-24 08:50:28', '0000-00-00 00:00:00', 0, 'yes'),
(5, 8, 4, NULL, '2015-09-24 09:09:58', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `pastimes`
--

CREATE TABLE IF NOT EXISTS `pastimes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pastime_name` text NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `permission_name` tinytext NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `permission_access`
--

CREATE TABLE IF NOT EXISTS `permission_access` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `permission_id` int(11) unsigned NOT NULL,
  `system_module_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `permission_id` (`permission_id`),
  KEY `system_module_id` (`system_module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `preferences`
--

CREATE TABLE IF NOT EXISTS `preferences` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `preference_key` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `promo_codes`
--

CREATE TABLE IF NOT EXISTS `promo_codes` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `promo_code` tinytext NOT NULL,
  `status` tinytext NOT NULL,
  `amount` int(10) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_text` tinytext NOT NULL,
  `difficulty_level` tinytext NOT NULL,
  `question_hint` tinytext NOT NULL,
  `question_format` tinytext NOT NULL,
  `question_creator_id` int(11) unsigned NOT NULL,
  `assets_link` tinytext NOT NULL,
  `question_image_link` tinytext NOT NULL,
  `evaluation_notes` text NOT NULL,
  `solution` text NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `subject_id` int(11) unsigned NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `question_creator_id` (`question_creator_id`),
  KEY `topic_id` (`topic_id`),
  KEY `subject_id` (`subject_id`),
  KEY `course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `question_ratings`
--

CREATE TABLE IF NOT EXISTS `question_ratings` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(11) unsigned NOT NULL,
  `rating_scale` int(10) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `reported_content`
--

CREATE TABLE IF NOT EXISTS `reported_content` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `content_type` tinytext NOT NULL,
  `content_id` int(11) unsigned NOT NULL,
  `comments` text NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '1-student,2-teacher,3-author,4-school,5-admin',
  `role_name` varchar(255) NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role_name`, `is_delete`, `is_testdata`, `created_date`, `modified_date`) VALUES
(1, 'admin', 0, 'yes', '2015-09-22 05:58:40', '0000-00-00 00:00:00'),
(2, 'student', 0, 'yes', '2015-09-22 05:58:40', '0000-00-00 00:00:00'),
(3, 'teacher', 0, 'yes', '2015-09-22 05:58:40', '0000-00-00 00:00:00'),
(4, 'author', 0, 'yes', '2015-09-22 05:58:40', '0000-00-00 00:00:00'),
(5, 'school', 0, 'yes', '2015-09-22 05:58:40', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `schools`
--

CREATE TABLE IF NOT EXISTS `schools` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `school_name` varchar(100) NOT NULL,
  `school_nickname` varchar(20) NOT NULL,
  `principal_name` varchar(20) NOT NULL,
  `school_code` int(11) NOT NULL,
  `school_type` enum('co-education','girls','boys') NOT NULL,
  `school_email_id` varchar(100) NOT NULL,
  `school_contact_no1` varchar(20) NOT NULL,
  `school_contact_no2` varchar(20) NOT NULL,
  `school_grade` varchar(4) NOT NULL,
  `school_mode` enum('day','day"/"boarding','day"/"hostel') NOT NULL,
  `address` varchar(100) NOT NULL,
  `district_id` int(11) unsigned NOT NULL,
  `city_id` int(11) unsigned NOT NULL,
  `state_id` int(11) unsigned NOT NULL,
  `country_id` int(11) unsigned NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `district_id` (`district_id`),
  KEY `city_id` (`city_id`),
  KEY `state_id` (`state_id`),
  KEY `country_id` (`country_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=62 ;

--
-- Dumping data for table `schools`
--

INSERT INTO `schools` (`id`, `school_name`, `school_nickname`, `principal_name`, `school_code`, `school_type`, `school_email_id`, `school_contact_no1`, `school_contact_no2`, `school_grade`, `school_mode`, `address`, `district_id`, `city_id`, `state_id`, `country_id`, `is_delete`, `is_testdata`, `created_date`, `modified_date`) VALUES
(3, 'lpsv', '', 'pareshsir', 0, 'co-education', 'lpsv@mail.com', '12345678', '12345678', 'A', 'day', 'adajan , surat', 1, 1, 1, 2, 0, 'yes', '2015-09-22 06:13:37', '0000-00-00 00:00:00'),
(4, 'Springdales School', '', 'Kessie Holman', 0, 'co-education', 'volutpat@eu.ca', '303-7329', '1-777-835-2447', 'A', 'day', 'Ap #669-9318 Libero. St.', 3, 3, 12, 2, 0, 'yes', '2015-09-22 09:21:50', '0000-00-00 00:00:00'),
(5, 'Saint Giri Senior Secondary School', '', 'Micah Mccarty', 0, 'co-education', 'enim.commodo.hendrerit@dolorsitamet.org', '852-4517', '871-3794', 'A', 'day', 'P.O. Box 371, 3796 Est, Rd.', 4, 4, 12, 2, 0, 'yes', '2015-09-22 09:56:39', '0000-00-00 00:00:00'),
(7, 'St. Columba''s School', '', 'Lee Summers', 0, 'co-education', 'Nullam@odio.com', '934-8387', '935-9311', 'B', 'day', 'P.O,Ap #777-7948, Tincidunt Av.', 5, 5, 12, 2, 0, 'yes', '2015-09-22 10:02:01', '0000-00-00 00:00:00'),
(8, 'St. Paul''s School', '', 'Chancellor Phelps', 0, 'co-education', 'ante@Integerid.edu', '291-0065', '957-4543', 'D', 'day', '135-844 Donec Road', 7, 7, 12, 2, 0, 'yes', '2015-09-22 10:08:50', '0000-00-00 00:00:00'),
(9, 'St. John''s Sr. Sec. School', '', 'Tashya Garrison', 0, 'co-education', 'facilisis.lorem@ut.ca', '1-165-642-8124', '841-3073', 'E', 'day', 'Ap #341-4144 Donec Rd.', 8, 8, 12, 2, 0, 'yes', '2015-09-22 10:10:28', '0000-00-00 00:00:00'),
(10, 'St. Mark''s Senior Secondary Public School', '', 'Faith Ferrell', 0, 'co-education', 'eget.dictum.placerat@liberolacus.edu', '426-1407', '1-890-448-7119', 'A', 'day', 'P.O. Box 266, 5153 Risus. Av.', 14, 14, 13, 2, 0, 'yes', '2015-09-22 10:10:28', '0000-00-00 00:00:00'),
(11, 'St. Mark''s Senior Secondary School', '', 'Gillian Holmes', 0, 'co-education', 'sociis@idnunc.net', '875-2513', '829-0522', 'B', 'day', 'Ap #842-3664 Mauris. St.', 15, 15, 27, 2, 0, 'yes', '2015-09-22 10:10:28', '0000-00-00 00:00:00'),
(12, 'St. Xavier''s School, Delhi', '', 'Rajah Hancock', 0, 'co-education', 'amet@amet.org', '1-827-762-1075', '1-296-335-4766', 'C', 'day', 'Ap #207-6039 Dui. Rd.', 17, 17, 27, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(13, 'Summer Fields School, New Delhi', '', 'Adrienne Kelley', 0, 'co-education', 'lacus@lacusMaurisnon.com', '1-451-991-9946', '334-3874', 'D', 'day', 'P.O. Box 371, 3796 Est, Rd.', 18, 18, 27, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(14, 'The Air Force School (Subroto Park)', '', 'Belle Owens', 0, 'co-education', 'dictum.ultricies.ligula@aultriciesadipiscing.org', '1-397-590-6845', '1-999-775-3961', 'E', 'day', 'P.O. Box 466, 6972 Non, Rd.', 19, 19, 27, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(15, 'The Mother''s International School', '', 'Acton Farley', 0, 'co-education', 'sollicitudin.commodo.ipsum@volutpatnunc.net', '307-3843', '876-1187', 'A', 'day', 'P.O. Box 326, 3802 Sed Road', 20, 20, 27, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(16, 'The Shri Ram School', '', 'Berk Sandoval', 0, 'co-education', 'velit.in@neque.com', '625-5749', '199-1556', 'B', 'day', 'Ap #658-4564 Tristique Rd.', 21, 21, 27, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(17, 'Vasant Valley School', '', 'Quinn Galloway', 0, 'co-education', 'amet.consectetuer.adipiscing@luctuset.edu', '1-630-667-7694', '485-5067', 'C', 'day', 'Ap #540-9323 Quis, Road', 22, 22, 23, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(18, 'Assagao Union High School', '', 'Ursula Guerra', 0, 'co-education', 'nulla.at.sem@etipsumcursus.edu', '414-7518', '920-7429', 'C', 'day', '447-3249 Tristique St.', 33, 33, 24, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(19, 'Little Flower of Jesus High School', '', 'Jermaine Moore', 0, 'co-education', 'molestie.orci@Sed.net', '136-3853', '1-840-350-3339', 'D', 'day', 'P.O. Box 440, 7305 Ipsum Av.', 34, 34, 24, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(20, 'Loyola High School', '', 'Merrill Bryant', 0, 'co-education', 'sed.sapien@utcursusluctus.edu', '765-9226', '665-2926', 'E', 'day', 'P.O. Box 820, 490 At, Avenue', 35, 35, 24, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(21, 'Regina Mundi High School', '', 'Hamilton Petty', 0, 'co-education', 'vulputate@Cras.com', '235-0454', '192-5932', 'A', 'day', '6289 Diam Avenue', 36, 36, 24, 2, 0, 'yes', '2015-09-22 10:13:15', '0000-00-00 00:00:00'),
(22, 'Regina Mundi School, Chicalim', '', 'Slade Munoz', 0, 'co-education', 'molestie.in.tempus@feugiatmetussit.edu', '781-5486', '1-829-275-5138', 'A', 'day', 'P.O. Box 192, 8452 In, Rd.', 43, 43, 25, 2, 0, 'yes', '2015-09-22 10:13:28', '0000-00-00 00:00:00'),
(23, 'SFX High School Siolim', '', 'Quon Cortez', 0, 'co-education', 'Donec@liberoProin.net', '628-0339', '207-5642', 'B', 'day', '900-253 Nunc Avenue', 44, 44, 25, 2, 0, 'yes', '2015-09-22 10:13:28', '0000-00-00 00:00:00'),
(24, 'Sharada Mandir High School', '', 'Madeson Finch', 0, 'co-education', 'pede@egestaslaciniaSed.org', '1-375-370-9664', '681-6914', 'C', 'day', 'P.O. Box 974, 3223 Ipsum St.', 45, 45, 25, 2, 0, 'yes', '2015-09-22 10:13:28', '0000-00-00 00:00:00'),
(25, 'Zydus School for Excellence', '', 'Desiree Barrera', 0, 'co-education', 'quis.diam@sedliberoProin.com', '1-156-501-7393', '1-815-691-6596', 'D', 'day', 'P.O. Box 546, 7547 Malesuada Rd.', 49, 49, 25, 2, 0, 'yes', '2015-09-22 10:13:28', '0000-00-00 00:00:00'),
(26, 'Shree Damodar Higher Secondary School Of Science', '', 'Erasmus Hull', 0, 'co-education', 'libero.et@sit.net', '931-6076', '1-480-243-6658', 'E', 'day', 'P.O. Box 372, 2380 Proin Avenue', 50, 50, 26, 2, 0, 'yes', '2015-09-22 10:13:28', '0000-00-00 00:00:00'),
(27, 'St. Britto high school', '', 'James Coffey', 0, 'co-education', 'Donec.est.Nunc@ipsum.co.uk', '426-0025', '378-0905', 'A', 'day', '3873 Dignissim St.', 51, 51, 26, 2, 0, 'yes', '2015-09-22 10:14:44', '0000-00-00 00:00:00'),
(28, 'St. Mary''s Convent High School', '', 'Otto Brooks', 0, 'co-education', 'Quisque.nonummy@dictumeueleifend.org', '370-1387', '1-641-439-4366', 'B', 'day', '640 Iaculis Road', 52, 52, 26, 2, 0, 'yes', '2015-09-22 10:14:44', '0000-00-00 00:00:00'),
(29, 'A. G. High School', '', 'Odessa Gordon', 0, 'co-education', 'massa@semperpretiumneque.net', '1-220-200-1439', '801-3043', 'B', 'day', 'Ap #893-7106 Ornare, Street', 53, 53, 26, 2, 0, 'yes', '2015-09-22 10:14:44', '0000-00-00 00:00:00'),
(30, 'Alfred High School', '', 'Montana Ramos', 0, 'co-education', 'mauris@eleifendnunc.edu', '591-7060', '787-8998', 'C', 'day', 'P.O. Box 584, 4329 Non, Ave', 54, 54, 26, 2, 0, 'yes', '2015-09-22 10:14:44', '0000-00-00 00:00:00'),
(31, 'Alfred High School', '', 'Anjolie Gentry', 0, 'co-education', 'cursus@sem.ca', '1-693-594-1249', '1-490-264-9963', 'D', 'day', '904-7926 Sagittis. Rd.', 55, 55, 18, 2, 0, 'yes', '2015-09-22 10:14:45', '0000-00-00 00:00:00'),
(32, 'The Calorx School', '', 'Chloe Mays', 0, 'co-education', 'ad.litora.torquent@Maecenas.co.uk', '1-856-443-9654', '1-673-956-7470', 'E', 'day', 'P.O. Box 674, 6167 Donec Rd.', 56, 56, 18, 2, 0, 'yes', '2015-09-22 10:14:45', '0000-00-00 00:00:00'),
(33, 'Crescent National High School ', '', 'Griffith Whitehead', 0, 'co-education', 'dapibus.quam@sodales.co.uk', '1-785-513-5818', '1-941-894-7877', 'A', 'day', '8207 Cras Avenue', 57, 57, 18, 2, 0, 'yes', '2015-09-22 10:14:45', '0000-00-00 00:00:00'),
(34, 'Apollo International School', '', 'Mia Wells', 0, 'co-education', 'convallis.in@odio.org', '1-237-735-5657', '1-615-463-3075', 'B', 'day', '2073 Et St.', 58, 58, 18, 2, 0, 'yes', '2015-09-22 10:14:45', '0000-00-00 00:00:00'),
(35, 'Bharti International Convent School', '', 'Molly Baxter', 0, 'co-education', 'commodo.auctor.velit@ullamcorperviverraMaecenas.net', '1-133-844-8221', '979-2924', 'C', 'day', 'Ap #487-5715 Ante. Ave', 59, 59, 18, 2, 0, 'yes', '2015-09-22 10:15:51', '0000-00-00 00:00:00'),
(36, 'St. Xavier''s High School Loyola Hall', '', 'Pandora Lowe', 0, 'co-education', 'orci.consectetuer@ametconsectetuer.co.uk', '945-3417', '892-9020', 'C', 'day', '1050 Accumsan St.', 60, 60, 18, 2, 0, 'yes', '2015-09-22 10:15:51', '0000-00-00 00:00:00'),
(37, 'St. Augustine''s Higher Secondary School, Karimkunnam', '', 'Eleanor Robinson', 0, 'co-education', 'molestie.arcu.Sed@urnajusto.co.uk', '176-8334', '857-6518', 'D', 'day', 'Ap #913-8149 Tortor. Road', 61, 61, 18, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(38, 'St. Francis Sales Central School', '', 'Ainsley Stevenson', 0, 'co-education', 'quis@Praesenteudui.net', '102-6038', '994-5924', 'E', 'day', 'Ap #878-9663 Sed Av.', 62, 62, 18, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(39, 'St. Joseph''s Convent School', '', 'Odysseus Aguilar', 0, 'co-education', 'vel.arcu.eu@ametorciUt.com', '1-491-408-7057', '1-580-199-0199', 'A', 'day', 'P.O. Box 123, 6794 Eget Avenue', 63, 63, 18, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(40, 'St Joseph''s Higher Secondary School', '', 'Julie Gordon', 0, 'co-education', 'luctus.sit@vitae.net', '1-396-169-8974', '1-524-177-5444', 'A', 'day', '1261 Rhoncus. St.', 64, 64, 18, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(41, 'St. Mary''s Residential Central School', '', 'Callum Nieves', 0, 'co-education', 'mollis.nec.cursus@purus.co.uk', '1-432-254-3494', '208-1375', 'B', 'day', 'Ap #154-2886 Luctus Rd.', 65, 65, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(42, 'St Peters School, Kadayiruppu', '', 'Veda Levine', 0, 'co-education', 'nisl.sem@Sed.net', '791-3150', '1-319-132-7090', 'C', 'day', '267 Eu Rd.', 66, 66, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(43, 'St Stephens School', '', 'Cheryl Freeman', 0, 'co-education', 'est@Suspendissenonleo.edu', '1-842-904-9297', '1-777-835-2447', 'D', 'day', '5996 Velit Street', 67, 67, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(44, 'St. Thomas Convent School', '', 'Macaulay Guerrero', 0, 'co-education', 'Morbi.metus@dictumultriciesligula.com', '1-949-861-9843', '871-3794', 'E', 'day', 'P.O. Box 139, 9269 Mauris, Rd.', 68, 68, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(45, 'Technical Higher Secondary School', '', 'Kevin Hancock', 0, 'co-education', 'dictum.eu@mollisIntegertincidunt.edu', '1-475-229-8776', '935-9311', 'A', 'day', '197-470 Quisque St.', 69, 69, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(46, 'St. Joseph''s Convent School, Bhopal', '', 'Ariel Davis', 0, 'co-education', 'risus.Quisque.libero@dui.co.uk', '1-612-794-8111', '978-6654', 'B', 'day', '450-687 Donec Road', 70, 70, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(47, 'St.Paul Higher Secondary School, Indore', '', 'Sharon Delaney', 0, 'co-education', 'Nulla.dignissim.Maecenas@semsempererat.ca', '1-462-655-1683', '957-4543', 'B', 'day', '9164 Venenatis Road', 71, 71, 19, 2, 0, 'yes', '2015-09-22 10:17:01', '0000-00-00 00:00:00'),
(48, 'Sanskaar Valley School, Bhopal', '', 'Kane Dickson', 0, 'co-education', 'consequat.auctor.nunc@Proinsed.com', '1-342-200-3935', '1-890-448-7119', 'D', 'day', 'P.O. Box 917, 4621 Diam. Av.', 76, 76, 22, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(49, 'Vidya Niketan School, Chhindwara', '', 'Kasper Hull', 0, 'co-education', 'egestas.Duis@orci.edu', '1-181-280-4171', '829-0522', 'E', 'day', 'Ap #995-2858 Nullam Ave', 77, 77, 22, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(50, 'Delhi Public School, Bhopal', '', 'Phillip Melton', 0, 'co-education', 'tellus.Suspendisse@facilisis.edu', '138-2970', '1-584-498-5975', 'A', 'day', 'P.O. Box 875, 6901 Adipiscing Rd.', 78, 78, 22, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(51, 'Motilal Nehru School of Sports, Rai', '', 'Kellie Love', 0, 'co-education', 'Sed@semmagnanec.net', '1-568-952-4866', '1-296-335-4766', 'B', 'day', '602-4261 Ac Av.', 79, 79, 16, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(52, 'Rishikul Vidyapeeth, Sonipat', '', 'Maggy Goodman', 0, 'co-education', 'Sed@Phaselluslibero.ca', '909-1766', '334-3874', 'C', 'day', 'Ap #164-496 Quam Av.', 85, 85, 14, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(53, 'Vidya Devi Jindal School', '', 'Ronan Bailey', 0, 'co-education', 'neque.venenatis@tortor.edu', '1-768-335-4477', '1-999-775-3961', 'C', 'day', '9471 A Ave', 86, 86, 14, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(54, 'Shalom Hills International School', '', 'Moses Rutledge', 0, 'co-education', 'natoque.penatibus.et@amet.org', '1-485-235-9960', '876-1187', 'D', 'day', 'Ap #736-7056 Duis St.', 87, 87, 14, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(55, 'The Shri Ram School', '', 'Christian Padilla', 0, 'co-education', 'vitae.nibh@volutpatNulladignissim.org', '579-8198', '199-1556', 'E', 'day', 'P.O. Box 916, 8220 Suspendisse Ave', 88, 88, 15, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(56, 'Doon International School', '', 'Jael Harper', 0, 'co-education', 'lorem@nec.org', '1-846-409-4219', '485-5067', 'A', 'day', 'P.O. Box 532, 9867 Cum Rd.', 96, 96, 31, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(57, 'Mahatma Gandhi International School', '', 'Wanda Powell', 0, 'co-education', 'ut.aliquam@eu.net', '1-272-277-0477', '920-7429', 'B', 'day', '9904 Turpis Street', 97, 97, 31, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(58, 'Mount Carmel High School, Ahmedabad', '', 'Garrison Hopkins', 0, 'co-education', 'tempus.non@Phasellus.edu', '341-4227', '1-840-350-3339', 'C', 'day', '252-7673 Eget Ave', 91, 91, 17, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(59, 'Mount Carmel High School, Gandhinagar', '', 'Minerva Marshall', 0, 'co-education', 'lacinia.at.iaculis@semconsequatnec.org', '191-6225', '665-2926', 'D', 'day', '702-1144 Luctus, Ave', 98, 98, 32, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(60, 'Sheth Chimanlal Nagindas Vidyalaya', '', 'Riley Small', 0, 'co-education', 'accumsan.laoreet@ametfaucibusut.com', '1-608-488-2988', '192-5932', 'E', 'day', '869-3111 Et Avenue', 99, 99, 32, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00'),
(61, 'St. Ann''s School, Ahmedabad', '', 'George Holder', 0, 'co-education', 'libero.nec.ligula@ultricesposuerecubilia.co.uk', '1-964-982-2191', '1-829-275-5138', 'E', 'day', 'P.O. Box 790, 3538 Consectetuer Rd.', 100, 100, 32, 2, 0, 'yes', '2015-09-22 10:17:44', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `school_classroom`
--

CREATE TABLE IF NOT EXISTS `school_classroom` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `classroom_id` int(11) unsigned NOT NULL,
  `classroom_name` text NOT NULL,
  `school_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `classroom_id` (`classroom_id`),
  KEY `school_id` (`school_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `school_classroom`
--

INSERT INTO `school_classroom` (`id`, `classroom_id`, `classroom_name`, `school_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 2, 'test', 3, '2015-09-23 09:53:09', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `school_course`
--

CREATE TABLE IF NOT EXISTS `school_course` (
  `id` int(11) NOT NULL,
  `school_id` int(11) unsigned NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  KEY `school_id` (`school_id`),
  KEY `course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `school_course`
--

INSERT INTO `school_course` (`id`, `school_id`, `course_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 3, 2, '2015-09-23 10:38:41', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE IF NOT EXISTS `states` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `state_name` varchar(30) NOT NULL,
  `country_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `country_id` (`country_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Dumping data for table `states`
--

INSERT INTO `states` (`id`, `state_name`, `country_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'gujarat', 2, '2015-09-22 06:10:39', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'Ashanti', 1, '2015-09-22 06:56:58', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Brong-Ahafo', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Greater Accra  ', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Central', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(6, 'Eastern', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(7, 'Northern', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(8, 'Western', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(9, 'Upper East', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(10, 'Upper West', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(11, 'Volta', 1, '2015-09-22 08:44:46', '0000-00-00 00:00:00', 0, 'yes'),
(12, 'Arunachal Pradesh', 2, '2015-09-22 08:51:11', '0000-00-00 00:00:00', 0, 'yes'),
(13, 'Assam', 2, '2015-09-22 08:51:49', '0000-00-00 00:00:00', 0, 'yes'),
(14, 'Bihar', 2, '2015-09-22 08:51:49', '0000-00-00 00:00:00', 0, 'yes'),
(15, 'Chhattisgarh', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(16, 'Goa', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(17, 'Gujarat', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(18, 'Haryana', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(19, 'Faridabad', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(20, 'Himachal Pradesh', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(21, 'Jammu and Kashmir', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(22, 'Jharkhand', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(23, 'Karnataka', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(24, 'Kerala', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(25, 'Madhya Pradesh', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(26, 'Maharashtra', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(27, 'Manipur', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(28, 'Meghalaya', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(29, 'Mizoram', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(30, 'Punjab', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(31, 'Rajasthan', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(32, 'Sikkim', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(33, 'Tamil Nadu', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(34, 'Tripura', 2, '2015-09-22 08:51:50', '0000-00-00 00:00:00', 0, 'yes'),
(35, 'Uttar Pradesh', 2, '2015-09-22 08:51:51', '0000-00-00 00:00:00', 0, 'yes'),
(36, 'Uttarakhand', 2, '2015-09-22 08:51:51', '0000-00-00 00:00:00', 0, 'yes'),
(37, 'West Bengal', 2, '2015-09-22 08:51:51', '0000-00-00 00:00:00', 0, 'yes'),
(38, 'Chandigarh', 2, '2015-09-22 08:51:51', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `student_academic_info`
--

CREATE TABLE IF NOT EXISTS `student_academic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `school_id` int(11) unsigned NOT NULL,
  `classroom_id` int(11) unsigned NOT NULL,
  `academic_year` varchar(255) NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `school_id` (`school_id`),
  KEY `classroom_id` (`classroom_id`),
  KEY `year_id` (`academic_year`),
  KEY `course_id` (`course_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `student_academic_info`
--

INSERT INTO `student_academic_info` (`id`, `user_id`, `school_id`, `classroom_id`, `academic_year`, `course_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 1, 3, 2, '2015-2016', 2, '2015-09-23 09:17:13', '0000-00-00 00:00:00', 0, 'yes'),
(2, 16, 7, 3, '2015-2016', 4, '2015-09-23 09:18:12', '0000-00-00 00:00:00', 0, 'yes'),
(3, 17, 9, 2, '2015-2016', 4, '2015-09-23 09:18:12', '0000-00-00 00:00:00', 0, 'yes'),
(4, 18, 12, 6, '2015-2016', 14, '2015-09-23 09:18:33', '0000-00-00 00:00:00', 0, 'yes'),
(5, 19, 5, 6, '2015-2016', 14, '2015-09-23 09:18:33', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `student_exam_response`
--

CREATE TABLE IF NOT EXISTS `student_exam_response` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `exam_id` int(11) unsigned NOT NULL,
  `question_id` int(11) unsigned NOT NULL,
  `choice_id` int(11) unsigned NOT NULL,
  `answer_text` int(11) unsigned NOT NULL,
  `is_right` tinytext NOT NULL,
  `response_duration` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `exam_id` (`exam_id`),
  KEY `question_id` (`question_id`),
  KEY `answer_text` (`answer_text`),
  KEY `choice_id` (`choice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `student_exam_score`
--

CREATE TABLE IF NOT EXISTS `student_exam_score` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `exam_id` int(11) unsigned NOT NULL,
  `attempt_count` int(11) unsigned NOT NULL,
  `correct_answers` int(11) unsigned NOT NULL,
  `incorrect_answers` int(11) unsigned NOT NULL,
  `total_time_spent` int(11) unsigned NOT NULL,
  `evaluation_privacy` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `exam_id` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `student_profile`
--

CREATE TABLE IF NOT EXISTS `student_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `total_score` int(11) unsigned NOT NULL,
  `total_exams` int(11) unsigned NOT NULL,
  `total_badges` int(11) unsigned NOT NULL,
  `total_questions_asked` int(11) unsigned NOT NULL,
  `total_posts` int(11) unsigned NOT NULL,
  `rank` int(11) unsigned NOT NULL,
  `wallet_balance` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `student_subjective_evaluation`
--

CREATE TABLE IF NOT EXISTS `student_subjective_evaluation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `evaluation_by` int(11) unsigned NOT NULL,
  `question_id` int(11) unsigned NOT NULL,
  `evaluation_score` int(11) unsigned NOT NULL,
  `evaluation_notes` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `evaluation_by` (`evaluation_by`),
  KEY `question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `studymates`
--

CREATE TABLE IF NOT EXISTS `studymates` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mate_id` int(11) unsigned NOT NULL,
  `mate_of` int(11) unsigned NOT NULL,
  `is_online` int(5) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `mate_id` (`mate_id`),
  KEY `mate_of` (`mate_of`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `studymates_request`
--

CREATE TABLE IF NOT EXISTS `studymates_request` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `request_from_mate_id` int(11) unsigned NOT NULL,
  `request_to_mate_id` int(11) unsigned NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `request_from_mate_id` (`request_from_mate_id`),
  KEY `request_to_mate_id` (`request_to_mate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE IF NOT EXISTS `subjects` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(30) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`id`, `subject_name`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'Physics', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'Chemistry', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Mathematics', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Biology', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Economics', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(6, 'Accountancy', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(7, 'Business Studies', '2015-09-22 20:49:35', '0000-00-00 00:00:00', 0, 'yes'),
(8, 'English', '2015-09-22 20:50:15', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `surveys`
--

CREATE TABLE IF NOT EXISTS `surveys` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `survey_title` tinytext NOT NULL,
  `survey_description` tinytext NOT NULL,
  `created_by` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `created_by` (`created_by`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey_answer_choice`
--

CREATE TABLE IF NOT EXISTS `survey_answer_choice` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `survey_question_id` int(11) unsigned NOT NULL,
  `choice_text` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `survey_question_id` (`survey_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey_audience`
--

CREATE TABLE IF NOT EXISTS `survey_audience` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `survey_id` int(11) unsigned NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `survey_id` (`survey_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey_question`
--

CREATE TABLE IF NOT EXISTS `survey_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_text` tinytext NOT NULL,
  `question_hint` tinytext NOT NULL,
  `question_format` tinytext NOT NULL,
  `question_creator_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `question_creator_id` (`question_creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `survey_response`
--

CREATE TABLE IF NOT EXISTS `survey_response` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `survey_question_id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `choice_id` int(11) unsigned NOT NULL,
  `answer_text` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `survey_question_id` (`survey_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `system_module`
--

CREATE TABLE IF NOT EXISTS `system_module` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `module_name` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tag_name` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tags_book`
--

CREATE TABLE IF NOT EXISTS `tags_book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `book_id` int(11) unsigned NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `book_id` (`book_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tags_forum_question`
--

CREATE TABLE IF NOT EXISTS `tags_forum_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `forum_question_id` int(11) unsigned NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `forum_question_id` (`forum_question_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tags_lecture`
--

CREATE TABLE IF NOT EXISTS `tags_lecture` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) unsigned NOT NULL,
  `lecture_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `tag_id` (`tag_id`),
  KEY `lecture_id` (`lecture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tags_question`
--

CREATE TABLE IF NOT EXISTS `tags_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(11) unsigned NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tag_book_assignment`
--

CREATE TABLE IF NOT EXISTS `tag_book_assignment` (
  `id` int(11) unsigned NOT NULL,
  `book_assignment_id` int(11) unsigned NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  KEY `book_assignment_id` (`book_assignment_id`),
  KEY `tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `teacher_profile`
--

CREATE TABLE IF NOT EXISTS `teacher_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `total_assignment` int(11) unsigned NOT NULL,
  `total_submission_received` int(11) unsigned NOT NULL,
  `total_posts` int(11) unsigned NOT NULL,
  `total_badges` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `teacher_subject_info`
--

CREATE TABLE IF NOT EXISTS `teacher_subject_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `subject_id` int(11) unsigned NOT NULL,
  `class_name` text NOT NULL,
  `school_id` int(11) unsigned NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `year_id` int(5) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `subject_id` (`subject_id`),
  KEY `school_id` (`school_id`),
  KEY `course_id` (`course_id`),
  KEY `year_id` (`year_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `topics`
--

CREATE TABLE IF NOT EXISTS `topics` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `topic_name` tinytext NOT NULL,
  `topic_description` text NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `subject_id` int(11) unsigned NOT NULL,
  `year_id` int(5) NOT NULL,
  `evaluation_keywords` tinytext NOT NULL,
  `allocation_count` int(11) unsigned NOT NULL,
  `created_by` int(11) unsigned NOT NULL,
  `status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `created_by` (`created_by`),
  KEY `course_id` (`course_id`),
  KEY `subject_id` (`subject_id`),
  KEY `year_id` (`year_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `topics`
--

INSERT INTO `topics` (`id`, `topic_name`, `topic_description`, `course_id`, `subject_id`, `year_id`, `evaluation_keywords`, `allocation_count`, `created_by`, `status`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'Chemistry - Banzin', 'Discussion about banzin ', 1, 1, 1, 'banzin Ch4', 0, 1, '1', '2015-09-24 04:48:40', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `tutorial_groups`
--

CREATE TABLE IF NOT EXISTS `tutorial_groups` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` tinytext NOT NULL,
  `group_profile_pic` tinytext,
  `group_type` tinytext NOT NULL,
  `group_status` tinyint(1) NOT NULL DEFAULT '0',
  `is_completed` tinyint(1) NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=43 ;

--
-- Dumping data for table `tutorial_groups`
--

INSERT INTO `tutorial_groups` (`id`, `group_name`, `group_profile_pic`, `group_type`, `group_status`, `is_completed`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'Test Group', '', '', 0, 1, '2015-09-22 05:47:18', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `tutorial_group_discussion`
--

CREATE TABLE IF NOT EXISTS `tutorial_group_discussion` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) unsigned NOT NULL,
  `sender_id` int(11) unsigned NOT NULL,
  `message` text NOT NULL,
  `message_type` tinytext NOT NULL,
  `message_status` tinytext NOT NULL,
  `media_link` tinytext NOT NULL,
  `media_type` tinytext NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `sender_id` (`sender_id`),
  KEY `topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tutorial_group_member`
--

CREATE TABLE IF NOT EXISTS `tutorial_group_member` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `joining_status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=68 ;

--
-- Dumping data for table `tutorial_group_member`
--

INSERT INTO `tutorial_group_member` (`id`, `group_id`, `user_id`, `joining_status`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 1, 1, '1', '2015-09-22 05:48:09', '0000-00-00 00:00:00', 0, 'yes'),
(4, 1, 16, '1', '2015-09-22 06:43:33', '0000-00-00 00:00:00', 0, 'yes'),
(5, 1, 17, '1', '2015-09-22 06:43:33', '0000-00-00 00:00:00', 0, 'yes'),
(6, 1, 18, '1', '2015-09-22 06:43:47', '0000-00-00 00:00:00', 0, 'yes'),
(7, 1, 19, '1', '2015-09-22 06:43:52', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `tutorial_group_member_score`
--

CREATE TABLE IF NOT EXISTS `tutorial_group_member_score` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) unsigned NOT NULL,
  `score` int(11) unsigned NOT NULL,
  `member_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tutorial_group_question`
--

CREATE TABLE IF NOT EXISTS `tutorial_group_question` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `question_id` int(11) unsigned NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `tutorial_group_topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tutorial_group_topic_allocation`
--

CREATE TABLE IF NOT EXISTS `tutorial_group_topic_allocation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) unsigned NOT NULL,
  `interface_type` tinytext NOT NULL,
  `date_day` tinytext NOT NULL,
  `status` tinytext NOT NULL,
  `topic_id` int(11) unsigned NOT NULL,
  `group_score` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `group_id` (`group_id`),
  KEY `topic_id` (`topic_id`),
  KEY `group_score` (`group_score`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tutorial_group_topic_allocation`
--

INSERT INTO `tutorial_group_topic_allocation` (`id`, `group_id`, `interface_type`, `date_day`, `status`, `topic_id`, `group_score`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 1, '', '', '', 1, 0, '2015-09-24 04:50:05', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  `contact_number` varchar(20) DEFAULT NULL,
  `home_address` text,
  `city_id` int(11) unsigned DEFAULT NULL,
  `state_id` int(11) unsigned DEFAULT NULL,
  `country_id` int(11) unsigned DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `device_type` varchar(10) DEFAULT NULL,
  `device_token` varchar(80) DEFAULT NULL,
  `role_id` int(11) unsigned DEFAULT NULL,
  `user_status` enum('active','blocked','inactive') DEFAULT NULL,
  `about_me` text,
  `user_current_api_version` int(11) DEFAULT NULL,
  `membership_validity_date` date DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`),
  KEY `state_id` (`state_id`),
  KEY `country_id` (`country_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=110 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `first_name`, `last_name`, `full_name`, `email_id`, `contact_number`, `home_address`, `city_id`, `state_id`, `country_id`, `birthdate`, `gender`, `device_type`, `device_token`, `role_id`, `user_status`, `about_me`, `user_current_api_version`, `membership_validity_date`, `package_id`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(1, 'admin', '5A2DUoO70Cm3UkFbrzOfwJTOYFif37xLL33BkKYFmQ/eK6NS+JZUorq3nib+JU4xg/San70bubMpOFGM/naQ6w==', NULL, NULL, NULL, 'admin@mail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, 'active', NULL, NULL, NULL, NULL, '2015-09-22 04:54:11', '0000-00-00 00:00:00', 0, 'yes'),
(2, 'sandip', 'idpz2B6PZfibh/q8DoHgW4BQbTgKD7Pb7DWmmWWBTbz32G5BV1Nu0J+3o6C9ogG1Ojx50qy5JL/7mbdhi76Zxw==', 'Sandip', 'Patel', NULL, 'kap.narola@narolainfotech.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'active', NULL, NULL, NULL, NULL, '2015-09-22 05:50:09', '0000-00-00 00:00:00', 0, 'yes'),
(3, 'Khushboo', NULL, 'Khushboo', 'Hathi', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'active', NULL, NULL, NULL, NULL, '2015-09-22 05:50:51', '0000-00-00 00:00:00', 0, 'yes'),
(4, 'Sheetal', NULL, 'sheetal', 'patel', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'active', NULL, NULL, NULL, NULL, '2015-09-22 05:51:24', '0000-00-00 00:00:00', 0, 'yes'),
(5, 'Pratik', NULL, 'Pratik', 'shah', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'active', NULL, NULL, NULL, NULL, '2015-09-22 05:51:45', '0000-00-00 00:00:00', 0, 'yes'),
(14, 'test', 'G5dOsO5c9DjXVe/V50HqvSXxO1OugvYfvGkPdpJkjKLeHk4+Bjd0sNMXnA+5p+H64XBFS8z447BNlaOB3qsyRQ==', NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, NULL, 'Gender', NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, '2015-09-22 10:49:09', '2015-09-22 10:49:09', 0, 'yes'),
(15, 'E1K0A4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Emily', 'Bryan', 'Emily Bryan', 'EmilyBryana@gmail.com', '259-2847', '627-7110 Dis Rd.', 3, 12, 2, '1988-05-23', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 11:32:37', '0000-00-00 00:00:00', 0, 'yes'),
(16, 'D0I2B5', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Eagan', 'Tanner', 'Eagan  Tanner', 'EaganTannera@gmail.com', '514-7447', 'Ap #416-8971 Aliquam St.', 4, 12, 2, '1989-11-08', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:15', '0000-00-00 00:00:00', 0, 'yes'),
(17, 'T7O1X4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Palmer', 'Wynn', 'Palmer  Wynn', 'PalmerWynna@gmail.com', '1-393-334-5141', '616 Vestibulum Avenue', 5, 12, 2, '1990-07-06', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(18, 'M0I2N0', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Branden', 'Justice', 'Branden  Justice', 'BrandenJusticea@gmail.com', '706-2196', '351-7788 Risus. Road', 6, 12, 2, '1991-03-08', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(19, 'Y0X8G0', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Sawyer', 'Flynn', 'Sawyer  Flynn', 'SawyerFlynna@gmail.com', '727-1192', 'P.O. Box 344, 1915 Primis Ave', 7, 12, 2, '1992-08-01', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(20, 'U0W2G4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Harriet', 'Ingram', 'Harriet  Ingram', 'HarrietIngrama@gmail.com', '1-661-807-0437', '179-6085 Lobortis Avenue', 8, 12, 2, '1993-01-02', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(21, 'N4H2V7', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Wesley', 'Cherry', 'Wesley  Cherry', 'WesleyCherrya@gmail.com', '1-877-294-9937', 'Ap #190-9175 Eu St.', 14, 13, 2, '1994-07-22', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(22, 'S9A0L2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Ayanna', 'Zimmerman', 'Ayanna  Zimmerman', 'AyannaZimmermana@gmail.com', '1-848-744-5152', '944-5973 Tellus Street', 15, 27, 2, '1995-10-23', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(23, 'K9A0Z0', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Gillian', 'Underwood', 'Gillian  Underwood', 'GillianUnderwooda@gmail.com', '1-879-345-4043', '113 Pretium Street', 16, 27, 2, '1996-08-08', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(24, 'I1L5N4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Daniel', 'Leblanc', 'Daniel  Leblanc', 'DanielLeblanca@gmail.com', '156-0727', 'Ap #998-219 Mauris St.', 17, 27, 2, '1997-08-21', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(25, 'P4W5L3', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Priscilla', 'Moss', 'Priscilla  Moss', 'PriscillaMossa@gmail.com', '1-583-911-0941', '560-4386 Aliquam Street', 18, 27, 2, '1998-07-25', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(26, 'L5C5F6', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Iola', 'Mason', 'Iola  Mason', 'IolaMasona@gmail.com', '784-8872', 'P.O. Box 236, 5023 Penatibus St.', 19, 27, 2, '1988-06-13', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(27, 'E9X9U4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Adrienne', 'Mendez', 'Adrienne  Mendez', 'AdrienneMendeza@gmail.com', '1-120-702-0468', 'P.O. Box 530, 1765 Bibendum St.', 20, 27, 2, '1989-04-27', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(28, 'O0W3J3', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Piper', 'Kirby', 'Piper  Kirby', 'PiperKirbya@gmail.com', '1-921-408-0064', '581-8351 Pretium Av.', 21, 27, 2, '1990-12-05', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(29, 'R4Q9A2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Carla', 'Smith', 'Carla  Smith', 'CarlaSmitha@gmail.com', '338-5189', 'P.O. Box 812, 6890 Convallis Rd.', 22, 23, 2, '1991-01-05', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(30, 'V4L7L9', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Ashely', 'Guerrero', 'Ashely  Guerrero', 'AshelyGuerreroa@gmail.com', '1-572-693-0932', '2197 Sit St.', 33, 24, 2, '1992-01-04', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:44', '0000-00-00 00:00:00', 0, 'yes'),
(31, 'V7K2O8', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Octavius', 'Thornton', 'Octavius  Thornton', 'OctaviusThorntona@gmail.com', '1-815-505-6323', '539-3396 Orci. Rd.', 34, 24, 2, '1993-07-02', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(32, 'E5U1S4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Kalia', 'Barton', 'Kalia  Barton', 'KaliaBartona@gmail.com', '1-119-361-6298', '6039 Id, Rd.', 35, 24, 2, '1994-05-11', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(33, 'V2K3S2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Russell', 'Carroll', 'Russell  Carroll', 'RussellCarrolla@gmail.com', '349-5521', '6156 Porttitor Ave', 36, 24, 2, '1995-10-25', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(34, 'G7V4H1', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Dalton', 'Crosby', 'Dalton  Crosby', 'DaltonCrosbya@gmail.com', '1-350-223-3528', 'P.O. Box 298, 8082 Auctor Rd.', 43, 25, 2, '1996-01-24', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(35, 'C5S2Q7', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Tyrone', 'Mcintyre', 'Tyrone  Mcintyre', 'TyroneMcintyrea@gmail.com', '1-559-776-1105', 'Ap #902-6941 Integer Ave', 44, 25, 2, '1997-10-06', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(36, 'E5Z5B4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Caldwell', 'Warner', 'Caldwell  Warner', 'CaldwellWarnera@gmail.com', '1-829-490-5392', '9321 Dignissim Rd.', 45, 25, 2, '1998-11-24', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(37, 'E5K7F3', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Damian', 'Edwards', 'Damian  Edwards', 'DamianEdwardsa@gmail.com', '875-1506', '3668 Libero Ave', 49, 25, 2, '1988-03-26', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(38, 'Q7H9E0', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Kim', 'Avery', 'Kim  Avery', 'KimAverya@gmail.com', '520-3740', 'Ap #919-685 Sit St.', 50, 26, 2, '1989-09-19', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(39, 'Q0H3K1', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Lee', 'Blair', 'Lee  Blair', 'LeeBlaira@gmail.com', '1-500-446-6091', 'Ap #732-1201 Donec Avenue', 51, 26, 2, '1990-08-06', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(40, 'W2W2W4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Neve', 'Scott', 'Neve  Scott', 'NeveScotta@yahoo.com', '1-286-769-7518', 'Ap #523-3385 Ipsum Ave', 52, 26, 2, '1991-02-17', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(41, 'K7P3G2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Sloane', 'Richardson', 'Sloane  Richardson', 'SloaneRichardsona@yahoo.com', '1-420-730-1637', 'Ap #842-5800 Et Road', 53, 26, 2, '1992-01-30', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(42, 'S1W2Y8', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Caesar', 'Sanders', 'Caesar  Sanders', 'CaesarSandersa@yahoo.com', '1-675-123-8809', '9669 Tempor Rd.', 54, 26, 2, '1993-07-01', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(43, 'A0Y6Y9', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Aaron', 'Abbott', 'Aaron  Abbott', 'AaronAbbotta@yahoo.com', '1-137-819-7901', 'P.O. Box 257, 8562 Lorem Road', 55, 18, 2, '1994-12-23', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(44, 'V9S8I5', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Tanek', 'Madden', 'Tanek  Madden', 'TanekMaddena@yahoo.com', '377-8738', 'P.O. Box 958, 8574 Conubia St.', 56, 18, 2, '1995-08-28', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(45, 'E0U4W3', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Reuben', 'Jimenez', 'Reuben  Jimenez', 'ReubenJimeneza@yahoo.com', '151-8305', 'Ap #576-6670 Vivamus Ave', 57, 18, 2, '1996-03-06', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(46, 'C2F6H5', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Zephania', 'Bishop', 'Zephania  Bishop', 'ZephaniaBishopa@yahoo.com', '1-254-261-3630', 'Ap #344-1074 Ac, Rd.', 58, 18, 2, '1997-05-05', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(47, 'J5O2H3', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Beck', 'Hodge', 'Beck  Hodge', 'BeckHodgea@yahoo.com', '1-782-398-3250', '998-3111 Mi Rd.', 59, 18, 2, '1998-12-06', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(48, 'O3W8V7', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Colby', 'Mckee', 'Colby  Mckee', 'ColbyMckeea@yahoo.com', '1-145-140-4217', 'P.O. Box 490, 428 Rhoncus. St.', 60, 18, 2, '1988-12-02', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(49, 'M0U8V5', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Adria', 'Petty', 'Adria  Petty', 'AdriaPettya@yahoo.com', '665-4738', 'Ap #210-2573 Dapibus St.', 61, 18, 2, '1989-10-07', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(50, 'T6Y7W0', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Wyatt', 'Torres', 'Wyatt  Torres', 'WyattTorresa@yahoo.com', '222-5803', 'P.O. Box 212, 1040 Ipsum Street', 62, 18, 2, '1990-05-18', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(51, 'B4Z7C9', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Quinn', 'Collier', 'Quinn  Collier', 'QuinnColliera@yahoo.com', '1-578-877-8026', '754-3453 Ut, St.', 63, 18, 2, '1991-08-04', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(52, 'R2Q0R4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Janna', 'Beasley', 'Janna  Beasley', 'JannaBeasleya@yahoo.com', '1-460-125-8711', 'Ap #520-2084 Parturient Street', 64, 18, 2, '1992-09-07', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(53, 'T4L9E2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Aurelia', 'Osborne', 'Aurelia  Osborne', 'AureliaOsbornea@gmail.com', '323-8744', '784-3994 Elementum, St.', 65, 19, 2, '1993-09-22', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(54, 'S3Q9K8', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Coby', 'Wolf', 'Coby  Wolf', 'CobyWolfa@gmail.com', '711-3868', '946-8010 Egestas. Av.', 66, 19, 2, '1994-06-15', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(55, 'H8B7H8', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Holmes', 'Morse', 'Holmes  Morse', 'HolmesMorsea@gmail.com', '1-890-930-9233', 'Ap #506-847 Cursus St.', 67, 19, 2, '1995-02-16', 'Male', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(56, 'D5W1F4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Kitra', 'Perry', 'Kitra  Perry', 'KitraPerrya@gmail.com', '466-9445', 'P.O. Box 288, 4212 Magna St.', 68, 19, 2, '1996-07-18', 'Female', 'Android', NULL, 2, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(57, 'O2A1A2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Nehru', 'Holt', 'Nehru  Holt', 'NehruHolta@gmail.com', '1-356-278-8020', 'Ap #110-7549 Nulla Road', 69, 19, 2, '1997-12-07', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(58, 'B4R2C1', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Delilah', 'Mullins', 'Delilah  Mullins', 'DelilahMullinsa@gmail.com', '1-839-291-4664', '8099 Vel, Rd.', 70, 19, 2, '1998-07-16', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(59, 'Z3F1T0', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Whilemina', 'Kidd', 'Whilemina  Kidd', 'WhileminaKidda@yahoo.com', '1-287-748-9370', 'Ap #586-3575 Pellentesque, Street', 71, 19, 2, '1988-03-28', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(60, 'H6K4U3', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Kimberly', 'Guy', 'Kimberly  Guy', 'KimberlyGuya@yahoo.com', '1-723-921-4886', 'P.O. Box 923, 3866 Dictum Street', 72, 21, 2, '1989-09-15', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(61, 'R4G2R2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Rooney', 'Gay', 'Rooney  Gay', 'RooneyGaya@yahoo.com', '790-9773', '7662 Vel, Avenue', 76, 22, 2, '1990-10-10', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(62, 'E1I2T5', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Hermione', 'Avery', 'Hermione  Avery', 'HermioneAverya@yahoo.com', '634-9290', 'Ap #224-510 Auctor Rd.', 77, 22, 2, '1991-11-27', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(63, 'K6E8M8', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Giacomo', 'Ellis', 'Giacomo  Ellis', 'GiacomoEllisa@yahoo.com', '1-777-634-0592', 'Ap #828-7146 Mauris Rd.', 78, 22, 2, '1992-12-25', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(64, 'M6Z8R6', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Tasha', 'Hatfield', 'Tasha  Hatfield', 'TashaHatfielda@gmail.com', '1-610-376-2175', '3500 Phasellus Street', 79, 16, 2, '1993-06-05', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(65, 'C4X5X6', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Herman', 'Wade', 'Herman  Wade', 'HermanWadea@gmail.com', '707-6094', 'P.O. Box 605, 1296 Eu, Ave', 85, 14, 2, '1994-06-12', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:45', '0000-00-00 00:00:00', 0, 'yes'),
(66, 'W0V6P8', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Ann', 'Gonzales', 'Ann  Gonzales', 'AnnGonzalesa@gmail.com', '743-4927', 'P.O. Box 111, 1940 Eu Avenue', 86, 14, 2, '1995-09-23', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(67, 'Z2C8G9', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Ethan', 'Puckett', 'Ethan  Puckett', 'EthanPucketta@gmail.com', '1-478-930-9842', 'Ap #472-6537 Non St.', 87, 14, 2, '1996-07-06', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(68, 'N3A1M2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Joel', 'Mccarty', 'Joel  Mccarty', 'JoelMccartya@gmail.com', '1-112-118-3028', '4587 Accumsan Road', 88, 15, 2, '1997-04-03', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(69, 'G2J8X4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Mohammad', 'Howe', 'Mohammad  Howe', 'MohammadHowea@gmail.com', '1-749-491-4414', '177-357 Ullamcorper, Rd.', 96, 31, 2, '1998-02-25', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(70, 'Y9V3O2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Darrel', 'Cooley', 'Darrel  Cooley', 'DarrelCooleya@yahoo.com', '1-420-901-8002', '9178 Lorem Av.', 97, 31, 2, '1988-06-14', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(71, 'Z8U5S6', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Arsenio', 'Rutledge', 'Arsenio  Rutledge', 'ArsenioRutledgea@yahoo.com', '629-4595', '931 Sit Rd.', 91, 17, 2, '1989-05-20', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(72, 'X8V6A1', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Maite', 'Benjamin', 'Maite  Benjamin', 'MaiteBenjamina@gmail.com', '1-526-817-3167', '179-6299 Sapien. St.', 98, 32, 2, '1990-04-23', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(73, 'I0P5T4', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Beau', 'Tucker', 'Beau  Tucker', 'BeauTuckera@gmail.com', '1-439-207-5772', 'Ap #956-8911 Lorem St.', 99, 32, 2, '1991-11-09', 'Male', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(74, 'R9C5T2', 'mOhDWZuemVG0h0Vd9GLvtmMC8L0ag1z4WVkkv19FUFsMsTan9Rzhmfpibu3mPKlAadWcR1qrmWY8y9mdV81ddg==', 'Ruth', 'Barrett', '', 'RuthBarretta@gmail.com', '1-186-697-5112', '356-5774 Nunc. Avenue', 100, 32, 2, '1992-01-28', 'Female', 'Android', NULL, 3, NULL, NULL, 1, NULL, NULL, '2015-09-22 12:22:46', '0000-00-00 00:00:00', 0, 'yes'),
(108, 'ism-test-update', '5A2DUoO70Cm3UkFbrzOfwJTOYFif37xLL33BkKYFmQ/eK6NS+JZUorq3nib+JU4xg/San70bubMpOFGM/naQ6w==', 'fname-update', 'lname-update', 'fullname-update', 'ism-update@test.com', '848484848484', 'surat adajan update', 9, 13, 2, '1991-10-14', 'male', NULL, NULL, 2, 'active', 'about me update', NULL, NULL, 0, '2015-09-24 02:27:08', '2015-09-24 02:38:58', 0, 'yes'),
(109, 'admin_new', '5A2DUoO70Cm3UkFbrzOfwJTOYFif37xLL33BkKYFmQ/eK6NS+JZUorq3nib+JU4xg/San70bubMpOFGM/naQ6w==', NULL, NULL, NULL, 'admin@mail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'active', NULL, NULL, NULL, NULL, '2015-09-22 04:54:11', '0000-00-00 00:00:00', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `user_account_preference`
--

CREATE TABLE IF NOT EXISTS `user_account_preference` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `preference_id` int(11) unsigned NOT NULL,
  `preference_value` tinytext NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `preference_id` (`preference_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_badge`
--

CREATE TABLE IF NOT EXISTS `user_badge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `badge_id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `badge_id` (`badge_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_chat`
--

CREATE TABLE IF NOT EXISTS `user_chat` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) unsigned NOT NULL,
  `receiver_id` int(11) unsigned NOT NULL,
  `message` tinytext NOT NULL,
  `media_link` tinytext NOT NULL,
  `media_type` tinytext NOT NULL,
  `received_status` tinytext NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_favorite_author`
--

CREATE TABLE IF NOT EXISTS `user_favorite_author` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `author_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_favorite_book`
--

CREATE TABLE IF NOT EXISTS `user_favorite_book` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `book_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_favorite_movie`
--

CREATE TABLE IF NOT EXISTS `user_favorite_movie` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `movie_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `movie_id` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_favorite_pastime`
--

CREATE TABLE IF NOT EXISTS `user_favorite_pastime` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `pastime_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `pastime_id` (`pastime_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_forgot_password`
--

CREATE TABLE IF NOT EXISTS `user_forgot_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `complete_date` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user_forgot_password`
--

INSERT INTO `user_forgot_password` (`id`, `user_id`, `token`, `created_date`, `complete_date`, `status`) VALUES
(1, 2, '92g4GXPJENaib643t98eV40TjrfHMOqS5IuU3d0chF72o131LlmZWp4KYRBk3AsCnD', '2015-09-24 11:40:25', '2015-09-24 17:10:25', 1),
(2, 2, 'T5c19F6W714BED8dg0YCUlk30tm3248iAsV4GheXq1Z9SorbL2IJPjRHMNpaKfOn6u', '2015-09-24 12:03:38', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_library`
--

CREATE TABLE IF NOT EXISTS `user_library` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `book_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_notification`
--

CREATE TABLE IF NOT EXISTS `user_notification` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `notification_to` int(11) unsigned NOT NULL,
  `notification_text` tinytext NOT NULL,
  `notification_from` int(11) unsigned NOT NULL,
  `notification_id` int(11) unsigned NOT NULL,
  `navigate_to` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `notification_to` (`notification_to`),
  KEY `notification_from` (`notification_from`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_permission`
--

CREATE TABLE IF NOT EXISTS `user_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `permission_access_id` int(11) unsigned NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `permission_access_id` (`permission_access_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_profile_picture`
--

CREATE TABLE IF NOT EXISTS `user_profile_picture` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `profile_link` tinytext,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `user_profile_picture`
--

INSERT INTO `user_profile_picture` (`id`, `user_id`, `profile_link`, `created_date`, `modified_date`, `is_delete`, `is_testdata`) VALUES
(4, 16, 'user_97/user5_1443001469.jpg', '2015-09-23 09:44:29', '2015-09-23 09:44:29', 0, 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `word_watch`
--

CREATE TABLE IF NOT EXISTS `word_watch` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `word` tinytext NOT NULL,
  `word_ranking` tinyint(4) NOT NULL DEFAULT '1',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_testdata` varchar(3) NOT NULL DEFAULT 'yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answer_choices`
--
ALTER TABLE `answer_choices`
  ADD CONSTRAINT `answer_choices_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `answer_choices_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `assignments`
--
ALTER TABLE `assignments`
  ADD CONSTRAINT `assignments_ibfk_1` FOREIGN KEY (`assignment_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `assignments_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `assignments_ibfk_4` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `assignments_ibfk_5` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `assignment_submission`
--
ALTER TABLE `assignment_submission`
  ADD CONSTRAINT `assignment_submission_ibfk_2` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `assignment_submission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `author_book`
--
ALTER TABLE `author_book`
  ADD CONSTRAINT `author_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `author_book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `author_profile`
--
ALTER TABLE `author_profile`
  ADD CONSTRAINT `author_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `auto_generated_credential`
--
ALTER TABLE `auto_generated_credential`
  ADD CONSTRAINT `auto_generated_credential_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `auto_generated_credential_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `auto_generated_credential_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `badges`
--
ALTER TABLE `badges`
  ADD CONSTRAINT `badges_ibfk_1` FOREIGN KEY (`badge_category_id`) REFERENCES `badge_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `banners`
--
ALTER TABLE `banners`
  ADD CONSTRAINT `banners_ibfk_2` FOREIGN KEY (`added_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `banners_ibfk_1` FOREIGN KEY (`banner_category_id`) REFERENCES `banner_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `book_assignment`
--
ALTER TABLE `book_assignment`
  ADD CONSTRAINT `book_assignment_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `book_assignment_ibfk_1` FOREIGN KEY (`added_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `cities`
--
ALTER TABLE `cities`
  ADD CONSTRAINT `cities_ibfk_1` FOREIGN KEY (`state_id`) REFERENCES `states` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `classrooms`
--
ALTER TABLE `classrooms`
  ADD CONSTRAINT `classrooms_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `classroom_assignment`
--
ALTER TABLE `classroom_assignment`
  ADD CONSTRAINT `classroom_assignment_ibfk_2` FOREIGN KEY (`school_classroom_id`) REFERENCES `school_classroom` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `classroom_assignment_ibfk_1` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `course_subject`
--
ALTER TABLE `course_subject`
  ADD CONSTRAINT `course_subject_ibfk_1` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `course_subject_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `districts`
--
ALTER TABLE `districts`
  ADD CONSTRAINT `districts_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `exams`
--
ALTER TABLE `exams`
  ADD CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `exams_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `exam_schedule`
--
ALTER TABLE `exam_schedule`
  ADD CONSTRAINT `exam_schedule_ibfk_2` FOREIGN KEY (`school_classroom_id`) REFERENCES `school_classroom` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `exam_schedule_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feeds`
--
ALTER TABLE `feeds`
  ADD CONSTRAINT `feeds_ibfk_1` FOREIGN KEY (`feed_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feeds_tagged_user`
--
ALTER TABLE `feeds_tagged_user`
  ADD CONSTRAINT `feeds_tagged_user_ibfk_2` FOREIGN KEY (`feed_id`) REFERENCES `feeds` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `feeds_tagged_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feed_comment`
--
ALTER TABLE `feed_comment`
  ADD CONSTRAINT `feed_comment_ibfk_2` FOREIGN KEY (`feed_id`) REFERENCES `feeds` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `feed_comment_ibfk_1` FOREIGN KEY (`comment_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feed_image`
--
ALTER TABLE `feed_image`
  ADD CONSTRAINT `feed_image_ibfk_1` FOREIGN KEY (`feed_id`) REFERENCES `feeds` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feed_like`
--
ALTER TABLE `feed_like`
  ADD CONSTRAINT `feed_like_ibfk_2` FOREIGN KEY (`feed_id`) REFERENCES `feeds` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `feed_like_ibfk_1` FOREIGN KEY (`like_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `followers`
--
ALTER TABLE `followers`
  ADD CONSTRAINT `followers_ibfk_2` FOREIGN KEY (`follow_to`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `followers_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `forum_question`
--
ALTER TABLE `forum_question`
  ADD CONSTRAINT `forum_question_ibfk_1` FOREIGN KEY (`posted_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `forum_question_comment`
--
ALTER TABLE `forum_question_comment`
  ADD CONSTRAINT `forum_question_comment_ibfk_2` FOREIGN KEY (`forum_question_id`) REFERENCES `forum_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `forum_question_comment_ibfk_1` FOREIGN KEY (`comment_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `invited_user`
--
ALTER TABLE `invited_user`
  ADD CONSTRAINT `invited_user_ibfk_1` FOREIGN KEY (`invited_by_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `lectures`
--
ALTER TABLE `lectures`
  ADD CONSTRAINT `lectures_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `lectures_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `lectures_ibfk_3` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `lectures_ibfk_5` FOREIGN KEY (`lecture_by`) REFERENCES `lectures` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `message_receiver`
--
ALTER TABLE `message_receiver`
  ADD CONSTRAINT `message_receiver_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `message_receiver_ibfk_1` FOREIGN KEY (`message_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `noticeboard`
--
ALTER TABLE `noticeboard`
  ADD CONSTRAINT `noticeboard_ibfk_1` FOREIGN KEY (`posted_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `noticeboard_viewer`
--
ALTER TABLE `noticeboard_viewer`
  ADD CONSTRAINT `noticeboard_viewer_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `permission`
--
ALTER TABLE `permission`
  ADD CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `permission_access`
--
ALTER TABLE `permission_access`
  ADD CONSTRAINT `permission_access_ibfk_1` FOREIGN KEY (`system_module_id`) REFERENCES `system_module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `promo_codes`
--
ALTER TABLE `promo_codes`
  ADD CONSTRAINT `promo_codes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_4` FOREIGN KEY (`question_creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `questions_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `questions_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `question_ratings`
--
ALTER TABLE `question_ratings`
  ADD CONSTRAINT `question_ratings_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `question_ratings_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `reported_content`
--
ALTER TABLE `reported_content`
  ADD CONSTRAINT `reported_content_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `schools`
--
ALTER TABLE `schools`
  ADD CONSTRAINT `schools_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `schools_ibfk_2` FOREIGN KEY (`state_id`) REFERENCES `states` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `schools_ibfk_3` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `school_classroom`
--
ALTER TABLE `school_classroom`
  ADD CONSTRAINT `school_classroom_ibfk_2` FOREIGN KEY (`school_id`) REFERENCES `schools` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `school_classroom_ibfk_1` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `school_course`
--
ALTER TABLE `school_course`
  ADD CONSTRAINT `school_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `school_course_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `states`
--
ALTER TABLE `states`
  ADD CONSTRAINT `states_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `student_academic_info`
--
ALTER TABLE `student_academic_info`
  ADD CONSTRAINT `student_academic_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_academic_info_ibfk_2` FOREIGN KEY (`school_id`) REFERENCES `schools` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_academic_info_ibfk_3` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_academic_info_ibfk_5` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `student_exam_response`
--
ALTER TABLE `student_exam_response`
  ADD CONSTRAINT `student_exam_response_ibfk_4` FOREIGN KEY (`choice_id`) REFERENCES `answer_choices` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_exam_response_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_exam_response_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_exam_response_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `student_exam_score`
--
ALTER TABLE `student_exam_score`
  ADD CONSTRAINT `student_exam_score_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_exam_score_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `student_profile`
--
ALTER TABLE `student_profile`
  ADD CONSTRAINT `student_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `student_subjective_evaluation`
--
ALTER TABLE `student_subjective_evaluation`
  ADD CONSTRAINT `student_subjective_evaluation_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_subjective_evaluation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `student_subjective_evaluation_ibfk_2` FOREIGN KEY (`evaluation_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `studymates`
--
ALTER TABLE `studymates`
  ADD CONSTRAINT `studymates_ibfk_2` FOREIGN KEY (`mate_of`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `studymates_ibfk_1` FOREIGN KEY (`mate_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `studymates_request`
--
ALTER TABLE `studymates_request`
  ADD CONSTRAINT `studymates_request_ibfk_2` FOREIGN KEY (`request_to_mate_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `studymates_request_ibfk_1` FOREIGN KEY (`request_from_mate_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `surveys`
--
ALTER TABLE `surveys`
  ADD CONSTRAINT `surveys_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `survey_answer_choice`
--
ALTER TABLE `survey_answer_choice`
  ADD CONSTRAINT `survey_answer_choice_ibfk_1` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `survey_audience`
--
ALTER TABLE `survey_audience`
  ADD CONSTRAINT `survey_audience_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `survey_audience_ibfk_1` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `survey_question`
--
ALTER TABLE `survey_question`
  ADD CONSTRAINT `survey_question_ibfk_1` FOREIGN KEY (`question_creator_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `survey_response`
--
ALTER TABLE `survey_response`
  ADD CONSTRAINT `survey_response_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `survey_response_ibfk_1` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tags_book`
--
ALTER TABLE `tags_book`
  ADD CONSTRAINT `tags_book_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tags_book_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tags_forum_question`
--
ALTER TABLE `tags_forum_question`
  ADD CONSTRAINT `tags_forum_question_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tags_forum_question_ibfk_1` FOREIGN KEY (`forum_question_id`) REFERENCES `forum_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tags_lecture`
--
ALTER TABLE `tags_lecture`
  ADD CONSTRAINT `tags_lecture_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tags_lecture_ibfk_1` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tags_question`
--
ALTER TABLE `tags_question`
  ADD CONSTRAINT `tags_question_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tags_question_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tag_book_assignment`
--
ALTER TABLE `tag_book_assignment`
  ADD CONSTRAINT `tag_book_assignment_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tag_book_assignment_ibfk_1` FOREIGN KEY (`book_assignment_id`) REFERENCES `book_assignment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `teacher_profile`
--
ALTER TABLE `teacher_profile`
  ADD CONSTRAINT `teacher_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `teacher_subject_info`
--
ALTER TABLE `teacher_subject_info`
  ADD CONSTRAINT `teacher_subject_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `teacher_subject_info_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `teacher_subject_info_ibfk_3` FOREIGN KEY (`school_id`) REFERENCES `schools` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `teacher_subject_info_ibfk_4` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `topics`
--
ALTER TABLE `topics`
  ADD CONSTRAINT `topics_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `topics_ibfk_2` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `topics_ibfk_4` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tutorial_group_discussion`
--
ALTER TABLE `tutorial_group_discussion`
  ADD CONSTRAINT `tutorial_group_discussion_ibfk_3` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tutorial_group_discussion_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tutorial_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tutorial_group_discussion_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tutorial_group_member`
--
ALTER TABLE `tutorial_group_member`
  ADD CONSTRAINT `tutorial_group_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tutorial_group_member_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tutorial_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tutorial_group_member_score`
--
ALTER TABLE `tutorial_group_member_score`
  ADD CONSTRAINT `tutorial_group_member_score_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tutorial_group_member_score_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tutorial_group_question`
--
ALTER TABLE `tutorial_group_question`
  ADD CONSTRAINT `tutorial_group_question_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tutorial_group_question_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `tutorial_group_topic_allocation`
--
ALTER TABLE `tutorial_group_topic_allocation`
  ADD CONSTRAINT `tutorial_group_topic_allocation_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `tutorial_group_topic_allocation_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `tutorial_groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `users_ibfk_2` FOREIGN KEY (`state_id`) REFERENCES `states` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `users_ibfk_3` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `users_ibfk_4` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_account_preference`
--
ALTER TABLE `user_account_preference`
  ADD CONSTRAINT `user_account_preference_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_account_preference_ibfk_1` FOREIGN KEY (`preference_id`) REFERENCES `preferences` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_badge`
--
ALTER TABLE `user_badge`
  ADD CONSTRAINT `user_badge_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_badge_ibfk_1` FOREIGN KEY (`badge_id`) REFERENCES `badges` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_chat`
--
ALTER TABLE `user_chat`
  ADD CONSTRAINT `user_chat_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_chat_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_favorite_author`
--
ALTER TABLE `user_favorite_author`
  ADD CONSTRAINT `user_favorite_author_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_favorite_author_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_favorite_book`
--
ALTER TABLE `user_favorite_book`
  ADD CONSTRAINT `user_favorite_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_favorite_book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_favorite_movie`
--
ALTER TABLE `user_favorite_movie`
  ADD CONSTRAINT `user_favorite_movie_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_favorite_movie_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_favorite_pastime`
--
ALTER TABLE `user_favorite_pastime`
  ADD CONSTRAINT `user_favorite_pastime_ibfk_2` FOREIGN KEY (`pastime_id`) REFERENCES `pastimes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_favorite_pastime_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_library`
--
ALTER TABLE `user_library`
  ADD CONSTRAINT `user_library_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_library_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_notification`
--
ALTER TABLE `user_notification`
  ADD CONSTRAINT `user_notification_ibfk_2` FOREIGN KEY (`notification_from`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_notification_ibfk_1` FOREIGN KEY (`notification_to`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_permission`
--
ALTER TABLE `user_permission`
  ADD CONSTRAINT `user_permission_ibfk_2` FOREIGN KEY (`permission_access_id`) REFERENCES `permission_access` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_permission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_profile_picture`
--
ALTER TABLE `user_profile_picture`
  ADD CONSTRAINT `user_profile_picture_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
