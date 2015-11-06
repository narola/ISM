package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.AllNoticeAdapter;
import com.ism.adapter.AllNoticeSortByAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.model.AllNotice;
import com.ism.ws.model.Data;

import java.util.ArrayList;

public class AllNoticeFragment extends Fragment {

	private static final String TAG = AllNoticeFragment.class.getSimpleName();

	private View view;
	private Spinner spCategory, spSortBy;
	private RecyclerView recyclerAllNotice;

	private ArrayList<Data> arrListAllNotice;
	private AllNoticeAdapter adpAllNotice;
	private AllNoticeSortByAdapter adpSortBy;

	private FragmentListener listenerFragment;

	public static AllNoticeFragment newInstance(ArrayList<Data> arrListAllNotice) {
		AllNoticeFragment fragment = new AllNoticeFragment();
		fragment.setArrListNotice(arrListAllNotice);
		return fragment;
	}

	private void setArrListNotice(ArrayList<Data> arrListAllNotice) {
		this.arrListAllNotice = arrListAllNotice;
	}

	public AllNoticeFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_all_notice, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		spCategory = (Spinner) view.findViewById(R.id.sp_notice_category);
		spSortBy = (Spinner) view.findViewById(R.id.sp_notice_sortby);
		recyclerAllNotice = (RecyclerView) view.findViewById(R.id.recycler_all_notice);

		adpSortBy = new AllNoticeSortByAdapter(getActivity());
		spSortBy.setAdapter(adpSortBy);

		/*arrListAllNotice = new ArrayList<AllNotice>();
		arrListAllNotice.add(new AllNotice("Notice title 1", "Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content."));
		arrListAllNotice.add(new AllNotice("Notice title 2", "Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content."));
		arrListAllNotice.add(new AllNotice("Notice title 3", "Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content."));
		arrListAllNotice.add(new AllNotice("Notice title 4", "Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content."));
		arrListAllNotice.add(new AllNotice("Notice title 5", "Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content. Notice content."));*/

		recyclerAllNotice.setLayoutManager(new LinearLayoutManager(getActivity()));

		RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 10;
				outRect.right = 10;
				outRect.left = 10;
				if (parent.getChildLayoutPosition(view) == 0) {
					outRect.top = 10;
				}
			}
		};

		recyclerAllNotice.addItemDecoration(itemDecoration);

		fillListAllNotice(arrListAllNotice);

		spSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	private void fillListAllNotice(ArrayList<Data> arrListAllNotice) {
		try {
			if (arrListAllNotice != null) {
				adpAllNotice = new AllNoticeAdapter(getActivity(), arrListAllNotice);
				recyclerAllNotice.setAdapter(adpAllNotice);
			}
		} catch (Exception e) {
			Log.e(TAG, "fillListAllNotice Exception : " + e.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listenerFragment = (FragmentListener) activity;
			if (listenerFragment != null) {
				listenerFragment.onFragmentAttached(HostActivity.FRAGMENT_ALL_NOTES);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onAttach Exception : " + e.toString());
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			if (listenerFragment != null) {
				listenerFragment.onFragmentDetached(HostActivity.FRAGMENT_ALL_NOTES);
			}
		} catch (ClassCastException e) {
			Log.e(TAG, "onDetach Exception : " + e.toString());
		}
		listenerFragment = null;
	}

}
