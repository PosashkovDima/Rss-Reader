package com.example.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private String finalUrl = "http://news.yandex.ru/hardware.rss";
	private HandleXml obj;
	private EditText title, link, description, postDate;

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
//		title.setText(obj.getTitle());
//		link.setText(obj.getLink());
//		description.setText(obj.getDescription());
//		postDate.setText(obj.getPubDate());
	}

}
