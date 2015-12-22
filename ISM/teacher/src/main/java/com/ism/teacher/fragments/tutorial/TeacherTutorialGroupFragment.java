package com.ism.teacher.fragments.tutorial;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.tutorial.TutorialGroupAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Group;

import java.util.ArrayList;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherTutorialGroupFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {

    private static final String TAG = TeacherTutorialGroupFragment.class.getSimpleName();

    //Views
    private LinearLayout llGroupMembers;
    private RecyclerView rvTutorialGroup;
    private TextView tvGroupName, tvGroupClassName, tvGroupScores, tvGroupRank, tvTopic, tvNoGroupMembers;
    private Button btnScheduleExam;

    //Objects
    private FragmentListener fragListener;

    private TutorialGroupAdapter tutorialGroupAdapter;

    public static TeacherTutorialGroupFragment newInstance() {
        TeacherTutorialGroupFragment teacherTutorialGroupFragment = new TeacherTutorialGroupFragment();
        return teacherTutorialGroupFragment;
    }

    public TeacherTutorialGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tutorial_group_teacher, container, false);

        initGlobal(rootview);
        return rootview;
    }

    public void initGlobal(View rootview) {
        llGroupMembers = (LinearLayout) rootview.findViewById(R.id.ll_group_members);
        rvTutorialGroup = (RecyclerView) rootview.findViewById(R.id.rv_tutorial_group);

        tvNoGroupMembers = (TextView) rootview.findViewById(R.id.tv_no_group_members);
        tvGroupName = (TextView) rootview.findViewById(R.id.tv_group_name);
        tvGroupClassName = (TextView) rootview.findViewById(R.id.tv_group_class_name);
        tvGroupScores = (TextView) rootview.findViewById(R.id.tv_group_scores);
        tvGroupRank = (TextView) rootview.findViewById(R.id.tv_group_rank);
        tvTopic = (TextView) rootview.findViewById(R.id.tv_topic);
        btnScheduleExam = (Button) rootview.findViewById(R.id.btn_schedule_exam);
        btnScheduleExam.setOnClickListener(this);

        rvTutorialGroup.setHasFixedSize(true);
        rvTutorialGroup.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        tutorialGroupAdapter = new TutorialGroupAdapter(getActivity());
        rvTutorialGroup.setAdapter(tutorialGroupAdapter);

        applyFonts();

        if (Utility.isInternetConnected(getActivity())) {
            callGroupAllocationApi();
        }
    }

    private void callGroupAllocationApi() {
        try {
            ((TeacherHostActivity) getActivity()).showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(WebConstants.USER_ID_370);
            new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GROUP_ALLOCATION);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    private void applyFonts() {
        tvGroupName.setTypeface(Global.myTypeFace.getRalewayBold());
        tvGroupClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvGroupScores.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvGroupRank.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvTopic.setTypeface(Global.myTypeFace.getRalewayBold());
        btnScheduleExam.setTypeface(Global.myTypeFace.getRalewayRegular());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_TEACHER_TUTORIAL_GROUP);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_TEACHER_TUTORIAL_GROUP);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GROUP_ALLOCATION:
                    onResponseGetGroupAllocation(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetGroupAllocation(Object object, Exception error) {
        try {
            ((TeacherHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                    if (responseHandler.getGroup().size() > 0) {

                        setTutorialGroupDetails(responseHandler.getGroup());
                        tutorialGroupAdapter.addAll(responseHandler.getGroup().get(0).getGroupMembers());
                    } else {
                        Utility.hideView(llGroupMembers);
                        Utility.showView(tvNoGroupMembers);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetGroupAllocation Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetGroupAllocation : " + e.toString());
        }
    }

    private void setTutorialGroupDetails(ArrayList<Group> group) {
        tvGroupName.setText(getActivity().getResources().getString(R.string.str_group_name) + " : ");
        tvGroupName.append(Utility.getSpannableString(group.get(0).getGroupName(), getActivity().getResources().getColor(R.color.color_green_exam_name)));

        tvGroupClassName.setText("(" + group.get(0).getGroupClass() + ")");

        tvGroupScores.setText(getActivity().getResources().getString(R.string.str_group_scores) + " : ");
        tvGroupScores.append(Utility.getSpannableString(group.get(0).getGroupScore(), getActivity().getResources().getColor(R.color.color_blue_group_score)));

        if (group.get(0).getGroupRank() != null && !group.get(0).getGroupRank().equals("")) {
            tvGroupRank.setText(getActivity().getResources().getString(R.string.str_group_rank) + " : ");
            tvGroupRank.append(Utility.getSpannableString(group.get(0).getGroupRank(), getActivity().getResources().getColor(R.color.color_blue_group_score)));
        } else {
            tvGroupRank.setText(getActivity().getResources().getString(R.string.str_group_rank) + " : ");
            tvGroupRank.append(Utility.getSpannableString("---", getActivity().getResources().getColor(R.color.color_blue_group_score)));
        }

        tvTopic.setText(getActivity().getResources().getString(R.string.str_teacher_tutorial_label) + " " + group.get(0).getTopicName());
    }

    @Override
    public void onClick(View v) {
//        if (v == btnScheduleExam) {
//            Utility.showToast("Tutorial Exam", getActivity());
//        }
    }
}