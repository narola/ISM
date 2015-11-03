package com.ism.common.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.ism.common.R;

/*
 *    The MIT License (MIT)
 *
 *   Copyright (c) 2014 Danylyk Dmytro
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

public class ActionProcessButton extends ProcessButton {

	private ProgressBar mProgressBar;

	private Mode mMode;

	private int mColor1;
	private int mColor2;
	private int mColor3;
	private int mColor4;

	public enum Mode {
		PROGRESS, ENDLESS;
	}

	public ActionProcessButton(Context context) {
		super(context);
		init(context);
	}

	public ActionProcessButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ActionProcessButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		Resources res = context.getResources();

		mMode = Mode.ENDLESS;

		mColor1 = res.getColor(R.color.holo_blue_bright);
		mColor2 = res.getColor(R.color.holo_green_light);
		mColor3 = res.getColor(R.color.holo_orange_light);
		mColor4 = res.getColor(R.color.holo_red_light);
	}

	public void setMode(Mode mode) {
		mMode = mode;
	}

	public void setColorScheme(int color1, int color2, int color3, int color4) {
		mColor1 = color1;
		mColor2 = color2;
		mColor3 = color3;
		mColor4 = color4;
	}

	@Override
	public void drawProgress(Canvas canvas) {
		if (getBackground() != getNormalDrawable()) {
			setBackgroundDrawable(getNormalDrawable());
		}

		switch (mMode) {
			case ENDLESS:
				drawEndlessProgress(canvas);
				break;
			case PROGRESS:
				drawLineProgress(canvas);
				break;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mProgressBar != null) {
			setupProgressBarBounds();
		}
	}

	private void drawLineProgress(Canvas canvas) {

    	/*
    	 * here you can customize progress bar 
    	 * progress bar height and its width 
    	 */


		float scale = (float) getProgress() / (float) getMaxProgress();
		float indicatorWidth = (float) getMeasuredWidth() * scale;

		double indicatorHeightPercent = 0.05; // 5%
		int bottom = (int) (getMeasuredHeight() - getMeasuredHeight() * indicatorHeightPercent);
		getProgressDrawable().setBounds(0, bottom, (int) indicatorWidth, getMeasuredHeight());
		getProgressDrawable().draw(canvas);
	}

	private void drawEndlessProgress(Canvas canvas) {
    	
    	/*
    	 * here you can customize end less progress bar 
    	 * you can change each color of progress bar using mColor1, mColor2, mColor3, mColor4
    	 * 
    	 */

		if (mProgressBar == null) {
			mProgressBar = new ProgressBar(this);
			setupProgressBarBounds();
			mProgressBar.setColorScheme(mColor1, mColor2, mColor3, mColor4);
			mProgressBar.start();
		}

		if (getProgress() > 0) {
			mProgressBar.draw(canvas);
		}
	}

	private void setupProgressBarBounds() {
		double indicatorHeight = getDimension(R.dimen.layer_padding);
		int bottom = (int) (getMeasuredHeight() - indicatorHeight);
		mProgressBar.setBounds(0, bottom, getMeasuredWidth(), getMeasuredHeight());
	}


}
