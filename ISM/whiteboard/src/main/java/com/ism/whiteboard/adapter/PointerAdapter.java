package com.ism.whiteboard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ism.whiteboard.R;

/**
 * Created by c161 on 16/09/15.
 */
public class PointerAdapter extends BaseAdapter {

    private static final String TAG = PointerAdapter.class.getSimpleName();

    private Context mContext;
    private String[] mPointerList;
    private LayoutInflater mInflater;

    public PointerAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mPointerList = mContext.getResources().getStringArray(R.array.pointer_size_list);
    }

    @Override
    public int getCount() {
        return mPointerList.length;
    }

    @Override
    public Object getItem(int position) {
        return mPointerList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_spinner_pointer, parent, false);
            holder = new ViewHolder();
            holder.imagePointer = (ImageView) convertView.findViewById(R.id.image_pointer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            switch (position) {
                case 0:
                    holder.imagePointer.setImageResource(R.drawable.ic_pointer_size_one);
                    break;
                case 1:
                    holder.imagePointer.setImageResource(R.drawable.ic_pointer_size_two);
                    break;
                case 2:
                    holder.imagePointer.setImageResource(R.drawable.ic_pointer_size_three);
                    break;
                case 3:
                    holder.imagePointer.setImageResource(R.drawable.ic_pointer_size_four);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "getView Exception : " + e.toString());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imagePointer;
    }

}
