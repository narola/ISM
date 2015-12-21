package com.test;

import org.json.*;
import java.util.ArrayList;

public class test {
	
    private String message;
    private ArrayList<String> data;
    private String status;
    
    
	public test () {
		
	}	
        
    public test (JSONObject json) {
    
        this.message = json.optString("message");

        this.data = new ArrayList<String>();
        JSONArray arrayData = json.optJSONArray("data");
        if (null != arrayData) {
            int dataLength = arrayData.length();
            for (int i = 0; i < dataLength; i++) {
                String item = arrayData.optString(i);
                if (null != item) {
                    this.data.add(item);
                }
            }
        }
        else {
            String item = json.optString("data");
            if (null != item) {
                this.data.add(item);
            }
        }

        this.status = json.optString("status");

    }
    
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getData() {
        return this.data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    
}
