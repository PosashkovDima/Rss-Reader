package com.example.rssreader.parse;

import java.util.ArrayList;
import java.util.List;

public class RssFeed {

	private List<RssItem> itemList;

	RssFeed() {
		itemList = new ArrayList<RssItem>();
	}

	void addItems(List<RssItem> itemList) {
		this.itemList = itemList;
	}

	public List<RssItem> getItems() {
		return itemList;
	}

}