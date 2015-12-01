package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.User;

import java.util.ArrayList;

/**
 * Created by c161 on 30/11/15.
 */
public class FindStudymatesAdapter extends RecyclerView.Adapter<FindStudymatesAdapter.ViewHolder> {

	private static final String TAG = FindStudymatesAdapter.class.getSimpleName();

	private Context context;
	private MyTypeFace myTypeFace;
	private ArrayList<User> arrayListUser;

	public FindStudymatesAdapter(Context context, ArrayList<User> arrListUser) {
		this.context = context;
		this.arrayListUser = arrListUser;
		myTypeFace = new MyTypeFace(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		View viewStatus;
		CircleImageView imgDp;
		TextView txtUserName, txtSchool, txtLocation, txtFollowing;
		Button btnAddStudymate;

		public ViewHolder(View view) {
			super(view);
			viewStatus = view.findViewById(R.id.view_status);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
			txtSchool = (TextView) view.findViewById(R.id.txt_school);
			txtLocation = (TextView) view.findViewById(R.id.txt_location);
			txtFollowing = (TextView) view.findViewById(R.id.txt_following);
			btnAddStudymate = (Button) view.findViewById(R.id.btn_add_studymate);

			txtUserName.setTypeface(myTypeFace.getRalewaySemiBold());
			txtSchool.setTypeface(myTypeFace.getRalewayRegular());
			txtLocation.setTypeface(myTypeFace.getRalewayRegular());
			txtFollowing.setTypeface(myTypeFace.getRalewayRegular());
			btnAddStudymate.setTypeface(myTypeFace.getRalewayRegular());
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_find_studymate_request, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (position % 2 == 0) {
			holder.viewStatus.setBackgroundResource(R.drawable.bg_online);
			holder.btnAddStudymate.setBackgroundResource(R.drawable.bg_button_dark_small);
			holder.btnAddStudymate.setText(R.string.respond_to_request);
		} else {
			holder.viewStatus.setBackgroundResource(R.drawable.bg_offline);
			holder.btnAddStudymate.setBackgroundResource(R.drawable.bg_button_green_small);
			holder.btnAddStudymate.setText(R.string.add_studymate);
		}
		holder.txtUserName.setText("Daniel Golman");
		holder.txtSchool.setText("Student from Lordes Convents");
		holder.txtLocation.setText("Live in Ghana");
		holder.txtFollowing.setText("Following 34 Authors");
	}

	@Override
	public int getItemCount() {
		return arrayListUser.size();
	}

}
