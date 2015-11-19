package com.ism.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c162 on 09/11/15.
 */
public class BlockedUserAdapter extends BaseAdapter {
    ArrayList<Data> arrayList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    Fragment fragment;
    ResponseObject responseObject;
    MyTypeFace myTypeFace;

    public BlockedUserAdapter(ResponseObject responseObject, Context context, Fragment fragment) {
        this.responseObject = responseObject;
        this.context = context;
        this.fragment = fragment;
        inflater=LayoutInflater.from(context);
        myTypeFace=new MyTypeFace(context);
    }

    @Override
    public int getCount() {
     //   return responseObject.getData().get(0).getBlockedList().size();
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return responseObject.getData().get(0).getBlockedList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null) {
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.row_bloked_user, parent, false);
            viewHolder.txtEmailAddress=(TextView)convertView.findViewById(R.id.txt_email_address);
            viewHolder.txtUnblock=(TextView)convertView.findViewById(R.id.txt_unblock);
            viewHolder.txtName=(TextView)convertView.findViewById(R.id.txt_name);

            viewHolder.txtEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
            viewHolder.txtName.setTypeface(myTypeFace.getRalewayRegular());
            viewHolder.txtUnblock.setTypeface(myTypeFace.getRalewayRegular());
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtUnblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showToast("Unblock :" +position,context);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView txtName,txtEmailAddress,txtUnblock;
    }
}
