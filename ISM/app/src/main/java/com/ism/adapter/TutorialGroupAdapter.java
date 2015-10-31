package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;

/**
 * Created by c162 on 08/10/15.
 */
public class TutorialGroupAdapter extends BaseAdapter {

    private static final String TAG = TutorialGroupAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater inflater;
	private MyTypeFace myTypeFace;

    public TutorialGroupAdapter(Context context) {
        this.context = context;
	    inflater = LayoutInflater.from(context);
	    myTypeFace = new MyTypeFace(context);;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_tutorial_group, parent, false);
            viewHolder.rawtg_txt_name = (TextView) convertView.findViewById(R.id.txt_studentName);
            viewHolder.rawtg_txt_address = (TextView) convertView.findViewById(R.id.txt_address);
            viewHolder.rawtg_txt_school = (TextView) convertView.findViewById(R.id.txt_school);
            viewHolder.rawtg_txt_status = (TextView) convertView.findViewById(R.id.txt_status);
            viewHolder.rawtg_img_dp = (ImageView) convertView.findViewById(R.id.img_dp_post_creator);

            viewHolder.rawtg_txt_name.setTypeface(myTypeFace.getRalewayBold());
            viewHolder.rawtg_txt_status.setTypeface(myTypeFace.getRalewaySemiBold());
            viewHolder.rawtg_txt_address.setTypeface(myTypeFace.getRalewayRegular());
            viewHolder.rawtg_txt_school.setTypeface(myTypeFace.getRalewayRegular());

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder {
        TextView rawtg_txt_name;
        TextView rawtg_txt_address;
        ImageView rawtg_img_dp;
        TextView rawtg_txt_school;
        TextView rawtg_txt_status;
    }
}
