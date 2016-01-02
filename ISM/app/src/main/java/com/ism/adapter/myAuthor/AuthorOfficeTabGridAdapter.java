package com.ism.adapter.myAuthor;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.fragment.myAuthor.AuthorOfficeFragment;
import com.ism.model.AuthorOfficeTabDataSet;
import com.ism.object.Global;

/**
 * these is the grid adapter for the office tab.
 */
public class AuthorOfficeTabGridAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    AuthorOfficeTabDataSet officeTabDataSet;
    Fragment fragment;


    // Constructor
    public AuthorOfficeTabGridAdapter(Context context, AuthorOfficeTabDataSet officeTabDataSet, Fragment fragment) {
        mContext = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.officeTabDataSet = officeTabDataSet;
        this.fragment = fragment;

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
            convertView = inflater.inflate(R.layout.row_grid_authortab, null);
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

            holder.tvOfficetabInfo.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.tvOfficetabInfo.setText(officeTabDataSet.getOfficeTabInfoList()[position]);
            holder.tvOfficetabInfo.setCompoundDrawablesWithIntrinsicBounds(officeTabDataSet.getOfficeTabInfoImages()[position], 0, 0, 0);

            holder.llOfficeTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    switch (position) {
                        case 0:
                            ((AuthorOfficeFragment) fragment).loadFragment(AuthorOfficeFragment.FRAGMENT_MY_DESK);
                            break;
                        case 1:
                            ((AuthorOfficeFragment) fragment).loadFragment(AuthorOfficeFragment.FRAGMENT_GOTRENDING);
                            break;
                        case 2:
                            ((AuthorOfficeFragment) fragment).loadFragment(AuthorOfficeFragment.FRAGMENT_TRIAL);
                            break;
                        case 3:
                            ((AuthorOfficeFragment) fragment).loadFragment(AuthorOfficeFragment.FRAGMENT_MYTHIRTY);
                            break;
                        case 4:
                            ((AuthorOfficeFragment) fragment).loadFragment(AuthorOfficeFragment.FRAGMENT_ASSESSMENT);
                            break;


                    }
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
