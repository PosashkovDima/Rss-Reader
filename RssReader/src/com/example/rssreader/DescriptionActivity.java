package com.example.rssreader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rssreader.image.DownloadImageService;

public class DescriptionActivity extends Activity {

	private static final String DESCRIPTION = "description";
	private static final String DOWNLOADED_IMAGE_NAME = "downloadedImage.jpg";
	private static final String SAVED_DESCRIPTION = "savedDescription";
	// private static final String IMAGE_LINK = "imageLink";
	private String description;
	// private String imageLink;
	private ImageView imageDownloaded;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);
		tv = (TextView) findViewById(R.id.textViewDescription);
		if (savedInstanceState == null) {
			description = getIntent().getStringExtra(DESCRIPTION);
			// imageLink = getIntent().getStringExtra(IMAGE_LINK);
			// go downloadimageservice
			tv.setText(description);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_DESCRIPTION, description);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		description = savedInstanceState.getString(SAVED_DESCRIPTION);
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
					setImage();
				}
			}
		}
	};

	/**
	 * Set image on imageView.
	 */
	private void setImage() {
		imageDownloaded = (ImageView) findViewById(R.id.imageDownloaded);
		String imagePath = getFilesDir().toString() + "/"
				+ DOWNLOADED_IMAGE_NAME;
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
