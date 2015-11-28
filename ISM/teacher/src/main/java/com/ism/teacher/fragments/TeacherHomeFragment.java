package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ism.teacher.PostActivity;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.PostFeedsAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.model.TagFriendInFeedRequest;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherHomeFragment.class.getSimpleName();

    private View rootview;
    private RecyclerView recyclerviewPost;
    private FragmentListener fragListener;

    PostFeedsAdapter postFeedsAdapter;

    //to open new post
    LinearLayout llPost;
    EditText etWritePost;
    View.OnClickListener onClickAttachFile;


    public static TeacherHomeFragment newInstance() {
        TeacherHomeFragment fragTeacherHome = new TeacherHomeFragment();
        return fragTeacherHome;
    }

    public TeacherHomeFragment() {
        // Required empty public constructor
    }

    public TagFriendInFeedRequest tagFriendInFeedRequest = new TagFriendInFeedRequest();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_post_home, container, false);

        initGlobal(rootview);


        return rootview;
    }

    private void initGlobal(View rootview) {
        recyclerviewPost = (RecyclerView) rootview.findViewById(R.id.recyclerview_post);

        postFeedsAdapter = new PostFeedsAdapter(getActivity());
        recyclerviewPost.setAdapter(postFeedsAdapter);
        recyclerviewPost.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (Utility.isInternetConnected(getActivity())) {
            callAllFeedsApi();
        }

        Log.e(TAG, "called");
        llPost = (LinearLayout) rootview.findViewById(R.id.ll_post);
        etWritePost = (EditText) rootview.findViewById(R.id.et_writePost);
        etWritePost.setEnabled(true);

        onClickAttachFile = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAttachFileClick(view);
            }
        };

        etWritePost.setOnClickListener(onClickAttachFile);
        llPost.setOnClickListener(onClickAttachFile);

    }

    private void onAttachFileClick(View view) {
        if (view == llPost || view == etWritePost) {
            Intent intent = new Intent(getActivity(), PostActivity.class);
            startActivityForResult(intent, AppConstant.REQUEST_CODE_ADD_POST);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstant.REQUEST_CODE_ADD_POST) {
            callAllFeedsApi();
        }

    }

    private void callAllFeedsApi() {
        try {
            //   ((TeacherHostActivity) getActivity()).startProgress();
            Attribute attribute = new Attribute();
            attribute.setUserId(WebConstants.USER_ID_370);
            new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_FEEDS);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
            if (fragListener != null) {
                fragListener.onFragmentAttached(TeacherHostActivity.FRAGMENT_TEACHER_HOME);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(TeacherHostActivity.FRAGMENT_TEACHER_HOME);
                // callLikeFeed();
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.GET_ALL_FEEDS:
                    onResponseGetAllFeeds(object, error);
                    break;
            }

        /*    if (apiMethod == WebConstants.GET_ALL_FEEDS) {
                responseHandler = (ResponseHandler) object;
                if (responseHandler != null) {
                    if (responseHandler.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

                        if (responseHandler.getData().size() > 0) {
                            postFeedsAdapter = new PostFeedsAdapter(getActivity(), responseHandler.getData(), this);
                            recyclerviewPost.setAdapter(postFeedsAdapter);
                            recyclerviewPost.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    } else {
                        Toast.makeText(getActivity(), apiMethod + " Not Successful!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utility.showToast(getActivity().getResources().getString(R.string.web_service_issue), getActivity());
                }


            }*/
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }

    private void onResponseGetAllFeeds(Object object, Exception error) {
        try {
//            ((TeacherHostActivity) getActivity()).stopProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (responseHandler.getFeeds().size() > 0) {
                        postFeedsAdapter.addAll(responseHandler.getFeeds());
                    }


                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Utility.showToast(responseHandler.getMessage(), getActivity());
                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseGetAllFeeds api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllFeeds Exception : " + e.toString());
        }
    }


}