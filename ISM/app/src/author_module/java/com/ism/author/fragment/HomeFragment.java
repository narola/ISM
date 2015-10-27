package com.ism.author.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.author.AuthorHostActivity;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.PostFeedsAdapter;
import com.ism.author.dialog.TagUserDialog;
import com.ism.author.dialog.ViewAllCommentsDialog;
import com.ism.author.login.Urls;
import com.ism.author.model.AddCommentRequest;
import com.ism.author.model.Data;
import com.ism.author.model.GetAllCommentRequest;
import com.ism.author.model.GetAllFeedsRequest;
import com.ism.author.model.ResponseObject;
import com.ism.author.model.TagFriendInFeedRequest;
import com.ism.author.ws.WebserviceWrapper;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

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
    EditText etWritePost;
    TextView txtAddPost;

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

        imgAddEmoticonsInPost = (ImageView) view.findViewById(R.id.img_add_emoticons_in_post);
        imgAttachFileInPost = (ImageView) view.findViewById(R.id.img_attach_file_in_post);
        imgAttachImageInPost = (ImageView) view.findViewById(R.id.img_attach_image_in_post);
        imgTagInPost = (ImageView) view.findViewById(R.id.img_tag_in_post);

        etWritePost = (EditText) view.findViewById(R.id.et_writePost);
        txtAddPost = (TextView) view.findViewById(R.id.txt_add_post);

//        lvPostFeeds = (ListView) view.findViewById(R.id.lv_post_feeds);
        rvPostFeeds = (RecyclerView) view.findViewById(R.id.rv_post_feeds);


        onClickAttachFile = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAttachFileClick(view);


            }
        };

        imgAddEmoticonsInPost.setOnClickListener(onClickAttachFile);
        imgAttachFileInPost.setOnClickListener(onClickAttachFile);
        imgAttachImageInPost.setOnClickListener(onClickAttachFile);
        imgTagInPost.setOnClickListener(onClickAttachFile);

        onClickAddPost = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAddPostClick(view);


            }
        };

        callGetAllPostFeeds();

        postFeedsAdapter = new PostFeedsAdapter(this, viewAllCommetsListener, getActivity());
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
            Debug.e(TAG, "onAttach Exception : " + e.toString());
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
            Debug.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    private void onAddPostClick(View view) {

        if (view == txtAddPost) {

        }


    }

    private void onAttachFileClick(View view) {

        if (view == imgAddEmoticonsInPost) {

        } else if (view == imgAttachFileInPost) {

        } else if (view == imgAttachImageInPost) {

        } else if (view == imgTagInPost) {

        }

    }


    private void callGetAllPostFeeds() {


        if (Utils.isInternetConnected(getActivity())) {

            try {
                GetAllFeedsRequest getAllFeedsRequest = new GetAllFeedsRequest();
                getAllFeedsRequest.setUser_id("141");

                new WebserviceWrapper(getActivity(), getAllFeedsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETALLFEEDS);

            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    public void callGetAllComments(int position) {


        if (Utils.isInternetConnected(getActivity())) {

            try {
                GetAllCommentRequest getAllCommentRequest = new GetAllCommentRequest();
                getAllCommentRequest.setFeed_id(responseObj.getData().get(position).getFeedId());

                new WebserviceWrapper(getActivity(), getAllCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETALLCOMMENTS);

            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }
    }


    public void callAddComment(AddCommentRequest addCommentRequest) {

        AddCommentRequest addComment = addCommentRequest;

        if (Utils.isInternetConnected(getActivity())) {

            try {
                new WebserviceWrapper(getActivity(), addCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.ADDCOMMENT);

            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getString(R.string.strnetissue), getActivity());
        }

    }


    public void callGetStudyMates(int position) {

        if (Utils.isInternetConnected(getActivity())) {

            try {
                GetAllFeedsRequest getAllFeedsRequest = new GetAllFeedsRequest();

//             getAllFeedsRequest.setUser_id(responseObj.getData().get(position).getUserId());
                getAllFeedsRequest.setUser_id("167");


                new WebserviceWrapper(getActivity(), getAllFeedsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSTUDYMATES);

            } catch (Exception e) {
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
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
                Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
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

                if (responseObj.getStatus().equals(Urls.STATUS_SUCCESS) && responseObj != null) {

                    postFeedsAdapter.addAll(responseObj.getData());

                } else {
                    Toast.makeText(getActivity(), responseObj.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            } else if (apiMethodName == WebserviceWrapper.ADDCOMMENT) {

                ResponseObject commentResponseObject = (ResponseObject) object;

                if (commentResponseObject.getStatus().equals(Urls.STATUS_SUCCESS) && commentResponseObject != null) {
                    Debug.e(TAG, "The message is::" + commentResponseObject.getMessage());
                } else {

                    Toast.makeText(getActivity(), commentResponseObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


            } else if (apiMethodName == WebserviceWrapper.GETSTUDYMATES) {


                ResponseObject getStudyMatesResponseObject = (ResponseObject) object;

                if (getStudyMatesResponseObject.getStatus().equals(Urls.STATUS_SUCCESS) && getStudyMatesResponseObject != null) {
                    Debug.e(TAG, "The message is::" + getStudyMatesResponseObject.getMessage());

                    openTagUserDialog(getStudyMatesResponseObject.getData());


                } else {

                    Toast.makeText(getActivity(), getStudyMatesResponseObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            } else if (apiMethodName == WebserviceWrapper.TAGFRIENDINFEED) {

                ResponseObject getFriendInFeedObject = (ResponseObject) object;

                if (getFriendInFeedObject.getStatus().equals(Urls.STATUS_SUCCESS) && getFriendInFeedObject != null) {
                    Debug.e(TAG, "The message is::" + getFriendInFeedObject.getMessage());


                    Toast.makeText(getActivity(), getFriendInFeedObject.getMessage(),
                            Toast.LENGTH_LONG).show();


                } else {

                    Toast.makeText(getActivity(), getFriendInFeedObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            } else if (apiMethodName == WebserviceWrapper.GETALLCOMMENTS) {

                ResponseObject getAllCommentsObject = (ResponseObject) object;

                if (getAllCommentsObject.getStatus().equals(Urls.STATUS_SUCCESS) && getAllCommentsObject != null) {
                    Debug.e(TAG, "The message is::" + getAllCommentsObject.getMessage());


                    ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData());
                    viewAllCommentsDialog.show();


                } else {

                    Toast.makeText(getActivity(), getAllCommentsObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }


    View.OnClickListener viewAllCommetsListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

//            int position = (Integer) v.getTag();
//
//            ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData().get(position).getCommentList());
//            viewAllCommentsDialog.show();

        }
    };

    private void openTagUserDialog(ArrayList<Data> data) {

        TagUserDialog tagUserDialog = new TagUserDialog(getActivity(), HomeFragment.this, data);
        tagUserDialog.show();

    }


}
