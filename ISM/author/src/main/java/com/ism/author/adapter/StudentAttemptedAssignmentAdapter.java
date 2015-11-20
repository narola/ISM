package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.fragment.GetObjectiveAssignmentQuestionsFragment;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 19/11/15.
 */
public class StudentAttemptedAssignmentAdapter extends RecyclerView.Adapter<StudentAttemptedAssignmentAdapter.ViewHolder> {

    private static final String TAG = StudentAttemptedAssignmentAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Data> listOfStudents = new ArrayList<Data>();
    private MyTypeFace myTypeFace;
    private ImageLoader imageLoader;
    private LayoutInflater inflater;
    private FragmentArgument fragmentArgument;


    public StudentAttemptedAssignmentAdapter(Context mContext, FragmentArgument fragmentArgument) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        myTypeFace = new MyTypeFace(mContext);
        inflater = LayoutInflater.from(mContext);
        this.fragmentArgument = fragmentArgument;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_student_attempted, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        try {

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.imgUserPic, ISMAuthor.options);

            holder.txtStudentName.setTypeface(myTypeFace.getRalewayBold());
            holder.txtStudentSchool.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtStudentMarks.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtStudentClass.setTypeface(myTypeFace.getRalewayRegular());

            holder.txtStudentName.setText(listOfStudents.get(position).getFullName());
            holder.txtStudentSchool.setText(listOfStudents.get(position).getSchoolName());
            holder.txtStudentMarks.setText(listOfStudents.get(position).getEvaluationScore());
            holder.txtStudentSchool.setText(listOfStudents.get(position).getClassName());

            if (fragmentArgument.getFragmentArgumentObject().getStudentId().equals(listOfStudents.get(position).getStudentId())) {
                holder.llMain.setBackgroundColor(mContext.getResources().getColor(R.color.fragment_background_color));
                holder.txt_bottom_line.setBackgroundColor(mContext.getResources().getColor(R.color.color_blue));
            } else {
                holder.llMain.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
                holder.txt_bottom_line.setBackgroundColor(mContext.getResources().getColor(R.color.border_gray));
            }

            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    fragmentArgument.getFragmentArgumentObject().setStudentId(listOfStudents.get(position).getStudentId());
                    notifyDataSetChanged();
                    ((GetObjectiveAssignmentQuestionsFragment) fragmentArgument.getFragment()).loadStudentEvaluationData();

                }
            });


        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }

    }

    @Override
    public int getItemCount() {
        return listOfStudents.size();
    }

    public void addAll(ArrayList<Data> data) {
        try {
            this.listOfStudents.clear();
            this.listOfStudents.addAll(data);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgUserPic;
        TextView txtStudentName, txtStudentSchool, txtStudentMarks, txtStudentClass, txt_bottom_line;
        LinearLayout llMain;

        public ViewHolder(View convertView) {
            super(convertView);
            try {

                imgUserPic = (CircleImageView) convertView.findViewById(R.id.img_user_pic);
                txtStudentName = (TextView) convertView.findViewById(R.id.txt_student_name);
                txtStudentSchool = (TextView) convertView.findViewById(R.id.txt_student_school);
                txtStudentMarks = (TextView) convertView.findViewById(R.id.txt_student_marks);
                txtStudentClass = (TextView) convertView.findViewById(R.id.txt_student_class);
                llMain = (LinearLayout) convertView.findViewById(R.id.ll_main);
                txt_bottom_line = (TextView) convertView.findViewById(R.id.txt_bottom_line);

            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }
}
