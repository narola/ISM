package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.teacher.model.Comment;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * these is the postfeedcommentsadapter
 */
public class TagStudyMatesAdapter extends RecyclerView.Adapter<TagStudyMatesAdapter.ViewHolder> {

    private static final String TAG = TagStudyMatesAdapter.class.getSimpleName();

    Context context;
    ArrayList<Data> listOfStudyMates = new ArrayList<>();

    View.OnClickListener tagstudyMatesListener;
    Fragment fragment;

//    public TagStudyMatesAdapter(Context context, ArrayList<Data> listOfStudyMates, View.OnClickListener tagstudyMatesListener, Fragment fragment) {
//
//        this.context = context;
//        listOfStudyMates = listOfStudyMates;
//        this.tagstudyMatesListener = tagstudyMatesListener;
//        this.fragment = fragment;
//
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_studymates_teacher, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtStudymateName.setText(listOfStudyMates.get(position).getFull_name());
//        holder.imgStudymateDp.;
//        holder.chkAddusertotag;
    }


    public void addAll(ArrayList<Data> data) {

        try {
            this.listOfStudyMates.clear();
            this.listOfStudyMates.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfStudyMates.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgStudymateDp;
        TextView txtStudymateName;
        CheckBox chkAddusertotag;


        public ViewHolder(View itemView) {
            super(itemView);

            imgStudymateDp = (ImageView) itemView.findViewById(R.id.img_studymate_dp);
            txtStudymateName = (TextView) itemView.findViewById(R.id.txt_studymate_name);
            chkAddusertotag = (CheckBox) itemView.findViewById(R.id.chk_addusertotag);

        }
    }


}
