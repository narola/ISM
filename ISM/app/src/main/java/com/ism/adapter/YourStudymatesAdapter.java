package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
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
public class YourStudymatesAdapter extends RecyclerView.Adapter<YourStudymatesAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse, ConfirmationListener {

	private static final String TAG = YourStudymatesAdapter.class.getSimpleName();

	private Context context;
	private MyTypeFace myTypeFace;
	private ArrayList<User> arrayListUser;
	private int currentOptionPosition;

	private static final int REQUEST_BLOCK_STUDYMATE = 0;
	private static final int REQUEST_REMOVE_STUDYMATE = 1;

	public YourStudymatesAdapter(Context context, ArrayList<User> arrListUser) {
		this.context = context;
		this.arrayListUser = arrListUser;
		myTypeFace = new MyTypeFace(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		View viewStatus;

		CircleImageView imgDp;

		TextView txtUserName, txtSchool, txtFollowing;
		Button btnViewProfile;
		Spinner spinnerOptions;

		public ViewHolder(View view) {
			super(view);
			viewStatus = view.findViewById(R.id.view_status);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
			txtSchool = (TextView) view.findViewById(R.id.txt_school);
			txtFollowing = (TextView) view.findViewById(R.id.txt_following);
			btnViewProfile = (Button) view.findViewById(R.id.btn_view_profile);
			spinnerOptions = (Spinner) view.findViewById(R.id.spinner_studymate_options);

			txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
			txtSchool.setTypeface(myTypeFace.getRalewayRegular());
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
		holder.txtFollowing.setText(R.string.following + " " + arrayListUser.get(position).getTotalAuthorsFollowed() + " " + context.getString(R.string.authors));

		holder.spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (Utility.isConnected(context)) {
					switch (position) {
						case 1:
							Utility.askConfirmation(context, YourStudymatesAdapter.this, REQUEST_BLOCK_STUDYMATE,
									context.getString(R.string.block_studymate),
									context.getString(R.string.msg_block_studymate) + arrayListUser.get(position).getFullName(),
									false);
							currentOptionPosition = position;
							break;
						case 2:
							Utility.askConfirmation(context, YourStudymatesAdapter.this, REQUEST_REMOVE_STUDYMATE,
									context.getString(R.string.remove_studymate),
									context.getString(R.string.msg_remove_studymate) + arrayListUser.get(position).getFullName(),
									false);
							currentOptionPosition = position;
							callApiRemoveStudymate();
							break;
					}
				} else {
					Utility.alertOffline(context);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				Log.e(TAG, "onNothingSelected");
			}
		});

	}

	private void callApiBlockStudymate(String blockUserId) {
		try {
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);
			attribute.setBlockUser(blockUserId);

			new WebserviceWrapper(context, attribute, this).new WebserviceCaller()
					.execute(WebConstants.BLOCK_USER);
		} catch (Exception e) {
			Log.e(TAG, "callApiBlockStudymate Exception : " + e.toString());
		}
	}

	private void callApiRemoveStudymate() {

	}

	@Override
	public int getItemCount() {
		return arrayListUser.size();
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.BLOCK_USER:
					onResponseBlockUser(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseBlockUser(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					Log.e(TAG, "onResponseBlockUser : User blocked successfully");
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseBlockUser failed message : " + responseHandler.getMessage());
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseBlockUser api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseBlockUser Exception : " + e.toString());
		}
	}

	@Override
	public void onConfirmationResponse(int requestId, boolean confirmed) {
		if (confirmed) {
			switch (requestId) {
				case REQUEST_BLOCK_STUDYMATE:
					callApiBlockStudymate(arrayListUser.get(currentOptionPosition).getUserId());
					break;
				case REQUEST_REMOVE_STUDYMATE:
					break;
			}
		}
	}

}