package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.User;

import java.util.ArrayList;

/**
 * Created by c161 on 28/11/15.
 */
public class YourStudymatesAdapter extends RecyclerView.Adapter<YourStudymatesAdapter.ViewHolder> {

	private static final String TAG = YourStudymatesAdapter.class.getSimpleName();

	private Context context;
	private MyTypeFace myTypeFace;
	private ArrayList<User> arrayListUser;

	public YourStudymatesAdapter(Context context, ArrayList<User> arrListUser) {
		this.context = context;
		this.arrayListUser = arrListUser;
		myTypeFace = new MyTypeFace(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		View viewStatus;
		CircleImageView imgDp;
		TextView txtUserName, txtSchool, txtLocation, txtFollowing;
		Button btnViewProfile;

		public ViewHolder(View view) {
			super(view);
			viewStatus = view.findViewById(R.id.view_status);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
			txtSchool = (TextView) view.findViewById(R.id.txt_school);
			txtLocation = (TextView) view.findViewById(R.id.txt_location);
			txtFollowing = (TextView) view.findViewById(R.id.txt_following);
			btnViewProfile = (Button) view.findViewById(R.id.btn_view_profile);

			txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
			txtSchool.setTypeface(myTypeFace.getRalewayRegular());
			txtLocation.setTypeface(myTypeFace.getRalewayRegular());
			txtFollowing.setTypeface(myTypeFace.getRalewayRegular());
			btnViewProfile.setTypeface(myTypeFace.getRalewayRegular());
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_your_studymates, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrayListUser.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
		holder.viewStatus.setBackgroundResource(arrayListUser.get(position).getIsOnline().equals("1") ? R.drawable.bg_online : R.drawable.bg_offline);
		holder.txtUserName.setText(arrayListUser.get(position).getFullName());
		holder.txtSchool.setText(arrayListUser.get(position).getSchoolName());
		holder.txtLocation.setText("Live in Ghana");
		holder.txtFollowing.setText("Following 34 Authors");
	}

	@Override
	public int getItemCount() {
		return arrayListUser.size();
	}

}
