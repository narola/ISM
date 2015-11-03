package com.ism.teacher.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.helper.MyTypeFace;
import com.ism.teacher.model.NoticeModel;

import java.util.ArrayList;

/**
 * Created by c162 on 10/10/15.
 */
public class NoticeAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    NoticeAdapter adapter;
    ArrayList<NoticeModel> arrayList=new ArrayList<NoticeModel>();
    public NoticeAdapter(Context context, ArrayList<NoticeModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_notice, parent, false);
   // if(position<=2){
        MyTypeFace myTypeFace=new MyTypeFace(context);
        TextView raw_txt_notice = (TextView) view.findViewById(R.id.txt_noticeName);
        TextView raw_txt_noticeDesc = (TextView) view.findViewById(R.id.txt_noticeDesc);
        TextView raw_txt_readmore = (TextView) view.findViewById(R.id.txt_noticeReadMore);
     //   TextView raw_txt_viewAll = (TextView) view.findViewById(R.id.raw_txt_noticeViewAll);
        raw_txt_notice.setTypeface(myTypeFace.getRalewayBold());
        raw_txt_noticeDesc.setTypeface(myTypeFace.getRalewayThin());
        raw_txt_readmore.setTypeface(myTypeFace.getRalewayLight());
       // raw_txt_viewAll.setTypeface(myTypeFace.getRalewayRegular());

      //  if(position<2) {

            raw_txt_notice.setVisibility(View.VISIBLE);
            raw_txt_noticeDesc.setVisibility(View.VISIBLE);
            raw_txt_readmore.setVisibility(View.VISIBLE);
          //  raw_txt_viewAll.setVisibility(View.GONE);
            raw_txt_notice.setText(arrayList.get(position).getStrNoticeName());
            raw_txt_noticeDesc.setText(arrayList.get(position).getStrNoticeDesc());
        //}
//        if (position==2){
//            raw_txt_notice.setVisibility(View.GONE);
//            raw_txt_noticeDesc.setVisibility(View.GONE);
//            raw_txt_readmore.setVisibility(View.GONE);
//           // raw_txt_viewAll.setVisibility(View.VISIBLE);
//            view.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
//        }
//    }
//

        return view;
    }
}
