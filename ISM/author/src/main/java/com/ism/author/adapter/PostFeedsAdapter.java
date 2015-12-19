package com.ism.author.adapter;

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

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.constant.AppConstant;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.TagUserDialog;
import com.ism.author.dialog.ViewAllCommentsDialog;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.CommentList;
import com.ism.author.ws.model.FeedImages;
import com.ism.author.ws.model.Feeds;

import java.util.ArrayList;

/**
 * these adapter class is for getting all the postfeeds.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse, TagUserDialog.TagUserListener {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();


    private Context mContext;
    private ArrayList<Feeds> arrListFeeds = new ArrayList<Feeds>();
    String likePrefData, unlikePrefData;
    private int addCommentFeedPosition = -1;
    private int tagFeedPosition = -1;
    private LayoutInflater inflater;


    public PostFeedsAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_post_feed, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListFeeds.get(position).getProfilePic(),
                holder.imgDpPostCreator, ISMAuthor.options);
        holder.txtPostCreaterName.setText(arrListFeeds.get(position).getFullName());
        holder.txtPostContent.setText(arrListFeeds.get(position).getFeedText());
        holder.txtPostTotalLikeCount.setText(arrListFeeds.get(position).getTotalLike());
        holder.txtPostTotalCommentCount.setText(arrListFeeds.get(position).getTotalComment());
        if (arrListFeeds.get(position).getFeedText().length() > 250) {
            holder.txtPostContent.setLines(4);
        }
        if (addCommentFeedPosition != -1 && addCommentFeedPosition == position) {
            holder.etWriteComment.setText("");
            addCommentFeedPosition = -1;
        }

        if (Integer.valueOf(arrListFeeds.get(position).getLike()) == 1) {
            holder.imgPostLike.setSelected(true);
        } else {
            holder.imgPostLike.setSelected(false);
        }

        holder.llCommentInflater.removeAllViews();


        if (arrListFeeds.get(position).getCommentList() != null) {

            holder.txtCommentViewAll.setVisibility(Integer.parseInt(arrListFeeds.get(position).getTotalComment()) > 0 ? View.VISIBLE : View.GONE);
            if (holder.llCommentInflater.getChildCount() == 0) {
                for (int i = 0; i < arrListFeeds.get(position).getCommentList().size(); i++) {
                    if (i <= 1) {

                        View v = getCommetInflaterView(arrListFeeds.get(position).getCommentList().get(i));
                        holder.llCommentInflater.addView(v);

                    } else {
                        break;
                    }
                }
            }
        }

        holder.txtCommentViewAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                callApiGetAllComments(position);

            }
        });


        holder.txtAddComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = holder.etWriteComment.getText().toString().trim();


                if (comment != null && comment.length() > 0) {
                    callApiAddComment(position, comment);
                }


            }
        });

        holder.imgPostTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utility.isConnected(getActivity())) {
                    tagFeedPosition = position;
                    callApiGetStudyMates();
                } else {
                    Utility.toastOffline(getActivity());
                }


            }
        });

        holder.imgPostLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, mContext, "");
                unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, mContext, "");

                holder.imgPostLike.setSelected(!holder.imgPostLike.isSelected());

                if (holder.imgPostLike.isSelected()) {
                    arrListFeeds.get(position).setLike(String.valueOf(AppConstant.LIKE));
                    arrListFeeds.get(position).setTotalLike(String.valueOf(Integer.parseInt(arrListFeeds.get(position).getTotalLike()) + 1));

                    setPrefForLike(arrListFeeds.get(position).getFeedId() + ",");
                } else {
                    arrListFeeds.get(position).setLike(String.valueOf(AppConstant.DISLIKE));
                    arrListFeeds.get(position).setTotalLike(String.valueOf(Integer.parseInt(arrListFeeds.get(position).getTotalLike()) - 1));

                    setPrefForUnlike(arrListFeeds.get(position).getFeedId() + ",");

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
        if (arrListFeeds.get(position).getVideoThumbnail() != "") {

            holder.imgVideo.setVisibility(View.VISIBLE);
            holder.imgPlay.setVisibility(View.VISIBLE);

            Log.i(TAG, WebConstants.FEED_MEDIA + arrListFeeds.get(position).getVideoThumbnail() + "");
            Global.imageLoader.displayImage(WebConstants.FEED_MEDIA + arrListFeeds.get(position).getVideoThumbnail(), holder.imgVideo, ISMAuthor.options);

        }
        //audio
        if (arrListFeeds.get(position).getAudioLink() != "") {

            holder.imgAudio.setVisibility(View.VISIBLE);
        }
        // images
        if (arrListFeeds.get(position).getFeedImages().size() != 0) {

            holder.imgImage.setVisibility(View.VISIBLE);

            ArrayList<FeedImages> feedImages = new ArrayList<FeedImages>();
            feedImages = arrListFeeds.get(position).getFeedImages();
            for (int i = 0; i < feedImages.size(); i++) {
                Log.i(TAG, WebConstants.FEED_MEDIA + feedImages.get(i).getImageLink() + "");
                Global.imageLoader.displayImage(WebConstants.FEED_MEDIA + feedImages.get(i).getImageLink(), holder.imgImage, ISMAuthor.options);

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
        return arrListFeeds.size();
    }

    public void addAll(ArrayList<Feeds> feeds) {
        try {
            this.arrListFeeds.clear();
            this.arrListFeeds.addAll(feeds);
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

    private View getCommetInflaterView(CommentList commentList) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View v;
        v = layoutInflater.inflate(R.layout.row_post_commenter, null, false);

        TextView txtCommenterUsername = (TextView) v.findViewById(R.id.txt_commenter_username);
        TextView txtCommenterComment = (TextView) v.findViewById(R.id.txt_commenter_comment);
        TextView txtCommentDuration = (TextView) v.findViewById(R.id.txt_comment_duration);

        ImageView imgCommenterDp = (ImageView) v.findViewById(R.id.img_commenter_dp);


        txtCommenterUsername.setText(commentList.getFullName());
        txtCommenterComment.setText(commentList.getComment());
        txtCommentDuration.setText("5 min");


        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + commentList.getProfilePic(), imgCommenterDp, ISMAuthor.options);

        return v;
    }

    private void callApiAddComment(int position, String comment) {
        if (Utility.isConnected(getActivity())) {
            try {
                addCommentFeedPosition = position;
                Attribute attribute = new Attribute();
                attribute.setFeedId(arrListFeeds.get(position).getFeedId());
                attribute.setCommentBy(Global.strUserId);
                attribute.setComment(comment);


                if (arrListFeeds.get(position).getCommentList().size() < 2) {
                    CommentList commentToAdd = new CommentList();
                    commentToAdd.setComment(comment);
                    arrListFeeds.get(position).getCommentList().add(commentToAdd);
                }


                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.ADDCOMMENT);
            } catch (Exception e) {
                Log.e(TAG, "callApiAddComment Exception : " + e.toString());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiGetAllComments(int position) {
        if (Utility.isConnected(getActivity())) {
            try {

                Attribute attribute = new Attribute();
                attribute.setFeedId(arrListFeeds.get(position).getFeedId());
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GETALLCOMMENTS);

            } catch (Exception e) {
                Debug.i(TAG + getActivity().getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    private void callApiGetStudyMates() {

        if (Utility.isConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setUserId(WebConstants.TEST_GETSTUDYMATES);
                new WebserviceWrapper(getActivity(), attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETSTUDYMATES);
            } catch (Exception e) {
                Log.i(TAG + getActivity().getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utility.toastOffline(getActivity());
        }
    }


    @Override
    public void tagUsers(String[] arrTagUser) {

        if (Utility.isConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setFeedId(arrListFeeds.get(tagFeedPosition).getFeedId());
//                attribute.setTaggedBy(WebConstants.TEST_USER_ID);
                attribute.setTaggedBy(Global.strUserId);
                attribute.setTaggedUserIds(arrTagUser);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.TAGFRIENDINFEED);
            } catch (Exception e) {
                Log.e(TAG, "callApiTagUsers Exception : " + e.toString());
            }
        } else {
            Utility.toastOffline(getActivity());
        }


    }


    private Context getActivity() {
        return mContext;

    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            if (object != null) {
                switch (apiCode) {
                    case WebConstants.ADDCOMMENT:
                        onResponseAddComment(object);
                        break;
                    case WebConstants.GETALLCOMMENTS:
                        onResponseGetAllComments(object);
                        break;
                    case WebConstants.GETSTUDYMATES:
                        onResponseGetAllStudyMates(object);
                        break;
                    case WebConstants.TAGFRIENDINFEED:
                        onResponseTagStudyMates(object);
                        break;
                }
            } else {
                Debug.e(TAG, "onResponse ApiCall Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponse Exception : " + e.toString());
        }

    }


    private void onResponseAddComment(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                Utils.showToast(getActivity().getString(R.string.msg_success_comment_add), getActivity());
                arrListFeeds.get(addCommentFeedPosition).
                        setTotalComment("" + (Integer.parseInt(arrListFeeds.get(addCommentFeedPosition).getTotalComment()) + 1));


                if (arrListFeeds.get(addCommentFeedPosition).getCommentList().size() <= 2) {

                    CommentList addedComment = arrListFeeds.get(addCommentFeedPosition).getCommentList().
                            get(arrListFeeds.get(addCommentFeedPosition).getCommentList().size() - 1);

                    addedComment.setId(arrListFeeds.get(addCommentFeedPosition).getFeedId());
                    addedComment.setCommentBy(Global.strUserId);
                    addedComment.setFullName(Global.strFullName);
                    addedComment.setProfilePic(Global.strProfilePic);
                }

                notifyDataSetChanged();

                Utils.showToast(getActivity().getString(R.string.msg_success_comment_add), getActivity());
            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {

                arrListFeeds.get(addCommentFeedPosition).getCommentList().remove(arrListFeeds.get(addCommentFeedPosition).getCommentList().size() - 1);
                Utils.showToast(getActivity().getString(R.string.msg_failed_comment), getActivity());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseAddComment Exception : " + e.toString());
        }
    }

    private void onResponseGetAllComments(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                Debug.e(TAG, "onResponseGetAllComments success  : " + responseHandler.getComments().size());
                ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseHandler.getComments());
                viewAllCommentsDialog.show();
            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                Utils.showToast(getActivity().getString(R.string.msg_failed_comment), getActivity());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllComments Exception : " + e.toString());
        }
    }

    private void onResponseGetAllStudyMates(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                if (responseHandler.getStudymates().size() > 0) {
                    TagUserDialog tagUserDialog = new TagUserDialog(getActivity(), responseHandler.getStudymates(), this);
                    tagUserDialog.show();
                } else {
                    Utils.showToast(getActivity().getString(R.string.msg_no_studymates), getActivity());
                }
            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                Utils.showToast(getActivity().getString(R.string.msg_failed_comment), getActivity());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseGetAllStudyMates Exception : " + e.toString());
        }
    }

    private void onResponseTagStudyMates(Object object) {
        try {
            ResponseHandler responseObj = (ResponseHandler) object;
            tagFeedPosition = -1;
            if (responseObj.getStatus().equals(ResponseHandler.SUCCESS)) {
                Utils.showToast(getActivity().getString(R.string.msg_tag_done), getActivity());
            } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                Utils.showToast(getActivity().getString(R.string.msg_tag_failed), getActivity());

            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseTagStudyMates Exception : " + e.toString());
        }
    }


}
