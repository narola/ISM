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

/**
 * Created by c161 on --/10/15.
 *
 */
public class NotesFragment extends Fragment
{

    private static final String TAG = NotesFragment.class.getSimpleName();

    private View view;

    private FragmentListener fragListener;
    private AccordionView accordionNotes;
    private ListView lvNotice, lvEvents, lvHighScore;
    private TextView txtViewAll;

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
        setUpListView();
        return view;
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
        lvNotice.setAdapter(new NoticeAdapter(getActivity(), arrayListNotice));
        if (arrayListNotice.size() == 1)
            lvNotice.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (arrayListNotice.size() > 2) {
            txtViewAll.setVisibility(View.VISIBLE);
        } else {
            txtViewAll.setVisibility(View.VISIBLE);
        }

        //EVENTS
        EventsModel eventsModel;
        ArrayList<EventsModel> arrayListEvent = new ArrayList<EventsModel>();
        eventsModel = new EventsModel("", "", "", "");
        lvEvents.setAdapter(new EventsAdapter(getActivity(), arrayListEvent));
        //notes_ll_events.addView(lvEvents);

        //HIGH SCORE

        HighScoreModel highScoreModel;
        HighScoreStudentModel highScoreStudentModel;
        ArrayList<HighScoreStudentModel> arrayListHighScoreStudentList=new ArrayList<>();
        highScoreStudentModel=new HighScoreStudentModel("Adam Stanger","St. Xaviers FY CS","500","");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        highScoreStudentModel=new HighScoreStudentModel("Adam Stanger","St. Xaviers FY CS","500","");
        arrayListHighScoreStudentList.add(highScoreStudentModel);
        ArrayList<HighScoreModel> arrayListHighScore=new ArrayList<>();
        highScoreModel=new HighScoreModel("Maths",arrayListHighScoreStudentList);
        arrayListHighScore.add(highScoreModel);
        highScoreModel=new HighScoreModel("Science",arrayListHighScoreStudentList);
        arrayListHighScore.add(highScoreModel);
        lvHighScore.setAdapter(new HighScoreAdapter(getActivity(),arrayListHighScore));

    }

    private void initGlobal(View v) {
        accordionNotes = (AccordionView) v.findViewById(R.id.notes_accordion_view);
        //NOTICE
        //  notes_ll_notice = (LinearLayout) v.findViewById(R.id.notes_ll_notice);
        lvNotice = (ListView) v.findViewById(R.id.lv_notice);
        lvNotice.setDivider(new ColorDrawable(Color.parseColor("#f4f4f4")));
        lvNotice.setDividerHeight(2);
        txtViewAll = (TextView) v.findViewById(R.id.txt_viewAll);
        txtViewAll.setTypeface(new MyTypeFace(getActivity()).getRalewayRegular());
        //EVENTS
        // notes_ll_events = (LinearLayout) v.findViewById(R.id.notes_ll_events);
        lvEvents = (ListView) v.findViewById(R.id.lv_events);
        lvEvents.setDivider(new ColorDrawable(Color.parseColor("#f4f4f4")));
        lvEvents.setDividerHeight(2);
//        lvEvents.setText("Hello");
//        lvEvents.setTextColor(Color.BLACK);

        //HIGH SCORE
        lvHighScore = (ListView) v.findViewById(R.id.lv_highScore);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
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

}