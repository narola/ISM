package com.narola.c161.whiteboard.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narola.c161.whiteboard.R;
import com.narola.c161.whiteboard.WhiteboardActivity;
import com.narola.c161.whiteboard.view.Whiteboard;

/**
 * Created by c161 on 16/09/15.
 */
public class WhiteboardFragment extends Fragment implements Whiteboard.WhiteboardListener {

    private static final String TAG = WhiteboardFragment.class.getSimpleName();

    private View mView;
    private Whiteboard mWhiteboard;
    private Whiteboard.WhiteboardListener mWhiteboardListener;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_whiteboard, container, false);

        initGlobal();

        return mView;
    }

    private void initGlobal() {
        mWhiteboard = (Whiteboard) mView.findViewById(R.id.whiteboard);

        mWhiteboard.setWhiteboardListener(this);

    }

    @Override
    public void onSendImageListener(Bitmap bitmap) {
        if (mWhiteboardListener != null) {
            mWhiteboardListener.onSendImageListener(bitmap);
        } else {
            Log.e(TAG, "whiteBoard listener null");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mWhiteboardListener = (Whiteboard.WhiteboardListener) activity;
    }

}
