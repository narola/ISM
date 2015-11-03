package com.ism.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;


/**
 * these is the postfeedcommentsadapter
 */
public class AssignmentSubjectsAdapter extends RecyclerView.Adapter<AssignmentSubjectsAdapter.ViewHolder> {

    private static final String TAG = AssignmentSubjectsAdapter.class.getSimpleName();

    Context mContext;
   // ArrayList<Data> listOfComments = new ArrayList<Data>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.assignment_subjects_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


//    public void addAll(ArrayList<Data> data) {
//
//        try {
//            this.listOfComments.clear();
//            this.listOfComments.addAll(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return 7;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCommenterUsername, txtCommenterComment, txtCommentDuration;
        ImageView imgSeparator;


        public ViewHolder(View itemView) {
            super(itemView);

//            txtCommenterUsername = (TextView) itemView.findViewById(R.id.txt_username_commenter);
//            txtCommenterComment = (TextView) itemView.findViewById(R.id.txt_comments_from_commenter);
//            txtCommentDuration = (TextView) itemView.findViewById(R.id.txt_comment_duration);
//            imgSeparator = (ImageView) itemView.findViewById(R.id.img_separator);

        }
    }


}
