package com.narola.c161.whiteboard;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.narola.c161.whiteboard.fragment.WhiteboardFragment;
import com.narola.c161.whiteboard.view.Whiteboard;

public class WhiteboardActivity extends AppCompatActivity implements Whiteboard.WhiteboardListener {

    private static final String TAG = WhiteboardActivity.class.getSimpleName();

//    private WhiteboardView mWhiteboardView;
    private Whiteboard mWhiteboard;
    private ImageView mImageWhiteboard;

    private WhiteboardFragment mWhiteboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);

        initGlobal();

    }

    private void initGlobal() {
        mImageWhiteboard = (ImageView) findViewById(R.id.image_whiteboard);
//        mWhiteboardView = (WhiteboardView) findViewById(R.id.whiteboard_view_ism);
        mWhiteboard = (Whiteboard) findViewById(R.id.whiteboard);

        mWhiteboard.setWhiteboardListener(this);

//        mWhiteboard.getWhiteboardView().setBackgroundColor(Color.CYAN);


        mWhiteboardFragment = new WhiteboardFragment();


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_whiteboard, mWhiteboardFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_whiteboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_draw:
//                mWhiteboardView.draw();
                break;
            case R.id.action_erase:
//                mWhiteboardView.startErase();
                break;
            case R.id.action_clear_all:
//                mWhiteboardView.clearBoard();
                break;
            case R.id.action_display_image:
//                Bitmap bitmap = mWhiteboardView.getBitmap();
//                if (bitmap != null) {
//                    mImageWhiteboard.setImageBitmap(bitmap);
//                }
                break;
            case R.id.action_pen_thickness:
//                mWhiteboardView.setPenThickness((mWhiteboardView.getPenThickness() == 5) ? 10 : 5);
                break;
            case R.id.action_eraser_thickness:
//                mWhiteboardView.setEraserThickness((mWhiteboardView.getEraserThickness() == 5) ? 10 : 5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendImageListener(Bitmap bitmap) {
        if (bitmap != null) {
            mImageWhiteboard.setImageBitmap(bitmap);
        }
    }

}
