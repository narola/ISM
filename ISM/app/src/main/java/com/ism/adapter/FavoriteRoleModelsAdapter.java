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
import com.ism.constant.WebConstants;
import com.ism.dialog.RoleModelsDetailsDialog;
import com.ism.fragment.userprofile.RoleModelFragment;
import com.ism.object.MyTypeFace;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.ws.model.RolemodelData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by c162 on 19/11/15.
 */
public class FavoriteRoleModelsAdapter extends BaseAdapter implements Filterable {
    private static final String TAG = FavoriteRoleModelsAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    Context context;
    ArrayList<RolemodelData> arrayList = new ArrayList<>();
    LayoutInflater inflater;
    MyTypeFace myTypeFace;
    RoleModelsFilter roleModelsFilter;
    ArrayList<RolemodelData> arrayListFilter = new ArrayList<>();
    public FavoriteRoleModelsAdapter(Context context, ArrayList<RolemodelData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListFilter = arrayList;
        imageLoader = ImageLoader.getInstance();
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

            holder.imgBook = (ImageView) convertView.findViewById(R.id.img_pic);
            holder.imgInfo = (ImageView) convertView.findViewById(R.id.img_book_info);
            holder.imgAddToFav = (ImageView) convertView.findViewById(R.id.img_add_fav);
            holder.imgBookAdd = (ImageView) convertView.findViewById(R.id.img_book_add);
            holder.txtBookName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtOrganization = (TextView) convertView.findViewById(R.id.txt_author);
            holder.imgAddToFav.setVisibility(View.VISIBLE);
            holder.imgAddToFav.setBackgroundResource(R.drawable.img_like_red);
            holder.imgInfo.setVisibility(View.VISIBLE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            holder.txtOrganization.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtBookName.setTypeface(myTypeFace.getRalewayRegular());
            holder.txtBookName.setText(arrayList.get(position).getModelName());
            holder.txtOrganization.setText(arrayList.get(position).getOrganization());
            imageLoader.displayImage(WebConstants.URL_HOST_202 + arrayList.get(position).getModelImage(), holder.imgBook, Utility.getDisplayImageOption(R.drawable.img_no_cover_available, R.drawable.img_no_cover_available));

            holder.imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoleModelsDetailsDialog roleModelsDetailsDialog = new RoleModelsDetailsDialog(context, arrayList, position, imageLoader);
                    roleModelsDetailsDialog.show();
                }
            });
        } catch (Exception e) {
            Debug.i(TAG, "getView Exception : " + e.getLocalizedMessage());
        }

        return convertView;
    }
    @Override
    public Filter getFilter() {
        if (roleModelsFilter == null) {
            roleModelsFilter = new RoleModelsFilter();
        }
        return roleModelsFilter;
    }
    class RoleModelsFilter extends Filter {

        // Invoked in a worker thread to filter the data according to the
        // constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            try {

                Debug.i(TAG, "Search string : " + constraint);

                if (constraint != null) {
                    Debug.i(TAG, "Search string : " + constraint);
                    ArrayList<RolemodelData> filterList = new ArrayList<RolemodelData>();
                    for (int i = 0; i < arrayListFilter.size(); i++) {
                        if (arrayListFilter.get(i).getModelName().toLowerCase().contains(constraint.toString().toLowerCase()) || arrayListFilter.get(i).getOrganization().toLowerCase().contains(constraint.toString().toLowerCase()) ) {
                            Debug.i(TAG, "i : " + i);
                            RolemodelData data = new RolemodelData();
                            data.setDescription(arrayListFilter.get(i).getDescription());
                            data.setModelImage(arrayListFilter.get(i).getModelImage());
                            data.setAchievements(arrayListFilter.get(i).getAchievements());
                            data.setRolemodelId(arrayListFilter.get(i).getRolemodelId());
                            data.setActivities(arrayListFilter.get(i).getActivities());
                            data.setModelName(arrayListFilter.get(i).getModelName());
                            data.setBirthdate(arrayListFilter.get(i).getBirthdate());
                            data.setEducation(arrayListFilter.get(i).getEducation());
                            data.setOrganization(arrayListFilter.get(i).getOrganization());
                            data.setQuotes(arrayListFilter.get(i).getQuotes());
                            filterList.add(data);

                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = arrayListFilter.size();
                    results.values = arrayListFilter;
                }
                return results;
            } catch (Exception e) {
                Debug.i(TAG, "FilterResults Exceptions : " + e.getLocalizedMessage());
                return null;

            }


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {

                arrayList = (ArrayList<RolemodelData>) results.values;
                if (arrayList.size() == 0) {
                    RoleModelFragment.txtFavEmpty.setVisibility(View.VISIBLE);
                    RoleModelFragment.listViewFav.setVisibility(View.GONE);
                } else {
                    RoleModelFragment.txtFavEmpty.setVisibility(View.GONE);
                    RoleModelFragment.listViewFav.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            } catch (Exception e) {
                Debug.i(TAG, "publishResults on Exception :  " + e.getLocalizedMessage());
            }
        }

    }

    public class ViewHolder {

        private ImageView imgBook;
        private ImageView imgInfo;
        private ImageView imgAddToFav;
        private ImageView imgBookAdd;
        private TextView txtOrganization;
        private TextView txtBookName;


    }
}
