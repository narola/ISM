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
import android.widget.Toast;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.PreferenceData;
import com.ism.author.Utility.Utils;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.TagUserDialog;
import com.ism.author.dialog.ViewAllCommentsDialog;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.ImageLoaderInit;
import com.ism.author.model.Data;
import com.ism.author.model.PostFeedCommentsModel;
import com.ism.author.model.PostFeedImagesModel;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these adapter class is for getting all the postfeeds.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse, TagUserDialog.TagUserListener {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();


    private Context mContext;
    private ArrayList<Data> listOfPostFeeds = new ArrayList<Data>();
    String likePrefData, unlikePrefData;
    private ImageLoader imageLoader;
    private int addCommentFeedPosition = -1;
    private int tagFeedPosition = -1;


    public PostFeedsAdapter(Context context) {
        this.mContext = context;
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

        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgDpPostCreator, ISMAuthor.options);
        holder.txtPostCreaterName.setText(listOfPostFeeds.get(position).getFullName());
        holder.txtPostContent.setText(listOfPostFeeds.get(position).getFeedText());
        holder.txtPostTotalLikeCount.setText(listOfPostFeeds.get(position).getTotalLike());
        holder.txtPostTotalCommentCount.setText(listOfPostFeeds.get(position).getTotalComment());
        if (listOfPostFeeds.get(position).getFeedText().length() > 250) {
            holder.txtPostContent.setLines(4);
        }
        if (addCommentFeedPosition != -1 && addCommentFeedPosition == position) {
            holder.etWriteComment.setText("");
            addCommentFeedPosition = -1;
        }

        if (listOfPostFeeds.get(position).getLike() == 1) {
            holder.imgPostLike.setSelected(true);
        } else {
            holder.imgPostLike.setSelected(false);
        }

        holder.llCommentInflater.removeAllViews();


        if (listOfPostFeeds.get(position).getCommentList() != null) {

            holder.txtCommentViewAll.setVisibility(Integer.parseInt(listOfPostFeeds.get(position).getTotalComment()) > 0 ? View.VISIBLE : View.GONE);
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
                    callApiComment(position, comment);
                }


            }
        });

        holder.imgPostTag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
                    tagFeedPosition = position;
                    callApiGetStudyMates();
                } else {
                    Utils.showToast(getActivity().getString(R.string.strnetissue), getActivity());
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

            holder.imgVideo.setVisibility(View.VISIBLE);
            holder.imgPlay.setVisibility(View.VISIBLE);

            Log.i(TAG, WebConstants.FEED_MEDIA + listOfPostFeeds.get(position).getVideo_thumbnail() + "");
            imageLoader.displayImage(WebConstants.FEED_MEDIA + listOfPostFeeds.get(position).getVideo_thumbnail(), holder.imgVideo, ImageLoaderInit.options);

        }
        //audio
        if (listOfPostFeeds.get(position).getAudioLink() != "") {

            holder.imgAudio.setVisibility(View.VISIBLE);
        }
        // images
        if (listOfPostFeeds.get(position).getFeed_images().size() != 0) {

            holder.imgImage.setVisibility(View.VISIBLE);

            ArrayList<PostFeedImagesModel> listImages = new ArrayList<PostFeedImagesModel>();
            listImages = listOfPostFeeds.get(position).getFeed_images();
            for (int i = 0; i < listImages.size(); i++) {
                Log.i(TAG, WebConstants.FEED_MEDIA + listImages.get(i).getImage_link() + "");
                imageLoader.displayImage(WebConstants.FEED_MEDIA + listImages.get(i).getImage_link(), holder.imgImage, ImageLoaderInit.options);

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

    private View getCommetInflaterView(PostFeedCommentsModel commentData) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View v;
        v = layoutInflater.inflate(R.layout.row_post_commenter, null, false);

        TextView txtCommenterUsername = (TextView) v.findViewById(R.id.txt_commenter_username);
        TextView txtCommenterComment = (TextView) v.findViewById(R.id.txt_commenter_comment);
        TextView txtCommentDuration = (TextView) v.findViewById(R.id.txt_comment_duration);

        ImageView imgCommenterDp = (ImageView) v.findViewById(R.id.img_commenter_dp);


        txtCommenterUsername.setText(commentData.getUsername());
        txtCommenterComment.setText(commentData.getComment());
        txtCommentDuration.setText(commentData.getCommentBy());

        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", imgCommenterDp, ISMAuthor.options);

        return v;
    }

    private void callApiComment(int position, String comment) {
        if (Utils.isInternetConnected(getActivity())) {
            try {
                addCommentFeedPosition = position;
                RequestObject requestObject = new RequestObject();
                requestObject.setFeedId(listOfPostFeeds.get(position).getFeedId());
                requestObject.setCommentBy(WebConstants.TEST_USER_ID);
                requestObject.setComment(comment);

                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
                        .execute(WebserviceWrapper.ADDCOMMENT);
            } catch (Exception e) {
                Log.e(TAG, "callApiComment Exception : " + e.toString());
            }
        } else {
            Utils.showToast(getActivity().getString(R.string.strnetissue), getActivity());
        }
    }


    private void callApiGetAllComments(int position) {
        if (Utils.isInternetConnected(getActivity())) {
            try {

                RequestObject requestObject = new RequestObject();
                requestObject.setFeedId(listOfPostFeeds.get(position).getFeedId());

                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETALLCOMMENTS);
            } catch (Exception e) {
                Log.i(TAG + getActivity().getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getString(R.string.strnetissue), getActivity());
        }
    }


    private void callApiGetStudyMates() {

        if (Utils.isInternetConnected(getActivity())) {

            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setUserId(WebConstants.TEST_GETSTUDYMATES);
                new WebserviceWrapper(getActivity(), requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebserviceWrapper.GETSTUDYMATES);
            } catch (Exception e) {
                Log.i(TAG + getActivity().getString(R.string.strerrormessage), e.getLocalizedMessage());
            }
        } else {
            Utils.showToast(getActivity().getString(R.string.strnetissue), getActivity());
        }
    }


    @Override
    public void tagUsers(String[] arrTagUser) {

        if (Utils.isInternetConnected(getActivity())) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setFeedId(listOfPostFeeds.get(tagFeedPosition).getFeedId());
                requestObject.setTaggedBy(WebConstants.TEST_TAGGED_BY);
                requestObject.setTaggedUserIds(arrTagUser);

                new WebserviceWrapper(getActivity(), requestObject, this).new WebserviceCaller()
                        .execute(WebserviceWrapper.TAGFRIENDINFEED);
            } catch (Exception e) {
                Log.e(TAG, "callApiTagUsers Exception : " + e.toString());
            }
        } else {
            Utils.showToast(getActivity().getString(R.string.strnetissue), getActivity());
        }


    }


    private Context getActivity() {
        return mContext;

    }


    @Override
    public void onResponse(int apiMethodName, Object object, Exception error) {

        try {
            if (apiMethodName == WebserviceWrapper.ADDCOMMENT) {
                try {
                    ResponseObject responseObj = (ResponseObject) object;
                    if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                        listOfPostFeeds.get(addCommentFeedPosition).setTotalComment("" + (Integer.parseInt(listOfPostFeeds.get(addCommentFeedPosition).getTotalComment()) + 1));
                        notifyDataSetChanged();
                    } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                        Utils.showToast(responseObj.getMessage(), mContext);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponseAddComment Exception : " + e.toString());
                }
            }
            if (apiMethodName == WebserviceWrapper.GETALLCOMMENTS) {
                try {
                    ResponseObject responseObj = (ResponseObject) object;
                    if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                        ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(getActivity(), responseObj.getData());
                        viewAllCommentsDialog.show();
                    } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                        Utils.showToast(responseObj.getMessage(), getActivity());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponseGetAllComments Exception : " + e.toString());
                }
            } else if (apiMethodName == WebserviceWrapper.GETSTUDYMATES) {

                ResponseObject responseObject = (ResponseObject) object;
                if (responseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && responseObject != null) {
                    if (responseObject.getData().size() > 0) {
                        TagUserDialog tagUserDialog = new TagUserDialog(getActivity(), responseObject.getData(), this);
                        tagUserDialog.show();
                    }
                } else {
                    Utils.showToast(responseObject.getMessage(), getActivity());
                }

            } else if (apiMethodName == WebserviceWrapper.TAGFRIENDINFEED) {

                ResponseObject tagFriendInFeedResponseObject = (ResponseObject) object;
                if (tagFriendInFeedResponseObject.getStatus().equals(WebConstants.STATUS_SUCCESS) && tagFriendInFeedResponseObject != null) {
                    Log.i(TAG, "The message is::" + tagFriendInFeedResponseObject.getMessage());
                    Toast.makeText(getActivity(), tagFriendInFeedResponseObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), tagFriendInFeedResponseObject.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {
            Log.i(TAG + mContext.getString(R.string.strerrormessage), e.getLocalizedMessage());

        }

    }


}
