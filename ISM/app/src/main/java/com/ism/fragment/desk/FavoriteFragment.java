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
import android.widget.ScrollView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.deskAdapter.FavAssignmentsAdapter;
import com.ism.adapter.deskAdapter.FavBooksAdapter;
import com.ism.adapter.deskAdapter.FavEventsAdapter;
import com.ism.adapter.deskAdapter.FavExamsAdapter;
import com.ism.adapter.deskAdapter.FavLinksAdapter;
import com.ism.adapter.deskAdapter.FavNotesAdapter;
import com.ism.fragment.DeskFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;

/**
 * Created by c162 on 24/10/15.
 */
public class FavoriteFragment extends Fragment implements HostActivity.HostListenerItemChange {
    private static final String TAG = FavoriteFragment.class.getSimpleName();
    private View view;

//    public static final int FRAGMENT_ALL_BOOKS = 101;
//    public static final int FRAGMENT_ALL_NOTES = 102;
//    public static final int FRAGMENT_ALL_LINKS = 103;
//    public static final int FRAGMENT_ALL_EVENTS = 104;
//    public static final int FRAGMENT_ALL_ASSIGNMENTS = 105;
//    public static final int FRAGMENT_ALL_EXAMS = 106;

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
    private FavEventsAdapter favEventsAdapter;
    private ScrollView scrollView;
    private View.OnClickListener onClickItem;
    private DeskFragment deskFragment;

    public static int currentFragment = -1;

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
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

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
        txtEvents.setBackgroundColor(getResources().getColor(R.color.color_green_events));

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
        includeLinks = view.findViewById(R.id.include_links);

        txtLinks = (TextView) includeLinks.findViewById(R.id.txt_fav_item);
        txtLinks.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtLinks.setText(R.string.links);
        txtLinks.setBackgroundColor(getResources().getColor(R.color.color_orange));

        txtViewAllLinks = (TextView) includeLinks.findViewById(R.id.txt_view_all);
        txtViewAllLinks.setTypeface(Global.myTypeFace.getRalewayRegular());

        listViewLinks = (RecyclerView) includeLinks.findViewById(R.id.recyclerview);
        listViewLinks.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        setUpNotes();
        setUpBooks();
        setUpAssignments();
        setUpExams();
        setUpLinks();
        setUpEvents();
        onClickItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem(v);
            }
        };
        txtViewAllNotes.setOnClickListener(onClickItem);
        txtViewAllLinks.setOnClickListener(onClickItem);
        txtViewAllBooks.setOnClickListener(onClickItem);
        txtViewAllEvents.setOnClickListener(onClickItem);
        txtViewAllExmas.setOnClickListener(onClickItem);
        txtViewAllAssignment.setOnClickListener(onClickItem);
    }


    private void onClickItem(View v) {
        try {
            if (txtViewAllNotes == v) {

            } else if (txtViewAllBooks == v) {
                loadFragment(DeskFragment.FRAGMENT_ALL_BOOKS);
            } else if (txtViewAllAssignment == v) {
                loadFragment(DeskFragment.FRAGMENT_ALL_ASSIGNMENTS);
            } else if (txtViewAllExmas == v) {
                loadFragment(DeskFragment.FRAGMENT_ALL_EXAMS);
            }
        } catch (Exception e) {
            Debug.i(TAG, "onClickItem Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void loadFragment(int fragment) {
        switch (fragment) {
            case DeskFragment.FRAGMENT_ALL_BOOKS:
                currentFragment = fragment;
                activityHost.onChildFragmentAttached(false);
                activityHost.onSetPositionSpinner(2);
                deskFragment.setCurrentFragment(fragment);
                getFragmentManager().beginTransaction().replace(R.id.fl_desk, AllBooksFragment.newInstance()).commit();
                break;
            case DeskFragment.FRAGMENT_ALL_ASSIGNMENTS:
                currentFragment = fragment;
                deskFragment.setCurrentFragment(fragment);
                activityHost.onChildFragmentAttached(false);
                activityHost.onSetPositionSpinner(3);
                getFragmentManager().beginTransaction().replace(R.id.fl_desk, AllAssignmentsFragment.newInstance()).commit();
                break;
            case DeskFragment.FRAGMENT_ALL_EXAMS:
                currentFragment = fragment;
                deskFragment.setCurrentFragment(fragment);
                activityHost.onChildFragmentAttached(false);
                activityHost.onSetPositionSpinner(4);
                getFragmentManager().beginTransaction().replace(R.id.fl_desk, AllExamsFragment.newInstance()).commit();
                break;
        }
    }

    private void setUpEvents() {
        try {
            favEventsAdapter = new FavEventsAdapter(getActivity());
            listViewEvents.setAdapter(favEventsAdapter);
//            listViewLinks.setAdapter(linksAdapter);
        } catch (Exception e) {
            Debug.i(TAG, "setUpEvents Exceptions : " + e.getLocalizedMessage());
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
            activityHost.onChildFragmentAttached(false);
//            activityHost.onSetPositionSpinner(0);
            deskFragment = DeskFragment.newInstance(currentFragment);
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onControllerTopItemChange(int position) {
        switch (position){
//            case 1:
//                deskFragment.loadFragment( DeskFragment.FRAGMENT_FAVOURITES);
//                break;
            case 1:
                loadFragment( DeskFragment.FRAGMENT_ALL_NOTES);
                break;
            case 2:
                loadFragment( DeskFragment.FRAGMENT_ALL_BOOKS);
                break;
            case 3:
                loadFragment( DeskFragment.FRAGMENT_ALL_ASSIGNMENTS);
                break;
            case 4:
                loadFragment( DeskFragment.FRAGMENT_ALL_EXAMS);
                break;
            case 5:
                loadFragment( DeskFragment.FRAGMENT_ALL_LINKS);
                break;
            case 6:
                loadFragment( DeskFragment.FRAGMENT_ALL_EVENTS);
                break;
        }
    }

}