package com.ism.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ism.R;

import io.realm.RealmResults;
import model.FeedComment;

/**
 * Created by c166 on 24/10/15.
 */
public class ViewAllCommentsDialogTest extends Dialog implements View.OnClickListener {

    private Context mContext;
    private RecyclerView rvPostFeedsComments;
    private PostFeedCommentsAdapterTest postFeedCommentsAdapter;
    private TextView tvDialogClose;
    private RealmResults<FeedComment> commentsList;

    public ViewAllCommentsDialogTest(Context mContext, RealmResults<FeedComment> commentsList) {
        super(mContext);

        this.mContext = mContext;
        this.commentsList = commentsList;

        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_view_all_comments_student);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();

    }

    private void initializeDialog() {

        postFeedCommentsAdapter = new PostFeedCommentsAdapterTest();
//        postFeedCommentsAdapter = new PostFeedCommentsAdapter();
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
