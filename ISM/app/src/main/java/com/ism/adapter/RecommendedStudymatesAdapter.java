package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.interfaces.ConfirmationListener;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.User;

import java.util.ArrayList;

/**
 * Created by c161 on 28/11/15.
 */
public class RecommendedStudymatesAdapter extends RecyclerView.Adapter<RecommendedStudymatesAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse, ConfirmationListener {

	private static final String TAG = RecommendedStudymatesAdapter.class.getSimpleName();

	private Context context;
	private MyTypeFace myTypeFace;
	private ArrayList<User> arrayListUser;
	private int currentPosition;

	public RecommendedStudymatesAdapter(Context context, ArrayList<User> arrListUser) {
		this.context = context;
		this.arrayListUser = arrListUser;
		myTypeFace = new MyTypeFace(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		CircleImageView imgDp;

		TextView txtUserName, txtFollowing, txtSchool, txtCourse;

		Button btnAddStudymate;
		public ViewHolder(View view) {
			super(view);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
			txtFollowing = (TextView) view.findViewById(R.id.txt_following);
			txtSchool = (TextView) view.findViewById(R.id.txt_school);
			txtCourse = (TextView) view.findViewById(R.id.txt_course);
			btnAddStudymate = (Button) view.findViewById(R.id.btn_add_studymate);

			txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
			txtFollowing.setTypeface(myTypeFace.getRalewayRegular());
			txtSchool.setTypeface(myTypeFace.getRalewayRegular());
			txtCourse.setTypeface(myTypeFace.getRalewayRegular());
			btnAddStudymate.setTypeface(myTypeFace.getRalewayRegular());
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommended_studymate_request, parent, false));
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrayListUser.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
		holder.txtUserName.setText(arrayListUser.get(position).getFullName());
		holder.txtFollowing.setText("Following 34 Authors");
		holder.txtSchool.setText(arrayListUser.get(position).getSchoolName());
		holder.txtCourse.setText(arrayListUser.get(position).getCourseName());

		holder.btnAddStudymate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Utility.askConfirmation(context, RecommendedStudymatesAdapter.this, 0,
						context.getString(R.string.add_studymate),
						context.getString(R.string.msg_add_studymate) + arrayListUser.get(position).getFullName() + context.getString(R.string.as_studymate),
						false);
				currentPosition = position;
			}
		});
	}

	private void callApiAddStudymate(String studymateId) {
		try {
			Attribute attribute = new Attribute();
			attribute.setMateOf(Global.strUserId);
			attribute.setMateId(studymateId);

			new WebserviceWrapper(context, attribute, this).new WebserviceCaller()
					.execute(WebConstants.SEND_REQUEST_STUDYMATE);
		} catch (Exception e) {
			Log.e(TAG, "callApiAddStudymate Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrayListUser.size();
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.SEND_REQUEST_STUDYMATE:
					onResponseAddStudymate(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseAddStudymate(Object object, Exception error) {
		try {
			((HostActivity) context).hideProgress();
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					Log.e(TAG, "onResponseAddStudymate : Studymate request sent successfully");
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseAddStudymate failed message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseAddStudymate api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAddStudymate Exception : " + e.toString());
		}
	}

	@Override
	public void onConfirmationResponse(int requestId, boolean confirmed) {
		if (confirmed) {
			((HostActivity) context).showProgress();
			callApiAddStudymate(arrayListUser.get(currentPosition).getUserId());
		}
	}

}
