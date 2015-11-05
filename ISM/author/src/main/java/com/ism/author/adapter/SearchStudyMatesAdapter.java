package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Utils;
import com.ism.author.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these adapter class is for getting studymates to tag them.
 */
public class SearchStudyMatesAdapter extends RecyclerView.Adapter<SearchStudyMatesAdapter.ViewHolder> {


    private static final String TAG = SearchStudyMatesAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Data> listOfStudyMates = new ArrayList<Data>();
    public ArrayList<String> tagIds = new ArrayList<String>();

    public ArrayList<String> getTagIds() {
        return tagIds;
    }

    public SearchStudyMatesAdapter(Context mContext) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
    }

    private ImageLoader imageLoader;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_studymates, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

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


        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgStudymateDp, ISMAuthor.options);


    }

    public void addAll(ArrayList<Data> data) {
        try {

            this.listOfStudyMates.clear();
            this.listOfStudyMates.addAll(data);
            this.copyListOfStudyMates = data;

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


    ArrayList<Data> copyListOfStudyMates;

    public void filter(CharSequence charText) {
        listOfStudyMates.clear();

        if (charText.length() == 0) {
            listOfStudyMates.addAll(copyListOfStudyMates);
        } else {

            for (Data wp : copyListOfStudyMates) {
                if (wp.getFullName().contains(charText)) {
                    listOfStudyMates.add(wp);
                }
            }
            if (listOfStudyMates.size() == 0) {
                Utils.showToast(mContext.getString(R.string.strnoresult), mContext);
            }
        }
        notifyDataSetChanged();
    }


    private Context getActivity() {
        return mContext;

    }

}
