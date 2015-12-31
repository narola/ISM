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
import com.ism.fragment.desk.JotterFragment;
import com.ism.object.Global;

import io.realm.RealmResults;
import model.Notes;

/**
 * Created by c162 on 24/12/15.
 */
public class JotterNotesAdapter extends RecyclerView.Adapter<JotterNotesAdapter.ViewHolder> {

    private static final String TAG = JotterNotesAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private Context context;
    private RealmResults<model.Notes> arrayList;
    //	private HostActivity activityHost;
    ViewNoteListener viewNoteListener;
    private int lastSelected;

    public void setViewNoteListener(ViewNoteListener viewNoteListener) {
        this.viewNoteListener = viewNoteListener;
    }

    public JotterNotesAdapter(Context context, RealmResults<Notes> arrListNotice, JotterFragment jotterFragment, int lastPosition) {
        this.context = context;
        this.arrayList = arrListNotice;
        this.viewNoteListener = jotterFragment;
        inflater = LayoutInflater.from(context);
        this.lastSelected = lastPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jotter, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
//            if (position == 0) {
//                holder.rrMain.setBackgroundColor(Color.parseColor("#D6DBDF"));
//            }
            holder.txtSubject.setText(arrayList.get(position).getNoteSubject());
            holder.txtNotename.setText(arrayList.get(position).getNoteName());
            holder.txtNoteBy.setText("By : " + arrayList.get(position).getUser().getFullName());

            holder.txtNoteBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleAllNotice = new Bundle();
                    //  bundleAllNotice.putParcelableArrayList(AllNoticeFragment.ARG_ARR_LIST_NOTICE, arrayList);
//					activityHost.loadFragment(HostActivity.FRAGMENT_ALL_NOTICE, bundleAllNotice);
                }
            });
            if (lastSelected == position) {
                holder.rrMain.setBackgroundColor(Color.parseColor("#D6DBDF"));
            } else {
                holder.rrMain.setBackgroundColor(Color.parseColor("#f7f7f7"));
            }
            holder.rrMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelected = position;
                    holder.rrMain.setBackgroundColor(Color.parseColor("#D6DBDF"));
                    notifyDataSetChanged();
                    viewNoteListener.onNoteListener(position);
                }
            });
//            if (arrayList.get(position).getAudioLink() != null && arrayList.get(position).getAudioLink().length() > 0) {
//                holder.imgMedia.setImageResource(R.drawable.ic_audio_gray);
//                holder.imgMedia.setVisibility(View.VISIBLE);
//            } else if (arrayList.get(position).getVideoLink() != null && arrayList.get(position).getVideoLink().length() > 0) {
//                holder.imgMedia.setVisibility(View.VISIBLE);
//                holder.imgMedia.setImageResource(R.drawable.ic_video_dark_gray);
//            }

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
        return arrayList.size();
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
            imgMedia = (ImageView) view.findViewById(R.id.img_media_video);
            rrMain = (RelativeLayout) view.findViewById(R.id.rr_main);

            txtSubject.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtNotename.setTypeface(Global.myTypeFace.getRalewayMedium());
            txtNoteBy.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }

    public interface ViewNoteListener {
        public void onNoteListener(int position);
    }

}
