package com.example.rssreader.parsexml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class HandleXmlRbk {

	private List<Feed> feedsList;
	private String feedsUrl;

	public HandleXmlRbk(String feedsUrl) {
		feedsList = new ArrayList<Feed>();
		this.feedsUrl = feedsUrl;
	}

	/**
	 * Parse XML and store it to List<RssItem>
	 * 
	 * @param XmlPullParser
	 *            myParser
	 * */
	public void parseXmlAndStoreIt(XmlPullParser xmlPullParser) {
		int event;
		int attributeCount;
		String name;
		String text = null;
		Feed currentFeed = null;

		try {
			event = xmlPullParser.getEventType();

			while (event != XmlPullParser.END_DOCUMENT) {
				name = xmlPullParser.getName();
				switch (event) {
				case XmlPullParser.START_TAG:
					if (name.equalsIgnoreCase("item")) {
						currentFeed = new Feed();
						currentFeed.setImageUrl(null);

					} else if (name.equalsIgnoreCase("enclosure")) {

						attributeCount = xmlPullParser.getAttributeCount();
						for (int i = 0; i < attributeCount; i++) {
							if ("url".equals(xmlPullParser.getAttributeName(i))) {
								String enclosureUrl = xmlPullParser
										.getAttributeValue(i);
								if (!"".equals(enclosureUrl)) {
									currentFeed.setImageUrl(enclosureUrl);
								}
							}
						}
					}
					break;
				case XmlPullParser.TEXT:
					text = xmlPullParser.getText();
					break;
				case XmlPullParser.END_TAG:
					if (currentFeed != null) {
						if (name.equalsIgnoreCase("title")) {
							currentFeed.setTitle(text);

						} else if (name.equalsIgnoreCase("description")) {
							currentFeed.setDescription(text);

						} else if (name.equalsIgnoreCase("pubDate")) {
							currentFeed.setPubDate(text.replace(" +0400", ""));

						} else if (name.equalsIgnoreCase("item")) {
							feedsList.add(currentFeed);
							currentFeed = null;
						}
					}
					break;
				}
				try {
					event = xmlPullParser.next();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		} catch (XmlPullParserException e) {

			// e.printStackTrace();
		}

	}

	/**
	 * Set connection with content by url, download .xml and start parsing.
	 */
	public List<Feed> fetchFeeds() {
		InputStream stream = null;
		XmlPullParser parser;
		XmlPullParserFactory xmlFactoryObject;
		HttpURLConnection httpUrlConnection;
		URL url;
		try {
			url = new URL(feedsUrl);

			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setReadTimeout(10000);
			httpUrlConnection.setConnectTimeout(15000);

			httpUrlConnection.setRequestMethod("GET");

			httpUrlConnection.setDoInput(true);
			httpUrlConnection.connect();
			stream = httpUrlConnection.getInputStream();

			xmlFactoryObject = XmlPullParserFactory.newInstance();
			parser = xmlFactoryObject.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(stream, null);
			parseXmlAndStoreIt(parser);
		} catch (XmlPullParserException e1) {
			feedsList = null;
		} catch (IOException e) {
			feedsList = null;
		} finally {

			try {
				stream.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return feedsList;
	}
}