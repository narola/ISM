package com.ism.teacher.adapters.userprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.Students;

import java.util.ArrayList;

/**
 * Created by c75 on 21/12/15.
 */
public class MyStudentsUserProfileAdapter extends RecyclerView.Adapter<MyStudentsUserProfileAdapter.ViewHolder> {

    private static final String TAG = MyStudentsUserProfileAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Students> arrListmyStudents = new ArrayList<>();


    public MyStudentsUserProfileAdapter(Context context, ArrayList<Students> arrListmyStudents) {
        this.mContext = context;
        this.arrListmyStudents = arrListmyStudents;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgStudentPic;
        TextView tvStudentName, tvClassName, tvStudentSchool, tvLivingIn, tvIsmRank, tvIsmRankLabel, tvScore, tvScoreLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            imgStudentPic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);

            tvStudentName = (TextView) itemView.findViewById(R.id.tv_student_name);
            tvClassName = (TextView) itemView.findViewById(R.id.tv_class_name);
            tvStudentSchool = (TextView) itemView.findViewById(R.id.tv_student_school);
            tvLivingIn = (TextView) itemView.findViewById(R.id.tv_living_in);
            tvIsmRank = (TextView) itemView.findViewById(R.id.tv_ism_rank);
            tvIsmRankLabel = (TextView) itemView.findViewById(R.id.tv_ism_rank_label);
            tvScore = (TextView) itemView.findViewById(R.id.tv_score);
            tvScoreLabel = (TextView) itemView.findViewById(R.id.tv_score_label);

            tvStudentName.setTypeface(Global.myTypeFace.getRalewayBold());
            tvClassName.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvStudentSchool.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvLivingIn.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvIsmRank.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvIsmRankLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvScore.setTypeface(Global.myTypeFace.getRalewayRegular());
            tvScoreLabel.setTypeface(Global.myTypeFace.getRalewayRegular());
        }
    }

    @Override
    public MyStudentsUserProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_mystudents_fromuser_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyStudentsUserProfileAdapter.ViewHolder holder, int position) {
        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + "Users_Images/" + arrListmyStudents.get(position).getProfilePic(), holder.imgStudentPic, ISMTeacher.options);

        holder.tvStudentName.setText(arrListmyStudents.get(position).getFullName());
        holder.tvStudentSchool.setText(mContext.getString(R.string.str_student_from) + " " + arrListmyStudents.get(position).getSchoolName());
        /**
         * Needed class name,living in,ism rank and score in api response
         */
    }

    @Override
    public int getItemCount() {
        return arrListmyStudents.size();
    }


}
