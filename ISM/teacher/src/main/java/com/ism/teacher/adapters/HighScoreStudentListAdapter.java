package com.ism.teacher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.HighScoreStudentModel;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15.
 */
public class HighScoreStudentListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;

    public HighScoreStudentListAdapter(Context context, ArrayList<HighScoreStudentModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(context);

    }

    ArrayList<HighScoreStudentModel> arrayList = new ArrayList<HighScoreStudentModel>();

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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.row_highscore_sublist, parent, false);
        TextView raw_txt_highScoreStudentName = (TextView) convertView.findViewById(R.id.txt_highScoreStudentName);
        TextView raw_txt_highScoreSchoolYearClass = (TextView) convertView.findViewById(R.id.txt_highScoreSchoolYearClass);
        TextView raw_txt_highScoreMarks = (TextView) convertView.findViewById(R.id.txt_highScoreMarks);
        raw_txt_highScoreStudentName.setTypeface(new MyTypeFace(context).getRalewayRegular());
        raw_txt_highScoreSchoolYearClass.setTypeface(new MyTypeFace(context).getRalewayThin());
        raw_txt_highScoreMarks.setTypeface(new MyTypeFace(context).getRalewayBold());
        raw_txt_highScoreStudentName.setText(arrayList.get(position).getStrStudentName());
        raw_txt_highScoreSchoolYearClass.setText(arrayList.get(position).getStrStudentSchoolYearClass());
        raw_txt_highScoreMarks.setText(arrayList.get(position).getStrStudentMarks());

        return convertView;
    }
}
