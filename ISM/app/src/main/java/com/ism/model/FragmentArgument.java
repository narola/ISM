package com.ism.model;

import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 10/11/15.
 */
public class FragmentArgument {

	private static final String TAG = FragmentArgument.class.getSimpleName();

	private Object objectData;
	private int intPosition;

	public FragmentArgument() {
	}

	public FragmentArgument(Object arrayListData) {
		this.objectData = arrayListData;
	}

	public Object getObjectData() {
		return objectData;
	}

	public void setObjectData(Object objectData) {
		this.objectData = objectData;
	}

	public int getPosition() {
		return intPosition;
	}

	public void setPosition(int intPosition) {
		this.intPosition = intPosition;
	}
}
