package com.ism.teacher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.adapters.PostFeedCommentsAdapter;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 24/10/15.
 */
public class ViewAllCommentsDialog extends Dialog implements View.OnClickListener {


    Context mContext;
    RecyclerView rvPostFeedsComments;
    PostFeedCommentsAdapter postFeedCommentsAdapter;
    TextView tvDialogClose;
    ArrayList<Data> commentsList;


    public ViewAllCommentsDialog(Context mContext, ArrayList<Data> commentsList) {
        super(mContext);

        this.mContext = mContext;
        this.commentsList = commentsList;

        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        w.setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_view_all_comments);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();


    }

    private void initializeDialog() {

        postFeedCommentsAdapter = new PostFeedCommentsAdapter();
        rvPostFeedsComments = (RecyclerView) findViewById(R.id.rv_post_feeds_comments);
        tvDialogClose = (TextView) findViewById(R.id.tv_dialog_close);
        tvDialogClose.setOnClickListener(this);
        // Attach the adapter to the recyclerview to populate items
        rvPostFeedsComments.setAdapter(postFeedCommentsAdapter);
        // Set layout manager to position the items
        rvPostFeedsComments.setLayoutManager(new LinearLayoutManager(mContext));
        postFeedCommentsAdapter.addAll(commentsList);

    }

    @Override
    public void onClick(View v) {


        if (v == tvDialogClose) {
            dismiss();
        }


    }
}
