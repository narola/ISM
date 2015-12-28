package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.object.MyTypeFace;
import com.ism.views.CircleImageView;
import com.ism.ws.model.UserActivitiy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c161 on 25/11/15.
 */
public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.ViewHolder> {

	private static final String TAG = UserActivityAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<UserActivitiy> arrListActivity;
	private ImageLoader imageLoader;
	private MyTypeFace myTypeFace;
	private LayoutInflater inflater;

	public UserActivityAdapter(Context context, ArrayList<UserActivitiy> arrListActivity) {
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
			txtActivityTitle.setTypeface(myTypeFace.getRalewaySemiBold());
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_activity, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		try {

			/*if (position == 0 || !arrListActivity.get(position).getTime().equals(arrListActivity.get(position - 1).getTime())) {
				holder.llHeader.setVisibility(View.VISIBLE);
				holder.txtTime.setText(arrListActivity.get(position).getTime());
			} else {
				holder.llHeader.setVisibility(View.GONE);
			}*/

			holder.llHeader.setPadding(0, position == 0 ? 0 : 15, 0, 0);

			if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_STUDYMATE)) {
				showStudymate(holder, arrListActivity.get(position));
			} else if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_FEED_POSTED)) {
				showFeedPost(holder, arrListActivity.get(position));
			} else if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_FEED_COMMENTED)) {
				showComment(holder, arrListActivity.get(position));
			} else if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_FEED_LIKED)) {
				showFeedLike(holder, arrListActivity.get(position));
			} else if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_TOPIC_ALLOCATED)) {
				showTopic(holder, arrListActivity.get(position));
			} else if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_ASSIGNMENT_SUBMITTED)) {
				showAssignment(holder, arrListActivity.get(position));
			} else if (arrListActivity.get(position).getActivityType().equals(UserActivitiy.ACTIVITY_EXAM_ATTEMPTED)) {
				showExam(holder, arrListActivity.get(position));
			}

		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	private void showComment(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.commented_on);
		View viewComment = setMyActivityLayout(holder, R.layout.layout_timeline_comment);

		CircleImageView imgDp = (CircleImageView) viewComment.findViewById(R.id.img_dp);
		TextView txtName = (TextView) viewComment.findViewById(R.id.txt_name);
		TextView txtPost = (TextView) viewComment.findViewById(R.id.txt_post);
		TextView txtTime = (TextView) viewComment.findViewById(R.id.txt_time);
		TextView txtLikes = (TextView) viewComment.findViewById(R.id.txt_likes);
		TextView txtComment = (TextView) viewComment.findViewById(R.id.txt_comment);
		ImageView imgLike = (ImageView) viewComment.findViewById(R.id.img_like);

		txtName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtPost.setTypeface(myTypeFace.getRalewayRegular());
		txtTime.setTypeface(myTypeFace.getRalewayItalic());
		txtLikes.setTypeface(myTypeFace.getRalewayItalic());
		txtComment.setTypeface(myTypeFace.getRalewayItalic());

		Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + userActivity.getFeedPosted().getFeedUserPic(), imgDp);
		txtName.setText(userActivity.getFeedPosted().getFeedUserName());
		txtPost.setText(userActivity.getFeedPosted().getFeedText());
		txtTime.setText(com.ism.utility.Utility.formatMySqlDateToMMMDDYYYY(userActivity.getActivityTime()));
		txtLikes.setText("4");
		txtComment.setText("comment text");
		imgLike.setActivated(true);
	}

	private void showAssignment(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.assignment_submitted);
		View viewAssignment = setMyActivityLayout(holder, R.layout.layout_timeline_assignment);

		TextView txtAssignmentName = (TextView) viewAssignment.findViewById(R.id.txt_exam_name);
		TextView txtSubjectName = (TextView) viewAssignment.findViewById(R.id.txt_subject_name);
		TextView txtSubmittedOn = (TextView) viewAssignment.findViewById(R.id.txt_submitted_on);

		txtAssignmentName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtSubjectName.setTypeface(myTypeFace.getRalewayRegular());
		txtSubmittedOn.setTypeface(myTypeFace.getRalewayItalic());

		txtAssignmentName.setText(userActivity.getFeedPosted().getFeedUserName());
		txtSubjectName.setText(userActivity.getFeedPosted().getFeedText());
		txtSubmittedOn.setText(com.ism.utility.Utility.formatMySqlDateToMMMDDYYYY(userActivity.getActivityTime()));
	}

	private void showExam(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.exam_attempted);
		View viewExam = setMyActivityLayout(holder, R.layout.layout_timeline_table);
	}

	private void showFeedLike(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.feed_liked);
		View viewFeedLike = setMyActivityLayout(holder, R.layout.layout_timeline_feed_post);

		CircleImageView imgDp = (CircleImageView) viewFeedLike.findViewById(R.id.img_dp);
		TextView txtName = (TextView) viewFeedLike.findViewById(R.id.txt_name);
		TextView txtPost = (TextView) viewFeedLike.findViewById(R.id.txt_post);
		TextView txtTime = (TextView) viewFeedLike.findViewById(R.id.txt_time);
		TextView txtLikes = (TextView) viewFeedLike.findViewById(R.id.txt_likes);
		ImageView imgLike = (ImageView) viewFeedLike.findViewById(R.id.img_like);

		txtName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtPost.setTypeface(myTypeFace.getRalewayRegular());
		txtTime.setTypeface(myTypeFace.getRalewayItalic());
		txtLikes.setTypeface(myTypeFace.getRalewayItalic());

		Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + userActivity.getFeedPosted().getFeedUserPic(), imgDp);
		txtName.setText(userActivity.getFeedPosted().getFeedUserName());
		txtPost.setText(userActivity.getFeedPosted().getFeedText());
		txtTime.setText(com.ism.utility.Utility.formatMySqlDateToMMMDDYYYY(userActivity.getActivityTime()));
		txtLikes.setText("4");
		imgLike.setActivated(true);
	}

	private void showStudymate(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.became_studymate_with);
		View viewStudymate = setMyActivityLayout(holder, R.layout.layout_timeline_studymate);

		CircleImageView imgDp = (CircleImageView) viewStudymate.findViewById(R.id.img_dp);
		TextView txtName = (TextView) viewStudymate.findViewById(R.id.txt_user_name);
		TextView txtSchool = (TextView) viewStudymate.findViewById(R.id.txt_school);
		TextView txtFollowing = (TextView) viewStudymate.findViewById(R.id.txt_following);

		txtName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtSchool.setTypeface(myTypeFace.getRalewayRegular());
		txtFollowing.setTypeface(myTypeFace.getRalewayRegular());

		imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + userActivity.getStudymates().getStudymateProfilePic(), imgDp, ISMStudent.options);
		txtName.setText(userActivity.getStudymates().getStudymateName());
		txtSchool.setText(userActivity.getStudymates().getStudymateSchoolName());
		txtFollowing.setText("Following 39 Authors");
	}

	private void showTopic(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.topics_allocated);
		View viewTopic = setMyActivityLayout(holder, R.layout.layout_timeline_table);

		TextView txtHeader = (TextView) viewTopic.findViewById(R.id.txt_header);
		TextView txtDiscusion = (TextView) viewTopic.findViewById(R.id.txt_title1);
		TextView txtComment = (TextView) viewTopic.findViewById(R.id.txt_value1);
		TextView txtExamination = (TextView) viewTopic.findViewById(R.id.txt_title2);
		TextView txtScore = (TextView) viewTopic.findViewById(R.id.txt_value2);

		txtHeader.setTypeface(myTypeFace.getRalewayRegular());
		txtDiscusion.setTypeface(myTypeFace.getRalewayRegular());
		txtComment.setTypeface(myTypeFace.getRalewayRegular());
		txtExamination.setTypeface(myTypeFace.getRalewayRegular());
		txtScore.setTypeface(myTypeFace.getRalewayRegular());
	}

	private void showFeedPost(ViewHolder holder, UserActivitiy userActivity) {
		holder.txtActivityTitle.setText(R.string.feed_posted);
		View viewFeedPost = setMyActivityLayout(holder, R.layout.layout_timeline_feed_post);

		CircleImageView imgDp = (CircleImageView) viewFeedPost.findViewById(R.id.img_dp);
		TextView txtName = (TextView) viewFeedPost.findViewById(R.id.txt_name);
		TextView txtPost = (TextView) viewFeedPost.findViewById(R.id.txt_post);
		TextView txtTime = (TextView) viewFeedPost.findViewById(R.id.txt_time);

		txtName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtPost.setTypeface(myTypeFace.getRalewayRegular());
		txtTime.setTypeface(myTypeFace.getRalewayItalic());

		Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + userActivity.getFeedPosted().getFeedUserPic(), imgDp);
		txtName.setText(userActivity.getFeedPosted().getFeedUserName());
		txtPost.setText(userActivity.getFeedPosted().getFeedText());
		txtTime.setText(com.ism.utility.Utility.formatMySqlDateToMMMDDYYYY(userActivity.getActivityTime()));
	}

	private View setMyActivityLayout(ViewHolder holder, int layoutResId) {
		holder.vsActivityContent.removeAllViews();
		View viewActivity = inflater.inflate(layoutResId, null);
		holder.vsActivityContent.addView(viewActivity);
		return viewActivity;
	}

	@Override
	public int getItemCount() {
		return arrListActivity.size();
	}

}