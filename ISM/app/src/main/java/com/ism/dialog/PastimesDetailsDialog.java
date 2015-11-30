package com.ism.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.ws.model.PastimeData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 24/10/15.
 */
public class PastimesDetailsDialog extends Dialog implements View.OnClickListener {
    ImageLoader imageLoader;
    private final int position;
    private Context mContext;
    private TextView tvDialogClose;
    private ArrayList<PastimeData> arrayList;
    MyTypeFace myTypeFace;
    private TextView txtDone;

    public PastimesDetailsDialog(Context mContext, ArrayList<PastimeData>
            arrayList, int position, ImageLoader imageLoader) {
        super(mContext);

        this.mContext = mContext;
        this.arrayList = arrayList;
        this.position = position;
        myTypeFace = new MyTypeFace(mContext);
        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.imageLoader = imageLoader;
        setContentView(R.layout.dailog_pastimes_details);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();

    }

    private void initializeDialog() {
        TextView txtPastimesDetails = (TextView) findViewById(R.id.txt_pastimes_details);
        txtDone = (TextView) findViewById(R.id.txt_done);
        TextView txtPastimes = (TextView) findViewById(R.id.txt_pastimes);
        TextView txtPastimesName = (TextView) findViewById(R.id.txt_pastimes_name);
        ImageView imgPastimes = (ImageView) findViewById(R.id.img_image);
        txtPastimesDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtPastimes.setTypeface(myTypeFace.getRalewayRegular());
        txtPastimesName.setTypeface(myTypeFace.getRalewayRegular());
        txtDone.setTypeface(myTypeFace.getRalewayRegular());
        txtPastimesName.setText(arrayList.get(position).getPastimeName());
        imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getPastimeImage(), imgPastimes, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
        txtDone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == txtDone) {
            dismiss();
        }
    }
}
