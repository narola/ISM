package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ism.R;
import com.ism.adapter.RecommendedStudymatesAdapter;
import com.ism.adapter.YourStudymatesAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.User;

import java.util.ArrayList;

import io.realm.internal.Util;

public class YourStudymatesFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	public static final String TAG = YourStudymatesFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerYourStudymates, recyclerRecommendedStudymates;
	private ImageView imgPrevious, imgNext;

	private YourStudymatesAdapter adpYourStudymates;
	private RecommendedStudymatesAdapter adpRecommendedStudymates;
	private ArrayList<User> arrListYourStudymates;
	private ArrayList<User> arrListRecommendedStudymates;

	public static YourStudymatesFragment newInstance(Bundle bundleArguments) {
		YourStudymatesFragment fragment = new YourStudymatesFragment();
		fragment.setArguments(bundleArguments);
		return fragment;
	}

	public YourStudymatesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_your_studymates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		recyclerYourStudymates = (RecyclerView) view.findViewById(R.id.recycler_your_studymates);
		recyclerRecommendedStudymates = (RecyclerView) view.findViewById(R.id.recycler_recommended_studymates);
		imgPrevious = (ImageView) view.findViewById(R.id.img_previous);
		imgNext = (ImageView) view.findViewById(R.id.img_next);

		if (Utility.isConnected(getActivity())) {
			callApiGetStudymates();
			callApiGetRecommentedStudymates();
		} else {
			Utility.alertOffline(getActivity());
		}

		RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 1;
			}
		};
		recyclerYourStudymates.addItemDecoration(decoration);
		recyclerYourStudymates.setLayoutManager(new LinearLayoutManager(getActivity()));

		arrListYourStudymates = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("User " + i);
			arrListYourStudymates.add(user);
		}
		adpYourStudymates = new YourStudymatesAdapter(getActivity(), arrListYourStudymates);
		recyclerYourStudymates.setAdapter(adpYourStudymates);



		final LinearLayoutManager layoutManagerRecommended = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		recyclerRecommendedStudymates.setLayoutManager(layoutManagerRecommended);
		arrListRecommendedStudymates = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("User " + i);
			arrListRecommendedStudymates.add(user);
		}
		adpRecommendedStudymates = new RecommendedStudymatesAdapter(getActivity(), arrListRecommendedStudymates);
		recyclerRecommendedStudymates.setAdapter(adpRecommendedStudymates);

		imgNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				recyclerRecommendedStudymates.getLayoutManager().smoothScrollToPosition(recyclerRecommendedStudymates, null, layoutManagerRecommended.findLastCompletelyVisibleItemPosition() + 1);
			}
		});

		imgPrevious.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int firstVisiblePosition = layoutManagerRecommended.findFirstCompletelyVisibleItemPosition();
				if (firstVisiblePosition > 0) {
					recyclerRecommendedStudymates.getLayoutManager().smoothScrollToPosition(recyclerRecommendedStudymates, null, firstVisiblePosition - 1);
				}
			}
		});
	}

	private void callApiGetRecommentedStudymates() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_NOTIFICATION);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetRecommentedStudymates Exception : " + e.toString());
		}
	}

	private void callApiGetStudymates() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_NOTIFICATION);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetStudymates Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {

	}
}