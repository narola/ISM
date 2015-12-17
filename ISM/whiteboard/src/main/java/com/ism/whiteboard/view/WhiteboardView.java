package com.ism.whiteboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ism.whiteboard.R;

public class WhiteboardView extends View implements View.OnTouchListener {

    private static final String TAG = WhiteboardView.class.getSimpleName();

    private Paint   mPaint;
    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private Paint   mPaintBitmap;
    private float   mX, mY;
    private Paint mEraserPaint;
    private Bitmap  mPointerBitmap;

    private static final float TOUCH_TOLERANCE = 1;
    private Path mPath;

    private static final int ACTION_MODE_DRAW = 0;
    private static final int ACTION_MODE_ERASE = 1;
    private static final int ACTION_MODE_CLEAR = 2;
    private int mActionMode = ACTION_MODE_DRAW;
    private int mMotionEventAction = -1;

    private int mPenThickness = 5;
    private int mEraserThickness = mPenThickness * 2;
    private int mBoardColor = Color.WHITE;
    private int mPaintColor = Color.BLACK;

    public WhiteboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        setBackgroundColor(Color.WHITE);

        mPath = new Path();
        mPaintBitmap = new Paint(Paint.DITHER_FLAG);
        mEraserPaint = new Paint(Paint.DITHER_FLAG);
        mEraserPaint.setColor(Color.GRAY);
        mPointerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pen_active);

        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mPenThickness);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mBoardColor);
    }

    @Override
    public void onDraw(Canvas canvas) {
        switch (mActionMode) {
            case ACTION_MODE_DRAW:
                canvas.drawBitmap(mBitmap, 0, 0, mPaintBitmap);
                canvas.drawPath(mPath, mPaint);

                break;
            case ACTION_MODE_ERASE:
                canvas.drawBitmap(mBitmap, 0, 0, mPaintBitmap);
                canvas.drawPath(mPath, mPaint);

                Matrix matrixEraser = new Matrix();
                matrixEraser.postTranslate(mX - 4, mY - mPointerBitmap.getHeight() + 2);

                switch (mMotionEventAction) {
                    case MotionEvent.ACTION_DOWN:
                        canvas.drawBitmap(mPointerBitmap, matrixEraser, mPaint);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        canvas.drawBitmap(mPointerBitmap, matrixEraser, mPaint);
                        break;
                }

                break;
            case ACTION_MODE_CLEAR:
                canvas.drawColor(mBoardColor);
                mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
                mCanvas.drawColor(mBoardColor);
                mActionMode = ACTION_MODE_DRAW;

                break;
        }

    }

    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        mMotionEventAction = event.getAction();
        switch (mMotionEventAction) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }

    private void touchStart(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);

        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);

        // kill this so we don't double draw
        mPath.reset();
    }



    /**
     *
     *  WhiteboardView methods
     *
     */

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void draw() {
        mActionMode = ACTION_MODE_DRAW;
        mPaint.setStrokeWidth(mPenThickness);
        mPaint.setColor(mPaintColor);
        mPointerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pen_active);
//        mPaint.setXfermode(null);
    }

    public void startErase() {
        mActionMode = ACTION_MODE_ERASE;
        mPaint.setStrokeWidth(mEraserThickness);
        mPaint.setColor(mBoardColor);
        mPointerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_eraser_active);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void clearBoard() {
        mActionMode = ACTION_MODE_CLEAR;
        invalidate();
    }

    public int getPenThickness() {
        return mPenThickness;
    }

    public void setPenThickness(int penThickness) {
        if (penThickness > 0) {
            this.mPenThickness = penThickness;
        } else {
            this.mPenThickness = 1;
        }
        if (mActionMode == ACTION_MODE_DRAW) {
            mPaint.setStrokeWidth(mPenThickness);
        }
    }

    public int getEraserThickness() {
        return mEraserThickness;
    }

    public void setEraserThickness(int eraserThickness) {
        if (eraserThickness > 0) {
            this.mEraserThickness = eraserThickness;
        } else {
            this.mEraserThickness = 1;
        }
        if (mActionMode == ACTION_MODE_ERASE) {
            mPaint.setStrokeWidth(mEraserThickness);
        }
    }

    public int getPaintColor() {
        return mPaintColor;
    }

    public void setPaintColor(int paintColor) {
        this.mPaintColor = paintColor;
        if (mActionMode == ACTION_MODE_DRAW) {
            mPaint.setColor(mPaintColor);
        }
    }

}