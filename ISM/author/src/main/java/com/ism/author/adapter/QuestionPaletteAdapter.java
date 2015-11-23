package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.fragment.GetSubjectiveAssignmentQuestionsFragment;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.model.Evaluation;

import java.util.ArrayList;

/**
 * Created by c166 on 19/11/15.
 */
public class QuestionPaletteAdapter extends RecyclerView.Adapter<QuestionPaletteAdapter.ViewHolder> {

    private static final String TAG = QuestionPaletteAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Evaluation> arrListEvaluation = new ArrayList<Evaluation>();
    private MyTypeFace myTypeFace;
    private Fragment mFragment;
    private LayoutInflater inflater;

    public QuestionPaletteAdapter(Context mContext, Fragment mFragment) {
        this.mContext = mContext;
        this.mFragment = mFragment;
        myTypeFace = new MyTypeFace(mContext);
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_question_palette, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.tvQuestionStatus.setText("" + (position + 1));
            holder.tvQuestionStatus.setTypeface(myTypeFace.getRalewayRegular());

            holder.tvQuestionStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBaseFragment().scrollToSpecificQuestion(position);

                }
            });
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }

    }

    @Override
    public int getItemCount() {
        return arrListEvaluation.size();
    }

    public void addAll(ArrayList<Evaluation> evaluations) {
        try {
            this.arrListEvaluation.clear();
            this.arrListEvaluation.addAll(evaluations);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestionStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQuestionStatus = (TextView) itemView.findViewById(R.id.tv_question_status);

        }
    }

    private GetSubjectiveAssignmentQuestionsFragment getBaseFragment() {
        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;
    }
}
