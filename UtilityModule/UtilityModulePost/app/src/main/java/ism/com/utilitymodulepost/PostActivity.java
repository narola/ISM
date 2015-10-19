package ism.com.utilitymodulepost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ism.com.utilitymodulepost.adapter.PostFileAdapter;
import ism.com.utilitymodulepost.helper.CircularSeekBar;
import ism.com.utilitymodulepost.helper.HorizontalListView;
import ism.com.utilitymodulepost.model.PostFileModel;


public class PostActivity extends Activity implements View.OnClickListener {
    public static final String TAG = PostActivity.class.getSimpleName();
    private InputMethodManager inputMethod;
    private TextView txtPost, txtCaptue, txtChoose;
    private PopupWindow popupWindow;
    private EditText etSayIt;
    private ImageView imgAudio, imgVideo, imgImage, imgTool, imgKeyboard, imgEmoticons, imgLink, imgStop, imgSave, imgCapture, imgCancel;
    private LinearLayout llContainer, llAudioRecoder;
    public static final int FRAGMENT_IMAGE = 0;
    public static final int FRAGMENT_VIDEO = 1;
    public static final int FRAGMENT_AUDIO = 2;
    public static ArrayList<PostFileModel> arrayList = new ArrayList<PostFileModel>();
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
    private String mFileName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        initLayout();

//        txtPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow();
//            }
//        });
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
        imgKeyboard.setOnClickListener(this);
        listview = (HorizontalListView) findViewById(R.id.horilistview);
        inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        llBlank.setVisibility(View.VISIBLE);
    }

