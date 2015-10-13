package com.ism.fragment;

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

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private static final String TAG = NotesFragment.class.getSimpleName();

    private View mView;

    private FragmentListener mFragmentListener;
    private AccordionView notes_accordion_view;
    private LinearLayout notes_ll_notice, notes_ll_events;
    private ListView notice_lv_notice, notice_lv_events, notes_lv_highScore;
    private boolean moreRecordFound = false;
    private TextView notes_txt_viewAll;

    public static NotesFragment newInstance() {
        NotesFragment fragmentNotes = new NotesFragment();
        return fragmentNotes;
    }

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_notes, container, false);

        initGlobal(mView);
        setUpListView();
        return mView;
    }

    private void setUpListView() {
        //NOTICE
        NoticeModel model;

        //logic for adapter =>set  only two record for adapter if four or more record found then setvisibilty of VIEW ALL VISIBLE
        ArrayList<NoticeModel> arrayListNotice = new ArrayList<NoticeModel>();
        model = new NoticeModel("Dance competition", "Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years.");
        arrayListNotice.add(model);
        model = new NoticeModel("Dance competition", "Our school is orgnizing a dance competition on 01-01-2016 in scholl auditorium. Minimum age limits is 23 years.");
        arrayListNotice.add(model);
        notice_lv_notice.setAdapter(new NoticeAdapter(getActivity(), arrayListNotice));
        if (arrayListNotice.size() == 1)
            notice_lv_notice.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (arrayListNotice.size() > 2) {
            notes_txt_viewAll.setVisibility(View.VISIBLE);
        } else {
            notes_txt_viewAll.setVisibility(View.VISIBLE);
        }

        //EVENTS
        EventsModel eventsModel;
        ArrayList<EventsModel> arrayListEvent = new ArrayList<EventsModel>();
        eventsModel = new EventsModel("", "", "", "");
        notice_lv_events.setAdapter(new EventsAdapter(getActivity(), arrayListEvent));
        //notes_ll_events.addView(notice_lv_events);

        //HIGH SCORE

        HighScoreModel highScoreModel;
        HighScoreStudentModel highScoreStudentModel;
        ArrayList<HighScoreStudentModel> arrayListHighScoreStudentList = new ArrayList<>();
        highScoreStudentModel = new HighScoreStudentModel("Adam Stanger", "St. Xaviers FY CS", "500", "");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        highScoreStudentModel = new HighScoreStudentModel("Adam Stanger", "St. Xaviers FY CS", "500", "");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        highScoreStudentModel = new HighScoreStudentModel("Adam Stanger", "St. Xaviers FY CS", "500", "");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        ArrayList<HighScoreModel> arrayListHighScore = new ArrayList<>();
        highScoreModel = new HighScoreModel("Maths", arrayListHighScoreStudentList);
        arrayListHighScore.add(highScoreModel);
        highScoreModel = new HighScoreModel("Science", arrayListHighScoreStudentList);
        arrayListHighScore.add(highScoreModel);
        notes_lv_highScore.setAdapter(new HighScoreAdapter(getActivity(), arrayListHighScore));

    }

    private void initGlobal(View v) {
        notes_accordion_view = (AccordionView) v.findViewById(R.id.notes_accordion_view);
        //NOTICE
        //  notes_ll_notice = (LinearLayout) v.findViewById(R.id.notes_ll_notice);
        notice_lv_notice = (ListView) v.findViewById(R.id.notes_lv_notice);
        notice_lv_notice.setDivider(new ColorDrawable(Color.parseColor("#f4f4f4")));
        notice_lv_notice.setDividerHeight(2);
        notes_txt_viewAll = (TextView) v.findViewById(R.id.notes_txt_viewAll);
        notes_txt_viewAll.setTypeface(new MyTypeFace(getActivity()).getRalewayRegular());
        //EVENTS
        // notes_ll_events = (LinearLayout) v.findViewById(R.id.notes_ll_events);
        notice_lv_events = (ListView) v.findViewById(R.id.notes_lv_events);
        notice_lv_events.setDivider(new ColorDrawable(Color.parseColor("#f4f4f4")));
        notice_lv_events.setDividerHeight(2);
//        notice_lv_events.setText("Hello");
//        notice_lv_events.setTextColor(Color.BLACK);

        //HIGH SCORE
        notes_lv_highScore = (ListView) v.findViewById(R.id.notes_lv_highScore);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mFragmentListener = (FragmentListener) activity;
            if (mFragmentListener != null) {
                mFragmentListener.onFragmentAttached(HostActivity.FRAGMENT_NOTES);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (mFragmentListener != null) {
                mFragmentListener.onFragmentDetached(HostActivity.FRAGMENT_NOTES);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        mFragmentListener = null;
    }


}
