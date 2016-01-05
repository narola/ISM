package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.TeacherChatAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Students;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class TeacherChatFragment extends Fragment  implements WebserviceWrapper.WebserviceResponse{

    private static final String TAG = TeacherChatFragment.class.getSimpleName();

    private RecyclerView rvMystudentsList;
    ArrayList<Students> arrListmyStudents = new ArrayList<>();

    private FragmentListener fragListener;
    TeacherHostActivity activityHost;

    TeacherChatAdapter teacherChatAdapter;

    public static TeacherChatFragment newInstance() {
        TeacherChatFragment fragChat = new TeacherChatFragment();
        return fragChat;
    }

    public TeacherChatFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (TeacherHostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_TEACHER_CHAT);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_TEACHER_CHAT);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_teacher_chat, container, false);

        initGlobal(view);
        Log.e(TAG, "called");
        return view;
    }

    private void initGlobal(View rootview) {
        rvMystudentsList =(RecyclerView)rootview.findViewById(R.id.rv_mystudents_list);
        rvMystudentsList.setLayoutManager(new LinearLayoutManager(getActivity()));
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



    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
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
                        teacherChatAdapter = new TeacherChatAdapter(getActivity(), arrListmyStudents);
                        rvMystudentsList.setAdapter(teacherChatAdapter);
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
}
