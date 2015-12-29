package com.ism.teacher.fragments.notes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Debug;
import com.ism.teacher.Utility.Utility;
import com.ism.teacher.activity.TeacherHostActivity;
import com.ism.teacher.richeditor.Formula;
import com.ism.teacher.richeditor.GridAdaptor;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

/**
 * This Fragment is used to view notes/edit notes.
 */
public class NotesAddEditFragment extends Fragment implements View.OnClickListener, RichTextEditor.RichTextListener,Formula.FormulaListener, GridAdaptor.InsertSymbolListener {

    private static final String TAG = NotesAddEditFragment.class.getSimpleName();

    /**
     * Hide the text edit options when user is just reading note.
     * enable the options to edit text when user need to edit
     *
     */

    HorizontalScrollView richEditorEditControls;
    RichTextEditor rteNotes;
    private Formula formula;
    RelativeLayout rlRichEditorContainer;

    ImageView imgEditNote, imgShareNote;
    EditText etNoteTitle;
    private final int SELECT_PHOTO = 1, SELECT_VIDEO = 2;
    Uri selectedUri = null;

    //To know user is viewing note or creating new note
    public static String ARG_IS_CREATE_NOTE = "isCreateNote";

    public static NotesAddEditFragment newInstance() {
        NotesAddEditFragment notesAddEditFragment = new NotesAddEditFragment();
        return notesAddEditFragment;
    }

    public NotesAddEditFragment() {
        // Required empty public constructor
    }

    public NotesAddEditFragment (Fragment fragment)
    {

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TeacherHostActivity) getActivity()).rlControllerTopMenu.setBackgroundResource(R.drawable.bg_controller_top_blue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_notes, container, false);

        initGlobal(view);
        return view;
    }

    private void initGlobal(View rootview) {
        rlRichEditorContainer =(RelativeLayout)rootview.findViewById(R.id.rl_rich_editor_container);
        rteNotes = (RichTextEditor) rootview.findViewById(R.id.rte_notes);
        richEditorEditControls = (HorizontalScrollView) rootview.findViewById(R.id.horizontal_rich_editor_top_options);
        Utility.hideView(richEditorEditControls);

        imgEditNote = (ImageView) rootview.findViewById(R.id.img_edit_note);
        imgShareNote = (ImageView) rootview.findViewById(R.id.img_share_note);
        etNoteTitle = (EditText) rootview.findViewById(R.id.et_note_title);

        imgEditNote.setOnClickListener(this);
        imgShareNote.setOnClickListener(this);
        rteNotes.setHtml("");

        rteNotes.setRichTextListener(this);

        /**
         * For add new note
         */
        if (getBundleArguments().getBoolean(NotesAddEditFragment.ARG_IS_CREATE_NOTE)) {
            etNoteTitle.setEnabled(true);
            etNoteTitle.setText("");
            etNoteTitle.setHint("Add title of your new Note");
            etNoteTitle.setHintTextColor(getResources().getColor(R.color.color_shade_news_feed));
            Utility.showView(richEditorEditControls);
            rteNotes.setHtml("");
        } else {

            Utility.hideView(richEditorEditControls);
        }

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
        formula = new Formula(getActivity());
        formula.setFormulaListener((Formula.FormulaListener) this);
        rlRichEditorContainer.addView(formula);
        formula.gridAdaptor.setInsertSymbolListener(this);
    }

    private void openImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    private void openVideo() {
        Intent videoPickerIntent = new Intent(Intent.ACTION_PICK);
        videoPickerIntent.setType("video/*");
        startActivityForResult(videoPickerIntent, SELECT_VIDEO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);
        if (returnedIntent != null) {
            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            selectedUri = returnedIntent.getData();
                            String imgPath = Utility.getRealPathFromURI(selectedUri, getActivity());
                            rteNotes.insertImage(imgPath);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case SELECT_VIDEO:

                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            selectedUri = returnedIntent.getData();
                            String videoPath = Utility.getRealPathFromURI(selectedUri, getActivity());
                            rteNotes.insertVideo(videoPath);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;
            }
        }
    }

    public Bundle getBundleArguments() {
        return ((TeacherHostActivity) getActivity()).getBundle();
    }

    @Override
    public void close() {
        rlRichEditorContainer.removeView(formula);
        formula = null;
    }

    @Override
    public void insertSymbol(String symbol) {
        rteNotes.addSymbols(symbol);
    }
}
