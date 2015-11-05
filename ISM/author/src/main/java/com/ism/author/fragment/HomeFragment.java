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
import android.widget.Toast;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.PostActivity;
import com.ism.author.adapter.PostFeedsAdapter;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.TagUserDialog;
import com.ism.author.dialog.ViewAllCommentsDialog;
import com.ism.author.interfaces.FragmentListener;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;

import java.util.ArrayList;

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

    LinearLayout llPost;
    EditText etWritePost;

    RecyclerView rvPostFeeds;
    PostFeedsAdapter postFeedsAdapter;

    OnClickListener onClickAttachFile;


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


        postFeedsAdapter = new PostFeedsAdapter(this, getActivity());
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
            Intent intent = new Intent(getActivity(), PostActivity.class);
            startActivity(intent);

        }

    }


    private void callApiGetAllPostFeeds() {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setUserId(WebConstants.TEST_USER_ID);
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETALLFEEDS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    public void callApiGetStudyMates() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setUserId(WebConstants.TEST_GETSTUDYMATES);
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSTUDYMATES);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    public RequestObject tagFriendInFeedRequest = new RequestObject();

    public void callApiTagFriendInFeed() {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), tagFriendInFeedRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.TAGFRIENDINFEED);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    String likePrefData, unlikePrefData;

    public void callApiLikeFeed() {

        likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, getActivity(), "");
        unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, getActivity(), "");

        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setUserId(WebConstants.TEST_LIKEUSERID);

                if (likePrefData.length() > 0) {
                    requestObject.setLikedId((likePrefData.substring(0, likePrefData.length() - 1)).split(","));
//                    likeFeedRequest.setLiked_id(new String[]{"61"});
                }

                if (unlikePrefData.length() > 0) {
                    requestObject.setUnlikedId((unlikePrefData.substring(0, unlikePrefData.length() - 1)).split(","));
//                    likeFeedRequest.setUnliked_id(new String[]{"71", "62"});
                }


                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.LIKEFEED);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    ResponseObject responseObj;

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {
        try {
            if (apiMethodName == WebserviceWrapper.GETALLFEEDS) {

                responseObj = (ResponseObject) object;
                if (responseObj.getStatus().equals(WebConstants.STATUS_SUCCESS) && responseObj != null) {
                    postFeedsAdapter.addAll(responseObj.getData());
                } else {
                    Utils.showToast(responseObj.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.GETSTUDYMATES) {

                ResponseObject getStudyMatesResponseObject = (ResponseObject) object;
                if (getStudyMatesResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && getStudyMatesResponseObject != null) {
                    if (responseObj.getData().size() > 0) {
                        openTagUserDialog(getStudyMatesResponseObject.getData());
                    }
                } else {
                    Utils.showToast(getStudyMatesResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.TAGFRIENDINFEED) {

                ResponseObject tagFriendInFeedResponseObject = (ResponseObject) object;
                if (tagFriendInFeedResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && tagFriendInFeedResponseObject != null) {
                    Log.i(TAG, "The message is::" + tagFriendInFeedResponseObject.getMessage());
                    Toast.makeText(getActivity(), tagFriendInFeedResponseObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), tagFriendInFeedResponseObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {

            Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }


    private void openTagUserDialog(ArrayList<Data> data) {

        TagUserDialog tagUserDialog = new TagUserDialog(getActivity(), HomeFragment.this, data);
        tagUserDialog.show();

    }


    private void openViewAllCommentsDialog(ArrayList<Data> data) {

        ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), data);
        viewAllCommentsDialog.show();

    }



}
