package com.ism.author.adapter.userprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;
import com.ism.author.ws.model.Followers;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by c162 on 17/12/15.
 */
public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {


    private static final String TAG = FollowersAdapter.class.getSimpleName();
    Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Followers> arrListFollowers = new ArrayList<Followers>();

    public FollowersAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_followers, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {

            Global.imageLoader.displayImage(WebConstants.USER_IMAGES + arrListFollowers.get(position).getFollowerProfilePic(), holder.imgDpFollower,
                    Utility.getDisplayImageOption(R.drawable.userdp, R.drawable.userdp));

            holder.txtName.setText(arrListFollowers.get(position).getFollowerName());


            DecimalFormat decimalFormat = new DecimalFormat("#");

            holder.txtTotalFollowing.setText(mContext.getString(R.string.strfollowing) + " " +
                    decimalFormat.format(arrListFollowers.get(position).getNumberOfAuthorFollowed()) + " " + mContext.getString(R.string.strauthor));
            holder.txtSchoolName.setText(mContext.getString(R.string.strstudentfrom) + " " + arrListFollowers.get(position).getFollowerSchool());
            holder.txtLiveFrom.setText(mContext.getString(R.string.strlivein) + " " + arrListFollowers.get(position).getFollowerCountryName());

        } catch (Exception e) {
            Debug.i(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {

        return arrListFollowers.size();

    }

    public void addAll(ArrayList<Followers> followers) {
        try {
            this.arrListFollowers.clear();
            this.arrListFollowers.addAll(followers);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CircleImageView imgDpFollower;
        TextView txtName, txtSchoolName, txtTotalFollowing, txtFollowing, txtLiveFrom;
        RelativeLayout rrMain;

        public ViewHolder(View view) {
            super(view);

            imgDpFollower = (CircleImageView) itemView
                    .findViewById(R.id.img_user_dp);

            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtSchoolName = (TextView) view.findViewById(R.id.txt_school_name);
            txtLiveFrom = (TextView) view.findViewById(R.id.txt_live_from);
            txtTotalFollowing = (TextView) view.findViewById(R.id.txt_total_following);
            txtFollowing = (TextView) view.findViewById(R.id.txt_following);
            rrMain = (RelativeLayout) view.findViewById(R.id.rr_main);


            txtName.setTypeface(Global.myTypeFace.getRalewayMedium());
            txtSchoolName.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtLiveFrom.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtTotalFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());

        }
    }
}
