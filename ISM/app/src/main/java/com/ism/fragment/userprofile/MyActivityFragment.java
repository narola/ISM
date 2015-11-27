package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.MyActivityAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.model.TestActivity;
import com.ism.utility.Debug;

import java.util.ArrayList;

/**
 * Created by c161 on 06/11/15.
 */
public class MyActivityFragment extends Fragment {

	private static final String TAG = MyActivityFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerMyActivity;

	private HostActivity activityHost;
	private FragmentListener fragListener;
	private MyActivityAdapter adpMyActivity;

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
		recyclerMyActivity = (RecyclerView) view.findViewById(R.id.recycler_my_activity);

		final ArrayList<TestActivity> arrayListActivities = new ArrayList<>();
		String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 2; j++) {
				TestActivity testActivity = new TestActivity();
				testActivity.setTime(months[i] + " 2015");
				testActivity.setActivityTitle("Status Updated");
				arrayListActivities.add(testActivity);
			}
		}

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
				if (parent.getChildLayoutPosition(view) == arrayListActivities.size() - 1) {
					outRect.bottom = spacing;
				}
			}
		};
		recyclerMyActivity.addItemDecoration(itemDecoration);

		adpMyActivity = new MyActivityAdapter(activityHost, arrayListActivities);
		recyclerMyActivity.setAdapter(adpMyActivity);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityHost = (HostActivity) activity;
			fragListener = (FragmentListener) activity;
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

}