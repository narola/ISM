package com.narola.kpa.scientificeditor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narola.kpa.scientificeditor.R;

/**
 * Created by Krunal Panchal on 24/09/15.
 */
public class ScientificEditor extends LinearLayout {

    private static final String TAG = ScientificEditor.class.getSimpleName();

    private TextView mTextQuestion;
    private ImageView mImageQuestion;
    private WebView mWebViewFormula;

    private Context mContext;

    private String mFormula = "";
    private boolean mIsMathJaxLoaded = false;
    private boolean mIsFormulaSet = false;

    public ScientificEditor(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);

        /*TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScientificEditor);

        try {
            int attribute = typedArray.getInteger(R.styleable.ScientificEditor_attribute, 0);
        } finally {
            typedArray.recycle();
        }*/

        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.view_scientific_editor, this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTextQuestion = (TextView) findViewById(R.id.text_question);
        mImageQuestion = (ImageView) findViewById(R.id.image_question);
        mWebViewFormula = (WebView) findViewById(R.id.webview_formula);

        mWebViewFormula.getSettings().setJavaScriptEnabled(true);
        mWebViewFormula.getSettings().setUseWideViewPort(true);
        mWebViewFormula.getSettings().setDefaultFontSize(17);

        mWebViewFormula.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        mWebViewFormula.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mIsMathJaxLoaded = true;

                if (mIsFormulaSet) {
                    setFormula(mFormula);
                }

            }
        });

        mWebViewFormula.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
                + "MathJax.Hub.Config({ "
                + "showMathMenu: false, "
                + "jax: ['input/TeX','output/HTML-CSS'], "
                + "extensions: ['tex2jax.js'], "
                + "TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
                + "'noErrors.js','noUndefined.js'] } "
                + "});</script>"
                + "<script type='text/javascript' "
                + "src='file:///android_asset/MathJax/MathJax.js'"
                + "></script><span id='math'></span>", "text/html", "utf-8", "");

        /**
         * For online use. Uses latest MathJax functionality.
         */
        /*mWebViewFormula.loadDataWithBaseURL("http://bar","<script type='text/javascript' src='http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML'></script>" +
                "<span id='math'></span>","text/html","utf-8","");*/

    }

    public void setQuestion(String question) {
        mTextQuestion.setText(question);
    }

    public TextView getQuestionTextView() {
        return mTextQuestion;
    }

    public void setQuestionImage(Uri imageUri) {
        mImageQuestion.setImageURI(imageUri);
    }

    public ImageView getQuestionImageView() {
        return mImageQuestion;
    }

    public void setFormula(String formula) {
        mFormula = formula;
        mIsFormulaSet = true;
        if (mIsMathJaxLoaded) {
            mWebViewFormula.evaluateJavascript("javascript:document.getElementById('math').innerHTML=" +
                    "'<br/>\\\\[" + doubleEscapeTeX(formula) + "\\\\]<br/>';", null);
            mWebViewFormula.evaluateJavascript("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);", null);
        }
    }

    private String doubleEscapeTeX(String s) {
        String t = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') t += '\\';
            if (s.charAt(i) != '\n') t += s.charAt(i);
            if (s.charAt(i) == '\\') t += "\\";
        }
        return t;
    }

}