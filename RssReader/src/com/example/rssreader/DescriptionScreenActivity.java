package com.example.rssreader;

import com.example.rssreader.image.DownloadImageService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionScreenActivity extends Activity {

	private static final String DESCRIPTION = "description";
	private static final String DOWNLOADED_IMAGE_NAME = "downloadedImage.jpg";
	private ImageView imageDownloaded;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);

		tv = (TextView) findViewById(R.id.textViewDescription);
		tv.setText(getIntent().getStringExtra(DESCRIPTION));
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
