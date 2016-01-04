package com.ism.teacher.adapters.settings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.BlockedUsers;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class BlockedUserAdapter extends BaseAdapter implements WebserviceWrapper.WebserviceResponse {
    private static final String TAG = BlockedUserAdapter.class.getSimpleName();
    private final ArrayList<BlockedUsers> arrayList;
    private final TeacherHostActivity.ResizeView resizeView;
    Context context;
    LayoutInflater inflater;
    private int blockUserPosition = -1;

    public BlockedUserAdapter(Context context, ArrayList<BlockedUsers> arrayList, TeacherHostActivity.ResizeView resizeView) {
        this.context = context;
        this.resizeView = resizeView;
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

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtEmailAddress.setTypeface(Global.myTypeFace.getRalewayRegular());
            viewHolder.txtName.setTypeface(Global.myTypeFace.getRalewayRegular());
            viewHolder.txtUnblock.setTypeface(Global.myTypeFace.getRalewayRegular());
            viewHolder.txtEmailAddress.setText(arrayList.get(position).getEmailId());
            viewHolder.txtName.setText(arrayList.get(position).getFullName());
            viewHolder.txtUnblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast("Unblock : " + position,context);
                    blockUserPosition = position;
                    callApiForUnBlockUser(arrayList.get(position).getEmailId());
                }
            });
        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }
        return convertView;
    }

    private void onResponseUnBlockUser(Object object, Exception error) {
        try {
            ((TeacherHostActivity) context).hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrayList.remove(blockUserPosition);
                    notifyDataSetChanged();
                    resizeView.onUnBlockUser();
                    Debug.i(TAG, "onResponseUnBlockUser success");
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Log.i(TAG, "onResponseUnBlockUser Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseUnBlockUser api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseUnBlockUser Exception : " + e.toString());
        }
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        switch (apiCode) {
            case WebConstants.UNBLOCK_USER:
                onResponseUnBlockUser(object, error);
                break;

        }
    }

    class ViewHolder {
        TextView txtName, txtEmailAddress, txtUnblock;
    }

    private void callApiForUnBlockUser(String email) {
        try {
            if (Utility.isConnected(context)) {
                ((TeacherHostActivity) context).showProgress();
                Attribute attribute = new Attribute();
                attribute.setEmailId(email);
                attribute.setUserId(Global.strUserId);
                attribute.setBlockUser("0");
                new WebserviceWrapper(context, attribute, this).new WebserviceCaller().execute(WebConstants.UNBLOCK_USER);
            } else {
                Utility.alertOffline(context);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callApiForBlockUser Exception : " + e.getLocalizedMessage());
        }
    }
}
