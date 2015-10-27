package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * these is the postfeedcommentsadapter
 */
public class PostFeedCommentsAdapter extends RecyclerView.Adapter<PostFeedCommentsAdapter.ViewHolder> {

    private static final String TAG = PostFeedCommentsAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfComments = new ArrayList<Data>();


    @Override
    public PostFeedCommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_post_commenter, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostFeedCommentsAdapter.ViewHolder holder, int position) {
        holder.txtCommenterUsername.setText(listOfComments.get(position).getFullName());
        holder.txtCommenterComment.setText(listOfComments.get(position).getComment());
        holder.txtCommentDuration.setText(listOfComments.get(position).getCommentBy());
    }


    public void addAll(ArrayList<Data> data) {

        try {
            this.listOfComments.clear();
            this.listOfComments.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfComments.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCommenterUsername;
        TextView txtCommenterComment;
        TextView txtCommentDuration;


        public ViewHolder(View itemView) {
            super(itemView);

            txtCommenterUsername = (TextView) itemView.findViewById(R.id.txt_commenter_username);
            txtCommenterComment = (TextView) itemView.findViewById(R.id.txt_commenter_comment);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);

        }
    }


}
