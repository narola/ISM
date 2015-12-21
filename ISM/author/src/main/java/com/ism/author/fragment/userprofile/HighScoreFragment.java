package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.HighScoreAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.HighScoreSubject;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.User;

import java.util.ArrayList;


/**
 * Created by c166 on 21/10/15.
 */
public class HighScoreFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = HighScoreFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private AuthorHostActivity activityHost;
    private ArrayList<User> arrListHighScorers;
    private HighScoreAdapter adpHighScorers;
    private ListView lvHighScore;
    private TextView txtEmpty;

    public static HighScoreFragment newInstance() {
        HighScoreFragment fragHighScore = new HighScoreFragment();
        return fragHighScore;
    }

    public HighScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_highscore, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        lvHighScore = (ListView) view.findViewById(R.id.lv_highScore);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        if (Utility.isConnected(activityHost)) {
            callApiGetHighScorers();
        } else {
            Utility.alertOffline(activityHost);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_HIGHSCORE);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_HIGHSCORE);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiGetHighScorers() {
        try {
            activityHost.showProgress();
            Attribute attribute = new Attribute();
//            attribute.setUserId(Global.strUserId);
//            attribute.setRoleId(Global.role);
            attribute.setUserId("1");
            attribute.setRoleId("student");

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_HIGH_SCORERS);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetHighScorers");
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_HIGH_SCORERS:
                    onResponseGetHighScorers(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetHighScorers(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    arrListHighScorers = responseHandler.getHighScorers();
                    fillListHighScorers();
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetHighScorers Failed");
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetHighScorers api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetHighScorers Exception : " + e.toString());
        }
    }
    private void fillListHighScorers() {
        try {
            if (arrListHighScorers != null) {
                ArrayList<HighScoreSubject> arrListHighScoreSubject = new ArrayList<HighScoreSubject>();
                for (User student : arrListHighScorers) {
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
                        ArrayList<User> students = new ArrayList<>();
                        students.add(student);
                        highScoreSubject.setArrListStudent(students);
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
