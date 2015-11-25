package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.PastimeData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavouritePastTimeAdapter extends BaseAdapter {
    private static final String TAG = FavouritePastTimeAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<PastimeData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;

    public FavouritePastTimeAdapter(Context context, ArrayList<PastimeData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user_books, null);
            holder = new ViewHolder();
            holder.imgPastime = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.txtPastimeName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgLike = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgLike.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);
            holder.imgLike.setBackgroundResource(R.drawable.img_like_red);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.txtPastimeName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtName.setText("");
            holder.txtPastimeName.setText(arrayList.get(position).getPastimeName());
            imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getPastimeImage(), holder.imgPastime, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));

        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    public class ViewHolder {

        private ImageView imgPastime;
        private TextView txtPastimeName;
        private TextView txtName;
        private ImageView imgInfo;
        public ImageView imgLike;
    }
}
