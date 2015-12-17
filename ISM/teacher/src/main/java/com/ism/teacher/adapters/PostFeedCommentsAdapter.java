package com.ism.teacher.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.model.CommentList;

import java.util.ArrayList;

/**
 * these is the postfeedcommentsadapter
 */
public class PostFeedCommentsAdapter extends RecyclerView.Adapter<PostFeedCommentsAdapter.ViewHolder> {

    private static final String TAG = PostFeedCommentsAdapter.class.getSimpleName();

    Context mContext;
    private ArrayList<CommentList> arrListComment = new ArrayList<CommentList>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.post_comments_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtCommenterUsername.setText(arrListComment.get(position).getFullName());
        holder.txtCommenterComment.setText(arrListComment.get(position).getComment());
        holder.txtCommentDuration.setText(arrListComment.get(position).getCommentBy());


        if (position == 0) {
            holder.imgSeparator.setVisibility(View.GONE);
        } else {
            holder.imgSeparator.setVisibility(View.VISIBLE);
        }
    }


    public void addAll(ArrayList<CommentList> commentList) {

        try {
            this.arrListComment.clear();
            this.arrListComment.addAll(commentList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrListComment.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCommenterUsername, txtCommenterComment, txtCommentDuration;
        ImageView imgSeparator;


        public ViewHolder(View itemView) {
            super(itemView);

            txtCommenterUsername = (TextView) itemView.findViewById(R.id.txt_username_commenter);
            txtCommenterComment = (TextView) itemView.findViewById(R.id.txt_comments_from_commenter);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
            imgSeparator = (ImageView) itemView.findViewById(R.id.img_separator);

        }
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }
}
