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
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.activtiy.PostFeedActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.TagUserDialog;
import com.ism.author.dialog.ViewAllCommentsDialog;
import com.ism.author.object.Global;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;

import io.realm.RealmList;
import io.realm.RealmResults;
import model.ROFeedImage;
import model.ROFeedLike;
import model.ROFeeds;
import model.ROFeedComment;
import model.ROUser;
import realmhelper.AuthorHelper;

/**
 * Adapter to display postfeed.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse,
        TagUserDialog.TagUserListener {


    private static final String TAG = PostFeedsAdapter.class.getSimpleName();
    private Context mContext;
    private RealmResults<ROFeeds> arrListFeeds = null;

    private LayoutInflater inflater;
    private AuthorHelper authorHelper;
    private int addCommentFeedPosition = -1;
    private int tagFeedPosition = -1;
    private String comment = "";


    public PostFeedsAdapter(Context context, AuthorHelper authorHelper) {
        this.mContext = context;
        this.authorHelper = authorHelper;
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

        if (arrListFeeds.get(position).getFeedBy().getProfilePicture() != null && arrListFeeds.get(position).getFeedBy().getProfilePicture() != "") {
            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListFeeds.get(position).getFeedBy().getProfilePicture(),
                    holder.imgDpPostCreator, ISMAuthor.options);
        } else {
            holder.imgDpPostCreator.setImageResource(R.drawable.userdp);
        }

        holder.txtPostCreaterName.setText(arrListFeeds.get(position).getFeedBy().getFullName());
        holder.txtPostContent.setText(arrListFeeds.get(position).getFeedText());
        holder.txtPostTotalLikeCount.setText(String.valueOf(arrListFeeds.get(position).getTotalLike()));
        holder.txtPostTotalCommentCount.setText(String.valueOf(arrListFeeds.get(position).getTotalComment()));


        if (addCommentFeedPosition != -1 && addCommentFeedPosition == position) {
            holder.etWriteComment.setText("");
            addCommentFeedPosition = -1;
        }

        if (Integer.valueOf(arrListFeeds.get(position).getSelfLike()) == 1) {
            holder.imgPostLike.setSelected(true);
        } else {
            holder.imgPostLike.setSelected(false);
        }

        holder.llCommentInflater.removeAllViews();

        if (arrListFeeds.get(position).getRoFeedComment() != null) {

            holder.txtCommentViewAll.setVisibility(arrListFeeds.get(position).getTotalComment() > 0 ? View.VISIBLE : View.GONE);
            if (holder.llCommentInflater.getChildCount() == 0) {
                for (int i = 0; i < arrListFeeds.get(position).getRoFeedComment().size(); i++) {
                    if (i <= 1) {
                        View v = getCommetInflaterView(arrListFeeds.get(position).getRoFeedComment().get(i));
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

                comment = holder.etWriteComment.getText().toString().trim();
                if (comment != null && comment.length() > 0) {
                    callApiAddComment(position);
                    holder.etWriteComment.setText("");
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
                    Utility.alertOffline(getActivity());
                }


            }
        });

        holder.imgPostLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.imgPostLike.setSelected(!holder.imgPostLike.isSelected());
                authorHelper.updateFeedSelfLikeStatus(arrListFeeds.get(position).getFeedId(), holder.imgPostLike.isSelected() ? "1" : "0");
                addAll();
                insertUpdateLikeFeedData(position, holder.imgPostLike.isSelected() ? 1 : 0);
            }
        });


        holder.imgVideo.setVisibility(View.GONE);
        holder.imgPlay.setVisibility(View.GONE);
        holder.imgAudio.setVisibility(View.GONE);
        holder.imgImage.setVisibility(View.GONE);

        // holder.rlImage.setVisibility(View.GONE);
        //video
        if (arrListFeeds.get(position).getVideoThumbnail() != "") {

            // holder.imgVideo.setVisibility(View.VISIBLE);
            // holder.imgPlay.setVisibility(View.VISIBLE);
            // holder.rlImage.addView(getMediaFilesView(arrListFeeds.get(position).getVideoThumbnail(), PostFeedActivity.VIDEO));
            Log.i(TAG, WebConstants.FEED_MEDIA_KINJAL + arrListFeeds.get(position).getVideoThumbnail() + "");
            //Global.imageLoader.displayImage(WebConstants.FEED_MEDIA_KINJAL + arrListFeeds.get(position).getVideoThumbnail(), holder.imgVideo, ISMAuthor.options);

        }
        //audio
        if (arrListFeeds.get(position).getAudioLink() != "") {
            //  holder.rlImage.addView(getMediaFilesView(arrListFeeds.get(position).getAudioLink(), PostFeedActivity.AUDIO));
            // holder.imgAudio.setVisibility(View.VISIBLE);
        }
        // images
        if (arrListFeeds.get(position).getRoFeedImages().size() != 0) {

            // holder.imgImage.setVisibility(View.VISIBLE);

            RealmList<ROFeedImage> ROFeedImages = new RealmList<>();
            ROFeedImages = arrListFeeds.get(position).getRoFeedImages();
            for (int i = 0; i < ROFeedImages.size(); i++) {
                Log.i(TAG, WebConstants.FEED_MEDIA_KINJAL + ROFeedImages.get(i).getImageLink() + "");
                //  Global.imageLoader.displayImage(WebConstants.FEED_MEDIA_KINJAL + ROFeedImages.get(i).getImageLink(), holder.imgImage, ISMAuthor.options);
                // holder.rlImage.addView(getMediaFilesView(ROFeedImages.get(i).getImageLink(), PostFeedActivity.IMAGE));
            }

        }


    }

    @Override
    public int getItemCount() {

        if (arrListFeeds != null) {
            return arrListFeeds.size();
        } else {
            return 0;
        }

    }

    public void addAll() {

        try {
            addCommentFeedPosition = -1;
            tagFeedPosition = -1;
            comment = "";
            arrListFeeds = authorHelper.getAllPostFeeds();
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        CircleImageView imgDpPostCreator;
        TextView txtPostCreaterName, txtPostContent, txtPostTotalLikeCount, txtPostTotalCommentCount, txtCommentViewAll, txtAddComment;
        ImageView imgPostLike, imgPostComment, imgPostTag, imgAudio, imgImage, imgPlay, imgVideo;
        EditText etWriteComment;
        LinearLayout llCommentInflater, llMediaFiles;
        LinearLayout rlImage;

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
            rlImage = (LinearLayout) itemView.findViewById(R.id.rl_image);

            txtPostCreaterName.setTypeface(Global.myTypeFace.getRalewayBold());
            txtPostContent.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtPostTotalLikeCount.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtPostTotalCommentCount.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtCommentViewAll.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtAddComment.setTypeface(Global.myTypeFace.getRalewayRegular());
            etWriteComment.setTypeface(Global.myTypeFace.getRalewayRegular());


        }
    }

    private View getCommetInflaterView(ROFeedComment ROFeedComment) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View v;
        v = layoutInflater.inflate(R.layout.row_post_commenter, null, false);

        TextView txtCommenterUsername = (TextView) v.findViewById(R.id.txt_commenter_username);
        TextView txtCommenterComment = (TextView) v.findViewById(R.id.txt_commenter_comment);
        TextView txtCommentDuration = (TextView) v.findViewById(R.id.txt_comment_duration);

        ImageView imgCommenterDp = (ImageView) v.findViewById(R.id.img_commenter_dp);


        txtCommenterUsername.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtCommenterComment.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtCommentDuration.setTypeface(Global.myTypeFace.getRalewayRegular());


        txtCommenterUsername.setText(ROFeedComment.getCommentBy().getFullName());
        txtCommenterComment.setText(ROFeedComment.getComment());
        txtCommentDuration.setText(com.ism.commonsource.utility.Utility.getTimeDuration(ROFeedComment.getCreatedDate()));


        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + ROFeedComment.getCommentBy().getProfilePicture(),
                imgCommenterDp, Utility.getDisplayImageOption(R.drawable.userdp, R.drawable.userdp));


        return v;
    }

    private View getMediaFilesView(String filepath, String type) {
        View view = null;
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.item_media_file_post, null);
            if (type.equals(PostFeedActivity.AUDIO))
                ((ImageView) view.findViewById(R.id.image)).setImageResource(R.drawable.audioplay);
            else if (type.equals(PostFeedActivity.VIDEO)) {
                Global.imageLoader.displayImage(WebConstants.FEED_MEDIA_KINJAL + filepath,
                        (ImageView) view.findViewById(R.id.image), ISMAuthor.options);
                ((ImageView) view.findViewById(R.id.image_play)).setVisibility(View.VISIBLE);
            } else if (type.equals(PostFeedActivity.IMAGE)) {
                Global.imageLoader.displayImage(WebConstants.FEED_MEDIA_KINJAL + filepath,
                        (ImageView) view.findViewById(R.id.image), ISMAuthor.options);
            }
        } catch (Exception e) {
            Log.e(TAG, "getMediaFilesView Exception : " + e.toString());
        }
        return view;
    }

    private void callApiAddComment(int position) {

        if (Utility.isConnected(getActivity())) {
            try {
                addCommentFeedPosition = position;

                Attribute attribute = new Attribute();
                attribute.setFeedId(arrListFeeds.get(position).getFeedId());
                attribute.setCommentBy(Global.strUserId);
                attribute.setComment(comment);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.ADDCOMMENT);
            } catch (Exception e) {
                Log.e(TAG, "callApiAddComment Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
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
            Utility.alertOffline(getActivity());
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
            Utility.alertOffline(getActivity());
        }
    }


    @Override
    public void tagUsers(String[] arrTagUser) {
        if (Utility.isConnected(getActivity())) {
            try {
                Attribute attribute = new Attribute();
                attribute.setFeedId(arrListFeeds.get(tagFeedPosition).getFeedId());
                attribute.setTaggedBy(Global.strUserId);
                attribute.setTaggedUserIds(arrTagUser);

                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
                        .execute(WebConstants.TAGFRIENDINFEED);
            } catch (Exception e) {
                Log.e(TAG, "callApiTagUsers Exception : " + e.toString());
            }
        } else {
            Utility.alertOffline(getActivity());
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

                Utility.showToast(getActivity().getString(R.string.msg_success_comment_add), getActivity());

                ROFeedComment ROFeedComment = new ROFeedComment();
                ROFeedComment.setFeedCommentId(responseHandler.getComment().get(0).getCommentId());
                ROFeedComment.setComment(comment);
                ROFeedComment.setCreatedDate(null);

                ROUser commentBy = new ROUser();
                commentBy.setUserId(Integer.valueOf(Global.strUserId));
                commentBy.setProfilePicture(Global.strProfilePic);
                commentBy.setFullName(Global.strFullName);

                ROFeedComment.setCommentBy(commentBy);
                ROFeedComment.setRoFeed(arrListFeeds.get(addCommentFeedPosition));

                authorHelper.addComment(arrListFeeds.get(addCommentFeedPosition).getFeedId(), ROFeedComment);

                addAll();
            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                Utility.showToast(getActivity().getString(R.string.msg_failed_comment), getActivity());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseAddComment Exception : " + e.toString());
        }
    }

    private void onResponseGetAllComments(Object object) {
        try {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

                ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseHandler.getComments());
                viewAllCommentsDialog.show();

            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                Utility.showToast(getActivity().getString(R.string.msg_failed_comment), getActivity());
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
                    Utility.showToast(getActivity().getString(R.string.msg_no_studymates), getActivity());
                }
            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                Utility.showToast(getActivity().getString(R.string.msg_failed_comment), getActivity());
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
                Utility.showToast(getActivity().getString(R.string.msg_tag_done), getActivity());
            } else if (responseObj.getStatus().equals(ResponseHandler.FAILED)) {
                Utility.showToast(getActivity().getString(R.string.msg_tag_failed), getActivity());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseTagStudyMates Exception : " + e.toString());
        }
    }


    private void insertUpdateLikeFeedData(int position, int isLike) {

        ROFeedLike ROFeedLike = new ROFeedLike();

        ROUser ROUser = new ROUser();
        ROUser.setUserId(Integer.valueOf(Global.strUserId));
        ROUser.setFullName(Global.strFullName);
        ROUser.setProfilePicture(Global.strProfilePic);

        ROFeedLike.setLikeBy(ROUser);
        ROFeedLike.setFeed(arrListFeeds.get(position));
        ROFeedLike.setCreatedDate(null);
        ROFeedLike.setModifiedDate(null);
        ROFeedLike.setIsLiked(isLike);
        ROFeedLike.setIsSync(0);

        authorHelper.insertUpdateLikeFeedData(ROFeedLike);
    }

}
