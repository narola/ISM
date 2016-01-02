
package com.ism.teacher.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.object.Global;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter for the expandable listview
 */
public final class NotesExpandableListAdapter extends BaseExpandableListAdapter {
    private int selectedGroup;
    private int selectedChild;
    private Context mContext;
    private HashMap<String, ArrayList<String>> children;
    private ArrayList<String> listDataHeader;

    public NotesExpandableListAdapter(Context context, ArrayList<String> listDataHeader, HashMap<String, ArrayList<String>> child) {

        this.listDataHeader = new ArrayList<>();
        this.listDataHeader.addAll(listDataHeader);

        mContext = context;
        children = child;

    }

    public void onPick(int[] position) {
        selectedGroup = position[0];
        selectedChild = position[1];
    }

    static class ViewHolder {
        TextView tvNoteTitle;
        ImageView img_icon;

    }

    public void onDrop(int[] from, int[] to) {
        if (to[0] > children.size() || to[0] < 0 || to[1] < 0)
            return;
        String tValue = getValue(from);
        children.get(children.keySet().toArray()[from[0]]).remove(tValue);
        children.get(children.keySet().toArray()[to[0]]).add(to[1], tValue);
        selectedGroup = -1;
        selectedChild = -1;
        notifyDataSetChanged();
    }

    private String getValue(int[] id) {
        return (String) children.get(children.keySet().toArray()[id[0]]).get(id[1]);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return this.children.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

//        final String childText = (String) getChild(groupPosition, childPosition);

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.notes_child_row, null);
            // Creates a ViewHolder and store references to the two children
            // views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.tvNoteTitle = (TextView) convertView.findViewById(R.id.tv_note_title);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);

            holder.tvNoteTitle.setTypeface(Global.myTypeFace.getRalewayRegular());
            convertView.setTag(holder);

        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind the data efficiently with the holder.
        holder.tvNoteTitle.setText((String) (getChild(groupPosition, childPosition)));

        if (groupPosition != selectedGroup && childPosition != selectedChild) {
            convertView.setVisibility(View.VISIBLE);
//			ImageView iv = (ImageView) convertView
//					.findViewById(R.id.move_icon_customizer_item);
//			iv.setVisibility(View.VISIBLE);
        }
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.children.get(this.listDataHeader.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.notes_group_row, null);
        }

        TextView tvLectureName = (TextView) convertView.findViewById(R.id.tv_lecture_name);
        ImageView imgIndicator = (ImageView) convertView.findViewById(R.id.img_indicator);

        tvLectureName.setTypeface(Global.myTypeFace.getRalewayRegular());
        tvLectureName.setText(headerTitle);

        if (isExpanded) {
            imgIndicator.setImageResource(R.drawable.ic_arrow_close);
        } else {
            imgIndicator.setImageResource(R.drawable.ic_arrow_open);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}