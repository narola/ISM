package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.jotterAdapter.FavAssignmentsAdapter;
import com.ism.adapter.jotterAdapter.FavBooksAdapter;
import com.ism.adapter.jotterAdapter.FavNotesAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;

/**
 * Created by c162 on 24/10/15.
 */
public class FavoriteFragment extends Fragment {

    private static final String TAG = FavoriteFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private HostActivity activityHost;
    private View includeNotes, includeBooks, includeAssignments, includeExams, includeLinks, includeAuthors, includeEvents;
//    private RelativeLayout rrNotes, rrBooks, rrAssignments, rrExams, rrEvents, rrAuthors, rrLinks;
    private TextView txtNotes, txtBooks, txtAssignments, txtEvents, txtLinks, txtAuthors, txtExams;
    private TextView txtViewAllNotes, txtViewAllBooks, txtViewAllAssignment, txtViewAllExmas, txtViewAllEvents, txtViewAllLinks, txtViewAllAuthors;
    private RecyclerView listViewNotes, listViewBooks, listViewAssignments, listViewExams, listViewAuthors, listViewLinks, listViewEvents;
    private FavNotesAdapter favNotesAdapter;
    private FavBooksAdapter favBooksAdapter;
    private FavAssignmentsAdapter favAssignmentsAdapter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragDesk = new FavoriteFragment();
        return fragDesk;
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desk_favorite, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        // inclue notes
        includeNotes = view.findViewById(R.id.include_notes);

//        rrNotes = (RelativeLayout) includeNotes.findViewById(R.id.rr_fav);
//        rrNotes.setBackgroundColor(getResources().getColor(R.color.color_red));

        txtNotes = (TextView) includeNotes.findViewById(R.id.txt_fav_item);
        txtNotes.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtNotes.setText(R.string.notes);
        txtNotes.setBackgroundColor(getResources().getColor(R.color.color_red));

        txtViewAllNotes = (TextView) includeNotes.findViewById(R.id.txt_view_all);
        txtViewAllNotes.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewNotes = (RecyclerView) includeNotes.findViewById(R.id.recyclerview);
        listViewNotes.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        // inclue books
        includeBooks = view.findViewById(R.id.include_books);

//        rrBooks = (RelativeLayout) includeBooks.findViewById(R.id.rr_fav);
//        rrBooks.setBackgroundColor(getResources().getColor(R.color.color_blue));

        txtBooks = (TextView) includeBooks.findViewById(R.id.txt_fav_item);
        txtBooks.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtBooks.setText(R.string.books);
        txtBooks.setBackgroundColor(getResources().getColor(R.color.color_blue));

        txtViewAllBooks = (TextView) includeBooks.findViewById(R.id.txt_view_all);
        txtViewAllBooks.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewBooks = (RecyclerView) includeBooks.findViewById(R.id.recyclerview);
        listViewBooks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // inclue assignments
        includeAssignments = view.findViewById(R.id.include_assignments);

//        rrAssignments = (RelativeLayout) includeAssignments.findViewById(R.id.rr_fav);
//        rrAssignments.setBackgroundColor(getResources().getColor(R.color.color_parrot));

        txtAssignments = (TextView) includeAssignments.findViewById(R.id.txt_fav_item);
        txtAssignments.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtAssignments.setText(R.string.assignments);
        txtAssignments.setBackgroundColor(getResources().getColor(R.color.color_parrot));

        txtViewAllAssignment = (TextView) includeAssignments.findViewById(R.id.txt_view_all);
        txtViewAllAssignment.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewAssignments = (RecyclerView) includeAssignments.findViewById(R.id.recyclerview);
        listViewAssignments.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        // inclue exams
        includeExams = view.findViewById(R.id.include_exam);

//        rrExams = (RelativeLayout) includeExams.findViewById(R.id.rr_fav);
//        rrExams.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtExams = (TextView) includeExams.findViewById(R.id.txt_fav_item);
        txtExams.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtExams.setText(R.string.exams);
        txtExams.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtViewAllExmas = (TextView) includeExams.findViewById(R.id.txt_view_all);
        txtViewAllExmas.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewExams = (RecyclerView) includeExams.findViewById(R.id.recyclerview);
        listViewExams.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        // inclue events
        includeEvents = view.findViewById(R.id.include_events);

//        rrEvents = (RelativeLayout) includeEvents.findViewById(R.id.rr_fav);
//        rrEvents.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtEvents = (TextView) includeEvents.findViewById(R.id.txt_fav_item);
        txtEvents.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtEvents.setText(R.string.events);
        txtEvents.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtViewAllEvents = (TextView) includeEvents.findViewById(R.id.txt_view_all);
        txtViewAllEvents.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewEvents = (RecyclerView) includeEvents.findViewById(R.id.recyclerview);
        listViewEvents.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

//        etNotes = (EditText) view.findViewById(R.id.et_notes);
//        etNotes.setTypeface(Global.myTypeFace.getRalewayRegular());


        setUpNotes();
        setUpBooks();
        setUpAssignments();


    }

    private void setUpAssignments() {
        try {
            favAssignmentsAdapter=new FavAssignmentsAdapter(getActivity());
            listViewAssignments.setAdapter(favAssignmentsAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpAssignments Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpBooks() {
        try {
            favBooksAdapter=new FavBooksAdapter(getActivity(),null,null);
            listViewBooks.setAdapter(favBooksAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpBooks Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpNotes() {
        try {
            favNotesAdapter=new FavNotesAdapter(getActivity());
            listViewNotes.setAdapter(favNotesAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpNotes Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

}
