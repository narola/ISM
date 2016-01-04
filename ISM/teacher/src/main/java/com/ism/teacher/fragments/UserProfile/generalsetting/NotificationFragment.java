package com.ism.teacher.fragments.userprofile.generalsetting;

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

import com.ism.teacher.R;
import com.ism.teacher.Utility.PreferenceData;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.MyTypeFace;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;


/**
 * Created by c162 on 22/12/15.
 */
public class NotificationFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    private View view;
    private MyTypeFace myTypeFace;
    private TextView txtAlertsTags, txtAlertsAuthor, txtAlertsCommentLike, txtAlertsNoticeBoard, txtCommentNotification, txtLikeNotification, txtNoticeBoardNotification, txtAuthorNotification, txtTagNotification;
    private View includeComment, includeLike, includeNoticeBoard, includeTag, includeAuthor;
    private RadioGroup radioGroupComment, radioGroupLike, radioGroupNoticeBoard, radioGroupAuthor, radioGroupTag;
    private RadioButton radioButtonYesComment, radioButtonNoComment, radioButtonNoLike, radioButtonYesLike, radioButtonYesNoticeBoard, radioButtonNoNoticeBoard, radioButtonYesTag, radioButtonNoTag, radioButtonYesAuthor, radioButtonNoAuthor;
    private static String TAG = NotificationFragment.class.getSimpleName();
    private TeacherHostActivity activityHost;
    private String key, key_value;
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
        generalSettingsFragment = GeneralSettingsFragment.newInstance();
        myTypeFace = new MyTypeFace(getActivity());
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
        txtAlertsAuthor.setTypeface(myTypeFace.getRalewayRegular());
        txtAlertsCommentLike.setTypeface(myTypeFace.getRalewayRegular());
        txtAlertsNoticeBoard.setTypeface(myTypeFace.getRalewayRegular());
        txtAlertsTags.setTypeface(myTypeFace.getRalewayRegular());

        txtCommentNotification.setTypeface(myTypeFace.getRalewayRegular());
        txtLikeNotification.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoComment.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoLike.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesComment.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesLike.setTypeface(myTypeFace.getRalewayRegular());
        txtNoticeBoardNotification.setTypeface(myTypeFace.getRalewayRegular());
        txtAuthorNotification.setTypeface(myTypeFace.getRalewayRegular());
        txtTagNotification.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoAuthor.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoNoticeBoard.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesAuthor.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesNoticeBoard.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonNoTag.setTypeface(myTypeFace.getRalewayRegular());
        radioButtonYesTag.setTypeface(myTypeFace.getRalewayRegular());
//        radioButtonNoNoticeBoard.setTypeface(myTypeFace.getRalewayRegular());
//        radioButtonYesAuthor.setTypeface(myTypeFace.getRalewayRegular());
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
    public void onClick(View v) {
        String key_value, strPref = null, value = null;
        if (v == radioButtonNoAuthor) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_FOLLOWED_AUTHOR_POST, getActivity()), "No", getActivity());
        } else if (v == radioButtonYesAuthor) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_FOLLOWED_AUTHOR_POST, getActivity()), "Yes", getActivity());
        } else if (v == radioButtonNoComment) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_COMMENT, getActivity()), "No", getActivity());
        } else if (v == radioButtonYesComment) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_COMMENT, getActivity()), "Yes", getActivity());
        } else if (v == radioButtonNoLike) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_LIKE, getActivity()), "No", getActivity());
        } else if (v == radioButtonYesLike) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_LIKE, getActivity()), "Yes", getActivity());
        } else if (v == radioButtonNoTag) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_TAGGED, getActivity()), "No", getActivity());
        } else if (v == radioButtonYesTag) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_TAGGED, getActivity()), "Yes", getActivity());
        } else if (v == radioButtonNoNoticeBoard) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_NOTIFICATION_NOTICEBOARD, getActivity()), "No", getActivity());
        } else if (v == radioButtonYesNoticeBoard) {
            generalSettingsFragment.setPreferenceList(PreferenceData.getStringPrefs(PreferenceData.NOTI_NOTIFICATION_NOTICEBOARD, getActivity()), "Yes", getActivity());
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

        key_value = getKeyPereference(PreferenceData.NOTI_NOTIFICATION_NOTICEBOARD);
        // Debug.i(TAG,"Value: "+key+"::"+key_value);
        if (key_value.equals("No")) {
            radioGroupNoticeBoard.check(R.id.radiobutton_no);

        } else {
            radioGroupNoticeBoard.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.NOTI_COMMENT);
        if (key_value.equals("No")) {
            radioGroupComment.check(R.id.radiobutton_no);

        } else {
            radioGroupComment.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.NOTI_LIKE);
        if (key_value.equals("No")) {
            radioGroupLike.check(R.id.radiobutton_no);

        } else {
            radioGroupLike.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.NOTI_FOLLOWED_AUTHOR_POST);
        if (key_value.equals("No")) {
            radioGroupAuthor.check(R.id.radiobutton_no);

        } else {
            radioGroupAuthor.check(R.id.radiobutton_yes);
        }
        key_value = getKeyPereference(PreferenceData.NOTI_TAGGED);
        if (key_value.equals("No")) {
            radioGroupTag.check(R.id.radiobutton_no);

        } else {
            radioGroupTag.check(R.id.radiobutton_yes);
        }

    }

    public String getKeyPereference(String keyPref) {
        key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        key_value = PreferenceData.getStringPrefs(key, getActivity(), "");
        return key_value;
    }

    public String getKeyPereferenceValue(String keyPref) {
        key = PreferenceData.getStringPrefs(keyPref, getActivity(), "");
        // key_value=PreferenceData.getStringPrefs(key, getActivity(), "");
        return key;
    }

    @Override
    public void onResponse(int apiCode, Object object, Exception error) {
        ResponseHandler responseHandler = (ResponseHandler) object;
        if (WebConstants.MANAGE_GENERAL_SETTINGS == apiCode) {
            if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {

            } else if (responseHandler.getStatus().equals(ResponseHandler.FAILED)) {

            }
        }
    }
}
