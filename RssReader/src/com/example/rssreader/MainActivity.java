package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
	// private String finalUrl =
	// "http://tutorialspoint.com/android/sampleXML.xml";

	private String finalUrl = "http://news.yandex.ru/hardware.rss";
	private HandleXml obj;
	private ListView lv;
	private List<RssItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.listView1);

		fetch();
	}

	public void fetch() {
		obj = new HandleXml(finalUrl);
		obj.fetchXML();
		while (!obj.isParsingComplite())
			;
		rssItems = obj.getItems();
		ListAdapter adapter = new ListAdapter(this, rssItems);

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new ListListener(rssItems, this));
	}

}
