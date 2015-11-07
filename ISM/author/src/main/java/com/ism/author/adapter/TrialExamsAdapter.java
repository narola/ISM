package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.constant.AppConstant;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.ResponseObject;


/**
 * Created by c162 on 03/11/15.
 */
public class TrialExamsAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    ResponseObject data;
    Fragment fragment;
    private MyTypeFace myTypeFace;

    public TrialExamsAdapter(Context context, ResponseObject data, Fragment fragment) {
        this.context = context;
        this.data = data;
        this.fragment = fragment;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myTypeFace=new MyTypeFace(context);

    }

    @Override
    public int getCount() {
        return data.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_trial, null);
            holder = new ViewHolder();

            holder.txtTotalStudent = (TextView) convertView.findViewById(R.id.txt_total_student);
            holder.txtStudentAttempted = (TextView) convertView.findViewById(R.id.txt_student_attempted);
            holder.txtExamType = (TextView) convertView.findViewById(R.id.txt_exam_type);
            holder.txtSubjectName = (TextView) convertView.findViewById(R.id.txt_subject_name);
            holder.txtAverage= (TextView) convertView.findViewById(R.id.txt_average_percent);
            holder.txtAttempted = (TextView) convertView.findViewById(R.id.txt_attempt_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.txtSubjectName.setText(data.getData().get(position).getSubjectName());
           // holder.txtExamType.setBackgroundResource(data.getOfficeTabTitleImages()[position]);
            holder.txtSubjectName.setTypeface(myTypeFace.getRalewayBold());

            holder.txtExamType.setText("Exam Type : " + data.getData().get(position).getExam_type());
            holder.txtExamType.setTypeface(myTypeFace.getRalewayThin());

            holder.txtTotalStudent.setText(data.getData().get(position).getTotal_student() + " Student");
            holder.txtTotalStudent.setTypeface(myTypeFace.getRalewayBold());

            holder.txtStudentAttempted.setText(data.getData().get(position).getTotal_student_attempted());
            holder.txtStudentAttempted.setTypeface(myTypeFace.getRalewayBold());

            holder.txtAverage.setText(data.getData().get(position).getAverage_score()+"%");
            holder.txtAverage.setTypeface(myTypeFace.getRalewayThin());
            holder.txtAttempted.setTypeface(myTypeFace.getRalewayThin());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // send the value of exam_id,role_id
                    if(data.getData().get(position).getExamMode().equals(AppConstant.EXAM_MODE_OBJECTIVE))
                    ((AuthorHostActivity)context).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS);
                    else if(data.getData().get(position).getExamMode().equals(AppConstant.EXAM_MODE_SUBJECTIVE))
                        ((AuthorHostActivity)context).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    public  class ViewHolder{
        TextView txtTotalStudent,txtStudentAttempted,txtSubjectName,txtExamType,txtAverage,txtAttempted;


    }
}
