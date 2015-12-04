package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.StudymatesPagerAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/**
 * Created by c161 on 06/11/15.
 */
public class StudymatesFragment extends Fragment implements HostActivity.HostListenerStudymates, HostActivity.ProfileControllerPresenceListener {

	private static final String TAG = StudymatesFragment.class.getSimpleName();

	private View view, viewHighlighterTriangle;
	private TextView txtStudymates, txtFindStudymates;
	private FrameLayout flFragmentContainer;
	private ViewPager vpStudymates;

	private HostActivity activityHost;
	private FragmentListener fragListener;
	private StudymatesPagerAdapter adpStudymatesPager;

	public static final String CURRENT_FRAGMENT = "currentFragment";
	public static final int FRAGMENT_SUGGESTED_STUDYMATES = 0;
	public static final int FRAGMENT_FIND_MORE_STUDYMATES = 1;
	private int currentFragment = FRAGMENT_SUGGESTED_STUDYMATES;

	public static StudymatesFragment newInstance(Bundle bundleArguments) {
		StudymatesFragment fragment = new StudymatesFragment();
		fragment.setArguments(bundleArguments);
		return fragment;
	}

	public StudymatesFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			currentFragment = getArguments().getInt(CURRENT_FRAGMENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_studymates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		viewHighlighterTriangle = view.findViewById(R.id.view_highlighter_triangle);
		txtStudymates = (TextView) view.findViewById(R.id.txt_studymates);
		txtFindStudymates = (TextView) view.findViewById(R.id.txt_find_studymates);
		flFragmentContainer = (FrameLayout) view.findViewById(R.id.fl_fragment_container);
		vpStudymates = (ViewPager) view.findViewById(R.id.vp_studymates);

		viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == HostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

		adpStudymatesPager = new StudymatesPagerAdapter(activityHost.getFragmentManager());
		vpStudymates.setAdapter(adpStudymatesPager);
		vpStudymates.setCurrentItem(currentFragment);
		highlightCurrentVpItem(currentFragment);

		vpStudymates.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				highlightCurrentVpItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		txtStudymates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vpStudymates.setCurrentItem(0);
				highlightYourStudymates();
			}
		});

		txtFindStudymates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vpStudymates.setCurrentItem(1);
				highlightFindStudymates();
			}
		});

	}

	private void highlightCurrentVpItem(int position) {
		switch (position) {
			case 0:
				highlightYourStudymates();
				break;
			case 1:
				highlightFindStudymates();
				break;
		}
	}

	private void highlightFindStudymates() {
		txtFindStudymates.setEnabled(false);
		txtStudymates.setEnabled(true);
	}

	private void highlightYourStudymates() {
		txtStudymates.setEnabled(false);
		txtFindStudymates.setEnabled(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragListener = (FragmentListener) activity;
			activityHost = (HostActivity) activity;
			activityHost.setListenerHostStudymates(this);
			activityHost.setListenerProfileControllerPresence(this);
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_STUDYMATES);
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
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_STUDYMATES);
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
	public void setNewFragmentArguments(Bundle fragmentArguments) {
		if (fragmentArguments != null) {
			vpStudymates.setCurrentItem(fragmentArguments.getInt(CURRENT_FRAGMENT));
		}
	}
}
