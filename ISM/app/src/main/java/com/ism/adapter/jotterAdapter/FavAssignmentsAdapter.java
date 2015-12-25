package com.ism.adapter.jotterAdapter;

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
public class FavAssignmentsAdapter extends RecyclerView.Adapter<FavAssignmentsAdapter.ViewHolder> {

    private static final String TAG = FavAssignmentsAdapter.class.getSimpleName();
    private Context context;

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

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBookSubject;
        private TextView txtAssignmentName;
        private TextView txtAssignmentBy;
        private TextView txtExamType;
        private TextView txtSubject;
        private ImageView imgUser;
        private ImageView imgMenu;

        public ViewHolder(View itemView) {
            super(itemView);

            txtSubject = (TextView) itemView.findViewById(R.id.txt_subject);
            txtSubject.setTypeface(Global.myTypeFace.getRalewayBold());

            txtExamType = (TextView) itemView.findViewById(R.id.txt_exam_type);
            txtExamType.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtAssignmentBy = (TextView) itemView.findViewById(R.id.txt_assignment_by);
            txtAssignmentBy.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtAssignmentName = (TextView) itemView.findViewById(R.id.txt_assignment_name);
            txtAssignmentName.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtBookSubject = (TextView) itemView.findViewById(R.id.txt_book_subject);
            txtBookSubject.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu);

            imgUser = (ImageView) itemView.findViewById(R.id.img_user_pic);

        }
    }
}
