package com.ism.fragment.userprofile;

import android.support.v4.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ism.R;
import com.ism.adapter.FindStudymatesAdapter;
import com.ism.object.MyTypeFace;
import com.ism.ws.model.User;

import java.util.ArrayList;

/**
 * Created by c161 on 10/11/15.
 */
public class FindStudymatesFragment extends Fragment {

	private static final String TAG = FindStudymatesFragment.class.getSimpleName();

	private View view;
	private RecyclerView recyclerFindStudymates;
	private TextView txtPeople, txtSchool, txtArea, txtCourse;

	private FindStudymatesAdapter adpFindStudymate;
	private ArrayList<User> arrListFindStudymates;

	public static FindStudymatesFragment newInstance(Bundle bundleArguments) {
		FindStudymatesFragment fragFindStudymates = new FindStudymatesFragment();
		fragFindStudymates.setArguments(bundleArguments);
		return fragFindStudymates;
	}

	public FindStudymatesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_find_studymates, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {
		recyclerFindStudymates = (RecyclerView) view.findViewById(R.id.recycler_find_studymates);
		txtPeople = (TextView) view.findViewById(R.id.txt_people);
		txtSchool = (TextView) view.findViewById(R.id.txt_school);
		txtArea = (TextView) view.findViewById(R.id.txt_area);
		txtCourse = (TextView) view.findViewById(R.id.txt_course);

		MyTypeFace myTypeFace = new MyTypeFace(getActivity());
		txtPeople.setTypeface(myTypeFace.getRalewaySemiBold());
		txtSchool.setTypeface(myTypeFace.getRalewaySemiBold());
		txtArea.setTypeface(myTypeFace.getRalewaySemiBold());
		txtCourse.setTypeface(myTypeFace.getRalewaySemiBold());

		RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				outRect.bottom = 1;
			}
		};

		recyclerFindStudymates.addItemDecoration(decoration);
		recyclerFindStudymates.setLayoutManager(new LinearLayoutManager(getActivity()));

		arrListFindStudymates = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setUsername("User " + i);
			arrListFindStudymates.add(user);
		}

		adpFindStudymate = new FindStudymatesAdapter(getActivity(), arrListFindStudymates);
		recyclerFindStudymates.setAdapter(adpFindStudymate);
	}

}
