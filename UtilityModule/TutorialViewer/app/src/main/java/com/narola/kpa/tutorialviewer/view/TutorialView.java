package com.narola.kpa.tutorialviewer.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.narola.kpa.tutorialviewer.adapter.NoteAdapter;
import com.narola.kpa.tutorialviewer.R;
import com.narola.kpa.tutorialviewer.database.model.Note;
import com.narola.kpa.tutorialviewer.object.Global;
import com.narola.kpa.tutorialviewer.utility.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.HashMap;

import io.realm.RealmResults;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by Krunal Panchal on 25/09/15.
 */
public class TutorialView extends RelativeLayout implements NoteAdapter.NoteAdapterListener {

    private static final String TAG = TutorialView.class.getSimpleName();

    private RelativeLayout mLayoutControllerOne;
    private RelativeLayout mLayoutControllerTwo;
    private RelativeLayout mLayoutVideo;
    private RelativeLayout mLayoutNoteEditor;
    private RelativeLayout mLayoutNewNote;
    private RelativeLayout mLayoutNoteList;
    private LinearLayout mLayoutControllerThree;
    private LinearLayout mLayoutNotes;
    private TextView mTextTimeCurrent;
    private TextView mTextTimeTotal;
    private TextView mTextTutorialTitle;
    private TextView mTextNoteTime;
    private TextView mTextGetVideo;
    private SeekBar mSeekVideoProgress;
    private SeekBar mSeekVolume;
    private ImageView mImageToggleTutorialView;
    private ImageView mImagePrevious;
    private ImageView mImagePreviousSmall;
    private ImageView mImageRewind;
    private ImageView mImageVolumeUp;
    private ImageView mImageVolumeDown;
    private ImageView mImagePlayPause;
    private ImageView mImagePlayPauseSmall;
    private ImageView mImageForward;
    private ImageView mImageNext;
    private ImageView mImageNextSmall;
    private ImageView mImageVideoThumbnail;
    private ImageView mImageNoteDelete;
    private ImageView mImageNoteShare;
    private ImageView mImageNewNote;
    private View mViewDivider1;
    private View mViewDivider2;
    private FrameLayout mFrameVideo;
    private VideoView mVideoView;
    private Button mButtonAssignments;
    private Button mButtonMyNotes;
    private EditText mEditNewNote;
    private ListView mListNotes;

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private OnNextClickListener mOnNextClickListener;
    private OnPreviousClickListener mOnPreviousClickListener;
    private Animation mAnimationSlideUp;
    private Animation mAnimationSlideDown;
    private MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();
    private FFmpegMediaMetadataRetriever mFFmpegMediaMetadataRetriever = new FFmpegMediaMetadataRetriever();
    private RealmResults<Note> mNotes;
    private NoteAdapter mNoteAdapter;
    private Note mCurrentNote;

    private static final int SHOW_PROGRESS = 1;
    private int mTotalDuration = 0;
    private boolean mIsDragging = false;
    private boolean mStartImmediately = false;
    private boolean mIsVideoUriSet = false;
    private boolean mIsLocalVideoFile = false;
    private String mTutorialId;
    private String mTutorialTitle;

    public interface OnNextClickListener {
        void onClick();
    }

    public interface OnPreviousClickListener {
        void onClick();
    }

