package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.model.TestActivity;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 25/11/15.
 */
public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityAdapter.ViewHolder> {

	private static final String TAG = MyActivityAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<TestActivity> arrListActivity;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private LayoutInflater inflater;

	public MyActivityAdapter(Context context, ArrayList<TestActivity> arrListActivity) {
		this.context = context;
		this.arrListActivity = arrListActivity;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		myTypeFace = new MyTypeFace(context);
		inflater = LayoutInflater.from(context);
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public LinearLayout llHeader;
		public TextView txtTime, txtActivityTitle;
		public FrameLayout vsActivityContent;

		public ViewHolder(View view) {
			super(view);
			llHeader = (LinearLayout) view.findViewById(R.id.ll_header);
			txtTime = (TextView) view.findViewById(R.id.txt_time_header);
			txtActivityTitle = (TextView) view.findViewById(R.id.txt_activity_title);
			vsActivityContent = (FrameLayout) view.findViewById(R.id.vs_activity_content);

			txtTime.setTypeface(myTypeFace.getRalewayBold());
			txtActivityTitle.setTypeface(myTypeFace.getRalewayRegular());
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_activity, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		try {

			if (position == 0 || !arrListActivity.get(position).getTime().equals(arrListActivity.get(position - 1).getTime())) {
				holder.llHeader.setVisibility(View.VISIBLE);
				holder.txtTime.setText(arrListActivity.get(position).getTime());
			} else {
				holder.llHeader.setVisibility(View.GONE);
			}

			holder.llHeader.setPadding(0, position == 0 ? 0 : 15, 0, 0);

			holder.vsActivityContent.removeAllViews();
			holder.vsActivityContent.addView(inflater.inflate(position % 2 == 0 ? R.layout.layout_timeline_feed_post :
					R.layout.layout_timeline_table, null));

			int activityType = 1;
			switch (activityType) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
			}

			holder.txtActivityTitle.setText(arrListActivity.get(position).getActivityTitle());
		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListActivity.size();
	}

}