package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ism.R;
import com.ism.adapter.MessageAdapter;
import com.ism.adapter.NotificationAdapter;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 09/11/15.
 */
public class AllMessageFragment extends Fragment {

	private static final String TAG = AllMessageFragment.class.getSimpleName();

	private View view;
	private ListView lvAllMessage;

	private ArrayList<Data> arrListMessage;
	private MessageAdapter adpMessage;

	public static AllMessageFragment newInstance(ArrayList<Data> arrListMessage) {
		AllMessageFragment fragment = new AllMessageFragment();
		fragment.setArrListMessage(arrListMessage);
		return fragment;
	}

	public void setArrListMessage(ArrayList<Data> arrListMessage) {
		this.arrListMessage = arrListMessage;
	}

	public AllMessageFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_message, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllMessage = (ListView) view.findViewById(R.id.lv_all_message);

		if (arrListMessage != null) {
			adpMessage = new MessageAdapter(getActivity(), arrListMessage);
			lvAllMessage.setAdapter(adpMessage);
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
