package com.ism.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import com.ism.R;

import java.util.ArrayList;

/**
 * Created by c162 on 13/10/15.
 */
public class MediaFileAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    ArrayList<MediaFilesModel> arrayList = new ArrayList<>();
    Context context;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.row_media_files, parent, false);
        if (arrayList.get(position).getStrFileType().equals("image")) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_image);
            imageView.setVisibility(View.VISIBLE);
//            if (arrayList.get(position).getBitmap().equals(null)) {
//                Picasso.with(context)
//                        .load(new File(AppConstant.imageCapturePath + File.separator + arrayList.get(position).getStrFile()))
//                        .error(R.drawable.ic_launcher)
//                        .placeholder(R.drawable.ic_launcher)
//                        .into(imageView);
//            }
//            else{
            imageView.setImageBitmap(arrayList.get(position).getBitmap());
        //}
            //imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.logo));
        } else if (arrayList.get(position).getStrFileType().equals("video")) {
            VideoView imageView = (VideoView) convertView.findViewById(R.id.video);
            imageView.setVisibility(View.VISIBLE);
            //imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.logo));
        } else if (arrayList.get(position).getStrFileType().equals("audio")) {

            ImageView imageView = (ImageView) convertView.findViewById(R.id.img_image);
            imageView.setVisibility(View.VISIBLE);
            imageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.logo));
        }
        return convertView;
    }
}
