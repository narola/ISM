package com.ism.teacher.fragments.notes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

/**
 * This Fragment is used to view notes/edit notes.
 */
public class NotesAddEditFragment extends Fragment {

    private static final String TAG = NotesAddEditFragment.class.getSimpleName();


    RichTextEditor rte_notes;
    HorizontalScrollView testHorizontalScrollView;

    public static NotesAddEditFragment newInstance() {
        NotesAddEditFragment notesAddEditFragment = new NotesAddEditFragment();
        return notesAddEditFragment;
    }

    public NotesAddEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_notes, container, false);

        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {

        rte_notes = (RichTextEditor) rootview.findViewById(R.id.rte_notes);
        testHorizontalScrollView = (HorizontalScrollView) rootview.findViewById(R.id.horizontal_rich_editor_top_options);
        Utility.hideView(testHorizontalScrollView);
    }

    private Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }
}
