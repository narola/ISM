package com.ism.author.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.object.Global;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by c85 on 08/12/15.
 */
public class HtmlImageGetter implements Html.ImageGetter {


    String TAG = HtmlImageGetter.class.getSimpleName();

    private int height, width;
    private Context mContext;
    private RefreshDataAfterLoadImage refreshDataAfterLoadImage;

//    public HtmlImageGetter(int height, int width, Context context) {
//        this.height = height;
//        this.width = width;
//        this.mContext = context;
//    }


    public HtmlImageGetter(int height, int width, Context context, RefreshDataAfterLoadImage refreshDataAfterLoadImage) {
        this.height = height;
        this.width = width;
        this.mContext = context;
        this.refreshDataAfterLoadImage = refreshDataAfterLoadImage;
    }

    private TextView textView;
    private String htmlText;

    @Override
    public Drawable getDrawable(String source) {
        final Drawable[] d = {null};
        if (source.startsWith("http") || source.startsWith("https:")) {
            try {

                ImageSize targetSize = new ImageSize(width, height);
                Global.imageLoader.loadImage(source, targetSize, new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .showImageOnLoading(R.drawable.img_image)
                        .showImageForEmptyUri(R.drawable.img_image)
                        .showImageOnFail(R.drawable.img_image)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                        // Do whatever you want with Bitmap
                        d[0] = new BitmapDrawable(mContext.getResources(), loadedImage);
                        d[0].setBounds(0, 0,
                                height,
                                width);

                        /**
                         * This is to call refresh data only for the first time when each image gets loaded.
                         */

                        if (refreshDataAfterLoadImage != null) {
                            refreshDataAfterLoadImage.refreshData();
                        }
                    }
                });
            } catch (Exception error) {
                Log.e("log_tag", "Image not found. Check the Image Url.", error);
            }
        } else {
            try {
                String path = source.replace("file://", "");
                d[0] = Drawable.createFromPath(path);
                d[0].setBounds(0, 0, height, width);
            } catch (Exception error) {
                Log.e("log_tag", "Image not found. Check the ID.", error);
            }
        }


        return d[0];
    }


    public interface RefreshDataAfterLoadImage {
        public void refreshData();
    }
}
