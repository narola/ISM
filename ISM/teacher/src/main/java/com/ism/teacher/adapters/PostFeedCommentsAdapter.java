package com.ism.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * these is the postfeedcommentsadapter
 */
public class PostFeedCommentsAdapter extends RecyclerView.Adapter<PostFeedCommentsAdapter.ViewHolder> {

    private static final String TAG = PostFeedCommentsAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfComments = new ArrayList<Data>();


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
        holder.txtCommenterUsername.setText(listOfComments.get(position).getFull_name());
        holder.txtCommenterComment.setText(listOfComments.get(position).getComment());
        holder.txtCommentDuration.setText(listOfComments.get(position).getCommentBy());

        if (position == 0) {
            holder.imgSeparator.setVisibility(View.GONE);
        } else {
            holder.imgSeparator.setVisibility(View.VISIBLE);
        }
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


}
