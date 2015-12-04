package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ism.activity.HostActivity;
import com.ism.R;
import com.ism.adapter.StudymateChatAdapter;
import com.ism.constant.WebConstants;
import com.ism.fragment.userprofile.StudymatesFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.User;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class ChatFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = ChatFragment.class.getSimpleName();

    private View view;
	private ListView lvStudymates;
	private Button btnSuggestedStudymates, btnFindMoreStudymates;

	private HostActivity activityHost;
    private FragmentListener fragListener;
	private ArrayList<User> arrListStudymate;
	private StudymateChatAdapter adpStudymateChat;

    public static ChatFragment newInstance() {
        ChatFragment fragChat = new ChatFragment();
        return fragChat;
    }

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
	    lvStudymates = (ListView) view.findViewById(R.id.lv_studymates);
	    btnSuggestedStudymates = (Button) view.findViewById(R.id.btn_suggested_studymates);
	    btnFindMoreStudymates = (Button) view.findViewById(R.id.btn_find_more_studymates);

		if (Utility.isConnected(getActivity())) {
			callApiGetStudymates();
		} else {
			Utility.alertOffline(getActivity());
		}

	    btnSuggestedStudymates.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Bundle fragmentArguments = new Bundle();
			    fragmentArguments.putInt(StudymatesFragment.CURRENT_FRAGMENT, StudymatesFragment.FRAGMENT_SUGGESTED_STUDYMATES);
			    activityHost.loadFragment(HostActivity.FRAGMENT_STUDYMATES, fragmentArguments);
		    }
	    });

	    btnFindMoreStudymates.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Bundle fragmentArguments = new Bundle();
			    fragmentArguments.putInt(StudymatesFragment.CURRENT_FRAGMENT, StudymatesFragment.FRAGMENT_FIND_MORE_STUDYMATES);
			    activityHost.loadFragment(HostActivity.FRAGMENT_STUDYMATES, fragmentArguments);
		    }
	    });

    }

	private void callApiGetStudymates() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_ALL_STUDYMATES_WITH_DETAILS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetStudymates Exception : " + e.toString());
		}
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
	        activityHost = (HostActivity) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_CHAT);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_CHAT);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_ALL_STUDYMATES_WITH_DETAILS:
					onResponseGetStudymates(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetStudymates(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListStudymate = responseHandler.getStudymates();
					fillListStudymates();
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetStudymates failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetStudymates api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetStudymates Exception : " + e.toString());
		}
	}

	private void fillListStudymates() {
		if (arrListStudymate != null) {
			adpStudymateChat = new StudymateChatAdapter(getActivity(), arrListStudymate);
			lvStudymates.setAdapter(adpStudymateChat);
		}
	}
}
