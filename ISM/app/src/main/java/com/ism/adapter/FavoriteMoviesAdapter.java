package com.ism.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.dialog.MovieDetailsDialog;
import com.ism.fragment.userprofile.MoviesFragment;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.MovieData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 20/11/15.
 */
public class FavoriteMoviesAdapter extends BaseAdapter implements Filterable{
    private static final String TAG = FavoriteMoviesAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<MovieData> arrayList = new ArrayList<>();
    ArrayList<MovieData> arrayListFilter = new ArrayList<>();
    MovieFilter movieFilter;
    LayoutInflater inflater;
    MyTypeFace myTypeFace;

    public FavoriteMoviesAdapter(Context context, ArrayList<MovieData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter=arrayList;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user_books, null);
            holder = new ViewHolder();

            holder.imgMovie = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.txtMovieName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtYear = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgLike = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgLike.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.txtMovieName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtYear.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtMovieName.setGravity(Gravity.LEFT);
            holder.txtYear.setGravity(Gravity.LEFT);
            holder.imgLike.setBackgroundResource(R.drawable.img_like_red);
            imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD +arrayList.get(position).getMovieImage(), holder.imgMovie, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtMovieName.setText(arrayList.get(position).getMovieName());
            holder.txtYear.setText(arrayList.get(position).getMovieGenre());
            // if(arrayList.get(position).ge)
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myPopup(position);
                    MovieDetailsDialog movieDetailsDialog = new MovieDetailsDialog(context, arrayList, position, imageLoader);
                    movieDetailsDialog.show();
                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(movieFilter==null){
            movieFilter=new MovieFilter();
        }
        return movieFilter;
    }
    class MovieFilter extends Filter {

        // Invoked in a worker thread to filter the data according to the
        // constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            try {

                Debug.i(TAG, "Search string : " + constraint);
                Debug.i(TAG, "Initially Size of list : " + arrayListFilter.size());
                if (constraint!= null) {
                    Debug.i(TAG, "Search string : " + constraint);
                    ArrayList<MovieData> filterList = new ArrayList<MovieData>();
                    for (int i = 0; i < arrayListFilter.size(); i++) {
                        if (arrayListFilter.get(i).getMovieName().toLowerCase().contains(constraint.toString().toLowerCase())  || arrayListFilter.get(i).getScreenplay().toLowerCase().contains(constraint.toString().toLowerCase()) ) {

//                            if (arrayListFilter.get(i).getAuthorName().contains(constraint) || arrayListFilter.get(i).getBookName().contains(constraint) || arrayListFilter.get(i).getPublisherName().contains(constraint)) {
                            Debug.i(TAG, "i : " + i);
                            MovieData movieData = new MovieData();
                            movieData.setDescription(arrayListFilter.get(i).getDescription());
                            movieData.setMovieImage(arrayListFilter.get(i).getMovieImage());
                            movieData.setMovieGenre(arrayListFilter.get(i).getMovieGenre());
                            movieData.setMovieId(arrayListFilter.get(i).getMovieId());
                            movieData.setScreenplay(arrayListFilter.get(i).getScreenplay());
                            movieData.setMovieName(arrayListFilter.get(i).getMovieName());
                            filterList.add(movieData);

                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = arrayListFilter.size();
                    results.values = arrayListFilter;
                }
                Debug.i(TAG, "Size of list : " + results.count );
                return results;
            } catch (Exception e) {
                Debug.i(TAG, "FilterResults Exceptions : " + e.getLocalizedMessage());
                return null;

            }


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {

                arrayList = (ArrayList<MovieData>) results.values;
                if(arrayList.size()==0)
                {
                    MoviesFragment.txtFavEmpty.setVisibility(View.VISIBLE);
                    MoviesFragment.listViewFav.setVisibility(View.GONE);
                }
                else{
                    MoviesFragment.txtFavEmpty.setVisibility(View.GONE);
                    MoviesFragment.listViewFav.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            } catch (Exception e) {
                Debug.i(TAG, "publishResults on Exception :  " + e.getLocalizedMessage());
            }
        }

    }
    public class ViewHolder {

        private ImageView imgMovie;
        private TextView txtMovieName;
        private TextView txtYear;
        public ImageView imgLike;
        public ImageView imgInfo;
    }
}
