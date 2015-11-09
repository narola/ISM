package com.ism.teacher.adapters;

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
import android.widget.TextView;
import android.widget.Toast;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.dialog.TagStudyMatesDialog;
import com.ism.teacher.dialog.ViewAllCommentsDialog;
import com.ism.teacher.helper.PreferenceData;
import com.ism.teacher.model.Comment;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.PostFeedImagesModel;
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.WebserviceWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c75 on 24/10/15.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse, TagStudyMatesDialog.TagStudyMatesListener {

    private static final String TAG = PostFeedsAdapter.class.getSimpleName();
    ArrayList<Data> arrayListAllFeedsData;
    Comment objComment;
    Context context;

    Fragment fragment;
    View.OnClickListener viewAllCommetsListener;
    String likePrefData, unlikePrefData;
    private ImageLoader imageLoader;


    private int addCommentFeedPosition = -1;
    private int tagFeedPosition = -1;

    public PostFeedsAdapter(Context context, ArrayList<Data> data) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = data;
    }

    public PostFeedsAdapter(Context context, ArrayList<Data> data, View.OnClickListener viewAllCommetsListener, Fragment fragment) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = data;
        this.viewAllCommetsListener = viewAllCommetsListener;
        this.fragment = fragment;
    }

    public PostFeedsAdapter(Context context, ArrayList<Data> arrayListFeeds, Fragment fragment) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = arrayListFeeds;
        this.fragment = fragment;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }


    /**
     * Used to create the row_items(as in listview getView()method
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View post_row_view = inflater.inflate(R.layout.row_teacher_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(post_row_view);
        return viewHolder;
    }

    @Override
    public void tagStudyMates(String[] arrTagUser) {

        if (Utility.isOnline(context)) {
            try {
                RequestObject requestObject = new RequestObject();
                requestObject.setFeedId(arrayListAllFeedsData.get(tagFeedPosition).getFeed_id());
                requestObject.setTaggedBy(arrayListAllFeedsData.get(tagFeedPosition).getUser_id());
                requestObject.setTaggedUserIds(arrTagUser);

                new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
                        .execute(WebConstants.TAG_FRIEND_IN_FEED);
            } catch (Exception e) {
                Log.e(TAG, "callApiGetStudyMates Exception : " + e.toString());
            }
        } else {
            Utility.toastOffline(context);
        }
    }


    /**
     * USed to create static class for all the view if listitem child
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtUsernamePostCreator, txtPostContent, txtPostLikeCounter, txtPostCommentsCounter, txtViewAllComments, txtSubmitPost;
        public EditText etWritePost;
        public ImageView imgDpPostCreator, imgTagStudymates, imgLikePost, imgComments, imgAudio, imgImage, imgPlay, imgVideo;
        public LinearLayout llParentTeacherPost, llCommentRowInflater;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            llParentTeacherPost = (LinearLayout) itemView.findViewById(R.id.ll_parent_teacher_post);
            llCommentRowInflater = (LinearLayout) itemView.findViewById(R.id.ll_comment_row_inflater);

            imgDpPostCreator = (ImageView) itemView.findViewById(R.id.img_dp_post_creator);
            txtUsernamePostCreator = (TextView) itemView.findViewById(R.id.txt_username_post_creator);
            txtPostContent = (TextView) itemView.findViewById(R.id.txt_post_content);
            txtPostLikeCounter = (TextView) itemView.findViewById(R.id.txt_post_like_counter);
            txtPostCommentsCounter = (TextView) itemView.findViewById(R.id.txt_post_comments_counter);
            txtViewAllComments = (TextView) itemView.findViewById(R.id.txt_view_all_comments);
            etWritePost = (EditText) itemView.findViewById(R.id.et_writePost);
            txtSubmitPost = (TextView) itemView.findViewById(R.id.txt_submit_post);
            imgTagStudymates = (ImageView) itemView.findViewById(R.id.img_tag_studymates);
            imgLikePost = (ImageView) itemView.findViewById(R.id.img_like_post);
            imgComments = (ImageView) itemView.findViewById(R.id.img_comments);


            imgAudio = (ImageView) itemView.findViewById(R.id.img_audio);
            imgImage = (ImageView) itemView.findViewById(R.id.img_image);
            imgVideo = (ImageView) itemView.findViewById(R.id.img_video);
            imgPlay = (ImageView) itemView.findViewById(R.id.img_play);
        }


    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_138/group1_1445832486.jpg
        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgDpPostCreator, ISMTeacher.options);

        holder.txtUsernamePostCreator.setText(arrayListAllFeedsData.get(position).getFull_name());
        holder.txtPostContent.setText(arrayListAllFeedsData.get(position).getFeed_text());
        holder.txtPostLikeCounter.setText(arrayListAllFeedsData.get(position).getTotal_like());
        holder.txtPostCommentsCounter.setText(arrayListAllFeedsData.get(position).getTotal_comment());


        if (addCommentFeedPosition != -1 && addCommentFeedPosition == position) {
            holder.etWritePost.setText("");
            addCommentFeedPosition = -1;
        }

        if (arrayListAllFeedsData.get(position).getLike() == 1) {
            holder.imgLikePost.setSelected(true);
        } else {
            holder.imgLikePost.setSelected(false);
        }

        holder.llCommentRowInflater.removeAllViews();


        if (holder.llCommentRowInflater.getChildCount() == 0) {
            for (int i = 0; i < arrayListAllFeedsData.get(position).getCommentList().size(); i++) {

                if (i <= 1) {
                    View v = getCommentInflaterView(arrayListAllFeedsData.get(position).getCommentList().get(i));
                    holder.llCommentRowInflater.addView(v);

                } else {
                    break;
                }

            }

        }

        holder.txtViewAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int total_comments = Integer.parseInt(arrayListAllFeedsData.get(position).getTotal_comment());
                if (total_comments > 0) {
                    callApiGetAllComments(position);
                }

            }
        });


        holder.txtSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrayListAllFeedsData.size() > 0) {

                    if (validateStringPresence(holder.etWritePost)) {

                        callApiComment(position, holder.etWritePost.getText().toString());
                    }

                }
            }
        });


        holder.imgTagStudymates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utility.isOnline(context)) {
                    tagFeedPosition = position;
                    callApiGetStudyMates();
                } else {
                    Utility.toastOffline(context);
                }

            }

        });


        holder.imgLikePost.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                likePrefData = PreferenceData.getStringPrefs(PreferenceData.LIKE_ID_LIST, context);
                unlikePrefData = PreferenceData.getStringPrefs(PreferenceData.UNLIKE_ID_LIST, context);

                holder.imgLikePost.setSelected(!holder.imgLikePost.isSelected());

                if (holder.imgLikePost.isSelected()) {
                    arrayListAllFeedsData.get(position).setLike(1);
                    arrayListAllFeedsData.get(position).setTotal_like(String.valueOf(Integer.parseInt(arrayListAllFeedsData.get(position).getTotal_like()) + 1));

                    setPrefForLike(arrayListAllFeedsData.get(position).getFeed_id() + ",");
                } else {
                    arrayListAllFeedsData.get(position).setLike(0);
                    arrayListAllFeedsData.get(position).setTotal_like(String.valueOf(Integer.parseInt(arrayListAllFeedsData.get(position).getTotal_like()) - 1));

                    setPrefForUnlike(arrayListAllFeedsData.get(position).getFeed_id() + ",");

                }
                notifyDataSetChanged();
            }

        });


        holder.imgVideo.setVisibility(View.GONE);
        holder.imgPlay.setVisibility(View.GONE);
        holder.imgAudio.setVisibility(View.GONE);
        holder.imgImage.setVisibility(View.GONE);

        // holder.rlImage.setVisibility(View.GONE);
        //video
        if (arrayListAllFeedsData.get(position).getVideo_thumbnail() != "") {

            holder.imgVideo.setVisibility(View.VISIBLE);
            holder.imgPlay.setVisibility(View.VISIBLE);

            Log.i(TAG, WebConstants.FEED_MEDIA + arrayListAllFeedsData.get(position).getVideo_thumbnail() + "");
            imageLoader.displayImage(WebConstants.FEED_MEDIA + arrayListAllFeedsData.get(position).getVideo_thumbnail(), holder.imgVideo, ISMTeacher.options);

        }
        //audio
        if (arrayListAllFeedsData.get(position).getAudioLink() != "") {

            holder.imgAudio.setVisibility(View.VISIBLE);
        }
        // images
        if (arrayListAllFeedsData.get(position).getFeed_images().size() != 0) {

            holder.imgImage.setVisibility(View.VISIBLE);

            ArrayList<PostFeedImagesModel> listImages = new ArrayList<PostFeedImagesModel>();
            listImages = arrayListAllFeedsData.get(position).getFeed_images();
            for (int i = 0; i < listImages.size(); i++) {
                Log.i(TAG, WebConstants.FEED_MEDIA + listImages.get(i).getImage_link() + "");
                imageLoader.displayImage(WebConstants.FEED_MEDIA + listImages.get(i).getImage_link(), holder.imgImage, ISMTeacher.options);

            }

        }


    }

    public void callApiGetStudyMates() {
        try {
            RequestObject requestObject = new RequestObject();
//            requestObject.setUserId(Global.strUserId);
            requestObject.setUserId("167");

            new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
                    .execute(WebConstants.GET_STUDYMATES);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetStudyMates Exception : " + e.toString());
        }
    }


    private void callApiComment(int position, String comment) {
        try {
            addCommentFeedPosition = position;
            RequestObject requestObject = new RequestObject();
            requestObject.setFeedId(arrayListAllFeedsData.get(position).getFeed_id());
            requestObject.setCommentBy(arrayListAllFeedsData.get(position).getUser_id());
            requestObject.setComment(comment);

            new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
                    .execute(WebConstants.ADD_COMMENTS);
        } catch (Exception e) {
            Log.e(TAG, "callApiComment Exception : " + e.toString());
        }


    }

    private void callApiGetAllComments(int position) {

        try {
            addCommentFeedPosition = position;
            RequestObject requestObject = new RequestObject();
            requestObject.setFeedId(arrayListAllFeedsData.get(position).getFeed_id());

            new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
                    .execute(WebConstants.GET_ALL_COMMENTS);
        } catch (Exception e) {
            Log.e(TAG, "callApiGetAllComments Exception : " + e.toString());
        }
    }

    public void setPrefForLike(String feed_id) {

        PreferenceData.setStringPrefs(PreferenceData.LIKE_ID_LIST, context, likePrefData + feed_id);
        if (unlikePrefData != null && unlikePrefData.length() > 0) {
            PreferenceData.setStringPrefs(PreferenceData.UNLIKE_ID_LIST, context, unlikePrefData.replaceAll(feed_id, ""));
        }

    }

    public void setPrefForUnlike(String feed_id) {

        PreferenceData.setStringPrefs(PreferenceData.UNLIKE_ID_LIST, context, unlikePrefData + feed_id);
        if (likePrefData != null && likePrefData.length() > 0) {
            PreferenceData.setStringPrefs(PreferenceData.LIKE_ID_LIST, context, likePrefData.replaceAll(feed_id, ""));
        }

    }

    public boolean validateStringPresence(EditText editText) {
        if (editText.getText() == null
                || editText.getText().toString().trim().length() == 0) {
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }


    private View getCommentInflaterView(Comment commentData) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.post_comments_list_item, null, false);

        ImageView img_dp_commenter = (CircleImageView) view.findViewById(R.id.img_dp_commenter);
        TextView txtCommenterUsername = (TextView) view.findViewById(R.id.txt_username_commenter);
        TextView txtCommenterComment = (TextView) view.findViewById(R.id.txt_comments_from_commenter);
        TextView txtCommentDuration = (TextView) view.findViewById(R.id.txt_comment_duration);


        txtCommenterUsername.setText(commentData.getUsername());
        txtCommenterComment.setText(commentData.getComment());
        txtCommentDuration.setText(commentData.getCommentBy());

        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                img_dp_commenter, ISMTeacher.options);


        return view;

    }

    @Override
    public int getItemCount() {
        return arrayListAllFeedsData.size();
    }


    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            if (object != null) {
                switch (apiCode) {
                    case WebConstants.ADD_COMMENTS:
                        onResponseAddComment(object);
                        break;
                    case WebConstants.GET_ALL_COMMENTS:
                        onResponseGetAllComments(object);
                        break;
                    case WebConstants.GET_STUDYMATES:
                        onResponseGetAllStudyMates(object);
                        break;
                    case WebConstants.TAG_FRIEND_IN_FEED:
                        onResponseTagStudyMates(object);
                        break;
                }
            } else {
                Log.e(TAG, "onResponse ApiCall Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseTagStudyMates(Object object) {
        try {
            ResponseObject responseObj = (ResponseObject) object;
            tagFeedPosition = -1;
            if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                Toast.makeText(context, "Tag done!", Toast.LENGTH_SHORT).show();
            } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                Toast.makeText(context, "Tag failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseTagStudyMates Exception : " + e.toString());
        }

    }

    private void onResponseGetAllStudyMates(Object object) {

        try {
            ResponseObject responseObj = (ResponseObject) object;
            if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                if (responseObj.getData().size() > 0) {
                    TagStudyMatesDialog tagStudyMatesDialog = new TagStudyMatesDialog(context, responseObj.getData(), this);
                    tagStudyMatesDialog.show();
                }
            } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                Toast.makeText(context, responseObj.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllStudyMates Exception : " + e.toString());
        }
    }

    private void onResponseGetAllComments(Object object) {

        try {
            ResponseObject responseObj = (ResponseObject) object;
            if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(context, responseObj.getData());
                viewAllCommentsDialog.show();
            } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                Toast.makeText(context, responseObj.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseGetAllComments Exception : " + e.toString());
        }


    }

    private void onResponseAddComment(Object object) {
        try {
            ResponseObject responseObj = (ResponseObject) object;
            if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
                arrayListAllFeedsData.get(addCommentFeedPosition).setTotal_comment("" + (Integer.parseInt(arrayListAllFeedsData.get(addCommentFeedPosition).getTotal_comment()) + 1));
                notifyDataSetChanged();
            } else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
                Toast.makeText(context, R.string.msg_failed_comment, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseAddComment Exception : " + e.toString());
        }

    }


}
