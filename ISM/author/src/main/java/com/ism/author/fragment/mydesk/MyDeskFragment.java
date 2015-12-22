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

/**
 * Created by c162 on 28/10/15.
 */
public class MyDeskFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MyDeskFragment.class.getSimpleName();

    public static final int FRAGMENT_ABOUT_ME = 0;
    public static final int FRAGMENT_BOOKS = 1;
    public static final int FRAGMENT_ASSIGNMENTS = 2;
    public static final int FRAGMENT_BOOKREFERENCE = 3;

    private View view;
    private FragmentListener fragListener;
    private TextView txtAboutMe, txtBooks, txtAssignments;
    private int currentFragment = -1;
    private TextView txtAdd;

    public static MyDeskFragment newInstance() {
        MyDeskFragment myDeskFragment = new MyDeskFragment();
        return myDeskFragment;
    }

    public MyDeskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mydesk, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {

        txtAboutMe = (TextView) view.findViewById(R.id.txt_about_me);
        txtAdd = (TextView) view.findViewById(R.id.txt_add);
        txtBooks = (TextView) view.findViewById(R.id.txt_books);
        txtAssignments = (TextView) view.findViewById(R.id.txt_assignments);

        txtAboutMe.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAdd.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBooks.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAssignments.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtAboutMe.setEnabled(false);

        txtAboutMe.setOnClickListener(this);
        txtBooks.setOnClickListener(this);
        txtAssignments.setOnClickListener(this);
        txtAdd.setOnClickListener(this);

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
                txtAdd.setVisibility(View.GONE);
                AboutMeFragment aboutMeFragment = AboutMeFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, aboutMeFragment).commit();
                break;

            case FRAGMENT_BOOKS:

                currentFragment = frag;
                txtAdd.setVisibility(View.GONE);
                MyDeskBooksFragment myDeskBooksFragment = MyDeskBooksFragment.newInstance();
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, myDeskBooksFragment).commit();
                break;

            case FRAGMENT_ASSIGNMENTS:

                currentFragment = frag;
                txtAdd.setVisibility(View.VISIBLE);
                MyDeskAssignmentsFragment myDeskAssignmentsFragment = MyDeskAssignmentsFragment.newInstance(this);
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, myDeskAssignmentsFragment).commit();
                break;

            case FRAGMENT_BOOKREFERENCE:

                currentFragment = frag;
                txtAdd.setVisibility(View.VISIBLE);
                BookReferenceFragment bookReferenceFragment = BookReferenceFragment.newInstance(this);
                getChildFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, bookReferenceFragment).commit();
                break;

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_MY_DESK);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_MY_DESK);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    public void onBackClick() {

        if (currentFragment == FRAGMENT_BOOKREFERENCE) {

            loadFragment(FRAGMENT_ASSIGNMENTS);

        } else {
            ((AuthorHostActivity) getActivity()).handleBackClick(AppConstant.FRAGMENT_MYDESK);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_about_me:
                loadFragment(FRAGMENT_ABOUT_ME);

                break;
            case R.id.txt_books:
                loadFragment(FRAGMENT_BOOKS);

                break;
            case R.id.txt_assignments:
                loadFragment(FRAGMENT_ASSIGNMENTS);

                break;
            case R.id.txt_add:
                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ADD_ASSIGNMENT);

                break;
        }
        selected(v);
    }
}
