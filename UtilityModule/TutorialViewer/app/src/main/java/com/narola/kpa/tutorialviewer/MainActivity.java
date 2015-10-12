package com.narola.kpa.tutorialviewer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private VideoView mVideoView;
    private MediaPlayer mMediaPlayer;

    private SurfaceView mPreview;
    private SurfaceHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGlobal();

    }

    private void initGlobal() {
        mVideoView = (VideoView) findViewById(R.id.videoview);
        mPreview = (SurfaceView) findViewById(R.id.surface);

        mHolder = mPreview.getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.e(TAG, "surfaceCreated");
                if (mMediaPlayer != null) {
                    mMediaPlayer.setDisplay(mHolder);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e(TAG, "surfaceDestroyed");
            }
        });


        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sm_long));
//        mVideoView.setVideoURI(Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"));
        MediaController mediaController = new MediaController(this);
        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        mVideoView.setMediaController(mediaController);
        mVideoView.requestFocus();
        mVideoView.start();


        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sm_long));
//            mMediaPlayer.setDataSource(this, Uri.parse("http://techslides.com/demos/sample-videos/small.mp4"));
//            mMediaPlayer = MediaPlayer.create(this, R.raw.videoviewdemo);
//            mMediaPlayer.setDisplay(mHolder);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "MediaPlayer ArgumentException : " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "MediaPlayer IOException : " + e.toString());
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mVideoView.suspend();
    }
}
