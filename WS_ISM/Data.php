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

			case "GetCities": {
				return $this->getCities($_POST['country_id']);
			}
				break;
		}
	}

	public function getCountries()
	{

	}

	public function getCities($user_id)
	{

	}

}

?>