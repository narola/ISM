package com.ism.teacher.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.Data;
import com.ism.teacher.model.ResponseObject;

import java.util.ArrayList;

/**
 * Created by c75 on 16/11/15.
 */
public class SubjectiveQuestionAdapter extends RecyclerView.Adapter<SubjectiveQuestionAdapter.ViewHolder> {

    private static String TAG = SubjectiveQuestionAdapter.class.getSimpleName();

    Fragment mFragment;
    Context context;
    public static MyTypeFace myTypeFace;
    ResponseObject responseObject, studentEvalResObj;

    ArrayList<Data> dataArrayList = new ArrayList<Data>();

    //Boolean flags
    public boolean flag_excellent = false;
    public boolean flag_good = false;
    public boolean flag_fair = false;
    public boolean flag_average = false;
    public boolean flag_poor = false;
    public boolean flag_incorrect = false;

    public SubjectiveQuestionAdapter(ResponseObject responseObject, Context context, Fragment fragment, ResponseObject studentEvalResObj) {
        this.responseObject = responseObject;
        this.context = context;
        this.mFragment = fragment;
        this.studentEvalResObj = studentEvalResObj;
        myTypeFace = new MyTypeFace(context);
    }

   /* public void addAll(ArrayList<Data> data) {
        try {
            this.arrayListSubjectiveQuestions.clear();
            this.arrayListSubjectiveQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }
*/

    @Override
    public SubjectiveQuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_subjective_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestionNo, txtQuestionText, txtAnswer;
        EditText etEvaluationNotes, etComments;

        //Textviews for answer rating
        TextView txtExcellent, txtGood, txtFair, txtAverage, txtPoor, txtIncorrect;
        ImageView imgCopyComments, imgDeleteComments;

        public ViewHolder(View itemView) {
            super(itemView);
            try {
                txtQuestionNo = (TextView) itemView.findViewById(R.id.txt_question_no);
                txtQuestionText = (TextView) itemView.findViewById(R.id.txt_question_text);
                txtAnswer = (TextView) itemView.findViewById(R.id.txt_answer);
                etEvaluationNotes = (EditText) itemView.findViewById(R.id.et_evaluation_notes);
                etComments = (EditText) itemView.findViewById(R.id.et_comments);

                txtExcellent = (TextView) itemView.findViewById(R.id.txt_excellent);
                txtGood = (TextView) itemView.findViewById(R.id.txt_good);
                txtFair = (TextView) itemView.findViewById(R.id.txt_fair);
                txtAverage = (TextView) itemView.findViewById(R.id.txt_average);
                txtPoor = (TextView) itemView.findViewById(R.id.txt_poor);
                txtIncorrect = (TextView) itemView.findViewById(R.id.txt_incorrect);

                imgCopyComments = (ImageView) itemView.findViewById(R.id.img_copy_comments);
                imgDeleteComments = (ImageView) itemView.findViewById(R.id.img_delete_comments);


                txtQuestionNo.setTypeface(myTypeFace.getRalewayBold());
                txtQuestionText.setTypeface(myTypeFace.getRalewayRegular());
                txtAnswer.setTypeface(myTypeFace.getRalewayThin());


                txtExcellent.setTypeface(myTypeFace.getRalewayRegular());
                txtGood.setTypeface(myTypeFace.getRalewayRegular());
                txtFair.setTypeface(myTypeFace.getRalewayRegular());
                txtAverage.setTypeface(myTypeFace.getRalewayRegular());
                txtPoor.setTypeface(myTypeFace.getRalewayRegular());
                txtIncorrect.setTypeface(myTypeFace.getRalewayRegular());


            } catch (Exception e) {
                Debug.i(TAG, "ViewHolder Exceptions :" + e.toString());
            }


        }
    }

    @Override
    public void onBindViewHolder(final SubjectiveQuestionAdapter.ViewHolder holder, int position) {
        try {
            ArrayList<Data> arrayList = new ArrayList<Data>();
            arrayList = responseObject.getData().get(0).getQuestions();
            int qno = position + 1;
            //holder.txtQuestionNo.setText("Questions " + qno);
            holder.txtQuestionNo.setText(Html.fromHtml("QUESTION: " + qno));
            holder.txtQuestionText.setText(arrayList.get(position).getQuestionText());


            if (studentEvalResObj != null) {
                dataArrayList = studentEvalResObj.getData().get(0).getArrayListEvaluation();

                if (arrayList.get(position).getQuestionId().equalsIgnoreCase(dataArrayList.get(position).getQuestionId())) {
                    holder.txtAnswer.setText(dataArrayList.get(position).getStudentResponse());
                    holder.etEvaluationNotes.setText(dataArrayList.get(position).getEvaluationNotes());
                }
                notifyDataSetChanged();

            }

            holder.txtExcellent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (flag_excellent) {
                        holder.txtExcellent.setTextColor(context.getResources().getColor(R.color.color_blue));
                        flag_excellent = false;
                    } else {
                        holder.txtExcellent.setTextColor(context.getResources().getColor(R.color.red_error));
                        flag_excellent = true;
                    }
                }
            });
            holder.txtGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.txtFair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.txtAverage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.txtPoor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.txtIncorrect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        } catch (Exception e) {
            Debug.i(TAG, "BindViewHolder Exceptions :" + e.getLocalizedMessage());
        }
    }


    @Override
    public int getItemCount() {
        return responseObject.getData().get(0).getQuestions().size();
    }
}
