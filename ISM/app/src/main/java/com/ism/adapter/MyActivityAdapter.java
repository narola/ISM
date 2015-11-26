package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
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
	private ArrayList<String> arrListActivity;
	private ImageLoader imageLoader;

	public MyActivityAdapter(Context context, ArrayList<String> arrListActivity) {
		this.context = context;
		this.arrListActivity = arrListActivity;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public CircleImageView imgDp;
		public TextView txtName;

		public ViewHolder(View view) {
			super(view);
//			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtName = (TextView) view.findViewById(R.id.txt_time);
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
//			imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgDp, ISMStudent.options);
			holder.txtName.setText(arrListActivity.get(position));
		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListActivity.size();
	}

}