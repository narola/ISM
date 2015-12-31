package com.ism.fragment.userProfile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.PreferenceData;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;

/**
 * Created by c162 on 09/11/15.
 */
public class NotificationFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    private View view;
    private TextView txtAlertsTags, txtAlertsAuthor, txtAlertsCommentLike, txtAlertsNoticeBoard, txtCommentNotification, txtLikeNotification, txtNoticeBoardNotification, txtAuthorNotification, txtTagNotification;
    private View includeComment, includeLike, includeNoticeBoard, includeTag, includeAuthor;
    private RadioGroup radioGroupComment, radioGroupLike, radioGroupNoticeBoard, radioGroupAuthor, radioGroupTag;
    private RadioButton radioButtonYesComment, radioButtonNoComment, radioButtonNoLike, radioButtonYesLike, radioButtonYesNoticeBoard, radioButtonNoNoticeBoard, radioButtonYesTag, radioButtonNoTag, radioButtonYesAuthor, radioButtonNoAuthor;
    private static String TAG = NotificationFragment.class.getSimpleName();
    private HostActivity activityHost;
    private String key,key_value;
    private GeneralSettingsFragment generalSettingsFragment;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
        generalSettingsFragment=GeneralSettingsFragment.newInstance();
        txtAlertsTags = (TextView) view.findViewById(R.id.txt_alerts_tags);
        txtAlertsAuthor = (TextView) view.findViewById(R.id.txt_alerts_author);
        txtAlertsNoticeBoard = (TextView) view.findViewById(R.id.txt_alerts_notice_board);
        txtAlertsCommentLike = (TextView) view.findViewById(R.id.txt_alerts_comment_like);

        includeTag = view.findViewById(R.id.include_tags);
        txtTagNotification = (TextView) includeTag.findViewById(R.id.txt_notification_name);
        radioGroupTag = (RadioGroup) includeTag.findViewById(R.id.radigroup_yes_no);
        radioButtonYesTag = (RadioButton) includeTag.findViewById(R.id.radiobutton_yes);
        radioButtonNoTag = (RadioButton) includeTag.findViewById(R.id.radiobutton_no);
        //txtNoticeBoardNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtTagNotification.setText(R.string.strReceivedNotificationTag);

        includeAuthor = view.findViewById(R.id.include_author);
        txtAuthorNotification = (TextView) includeAuthor.findViewById(R.id.txt_notification_name);
        radioGroupAuthor = (RadioGroup) includeAuthor.findViewById(R.id.radigroup_yes_no);
        radioButtonYesAuthor = (RadioButton) includeAuthor.findViewById(R.id.radiobutton_yes);
        radioButtonNoAuthor = (RadioButton) includeAuthor.findViewById(R.id.radiobutton_no);
        //txtNoticeBoardNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtAuthorNotification.setText(R.string.strAuthorNotification);

        includeNoticeBoard = view.findViewById(R.id.include_notice_board);
        txtNoticeBoardNotification = (TextView) includeNoticeBoard.findViewById(R.id.txt_notification_name);
        radioGroupNoticeBoard = (RadioGroup) includeNoticeBoard.findViewById(R.id.radigroup_yes_no);
        radioButtonYesNoticeBoard = (RadioButton) includeNoticeBoard.findViewById(R.id.radiobutton_yes);
        radioButtonNoNoticeBoard = (RadioButton) includeNoticeBoard.findViewById(R.id.radiobutton_no);
        //txtNoticeBoardNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtNoticeBoardNotification.setText(R.string.strNoticeNotification);


        includeLike = view.findViewById(R.id.include_like);
        txtLikeNotification = (TextView) includeLike.findViewById(R.id.txt_notification_name);
        radioGroupLike = (RadioGroup) includeLike.findViewById(R.id.radigroup_yes_no);
        radioButtonYesLike = (RadioButton) includeLike.findViewById(R.id.radiobutton_yes);
        radioButtonNoLike = (RadioButton) includeLike.findViewById(R.id.radiobutton_no);
        //txtCommentNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtLikeNotification.setText(R.string.strLikeNotification);

        includeComment = view.findViewById(R.id.include_comment);
        txtCommentNotification = (TextView) includeComment.findViewById(R.id.txt_notification_name);
        radioGroupComment = (RadioGroup) includeComment.findViewById(R.id.radigroup_yes_no);
        radioButtonYesComment = (RadioButton) includeComment.findViewById(R.id.radiobutton_yes);
        radioButtonNoComment = (RadioButton) includeComment.findViewById(R.id.radiobutton_no);
        // txtCommentNotification.setTextColor(getResources().getColor(R.color.color_blue));
        txtCommentNotification.setText(R.string.strCommentNotification);


        //set typeface
        txtAlertsAuthor.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtAlertsCommentLike.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtAlertsNoticeBoard.setTypeface(Global.myTypeFace.getRalewayMedium());
        txtAlertsTags.setTypeface(Global.myTypeFace.getRalewayMedium());

        txtCommentNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtLikeNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoComment.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoLike.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesComment.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesLike.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtNoticeBoardNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAuthorNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtTagNotification.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoNoticeBoard.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesNoticeBoard.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoTag.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonYesTag.setTypeface(Global.myTypeFace.getRalewayRegular());
