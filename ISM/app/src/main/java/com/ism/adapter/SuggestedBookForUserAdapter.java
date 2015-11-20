package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.object.ISMStudent;
import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.ws.model.Suggested;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class SuggestedBookForUserAdapter extends BaseAdapter {
    private static final String TAG = UserFavoriteBooksAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<Suggested> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;

    public SuggestedBookForUserAdapter(Context context, ArrayList<Suggested> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }


    @Override
    public int getCount() {
        return 3;
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

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_book_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgBookLike = (ImageView) convertView.findViewById(R.id.img_book_like);
            holder.imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_book_name);
            holder.txtBookAuthor = (TextView) convertView.findViewById(R.id.txt_book_author);

            holder.imgBookAdd.setVisibility(View.VISIBLE);
            holder.imgBookLike.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.txtBookAuthor.setTypeface(myTypeFace.getRalewayRegular());

            holder.txtBookName.setTypeface(myTypeFace.getRalewayRegular());

//			imageLoader.displayImage(AppConstant.URL_USERS_IMAGE_PATH + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
            imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgBook, ISMStudent.options);
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            holder.txtBookAuthor.setText(arrayList.get(position).getAuthorName());
            // if(arrayList.get(position).ge)


        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    public class ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgBookLike;
        private ImageView imgBookAdd;
        private TextView txtBookAuthor;
        private TextView txtBookName;


    }
}
