package com.ism.adapter.myAuthor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.realm.RealmHandler;

import java.util.ArrayList;

/**
 * Created by c162 on 08/01/16.
 */
public class GoTrendingQueAnsAuthorAdapter extends PagerAdapter {


    private static final String TAG = GoTrendingQueAnsAuthorAdapter.class.getSimpleName();
    private final Context mContext;
    private final RealmHandler realmHandler;
    private ArrayList<com.ism.ws.model.TrendingQuestionDetails> arrayList = new ArrayList<>();
    private LayoutInflater inflater;

    public GoTrendingQueAnsAuthorAdapter(Context mContext) {
        this.mContext = mContext;
        realmHandler = new RealmHandler(mContext);
        this.inflater = LayoutInflater.from(mContext);
    }

    public void addAll(ArrayList<com.ism.ws.model.TrendingQuestionDetails> trendingQuestionDetailses) {
        try {
            this.arrayList.clear();
            this.arrayList.addAll(trendingQuestionDetailses);
        } catch (Exception e) {
            Log.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

//    public void add(TrendingQuestionDetails trendingQuestionDetail) {
//        try {
//            this.arrayList.add(trendingQuestionDetail);
//        } catch (Exception e) {
//            Log.e(TAG, "addAllData Exception : " + e.toString());
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        try {


            View view = inflater.inflate(R.layout.list_item_question_answer_trending, container, false);
            RelativeLayout main = (RelativeLayout) view.findViewById(R.id.rr_main);
            TextView txtUsername = (TextView) view.findViewById(R.id.txt_user_name);
            txtUsername.setTypeface(Global.myTypeFace.getRalewayMedium());

            TextView txtAuthorname = (TextView) view.findViewById(R.id.txt_author_name);
            txtAuthorname.setTypeface(Global.myTypeFace.getRalewayBold());

            TextView txtAnswer = (TextView) view.findViewById(R.id.txt_answer);
            txtAnswer.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtAnswer.setMovementMethod(new ScrollingMovementMethod());

            TextView txtQuestion = (TextView) view.findViewById(R.id.txt_question);
            txtQuestion.setTypeface(Global.myTypeFace.getRalewayRegular());

            ImageView imgUserPic = (ImageView) view.findViewById(R.id.img_user_pic);

            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + arrayList.get(position).getPostedByPic(), imgUserPic, ISMStudent.options);
//        Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER+arrayList.get(position).getQuestionBy().getProfilePicture(),imgUserPic, ISMStudent.options);

            txtAuthorname.setText(((HostActivity) mContext).getBundle().getString(AppConstant.AUTHOR_NAME));

            txtQuestion.setText(arrayList.get(position).getQuestionText());

            txtAnswer.setText(arrayList.get(position).getAnswerText());

            txtUsername.setText(arrayList.get(position).getPostedByUsername());
//        txtUsername.setText(arrayList.get(position).getQuestionBy().getFullName());

            container.addView(view);
            return view;
        } catch (Exception e) {
            Log.e(TAG, "instantiateItem Exceptions : " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
