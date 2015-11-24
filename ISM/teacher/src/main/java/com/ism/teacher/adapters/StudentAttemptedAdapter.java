package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
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
import com.ism.teacher.fragments.ExamObjectiveDetailFragment;
import com.ism.teacher.fragments.StudentAttemptedFragment;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.views.CircleImageView;
import com.ism.teacher.ws.helper.Attribute;
import com.ism.teacher.ws.helper.ResponseHandler;
import com.ism.teacher.ws.helper.WebserviceWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 04/11/15.
 */
public class StudentAttemptedAdapter extends RecyclerView.Adapter<StudentAttemptedAdapter.Viewholder> implements WebserviceWrapper.WebserviceResponse {
    ResponseHandler resObjStudentAttempted;
    private Context context;
    Fragment fragment;
    LayoutInflater inflater;
    private static MyTypeFace myTypeFace;
    ImageLoader imageLoader;
    private static String TAG = StudentAttemptedAdapter.class.getSimpleName();
    private int lastSelected = -1;
    private String studentName;
    private ResponseHandler responseObjectEval;

    public StudentAttemptedAdapter(ResponseHandler resObjStudentAttempted, Context context, Fragment fragment) {
        this.resObjStudentAttempted = resObjStudentAttempted;
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
        imageLoader = ImageLoader.getInstance();
        //imageLoader.init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.row_student_attempted, parent, false);
        Viewholder viewholder = new Viewholder(convertView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final Viewholder viewholder, final int position) {
        final ArrayList<Data> arrayList = resObjStudentAttempted.getData().get(0).getArrayListEvaluation();
        try {
            // Debug.i(TAG, "FullName:" + arrayList.get(position).getFullName());
            studentName = arrayList.get(position).getFull_name();
            viewholder.txtStudentName.setText(arrayList.get(position).getFull_name());
            viewholder.txtSchoolName.setText("Exam Type : " + arrayList.get(position).getSchoolName());
            viewholder.txtClass.setText(arrayList.get(position).getClass_name());
            // viewholder.txtScore.setText(arrayList.get(position).getEvaluationsScore());

            if (arrayList.get(position).isFlagged()) {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_selected);
            } else {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_unselected);
            }
            imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getProfile_pic(), viewholder.imgUserPic, ISMTeacher.options);
            viewholder.lLMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showToast("Click " + position, context);
                    if (lastSelected == -1) {
                        arrayList.get(position).setIsFlagged(true);
                        lastSelected = position;
                    } else if (position == lastSelected) {
                        arrayList.get(position).setIsFlagged(false);
                        lastSelected = -1;
                    } else if (position != lastSelected) {
                        arrayList.get(position).setIsFlagged(true);
                        arrayList.get(lastSelected).setIsFlagged(false);
                        lastSelected = position;
                    }
                    if (arrayList.get(position).isFlagged()) {
                           ((TeacherHostActivity) context).startProgress();
                        callAPIStudentEvaluations(arrayList.get(position).getStudent_id(), resObjStudentAttempted.getData().get(0).getExam_id(), studentName);
                    } else {
                         ((TeacherHostActivity) context).startProgress();
                        ObjectiveQuestionsAdapter objectiveQuestionsAdapter = new ObjectiveQuestionsAdapter(StudentAttemptedFragment.responseObjQuestions, context, fragment, null);
                        ExamObjectiveDetailFragment.rvList.setAdapter(objectiveQuestionsAdapter);
                        objectiveQuestionsAdapter.notifyDataSetChanged();
                          ((TeacherHostActivity) context).stopProgress();
                    }
                    notifyDataSetChanged();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callAPIStudentEvaluations(String studentId, String examId, String studentName) {
        try {
            if (Utility.isInternetConnected(context)) {
                  ((TeacherHostActivity) context).startProgress();
                Attribute attribute=new Attribute();
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
    public long getItemId(int position) {
        return resObjStudentAttempted.getData().get(0).getArrayListEvaluation().size();
    }

    @Override
    public int getItemCount() {
        return resObjStudentAttempted.getData().get(0).getArrayListEvaluation().size();
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        try {
              ((TeacherHostActivity) context).stopProgress();

            if (API_METHOD == WebConstants.GET_EXAM_EVALUATIONS) {
                ResponseHandler responseObject = (ResponseHandler) object;
                if (responseObject.getStatus().equals(WebConstants.API_STATUS_SUCCESS)) {
                    if (responseObject.getData().get(0).getArrayListEvaluation().size() != 0) {
                        responseObjectEval = responseObject;
                        ObjectiveQuestionsAdapter objectiveQuestionsAdapter = new ObjectiveQuestionsAdapter(StudentAttemptedFragment.responseObjQuestions, context, fragment, responseObjectEval);
                        ExamObjectiveDetailFragment.rvList.setAdapter(objectiveQuestionsAdapter);
                        objectiveQuestionsAdapter.notifyDataSetChanged();
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
                txtStudentName.setTypeface(myTypeFace.getRalewaySemiBold());
                txtSchoolName.setTypeface(myTypeFace.getRalewayRegular());
                txtClass.setTypeface(myTypeFace.getRalewayRegular());
                txtScore.setTypeface(myTypeFace.getRalewayBold());
            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }
        }
    }
}
