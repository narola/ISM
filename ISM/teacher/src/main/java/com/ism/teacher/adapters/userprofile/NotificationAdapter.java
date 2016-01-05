package com.ism.teacher.adapters.userprofile;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.commonsource.utility.Utility;
import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.model.Notification;

import java.util.ArrayList;

/**
 * Created by c162 on 27/11/15.
 */
public class NotificationAdapter extends BaseAdapter {

	private static final String TAG = NotificationAdapter.class.getSimpleName();

	private ArrayList<Notification> arrListNotification;
	private Context context;
	private LayoutInflater inflater;
	private int listItemLimit = 0;

	public NotificationAdapter(Context context, ArrayList<Notification> arrListNotification) {
		this.arrListNotification = arrListNotification;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	public NotificationAdapter(Context context, ArrayList<Notification> arrListNotification, int listItemLimit) {
		this(context, arrListNotification);
		this.listItemLimit = listItemLimit;
	}

	@Override
	public int getCount() {
		return listItemLimit > 0 ? listItemLimit < arrListNotification.size() ? listItemLimit : arrListNotification.size() :arrListNotification.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListNotification.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_notification, parent, false);
			holder = new ViewHolder();
			holder.imgDp = (CircleImageView) convertView.findViewById(R.id.img_dp);
			holder.txtNameNotification = (TextView) convertView.findViewById(R.id.txt_name_message);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
			holder.txtNameNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
			holder.txtTime.setTypeface(Global.myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListNotification.get(position).getNotificationFromProfilePic(),
                    holder.imgDp, ISMTeacher.options);
			//imageLoader.displayImage(Global.strProfilePic, holder.imgDp, ISMAuthor.options);
			holder.txtNameNotification.setText(Html.fromHtml("<font color='#41B1E3'>" + arrListNotification.get(position).getNotificationFromName()
                    + "</font><font color='#323941'> " + arrListNotification.get(position).getNotificationText() + "</font>"));
			holder.txtTime.setText(Utility.getTimeDuration(arrListNotification.get(position).getNotificationDate()));
            Log.e(TAG, "arrListNotification.get("+position+").getNotificationText() : " + arrListNotification.get(position).getNotificationText());

            if (arrListNotification.get(position).getIsRead().equals("1")) {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                convertView.setBackgroundResource(R.drawable.shape_unread_notification);
            }

        } catch (Exception e) {
            Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		CircleImageView imgDp;
		TextView txtNameNotification, txtTime;
	}

}