package com.ism.teacher.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.object.Global;
import com.ism.teacher.object.MyTypeFace;

import java.util.List;

/**
 * Created by c161 on 27/10/15.
 */
public class Adapters {

    private static final String TAG = Adapters.class.getSimpleName();

    /**
     * @param mContext
     * @param spinner
     * @param strArr
     */

    public static int ADAPTER_SMALL = 0, ADAPTER_NORMAL = 1;
    static Integer layout;

    //Question List
    public static void setUpSpinner(final Context context, Spinner spinner, List<String> strArr, int ADAPTER_TYPE) {
        final MyTypeFace myTypeFace = new MyTypeFace(context);

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
                    textView.setTextColor(context.getResources().getColorStateList(R.color.white));
                    textView.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
                    textView.setEnabled(false);
                    textView.setOnClickListener(null);
                } else {
                    textView.setTextColor(context.getResources().getColorStateList(R.color.color_dark_gray));
                    textView.setBackgroundResource(R.color.white);
                }

                return textView;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }


    public static void setUpSpinner(final Context context, Spinner spinner, List<String> strArr) {
        final MyTypeFace myTypeFace = new MyTypeFace(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner, strArr) {

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
                    textView.setTextColor(context.getResources().getColorStateList(R.color.white));
                    textView.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
                    textView.setEnabled(false);
                    textView.setOnClickListener(null);
                } else {
                    textView.setTextColor(context.getResources().getColorStateList(R.color.color_dark_gray));
                    textView.setBackgroundResource(R.color.white);
                }

                return textView;
            }
        };
        adapter.setDropDownViewResource(R.layout.row_spinner);
        spinner.setAdapter(adapter);
    }


}
