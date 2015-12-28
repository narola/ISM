package com.ism.adapter.jotterAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
public class FavExamsAdapter extends RecyclerView.Adapter<FavExamsAdapter.ViewHolder> {

    private static final String TAG = FavExamsAdapter.class.getSimpleName();
    private Context context;

    public FavExamsAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exam, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            holder.txtYearClass.setText(Html.fromHtml("2015 : <font color='#66C188'><b>12 th Science</b></font>"));
            holder.txtExamType.setText(Html.fromHtml("Exam Type : <font color='#1BC4A2'>ISM MOCK</font>"));
        } catch (Exception e) {
            Debug.i(TAG, "onBinderViewHolder Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtExamType;
        private TextView txtYearClass;
        private TextView txtAssignmentName;
        private TextView txtExamBy;
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

            txtExamType = (TextView) itemView.findViewById(R.id.txt_exam_type);
            txtExamType.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtExamBy = (TextView) itemView.findViewById(R.id.txt_exam_by);
            txtExamBy.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtAssignmentName = (TextView) itemView.findViewById(R.id.txt_exam_name);
            txtAssignmentName.setTypeface(Global.myTypeFace.getRalewayRegular());

            txtYearClass = (TextView) itemView.findViewById(R.id.txt_year_class);
            txtYearClass.setTypeface(Global.myTypeFace.getRalewayRegular());

            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu);

            imgUser = (ImageView) itemView.findViewById(R.id.img_user_pic);

        }
    }
}
