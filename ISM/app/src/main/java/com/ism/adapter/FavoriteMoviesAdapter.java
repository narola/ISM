package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.dialog.MovieDetailsDialog;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.model.MovieData;

import java.util.ArrayList;

/**
 * Created by c162 on 20/11/15.
 */
public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder> {
    private static final String TAG = FavoriteMoviesAdapter.class.getSimpleName();
    private final HostActivity.ManageResourcesListner manageResourcesListner;
    Context context;
    ArrayList<MovieData> arrayList = new ArrayList<>();

    public FavoriteMoviesAdapter(Context context, ArrayList<MovieData> arrayList, HostActivity.ManageResourcesListner manageResourcesListner) {
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

            holder.txtMovieName.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtYear.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtMovieName.setGravity(Gravity.LEFT);
            holder.txtYear.setGravity(Gravity.LEFT);
            holder.imgRemoveFav.setBackgroundResource(R.drawable.ic_like_red_active);
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getMovieImage(), holder.imgMovie, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtMovieName.setText(arrayList.get(position).getMovieName());
            holder.txtYear.setText(arrayList.get(position).getMovieGenre());
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDetailsDialog movieDetailsDialog = new MovieDetailsDialog(context, arrayList, position, Global.imageLoader);
                    movieDetailsDialog.show();
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

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgMovie;
        private TextView txtMovieName;
        private TextView txtYear;
        public ImageView imgRemoveFav;
        public ImageView imgInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            imgMovie = (ImageView) itemView.findViewById(R.id.img_pic);
            txtMovieName = (TextView) itemView.findViewById(R.id.txt_name);
            txtYear = (TextView) itemView.findViewById(R.id.txt_author);
            imgRemoveFav = (ImageView) itemView.findViewById(R.id.img_add_fav);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            imgRemoveFav.setVisibility(View.VISIBLE);
            imgInfo.setVisibility(View.VISIBLE);
        }
    }
}
