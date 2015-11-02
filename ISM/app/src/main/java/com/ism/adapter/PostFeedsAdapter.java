package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.helper.CircleImageView;
import com.ism.login.AppConstant;
import com.ism.ws.model.Comment;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 30/10/15.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder> {

	private static final String TAG = PostFeedsAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<Data> arrListFeeds;
	private ImageLoader imageLoader;

	public PostFeedsAdapter(Context context, ArrayList<Data> arrListFeeds) {
		this.context = context;
		this.arrListFeeds = arrListFeeds;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public CircleImageView imgDp;
		public ImageView imgLike;
		public TextView txtName, txtPost, txtLikes, txtComments, txtViewAll;
		public LinearLayout llComments;

		public ViewHolder(View view) {
			super(view);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			imgLike = (ImageView) view.findViewById(R.id.img_like);
			txtName = (TextView) view.findViewById(R.id.txt_name);
			txtPost = (TextView) view.findViewById(R.id.txt_post);
			txtLikes = (TextView) view.findViewById(R.id.txt_likes);
			txtComments = (TextView) view.findViewById(R.id.txt_comments);
			txtViewAll = (TextView) view.findViewById(R.id.txt_view_all);
			llComments = (LinearLayout) view.findViewById(R.id.ll_comments);
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		try {
//			imageLoader.displayImage(AppConstant.URL_IMAGE_PATH + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
			imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png", holder.imgDp, ISMStudent.options);
			holder.txtName.setText(arrListFeeds.get(position).getFullName());
			holder.txtPost.setText(arrListFeeds.get(position).getFeedText());
			holder.txtLikes.setText(arrListFeeds.get(position).getTotalLike());
			holder.txtComments.setText(arrListFeeds.get(position).getTotalComment());

			holder.llComments.removeAllViews();

			if (arrListFeeds.get(position).getComments() != null) {
				holder.txtViewAll.setVisibility(arrListFeeds.get(position).getComments().size() > 2 ? View.VISIBLE : View.GONE);
				for (int i = 0; i < arrListFeeds.get(position).getComments().size(); i++) {
					if (i > 1) {
						break;
					}
					holder.llComments.addView(getCommentView(arrListFeeds.get(position).getComments().get(i)));
				}
			}

			holder.imgLike.setActivated(arrListFeeds.get(position).getLike().equals("1"));

			holder.imgLike.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					arrListFeeds.get(position).setLike(arrListFeeds.get(position).getLike().equals("1") ? "0" : "1");
					holder.imgLike.setActivated(arrListFeeds.get(position).getLike().equals("1"));
				}
			});

			holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "View all comments", Toast.LENGTH_SHORT).show();
				}
			});

		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListFeeds.size();
	}

	private View getCommentView(Comment comment) {
		View view = null;
		try {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			view = layoutInflater.inflate(R.layout.item_comment_post, null);

		/*imageLoader.displayImage(AppConstant.URL_IMAGE_PATH + comment.getProfileLink(),
				(CircleImageView) view.findViewById(R.id.img_dp), ISMStudent.options);*/
			imageLoader.displayImage("http://192.168.1.162/ISM/WS_ISM/Images/Users_Images/user_434/image_1446011981010_test.png",
					(CircleImageView) view.findViewById(R.id.img_dp), ISMStudent.options);

			((TextView) view.findViewById(R.id.txt_name)).setText(comment.getFullName());
			((TextView) view.findViewById(R.id.txt_comment)).setText(comment.getComment());
			((TextView) view.findViewById(R.id.txt_duration)).setText("5 min");
		} catch (Exception e) {
			Log.e(TAG, "getCommentView Exception : " + e.toString());
		}
		return view;
	}

}