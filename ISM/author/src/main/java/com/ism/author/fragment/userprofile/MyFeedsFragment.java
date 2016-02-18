package com.ism.author.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.adapter.PostFeedsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;

import realmhelper.AuthorHelper;

/**
 * Created by c162 on 26/10/15.
 */
public class MyFeedsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = MyFeedsFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private AuthorHostActivity activityHost;
    private PostFeedsAdapter adpPostFeeds;
    private RecyclerView recyclerPostFeeds;
    private LinearLayout llNewPost;
    private TextView tvNoDataMsg;
    private AuthorHelper authorHelper;

    public static MyFeedsFragment newInstance() {
        MyFeedsFragment fragBooks = new MyFeedsFragment();
        return fragBooks;
    }

    public MyFeedsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myfeeds, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        authorHelper = new AuthorHelper(getActivity());
        recyclerPostFeeds = (RecyclerView) view.findViewById(R.id.recycler_post);
        llNewPost = (LinearLayout) view.findViewById(R.id.rl_new_post);
        adpPostFeeds = new PostFeedsAdapter(getActivity(), authorHelper);
        recyclerPostFeeds.setAdapter(adpPostFeeds);
        recyclerPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 10;
                outRect.right = 10;
                outRect.left = 10;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = 10;
                }
            }
        };
        recyclerPostFeeds.addItemDecoration(itemDecoration);

        if (Utility.isConnected(getActivity())) {
            callApiGetMyFeeds();
        } else {
            Utility.alertOffline(getActivity());
        }

        llNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Start post activity
            }
        });

        tvNoDataMsg = (TextView) view.findViewById(R.id.tv_no_data_msg);

        setEmptyView(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (AuthorHostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_MY_FEEDS);
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
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_MY_FEEDS);
            }
        } catch (ClassCastException e) {
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void callApiGetMyFeeds() {
        try {
            activityHost.showProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(Global.strUserId);

            new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                    .execute(WebConstants.GET_MY_FEEDS);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetMyFeeds Exception : " + e.toString());
        }
    }


    private void onResponseGetMyFeeds(Object object, Exception error) {

        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {


                    if (responseHandler.getFeeds().size() > 0) {
//                        adpPostFeeds.addAll(responseHandler.getFeeds());
                        setEmptyView(false);
                    } else {
                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetMyFeeds Failed : " + responseHandler.getMessage());
                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetMyFeeds apiCall Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetMyFeeds Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_MY_FEEDS:
                    onResponseGetMyFeeds(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getString(R.string.no_user_post));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        recyclerPostFeeds.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }
}
