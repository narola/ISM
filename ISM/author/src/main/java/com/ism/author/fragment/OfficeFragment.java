package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.adapter.OfficeTabGridAdapter;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.OfficeTabDataSet;


/**
 * this fragment is to handle the office tab events.
 */
public class OfficeFragment extends Fragment {


    private static final String TAG = OfficeFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static final int FRAGMENT_MY_DESK = 0;
    public static final int FRAGMENT_GOTRENDING = 1;
    public static final int FRAGMENT_TRIAL = 2;
    public static final int FRAGMENT_MYTHIRTY = 3;
    public static final int FRAGMENT_ASSESSMENT = 4;
    public static int CURRENT_OFFICE_FRAGMENT = 0;

    GridView gvOfficetab;
    OfficeTabGridAdapter officeTabGridAdapter;
    OfficeTabDataSet officeTabDataSet = new OfficeTabDataSet();


    public static OfficeFragment newInstance() {
        OfficeFragment fragOffice = new OfficeFragment();
        return fragOffice;
    }

    public OfficeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_office, container, false);

        initGlobal();
        return view;

    }

    private void initGlobal() {


        officeTabDataSet.setOfficeTabTitleList(new String[]{getString(R.string.strmydesk), getString(R.string.strgotrending), getString(R.string.strTrial),
                getString(R.string.strmy30th), getString(R.string.strassessment)});
        officeTabDataSet.setOfficeTabInfoList(new String[]{getString(R.string.strmydeskinfo), getString(R.string.strgotrendinginfo),
                getString(R.string.strtrialinfo), getString(R.string.strmy30thinfo), getString(R.string.strassessmentinfo)});
        officeTabDataSet.setOfficeTabTitleImages(new Integer[]{R.drawable.my_desk_title, R.drawable.go_trending_title, R.drawable.trial_title,
                R.drawable.my_30th_title, R.drawable.assessment_title});
        officeTabDataSet.setOfficeTabInfoImages(new Integer[]{R.drawable.my_desk_logo, R.drawable.go_trending_logo, R.drawable.trial_logo,
                R.drawable.my_30th_logo, R.drawable.assessment_logo});


        gvOfficetab = (GridView) view.findViewById(R.id.gv_officetab);
        officeTabGridAdapter = new OfficeTabGridAdapter(getActivity(), officeTabDataSet, this);
        gvOfficetab.setAdapter(officeTabGridAdapter);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_OFFICE);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_OFFICE);
                Debug.i(TAG, "detach");
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void loadFragment(int fragment) {

        CURRENT_OFFICE_FRAGMENT = fragment;
        switch (fragment) {

            case FRAGMENT_MY_DESK:
                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_DESK);
                break;

            case FRAGMENT_GOTRENDING:
                break;

            case FRAGMENT_TRIAL:
                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
                break;

            case FRAGMENT_MYTHIRTY:
                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
                break;

            case FRAGMENT_ASSESSMENT:
                ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                break;


        }
    }

}
