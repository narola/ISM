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
import com.ism.teacher.model.RequestObject;
import com.ism.teacher.model.ResponseObject;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.WebserviceWrapper;
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
    ResponseObject resObjStudentAttempted;
    private ResponseObject responseObjectEval;

    public MyStudentsAdapter(ResponseObject resObjStudentAttempted, Context context, Fragment fragment) {
        this.resObjStudentAttempted = resObjStudentAttempted;
        this.mFragment = fragment;
        this.mContext = context;
        imageLoader = ImageLoader.getInstance();
        myTypeFace = new MyTypeFace(context);
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
        final ArrayList<Data> arrayListStudents = resObjStudentAttempted.getData().get(0).getArrayListEvaluation();
        try {
            holder.txtStudentName.setText(arrayListStudents.get(position).getFull_name());

            imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getProfile_pic(), holder.imgStudentPic, ISMTeacher.options);

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
                        // callAPIStudentEvaluations(arrayListStudents.get(position).getStudent_id(), resObjStudentAttempted.getData().get(0).getExam_id());
                        callAPIStudentEvaluations(arrayListStudents.get(position).getStudent_id(), "11");
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

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void callAPIStudentEvaluations(String student_id, String exam_id) {

        try {
            if (Utility.isInternetConnected(mContext)) {
                ((TeacherHostActivity) mContext).startProgress();
                RequestObject requestObject = new RequestObject();
                requestObject.setStudentId(student_id);
                requestObject.setExamId(exam_id);

                Log.e("subjective exam evaluation ", "student_id:" + student_id + "examid" + exam_id);

                new WebserviceWrapper(mContext, requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
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
        Log.e("test", "" + resObjStudentAttempted.getData().get(0).getArrayListEvaluation().size());
        return resObjStudentAttempted.getData().get(0).getArrayListEvaluation().size();
    }


    @Override
    public void onResponse(int api_code, Object object, Exception error) {
        try {
            ((TeacherHostActivity) mContext).stopProgress();
            switch (api_code) {
                case WebConstants.GET_EXAM_EVALUATIONS:
                    ResponseObject responseObject = (ResponseObject) object;
                    if (responseObject.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {
                        if (responseObject.getData().get(0).getArrayListEvaluation().size() != 0) {
                            responseObjectEval = responseObject;

                            if (responseObjectEval.getStatus().equalsIgnoreCase(WebConstants.API_STATUS_SUCCESS)) {
                                SubjectiveQuestionsFragment.rlStudentDetails.setVisibility(View.VISIBLE);
                                SubjectiveQuestionsFragment.txt_student_name.setText(responseObject.getData().get(0).getStudentName());
                                // imageLoader.displayImage(WebConstants.USER_IMAGES + arrayListStudents.get(position).getProfile_pic(), holder.imgStudentPic, ISMTeacher.options);
                            }

                            SubjectiveQuestionAdapter subjectiveQuestionAdapter = new SubjectiveQuestionAdapter(SubjectiveQuestionsFragment.responseObjQuestions, mContext, mFragment, responseObjectEval);
                            SubjectiveQuestionsFragment.rvSubjectiveQuestionList.setAdapter(subjectiveQuestionAdapter);
                            subjectiveQuestionAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Debug.i(TAG, "Response :" + WebConstants.GET_EXAM_EVALUATIONS + " :" + responseObject.getStatus());
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }

    }
}
