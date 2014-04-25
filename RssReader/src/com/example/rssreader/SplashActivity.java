package com.example.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rssreader.parse.HandleXmlRbk;
import com.example.rssreader.parse.RssFeed;

public class SplashActivity extends Activity {

	private HandleXmlRbk myHandleRbkRss;
	private RssFeed feed;
	private static final String FEED = "feed";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		runRssReaderRbk();
	}

	public void runRssReaderRbk() {
		myHandleRbkRss = new HandleXmlRbk();
		myHandleRbkRss.fetchXml();
		while (!myHandleRbkRss.isParsingComplite())
			;
		feed = new RssFeed();
		feed.addItems(myHandleRbkRss.getItems());
		onPostFeedDownload();
	}

	private void onPostFeedDownload() {

		Bundle bundle = new Bundle();
		bundle.putSerializable(FEED, feed);

		Intent intent = new Intent(SplashActivity.this, RssReader.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
