package com.ism.fragment.myAuthor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.FindMoreAuthorAdapter;
import com.ism.constant.WebConstants;
import com.ism.fragment.MyAuthorFragment;
import com.ism.fragment.myAuthor.authorDesk.AcceptTermAndConditionsFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.AuthorData;

import java.util.ArrayList;

/**
 * Created by c162 on 04/1/16.
 */
public class FindMoreAuthorsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = FindMoreAuthorsFragment.class.getSimpleName();

    private View view;

    private RecyclerView rvMyAuthorList;
    private TextView txtEmptyView;
    private FindMoreAuthorAdapter findMoreAuthorAdapter;
    private HostActivity activityHost;
    private int currentFragment;
    private FragmentListener fragListener;
    private ArrayList<AuthorData> arrListRecommendedAuthors=new ArrayList<>();

    public static FindMoreAuthorsFragment newInstance() {
        FindMoreAuthorsFragment fragReportCard = new FindMoreAuthorsFragment();
        return fragReportCard;
    }

    public FindMoreAuthorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_author, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        rvMyAuthorList = (RecyclerView) view.findViewById(R.id.recyclerview);
        rvMyAuthorList.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        txtEmptyView = (TextView) view.findViewById(R.id.txt_empty_view);
        txtEmptyView.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEmptyView.setText(activityHost.getString(R.string.strNoMoreAuthorsAvailable));

        findMoreAuthorAdapter = new FindMoreAuthorAdapter(this, getActivity());
        rvMyAuthorList.setAdapter(findMoreAuthorAdapter);

        //Utility.showView(txtEmptyView);
        callApiGetRecommendedAuthors();
        onClicks();
    }

    private void callApiGetRecommendedAuthors() {

        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();

                new WebserviceWrapper(getActivity(), new Attribute(), this).new WebserviceCaller()
                        .execute(WebConstants.GET_RECOMMENDED_AUTHORS);
            } catch (Exception e) {
                Utility.alertOffline(getActivity());
            }
        } else {
            Utility.alertOffline(getActivity());
        }

    }

    private void onClicks() {
        try {


        } catch (Exception e) {
            Log.e(TAG, "onCLicks Exceptions : " + e.getLocalizedMessage());
        }
    }

    public void loadFragment(int fragment) {
        try {
            switch (fragment) {
                case MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION:
                    currentFragment = fragment;
                    AcceptTermAndConditionsFragment acceptTermAndConditionsFragment = AcceptTermAndConditionsFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.fl_my_authors, acceptTermAndConditionsFragment).commit();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "loadFragment Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            Log.e(TAG, "onAttach ");
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
            }

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Log.e(TAG, "onDetach");
            fragListener.onFragmentDetached(MyAuthorFragment.FRAGMENT_FIND_MORE_AUTHORS);
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.GET_RECOMMENDED_AUTHORS:
                    onResponseRecommendedAuthors(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseRecommendedAuthors(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

                    if (responseHandler.getAuthor().size() > 0) {

                        arrListRecommendedAuthors.addAll(responseHandler.getAuthor());
                        findMoreAuthorAdapter.addAll(arrListRecommendedAuthors);
                        findMoreAuthorAdapter.notifyDataSetChanged();

                        txtEmptyView.setVisibility(View.GONE);
                    } else {
                        txtEmptyView.setVisibility(View.VISIBLE);
                    }

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Utility.showToast(getActivity(), responseHandler.getMessage());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseRecommendedAuthors api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseRecommendedAuthors Exception : " + e.toString());
        }
    }
}
