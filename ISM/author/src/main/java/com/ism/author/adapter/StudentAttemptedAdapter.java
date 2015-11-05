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
import com.ism.author.constant.WebConstants;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.ImageLoaderInit;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.ResponseObject;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 04/11/15.
 */
public class StudentAttemptedAdapter extends RecyclerView.Adapter<StudentAttemptedAdapter.Viewholder> {
    ArrayList<Data> arrayList = new ArrayList<Data>();
    ResponseObject responseObject;
    Context context;
    Fragment fragment;
    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    ImageLoader imageLoader;
    private String TAG=StudentAttemptedAdapter.class.getSimpleName();

    public StudentAttemptedAdapter(ResponseObject responseObject, Context context, Fragment fragment) {
        this.responseObject = responseObject;
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
        imageLoader = ImageLoader.getInstance();
        //imageLoader.init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.row_student_attempted, parent, false);
        Viewholder viewholder=new Viewholder(convertView);
        myTypeFace = new MyTypeFace(context);
        imageLoader = ImageLoader.getInstance();
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, int position) {
    ArrayList<Data> arrayList=responseObject.getData().get(0).getEvoluations();
        try {
            Debug.i(TAG, "FullName:" + arrayList.get(position).getFullName());
            viewholder.txtStudentName.setText(arrayList.get(position).getFullName());
            viewholder.txtSchoolName.setText("Exam Type : " + arrayList.get(position).getSchoolName());
            viewholder.txtClass.setText(arrayList.get(position).getClassName());
           // viewholder.txtScore.setText(arrayList.get(position).getEvoluationsScore());
            imageLoader.displayImage(WebConstants.USER_IMAGES+arrayList.get(position).getProfilePic(), viewholder.imgUserPic, ImageLoaderInit.options);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public long getItemId(int position) {
        return responseObject.getData().get(0).getEvoluations().size();
    }

    @Override
    public int getItemCount() {
        return responseObject.getData().get(0).getEvoluations().size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{
        CircleImageView imgUserPic;
        TextView txtStudentName, txtSchoolName, txtClass, txtScore;

        public Viewholder(View convertView) {
            super(convertView);
            imgUserPic = (CircleImageView) convertView.findViewById(R.id.img_user_pic);
            txtClass = (TextView) convertView.findViewById(R.id.txt_student_class);
            txtStudentName = (TextView) convertView.findViewById(R.id.txt_student_name);
            txtSchoolName = (TextView) convertView.findViewById(R.id.txt_student_school);
            txtScore = (TextView) convertView.findViewById(R.id.txt_student_marks);
            txtStudentName.setTypeface(myTypeFace.getRalewaySemiBold());
            txtSchoolName.setTypeface(myTypeFace.getRalewayRegular());
            txtClass.setTypeface(myTypeFace.getRalewayRegular());
            txtScore.setTypeface(myTypeFace.getRalewayBold());
        }
    }
}
