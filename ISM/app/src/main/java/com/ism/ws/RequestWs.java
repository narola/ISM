package com.ism.ws;

import java.util.List;

import org.apache.http.NameValuePair;

public class RequestWs {

	/** Get Request **/
	public <CLS> CLS getGetRequest(String url, Class<CLS> cls) throws Exception {
		return new WebServiceRequestGet(url).execute(cls);
	}

	/** Post Request **/
	public <CLS> CLS getPostRequest(String url, Class<CLS> cls, Object reqCls,
			List<NameValuePair> nameValuePair) throws Exception {
		if (nameValuePair != null) {
			return new WebServiceRequestPost(url).execute(nameValuePair, cls,
					null);
		} else {
			return new WebServiceRequestPost(url).execute(null, cls, reqCls);

		}
	}

	/** Post Request upload file **/
	// public <CLS> CLS getPostRequest( String url, Class<CLS> cls,
	// List<NameValuePair> nameValuePair )
	// throws Exception
	// {
	// return new WebServiceRequestUploadFile( url ).execute( nameValuePair, cls
	// );
	// }

}
