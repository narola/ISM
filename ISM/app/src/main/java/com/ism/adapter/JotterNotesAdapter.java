package com.ism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.fragment.userProfile.AllNoticeFragment;
import com.ism.object.MyTypeFace;
import com.ism.ws.model.Notice;

import java.util.ArrayList;

/**
 * Created by c162 on 24/12/15.
 */
public class JotterNotesAdapter extends RecyclerView.Adapter<JotterNotesAdapter.ViewHolder> {

    private static final String TAG = JotterNotesAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Notice> arrListNotice;
    private MyTypeFace myTypeFace;
//	private HostActivity activityHost;

    public JotterNotesAdapter(Context context, ArrayList<Notice> arrListNotice) {
        this.context = context;
        this.arrListNotice = arrListNotice;
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jotter, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            if (position == 0) {
                holder.rrMain.setBackgroundColor(Color.parseColor("#D6DBDF"));
            }
            holder.txtSubject.setText(arrListNotice.get(position).getNoticeTitle());
            holder.txtNotename.setText(arrListNotice.get(position).getNotice());

            holder.txtNoteBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleAllNotice = new Bundle();
                    bundleAllNotice.putParcelableArrayList(AllNoticeFragment.ARG_ARR_LIST_NOTICE, arrListNotice);
//					activityHost.loadFragment(HostActivity.FRAGMENT_ALL_NOTICE, bundleAllNotice);
                }
            });
            holder.rrMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.rrMain.setBackgroundColor(Color.parseColor("#D6DBDF"));
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rrMain;
        TextView txtSubject, txtNotename, txtNoteBy;
        public ImageView imgMedia;

        public ViewHolder(View view) {
            super(view);
            txtSubject = (TextView) view.findViewById(R.id.txt_subject);
            txtNotename = (TextView) view.findViewById(R.id.txt_note_name);
            txtNoteBy = (TextView) view.findViewById(R.id.txt_notes_by);
            imgMedia = (ImageView) view.findViewById(R.id.img_media_files);
            rrMain = (RelativeLayout) view.findViewById(R.id.rr_main);

            txtSubject.setTypeface(myTypeFace.getRalewayRegular());
            txtNotename.setTypeface(myTypeFace.getRalewayThin());
            txtNoteBy.setTypeface(myTypeFace.getRalewayLight());

        }
    }

}
