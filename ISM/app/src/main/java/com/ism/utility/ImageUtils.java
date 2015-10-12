package com.ism.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.util.TypedValue;

public class ImageUtils {

	public ImageUtils() {
		// TODO Auto-generated constructor stub
	}

	/* Get Image Data that are in Base64 of byte array */

	public String getOriginalImageData(String imagePath) {

		String encodedImage = null;

		Bitmap bm = BitmapFactory.decodeFile(imagePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); // bm is the bitmap
															// object
		byte[] byteArray = baos.toByteArray();

		encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return encodedImage;

	}

	public String getThumbImageData(String imagePath) {

		String encodedImage = null;

		Bitmap bm = BitmapFactory.decodeFile(imagePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Bitmap.createScaledBitmap(bm, 100, 100, true);

		bm = ThumbnailUtils.extractThumbnail(bm, 100, 100,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

		bm.compress(Bitmap.CompressFormat.JPEG, 10, baos);

		byte[] byteArray = baos.toByteArray();

		encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

		return encodedImage;

	}

	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int color,
			int cornerDips, int borderDips, Context context) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// final int borderSizePx = (int) TypedValue.applyDimension(
		// TypedValue.COMPLEX_UNIT_DIP, (float) borderDips, context
		// .getResources().getDisplayMetrics());

		final int cornerSizePx = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, (float) cornerDips, context
						.getResources().getDisplayMetrics());
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		// prepare canvas for transfer
		paint.setAntiAlias(true);
		paint.setColor(0xFFFFFFFF);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

		// draw bitmap
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		// draw border
		// paint.setColor(color);
		// paint.setStyle(Paint.Style.STROKE);
		// paint.setStrokeWidth((float) borderSizePx);
		// canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

		return output;
	}

	public Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
