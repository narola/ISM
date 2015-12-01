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
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.BookDetailsDialog;
import com.ism.author.object.Global;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 30/11/15.
 */
public class DeskBookAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = DeskBookAdapter.class.getSimpleName();
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
  //  AddToFavouriteListner addToFavouriteListner;
    SuggestedBookFilter suggestedBookFilter;
    ArrayList<BookData> arrayListFilter = new ArrayList<>();
    private ArrayList<String> arrayFavResourceIds=new ArrayList<String>();


    public DeskBookAdapter(Context context, ArrayList<BookData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter=arrayList;
        inflater = LayoutInflater.from(context);
      //  this.addToFavouriteListner = favouriteBooksListner;
    }
    public ArrayList<String> getUnFavResourceIds(){
        return arrayFavResourceIds;
    }
//    public void setFavResourceIds( ArrayList<String> arrayFavResourceIds){
//        this.arrayFavResourceIds=arrayFavResourceIds;
//    }
    public interface AddToFavouriteListner {
        public void onAddToFav(int position);
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
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

//			imageLoader.displayImage(AppConstant.URL_USERS_IMAGE_PATH + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
            Global.imageLoader.displayImage(WebConstants.URL + arrayList.get(position).getBookImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            Debug.i(TAG,"book name : " +arrayList.get(position).getBookName());
         //   holder.txtBookAuthor.setText(arrayList.get(position).getAuthorName());
            // if(arrayList.get(position).ge)
//
            holder.imgAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.i(TAG, "onClickAddToFav : " + position);
                    arrayFavResourceIds.add(arrayList.get(position).getBookId());
                    // callApiAddResourceToFav();
                }
            });
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myPopup(position);
                    BookDetailsDialog bookDetailsDialog=new BookDetailsDialog(context,arrayList,position,Global.imageLoader);
                    bookDetailsDialog.show();
                }
            });


        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

//    public PopupWindow myPopup(int position) {
//
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View view = inflater.inflate(R.layout.dailog_book_details, null);
//
//        final PopupWindow popupWindow = new PopupWindow(view,
//                400,
//                500, true);
//
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        // popupWindow.setTouchInterceptor(customPopUpTouchListenr);
//
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        // TextView txtDetails = (TextView) view.findViewById(R.id.txt_about_me_details);
//
//        final TextView txtBookDetails = (TextView) view
//                .findViewById(R.id.txt_book_details);
//        TextView txtDone = (TextView) view
//                .findViewById(R.id.txt_done);
//        TextView txtBook = (TextView) view
//                .findViewById(R.id.txt_book);
//        TextView txtBookName = (TextView) view
//                .findViewById(R.id.txt_book_name);
//        TextView txtPublisher = (TextView) view
//                .findViewById(R.id.txt_publisher);
//        TextView txtPublisherName = (TextView) view
//                .findViewById(R.id.txt_publisher_name);
//        TextView txtAuthor = (TextView) view
//                .findViewById(R.id.txt_author);
//        TextView txtAuthorName = (TextView) view
//                .findViewById(R.id.txt_author_name);
//        TextView txtPrice = (TextView) view
//                .findViewById(R.id.txt_price);
//        TextView txtPriceValue = (TextView) view
//                .findViewById(R.id.txt_price_value);
//        TextView txtDesc = (TextView) view
//                .findViewById(R.id.txt_desc);
//        TextView txtDescDetails = (TextView) view
//                .findViewById(R.id.txt_desc_details);
//        TextView txtEbook = (TextView) view
//                .findViewById(R.id.txt_ebook_for_link);
//        TextView txtEbookLink = (TextView) view
//                .findViewById(R.id.txt_ebook_link);
//
//        txtAuthor.setTypeface(myTypeFace.getRalewayRegular());
//        txtAuthorName.setTypeface(myTypeFace.getRalewayRegular());
//        txtBook.setTypeface(myTypeFace.getRalewayRegular());
//        txtBookDetails.setTypeface(myTypeFace.getRalewayRegular());
//        txtBookName.setTypeface(myTypeFace.getRalewayRegular());
//        txtDesc.setTypeface(myTypeFace.getRalewayRegular());
//        txtDescDetails.setTypeface(myTypeFace.getRalewayRegular());
//        txtDone.setTypeface(myTypeFace.getRalewayRegular());
//        txtPublisher.setTypeface(myTypeFace.getRalewayRegular());
//        txtPublisherName.setTypeface(myTypeFace.getRalewayRegular());
//        txtPrice.setTypeface(myTypeFace.getRalewayRegular());
//        txtPriceValue.setTypeface(myTypeFace.getRalewayRegular());
//        txtEbook.setTypeface(myTypeFace.getRalewayRegular());
//        txtEbookLink.setTypeface(myTypeFace.getRalewayRegular());
//
//
//        txtAuthorName.setText(arrayList.get(position).getAuthorName());
//        txtPublisherName.setText(arrayList.get(position).getPublisherName());
//        txtPriceValue.setText(arrayList.get(position).getPrice());
//        txtDescDetails.setText(arrayList.get(position).getDescription());
//        txtBookName.setText(arrayList.get(position).getBookName());
//        txtEbookLink.setText(arrayList.get(position).getEbookLink());
//        //txtAuthor.setText(arrayList.get(position).getAuthorName());
//
//
//        txtDone.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                popupWindow.dismiss();
//
//            }
//        });
//        popupWindow.setFocusable(true);
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setContentView(view);
//        return popupWindow;
//
//    }


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

                if (constraint!= null) {
                    Debug.i(TAG, "Search string : " + constraint);
                    ArrayList<BookData> filterList = new ArrayList<BookData>();
                    for (int i = 0; i < arrayListFilter.size(); i++) {
                        if (arrayListFilter.get(i).getBookName().toLowerCase().contains(constraint.toString().toLowerCase()) || arrayListFilter.get(i).getPublisherName().toLowerCase().contains(constraint.toString().toLowerCase()) ) {

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
                if(arrayList.size()==0)
                {
                    //BooksFragment.txtSuggestedEmpty.setVisibility(View.VISIBLE);
                    //BooksFragment.listViewSuggestedBooks.setVisibility(View.GONE);
                }
                else{
                   // BooksFragment.txtSuggestedEmpty.setVisibility(View.GONE);
                   // BooksFragment.listViewSuggestedBooks.setVisibility(View.VISIBLE);
                }
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
