package com.ism.teacher.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;

/**
 * Created by c75 on 19/10/15.
 */
public class PostFeedAdapter extends BaseAdapter {

    Context context;

    public PostFeedAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder {
        public TextView txtUsernamePostCreator, txtPostContent, txtPostLikeCounter, txtPostCommentsCounter, txtViewAllComments;
        public EditText etWritePost;
        public ImageView imgDpPostCreator;
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
            convertView = inflater.inflate(R.layout.row_teacher_post, parent, false);

            objViewHolder = new ViewHolder();

            objViewHolder.imgDpPostCreator = (ImageView) convertView.findViewById(R.id.img_dp_post_creator);
            objViewHolder.txtUsernamePostCreator = (TextView) convertView.findViewById(R.id.txt_username_post_creator);
            objViewHolder.txtPostContent = (TextView) convertView.findViewById(R.id.txt_post_content);
            objViewHolder.txtPostLikeCounter = (TextView) convertView.findViewById(R.id.txt_post_like_counter);
            objViewHolder.txtPostCommentsCounter = (TextView) convertView.findViewById(R.id.txt_post_comments_counter);
            objViewHolder.txtViewAllComments = (TextView) convertView.findViewById(R.id.txt_view_all_comments);
            objViewHolder.etWritePost = (EditText) convertView.findViewById(R.id.et_writePost);

            convertView.setTag(objViewHolder);

        } else {
            objViewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }
}
