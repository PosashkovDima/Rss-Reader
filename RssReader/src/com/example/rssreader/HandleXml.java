package com.example.rssreader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class HandleXml {

	private List<RssItem> rssItems;

	private String urlString = null;
	private XmlPullParserFactory xmlFactoryObject;
	private volatile boolean isParsingComplete = false;
	private RssItem currentItem;

	public HandleXml(String url) {
		this.urlString = url;
		rssItems = new ArrayList<RssItem>();
	}

	public boolean isParsingComplite() {
		return isParsingComplete;
	}

	public List<RssItem> getItems() {
		return rssItems;
	}

	/**
	 * Parse XML and save it to List<RssItem>
	 * 
	 * @param XmlPullParser
	 *            myParser
	 * */
	public void parseXMLAndStoreIt(XmlPullParser myParser) {
		int event;
		String text = null;

		try {
			event = myParser.getEventType();

			while (event != XmlPullParser.END_DOCUMENT) {

				String name = myParser.getName();

				switch (event) {
				case XmlPullParser.START_TAG:
					if (name.equals("item")) {
						currentItem = new RssItem();

					}
					break;
				case XmlPullParser.TEXT:
					text = myParser.getText();
					break;
				case XmlPullParser.END_TAG:
					if (currentItem != null) {
						if (name.equals("title")) {
							currentItem.setTitle(text);

							Log.e("handleXML", name);
							Log.e("handleXML", currentItem.getTitle());

						} else if (name.equals("link")) {
							currentItem.setLink(text);

							Log.e("handleXML", name);

						} else if (name.equals("description")) {
							currentItem.setDescription(text);

							Log.e("handleXML", name);

							rssItems.add(currentItem);
							currentItem = null;
						}
					}
					break;
				}
				event = myParser.next();
				Log.e("handleXML", "next:");
			}
			Log.e("handleXML", "isParsingComplete=true");
			isParsingComplete = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchXML() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(urlString);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(10000);
					conn.setConnectTimeout(15000);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);

					conn.connect();
					InputStream stream = conn.getInputStream();

					xmlFactoryObject = XmlPullParserFactory.newInstance();
					XmlPullParser myparser = xmlFactoryObject.newPullParser();

					myparser.setFeature(
							XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
					myparser.setInput(stream, null);
					parseXMLAndStoreIt(myparser);
					stream.close();
				} catch (Exception e) {
				}
			}
		});
		thread.start();
	}
}