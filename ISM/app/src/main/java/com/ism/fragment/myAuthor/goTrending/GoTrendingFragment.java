package com.ism.fragment.myAuthor.goTrending;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.GoTrendingQueAnsAuthorAdapter;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.views.CirclePageIndicator;

/**
 * Created by c162 on 07/01/16.
 */
public class GoTrendingFragment extends Fragment {

    private FragmentListener fragmentListener;
    private HostActivity activityHost;
    private View view;
    private ViewPager viewpager;
    private GoTrendingQueAnsAuthorAdapter authorAdapter ;
    private CirclePageIndicator indicator;

    public static GoTrendingFragment newInstance() {
        GoTrendingFragment fragment = new GoTrendingFragment();
        return fragment;
    }

    public GoTrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_go_trending, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        authorAdapter=new GoTrendingQueAnsAuthorAdapter(getActivity());
        viewpager.setAdapter(authorAdapter);

        indicator=(CirclePageIndicator)view.findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
       // viewpager.setAdapter(new Ima);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentListener = (FragmentListener) activity;
        activityHost = (HostActivity) activity;
        if (fragmentListener != null)
            fragmentListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_GO_TRENDING);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (fragmentListener != null)
            fragmentListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_GO_TRENDING);
    }
}
