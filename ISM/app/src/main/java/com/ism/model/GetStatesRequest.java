package com.ism.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetStatesRequest {

    private int countryId;

	@JsonProperty("country_id")
    public int getCountryId() {
        return this.countryId;
    }


	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

}
