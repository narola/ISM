package com.ism.common.view;

import android.os.Handler;

import java.util.Random;

public class ProgressGenerator {

	private int progress;
	private Handler handler;
	private Runnable runnable;

	public void start(final ProcessButton button) {
		handler = new Handler();
		runnable = new Runnable() {
			@Override
			public void run() {
				progress = 10;
				button.setProgress(progress);
				handler.postDelayed(this, generateDelay());
			}
		};
		handler.postDelayed(runnable, generateDelay());
	}

	public void stop() {
		if (handler != null && runnable != null) {
			handler.removeCallbacks(runnable);
		}
	}

	private Random random = new Random();

	private int generateDelay() {
		return random.nextInt(1000);
	}
}
