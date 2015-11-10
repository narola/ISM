package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ism.R;
import com.ism.adapter.NotificationAdapter;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 09/11/15.
 */
public class AllNotificationFragment extends Fragment {

	private static final String TAG = AllNotificationFragment.class.getSimpleName();

	private View view;
	private ListView lvAllNotification;
	private RelativeLayout rlPost;

	private ArrayList<Data> arrListNotification;
	private NotificationAdapter adpNotification;

	public static AllNotificationFragment newInstance(ArrayList<Data> arrListNotification) {
		AllNotificationFragment fragment = new AllNotificationFragment();
		fragment.setArrListNotification(arrListNotification);
		return fragment;
	}

	public void setArrListNotification(ArrayList<Data> arrListNotification) {
		this.arrListNotification = arrListNotification;
	}

	public AllNotificationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_notification, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllNotification = (ListView) view.findViewById(R.id.lv_all_notification);

		lvAllNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				lvAllNotification.setVisibility(View.GONE);
				if (rlPost == null) {
					rlPost = (RelativeLayout)((ViewStub) view.findViewById(R.id.vs_post)).inflate();
				} else {
					rlPost.setVisibility(View.VISIBLE);
				}
			}
		});

		if (arrListNotification != null) {
			adpNotification = new NotificationAdapter(getActivity(), arrListNotification);
			lvAllNotification.setAdapter(adpNotification);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
