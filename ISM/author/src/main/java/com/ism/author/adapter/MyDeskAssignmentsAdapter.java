package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.fragment.mydesk.MyDeskFragment;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c166 on 21/12/15.
 */
public class MyDeskAssignmentsAdapter extends RecyclerView.Adapter<MyDeskAssignmentsAdapter.ViewHolder> {

    private static final String TAG = ExamsAdapter.class.getSimpleName();
    private Fragment mFragment;
    private ArrayList<Assignment> arrListAssignments = new ArrayList<Assignment>();
    private LayoutInflater inflater;

    public MyDeskAssignmentsAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_mydesk_assignments, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.tvAssignmentName.setTypeface(Global.myTypeFace.getRalewayBold());
            holder.tvViewAllAssignments.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvAssignmentOne.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvAssignmentTwo.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvAssignmentThree.setTypeface(Global.myTypeFace.getRalewayRegular());

            if (position == 0 || position % 3 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.assignment_bg_blue);
            } else if (position == 1 || (position - 1) % 3 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.assignment_bg_pink);
            } else if (position == 2 || (position - 2) % 3 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.assignment_bg_green);
            }

            holder.llAssignmentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((MyDeskFragment) mFragment).loadFragment(MyDeskFragment.FRAGMENT_BOOKREFERENCE);

                }
            });

        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return arrListAssignments.size();
    }

    public void addAll(ArrayList<Assignment> assignments) {
        try {
            this.arrListAssignments.clear();
            this.arrListAssignments.addAll(assignments);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llAssignmentContainer;
        RelativeLayout rlTopAssignment;
        TextView tvAssignmentName, tvViewAllAssignments, tvAssignmentOne, tvAssignmentTwo, tvAssignmentThree;


        public ViewHolder(View itemView) {
            super(itemView);

            llAssignmentContainer = (LinearLayout) itemView.findViewById(R.id.ll_assignment_container);
            rlTopAssignment = (RelativeLayout) itemView.findViewById(R.id.rl_top_assignment);

            tvAssignmentName = (TextView) itemView.findViewById(R.id.tv_assignment_name);
            tvViewAllAssignments = (TextView) itemView.findViewById(R.id.tv_view_all_assignments);
            tvAssignmentOne = (TextView) itemView.findViewById(R.id.tv_assignment_one);
            tvAssignmentTwo = (TextView) itemView.findViewById(R.id.tv_assignment_two);
            tvAssignmentThree = (TextView) itemView.findViewById(R.id.tv_assignment_three);


        }
    }

}
