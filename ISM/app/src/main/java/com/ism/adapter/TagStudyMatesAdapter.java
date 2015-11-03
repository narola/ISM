package com.ism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.ws.model.Data;

import java.util.ArrayList;

/**
 * Tag Study Mates Adapter close to display the list of studymates
 */
public class TagStudyMatesAdapter extends RecyclerView.Adapter<TagStudyMatesAdapter.ViewHolder> {

	private static final String TAG = TagStudyMatesAdapter.class.getSimpleName();

	Context context;
	ArrayList<Data> listOfAllStudyMates = new ArrayList<>();
	ArrayList<Data> copyListOfStudyMates = new ArrayList<>();

	public TagStudyMatesAdapter(Context context, ArrayList<Data> listOfStudyMates) {
		this.context = context;
		this.listOfAllStudyMates = listOfStudyMates;
		this.copyListOfStudyMates.addAll(listOfAllStudyMates);
	}

	public ArrayList<String> tagIds = new ArrayList<String>();

	public ArrayList<String> getTagIds() {
		return tagIds;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);
		View contactView = inflater.inflate(R.layout.row_studymates_student, parent, false);
		ViewHolder viewHolder = new ViewHolder(contactView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		holder.txtStudymateName.setText(listOfAllStudyMates.get(position).getFullName());
		holder.chkAddusertotag.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (holder.chkAddusertotag.isChecked()) {
					tagIds.add(listOfAllStudyMates.get(position).getUserId());
				} else {
					tagIds.remove(listOfAllStudyMates.get(position).getUserId());
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return listOfAllStudyMates.size();
	}


	public static class ViewHolder extends RecyclerView.ViewHolder {

		ImageView imgStudymateDp, imgSeparatorMates;
		TextView txtStudymateName;
		CheckBox chkAddusertotag;

		public ViewHolder(View itemView) {
			super(itemView);
			imgStudymateDp = (ImageView) itemView.findViewById(R.id.img_studymate_dp);
			txtStudymateName = (TextView) itemView.findViewById(R.id.txt_studymate_name);
			chkAddusertotag = (CheckBox) itemView.findViewById(R.id.chk_addusertotag);
			imgSeparatorMates = (ImageView) itemView.findViewById(R.id.img_separator_mates);
		}
	}

	public void filter(CharSequence charText) {
		listOfAllStudyMates.clear();
		if (charText.length() == 0) {
			listOfAllStudyMates.addAll(copyListOfStudyMates);
		} else {
			for (Data wp : copyListOfStudyMates) {
				if (wp.getFullName().contains(charText)) {
					listOfAllStudyMates.add(wp);
				}
			}
			if (listOfAllStudyMates.size() == 0) {
				Toast.makeText(context, "No search result found!", Toast.LENGTH_SHORT).show();
			}
		}
		notifyDataSetChanged();
	}

}