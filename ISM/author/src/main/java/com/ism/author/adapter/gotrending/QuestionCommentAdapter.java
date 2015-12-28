package com.ism.author.adapter.gotrending;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.Comment;

import java.util.ArrayList;

/**
 * Created by c166 on 25/12/15.
 */
public class QuestionCommentAdapter extends RecyclerView.Adapter<QuestionCommentAdapter.ViewHolder> {


    private static final String TAG = QuestionCommentAdapter.class.getSimpleName();
    private Fragment mFragment;
    private ArrayList<Comment> arrListQuestionComments = new ArrayList<Comment>();
    private LayoutInflater inflater;
    private Context mContext;


    public QuestionCommentAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_question_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {


            holder.txtCommentorName.setText(arrListQuestionComments.get(position).getCommentBy());
            holder.txtComment.setText(arrListQuestionComments.get(position).getCommentText());

        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return arrListQuestionComments.size();
    }

    public void addAll(ArrayList<Comment> comments) {
        try {
            this.arrListQuestionComments.clear();
            this.arrListQuestionComments.addAll(comments);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgCommentorDp;
        TextView txtCommentorName, txtComment;

        public ViewHolder(View itemView) {
            super(itemView);

            imgCommentorDp = (CircleImageView) itemView.findViewById(R.id.img_commenter_dp);
            txtCommentorName = (TextView) itemView.findViewById(R.id.txt_commentor_name);
            txtComment = (TextView) itemView.findViewById(R.id.txt_comment);

            txtCommentorName.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtComment.setTypeface(Global.myTypeFace.getRalewayRegular());


        }
    }
}
