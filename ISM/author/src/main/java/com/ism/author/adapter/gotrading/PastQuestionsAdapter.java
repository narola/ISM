package com.ism.author.adapter.gotrading;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;

import java.util.ArrayList;

/**
 * Created by c162 on 17/12/15.
 */
public class PastQuestionsAdapter extends RecyclerView.Adapter<PastQuestionsAdapter.ViewHolder> {

    private static final String ARG_QUESTION_ID = "questionId";
    private static final String ARG_QUESTION_CREATOR_PROFILE_PIC = "creatorProfPic";
    private static final String ARG_CREATOR_NAME = "questionCreatorName";
    private static final String ARG_CREATOR_ID = "questionCreatorId";
    private static final String ARG_DATE = "questionCreatedDate";
    private static final String ARG_FOLLOWERS = "followers";
    Context context;
    ArrayList<String> arrayList=new ArrayList<>();
    private LayoutInflater inflater;
    private int selectedView=-1;

    public PastQuestionsAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_past_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(getBundleArguments().containsKey(PastQuestionsAdapter.ARG_QUESTION_ID)) {
            if (getBundleArguments().getInt(PastQuestionsAdapter.ARG_QUESTION_ID) == position) {
                holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.color_white));
            } else {
                holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.divider_light));
            }
        }

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    /*this is to select the current student*/
                setBundleArgument(position);
                notifyDataSetChanged();
                //((AuthorHostActivity) context).loadStudentEvaluationData();

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgDpPostCreator;
        TextView txtAnswer, txtFollowers, txtQuestion, txt_Creator_Name,txtTotalFollowers;
        LinearLayout llMain;

        public ViewHolder(View view) {
            super(view);
            imgDpPostCreator = (CircleImageView) itemView
                    .findViewById(R.id.img_user_dp);
            txtAnswer =(TextView)view.findViewById(R.id.txt_answer);
            txtAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtFollowers =(TextView)view.findViewById(R.id.txt_followers);
            txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtTotalFollowers=(TextView)view.findViewById(R.id.txt_no_of_followers);
            txtTotalFollowers.setTypeface(Global.myTypeFace.getRalewayBold());

            txtQuestion=(TextView)view.findViewById(R.id.txt_question);
            txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

            txt_Creator_Name=(TextView)view.findViewById(R.id.txt_creator_name);
            txt_Creator_Name.setTypeface(Global.myTypeFace.getRalewayRegular());

            llMain=(LinearLayout)view.findViewById(R.id.ll_main);

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
