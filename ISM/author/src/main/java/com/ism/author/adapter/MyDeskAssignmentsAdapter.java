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
import com.ism.author.utility.Debug;
import com.ism.author.utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.fragment.mydesk.MyDeskFragment;
import com.ism.author.object.Global;
import com.ism.author.ws.model.AuthorBookAssignment;

import java.util.ArrayList;

/**
 * Created by c166 on 21/12/15.
 */
public class MyDeskAssignmentsAdapter extends RecyclerView.Adapter<MyDeskAssignmentsAdapter.ViewHolder> {

    private static final String TAG = MyDeskAssignmentsAdapter.class.getSimpleName();
    private Fragment mFragment;
    private ArrayList<AuthorBookAssignment> arrListAuthorBooksAssignments = new ArrayList<AuthorBookAssignment>();
    private LayoutInflater inflater;
    private Context mContext;

    public static String ARG_BOOK_ID = "bookId";

    public MyDeskAssignmentsAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.mContext = mContext;
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

            if (position == 0 || position % 3 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.assignment_bg_blue);
            } else if (position == 1 || (position - 1) % 3 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.assignment_bg_pink);
            } else if (position == 2 || (position - 2) % 3 == 0) {
                holder.rlTopAssignment.setBackgroundResource(R.drawable.assignment_bg_green);
            }


            holder.tvBookName.setText(arrListAuthorBooksAssignments.get(position).getBookName());


            if (arrListAuthorBooksAssignments.get(position).getAssignments().size() > 0) {

                holder.tvAssignmentOne.setText(arrListAuthorBooksAssignments.get(position).getAssignments().get(0).getAssignmentName());
                if (arrListAuthorBooksAssignments.get(position).getAssignments().size() > 1) {
                    holder.tvAssignmentTwo.setText(arrListAuthorBooksAssignments.get(position).getAssignments().get(1).getAssignmentName());
                } else {
                    holder.tvAssignmentTwo.setVisibility(View.INVISIBLE);
                }
                if (arrListAuthorBooksAssignments.get(position).getAssignments().size() > 2) {
                    holder.tvAssignmentThree.setText(arrListAuthorBooksAssignments.get(position).getAssignments().get(2).getAssignmentName());
                } else {
                    holder.tvAssignmentThree.setVisibility(View.INVISIBLE);
                }
            } else {
                holder.tvAssignmentOne.setVisibility(View.INVISIBLE);
                holder.tvAssignmentTwo.setVisibility(View.INVISIBLE);
                holder.tvAssignmentThree.setVisibility(View.INVISIBLE);

            }

            holder.llAssignmentContainer.setTag(arrListAuthorBooksAssignments.get(position).getBookId());

            holder.llAssignmentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((AuthorHostActivity) mContext).getBundle().putString(ARG_BOOK_ID, (String) v.getTag());

                    ((MyDeskFragment) mFragment).loadFragment(MyDeskFragment.FRAGMENT_BOOKASSIGNMENT);

                }
            });


            holder.tvViewAllAssignments.setText(arrListAuthorBooksAssignments.get(position).getTotalAssignments() + "\n");
            holder.tvViewAllAssignments.append(Utility.getSpannableString(mContext.getResources().getString(R.string.strviewall),
                    mContext.getResources().getColor(R.color.color_question)));

        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return arrListAuthorBooksAssignments.size();
    }

    public void addAll(ArrayList<AuthorBookAssignment> authorBookAssignments) {
        try {
            this.arrListAuthorBooksAssignments.clear();
            this.arrListAuthorBooksAssignments.addAll(authorBookAssignments);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llAssignmentContainer;
        RelativeLayout rlTopAssignment;
        TextView tvBookName, tvViewAllAssignments, tvAssignmentOne, tvAssignmentTwo, tvAssignmentThree;


        public ViewHolder(View itemView) {
            super(itemView);

            llAssignmentContainer = (LinearLayout) itemView.findViewById(R.id.ll_assignment_container);
            rlTopAssignment = (RelativeLayout) itemView.findViewById(R.id.rl_top_assignment);

            tvBookName = (TextView) itemView.findViewById(R.id.tv_book_name);
            tvViewAllAssignments = (TextView) itemView.findViewById(R.id.tv_view_all_assignments);
            tvAssignmentOne = (TextView) itemView.findViewById(R.id.tv_assignment_one);
            tvAssignmentTwo = (TextView) itemView.findViewById(R.id.tv_assignment_two);
            tvAssignmentThree = (TextView) itemView.findViewById(R.id.tv_assignment_three);


            tvBookName.setTypeface(Global.myTypeFace.getRalewayBold());
            tvViewAllAssignments.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvAssignmentOne.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvAssignmentTwo.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvAssignmentThree.setTypeface(Global.myTypeFace.getRalewayRegular());


        }
    }

}
