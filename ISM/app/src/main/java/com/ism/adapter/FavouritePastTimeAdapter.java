package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
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
           // holder.imgInfo.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {


            holder.txtPastimeName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtName.setText("");
//			imageLoader.displayImage(AppConstant.URL_USERS_IMAGE_PATH + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgPastime, ISMStudent.options);
            holder.txtPastimeName.setText(arrayList.get(position).getPastimeName());


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
    }
}
