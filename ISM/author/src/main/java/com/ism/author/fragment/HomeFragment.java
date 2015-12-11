package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.PostFeedActivity;
import com.ism.author.adapter.PostFeedsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.object.Global;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

/*
* This is the homefragment containg the newsfeed.
*/
public class HomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;
    private ActionProcessButton progress_bar;
    private ProgressGenerator progressGenerator;

    public static HomeFragment newInstance() {
        HomeFragment fragHome = new HomeFragment();
        return fragHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    private LinearLayout llPost;
    private EditText etWritePost;
    private RecyclerView rvPostFeeds;
    private PostFeedsAdapter postFeedsAdapter;
    private OnClickListener onClickAttachFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initGlobal(view);
        return view;
    }

    private void initGlobal(View view) {
        llPost = (LinearLayout) view.findViewById(R.id.ll_post);
        rvPostFeeds = (RecyclerView) view.findViewById(R.id.rv_post_feeds);
        etWritePost = (EditText) view.findViewById(R.id.et_writePost);
        etWritePost.setEnabled(true);

        onClickAttachFile = new OnClickListener() {
            @Override
            public void onClick(View view) {

                onAttachFileClick(view);
            }
        };
        etWritePost.setOnClickListener(onClickAttachFile);
        llPost.setOnClickListener(onClickAttachFile);


        postFeedsAdapter = new PostFeedsAdapter(getActivity());
        rvPostFeeds.setAdapter(postFeedsAdapter);
        rvPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));

        callApiGetAllPostFeeds();


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(AuthorHostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(AuthorHostActivity.FRAGMENT_HOME);
            }
        } catch (ClassCastException e) {
            Log.i(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    private void onAttachFileClick(View view) {

        if (view == llPost || view == etWritePost) {
            Intent intent = new Intent(getActivity(), PostFeedActivity.class);
            startActivity(intent);

        }

    }


    private void callApiGetAllPostFeeds() {
        if (Utility.isConnected(getActivity())) {
            ((AuthorHostActivity) getActivity()).showProgress();
            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETALLFEEDS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


//    String likePrefData, unlikePrefData;
//
//    public void callApiLikeFeed() {
//
//        likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, getActivity(), "");
//        unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, getActivity(), "");
//
//        if (Utils.isInternetConnected(getActivity())) {
//            try {
//                Attribute requestObject = new Attribute();
//                requestObject.setUserId(WebConstants.TEST_LIKEUSERID);
//
//                if (likePrefData.length() > 0) {
//                    requestObject.setLikedId((likePrefData.substring(0, likePrefData.length() - 1)).split(","));
////                    likeFeedRequest.setLiked_id(new String[]{"61"});
//                }
//
//                if (unlikePrefData.length() > 0) {
//                    requestObject.setUnlikedId((unlikePrefData.substring(0, unlikePrefData.length() - 1)).split(","));
////                    likeFeedRequest.setUnliked_id(new String[]{"71", "62"});
//                }
//
//
//                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
//                        .execute(WebserviceWrapper.LIKEFEED);
//            } catch (Exception e) {
//                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
//            }
//        } else {
//            Utils.showToast(getString(R.string.strnetissue), getActivity());
//        }
//
//    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GETALLFEEDS:
                    onResponseGetAllFeeds(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseGetAllFeeds(Object object, Exception error) {
        try {
            ((AuthorHostActivity) getActivity()).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    postFeedsAdapter.addAll(responseHandler.getFeeds());
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utils.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
        }
    }


}
