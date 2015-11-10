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
import com.ism.adapter.StudymateAdapter;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 09/11/15.
 */
public class AllStudymateRequestFragment extends Fragment {

	private static final String TAG = AllStudymateRequestFragment.class.getSimpleName();

	private View view;
	private ListView lvAllStudyMate;

	private ArrayList<Data> arrListStudymateRequest;
	private StudymateAdapter adpStudymate;

	public static AllStudymateRequestFragment newInstance(ArrayList<Data> arrListStudymateRequest) {
		AllStudymateRequestFragment fragment = new AllStudymateRequestFragment();
		fragment.setArrListStudymateRequest(arrListStudymateRequest);
		return fragment;
	}

	public void setArrListStudymateRequest(ArrayList<Data> arrListStudymateRequest) {
		this.arrListStudymateRequest = arrListStudymateRequest;
	}

	public AllStudymateRequestFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_studymate, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllStudyMate = (ListView) view.findViewById(R.id.lv_all_studymate);

		if (arrListStudymateRequest != null) {
			adpStudymate = new StudymateAdapter(getActivity(), arrListStudymateRequest);
			lvAllStudyMate.setAdapter(adpStudymate);
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
