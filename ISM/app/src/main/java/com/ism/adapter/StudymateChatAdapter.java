package com.ism.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 07/11/15.
 */
public class StudymateChatAdapter extends BaseAdapter {

	private static final String TAG = StudymateChatAdapter.class.getSimpleName();

	private ArrayList<User> arrListStudymate;
	private Context context;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;

	public StudymateChatAdapter(Context context, ArrayList<User> arrListStudymate) {
		this.arrListStudymate = arrListStudymate;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
	}

	@Override
	public int getCount() {
		return arrListStudymate.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListStudymate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_studymate_chat, parent, false);
			holder = new ViewHolder();
			holder.imgDp = (CircleImageView) convertView.findViewById(R.id.img_dp);
			holder.txtUserName = (TextView) convertView.findViewById(R.id.txt_user_name);
			holder.txtSchool = (TextView) convertView.findViewById(R.id.txt_school);
			holder.viewStatus = convertView.findViewById(R.id.view_status);

			holder.txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
			holder.txtSchool.setTypeface(myTypeFace.getRalewayRegular());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		try {
			imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrListStudymate.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
			holder.txtUserName.setText(arrListStudymate.get(position).getFullName());
			holder.txtSchool.setText(arrListStudymate.get(position).getSchoolName());
			holder.viewStatus.setBackgroundResource(arrListStudymate.get(position).getIsOnline().equals("1") ? R.drawable.bg_online : R.drawable.bg_offline);

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});

		} catch (Exception e) {
			Log.e(TAG, "getView Exception : " + e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		CircleImageView imgDp;
		TextView txtUserName, txtSchool;
		View viewStatus;
	}

}