package com.ism.author.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.adapter.SearchStudyMatesAdapter;
import com.ism.author.fragment.HomeFragment;
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 26/10/15.
 */
public class TagUserDialog extends Dialog implements View.OnClickListener {


    Context mContext;
    RecyclerView rvStudymates;
    SearchStudyMatesAdapter searchStudyMatesAdapter;
    TextView tvDialogClose, tvTagUsers;
    ArrayList<Data> data;

    Fragment fragment;


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
        tvDialogClose.setOnClickListener(this);
        tvTagUsers.setOnClickListener(this);


        rvStudymates.setAdapter(searchStudyMatesAdapter);
        rvStudymates.setLayoutManager(new LinearLayoutManager(mContext));
        searchStudyMatesAdapter.addAll(data);

    }


    @Override
    public void onClick(View v) {


        if (v == tvDialogClose) {

            dismiss();

        } else if (v == tvTagUsers) {


//            String [] tagUserArry = searchStudyMatesAdapter.getTagIds().toArray(new String[searchStudyMatesAdapter.getTagIds().size()]);


            String[] tagUserArry = new String[]{"141", "167"};
            ((HomeFragment) fragment).tagFriendInFeedRequest.setUser_id(tagUserArry);
            ((HomeFragment) fragment).callTagFriendInFeed();
            dismiss();


        }

    }
}
