package com.ism.author.adapter;

import android.app.Fragment;
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
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utils;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.helper.CircleImageView;
import com.ism.author.login.Urls;
import com.ism.author.model.AddCommentRequest;
import com.ism.author.model.Data;
import com.ism.author.model.GetAllCommentRequest;
import com.ism.author.model.GetAllFeedsComment;
import com.ism.utility.Debug;

import java.util.ArrayList;

/**
 * these adapter class is for getting all the postfeeds.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();

    Context mContext;
    Fragment fragment;
    ArrayList<Data> listOfPostFeeds = new ArrayList<Data>();
    String likePrefData, unlikePrefData;

    public PostFeedsAdapter(Context context) {
        this.mContext = context;
    }

    public PostFeedsAdapter(Fragment fragment, Context context) {
        this.mContext = context;
        this.fragment = fragment;

        PreferenceData.clearPreference(mContext);
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
    public void onBindViewHolder(final PostFeedsAdapter.ViewHolder holder, final int position) {

        holder.txtPostCreaterName.setText(listOfPostFeeds.get(position).getFullName());
        holder.txtPostContent.setText(listOfPostFeeds.get(position).getFeedText());
        holder.txtPostTotalLikeCount.setText(listOfPostFeeds.get(position).getTotalLike());
        holder.txtPostTotalCommentCount.setText(listOfPostFeeds.get(position).getTotalComment());


        if (listOfPostFeeds.get(position).getLike() == 1) {
            holder.imgPostLike.setSelected(true);
        } else {
            holder.imgPostLike.setSelected(false);
        }

        holder.llCommentInflater.removeAllViews();
        if (holder.llCommentInflater.getChildCount() == 0) {
            for (int i = 0; i < listOfPostFeeds.get(position).getCommentList().size(); i++) {
                if (i <= 1) {

                    View v = getCommetInflaterView(listOfPostFeeds.get(position).getCommentList().get(i));
                    holder.llCommentInflater.addView(v);

                } else {
                    break;
                }
            }
        }

        holder.txtCommentViewAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listOfPostFeeds.get(position).getCommentList().size() > 0) {
                    GetAllCommentRequest getAllCommentRequest = new GetAllCommentRequest();
                    getAllCommentRequest.setFeed_id(listOfPostFeeds.get(position).getFeedId());
                    ((HomeFragment) fragment).callGetAllComments(getAllCommentRequest);
                } else {
                    Utils.showToast(mContext.getString(R.string.strnocomments), mContext);
                }


            }
        });


        holder.txtAddComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (holder.etWriteComment.getText() != null
                        && holder.etWriteComment.getText().toString().trim().length() != 0) {

                    ((HomeFragment) fragment).setSetAddCommentRowPosition(position);
                    AddCommentRequest addCommentRequest = new AddCommentRequest();
                    addCommentRequest.setComment(holder.etWriteComment.getText().toString());
//                   addCommentRequest.setComment_by(listOfPostFeeds.get(position).getUserId());
                    addCommentRequest.setComment_by(Urls.TEST_USER_ID);
                    addCommentRequest.setFeed_id(listOfPostFeeds.get(position).getFeedId());
                    ((HomeFragment) fragment).callAddComment(addCommentRequest);

                } else {
                    Utils.showToast(mContext.getString(R.string.stremptycomment), mContext);
                }


            }
        });

        holder.imgPostTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //this is for the real data
//                ((HomeFragment) fragment).tagFriendInFeedRequest.setFeed_id(listOfPostFeeds.get(position).getFeedId());
//                ((HomeFragment) fragment).tagFriendInFeedRequest.setTagged_by(listOfPostFeeds.get(position).getUserId());

                //this is testing data.
                ((HomeFragment) fragment).tagFriendInFeedRequest.setFeed_id(Urls.TEST_FEEDID);
                ((HomeFragment) fragment).tagFriendInFeedRequest.setTagged_by(Urls.TEST_TAGGED_BY);
                ((HomeFragment) fragment).callGetStudyMates();
            }
        });

        holder.imgPostLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, mContext, "");
                unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, mContext, "");

                holder.imgPostLike.setSelected(!holder.imgPostLike.isSelected());

                if (holder.imgPostLike.isSelected()) {
                    listOfPostFeeds.get(position).setLike(1);
                    listOfPostFeeds.get(position).setTotalLike(String.valueOf(Integer.parseInt(listOfPostFeeds.get(position).getTotalLike()) + 1));

                    setPrefForLike(listOfPostFeeds.get(position).getFeedId() + ",");
                } else {
                    listOfPostFeeds.get(position).setLike(0);
                    listOfPostFeeds.get(position).setTotalLike(String.valueOf(Integer.parseInt(listOfPostFeeds.get(position).getTotalLike()) - 1));

                    setPrefForUnlike(listOfPostFeeds.get(position).getFeedId() + ",");

                }
                notifyDataSetChanged();

                Debug.e(TAG, "Pref for like==" + PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, mContext, ""));
                Debug.e(TAG, "Pref for unlike==" + PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, mContext, ""));


            }
        });


    }


    private void setPrefForLike(String feed_id) {

        PreferenceData.setStringPrefs(PreferenceData.LIKE_ID_LIST, mContext, likePrefData + feed_id);
        if (unlikePrefData != null && unlikePrefData.length() > 0) {
            PreferenceData.setStringPrefs(PreferenceData.UNLIKE_ID_LIST, mContext, unlikePrefData.replaceAll(feed_id, ""));
        }


    }

    private void setPrefForUnlike(String feed_id) {

        PreferenceData.setStringPrefs(PreferenceData.UNLIKE_ID_LIST, mContext, unlikePrefData + feed_id);
        if (likePrefData != null && likePrefData.length() > 0) {
            PreferenceData.setStringPrefs(PreferenceData.LIKE_ID_LIST, mContext, likePrefData.replaceAll(feed_id, ""));
        }

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

        holder.txtCommenterUsername.setText(commentData.getUsername());
        holder.txtCommenterComment.setText(commentData.getComment());
        holder.txtCommentDuration.setText(commentData.getCommentBy());

        return v;
    }


    public class CommentsViewHolder {

        TextView txtCommenterUsername;
        TextView txtCommenterComment;
        TextView txtCommentDuration;

    }


}
