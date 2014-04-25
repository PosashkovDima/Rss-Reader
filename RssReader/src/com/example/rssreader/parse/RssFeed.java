package com.example.rssreader.parse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RssFeed implements Serializable {

	private static final long serialVersionUID = 1L;
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