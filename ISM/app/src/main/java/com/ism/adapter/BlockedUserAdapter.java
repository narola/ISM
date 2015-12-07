package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.BlockedUsers;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class BlockedUserAdapter extends BaseAdapter {
    private static final String TAG = BlockedUserAdapter.class.getSimpleName();
    private final ArrayList<BlockedUsers> arrayList;
    Context context;
    LayoutInflater inflater;

    public BlockedUserAdapter(Context context, ArrayList<BlockedUsers> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        try {
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.row_bloked_user, parent, false);
                viewHolder.txtEmailAddress = (TextView) convertView.findViewById(R.id.txt_email_address);
                viewHolder.txtUnblock = (TextView) convertView.findViewById(R.id.txt_unblock);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);

                viewHolder.txtEmailAddress.setTypeface(Global.myTypeFace.getRalewayRegular());
                viewHolder.txtName.setTypeface(Global.myTypeFace.getRalewayRegular());
                viewHolder.txtUnblock.setTypeface(Global.myTypeFace.getRalewayRegular());
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.txtEmailAddress.setText(arrayList.get(position).getEmailId());
            viewHolder.txtName.setText(arrayList.get(position).getFullName());
            viewHolder.txtUnblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast(context, "Unblock : " + position);
                }
            });
        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }
        return convertView;
    }

    class ViewHolder {
        TextView txtName, txtEmailAddress, txtUnblock;
    }
}
