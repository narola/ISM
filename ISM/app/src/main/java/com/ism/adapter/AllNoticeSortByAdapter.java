package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

/**
 * Created by c161 on 05/11/15.
 */
public class AllNoticeSortByAdapter extends BaseAdapter {

	private static final String TAG = AllNoticeSortByAdapter.class.getSimpleName();

	private Context context;
	private String[] arrStrSortBy;
	private LayoutInflater inflater;
	private MyTypeFace myTypeFace;

	public AllNoticeSortByAdapter(Context context) {
		this.context = context;
		arrStrSortBy = context.getResources().getStringArray(R.array.notice_sortby);
		inflater = LayoutInflater.from(context);
		myTypeFace = new MyTypeFace(context);
	}

	@Override
	public int getCount() {
		return arrStrSortBy.length;
	}

	@Override
	public Object getItem(int position) {
		return arrStrSortBy[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_simple_light, parent, false);
			holder = new ViewHolder();
			holder.txtSortBy = (TextView) convertView.findViewById(R.id.text);
			holder.txtSortBy.setTypeface(myTypeFace.getRalewayThin());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.txtSortBy.setText(arrStrSortBy[position]);
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		TextView txtSortBy;
	}
}
