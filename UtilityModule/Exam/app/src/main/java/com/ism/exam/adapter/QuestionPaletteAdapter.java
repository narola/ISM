package com.ism.exam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.model.QuestionObjective;

import java.util.ArrayList;

/**
 * Created by c161 on 14/10/15.
 */
public class QuestionPaletteAdapter extends BaseAdapter {

	private static final String TAG = QuestionPaletteAdapter.class.getSimpleName();

	private ArrayList<QuestionObjective> arrListQuestions;
	private Context context;
	private LayoutInflater inflater;

	public QuestionPaletteAdapter(Context context, ArrayList<QuestionObjective> arrListQuestions) {
		this.context = context;
		this.arrListQuestions = arrListQuestions;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return arrListQuestions.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListQuestions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_item_question_palette, parent, false);
			holder = new ViewHolder();
			holder.txtQuestionNo = (TextView) convertView.findViewById(R.id.txt_question_no);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.txtQuestionNo.setText(position + 1 + "");
			if (position == 4) {
				holder.txtQuestionNo.setBackgroundResource(R.drawable.ic_current);
			} else if (arrListQuestions.get(position).isReviewLater()) {
				holder.txtQuestionNo.setBackgroundResource(R.drawable.ic_review);
			} else if (arrListQuestions.get(position).isSkipped()) {
				holder.txtQuestionNo.setBackgroundResource(R.drawable.ic_skip);
			} else if (arrListQuestions.get(position).isAnswered()) {
				holder.txtQuestionNo.setBackgroundResource(R.drawable.ic_answered);
			} else {
				holder.txtQuestionNo.setBackgroundResource(R.drawable.ic_not_answered);
			}
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		TextView txtQuestionNo;
	}

}
