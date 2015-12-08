package com.ism.author.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.Utility.Debug;
import com.ism.author.activtiy.AuthorHostActivity;
import com.ism.author.ws.model.BookData;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class BooksAdapter extends  RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private static final String TAG = BooksAdapter.class.getSimpleName();
    private final AuthorHostActivity.BooksListner booksListner;
    Context context;
    ArrayList<BookData> arrayList = new ArrayList<>();

    public BooksAdapter(Context context, ArrayList<BookData> arrayList, AuthorHostActivity.BooksListner booksListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.booksListner = booksListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.row_books, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            //Global.imageLoader.displayImage(WebConstants.HOST_IMAGES + arrayList.get(position).getFrontCoverImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));

        } catch (Exception e) {
            Debug.i(TAG,"onBindViewHolder Exception : " + e.getLocalizedMessage());
        }

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 25;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgLibraryBook;
        private ImageView imgAddToUnFav;
        private RelativeLayout llMain;
        private TextView txtBookName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgBook = (ImageView) itemView.findViewById(R.id.img_pic);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_book_info);
            imgAddToUnFav = (ImageView) itemView.findViewById(R.id.img_add_fav);
            imgLibraryBook = (ImageView) itemView.findViewById(R.id.img_book_add);
            txtBookName = (TextView) itemView.findViewById(R.id.txt_name);
            llMain= (RelativeLayout) itemView.findViewById(R.id.ll_main);
        }
    }
}
