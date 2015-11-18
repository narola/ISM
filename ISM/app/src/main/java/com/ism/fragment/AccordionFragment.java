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
import com.ism.adapter.HighScoreAdapter;
import com.ism.adapter.NoticeAdapter;
import com.ism.constant.WebConstants;
import com.ism.interfaces.FragmentListener;
import com.ism.model.FragmentArgument;
import com.ism.model.HighScoreSubject;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.AccordionView;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class AccordionFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = AccordionFragment.class.getSimpleName();

    private View view;
    private AccordionView accordionNotes;
    private ListView lvNotice, lvHighScore;
    private TextView txtViewAllNotice;

	private HostActivity activityHost;
	private ArrayList<Data> arrListNotice;
	private ArrayList<Data> arrListHighScorers;
	private NoticeAdapter adpNotice;
	private HighScoreAdapter adpHighScorers;
    private FragmentListener fragListener;

    public static AccordionFragment newInstance() {
        AccordionFragment fragNotes = new AccordionFragment();
        return fragNotes;
    }

    public AccordionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_accordion, container, false);

        initGlobal(view);

        return view;
    }

	private void initGlobal(View v) {
		accordionNotes = (AccordionView) v.findViewById(R.id.accordion_view_chat);
		lvNotice = (ListView) v.findViewById(R.id.lv_notice);
		//lvEvents = (ListView) v.findViewById(R.id.lv_events);
		lvHighScore = (ListView) v.findViewById(R.id.lv_highScore);
		txtViewAllNotice = (TextView) v.findViewById(R.id.txt_viewAll_notice);

		txtViewAllNotice.setTypeface(new MyTypeFace(getActivity()).getRalewaySemiBold());

		txtViewAllNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            activityHost.loadFragment(HostActivity.FRAGMENT_ALL_NOTES, new FragmentArgument(arrListNotice));
            }
        });

        callApiGetAllNotice();
		callApiGetHighScorers();
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
			requestObject.setRoleId(WebConstants.ROLE_ALL);
//			requestObject.setRoleId(WebConstants.ROLE_STUDENT);

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_ALL_NOTICES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetAllNotice");
		}
	}

	private void callApiGetHighScorers() {
		try {
			activityHost.showProgress();
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(Global.strUserId);
			requestObject.setRoleId(WebConstants.ROLE_STUDENT);

			new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
					.execute(WebConstants.GET_HIGH_SCORERS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetHighScorers");
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_ALL_NOTICES:
					onResponseGetAllNotice(object, error);
					break;
				case WebConstants.GET_HIGH_SCORERS:
					onResponseGetHighScorers(object, error);
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

	private void onResponseGetHighScorers(Object object, Exception error) {
		try {
			activityHost.hideProgress();
			if (object != null) {
				ResponseObject responseObj = (ResponseObject) object;
				if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					arrListHighScorers = responseObj.getData();
					fillListHighScorers();
				} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
					Log.e(TAG, "onResponseGetHighScorers Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetHighScorers api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetHighScorers Exception : " + e.toString());
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

	private void fillListHighScorers() {
		try {
			if (arrListHighScorers != null) {
				ArrayList<HighScoreSubject> arrListHighScoreSubject = new ArrayList<HighScoreSubject>();
				for (Data student : arrListHighScorers) {
					boolean subjectFound = false;
					for (HighScoreSubject highScoreSubject : arrListHighScoreSubject) {
						if (highScoreSubject.getSubjectName().equals(student.getSubjectName())) {
							highScoreSubject.getArrListStudent().add(student);
							subjectFound = true;
							break;
						}
					}
					if (!subjectFound) {
						HighScoreSubject highScoreSubject = new HighScoreSubject();
						highScoreSubject.setSubjectName(student.getSubjectName());
						highScoreSubject.setArrListStudent(new ArrayList<Data>());
						arrListHighScoreSubject.add(highScoreSubject);
					}
				}
				adpHighScorers = new HighScoreAdapter(activityHost, arrListHighScoreSubject);
				lvHighScore.setAdapter(adpHighScorers);
			} else {
				Log.e(TAG, "high scorers list null");
			}
		} catch (Exception e) {
			Log.e(TAG, "fillListHighScorers Exception : " + e.toString());
		}
	}

}