//        radioButtonNoNoticeBoard.setTypeface(Global.myTypeFace.getRalewayRegular());
//        radioButtonYesAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());
        radioButtonNoComment.setOnClickListener(this);
        radioButtonYesComment.setOnClickListener(this);
        radioButtonNoLike.setOnClickListener(this);
        radioButtonYesLike.setOnClickListener(this);
        radioButtonNoTag.setOnClickListener(this);
        radioButtonYesTag.setOnClickListener(this);
        radioButtonNoAuthor.setOnClickListener(this);
        radioButtonYesAuthor.setOnClickListener(this);
        radioButtonNoNoticeBoard.setOnClickListener(this);
        radioButtonYesNoticeBoard.setOnClickListener(this);
        setDefaultValues();
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (WebConstants.MANAGE_GENERAL_SETTINGS == apiCode) {
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {

            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {

            }
        }

    }


    @Override
    public void onClick(View v) {
        String key_value,strPref = null,value = null;
        if (v == radioButtonNoAuthor) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_FOLLOWED_AUTHOR_POST, getActivity()), getActivity().getResources().getString(R.string.strNo),getActivity());
        } else if (v == radioButtonYesAuthor) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_FOLLOWED_AUTHOR_POST, getActivity()), getActivity().getResources().getString(R.string.strYes),getActivity());
        } else if (v == radioButtonNoComment) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_COMMENT, getActivity()), getActivity().getResources().getString(R.string.strNo),getActivity());
        } else if (v == radioButtonYesComment) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_COMMENT, getActivity()), getActivity().getResources().getString(R.string.strYes),getActivity());
        } else if (v == radioButtonNoLike) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_LIKE, getActivity()), getActivity().getResources().getString(R.string.strNo),getActivity());
        } else if (v == radioButtonYesLike) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_LIKE, getActivity()), getActivity().getResources().getString(R.string.strYes),getActivity());
        } else if (v == radioButtonNoTag) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_TAGGED, getActivity()), getActivity().getResources().getString(R.string.strNo),getActivity());
        } else if (v == radioButtonYesTag) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_TAGGED, getActivity()), getActivity().getResources().getString(R.string.strYes),getActivity());
        } else if (v == radioButtonNoNoticeBoard) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_NOTIFICATION_NOTICEBOARD, getActivity()), getActivity().getResources().getString(R.string.strNo),getActivity());
        } else if (v == radioButtonYesNoticeBoard) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_NOTIFICATION_NOTICEBOARD, getActivity()), getActivity().getResources().getString(R.string.strYes),getActivity());
        }
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
           // generalSettingsFragment.callApiGetGeneralSettingPreferences();
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
    }

    private void setDefaultValues() {

        key_value=getKeyPereference(PreferenceData.NOTI_NOTIFICATION_NOTICEBOARD);
       // Debug.i(TAG,"Value: "+key+"::"+key_value);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupNoticeBoard.check(R.id.radiobutton_no);

        } else {
            radioGroupNoticeBoard.check(R.id.radiobutton_yes);
        }
        key_value=getKeyPereference(PreferenceData.NOTI_COMMENT);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupComment.check(R.id.radiobutton_no);

        } else {
            radioGroupComment.check(R.id.radiobutton_yes);
        }
        key_value=getKeyPereference(PreferenceData.NOTI_LIKE);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupLike.check(R.id.radiobutton_no);

        } else {
            radioGroupLike.check(R.id.radiobutton_yes);
        }
        key_value=getKeyPereference(PreferenceData.NOTI_FOLLOWED_AUTHOR_POST);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupAuthor.check(R.id.radiobutton_no);

        } else {
            radioGroupAuthor.check(R.id.radiobutton_yes);
        }
        key_value=getKeyPereference(PreferenceData.NOTI_TAGGED);
        if (key_value.equals(getActivity().getResources().getString(R.string.strNo))) {
            radioGroupTag.check(R.id.radiobutton_no);

        } else {
            radioGroupTag.check(R.id.radiobutton_yes);
        }

    }

    public String getKeyPereference(String keyPref) {
        key=PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        key_value=PreferenceData.getStringPrefs(key, getActivity(), "");
        return  key_value;
    }
    public String getKeyPereferenceValue(String keyPref) {
        key=PreferenceData.getStringPrefs(keyPref, getActivity(), "");
       // key_value=PreferenceData.getStringPrefs(key, getActivity(), "");
        return  key;
    }
}
