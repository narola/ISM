package com.ism.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.teacher.R;
import com.ism.teacher.model.Data;
import com.ism.teacher.ws.model.Studymates;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Tag Study Mates Adapter close to display the list of studymates
 */
public class TagStudyMatesAdapter extends RecyclerView.Adapter<TagStudyMatesAdapter.ViewHolder> {

    private static final String TAG = TagStudyMatesAdapter.class.getSimpleName();

    Context context;
    ArrayList<Studymates> arrListStudyMates = new ArrayList<>();
    ArrayList<Studymates> copyListOfStudyMates = new ArrayList<>();

    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public TagStudyMatesAdapter(Context mContext) {
        this.context = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        inflater = LayoutInflater.from(mContext);
    }


    public ArrayList<String> tagIds = new ArrayList<String>();

    public ArrayList<String> getTagIds() {
        return tagIds;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_studymates_teacher, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtStudymateName.setText(arrListStudyMates.get(position).getFullName());

        holder.chkAddusertotag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (holder.chkAddusertotag.isChecked()) {

                    tagIds.add(arrListStudyMates.get(position).getUserId());


                } else {
                    tagIds.remove(arrListStudyMates.get(position).getUserId());
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return arrListStudyMates.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgStudymateDp, imgSeparatorMates;
        TextView txtStudymateName;
        CheckBox chkAddusertotag;


        public ViewHolder(View itemView) {
            super(itemView);

            imgStudymateDp = (ImageView) itemView.findViewById(R.id.img_studymate_dp);
            txtStudymateName = (TextView) itemView.findViewById(R.id.txt_studymate_name);
            chkAddusertotag = (CheckBox) itemView.findViewById(R.id.chk_addusertotag);
            imgSeparatorMates = (ImageView) itemView.findViewById(R.id.img_separator_mates);

        }
    }

    public void filter(CharSequence charText) {
        arrListStudyMates.clear();

        if (charText.length() == 0) {
            arrListStudyMates.addAll(copyListOfStudyMates);
        } else {

            for (Studymates wp : copyListOfStudyMates) {
                if (wp.getFullName().contains(charText)) {
                    arrListStudyMates.add(wp);
                }
            }
            if (arrListStudyMates.size() == 0) {
                Toast.makeText(context, "No search result found!", Toast.LENGTH_SHORT).show();
            }
        }
        notifyDataSetChanged();
    }

    private Context getActivity() {
        return context;

    }


    public void addAll(ArrayList<Studymates> studyMates) {
        try {

            this.arrListStudyMates.clear();
            this.arrListStudyMates.addAll(studyMates);
            this.copyListOfStudyMates = studyMates;

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }
}
