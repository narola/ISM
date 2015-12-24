package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.Utility.Utility;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.model.AuthorBook;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private static final String TAG = BooksAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<AuthorBook> arrListAuthorBooks = new ArrayList<AuthorBook>();
    private LayoutInflater inflater;


    public BooksAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = inflater.inflate(R.layout.row_books, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            if (position == 0) {

                Global.imageLoader.displayImage(WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position).getBookImage(), holder.imgBookOne,
                        Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
                Global.imageLoader.displayImage(WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position + 1).getBookImage(), holder.imgBookTwo,
                        Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
                Global.imageLoader.displayImage(WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position + 2).getBookImage(), holder.imgBookThree,
                        Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
                Global.imageLoader.displayImage(WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position + 3).getBookImage(), holder.imgBookFour,
                        Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));


                setBookImage(holder.imgBookOne, position , WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position).getBookImage());
                setBookImage(holder.imgBookTwo, position+ 1, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position+ 1).getBookImage());
                setBookImage(holder.imgBookOne, position + 2, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get((position * 4) + 2).getBookImage());
                setBookImage(holder.imgBookOne, (position * 4) + 3, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get((position * 4) + 3).getBookImage());

            } else {

                setBookImage(holder.imgBookOne, position * 4, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position * 4).getBookImage());
                setBookImage(holder.imgBookTwo, (position * 4) + 1, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get((position * 4) + 1).getBookImage());
                setBookImage(holder.imgBookOne, (position * 4) + 2, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get((position * 4) + 2).getBookImage());
                setBookImage(holder.imgBookOne, (position * 4) + 3, WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get((position * 4) + 3).getBookImage());
            }

        } catch (Exception e) {
            Debug.i(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {

        return (int) Math.ceil(arrListAuthorBooks.size() / 4);

    }


    private void setBookImage(ImageView imageView, int position, String imageUrl) {

        if (position <= arrListAuthorBooks.size()) {
            Global.imageLoader.displayImage(WebConstants.BOOKS_IMAGES + arrListAuthorBooks.get(position + 3).getBookImage(), imageView,
                    Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imgBookOne, imgBookTwo, imgBookThree, imgBookFour;

        public ViewHolder(View itemView) {
            super(itemView);

            imgBookOne = (ImageView) itemView.findViewById(R.id.img_book_one);
            imgBookTwo = (ImageView) itemView.findViewById(R.id.img_book_two);
            imgBookThree = (ImageView) itemView.findViewById(R.id.img_book_three);
            imgBookFour = (ImageView) itemView.findViewById(R.id.img_book_four);

        }
    }
}
