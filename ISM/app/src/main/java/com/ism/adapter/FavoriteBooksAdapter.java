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
import com.ism.dialog.BookDetailsDialog;
import com.ism.fragment.userprofile.BooksFragment;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavoriteBooksAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = FavoriteBooksAdapter.class.getSimpleName();
    private final BooksFragment fragment;
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();
    ArrayList<BookData> arrayListFilter = new ArrayList<>();
    private ArrayList<String> arrayUnFavResourceIds = new ArrayList<String>();
    LayoutInflater inflater;
    FavBookFilter favBookFilter;

    public FavoriteBooksAdapter(Context context, ArrayList<BookData> arrayList, BooksFragment fragment) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
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

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgAddToUnFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgLibraryBook = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtBookAuthor = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgInfo.setVisibility(View.VISIBLE);
            holder.imgAddToUnFav.setVisibility((View.VISIBLE));
            holder.imgLibraryBook.setVisibility((View.VISIBLE));

            holder.imgAddToUnFav.setBackgroundResource(R.drawable.img_like_red);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.txtBookAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
            Global.imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getBookImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            holder.txtBookAuthor.setText(arrayList.get(position).getAuthorName());
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myPopup(position);
                    BookDetailsDialog bookDetailsDialog = new BookDetailsDialog(context, arrayList, position, Global.imageLoader);
                    bookDetailsDialog.show();
                }
            });
            holder.imgAddToUnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.i(TAG, "onClickAddToUnFav : " + position);
                    fragment.onRemoveFromFav(position);
                    // callApiAddResourceToFav();
                }
            });
            holder.imgLibraryBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.i(TAG, "onClickAddToUnFav : " + position);
                    //if()
                   // fragment.onAddToLibrary(arrayList.get(position).getBookId());
                   // else{
                  //      fragment.onRemoveFromLibrary(arrayList.get(position).getBookId());
                  //  }
                    // callApiAddResourceToFav();
                }
            });

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
            FilterResults results = new FilterResults();
            try {

                Debug.i(TAG, "Search string : " + constraint);

                if (constraint != null) {
                    Debug.i(TAG, "Search string : " + constraint);
                    ArrayList<BookData> filterList = new ArrayList<BookData>();
                    for (int i = 0; i < arrayListFilter.size(); i++) {
                        Debug.i(TAG, "arrayListFilter.get(i).getAuthorName() : " + arrayListFilter.get(i).getBookName());
                        if (arrayListFilter.get(i).getBookName().toLowerCase().contains(constraint.toString().toLowerCase()) || arrayListFilter.get(i).getPublisherName().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                            if (arrayListFilter.get(i).getAuthorName().contains(constraint) || arrayListFilter.get(i).getBookName().contains(constraint) || arrayListFilter.get(i).getPublisherName().contains(constraint)) {
                            Debug.i(TAG, "i : " + i);
                            BookData book = new BookData();
                            book.setDescription(arrayListFilter.get(i).getDescription());
                            book.setAuthorImage(arrayListFilter.get(i).getAuthorImage());
                            book.setAuthorName(arrayListFilter.get(i).getAuthorName());
                            book.setBookId(arrayListFilter.get(i).getBookId());
                            book.setBookImage(arrayListFilter.get(i).getBookImage());
                            book.setBookName(arrayListFilter.get(i).getBookName());
                            book.setEbookLink(arrayListFilter.get(i).getEbookLink());
                            book.setPrice(arrayListFilter.get(i).getPrice());
                            book.setPublisherName(arrayListFilter.get(i).getPublisherName());
                            filterList.add(book);
                            Debug.i(TAG, "FilterList size :" + filterList.size());

                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                    Debug.i(TAG, "FilterList size :" + filterList.size());
                } else {
                    results.count = arrayListFilter.size();
                    results.values = arrayListFilter;
                    Debug.i(TAG, "Else FilterList size :" + arrayListFilter.size());
                }
                return results;
            } catch (Exception e) {
                Debug.i(TAG, "FilterResults Exceptions : " + e.getLocalizedMessage());
                return null;

            }


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {

                arrayList = (ArrayList<BookData>) results.values;
                if (arrayList.size() == 0) {
                    BooksFragment.txtFavEmpty.setVisibility(View.VISIBLE);
                    BooksFragment.listViewFavBooks.setVisibility(View.GONE);
                } else {
                    BooksFragment.txtFavEmpty.setVisibility(View.GONE);
                    BooksFragment.listViewFavBooks.setVisibility(View.VISIBLE);
                }
                Debug.i(TAG, "publishResults arrayList :  " + arrayList.size());
                notifyDataSetChanged();
            } catch (Exception e) {
                Debug.i(TAG, "publishResults on Exception :  " + e.getLocalizedMessage());
            }
        }

    }

    class ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgLibraryBook;
        private ImageView imgAddToUnFav;
        private TextView txtBookAuthor;
        private TextView txtBookName;


    }
}
