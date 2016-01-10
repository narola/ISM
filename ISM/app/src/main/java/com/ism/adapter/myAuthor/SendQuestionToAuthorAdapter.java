package com.ism.adapter.myAuthor;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.fragment.myAuthor.goTrending.GoTrendingFragment;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.Assignment;
import com.ism.ws.model.TrendingQuestion;

import java.util.ArrayList;

/**
 * Created by c162 on 01/01/16.
 */
public class SendQuestionToAuthorAdapter extends RecyclerView.Adapter<SendQuestionToAuthorAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = SendQuestionToAuthorAdapter.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<TrendingQuestion> arrayList;
    private GoTrendingFragment fragment;
    private Fragment mFragment;
    private ArrayList<Assignment> arrListBookAssignment = new ArrayList<Assignment>();
    private LayoutInflater inflater;
    private int totalQuestions = 5;

    public SendQuestionToAuthorAdapter(Fragment mFragment, Context mContext, ArrayList<TrendingQuestion> arrayList) {
        this.mFragment = mFragment;
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(mContext);
        fragment = GoTrendingFragment.newInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.list_item_send_question_author, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            if (position < totalQuestions) {
                Utility.showView(holder.rrQuestion);
                Utility.hideView(holder.rrSubmit);
                holder.txtQuestion.setText(arrayList.get(position).getQuestionText());
                holder.txtUserName.setText(arrayList.get(position).getPostedByUsername());
                holder.txtQuestionNo.setText(String.valueOf(position + 1));
                holder.txtTotalFollowing.setText(arrayList.get(position).getFollowerCount());
                Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrayList.get(position).getPostedByPic(), holder.imgUserPic, ISMStudent.options);
            } else {
                Utility.hideView(holder.rrQuestion);
                Utility.showView(holder.rrSubmit);

            }

            holder.txtFollowQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callApiForFollowQuestion(arrayList.get(position).getTrendingId());
                }
            });
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    private void callApiForFollowQuestion(String trendingId) {
        if (Utility.isConnected(mContext)) {
            try {
                ((HostActivity)mContext).showProgress();
                Attribute attribute = new Attribute();
                attribute.setQuestionId(trendingId);
                attribute.setUserId(Global.strUserId);

                new WebserviceWrapper(mContext, attribute, this).new WebserviceCaller()
                        .execute(WebConstants.FOLLOW_QUESTION);
            } catch (Exception e) {
                Log.e(TAG, "callApiForGetTrendingQuestionDetails Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(mContext);
        }
    }


    @Override
    public int getItemCount() {
        if (arrayList.size() >= 0 && arrayList.size() <=5) {
            totalQuestions = arrayList.size();
        }
        return 5;
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            ((HostActivity)mContext).hideProgress();
            switch (apiCode) {
                case WebConstants.FOLLOW_QUESTION:
                    onResponseFollowQuestion(object, error);
                    break;
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exceptions : " + e.getLocalizedMessage());
        }
    }

    private void onResponseFollowQuestion(Object object, Exception error) {it
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    //update value for follow_question
                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseFollowQuestion : " + WebConstants.FAILED);
                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseFollowQuestion error : " + error.toString());
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponseFollowQuestion Exceptions : " + e.getLocalizedMessage());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rrQuestion, rrSubmit;
        private TextView txtQuestionNo, txtQuestion, txtFollowing, txtTotalFollowing, txtFollowQuestion, txtUserName;
        private ImageView imgLike, imgUserPic;

        public ViewHolder(View itemView) {
            super(itemView);

            rrQuestion = (RelativeLayout) itemView.findViewById(R.id.rr_question);
            rrSubmit = (RelativeLayout) itemView.findViewById(R.id.rr_submit);

            txtQuestionNo = (TextView) itemView.findViewById(R.id.txt_question_no);
            txtQuestionNo.setTypeface(Global.myTypeFace.getRalewayBold());

            txtQuestion = (TextView) itemView.findViewById(R.id.txt_question);
            txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtFollowQuestion = (TextView) itemView.findViewById(R.id.txt_follow_question);
            txtFollowQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtTotalFollowing = (TextView) itemView.findViewById(R.id.txt_total_following);
            txtTotalFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtFollowing = (TextView) itemView.findViewById(R.id.txt_following);
            txtFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtUserName = (TextView) itemView.findViewById(R.id.txt_user_name);
            txtUserName.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgLike = (ImageView) itemView.findViewById(R.id.img_like);

            imgUserPic = (ImageView) itemView.findViewById(R.id.img_user_pic);

        }
    }

}
