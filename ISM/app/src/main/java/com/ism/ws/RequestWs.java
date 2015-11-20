package com.ism.ws;

import com.ism.ws.helper.WSRequestPost;

public class RequestWs {

	/**
	 * Post Request
	 **/
	public <CLS> CLS getRequest(String url, Class<CLS> cls, Object reqCls) throws Exception {
		return new WSRequestPost(url).execute(cls, reqCls);
	}

}
