package com.ism.teacher.adapters;

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

import com.ism.teacher.R;
import com.ism.teacher.fragments.TeacherHomeFragment;
import com.ism.teacher.helper.PreferenceData;
import com.ism.teacher.model.AddCommentRequest;
import com.ism.teacher.model.Comment;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.FeedIdRequest;

import java.util.ArrayList;

/**
 * Created by c75 on 24/10/15.
 */
public class AllFeedsAdapter extends RecyclerView.Adapter<AllFeedsAdapter.ViewHolder> {

    ArrayList<Data> arrayListAllFeedsData;
    Comment objComment;
    Context context;

    Fragment fragment;
    View.OnClickListener viewAllCommetsListener;
    String likePrefData, unlikePrefData;

    public AllFeedsAdapter(Context context, ArrayList<Data> data) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = data;
    }

    public AllFeedsAdapter(Context context, ArrayList<Data> data, View.OnClickListener viewAllCommetsListener, Fragment fragment) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = data;
        this.viewAllCommetsListener = viewAllCommetsListener;
        this.fragment = fragment;
    }

    public AllFeedsAdapter(Context context, ArrayList<Data> data, Fragment fragment) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = data;
        this.fragment = fragment;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtUsernamePostCreator.setText(arrayListAllFeedsData.get(position).getFull_name());
        holder.txtPostContent.setText(arrayListAllFeedsData.get(position).getFeed_text());
        holder.txtPostLikeCounter.setText(arrayListAllFeedsData.get(position).getTotal_like());
        holder.txtPostCommentsCounter.setText(arrayListAllFeedsData.get(position).getTotal_comment());


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

                FeedIdRequest feedIdRequest = new FeedIdRequest();

                feedIdRequest.setFeed_id(arrayListAllFeedsData.get(position).getFeed_id());

                ((TeacherHomeFragment) fragment).callViewAllCommentsApi(feedIdRequest);
            }
        });


        holder.txtSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrayListAllFeedsData.size() > 0) {

                    if (validateStringPresence(holder.etWritePost)) {
                        AddCommentRequest addCommentRequest = new AddCommentRequest();

                        addCommentRequest.setFeed_id(arrayListAllFeedsData.get(position).getFeed_id());
                        addCommentRequest.setComment_by(arrayListAllFeedsData.get(position).getUser_id());
                        addCommentRequest.setComment(holder.etWritePost.getText().toString());

                        ((TeacherHomeFragment) fragment).callAddCommentApi(addCommentRequest);
                        ((TeacherHomeFragment) fragment).setSetAddCommentRowPosition(position);
                    }

                }
            }
        });


        holder.imgTagStudymates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TeacherHomeFragment) fragment).tagFriendInFeedRequest.setFeed_id("240");
                ((TeacherHomeFragment) fragment).tagFriendInFeedRequest.setTagged_by("134");
                ((TeacherHomeFragment) fragment).callGetStudyMates();

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
        View v = layoutInflater.inflate(R.layout.post_comments_list_item, null, false);

        TextView txtCommenterUsername = (TextView) v.findViewById(R.id.txt_username_commenter);
        TextView txtCommenterComment = (TextView) v.findViewById(R.id.txt_comments_from_commenter);
        TextView txtCommentDuration = (TextView) v.findViewById(R.id.txt_comment_duration);


        txtCommenterUsername.setText(commentData.getUsername());
        txtCommenterComment.setText(commentData.getComment());
        txtCommentDuration.setText(commentData.getCommentBy());


        return v;

    }

    @Override
    public int getItemCount() {
        return arrayListAllFeedsData.size();
    }

    /**
     * USed to create static class for all the view if listitem child
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtUsernamePostCreator, txtPostContent, txtPostLikeCounter, txtPostCommentsCounter, txtViewAllComments, txtSubmitPost;
        public EditText etWritePost;
        public ImageView imgDpPostCreator, imgTagStudymates, imgLikePost, imgComments;
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
        }


    }
}
