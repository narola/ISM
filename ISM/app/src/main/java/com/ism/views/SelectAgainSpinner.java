package com.ism.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by c161 on 07/12/15.
 */
public class SelectAgainSpinner extends Spinner {

	OnItemSelectedListener listener;

	public SelectAgainSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setSelection(int position) {
		super.setSelection(position);

		if (position == getSelectedItemPosition()) {
			listener.onItemSelected(null, null, position, 0);
		}
	}

	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		this.listener = listener;
	}

}
