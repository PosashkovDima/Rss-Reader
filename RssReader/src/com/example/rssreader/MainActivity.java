package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
	private String finalUrl = "http://news.yandex.ru/hardware.rss";
	private HandleXml myHandle;
	private ListView listViewRss;
	private List<RssItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listViewRss = (ListView) findViewById(R.id.listView1);

		runRssReader();
	}

	public void runRssReader() {
		myHandle = new HandleXml(finalUrl);
		myHandle.fetchXml();
		while (!myHandle.isParsingComplite())
			;
		rssItems = myHandle.getItems();
		ListAdapter listAdapterRss = new ListAdapter(this, rssItems);

		listViewRss.setAdapter(listAdapterRss);
		listViewRss.setOnItemClickListener(new ListListener(rssItems, this));
	}

}
