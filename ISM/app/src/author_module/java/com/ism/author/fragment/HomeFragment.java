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
import com.ism.author.dialog.ViewAllCommentsDialog;
import com.ism.author.login.Urls;
import com.ism.author.model.AddCommentRequest;
import com.ism.author.model.GetAllFeedsRequest;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.ism.interfaces.FragmentListener;
import com.ism.utility.Debug;

/*
* This is the homefragment containg the newsfeed.
*/

//WebserviceWrapper.WebserviceResponse,
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


        if (Utils.isInternetConnected(getActivity())) {

            getAllPostFeeds();

        } else {

            Utils.showToast(getString(R.string.strnetissue), getActivity());

        }

        // Create adapter passing in the sample user data
        postFeedsAdapter = new PostFeedsAdapter(getActivity(), viewAllCommetsListener, addCommentListener);
        // Attach the adapter to the recyclerview to populate items
        rvPostFeeds.setAdapter(postFeedsAdapter);
        // Set layout manager to position the items
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


    private void getAllPostFeeds() {

        try {
            GetAllFeedsRequest getAllFeedsRequest = new GetAllFeedsRequest();
            getAllFeedsRequest.setUser_id("141");

            new WebserviceWrapper(getActivity(), getAllFeedsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.GETALLFEEDS);

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }
    }


    private void addComment() {


        try {

            AddCommentRequest addCommentRequest = new AddCommentRequest();
            addCommentRequest.setFeed_id("");
            addCommentRequest.setComment_by("");
            addCommentRequest.setComment("");


            new WebserviceWrapper(getActivity(), addCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.ADDCOMMENT);

        } catch (Exception e) {
            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
        }

    }

    ResponseObject responseObj;

    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            responseObj = (ResponseObject) object;
            if (responseObj.getStatus().equals(Urls.STATUS_SUCCESS) && responseObj != null) {


                Debug.e(TAG, "The response in fragment is" + (responseObj.getData()).get(0).getUsername());

                if (apiMethodName == WebserviceWrapper.GETALLFEEDS) {
                    postFeedsAdapter.addAll(responseObj.getData());
                }

            } else {

                Toast.makeText(getActivity(), responseObj.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

            Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());

        }
    }


    View.OnClickListener viewAllCommetsListener = new OnClickListener() {
        @Override
        public void onClick(View v) {


            int position = (Integer) v.getTag();

            ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData().get(position).getCommentList());
            viewAllCommentsDialog.show();


        }
    };

    View.OnClickListener addCommentListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();
            addComment();


        }
    };

}
