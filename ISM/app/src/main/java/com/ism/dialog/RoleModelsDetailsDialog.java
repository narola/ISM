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
import com.ism.ws.model.RolemodelData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 24/10/15.
 */
public class RoleModelsDetailsDialog extends Dialog implements View.OnClickListener {
    ImageLoader imageLoader;
    private final int position;
    private Context mContext;
    private TextView tvDialogClose;
    private ArrayList<RolemodelData> arrayList;
    MyTypeFace myTypeFace;
    private TextView txtDone;

    public RoleModelsDetailsDialog(Context mContext, ArrayList<RolemodelData>
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
        setContentView(R.layout.dailog_rolemodel_details);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();

    }

    private void initializeDialog() {
        TextView txtRoleModelDetails = (TextView) findViewById(R.id.txt_role_model_details);
        txtDone = (TextView) findViewById(R.id.txt_done);
        TextView txtRoleModels = (TextView) findViewById(R.id.txt_role_models);
        TextView txtRoleModelsName = (TextView) findViewById(R.id.txt_role_models_name);
        TextView txtBirthdate = (TextView) findViewById(R.id.txt_birthdate);
        TextView txtBirthdateValue = (TextView) findViewById(R.id.txt_birthdate_value);
        TextView txtEducation = (TextView) findViewById(R.id.txt_education);
        TextView txtEducationValue = (TextView) findViewById(R.id.txt_education_value);
        TextView txtOrganization = (TextView) findViewById(R.id.txt_organization);
        TextView txtOrganizationValue = (TextView) findViewById(R.id.txt_organization_name);
        TextView txtDesc = (TextView) findViewById(R.id.txt_desc);
        TextView txtDescDetails = (TextView) findViewById(R.id.txt_desc_details);
        TextView txtAchive = (TextView) findViewById(R.id.txt_achievements);
        TextView txtAchiveValue = (TextView) findViewById(R.id.txt_achievements_value);
        TextView txtActivities = (TextView) findViewById(R.id.txt_activities);
        TextView txtActivitiesValue = (TextView) findViewById(R.id.txt_activities_value);
        TextView txtQuotes = (TextView) findViewById(R.id.txt_quotes);
        TextView txtQuotesValue = (TextView) findViewById(R.id.txt_quotes_value);
        ImageView imgRoleModel = (ImageView) findViewById(R.id.img_image);
        txtEducation.setTypeface(myTypeFace.getRalewayRegular());
        txtEducationValue.setTypeface(myTypeFace.getRalewayRegular());
        txtRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtRoleModelDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtRoleModels.setTypeface(myTypeFace.getRalewayRegular());
        txtDesc.setTypeface(myTypeFace.getRalewayRegular());
        txtDescDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtDone.setTypeface(myTypeFace.getRalewayRegular());
        txtBirthdate.setTypeface(myTypeFace.getRalewayRegular());
        txtBirthdateValue.setTypeface(myTypeFace.getRalewayRegular());
        txtOrganization.setTypeface(myTypeFace.getRalewayRegular());
        txtOrganizationValue.setTypeface(myTypeFace.getRalewayRegular());
        txtAchive.setTypeface(myTypeFace.getRalewayRegular());
        txtAchiveValue.setTypeface(myTypeFace.getRalewayRegular());
        txtActivities.setTypeface(myTypeFace.getRalewayRegular());
        txtActivitiesValue.setTypeface(myTypeFace.getRalewayRegular());
        txtQuotes.setTypeface(myTypeFace.getRalewayRegular());
        txtQuotesValue.setTypeface(myTypeFace.getRalewayRegular());


        txtEducationValue.setText(arrayList.get(position).getEducation());
        txtBirthdateValue.setText(arrayList.get(position).getBirthdate());
        txtOrganizationValue.setText(arrayList.get(position).getOrganization());
        txtDescDetails.setText(arrayList.get(position).getDescription());
        txtRoleModelsName.setText(arrayList.get(position).getModelName());
        txtAchiveValue.setText(arrayList.get(position).getAchievements());
        txtActivitiesValue.setText(arrayList.get(position).getActivities());
        txtQuotesValue.setText(arrayList.get(position).getQuotes());
        imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getModelImage(), imgRoleModel, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
        txtDone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == txtDone) {
            dismiss();
        }
    }
}
