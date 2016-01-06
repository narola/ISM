package com.ism.adapter.myAuthor;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.AppConstant;
import com.ism.fragment.MyAuthorFragment;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c162 on 01/01/16.
 */
public class MyAuthorAdapter extends RecyclerView.Adapter<MyAuthorAdapter.ViewHolder> {


    private static final String TAG = MyAuthorAdapter.class.getSimpleName();
    private final Context mContext;
    private final MyAuthorFragment myAuthorFragment;
    private Fragment mFragment;
    private ArrayList<Assignment> arrListBookAssignment = new ArrayList<Assignment>();
    private LayoutInflater inflater;
    public MyAuthorAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.mContext=mContext;
        this.inflater = LayoutInflater.from(mContext);
        myAuthorFragment= MyAuthorFragment.newInstance();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.list_item_author, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {

//            holder.tvAssignmentTitle.setText((position + 1) + ". " + arrListBookAssignment.get(position).getAssignmentName());
//            holder.tvAssignmentContent.setText(arrListBookAssignment.get(position).getAssignmentText());
            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //myAuthorFragment.loadFragment(MyAuthorFragment.FRAGMENT_AUTHOR);
                    Bundle bundle=((HostActivity) mContext).getBundle();
                    bundle.putString(AppConstant.AUTHOR_NAME, "Roger S. Pressman");
                    ((HostActivity) mContext).setBundle(bundle);
//                    ((HostActivity)mContext).loadFragment(HostActivity.FRAGMENT_AUTHOR_OFFICE,bundle);
                    ((HostActivity)mContext).loadFragment(MyAuthorFragment.FRAGMENT_AUTHOR_OFFICE,null);
                }
            });
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return 20;
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
        private ImageView imgOption;
        public ViewHolder(View itemView) {
            super(itemView);

//            tvAssignmentTitle = (TextView) itemView.findViewById(R.id.tv_assignment_title);
//            tvAssignmentContent = (TextView) itemView.findViewById(R.id.tv_assignment_content);
//
//
//            tvAssignmentTitle.setTypeface(Global.myTypeFace.getRalewayBold());
//            tvAssignmentContent.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgOption=(ImageView)itemView.findViewById(R.id.img_menu);

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
                if (Utility.containsString(wp.getAssignmentName(), charText.toString(), false)) {
                    arrListBookAssignment.add(wp);
                }
            }
            if (arrListBookAssignment.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }

}
