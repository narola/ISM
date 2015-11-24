package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.MovieData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class SuggestedMoviesAdapter extends BaseAdapter implements WebserviceWrapper.WebserviceResponse {
    private static final String TAG = SuggestedMoviesAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<MovieData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    SuggestedBookAdapter.AddToFavouriteListner addToFavouriteListner;
    private int addToFavItemId;

    public SuggestedMoviesAdapter(Context context, ArrayList<MovieData> arrayList, SuggestedBookAdapter.AddToFavouriteListner addToFavouriteListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.addToFavouriteListner = addToFavouriteListner;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user_books, null);
            holder = new ViewHolder();

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgMovieToFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtMovieName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtMovieYear = (TextView) convertView.findViewById(R.id.txt_author);

         //   holder.imgBookAdd.setVisibility(View.VISIBLE);
            holder.imgMovieToFav.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.txtMovieYear.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtMovieName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtMovieName.setGravity(Gravity.LEFT);
            holder.txtMovieYear.setGravity(Gravity.LEFT);
            holder.txtMovieName.setText(arrayList.get(position).getMovieName());
            holder.txtMovieYear.setText(arrayList.get(position).getMovieGenre());
            imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getMovieImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.imgMovieToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavItemId = position;
                    callApiAddResourceToFav(position);

                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    private void callApiAddResourceToFav(int position) {
        try {
            if (Utility.isConnected(context)) {
                ((HostActivity) context).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId("1");
                attribute.setResourceId(arrayList.get(position).getMovieId());
                attribute.setResourceName(AppConstant.RESOURCE_MOVIES);

                new WebserviceWrapper(context, attribute, this).new WebserviceCaller().execute(WebConstants.ADD_RESOURCE_TO_FAVORITE);
            } else {
                Utility.alertOffline(context);
            }
        } catch (Exception e) {
            Debug.i(TAG, "callApiAddResourceToFav Exception : " + e.getLocalizedMessage());
        }
    }

    private void onResponseAddResourceToFavorite(Object object, Exception error) {
        try {
            ((HostActivity) context).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.i(TAG, "onResponseAddResourceToFavorite success" + addToFavouriteListner);
                    if (addToFavouriteListner != null)
                        addToFavouriteListner.onAddToFav(addToFavItemId);

                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Debug.i(TAG, "onResponseAddResourceToFavorite Failed");
                }
            } else if (error != null) {
                Debug.i(TAG, "onResponseAddResourceToFavorite api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseAddResourceToFavorite Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.ADD_RESOURCE_TO_FAVORITE:
                    onResponseAddResourceToFavorite(object, error);
                    break;

            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    public class ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgMovieToFav;
        private ImageView imgBookAdd;
        private TextView txtMovieYear;
        private TextView txtMovieName;


    }
}
