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
import com.ism.adapter.jotterAdapter.FavExamsAdapter;
import com.ism.adapter.jotterAdapter.FavLinksAdapter;
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
    private FavExamsAdapter favExamsAdapter;
    private FavLinksAdapter linksAdapter;

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
        listViewNotes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

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
        listViewAssignments.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // inclue exams
//        includeExams = view.findViewById(R.id.include_exam);

//        rrExams = (RelativeLayout) includeExams.findViewById(R.id.rr_fav);
//        rrExams.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtExams = (TextView) view.findViewById(R.id.txt_fav_exam);
        txtExams.setTypeface(Global.myTypeFace.getRalewayMedium());
//        txtExams.setText(R.string.exams);
//        txtExams.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtViewAllExmas = (TextView) view.findViewById(R.id.txt_view_all_exam);
        txtViewAllExmas.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewExams = (RecyclerView) view.findViewById(R.id.recyclerview_exams);
        listViewExams.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // inclue events
        includeEvents = view.findViewById(R.id.include_events);

        txtEvents = (TextView) includeEvents.findViewById(R.id.txt_fav_item);
        txtEvents.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtEvents.setText(R.string.events);
        txtEvents.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));

        txtViewAllEvents = (TextView) includeEvents.findViewById(R.id.txt_view_all);
        txtViewAllEvents.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewEvents = (RecyclerView) includeEvents.findViewById(R.id.recyclerview);
        listViewEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

//        // inclue author
//        includeAuthors = view.findViewById(R.id.include_authors);
//
//        txtAuthors = (TextView) includeAuthors.findViewById(R.id.txt_fav_item);
//        txtAuthors.setTypeface(Global.myTypeFace.getRalewayMedium());
//        txtAuthors.setText(R.string.authors);
//        txtAuthors.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));
//
//        txtViewAllAuthors = (TextView) includeAuthors.findViewById(R.id.txt_view_all);
//        txtViewAllAuthors.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        listViewAuthors = (RecyclerView) includeAuthors.findViewById(R.id.recyclerview);
//        listViewAuthors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

//        // inclue links
//        includeLinks = view.findViewById(R.id.include_links);
//
//        txtLinks = (TextView) includeLinks.findViewById(R.id.txt_fav_item);
//        txtLinks.setTypeface(Global.myTypeFace.getRalewayMedium());
//        txtLinks.setText(R.string.links);
//        txtLinks.setBackgroundColor(getResources().getColor(R.color.color_pink_dark));
//
//        txtViewAllLinks = (TextView) includeLinks.findViewById(R.id.txt_view_all);
//        txtViewAllLinks.setTypeface(Global.myTypeFace.getRalewayRegular());
//
//        listViewLinks = (RecyclerView) includeLinks.findViewById(R.id.recyclerview);
//        listViewLinks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        setUpNotes();
        setUpBooks();
        setUpAssignments();
        setUpExams();
        //setUpLinks();
        setUpEvents();
        onClicks();


    }

    private void setUpEvents() {
        try {
            linksAdapter = new FavLinksAdapter(getActivity());
            listViewEvents.setAdapter(linksAdapter);
//            listViewLinks.setAdapter(linksAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpEvents Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onClicks() {
        try {
           txtViewAllNotes.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   
               }
           });

        } catch (Exception e) {
            Debug.i(TAG, "onClicks Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpLinks() {
        try {
            linksAdapter = new FavLinksAdapter(getActivity());
            listViewLinks.setAdapter(linksAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpLinks Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpExams() {
        try {
            favExamsAdapter = new FavExamsAdapter(getActivity());
            listViewExams.setAdapter(favExamsAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpExams Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpAssignments() {
        try {
            favAssignmentsAdapter = new FavAssignmentsAdapter(getActivity());
            listViewAssignments.setAdapter(favAssignmentsAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpAssignments Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpBooks() {
        try {
            favBooksAdapter = new FavBooksAdapter(getActivity(), null, null);
            listViewBooks.setAdapter(favBooksAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpBooks Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpNotes() {
        try {
            favNotesAdapter = new FavNotesAdapter(getActivity());
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
