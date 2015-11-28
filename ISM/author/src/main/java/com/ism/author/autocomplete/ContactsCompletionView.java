package com.ism.author.autocomplete;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.model.HashTagsModel;
import com.ism.author.object.MyTypeFace;

import java.util.Random;

/**
 * Sample token completion view for basic contact info
 * <p/>
 * Created on 9/12/13.
 *
 * @author mgod
 */
public class ContactsCompletionView extends TokenCompleteTextView<HashTagsModel> {

    private MyTypeFace myTypeFace;

    public ContactsCompletionView(Context context) {
        super(context);
        initTypeFace(context);


    }

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeFace(context);

    }

    public ContactsCompletionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTypeFace(context);

    }

    @Override
    protected View getViewForObject(HashTagsModel hashTagsModel) {


        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) l.inflate(R.layout.tag_layout, (ViewGroup) ContactsCompletionView.this.getParent(), false);
        ((TextView) view.findViewById(R.id.tv_tag_name)).setText(hashTagsModel.getTagName());
        ((TextView) view.findViewById(R.id.tv_tag_name)).setTypeface(myTypeFace.getRalewayRegular());


        Drawable background = ((TextView) view.findViewById(R.id.tv_tag_name)).getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(getRandomColor());
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(getRandomColor());
        }

        return view;
    }

    @Override
    protected HashTagsModel defaultObject(String completionText) {
        return new HashTagsModel(completionText.trim(), "0");
    }


    public static int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }

    private void initTypeFace(Context context) {
        myTypeFace = new MyTypeFace(context);
    }

}
