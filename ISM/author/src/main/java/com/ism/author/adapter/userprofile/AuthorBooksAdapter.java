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
import com.ism.author.constant.WebConstants;
import com.ism.author.dialog.BookDetailsDialog;
import com.ism.author.object.Global;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

import model.AuthorBook;

/**
 * Created by c166 on 01/01/16.
 */
public class AuthorBooksAdapter extends RecyclerView.Adapter<AuthorBooksAdapter.ViewHolder> {


    private static final String TAG = AuthorBook.class.getSimpleName();
    private Context mContext;
    private ArrayList<BookData> arrListAuthorBooks = new ArrayList<BookData>();
    private LayoutInflater inflater;

    public AuthorBooksAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = inflater.inflate(R.layout.row_author_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {

            Global.imageLoader.displayImage(WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position).getFrontCoverImage(), holder.imgBookCover,
                    Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));

            holder.txtBookName.setText(arrListAuthorBooks.get(position).getBookName());

            holder.imgBookInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookDetailsDialog bookDetailsDialog = new BookDetailsDialog(mContext, arrListAuthorBooks.get(position));
                    bookDetailsDialog.show();
                }
            });


        } catch (Exception e) {
            Debug.i(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrListAuthorBooks.size();
    }


    public void addAll(ArrayList<BookData> authorBook) {
        try {
            this.copyListOfAuthorBooks = authorBook;
            this.arrListAuthorBooks.clear();
            this.arrListAuthorBooks.addAll(authorBook);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBookCover, imgBookInfo;
        TextView txtBookName, txtBookViewers;


        public ViewHolder(View itemView) {
            super(itemView);

            imgBookCover = (ImageView) itemView.findViewById(R.id.img_book_cover);
            imgBookInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            txtBookName = (TextView) itemView.findViewById(R.id.txt_book_name);
            txtBookViewers = (TextView) itemView.findViewById(R.id.txt_book_viewers);

            txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
            txtBookViewers.setTypeface(Global.myTypeFace.getRalewayRegular());
        }
    }

    public ArrayList<BookData> copyListOfAuthorBooks;

    public void filter(CharSequence charText) {

        arrListAuthorBooks.clear();
        if (charText.length() == 0) {
            arrListAuthorBooks.addAll(copyListOfAuthorBooks);
        } else {

            Debug.e(TAG, "The string is:::" + charText);
            for (BookData wp : copyListOfAuthorBooks) {
                if (Utility.containsString(wp.getBookName(), charText.toString(), false)) {
                    arrListAuthorBooks.add(wp);
                }
            }
            if (arrListAuthorBooks.size() == 0) {
            }
        }
        notifyDataSetChanged();
    }


}
