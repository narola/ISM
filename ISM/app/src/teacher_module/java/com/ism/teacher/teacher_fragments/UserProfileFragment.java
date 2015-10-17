package com.ism.teacher.teacher_fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.adapter.EventsAdapter;
import com.ism.adapter.HighScoreAdapter;
import com.ism.adapter.NoticeAdapter;
import com.ism.helper.AccordionView;
import com.ism.interfaces.FragmentListener;
import com.ism.model.EventsModel;
import com.ism.model.HighScoreModel;
import com.ism.model.HighScoreStudentModel;
import com.ism.model.NoticeModel;
import com.ism.object.MyTypeFace;
import com.ism.teacher.teacher_login.TeacherHomeActivity;

import java.util.ArrayList;

/**
 * Created by c161 on --/10/15.
 */
public class UserProfileFragment extends Fragment {

    private static final String TAG = UserProfileFragment.class.getSimpleName();

    private View rootview;


    private FragmentListener fragListener;

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragChat = new UserProfileFragment();
        return fragChat;
    }

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_profile, container, false);

        //initGlobal(rootview);

        Log.e(TAG,"called");
        return rootview;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_USER_PROFILE);
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
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_USER_PROFILE);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}