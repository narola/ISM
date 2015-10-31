package com.ism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.adapter.PostFeedsAdapter;
import com.ism.utility.Utility;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;

public class ClassWallFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = ClassWallFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerPost;

	private PostFeedsAdapter adpPostFeeds;
	private RecyclerView.LayoutManager layoutManager;

	public static ClassWallFragment newInstance() {
		ClassWallFragment fragment = new ClassWallFragment();
		return fragment;
	}

	public ClassWallFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_class_wall, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		recyclerPost = (RecyclerView) view.findViewById(R.id.recycler_post);

		layoutManager = new LinearLayoutManager(getActivity());
		recyclerPost.setLayoutManager(layoutManager);

		if (Utility.isOnline(getActivity())) {
			Log.e(TAG, "feed api called");
			callApiGetAllFeeds();
		} else {
			Utility.toastOffline(getActivity());
		}

	}

	private void callApiGetAllFeeds() {
		try {
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId("141");

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_ALL_FEEDS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetAllFeeds Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.GET_ALL_FEEDS:
						onResponseGetAllFeeds(object);
						break;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetAllFeeds(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				Log.e(TAG, "onResponseGetAllFeeds fetched");
				adpPostFeeds = new PostFeedsAdapter(getActivity(), responseObj.getData());
				Log.e(TAG, "onResponseGetAllFeeds adapter initialized");
				recyclerPost.setAdapter(adpPostFeeds);
				Log.e(TAG, "onResponseGetAllFeeds adapter set");
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Log.e(TAG, "onResponseGetAllFeeds Failed : " + responseObj.getMessage());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
		}
	}
}
