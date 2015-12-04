package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ism.R;
import com.ism.ws.model.BookData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c162 on 03/12/15.
 */
public class LayoutFavouriteBooksAdapter  {
    private final Context mContext;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;
    private static final int COUNT = 100;

    public LayoutFavouriteBooksAdapter(Context context, ArrayList<BookData> arrayList) {
        mContext = context;
        mItems = new ArrayList<Integer>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            addItem(i);
        }

    }



    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
       // notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
       // notifyItemRemoved(position);
    }
//
//    @Override
//    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//     //   final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
//      //  return new SimpleViewHolder(view);
//        return  null;
//    }
//
//    @Override
//    public void onBindViewHolder(SimpleViewHolder holder, int position) {
//        holder.title.setText(mItems.get(position).toString());
//
//       // boolean isVertical = (mRecyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
//        final View itemView = holder.itemView;
//
//        final int itemId = mItems.get(position);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItems.size();
//    }
}
