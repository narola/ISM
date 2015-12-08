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
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.UserActivityAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.UserActivitiy;

import java.util.ArrayList;

/**
 * Created by c161 on 06/11/15.
 */
public class MyActivityFragment extends Fragment implements HostActivity.ProfileControllerPresenceListener, WebserviceWrapper.WebserviceResponse {

	private static final String TAG = MyActivityFragment.class.getSimpleName();

	private View view, viewHighlighterTriangle;
	private RecyclerView recyclerMyActivity;
	private TextView txtEmptyListMessage;

	private HostActivity activityHost;
	private FragmentListener fragListener;
	private UserActivityAdapter adpUserActivity;
	private ArrayList<UserActivitiy> arrListUserActivity;

	public static MyActivityFragment newInstance() {
		MyActivityFragment fragment = new MyActivityFragment();
		return fragment;
	}

	public MyActivityFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_activity, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		viewHighlighterTriangle = view.findViewById(R.id.view_highlighter_triangle);
		recyclerMyActivity = (RecyclerView) view.findViewById(R.id.recycler_my_activity);
		txtEmptyListMessage = (TextView) view.findViewById(R.id.txt_emptylist_message);

		txtEmptyListMessage.setTypeface(Global.myTypeFace.getRalewayRegular());
		viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == HostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

		if (Utility.isConnected(getActivity())) {
			callApiGetMyActivity();
		} else {
			Utility.alertOffline(getActivity());
		}

		/*final ArrayList<TestActivity> arrayListActivities = new ArrayList<>();
		String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

		Random random = new Random();
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 2; j++) {
				TestActivity testActivity = new TestActivity();
				testActivity.setTime(months[i] + " 2015");
				testActivity.setActivityTitle("Status Updated");
				testActivity.setActivityType(random.nextInt(7));
				arrayListActivities.add(testActivity);
			}
		}*/

		recyclerMyActivity.setLayoutManager(new LinearLayoutManager(activityHost));
		RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				int spacing = 5;
				outRect.left = spacing;
				outRect.right = spacing;
				if (parent.getChildLayoutPosition(view) == 0) {
					outRect.top = spacing;
				}
				if (parent.getChildLayoutPosition(view) == arrListUserActivity.size() - 1) {
					outRect.bottom = spacing;
				}
			}
		};
		recyclerMyActivity.addItemDecoration(itemDecoration);

		/*adpUserActivity = new MyActivityAdapter(activityHost, arrayListActivities);
		txtEmptyListMessage.setVisibility(arrayListActivities != null && arrayListActivities.size() > 0 ? View.GONE : View.VISIBLE);
		recyclerMyActivity.setAdapter(adpUserActivity);*/

	}

	private void callApiGetMyActivity() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_MY_ACTIVITY);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetMyActivity Exception : " + e.toString());
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
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_MY_ACTIVITY);
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
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_MY_ACTIVITY);
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

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_MY_ACTIVITY:
					onResponseGetMyActivity(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetMyActivity(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListUserActivity = responseHandler.getUserActivities();
					txtEmptyListMessage.setVisibility(arrListUserActivity != null && arrListUserActivity.size() > 0 ? View.GONE : View.VISIBLE);
					adpUserActivity = new UserActivityAdapter(getActivity(), arrListUserActivity);
					recyclerMyActivity.setAdapter(adpUserActivity);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetMyActivity Failed Message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetMyActivity api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetMyActivity Exception : " + e.toString());
		}
	}
}