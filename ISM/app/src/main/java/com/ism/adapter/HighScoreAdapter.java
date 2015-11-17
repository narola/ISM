package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.model.HighScoreSubject;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15
 * Updated by c161 on 17/11/15
 */
public class HighScoreAdapter extends BaseAdapter {

	private static final String TAG = HighScoreAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<HighScoreSubject> arrListSubject;
    private LayoutInflater inflater;
	private MyTypeFace myTypeFace;

    public HighScoreAdapter(Context context, ArrayList<HighScoreSubject> arrListSubject) {
        this.context = context;
        this.arrListSubject = arrListSubject;
        this.inflater= LayoutInflater.from(context);
	    myTypeFace = new MyTypeFace(context);
    }

    @Override
    public int getCount() {
        return arrListSubject.size();
    }

    @Override
    public Object getItem(int position) {
        return arrListSubject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if (convertView == null) {
		    convertView = inflater.inflate(R.layout.list_item_high_score, parent, false);
		    holder = new ViewHolder();
		    holder.txtSubject = (TextView) convertView.findViewById(R.id.txt_subject);
		    holder.llStudentList = (LinearLayout) convertView.findViewById(R.id.ll_student_list);

			holder.txtSubject.setTypeface(myTypeFace.getRalewayRegular());
		    convertView.setTag(holder);
	    } else {
		    holder = (ViewHolder) convertView.getTag();
	    }

		try {
			holder.txtSubject.setText(arrListSubject.get(position).getSubjectName());
			holder.llStudentList.removeAllViews();
			for (Data student : arrListSubject.get(position).getArrListStudent()) {
				holder.llStudentList.addView(getStudentView(student));
			}
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
    }

	private View getStudentView(Data data) {
		View studentView = inflater.inflate(R.layout.list_item_highscore_student, null);
		CircleImageView imgDp = (CircleImageView) studentView.findViewById(R.id.img_dp);
		TextView txtName = (TextView) studentView.findViewById(R.id.txt_student_name);
		TextView txtSchool = (TextView) studentView.findViewById(R.id.txt_school);
		TextView txtScore = (TextView) studentView.findViewById(R.id.txt_score);

		txtName.setTypeface(myTypeFace.getRalewayRegular());
		txtSchool.setTypeface(myTypeFace.getRalewayThin());
		txtScore.setTypeface(myTypeFace.getRalewayBold());

		txtName.setText(data.getUserName());
		txtSchool.setText(data.getSchoolName());
		txtScore.setText(data.getExamScore());

		return studentView;
	}

	class ViewHolder {
		TextView txtSubject;
		LinearLayout llStudentList;
	}
}