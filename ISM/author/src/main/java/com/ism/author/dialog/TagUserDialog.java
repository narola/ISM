package com.ism.author.dialog;

import android.app.Dialog;
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

import com.ism.author.R;
import com.ism.author.Utility.Utils;
import com.ism.author.adapter.SearchStudyMatesAdapter;
import com.ism.author.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 26/10/15.
 */
public class TagUserDialog extends Dialog implements View.OnClickListener {


    private static final String TAG = TagUserDialog.class.getSimpleName();

    private Context mContext;
    private RecyclerView rvStudymates;
    private SearchStudyMatesAdapter searchStudyMatesAdapter;
    private TextView tvDialogClose, tvTagUsers;
    private EditText etSearchStudymates;
    private ArrayList<Data> data;
    private TagUserListener tagUserListener;


    public interface TagUserListener {
        public void tagUsers(String[] arrTagUser);
    }


    public TagUserDialog(Context mContext, ArrayList<Data> data, TagUserListener tagUserListener) {
        super(mContext);

        this.mContext = mContext;
        this.data = data;
        this.tagUserListener = tagUserListener;

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

        searchStudyMatesAdapter = new SearchStudyMatesAdapter(mContext);
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
                tagUserListener.tagUsers(tagUserArray);
                dismiss();
            } else {
                Utils.showToast(mContext.getString(R.string.strselectuser), mContext);
            }


        }

    }

    private Context getActivity() {
        return mContext;
    }
}
