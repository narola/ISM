package com.ism.author.fragment.mydesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.AppConstant;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

/**
 * Created by c162 on 17/12/15.
 */
public class AddAssignmentFragment extends Fragment {

    private static final String TAG = AddAssignmentFragment.class.getSimpleName();

    private View view;
    private FragmentListener fragListener;
    private TextView txtAssignmentInfo, txtSave;
    private AuthorHostActivity activityHost;
    private TextView txtCancel;
    private RichTextEditor richtexteditor;

    public static AddAssignmentFragment newInstance() {
        AddAssignmentFragment myDeskFragment = new AddAssignmentFragment();
        return myDeskFragment;
    }

    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_assignment, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        txtAssignmentInfo = (TextView) view.findViewById(R.id.txt_assignment_info);
        txtCancel = (TextView) view.findViewById(R.id.txt_cancel);
        txtSave = (TextView) view.findViewById(R.id.txt_save);
        richtexteditor = (RichTextEditor) view.findViewById(R.id.richtexteditor);
        richtexteditor.getRichEditor().setEditorFontSize(20);
        richtexteditor.hideMediaControls();
//        txtAssignments = (TextView) view.findViewById(R.id.txt_assignments);
        txtAssignmentInfo.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtSave.setTypeface(Global.myTypeFace.getRalewayRegular());
//        txtAssignments.setTypeface(Global.myTypeFace.getRalewayRegular());
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.txt_save: {
                    }
                    break;
                    case R.id.txt_cancel: {
                    }
                    break;

                }
            }
        };
        txtCancel.setOnClickListener(onClick);
        txtSave.setOnClickListener(onClick);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_ADD_ASSIGNMENT);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_ADD_ASSIGNMENT);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    public void onBackClick() {
        ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_ADD_ASSIGNMENT);
    }
}
