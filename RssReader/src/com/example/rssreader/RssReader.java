package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rssreader.parse.HandleXmlRbk;
import com.example.rssreader.parse.RssItem;

public class RssReader extends Activity {

	private HandleXmlRbk myHandleRbkRss;
	private ListView listViewRss;
	private List<RssItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_main);
		listViewRss = (ListView) findViewById(R.id.listViewRss);
		runRssReaderRbk();
		displayRss();
	}

	public void runRssReaderRbk() {
		myHandleRbkRss = new HandleXmlRbk();
		myHandleRbkRss.fetchXml();
		while (!myHandleRbkRss.isParsingComplite())
			;
		rssItems = myHandleRbkRss.getItems();
	}

	public void displayRss() {
		ListAdapter listAdapterRss = new ListAdapter(this, rssItems);

		listViewRss.setAdapter(listAdapterRss);
		listViewRss.setOnItemClickListener(new ListListener(rssItems, this));

	}

}
