package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.fragment.OfficeFragment;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.OfficeTabDataSet;


/**
 * these is the grid adapter for the office tab.
 */
public class OfficeTabGridAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    OfficeTabDataSet officeTabDataSet;
    Fragment fragment;
    private MyTypeFace myTypeFace;

    // Constructor
    public OfficeTabGridAdapter(Context context, OfficeTabDataSet officeTabDataSet, Fragment fragment) {
        mContext = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.officeTabDataSet = officeTabDataSet;
        this.fragment = fragment;

        myTypeFace = new MyTypeFace(mContext);
    }


    @Override
    public int getCount() {
        return officeTabDataSet.getOfficeTabInfoList().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_grid_officetab, null);
            holder = new ViewHolder();

            holder.tvOfficetabTitle = (TextView) convertView.findViewById(R.id.tv_officetab_title);
            holder.tvOfficetabInfo = (TextView) convertView.findViewById(R.id.tv_officetab_info);
            holder.llOfficeTab = (LinearLayout) convertView.findViewById(R.id.ll_officetab);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.tvOfficetabTitle.setText(officeTabDataSet.getOfficeTabTitleList()[position]);
            holder.tvOfficetabTitle.setBackgroundResource(officeTabDataSet.getOfficeTabTitleImages()[position]);

            holder.tvOfficetabInfo.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvOfficetabInfo.setText(officeTabDataSet.getOfficeTabInfoList()[position]);
            holder.tvOfficetabInfo.setCompoundDrawablesWithIntrinsicBounds(officeTabDataSet.getOfficeTabInfoImages()[position], 0, 0, 0);

            holder.llOfficeTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 2)
                        ((OfficeFragment) fragment).loadFragment(OfficeFragment.FRAGMENT_TRIAL);

                    if (position == 4)
                        ((OfficeFragment) fragment).loadFragment(OfficeFragment.FRAGMENT_ASSESSMENT);


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    public class ViewHolder {

        TextView tvOfficetabTitle, tvOfficetabInfo;
        LinearLayout llOfficeTab;

    }


}
