package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these adapter class is for getallthe comments of particular feed
 */
public class PostFeedCommentsAdapter extends RecyclerView.Adapter<PostFeedCommentsAdapter.ViewHolder> {

    private static final String TAG = PostFeedCommentsAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<Data> listOfComments = new ArrayList<Data>();
    private ImageLoader imageLoader;
    private LayoutInflater inflater;


    public PostFeedCommentsAdapter(Context context) {
        this.mContext = context;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_post_commenter, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtCommenterUsername.setText(listOfComments.get(position).getFullName());
        holder.txtCommenterComment.setText(listOfComments.get(position).getComment());
        holder.txtCommentDuration.setText(listOfComments.get(position).getCommentBy());

        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgCommenterDp, ISMAuthor.options);
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
        ImageView imgCommenterDp;


        public ViewHolder(View itemView) {
            super(itemView);

            txtCommenterUsername = (TextView) itemView.findViewById(R.id.txt_commenter_username);
            txtCommenterComment = (TextView) itemView.findViewById(R.id.txt_commenter_comment);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
            imgCommenterDp = (ImageView) itemView.findViewById(R.id.img_commenter_dp);

        }
    }


}
