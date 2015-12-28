package com.ism.teacher.fragments.notes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ism.teacher.R;
import com.ism.teacher.activity.TeacherHostActivity;

/**
 * Created by c75 on 25/12/15.
 */
public class NotesListFragment extends Fragment {

    private static final String TAG = NotesListFragment.class.getSimpleName();
    Fragment mFragment;

    public NotesListFragment() {
        // Required empty public constructor
    }

    public NotesListFragment(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeacherHostActivity) getActivity()).rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_blue);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

    }

}
