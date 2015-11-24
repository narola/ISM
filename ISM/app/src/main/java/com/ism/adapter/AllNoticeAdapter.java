package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.ws.model.Notice;

import java.util.ArrayList;

/**
 * Created by c161 on 04/11/15.
 */
public class AllNoticeAdapter extends RecyclerView.Adapter<AllNoticeAdapter.ViewHolder> {

	private static final String TAG = AllNoticeAdapter.class.getSimpleName();

	private Context context;
	private ArrayList<Notice> arrListAllNotice;
	private static MyTypeFace myTypeFace;

	public AllNoticeAdapter(Context context, ArrayList<Notice> arrListAllNotice) {
		this.context = context;
		this.arrListAllNotice = arrListAllNotice;
		myTypeFace = new MyTypeFace(context);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView txtNoticeTitle, txtNotice, txtNoticeTime;
		public ImageView imgViewMore;

		public ViewHolder(View view) {
			super(view);
			txtNoticeTitle = (TextView) view.findViewById(R.id.txt_notice_name);
			txtNotice = (TextView) view.findViewById(R.id.txt_notice_content);
			txtNoticeTime = (TextView) view.findViewById(R.id.txt_notice_time);
			imgViewMore = (ImageView) view.findViewById(R.id.img_view_more);

			txtNoticeTitle.setTypeface(myTypeFace.getRalewayMedium());
			txtNotice.setTypeface(myTypeFace.getRalewayThin());
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
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		try {
			holder.txtNoticeTitle.setText(arrListAllNotice.get(position).getNoticeTitle());
			holder.txtNotice.setText(arrListAllNotice.get(position).getNotice());
			holder.txtNoticeTime.setText(arrListAllNotice.get(position).getPostedOn());

			if (arrListAllNotice.get(position).isExpanded()) {
				expandNotice(holder);
			} else {
				restoreNotice(holder);
			}

			holder.imgViewMore.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (arrListAllNotice.get(position).isExpanded()) {
						restoreNotice(holder);
						arrListAllNotice.get(position).setIsExpanded(false);
					} else {
						expandNotice(holder);
						arrListAllNotice.get(position).setIsExpanded(true);
					}
				}
			});
		} catch (Exception e) {
			Log.e(TAG, "onBindViewHolder Exception : " + e.toString());
		}
	}

	private void expandNotice(ViewHolder holder) {
		holder.txtNotice.setMaxLines(Integer.MAX_VALUE);
		holder.txtNotice.setEllipsize(null);
		holder.imgViewMore.setImageResource(R.drawable.ic_restore);
	}

	private void restoreNotice(ViewHolder holder) {
		holder.txtNotice.setMaxLines(3);
		holder.txtNotice.setEllipsize(TextUtils.TruncateAt.END);
		holder.imgViewMore.setImageResource(R.drawable.ic_expand);
	}

	@Override
	public int getItemCount() {
		return arrListAllNotice.size();
	}
}
