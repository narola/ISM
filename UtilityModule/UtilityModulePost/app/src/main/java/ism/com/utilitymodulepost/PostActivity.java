package ism.com.utilitymodulepost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ism.com.utilitymodulepost.adapter.PostFileAdapter;
import ism.com.utilitymodulepost.helper.HorizontalListView;
import ism.com.utilitymodulepost.model.PostFileModel;


public class PostActivity extends Activity implements View.OnClickListener {
    public static final String TAG = PostActivity.class.getSimpleName();
    private InputMethodManager inputMethod;
    private TextView txtPost;
    private PopupWindow popupWindow;
    private EditText etSayIt;
    private ImageView imgAudio, imgVideo, imgImage, imgTool, imgKeyboard, imgEmoticons, imgLink;
    private LinearLayout llContainer;
    public static final int FRAGMENT_IMAGE = 0;
    public static final int FRAGMENT_VIDEO = 1;
    public static final int FRAGMENT_AUDIO = 2;
    public static ArrayList<PostFileModel> arrayList = new ArrayList<PostFileModel>();
    public static PostFileAdapter adapter;
    public static PostFileModel model;
    public static HorizontalListView listview;
    private LinearLayout llImage;
    private LinearLayout llChooseImg, llCaptureImg;
    public static int MEDIA_TYPE_IMAGE = 100;
    public static int CAPTURE_IMAGE = 200;
    public static int CAPTURE_VIDEO = 400;
    private String mFileName;
    private Uri uriFile;
    private PostFileAdapter postFileAdapter=new PostFileAdapter();

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
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        imgKeyboard = (ImageView) findViewById(R.id.img_keyboard);
        imgEmoticons = (ImageView) findViewById(R.id.img_emoticons);
        llChooseImg = (LinearLayout) findViewById(R.id.ll_chooseimg);
        llCaptureImg = (LinearLayout) findViewById(R.id.ll_captureimg);
        llCaptureImg.setOnClickListener(this);
        llChooseImg.setOnClickListener(this);
        imgImage.setOnClickListener(this);
        imgAudio.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        listview = (HorizontalListView) findViewById(R.id.horilistview);
        inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            Toast.makeText(getApplicationContext(), "Images", Toast.LENGTH_SHORT).show();
            hideKeyboard();


        } else if (v == imgAudio) {
            toolSelected(v);
            Toast.makeText(getApplicationContext(), "Audio", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            //loadFragment(FRAGMENT_AUDIO);
        } else if (v == imgVideo) {
            toolSelected(v);
            Toast.makeText(getApplicationContext(), "Video", Toast.LENGTH_SHORT).show();
            hideKeyboard();
            //loadFragment(FRAGMENT_VIDEO);
        } else if (v == llChooseImg) {

            OpenImageGallery();
        } else if (v == llCaptureImg) {
            captureImage();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MEDIA_TYPE_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            String path = data.getData().toString();
            Uri uri=data.getData();
            Log.i("uri", uri + "");
            if(arrayList.size()==0){
                model = new PostFileModel("image", uri);
                arrayList.add(model);
            }
            model = new PostFileModel("image", uri);
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
            // adapter.notifyDataSetChanged();
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            if(arrayList.size()==0){
                model = new PostFileModel("image", uriFile);
                arrayList.add(model);
            }
            model = new PostFileModel("image", uriFile);
            arrayList.add(model);
            listview.setVisibility(View.VISIBLE);
            listview.setAdapter(new PostFileAdapter(arrayList, getApplicationContext()));
        }
    }

    public void OpenImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MEDIA_TYPE_IMAGE);
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        uriFile = getOutputMediaFileUri(CAPTURE_IMAGE);
        // set the isImage file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFile);
        // set the isVideo isImage quality to high
        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    public void toolSelected(View v) {
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
}
