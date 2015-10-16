package com.ism.post;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ism.R;
import com.ism.helper.HorizontalListView;
import com.ism.login.AppConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by c162 on 13/10/15.
 */
public class CreatePostActivity extends Activity {
    private Button btn_post;
    private PopupWindow popupWindow;
    private android.view.ViewGroup parent;
    private Bitmap bitmap;
    private HorizontalListView listviewMedia;
    private static final String TAG = CreatePostActivity.class.getSimpleName();
    private MediaFileAdapter mediaFileAdapter;
    ArrayList<MediaFilesModel> arrayList = new ArrayList<MediaFilesModel>();
    private MediaFilesModel mediaFilesModel;
    boolean isImage = false, isVideo = false, isAudio = false;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static CreatePostActivity ActivityContext = null;
    private static final int CAPTURE_VIDEO_CODE = 200;
    private static final int CAPTURE_IMAGE = 100;
    private Uri fileUri;
    private MediaRecorder mRecorder;
    private String mFileName;
    private long startTime = 0L;
    TextView txtTimer;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;

    long timeSwapBuff = 0L;

    long updatedTime = 0L;
    private static final int MEDIA_TYPE_AUDIO=3;
    private static final int RECORD_AUDIO=300;
    private LinearLayout llListView,llAudio;
    private TextView txtSave,txtRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        initLayout();

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDisplay();
            }
        });
    }

    private void initLayout() {
        ActivityContext = this;
        mediaFileAdapter = new MediaFileAdapter(arrayList, getApplicationContext());
        btn_post = (Button) findViewById(R.id.btn_post);
        makeDirectories();
    }

    public void popupDisplay() {
        popupWindow = new PopupWindow(getApplicationContext());
        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_create_post, null);
        popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSplitTouchEnabled(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ImageView imgImage = (ImageView) view
                .findViewById(R.id.img_image);
        ImageView imgAudio = (ImageView) view
                .findViewById(R.id.img_audio);
        ImageView imgVideo = (ImageView) view
                .findViewById(R.id.video);
        ImageView imgSmiley = (ImageView) view
                .findViewById(R.id.img_smiley);
        EditText i = (EditText) view
                .findViewById(R.id.et_posttext);
         llAudio=(LinearLayout)view.findViewById(R.id.ll_audio);
         llListView=(LinearLayout)view.findViewById(R.id.ll_listview);
         txtRecord = (TextView) view.findViewById(R.id.txt_record);
         txtSave = (TextView) view.findViewById(R.id.txt_save);
        txtTimer = (TextView) view.findViewById(R.id.txt_timer);
        llAudio.setVisibility(View.GONE);
        llListView.setVisibility(View.VISIBLE);
        listviewMedia = (HorizontalListView) view
                .findViewById(R.id.lv_mediafiles);
        listviewMedia.setDividerWidth(3);
        imgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVideo = false;
                isImage = true;
                llAudio.setVisibility(View.GONE);
                llListView.setVisibility(View.VISIBLE);
                Image_Picker_Dialog();
            }
        });
        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVideo = true;
                isImage = false;
                llAudio.setVisibility(View.GONE);
                llListView.setVisibility(View.VISIBLE);
                Image_Picker_Dialog();
            }
        });
        imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAudio = true;
                isImage = false;
                isVideo = false;
                txtRecord.setText("Record");
                txtTimer.setText("00:00:00");
                Image_Picker_Dialog();

            }
        });

        txtRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtRecord.getText().toString().equals("Record")) {
                    txtRecord.setText("Stop");
                    startRecording();
                    startTime = SystemClock.uptimeMillis();

                    customHandler.postDelayed(updateTimerThread, 0);

                } else if (txtRecord.getText().toString().equals("Stop")) {
                    txtSave.setText("Save");
                    txtSave.setVisibility(View.VISIBLE);
                    txtRecord.setText("Play");
                    if (mRecorder != null) {
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder = null;
                        customHandler.removeCallbacks(updateTimerThread);
                    }
                } else if (txtRecord.getText().toString().equals("Play")) {
                    txtRecord.setText("Stop");
                    MediaPlayer m = new MediaPlayer();
                    try {
                        m.setDataSource(mFileName);
                        m.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    m.start();
                }
            }
        });
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addImage(bitmap,new File(mFileName));
                llListView.setVisibility(View.VISIBLE);
                llAudio.setVisibility(View.GONE);
            }
        });

    }

    private void startRecording() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecorder.setOutputFile(audioFileName());
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        mRecorder.start();
        // Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
    }

    public void openGallary() {
        if (isImage == true) {
            Intent intent = new Intent();
            intent.setType("isImage/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), MEDIA_TYPE_IMAGE);
        } else if (isVideo == true) {
            final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("isVideo/*");
            startActivityForResult(galleryIntent, MEDIA_TYPE_VIDEO);
        }else if (isAudio){
            Intent intent_upload = new Intent();
            intent_upload.setType("audio/*");
            intent_upload.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent_upload,MEDIA_TYPE_AUDIO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MEDIA_TYPE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Log.i(TAG, uri.getPath()+"");
               addImage(bitmap,new File(uri.getPath()));
                // imgDp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            fileUri = getOutputMediaFileUri(CAPTURE_IMAGE);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File file = getOutputMediaFile(CAPTURE_IMAGE);
            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            addImage(thumbnail,file);

        } else if (requestCode == MEDIA_TYPE_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Log.i(TAG, uri.getPath()+"");
            MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
            mMediaMetadataRetriever.setDataSource(getApplicationContext(), uri);
            bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
            addImage(bitmap,new File(uri.getPath()));
        } else if (requestCode == CAPTURE_VIDEO_CODE && resultCode == RESULT_OK) {

            MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
            mMediaMetadataRetriever.setDataSource(getApplicationContext(), data
                    .getData());
            bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
            addImage(bitmap,new File(data.getData().getPath()));
        }else if (requestCode == MEDIA_TYPE_AUDIO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
            mMediaMetadataRetriever.setDataSource(getApplicationContext(), uri);
            bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
            addImage(bitmap,new File(uri.getPath()));
        }else if (requestCode == RECORD_AUDIO && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri uri = data.getData();
//
//            MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
//            mMediaMetadataRetriever.setDataSource(getApplicationContext(), uri);
//            bitmap = mMediaMetadataRetriever.getFrameAtTime(1 * 1000);
//            addImage(bitmap,new File(uri.getPath()));
        }
    }

    public void Image_Picker_Dialog() {

        final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        if (isVideo)
            myAlertDialog.setTitle("Add Video");
        else if (isImage)
            myAlertDialog.setTitle("Add Image");
        else if (isAudio) {
            myAlertDialog.setTitle("Add Audio");
            myAlertDialog.setPositiveButton("Audio", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    openGallary();
                }
            });

            myAlertDialog.setNegativeButton("Record", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    openCameraPreview();
                }
            });
            myAlertDialog.show();
        }
       if(isVideo||isImage){
           myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface arg0, int arg1) {
                   openGallary();
               }
           });

           myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface arg0, int arg1) {
                   openCameraPreview();
               }
           });
           myAlertDialog.show();
       }

    }

    /**
     * Launching camera app to capture isImage
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    private void openCameraPreview() {
        if (isImage == true) {
            captureImage();
        } else if (isVideo == true) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            // create a file to save the isVideo
            fileUri = getOutputMediaFileUri(CAPTURE_VIDEO_CODE);
            // set the isImage file name
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // set the isVideo isImage quality to high
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            // start the Video Capture Intent
            startActivityForResult(intent, CAPTURE_VIDEO_CODE);
        }
        else if(isAudio){
            llListView.setVisibility(View.GONE);
            llAudio.setVisibility(View.VISIBLE);
            txtSave.setVisibility(View.GONE);
        }
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
        } else if (type == CAPTURE_VIDEO_CODE) {
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
                Log.d(TAG, "Oops! Failed create "
                        + "ISM" + " directory");

            }
        }
        mediaStorageDir = new File(AppConstant.imageCapturePath + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + "ISM" + " directory");

            }
        }
        mediaStorageDir = new File(AppConstant.videoCapturePath + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + "ISM" + " directory");

            }
        }

    }

    public void addImage(Bitmap bitmap,File file) {
        if (isVideo)
            mediaFilesModel = new MediaFilesModel("isVideo", bitmap,file);
        else if (isImage)
            mediaFilesModel = new MediaFilesModel("isImage", bitmap,file);
        else if (isAudio)
            mediaFilesModel = new MediaFilesModel("isAudio", bitmap,file);
        arrayList.add(mediaFilesModel);
        mediaFileAdapter = new MediaFileAdapter(arrayList, getApplicationContext());
        listviewMedia.setAdapter(mediaFileAdapter);
        mediaFileAdapter.notifyDataSetChanged();

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hour = mins / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            txtTimer.setText("" + String.format("%02d", hour) + ":" + "" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            //   + ":"                            + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };


}