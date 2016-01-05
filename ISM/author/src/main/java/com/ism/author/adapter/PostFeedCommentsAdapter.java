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
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.model.CommentList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these adapter class is for getallthe comments of particular feed
 */
public class PostFeedCommentsAdapter extends RecyclerView.Adapter<PostFeedCommentsAdapter.ViewHolder> {

    private static final String TAG = PostFeedCommentsAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<CommentList> arrListComment = new ArrayList<CommentList>();
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
        holder.txtCommenterUsername.setText(arrListComment.get(position).getFullName());
        holder.txtCommenterComment.setText(arrListComment.get(position).getComment());
        holder.txtCommentDuration.setText("5 min");


        if (arrListComment.get(position).getProfilePic() != null && arrListComment.get(position).getProfilePic() != "") {
            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListComment.get(position).getComment(),
                    holder.imgCommenterDp, ISMAuthor.options);
        } else {
            holder.imgCommenterDp.setImageResource(R.drawable.userdp);
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

        TextView txtCommenterUsername;
        TextView txtCommenterComment;
        TextView txtCommentDuration;
        ImageView imgCommenterDp;


        public ViewHolder(View itemView) {
            super(itemView);

            txtCommenterUsername = (TextView) itemView.findViewById(R.id.txt_commenter_username);
            txtCommenterComment = (TextView) itemView.findViewById(R.id.txt_commenter_comment);
            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
            imgCommenterDp = (ImageView) itemView.findViewById(R.id.img_commenter_dp);

            txtCommenterUsername.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtCommenterComment.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtCommentDuration.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }


}
