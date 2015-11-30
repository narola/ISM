package com.ism.fragment.userprofile;

import android.app.Fragment;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;
import com.ism.adapter.RecommendedStudymatesAdapter;
import com.ism.adapter.YourStudymatesAdapter;
import com.ism.ws.model.User;

import java.util.ArrayList;

public class YourStudymatesFragment extends Fragment {

	public static final String TAG = YourStudymatesFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerYourStudymates, recyclerRecommendedStudymates;

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

		RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 1;
			}
		};
		recyclerYourStudymates.addItemDecoration(decoration);
		recyclerYourStudymates.setLayoutManager(new LinearLayoutManager(getActivity()));

		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		recyclerRecommendedStudymates.setLayoutManager(layoutManager);


		arrListYourStudymates = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("User " + i);
			arrListYourStudymates.add(user);
		}

		adpYourStudymates = new YourStudymatesAdapter(getActivity(), arrListYourStudymates);
		recyclerYourStudymates.setAdapter(adpYourStudymates);

		arrListRecommendedStudymates = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("User " + i);
			arrListRecommendedStudymates.add(user);
		}

		adpRecommendedStudymates = new RecommendedStudymatesAdapter(getActivity(), arrListRecommendedStudymates);
		recyclerRecommendedStudymates.setAdapter(adpRecommendedStudymates);
	}

}