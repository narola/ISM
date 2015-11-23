package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.StudymateRequestAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.StudymateRequest;

import java.util.ArrayList;


/**
 * Created by c161 on 09/11/15.
 */
public class AllStudymateRequestFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	private static final String TAG = AllStudymateRequestFragment.class.getSimpleName();

	private View view;
	private ListView lvAllStudyMate;
	private TextView txtHeader;

	private FragmentListener fragListener;
	private HostActivity activityHost;
	private ArrayList<StudymateRequest> arrListStudymateRequest;
	private StudymateRequestAdapter adpStudymate;

	public static final String ARG_ARR_LIST_STUDYMATE_REQUEST = "arrListStudymateRequest";

	public static AllStudymateRequestFragment newInstance(Bundle bundleArgument) {
		AllStudymateRequestFragment fragment = new AllStudymateRequestFragment();
		fragment.setArguments(bundleArgument);
		return fragment;
	}

	public AllStudymateRequestFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			arrListStudymateRequest = getArguments().getParcelableArrayList(ARG_ARR_LIST_STUDYMATE_REQUEST);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_studymate_request, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllStudyMate = (ListView) view.findViewById(R.id.lv_all_studymate);
		txtHeader = (TextView) view.findViewById(R.id.txt_header_white);

		if (arrListStudymateRequest != null) {
			if (arrListStudymateRequest.size() == 0) {
				txtHeader.setText(activityHost.getString(R.string.studymate_requests));
			} else if (arrListStudymateRequest.size() == 1) {
				txtHeader.setText(activityHost.getString(R.string.respond_to_your) + " " + activityHost.getString(R.string.studymate_request));
			} else if (arrListStudymateRequest.size() > 1) {
				txtHeader.setText(activityHost.getString(R.string.respond_to_your) + " " + arrListStudymateRequest.size() + " " + activityHost.getString(R.string.studymate_requests));
			}
			adpStudymate = new StudymateRequestAdapter(getActivity(), arrListStudymateRequest);
			lvAllStudyMate.setAdapter(adpStudymate);
			callApiUpdateReadStatus();
		}

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragListener = (FragmentListener) activity;
			activityHost = (HostActivity) activity;
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onAttach Exception : " + e.toString());
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			if (fragListener != null) {
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_ALL_STUDYMATE_REQUEST);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	private void callApiUpdateReadStatus() {
		try {
			if (arrListStudymateRequest != null && arrListStudymateRequest.size() > 0) {
				ArrayList<String> recordIds = new ArrayList<String>();
				for (int i = 0; i < arrListStudymateRequest.size(); i++) {
					recordIds.add(arrListStudymateRequest.get(i).getRecordId());
				}
				Attribute attribute = new Attribute();
				attribute.setUserId(Global.strUserId);
				attribute.setReadCategory(WebConstants.STUDYMATE_REQUEST);
				attribute.setRecordIds(recordIds);

				new WebserviceWrapper(activityHost, attribute, this).new WebserviceCaller().
						execute(WebConstants.UPDATE_READ_STATUS);
			}
		} catch (Exception e) {
			Log.e(TAG, "callApiUpdateReadStatus Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.UPDATE_READ_STATUS:
					onResponseUpdateReadStatus(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseUpdateReadStatus(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					Log.e(TAG, "Read status updated");
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "Read status update failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseUpdateReadStatus api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseUpdateReadStatus Exception : " + e.toString());
		}
	}
}
