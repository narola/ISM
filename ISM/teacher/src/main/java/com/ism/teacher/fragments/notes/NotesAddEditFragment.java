package com.ism.teacher.fragments.notes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

/**
 * This Fragment is used to view notes/edit notes.
 */
public class NotesAddEditFragment extends Fragment implements View.OnClickListener, RichTextEditor.RichTextListener {

    private static final String TAG = NotesAddEditFragment.class.getSimpleName();


    RichTextEditor rteNotes;

    /**
     * Hide the text edit options when user is just reading note.
     * enable the options to edit text when user need to edit
     */

    HorizontalScrollView richEditorEditControls;
    ImageView imgEditNote, imgShareNote;
    TextView tvNoteTitle;
    private final int SELECT_PHOTO = 1, SELECT_VIDEO = 2;

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

        rteNotes = (RichTextEditor) rootview.findViewById(R.id.rte_notes);
        richEditorEditControls = (HorizontalScrollView) rootview.findViewById(R.id.horizontal_rich_editor_top_options);
        Utility.hideView(richEditorEditControls);

        imgEditNote = (ImageView) rootview.findViewById(R.id.img_edit_note);
        imgShareNote = (ImageView) rootview.findViewById(R.id.img_share_note);
        tvNoteTitle = (TextView) rootview.findViewById(R.id.tv_note_title);

        imgEditNote.setOnClickListener(this);
        imgShareNote.setOnClickListener(this);
        rteNotes.setHtml(getString(R.string.dummy_string));
        rteNotes.setRichTextListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_edit_note:
                Utility.showView(richEditorEditControls);
                break;
            case R.id.img_share_note:
                break;
        }
    }

    @Override
    public void imagePicker() {
        Debug.e(TAG, "image");

        openImage();
    }

    @Override
    public void videoPicker() {
        Debug.e(TAG, "video");
        openVideo();
    }

    @Override
    public void openFormulaDialog() {
        Debug.e(TAG, "formula");
    }

    private void openImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void openVideo() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/*");
        startActivityForResult(photoPickerIntent, SELECT_VIDEO);

    }
}
