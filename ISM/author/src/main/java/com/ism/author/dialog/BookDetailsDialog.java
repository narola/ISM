package com.ism.author.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.author.ISMAuthor;
import com.ism.author.R;
import com.ism.author.Utility.Utility;
import com.ism.author.constant.WebConstants;
import com.ism.author.object.Global;
import com.ism.author.ws.model.BookData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by c162 on 30/11/15.
 */
public class BookDetailsDialog extends Dialog implements View.OnClickListener {
    private final int position;
    private Context mContext;
    private TextView tvDialogClose;
    private ArrayList<BookData> arrayList;
    private TextView txtDone;

    public BookDetailsDialog(Context mContext, ArrayList<BookData>
            arrayList, int position, ImageLoader imageLoader) {
        super(mContext);

        this.mContext = mContext;
        this.arrayList = arrayList;
        this.position = position;
        Window w = getWindow();
        getWindow().getAttributes().windowAnimations = R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        txtAuthor.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtAuthorName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBook.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBookDetails.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtBookName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtDesc.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtDescDetails.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtDone.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtPublisher.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtPublisherName.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtPrice.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtPriceValue.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEbook.setTypeface(Global.myTypeFace.getRalewayRegular());
        txtEbookLink.setTypeface(Global.myTypeFace.getRalewayRegular());


        txtAuthorName.setText(arrayList.get(position).getAuthorName());
        txtPublisherName.setText(arrayList.get(position).getPublisherName());
        txtPriceValue.setText(arrayList.get(position).getPrice());
        txtDescDetails.setText(arrayList.get(position).getDescription());
        txtBookName.setText(arrayList.get(position).getBookName());
        txtEbookLink.setText(arrayList.get(position).getEbookLink());
        Global.imageLoader.displayImage(WebConstants.URL + arrayList.get(position).getBookImage(), imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
        Global.imageLoader.displayImage(WebConstants.URL + arrayList.get(position).getAuthorImage(), imgAuthor, ISMAuthor.options);
        txtDone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == txtDone) {
            dismiss();
        }
    }
}
