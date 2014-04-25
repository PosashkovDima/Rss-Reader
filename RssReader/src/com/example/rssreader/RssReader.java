package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rssreader.parse.RssFeed;
import com.example.rssreader.parse.RssItem;

public class RssReader extends Activity {

	private ListView listViewRss;
	private List<RssItem> rssItems;
	private RssFeed feed;
	private static final String FEED = "feed";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getItems();
		onDisplayRss();
	}

	private void getItems() {
		feed = (RssFeed) getIntent().getExtras().get(FEED);
		rssItems = feed.getItems();
	}

	private void onDisplayRss() {
		setContentView(R.layout.rss_main);
		listViewRss = (ListView) findViewById(R.id.listViewRss);
		ListAdapter listAdapterRss = new ListAdapter(this, rssItems);

		listViewRss.setAdapter(listAdapterRss);
		listViewRss.setOnItemClickListener(new ListListener(rssItems, this));

	}

}
