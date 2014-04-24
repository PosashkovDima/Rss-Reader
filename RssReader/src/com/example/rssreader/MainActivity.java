package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	private HandleXmlYandex myHandleYandexRss;
	private HandleXmlRbk myHandleRbkRss;
	private ListView listViewRss;
	private List<RssItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listViewRss = (ListView) findViewById(R.id.listView1);

		runRssReaderRbk();
		displayRss();
	}

	public void runRssReaderYandex() {
		myHandleYandexRss = new HandleXmlYandex();
		myHandleYandexRss.fetchXml();
		while (!myHandleYandexRss.isParsingComplite())
			;
		rssItems = myHandleYandexRss.getItems();
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
