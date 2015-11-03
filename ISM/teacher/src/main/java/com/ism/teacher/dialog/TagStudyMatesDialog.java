package com.ism.teacher.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.TagStudyMatesAdapter;
import com.ism.teacher.constants.AppConstant;
import com.ism.teacher.fragments.TeacherHomeFragment;
import com.ism.teacher.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 24/10/15.
 */
public class TagStudyMatesDialog extends Dialog implements View.OnClickListener {


    Context mContext;
    RecyclerView rvStudymatesTeacher;
    TagStudyMatesAdapter tagStudyMatesAdapter;
    TextView tvDialogClose, tvDialogTag;
    ArrayList<Data> studymatesList;
    EditText etSearchStudymates;
    Fragment fragment;

    public TagStudyMatesDialog(Context mContext, ArrayList<Data> studymatesList, Fragment fragment) {
        super(mContext);

        this.mContext = mContext;
        this.studymatesList = studymatesList;
        this.fragment = fragment;

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


        rvStudymatesTeacher = (RecyclerView) findViewById(R.id.rv_studymates_teacher);
        etSearchStudymates = (EditText) findViewById(R.id.et_search_studymates);
        tvDialogClose = (TextView) findViewById(R.id.tv_dialog_close);
        tvDialogTag = (TextView) findViewById(R.id.tv_dialog_tag);
        tvDialogTag.setOnClickListener(this);
        tvDialogClose.setOnClickListener(this);

        tagStudyMatesAdapter = new TagStudyMatesAdapter(mContext, studymatesList);
        rvStudymatesTeacher.setLayoutManager(new LinearLayoutManager(mContext));
        rvStudymatesTeacher.setAdapter(tagStudyMatesAdapter);

        etSearchStudymates.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tagStudyMatesAdapter.filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        if (v == tvDialogClose) {
            dismiss();
        } else if (v == tvDialogTag) {
            String[] tagUserArray = tagStudyMatesAdapter.getTagIds().toArray(new String[tagStudyMatesAdapter.getTagIds().size()]);
            if (tagUserArray.length > 0) {
                ((TeacherHomeFragment) fragment).tagFriendInFeedRequest.setUser_id(AppConstant.tagUserArray);
                ((TeacherHomeFragment) fragment).callTagFriendInFeed();
                dismiss();
            } else {
                Utils.showToast("Please Select Study mates to tag ", mContext);
            }
        }


    }
}
