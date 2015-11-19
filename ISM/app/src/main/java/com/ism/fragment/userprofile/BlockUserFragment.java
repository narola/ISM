package com.ism.fragment.userprofile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.BlockedUserAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.InputValidator;
import com.ism.ws.model.ResponseObject;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c162 on 09/11/15.
 */
public class BlockUserFragment extends Fragment implements WebserviceWrapper.WebserviceResponse,View.OnClickListener{
    private View view;
    private MyTypeFace myTypeFace;
    private TextView txtBlockAssign, txtManageBlockUser, txtContactISMAdmin, txtContactDetails, txtEmailAddress, txtBlockUser, txtNotification, txtBlock;
    private EditText etBlockUser, etEmailAddress;
    private ListView listView;
    private BlockedUserAdapter blockedUserAdapter;
    InputValidator inputValidator;
    private static String TAG = BlockUserFragment.class.getSimpleName();
    private HostActivity activityHost;

    public static BlockUserFragment newInstance() {
        BlockUserFragment fragment = new BlockUserFragment();
        return fragment;
    }

    public BlockUserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_block_user, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());
        inputValidator=new InputValidator(getActivity());
        txtBlockAssign = (TextView) view.findViewById(R.id.txt_block_studymates);
        txtManageBlockUser = (TextView) view.findViewById(R.id.txt_manage_block_user);
        txtContactISMAdmin = (TextView) view.findViewById(R.id.txt_contact_ism_admin);

        txtContactDetails = (TextView) view.findViewById(R.id.txt_contact_details);
        txtNotification = (TextView) view.findViewById(R.id.txt_notification_name);
        //txtEmailAddress = (TextView) view.findViewById(R.id.txt_email_address);
        txtBlockUser = (TextView) view.findViewById(R.id.txt_block_user);
        etBlockUser = (EditText) view.findViewById(R.id.et_block_user);
        //etEmailAddress = (EditText) view.findViewById(R.id.et_email_address);
        txtBlock = (TextView) view.findViewById(R.id.txt_block);

        //txtNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtNotification.setText(R.string.strBlockStudyMate);

        txtContactDetails.setText(Html.fromHtml(getString(R.string.strContactAdminEmailAddress)));
        listView = (ListView) view.findViewById(R.id.listview);

        //set typeface
        txtBlockAssign.setTypeface(myTypeFace.getRalewayRegular());
        txtContactISMAdmin.setTypeface(myTypeFace.getRalewayRegular());
        txtManageBlockUser.setTypeface(myTypeFace.getRalewayRegular());
        txtContactDetails.setTypeface(myTypeFace.getRalewayThin());
        //txtEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
        txtBlockUser.setTypeface(myTypeFace.getRalewayRegular());
        etBlockUser.setTypeface(myTypeFace.getRalewayRegular());
        txtNotification.setTypeface(myTypeFace.getRalewayRegular());
        txtBlock.setTypeface(myTypeFace.getRalewayRegular());
        //etEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
        setUpList();


        txtBlock.setOnClickListener(this);


    }

    private void setUpList() {
        blockedUserAdapter = new BlockedUserAdapter(null, getActivity(), this);
        listView.setAdapter(blockedUserAdapter);
        ListViewDynamicHight();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }
    public void ListViewDynamicHight() {
        int totalHeight = 0;
        for (int i = 0; i < blockedUserAdapter.getCount(); i++) {
            if (i < 6) {
                View listItem = blockedUserAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            else
                break;

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseObject responseObject=(ResponseObject)object;
        if (WebConstants.GENERAL_SETTINGS==apiCode){
            if(responseObject.getStatus().equals(ResponseObject.SUCCESS)){

            }else if(responseObject.getStatus().equals(ResponseObject.FAILED)){

            }
        }

    }

    @Override
    public void onClick(View v) {
    if(v==txtBlock){
        if(inputValidator.validateAllConstraintsEmail(etBlockUser)){
            //api call
        }
    }

    }
}
