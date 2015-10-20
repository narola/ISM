package com.ism.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.HostActivity;
import com.ism.R;
import com.ism.interfaces.FragmentListener;

/**
 * Created by c161 on --/10/15.
 */
public class ClassroomFragment extends Fragment implements HostActivity.HostListener {

    private static final String TAG = ClassroomFragment.class.getName();

    private View view;

    private FragmentListener fragListener;

    private static final String ARG_FRAGMENT = "fragment";
	public static final int FRAGMENT_CLASSWALL = 0;
	public static final int FRAGMENT_LESSON_NOTES = 1;
	public static final int FRAGMENT_ASSIGNMENT = 2;
	public static final int FRAGMENT_EXAM = 3;
	private int fragment;

    public static ClassroomFragment newInstance(int fragment) {
        ClassroomFragment fragClassroom = new ClassroomFragment();
	    Bundle args = new Bundle();
	    args.putInt(ARG_FRAGMENT, fragment);
	    fragClassroom.setArguments(args);
        return fragClassroom;
    }

    public ClassroomFragment() {
        // Required empty public constructor
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			fragment = getArguments().getInt(ARG_FRAGMENT);
			if (fragListener != null) {
				fragListener.onFragmentAttached(fragment);
			}
		}
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classroom, container, false);

        initGlobal();

        return view;
    }

    private void initGlobal() {

	    loadFragment(FRAGMENT_CLASSWALL);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragListener = (FragmentListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            if (fragListener != null) {
                fragListener.onFragmentDetached(fragment);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "onDetach Exception : " + e.toString());
        }
        fragListener = null;
    }

	@Override
	public void onControllerMenuItemClicked(int position) {
		loadFragment(position);
	}

	private void loadFragment(int fragment) {
		try {
			switch (fragment) {
				case FRAGMENT_CLASSWALL:
					getChildFragmentManager().beginTransaction().replace(R.id.fl_classroom, ClassWallFragment.newInstance()).commit();
					break;
				case FRAGMENT_LESSON_NOTES:
					break;
				case FRAGMENT_ASSIGNMENT:
					break;
				case FRAGMENT_EXAM:
					break;
			}
		} catch (Exception e) {
			Log.e(TAG, "loadFragment Exception : " + e.toString());
		}
	}

}
