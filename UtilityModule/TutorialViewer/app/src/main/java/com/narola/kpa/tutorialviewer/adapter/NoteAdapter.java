package com.narola.kpa.tutorialviewer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.narola.kpa.tutorialviewer.R;
import com.narola.kpa.tutorialviewer.database.model.Note;
import com.narola.kpa.tutorialviewer.object.Global;
import com.narola.kpa.tutorialviewer.utility.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Calendar;

import io.realm.RealmResults;

/**
 * Created by Krunal Panchal on 01/10/15.
 */
public class NoteAdapter extends BaseAdapter {

    private static final String TAG = NoteAdapter.class.getSimpleName();

    private Context mContext;
    private RealmResults<Note> mNotes;
    private NoteAdapterListener mNoteAdapterListener;
    private LayoutInflater mInflater;

    public interface NoteAdapterListener {
        void onListItemClicked(int position);
    }

    public NoteAdapter(Context mContext, RealmResults<Note> mNotes, NoteAdapterListener noteAdapterListener) {
        this.mNotes = mNotes;
        this.mContext = mContext;
        mNoteAdapterListener = noteAdapterListener;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_note, parent, false);
            holder = new ViewHolder();
            holder.textTutorialTime = (TextView) convertView.findViewById(R.id.text_tutorial_time);
            holder.textNoteText = (TextView) convertView.findViewById(R.id.text_note_text);
            holder.textNoteCreationTime = (TextView) convertView.findViewById(R.id.text_note_creation_time);
            holder.imageVideoThumbnail = (ImageView) convertView.findViewById(R.id.image_video_thumbnail);
            holder.imageDelete = (ImageView) convertView.findViewById(R.id.image_note_delete);
            holder.imageShare = (ImageView) convertView.findViewById(R.id.image_note_share);
            holder.textGetVideo = (TextView) convertView.findViewById(R.id.text_get_video);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            Picasso.with(mContext)
                    .load(new File(Global.imagePath + File.separator + mNotes.get(position).getNoteThumbnailName()))
                    .error(R.drawable.ic_share)
                    .placeholder(R.drawable.ic_share)
                    .into(holder.imageVideoThumbnail);

            holder.textTutorialTime.setText(Utility.stringForTime((int) mNotes.get(position).getTutorialTime()));
            holder.textNoteText.setText(mNotes.get(position).getNoteText());
            holder.textNoteCreationTime.setText(Utility.formatDateTime(mNotes.get(position).getNoteCreationTime()));

            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note.deleteNote(mNotes.get(position));
                    notifyDataSetChanged();
                }
            });

            holder.imageShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            holder.textGetVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNoteAdapterListener.onListItemClicked(position);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "getView Exception : " + e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        TextView textTutorialTime;
        TextView textNoteText;
        TextView textNoteCreationTime;
        ImageView imageVideoThumbnail;
        ImageView imageDelete;
        ImageView imageShare;
        TextView textGetVideo;
    }

}
