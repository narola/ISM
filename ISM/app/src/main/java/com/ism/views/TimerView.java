package com.ism.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.R;
import com.ism.utility.Utility;

/**
 * Created by c161 on 19/10/15.
 */
public class TimerView extends RelativeLayout {

	private static final String TAG = TimerView.class.getSimpleName();

	private TextView txtMinute;
	private TextView txtSecond;
	private ProgressBar progTime;

	private Context context;

	private int intTotalTimeMilli;
	private int intTimeMinute;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

        /*TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AssistantWebView);
        try {
            int attribute = typedArray.getInteger(R.styleable.AssistantWebView_attribute, 0);
        } finally {
            typedArray.recycle();
        }*/

		LayoutInflater mInflater = LayoutInflater.from(context);
		mInflater.inflate(R.layout.view_timer, this);

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		txtMinute = (TextView) findViewById(R.id.txt_minute);
		txtSecond = (TextView) findViewById(R.id.txt_second);
		progTime = (ProgressBar) findViewById(R.id.prog_time);
	}

	public void setTotalTimeMin(int totalTime) {
		intTotalTimeMilli = totalTime * 60 * 1000;
		progTime.setMax(totalTime * 60);
	}

	public void setTotalTimeMilli(long totalTime) {
		intTotalTimeMilli = (int) totalTime;
		progTime.setMax((int) totalTime);
	}

	public void setTimeMilli(int timeMilli) {
		if (timeMilli != 0) {
			int totalSeconds = timeMilli / 1000;
			int seconds = totalSeconds % 60;
			int minutes = (totalSeconds / 60) % 60;
			int hours = totalSeconds / 3600;
			txtMinute.setText(Utility.formatNumber((hours * 60) + minutes) + ":");
			txtSecond.setText(Utility.formatNumber(seconds) + "\nmin");
			progTime.setProgress((intTotalTimeMilli - timeMilli) / 1000);
		} else {
			txtMinute.setText(Utility.formatNumber(0) + ":");
			txtSecond.setText(Utility.formatNumber(0) + "\nmin");
			progTime.setProgress(progTime.getMax());
		}
	}
}
