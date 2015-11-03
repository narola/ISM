package com.ism.dialog;

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
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.TagStudyMatesAdapter;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Created by c166 on 24/10/15.
 */
public class TagStudyMatesDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = TagStudyMatesDialog.class.getSimpleName();

    private RecyclerView rvStudymates;
	private TextView tvDialogClose, tvDialogTag;
	private EditText etSearchStudymates;

	private Context context;
	private TagStudyMatesAdapter tagStudyMatesAdapter;
	private ArrayList<Data> studymatesList;
	private TagStudyMatesListener tagStudyMatesListener;


	public interface TagStudyMatesListener {
		public void tagStudyMates(String[] arrTagUser);
	}

    public TagStudyMatesDialog(Context context, ArrayList<Data> studymatesList, TagStudyMatesListener tagStudyMatesListener) {
        super(context);

        this.context = context;
        this.studymatesList = studymatesList;
	    this.tagStudyMatesListener = tagStudyMatesListener;

        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        w.setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_search_studymates_student);

        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();
    }

    private void initializeDialog() {
        rvStudymates = (RecyclerView) findViewById(R.id.rv_studymates_student);
        etSearchStudymates = (EditText) findViewById(R.id.et_search_studymates);
        tvDialogClose = (TextView) findViewById(R.id.tv_dialog_close);
        tvDialogTag = (TextView) findViewById(R.id.tv_dialog_tag);
        tvDialogTag.setOnClickListener(this);
        tvDialogClose.setOnClickListener(this);

        tagStudyMatesAdapter = new TagStudyMatesAdapter(context, studymatesList);
        rvStudymates.setLayoutManager(new LinearLayoutManager(context));
        rvStudymates.setAdapter(tagStudyMatesAdapter);

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
	            tagStudyMatesListener.tagStudyMates(tagUserArray);
                dismiss();
            } else {
	            Toast.makeText(context, "Please Select Study mates to tag.", Toast.LENGTH_LONG).show();
            }
        }

    }
}