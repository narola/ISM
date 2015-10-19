package com.ism.teacher.teachers_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;

/**
 * Created by c75 on 19/10/15.
 */
public class CommentsAdapter extends BaseAdapter {

    Context context;

    public CommentsAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder {
        public TextView txtUsernameCommenter, txtCommentsFromCommenter, txtCommentDuration;
        public ImageView imgDpCommenter;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder objViewHolder;
        if (convertView == null) {

            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.post_comments_list_item, parent, false);

            objViewHolder = new ViewHolder();

            objViewHolder.imgDpCommenter = (ImageView) convertView.findViewById(R.id.img_dp_commenter);
            objViewHolder.txtUsernameCommenter = (TextView) convertView.findViewById(R.id.txt_username_commenter);
            objViewHolder.txtCommentsFromCommenter = (TextView) convertView.findViewById(R.id.txt_comments_from_commenter);
            objViewHolder.txtCommentDuration = (TextView) convertView.findViewById(R.id.txt_comment_duration);

            convertView.setTag(objViewHolder);

        } else {
            objViewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }
}
