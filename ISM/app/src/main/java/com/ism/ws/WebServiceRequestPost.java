package com.ism.ws;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WebServiceRequestPost {

	private static final String LOG_TAG = "WebServiceRequestPost";

	private static final Lock lock = new ReentrantLock();

	private HttpPost httpPost;

	private HttpClient client;

	private String url;

	private HttpResponse response;

	private static ObjectMapper mapper = null;

	String data;

	public WebServiceRequestPost(String _url) {
		client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 4000);
		HttpConnectionParams.setSoTimeout(params, 120000);
		this.url = _url;
	}

	public <Request, Response> Response execute(
			List<NameValuePair> nameValuePairs, Class<Response> responseType,
			Request request) throws Exception {

		Response ret = null;

		int statusCode = 0;
		try {
			if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
				Log.i(LOG_TAG, "Executing url: " + url);
			}
			Log.v("main url", url + "");

			httpPost = new HttpPost(url);

			ObjectWriter writer = getMapper().writer();

			if (request != null) {
				String jsonObject = "";
				if (request != null) {
					// writer.writeValueAsString( request );
					jsonObject = writer.writeValueAsString(request);
					Log.i("main", jsonObject + "");
				}

				StringEntity entity = new StringEntity(jsonObject);

				httpPost.setEntity(entity);
			} else {
				if (nameValuePairs != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					Log.i("main", nameValuePairs + "");
				}
			}

			// now send the request
			response = client.execute(httpPost);

			if (httpPost.isAborted()) {
				throw new Exception(String.format("Operation [%s] is aborted.",
						url));
			}
			statusCode = response.getStatusLine().getStatusCode();

			// read the response
			if (statusCode == 200) {

				HttpEntity httpEntity = response.getEntity();
				data = EntityUtils.toString(httpEntity);
				Log.i("data", data);

				ret = getMapper().readValue(data, responseType);

			} else {
				ret = null;

			}
			httpPost = null;
		} catch (ConnectTimeoutException ex) {
			if (ex != null)
				Log.e(LOG_TAG, "Status code: " + Integer.toString(statusCode)
						+ " Exception thrown: " + ex.getMessage());
			ret = null;
			throw ex;
		} catch (SocketTimeoutException ex) {
			if (ex != null)
				Log.e(LOG_TAG, "Status code: " + Integer.toString(statusCode)
						+ " Exception thrown: " + ex.getMessage());
			ret = null;
			throw ex;
		} catch (Exception ex) {
			if (ex != null)
				Log.e(LOG_TAG, "Status code: " + Integer.toString(statusCode)
						+ " Exception thrown: " + ex.getMessage());
			ret = null;
			throw ex;
		}

		return ret;
	}

	public void abort() throws Exception {

		if (httpPost != null) {
			httpPost.abort();
			httpPost = null;
		}
	}

	protected synchronized ObjectMapper getMapper() {
		if (mapper != null) {
			return mapper;
		}

		try {
			lock.lock();
			if (mapper == null) {

				mapper = new ObjectMapper();
				mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,
						false);
			}
			lock.unlock();
		} catch (Exception ex) {
			if (ex != null)
				Log.e(LOG_TAG, "Mapper Initialization Failed. Exception :: "
						+ ex.getMessage());
		}

		return mapper;
	}
}