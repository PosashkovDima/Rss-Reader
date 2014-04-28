package com.example.rssreader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rssreader.imagedownload.DownloadImageService;

public class DescriptionActivity extends Activity {

	private static final String IS_IMAGE_EXIST = "isImageExist";
	private boolean isImageExist = false;
	private static final String DESCRIPTION = "description";
	private String description;
	private static final String IMAGE_LINK = "imageLink";
	private String imageLink;
	private static final String DOWNLOADED_IMAGE_NAME = "downloadedImage.jpg";
	private static final String SAVED_DESCRIPTION = "savedDescription";
	private ImageView imageDownloaded;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);
		tv = (TextView) findViewById(R.id.textViewDescription);
		imageDownloaded = (ImageView) findViewById(R.id.imageDownloaded);

		if (savedInstanceState == null) {
			description = getIntent().getStringExtra(DESCRIPTION);

			imageLink = getIntent().getStringExtra(IMAGE_LINK);
			if (imageLink != null) {
				downloadImage();
			} else {
				imageDownloaded.setVisibility(4);
			}
			tv.setText(description);
		}
	}

	/**
	 * Run Download image service.
	 */
	private void downloadImage() {
		Intent intent = new Intent(this, DownloadImageService.class);

		intent.putExtra(DownloadImageService.FILE_NAME, DOWNLOADED_IMAGE_NAME);
		intent.putExtra(DownloadImageService.URL, imageLink);

		startService(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_DESCRIPTION, description);
		outState.putBoolean(IS_IMAGE_EXIST, isImageExist);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		description = savedInstanceState.getString(SAVED_DESCRIPTION);
		isImageExist = savedInstanceState.getBoolean(IS_IMAGE_EXIST);
		if (isImageExist) {
			setImage();
		}
		tv.setText(description);
	}

	/**
	 * If news contains an image receiver set it to imageView.
	 */
	private BroadcastReceiver receiverDownloadingImage = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int resultCode = bundle.getInt(DownloadImageService.RESULT);
				if (resultCode == RESULT_OK) {
					isImageExist = true;
					setImage();
				}
			}
		}
	};

	/**
	 * Set image on imageView.
	 */
	private void setImage() {
		String imagePath = Environment.getExternalStorageDirectory().getPath()
				+ "/" + DOWNLOADED_IMAGE_NAME;
		imageDownloaded.setImageDrawable(Drawable.createFromPath(imagePath));
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiverDownloadingImage, new IntentFilter(
				DownloadImageService.NOTIFICATION));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverDownloadingImage);
	}

}
