package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.object.Global;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Examsubmittor;

import java.util.ArrayList;

/**
 * Created by c162 on 04/11/15.
 */
public class StudentAttemptedAdapter extends RecyclerView.Adapter<StudentAttemptedAdapter.Viewholder> implements WebserviceWrapper.WebserviceResponse {
    ResponseHandler resObjStudentAttempted;
    private Context context;
    Fragment fragment;
    LayoutInflater inflater;
    private static String TAG = StudentAttemptedAdapter.class.getSimpleName();
    private int lastSelected = -1;
    private String studentName;
    private ResponseHandler responseObjectEval;

    public StudentAttemptedAdapter(ResponseHandler resObjStudentAttempted, Context context, Fragment fragment) {
        this.resObjStudentAttempted = resObjStudentAttempted;
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.row_student_attempted, parent, false);
        Viewholder viewholder = new Viewholder(convertView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final Viewholder viewholder, final int position) {
        final ArrayList<Examsubmittor> arrayList = resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor();
        try {
            // Debug.i(TAG, "FullName:" + arrayList.get(position).getFullName());
            studentName = arrayList.get(position).getStudentName();
            viewholder.txtStudentName.setText(arrayList.get(position).getStudentName());
            // viewholder.txtClass.setText(arrayList.get(position).getClass_name());
            // viewholder.txtScore.setText(arrayList.get(position).getEvaluationsScore());

            if (arrayList.get(position).isFlagged()) {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_selected);
            } else {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_unselected);
            }
            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getStudentProfilePic(), viewholder.imgUserPic, ISMTeacher.options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callAPIStudentEvaluations(String studentId, String examId, String studentName) {
        try {
            if (Utility.isInternetConnected(context)) {
                ((TeacherHostActivity) context).showProgress();
                Attribute attribute = new Attribute();
                attribute.setStudentId(WebConstants.STUDENT_ID_202_OBJECCTIVE);
                attribute.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
                new WebserviceWrapper(context, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
            } else {
                Utility.showToast(context.getString(R.string.strnetissue), context);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }

    private void callAPIStudentEvaluations() {
        try {
            if (Utility.isInternetConnected(context)) {
                ((TeacherHostActivity) context).showProgress();
                Attribute attribute = new Attribute();
                attribute.setStudentId(WebConstants.STUDENT_ID_202_OBJECCTIVE);
                attribute.setExamId(WebConstants.EXAM_ID_9_OBJECTIVE);
                new WebserviceWrapper(context, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
            } else {
                Utility.showToast(context.getString(R.string.strnetissue), context);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public int getItemCount() {
        return resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor().size();
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        try {
            ((TeacherHostActivity) context).hideProgress();

            if (API_METHOD == WebConstants.GET_EXAM_EVALUATIONS) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(ResponseHandler.SUCCESS)) {
                    if (responseHandler.getExamEvaluation().get(0).getEvaluation().size() != 0) {
                        responseObjectEval = responseHandler;
//                        GetObjectiveAssignmentQuestionsAdapter getObjectiveAssignmentQuestionsAdapter = new GetObjectiveAssignmentQuestionsAdapter(StudentAttemptedFragment.responseObjQuestions, mContext, fragment, responseObjectEval);
//                        ObjectiveAssignmentQuestionsFragment.rvList.setAdapter(getObjectiveAssignmentQuestionsAdapter);
//                        getObjectiveAssignmentQuestionsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Debug.i(TAG, "Response :" + WebConstants.GET_EXAM_EVALUATIONS + " :" + resObjStudentAttempted.getStatus());
                }
            }

        } catch (Exception e) {
            Debug.i(TAG, "Response Exceptions :" + e.toString());
        }

    }


    public static class Viewholder extends RecyclerView.ViewHolder {
        public CircleImageView imgUserPic;
        public TextView txtStudentName, txtSchoolName, txtClass, txtScore;
        public LinearLayout lLMain;

        public Viewholder(View convertView) {
            super(convertView);
            try {
                imgUserPic = (CircleImageView) convertView.findViewById(R.id.img_user_pic);
                txtClass = (TextView) convertView.findViewById(R.id.txt_student_class);
                txtStudentName = (TextView) convertView.findViewById(R.id.txt_student_name);
                txtSchoolName = (TextView) convertView.findViewById(R.id.txt_student_school);
                txtScore = (TextView) convertView.findViewById(R.id.txt_student_marks);
                lLMain = (LinearLayout) convertView.findViewById(R.id.ll_main);
                txtStudentName.setTypeface(Global.myTypeFace.getRalewaySemiBold());
                txtSchoolName.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtClass.setTypeface(Global.myTypeFace.getRalewayRegular());
                txtScore.setTypeface(Global.myTypeFace.getRalewayBold());
            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) context).getBundle();
    }
}
