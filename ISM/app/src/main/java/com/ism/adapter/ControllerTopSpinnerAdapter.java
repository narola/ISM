package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;

import java.util.ArrayList;

/**
 * Created by Krunal Panchal on 09/10/15.
 */
public class ControllerTopSpinnerAdapter extends BaseAdapter {

	private static final String TAG = ControllerTopSpinnerAdapter.class.getSimpleName();

	private ArrayList<String> mMenu;
	private Context mContext;
	private LayoutInflater mInflater;

	public ControllerTopSpinnerAdapter(ArrayList<String> menu, Context context) {
		this.mMenu = menu;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mMenu.size();
	}

	@Override
	public Object getItem(int position) {
		return mMenu.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_simple_dark, parent, false);
			holder = new ViewHolder();
			holder.textTitle = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.textTitle.setText(mMenu.get(position));
			/*if (position == 0) {
				convertView.setBackgroundResource(R.drawable.bg_controller_top);
			} else {
				convertView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_controller_top_dark));
			}*/
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		TextView textTitle;
	}

}
