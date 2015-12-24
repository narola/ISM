package com.ism.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;

/**
 * Created by c162 on 24/12/15.
 */
public class AddNewNoteDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private TextView txtAddNewCategory;
    private ImageView imgSave;
    private EditText etCategoryName;

    public AddNewNoteDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;

        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_add_new_note);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();

    }

    private void initializeDialog() {

        txtAddNewCategory = (TextView) findViewById(R.id.txt_add_new_category);
        txtAddNewCategory.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgSave=(ImageView)findViewById(R.id.img_save);
        imgSave.setOnClickListener(this);

        etCategoryName=(EditText)findViewById(R.id.et_category_name);
        etCategoryName.setTypeface(Global.myTypeFace.getRalewayRegular());

    }

    @Override
    public void onClick(View v) {
        if (v == imgSave) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
