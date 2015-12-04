package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SuggestedBookAdapter extends RecyclerView.Adapter<SuggestedBookAdapter.ViewHolder> {
    private static final String TAG = SuggestedBookAdapter.class.getSimpleName();
    private final HostActivity.BooksListner booksListner;
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();
    LayoutInflater inflater;


    public SuggestedBookAdapter(Context context, ArrayList<BookData> arrayList, HostActivity.BooksListner booksListner) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        this.booksListner = booksListner;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_user_books, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            holder.txtBookAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());

            holder.txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());

//			imageLoader.displayImage(AppConstant.HOST_IMAGE_USER_OLD + arrListFeeds.get(position).getProfilePic(), holder.imgDp, ISMStudent.options);
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrayList.get(position).getBookImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            holder.txtBookAuthor.setText(arrayList.get(position).getAuthorName());

            if (arrayList.get(position).getIsInLibrary().equals("1")) {
                holder.imgLibraryBook.setActivated(true);
            } else {
                holder.imgLibraryBook.setActivated(false);

            }
            holder.imgAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.i(TAG, "onClickAddToFav : " + position);
                    booksListner.onAddToFav(position);
                }
            });
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailsDialog bookDetailsDialog = new BookDetailsDialog(context, arrayList, position, Global.imageLoader);
                    bookDetailsDialog.show();
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
        private ImageView imgAddToFav;
        private ImageView imgLibraryBook;
        private TextView txtBookAuthor;
        private TextView txtBookName;

        public ViewHolder(View convertView) {
            super(convertView);
            imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            imgAddToFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            imgLibraryBook = (ImageView) convertView.findViewById(R.id.img_book_add);
            txtBookName = (TextView) convertView.findViewById(R.id.txt_name);
            txtBookAuthor = (TextView) convertView.findViewById(R.id.txt_author);
            imgLibraryBook.setVisibility(View.VISIBLE);
            imgAddToFav.setVisibility(View.VISIBLE);
            imgInfo.setVisibility(View.VISIBLE);
        }
    }
}
