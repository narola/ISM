package com.narola.kpa.richtexteditor.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.narola.kpa.richtexteditor.R;

import jp.wasabeef.richeditor.RichEditor;
import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Krunal Panchal on 18/09/15.
 */
public class RichTextEditor extends LinearLayout {

    private static final String TAG = RichTextEditor.class.getSimpleName();

    private Context mContext;
    private ImageButton mImageUndo;
    private ImageButton mImageRedo;
    private ImageButton mImageBold;
    private ImageButton mImageItalic;
    private ImageButton mImageUnderline;
    private ImageButton mImageStrikeOut;
    private ImageButton mImageSubScript;
    private ImageButton mImageSuperScript;
    private ImageButton mImageFontColor;
    private ImageButton mImageAlignLeft;
    private ImageButton mImageAlignCenter;
    private ImageButton mImageAlignRight;
    private ImageButton mImageCreateLink;
    private ImageButton mImageRemoveLink;
    private ImageButton mImageOrderedList;
    private ImageButton mImageUnorderedList;
    private RichEditor mRichEditor;

    private String mPlaceHolder = "";
    private int mFontSize = 20;
    private int mFontColor = Color.BLACK;

    public RichTextEditor(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.bg_rich_text_editor);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RichTextEditor);

        try {
            mPlaceHolder = typedArray.getString(R.styleable.RichTextEditor_placeHolder);
            mFontSize = typedArray.getInteger(R.styleable.RichTextEditor_textSizeInPx, mFontSize);

            if (mPlaceHolder == null) {
                mPlaceHolder = "";
            }
        } finally {
            typedArray.recycle();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        layoutInflater.inflate(R.layout.view_rich_text_editor, this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mImageUndo = (ImageButton) findViewById(R.id.action_undo);
        mImageRedo = (ImageButton) findViewById(R.id.action_redo);
        mImageBold = (ImageButton) findViewById(R.id.action_bold);
        mImageItalic = (ImageButton) findViewById(R.id.action_italic);
        mImageUnderline = (ImageButton) findViewById(R.id.action_underline);
        mImageStrikeOut = (ImageButton) findViewById(R.id.action_strikethrough);
        mImageSubScript = (ImageButton) findViewById(R.id.action_subscript);
        mImageSuperScript = (ImageButton) findViewById(R.id.action_superscript);
        mImageFontColor = (ImageButton) findViewById(R.id.action_txt_color);
        mImageAlignLeft = (ImageButton) findViewById(R.id.action_align_left);
        mImageAlignCenter = (ImageButton) findViewById(R.id.action_align_center);
        mImageAlignRight = (ImageButton) findViewById(R.id.action_align_right);
        mImageCreateLink = (ImageButton) findViewById(R.id.action_create_link);
        mImageRemoveLink = (ImageButton) findViewById(R.id.action_remove_link);
        mImageOrderedList = (ImageButton) findViewById(R.id.action_ordered_list);
        mImageUnorderedList = (ImageButton) findViewById(R.id.action_unordered_list);
        mRichEditor = (RichEditor) findViewById(R.id.rich_editor);

        mRichEditor.setPlaceholder(mPlaceHolder);
        mRichEditor.setEditorFontSize(mFontSize);

        mImageUndo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.undo();
            }
        });

        mImageRedo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.redo();
            }
        });

        mImageBold.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setBold();
            }
        });

        mImageItalic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setItalic();
            }
        });

        mImageUnderline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setUnderline();
            }
        });

        mImageStrikeOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setStrikeThrough();
            }
        });

        mImageSubScript.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setSubscript();
            }
        });

        mImageSuperScript.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setSuperscript();
            }
        });

        mImageFontColor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AmbilWarnaDialog(mContext, mFontColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                        mFontColor = color;
                        mRichEditor.setTextColor(mFontColor);
                    }
                }).show();
            }
        });

        mImageAlignLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setAlignLeft();
            }
        });

        mImageAlignCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setAlignCenter();
            }
        });

        mImageAlignRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setAlignRight();
            }
        });

        mImageCreateLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateLinkDialog();
            }
        });

        mImageRemoveLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.removeLink();
            }
        });

        mImageOrderedList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setOrderedList();
            }
        });

        mImageUnorderedList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditor.setUnorderedList();
            }
        });

    }

    public RichEditor getRichEditor() {
        return mRichEditor;
    }

    private void openCreateLinkDialog() {
        final AlertDialog dialog;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);

        View mView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_create_link, null);
        final EditText editUrl = (EditText) mView.findViewById(R.id.edit_insert_link);

        editUrl.setSelection(editUrl.getText().length());

        alertBuilder.setView(mView)
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog = alertBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLinkValid(editUrl)) {
                    mRichEditor.createLink(editUrl.getText().toString());
                    dialog.dismiss();
                }
            }
        });
    }

    private boolean isLinkValid(EditText editText) {
        boolean valid = true;
        if (editText.getText() == null
                || editText.getText().toString().trim().length() == 0) {
            editText.setError(mContext.getString(R.string.required));
            valid = false;
        } else if (!Patterns.WEB_URL.matcher(editText.getText()).matches()) {
            editText.setError(mContext.getString(R.string.invalid_url));
            valid = false;
        } else {
            editText.setError(null);
        }
        return valid;
    }

}
