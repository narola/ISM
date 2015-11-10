package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.model.Data;
import com.ism.teacher.views.CircleImageView;

import java.util.ArrayList;

/**
 * Created by c75 on 10/11/15.
 */
public class MyStudentsAdapter extends RecyclerView.Adapter<MyStudentsAdapter.ViewHolder> {

    Fragment mFragment;
    Context mContext;
    ArrayList<Data> arrayListStudents = new ArrayList<>();

    public MyStudentsAdapter(Context context, Fragment fragment) {
        this.mFragment = fragment;
        this.mContext = context;
    }

    @Override
    public MyStudentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_my_students, parent, false);
//        View contactView = inflater.inflate(R.layout.assignment_student_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_student_name, txt_student_rollno;
        CircleImageView img_student_pic;
        ImageView img_online, img_separator;


        public ViewHolder(View itemView) {
            super(itemView);

            txt_student_name = (TextView) itemView.findViewById(R.id.txt_student_name);
            txt_student_rollno = (TextView) itemView.findViewById(R.id.txt_student_rollno);
            img_student_pic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);
            img_online = (ImageView) itemView.findViewById(R.id.img_online);
            img_separator = (ImageView) itemView.findViewById(R.id.img_separator);

        }
    }

    @Override
    public void onBindViewHolder(MyStudentsAdapter.ViewHolder holder, int position) {

        holder.txt_student_name.setText(arrayListStudents.get(position).getFull_name());
    }

    @Override
    public int getItemCount() {
        Log.e("test", "" + arrayListStudents.size());
        return arrayListStudents.size();
    }

    public void addAll(ArrayList<Data> data) {
        try {
            this.arrayListStudents.clear();
            this.arrayListStudents.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }
}
