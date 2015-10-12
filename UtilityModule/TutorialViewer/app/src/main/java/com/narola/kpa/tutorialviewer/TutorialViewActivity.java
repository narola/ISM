package com.narola.kpa.tutorialviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.narola.kpa.tutorialviewer.view.TutorialView;

public class TutorialViewActivity extends AppCompatActivity {

    private static final String TAG = TutorialViewActivity.class.getSimpleName();

    private TutorialView mTutorialView;
    private int mVideoPosition = 0;
    private boolean mIsVideoPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_view);

        initGlobal();

    }

    private void initGlobal() {
        mTutorialView = (TutorialView) findViewById(R.id.tutorial_view);

//        mTutorialView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.videoviewdemo, "1", "Android tutorial view", true);
        mTutorialView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.sm_long, "1", "Android tutorial view", true);
//        mTutorialView.setVideoUri(Uri.parse("http://192.168.1.202/pg/sm_long.3gp"), "1", "Android tutorial view", false);

        mTutorialView.setOnNextClickListener(new TutorialView.OnNextClickListener() {
            @Override
            public void onClick() {
                Log.e(TAG, "Next clicked");
            }
        });

        mTutorialView.setOnPreviousClickListener(new TutorialView.OnPreviousClickListener() {
            @Override
            public void onClick() {
                Log.e(TAG, "Previous clicked");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoPosition = mTutorialView.getCurrentVideoPosition();
        mIsVideoPlaying = mTutorialView.isVideoPlaying();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTutorialView.seekVideoTo(mVideoPosition);
        if (mIsVideoPlaying) {
            mIsVideoPlaying = false;
            mTutorialView.playVideo();
        }
    }
}
