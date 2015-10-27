package com.ism.author.adapter;

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
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 26/10/15.
 */
public class SearchStudyMatesAdapter extends RecyclerView.Adapter<SearchStudyMatesAdapter.ViewHolder> {


    private static final String TAG = SearchStudyMatesAdapter.class.getSimpleName();

    Context mContext;
    ArrayList<Data> listOfStudyMates = new ArrayList<Data>();
    Fragment fragment;
    public ArrayList<String> tagIds = new ArrayList<String>();

    public ArrayList<String> getTagIds() {
        return tagIds;
    }


    public SearchStudyMatesAdapter(Fragment fragment) {
        this.fragment = fragment;

    }


    @Override
    public SearchStudyMatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_studymates, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchStudyMatesAdapter.ViewHolder holder, final int position) {

        holder.txtStudymateName.setText(listOfStudyMates.get(position).getFullName());

        holder.chkAddusertotag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (holder.chkAddusertotag.isChecked()) {

                    tagIds.add(listOfStudyMates.get(position).getUserId());

                } else {
                    tagIds.remove(listOfStudyMates.get(position).getUserId());
                }


            }
        });


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
