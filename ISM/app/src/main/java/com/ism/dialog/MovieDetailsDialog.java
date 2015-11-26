package com.ism.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.constant.WebConstants;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.ws.model.MovieData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 24/10/15.
 */
public class MovieDetailsDialog extends Dialog implements View.OnClickListener {
    ImageLoader imageLoader;
    private final int position;
    private Context mContext;
    private TextView tvDialogClose;
    private ArrayList<MovieData> arrayList;
    MyTypeFace myTypeFace;
    private TextView txtDone;

    public MovieDetailsDialog(Context mContext, ArrayList<MovieData>
            arrayList, int position, ImageLoader imageLoader) {
        super(mContext);

        this.mContext = mContext;
        this.arrayList = arrayList;
        this.position = position;
        myTypeFace = new MyTypeFace(mContext);
        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.imageLoader = imageLoader;
        setContentView(R.layout.dailog_book_details);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();

    }

    private void initializeDialog() {
        //imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        TextView txtBookDetails = (TextView) findViewById(R.id.txt_book_details);
        txtDone = (TextView) findViewById(R.id.txt_done);
        TextView txtBook = (TextView) findViewById(R.id.txt_book);
        TextView txtBookName = (TextView) findViewById(R.id.txt_book_name);
        TextView txtPublisher = (TextView) findViewById(R.id.txt_publisher);
        TextView txtPublisherName = (TextView) findViewById(R.id.txt_publisher_name);
        TextView txtAuthor = (TextView) findViewById(R.id.txt_author);
        TextView txtAuthorName = (TextView) findViewById(R.id.txt_author_name);
        TextView txtPrice = (TextView) findViewById(R.id.txt_price);
        TextView txtPriceValue = (TextView) findViewById(R.id.txt_price_value);
        TextView txtDesc = (TextView) findViewById(R.id.txt_desc);
        TextView txtDescDetails = (TextView) findViewById(R.id.txt_desc_details);
        TextView txtEbook = (TextView) findViewById(R.id.txt_ebook_for_link);
        TextView txtEbookLink = (TextView) findViewById(R.id.txt_ebook_link);
        ImageView imgBook = (ImageView) findViewById(R.id.img_book_image);
        ImageView imgAuthor = (ImageView) findViewById(R.id.img_author);
        txtAuthor.setTypeface(myTypeFace.getRalewayRegular());
        txtAuthorName.setTypeface(myTypeFace.getRalewayRegular());
        txtBook.setTypeface(myTypeFace.getRalewayRegular());
        txtBookDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtBookName.setTypeface(myTypeFace.getRalewayRegular());
        txtDesc.setTypeface(myTypeFace.getRalewayRegular());
        txtDescDetails.setTypeface(myTypeFace.getRalewayRegular());
        txtDone.setTypeface(myTypeFace.getRalewayRegular());
        txtPublisher.setTypeface(myTypeFace.getRalewayRegular());
        txtPublisherName.setTypeface(myTypeFace.getRalewayRegular());
        txtPrice.setTypeface(myTypeFace.getRalewayRegular());
        txtPriceValue.setTypeface(myTypeFace.getRalewayRegular());
        txtEbook.setTypeface(myTypeFace.getRalewayRegular());
        txtEbookLink.setTypeface(myTypeFace.getRalewayRegular());
        txtBookDetails.setText(R.string.strMovieDetails);
        imgAuthor.setVisibility(View.GONE);
        txtAuthor.setText(R.string.strScreenPlay);
        txtAuthorName.setText(arrayList.get(position).getScreenplay());
        txtPublisherName.setVisibility(View.GONE);
        txtPriceValue.setVisibility(View.GONE);
        txtPublisher.setVisibility(View.GONE);
        txtPrice.setVisibility(View.GONE);
        txtBook.setText(R.string.strMovie);
        txtDescDetails.setText(arrayList.get(position).getDescription());
        txtBookName.setText(arrayList.get(position).getMovieName());
        txtEbook.setText(R.string.strMovieGenre);
        txtEbookLink.setText(arrayList.get(position).getMovieGenre());
        imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getMovieImage(), imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
        txtDone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == txtDone) {
            dismiss();
        }
    }
}
