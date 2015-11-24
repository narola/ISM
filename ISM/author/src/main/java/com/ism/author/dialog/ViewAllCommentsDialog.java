package com.ism.author.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.adapter.PostFeedCommentsAdapter;
import com.ism.author.ws.model.CommentList;

import java.util.ArrayList;

/**
 * Created by c166 on 24/10/15.
 */
public class ViewAllCommentsDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private RecyclerView rvPostFeedsComments;
    private PostFeedCommentsAdapter postFeedCommentsAdapter;
    private TextView tvDialogClose;
    private ArrayList<CommentList> commentList;


    public ViewAllCommentsDialog(Context mContext, ArrayList<CommentList> commentList) {
        super(mContext);

        this.mContext = mContext;
        this.commentList = commentList;
        Window w = getWindow();
        getWindow().getAttributes().windowAnimations =
                R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        w.setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_view_all_comments);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();


    }

    private void initializeDialog() {
        postFeedCommentsAdapter = new PostFeedCommentsAdapter(mContext);
        rvPostFeedsComments = (RecyclerView) findViewById(R.id.rv_post_feeds_comments);
        tvDialogClose = (TextView) findViewById(R.id.tv_dialog_close);
        tvDialogClose.setOnClickListener(this);
        rvPostFeedsComments.setAdapter(postFeedCommentsAdapter);
        rvPostFeedsComments.setLayoutManager(new LinearLayoutManager(mContext));
        postFeedCommentsAdapter.addAll(commentList);
    }

    @Override
    public void onClick(View v) {

        if (v == tvDialogClose) {
            dismiss();
        }


    }
}
