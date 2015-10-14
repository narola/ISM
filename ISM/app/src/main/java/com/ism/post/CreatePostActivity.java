package com.ism.post;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

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
    boolean image = false, video = false;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static CreatePostActivity ActivityContext = null;
    private static final int CAPTURE_VIDEO_CODE = 200;
    private static final int CAPTURE_IMAGE = 100;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post);
        ActivityContext = this;
        mediaFileAdapter = new MediaFileAdapter(arrayList, getApplicationContext());
        btn_post = (Button) findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDisplay();
            }
        });
    }

    public void popupDisplay() {
        popupWindow = new PopupWindow(getApplicationContext());
        // inflate your layout or dynamically add view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.layout_create_post, null);
        popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
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
        listviewMedia = (HorizontalListView) view
                .findViewById(R.id.lv_mediafiles);
        listviewMedia.setDividerWidth(3);
        imgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video = false;
                image = true;
                Image_Picker_Dialog();
            }
        });
        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video = true;
                image = false;
                Image_Picker_Dialog();
            }
        });

    }

    public void openGallary() {
        if (image == true) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), MEDIA_TYPE_IMAGE);
        } else if (video == true) {
            final Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("video/*");
            startActivityForResult(galleryIntent, MEDIA_TYPE_VIDEO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == MEDIA_TYPE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Log.d(TAG, String.valueOf(bitmap));
                mediaFilesModel = new MediaFilesModel("image", bitmap);
                arrayList.add(mediaFilesModel);
                listviewMedia.setAdapter(mediaFileAdapter);
                mediaFileAdapter.notifyDataSetChanged();
                // imgDp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            addImage(thumbnail);
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


        } else if (requestCode == MEDIA_TYPE_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Log.d(TAG, String.valueOf(bitmap));
                addImage(bitmap);
                // imgDp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAPTURE_VIDEO_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Log.i("", "" + data
                    .getData());
        }
    }

    public void Image_Picker_Dialog() {

        final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Add Photo");
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

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    private void openCameraPreview() {
        if (image == true) {
            captureImage();
        } else if (video == true) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            // create a file to save the video
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
            // set the image file name
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            // set the video image quality to high
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            // start the Video Capture Intent
            startActivityForResult(intent, CAPTURE_VIDEO_CODE);
//            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//            startActivityForResult(intent, 4);
        }
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(AppConstant.imageCapturePath + File.separator);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + "ISM" + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == CAPTURE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == CAPTURE_VIDEO_CODE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    /************
     * Convert Image Uri path to physical path
     **************/

    public static String convertImageUriToFile(Uri imageUri, Activity activity) {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {
                //  imageDetails.setText("No Image");
            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    String Path = cursor.getString(file_ColumnIndex);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + Path + "\n";

                    // Show Captured Image detail on activity
                    // imageDetails.setText( CapturedImageDetails );

                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return "" + imageID;
    }
public void addImage(Bitmap bitmap){
    mediaFilesModel = new MediaFilesModel("image", bitmap);
    arrayList.add(mediaFilesModel);
    mediaFileAdapter = new MediaFileAdapter(arrayList, getApplicationContext());
    listviewMedia.setAdapter(mediaFileAdapter);
    mediaFileAdapter.notifyDataSetChanged();

}
}