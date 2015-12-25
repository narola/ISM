package com.ism.author.adapter.gotrending;

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
import com.ism.author.adapter.ExamsAdapter;
import com.ism.author.object.Global;
import com.ism.author.ws.model.AuthorBookAssignment;

import java.util.ArrayList;

/**
 * Created by c166 on 25/12/15.
 */
public class QuestionCommentAdapter extends RecyclerView.Adapter<QuestionCommentAdapter.ViewHolder> {


    private static final String TAG = ExamsAdapter.class.getSimpleName();
    private Fragment mFragment;
    private ArrayList<AuthorBookAssignment> arrListAuthorBooksAssignments = new ArrayList<AuthorBookAssignment>();
    private LayoutInflater inflater;
    private Context mContext;


    public QuestionCommentAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_question_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {


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
