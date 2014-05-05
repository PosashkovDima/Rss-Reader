package com.example.rssreader.imagedownload;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class DownloadImageService extends IntentService {
	private int result = Activity.RESULT_CANCELED;
	public static final String EXTRA_URL = "url_path";
	public static final String EXTRA_RESULT = "result";
	public static final String EXTRA_BITMAP = "bitmap";
	public static final String NOTIFICATION = "com.example.rssreader.image";
	private Intent intent = new Intent(NOTIFICATION);

	public DownloadImageService() {
		super("DownloadImageService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String urlPath = intent.getStringExtra(EXTRA_URL);

		InputStream inputStream = null;

		URL url = null;
		Bitmap bitmap = null;
		try {
			url = new URL(urlPath);
			URLConnection conection;
			conection = url.openConnection();
			conection.connect();

			inputStream = new BufferedInputStream(url.openStream(), 8192);

			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}

		result = Activity.RESULT_OK;
		publishResults(result);
		saveBitmap(bitmap);
	}

	private void saveBitmap(Bitmap bitmap) {
		FileOutputStream fos = null;
		try {
			fos = openFileOutput(EXTRA_BITMAP, Context.MODE_PRIVATE);
			bitmap.compress(CompressFormat.JPEG, 90, fos);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} finally {
			try {
				fos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	private void publishResults(int result) {

		intent.putExtra(EXTRA_RESULT, result);
		sendBroadcast(intent);
	}
}
