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
import com.ism.ISMStudent;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.ws.model.Favorite;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 20/11/15.
 */
public class UserFavMoviesAdapter extends BaseAdapter {
    private static final String TAG = UserFavMoviesAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<Favorite> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;

    public UserFavMoviesAdapter(Context context, ArrayList<Favorite> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }


    @Override
    public int getCount() {
        return 4;
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.txtMovieName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtYear.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtMovieName.setGravity(Gravity.LEFT);
            holder.txtYear.setGravity(Gravity.LEFT);

//			imageLoader.displayImage(AppConstant.URL_USERS_IMAGE_PATH + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgMovie, ISMStudent.options);
            holder.txtMovieName.setText(arrayList.get(position).getBookName());
            holder.txtYear.setText(arrayList.get(position).getAuthorName());
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


    }
}
