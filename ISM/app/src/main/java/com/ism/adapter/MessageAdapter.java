package com.ism.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.commonsource.utility.Utility;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.Message;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 07/11/15.
 */
public class MessageAdapter extends BaseAdapter {

	private static final String TAG = MessageAdapter.class.getSimpleName();

	private ArrayList<Message> arrListMessage;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private int listItemLimit = 0;

	public MessageAdapter(Context context, ArrayList<Message> arrListMessage) {
		this.arrListMessage = arrListMessage;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
	}

	public MessageAdapter(Context context, ArrayList<Message> arrListMessage, int listItemLimit) {
		this(context, arrListMessage);
		this.listItemLimit = listItemLimit;
	}

	@Override
	public int getCount() {
		return listItemLimit > 0 ? listItemLimit : arrListMessage.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListMessage.get(position);
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
			holder.txtNameMessage = (TextView) convertView.findViewById(R.id.txt_name_message);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
			holder.txtNameMessage.setTypeface(myTypeFace.getRalewayRegular());
			holder.txtTime.setTypeface(myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			imageLoader.displayImage(Global.strProfilePic, holder.imgDp, ISMStudent.options);
			holder.txtNameMessage.setText(Html.fromHtml("<font color='#1BC4A2'>" + arrListMessage.get(position).getSenderName()
					+ "</font><font color='#323941'> " + arrListMessage.get(position).getMessageText() + "</font>"));
			holder.txtTime.setText(Utility.getTimeDuration(arrListMessage.get(position).getSentOn()));
		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		CircleImageView imgDp;
		TextView txtNameMessage, txtTime;
	}

}