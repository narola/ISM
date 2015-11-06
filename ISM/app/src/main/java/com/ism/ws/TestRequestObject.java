package com.ism.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by c161 on 05/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRequestObject {

	private int userId;
	private int cityId;

	public int getUserId() {
		return this.userId;
	}

	@JsonProperty("user_id")
	public void setUserId(int userId) {
		this.userId = userId;
	}

}
