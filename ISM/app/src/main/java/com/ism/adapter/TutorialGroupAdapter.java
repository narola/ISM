package com.ism.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.views.CircleImageView;
import com.ism.ws.model.TutorialGroupMember;

import java.util.ArrayList;

/**
 * Created by c161 on 15/10/15.
 */
public class TutorialGroupAdapter extends BaseAdapter {

	private static final String TAG = TutorialGroupAdapter.class.getSimpleName();
	private ArrayList<TutorialGroupMember> arrListGroupMember;
	private Context context;
	private LayoutInflater inflater;

	public TutorialGroupAdapter(Context context, ArrayList<TutorialGroupMember> arrListGroupMember) {
		this.arrListGroupMember = arrListGroupMember;
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return arrListGroupMember.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListGroupMember.get(position);
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
			holder.imgDp = (CircleImageView) convertView.findViewById(R.id.img_circle_dp);
			holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
			holder.txtStatus = (TextView) convertView.findViewById(R.id.txt_status);
			holder.imgNotification = (ImageView) convertView.findViewById(R.id.img_notification);

			holder.txtName.setTypeface(Global.myTypeFace.getRalewayRegular());
			holder.txtStatus.setTypeface(Global.myTypeFace.getRalewayRegular());

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			holder.txtName.setText(Html.fromHtml("<font color='#333333'>" + arrListGroupMember.get(position).getFullName()
					+ "</font><br/><small><font color='#AEAEAE'>"
					+ (arrListGroupMember.get(position).getSchoolName() != null ? arrListGroupMember.get(position).getSchoolName() : "")
					+ "</font></small>"));
			Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrListGroupMember.get(position).getProfilePic(), holder.imgDp);
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		CircleImageView imgDp;
		TextView txtName, txtStatus;
		ImageView imgNotification;
	}

}
