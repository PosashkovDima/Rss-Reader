package com.example.rssreader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rssreader.imagedownload.DownloadImageService;

public class DescriptionActivity extends Activity {

	private static final String EXTRA_IS_IMAGE_EXIST = "is_image_exist";
	private boolean isImageExist = false;
	private static final String EXTRA_DESCRIPTION = "description";
	private String description;
	private static final String EXTRA_IMAGE_URL = "image_url";
	private String imageUrl;
	private static final String EXTRA_DOWNLOADED_IMAGE_NAME = "downloaded_image.jpg";
	private static final String EXTRA_SAVED_DESCRIPTION = "saved_description";
	private ImageView imageViewDownloaded;
	private TextView textViewDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_description);
		textViewDescription = (TextView) findViewById(R.id.text_view_description);
		imageViewDownloaded = (ImageView) findViewById(R.id.image_view_downloaded);

		if (savedInstanceState == null) {
			description = getIntent().getStringExtra(EXTRA_DESCRIPTION);

			imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
			if (imageUrl != null || imageUrl != "") {
				downloadImage();
			} else {
				imageViewDownloaded.setVisibility(View.INVISIBLE);
			}

			textViewDescription.setText(description);
		}
	}

	/**
	 * Run Download image service.
	 */
	private void downloadImage() {
		Intent intent = new Intent(this, DownloadImageService.class);

		intent.putExtra(DownloadImageService.EXTRA_FILE_NAME,
				EXTRA_DOWNLOADED_IMAGE_NAME);
		intent.putExtra(DownloadImageService.EXTRA_URL, imageUrl);

		startService(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(EXTRA_SAVED_DESCRIPTION, description);
		outState.putBoolean(EXTRA_IS_IMAGE_EXIST, isImageExist);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		description = savedInstanceState.getString(EXTRA_SAVED_DESCRIPTION);
		isImageExist = savedInstanceState.getBoolean(EXTRA_IS_IMAGE_EXIST);
		if (isImageExist) {
			setImage();
		}
		textViewDescription.setText(description);
	}

	/**
	 * If news contains an image receiver set it to imageView.
	 */
	private BroadcastReceiver receiverDownloadingImage = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int resultCode = bundle
						.getInt(DownloadImageService.EXTRA_RESULT);
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
				+ "/" + EXTRA_DOWNLOADED_IMAGE_NAME;
		imageViewDownloaded
				.setImageDrawable(Drawable.createFromPath(imagePath));
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
