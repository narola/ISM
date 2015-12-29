package com.ism.author.adapter.gotrending;

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
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.TrendingQuestion;
import com.ism.commonsource.utility.Utility;

import java.util.ArrayList;

/**
 * Created by c162 on 17/12/15.
 */
public class QuestionsMostFollowAdapter extends RecyclerView.Adapter<QuestionsMostFollowAdapter.ViewHolder> {


    private static final String TAG = QuestionsMostFollowAdapter.class.getSimpleName();
    private final AuthorHostActivity.HostListenerTrending hostListenerTrending;
    Context context;
    ArrayList<TrendingQuestion> arrayList = new ArrayList<>();
    private LayoutInflater inflater;
    boolean isFlag = false;
    private int lastPosition = -1;

    public QuestionsMostFollowAdapter(Context context, ArrayList<TrendingQuestion> arrayList, AuthorHostActivity.HostListenerTrending hostListenerTrending) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        isFlag = true;
        this.hostListenerTrending = hostListenerTrending;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_most_follower_questions, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            if (lastPosition == -1) {
                lastPosition = position;
                hostListenerTrending.onViewSelect(position);
            }
            if (lastPosition == position) {

                holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.color_white));
            } else {
                holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.color_f2f2f2));
            }

            holder.txt_Creator_Name.setText(arrayList.get(position).getPostedByUsername());
            holder.txtDate.setText(Utility.DateFormat(arrayList.get(position).getPostedOn()));
            holder.txtFollowers.setText(arrayList.get(position).getFollowerCount() +
                    " Followers");
            holder.txtQuestion.setText(arrayList.get(position).getQuestionText());


            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrayList.get(position).getPostedByPic(), holder.imgDpPostCreator, ISMAuthor.options);

            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*this is to select the current student*/
                    //setBundleArgument(position);
                    lastPosition = position;
                    hostListenerTrending.onViewSelect(position);
                    notifyDataSetChanged();

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
        TextView txtDate, txtFollowers, txtQuestion, txt_Creator_Name;
        LinearLayout llMain;

        public ViewHolder(View view) {
            super(view);
            imgDpPostCreator = (CircleImageView) itemView
                    .findViewById(R.id.img_user_dp);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtDate.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtFollowers = (TextView) view.findViewById(R.id.txt_total_followers);
            txtFollowers.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtQuestion = (TextView) view.findViewById(R.id.txt_question);
            txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

            txt_Creator_Name = (TextView) view.findViewById(R.id.txt_creator_name);
            txt_Creator_Name.setTypeface(Global.myTypeFace.getRalewayRegular());

            llMain = (LinearLayout) view.findViewById(R.id.ll_main);

        }
    }

}
