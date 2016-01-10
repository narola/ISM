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

import com.ism.ISMStudent;
import com.ism.R;
import com.ism.activity.HostActivity;
import com.ism.adapter.JotterNotesAdapter;
import com.ism.adapter.ScientificGridAdaptor;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.dialog.AddNewNoteDialog;
import com.ism.dialog.ShareNoteDialog;
import com.ism.fragment.DeskFragment;
import com.ism.interfaces.FragmentListener;
import com.ism.object.Global;
import com.ism.richeditor.Formula;
import com.ism.richeditor.GridAdaptor;
import com.ism.utility.Utility;
import com.ism.views.CircleImageView;
import com.ism.ws.helper.WebserviceWrapper;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import io.realm.RealmResults;
import jp.wasabeef.richeditor.RichEditor;
import model.RONotes;
import realmhelper.StudentHelper;

/**
 * Created by c161 on --/10/15.
 */
public class JotterFragment extends Fragment implements HostActivity.InsertSymbolListener, DeskFragment.AlertDismissListener, RichEditor.OnTextChangeListener, RichTextEditor.RichTextListener, GridAdaptor.InsertSymbolListener, Formula.FormulaListener, WebserviceWrapper.WebserviceResponse, JotterNotesAdapter.ViewNoteListener {

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
    private TextView txtNoteBy, txtNoteTitle;
    private RealmResults<RONotes> arraylistNotes;
    private CircleImageView imgUser;
    private String strNoteText = "";
    private String strNoteId = "";
    private int lastPosition = 0;
    private int strNoteByID;
    private StudentHelper studentHelper;
    private RONotes objRONotes;

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
        studentHelper = new StudentHelper(getActivity());

        shareNoteDialog = new ShareNoteDialog(getActivity(), this);

        etNotes = (EditText) view.findViewById(R.id.et_notes);
        etNotes.setTypeface(Global.myTypeFace.getRalewayRegular());

        layoutText = (RelativeLayout) view.findViewById(R.id.rr_details);

        rteNotes = (RichTextEditor) view.findViewById(R.id.rte_notes);
        rteNotes.getRichEditor().setEditorFontSize(20);
        rteNotes.getRichEditor().setOnTextChangeListener(this);
        rteNotes.setRichTextListener(this);
//        rteNotes.setHtml(richtext);

        testHorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontal_rich_editor_top_options);
        testHorizontalScrollView.setVisibility(View.GONE);

        txtAddNew = (TextView) view.findViewById(R.id.txt_add);
        txtAddNew.setTypeface(Global.myTypeFace.getRalewayRegular());

        imgUser = (CircleImageView) view.findViewById(R.id.img_user_pic);

        txtNoteBy = (TextView) view.findViewById(R.id.txt_by);
        txtNoteBy.setTypeface(Global.myTypeFace.getRalewayRegular());

        txtNoteTitle = (TextView) view.findViewById(R.id.txt_note_name);
        txtNoteTitle.setTypeface(Global.myTypeFace.getRalewayBold());

        etSubject = (EditText) view.findViewById(R.id.et_subject);
        etSubject.setTypeface(Global.myTypeFace.getRalewayRegular());

        etSubject.setEnabled(false);

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
                if (strNoteByID == Integer.parseInt(Global.strUserId)) {
                    if (imgEdit.isActivated()) {
                        imgEdit.setActivated(false);
                        testHorizontalScrollView.setVisibility(View.GONE);
                        etSubject.setEnabled(false);
                        editNote(etSubject.getText().toString().trim(), rteNotes.getHtml().toString());
                    } else {
                        etSubject.setEnabled(true);
                        imgEdit.setActivated(true);
                        testHorizontalScrollView.setVisibility(View.VISIBLE);
                    }
                } else {
                    //   Utility.showToast();
                }
            }
        });

        listView = (RecyclerView) view.findViewById(R.id.recyclerview_notes);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        jotterNotesAdapter = new JotterNotesAdapter(getActivity(), null);
//        listView.setAdapter(jotterNotesAdapter);

        txtAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNoteDialog newNoteDialog = new AddNewNoteDialog(getActivity(), JotterFragment.this);
                newNoteDialog.show();
            }
        });


