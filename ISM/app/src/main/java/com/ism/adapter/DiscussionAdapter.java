package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.ws.model.Discussion;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import model.ROTutorialGroupDiscussion;

/**
 * Created by c161 on 25/11/15.
 */
public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

	private static final String TAG = DiscussionAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<ROTutorialGroupDiscussion> arrListDiscussion;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private LayoutInflater inflater;
	private int userId,previousId = -1;
	private String previousWeekDay= "";

	public DiscussionAdapter(Context context, ArrayList<ROTutorialGroupDiscussion> arrListDiscussion) {
		this.context = context;
		this.arrListDiscussion = arrListDiscussion;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
		inflater = LayoutInflater.from(context);
		userId = Integer.parseInt(PreferenceData.getStringPrefs(PreferenceData.USER_ID, context));
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public CircleImageView imgDp;
		public RelativeLayout rlHeader;
		public LinearLayout llChat;
		public TextView txtTimeHeader, txtTime, txtUserName, txtMessage;
        private boolean isSetDetails;

		public boolean isSetDetails() {
			return isSetDetails;
		}

		public void setIsSetDetails(boolean isSetDetails) {
			this.isSetDetails = isSetDetails;
		}

		public ViewHolder(View view) {
			super(view);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			rlHeader = (RelativeLayout) view.findViewById(R.id.rl_header);
			llChat = (LinearLayout) view.findViewById(R.id.ll_chat);
			txtTimeHeader = (TextView) view.findViewById(R.id.txt_time_header);
			txtTime = (TextView) view.findViewById(R.id.txt_time);
			txtUserName = (TextView) view.findViewById(R.id.txt_user_name);
			txtMessage = (TextView) view.findViewById(R.id.txt_message);

			txtTimeHeader.setTypeface(myTypeFace.getRalewayRegular());
			txtTime.setTypeface(myTypeFace.getRalewayThinItalic());
			txtUserName.setTypeface(myTypeFace.getRalewayRegular());
			txtMessage.setTypeface(myTypeFace.getRalewayRegular());
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tutorial_group_discussion, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		try {


			if(position == 0){
				Log.e("hello","hemm");
			}
			if(!arrListDiscussion.get(position).isShowDetails()){

				holder.imgDp.setVisibility(View.INVISIBLE);
				holder.txtUserName.setVisibility(View.GONE);
				holder.txtTimeHeader.setVisibility(View.GONE);
			}
			else{
				holder.imgDp.setVisibility(View.VISIBLE);
				holder.txtUserName.setVisibility(View.VISIBLE);
				holder.txtTimeHeader.setVisibility(View.VISIBLE);
			}

         Log.e("currebnt topic day",arrListDiscussion.get(position).getTopic().getTopicDay());
			Log.e("next topic day",arrListDiscussion.get(position).getTopic().getTopicDay());
			if (position == arrListDiscussion.size() - 1 || !arrListDiscussion.get(position).getTopic().getTopicDay()
					.equals(arrListDiscussion.get(position + 1).getTopic().getTopicDay())) {
				holder.rlHeader.setVisibility(View.VISIBLE);
				holder.txtTimeHeader.setVisibility(View.VISIBLE);
				holder.txtTimeHeader.setText(arrListDiscussion.get(position).getTopic().getTopicDay());
			} else {
				holder.rlHeader.setVisibility(View.GONE);
			}

			imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrListDiscussion.get(position).getSender().getProfilePicture(), holder.imgDp, ISMStudent.options);


			if (userId !=arrListDiscussion.get(position).getSender().getUserId()) {
//				Received Chats
				holder.llChat.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
				holder.txtUserName.setGravity(Gravity.LEFT);
				holder.txtTime.setGravity(Gravity.RIGHT);
				holder.txtMessage.setBackgroundResource(R.drawable.bg_chat_received);
			} else {
//=======
////			if (position % 2 == 0) {
//			if (arrListDiscussion.get(position).getUserId().equals("555")) {
//>>>>>>> adddedd0f52cebbf2708517d26e06ea1e3c5b94b
//				Sent Chats
				holder.llChat.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
				holder.txtUserName.setGravity(Gravity.RIGHT);
				holder.txtTime.setGravity(Gravity.LEFT);
				holder.txtMessage.setBackgroundResource(R.drawable.bg_chat_sent);
//			} else {
//				Received Chats
//				holder.llChat.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
//				holder.txtUserName.setGravity(Gravity.LEFT);
//				holder.txtTime.setGravity(Gravity.RIGHT);
//				holder.txtMessage.setBackgroundResource(R.drawable.bg_chat_received);
			}


			if(arrListDiscussion.get(position).isShowDetails()) {

//				holder.txtTime.setText(Utility.formatPHPDateToMMMDDYY_HHMMA(arrListDiscussion.get(position).getCreatedDate()));
				holder.txtTime.setText(Utility.getDateTime(arrListDiscussion.get(position).getCreatedDate(),Utility.DATE_FORMAT_MMMDDYY_HHMMA));
				holder.txtUserName.setText(arrListDiscussion.get(position).getSender().getFullName());

			}

			holder.txtMessage.setText(arrListDiscussion.get(position).getMessage());

			//holder.txtTime.setText(Utility.formatMySqlDate(arrListDiscussion.get(position).getCommentTimestamp(), Utility.DATE_FORMAT_MMMDDYY_HHMMA));
			//holder.txtUserName.setText(arrListDiscussion.get(position).getFullName());
			//holder.txtMessage.setText(arrListDiscussion.get(position).getComment());

		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListDiscussion.size();
	}

}