package com.ism.author.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.object.Global;

import java.util.List;

/**
 * Created by c161 on 27/10/15.
 */
public class Adapters {

    private static final String TAG = Adapters.class.getSimpleName();

    public static int ADAPTER_SMALL = 0, ADAPTER_NORMAL = 1;
    static Integer layout;

    public static void setUpSpinner(final Context context, Spinner spinner, List<String> strArr, int ADAPTER_TYPE) {

        if (ADAPTER_TYPE == ADAPTER_SMALL) {

            layout = R.layout.simple_spinner_small;

        } else if (ADAPTER_TYPE == ADAPTER_NORMAL) {

            layout = R.layout.simple_spinner;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, layout, strArr) {

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTypeface(Global.myTypeFace.getRalewayRegular());
                if (position == 0) {
                    textView.setTextColor(context.getResources().getColor(R.color.color_text_hint));
                    return textView;
                } else {
                    textView.setTextColor(context.getResources().getColor(R.color.color_black));
                    return textView;
                }
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTypeface(Global.myTypeFace.getRalewayRegular());
                textView.setCompoundDrawables(null, null, null, null);

                if (position == 0) {
                    textView.setTextColor(context.getResources().getColorStateList(R.color.color_white));
                    textView.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
                    textView.setEnabled(false);
                    textView.setOnClickListener(null);
                } else {
                    textView.setTextColor(context.getResources().getColorStateList(R.color.color_dark_gray));
                    textView.setBackgroundResource(R.color.color_white);
                }

                return textView;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }
    public static void setUpSpinner(final Context context, Spinner spinner, String[] strArr, final Typeface typeface, int resIdLayout) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, resIdLayout, strArr) {

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                return getTextView(context, position, typeface, textView);
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                setTextViewDropdown(context, position, typeface, textView);
                return textView;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }
    private static void setTextViewDropdown(Context context, int position, Typeface typeface, TextView textView) {
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        textView.setCompoundDrawables(null, null, null, null);
        if (position == 0) {
            textView.setTextColor(context.getResources().getColorStateList(R.color.color_white));
            textView.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
            textView.setEnabled(false);
            textView.setOnClickListener(null);
        } else {
            textView.setTextColor(context.getResources().getColorStateList(R.color.color_dark_gray));
            textView.setBackgroundResource(R.color.color_white);
        }
    }

    private static View getTextView(Context context, int position, Typeface typeface, TextView textView) {
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        if (position == 0) {
            textView.setTextColor(context.getResources().getColor(R.color.color_text_hint));
            return textView;
        } else {
            textView.setTextColor(context.getResources().getColor(R.color.color_black));
            return textView;
        }
    }

}
