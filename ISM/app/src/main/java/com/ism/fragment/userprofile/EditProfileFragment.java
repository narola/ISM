package com.ism.fragment.userprofile;

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
import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;

/**
 * Created by c161 on --/10/15.
 */
public class EditProfileFragment extends Fragment {

    private static final String TAG = EditProfileFragment.class.getSimpleName();
    private static final int FRAGMENT_ABOUT_ME = 0;
    private static final int FRAGMENT_BOOKS = 1;
    private static final int FRAGMENT_MOVIES = 2;
    private static final int FRAGMENT_ROLE_MODELS = 3;
    private static final int FRAGMENT_PASTTIME = 4;

    private View view;

    private FragmentListener fragListener;
    private TextView txtAboutMe, txtBooks, txtMovies, txtRoleModels, txtPastTime;
    private int currentFragment = -1;
    private HostActivity activityHost;
    MyTypeFace myTypeFace;

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragChat = new EditProfileFragment();
        return fragChat;
    }

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        txtAboutMe = (TextView) view.findViewById(R.id.txt_about_me);
        txtBooks = (TextView) view.findViewById(R.id.txt_books);
        txtMovies = (TextView) view.findViewById(R.id.txt_movies);
        txtRoleModels = (TextView) view.findViewById(R.id.txt_role_models);
        txtPastTime = (TextView) view.findViewById(R.id.txt_pasttime);
        myTypeFace = new MyTypeFace(getActivity());
        txtAboutMe.setTypeface(myTypeFace.getRalewayRegular());
        txtBooks.setTypeface(myTypeFace.getRalewayRegular());
        txtMovies.setTypeface(myTypeFace.getRalewayRegular());
        txtRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtPastTime.setTypeface(myTypeFace.getRalewayRegular());
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
                    case R.id.txt_movies: {
                        loadFragment(FRAGMENT_MOVIES);
                    }
                    break;
                    case R.id.txt_role_models: {
                        Debug.i(TAG, "loadFragment(FRAGMENT_ROLE_MODELS): " + FRAGMENT_ROLE_MODELS);
                        loadFragment(FRAGMENT_ROLE_MODELS);
                    }
                    break;
                    case R.id.txt_pasttime: {
                        Debug.i(TAG, "loadFragment(FRAGMENT_PASTTIME):" + FRAGMENT_PASTTIME);
                        loadFragment(FRAGMENT_PASTTIME);
                    }
                    break;

                }
                selected(v);
            }
        };
        loadFragment(FRAGMENT_ABOUT_ME);
        txtAboutMe.setOnClickListener(onClick);
        txtBooks.setOnClickListener(onClick);
        txtMovies.setOnClickListener(onClick);
        txtRoleModels.setOnClickListener(onClick);
        txtPastTime.setOnClickListener(onClick);
    }

    private void selected(View v) {
        txtAboutMe.setEnabled(true);
        txtBooks.setEnabled(true);
        txtMovies.setEnabled(true);
        txtRoleModels.setEnabled(true);
        txtPastTime.setEnabled(true);
        v.setEnabled(false);

    }

    private void loadFragment(int frag) {
        switch (frag) {
            case FRAGMENT_ABOUT_ME: {
                currentFragment = frag;
                AboutMeFragment fragment = AboutMeFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_BOOKS: {
                currentFragment = frag;
                UserBooksFragment fragment = UserBooksFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_MOVIES: {
                currentFragment = frag;
                UserMoviesFragment fragment = UserMoviesFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_ROLE_MODELS: {
                Debug.i(TAG, "call for UserRoleModelFragment ");
                currentFragment = frag;
                UserRoleModelFragment fragment = UserRoleModelFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
            case FRAGMENT_PASTTIME: {
                Debug.i(TAG, "call for UserPastTimeFragment ");
                currentFragment = frag;
                UserPastTimeFragment fragment = UserPastTimeFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment).commit();
            }
            break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(HostActivity.FRAGMENT_CHAT);
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
                fragListener.onFragmentDetached(HostActivity.FRAGMENT_CHAT);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

}
