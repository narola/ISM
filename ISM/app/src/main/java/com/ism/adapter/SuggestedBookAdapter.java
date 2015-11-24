package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
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
import com.ism.ws.model.Book;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class SuggestedBookAdapter extends BaseAdapter implements WebserviceWrapper.WebserviceResponse {
    private static final String TAG = SuggestedBookAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<Book> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    private int addToFavItem;
    AddToFavouriteListner addToFavouriteListner;

    public SuggestedBookAdapter(Context context, ArrayList<Book> arrayList,AddToFavouriteListner favouriteBooksListner) {
        this.context = context;
        this.arrayList = arrayList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
        this.addToFavouriteListner = favouriteBooksListner;
    }

    public interface AddToFavouriteListner {
        public void onAddToFav(int position);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user_books, null);
            holder = new ViewHolder();

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgAddToFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtBookAuthor = (TextView) convertView.findViewById(R.id.txt_author);

            holder.imgBookAdd.setVisibility(View.VISIBLE);
            holder.imgAddToFav.setVisibility(View.VISIBLE);
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
//
            holder.imgAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavItem = position;
                    Debug.i(TAG, "onClickAddToFav : " + position);
                    callApiAddResourceToFav(position);
                }
            });
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToFavItem = position;
                    Debug.i(TAG, "onClickAddToFav : " + position);
                    callApiAddResourceToFav(position);
                }
            });


        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }
//    public PopupWindow myPopup(final int type) {
//
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = inflater.inflate(R.layout.layout_add_about_yourself, null);
//
//        final PopupWindow popupWindow = new PopupWindow(view,
//                400,
//                400, true);
//
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        // popupWindow.setTouchInterceptor(customPopUpTouchListenr);
//
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        // TextView txtDetails = (TextView) view.findViewById(R.id.txt_about_me_details);
//        final EditText etAboutMe = (EditText) view
//                .findViewById(R.id.et_clickAddAboutMe);
//        final TextView txtAboutMe = (TextView) view
//                .findViewById(R.id.txt_about_me);
//        TextView txtCancel = (TextView) view
//                .findViewById(R.id.txt_cancel);
//        if (type == ABOUT_ME) {
//            txtAboutMe.setText(R.string.strAboutYou);
//            etAboutMe.setText(strDetailAboutMe);
//            etAboutMe.setHint("Write about your self");
//        } else if (type == YOUR_AMBITION) {
//            txtAboutMe.setText(R.string.strYourAmbitionInLife);
//            etAboutMe.setText(strAmbition);
//            etAboutMe.setHint("Write about your ambition in life");
//        }
//
//        etAboutMe.setText(strDetailAboutMe);
//        txtCancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (type == ABOUT_ME) {
//                    strDetailAboutMe = etAboutMe.getText().toString().trim();
//                } else if (type == YOUR_AMBITION) {
//                    strAmbition = etAboutMe.getText().toString().trim();
//                }
//                popupWindow.dismiss();
//
//            }
//        });
//        popupWindow.setFocusable(true);
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setContentView(view);
//        return popupWindow;
//
//    }
    private void callApiAddResourceToFav(int position) {
        try {
            if (Utility.isConnected(context)) {
                ((HostActivity) context).showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId("1");
                attribute.setResourceId(arrayList.get(position).getBookId());
                attribute.setResourceName(AppConstant.RESOURCE_BOOKS);

                new WebserviceWrapper(context, attribute, this).new WebserviceCaller().execute(WebConstants.ADD_RESOURCE_TO_FAVORITE);
            } else {
                Utility.toastOffline(context);
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
                    Debug.i(TAG, "onResponseAddResourceToFavorite success");
                    addToFavouriteListner.onAddToFav(addToFavItem);
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
        private ImageView imgAddToFav;
        private ImageView imgBookAdd;
        private TextView txtBookAuthor;
        private TextView txtBookName;


    }
}
