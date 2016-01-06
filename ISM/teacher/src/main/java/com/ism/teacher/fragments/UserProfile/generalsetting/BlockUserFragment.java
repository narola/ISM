package com.ism.teacher.fragments.userprofile.generalsetting;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.adapters.settings.BlockedUserAdapter;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.helper.InputValidator;
import com.ism.teacher.object.Global;
import com.ism.teacher.object.MyTypeFace;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.BlockedUsers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c162 on 09/11/15.
 */
public class BlockUserFragment extends Fragment implements View.OnClickListener, WebserviceWrapper.WebserviceResponse, TeacherHostActivity.ResizeView {
    private View view;
    private MyTypeFace myTypeFace;
    private TextView txtBlockAssign, txtManageBlockUser, txtContactISMAdmin, txtContactDetails, txtEmailAddress, txtBlockUser, txtNotification, txtBlock;
    private EditText etBlockUser, etEmailAddress;
    private ListView listView;
    private BlockedUserAdapter blockedUserAdapter;
    InputValidator inputValidator;
    private static String TAG = BlockUserFragment.class.getSimpleName();
    private TeacherHostActivity activityHost;
    ArrayList<BlockedUsers> arrayListBlockedUser = new ArrayList<>();
    private TextView txtEmpty;

    public static BlockUserFragment newInstance() {
        BlockUserFragment fragment = new BlockUserFragment();
        return fragment;
    }

    @Override
    public void onUnBlockUser() {
        ListViewDynamicHight();
    }


    public BlockUserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_block_user, container, false);
        initGlobal();
        return view;
    }

    private void initGlobal() {
        myTypeFace = new MyTypeFace(getActivity());
        inputValidator = new InputValidator(getActivity());
        txtBlockAssign = (TextView) view.findViewById(R.id.txt_block_studymates);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
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

        txtContactDetails.setText(Html.fromHtml(getActivity().getResources().getString(R.string.strContactAdminEmailAddress)));
        listView = (ListView) view.findViewById(R.id.listview);

        //set typeface
        txtEmpty.setTypeface(myTypeFace.getRalewayRegular());
        txtBlockAssign.setTypeface(myTypeFace.getRalewayRegular());
        txtContactISMAdmin.setTypeface(myTypeFace.getRalewayRegular());
        txtManageBlockUser.setTypeface(myTypeFace.getRalewayRegular());
        txtContactDetails.setTypeface(myTypeFace.getRalewayThin());
        //txtEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
        txtBlockUser.setTypeface(myTypeFace.getRalewayRegular());
        etBlockUser.setTypeface(myTypeFace.getRalewayRegular());
        txtNotification.setTypeface(myTypeFace.getRalewayRegular());
        txtBlock.setTypeface(myTypeFace.getRalewayRegular());
        listView.setEmptyView(txtEmpty);
        //etEmailAddress.setTypeface(myTypeFace.getRalewayRegular());
        callApiForBlockedUser();
        txtBlock.setOnClickListener(this);
        txtContactDetails.setOnClickListener(this);
        // txtContactDetails.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void setUpList() {
        try {
            if (arrayListBlockedUser.size() > 0) {
                blockedUserAdapter = new BlockedUserAdapter(getActivity(), arrayListBlockedUser, this);
                listView.setAdapter(blockedUserAdapter);
                txtEmpty.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                ListViewDynamicHight();
                blockedUserAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Debug.i(TAG, "setUpList Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (TeacherHostActivity) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    public void openGmail() {
//        Intent mailClient = new Intent(Intent.ACTION_VIEW);
//        mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
//        startActivity(mailClient);
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

// Add data to the intent, the receiving app will decide what to do with it.
            intent.putExtra(Intent.EXTRA_SUBJECT, "Some Subject Line");
            intent.putExtra(Intent.EXTRA_TEXT, "Body of the message, woot!");
//            Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
//            sharingIntent.setType("text/plain");
//
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Medicine Enquiry");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
//
//
//            if (sharingIntent.resolveActivity(getActivity().getPackageManager()) != null)
//                getActivity().startActivity(Intent.createChooser(sharingIntent, "Share using"));
//            else {
//                Toast.makeText(getActivity(), "No app found on your phone which can perform this action", Toast.LENGTH_SHORT).show();
//            }
        } catch (Exception e) {
            Debug.i(TAG, "openGmail Exception : " + e.getLocalizedMessage());
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
            } else
                break;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
//    @Override
//    public void onResponse(Object object, Exception error, int apiCode) {
//        ResponseObject responseObject=(ResponseObject)object;
//        if (WebConstants.MANAGE_GENERAL_SETTINGS==apiCode){
//            if(responseObject.getStatus().equals(ResponseObject.SUCCESS)){
//
//            }else if(responseObject.getStatus().equals(ResponseObject.FAILED)){
//
//            }
//        }
//
//    }

    @Override
    public void onClick(View v) {
        if (v == txtBlock) {
            if (inputValidator.validateAllConstraintsEmail(etBlockUser)) {
                //api call
                callApiForBlockUser();
            }
        } else if (v == txtContactDetails) {
            // startApplication("com.gmail");
            openGmail();

//            if(txtContactDetails.getSelectionStart()!=-1 && txtContactDetails.getSelectionEnd()!=-1){
//               // openGmail();
//            }
            //txtContactDetails.setMovementMethod(openGmail());
        }

    }

    public static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void callApiForBlockUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setEmailId(etBlockUser.getText().toString().trim());
                attribute.setUserId(Global.strUserId);
                attribute.setBlockUser("0");
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.BLOCK_USER);
            } else {
                Utility.alertOffline(getActivity());
            }

        } catch (Exception e) {
            Debug.i(TAG, "callApiForBlockUser Exception : " + e.getLocalizedMessage());
        }
    }

    private void callApiForBlockedUser() {
        try {
            if (Utility.isConnected(getActivity())) {
                activityHost.showProgress();
                Attribute attribute = new Attribute();
                attribute.setUserId(Global.strUserId);
                new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller().execute(WebConstants.BLOCKED_USER);
            } else {
                Utility.alertOffline(getActivity());
            }

        } catch (Exception e) {
            Debug.i(TAG, "callApiForBlockUser Exception : " + e.getLocalizedMessage());
        }
    }


    private void onResponseBlockedUser(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    arrayListBlockedUser = responseHandler.getBlockedUsers();
                    setUpList();
                    Debug.i(TAG, "onResponseBlockedUser success");
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Log.i(TAG, "onResponseBlockedUser Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseBlockedUser api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseBlockedUser Exception : " + e.toString());
        }

    }

    private void onResponseBlockUser(Object object, Exception error) {
        try {
            activityHost.hideProgress();
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    etBlockUser.setText("");
                    callApiForBlockedUser();
                    arrayListBlockedUser = new ArrayList<>();
                    // add block user in list
                    Debug.i(TAG, "onResponseBlockUser success");
                } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {
                    Log.i(TAG, "onResponseBlockUser Failed");
                }
            } else if (error != null) {
                Log.i(TAG, "onResponseBlockUser api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponseBlockUser Exception : " + e.toString());
        }

    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        try {
            switch (apiCode) {
                case WebConstants.BLOCK_USER:
                    onResponseBlockUser(object, error);
                    break;
                case WebConstants.BLOCKED_USER:
                    onResponseBlockedUser(object, error);
                    break;
            }
        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exception : " + e.getLocalizedMessage());
        }
    }
}
