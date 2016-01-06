package com.ism.adapter.deskAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.AllBooks;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private static final String TAG = BooksAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<AllBooks> arrListBooks = new ArrayList<AllBooks>();
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

                setBookImage(holder.imgBookOne, position);
                setBookImage(holder.imgBookTwo, position + 1);
                setBookImage(holder.imgBookThree, position + 2);
                setBookImage(holder.imgBookFour, position + 3);

            } else {

                setBookImage(holder.imgBookOne, position * 4);
                setBookImage(holder.imgBookTwo, (position * 4) + 1);
                setBookImage(holder.imgBookThree, (position * 4) + 2);
                setBookImage(holder.imgBookFour, (position * 4) + 3);
            }

        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public int getItemCount() {

        return (int) Math.ceil(((float) arrListBooks.size() / 4));

    }

    public void addAll(ArrayList<AllBooks> allBooks) {
        try {
            this.arrListBooks.clear();
            this.arrListBooks.addAll(allBooks);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }


    private void setBookImage(ImageView imageView, int position) {

        if (position < arrListBooks.size()) {

            Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER_OLD + arrListBooks.get(position).getFrontCoverImage(), imageView,
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
