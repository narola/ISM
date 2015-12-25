package com.ism.fragment.desk;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.ScientificGridAdaptor;
import com.ism.adapter.JotterNotesAdapter;
import com.ism.constant.AppConstant;
import com.ism.dialog.AddNewNoteDialog;
import com.ism.dialog.ShareNoteDialog;
import com.ism.fragment.DeskFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.richeditor.Formula;
import com.ism.richeditor.GridAdaptor;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by c161 on --/10/15.
 */
public class JotterFragment extends Fragment implements HostActivity.InsertSymbolListener, DeskFragment.AlertDismissListener, RichEditor.OnTextChangeListener, RichTextEditor.RichTextListener, GridAdaptor.InsertSymbolListener, Formula.FormulaListener {

    private static final String TAG = JotterFragment.class.getSimpleName();
    public static final int DIALOG_SHARE = 100;
    public static final int DIALOG_ADD_NOTES = 200;
    private static final int SELECT_PHOTO = 150;
    private static final int SELECT_VIDEO = 151;
    private View view;
    private FragmentListener fragListener;
    private EditText etNotes;
    private HostActivity activityHost;
    private ScientificGridAdaptor gridAdaptor;
    private RecyclerView listView;
    private JotterNotesAdapter jotterNotesAdapter;
    private TextView txtAddNew;
    private ImageView imgShare;
    private ShareNoteDialog shareNoteDialog;
    private RichTextEditor rteNotes;
    private HorizontalScrollView testHorizontalScrollView;
    private ImageView imgEdit;
    private Uri selectedUri;
    private String mediaType;
    private String richtext = "";
    private Formula formula;
    private RelativeLayout layoutText;
    private EditText etSubject;
    private String strNote = "";

    public static JotterFragment newInstance() {
        JotterFragment jotterFragment = new JotterFragment();
        return jotterFragment;
    }

    public JotterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jotter, container, false);

        initGlobal();

        return view;
    }


    private void initGlobal() {
        shareNoteDialog = new ShareNoteDialog(getActivity(), this);

//        etNotes = (EditText) view.findViewById(R.id.et_notes);
//        etNotes.setTypeface(Global.myTypeFace.getRalewayRegular());

        layoutText = (RelativeLayout) view.findViewById(R.id.rr_details);
        rteNotes = (RichTextEditor) view.findViewById(R.id.rte_notes);
        rteNotes.getRichEditor().setEditorFontSize(20);
        rteNotes.getRichEditor().setOnTextChangeListener(this);
        rteNotes.setRichTextListener((RichTextEditor.RichTextListener) this);
        rteNotes.setHtml(richtext);

        testHorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontal_rich_editor_top_options);
        testHorizontalScrollView.setVisibility(View.GONE);

        txtAddNew = (TextView) view.findViewById(R.id.txt_add);
        txtAddNew.setTypeface(Global.myTypeFace.getRalewayRegular());

        etSubject = (EditText) view.findViewById(R.id.et_subject);
        etSubject.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgShare = (ImageView) view.findViewById(R.id.img_share);
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgShare.setActivated(true);

                shareNoteDialog.show();
            }
        });

        imgEdit = (ImageView) view.findViewById(R.id.img_edit);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEdit.setActivated(true);
                rteNotes.setEnabled(true);
                rteNotes.requestFocus();
                rteNotes.setVisibility(View.VISIBLE);
                testHorizontalScrollView.setVisibility(View.VISIBLE);
            }
        });
        listView = (RecyclerView) view.findViewById(R.id.recyclerview_notes);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        jotterNotesAdapter = new JotterNotesAdapter(getActivity(), null);
        listView.setAdapter(jotterNotesAdapter);

        txtAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNoteDialog newNoteDialog = new AddNewNoteDialog(getActivity(), JotterFragment.this);
                newNoteDialog.show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);

        if (returnedIntent != null) {

            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == getActivity().RESULT_OK) {
                        try {
                            final Uri imageUri = returnedIntent.getData();
                            String imgPath = Utility.getRealPathFromURI(imageUri, getActivity());
                            rteNotes.insertImage(imgPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case SELECT_VIDEO:

                    if (resultCode == getActivity().RESULT_OK) {
                        try {

                            final Uri videoUri = returnedIntent.getData();
                            String videoPath = Utility.getRealPathFromURI(videoUri, getActivity());
                            rteNotes.insertVideo(videoPath);
//                                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
//                                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
//                                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
//                                imgSelectImage.setImageBitmap(bitmap);
//                                imgPlay.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;

                case AppConstant.REQUEST_CODE_PICK_FROM_GALLERY:

                    if (returnedIntent != null) {
                        selectedUri = returnedIntent.getData();
                        String[] columns = {MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.MIME_TYPE};
                        Cursor cursor = getActivity().getContentResolver().query(selectedUri, columns, null, null, null);
                        cursor.moveToFirst();

                        int mimeTypeColumnIndex = cursor.getColumnIndex(columns[1]);
                        String mimeType = cursor.getString(mimeTypeColumnIndex);
                        cursor.close();

                        if (mimeType.startsWith(AppConstant.MEDIATYPE_IMAGE)) {
                            try {
                                mediaType = AppConstant.MEDIATYPE_IMAGE;
                                // imgSelectImage.setImageURI(selectedUri);
                                // imgPlay.setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (mimeType.startsWith(AppConstant.MEDIATYPE_VIDEO)) {
                            try {
                                mediaType = AppConstant.MEDIATYPE_VIDEO;
                                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
                                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                                // imgSelectImage.setImageBitmap(bitmap);
                                //  imgPlay.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    break;
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityHost = (HostActivity) activity;
            activityHost.setInsertSymbolListener(this);
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach Exception : " + e.toString());
        }
    }


    //    @Override
//    public void Scientific(String symbol) {
//        //etNotes.setText(etNotes.getText().toString() + symbol);
//        // etNotes.setSelection(etNotes.getText().toString().length());
//    }
//length
    @Override
    public void onDismiss(int alertDialog, String note) {
        try {
            if (alertDialog == DIALOG_SHARE) {
                imgShare.setActivated(false);
            } else if (alertDialog == DIALOG_ADD_NOTES) {
                // if(getArguments()!=null){
                strNote = note;
                Debug.i(TAG, "New Note : " + strNote);
                // }
            }
        } catch (Exception e) {
            Debug.i(TAG, "onDismiss Exception : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void Scientific(String symbol) {
        rteNotes.addSymbols(symbol);
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

    @Override
    public void onTextChange(String text) {
        richtext = text;
    }

    @Override
    public void imagePicker() {
        openImage();
    }

    @Override
    public void videoPicker() {
        openVideo();
    }

    @Override
    public void openFormulaDialog() {
        formula = new Formula(getActivity());
        formula.setFormulaListener(this);
        layoutText.addView(formula);
        formula.gridAdaptor.setInsertSymbolListener(this);
    }

    @Override
    public void insertSymbol(String symbol) {
        rteNotes.addSymbols(symbol);
    }

    @Override
    public void close() {
        layoutText.removeView(formula);
        formula = null;
    }
}
