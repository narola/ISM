package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.R;

/**
 * Created by c161 on 06/11/15.
 */
public class MyWalletFragment extends Fragment {

	private static final String TAG = MyWalletFragment.class.getSimpleName();

	private View view;

	public static MyWalletFragment newInstance() {
		MyWalletFragment fragment = new MyWalletFragment();
		return fragment;
	}

	public MyWalletFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_wallet, container, false);

		initGlobal();

		return view;
	}

	private void initGlobal() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
