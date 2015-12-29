package com.ism.adapter.deskAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.R;
import com.ism.object.Global;
import com.ism.utility.Debug;

/**
 * Created by c162 on 25/12/15.
 */
public class FavNotesAdapter extends RecyclerView.Adapter<FavNotesAdapter.ViewHolder> {

    private static final String TAG = FavNotesAdapter.class.getSimpleName();
    private Context context;

    public FavNotesAdapter(Context context) {
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notes, parent, false);
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
        private final TextView txtNotes;
        private final ImageView imgLike;
        private final ImageView imgShare;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNotes = (TextView) itemView.findViewById(R.id.txt_notes);
            txtNotes.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgShare = (ImageView) itemView.findViewById(R.id.img_share);

            imgLike = (ImageView) itemView.findViewById(R.id.img_like);

        }
    }
}
