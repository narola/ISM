package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.model.AllNotice;
import com.ism.object.MyTypeFace;

import java.util.ArrayList;

/**
 * Created by c161 on 04/11/15.
 */
public class AllNoticeAdapter extends RecyclerView.Adapter<AllNoticeAdapter.ViewHolder> {

	private static final String TAG = AllNoticeAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<AllNotice> arrListAllNotice;
	private static MyTypeFace myTypeFace;

	public AllNoticeAdapter(Context context, ArrayList<AllNotice> arrListAllNotice) {
		this.context = context;
		this.arrListAllNotice = arrListAllNotice;
		myTypeFace = new MyTypeFace(context);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView txtNoticeName, txtNoticeContent, txtNoticeTime;
		public ImageView imgViewMore;

		public ViewHolder(View view) {
			super(view);
			txtNoticeName = (TextView) view.findViewById(R.id.txt_notice_name);
			txtNoticeContent = (TextView) view.findViewById(R.id.txt_notice_content);
			txtNoticeTime = (TextView) view.findViewById(R.id.txt_notice_time);
			imgViewMore = (ImageView) view.findViewById(R.id.img_view_more);

			txtNoticeName.setTypeface(myTypeFace.getRalewayMedium());
			txtNoticeContent.setTypeface(myTypeFace.getRalewayThin());
			txtNoticeTime.setTypeface(myTypeFace.getRalewayThin());
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_all_notice, parent, false);

		ViewHolder viewHolder = new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		try {
			holder.txtNoticeName.setText(arrListAllNotice.get(position).getStrNoticeName());
			holder.txtNoticeContent.setText(arrListAllNotice.get(position).getStrNoticeDesc());
			holder.txtNoticeTime.setText("11:00 am");

			holder.imgViewMore.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.txtNoticeContent.setMaxLines(Integer.MAX_VALUE);
					holder.txtNoticeContent.setEllipsize(null);
				}
			});
		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	@Override
	public int getItemCount() {
		return arrListAllNotice.size();
	}
}
