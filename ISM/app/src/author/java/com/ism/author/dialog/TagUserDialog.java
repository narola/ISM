package com.ism.author.dialog;

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

import com.ism.R;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.SearchStudyMatesAdapter;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.constant.WebConstants;
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 26/10/15.
 */
public class TagUserDialog extends Dialog implements View.OnClickListener {


    private static final String TAG = TagUserDialog.class.getSimpleName();

    Context mContext;
    Fragment fragment;
    RecyclerView rvStudymates;
    SearchStudyMatesAdapter searchStudyMatesAdapter;
    TextView tvDialogClose, tvTagUsers;
    EditText etSearchStudymates;
    ArrayList<Data> data;


    public TagUserDialog(Context mContext, Fragment fragment, ArrayList<Data> data) {
        super(mContext);

        this.mContext = mContext;
        this.data = data;
        this.fragment = fragment;


        Window w = getWindow();
        getWindow().getAttributes().windowAnimations =
                R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        w.setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_tag_user);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();


    }

    private void initializeDialog() {

        searchStudyMatesAdapter = new SearchStudyMatesAdapter(this.fragment);
        rvStudymates = (RecyclerView) findViewById(R.id.rv_studymates);
        tvDialogClose = (TextView) findViewById(R.id.tv_dialog_close);
        tvTagUsers = (TextView) findViewById(R.id.tv_tag_users);
        etSearchStudymates = (EditText) findViewById(R.id.et_search_studymates);
        tvDialogClose.setOnClickListener(this);
        tvTagUsers.setOnClickListener(this);


        rvStudymates.setAdapter(searchStudyMatesAdapter);
        rvStudymates.setLayoutManager(new LinearLayoutManager(mContext));
        searchStudyMatesAdapter.addAll(data);


        etSearchStudymates.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchStudyMatesAdapter.filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onClick(View v) {


        if (v == tvDialogClose) {

            dismiss();

        } else if (v == tvTagUsers) {

            String[] tagUserArray = searchStudyMatesAdapter.getTagIds().toArray(new String[searchStudyMatesAdapter.getTagIds().size()]);
            if (tagUserArray.length > 0) {
                ((HomeFragment) fragment).tagFriendInFeedRequest.setUser_id(WebConstants.tagUserArray);
                ((HomeFragment) fragment).callTagFriendInFeed();
                dismiss();
            } else {
                Utils.showToast(mContext.getString(R.string.strselectuser), mContext);
            }


        }

    }
}
