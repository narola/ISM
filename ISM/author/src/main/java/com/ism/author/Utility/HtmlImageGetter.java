package com.ism.author.Utility;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;

/**
 * Created by c85 on 08/12/15.
 */
public class HtmlImageGetter implements Html.ImageGetter {
    private int height, width;

    public HtmlImageGetter(int height, int width) {
        this.height = height;
        this.width = width;
    }



    @Override
    public Drawable getDrawable(String source) {

        Drawable d = null;
        try {
            String path = source.replace("file://", "");

            d = Drawable.createFromPath(path);
            d.setBounds(0, 0, height, width);
        } catch (Exception error) {
            Log.e("log_tag", "Image not found. Check the ID.", error);
        }
        return d;
    }
}
