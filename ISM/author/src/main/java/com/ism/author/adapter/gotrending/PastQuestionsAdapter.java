package com.ism.author.adapter.gotrending;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.TrendingQuestion;

import java.util.ArrayList;

/**
 * Created by c162 on 17/12/15.
 */
public class PastQuestionsAdapter extends RecyclerView.Adapter<PastQuestionsAdapter.ViewHolder> {
    private static final String TAG = PastQuestionsAdapter.class.getSimpleName();
    private static final String ARG_QUESTION_ID = "questionId";
    private static final String ARG_QUESTION_CREATOR_PROFILE_PIC = "creatorProfPic";
    private static final String ARG_CREATOR_NAME = "questionCreatorName";
    private static final String ARG_CREATOR_ID = "questionCreatorId";
    private static final String ARG_DATE = "questionCreatedDate";
    private static final String ARG_FOLLOWERS = "followers";
    Context context;
    ArrayList<TrendingQuestion> arrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private int selectedView = -1;

    public PastQuestionsAdapter(Context context, ArrayList<TrendingQuestion> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_past_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.txt_Creator_Name.setText(arrayList.get(position).getPostedByUsername());
            //holder.txtDate.setText(Utility.DateFormat(arrayList.get(position).getPostedOn()));
            holder.txtNoOfFollowers.setText(arrayList.get(position).getFollowerCount());
            holder.txtQuestion.setText(arrayList.get(position).getQuestionText());
          //  holder.txtAnswer.setText(Html.fromHtml("<font color=red>"+arrayList.get(position).getTotalComment() +" comments"));
            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getPostedByPic(), holder.imgDpPostCreator, ISMAuthor.options);
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*this is to select the current student*/
                    setBundleArgument(position);
                    //((AuthorHostActivity) context).loadStudentEvaluationData();

                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgDpPostCreator;
        TextView txtAnswer, txtFollowers, txtQuestion, txt_Creator_Name, txtTotalFollowers, txtNoOfFollowers;
        LinearLayout llMain;

        public ViewHolder(View view) {
            super(view);
            imgDpPostCreator = (CircleImageView) itemView
                    .findViewById(R.id.img_user_dp);
            txtAnswer = (TextView) view.findViewById(R.id.txt_answer);
            txtAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtFollowers = (TextView) view.findViewById(R.id.txt_followers);
            txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtNoOfFollowers = (TextView) view.findViewById(R.id.txt_no_of_followers);
            txtNoOfFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtTotalFollowers = (TextView) view.findViewById(R.id.txt_no_of_followers);
            txtTotalFollowers.setTypeface(Global.myTypeFace.getRalewayBold());

            txtQuestion = (TextView) view.findViewById(R.id.txt_question);
            txtQuestion.setTypeface(Global.myTypeFace.getRalewayMedium());

            txt_Creator_Name = (TextView) view.findViewById(R.id.txt_creator_name);
            txt_Creator_Name.setTypeface(Global.myTypeFace.getRalewayRegular());

            llMain = (LinearLayout) view.findViewById(R.id.ll_main);

        }
    }

    public void setBundleArgument(int position) {
        getBundleArguments().putInt(PastQuestionsAdapter.ARG_QUESTION_ID, position);
//        getBundleArguments().putString(QuestionsMostFollowAdapter.ARG_QUESTION_CREATOR_PROFILE_PIC,
//                arrayList.get(position).getStudentProfilePic());
//        getBundleArguments().putString(QuestionsMostFollowAdapter.ARG_CREATOR_NAME,
//                arrayList.get(position).getStudentName());
//        getBundleArguments().putString(QuestionsMostFollowAdapter.ARG_CREATOR_ID,
//                arrayList.get(position).getStudentId());
//        getBundleArguments().putString(QuestionsMostFollowAdapter.ARG_DATE,
//                arrayList.get(position).getStudentId());
//        getBundleArguments().putString(QuestionsMostFollowAdapter.ARG_FOLLOWERS,
//                arrayList.get(position).getStudentId());

        notifyDataSetChanged();
    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) context).getBundle();
    }

}
