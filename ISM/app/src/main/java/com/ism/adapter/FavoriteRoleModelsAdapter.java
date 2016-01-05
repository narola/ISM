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
import com.ism.dialog.RoleModelsDetailsDialog;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.model.RolemodelData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavoriteRoleModelsAdapter extends RecyclerView.Adapter<FavoriteRoleModelsAdapter.ViewHolder> {
    private static final String TAG = FavoriteRoleModelsAdapter.class.getSimpleName();
    private final HostActivity.ManageResourcesListner manageResourcesListner;
    Context context;
    ArrayList<RolemodelData> arrayList = new ArrayList<>();

    public FavoriteRoleModelsAdapter(Context context, ArrayList<RolemodelData> arrayList, HostActivity.ManageResourcesListner manageResourcesListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.manageResourcesListner = manageResourcesListner;
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
            holder.txtOrganization.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtBookName.setText(arrayList.get(position).getModelName());
            holder.txtOrganization.setText(arrayList.get(position).getOrganization());
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getModelImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));

            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoleModelsDetailsDialog roleModelsDetailsDialog = new RoleModelsDetailsDialog(context, arrayList, position, Global.imageLoader);
                    roleModelsDetailsDialog.show();
                }
            });
            holder.imgRemoveFromFav.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgRemoveFromFav;
        private ImageView imgBookAdd;
        private TextView txtOrganization;
        private TextView txtBookName;


        public ViewHolder(View itemView) {
            super(itemView);
            imgBook = (ImageView) itemView.findViewById(R.id.img_pic);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            imgRemoveFromFav = (ImageView) itemView.findViewById(R.id.img_add_fav);
            imgBookAdd = (ImageView) itemView.findViewById(R.id.img_book_add);
            txtBookName = (TextView) itemView.findViewById(R.id.txt_name);
            txtOrganization = (TextView) itemView.findViewById(R.id.txt_author);
            imgRemoveFromFav.setVisibility(View.VISIBLE);
            imgRemoveFromFav.setBackgroundResource(R.drawable.img_like_red);
            imgInfo.setVisibility(View.VISIBLE);
        }
    }
}
