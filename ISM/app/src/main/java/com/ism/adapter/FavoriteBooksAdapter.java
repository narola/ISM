package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.dialog.BookDetailsDialog;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavoriteBooksAdapter extends BaseAdapter {
    private static final String TAG = FavoriteBooksAdapter.class.getSimpleName();
    private final HostActivity.BooksListner booksListner;
    private HostActivity.ScrollListener scrollListener;
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    int total = 0;

    public FavoriteBooksAdapter(Context context, ArrayList<BookData> arrayList, HostActivity.BooksListner booksListner, HostActivity.ScrollListener scrollListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.scrollListener = scrollListener;
        inflater = LayoutInflater.from(context);
        this.booksListner = booksListner;
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
        final ViewHolder holder;
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
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getBookImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            if (arrayList.get(position).getIsInLibrary().equals("1")) {
                holder.imgLibraryBook.setActivated(true);
            } else {
                holder.imgLibraryBook.setActivated(false);

            }
            Debug.i(TAG, "view called : " + position + "Total position : " + total++);
            if (position == 0) {
                scrollListener.isFirstPosition();
            }
            if (arrayList.size() - 1 == position) {
                scrollListener.isLastPosition();
            }
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
                    booksListner.onRemoveFromFav(position);
                    // callApiAddResourceToFav();
                }
            });
            holder.imgLibraryBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.i(TAG, "onClickAddToLibrary : " + position);
                    if (arrayList.get(position).getIsInLibrary().equals("1")) {
                        arrayList.get(position).setIsInLibrary("0");
                        booksListner.onRemoveFromLibrary(arrayList.get(position).getBookId());
                        holder.imgLibraryBook.setActivated(false);
                    } else {
                        holder.imgLibraryBook.setActivated(true);
                        arrayList.get(position).setIsInLibrary("1");
                        booksListner.onAddToLibrary(arrayList.get(position).getBookId());
                    }
                }
            });

        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
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
