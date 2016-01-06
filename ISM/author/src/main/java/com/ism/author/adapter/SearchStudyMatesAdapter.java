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
import com.ism.author.utility.Utility;
import com.ism.author.object.Global;
import com.ism.author.ws.model.Studymates;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * these adapter class is for getting studymates to tag them.
 */
public class SearchStudyMatesAdapter extends RecyclerView.Adapter<SearchStudyMatesAdapter.ViewHolder> {


    private static final String TAG = SearchStudyMatesAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Studymates> arrListStudyMates = new ArrayList<Studymates>();
    private ArrayList<String> arrListTagIds = new ArrayList<String>();

    public ArrayList<String> getArrListTagIds() {
        return arrListTagIds;
    }

    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public SearchStudyMatesAdapter(Context mContext) {
        this.mContext = mContext;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_studymates, parent, false);
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
                    arrListTagIds.add(arrListStudyMates.get(position).getUserId());
                } else {
                    arrListTagIds.remove(arrListStudyMates.get(position).getUserId());
                }


            }
        });


        imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgStudymateDp, ISMAuthor.options);


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

    @Override
    public int getItemCount() {
        return arrListStudyMates.size();
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
            txtStudymateName.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }


    ArrayList<Studymates> copyListOfStudyMates;

    public void filter(CharSequence charText) {
        arrListStudyMates.clear();

        if (charText.length() == 0) {
            arrListStudyMates.addAll(copyListOfStudyMates);
        } else {
            for (Studymates wp : copyListOfStudyMates) {
                if (Utility.containsString(wp.getFullName(), charText.toString(), false)) {
                    arrListStudyMates.add(wp);
                }
            }
            if (arrListStudyMates.size() == 0) {
                Utility.showToast(mContext.getString(R.string.strnoresult), mContext);
            }
        }
        notifyDataSetChanged();
    }


    private Context getActivity() {
        return mContext;

    }

}
