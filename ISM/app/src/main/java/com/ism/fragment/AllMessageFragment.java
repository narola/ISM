package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.MessageAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.views.CircleImageView;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 09/11/15.
 */
public class AllMessageFragment extends Fragment implements HostActivity.HostListenerAllMessage {

	private static final String TAG = AllMessageFragment.class.getSimpleName();

	private View view;
	private ListView lvAllMessage;
	private RelativeLayout rlMessageDetails;
	private CircleImageView imgDp;
	private TextView txtName, txtMessage, txtReply;

	private FragmentListener fragListener;
	private HostActivity activityHost;
	private ArrayList<Data> arrListMessage;
	private MessageAdapter adpMessage;
	private ImageLoader imageLoader;

	private static String MESSAGE_POSITION = "notificationPosition";
	private int positionMessage;

	public static AllMessageFragment newInstance(ArrayList<Data> arrListMessage, int position) {
		AllMessageFragment fragment = new AllMessageFragment();
		Bundle args = new Bundle();
		args.putInt(MESSAGE_POSITION, position);
		fragment.setArguments(args);
		fragment.setArrListMessage(arrListMessage);
		return fragment;
	}

	public void setArrListMessage(ArrayList<Data> arrListMessage) {
		this.arrListMessage = arrListMessage;
	}

	public AllMessageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		positionMessage = getArguments().getInt(MESSAGE_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_message, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		lvAllMessage = (ListView) view.findViewById(R.id.lv_all_message);

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

		if (positionMessage >= 0) {
			showMessageDetails(positionMessage);
		}

		if (arrListMessage != null) {
			adpMessage = new MessageAdapter(getActivity(), arrListMessage);
			lvAllMessage.setAdapter(adpMessage);
		}

		lvAllMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				showMessageDetails(position);
			}
		});
	}

	private void showMessageList() {
		if (rlMessageDetails != null) {
			rlMessageDetails.setVisibility(View.GONE);
		}
		lvAllMessage.setVisibility(View.VISIBLE);
	}

	private void showMessageDetails(int position) {
		activityHost.showControllerTopBackButton();
		lvAllMessage.setVisibility(View.GONE);
		if (rlMessageDetails == null) {
			rlMessageDetails = (RelativeLayout)((ViewStub) view.findViewById(R.id.vs_message_details)).inflate();
			initViews();
		} else {
			rlMessageDetails.setVisibility(View.VISIBLE);
		}

//		imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", imgDp, ISMStudent.options);
		txtName.setText(arrListMessage.get(position).getSenderName());
		txtMessage.setText(arrListMessage.get(position).getMessageText());
	}

	private void initViews() {
		imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
		txtName = (TextView) view.findViewById(R.id.txt_name);
		txtMessage = (TextView) view.findViewById(R.id.txt_message);
		txtReply = (TextView) view.findViewById(R.id.txt_reply);

		txtReply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Reply message.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragListener = (FragmentListener) activity;
			activityHost = (HostActivity) activity;
			activityHost.setListenerHostAllMessage(this);
			if (fragListener != null) {
				fragListener.onFragmentAttached(HostActivity.FRAGMENT_ALL_MESSAGE);
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
				fragListener.onFragmentDetached(HostActivity.FRAGMENT_ALL_MESSAGE);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onDetach Exception : " + e.toString());
		}
		fragListener = null;
	}

	@Override
	public void onControllerTopBackClick() {
		showMessageList();
	}
}
