package com.ism.author.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.constant.WebConstants;
import com.ism.author.model.HighScoreSubject;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.User;

import java.util.ArrayList;

/**
 * Created by c162 on 19/12/15
 */
public class HighScoreAdapter extends BaseAdapter {

	private static final String TAG = HighScoreAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<HighScoreSubject> arrListSubject;
    private LayoutInflater inflater;

    public HighScoreAdapter(Context context, ArrayList<HighScoreSubject> arrListSubject) {
        this.context = context;
        this.arrListSubject = arrListSubject;
        this.inflater= LayoutInflater.from(context);
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

			holder.txtSubject.setTypeface(Global.myTypeFace.getRalewayRegular());
		    convertView.setTag(holder);
	    } else {
		    holder = (ViewHolder) convertView.getTag();
	    }

		try {
			holder.txtSubject.setText(arrListSubject.get(position).getSubjectName());
			holder.llStudentList.removeAllViews();
			for (int i = 0; i < arrListSubject.get(position).getArrListStudent().size(); i++) {
				holder.llStudentList.addView(getStudentView(arrListSubject.get(position).getArrListStudent().get(i),
						i != (arrListSubject.get(position).getArrListStudent().size() - 1)));
			}
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	private View getStudentView(User student, boolean showDivider) {
		View studentView = inflater.inflate(R.layout.list_item_highscore_student, null);
		CircleImageView imgDp = (CircleImageView) studentView.findViewById(R.id.img_dp);
		TextView txtName = (TextView) studentView.findViewById(R.id.txt_student_name);
		TextView txtSchool = (TextView) studentView.findViewById(R.id.txt_school);
		TextView txtScore = (TextView) studentView.findViewById(R.id.txt_score);
		studentView.findViewById(R.id.divider).setVisibility(showDivider ? View.VISIBLE : View.GONE);

		txtName.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtSchool.setTypeface(Global.myTypeFace.getRalewayThin());
		txtScore.setTypeface(Global.myTypeFace.getRalewayBold());

		Global.imageLoader.displayImage(WebConstants.USER_IMAGES + student.getProfilePic(), imgDp, ISMAuthor.options);
		txtName.setText(student.getFullName());
		txtSchool.setText(student.getSchoolName());
		txtScore.setText(student.getExamScore());

		return studentView;
	}

	class ViewHolder {
		TextView txtSubject;
		LinearLayout llStudentList;
	}
}