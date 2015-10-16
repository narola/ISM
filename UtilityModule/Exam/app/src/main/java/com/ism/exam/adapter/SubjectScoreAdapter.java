package com.ism.exam.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.model.SubjectScore;

import java.util.ArrayList;

/**
 * Created by c161 on 16/10/15.
 */
public class SubjectScoreAdapter extends BaseAdapter {

	private static final String TAG = SubjectScoreAdapter.class.getSimpleName();

	private ArrayList<SubjectScore> arrListScores;
	private Context context;
	private LayoutInflater inflater;

	public SubjectScoreAdapter(Context context, ArrayList<SubjectScore> arrListScores) {
		this.arrListScores = arrListScores;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return arrListScores.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListScores.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_result_graph, parent, false);
			holder = new ViewHolder();
			holder.txtSubject = (TextView) convertView.findViewById(R.id.txt_subject);
			holder.progScore = (ProgressBar) convertView.findViewById(R.id.prog_score);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.txtSubject.setText(arrListScores.get(position).getSubject());
			holder.progScore.setProgress(arrListScores.get(position).getScore());
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		TextView txtSubject;
		ProgressBar progScore;
	}

}
