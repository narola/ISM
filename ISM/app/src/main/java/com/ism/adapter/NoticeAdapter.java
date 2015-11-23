package com.ism.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.model.FragmentArgument;
import com.ism.object.MyTypeFace;
import com.ism.ws.model.Data;
import com.ism.ws.model.Notice;

import java.util.ArrayList;

/**
 * Created by c161 on 04/11/15.
 */
public class NoticeAdapter extends BaseAdapter {

    private static final String TAG = NoticeAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Notice> arrListNotes;
	private MyTypeFace myTypeFace;
	private HostActivity activityHost;

    public NoticeAdapter(Context context, ArrayList<Notice> arrListNotes, HostActivity activityHost) {
        this.context = context;
        this.arrListNotes = arrListNotes;
	    this.activityHost = activityHost;
        inflater = LayoutInflater.from(context);
	    myTypeFace = new MyTypeFace(context);
    }

    @Override
    public int getCount() {
        return arrListNotes.size() >= 2 ? 2 : arrListNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return arrListNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;
	    if (convertView == null) {
		    convertView = inflater.inflate(R.layout.row_notice, parent, false);

		    holder = new ViewHolder();
		    holder.txtNoticeTitle = (TextView) convertView.findViewById(R.id.txt_noticeName);
		    holder.txtNotice = (TextView) convertView.findViewById(R.id.txt_noticeDesc);
		    holder.txtReadmore = (TextView) convertView.findViewById(R.id.txt_noticeReadMore);

		    holder.txtNoticeTitle.setTypeface(myTypeFace.getRalewayBold());
		    holder.txtNotice.setTypeface(myTypeFace.getRalewayThin());
		    holder.txtReadmore.setTypeface(myTypeFace.getRalewayLight());

		    convertView.setTag(holder);
	    } else {
		    holder = (ViewHolder) convertView.getTag();
	    }

	    try {
		    holder.txtNoticeTitle.setText(arrListNotes.get(position).getNoticeTitle());
		    holder.txtNotice.setText(arrListNotes.get(position).getNotice());

		    holder.txtReadmore.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    activityHost.loadFragment(HostActivity.FRAGMENT_ALL_NOTES, new FragmentArgument(arrListNotes));
			    }
		    });

	    } catch (Exception e) {
		    Log.e(TAG, "getView Exception : " + e.toString());
	    }

	    return convertView;
    }

	class ViewHolder {
		TextView txtNoticeTitle, txtNotice, txtReadmore;
	}

}
