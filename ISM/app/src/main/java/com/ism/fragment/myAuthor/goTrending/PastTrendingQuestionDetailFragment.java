package com.ism.fragment.myAuthor.goTrending;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.myAuthor.PastQuestionsAdapter;
import com.ism.adapter.myAuthor.QuestionCommentAdapter;
import com.ism.constant.WebConstants;
import com.ism.fragment.MyAuthorFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Comment;
import com.ism.ws.model.TrendingQuestionDetails;

import java.util.ArrayList;

/**
 * Created by c162 on 16/1/16.
 */
public class PastTrendingQuestionDetailFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = PastTrendingQuestionDetailFragment.class.getSimpleName();
    private View view;

    private ImageView imgUserDp;
    private TextView txtCreatorName, txtQuestion, txtDate, txtTotalFollowers, txtTotalComments, tvNoDataMsg;
    private RecyclerView rvQuestionCommentsList;
    private QuestionCommentAdapter questionCommentAdapter;
    private ArrayList<Comment> arrListComments = new ArrayList<Comment>();
    private HostActivity activityHost;
    private FragmentListener fragmentListener;


    public static PastTrendingQuestionDetailFragment newInstance() {
        PastTrendingQuestionDetailFragment pastTrendingQuestionDetailFragment = new PastTrendingQuestionDetailFragment();
        return pastTrendingQuestionDetailFragment;
    }

    public PastTrendingQuestionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_detail, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

        imgUserDp = (ImageView) view.findViewById(R.id.img_user_dp);
        txtCreatorName = (TextView) view.findViewById(R.id.txt_creator_name);
        txtQuestion = (TextView) view.findViewById(R.id.txt_question);
        txtDate = (TextView) view.findViewById(R.id.txt_date);
        txtTotalFollowers = (TextView) view.findViewById(R.id.txt_total_followers);
        txtTotalComments = (TextView) view.findViewById(R.id.txt_total_comments);
        tvNoDataMsg = (TextView) view.findViewById(R.id.txt_empty_view);

        txtCreatorName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtDate.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTotalComments.setTypeface(Global.myTypeFace.getRalewayRegular());


        rvQuestionCommentsList = (RecyclerView) view.findViewById(R.id.rv_question_comments_list);
        questionCommentAdapter = new QuestionCommentAdapter(this, getActivity());
        rvQuestionCommentsList.setAdapter(questionCommentAdapter);
        rvQuestionCommentsList.setLayoutManager(new LinearLayoutManager(getActivity()));


        setEmptyView(true);

        callApiGetTrendingQuestionDetail();
    }


    private void setEmptyView(boolean isEnable) {

        tvNoDataMsg.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvNoDataMsg.setText(getResources().getString(R.string.no_comments_available));
        tvNoDataMsg.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        rvQuestionCommentsList.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    }

    private void callApiGetTrendingQuestionDetail() {
        if (Utility.isConnected(getActivity())) {
            try {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setQuestionId((activityHost.getBundle().getString(PastQuestionsAdapter.ARG_QUESTION_ID)));

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_TRENDING_QUESTION_DETAIL);
            } catch (Exception e) {
                Log.e(TAG, "callApiGetTrendingQuestionDetail Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
        }
    }

    private void onResponseGetTrendingQuestionDetail(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {


                    //  setData(responseHandler.getTrendingQuestionDetails().get(0));

                    if (responseHandler.getTrendingQuestionDetails().size() > 0) {
                        setUpData(responseHandler.getTrendingQuestionDetails());
                        setEmptyView(false);

                    } else {
                        setEmptyView(true);
                    }
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Log.e(TAG, "onResponseGetTrendingQuestionDetail : " + WebConstants.FAILED);

                }
            } else if (error != null) {
                Log.e(TAG, "onResponseGetTrendingQuestionDetail error : " + error.toString());
            }

        } catch (Exception e) {
            Log.e(TAG, "onResponseGetTrendingQuestionDetail Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void setUpData(ArrayList<TrendingQuestionDetails> arrayList) {
        try {

            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrayList.get(0).getPostedByPic(),
                    imgUserDp, ISMStudent.options);
            txtCreatorName.setText(arrayList.get(0).getPostedByUsername());
            txtDate.setText(Utility.getDateInApiFormat(arrayList.get(0).getPostedOn()));

//        txtDate.setText(Utility.getFormattedDate("dd-MMM-yyyy", questionComments.getPostedOn()));
            txtQuestion.setText(Html.fromHtml(arrayList.get(0).getQuestionText()));
            txtTotalFollowers.setText(arrayList.get(0).getFollowerCount() + " " + getActivity().getString(R.string.strfollowers));
            txtTotalComments.setText(arrayList.get(0).getTotalComment() + " " + getActivity().getString(R.string.strcomments));
            arrListComments.addAll(arrayList.get(0).getComment());
            questionCommentAdapter.addAll(arrListComments);
            questionCommentAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Debug.e(TAG, "setUpData Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityHost = (HostActivity) activity;
        fragmentListener = (FragmentListener) activity;
        if (fragmentListener != null) {
            activityHost.onFragmentAttached(MyAuthorFragment.FRAGMENT_PAST_TRENDING_QUESTION_DETAIL);
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            activityHost.hideProgress();
            switch (apiCode) {
                case WebConstants.GET_TRENDING_QUESTION_DETAIL:
                    onResponseGetTrendingQuestionDetail(object, error);
                    break;
            }

        } catch (Exception e) {
            Log.e(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }
}
