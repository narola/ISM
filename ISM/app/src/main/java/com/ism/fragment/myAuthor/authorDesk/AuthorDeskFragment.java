package com.ism.fragment.myAuthor.authorDesk;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;

/**
 * Created by c162 on 1/1/2016.
 */
public class AuthorDeskFragment extends Fragment {

    private static final String TAG = AuthorDeskFragment.class.getSimpleName();

    public static final int FRAGMENT_ABOUT_ME = 0;
    public static final int FRAGMENT_BOOKS = 1;
    public static final int FRAGMENT_ASSIGNMENTS = 2;
    public static final int FRAGMENT_BOOKASSIGNMENT = 3;


    private View view;
    private FragmentListener fragListener;
    private TextView txtAboutMe, txtBooks, txtAssignments;
    private int currentFragment;
    private HostActivity activityHost;

    public static AuthorDeskFragment newInstance() {
        AuthorDeskFragment myDeskFragment = new AuthorDeskFragment();
        return myDeskFragment;
    }

    public AuthorDeskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_desk, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {
        txtAboutMe = (TextView) view.findViewById(R.id.txt_about_me);
        txtBooks = (TextView) view.findViewById(R.id.txt_books);
        txtAssignments = (TextView) view.findViewById(R.id.txt_assignments);

        txtAboutMe.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssignments.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAboutMe.setEnabled(false);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.txt_about_me: {
                        loadFragment(FRAGMENT_ABOUT_ME);
                    }
                    break;
                    case R.id.txt_books: {
                        loadFragment(FRAGMENT_BOOKS);
                    }
                    break;
                    case R.id.txt_assignments: {
                        loadFragment(FRAGMENT_ASSIGNMENTS);
                    }
                    break;
                }
                selected(v);
            }
        };
//       handleBackClick(AppConstant.FRAGMENT_ASSIGNMENTS);
        loadFragment(FRAGMENT_ABOUT_ME);
        txtAboutMe.setOnClickListener(onClick);
        txtBooks.setOnClickListener(onClick);
        txtAssignments.setOnClickListener(onClick);
    }

    /*This is to handle backstack for particular fragment */

    public void handleBackClick(String fragmentName) {
        if (activityHost.getBundle().containsKey(fragmentName)) {
            loadFragment(activityHost.getBundle().getInt(fragmentName));
            activityHost.getBundle().remove(fragmentName);
            selected(txtAssignments);
        } else
            loadFragment(FRAGMENT_ABOUT_ME);
    }

    private void selected(View v) {
        txtAboutMe.setEnabled(true);
        txtBooks.setEnabled(true);
        txtAssignments.setEnabled(true);
        v.setEnabled(false);

    }

    public void loadFragment(int frag) {
        switch (frag) {
            case FRAGMENT_ABOUT_ME:
                currentFragment = frag;
                AboutAuthorFragment aboutMeFragment = AboutAuthorFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, aboutMeFragment).commit();
                break;

            case FRAGMENT_BOOKS:
                currentFragment = frag;
                AuthorDeskBooksFragment myDeskBooksFragment = AuthorDeskBooksFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, myDeskBooksFragment).commit();
                break;

            case FRAGMENT_ASSIGNMENTS:
                currentFragment = frag;
                AuthorDeskAssignmentsFragment myDeskAssignmentsFragment = AuthorDeskAssignmentsFragment.newInstance(this);
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, myDeskAssignmentsFragment).commit();
                break;

            case FRAGMENT_BOOKASSIGNMENT:
                currentFragment = frag;
                BookAssignmentsFragment bookAssignmentsFragment = BookAssignmentsFragment.newInstance(this);
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, bookAssignmentsFragment).commit();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            Log.e(TAG, "onAttach Attached fragment ");
            activityHost = (HostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_AUTHOR_DESK);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach fragment ");
            if (fragListener != null) {
                fragListener.onFragmentDetached(MyAuthorFragment.FRAGMENT_AUTHOR_DESK);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }
}
