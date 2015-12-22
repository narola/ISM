package com.ism.teacher.adapters.userprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;

/**
 * Created by c75 on 21/12/15.
 */
public class MyStudentsFromProfileAdapter extends RecyclerView.Adapter<MyStudentsFromProfileAdapter.ViewHolder> {

    private static final String TAG = MyStudentsFromProfileAdapter.class.getSimpleName();
    Context mContext;


    public MyStudentsFromProfileAdapter(Context context) {
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img_student_pic;
        TextView tv_student_name, tv_class_name, tv_student_school, tv_living_in, tv_ism_rank, tv_ism_rank_label, tv_score, tv_score_label;

        public ViewHolder(View itemView) {
            super(itemView);

            img_student_pic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);

            tv_student_name = (TextView) itemView.findViewById(R.id.tv_student_name);
            tv_class_name = (TextView) itemView.findViewById(R.id.tv_class_name);
            tv_student_school = (TextView) itemView.findViewById(R.id.tv_student_school);
            tv_living_in = (TextView) itemView.findViewById(R.id.tv_living_in);
            tv_ism_rank = (TextView) itemView.findViewById(R.id.tv_ism_rank);
            tv_ism_rank_label = (TextView) itemView.findViewById(R.id.tv_ism_rank_label);
            tv_score = (TextView) itemView.findViewById(R.id.tv_score);
            tv_score_label = (TextView) itemView.findViewById(R.id.tv_score_label);

            tv_student_name.setTypeface(Global.myTypeFace.getRalewayBold());
            tv_class_name.setTypeface(Global.myTypeFace.getRalewayRegular());
            tv_student_school.setTypeface(Global.myTypeFace.getRalewayRegular());
            tv_living_in.setTypeface(Global.myTypeFace.getRalewayRegular());
            tv_ism_rank.setTypeface(Global.myTypeFace.getRalewayBold());
            tv_ism_rank_label.setTypeface(Global.myTypeFace.getRalewayRegular());
            tv_score.setTypeface(Global.myTypeFace.getRalewayBold());
            tv_score_label.setTypeface(Global.myTypeFace.getRalewayRegular());
        }
    }

    @Override
    public MyStudentsFromProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_mystudents_fromuser_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyStudentsFromProfileAdapter.ViewHolder holder, int position) {
//        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListExamSubmittor.get(position).getStudentProfilePic(), holder.imgUserPic, ISMTeacher.options);
//
//        holder.tvUsername.setText();
//        holder.tvSchoolname.setText();

    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
