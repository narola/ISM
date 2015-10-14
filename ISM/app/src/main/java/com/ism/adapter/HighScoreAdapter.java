package com.ism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.R;
import com.ism.model.HighScoreModel;
import com.ism.object.MyTypeFace;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15.
 */
public class HighScoreAdapter extends BaseAdapter {
    private final LayoutInflater inflater;

    public HighScoreAdapter(Context context, ArrayList<HighScoreModel> arrayListSubject) {
        this.context = context;
        this.arrayListSubject = arrayListSubject;
        this.inflater= LayoutInflater.from(context);
    }

    Context context;
    ArrayList<HighScoreModel> arrayListSubject=new ArrayList<HighScoreModel>();

    @Override
    public int getCount() {
        return arrayListSubject.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSubject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.row_high_score,parent,false);
        TextView txt_highScoreSubject=(TextView)convertView.findViewById(R.id.txt_highScoreSubject);
        ListView lv_highScoreStudentList=(ListView)convertView.findViewById(R.id.lv_highScoreStudentList);
        txt_highScoreSubject.setTypeface(new MyTypeFace(context).getRalewayRegular());
        txt_highScoreSubject.setText(arrayListSubject.get(position).getStrSubjectName());
        lv_highScoreStudentList.setAdapter(new HighScoreStudentListAdapter(context, arrayListSubject.get(position).getArrayListStudent()));
        lv_highScoreStudentList.setDivider(new ColorDrawable(Color.parseColor("#f4f4f4")));
        lv_highScoreStudentList.setDividerHeight(1);
        return convertView;
    }
}
