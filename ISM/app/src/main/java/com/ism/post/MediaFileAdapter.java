package com.ism.post;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.ism.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by c162 on 13/10/15.
 */
public class MediaFileAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    ArrayList<MediaFilesModel> arrayList = new ArrayList<>();
    Context context;
    MediaPlayer mediaPlayer=new MediaPlayer();
    MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
    public MediaFileAdapter(ArrayList<MediaFilesModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
        convertView = inflater.inflate(R.layout.row_media_files, parent, false);
        RelativeLayout rlMain = (RelativeLayout) convertView.findViewById(R.id.rl_image);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_image);
        TextView videoIndicator = (TextView) convertView.findViewById(R.id.txt_video);
        TextView txtClose = (TextView) convertView.findViewById(R.id.txt_close);
        imageView.setVisibility(View.VISIBLE);
        Log.e("File", "" + arrayList.get(position).getUriFile());
        if (arrayList.get(position).getStrFileType().equals("isImage")) {
            if (arrayList.get(position).getBitmap()!=null) {
                imageView.setImageBitmap(arrayList.get(position).getBitmap());
                txtClose.setVisibility(View.VISIBLE);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (arrayList.get(position).getStrFileType().equals("isVideo")) {
            imageView.setImageBitmap(arrayList.get(position).getBitmap());
            txtClose.setVisibility(View.VISIBLE);
            MediaPlayer mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(context, Uri.fromFile(arrayList.get(position).getUriFile()));
                Log.i("TV",mediaPlayer.getDuration()+"");

            } catch (IOException e) {
                e.printStackTrace();
            }
           videoIndicator.setText(mediaPlayer.getDuration()+"");
           videoIndicator.setVisibility(View.VISIBLE);

        } else if (arrayList.get(position).getStrFileType().equals("isAudio")) {
            txtClose.setVisibility(View.VISIBLE);
            MediaPlayer mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(context, Uri.fromFile(arrayList.get(position).getUriFile()));
                mediaPlayer.getDuration();

            } catch (IOException e) {
                e.printStackTrace();
            }
//
//            try {
//                mediaPlayer.setDataSource(context, arrayList.get(position).getUriFile());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            videoIndicator.setText(mediaPlayer.getDuration()+"");
            videoIndicator.setVisibility(View.VISIBLE);
            imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
            }
        });
txtClose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        arrayList.remove(position);
    }
});
        return convertView;
    }
}
