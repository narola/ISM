package com.ism.author.ws;

import com.ism.author.Utility.Debug;
import com.ism.author.ws.helper.WebserviceConnector;

/**
 * Created by c166 on 23/10/15.
 */
public class RequestWs {

    private static final String TAG = RequestWs.class.getSimpleName();
    /** Get Request **/
//	public <CLS> CLS getGetRequest(String url, Class<CLS> cls) throws Exception {
//		return new WebServiceRequest(url).execute(cls);
//	}

    /**
     * Post Request method
     **/
    public <CLS> CLS getRequest(String url, Class<CLS> cls, Object reqCls) throws Exception {
        /*if (nameValuePair != null) {
            return new WebServiceRequest(url).execute(nameValuePair, cls,
					null);
		} else {*/
        Debug.i(TAG, "URL : " + url);
        Debug.i(TAG, "reqCls : " + reqCls);

        return new WebserviceConnector(url).execute(cls, reqCls);

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
