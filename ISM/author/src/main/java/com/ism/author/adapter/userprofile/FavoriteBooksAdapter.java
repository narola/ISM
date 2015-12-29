package com.ism.author.adapter.userprofile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.BookDetailsDialog;
import com.ism.author.object.Global;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavoriteBooksAdapter extends RecyclerView.Adapter<FavoriteBooksAdapter.ViewHolder> {

    private static final String TAG = FavoriteBooksAdapter.class.getSimpleName();


    private final AuthorHostActivity.BooksListner booksListner;
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();
    int total = 0;

    public FavoriteBooksAdapter(Context context, ArrayList<BookData> arrayList, AuthorHostActivity.BooksListner booksListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.booksListner = booksListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_desk_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.txtBookAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());
            holder.txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
            Global.imageLoader.displayImage(WebConstants.HOST_IMAGES + arrayList.get(position).getFrontCoverImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.txtBookName.setText(arrayList.get(position).getBookName());
            if (arrayList.get(position).getIsInLibrary().equals("1")) {
                holder.imgLibraryBook.setActivated(true);
            } else {
                holder.imgLibraryBook.setActivated(false);

            }
            Debug.i(TAG, "view called : " + position + "Total position : " + total++);
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
            Debug.i(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
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


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgLibraryBook;
        private ImageView imgAddToUnFav;
        private TextView txtBookAuthor;
        private TextView txtBookName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgBook = (ImageView) itemView.findViewById(R.id.img_pic);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            imgAddToUnFav = (ImageView) itemView.findViewById(R.id.img_add_fav);
            imgLibraryBook = (ImageView) itemView.findViewById(R.id.img_book_add);
            txtBookName = (TextView) itemView.findViewById(R.id.txt_name);
            txtBookAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            imgInfo.setVisibility(View.VISIBLE);
            imgAddToUnFav.setVisibility((View.VISIBLE));
            imgLibraryBook.setVisibility((View.VISIBLE));
            imgAddToUnFav.setBackgroundResource(R.drawable.img_like_red);
        }
    }
}
