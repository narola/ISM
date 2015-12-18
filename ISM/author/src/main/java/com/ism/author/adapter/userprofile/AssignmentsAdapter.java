package com.ism.author.adapter.userprofile;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.object.MyTypeFace;
import com.ism.author.ws.helper.ResponseHandler;


/**
 * Created by c162 on 28/11/15.
 */
public class AssignmentsAdapter extends BaseAdapter {
    private final String TAG = AssignmentsAdapter.class.getSimpleName();
    private Context context;
    LayoutInflater inflater;
    ResponseHandler data;
    Fragment fragment;
    private MyTypeFace myTypeFace;

    public AssignmentsAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        myTypeFace = new MyTypeFace(context);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_assignments, null);
            holder = new ViewHolder();

            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_book_name);
            holder.txtViewAll = (TextView) convertView.findViewById(R.id.txt_view_all);
            holder.txtAssignmentOne = (TextView) convertView.findViewById(R.id.txt_assignment_one);
            holder.txtAssignmentTwo = (TextView) convertView.findViewById(R.id.txt_assignment_two);
            holder.txtAssignmentThree = (TextView) convertView.findViewById(R.id.txt_assignment_three);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            // holder.txtSubjectName.setText(data.getExams().get(position).getSubjectName());
            // holder.txtExamType.setBackgroundResource(questionData.getOfficeTabTitleImages()[position]);

            // holder.txtViewAll.setText("Exam Type : " + data.getExams().get(position).getExamType());
            holder.txtViewAll.setTypeface(myTypeFace.getRalewayThin());

            // holder.txtBookName.setText(data.getExams().get(position).getTotalStudent() + " Student");
            holder.txtBookName.setTypeface(myTypeFace.getRalewayBold());

            // holder.txtAssignmentOne.setText(data.getExams().get(position).getTotalStudentAttempted());
            holder.txtAssignmentOne.setTypeface(myTypeFace.getRalewayBold());

            // holder.txtAssignmentTwo.setText(data.getExams().get(position).getAverageScore() + "%");
            holder.txtAssignmentTwo.setTypeface(myTypeFace.getRalewayThin());
            holder.txtAssignmentThree.setTypeface(myTypeFace.getRalewayThin());
            // holder.txtAssignmentThree.setText(data.getExams().get(position).getAverageScore() + "%");

//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    // send the value of exam_id,role_id
//                    if (data.getExams().get(position).getExamMode().equalsIgnoreCase(context.getResources().getString(R.string.strobjective)))
//                        ((AuthorHostActivity) context).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS, null);
//                    else if (data.getExams().get(position).getExamMode().equals(context.getResources().getString(R.string.strobjective)))
//                        ((AuthorHostActivity) context).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRIAL_EXAM_OBJECTIVE_DETAILS, null);
//
//                }
//            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    public class ViewHolder {
        TextView txtBookName, txtAssignmentOne, txtAssignmentTwo, txtAssignmentThree, txtViewAll;


    }
}
