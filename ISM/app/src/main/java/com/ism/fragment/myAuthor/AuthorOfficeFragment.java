package com.ism.fragment.myAuthor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.AuthorOfficeTabGridAdapter;
import com.ism.fragment.MyAuthorFragment;
import com.ism.fragment.myAuthor.authorDesk.AuthorDeskFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.model.AuthorOfficeTabDataSet;
import com.ism.utility.Debug;

/**
 * Created by c162 on 01/01/16.
 */
public class AuthorOfficeFragment extends Fragment {


    private static final String TAG = AuthorOfficeFragment.class.getSimpleName();
    private View view;
    private FragmentListener fragListener;

    public static int CURRENT_OFFICE_FRAGMENT = 0;

    GridView gvOfficetab;
    AuthorOfficeTabGridAdapter officeTabGridAdapter;
    AuthorOfficeTabDataSet officeTabDataSet = new AuthorOfficeTabDataSet();
    private HostActivity activityHost;
    private int currentFragment;
    private MyAuthorFragment myAuthorFragment;


    public static AuthorOfficeFragment newInstance() {
        AuthorOfficeFragment fragOffice = new AuthorOfficeFragment();
        return fragOffice;
    }

    public AuthorOfficeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_author_home, container, false);

        initGlobal();
        return view;

    }

    private void initGlobal() {
        myAuthorFragment = MyAuthorFragment.newInstance();

        officeTabDataSet.setOfficeTabTitleList(new String[]{getActivity().getString(R.string.strAuthorsDesk), getString(R.string.strgotrending), getString(R.string.strTrial),
                getString(R.string.strmy30th), getString(R.string.strassessment)});
        officeTabDataSet.setOfficeTabInfoList(new String[]{getString(R.string.strmydeskinfo), getString(R.string.strgotrendinginfo),
                getString(R.string.strtrialinfo), getString(R.string.strmy30thinfo), getString(R.string.strassessmentinfo)});
        officeTabDataSet.setOfficeTabTitleImages(new Integer[]{R.drawable.my_desk_title, R.drawable.go_trending_title, R.drawable.trial_title,
                R.drawable.my_30th_title, R.drawable.assessment_title});
        officeTabDataSet.setOfficeTabInfoImages(new Integer[]{R.drawable.my_desk_logo, R.drawable.go_trending_logo, R.drawable.trial_logo,
                R.drawable.my_30th_logo, R.drawable.assessment_logo});


        gvOfficetab = (GridView) view.findViewById(R.id.gv_officetab);
        officeTabGridAdapter = new AuthorOfficeTabGridAdapter(getActivity(), officeTabDataSet, this);
        gvOfficetab.setAdapter(officeTabGridAdapter);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            Log.e(TAG, "onAttach Attached fragment ");
            fragListener = (FragmentListener) activity;
            activityHost = (HostActivity) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach");
            if (fragListener != null) {
                fragListener.onFragmentDetached(MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    public void loadFragment(int fragment) {

        switch (fragment) {

            case MyAuthorFragment.FRAGMENT_AUTHOR_DESK:
                currentFragment = fragment;
                AuthorDeskFragment authorDeskFragment = AuthorDeskFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, authorDeskFragment).commit();
                //activityHost.loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_MY_DESK);
                break;

            case MyAuthorFragment.FRAGMENT_GOTRENDING:
                currentFragment = fragment;
                // ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_GOTRENDING);
                break;

            case MyAuthorFragment.FRAGMENT_TRIAL:
                currentFragment = fragment;
                //  ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
                break;

            case MyAuthorFragment.FRAGMENT_MYTHIRTY:
                currentFragment = fragment;
                //  ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL);
                break;

            case MyAuthorFragment.FRAGMENT_AUTHOR_ASSESSMENT:
                currentFragment = fragment;
                // ((AuthorHostActivity) getActivity()).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_ASSESSMENT);
                break;
        }
    }

    public void onTopControllerBackClick(int position) {
        loadFragment(currentFragment);
    }
}
