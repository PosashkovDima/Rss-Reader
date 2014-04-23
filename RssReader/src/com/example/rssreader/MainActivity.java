package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {
	private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
	private HandleXml obj; 
	private ListView lv;
	private List<RssItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.listView1); 
	}

	public void fetch(View view) {
		obj = new HandleXml(finalUrl);
		obj.fetchXML();
		while (!obj.isParsingComplite())
			;
		rssItems = obj.getItems();
		ListAdapter adapter = new ListAdapter(this, rssItems);
		lv.setAdapter(adapter);
	}

}
