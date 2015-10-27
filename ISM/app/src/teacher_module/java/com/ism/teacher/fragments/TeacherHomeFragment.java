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

import com.ism.R;
import com.ism.interfaces.FragmentListener;

import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.TagStudyMatesAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.dialog.TagCustomDialog;
import com.ism.teacher.dialog.ViewAllCommentsDialog;
import com.ism.teacher.model.AddCommentRequest;
import com.ism.teacher.model.Comment;
import com.ism.teacher.model.FeedIdRequest;
import com.ism.teacher.model.GetAllFeedsTeacherRequest;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.adapters.AllFeedsAdapter;
import com.ism.teacher.helper.ConnectionDetector;
import com.ism.teacher.login.TeacherHomeActivity;
import com.ism.teacher.model.TagFriendInFeedRequest;
import com.ism.teacher.ws.WebserviceWrapper;

import java.util.ArrayList;


/**
 * Created by c161 on --/10/15.
 */
public class TeacherHomeFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

    private static final String TAG = TeacherHomeFragment.class.getSimpleName();

    private View rootview;
    // private ListView listPostFeed;
    private RecyclerView recyclerviewPost;
    private FragmentListener fragListener;
    ConnectionDetector connectionDetector;

    AllFeedsAdapter allFeedsAdapter;
    TagStudyMatesAdapter tagStudyMatesAdapter;

    ArrayList<Comment> commentArrayList;
    ResponseObject responseObj;


    public static TeacherHomeFragment newInstance() {
        TeacherHomeFragment fragTeacherHome = new TeacherHomeFragment();
        return fragTeacherHome;
    }

    public TeacherHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectionDetector = new ConnectionDetector(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_post_home, container, false);

        recyclerviewPost = (RecyclerView) rootview.findViewById(R.id.recyclerview_post);
        if (connectionDetector.isConnectingToInternet()) {
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
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }


    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {

        responseObj = (ResponseObject) object;
        if (responseObj.getStatus().equalsIgnoreCase(AppConstant.API_STATUS_SUCCESS)) {

            if (API_METHOD == WebserviceWrapper.GET_ALL_FEEDS) {
                if (responseObj.getData().size() > 0) {
                    allFeedsAdapter = new AllFeedsAdapter(getActivity(), responseObj.getData(), viewAllListener, this);
                    recyclerviewPost.setAdapter(allFeedsAdapter);
                    recyclerviewPost.setLayoutManager(new LinearLayoutManager(getActivity()));
//                allFeedsAdapter.notifyDataSetChanged();
                }
            } else if (API_METHOD == WebserviceWrapper.GET_ALL_COMMENTS) {
                if (responseObj.getData().size() > 0) {
                    ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData());
                    viewAllCommentsDialog.show();
                }
            } else if (API_METHOD == WebserviceWrapper.GET_STUDYMATES) {
                if (responseObj.getData().size() > 0) {
                    TagCustomDialog tagCustomDialog = new TagCustomDialog(getActivity(), responseObj.getData());
                    tagCustomDialog.show();
//                    tagStudyMatesAdapter = new TagStudyMatesAdapter(getActivity(), responseObj.getData(), tagStudymatesListener, this);
//                    recyclerviewPost.setAdapter(allFeedsAdapter);
//                    recyclerviewPost.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

        } else {
            Toast.makeText(getActivity(), " Not Successful!!!", Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener viewAllListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();
//            if (responseObj.getData().get(position).getComment().size() > 0) {
//                ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData().get(position).getComment());
//                viewAllCommentsDialog.show();
//
//            }

            callViewAllCommentsApi(position);

        }
    };
    FeedIdRequest feedIdRequest = new FeedIdRequest();

    public void callViewAllCommentsApi(int position) {

        try {
            FeedIdRequest feedIdRequest = new FeedIdRequest();
            feedIdRequest.setFeed_id(responseObj.getData().get(position).getFeed_id());
            //  Log.e("feed id",""+responseObj.getData().get(position).getFeed_id());

            if (responseObj.getData().get(position).getFeed_id() != null) {
                new WebserviceWrapper(getActivity(), feedIdRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GET_ALL_COMMENTS);
            } else {
                Toast.makeText(getActivity(), "feed id is null", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
    }

    public TagFriendInFeedRequest tagFriendInFeedRequest = new TagFriendInFeedRequest();


    public void callAddCommentApi(int position, String postComment) {
        try {
            AddCommentRequest addCommentRequest = new AddCommentRequest();
            addCommentRequest.setFeed_id(responseObj.getData().get(position).getFeed_id());
            addCommentRequest.setComment_by(responseObj.getData().get(position).getUser_id());
            addCommentRequest.setComment(postComment);

            new WebserviceWrapper(getActivity(), addCommentRequest, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                    .execute(WebserviceWrapper.ADD_COMMENTS);

            //  recyclerviewPost.notifyAll();

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
            Toast.makeText(getActivity(), "No Internet Connection Found", Toast.LENGTH_SHORT).show();
        }
    }

}