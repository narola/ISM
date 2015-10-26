package com.ism.teacher.adapters;

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
import com.ism.teacher.model.Comment;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * Created by c75 on 24/10/15.
 */
public class AllFeedsAdapter extends
        RecyclerView.Adapter<AllFeedsAdapter.ViewHolder> {

    ArrayList<Data> arrayListAllFeedsData;
    Comment objComment;


    Context context;

    public AllFeedsAdapter(Context context, ArrayList<Data> data) {
        this.context = context;
        arrayListAllFeedsData = new ArrayList<>();
        arrayListAllFeedsData = data;
    }

    /**
     * Used to create the row_items(as in listview getView()method
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public AllFeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View post_row_view = inflater.inflate(R.layout.row_teacher_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(post_row_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllFeedsAdapter.ViewHolder holder, int position) {

        holder.txtUsernamePostCreator.setText(arrayListAllFeedsData.get(position).getFull_name());
        holder.txtPostContent.setText(arrayListAllFeedsData.get(position).getFeed_text());
        holder.txtPostLikeCounter.setText(arrayListAllFeedsData.get(position).getTotal_like());
        holder.txtPostCommentsCounter.setText(arrayListAllFeedsData.get(position).getTotal_comment());

        if (arrayListAllFeedsData.get(position).getComment().size() > 0) {


            if (arrayListAllFeedsData.get(position).getComment().size() > 2) {
                for (int i = 0; i < 2; i++) {

                    objComment = arrayListAllFeedsData.get(position).getComment().get(i);

//                    if (getCommentInflaterView(objComment) == null) {
//                        holder.llCommentRowInflater.addView(getCommentInflaterView(objComment));
//                    }
//                    else {
//                        holder.llCommentRowInflater.removeAllViews();
//                    }
                    holder.llCommentRowInflater.addView(getCommentInflaterView(objComment));

                }
            } else {
                for (int i = 0; i < arrayListAllFeedsData.get(position).getComment().size(); i++) {

                    objComment = arrayListAllFeedsData.get(position).getComment().get(i);
//                    if (getCommentInflaterView(objComment) == null) {
//                        holder.llCommentRowInflater.addView(getCommentInflaterView(objComment));
//
//                    }
//
//                    else {
//                        holder.llCommentRowInflater.removeAllViews();
//                    }
                    holder.llCommentRowInflater.addView(getCommentInflaterView(objComment));
                }
            }

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
        public TextView txtUsernamePostCreator, txtPostContent, txtPostLikeCounter, txtPostCommentsCounter, txtViewAllComments;
        public EditText etWritePost;
        public ImageView imgDpPostCreator;
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
        }


    }
}
