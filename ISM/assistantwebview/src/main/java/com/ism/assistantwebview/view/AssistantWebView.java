package com.ism.assistantwebview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.assistantwebview.R;
import com.ism.assistantwebview.utility.Utility;

/**
 * Created by Krunal Panchal on 05/10/15.
 */
public class AssistantWebView extends RelativeLayout {

    private static final String TAG = AssistantWebView.class.getSimpleName();

    private WebView mWebView;
    private ProgressBar mProgressWebView;
    private TextView mTextError;
    private ImageView mImageGoBack;
    private ImageView mImageForward;
    private ImageView mImageReload;
    private RelativeLayout mLayoutController;

    private Animation mAnimationFadeIn;
    private Animation mAnimationFadeOut;
    private Context mContext;

    private String mUrl = "";
	private int mControllerBackground;

    public AssistantWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AssistantWebView);
        try {
            mControllerBackground = typedArray.getResourceId(R.styleable.AssistantWebView_controllerBackground, 0);
        } finally {
            typedArray.recycle();
        }

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        mInflater.inflate(R.layout.view_assistant_webview, this);

        mAnimationFadeIn = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
        mAnimationFadeOut = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressWebView = (ProgressBar) findViewById(R.id.progress_webview);
        mTextError = (TextView) findViewById(R.id.text_error_message);
        mImageReload = (ImageView) findViewById(R.id.image_reload);
        mImageGoBack = (ImageView) findViewById(R.id.image_goback);
        mImageForward = (ImageView) findViewById(R.id.image_forward);
	    mLayoutController = (RelativeLayout) findViewById(R.id.layout_controller);

	    mLayoutController.setBackgroundResource(mControllerBackground);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; Android; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Utility.isOnline(mContext)) {
                    if (mTextError.getVisibility() == VISIBLE) {
                        hideError();
                    }
                    view.loadUrl(url);
                } else {
                    showError(mContext.getString(R.string.msg_offline));
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressWebView.setVisibility(VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressWebView.setVisibility(GONE);
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressWebView.setProgress(newProgress);
                mImageGoBack.setVisibility(mWebView.canGoBack() ? VISIBLE : INVISIBLE);
                mImageForward.setVisibility(mWebView.canGoForward() ? VISIBLE : INVISIBLE);
            }
        });

        mTextError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError();
            }
        });

        mImageReload.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        if (Utility.isOnline(mContext)) {
			        if (mWebView.getUrl() != null && mWebView.getUrl().length() > 0) {
				        mWebView.reload();
			        } else {
				        mWebView.loadUrl(mUrl);
			        }
		        } else {
			        showError(mContext.getString(R.string.msg_offline));
		        }
	        }
        });

        mImageGoBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
            }
        });

        mImageForward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
            }
        });

    }

    public void loadUrl(String url) {
        mUrl = url;
        if (Utility.isOnline(mContext)) {
            if (Patterns.WEB_URL.matcher(url).matches()) {
                mWebView.loadUrl(mUrl);
            } else {
                Log.e(TAG, "Invalid url : " + url);
            }
        } else {
            showError(mContext.getString(R.string.msg_offline));
        }
    }

    public String getUrl() {
	    return mWebView.getUrl();
    }

	public String getOriginalUrl() {
		return mWebView.getOriginalUrl();
	}

	public void setControllerBackground(int resId) {
		mLayoutController.setBackgroundResource(resId);
	}

    private void showError(String errorMessage) {
        if (errorMessage != null) {
            mTextError.setText(errorMessage);
        }
        mTextError.setVisibility(VISIBLE);
        mTextError.startAnimation(mAnimationFadeIn);
    }

    private void hideError() {
        mTextError.setText("");
        mTextError.setVisibility(GONE);
        mTextError.startAnimation(mAnimationFadeOut);
    }

}
