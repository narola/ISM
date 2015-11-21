package com.ism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.object.ISMStudent;
import com.ism.R;
import com.ism.commonsource.utility.Utility;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 07/11/15.
 */
public class NotificationAdapter extends BaseAdapter {

	private static final String TAG = NotificationAdapter.class.getSimpleName();

	private ArrayList<Data> arrListNotification;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private int listItemLimit = 0;

	public NotificationAdapter(Context context, ArrayList<Data> arrListNotification) {
		this.arrListNotification = arrListNotification;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
	}

	public NotificationAdapter(Context context, ArrayList<Data> arrListNotification, int listItemLimit) {
		this(context, arrListNotification);
		this.listItemLimit = listItemLimit;
	}

	@Override
	public int getCount() {
		return listItemLimit > 0 ? listItemLimit : arrListNotification.size();
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
			holder.txtNameNotification.setTypeface(myTypeFace.getRalewayRegular());
			holder.txtTime.setTypeface(myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			imageLoader.displayImage(WebConstants.URL_USERS_IMAGE_PATH + arrListNotification.get(position).getProfilePic(),
					holder.imgDp, ISMStudent.options);
			imageLoader.displayImage(Global.strProfilePic, holder.imgDp, ISMStudent.options);
			holder.txtNameNotification.setText(Html.fromHtml("<font color='#1BC4A2'>" + arrListNotification.get(position).getNotificationFromName()
					+ "</font><font color='#323941'> " + arrListNotification.get(position).getNotificationText() + "</font>"));
			holder.txtTime.setText(Utility.getTimeDuration(arrListNotification.get(position).getNotificationDate()));

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