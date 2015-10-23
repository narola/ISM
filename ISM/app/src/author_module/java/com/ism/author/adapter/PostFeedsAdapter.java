package com.ism.author.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.helper.CircleImageView;

/**
 * this is the adapter for the
 */
public class PostFeedsAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;

    public PostFeedsAdapter(Context c) {
        inflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = c;
    }


    @Override
    public int getCount() {
        return 0;
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

        ViewHolder holder;

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.row_post_feed, null);
            holder = new ViewHolder();
            holder.imgDpPostCreator = (CircleImageView) convertView
                    .findViewById(R.id.img_dp_post_creator);
            holder.txtPostCreaterName = (TextView) convertView.findViewById(R.id.txt_post_creater_name);
            holder.txtPostContent = (TextView) convertView.findViewById(R.id.txt_post_content);
            holder.txtPostTotalLikeCount = (TextView) convertView.findViewById(R.id.txt_post_total_like_count);
            holder.txtPostTotalCommentCount = (TextView) convertView.findViewById(R.id.txt_post_total_comment_count);
            holder.txtCommentViewAll = (TextView) convertView.findViewById(R.id.txt_comment_view_all);
            holder.imgPostLike = (ImageView) convertView.findViewById(R.id.img_post_like);
            holder.imgPostComment = (ImageView) convertView.findViewById(R.id.img_post_comment);
            holder.imgPostTag = (ImageView) convertView.findViewById(R.id.img_post_tag);
            holder.etWriteComment = (EditText) convertView.findViewById(R.id.et_writeComment);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        try {
            //set data here

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }


    public class ViewHolder {

        CircleImageView imgDpPostCreator;
        TextView txtPostCreaterName, txtPostContent, txtPostTotalLikeCount, txtPostTotalCommentCount, txtCommentViewAll;
        ImageView imgPostLike, imgPostComment, imgPostTag;
        EditText etWriteComment;

    }
}
