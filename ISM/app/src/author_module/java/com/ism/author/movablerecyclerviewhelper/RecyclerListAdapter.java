/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ism.author.movablerecyclerviewhelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.author.Utility.Utils;
import com.ism.author.model.AnswersModel;
import com.ism.author.model.Data;
import com.ism.object.MyTypeFace;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {


    private static final String TAG = RecyclerListAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<Data> listOfPreviewQuestions = new ArrayList<Data>();
    MyTypeFace myTypeFace;


//    private final List<String> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;

    public RecyclerListAdapter(Context context, OnStartDragListener dragStartListener) {
        this.mContext = context;
        mDragStartListener = dragStartListener;
//        mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        myTypeFace = new MyTypeFace(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_preview_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvPreviewQuestionNo.setText(mContext.getString(R.string.strquestion) + " " + (position + 1));
        holder.tvPreviewQuestionNo.setTypeface(myTypeFace.getRalewayBold());
        holder.tvPreviewQuestionNo.setPaintFlags(holder.tvPreviewQuestionNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        holder.tvPreviewQuestion.setTypeface(myTypeFace.getRalewayRegular());
        holder.tvPreviewQuestion.setText(listOfPreviewQuestions.get(position).getQuestionText());

        if (!listOfPreviewQuestions.get(position).getQuestionFormat().equalsIgnoreCase("mcq")) {

            holder.tvPreviewQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
            holder.tvPreviewQuestionAns.setText(listOfPreviewQuestions.get(position).getSolution());


            holder.llPreviewQuestionAnswers.setVisibility(View.GONE);
            holder.tvPreviewQuestionAns.setVisibility(View.VISIBLE);


        } else {

            holder.llPreviewQuestionAnswers.removeAllViews();
            if (holder.llPreviewQuestionAnswers.getChildCount() == 0) {
                for (int i = 0; i < listOfPreviewQuestions.get(position).getAnswers().size(); i++) {
                    View ansView = getAnsInflaterView(listOfPreviewQuestions.get(position).getAnswers().get(i), i);
                    holder.llPreviewQuestionAnswers.addView(ansView);
                }
            }

            holder.llPreviewQuestionAnswers.setVisibility(View.VISIBLE);
            holder.tvPreviewQuestionAns.setVisibility(View.GONE);
        }


        holder.imgPreviewQuestionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listOfPreviewQuestions.remove(listOfPreviewQuestions.get(position));
                notifyDataSetChanged();

            }
        });


    }

    public void addAll(ArrayList<Data> data) {
        try {
//            this.listOfPreviewQuestions.clear();
            this.listOfPreviewQuestions.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        listOfPreviewQuestions.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(listOfPreviewQuestions, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public int getItemCount() {
        return listOfPreviewQuestions.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
//    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
//            ItemTouchHelperViewHolder {
//
//        public final TextView textView;
//        public final ImageView handleView;
//
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.text);
//            handleView = (ImageView) itemView.findViewById(R.id.handle);
//        }
//
//        @Override
//        public void onItemSelected() {
//            itemView.setBackgroundColor(Color.LTGRAY);
//        }
//
//        @Override
//        public void onItemClear() {
//            itemView.setBackgroundColor(0);
//        }
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        TextView tvPreviewQuestionNo, tvPreviewQuestion, tvPreviewQuestionAns;
        LinearLayout llPreviewQuestionAnswers;
        ImageView imgPreviewQuestionMove, imgPreviewQuestionEdit, imgPreviewQuestionCopy, imgPreviewQuestionDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPreviewQuestionNo = (TextView) itemView.findViewById(R.id.tv_preview_question_no);
            tvPreviewQuestion = (TextView) itemView.findViewById(R.id.tv_preview_question);
            tvPreviewQuestionAns = (TextView) itemView.findViewById(R.id.tv_preview_question_ans);

            llPreviewQuestionAnswers = (LinearLayout) itemView.findViewById(R.id.ll_preview_question_answers);

            imgPreviewQuestionMove = (ImageView) itemView.findViewById(R.id.img_preview_question_move);
            imgPreviewQuestionEdit = (ImageView) itemView.findViewById(R.id.img_preview_question_edit);
            imgPreviewQuestionCopy = (ImageView) itemView.findViewById(R.id.img_preview_question_copy);
            imgPreviewQuestionDelete = (ImageView) itemView.findViewById(R.id.img_preview_question_delete);


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }


    private View getAnsInflaterView(AnswersModel answer, int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v;
        v = layoutInflater.inflate(R.layout.row_mcq_question_answer, null, false);
        TextView tvMcqQuestionAns = (TextView) v.findViewById(R.id.tv_mcq_question_ans);
        tvMcqQuestionAns.setTypeface(myTypeFace.getRalewayRegular());
        tvMcqQuestionAns.setText(Utils.getCharForNumber(position + 1) + ": " + answer.getChoiceText());

        return v;
    }
}