//    private void popupWindow() {
//        popupWindow = new PopupWindow(getApplicationContext());
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        //  arrayList = new ArrayList<MediaFilesModel>();
//        View view = inflater.inflate(R.layout.layout_post, null);
//        popupWindow = new PopupWindow(view,
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT, true);
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setSplitTouchEnabled(true);
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        etSayIt = (EditText) view.findViewById(R.id.et_sayit);
//        imgAudio = (ImageView) view.findViewById(R.id.img_audio);
//        imgVideo = (ImageView) view.findViewById(R.id.img_video);
//        imgImage = (ImageView) view.findViewById(R.id.img_image);
//        imgTool = (ImageView) view.findViewById(R.id.img_tool);
//        imgLink = (ImageView) view.findViewById(R.id.img_link);
//        imgKeyboard = (ImageView) view.findViewById(R.id.img_keyboard);
//        imgEmoticons = (ImageView) view.findViewById(R.id.img_emoticons);
//        imgImage.setOnClickListener(this);
//
//
//    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showKeyboard() {
        inputMethod.showSoftInput(etSayIt, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onClick(View v) {
        if (v == imgImage) {
            toolSelected(v);
            imgCapture.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgcamera));
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
            imgCapture.setBackgroundDrawable(getResources().getDrawable(R.drawable.imgcamera));
            Toast.makeText(getApplicationContext(), "Video", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            llContainer.setVisibility(View.VISIBLE);
            txtCaptue.setText(getResources().getString(R.string.strcapturevideousingcamera));
            txtChoose.setText(getResources().getString(R.string.strchoosevideofromgallery));
            //loadFragment(FRAGMENT_VIDEO);
        } else if (v == llChooseImg) {
            if (txtChoose.getText().toString().equals(getResources().getString(R.string.strchoosevideofromgallery))) {
                openVideoGallery();
            } else if (txtChoose.getText().toString().equals(getResources().getString(R.string.strchooseimagefromgallery)))
                OpenImageGallery();
            else if (txtChoose.getText().toString().equals(getResources().getString(R.string.strchooseyouraudio))) {
                OpenAudio();
            }

        } else if (v == llCaptureImg) {
            if (txtCaptue.getText().toString().equals(getResources().getString(R.string.strcapturevideousingcamera))) {
                captureVideo();
            } else if (txtCaptue.getText().toString().equals(getResources().getString(R.string.strcaptureimageusingcamera)))
                captureImage();
            else if (txtCaptue.getText().toString().equals(getResources().getString(R.string.strrecordyouraudio))) {
                llContainer.setVisibility(View.GONE);
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_stop));
                seekbar.setProgress(0);
                llBlank.setVisibility(View.GONE);
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
                seekbar.setProgress(0);
                //handler.removeCallbacks(r);
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_play));
            } else if (!isRecording && isPlay) {
                playIt();
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_pause));
            } else if (!isRecording && !isPlay) {
                playIt();
                imgStop.setBackgroundDrawable(getResources().getDrawable(R.drawable.audio_play));
            }
        } else if (v == imgSave) {
            model = new PostFileModel("audio", Uri.fromFile(new File(mFileName)));
            arrayList.add(model);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
            llAudioRecoder.setVisibility(View.GONE);
            llContainer.setVisibility(View.VISIBLE);
            llBlank.setVisibility(View.GONE);
        } else if (v == imgCancel) {
            File file = new File(mFileName);
            file.delete();
            llAudioRecoder.setVisibility(View.GONE);
            llContainer.setVisibility(View.VISIBLE);
            llBlank.setVisibility(View.GONE);

        }else if(v==imgKeyboard){
            toolSelected(v);
            llBlank.setVisibility(View.VISIBLE);
            showKeyboard();
        }
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
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare failed");
            }
            // Start record job
            mRecorder.start();
            // Change isRecroding flag to true
            isRecording = true;
            // Post the record progress
            //   handler.post(UpdateRecordTime);

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
            seekbar.setMax(recordTime);
            seekbar.setProgress(0);
            try {
                // Initialize the player and start playing the audio
                mPlayer.setDataSource(mFileName);
                mPlayer.prepare();
                mPlayer.start();
                // Post the play progress
                // handler.post(UpdatePlayTime);
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare failed");
            }
            isPlay = true;
        } else {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MEDIA_TYPE_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            String path = data.getData().toString();
            Uri uri = data.getData();
            Log.i("uri", uri + "");

            model = new PostFileModel("image", uri);
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
            // adapter.notifyDataSetChanged();
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();

            model = new PostFileModel("image", uriFile);
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        } else if (requestCode == MEDIA_TYPE_VIDEO && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            Log.i(TAG, uri.getPath() + "");
            model = new PostFileModel("video", uri);
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        } else if (requestCode == CAPTURE_VIDEO && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            Log.i(TAG, uri.getPath() + "");
            model = new PostFileModel("video", uri);
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        } else if (requestCode == MEDIA_TYPE_AUDIO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            listview.setVisibility(View.VISIBLE);
            model = new PostFileModel("audio", uri);
            arrayList.add(model);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        }
    }

    public void OpenImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MEDIA_TYPE_IMAGE);
    }

    public void OpenAudio() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MEDIA_TYPE_AUDIO);
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
    }

    public void toolSelected(View v) {
        llAudioRecoder.setVisibility(View.GONE);
        llContainer.setVisibility(View.GONE);
        llBlank.setVisibility(View.GONE);
        imgImage.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        imgKeyboard.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        imgVideo.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        imgAudio.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        imgTool.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        imgEmoticons.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        imgLink.setBackground(getResources().getDrawable(R.drawable.bg_icon));
        v.setBackgroundColor(getResources().getColor(R.color.color_green));
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
            mediaFile = new File(AppConstant.imageCapturePath + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == CAPTURE_VIDEO) {
            mediaFile = new File(AppConstant.videoCapturePath + File.separator
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
        mFileName = AppConstant.audioCapturePath + File.separator + "AUDIO_" + timeStamp + ".3gpp";
        return mFileName;
    }

    public void makeDirectories() {
        File mediaStorageDir = new File(AppConstant.audioCapturePath + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed create "
                        + "ISM" + " directory");
            }
        }
        mediaStorageDir = new File(AppConstant.imageCapturePath + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed create "
                        + "ISM" + " directory");

            }
        }
        mediaStorageDir = new File(AppConstant.videoCapturePath + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed create "
                        + "ISM" + " directory");
            }
        }

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
