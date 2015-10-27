package com.ism.teacher.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ism.R;
import com.ism.teacher.adapters.TagStudyMatesAdapter;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 24/10/15.
 */
public class TagCustomDialog extends Dialog implements View.OnClickListener {


    Context mContext;
    RecyclerView rvStudymatesTeacher;
    TagStudyMatesAdapter tagStudyMatesAdapter;
    TextView tvDialogClose;
    ArrayList<Data> studymatesList;


    public TagCustomDialog(Context mContext, ArrayList<Data> studymatesList) {
        super(mContext);

        this.mContext = mContext;
        this.studymatesList = studymatesList;

        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        w.setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_search_studymates_teacher);

        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();

    }

    private void initializeDialog() {

        //postFeedCommentsAdapter = new PostFeedCommentsAdapter();
        tagStudyMatesAdapter = new TagStudyMatesAdapter();
        rvStudymatesTeacher = (RecyclerView) findViewById(R.id.rv_studymates_teacher);

        tvDialogClose = (TextView) findViewById(R.id.tv_dialog_close);
        tvDialogClose.setOnClickListener(this);
        // Attach the adapter to the recyclerview to populate items

        // rvPostFeedsComments.setAdapter(postFeedCommentsAdapter);
        // Set layout manager to position the items
        rvStudymatesTeacher.setLayoutManager(new LinearLayoutManager(mContext));
        tagStudyMatesAdapter.addAll(studymatesList);
        rvStudymatesTeacher.setAdapter(tagStudyMatesAdapter);

    }

    @Override
    public void onClick(View v) {

        if (v == tvDialogClose) {
            dismiss();
        }


    }
}
