package com.ism.author.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

import java.util.List;

/**
 * Created by c166 on 02/11/15.
 */
public class SmallSpinnerAdapters {


    private static final String TAG = SmallSpinnerAdapters.class.getSimpleName();

    public static void setUpSpinner(final Context context, Spinner spinner, List<String> strArr) {
        final MyTypeFace myTypeFace = new MyTypeFace(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_small, strArr) {

            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTypeface(myTypeFace.getRalewayRegular());
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
                textView.setTypeface(myTypeFace.getRalewayRegular());
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
}
