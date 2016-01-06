package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.commonsource.utility.Utility;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;

import java.util.List;

import model.FeedComment;

/**
 * these adapter class is for getallthe comments of particular feed
 */
public class PostFeedCommentsAdapter extends RecyclerView.Adapter<PostFeedCommentsAdapter.ViewHolder> {

    private static final String TAG = PostFeedCommentsAdapter.class.getSimpleName();

    Context context;
    List<FeedComment> listOfComments;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_post_commenter, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try{
        holder.txtCommenterUsername.setText(listOfComments.get(position).getCommentBy().getFullName());
        holder.txtCommenterComment.setText(listOfComments.get(position).getComment());
        holder.txtCommentDuration.setText(Utility.getTimeDuration(listOfComments.get(position).getCreatedDate()));
        Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + listOfComments.get(position).getCommentBy().getProfilePicture(), holder.imgProfilePic, ISMStudent.options);}
        catch (Exception e){
            Log.e(TAG,"onBindViewHolder Exception : "+e.getLocalizedMessage());
        }
    }


    public void addAll(List<FeedComment> comments) {
        try {
            listOfComments=comments;
            listOfComments.clear();
            listOfComments.addAll(comments);
        } catch (Exception e) {
            Log.e(TAG, "addAll Exception : " + e.toString());
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfComments.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgProfilePic;
        TextView txtCommenterUsername;
        TextView txtCommenterComment;
        TextView txtCommentDuration;


        public ViewHolder(View itemView) {
            super(itemView);

            txtCommenterUsername = (TextView) itemView.findViewById(R.id.txt_commenter_username);
            txtCommenterComment = (TextView) itemView.findViewById(R.id.txt_commenter_comment);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
            imgProfilePic = (ImageView) itemView.findViewById(R.id.img_commenter_dp);

        }
    }


}
