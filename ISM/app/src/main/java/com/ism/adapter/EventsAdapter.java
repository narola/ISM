package com.ism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.R;
import com.ism.model.EventsModel;
import com.ism.object.MyTypeFace;

import java.util.ArrayList;

/**
 * Created by c162 on 12/10/15.
 */
public class EventsAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    ArrayList<EventsModel> arrayList = new ArrayList<EventsModel>();

    public EventsAdapter(Context context,ArrayList<EventsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_events, parent, false);
       // if (position <= 4) {
            MyTypeFace myTypeFace = new MyTypeFace(context);
            TextView raw_txt_eventDate = (TextView) view.findViewById(R.id.txt_eventDate);
            TextView raw_txt_eventDesc = (TextView) view.findViewById(R.id.txt_eventDesc);
            TextView raw_txt_eventMonth = (TextView) view.findViewById(R.id.txt_eventMonth);
            TextView raw_txt_eventName = (TextView) view.findViewById(R.id.txt_eventName);
            TextView raw_txt_eventYear = (TextView) view.findViewById(R.id.txt_eventYear);
            TextView raw_txt_eventTime = (TextView) view.findViewById(R.id.txt_eventTime);

            raw_txt_eventName.setTypeface(myTypeFace.getRalewayBold());
            raw_txt_eventDesc.setTypeface(myTypeFace.getRalewayLight());
            raw_txt_eventMonth.setTypeface(myTypeFace.getRalewayBold());
            raw_txt_eventDate.setTypeface(myTypeFace.getRalewayBold());
          //  raw_txt_eventTime.setTypeface(myTypeFace.getRalewayBold());
            raw_txt_eventYear.setTypeface(myTypeFace.getRalewayBold());

//            if (position < 3) {
//
//                raw_txt_notice.setVisibility(View.VISIBLE);
//                raw_txt_noticeDesc.setVisibility(View.VISIBLE);
//                raw_txt_readmore.setVisibility(View.VISIBLE);
//                raw_txt_viewAll.setVisibility(View.GONE);
//                raw_txt_notice.setText(arrayList.get(position).getStrNoticeName());
//                raw_txt_noticeDesc.setText(arrayList.get(position).getStrNoticeDesc());
//            }
//            if (position == 3) {
//                raw_txt_notice.setVisibility(View.GONE);
//                raw_txt_noticeDesc.setVisibility(View.GONE);
//                raw_txt_readmore.setVisibility(View.GONE);
//                raw_txt_viewAll.setVisibility(View.VISIBLE);
//                view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            }
    //    }


        return view;
    }
}
