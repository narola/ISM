package com.ism.adapter.myAuthor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.utility.Debug;
import com.ism.ws.model.Assignment;

import java.util.ArrayList;

/**
 * Created by c162 on 08/01/16.
 */
public class GoTrendingQueAnsAuthorAdapter extends PagerAdapter {


    private static final String TAG = GoTrendingQueAnsAuthorAdapter.class.getSimpleName();
    private final Context mContext;
    private ArrayList<Assignment> arrListBookAssignment = new ArrayList<Assignment>();
    private LayoutInflater inflater;
    int[] res = {
            android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_directions,
            android.R.drawable.ic_menu_gallery};
    int[] backgroundcolor = {
            0xFF101010,
            0xFF202020,
            0xFF303030,
            0xFF404040,
            0xFF505050};

    public GoTrendingQueAnsAuthorAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }


//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View contactView = inflater.inflate(R.layout.item_question_answer_trending, parent, false);
//        ViewHolder viewHolder = new ViewHolder(contactView);
//        return viewHolder;
//    }
//
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//
//        try {
//
//        } catch (Exception e) {
//            Debug.e(TAG, "onBindViewHolder Exception : " + e.toString());
//        }
//    }


    public void addAll(ArrayList<Assignment> assignments) {
        try {
            this.arrListBookAssignment.clear();
            this.arrListBookAssignment.addAll(assignments);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view = inflater.inflate(R.layout.item_question_answer_trending, container, false);
        RelativeLayout main=(RelativeLayout)view.findViewById(R.id.rr_main);
//        ViewHolder viewHolder = new ViewHolder(contactView);
       // return view;
//        TextView textView = new TextView(mContext);
//        textView.setTextColor(Color.WHITE);
//        textView.setTextSize(30);
//        textView.setTypeface(Typeface.DEFAULT_BOLD);
//        textView.setText(String.valueOf(position));
//
//        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(res[position]);
//        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        imageView.setLayoutParams(imageParams);
//
//        LinearLayout layout = new LinearLayout(mContext);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        layout.setBackgroundColor(backgroundcolor[position]);
//        layout.setLayoutParams(layoutParams);
//        layout.addView(textView);
//        layout.addView(imageView);
//
//        final int page = position;
//        layout.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext,
//                        "Page " + page + " clicked",
//                        Toast.LENGTH_LONG).show();
//            }});

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAssignmentTitle, tvAssignmentContent;
        private ImageView imgOption;

        public ViewHolder(View itemView) {
            super(itemView);

//            tvAssignmentTitle = (TextView) itemView.findViewById(R.id.tv_assignment_title);
//            tvAssignmentContent = (TextView) itemView.findViewById(R.id.tv_assignment_content);
//
//
//            tvAssignmentTitle.setTypeface(Global.myTypeFace.getRalewayBold());
//            tvAssignmentContent.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgOption = (ImageView) itemView.findViewById(R.id.img_menu);

        }
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

}
