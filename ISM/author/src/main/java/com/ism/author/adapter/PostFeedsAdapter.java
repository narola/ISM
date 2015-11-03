package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utils;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.ImageLoaderInit;
import com.ism.author.model.AddCommentRequest;
import com.ism.author.model.Data;
import com.ism.author.model.FeedImages;
import com.ism.author.model.GetAllCommentRequest;
import com.ism.author.model.GetAllFeedsComment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these adapter class is for getting all the postfeeds.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();
    private ImageLoader imageLoader;

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
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        PreferenceData.clearPreference(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_post_feed, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtPostCreaterName.setText(listOfPostFeeds.get(position).getFullName());
        holder.txtPostContent.setText(listOfPostFeeds.get(position).getFeedText());
        holder.txtPostTotalLikeCount.setText(listOfPostFeeds.get(position).getTotalLike());
        holder.txtPostTotalCommentCount.setText(listOfPostFeeds.get(position).getTotalComment());
        if (listOfPostFeeds.get(position).getFeedText().length() > 250) {
            holder.txtPostContent.setLines(4);
        }
        if(listOfPostFeeds.get(position).getTotalComment().equals("0") || listOfPostFeeds.get(position).getTotalComment().equals("1")){
            holder.txtCommentViewAll.setVisibility(View.GONE);
        }else{
            holder.txtCommentViewAll.setVisibility(View.VISIBLE);
        }
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

                    GetAllCommentRequest getAllCommentRequest = new GetAllCommentRequest();
                    getAllCommentRequest.setFeed_id(listOfPostFeeds.get(position).getFeedId());
                    ((HomeFragment) fragment).callGetAllComments(getAllCommentRequest);
//                } else {
//
//                    Utils.showToast(mContext.getString(R.string.strnocomments), mContext);
//                }


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
                    addCommentRequest.setComment_by(WebConstants.TEST_USER_ID);
                    addCommentRequest.setFeed_id(listOfPostFeeds.get(position).getFeedId());
                    ((HomeFragment) fragment).callAddComment(addCommentRequest);
                    int totalComment=Integer.parseInt(holder.txtPostTotalCommentCount.getText().toString())+1;
                    if(totalComment>2){
                        holder.txtCommentViewAll.setVisibility(View.VISIBLE);
                    }
                    holder.txtPostTotalCommentCount.setText(""+totalComment);
                    holder.etWriteComment.setText("");

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
                ((HomeFragment) fragment).tagFriendInFeedRequest.setFeed_id(WebConstants.TEST_FEEDID);
                ((HomeFragment) fragment).tagFriendInFeedRequest.setTagged_by(WebConstants.TEST_TAGGED_BY);
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

                Log.e(TAG, "Pref for like==" + PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, mContext, ""));
                Log.e(TAG, "Pref for unlike==" + PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, mContext, ""));


            }
        });
        holder.imgVideo.setVisibility(View.GONE);
        holder.imgPlay.setVisibility(View.GONE);
        holder.imgAudio.setVisibility(View.GONE);
        holder.imgImage.setVisibility(View.GONE);

       // holder.rlImage.setVisibility(View.GONE);
        //video
        if (listOfPostFeeds.get(position).getVideo_thumbnail() != "") {
            // WebConstants.FEED_MEDIA
//            holder.rlImage.setVisibility(View.VISIBLE);
            holder.imgVideo.setVisibility(View.VISIBLE);
            holder.imgPlay.setVisibility(View.VISIBLE);

            Log.i(TAG, WebConstants.FEED_MEDIA + listOfPostFeeds.get(position).getVideo_thumbnail() + "");
            imageLoader.displayImage(WebConstants.FEED_MEDIA + listOfPostFeeds.get(position).getVideo_thumbnail(), holder.imgVideo, ImageLoaderInit.options);

        }
        //audio
        if (listOfPostFeeds.get(position).getAudioLink() != "") {
            // WebConstants.FEED_MEDIA
//            holder.rlImage.setVisibility(View.GONE);
            holder.imgAudio.setVisibility(View.VISIBLE);
        }
        // images
        if (listOfPostFeeds.get(position).getFeed_images().size() != 0) {
            // WebConstants.FEED_MEDIA
//            holder.rlImage.setVisibility(View.GONE);
            holder.imgImage.setVisibility(View.VISIBLE);

            ArrayList<FeedImages> listImages = new ArrayList<FeedImages>();
            listImages = listOfPostFeeds.get(position).getFeed_images();
            for (int i = 0; i < listImages.size(); i++) {
                Log.i(TAG, WebConstants.FEED_MEDIA + listImages.get(i).getImage_link() + "");
//                ImageView imageView=new ImageView(mContext);
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(200,200));
                imageLoader.displayImage(WebConstants.FEED_MEDIA + listImages.get(i).getImage_link(), holder.imgImage, ImageLoaderInit.options);
                //holder.llMediaFiles.addView(imageView);
            }

        }


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
        ImageView imgPostLike, imgPostComment, imgPostTag, imgAudio, imgImage, imgPlay, imgVideo;
        EditText etWriteComment;
        LinearLayout llCommentInflater, llMediaFiles;
        RelativeLayout rlImage;

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
            imgAudio = (ImageView) itemView.findViewById(R.id.img_audio);
            imgImage = (ImageView) itemView.findViewById(R.id.img_image);
            imgVideo = (ImageView) itemView.findViewById(R.id.img_video);
            imgPlay = (ImageView) itemView.findViewById(R.id.img_play);
            imgPostComment = (ImageView) itemView.findViewById(R.id.img_post_comment);
            imgPostTag = (ImageView) itemView.findViewById(R.id.img_post_tag);
            etWriteComment = (EditText) itemView.findViewById(R.id.et_writeComment);
            llCommentInflater = (LinearLayout) itemView.findViewById(R.id.ll_comment_inflater);
            llMediaFiles = (LinearLayout) itemView.findViewById(R.id.ll_media);
            txtAddComment = (TextView) itemView.findViewById(R.id.txt_add_comment);
            rlImage = (RelativeLayout) itemView.findViewById(R.id.rl_image);

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