//        arraylistNotes=Global.studentHelper.getNotes(Global.studentHelper.getUser(Integer.parseInt(Global.strUserId)),null);
        setUpData();

    }

    private void editNote(String subject, String noteText) {
        try {
            studentHelper.realm.beginTransaction();
            objRONotes.setNoteText(noteText);
            objRONotes.setNoteSubject(subject);
            objRONotes.setIsSync(1);
            objRONotes.setModifiedDate(Utility.getDateMySql());
            studentHelper.realm.commitTransaction();
//
            setUpData();
        } catch (Exception e) {
            Log.e(TAG, "editNote Exception : " + e.getLocalizedMessage());
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
                copyDataToRealm(strNote, "", "");
                jotterNotesAdapter.notifyDataSetChanged();
                Log.e(TAG, "New Note : " + strNote);
                // }
            }
        } catch (Exception e) {
            Log.e(TAG, "onDismiss Exception : " + e.getLocalizedMessage());
        }
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
                            Log.e(TAG, "onVideoSelect Exception: " + e.getLocalizedMessage());
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
    public void Scientific(String symbol) {
        rteNotes.addSymbols(symbol);
    }

    public void copyDataToRealm(String noteName, String noteText, String noteSubject) {
        RONotes RONotes = new RONotes();
        RONotes.setRoUser(studentHelper.getUser(Integer.parseInt(Global.strUserId)));
        RONotes.setNoteName(noteName);
        RONotes.setServerNoteId(0);
        RONotes.setLocalNoteId(0);
        RONotes.setIsSync(1);
//        notes.setNoteText(strNoteText);
        RONotes.setCreatedDate(Utility.getDateMySql());
        studentHelper.saveNote(RONotes);
        setUpData();
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

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            switch (apiCode) {
                case WebConstants.GET_ALL_NOTES:
                    //    onResponseGetAllNotes(object, error);
                    break;
//                case WebConstants.EDIT_ABOUT_ME:
//                    onResponseEditAboutMe(object, error);
//                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "onResponse Exception : " + e.toString());
        }
    }

//    private void onResponseGetAllNotes(Object object, Exception error) {
//        try {
//            activityHost.hideProgress();
//            if (object != null) {
//                ResponseHandler responseObj = (ResponseHandler) object;
//                if (responseObj.getStatus().equals(WebConstants.SUCCESS)) {
//                    Log.e(TAG, "onResponseGetAllNotes success");
//                    arraylistNotes = responseObj.getNotes();
//                    setUpData(responseObj.getNotes());
//                } else if (responseObj.getStatus().equals(WebConstants.FAILED)) {
//                    Log.e(TAG, "onResponseGetAllNotes Failed");
//                }
//            } else if (error != null) {
//                Log.e(TAG, "onResponseGetAllNotes api Exception : " + error.toString());
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "onResponseGetAllNotes Exception : " + e.toString());
//        }
//    }

    private void setUpData() {
        try {
            arraylistNotes = studentHelper.getNotes(Integer.parseInt(Global.strUserId));
            jotterNotesAdapter = new JotterNotesAdapter(getActivity(), arraylistNotes, this, lastPosition);
            listView.setAdapter(jotterNotesAdapter);
            setUpNoteDetails(arraylistNotes.get(lastPosition));
        } catch (Exception e) {
            Log.e(TAG, "setUpData Exceptions : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onNoteListener(int position) {
        try {
            lastPosition = position;
            setUpNoteDetails(arraylistNotes.get(position));
        } catch (Exception e) {
            Log.e(TAG, "onNoteListener Exception : " + e.getLocalizedMessage());
        }
    }

    private void setUpNoteDetails(RONotes RONotes) {
        try {

            if (arraylistNotes == null) {
                Utility.hideView(imgEdit);
                Utility.hideView(imgShare);
                Utility.hideView(txtNoteTitle);
                Utility.hideView(txtNoteBy);
                Utility.hideView(rteNotes);
                Utility.hideView(etSubject);
                Utility.hideView(imgUser);
            } else {
                objRONotes = RONotes;
                strNoteId = String.valueOf(RONotes.getLocalNoteId());
                imgEdit.setActivated(false);
                imgShare.setActivated(false);
                txtNoteTitle.setText(RONotes.getNoteName());
                txtNoteBy.setText("By : " + RONotes.getRoUser().getFullName());
                strNoteByID = RONotes.getRoUser().getUserId();
                rteNotes.setHtml(RONotes.getNoteText());
                etSubject.setText(RONotes.getNoteSubject());
                Global.imageLoader.displayImage(WebConstants.HOST_IMAGE_USER + RONotes.getRoUser().getProfilePicture(), imgUser, ISMStudent.options);
            }
        } catch (Exception e) {
            Log.e(TAG, "setNoteDetails Exceptions : " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onTextChange(String text) {
        richtext = text;
    }
}
