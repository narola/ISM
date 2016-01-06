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
 * Created by c162 on 19/11/15.
 */
public class SuggestedMoviesAdapter extends RecyclerView.Adapter<SuggestedMoviesAdapter.ViewHolder> {
    private static final String TAG = SuggestedMoviesAdapter.class.getSimpleName();
    Context context;
    ArrayList<MovieData> arrayList = new ArrayList<>();
    HostActivity.ManageResourcesListner manageResourcesListner;

    public SuggestedMoviesAdapter(Context context, ArrayList<MovieData> arrayList, HostActivity.ManageResourcesListner manageResourcesListner) {
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
            holder.txtMovieYear.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtMovieName.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtMovieName.setGravity(Gravity.LEFT);
            holder.txtMovieYear.setGravity(Gravity.LEFT);
            holder.txtMovieName.setText(arrayList.get(position).getMovieName());
            holder.txtMovieYear.setText(arrayList.get(position).getMovieGenre());
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getMovieImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getMovieImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.imgMovieToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageResourcesListner.onAddToFav(position);
                }
            });
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDetailsDialog movieDetailsDialog = new MovieDetailsDialog(context, arrayList, position, Global.imageLoader);
                    movieDetailsDialog.show();
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
        private ImageView imgMovieToFav;
        private ImageView imgBookAdd;
        private TextView txtMovieYear;
        private TextView txtMovieName;

        public ViewHolder(View convertView) {
            super(convertView);
            imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            imgMovieToFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            txtMovieName = (TextView) convertView.findViewById(R.id.txt_name);
            txtMovieYear = (TextView) convertView.findViewById(R.id.txt_author);
            //imgBookAdd.setVisibility(View.VISIBLE);
            imgMovieToFav.setVisibility(View.VISIBLE);
            imgInfo.setVisibility(View.VISIBLE);
        }
    }
}
