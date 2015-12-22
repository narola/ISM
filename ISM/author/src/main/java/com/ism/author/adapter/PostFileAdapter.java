package com.ism.author.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.PostFeedActivity;
import com.ism.author.model.PostFileModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by c162 on 16/10/15.
 */
public class PostFileAdapter extends BaseAdapter {
    private static final String TAG = PostFileAdapter.class.getSimpleName();
    private LayoutInflater inflater;
    ArrayList<PostFileModel> arrayList = new ArrayList<>();
    Context context;
    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
    private Bitmap bitmap;

    public PostFileAdapter(ArrayList<PostFileModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setArrayList(ArrayList<PostFileModel> arrayList) {
        this.arrayList = arrayList;
    }

    public PostFileAdapter() {
        // this.inflater = LayoutInflater.from(context);
    }

    public ArrayList<PostFileModel> getArrayList() {
        Log.e("ArrayList size", "" + this.arrayList.size());
        return this.arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            convertView = inflater.inflate(R.layout.row_post_files, parent, false);
            RelativeLayout rlMain = (RelativeLayout) convertView.findViewById(R.id.rl_image);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_video);
            TextView videoIndicator = (TextView) convertView.findViewById(R.id.txt_video);
            ImageView imgClose = (ImageView) convertView.findViewById(R.id.img_cancel);
            ImageView imgPlay = (ImageView) convertView.findViewById(R.id.img_play);
            imageView.setVisibility(View.VISIBLE);
            Log.e("File", "" + arrayList.get(position).getStrFilePath());
            if (arrayList.get(position).getStrFileType().equals(PostFeedActivity.IMAGE)) {
                if (arrayList.get(position).getStrFilePath() != null) {
                    //  Uri uri = Uri.fromFile(new File(arrayList.get(position).getStrFilePath()));

                    Picasso.with(context)
                            .load(arrayList.get(position).getStrFilePath())
                            .error(R.drawable.ic_launcher)
                            .placeholder(R.drawable.selector_loading)
                            .into(imageView);
                    // bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(arrayList.get(position).getStrFilePath()));

                    // imageView.setImageBitmap(bitmap);

                }

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (arrayList.get(position).getStrFileType().equals(PostFeedActivity.VIDEO)) {
                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                mMediaMetadataRetriever.setDataSource(context, arrayList.get(position).getStrFilePath());
                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                imageView.setImageBitmap(bitmap);
                //videoIndicator.setText(mediaPlayer.getDuration() + "");
                videoIndicator.setVisibility(View.VISIBLE);
                imgPlay.setVisibility(View.VISIBLE);


            } else if (arrayList.get(position).getStrFileType().equals(PostFeedActivity.AUDIO)) {
               // videoIndicator.setText(mediaPlayer.getDuration() + "");
               videoIndicator.setVisibility(View.VISIBLE);
                imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.audioplay));
            }
            imgClose.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                }
            });
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayList.remove(position);
                    notifyDataSetChanged();
                }
            });
            imgClose.setVisibility(View.VISIBLE);
        }
        catch (Exception e){
            Debug.i(TAG,"getView Exception : "+e.getLocalizedMessage());
        }
        return convertView;
    }
}

