package com.ism.fragment.userprofile;

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
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.PostFeedsAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Feeds;

import java.util.ArrayList;

/**
 * Created by c161 on 06/11/15.
 */
public class MyFeedsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, HostActivity.ProfileControllerPresenceListener {

	private static final String TAG = MyFeedsFragment.class.getSimpleName();

	private View view, viewHighlighterTriangle;
	private RecyclerView recyclerPostFeeds;
	private RelativeLayout rlNewPost;
	private TextView txtEmptyListMessage;

	private PostFeedsAdapter adpPostFeeds;
	private HostActivity activityHost;
	private FragmentListener fragListener;

	public static MyFeedsFragment newInstance() {
		MyFeedsFragment fragment = new MyFeedsFragment();
		return fragment;
	}

	public MyFeedsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_feeds, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		viewHighlighterTriangle = view.findViewById(R.id.view_highlighter_triangle);
		recyclerPostFeeds = (RecyclerView) view.findViewById(R.id.recycler_post);
		rlNewPost = (RelativeLayout) view.findViewById(R.id.rl_new_post);
		txtEmptyListMessage = (TextView) view.findViewById(R.id.txt_emptylist_message);

		txtEmptyListMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
		viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == HostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

		recyclerPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));
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
		recyclerPostFeeds.addItemDecoration(itemDecoration);

		if (Utility.isConnected(getActivity())) {
			callApiGetMyFeeds();
		} else {
			Utility.alertOffline(getActivity());
		}

		rlNewPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//              Start post activity
			}
		});
	}

	private void callApiGetMyFeeds() {
		try {
			activityHost.showProgress();
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_MY_FEEDS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetMyFeeds Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_MY_FEEDS:
					onResponseGetMyFeeds(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetMyFeeds(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					ArrayList<Feeds> feeds = responseHandler.getFeeds();
					adpPostFeeds = new PostFeedsAdapter(getActivity(), feeds);
					txtEmptyListMessage.setVisibility(feeds != null && feeds.size() > 0 ? View.GONE : View.VISIBLE);
					recyclerPostFeeds.setAdapter(adpPostFeeds);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetMyFeeds Failed : " + responseHandler.getMessage());
				}
			} else if(error != null) {
				Log.e(TAG, "onResponseGetMyFeeds apiCall Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetMyFeeds Exception : " + e.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityHost = (HostActivity) activity;
			fragListener = (FragmentListener) activity;
			activityHost.setListenerProfileControllerPresence(this);
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_MY_FEEDS);
			}
		} catch (ClassCastException e) {
			Debug.e(TAG, "onAttach Exception : " + e.toString());
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			if (fragListener != null) {
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_MY_FEEDS);
			}
		} catch (ClassCastException e) {
			Debug.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	@Override
	public void onProfileControllerAttached() {
		viewHighlighterTriangle.setVisibility(View.VISIBLE);
	}

	@Override
	public void onProfileControllerDetached() {
		viewHighlighterTriangle.setVisibility(View.GONE);
	}

}
