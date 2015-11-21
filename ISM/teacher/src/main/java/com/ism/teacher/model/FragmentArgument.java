package com.ism.teacher.model;

import java.util.ArrayList;

/**
 * Created by c161 on 10/11/15.
 */
public class FragmentArgument {

    private static final String TAG = FragmentArgument.class.getSimpleName();

    private ArrayList<Data> arrayListData;
    private int intPosition;
    private RequestObject requestObject = new RequestObject();

    public FragmentArgument() {
    }

    public FragmentArgument(ArrayList<Data> arrayListData) {
        this.arrayListData = arrayListData;
    }

    public ArrayList<Data> getArrayListData() {
        return arrayListData;
    }

    public void setArrayListData(ArrayList<Data> arrayListData) {
        this.arrayListData = arrayListData;
    }

    public int getPosition() {
        return intPosition;
    }

    public void setPosition(int intPosition) {
        this.intPosition = intPosition;
    }

    public RequestObject getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(RequestObject requestObject) {
        this.requestObject = requestObject;
    }

}
