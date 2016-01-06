package com.ism.adapter.myAuthor;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.fragment.MyAuthorFragment;
import com.ism.fragment.myAuthor.FindMoreAuthorsFragment;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.AuthorData;

import java.util.ArrayList;

/**
 * Created by c162 on 04/01/16.
 */
public class FindMoreAuthorAdapter extends RecyclerView.Adapter<FindMoreAuthorAdapter.ViewHolder> implements WebserviceWrapper.WebserviceResponse {


    private static final String TAG = FindMoreAuthorAdapter.class.getSimpleName();
    private final Context mContext;
    private Fragment mFragment;
    private ArrayList<AuthorData> arrList = new ArrayList<AuthorData>();
    private LayoutInflater inflater;
    public FindMoreAuthorAdapter(Fragment mFragment, Context mContext) {
        this.mFragment = mFragment;
        this.mContext=mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.list_item_author, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            holder.txtAuthorName.setText(arrList.get(position).getAuthorName());
            holder.txtTotalBooks.setText(arrList.get(position).getTotalBooks()+" "+ mContext.getResources().getString(R.string.strBooks));
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER+arrList.get(position).getAuthorPic(),holder.imgUserPic, ISMStudent.options);
            holder.imgOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //myAuthorFragment.loadFragment(MyAuthorFragment.FRAGMENT_AUTHOR);
                    Bundle bundle = ((HostActivity) mContext).getBundle();
                    bundle.putString(AppConstant.AUTHOR_NAME, arrList.get(position).getAuthorName());
                    bundle.putString(AppConstant.AUTHOR_ID, arrList.get(position).getAuthorId());
                    ((HostActivity) mContext).setBundle(bundle);
//                    ((HostActivity)mContext).loadFragment(HostActivity.FRAGMENT_AUTHOR_OFFICE,bundle);
                    ((FindMoreAuthorsFragment) mFragment).loadFragment(MyAuthorFragment.FRAGMENT_TERM_AND_CONDITION);
                }
            });
            holder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.imgLike.isActivated()) {
                        holder.imgLike.setActivated(false);
                    } else
                        holder.imgLike.setActivated(true);
                    callApiForFollowAuthor(arrList.get(position).getAuthorId());
                }
            });
        } catch (Exception e) {
            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
        }
    }

    private void callApiForFollowAuthor(String authorId) {
        if (Utility.isConnected(mContext)) {
            try {
                ((HostActivity)mContext).showProgress();
                Attribute attribute=new Attribute();
                attribute.setFollowerId(Global.strUserId);
                attribute.setFollowTo(authorId);
                new WebserviceWrapper(mContext, attribute, this).new WebserviceCaller()
                        .execute(WebConstants.GET_FOLLOW_USER);
            } catch (Exception e) {
                Utility.alertOffline(mContext);
            }
        } else {
            Utility.alertOffline(mContext);
        }
    }


    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public void addAll(ArrayList<AuthorData> assignments) {
        try {
            this.copyListOfAuthors = assignments;
            this.arrList.clear();
            this.arrList.addAll(assignments);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            ((HostActivity)mContext).hideProgress();
            switch (apiCode) {
                case WebConstants.GET_FOLLOW_USER:
                    onResponseFollowAuthor(object, error);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

    private void onResponseFollowAuthor(Object object, Exception error) {
        try {
            if (object != null) {
                ResponseHandler responseHandler = (ResponseHandler) object;
                if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                    Debug.e(TAG, "onResponseFollowAuthor api SUCCESS  ");


                } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                    Utility.showToast(mContext, responseHandler.getMessage());

                }
            } else if (error != null) {
                Debug.e(TAG, "onResponseFollowAuthor api Exception : " + error.toString());
            }
        } catch (Exception e) {
            Debug.e(TAG, "onResponseFollowAuthor Exception : " + e.toString());
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtAuthorName, txtTotalBooks;
        private ImageView imgOption, imgLike,imgUserPic;

        public ViewHolder(View itemView) {
            super(itemView);

            txtAuthorName = (TextView) itemView.findViewById(R.id.txt_author_name);
            txtAuthorName.setTypeface(Global.myTypeFace.getRalewayBold());

            txtTotalBooks = (TextView) itemView.findViewById(R.id.txt_total_books);
            txtTotalBooks.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgOption=(ImageView)itemView.findViewById(R.id.img_menu);
            imgLike =(ImageView)itemView.findViewById(R.id.img_add_fav);
            imgUserPic =(ImageView)itemView.findViewById(R.id.img_pic);

        }
    }

    public ArrayList<AuthorData> copyListOfAuthors;


}
