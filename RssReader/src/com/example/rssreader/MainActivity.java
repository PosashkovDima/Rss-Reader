package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private String finalUrl = "http://news.yandex.ru/hardware.rss";
	private HandleXml obj;
	private EditText title, link, description, postDate;
	private List<RssItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		title = (EditText) findViewById(R.id.editTextTitle);
		link = (EditText) findViewById(R.id.editTextLink);
		description = (EditText) findViewById(R.id.editTextDescription);
		postDate = (EditText) findViewById(R.id.editTextPubDate);
	}

	public void fetch(View view) {
		obj = new HandleXml(finalUrl);
		obj.fetchXML();
		while (!obj.isParsingComplite())
			;
		rssItems = obj.getItems();
		
		title.setText(rssItems.get(0).getTitle());
		Log.e("handleXML", rssItems.get(0).getTitle());
		
		link.setText(rssItems.get(0).getLink());
		Log.e("handleXML", rssItems.get(0).getLink());
		
		description.setText(rssItems.get(0).getDescription());
		Log.e("handleXML", "121313");
		// postDate.setText(obj.getPubDate());
	}

}
