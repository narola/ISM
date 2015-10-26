package com.ism.teacher.ws;

//import org.apache.http.NameValuePair;


public class RequestWs {

//	/** Get Request **/
//	public <CLS> CLS getGetRequest(String url, Class<CLS> cls) throws Exception {
//		return new WebServiceRequest(url).execute(cls);
//	}

	/** Post Request **/
	public <CLS> CLS getRequest(String url, Class<CLS> cls, Object reqCls) throws Exception {
		/*if (nameValuePair != null) {
			return new WebServiceRequest(url).execute(nameValuePair, cls,
					null);
		} else {*/
			return new WSRequestPost(url).execute(cls, reqCls);

//		}
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
