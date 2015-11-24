package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.Utility.Utils;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.StudentAttemptedFragment;
import com.ism.author.fragment.TrialExamObjectiveDetailFragment;
import com.ism.author.object.MyTypeFace;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.helper.Attribute;
import com.ism.author.ws.helper.ResponseHandler;
import com.ism.author.ws.helper.WebserviceWrapper;
import com.ism.author.ws.model.Examsubmittor;
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
    private ResponseHandler responseHandlerEval;

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

        final ArrayList<Examsubmittor> arrayList = resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor();
        try {
            studentName = arrayList.get(position).getStudentName();
            viewholder.txtStudentName.setText(arrayList.get(position).getStudentName());
            //viewholder.txtSchoolName.setText("Exam Type : " + arrayList.get(position).getEx());
            //  viewholder.txtClass.setText(arrayList.get(position).getClassName());


            if (arrayList.get(position).isFlagged()) {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_selected);
            } else {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_unselected);
            }
            imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getStudentProfilePic(), viewholder.imgUserPic, ISMAuthor.options);
            viewholder.lLMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.showToast("Click " + position, context);
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
                        ((AuthorHostActivity) context).startProgress();
                        callAPIStudentEvaluations(arrayList.get(position).getStudentId(), resObjStudentAttempted.getExamSubmission().get(0).getExamId(), studentName);
                    } else {
                        ((AuthorHostActivity) context).startProgress();
                        TrialExamDetailsAdapter trialExamDetailsAdapter = new TrialExamDetailsAdapter(StudentAttemptedFragment.responseObjQuestions, context, fragment, null);
                        TrialExamObjectiveDetailFragment.rvList.setAdapter(trialExamDetailsAdapter);
                        trialExamDetailsAdapter.notifyDataSetChanged();
                        ((AuthorHostActivity) context).stopProgress();
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
            if (Utility.isOnline(context)) {
                ((AuthorHostActivity) context).startProgress();
                Attribute attribute = new Attribute();
                attribute.setStudentId("202");
                attribute.setExamId("9");
                new WebserviceWrapper(context, attribute, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMEVALUATIONS);
            } else {
                Utility.toastOffline(context);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public long getItemId(int position) {
        return resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor().size();
    }

    @Override
    public int getItemCount() {
        return resObjStudentAttempted.getExamSubmission().get(0).getExamsubmittor().size();
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        try {
            ((AuthorHostActivity) context).stopProgress();

            if (API_METHOD == WebConstants.GETEXAMEVALUATIONS) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.STATUS_SUCCESS)) {
                    if (responseHandler.getExamEvaluation().get(0).getEvaluation().size() != 0) {
                        responseHandlerEval = responseHandler;
                        TrialExamDetailsAdapter trialExamDetailsAdapter = new TrialExamDetailsAdapter(StudentAttemptedFragment.responseObjQuestions, context, fragment, responseHandlerEval);
                        TrialExamObjectiveDetailFragment.rvList.setAdapter(trialExamDetailsAdapter);
                        trialExamDetailsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Debug.i(TAG, "Response :" + WebConstants.GETEXAMEVALUATIONS + " :" + resObjStudentAttempted.getStatus());
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
