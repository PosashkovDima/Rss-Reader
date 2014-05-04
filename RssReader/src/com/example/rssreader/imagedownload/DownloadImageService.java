package com.example.rssreader.imagedownload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

public class DownloadImageService extends IntentService {
	private int result = Activity.RESULT_CANCELED;
	public static final String EXTRA_URL = "url_path";
	public static final String EXTRA_FILE_NAME = "fileName";
	public static final String EXTRA_RESULT = "result";
	public static final String NOTIFICATION = "com.example.rssreader.image";
	private Intent intent = new Intent(NOTIFICATION);

	public DownloadImageService() {
		super("DownloadImageService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String urlPath = intent.getStringExtra(EXTRA_URL);
		String fileName = intent.getStringExtra(EXTRA_FILE_NAME);

		File fileOutput = new File(Environment.getExternalStorageDirectory(),
				fileName);
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		URL url = null;

		try {
			url = new URL(urlPath);
			URLConnection conection;
			conection = url.openConnection();
			conection.connect();

			inputStream = new BufferedInputStream(url.openStream(), 8192);
			outputStream = new FileOutputStream(fileOutput.getPath());
			int next = -1;

			while ((next = inputStream.read()) != -1) {
				outputStream.write(next);
			}
		} catch (FileNotFoundException e) {

			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}

		result = Activity.RESULT_OK;
		publishResults(result);
	}

	private void publishResults(int result) {

		intent.putExtra(EXTRA_RESULT, result);
		sendBroadcast(intent);
	}
}
