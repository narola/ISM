package com.ism.author.adapter;

import android.app.Fragment;
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
import com.ism.author.Utility.Utility;
import com.ism.author.fragment.GetSubjectiveAssignmentQuestionsFragment;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.FragmentArgument;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c166 on 17/11/15.
 */
public class MyStudentListAdapter extends RecyclerView.Adapter<MyStudentListAdapter.ViewHolder> {

    private static final String TAG = MyStudentListAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Data> listOfStudents = new ArrayList<Data>();
    private MyTypeFace myTypeFace;
    private ImageLoader imageLoader;
    private Fragment mFragment;
    private LayoutInflater inflater;


    public MyStudentListAdapter(Context mContext, Fragment mFragment) {
        this.mContext = mContext;
        this.mFragment = mFragment;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        myTypeFace = new MyTypeFace(mContext);
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_my_students, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {

            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
                    holder.imgStudentProfilePic, ISMAuthor.options);
            holder.tvStudentName.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvStudentRollNo.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvStudentName.setText(listOfStudents.get(position).getFullName());
            holder.tvStudentRollNo.setText(mContext.getResources().getString(R.string.strrollno) + (position + 1));

            if (getFragmentArgument().getFragmentArgumentObject().getStudentId().equals(listOfStudents.get(position).getStudentId())) {
                holder.tvStudentName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                holder.tvStudentName.setTextColor(mContext.getResources().getColor(R.color.color_gray));
            }

            holder.llMyStudentsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentArgument().getFragmentArgumentObject().setPosition(position + 1);
                    getFragmentArgument().getFragmentArgumentObject().setProfilePic(listOfStudents.get(position).getProfilePic());
                    getFragmentArgument().getFragmentArgumentObject().setStudentName(listOfStudents.get(position).getFullName());
                    getFragmnet().loadStudentEvaluationData(listOfStudents.get(position).getStudentId());
                    notifyDataSetChanged();
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
            this.copyListOfStudents = data;
            getFragmentArgument().setArrayListData(data);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imgStudentProfilePic;
        public TextView tvStudentName, tvStudentRollNo;
        public LinearLayout llMyStudentsContainer;

        public ViewHolder(View convertView) {
            super(convertView);
            try {

                imgStudentProfilePic = (CircleImageView) convertView.findViewById(R.id.img_student_profile_pic);
                tvStudentName = (TextView) convertView.findViewById(R.id.tv_student_name);
                tvStudentRollNo = (TextView) convertView.findViewById(R.id.tv_student_roll_no);
                llMyStudentsContainer = (LinearLayout) convertView.findViewById(R.id.ll_my_students_container);

            } catch (Exception e) {
                Debug.e(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }

    ArrayList<Data> copyListOfStudents;

    public void filter(CharSequence charText) {
        listOfStudents.clear();

        if (charText.length() == 0) {
            listOfStudents.addAll(copyListOfStudents);
        } else {
            for (Data wp : copyListOfStudents) {
                if (Utility.containsString(wp.getFullName(), charText.toString(), false)) {
                    listOfStudents.add(wp);
                }
            }
            if (listOfStudents.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }


    private FragmentArgument getFragmentArgument() {

        return ((GetSubjectiveAssignmentQuestionsFragment) mFragment).getFragmnetArgument();

    }

    private GetSubjectiveAssignmentQuestionsFragment getFragmnet() {

        return (GetSubjectiveAssignmentQuestionsFragment) mFragment;

    }

}
