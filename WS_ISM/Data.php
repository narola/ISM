<?php

/**
 * User: c161
 * Date: 24/10/15
 */
class TutorialGroup
{

	function __construct()
	{

	}

	public function call_service($service, $postData)
	{
		switch ($service) {
			case "GetCountries": {
				return $this->getCountries();
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

	public function getCountries()
	{
		$get_countries_query = "SELECT id, country_name from " . TABLE_COUNTRIES;

		$res = mysql_query($get_countries_query) or $message = mysql_error();

		if ($res) {

			$countryArray = array();
			if ((mysql_num_rows($res)) > 0) {

				while ($country = mysql_fetch_assoc($res)) {
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

		$data['status'] = ($status > 1) ? 'failed' : 'success';
		$data['message'] = $message;
		$data['countries'] = $countryArray;

		return $data;
	}

	public function getStates($postData)
	{
		$countryId = validateObject($postData, 'country_id', "");
		$countryId = addslashes($countryId);

		$get_states_query = "SELECT id, state_name from " . TABLE_STATES . " where country_id = " . $countryId;

		$res = mysql_query($get_states_query) or $message = mysql_error();

		if ($res) {

			$stateArray = array();
			if ((mysql_num_rows($res)) > 0) {

				while ($state = mysql_fetch_assoc($res)) {
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

		$data['status'] = ($status > 1) ? 'failed' : 'success';
		$data['message'] = $message;
		$data['states'] = $stateArray;

		return $data;
	}

	public function getCities($postData)
	{
		$stateId = validateObject($postData, 'state_id', "");
		$stateId = addslashes($stateId);

		$get_cities_query = "SELECT id, city_name from " . TABLE_CITIES . " where state_id = " . $stateId;

		$res = mysql_query($get_cities_query) or $message = mysql_error();

		if ($res) {

			$cityArray = array();
			if ((mysql_num_rows($res)) > 0) {

				while ($city = mysql_fetch_assoc($res)) {
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

		$data['status'] = ($status > 1) ? 'failed' : 'success';
		$data['message'] = $message;
		$data['cities'] = $cityArray;

		return $data;
	}

}

?>