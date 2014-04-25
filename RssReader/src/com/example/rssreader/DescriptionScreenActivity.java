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

public class DescriptionScreenActivity extends Activity {

	private static final String DESCRIPTION = "description";
	private static final String DOWNLOADED_IMAGE_NAME = "downloadedImage.jpg";
	private ImageView imageDownloaded;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description_activity);

		tv = (TextView) findViewById(R.id.textViewDescription);
		tv.setText(getIntent().getStringExtra(DESCRIPTION));

		imageDownloaded = (ImageView) findViewById(R.id.imageDownloaded);
	}

	private void setImage() {
		String imagePath = getFilesDir().toString() + "/"
				+ DOWNLOADED_IMAGE_NAME;
		imageDownloaded.setImageDrawable(Drawable.createFromPath(imagePath));
	}

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
