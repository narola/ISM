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

}

?>