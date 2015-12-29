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
import com.ism.ws.model.AllBooks;

import java.util.ArrayList;

/**
 * Created by c162 on 25/12/15.
 */
public class FavAssignmentsAdapter extends RecyclerView.Adapter<FavAssignmentsAdapter.ViewHolder> {

    private static final String TAG = FavAssignmentsAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<AllBooks> arrayList=new ArrayList<>();

    public FavAssignmentsAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assignment, parent, false);
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
    public void addAll(ArrayList<AllBooks> allBooks) {
        try {
            this.arrayList.clear();
            this.arrayList.addAll(allBooks);
        } catch (Exception e) {
            Debug.e(TAG, "addAllData Exception : " + e.toString());
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBookSubject;
        private TextView txtAssignmentName;
        private TextView txtAssignmentBy;
        private TextView txtExamMode;
        private TextView txtSubject;
        private ImageView imgUser;
        private ImageView imgMenu;

        public ViewHolder(View itemView) {
            super(itemView);

            txtSubject = (TextView) itemView.findViewById(R.id.txt_subject);
            txtSubject.setTypeface(Global.myTypeFace.getRalewayBold());

            txtExamMode = (TextView) itemView.findViewById(R.id.txt_exam_mode);
            txtExamMode.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtAssignmentBy = (TextView) itemView.findViewById(R.id.txt_exam_by);
            txtAssignmentBy.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtAssignmentName = (TextView) itemView.findViewById(R.id.txt_exam_name);
            txtAssignmentName.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtBookSubject = (TextView) itemView.findViewById(R.id.txt_book_subject);
            txtBookSubject.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu);

            imgUser = (ImageView) itemView.findViewById(R.id.img_user_pic);

        }
    }
}
