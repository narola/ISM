package com.ism.author.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.AuthorHostActivity;
import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utils;
import com.ism.author.constant.WebConstants;
import com.ism.author.fragment.StudentAttemptedFragment;
import com.ism.author.fragment.TrialExamObjectiveDetailFragment;
import com.ism.author.helper.CircleImageView;
import com.ism.author.helper.ImageLoaderInit;
import com.ism.author.helper.MyTypeFace;
import com.ism.author.model.Data;
import com.ism.author.model.RequestObject;
import com.ism.author.model.ResponseObject;
import com.ism.author.ws.WebserviceWrapper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 04/11/15.
 */
public class StudentAttemptedAdapter extends RecyclerView.Adapter<StudentAttemptedAdapter.Viewholder> implements WebserviceWrapper.WebserviceResponse {
    ArrayList<Data> arrayList = new ArrayList<Data>();
    ResponseObject resObjStudentAttempted;
    private Context context;
    Fragment fragment;
    LayoutInflater inflater;
    private static MyTypeFace myTypeFace;
    ImageLoader imageLoader;
    private static String TAG = StudentAttemptedAdapter.class.getSimpleName();
    private int lastSelected = -1;
    private String studentName;
    private ResponseObject responseObjectEval;

    public StudentAttemptedAdapter(ResponseObject resObjStudentAttempted, Context context, Fragment fragment) {
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
        final ArrayList<Data> arrayList = resObjStudentAttempted.getData().get(0).getEvaluations();
        try {
            // Debug.i(TAG, "FullName:" + arrayList.get(position).getFullName());
            studentName = arrayList.get(position).getFullName();
            viewholder.txtStudentName.setText(arrayList.get(position).getFullName());
            viewholder.txtSchoolName.setText("Exam Type : " + arrayList.get(position).getSchoolName());
            viewholder.txtClass.setText(arrayList.get(position).getClassName());
            // viewholder.txtScore.setText(arrayList.get(position).getEvaluationsScore());

            if (arrayList.get(position).isFlagged()) {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_selected);
            } else {
                viewholder.lLMain.setBackgroundResource(R.drawable.bg_student_attempted_unselected);
            }
            imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getProfilePic(), viewholder.imgUserPic, ImageLoaderInit.options);
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
                          callAPIStudentEvaluations(arrayList.get(position).getStudentId(), resObjStudentAttempted.getData().get(0).getExamID(), studentName);
                    }
                    else{
                        ((AuthorHostActivity) context).startProgress();
                        TrialExamDetailsAdapter trialExamDetailsAdapter = new TrialExamDetailsAdapter(StudentAttemptedFragment.responseObjQuestions, context,fragment,null);
                        TrialExamObjectiveDetailFragment.rvList.setAdapter(trialExamDetailsAdapter);
                        trialExamDetailsAdapter.notifyDataSetChanged();
                        ((AuthorHostActivity) context).stopProgress();
                    }
                    notifyDataSetChanged();


                }
            });
//            if
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callAPIStudentEvaluations(String studentId, String examId, String studentName) {
        try {
            if (Utils.isInternetConnected(context)) {
                ((AuthorHostActivity) context).startProgress();
                RequestObject requestObject = new RequestObject();
                requestObject.setStudentId("202");
                requestObject.setExamId("3");
                new WebserviceWrapper(context, requestObject, (WebserviceWrapper.WebserviceResponse) this).new WebserviceCaller()
                        .execute(WebConstants.GETEXAMEVALUATIONS);
            } else {
                Utils.showToast(context.getString(R.string.strnetissue), context);
            }

        } catch (Exception e) {
            Debug.i(TAG, "callAPIStudentEvaluations Exceptions: " + e.getLocalizedMessage());
        }
    }


    @Override
    public long getItemId(int position) {
        return resObjStudentAttempted.getData().get(0).getEvaluations().size();
    }

    @Override
    public int getItemCount() {
        return resObjStudentAttempted.getData().get(0).getEvaluations().size();
    }

    @Override
    public void onResponse(int API_METHOD, Object object, Exception error) {
        try {
            ((AuthorHostActivity) context).stopProgress();

            if (API_METHOD == WebConstants.GETEXAMEVALUATIONS) {
                ResponseObject responseObject = (ResponseObject) object;
                if (responseObject.getStatus().equals(WebConstants.STATUS_SUCCESS)) {
                    if (responseObject.getData().get(0).getEvaluations().size() != 0) {
                        responseObjectEval= responseObject;
                        TrialExamDetailsAdapter trialExamDetailsAdapter = new TrialExamDetailsAdapter(StudentAttemptedFragment.responseObjQuestions, context,fragment,responseObjectEval);
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
