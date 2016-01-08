package com.ism.whiteboard.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.ism.whiteboard.R;
import com.ism.whiteboard.adapter.PointerAdapter;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Whiteboard extends LinearLayout {

    private static final String TAG = Whiteboard.class.getSimpleName();

    private WhiteboardView mWhiteboardView;
    private Button mButtonSendImage;
    private ImageView mImageColorPicker;
    private ImageView mImagePen;
    private ImageView mImageEraser;
    private ImageView mImageClearAll;
    private Spinner mSpinnerPointerPen;
    private Spinner mSpinnerPointerEraser;

    private WhiteboardListener mWhiteboardListener;
    private Context mContext;

    private int mPenThickness = 5;
    private int mEraserThickness = mPenThickness * 2;
    private String[] mPointerSizeList;
    private int mPenColor = Color.BLACK;

    public interface WhiteboardListener {
        public void onSendImageClick(Bitmap bitmap);
    }

    public Whiteboard(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOrientation(VERTICAL);
        int padding = getResources().getDimensionPixelOffset(R.dimen.whiteboard_padding);
        setPadding(padding, padding, padding, padding);
        setBackgroundColor(Color.BLACK);
        mPointerSizeList = getResources().getStringArray(R.array.pointer_size_list);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WhiteboardView);

        try {
            mPenThickness = typedArray.getInteger(R.styleable.WhiteboardView_penThickness, mPenThickness);
            mEraserThickness = typedArray.getInteger(R.styleable.WhiteboardView_eraserThickness, mEraserThickness);
            if (mPenThickness <= 0) {
                mPenThickness = 1;
            }
            if (mEraserThickness <= 0) {
                mEraserThickness = 1;
            }
        } finally {
            typedArray.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_whiteboard, this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mWhiteboardView = (WhiteboardView) findViewById(R.id.whiteboard_view);
        mButtonSendImage = (Button) findViewById(R.id.button_send);
        mImageColorPicker = (ImageView) findViewById(R.id.image_color_picker);
        mImagePen = (ImageView) findViewById(R.id.image_pen);
        mImageEraser = (ImageView) findViewById(R.id.image_eraser);
        mImageClearAll = (ImageView) findViewById(R.id.image_clear_all);
        mSpinnerPointerPen = (Spinner) findViewById(R.id.spinner_pen);
        mSpinnerPointerEraser = (Spinner) findViewById(R.id.spinner_eraser);

        mImagePen.setImageResource(R.drawable.ic_pen_active);

        mWhiteboardView.setPenThickness(mPenThickness);
        mWhiteboardView.setEraserThickness(mEraserThickness);

        mSpinnerPointerPen.setAdapter(new PointerAdapter(mContext));
        mSpinnerPointerEraser.setAdapter(new PointerAdapter(mContext));

        mImageColorPicker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageColorPicker.setImageResource(R.drawable.ic_color_picker_active);
                new AmbilWarnaDialog(mContext, mPenColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                        mImageColorPicker.setImageResource(R.drawable.ic_color_picker);
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                        mPenColor = color;
                        mWhiteboardView.setPaintColor(color);
                        mImageColorPicker.setImageResource(R.drawable.ic_color_picker);
                    }
                }).show();
            }
        });

        mImagePen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWhiteboardView.draw();
                mImagePen.setImageResource(R.drawable.ic_pen_active);
                mImageEraser.setImageResource(R.drawable.ic_eraser);
            }
        });

        mImageEraser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWhiteboardView.startErase();
                mImageEraser.setImageResource(R.drawable.ic_eraser_active);
                mImagePen.setImageResource(R.drawable.ic_pen);
            }
        });

        mImageClearAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mWhiteboardView.clearBoard();
            }
        });

        mSpinnerPointerPen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWhiteboardView.setPenThickness(Integer.valueOf(mPointerSizeList[mSpinnerPointerPen.getSelectedItemPosition()]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerPointerEraser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWhiteboardView.setEraserThickness(Integer.valueOf(mPointerSizeList[mSpinnerPointerEraser.getSelectedItemPosition()]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mButtonSendImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWhiteboardListener != null) {
                    mWhiteboardListener.onSendImageClick(mWhiteboardView.getBitmap());
                }
            }
        });

    }

    public void setWhiteboardListener(WhiteboardListener mWhiteboardListener) {
        this.mWhiteboardListener = mWhiteboardListener;
    }

    public WhiteboardView getWhiteboardView() {
        return mWhiteboardView;
    }

}