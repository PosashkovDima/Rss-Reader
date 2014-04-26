package com.example.rssreader.parse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class HandleXmlRbk {

	private List<Feed> feedsList;

	private static final String URL_RBK = "http://static.feed.rbc.ru/rbc/internal/rss.rbc.ru/rbc.ru/news.rss";
	private XmlPullParserFactory xmlFactoryObject;
	private Feed currentFeed;

	public HandleXmlRbk() {
		feedsList = new ArrayList<Feed>();

	}

	/**
	 * Parse XML and store it to List<RssItem>
	 * 
	 * @param XmlPullParser
	 *            myParser
	 * */
	public void parseXmlAndStoreIt(XmlPullParser myParser) {
		int event;
		String text = null;
		try {
			event = myParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				String name = myParser.getName();
				switch (event) {
				case XmlPullParser.START_TAG:
					if (name.equals("item")) {
						currentFeed = new Feed();
					}
					break;
				case XmlPullParser.TEXT:
					text = myParser.getText();
					break;
				case XmlPullParser.END_TAG:
					if (currentFeed != null) {
						if (name.equals("title")) {
							currentFeed.setTitle(text);

						} else if (name.equals("link")) {
							currentFeed.setLink(text);

						} else if (name.equals("description")) {
							currentFeed.setDescription(text);

						} else if (name.equals("pubDate")) {

							currentFeed.setPubDate(text.replace(" +0400", ""));
							feedsList.add(currentFeed);
							currentFeed = null;
						}
					}
					break;
				}
				event = myParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set connection with content buy url, download .xml and start parsing.
	 */
	public List<Feed> fetchFeeds() {
		try {
			URL url = new URL(URL_RBK);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			conn.connect();
			InputStream stream = conn.getInputStream();

			xmlFactoryObject = XmlPullParserFactory.newInstance();
			XmlPullParser myparser = xmlFactoryObject.newPullParser();

			myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			myparser.setInput(stream, null);

			parseXmlAndStoreIt(myparser);

			stream.close();

			return feedsList;
		} catch (Exception e) {
			return null;
		}
	}
}