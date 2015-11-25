package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.teacher.ISMTeacher;
import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.constants.WebConstants;
import com.ism.teacher.fragments.SubjectiveQuestionsFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.ism.teacher.ws.model.Examsubmittor;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c75 on 10/11/15.
 */
public class MyStudentsAdapter extends RecyclerView.Adapter<MyStudentsAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse {
    private static final String TAG = MyStudentsAdapter.class.getSimpleName();
    Fragment mFragment;
    Context mContext;
    ImageLoader imageLoader;
    public static MyTypeFace myTypeFace;

    private int lastSelected = -1;
    ResponseHandler resObjStudentAttempted;
    private ResponseHandler responseObjectEval;

    //test
    String student_id_to_highlight = "";
    ArrayList<Examsubmittor> arrayListStudents = new ArrayList<>();
    public static String student_name = "";

    public MyStudentsAdapter(String student_id_to_highlight, ResponseHandler resObjStudentAttempted, Context context, Fragment fragment) {
        this.resObjStudentAttempted = resObjStudentAttempted;
        this.mFragment = fragment;
        this.mContext = context;
        imageLoader = ImageLoader.getInstance();
        myTypeFace = new MyTypeFace(context);
        this.student_id_to_highlight = student_id_to_highlight;
    }

    @Override
    public MyStudentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View students_row = inflater.inflate(R.layout.row_my_students, parent, false);
        ViewHolder viewHolder = new ViewHolder(students_row);
        return viewHolder;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtStudentName, txtStudentRollno;
        CircleImageView imgStudentPic;
        ImageView imgSeparator;

        RelativeLayout rlMyStudents;

        public ViewHolder(View itemView) {
            super(itemView);

            txtStudentName = (TextView) itemView.findViewById(R.id.txt_student_name);
            txtStudentRollno = (TextView) itemView.findViewById(R.id.txt_student_rollno);
            imgStudentPic = (CircleImageView) itemView.findViewById(R.id.img_student_pic);
            imgSeparator = (ImageView) itemView.findViewById(R.id.img_separator);

            rlMyStudents = (RelativeLayout) itemView.findViewById(R.id.rl_my_students);

            txtStudentName.setTypeface(myTypeFace.getRalewayRegular());
            txtStudentRollno.setTypeface(myTypeFace.getRalewayRegular());

        }
    }

    @Override
    public void onBindViewHolder(final MyStudentsAdapter.ViewHolder holder, final int position) {
        arrayListStudents = resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor();
        try {
            holder.txtStudentName.setText(arrayListStudents.get(position).getStudentName());

            imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getStudentProfilePic(), holder.imgStudentPic, ISMTeacher.options);

            holder.rlMyStudents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastSelected == -1) {
                        arrayListStudents.get(position).setIsFlagged(true);
                        lastSelected = position;
                    } else if (position == lastSelected) {
                        arrayListStudents.get(position).setIsFlagged(false);
                        lastSelected = -1;
                    } else if (position != lastSelected) {
                        arrayListStudents.get(position).setIsFlagged(true);
                        arrayListStudents.get(lastSelected).setIsFlagged(false);
                        lastSelected = position;
                    }
                    if (arrayListStudents.get(position).isFlagged()) {
                        ((TeacherHostActivity) mContext).startProgress();
                        // callAPIStudentEvaluations(arrayListStudents.get(position).getStudent_id(), resObjStudentAttempted.getData().get(0).getExam_id_received_from_bundle());
                        student_name = arrayListStudents.get(position).getStudentName();
                        callAPIStudentEvaluations(WebConstants.STUDENT_ID_1, WebConstants.EXAM_ID_11_SUBJECTIVE);
//                        callAPIStudentEvaluations(arrayListStudents.get(position).getStudentId(), WebConstants.EXAM_ID_11_SUBJECTIVE);
                    } else {
                        ((TeacherHostActivity) mContext).startProgress();

                        SubjectiveQuestionAdapter subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(SubjectiveQuestionsFragment.responseObjQuestions, mContext, mFragment, null);
                        SubjectiveQuestionsFragment.rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
                        subjectiveQuestionAdapter.notifyDataSetChanged();

                        ((TeacherHostActivity) mContext).stopProgress();
                    }
                    notifyDataSetChanged();


                }
            });

            if (student_id_to_highlight != null && !student_id_to_highlight.equals("")) {
                for (int i = 0; i < arrayListStudents.size(); i++) {
                    if (student_id_to_highlight.equalsIgnoreCase(arrayListStudents.get(i).getStudentId())) {
                        holder.txtStudentName.setTextColor(mContext.getResources().getColor(R.color.color_poor));
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void callAPIStudentEvaluations(String student_id, String exam_id) {

        try {
            if (Utility.isInternetConnected(mContext)) {
                ((TeacherHostActivity) mContext).startProgress();
                Attribute attribute = new Attribute();
                attribute.setStudentId(student_id);
                attribute.setExamId(exam_id);

                Log.e("subjective exam evaluation ", "student_id:" + student_id + "examid" + exam_id);

                new WebserviceWrapper(mContext, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GET_EXAM_EVALUATIONS);
            } else {
                Utility.showToast(mContext.getString(R.string.strnetissue), mContext);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public int getItemCount() {
        Log.e("test", "" + resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor().size());
        return resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor().size();
    }


    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            ((TeacherHostActivity) mContext).stopProgress();
            switch (api_code) {
                case WebConstants.GET_EXAM_EVALUATIONS:

                    ResponseHandler responseHandler = (ResponseHandler) object;
                    if (responseHandler.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {
                        if (responseHandler.getExamEvaluation().get(0).getEvaluation().size() != 0) {
                            responseObjectEval = responseHandler;

                            if (responseObjectEval.getStatus().equalsIgnoreCase(WebConstants.API_STATUS_SUCCESS)) {
                                SubjectiveQuestionsFragment.rlStudentDetails.setVisibility(View.VISIBLE);
                                SubjectiveQuestionsFragment.txt_student_name.setText(student_name);
                                // imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getProfile_pic(), holder.imgStudentPic, ISMTeacher.options);
                            }

                            SubjectiveQuestionAdapter subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(SubjectiveQuestionsFragment.responseObjQuestions, mContext, mFragment, responseObjectEval);
                            SubjectiveQuestionsFragment.rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
                            subjectiveQuestionAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Debug.i(TAG, "Response :" + WebConstants.GET_EXAM_EVALUATIONS + " :" + responseHandler.getStatus());
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }
}
