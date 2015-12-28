package com.ism.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ism.R;
import com.ism.adapter.PostFileAdapter;
import com.ism.commonsource.view.ActionProcessButton;
import com.ism.commonsource.view.ProgressGenerator;
import com.ism.constant.AppConstant;
import com.ism.constant.WebConstants;
import com.ism.model.PostFileModel;
import com.ism.object.Global;
import com.ism.utility.Debug;
import com.ism.utility.Utility;
import com.ism.views.CircularSeekBar;
import com.ism.views.HorizontalListView;
import com.ism.ws.helper.Attribute;
import com.ism.ws.helper.MediaUploadAttribute;
import com.ism.ws.helper.ResponseHandler;
import com.ism.ws.helper.WebserviceWrapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostFeedActivity extends Activity implements View.OnClickListener, WebserviceWrapper.WebserviceResponse {
    public static final String TAG = PostFeedActivity.class.getSimpleName();
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    private InputMethodManager inputMethod;
    private TextView txtPost, txtCaptue, txtChoose, txtCancel;
    private EditText etSayIt;
    private ImageView imgAudio, imgVideo, imgImage, imgTool, imgKeyboard, imgEmoticons, imgLink, imgStop, imgSave, imgCapture, imgCancel;
    private LinearLayout llContainer, llAudioRecoder;
    public static ArrayList<PostFileModel> arrayList;
    public static PostFileAdapter adapter;
    public static PostFileModel model;
    public static HorizontalListView listview;
    private LinearLayout llImage;
    private LinearLayout llChooseImg, llCaptureImg, llBlank;
    public static int MEDIA_TYPE_IMAGE = 100;
    public static int CAPTURE_IMAGE = 200;
    public static int MEDIA_TYPE_VIDEO = 300;
    public static int CAPTURE_VIDEO = 400;
    public static int RECORD_AUDIO = 500;
    public static int MEDIA_TYPE_AUDIO = 6000;
    private String mFileName, feed_id;
    private Uri uriFile;
    private PostFileAdapter postFileAdapter = new PostFileAdapter();
    private CircularSeekBar seekbar;
    private Handler handler;
    int i = 0;
    private MediaRecorder mRecorder;
    private Runnable r;
    private boolean isRecording = false;
    private int recordTime;
    private MediaPlayer mPlayer;
    private int playTime;
    private boolean isPlay = false;
    private String uploadUri;
    private String mediaType = null;
    private int recoderDone = 0;
    private ProgressGenerator progressGenerator;
    private ActionProcessButton progHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        initLayout();

    }

    private void initLayout() {
        // txtPost = (TextView) findViewById(R.id.txt_post);
        etSayIt = (EditText) findViewById(R.id.et_sayit);
        imgAudio = (ImageView) findViewById(R.id.img_audio);
        imgVideo = (ImageView) findViewById(R.id.img_video);
        imgImage = (ImageView) findViewById(R.id.img_image);
        imgTool = (ImageView) findViewById(R.id.img_tool);
        imgLink = (ImageView) findViewById(R.id.img_link);
        imgStop = (ImageView) findViewById(R.id.img_stop);
        imgSave = (ImageView) findViewById(R.id.img_save);
        imgCancel = (ImageView) findViewById(R.id.img_cancel);
        imgCapture = (ImageView) findViewById(R.id.img_capture);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        llAudioRecoder = (LinearLayout) findViewById(R.id.ll_recordaudio);
        imgKeyboard = (ImageView) findViewById(R.id.img_keyboard);
        imgEmoticons = (ImageView) findViewById(R.id.img_emoticons);
        llChooseImg = (LinearLayout) findViewById(R.id.ll_chooseimg);
        llBlank = (LinearLayout) findViewById(R.id.ll_blank);
        txtCaptue = (TextView) findViewById(R.id.txt_capture);
        txtCancel = (TextView) findViewById(R.id.txt_cancel);
        txtPost = (TextView) findViewById(R.id.txt_post);
        txtChoose = (TextView) findViewById(R.id.txt_choose);
        llCaptureImg = (LinearLayout) findViewById(R.id.ll_captureimg);
        seekbar = (CircularSeekBar) findViewById(R.id.circularSeekBar);
        llCaptureImg.setOnClickListener(this);
        llChooseImg.setOnClickListener(this);
        imgImage.setOnClickListener(this);
        imgAudio.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        imgStop.setOnClickListener(this);
        imgSave.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtPost.setOnClickListener(this);
        imgKeyboard.setOnClickListener(this);
        progressGenerator = new ProgressGenerator();
        progHost = (ActionProcessButton) findViewById(R.id.prog_host);
        arrayList = new ArrayList<>();
        listview = (HorizontalListView) findViewById(R.id.horilistview);
        inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //llBlank.setVisibility(View.VISIBLE);
        imgKeyboard.setBackgroundColor(getResources().getColor(R.color.color_blue));
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        inputMethod.showSoftInput(etSayIt, InputMethodManager.SHOW_IMPLICIT);
    }

    public void showProgress() {
        try {
            if (progHost != null && progHost.getVisibility() != View.VISIBLE) {
                progHost.setProgress(1);
                progHost.setVisibility(View.VISIBLE);
                progressGenerator.start(progHost);
            }
        } catch (Exception e) {
            Log.e(TAG, "showProgress Exception : " + e.toString());
        }
    }

    public void hideProgress() {
        try {
            if (progHost != null && progHost.getVisibility() == View.VISIBLE) {
                progHost.setProgress(100);
                progHost.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "hideProgress Exception : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onClick(View v) {
        if (v == imgImage) {
            toolSelected(v);
            imgCapture.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_camera));
            Toast.makeText(getApplicationContext(), "Images", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            llContainer.setVisibility(View.VISIBLE);
            txtCaptue.setText(getResources().getString(R.string.strcaptureimageusingcamera));
            txtChoose.setText(getResources().getString(R.string.strchooseimagefromgallery));


        } else if (v == imgAudio) {
            toolSelected(v);
            Toast.makeText(getApplicationContext(), "Audio", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            imgCapture.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_recorder));
            llContainer.setVisibility(View.VISIBLE);
            txtCaptue.setText(getResources().getString(R.string.strrecordyouraudio));
            txtChoose.setText(getResources().getString(R.string.strchooseyouraudio));
            //loadFragment(FRAGMENT_AUDIO);
        } else if (v == imgVideo) {
            toolSelected(v);
            imgCapture.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_camera));
            Toast.makeText(getApplicationContext(), "Video", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            llContainer.setVisibility(View.VISIBLE);
            txtCaptue.setText(getResources().getString(R.string.strcapturevideousingcamera));
            txtChoose.setText(getResources().getString(R.string.strchoosevideofromgallery));
            //loadFragment(FRAGMENT_VIDEO);
        } else if (v == llChooseImg) {
            if (txtChoose.getText().toString().equals(getResources().getString(R.string.strchoosevideofromgallery))) {
                if (!checkMediaFile()) {
                    openVideoGallery();
                } else {
                    Utility.showToast(this, "Please select single audio or video for post your feed!");
                }
            } else if (txtChoose.getText().toString().equals(getResources().getString(R.string.strchooseimagefromgallery)))
                OpenImageGallery();
            else if (txtChoose.getText().toString().equals(getResources().getString(R.string.strchooseyouraudio))) {
                if (!checkMediaFile()) {
                    OpenAudio();
                } else {
                    Utility.showToast(this, "Please select single audio or video for post your feed!");
                }
            }

        } else if (v == llCaptureImg) {
            if (txtCaptue.getText().toString().equals(getResources().getString(R.string.strcapturevideousingcamera))) {
                if (!checkMediaFile())
                    captureVideo();
                else {
                    Utility.showToast(this, "Please select single audio or video for post your feed!");
                }
            } else if (txtCaptue.getText().toString().equals(getResources().getString(R.string.strcaptureimageusingcamera)))
                captureImage();
            else if (txtCaptue.getText().toString().equals(getResources().getString(R.string.strrecordyouraudio))) {
                if (!checkMediaFile()) {
                    llContainer.setVisibility(View.GONE);
                    imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_stop));
                    seekbar.setProgress(0);
                    //llBlank.setVisibility(View.GONE);
                    llAudioRecoder.setVisibility(View.VISIBLE);
                    handler = new Handler();
                    i = 0;
                    r = new Runnable() {
                        public void run() {
                            seekbar.getProgress();
                            seekbar.setProgress(i++);
                            handler.postDelayed(this, 60);
                        }
                    };
                    handler.postDelayed(r, 0);
                    startRecording();
                } else {
                    Utility.showToast(this, "Please select single audio or video for post your feed!");
                }

            }
        } else if (v == imgStop) {
            if (isRecording) {
                if (mRecorder != null) {
                    //  mRecorder.stop();
                    //   mRecorder.release();
                    mRecorder = null;
                    handler.removeCallbacks(r);
                    isRecording = false;

                }
                recoderDone = 1;
                seekbar.setProgress(0);
                //handler.removeCallbacks(r);
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_play));
            } else if (recoderDone == 1) {
                playIt();
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_pause));
                recoderDone = 2;
            } else if (recoderDone == 2) {
                playIt();
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_play));
                recoderDone = 1;
            }
        } else if (v == imgSave) {
            model = new PostFileModel("audio", Uri.fromFile(new File(mFileName)), "");
            arrayList.add(model);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
            llAudioRecoder.setVisibility(View.GONE);
            llContainer.setVisibility(View.VISIBLE);
            // //llBlank.setVisibility(View.GONE);
        } else if (v == imgCancel) {
            File file = new File(mFileName);
            file.delete();
            llAudioRecoder.setVisibility(View.GONE);
            llContainer.setVisibility(View.VISIBLE);
            //llBlank.setVisibility(View.GONE);

        } else if (v == imgKeyboard) {
            toolSelected(v);
            //llBlank.setVisibility(View.VISIBLE);
            showKeyboard();
        } else if (v == txtCancel) {
            hideKeyboard();

            super.onBackPressed();
            //delete video or audio or image if capture
        } else if (v == txtPost) {
            hideKeyboard();
            if (etSayIt.getText().toString().length() != 0) {
                callPostFeed();
            } else {
                Utility.showToast(this, "Please Write any message to post your feed!");
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void callPostFeed() {
        String strThumbnailBase64 = null;
        List<String> listImages = new ArrayList<String>();
        try {
            Log.e(TAG + "Arraylist", "" + arrayList);
            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getStrFileType().equals("image")) {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), arrayList.get(i).getStrFilePath());
                        //imgDp.setImageBitmap(bitmap);
                        String strImgBase64 = Utility.getBase64ForImage(bitmap);
                        listImages.add(strImgBase64);
                    } else if (arrayList.get(i).getStrFileType().equals("video")) {
                        MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                        mMediaMetadataRetriever.setDataSource(this, arrayList.get(i).getStrFilePath());
                        Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                        strThumbnailBase64 = Utility.getBase64ForImage(bitmap);
                    }
                }

                Attribute attribute = new Attribute();
                attribute.setFeedBy(Global.strUserId);
                attribute.setImages(null);
                attribute.setVideoLink("");
                attribute.setAudioLink("");
                attribute.setPostedOn(Utility.getDate());
                attribute.setVideoThumbnail(strThumbnailBase64);
                attribute.setFeedText(etSayIt.getText().toString().trim());

                new WebserviceWrapper(this, attribute, this).new WebserviceCaller()
                        .execute(WebConstants.POSTFEED);

            }
        } catch (Exception e) {
            Log.e(TAG, "callPostFeed Exception :" + e.getLocalizedMessage() + "");
        }
    }

