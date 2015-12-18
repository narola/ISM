<?php

/**
 * User: c161
 * Date: 24/10/15
 */
error_reporting(0);
class TutorialGroup
{

	function __construct()
	{

	}

	public function call_service($service, $postData)
	{
		switch ($service) {
			case "GetCountries": {
				return $this->getCountries($postData);
			}
				break;

			case "GetStates": {
				return $this->getStates($postData);
			}
				break;

			case "GetCities": {
				return $this->getCities($postData);
			}
				break;

            case "GetAllBadges":
            {
                return $this->getAllBadges($postData);
            }
                break;

            case "GetAdBanners":
            {
                return $this->getAdBanners($postData);
            }
                break;

            case "GetAllCourses":
            {
                return $this->getAllCourses($postData); //done
            }
                break;

            case "GetAllSubjects":
            {
                return $this->getAllSubjects($postData); //done
            }
                break;

            case "GetAllTopics":
            {
                return $this->getAllTopics($postData); //done
            }
                break;

            case "GetAllClassrooms":
            {
                return $this->getAllClassrooms($postData); //done
            }
                break;

        }
	}

	public function getCountries($postData)
	{
        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $get_countries_query = "SELECT id, country_name from " . TABLE_COUNTRIES ." WHERE is_delete=0";

            $res = mysqli_query($GLOBALS['con'],$get_countries_query) or $message = mysqli_error($GLOBALS['con']);

            if ($res) {

                $countryArray = array();
                if ((($res)) > 0) {

                    while ($country = mysqli_fetch_assoc($res)) {
                        $countryObj = '';
                        $countryObj['id'] = $country['id'];
                        $countryObj['country_name'] = $country['country_name'];
                        $countryArray[] = $countryObj;
                    }

                    $status = 1;
                    $message = "Countries found.";

                } else {
                    $status = 1;
                    $message = "Countries not found.";
                }
            } else {
                $status = 2;
            }

           $status = ($status > 1) ? 'failed' : 'success';
        }
      else
          {
              $status="failed";
              $message = MALICIOUS_SOURCE;
          }

        $data['status']=$status;
		$data['message'] = $message;
		$data['countries'] = $countryArray;

		return $data;
	}

	public function getStates($postData)
	{
		$countryId = validateObject($postData, 'country_id', "");
		$countryId = addslashes($countryId);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);


        if($isSecure==yes) {
		$get_states_query = "SELECT id, state_name from " . TABLE_STATES . " where country_id = " . $countryId." and is_delete=0";

		$res = mysqli_query($GLOBALS['con'],$get_states_query) or $message = mysqli_error($GLOBALS['con']);

		if ($res) {

			$stateArray = array();
			if ((mysqli_num_rows($res)) > 0) {

				while ($state = mysqli_fetch_assoc($res)) {
					$stateObj = '';
					$stateObj['id'] = $state['id'];
					$stateObj['state_name'] = $state['state_name'];
					$stateArray[] = $stateObj;
				}

				$status = 1;
				$message = "States found for country.";

			} else {
				$status = 1;
				$message = "States not found.";
			}
		} else {
			$status = 2;
		}

            $status = ($status > 1) ? 'failed' : 'success';
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }

        $data['status']=$status;
		$data['message'] = $message;
		$data['states'] = $stateArray;

		return $data;
	}

	public function getCities($postData)
	{
		$stateId = validateObject($postData, 'state_id', "");
		$stateId = addslashes($stateId);

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {
		$get_cities_query = "SELECT id, city_name from " . TABLE_CITIES . " where state_id = " . $stateId ." and is_delete=0";

		$res = mysqli_query($GLOBALS['con'],$get_cities_query) or $message = mysqli_error($GLOBALS['con']);

		if ($res) {

			$cityArray = array();
			if ((mysqli_num_rows($res)) > 0) {

				while ($city = mysqli_fetch_assoc($res)) {
					$cityObj = '';
					$cityObj['id'] = $city['id'];
					$cityObj['city_name'] = $city['city_name'];
					$cityArray[] = $cityObj;
				}

				$status = 1;
				$message = "Cities found for state.";

			} else {
				$status = 1;
				$message = "Cities not found.";
			}
		} else {
			$status = 2;
		}

            $status = ($status > 1) ? 'failed' : 'success';
        }
        else
        {
            $status="failed";
            $message = MALICIOUS_SOURCE;
        }

        $data['status']=$status;
		$data['message'] = $message;
		$data['cities'] = $cityArray;

		return $data;
	}


    /*
     * getAllBadges
    */
    public function getAllBadges ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT badges.`id` as 'badge_id', badges.`badge_name`, badges.`badge_description`,badges.`badge_award`,badges.`badge_category_id`,badge_category.badge_category_name FROM ".TABLE_BADGES ." badges INNER JOIN ".TABLE_BADGE_CATEGORY." badge_category ON badges.badge_category_id= badge_category.id WHERE badges.is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {

                    $data[] = $row;

                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['badges']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
    * getAdBanners
   */
    public function getAdBanners ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `banner_title` FROM ".TABLE_BANNERSN ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $data[] = $row;
                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['ad_banners']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
   * getCourses
   */
    public function getAllCourses ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();

        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `course_name` FROM ".TABLE_COURSES ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $data[] = $row;
                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['courses']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getCourses
    */
    public function getAllSubjects ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `subject_name`, `subject_image` FROM ".TABLE_SUBJECTS ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $data[] = $row;

                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['subjects']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getTopic
    */
    public function getAllTopics ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `topic_name`, `topic_description` FROM ".TABLE_TOPICS ." where is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $data[] = $row;

                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['topics']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

    /*
     * getClassrooms
    */
    public function getAllClassrooms ($postData)
    {
        $message='';
        $status='';
        $data=array();
        $post=array();
        $response=array();


        $secret_key = validateObject($postData, 'secret_key', "");
        $secret_key = addslashes($secret_key);

        $access_key = validateObject($postData, 'access_key', "");
        $access_key = addslashes($access_key);

        $security=new SecurityFunctions();
        $isSecure = $security->checkForSecurity($access_key,$secret_key);

        if($isSecure==yes) {

            $query = "SELECT `id`, `course_id`, `class_name`, `class_nickname` FROM ".TABLE_CLASSROOMS ." WHERE is_delete=0";
            $result = mysqli_query($GLOBALS['con'], $query) or $message = mysqli_error($GLOBALS['con']);
            // echo $query;
            if (mysqli_num_rows($result)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $data[] = $row;
                }
                $status = SUCCESS;

            } else {
                $status = SUCCESS;
                $message = DEFAULT_NO_RECORDS;
            }
        }
        else
        {
            $status=FAILED;
            $message = MALICIOUS_SOURCE;
        }
        $response['classrooms']=$data;
        $response['message']=$message;
        $response['status']=$status;

        return $response;
    }

}

?>