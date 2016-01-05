package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c166 on 25/12/15.
 */
public class BookAssignmentsAdapter extends RecyclerView.Adapter<BookAssignmentsAdapter.ViewHolder> {


    private static final String TAG = BookAssignmentsAdapter.class.getSimpleName();
    private Fragment mFragment;
    private ArrayList<Assignment> arrListBookAssignment = new ArrayList<Assignment>();
    private LayoutInflater inflater;

    public BookAssignmentsAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_book_assignment, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {

            holder.tvAssignmentTitle.setText((position + 1) + ". " + arrListBookAssignment.get(position).getAssignmentName());
            holder.tvAssignmentContent.setText(arrListBookAssignment.get(position).getAssignmentText());
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return arrListBookAssignment.size();
    }

    public void addAll(ArrayList<Assignment> assignments) {
        try {
            this.copyListOfAssignments = assignments;
            this.arrListBookAssignment.clear();
            this.arrListBookAssignment.addAll(assignments);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAssignmentTitle, tvAssignmentContent;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAssignmentTitle = (TextView) itemView.findViewById(R.id.tv_assignment_title);
            tvAssignmentContent = (TextView) itemView.findViewById(R.id.tv_assignment_content);


            tvAssignmentTitle.setTypeface(Global.myTypeFace.getRalewayBold());
            tvAssignmentContent.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }

    public ArrayList<Assignment> copyListOfAssignments;

    public void filter(CharSequence charText) {

        arrListBookAssignment.clear();
        if (charText.length() == 0) {
            arrListBookAssignment.addAll(copyListOfAssignments);
        } else {

            Debug.e(TAG, "The string is:::" + charText);
            for (Assignment wp : copyListOfAssignments) {

                if (charText != "") {
                    if (Utility.containsString(wp.getAssignmentName(), charText.toString(), false)) {
                        arrListBookAssignment.add(wp);
                    }
                }

            }
            if (arrListBookAssignment.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }

}