//    private Bitmap getImage(int id) {
//
//        Bitmap thumb = MediaStore.Video.Thumbnails.getThumbnail(
//
//                getContentResolver(),
//
//                id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
//
//        return thumb;
//
//    }

    public String getPathImage(Uri uri) {
        String wholeID = DocumentsContract.getDocumentId(uri);
        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public String getPathAudio(Uri uri) {
        String wholeID = DocumentsContract.getDocumentId(uri);
        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Audio.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Audio.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public String getPathVideo(Uri uri) {
        String wholeID = DocumentsContract.getDocumentId(uri);
        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Video.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Video.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public void startRecording() {
        if (!isRecording) {
            //Create MediaRecorder and initialize audio source, output format, and audio encoder
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(audioFileName());
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // Starting record time
            recordTime = 0;
            // Show TextView that displays record time
            // tv.setVisibility(TextView.VISIBLE);
            try {
                mRecorder.prepare();
                // Start record job
                mRecorder.start();
                // Change isRecroding flag to true
                isRecording = true;
                // Post the record progress
                //   handler.post(UpdateRecordTime);
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare failed");
            }


        }
    }

//    Runnable UpdateRecordTime = new Runnable() {
//        public void run() {
//            if (isRecording) {
//                recordTime += 1;
//                // Delay 1s before next call
//                // handler.postDelayed(this, 1000);
//            }
//        }
//    };

    public void stopRecording() {
        if (isRecording) {
            // Stop recording and release resource
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            // Change isRecording flag to false
            isRecording = false;
            // Hide TextView that shows record time
            // tv.setVisibility(TextView.GONE);
            // playIt(); // Play the audio
        }
    }

    public void playIt() {
        // Create MediaPlayer object
        mPlayer = new MediaPlayer();
        if (!isPlay) {
            // set start time
            playTime = 0;
            // Reset max and progress of the SeekBar

            seekbar.setProgress(0);

            try {
                // Initialize the player and start playing the audio
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
                mPlayer.start();
                handler = new Handler();
                i = 0;
                Runnable r = new Runnable() {
                    public void run() {
                        seekbar.getProgress();
                        seekbar.setProgress(i++);
                        handler.postDelayed(this, 60);
                    }
                };
                handler.postDelayed(r, 0);
                // Post the play progress
                // handler.post(UpdatePlayTime);
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare failed");
            }
            isPlay = true;
        } else {
            handler.removeCallbacks(r);
            seekbar.setMax(0);
            isPlay = false;
            mPlayer.pause();
        }

    }

    //    Runnable UpdatePlayTime = new Runnable() {
//        public void run() {
//            if (mPlayer.isPlaying()) {
//                //   tv.setText(String.valueOf(playTime));
//                // Update play time and SeekBar
//                playTime += 1;
//                seekbar.setProgress(playTime);
//                // Delay 1s before next call
//                handler.postDelayed(this, 1000);
//            }
//        }
//    };
//    private void startRecording() {
//        try {
//            mRecorder = new MediaRecorder();
//            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//            mRecorder.setOutputFile(audioFileName());
//            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mRecorder.prepare();
//        } catch (IOException e) {
//            Log.e(TAG, "prepare() failed");
//        }
//        mRecorder.start();
//
//
//      //  handler.postDelayed(r, 0);
//        // Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
//    }
    ProgressDialog pd;

//    private void showProgressDialog() {
//
//        pd = new ProgressDialog(PostActivity.this);
//        pd.setMessage(getResources().getString(R.string.loading));
//        pd.show();
//
//    }

//    private void dismissProgressDialog() {
//        if (pd != null) {
//            pd.dismiss();
//        }
//    }

    @Override
    public void onResponse(Object object, Exception error, int apiCode) {
        try {
            if (apiCode == WebConstants.POSTFEED) {
                onResponsePostFeed(object, error);
            } else if (apiCode == WebConstants.UPLOAD_FEED_MEDIA) {
                onResponsePostFeedMeida(object, error);
            }

        } catch (Exception e) {
            Debug.i(TAG, "onResponse Exception : " + error);
        }
    }

    private void onResponsePostFeedMeida(Object object, Exception error) {
        hideProgress();
        if (object != null) {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                arrayList.clear();
                super.onBackPressed();
            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                Utility.showToast(this, "Please try again!");
            }
        } else if (error != null) {
            Debug.i(TAG, "onResponsePostFeedMeida error : " + error);
        }
    }

    private void onResponsePostFeed(Object object, Exception error) {
        if (object != null) {
            ResponseHandler responseHandler = (ResponseHandler) object;
            if (responseHandler.getStatus().equals(WebConstants.SUCCESS)) {
                feed_id = responseHandler.getFeed().get(0).getFeedId();
                if (arrayList != null) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getStrFileType().equals("video")) {
                            mediaType = "video";
                            uploadUri = getPathVideo(arrayList.get(i).getStrFilePath());
                        } else if (arrayList.get(i).getStrFileType().equals("audio")) {
                            uploadUri = getPathAudio(arrayList.get(i).getStrFilePath());
                            mediaType = "audio";
                        } else if (arrayList.get(i).getStrFileType().equals("image")) {
                            mediaType = "image";
                            uploadUri = getPathImage(arrayList.get(i).getStrFilePath());
                        } else if (arrayList.get(i).getStrFileType().equals("video")) {
                            MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
                            mMediaMetadataRetriever.setDataSource(this, arrayList.get(i).getStrFilePath());
                            Bitmap bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
                            // strThumbnailBase64 = Utility.getBase64ForImage(bitmap);
                        }
                        if (arrayList.get(i).getStrFilePath() != null) {
                            Debug.e(TAG, "Thefile path is:" + uploadUri);
                            callApiForUploadMediaFile(feed_id, mediaType,
                                    uploadUri);
                        }
                    }
                } else {
                    arrayList.clear();
                    super.onBackPressed();
                }

            } else if (responseHandler.getStatus().equals(WebConstants.FAILED)) {
                Utility.showToast(this, "Please try again!");
            }
        } else if (error != null) {
            Debug.i(TAG, "onResponsePostFeed error : " + error);
        }
    }

    private void callApiForUploadMediaFile(String feed_id, String mediaType, String filepath) {
        try {
            Attribute attribute = new Attribute();
            MediaUploadAttribute fileParam = new MediaUploadAttribute();
            fileParam.setParamName("mediaFile");
            fileParam.setFileName(filepath);
            attribute.getArrListFile().add(fileParam);

            Debug.i(TAG, "Media File Path : " + filepath);

            MediaUploadAttribute mediaTypeParam = new MediaUploadAttribute();
            mediaTypeParam.setParamName("mediaType");
            mediaTypeParam.setParamValue(mediaType);
            attribute.getArrListParam().add(mediaTypeParam);


            MediaUploadAttribute feedIdParam = new MediaUploadAttribute();
            feedIdParam.setParamName("feed_id");
            feedIdParam.setParamValue(feed_id);
            attribute.getArrListParam().add(feedIdParam);

            MediaUploadAttribute feedByParam = new MediaUploadAttribute();
            feedByParam.setParamName("feed_by");
            feedByParam.setParamValue(Global.strUserId);
            attribute.getArrListParam().add(feedByParam);
            showProgress();
            new WebserviceWrapper(this, attribute, this).new WebserviceCaller()
                    .execute(WebConstants.UPLOAD_FEED_MEDIA);
        } catch (Exception e) {
            Debug.i(TAG, "callApiForUploadMediaFile Exception : " + e.getLocalizedMessage());


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MEDIA_TYPE_IMAGE && resultCode == RESULT_OK) {
            String path = data.getData().toString();
            Uri uri = data.getData();
            Log.i("uri", uri + "");
            model = new PostFileModel("image", uri, "");
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
            // adapter.notifyDataSetChanged();
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            model = new PostFileModel("image", uriFile, "");
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        } else if (requestCode == MEDIA_TYPE_VIDEO && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e(TAG + "::", "" + Environment.getExternalStorageDirectory());
            Log.e(TAG + "::", "" + Environment.getExternalStorageDirectory() + uri.getPath());
            model = new PostFileModel("video", uri, "");
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        } else if (requestCode == CAPTURE_VIDEO && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.i(TAG, uri.getPath() + "");
            model = new PostFileModel("video", uri, "");
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        } else if (requestCode == MEDIA_TYPE_AUDIO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Log.i(TAG, uri + "");
            listview.setVisibility(View.VISIBLE);
            model = new PostFileModel("audio", uri, "");
            arrayList.add(model);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Video.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void OpenImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, MEDIA_TYPE_IMAGE);
    }

    public void OpenAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, MEDIA_TYPE_AUDIO);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        uriFile = getOutputMediaFileUri(CAPTURE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    public void openVideoGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("video/*");
        startActivityForResult(galleryIntent, MEDIA_TYPE_VIDEO);
//        Intent galleryIntent =new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//        galleryIntent.setType("video/*");
//        startActivityForResult(galleryIntent, MEDIA_TYPE_VIDEO);
    }

    public void toolSelected(View v) {
        llAudioRecoder.setVisibility(View.GONE);
        llContainer.setVisibility(View.GONE);
        ////llBlank.setVisibility(View.GONE);
        imgImage.setBackground(getResources().getDrawable(R.drawable.sidebar));
        imgKeyboard.setBackground(getResources().getDrawable(R.drawable.sidebar));
        imgVideo.setBackground(getResources().getDrawable(R.drawable.sidebar));
        imgAudio.setBackground(getResources().getDrawable(R.drawable.sidebar));
        imgTool.setBackground(getResources().getDrawable(R.drawable.sidebar));
        imgEmoticons.setBackground(getResources().getDrawable(R.drawable.sidebar));
        imgLink.setBackground(getResources().getDrawable(R.drawable.sidebar));
        v.setBackgroundColor(getResources().getColor(R.color.green));
//        if (v == imgImage) {
//            imgImage.setBackgroundColor(getResources().getColor(R.color.color_green));
//        }
    }

    /**
     * Creating file uri to store isImage/isVideo
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning isImage / isVideo
     */
    private static File getOutputMediaFile(int type) {

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == CAPTURE_IMAGE) {
            mediaFile = new File(AppConstant.IMAGE_CAPTURE_PATH + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == CAPTURE_VIDEO) {
            mediaFile = new File(AppConstant.VIDEO_CAPTURE_PATH + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    public String audioFileName() {
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        mFileName = AppConstant.AUDIO_CAPTURE_PATH + File.separator + "AUDIO_" + timeStamp + ".3gpp";
        return mFileName;
    }

    public void makeDirectories() {
        File mediaStorageDir = new File(AppConstant.AUDIO_CAPTURE_PATH + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed create "
                        + "ISM" + " directory");
            }
        }
        mediaStorageDir = new File(AppConstant.IMAGE_CAPTURE_PATH + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed create "
                        + "ISM" + " directory");

            }
        }
        mediaStorageDir = new File(AppConstant.VIDEO_CAPTURE_PATH + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed create "
                        + "ISM" + " directory");
            }
        }

    }

    public boolean checkMediaFile() {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getStrFileType().equals("video")) {
                return true;
            } else if (arrayList.get(i).getStrFileType().equals("audio")) {
                return true;
            }
        }
        return false;
    }

    public void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // create a file to save the isVideo
        uriFile = getOutputMediaFileUri(CAPTURE_VIDEO);
        // set the isImage file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
        // set the isVideo isImage quality to high
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO);
    }

}
