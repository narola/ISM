package com.ism.model;

import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 10/11/15.
 */
public class FragmentArgument {

	private static final String TAG = FragmentArgument.class.getSimpleName();

	private ArrayList<Data> arrayListData;
	private int intPosition;

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
}
