package com.narola.kpa.scientificeditor;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEditFormula;
    private Button mButtonShow;
    private Button mButtonClear;
    private Button mButtonExample;
    private WebView mWebViewFormula;

    private int exampleIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGlobal();

    }

    private void initGlobal() {
        mWebViewFormula = (WebView) findViewById(R.id.webview_formula);
        mButtonShow = (Button) findViewById(R.id.button_show);
        mButtonClear = (Button) findViewById(R.id.button_clear);
        mButtonExample = (Button) findViewById(R.id.button_example);
        mEditFormula = (EditText) findViewById(R.id.edit_formula);


        mWebViewFormula.getSettings().setJavaScriptEnabled(true);

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

                Log.e(TAG, "url : " + url);

            }
        });

//        mWebViewFormula.getSettings().setBuiltInZoomControls(true);

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

        /*mWebViewFormula.loadDataWithBaseURL("http://bar","<script type='text/javascript' src='http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML'></script>" +
                "<span id='math'></span>","text/html","utf-8","");*/

        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "javascript:document.getElementById('math').innerHTML=" +
                        "'Some additional editor text <br/>lk sldkfj sl;kdjf ksdjf ks<br/>sdjk fnskldj flksj dlkofjsdjko<br/>k jsdhkf hsdiufh isuhdifu" +
                        " \\\\[" + doubleEscapeTeX(mEditFormula.getText().toString()) + "\\\\]" +
                        " <img src=\"file:///android_asset/img_question.jpg\" align=\"middle\" height=\"100\" width=\"100\"> <br/>" +
                        "nskldfj sjdf ojksdfio sdhfsdjk<br/>" +
                        "sdfjoisudoi fjwoiej fjks dofjkewoijfow<br/>" +
                        "Some additional editor text';";
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    mWebViewFormula.loadUrl(data);
                    mWebViewFormula.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                } else {
                    loadJavaScriptForKITKAT(data);
                    loadJavaScriptForKITKAT("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                }
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditFormula.setText("");
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    mWebViewFormula.loadUrl("javascript:document.getElementById('math').innerHTML='';");
                    mWebViewFormula.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                } else {
                    loadJavaScriptForKITKAT("javascript:document.getElementById('math').innerHTML='';");
                    loadJavaScriptForKITKAT("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                }
            }
        });

        mButtonExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditFormula.setText(getExample(exampleIndex++));
                if (exampleIndex > getResources().getStringArray(R.array.tex_examples).length - 1) {
                    exampleIndex = 0;
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    mWebViewFormula.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\[" + doubleEscapeTeX(mEditFormula.getText().toString()) + "\\\\]';");
                    mWebViewFormula.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                } else {
                    loadJavaScriptForKITKAT("javascript:document.getElementById('math').innerHTML='\\\\[" + doubleEscapeTeX(mEditFormula.getText().toString()) + "\\\\]';");
                    loadJavaScriptForKITKAT("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                }
            }
        });


        mEditFormula.setText(Html.fromHtml("&sum;"));

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void loadJavaScriptForKITKAT(String javaScriptString) {
        mWebViewFormula.evaluateJavascript(javaScriptString, null);
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

    private String getExample(int index) {
        return getResources().getStringArray(R.array.tex_examples)[index];
    }

}