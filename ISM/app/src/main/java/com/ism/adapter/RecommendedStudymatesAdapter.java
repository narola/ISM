package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RecommendedStudymatesAdapter extends RecyclerView.Adapter<RecommendedStudymatesAdapter.ViewHolder> {

	private static final String TAG = RecommendedStudymatesAdapter.class.getSimpleName();

	private Context context;
	private MyTypeFace myTypeFace;
	private ArrayList<User> arrayListUser;

	public RecommendedStudymatesAdapter(Context context, ArrayList<User> arrListUser) {
		this.context = context;
		this.arrayListUser = arrListUser;
		myTypeFace = new MyTypeFace(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		CircleImageView imgDp;
		TextView txtUserName, txtFollowing, txtSchool, txtCourse;

		public ViewHolder(View view) {
			super(view);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
			txtFollowing = (TextView) view.findViewById(R.id.txt_following);
			txtSchool = (TextView) view.findViewById(R.id.txt_school);
			txtCourse = (TextView) view.findViewById(R.id.txt_course);

			txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
			txtFollowing.setTypeface(myTypeFace.getRalewayRegular());
			txtSchool.setTypeface(myTypeFace.getRalewayRegular());
			txtCourse.setTypeface(myTypeFace.getRalewayRegular());
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommended_studymate_request, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrayListUser.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
		holder.txtUserName.setText(arrayListUser.get(position).getFullName());
		holder.txtFollowing.setText("Following 34 Authors");
		holder.txtSchool.setText(arrayListUser.get(position).getSchoolName());
		holder.txtCourse.setText(arrayListUser.get(position).getCourseName());
	}

	@Override
	public int getItemCount() {
		return arrayListUser.size();
	}

}
