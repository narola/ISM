package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.EventsAdapter;
import com.ism.adapter.HighScoreAdapter;
import com.ism.adapter.NoticeAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.model.EventsModel;
import com.ism.model.HighScoreModel;
import com.ism.model.HighScoreStudentModel;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.AccordionView;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.TestRequestObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class NotesFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = NotesFragment.class.getSimpleName();

    private View view;
    private AccordionView accordionNotes;
    private ListView lvNotice, lvEvents, lvHighScore;
    private TextView txtViewAllNotice;

	private HostActivity activityHost;
	private ArrayList<Data> arrListNotice;
	private NoticeAdapter adpNotice;
    private FragmentListener fragListener;

    public static NotesFragment newInstance() {
        NotesFragment fragNotes = new NotesFragment();
        return fragNotes;
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes, container, false);

        initGlobal(view);

        return view;
    }

	private void initGlobal(View v) {
		accordionNotes = (AccordionView) v.findViewById(R.id.accordion_view_chat);
		lvNotice = (ListView) v.findViewById(R.id.lv_notice);
		lvEvents = (ListView) v.findViewById(R.id.lv_events);
		lvHighScore = (ListView) v.findViewById(R.id.lv_highScore);
		txtViewAllNotice = (TextView) v.findViewById(R.id.txt_viewAll_notice);

		txtViewAllNotice.setTypeface(new MyTypeFace(getActivity()).getRalewaySemiBold());

		txtViewAllNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityHost.showAllNotice(arrListNotice);
            }
        });

        callApiGetAllNotice();

		setUpListView();
	}

    private void setUpListView() {
        //NOTICE
        //logic for adapter => set  only two record for adapter. if four or more record found then setvisibilty of VIEW ALL VISIBLE.
        /*ArrayList<Notice> arrayListNotice = new ArrayList<Notice>();
        arrayListNotice.add(new Notice("Dance competition", "Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years."));
        arrayListNotice.add(new Notice("Dance competition", "Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years. Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years."));
        arrayListNotice.add(new Notice("Dance competition", "Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years."));
        arrayListNotice.add(new Notice("Dance competition", "Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years. Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years."));

	    adpNotice = new NoticeAdapter(getActivity(), arrayListNotice, activityHost);
	    lvNotice.setAdapter(adpNotice);

	    if (arrayListNotice.size() > 2) {
            txtViewAllNotice.setVisibility(View.VISIBLE);
        } else {
		    if (arrayListNotice.size() == 1) {
			    lvNotice.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		    }
            txtViewAllNotice.setVisibility(View.GONE);
        }*/

        //EVENTS
        EventsModel eventsModel;
        ArrayList<EventsModel> arrayListEvent = new ArrayList<EventsModel>();
        eventsModel = new EventsModel("", "", "", "");
        lvEvents.setAdapter(new EventsAdapter(getActivity(), arrayListEvent));
        //notes_ll_events.addView(lvEvents);

        //HIGH SCORE
        HighScoreModel highScoreModel;
        HighScoreStudentModel highScoreStudentModel;
        ArrayList<HighScoreStudentModel> arrayListHighScoreStudentList = new ArrayList<>();
        highScoreStudentModel = new HighScoreStudentModel("Adam Stanger", "St. Xaviers FY CS", "500", "");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        highScoreStudentModel = new HighScoreStudentModel("Adam Stanger", "St. Xaviers FY CS", "500", "");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        ArrayList<HighScoreModel> arrayListHighScore = new ArrayList<>();
        highScoreModel = new HighScoreModel("Maths", arrayListHighScoreStudentList);
        arrayListHighScore.add(highScoreModel);
        highScoreModel = new HighScoreModel("Science", arrayListHighScoreStudentList);
        arrayListHighScore.add(highScoreModel);
        lvHighScore.setAdapter(new HighScoreAdapter(getActivity(), arrayListHighScore));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
	        activityHost = (HostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_NOTES);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_NOTES);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

	private void callApiGetAllNotice() {
		try {
			activityHost.showProgress();
			RequestObject requestObject = new RequestObject();
//			requestObject.setUserId(Global.strUserId);
			requestObject.setRoleId("all");
//			TestRequestObject requestObject = new TestRequestObject();
//			requestObject.setUserId(Integer.valueOf(Global.strUserId));

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_ALL_NOTICES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetAllNotice");
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_ALL_NOTICES:
					onResponseGetAllNotice(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetAllNotice(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListNotice = responseObj.getData();
					fillListNotice();
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onResponseGetAllNotice Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetAllNotice api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAllNotice Exception : " + e.toString());
		}
	}

	private void fillListNotice() {
		try {
			if (arrListNotice != null) {
				adpNotice = new NoticeAdapter(getActivity(), arrListNotice, activityHost);
				lvNotice.setAdapter(adpNotice);

				if (arrListNotice.size() > 2) {
					txtViewAllNotice.setVisibility(View.VISIBLE);
				} else {
					if (arrListNotice.size() == 1) {
						lvNotice.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
					}
					txtViewAllNotice.setVisibility(View.GONE);
				}
			} else {
				Log.e(TAG, "all notice list null");
			}
		} catch (Exception e) {
			Log.e(TAG, "fillListNotice Exception : " + e.toString());
		}
	}

}