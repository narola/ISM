package com.ism.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.ism.fragment.userProfile.FindStudymatesFragment;
import com.ism.fragment.userProfile.YourStudymatesFragment;

/**
 * Created by c161 on 27/11/15.
 */
public class StudymatesPagerAdapter extends FragmentStatePagerAdapter {

	private static final String TAG = StudymatesPagerAdapter.class.getSimpleName();

	private FindStudymatesFragment fragFindStudymates;
	private YourStudymatesFragment fragYourStudymates;

	public StudymatesPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				if (fragYourStudymates == null) {
					fragYourStudymates = YourStudymatesFragment.newInstance(null);
				}
				return fragYourStudymates;
			case 1:
				if (fragFindStudymates == null) {
					fragFindStudymates = FindStudymatesFragment.newInstance(null);
				}
				return fragFindStudymates;
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

}
