package com.ism.teacher.fragments.userprofile;

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

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.PostFeedsAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;


/**
 * Created by c162 on 26/10/15.
 */
public class MyFeedsFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = MyFeedsFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private TeacherHostActivity activityHost;
    private PostFeedsAdapter adpPostFeeds;
    private RecyclerView recyclerPostFeeds;
    private LinearLayout llNewPost;
    private TextView tvNoDataMsg;

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
        recyclerPostFeeds = (RecyclerView) view.findViewById(R.id.recycler_post);
        llNewPost = (LinearLayout) view.findViewById(R.id.rl_new_post);
        adpPostFeeds = new PostFeedsAdapter(getActivity());
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
            activityHost = (TeacherHostActivity) activity;
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_MY_FEEDS);
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
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_MY_FEEDS);
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
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (responseHandler.getFeeds().size() > 0) {
                        adpPostFeeds.addAll(responseHandler.getFeeds());
                        setEmptyView(false);
                    } else {
                        setEmptyView(true);
                    }

                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
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
