package com.ism.author.adapter.userprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.object.Global;
import com.ism.author.views.CircleImageView;

import java.util.ArrayList;

/**
 * Created by c162 on 17/12/15.
 */
public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    Context context;
    ArrayList<String> arrayList=new ArrayList<>();
    private LayoutInflater inflater;

    public FollowersAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_followers, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgDpPostCreator;
        TextView txtName, txtSchoolName, txtTotalFollowing, txt_Following, txtLiveFrom;
        RelativeLayout rrMain;

        public ViewHolder(View view) {
            super(view);
            imgDpPostCreator = (CircleImageView) itemView
                    .findViewById(R.id.img_user_dp);

            txtName =(TextView)view.findViewById(R.id.txt_name);
            txtName.setTypeface(Global.myTypeFace.getRalewayMedium());

            txtSchoolName =(TextView)view.findViewById(R.id.txt_school_name);
            txtSchoolName.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtLiveFrom =(TextView)view.findViewById(R.id.txt_live_from);
            txtLiveFrom.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtTotalFollowing =(TextView)view.findViewById(R.id.txt_total_following);
            txtTotalFollowing.setTypeface(Global.myTypeFace.getRalewayRegular());

            txt_Following =(TextView)view.findViewById(R.id.txt_following);
            txt_Following.setTypeface(Global.myTypeFace.getRalewayRegular());

            rrMain =(RelativeLayout)view.findViewById(R.id.rr_main);

        }
    }
}
