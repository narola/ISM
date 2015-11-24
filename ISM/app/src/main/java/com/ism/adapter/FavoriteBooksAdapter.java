package com.ism.adapter;

import android.content.Context;
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
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.Book;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavoriteBooksAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = FavoriteBooksAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<Book> arrayList = new ArrayList<>();
    ArrayList<Book> arrayListFilter = new ArrayList<>();

    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    FavBookFilter favBookFilter;

    public FavoriteBooksAdapter(Context context, ArrayList<Book> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user_books, null);
            holder = new ViewHolder();

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgBookLike = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtBookAuthor = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgInfo.setVisibility(View.VISIBLE);
            holder.imgBookLike.setVisibility((View.VISIBLE));
            holder.imgBookLike.setBackgroundResource(R.drawable.img_like_red);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.txtBookAuthor.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtBookName.setTypeface(myTypeFace.getRalewayRegular());
            imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getBookImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            holder.txtBookAuthor.setText(arrayList.get(position).getAuthorName());
            // if(arrayList.get(position).ge)


        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (favBookFilter == null) {
            favBookFilter = new FavBookFilter();
        }
        return favBookFilter;
    }

    class FavBookFilter extends Filter {

        // Invoked in a worker thread to filter the data according to the
        // constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            try {
                FilterResults results = new FilterResults();
                Debug.i(TAG, "Search string : " + constraint);
                if (constraint.length() > 0) {

                    ArrayList<Book> filterList = new ArrayList<Book>();


                    for (int i = 0; i < arrayListFilter.size(); i++) {

                        if (arrayListFilter.get(i).getAuthorName().contains(constraint) || arrayListFilter.get(i).getBookName().contains(constraint) || arrayListFilter.get(i).getPublisherName().contains(constraint)) {
                            filterList.add(arrayListFilter.get(i));

                        }
                    }
                    results.count = filterList.size();

                    results.values = filterList;
                } else

                {

                    results.count = arrayListFilter.size();

                    results.values = arrayListFilter;

                }
                return results;
            } catch (Exception e) {
                Debug.i(TAG, "FilterResults Exceptions : " + e.getLocalizedMessage());
                return null;

            }


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList = (ArrayList<Book>) results.values;
            notifyDataSetChanged();
        }

    }

    class ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgBookLike;
        private ImageView imgBookAdd;
        private TextView txtBookAuthor;
        private TextView txtBookName;


    }
}
