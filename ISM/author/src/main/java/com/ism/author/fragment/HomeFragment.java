package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.ism.author.model.AddCommentRequest;
import com.ism.author.model.Data;
import com.ism.author.model.GetAllCommentRequest;
import com.ism.author.model.GetAllFeedsRequest;
import com.ism.author.model.LikeFeedRequest;
import com.ism.author.model.ResponseObject;
import com.ism.author.model.TagFriendInFeedRequest;
import com.ism.author.ws.WebserviceWrapper;

import java.util.ArrayList;

/*
* This is the homefragment containg the newsfeed.
*/
public class HomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View view;

    private FragmentListener fragListener;

    public static HomeFragment newInstance() {
        HomeFragment fragHome = new HomeFragment();
        return fragHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    ImageView imgAddEmoticonsInPost, imgAttachFileInPost, imgAttachImageInPost, imgTagInPost;
    LinearLayout llPost;
    EditText etWritePost;
//    ListView lvPostFeeds;
//    PostFeedsListAdapter postFeedsAdapter;

    RecyclerView rvPostFeeds;
    PostFeedsAdapter postFeedsAdapter;

    OnClickListener onClickAttachFile, onClickAddPost;


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
        callGetAllPostFeeds();

        postFeedsAdapter = new PostFeedsAdapter(this, getActivity());
        rvPostFeeds.setAdapter(postFeedsAdapter);
        rvPostFeeds.setLayoutManager(new LinearLayoutManager(getActivity()));


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
//            callLikeFeed();
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

//    public void postFeed() {
//        Intent intent = new Intent(getActivity(), PostActivity.class);
//        startActivity(intent);
//    }

    private void callGetAllPostFeeds() {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                GetAllFeedsRequest getAllFeedsRequest = new GetAllFeedsRequest();
//                getAllFeedsRequest.setUser_id("141");
                getAllFeedsRequest.setUser_id(WebConstants.TEST_USER_ID);
                new WebserviceWrapper(getActivity(), getAllFeedsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETALLFEEDS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }

    public void callGetAllComments(GetAllCommentRequest getAllCommentRequest) {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), getAllCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETALLCOMMENTS);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }

    public void callAddComment(AddCommentRequest addCommentRequest) {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), addCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.ADDCOMMENT);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }

    public void callGetStudyMates() {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                GetAllFeedsRequest getAllFeedsRequest = new GetAllFeedsRequest();
//                getAllFeedsRequest.setUser_id("167");
                getAllFeedsRequest.setUser_id(WebConstants.TEST_GETSTUDYMATES);
                new WebserviceWrapper(getActivity(), getAllFeedsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSTUDYMATES);
            } catch (Exception e) {
                Log.i(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    public TagFriendInFeedRequest tagFriendInFeedRequest = new TagFriendInFeedRequest();

    public void callTagFriendInFeed() {
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

    public void callLikeFeed() {


        likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, getActivity(), "");
        unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, getActivity(), "");


        if (Utils.isInternetConnected(getActivity())) {
            try {
                LikeFeedRequest likeFeedRequest = new LikeFeedRequest();
                likeFeedRequest.setUser_id(WebConstants.TEST_LIKEUSERID);

                if (likePrefData.length() > 0) {
                    likeFeedRequest.setLiked_id((likePrefData.substring(0, likePrefData.length() - 1)).split(","));
//                    likeFeedRequest.setLiked_id(new String[]{"61"});
                }

                if (unlikePrefData.length() > 0) {
                    likeFeedRequest.setUnliked_id((unlikePrefData.substring(0, unlikePrefData.length() - 1)).split(","));
//                    likeFeedRequest.setUnliked_id(new String[]{"71", "62"});
                }


                new WebserviceWrapper(getActivity(), likeFeedRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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

            } else if (apiMethodName == WebserviceWrapper.GETALLCOMMENTS) {

                ResponseObject getAllCommentsResponseObject = (ResponseObject) object;
                if (getAllCommentsResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && getAllCommentsResponseObject != null) {
                    Log.i(TAG, "The message is::" + getAllCommentsResponseObject.getMessage());
                    openViewAllCommentsDialog(getAllCommentsResponseObject.getData());
                } else {
                    Utils.showToast(getAllCommentsResponseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.ADDCOMMENT) {

                ResponseObject addCommentResponseObject = (ResponseObject) object;
                if (addCommentResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && addCommentResponseObject != null) {
                    Utils.showToast(addCommentResponseObject.getMessage(), getActivity());

                    updatePostFeedViewAfterAddComment();
                } else {
                    Utils.showToast(addCommentResponseObject.getMessage(), getActivity());
                }


            } else if (apiMethodName == WebserviceWrapper.GETSTUDYMATES) {

                ResponseObject getStudyMatesResponseObject = (ResponseObject) object;
                if (getStudyMatesResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && getStudyMatesResponseObject != null) {
                    Log.i(TAG, "The message is::" + getStudyMatesResponseObject.getMessage());
                    openTagUserDialog(getStudyMatesResponseObject.getData());
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


    int setAddCommentRowPosition;

    public int getSetAddCommentRowPosition() {
        return setAddCommentRowPosition;
    }

    public void setSetAddCommentRowPosition(int setAddCommentRowPosition) {
        this.setAddCommentRowPosition = setAddCommentRowPosition;
    }

    private void updatePostFeedViewAfterAddComment() {

        int position = getSetAddCommentRowPosition();
        responseObj.getData().get(position).setTotalComment(String.valueOf(Integer.parseInt(responseObj.getData().get(position).getTotalComment()) + 1));
        View v = rvPostFeeds.getChildAt(position);
        EditText etWriteComment = (EditText) v.findViewById(R.id.et_writeComment);
        etWriteComment.setText("");
        postFeedsAdapter.notifyDataSetChanged();


    }


}
