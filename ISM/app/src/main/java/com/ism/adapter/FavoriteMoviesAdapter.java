package com.ism.adapter;

import android.content.Context;
import android.view.Gravity;
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
import com.ism.ws.model.MovieData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 20/11/15.
 */
public class FavoriteMoviesAdapter extends BaseAdapter {
    private static final String TAG = FavoriteMoviesAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<MovieData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;

    public FavoriteMoviesAdapter(Context context, ArrayList<MovieData> arrayList) {
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

            holder.imgMovie = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.txtMovieName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtYear = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgLike = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgLike.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.txtMovieName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtYear.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtMovieName.setGravity(Gravity.LEFT);
            holder.txtYear.setGravity(Gravity.LEFT);
            holder.imgLike.setBackgroundResource(R.drawable.img_like_red);
            imageLoader.displayImage(WebConstants.URL_HOST_202+arrayList.get(position).getMovieImage(), holder.imgMovie, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtMovieName.setText(arrayList.get(position).getMovieName());
            holder.txtYear.setText(arrayList.get(position).getMovieGenre());
            // if(arrayList.get(position).ge)


        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    public class ViewHolder {

        private ImageView imgMovie;
        private TextView txtMovieName;
        private TextView txtYear;
        public ImageView imgLike;
        public ImageView imgInfo;
    }
}
