package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.model.TestActivity;
import com.ism.object.Global;
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

			if (position == 0 || !arrListActivity.get(position).getTime().equals(arrListActivity.get(position - 1).getTime())) {
				holder.llHeader.setVisibility(View.VISIBLE);
				holder.txtTime.setText(arrListActivity.get(position).getTime());
			} else {
				holder.llHeader.setVisibility(View.GONE);
			}

			holder.llHeader.setPadding(0, position == 0 ? 0 : 15, 0, 0);

			switch (arrListActivity.get(position).getActivityType()) {
				case 0:
					showFeedPost(holder, arrListActivity.get(position));
					break;
				case 1:
					showTopic(holder, arrListActivity.get(position));
					break;
				case 2:
					showStudymate(holder, arrListActivity.get(position));
					break;
				case 3:
					showFeedLike(holder, arrListActivity.get(position));
					break;
				case 4:
					showExam(holder, arrListActivity.get(position));
					break;
				case 5:
					showAssignment(holder, arrListActivity.get(position));
					break;
				case 6:
					showComment(holder, arrListActivity.get(position));
					break;
			}

		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	private void showComment(ViewHolder holder, TestActivity testActivity) {
		holder.txtActivityTitle.setText(R.string.commented_on);
		View viewComment = setMyActivityLayout(holder, R.layout.layout_timeline_comment);

		CircleImageView imgDp = (CircleImageView) viewComment.findViewById(R.id.img_dp);
		TextView txtName = (TextView) viewComment.findViewById(R.id.txt_user_name);
		TextView txtStatusTime = (TextView) viewComment.findViewById(R.id.txt_status_time);
		TextView txtStatusText = (TextView) viewComment.findViewById(R.id.txt_status_text);
		TextView txtSchool = (TextView) viewComment.findViewById(R.id.txt_school);
		TextView txtFollowing = (TextView) viewComment.findViewById(R.id.txt_following);

		txtName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtStatusTime.setTypeface(myTypeFace.getRalewayItalic());
		txtStatusText.setTypeface(myTypeFace.getRalewayRegular());
		txtSchool.setTypeface(myTypeFace.getRalewayRegular());
		txtFollowing.setTypeface(myTypeFace.getRalewayRegular());

		imageLoader.displayImage(Global.strProfilePic, imgDp, ISMStudent.options);
		txtName.setText("Albert Crowley");
		txtStatusText.setText("Status update Status update Status update Status update Status update Status update Status update Status update Status update Status update Status update Status update Status update Status update Status update ");
		txtStatusTime.setText("Sep 1, 2015");
		txtSchool.setText("St. Mary");
		txtFollowing.setText("Following 39 Authors");
	}

	private void showAssignment(ViewHolder holder, TestActivity testActivity) {
		holder.txtActivityTitle.setText(R.string.assignment_submitted);
		View viewAssignment = setMyActivityLayout(holder, R.layout.layout_timeline_assignment);
	}

	private void showExam(ViewHolder holder, TestActivity testActivity) {
		holder.txtActivityTitle.setText(R.string.exam_attempted);
		View viewExam = setMyActivityLayout(holder, R.layout.layout_timeline_table);
	}

	private void showFeedLike(ViewHolder holder, TestActivity testActivity) {
		holder.txtActivityTitle.setText(R.string.feed_liked);
		View viewFeedLike = setMyActivityLayout(holder, R.layout.layout_timeline_feed_like);
	}

	private void showStudymate(ViewHolder holder, TestActivity testActivity) {
		holder.txtActivityTitle.setText(R.string.became_studymate_with);
		View viewStudymate = setMyActivityLayout(holder, R.layout.layout_timeline_studymate);

		CircleImageView imgDp = (CircleImageView) viewStudymate.findViewById(R.id.img_dp);
		TextView txtName = (TextView) viewStudymate.findViewById(R.id.txt_user_name);
		TextView txtSchool = (TextView) viewStudymate.findViewById(R.id.txt_school);
		TextView txtFollowing = (TextView) viewStudymate.findViewById(R.id.txt_following);

		txtName.setTypeface(myTypeFace.getRalewaySemiBold());
		txtSchool.setTypeface(myTypeFace.getRalewayRegular());
		txtFollowing.setTypeface(myTypeFace.getRalewayRegular());

		imageLoader.displayImage(Global.strProfilePic, imgDp, ISMStudent.options);
		txtName.setText("Albert Crowley");
		txtSchool.setText("St. Mary");
		txtFollowing.setText("Following 39 Authors");
	}

	private void showTopic(ViewHolder holder, TestActivity testActivity) {
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

	private void showFeedPost(ViewHolder holder, TestActivity testActivity) {
		holder.txtActivityTitle.setText(R.string.feed_posted);
		View viewFeedPost = setMyActivityLayout(holder, R.layout.layout_timeline_feed_post);
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