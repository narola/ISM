package com.ism.teacher.fragments.notes;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.activity.TeacherHostActivity;

/**
 * Created by c75 on 25/12/15.
 */
public class NotesContainer extends Fragment {

    private static final String TAG = NotesContainer.class.getSimpleName();
    FrameLayout flLeftNoteContainer, flRightNoteContainer;


    NotesAddEditFragment notesAddEditFragment;
    NotesListFragment notesListFragment;

    public static NotesContainer newInstance() {
        NotesContainer notesContainer = new NotesContainer();
        return notesContainer;
    }

    public NotesContainer() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeacherHostActivity) getActivity()).rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_blue);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_container, container, false);

        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

        flLeftNoteContainer = (FrameLayout) rootview.findViewById(R.id.fl_left_note_container);
        flRightNoteContainer = (FrameLayout) rootview.findViewById(R.id.fl_right_note_container);

        notesAddEditFragment = new NotesAddEditFragment(this);
        notesListFragment = new NotesListFragment(this);
        loadFragmentInLeftContainer();
        loadFragmentInRightContainer();

    }

    private void loadFragmentInLeftContainer() {
        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_left_note_container, notesListFragment).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragmentInLeft Note Exception : " + e.toString());
        }

    }

    private void loadFragmentInRightContainer() {
        try {
            getFragmentManager().beginTransaction().replace(R.id.fl_right_note_container, notesAddEditFragment).commit();
        } catch (Exception e) {
            Debug.e(TAG, "loadFragmentInRightNote Exception : " + e.toString());
        }

    }

}
