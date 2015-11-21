package com.ism.ws;

import com.ism.ws.helper.WebserviceConnector;

public class RequestWs {

	/**
	 * Post Request
	 **/
	public <CLS> CLS getRequest(String url, Class<CLS> cls, Object reqCls) throws Exception {
		return new WebserviceConnector(url).execute(cls, reqCls);
	}

}
