package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.helper.CircleImageView;
import com.ism.author.model.Data;
import com.ism.author.model.GetAllFeedsComment;

import java.util.ArrayList;

/**
 * these is the adapter class for the postfeeds
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfPostFeeds = new ArrayList<Data>();
    private int NO_OF_COMMENTS_TO_SHOW = 2;
    View.OnClickListener viewAllCommetsListener, addCommentListener;

    public PostFeedsAdapter(Context context, View.OnClickListener viewAllCommetsListener, View.OnClickListener addCommentListener) {
        this.mContext = context;
        this.viewAllCommetsListener = viewAllCommetsListener;
        this.addCommentListener = addCommentListener;

    }

    @Override
    public PostFeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_post_feed, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostFeedsAdapter.ViewHolder holder, int position) {

        holder.txtPostCreaterName.setText(listOfPostFeeds.get(position).getUsername());
        holder.txtPostContent.setText(listOfPostFeeds.get(position).getFeedText());
        holder.txtPostTotalLikeCount.setText(listOfPostFeeds.get(position).getTotalLike());
        holder.txtPostTotalCommentCount.setText(listOfPostFeeds.get(position).getTotalComment());


        if (listOfPostFeeds.get(position).getCommentList().size() > 0) {

            for (int i = 0; i < listOfPostFeeds.get(position).getCommentList().size(); i++) {


                View v = getCommetInflaterView(listOfPostFeeds.get(position).getCommentList().get(i));

                if (holder.llCommentInflater.indexOfChild(v) == -1)
                    holder.llCommentInflater.addView(v);

            }
        }

        holder.txtCommentViewAll.setTag(position);
        holder.txtCommentViewAll.setOnClickListener(viewAllCommetsListener);

        holder.txtAddComment.setTag(position);
        holder.txtAddComment.setOnClickListener(addCommentListener);

    }

    @Override
    public int getItemCount() {
        return listOfPostFeeds.size();
    }

    public void addAll(ArrayList<Data> data) {

        try {
            this.listOfPostFeeds.clear();
            this.listOfPostFeeds.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgDpPostCreator;
        TextView txtPostCreaterName, txtPostContent, txtPostTotalLikeCount, txtPostTotalCommentCount, txtCommentViewAll, txtAddComment;
        ImageView imgPostLike, imgPostComment, imgPostTag;
        EditText etWriteComment;
        LinearLayout llCommentInflater;


        public ViewHolder(View itemView) {
            super(itemView);
            imgDpPostCreator = (CircleImageView) itemView
                    .findViewById(R.id.img_dp_post_creator);
            txtPostCreaterName = (TextView) itemView.findViewById(R.id.txt_post_creater_name);
            txtPostContent = (TextView) itemView.findViewById(R.id.txt_post_content);
            txtPostTotalLikeCount = (TextView) itemView.findViewById(R.id.txt_post_total_like_count);
            txtPostTotalCommentCount = (TextView) itemView.findViewById(R.id.txt_post_total_comment_count);
            txtCommentViewAll = (TextView) itemView.findViewById(R.id.txt_comment_view_all);
            imgPostLike = (ImageView) itemView.findViewById(R.id.img_post_like);
            imgPostComment = (ImageView) itemView.findViewById(R.id.img_post_comment);
            imgPostTag = (ImageView) itemView.findViewById(R.id.img_post_tag);
            etWriteComment = (EditText) itemView.findViewById(R.id.et_writeComment);
            llCommentInflater = (LinearLayout) itemView.findViewById(R.id.ll_comment_inflater);
            txtAddComment = (TextView) itemView.findViewById(R.id.txt_add_comment);

        }
    }

    private View getCommetInflaterView(GetAllFeedsComment commentData) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        CommentsViewHolder holder;
        View v = null;

        if (v == null) {

            v = layoutInflater.inflate(R.layout.row_post_commenter, null, false);
            holder = new CommentsViewHolder();
            holder.txtCommenterUsername = (TextView) v.findViewById(R.id.txt_commenter_username);
            holder.txtCommenterComment = (TextView) v.findViewById(R.id.txt_commenter_comment);
            holder.txtCommentDuration = (TextView) v.findViewById(R.id.txt_comment_duration);
            v.setTag(holder);
        } else {

            holder = (CommentsViewHolder) v.getTag();
        }

        return v;


    }


    public class CommentsViewHolder {

        TextView txtCommenterUsername;
        TextView txtCommenterComment;
        TextView txtCommentDuration;

    }

}
