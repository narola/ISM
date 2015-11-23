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
import com.ism.adapter.Adapters;
import com.ism.adapter.AllNoticeAdapter;
import com.ism.interfaces.FragmentListener;
import com.ism.object.MyTypeFace;
import com.ism.utility.Utility;
import com.ism.ws.model.Data;
import com.ism.ws.model.Notice;

import java.util.ArrayList;

/**
 * Created by c161 on 04/11/15.
 */
public class AllNoticeFragment extends Fragment {

	private static final String TAG = AllNoticeFragment.class.getSimpleName();

	private View view;
	private Spinner spSortBy;
	private RecyclerView recyclerAllNotice;

	private ArrayList<Notice> arrListAllNotice;
	private AllNoticeAdapter adpAllNotice;

	private FragmentListener listenerFragment;

	public static AllNoticeFragment newInstance(ArrayList<Notice> arrListAllNotice) {
		AllNoticeFragment fragment = new AllNoticeFragment();
		fragment.setArrListNotice(arrListAllNotice);
		return fragment;
	}

	private void setArrListNotice(ArrayList<Notice> arrListAllNotice) {
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
		spSortBy = (Spinner) view.findViewById(R.id.sp_notice_sortby);
		recyclerAllNotice = (RecyclerView) view.findViewById(R.id.recycler_all_notice);

		MyTypeFace myTypeFace = new MyTypeFace(getActivity());

		Adapters.setUpSpinner(getActivity(), spSortBy, getActivity().getResources().getStringArray(R.array.notice_sortby)
				, myTypeFace.getRalewayRegular(), R.layout.list_item_simple_light);

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
					case 1: // NoticeTitle Ascending
						Utility.sortNoticeTitleAsc(arrListAllNotice);
						break;
					case 2: // NoticeTitle Descending
						Utility.sortNoticeTitleDesc(arrListAllNotice);
						break;
					case 3: // Latest Top
						Utility.sortPostedOnDesc(arrListAllNotice);
						break;
					case 4: // Oldest Top
						Utility.sortPostedOnAsc(arrListAllNotice);
						break;
				}
				adpAllNotice.notifyDataSetChanged();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	private void fillListAllNotice(ArrayList<Notice> arrListAllNotice) {
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
