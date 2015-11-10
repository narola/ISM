package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.ws.ResponseObject;
import com.ism.ws.WebserviceWrapper;

/**
 * Created by c162 on 09/11/15.
 */
public class NotificationFragment extends Fragment implements WebserviceWrapper.WebserviceResponse, View.OnClickListener {
    private View view;
    private MyTypeFace myTypeFace;
    private TextView txtAlertsTags, txtAlertsAuthor, txtAlertsCommentLike, txtAlertsNoticeBoard, txtCommentNotification, txtLikeNotification, txtNoticeBoardNotification, txtAuthorNotification, txtTagNotification;
    private View includeComment, includeLike, includeNoticeBoard, includeTag, includeAuthor;
    private RadioGroup radioGroupComment, radioGroupLike, radioGroupNoticeBoard, radioGroupAuthor, radioGroupTag;
    private RadioButton radioButtonYesComment, radioButtonNoComment, radioButtonNoLike, radioButtonYesLike, radioButtonYesNoticeBoard, radioButtonNoNoticeBoard, radioButtonYesTag, radioButtonNoTag, radioButtonYesAuthor, radioButtonNoAuthor;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_notifications, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {
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

    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        ResponseObject responseObject = (ResponseObject) object;
        if (WebConstants.NOTIFICATION == apiCode) {
            if (responseObject.getStatus().equals(ResponseObject.SUCCESS)) {

            } else if (responseObject.getStatus().equals(ResponseObject.FAILED)) {

            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v == radioButtonNoAuthor) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesAuthor) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonNoComment) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesComment) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonNoLike) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesLike) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonNoTag) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesTag) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonNoNoticeBoard) {
            Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
        } else if (v == radioButtonYesNoticeBoard) {
            Toast.makeText(getActivity(), "Yes", Toast.LENGTH_SHORT).show();
        }
    }
}
