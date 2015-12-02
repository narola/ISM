package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.constant.WebConstants;
import com.ism.dialog.PastimesDetailsDialog;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.PastimeData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavouritePastTimeAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = FavouritePastTimeAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    private final HostActivity.ManageResourcesListner manageResourcesListner;
    Context context;
    ArrayList<PastimeData> arrayList = new ArrayList<>();
    ArrayList<PastimeData> arrayListFilter = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    PastimesFilter pastimesFilter;

    public FavouritePastTimeAdapter(Context context, ArrayList<PastimeData> arrayList, HostActivity.ManageResourcesListner manageResourcesListner) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
        imageLoader = ImageLoader.getInstance();
        this.manageResourcesListner = manageResourcesListner;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
        myTypeFace = new MyTypeFace(context);
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user_books, null);
            holder = new ViewHolder();
            holder.imgPastime = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.txtPastimeName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgRemoveFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgRemoveFav.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);
            holder.imgRemoveFav.setBackgroundResource(R.drawable.img_like_red);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.txtPastimeName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtName.setText("");
            holder.txtPastimeName.setText(arrayList.get(position).getPastimeName());
            imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getPastimeImage(), holder.imgPastime, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));
            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    myPopup(position);
                    PastimesDetailsDialog pastimesDetailsDialog = new PastimesDetailsDialog(context, arrayList, position, imageLoader);
                    pastimesDetailsDialog.show();
                }
            });
            holder.imgRemoveFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manageResourcesListner.onRemoveFromFav(position);
                    Debug.i(TAG, "onClickAddToFav : " + position);

                }
            });
        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (pastimesFilter == null) {
            pastimesFilter = new PastimesFilter();
        }
        return pastimesFilter;
    }

    class PastimesFilter extends Filter {

        // Invoked in a worker thread to filter the data according to the
        // constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            try {

                Debug.i(TAG, "Search string : " + constraint);

                if (constraint != null) {
                    Debug.i(TAG, "Search string : " + constraint);
                   // Debug.i(TAG, "Initailly list size  : " + arrayListFilter.size());
                    ArrayList<PastimeData> filterList = new ArrayList<PastimeData>();
                    for (int i = 0; i < arrayListFilter.size(); i++) {
                        if (arrayListFilter.get(i).getPastimeName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            Debug.i(TAG, "i : " + i);
                            PastimeData data = new PastimeData();
                            data.setPastimeId(arrayListFilter.get(i).getPastimeId());
                            data.setPastimeImage(arrayListFilter.get(i).getPastimeImage());
                            data.setPastimeName(arrayListFilter.get(i).getPastimeName());
                            filterList.add(data);

                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = arrayListFilter.size();
                    results.values = arrayListFilter;
                }
                Debug.i(TAG, "returns list size  : " + results.count);
                return results;
            } catch (Exception e) {
                Debug.i(TAG, "FilterResults Exceptions : " + e.getLocalizedMessage());
                return null;

            }


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {

                arrayList = (ArrayList<PastimeData>) results.values;
                manageResourcesListner.onSearchFav(arrayList);
                notifyDataSetChanged();
            } catch (Exception e) {
                Debug.i(TAG, "publishResults on Exception :  " + e.getLocalizedMessage());
            }
        }

    }


    public class ViewHolder {

        private ImageView imgPastime;
        private TextView txtPastimeName;
        private TextView txtName;
        private ImageView imgInfo;
        public ImageView imgRemoveFav;
    }
}
