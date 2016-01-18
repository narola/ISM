package com.ism.author.adapter.gotrending;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.utility.Debug;
import com.ism.author.utility.HtmlImageGetter;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.TrendingQuestion;

import java.util.ArrayList;

/**
 * Created by c162 on 17/12/15.
 */
public class PastQuestionsAdapter extends RecyclerView.Adapter<PastQuestionsAdapter.ViewHolder> implements HtmlImageGetter.RefreshDataAfterLoadImage {
    private static final String TAG = PastQuestionsAdapter.class.getSimpleName();
    public static final String ARG_QUESTION_ID = "questionId";
    Context context;
    ArrayList<TrendingQuestion> arrayList = new ArrayList<>();
    private LayoutInflater inflater;


    public PastQuestionsAdapter(Context context, ArrayList<TrendingQuestion> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_past_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.txt_Creator_Name.setText(arrayList.get(position).getPostedByUsername());
            holder.txtNoOfFollowers.setText(arrayList.get(position).getFollowerCount());
            holder.txtQuestion.setText(arrayList.get(position).getQuestionText());
            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getPostedByPic(), holder.imgDpPostCreator, ISMAuthor.options);


//            holder.txtAnswer.setText(Html.fromHtml(arrayList.get(position).getAnswerText()));

            if (arrayList.get(position).getAnswerText().contains("img") || arrayList.get(position).getAnswerText().contains("http:")
                    || arrayList.get(position).getAnswerText().contains("https:")) {

                if (arrayList.get(position).getSpan() == null) {
                    Debug.e(TAG, "The answer_text is::::" + arrayList.get(position).getAnswerText());
                    arrayList.get(position).setSpan(Html.fromHtml(arrayList.get(position).getAnswerText(),
                            new HtmlImageGetter(50, 50, context, (HtmlImageGetter.RefreshDataAfterLoadImage) this
                            ), null));

                } else {
                    Debug.e(TAG, "The answer_text is::::" + arrayList.get(position).getAnswerText());
                    holder.txtAnswer.setText(Html.fromHtml(arrayList.get(position).getAnswerText(),
                            new HtmlImageGetter(50, 50, context, null
                            ), null));
                }
            } else {
                holder.txtAnswer.setText(Html.fromHtml(arrayList.get(position).getAnswerText()));
            }

            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*this is to select the current student*/
                    setBundleArgument(arrayList.get(position).getTrendingId());
                    ((AuthorHostActivity) context).loadFragmentInMainContainer(AuthorHostActivity.FRAGMENT_TRENDING_QUESTION_DETAIL);
                    //
                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgDpPostCreator;
        TextView txtAnswer, txtFollowers, txtQuestion, txt_Creator_Name, txtTotalFollowers, txtNoOfFollowers;
        LinearLayout llMain;

        public ViewHolder(View view) {
            super(view);
            imgDpPostCreator = (CircleImageView) itemView
                    .findViewById(R.id.img_user_dp);
            txtAnswer = (TextView) view.findViewById(R.id.txt_author_answer);
            txtAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtFollowers = (TextView) view.findViewById(R.id.txt_followers);
            txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtNoOfFollowers = (TextView) view.findViewById(R.id.txt_no_of_followers);
            txtNoOfFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtTotalFollowers = (TextView) view.findViewById(R.id.txt_no_of_followers);
            txtTotalFollowers.setTypeface(Global.myTypeFace.getRalewayBold());

            txtQuestion = (TextView) view.findViewById(R.id.txt_question);
            txtQuestion.setTypeface(Global.myTypeFace.getRalewayMedium());

            txt_Creator_Name = (TextView) view.findViewById(R.id.txt_creator_name);
            txt_Creator_Name.setTypeface(Global.myTypeFace.getRalewayRegular());

            llMain = (LinearLayout) view.findViewById(R.id.ll_main);

        }
    }

    public void setBundleArgument(String questionId) {

        getBundleArguments().putString(PastQuestionsAdapter.ARG_QUESTION_ID, questionId);
        notifyDataSetChanged();

    }

    private Bundle getBundleArguments() {
        return ((AuthorHostActivity) context).getBundle();
    }

    @Override
    public void refreshData() {


        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {

                notifyDataSetChanged();
                Debug.e(TAG, "notify data called");

            }
        };

        handler.post(r);
    }


}
