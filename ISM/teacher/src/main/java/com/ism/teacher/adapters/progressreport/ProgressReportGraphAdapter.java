package com.ism.teacher.adapters.progressreport;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * these is the assignment subjects adapter
 */
public class ProgressReportGraphAdapter extends RecyclerView.Adapter<ProgressReportGraphAdapter.ViewHolder> {

    private static final String TAG = ProgressReportGraphAdapter.class.getSimpleName();

    private Context mContext;
    private FragmentManager fragmentManager;

    public ProgressReportGraphAdapter(Context mContext) {
        this.mContext = mContext;

        //Get FragmentManager
        fragmentManager = ((Activity) mContext).getFragmentManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_progressreport_graph, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgStudentPic;
        TextView tvStudentName, tvStudentScore, tvRank;
        ImageView imgReportDetail;
        LinearLayout llParentGraph;
        BarChart barChartReport;

        public ViewHolder(View itemView) {
            super(itemView);

            imgStudentPic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);
            imgReportDetail = (ImageView) itemView.findViewById(R.id.img_report_detail);

            tvStudentName = (TextView) itemView.findViewById(R.id.tv_student_name);
            tvStudentScore = (TextView) itemView.findViewById(R.id.tv_student_score);
            tvRank = (TextView) itemView.findViewById(R.id.tv_rank);

            llParentGraph = (LinearLayout) itemView.findViewById(R.id.ll_parent_graph);
            barChartReport = (BarChart) itemView.findViewById(R.id.bar_chart_report);

            tvRank.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvStudentName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvStudentScore.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    }

    public void addAll(ArrayList<Examsubmittor> evaluationModelArrayList) {
        try {
//            this.arrListExamSubmittor.clear();
//            this.arrListExamSubmittor.addAll(evaluationModelArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // return arrListExamSubmittor.size();
        return 5;
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) mContext).getBundle();
    }

}
