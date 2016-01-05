package com.ism.teacher.fragments.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.userprofile.MyStudentsUserProfileAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Students;

import java.util.ArrayList;

/**
 * Created by c75 on 22/12/15.
 */
public class MyStudentsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, TeacherHostActivity.ProfileControllerPresenceListener {

    private static final String TAG = MyStudentsFragment.class.getSimpleName();

    //Views
    private RecyclerView rvMystudents;
    private View viewHighlighterTriangle;

    ArrayList<Students> arrListmyStudents = new ArrayList<>();
    MyStudentsUserProfileAdapter myStudentsUserProfileAdapter;
    private FragmentListener fragListener;
    private TeacherHostActivity activityHost;

    public static MyStudentsFragment newInstance() {
        MyStudentsFragment myStudentsFragment = new MyStudentsFragment();
        return myStudentsFragment;
    }

    public MyStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (TeacherHostActivity) activity;
            fragListener = (FragmentListener) activity;
            activityHost.setListenerProfileControllerPresence(this);
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_MY_STUDENTS);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_MY_STUDENTS);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_mystudents_teacher_profile, container, false);

        initGlobal(rootview);
        return rootview;
    }

    private void initGlobal(View rootview) {
        viewHighlighterTriangle = (View) rootview.findViewById(R.id.view_highlighter_triangle);
        rvMystudents = (RecyclerView) rootview.findViewById(R.id.rv_mystudents);
        rvMystudents.setHasFixedSize(true);
        rvMystudents.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        viewHighlighterTriangle.setVisibility(activityHost.getCurrentRightFragment() == TeacherHostActivity.FRAGMENT_PROFILE_CONTROLLER ? View.VISIBLE : View.GONE);

        callApiGetMyStudents();
    }

    private void callApiGetMyStudents() {
        try {
            if (Utility.isConnected(activityHost)) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
//                attribute.setUserId(Global.strUserId);
                attribute.setUserId(WebConstants.USER_ID_319);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_MY_STUDENTS);
            } else {
                Utility.alertOffline(activityHost);
            }

        } catch (Exception e) {
            Debug.e(TAG, "callApiGetNotifications Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {

        switch (apiCode) {
            case WebConstants.GET_MY_STUDENTS:
                onResponseGetMyStudents(object, error);
                break;
        }
    }

    private void onResponseGetMyStudents(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    Debug.e(TAG, "onResponseGetNotification success");
                    arrListmyStudents = responseHandler.getStudents();

                    if (arrListmyStudents.size() > 0) {
                        myStudentsUserProfileAdapter = new MyStudentsUserProfileAdapter(getActivity(), arrListmyStudents);
                        rvMystudents.setAdapter(myStudentsUserProfileAdapter);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Debug.e(TAG, "onResponseGetNotification Failed");
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetNotification api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetNotification Exception : " + e.toString());
        }
    }

    @Override
    public void onProfileControllerAttached() {
        viewHighlighterTriangle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProfileControllerDetached() {
        viewHighlighterTriangle.setVisibility(View.GONE);
    }
}
