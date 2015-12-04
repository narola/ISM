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
import android.widget.TextView;

import com.ism.R;
import com.ism.adapter.RecommendedStudymatesAdapter;
import com.ism.adapter.YourStudymatesAdapter;
import com.ism.constant.WebConstants;
import com.ism.object.Global;
import com.ism.utility.Utility;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;
import com.ism.ws.model.User;

import java.util.ArrayList;

public class YourStudymatesFragment extends Fragment implements WebserviceWrapper.WebserviceResponse {

	public static final String TAG = YourStudymatesFragment.class.getSimpleName();

	private View view;
	private TextView txtEmptyViewStudymates, txtEmptyViewRecommended;
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
		txtEmptyViewStudymates = (TextView) view.findViewById(R.id.txt_empty_view_studymates);
		txtEmptyViewRecommended = (TextView) view.findViewById(R.id.txt_empty_view_recommended);
		recyclerYourStudymates = (RecyclerView) view.findViewById(R.id.recycler_your_studymates);
		recyclerRecommendedStudymates = (RecyclerView) view.findViewById(R.id.recycler_recommended_studymates);
		imgPrevious = (ImageView) view.findViewById(R.id.img_previous);
		imgNext = (ImageView) view.findViewById(R.id.img_next);

		txtEmptyViewStudymates.setTypeface(Global.myTypeFace.getRalewayRegular());
		txtEmptyViewRecommended.setTypeface(Global.myTypeFace.getRalewayRegular());

		if (Utility.isConnected(getActivity())) {
			callApiGetStudymates();
//			callApiGetRecommentedStudymates();
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

		/*arrListYourStudymates = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("User " + i);
			arrListYourStudymates.add(user);
		}
		adpYourStudymates = new YourStudymatesAdapter(getActivity(), arrListYourStudymates);
		recyclerYourStudymates.setAdapter(adpYourStudymates);*/



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
					.execute(WebConstants.GET_ALL_RECOMMENDED_STUDYMATES);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetRecommentedStudymates Exception : " + e.toString());
		}
	}

	private void callApiGetStudymates() {
		try {
			Attribute attribute = new Attribute();
			attribute.setUserId(Global.strUserId);

			new WebserviceWrapper(getActivity(), attribute, this).new WebserviceCaller()
					.execute(WebConstants.GET_ALL_STUDYMATES_WITH_DETAILS);
		} catch (Exception e) {
			Log.e(TAG, "callApiGetStudymates Exception : " + e.toString());
		}
	}

	@Override
	public void onResponse(Object object, Exception error, int apiCode) {
		try {
			switch (apiCode) {
				case WebConstants.GET_ALL_STUDYMATES_WITH_DETAILS:
					onResponseGetStudyamtes(object, error);
					break;
				case WebConstants.GET_ALL_RECOMMENDED_STUDYMATES:
					onResponseGetAllRecommendedStudyamtes(object, error);
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponse Exception : " + e.toString());
		}
	}

	private void onResponseGetAllRecommendedStudyamtes(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListRecommendedStudymates = responseHandler.getStudymates();
					txtEmptyViewRecommended.setVisibility(arrListYourStudymates != null && arrListRecommendedStudymates.size() > 0 ? View.GONE : View.VISIBLE);
					adpRecommendedStudymates = new RecommendedStudymatesAdapter(getActivity(), arrListRecommendedStudymates);
					recyclerRecommendedStudymates.setAdapter(adpRecommendedStudymates);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetAllRecommendedStudyamtes Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetAllRecommendedStudyamtes api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetAllRecommendedStudyamtes Exception : " + e.toString());
		}
	}

	private void onResponseGetStudyamtes(Object object, Exception error) {
		try {
			if (object != null) {
				ResponseHandler responseHandler = (ResponseHandler) object;
				if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
					arrListYourStudymates = responseHandler.getStudymates();
					txtEmptyViewStudymates.setVisibility(arrListYourStudymates != null && arrListYourStudymates.size() > 0 ? View.GONE : View.VISIBLE);
					adpYourStudymates = new YourStudymatesAdapter(getActivity(), arrListYourStudymates);
					recyclerYourStudymates.setAdapter(adpYourStudymates);
				} else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
					Log.e(TAG, "onResponseGetStudyamtes Failed");
				}
			} else if (error != null) {
				Log.e(TAG, "onResponseGetStudyamtes api Exception : " + error.toString());
			}
		} catch (Exception e) {
			Log.e(TAG, "onResponseGetStudyamtes Exception : " + e.toString());
		}
	}
}