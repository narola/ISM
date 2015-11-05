package com.ism.teacher.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ism.teacher.R;
import com.ism.teacher.Utility.Utils;
import com.ism.teacher.adapters.Adapters;

import java.util.Arrays;
import java.util.List;

/**
 * Created by c166 on 31/10/15.
 */
public class QuestionAddEditFragment extends Fragment {


    private static final String TAG = QuestionAddEditFragment.class.getSimpleName();
    private View view;
    Fragment mFragment;
    private static final String ARG_FRAGMENT = "fragment";

    //Views
    private EditText etQuestionTitle, etAnswerBox, etEvaluationNotes, etSolution;
    private ImageView imgEditQuestion, imgCopyQuestion, imgDeleteQuestion;
    private Spinner spQuestionType;
    private Button btnSaveQuestion, btnSaveNAddmoreQuestion;
    private CheckBox chkAddQuestionToPreview;

    private List<String> arrayListQuestionType;

    private LinearLayout ll_questions_options;
    int row_count = 2;
    ImageView imgAdd, img_remove_row;

    RelativeLayout rl_image;
    ImageView img_add_pics, img_play;

    public QuestionAddEditFragment(Fragment fragment) {
        this.mFragment = fragment;
    }


    public QuestionAddEditFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question_addedit, container, false);
        initGlobal();

        Utils.showToast("THE QUESTION ADD EDIT FRAGMENT CALLED", getActivity());
        return view;

    }

    private void initGlobal() {
        rl_image = (RelativeLayout) view.findViewById(R.id.rl_image);
        etQuestionTitle = (EditText) view.findViewById(R.id.et_question_title);
        etAnswerBox = (EditText) view.findViewById(R.id.et_answer_box);
        etEvaluationNotes = (EditText) view.findViewById(R.id.et_evaluation_notes);
        etSolution = (EditText) view.findViewById(R.id.et_solution);

        img_add_pics = (ImageView) view.findViewById(R.id.img_add_pics);
        img_play = (ImageView) view.findViewById(R.id.img_play);

        imgEditQuestion = (ImageView) view.findViewById(R.id.img_edit_question);
        imgCopyQuestion = (ImageView) view.findViewById(R.id.img_copy_question);
        imgDeleteQuestion = (ImageView) view.findViewById(R.id.img_delete_question);

        spQuestionType = (Spinner) view.findViewById(R.id.sp_question_type);
        chkAddQuestionToPreview = (CheckBox) view.findViewById(R.id.chk_add_to_preview);

        btnSaveQuestion = (Button) view.findViewById(R.id.btn_save_question);
        btnSaveNAddmoreQuestion = (Button) view.findViewById(R.id.btn_save_n_addmore_question);

        arrayListQuestionType = Arrays.asList(getResources().getStringArray(R.array.question_type));
        Adapters.setUpExamTypeSpinner(getActivity(), spQuestionType, arrayListQuestionType);

        ll_questions_options = (LinearLayout) view.findViewById(R.id.ll_questions_options);

        spQuestionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //For MCQ
                if (spQuestionType.getSelectedItemPosition() == 0) {
                    etAnswerBox.setVisibility(View.VISIBLE);
                    etAnswerBox.setText("");
                    etAnswerBox.setMinLines(2);

                    ll_questions_options.removeAllViews();

                } else if (spQuestionType.getSelectedItemPosition() == 1) {
                    etAnswerBox.setVisibility(View.VISIBLE);
                    etAnswerBox.setText("");
                    etAnswerBox.setMinLines(5);
                    ll_questions_options.removeAllViews();
                } else {
                    etAnswerBox.setVisibility(View.GONE);
                    for (int j = 1; j <= 2; j++) {
                        View mcq = getMCQInflaterView(j);
                        ll_questions_options.addView(mcq);
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("*/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    Uri selectedUri = null;
    private String selectedImagePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            selectedUri = data.getData();
            //Do whatever that you desire here. or leave this blank
            String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.MIME_TYPE};

            Cursor cursor = getActivity().getContentResolver().query(selectedUri, columns, null, null, null);
            cursor.moveToFirst();

            int pathColumnIndex = cursor.getColumnIndex(columns[0]);
            int mimeTypeColumnIndex = cursor.getColumnIndex(columns[1]);

            String contentPath = cursor.getString(pathColumnIndex);
            String mimeType = cursor.getString(mimeTypeColumnIndex);
            cursor.close();

            if (mimeType.startsWith("image")) {
                img_add_pics.setImageURI(selectedUri);
                Utils.showToast("Image", getActivity());
            } else if (mimeType.startsWith("video")) {
//                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
//                mMediaMetadataRetriever.setDataSource(getActivity(), selectedUri);
////                Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1* 1000);
//                Log.e("uri path",selectedUri.toString());

                String path = getRealPathFromURI(getActivity(), selectedUri);
                Log.e("file path", path);

//                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);

                Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_remove_row);
                Bitmap bitmap = ThumbnailUtils.extractThumbnail(icon,50,50);

                img_add_pics.setImageBitmap(bitmap);
                img_play.setVisibility(View.VISIBLE);
            }

        }


    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private View getMCQInflaterView(int row) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View v;
        v = layoutInflater.inflate(R.layout.question_options_layout, null, false);
        EditText editText = (EditText) v.findViewById(R.id.et_question_options);
        imgAdd = (ImageView) v.findViewById(R.id.img_add_row);
        img_remove_row = (ImageView) v.findViewById(R.id.img_remove_row);

        if (row == row_count) {
            imgAdd.setVisibility(View.VISIBLE);
        } else {
            imgAdd.setVisibility(View.INVISIBLE);
            img_remove_row.setVisibility(View.INVISIBLE);
        }

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgAdd.setVisibility(View.INVISIBLE);
                img_remove_row.setVisibility(View.INVISIBLE);
                row_count++;
                View v = getMCQInflaterView(row_count);
                ll_questions_options.addView(v);
                imgAdd.setVisibility(View.VISIBLE);
                img_remove_row.setVisibility(View.VISIBLE);


            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgAdd.setVisibility(View.INVISIBLE);
                img_remove_row.setVisibility(View.INVISIBLE);
                row_count++;
                View v = getMCQInflaterView(row_count);
                ll_questions_options.addView(v);
                imgAdd.setVisibility(View.VISIBLE);
                img_remove_row.setVisibility(View.VISIBLE);
            }
        });

       /* img_remove_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_questions_options.removeViewAt(row_count);
                row_count--;
            }
        });*/


        return v;
    }


}
