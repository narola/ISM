package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.PostFeedsAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.model.RequestObject;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.WebserviceWrapper;

public class ClassWallFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = ClassWallFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerPost;
	private RelativeLayout llPost;

	private PostFeedsAdapter adpPostFeeds;
	private HostActivity activityHost;

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
		llPost = (RelativeLayout) view.findViewById(R.id.ll_post);

		recyclerPost.setLayoutManager(new LinearLayoutManager(getActivity()));

		RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 10;
				outRect.right = 10;
				outRect.left = 10;
				if (parent.getChildLayoutPosition(view) == 0) {
					outRect.top = 10;
				}
			}
		};

		recyclerPost.addItemDecoration(itemDecoration);

		if (Utility.isOnline(getActivity())) {
			callApiGetAllFeeds();
		} else {
			Utility.toastOffline(getActivity());
		}

		llPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//              Start post activity
			}
		});

	}

	private void callApiGetAllFeeds() {
		try {
			activityHost.showProgress();
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(Global.strUserId);
			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_ALL_FEEDS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetAllFeeds Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
				switch (apiCode) {
					case WebConstants.GET_ALL_FEEDS:
						onResponseGetAllFeeds(object, error);
						break;
				}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetAllFeeds(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					adpPostFeeds = new PostFeedsAdapter(getActivity(), responseObj.getData());
					recyclerPost.setAdapter(adpPostFeeds);
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onResponseGetAllFeeds Failed : " + responseObj.getMessage());
				}
			} else if(error != null) {
				Log.e(TAG, "onResponseGetAllFeeds apiCall Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		activityHost = (HostActivity) activity;
	}
}
