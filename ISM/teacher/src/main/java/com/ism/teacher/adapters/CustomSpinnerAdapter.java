package com.ism.teacher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.teacher.R;


/**
 * Created by c162 on 08/10/15.
 */
public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    String list[];
    public CustomSpinnerAdapter(Context ctx, String[] objects) {

        this.context=ctx;
        this.list=objects;
    }

//    @Override
//    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
//        return getCustomView(position, cnvtView, prnt);
//    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View cnvtView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View mySpinner = inflater.inflate(R.layout.simple_spinner, parent, false);
        TextView main_text = (TextView) mySpinner.findViewById(R.id.txt_title);
        main_text.setText(list[position]);

        return mySpinner;
        //return getCustomView(pos, cnvtView, prnt);
    }

//    public View getCustomView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);
//        TextView main_text = (TextView) mySpinner.findViewById(R.id.myspinner);
//        main_text.setText(list[position]);
//
//        return mySpinner;
//    }



}