    public TutorialView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        /*TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TutorialView);
        try {
            int attribute = typedArray.getInteger(R.styleable.TutorialView_attribute, 0);
        } finally {
            typedArray.recycle();
        }*/

        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.view_tutorial, this);

        mAnimationSlideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
        mAnimationSlideDown = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayoutControllerOne = (RelativeLayout) findViewById(R.id.layout_controller_one);
        mLayoutControllerTwo = (RelativeLayout) findViewById(R.id.layout_controller_two);
        mLayoutVideo = (RelativeLayout) findViewById(R.id.layout_video);
        mLayoutNoteEditor = (RelativeLayout) findViewById(R.id.layout_note_editor);
        mLayoutNewNote = (RelativeLayout) findViewById(R.id.layout_new_note);
        mLayoutNoteList = (RelativeLayout) findViewById(R.id.layout_note_list);
        mLayoutControllerThree = (LinearLayout) findViewById(R.id.layout_controller_three);
        mLayoutNotes = (LinearLayout) findViewById(R.id.layout_notes);
        mTextTimeCurrent = (TextView) findViewById(R.id.text_time_current);
        mTextTimeTotal = (TextView) findViewById(R.id.text_time_total);
        mTextTutorialTitle = (TextView) findViewById(R.id.text_tutorial_title);
        mTextNoteTime = (TextView) findViewById(R.id.text_note_time);
        mTextGetVideo = (TextView) findViewById(R.id.text_get_video);
        mSeekVideoProgress = (SeekBar) findViewById(R.id.seekbar_video_progress);
        mSeekVolume = (SeekBar) findViewById(R.id.seekbar_volume);
        mImageToggleTutorialView = (ImageView) findViewById(R.id.image_toggle_tutorial_view);
        mImagePrevious = (ImageView) findViewById(R.id.image_previous);
        mImagePreviousSmall = (ImageView) findViewById(R.id.image_previous_small);
        mImageRewind = (ImageView) findViewById(R.id.image_rewind);
        mImageVolumeUp = (ImageView) findViewById(R.id.image_volume_up);
        mImageVolumeDown = (ImageView) findViewById(R.id.image_volume_down);
        mImagePlayPause = (ImageView) findViewById(R.id.image_play_pause);
        mImagePlayPauseSmall = (ImageView) findViewById(R.id.image_play_pause_small);
        mImageForward = (ImageView) findViewById(R.id.image_forward);
        mImageNext = (ImageView) findViewById(R.id.image_next);
        mImageNextSmall = (ImageView) findViewById(R.id.image_next_small);
        mImageVideoThumbnail = (ImageView) findViewById(R.id.image_video_thumbnail);
        mImageNoteDelete = (ImageView) findViewById(R.id.image_note_delete);
        mImageNoteShare = (ImageView) findViewById(R.id.image_note_share);
        mImageNewNote = (ImageView) findViewById(R.id.image_new_note);
        mViewDivider1 = findViewById(R.id.view_divider1);
        mViewDivider2 = findViewById(R.id.view_divider2);
        mFrameVideo = (FrameLayout) findViewById(R.id.frame_video);
        mVideoView = (VideoView) findViewById(R.id.video_tutorial);
        mButtonAssignments = (Button) findViewById(R.id.button_assignments);
        mButtonMyNotes = (Button) findViewById(R.id.button_my_notes);
        mEditNewNote = (EditText) findViewById(R.id.edit_new_note);
        mListNotes = (ListView) findViewById(R.id.list_notes);

        mImagePlayPause.setEnabled(false);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mSeekVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mSeekVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        mImageToggleTutorialView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTutorialView();
            }
        });

        mImagePrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPreviousClickListener != null) {
                    mOnPreviousClickListener.onClick();
                }
            }
        });

        mImagePreviousSmall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePrevious.performClick();
            }
        });

        mImageRewind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mVideoView.getCurrentPosition();
                pos -= 5000; // milliseconds
                mVideoView.seekTo(pos);
                setProgress();
            }
        });

        mImageVolumeUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekVolume.setProgress(mSeekVolume.getProgress() + 1);
            }
        });

        mImageVolumeDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeekVolume.setProgress(mSeekVolume.getProgress() - 1);
            }
        });

        mImagePlayPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayPause();
            }
        });

        mImagePlayPauseSmall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePlayPause.performClick();
            }
        });

        mImageForward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mVideoView.getCurrentPosition();
                pos += 5000; // milliseconds
                mVideoView.seekTo(pos);
                setProgress();
            }
        });

        mImageNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNextClickListener != null) {
                    mOnNextClickListener.onClick();
                }
            }
        });

        mImageNextSmall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageNext.performClick();
            }
        });

        mButtonAssignments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButtonMyNotes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mTextGetVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLayoutNotes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoteList();
            }
        });

        mImageNoteDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentNote != null) {
                    Note.deleteNote(mCurrentNote);
                    mLayoutNotes.performClick();
                }
            }
        });

        mImageNoteShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mImageNewNote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote();
            }
        });

        mLayoutVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLayoutNoteEditor.getVisibility() == GONE) {
                    if (mLayoutControllerTwo.getVisibility() == VISIBLE) {
                        hideControllerTwo();
                    } else {
                        showControllerTwo();
                    }
                }
            }
        });

        mEditNewNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveCurrentNote();
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mTotalDuration = mVideoView.getDuration() / 1000;
                mSeekVideoProgress.setMax(mTotalDuration);
                mImagePlayPause.setEnabled(true);
                if (mStartImmediately) {
                    togglePlayPause();
                    mStartImmediately = false;
                } else {
                    if (mLayoutNoteEditor.getVisibility() == GONE) {
                        showControllerTwo();
                    }
                }

                mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        mSeekVideoProgress.setSecondaryProgress((mTotalDuration * percent) / 100);
                    }
                });


            }
        });

        mVideoView.requestFocus();

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mHandler.removeMessages(SHOW_PROGRESS);
                if (!mIsDragging) {
                    mSeekVideoProgress.setProgress(mSeekVideoProgress.getMax());
                    mTextTimeCurrent.setText(Utility.stringForTime(mVideoView.getDuration()));
                    if (mLayoutNoteEditor.getVisibility() == GONE) {
                        showControllerTwo();
                    }
                }
                updatePausePlay();
            }
        });

        mSeekVideoProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int seekTo = progress * 1000;
                    mVideoView.seekTo(seekTo);
                    mTextTimeCurrent.setText(Utility.stringForTime(seekTo));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeMessages(SHOW_PROGRESS);
                mIsDragging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setProgress();
                updatePausePlay();
                if (mVideoView.isPlaying()) {
                    mHandler.sendEmptyMessage(SHOW_PROGRESS);
                } else {
                    mVideoView.seekTo(seekBar.getProgress() * 1000);
                }
                mIsDragging = false;
            }
        });

        mSeekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void saveCurrentNote() {
        if (mCurrentNote != null) {
            Note.updateNoteText(mCurrentNote, mEditNewNote.getText().toString());
        }
    }

    private void toggleTutorialView() {
        if (mLayoutNoteEditor.getVisibility() == GONE) {
            showNoteEditor();
            createNewNote();
        } else {
            hideNoteEditor();
        }
    }

    private void createNewNote() {
        try {
            if (mLayoutNewNote.getVisibility() == GONE) {
                mLayoutNoteList.setVisibility(GONE);
                mLayoutNewNote.setVisibility(VISIBLE);
                mImageNoteDelete.setVisibility(VISIBLE);
                mImageNoteShare.setVisibility(VISIBLE);
                mViewDivider1.setVisibility(VISIBLE);
                mViewDivider2.setVisibility(VISIBLE);
                mLayoutNotes.setVisibility(VISIBLE);
            }

            long noteCreationTime = System.currentTimeMillis();
            String imageName = "";
            if (mIsVideoUriSet) {
                Bitmap bitmap;
                if (mIsLocalVideoFile) {
                    bitmap = mMediaMetadataRetriever.getFrameAtTime(mVideoView.getCurrentPosition() * 1000);
                } else {
                    bitmap = mFFmpegMediaMetadataRetriever.getFrameAtTime(mVideoView.getCurrentPosition() * 1000);
                }
                imageName = mTutorialId + "_" + noteCreationTime + ".jpg";
                Utility.saveBitmapAsJPG(bitmap, imageName);
                mImageVideoThumbnail.setImageBitmap(bitmap);
                /*Picasso.with(mContext)
                        .load(new File(Global.imagePath + File.separator + imageName))
                        .error(R.drawable.ic_share)
                        .placeholder(R.drawable.ic_share)
                        .into(mImageVideoThumbnail);*/
            }

            mTextTutorialTitle.setText(mTutorialTitle);
            long tutorialTime = mVideoView.getCurrentPosition();
            mTextNoteTime.setText("Time " + Utility.stringForTime((int) tutorialTime));

            mCurrentNote = Note.addNote(mTutorialId, mTutorialTitle, tutorialTime, noteCreationTime, "", imageName);
            mEditNewNote.requestFocus();
            mEditNewNote.setText("");
            Utility.showSoftKeyboard(mEditNewNote, mContext);
        } catch (Exception e) {
            Log.e(TAG, "createNewNote Exception : " + e.toString());
        }
    }

    private void openNote(int position) {
        try {
            if (mLayoutNewNote.getVisibility() == GONE) {
                mLayoutNoteList.setVisibility(GONE);
                mLayoutNewNote.setVisibility(VISIBLE);
                mImageNoteDelete.setVisibility(VISIBLE);
                mImageNoteShare.setVisibility(VISIBLE);
                mViewDivider1.setVisibility(VISIBLE);
                mViewDivider2.setVisibility(VISIBLE);
                mLayoutNotes.setVisibility(VISIBLE);
            }

            mCurrentNote = mNotes.get(position);

            Picasso.with(mContext)
                    .load(new File(Global.imagePath + File.separator + mCurrentNote.getNoteThumbnailName()))
                    .error(R.drawable.ic_share)
                    .placeholder(R.drawable.ic_share)
                    .into(mImageVideoThumbnail);

            mTextTutorialTitle.setText(mCurrentNote.getTutorialName());
            long tutorialTime = mCurrentNote.getTutorialTime();
            mTextNoteTime.setText("Time " + Utility.stringForTime((int) tutorialTime));
            mEditNewNote.setText(mCurrentNote.getNoteText());
        } catch(Exception e) {
            Log.e(TAG, "openNote Exception : " + e.toString());
        }
    }

    private void showNoteEditor() {
        mLayoutNoteEditor.setVisibility(VISIBLE);
        mLayoutControllerThree.setVisibility(VISIBLE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mFrameVideo.setLayoutParams(layoutParams);
        if (mLayoutControllerTwo.getVisibility() == VISIBLE) {
            hideControllerTwo();
        }
    }

    private void hideNoteEditor() {
        mLayoutNoteEditor.setVisibility(GONE);
        mLayoutControllerThree.setVisibility(GONE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mFrameVideo.setLayoutParams(layoutParams);
    }

    private void showNoteList() {
        mLayoutNewNote.setVisibility(GONE);
        mLayoutNoteList.setVisibility(VISIBLE);
        mImageNoteDelete.setVisibility(GONE);
        mImageNoteShare.setVisibility(GONE);
        mViewDivider1.setVisibility(GONE);
        mViewDivider2.setVisibility(GONE);
        mLayoutNotes.setVisibility(GONE);
        fillNoteList();
    }

    private void fillNoteList() {
        try {
            mNotes = Note.getAllNotes();
            if (mNotes != null) {
                mNoteAdapter = new NoteAdapter(mContext, mNotes, this);
                mListNotes.setAdapter(mNoteAdapter);
            }
        } catch (Exception e) {
            Log.e(TAG, "fillNoteList Exception : " + e.toString());
        }
    }

    public void setVideoPath(String videoPath, String tutorialId, String tutorialTitle, boolean startVideo) {
        setVideoUri(Uri.parse(videoPath), tutorialId, tutorialTitle, startVideo);
    }

    public void setVideoUri(Uri videoUri, String tutorialId, String tutorialTitle, boolean startVideo) {
        mTutorialId = tutorialId == null ? "" : tutorialId;
        mTutorialTitle = tutorialTitle == null ? "" : tutorialTitle;
        mStartImmediately = startVideo;
        mVideoView.setVideoURI(videoUri);
        mIsVideoUriSet = true;
        try {
            if (videoUri.toString().contains("http")) {
                mIsLocalVideoFile = false;
                mFFmpegMediaMetadataRetriever.setDataSource(videoUri.toString());
            } else {
                mIsLocalVideoFile = true;
                mMediaMetadataRetriever.setDataSource(mContext, videoUri);
            }
        } catch (Exception e) {
            Log.e(TAG, "mediaRetrieverException : " + e.toString());
        }
    }

    private void showControllerTwo() {
        mLayoutControllerTwo.setVisibility(VISIBLE);
        mLayoutControllerTwo.startAnimation(mAnimationSlideUp);
    }

    private void hideControllerTwo() {
        mLayoutControllerTwo.setVisibility(GONE);
        mLayoutControllerTwo.startAnimation(mAnimationSlideDown);
    }

    private void togglePlayPause() {
        if (mVideoView.isPlaying() && mMediaPlayer != null) {
            mHandler.removeMessages(SHOW_PROGRESS);
            mVideoView.pause();
        } else {
            mHandler.sendEmptyMessage(SHOW_PROGRESS);
            mVideoView.start();
        }
        updatePausePlay();
    }

    private void updatePausePlay() {
        if (mVideoView.isPlaying()) {
            showPause();
        } else {
            showPlay();
        }
    }

    private void showPlay() {
        mImagePlayPause.setImageResource(R.drawable.ic_play);
        mImagePlayPauseSmall.setImageResource(R.drawable.ic_play);
    }

    private void showPause() {
        mImagePlayPause.setImageResource(R.drawable.ic_pause);
        mImagePlayPauseSmall.setImageResource(R.drawable.ic_pause);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS:
                    setProgress();
                    msg = obtainMessage(SHOW_PROGRESS);
                    sendMessageDelayed(msg, 1000);
                    break;
            }
        }
    };

    private int setProgress() {
        int position = mVideoView.getCurrentPosition();
        int duration = mVideoView.getDuration();

        if (duration > 0) {
            int pos = position / 1000;
            mSeekVideoProgress.setProgress(pos);
        }

        if (mTextTimeCurrent != null) {
            mTextTimeCurrent.setText(Utility.stringForTime(position));
        }

        if (mTextTimeTotal != null) {
            mTextTimeTotal.setText(Utility.stringForTime(duration));
        }

        return position;
    }

    public void setOnNextClickListener(OnNextClickListener onNextClickListener) {
        this.mOnNextClickListener = onNextClickListener;
    }

    public void setOnPreviousClickListener(OnPreviousClickListener onPreviousClickListener) {
        this.mOnPreviousClickListener = onPreviousClickListener;
    }

    @Override
    public void onListItemClicked(int position) {
        openNote(position);
    }

    public int getCurrentVideoPosition() {
        if (mIsVideoUriSet) {
            return mVideoView.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public void seekVideoTo(int timeMilliSeconds) {
        if (mIsVideoUriSet) {
            mVideoView.seekTo(timeMilliSeconds);
        }
    }

    public boolean isVideoPlaying() {
        return mVideoView.isPlaying();
    }

    public void playVideo() {
        if (mIsVideoUriSet) {
            mVideoView.start();
            showPause();
        }
    }

    public void pauseVideo() {
        if (mIsVideoUriSet) {
            mVideoView.pause();
            showPlay();
        }
    }

}
