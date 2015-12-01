package com.ism.author.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.BookDetailsDialog;
import com.ism.author.fragment.mydesk.MyDeskBooksFragment;
import com.ism.author.object.Global;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class SuggestedBookAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = SuggestedBookAdapter.class.getSimpleName();
    private final AuthorHostActivity.AddToFavouriteListner addToFavouriteListner;
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    SuggestedBookFilter suggestedBookFilter;
    ArrayList<BookData> arrayListFilter = new ArrayList<>();
    private ArrayList<String> arrayFavResourceIds = new ArrayList<String>();
    private MyDeskBooksFragment myDeskBooksFragment;

    public SuggestedBookAdapter(Context context, ArrayList<BookData> arrayList,AuthorHostActivity.AddToFavouriteListner addToFavouriteListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
        this.addToFavouriteListner=addToFavouriteListner;
        inflater = LayoutInflater.from(context);
        myDeskBooksFragment=new MyDeskBooksFragment().newInstance();
    }
    // public ArrayList<String> getUnFavResourceIds(){
    //      return arrayFavResourceIds;
    //  }
//    public void setFavResourceIds( ArrayList<String> arrayFavResourceIds){
//        this.arrayFavResourceIds=arrayFavResourceIds;
//    }

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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_desk_book, null);
            holder = new ViewHolder();

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgAddToFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_name);

            holder.imgBookAdd.setVisibility(View.VISIBLE);
            holder.imgAddToFav.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            holder.txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
            arrayList.get(position).setIsLibrary("0");
//			imageLoader.displayImage(AppConstant.URL_USERS_IMAGE_PATH + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
            Global.imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getBookImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            if (arrayList.get(position).getIsLibrary().equals("1")) {
                holder.imgBookAdd.setActivated(true);
            }else {
                holder.imgBookAdd.setActivated(false);

            }

            holder.imgAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.i(TAG, "onClickAddToFav : " + position);
                    // arrayFavResourceIds.add(arrayList.get(position).getBookId());
                    addToFavouriteListner.onAddToFav(position);
                }
            });
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myPopup(position);
                    BookDetailsDialog bookDetailsDialog = new BookDetailsDialog(context, arrayList, position, Global.imageLoader);
                    bookDetailsDialog.show();
                }
            });
            holder.imgBookAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayList.get(position).getIsLibrary().equals("1")) {
                        arrayList.get(position).setIsLibrary("0");
                        addToFavouriteListner.onRemoveFromLibrary(arrayList.get(position).getBookId());
                        holder.imgBookAdd.setActivated(false);
                    } else {
                        holder.imgBookAdd.setActivated(true);
                        arrayList.get(position).setIsLibrary("1");
                        addToFavouriteListner.onAddToLibrary(arrayList.get(position).getBookId());
                    }
                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (suggestedBookFilter == null) {
            suggestedBookFilter = new SuggestedBookFilter();
        }
        return suggestedBookFilter;
    }


    class SuggestedBookFilter extends Filter {

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

                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
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
            try {

                arrayList = (ArrayList<BookData>) results.values;
                addToFavouriteListner.onSearchSuggested(arrayList);
                notifyDataSetChanged();
            } catch (Exception e) {
                Debug.i(TAG, "publishResults on Exception :  " + e.getLocalizedMessage());
            }
        }

    }

    public class ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgAddToFav;
        private ImageView imgBookAdd;
        private TextView txtBookAuthor;
        private TextView txtBookName;


    }
}
