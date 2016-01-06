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
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.ws.model.RolemodelData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class SuggestedRoleModelsAdapter extends RecyclerView.Adapter<SuggestedRoleModelsAdapter.ViewHolder> {
    private static final String TAG = SuggestedRoleModelsAdapter.class.getSimpleName();
    Context context;
    ArrayList<RolemodelData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    HostActivity.ManageResourcesListner manageResourcesListner;
    public SuggestedRoleModelsAdapter(Context context, ArrayList<RolemodelData> arrayList,HostActivity.ManageResourcesListner manageResourcesListner)  {
        this.context = context;
        this.arrayList = arrayList;
        this.manageResourcesListner=manageResourcesListner;
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
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getModelImage(), holder.imgRoleModel, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getModelName());
            holder.txtOrganization.setText(arrayList.get(position).getOrganization());
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoleModelsDetailsDialog roleModelsDetailsDialog = new RoleModelsDetailsDialog(context, arrayList, position, Global.imageLoader);
                    roleModelsDetailsDialog.show();
                }
            });
            holder.imgAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageResourcesListner.onAddToFav(position);
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

        private ImageView imgRoleModel;
        private ImageView imgInfo;
        private ImageView imgAddToFav;
        private ImageView imgBookAdd;
        private TextView txtOrganization;
        private TextView txtBookName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgRoleModel = (ImageView) itemView.findViewById(R.id.img_pic);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            imgAddToFav = (ImageView) itemView.findViewById(R.id.img_add_fav);
            imgBookAdd = (ImageView) itemView.findViewById(R.id.img_book_add);
            txtBookName = (TextView) itemView.findViewById(R.id.txt_name);
            txtOrganization = (TextView) itemView.findViewById(R.id.txt_author);
            imgBookAdd.setVisibility(View.GONE);
            imgAddToFav.setVisibility(View.VISIBLE);
            imgInfo.setVisibility(View.VISIBLE);
        }
    }
}
