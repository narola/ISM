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
import com.ism.model.TestDiscussion;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 25/11/15.
 */
public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

	private static final String TAG = DiscussionAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<TestDiscussion> arrListDiscussion;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private LayoutInflater inflater;

	public DiscussionAdapter(Context context, ArrayList<TestDiscussion> arrListDiscussion) {
		this.context = context;
		this.arrListDiscussion = arrListDiscussion;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
		inflater = LayoutInflater.from(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public CircleImageView imgDp;
		public RelativeLayout rlHeader;
		public LinearLayout llChat;
		public TextView txtTimeHeader, txtTime, txtUserName, txtMessage;

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
			if (position == arrListDiscussion.size() - 1 || !arrListDiscussion.get(position).getTime().equals(arrListDiscussion.get(position + 1).getTime())) {
				holder.rlHeader.setVisibility(View.VISIBLE);
				holder.txtTime.setText(arrListDiscussion.get(position).getTime());
			} else {
				holder.rlHeader.setVisibility(View.GONE);
			}
//			holder.rlHeader.setPadding(0, position == 0 ? 0 : 15, 0, 0);

			imageLoader.displayImage(Global.strProfilePic, holder.imgDp, ISMStudent.options);

			if (position % 2 == 0) {
//				Received Chats
				holder.llChat.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
				holder.txtUserName.setGravity(Gravity.LEFT);
				holder.txtTime.setGravity(Gravity.RIGHT);
				holder.txtMessage.setBackgroundResource(R.drawable.bg_chat_received);
			} else {
//				Sent Chats
				holder.llChat.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
				holder.txtUserName.setGravity(Gravity.RIGHT);
				holder.txtTime.setGravity(Gravity.LEFT);
				holder.txtMessage.setBackgroundResource(R.drawable.bg_chat_sent);
			}

			holder.txtTime.setText(arrListDiscussion.get(position).getTime());
			holder.txtUserName.setText(arrListDiscussion.get(position).getUserName());
			holder.txtMessage.setText(arrListDiscussion.get(position).getMessage());
		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListDiscussion.size();
	}

}