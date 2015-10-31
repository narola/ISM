package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.helper.CircleImageView;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c161 on 30/10/15.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> {

	private static final String TAG = PostFeedsAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<Data> arrListFeeds;

	public PostFeedsAdapter(Context context, ArrayList<Data> arrListFeeds) {
		this.context = context;
		this.arrListFeeds = arrListFeeds;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public CircleImageView imgDp;
		public TextView txtName;
		public TextView txtPost;

		public ViewHolder(View view) {
			super(view);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			txtName = (TextView) view.findViewById(R.id.txt_name);
			txtPost = (TextView) view.findViewById(R.id.txt_post);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		try {
			holder.txtName.setText(arrListFeeds.get(position).getFullName());
			holder.txtPost.setText(arrListFeeds.get(position).getFeedText());
		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListFeeds.size();
	}

}