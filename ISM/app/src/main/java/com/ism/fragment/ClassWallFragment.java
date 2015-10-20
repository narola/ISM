package com.ism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

public class ClassWallFragment extends Fragment {

	private static final String TAG = ClassWallFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerPost;

	public static ClassWallFragment newInstance() {
		ClassWallFragment fragment = new ClassWallFragment();
		return fragment;
	}

	public ClassWallFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_class_wall, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

}
