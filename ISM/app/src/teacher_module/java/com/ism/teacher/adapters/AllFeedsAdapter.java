package com.ism.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * Created by c75 on 24/10/15.
 */
public class AllFeedsAdapter extends
        RecyclerView.Adapter<AllFeedsAdapter.ViewHolder> {

    ArrayList<Data> arrayListAllFeedsData;
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
        View contactView = inflater.inflate(R.layout.row_teacher_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllFeedsAdapter.ViewHolder holder, int position) {

        holder.txtUsernamePostCreator.setText(arrayListAllFeedsData.get(position).getFull_name());
        holder.txtPostContent.setText(arrayListAllFeedsData.get(position).getFeed_text());
        holder.txtPostLikeCounter.setText(arrayListAllFeedsData.get(position).getTotal_like());
        holder.txtPostCommentsCounter.setText(arrayListAllFeedsData.get(position).getTotal_comment());
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

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

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
