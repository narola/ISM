package com.ism.commonsource.view;

import android.os.Handler;

import java.util.Random;

public class ProgressGenerator {

	private int progress;

	public void start(final ProcessButton button) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				progress = button.getProgress();
				button.setProgress(progress);
				if (progress < 100) {
					handler.postDelayed(this, generateDelay());
				}
			}
		}, generateDelay());
	}

	private Random random = new Random();

	private int generateDelay() {
		return random.nextInt(1000);
	}
}
