package com.ism.teacher.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.AllFeedsAdapter;
import com.ism.teacher.adapters.TagStudyMatesAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.dialog.TagStudyMatesDialog;
import com.ism.teacher.dialog.ViewAllCommentsDialog;
import com.ism.teacher.helper.PreferenceData;
import com.ism.teacher.interfaces.FragmentListener;
import com.ism.teacher.login.TeacherHomeActivity;
import com.ism.teacher.model.AddCommentRequest;
import com.ism.teacher.model.Comment;
import com.ism.teacher.model.FeedIdRequest;
import com.ism.teacher.model.GetAllFeedsTeacherRequest;
import com.ism.teacher.model.LIkeFeedRequest;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.model.TagFriendInFeedRequest;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherHomeFragment.class.getSimpleName();

    private View rootview;
    private RecyclerView recyclerviewPost;
    private FragmentListener fragListener;

    AllFeedsAdapter allFeedsAdapter;
    TagStudyMatesAdapter tagStudyMatesAdapter;

    ArrayList<Comment> commentArrayList;
    int setAddCommentRowPosition;

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

        recyclerviewPost = (RecyclerView) rootview.findViewById(R.id.recyclerview_post);
        if (Utils.isInternetConnected(getActivity())) {
            callAllFeedsApi();
        }

        Log.e(TAG, "called");
        return rootview;
    }

    private void callAllFeedsApi() {
        try {
            GetAllFeedsTeacherRequest getAllFeedsTeacherRequest = new GetAllFeedsTeacherRequest();
            getAllFeedsTeacherRequest.setUser_id("141");

            new WebserviceWrapper(getActivity(), getAllFeedsTeacherRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.GET_ALL_FEEDS);

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
                fragListener.onFragmentAttached(TeacherHomeActivity.FRAGMENT_TEACHER_HOME);
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
                fragListener.onFragmentDetached(TeacherHomeActivity.FRAGMENT_TEACHER_HOME);
                // callLikeFeed();
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

    ResponseObject responseObj;

    @Override
    public void onResponse(int apiMethod, Object object, Exception error) {
	    try {
		    if (apiMethod == WebserviceWrapper.GET_ALL_FEEDS) {
			    responseObj = (ResponseObject) object;
			    if (responseObj != null) {
				    if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {
					    if (responseObj.getData().size() > 0) {
						    allFeedsAdapter = new AllFeedsAdapter(getActivity(), responseObj.getData(), this);
						    recyclerviewPost.setAdapter(allFeedsAdapter);
						    recyclerviewPost.setLayoutManager(new LinearLayoutManager(getActivity()));
					    }
				    } else {
					    Toast.makeText(getActivity(), apiMethod + " Not Successful!!!", Toast.LENGTH_SHORT).show();
				    }
			    } else {
				    Utils.showToast(getActivity().getResources().getString(R.string.web_service_issue), getActivity());
			    }


		    } else if (apiMethod == WebserviceWrapper.GET_ALL_COMMENTS) {
			    ResponseObject responseObj = (ResponseObject) object;

			    if (responseObj != null) {
				    if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {
					    if (responseObj.getData().size() > 0) {
						    ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData());
						    viewAllCommentsDialog.show();
					    }
				    } else {
					    Toast.makeText(getActivity(), apiMethod + " Not Successful!!!", Toast.LENGTH_SHORT).show();
				    }
			    } else {
				    Utils.showToast(getActivity().getResources().getString(R.string.web_service_issue), getActivity());
			    }


		    } else if (apiMethod == WebserviceWrapper.GET_STUDYMATES) {
			    ResponseObject responseObj = (ResponseObject) object;

			    if (responseObj != null) {
				    if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {
					    if (responseObj.getData().size() > 0) {
						    TagStudyMatesDialog tagStudyMatesDialog = new TagStudyMatesDialog(getActivity(), responseObj.getData(), this);
						    tagStudyMatesDialog.show();
					    }
				    } else {
					    Toast.makeText(getActivity(), apiMethod + " Not Successful!!!", Toast.LENGTH_SHORT).show();
				    }
			    } else {
				    Utils.showToast(getActivity().getResources().getString(R.string.web_service_issue), getActivity());

			    }


		    } else if (apiMethod == WebserviceWrapper.TAG_FRIEND_IN_FEED) {
			    ResponseObject responseObj = (ResponseObject) object;

			    if (responseObj != null) {
				    if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {
					    Toast.makeText(getActivity(), "Tag Successful", Toast.LENGTH_SHORT).show();
				    } else {
					    Toast.makeText(getActivity(), "Tag Not Successful", Toast.LENGTH_SHORT).show();
				    }


			    } else {
				    Utils.showToast(getActivity().getResources().getString(R.string.web_service_issue), getActivity());

			    }

		    } else if (apiMethod == WebserviceWrapper.ADD_COMMENTS) {
			    ResponseObject responseObj = (ResponseObject) object;
			    if (responseObj != null) {
				    if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

					    updatePostFeedViewAfterAddComment();

					    Toast.makeText(getActivity(), "Comment Added Successfully!!", Toast.LENGTH_SHORT).show();
				    } else {
					    Toast.makeText(getActivity(), apiMethod + " Not Successful!!!", Toast.LENGTH_SHORT).show();
				    }
			    } else {
				    Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());

			    }

		    }
	    } catch (Exception e) {
		    Log.e(TAG, "onResponse Exception : " + e.toString());
	    }

    }

    public void callViewAllCommentsApi(FeedIdRequest feedIdRequest) {

        try {

            new WebserviceWrapper(getActivity(), feedIdRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.GET_ALL_COMMENTS);

        } catch (Exception e) {

        }
    }


    public void callAddCommentApi(AddCommentRequest addCommentRequest) {
        try {
            new WebserviceWrapper(getActivity(), addCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.ADD_COMMENTS);

        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
    }


    public void callGetStudyMates() {

        if (Utils.isInternetConnected(getActivity())) {

            try {
                GetAllFeedsTeacherRequest getAllFeedsRequest = new GetAllFeedsTeacherRequest();
                getAllFeedsRequest.setUser_id("167");


                new WebserviceWrapper(getActivity(), getAllFeedsRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GET_STUDYMATES);

            } catch (Exception e) {
                // Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }
    }

    public void callTagFriendInFeed() {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                new WebserviceWrapper(getActivity(), tagFriendInFeedRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.TAG_FRIEND_IN_FEED);
            } catch (Exception e) {
                // Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getResources().getString(R.string.no_internet), getActivity());
        }

    }


    public int getSetAddCommentRowPosition() {
        return setAddCommentRowPosition;
    }

    public void setSetAddCommentRowPosition(int setAddCommentRowPosition) {
        this.setAddCommentRowPosition = setAddCommentRowPosition;
    }

    private void updatePostFeedViewAfterAddComment() {
        int position = getSetAddCommentRowPosition();
        responseObj.getData().get(position).setTotal_comment(String.valueOf(Integer.parseInt(responseObj.getData().get(position).getTotal_comment()) + 1));
        View v = recyclerviewPost.getChildAt(position);
        EditText etWriteComment = (EditText) v.findViewById(R.id.et_writePost);
        etWriteComment.setText("");
        allFeedsAdapter.notifyDataSetChanged();

    }

    String likePrefData, unlikePrefData;

    public void callLikeFeed() {


        likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, getActivity(), "");
        unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, getActivity(), "");


        if (Utils.isInternetConnected(getActivity())) {
            try {
                LIkeFeedRequest likeFeedRequest = new LIkeFeedRequest();
                likeFeedRequest.setUser_id(AppConstant.TEST_USER_ID);

                if (likePrefData.length() > 0) {
                    likeFeedRequest.setLiked_id((likePrefData.substring(0, likePrefData.length() - 1)).split(","));
//                    likeFeedRequest.setLiked_id(new String[]{"61"});
                }

                if (unlikePrefData.length() > 0) {
                    likeFeedRequest.setUnliked_id((unlikePrefData.substring(0, unlikePrefData.length() - 1)).split(","));
//                    likeFeedRequest.setUnliked_id(new String[]{"71", "62"});
                }


                new WebserviceWrapper(getActivity(), likeFeedRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.LIKE_FEED);
            } catch (Exception e) {
                // Debug.e(TAG + getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

    }
}