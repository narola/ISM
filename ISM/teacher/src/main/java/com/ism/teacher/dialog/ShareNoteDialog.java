package com.ism.teacher.dialog;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.object.Global;


/**
 * Created by c162 on 24/12/15.
 */
public class ShareNoteDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private Fragment mFragment;
    private TextView txtShareNoteTitle;
    private ImageView imgShare;
    private EditText etStudymatename;
    private TextView txtShareWith;
    //  DeskFragment.AlertDismissListener alertDismissListener;

    public ShareNoteDialog(Context mContext, Fragment fragment) {
        super(mContext);
        this.mContext = mContext;
        this.mFragment = fragment;

        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_share_notes);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);
        //  this.alertDismissListener = jotterFragment;
        initializeDialog();

    }

    private void initializeDialog() {

        //  new DeskFragment().setAlertDismissListener(alertDismissListener);
        txtShareNoteTitle = (TextView) findViewById(R.id.txt_share_note_title);
        txtShareNoteTitle.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtShareWith = (TextView) findViewById(R.id.txt_share_with);
        txtShareWith.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgShare = (ImageView) findViewById(R.id.img_share);
        imgShare.setOnClickListener(this);

        etStudymatename = (EditText) findViewById(R.id.et_studymate_name);
        etStudymatename.setTypeface(Global.myTypeFace.getRalewayRegular());

    }

    @Override
    public void onClick(View v) {
        if (v == imgShare) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //    alertDismissListener.onDismiss(JotterFragment.DIALOG_SHARE,null);
    }
}
