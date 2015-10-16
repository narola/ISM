package com.ism.exam.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.exam.R;
import com.ism.exam.model.TutorialGroupMember;
import com.ism.exam.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/15.
 */
public class TutorialGroupAdapter extends BaseAdapter {

	private static final String TAG = TutorialGroupAdapter.class.getSimpleName();
	private ArrayList<TutorialGroupMember> arrListMembers;
	private Context context;
	private LayoutInflater inflater;

	public TutorialGroupAdapter(Context context, ArrayList<TutorialGroupMember> arrListMembers) {
		this.arrListMembers = arrListMembers;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return arrListMembers.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListMembers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_tutorial_group, parent, false);
			holder = new ViewHolder();
			holder.imgDp = (ImageView) convertView.findViewById(R.id.img_circle_dp);
			holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
			holder.imgNotification = (ImageView) convertView.findViewById(R.id.img_notification);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.txtName.setText(Html.fromHtml("<font color='#333333' size='8'>" + arrListMembers.get(position).getName() + "</font><font color='#595959' size='15'> + " + arrListMembers.get(position).getSchoolName() + "</font>"));
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		ImageView imgDp;
		TextView txtName;
		ImageView imgNotification;
	}

}
