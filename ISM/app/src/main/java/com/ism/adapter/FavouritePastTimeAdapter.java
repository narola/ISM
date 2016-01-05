package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.dialog.PastimesDetailsDialog;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.model.PastimeData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavouritePastTimeAdapter extends RecyclerView.Adapter<FavouritePastTimeAdapter.ViewHolder> {
    private static final String TAG = FavouritePastTimeAdapter.class.getSimpleName();
    private final HostActivity.ManageResourcesListner manageResourcesListner;
    Context context;
    ArrayList<PastimeData> arrayList = new ArrayList<>();

    public FavouritePastTimeAdapter(Context context, ArrayList<PastimeData> arrayList, HostActivity.ManageResourcesListner manageResourcesListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.manageResourcesListner = manageResourcesListner;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_user_books, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.txtPastimeName.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtName.setText("");
            holder.txtPastimeName.setText(arrayList.get(position).getPastimeName());
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getPastimeImage(), holder.imgPastime, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PastimesDetailsDialog pastimesDetailsDialog = new PastimesDetailsDialog(context, arrayList, position, Global.imageLoader);
                    pastimesDetailsDialog.show();
                }
            });
            holder.imgRemoveFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageResourcesListner.onRemoveFromFav(position);
                    Log.e(TAG, "onClickAddToFav : " + position);

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPastime;
        private TextView txtPastimeName;
        private TextView txtName;
        private ImageView imgInfo;
        public ImageView imgRemoveFav;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPastime = (ImageView) itemView.findViewById(R.id.img_pic);
            txtPastimeName = (TextView) itemView.findViewById(R.id.txt_name);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            txtName = (TextView) itemView.findViewById(R.id.txt_author);
            imgRemoveFav = (ImageView) itemView.findViewById(R.id.img_add_fav);
            imgRemoveFav.setVisibility(View.VISIBLE);
            imgInfo.setVisibility(View.VISIBLE);
            imgRemoveFav.setBackgroundResource(R.drawable.img_like_red);
        }
    }
}
