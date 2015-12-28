package com.ism.adapter.jotterAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ism.R;
import com.ism.utility.Debug;

/**
 * Created by c162 on 25/12/15.
 */
public class FavLinksAdapter extends RecyclerView.Adapter<FavLinksAdapter.ViewHolder> {

    private static final String TAG = FavLinksAdapter.class.getSimpleName();
    private Context context;

    public FavLinksAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_link, parent, false);
//        View view = LayoutInflater.from(context).inflate(R.layout.item_events, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {

        } catch (Exception e) {
            Debug.i(TAG, "onBinderViewHolder Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgShare;

        public ViewHolder(View itemView) {
            super(itemView);


            imgShare = (ImageView) itemView.findViewById(R.id.img_link);


        }
    }
}
