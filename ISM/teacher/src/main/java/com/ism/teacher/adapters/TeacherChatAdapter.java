package com.ism.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class TeacherChatAdapter extends RecyclerView.Adapter<TeacherChatAdapter.ViewHolder> {

    private static final String TAG = TeacherChatAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Students> arrListmyStudents = new ArrayList<>();


    public TeacherChatAdapter(Context context, ArrayList<Students> arrListmyStudents) {
        this.mContext = context;
        this.arrListmyStudents = arrListmyStudents;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgStudentPic;
        TextView tvStudentName;
        ImageView img_online,img_separator;

        public ViewHolder(View itemView) {
            super(itemView);

            imgStudentPic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);
            tvStudentName = (TextView) itemView.findViewById(R.id.tv_student_name);
            img_online=(ImageView)itemView.findViewById(R.id.img_online);
            img_separator=(ImageView)itemView.findViewById(R.id.img_separator);

            tvStudentName.setTypeface(Global.myTypeFace.getRalewayRegular());
        }
    }

    @Override
    public TeacherChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View assignments_view = inflater.inflate(R.layout.row_teacher_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(assignments_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TeacherChatAdapter.ViewHolder holder, int position) {
        Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListmyStudents.get(position).getProfilePic(), holder.imgStudentPic, ISMTeacher.options);

        holder.tvStudentName.setText(arrListmyStudents.get(position).getFullName());

        if(arrListmyStudents.get(position).getIsOnline().equalsIgnoreCase("0"))
        {
            holder.img_online.setBackground(mContext.getResources().getDrawable(R.drawable.bg_offline));
        }
        else
        {
            holder.img_online.setBackground(mContext.getResources().getDrawable(R.drawable.bg_online));
        }
        /**
         * Needed class name,living in,ism rank and score in api response
         */
    }

    @Override
    public int getItemCount() {
        return arrListmyStudents.size();
    }


}
