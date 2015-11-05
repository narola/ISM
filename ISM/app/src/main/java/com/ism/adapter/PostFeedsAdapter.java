package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.dialog.TagStudyMatesDialog;
import com.ism.dialog.ViewAllCommentsDialog;
import com.ism.views.CircleImageView;
import com.ism.utility.PreferenceData;
import com.ism.utility.Utility;
import com.ism.ws.RequestObject;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;
import com.ism.ws.model.Comment;
import com.ism.ws.model.Data;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 30/10/15.
 */
public class PostFeedsAdapter extends RecyclerView.Adapter<PostFeedsAdapter.ViewHolder>
		implements WebserviceWrapper.WebserviceResponse, TagStudyMatesDialog.TagStudyMatesListener {

	private static final String TAG = PostFeedsAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<Data> arrListFeeds;
	private ImageLoader imageLoader;

	private int addCommentFeedPosition = -1;
	private int tagFeedPosition = -1;
	private String strUserId;

	public PostFeedsAdapter(Context context, ArrayList<Data> arrListFeeds) {
		this.context = context;
		this.arrListFeeds = arrListFeeds;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		strUserId = PreferenceData.getStringPrefs(PreferenceData.USER_ID, context);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public CircleImageView imgDp;

		public ImageView imgLike, imgTagStudyMates;

		public TextView txtName, txtPost, txtLikes, txtComments, txtViewAll;
		public EditText etComment;
		public Button btnComment;
		public LinearLayout llComments;
		public ViewHolder(View view) {
			super(view);
			imgDp = (CircleImageView) view.findViewById(R.id.img_dp);
			imgLike = (ImageView) view.findViewById(R.id.img_like);
			imgTagStudyMates = (ImageView) view.findViewById(R.id.img_tag_study_mates);
			txtName = (TextView) view.findViewById(R.id.txt_name);
			txtPost = (TextView) view.findViewById(R.id.txt_post);
			txtLikes = (TextView) view.findViewById(R.id.txt_likes);
			txtComments = (TextView) view.findViewById(R.id.txt_comments);
			txtViewAll = (TextView) view.findViewById(R.id.txt_view_all);
			etComment = (EditText) view.findViewById(R.id.et_comment);
			btnComment = (Button) view.findViewById(R.id.btn_comment);
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

			if (addCommentFeedPosition != -1 && addCommentFeedPosition == position) {
				holder.etComment.setText("");
				addCommentFeedPosition = -1;
			}

			holder.llComments.removeAllViews();

			if (arrListFeeds.get(position).getComments() != null) {
				holder.txtViewAll.setVisibility(Integer.parseInt(arrListFeeds.get(position).getTotalComment()) > 2 ? View.VISIBLE : View.GONE);
				for (int i = 0; i < arrListFeeds.get(position).getComments().size(); i++) {
					holder.llComments.addView(getCommentView(arrListFeeds.get(position).getComments().get(i)));
				}
			}

			holder.imgLike.setActivated(arrListFeeds.get(position).getLike().equals("1"));

			holder.imgLike.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (arrListFeeds.get(position).getLike().equals("1")) {
						arrListFeeds.get(position).setLike("0");
						arrListFeeds.get(position).setTotalLike("" + (Integer.parseInt(arrListFeeds.get(position).getTotalLike()) - 1));
					} else {
						arrListFeeds.get(position).setLike("1");
						arrListFeeds.get(position).setTotalLike("" + (Integer.parseInt(arrListFeeds.get(position).getTotalLike()) + 1));
					}
					holder.imgLike.setActivated(arrListFeeds.get(position).getLike().equals("1"));
					notifyDataSetChanged();
				}
			});

			holder.txtViewAll.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					callApiGetAllComments(position);
				}
			});

			holder.imgTagStudyMates.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Utility.isOnline(context)) {
						tagFeedPosition = position;
						callApiGetStudyMates();
					} else {
						Utility.toastOffline(context);
					}
				}
			});

			holder.btnComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String comment = holder.etComment.getText().toString().trim();
					if (comment != null && comment.length() > 0) {
						callApiComment(position, comment);
					}
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

	private void callApiComment(int position, String comment) {
		try {
			addCommentFeedPosition = position;
			RequestObject requestObject = new RequestObject();
			requestObject.setFeedId(arrListFeeds.get(position).getFeedId());
			requestObject.setCommentBy(strUserId);
			requestObject.setComment(comment);

			new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
					.execute(WebserviceWrapper.ADD_COMMENT);
		} catch (Exception e) {
			Log.e(TAG, "callApiComment Exception : " + e.toString());
		}
	}

	private void callApiGetAllComments(int position) {
		try {
			addCommentFeedPosition = position;
			RequestObject requestObject = new RequestObject();
			requestObject.setFeedId(arrListFeeds.get(position).getFeedId());

			new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_ALL_COMMENTS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetAllComments Exception : " + e.toString());
		}
	}

	public void callApiGetStudyMates() {
		try {
			RequestObject requestObject = new RequestObject();
			requestObject.setUserId(strUserId);

			new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
					.execute(WebserviceWrapper.GET_ALL_STUDY_MATES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetStudyMates Exception : " + e.toString());
		}
	}

	@Override
	public void tagStudyMates(String[] arrTagUser) {
		if (Utility.isOnline(context)) {
			try {
				RequestObject requestObject = new RequestObject();
				requestObject.setFeedId(arrListFeeds.get(tagFeedPosition).getFeedId());
				requestObject.setTaggedBy(strUserId);
				requestObject.setTaggedUserIds(arrTagUser);

				new WebserviceWrapper(context, requestObject, this).new WebserviceCaller()
						.execute(WebserviceWrapper.TAG_STUDY_MATES);
			} catch (Exception e) {
				Log.e(TAG, "callApiGetStudyMates Exception : " + e.toString());
			}
		} else {
			Utility.toastOffline(context);
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			if (object != null) {
				switch (apiCode) {
					case WebserviceWrapper.ADD_COMMENT:
						onResponseAddComment(object);
						break;
					case WebserviceWrapper.GET_ALL_COMMENTS:
						onResponseGetAllComments(object);
						break;
					case WebserviceWrapper.GET_ALL_STUDY_MATES:
						onResponseGetAllStudyMates(object);
						break;
					case WebserviceWrapper.TAG_STUDY_MATES:
						onResponseTagStudyMates(object);
						break;
				}
			} else {
				Log.e(TAG, "onResponse ApiCall Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseTagStudyMates(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			tagFeedPosition = -1;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				Toast.makeText(context, "Tag done!", Toast.LENGTH_SHORT).show();
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(context, "Tag failed!", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseTagStudyMates Exception : " + e.toString());
		}
	}

	private void onResponseGetAllStudyMates(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
					if (responseObj.getData().size() > 0) {
						TagStudyMatesDialog tagStudyMatesDialog = new TagStudyMatesDialog(context, responseObj.getData(), this);
						tagStudyMatesDialog.show();
					}
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(context, responseObj.getMessage(), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAllStudyMates Exception : " + e.toString());
		}
	}

	private void onResponseGetAllComments(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				ViewAllCommentsDialog viewAllCommentsDialog = new ViewAllCommentsDialog(context, responseObj.getData());
				viewAllCommentsDialog.show();
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(context, responseObj.getMessage(), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAllComments Exception : " + e.toString());
		}
	}

	private void onResponseAddComment(Object object) {
		try {
			ResponseObject responseObj = (ResponseObject) object;
			if (responseObj.getStatus().equals(ResponseObject.SUCCESS)) {
				arrListFeeds.get(addCommentFeedPosition).setTotalComment("" + (Integer.parseInt(arrListFeeds.get(addCommentFeedPosition).getTotalComment()) + 1));
				notifyDataSetChanged();
			} else if (responseObj.getStatus().equals(ResponseObject.FAILED)) {
				Toast.makeText(context, R.string.msg_failed_comment, Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseAddComment Exception : " + e.toString());
		}
	}

}