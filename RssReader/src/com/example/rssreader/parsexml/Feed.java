package com.example.rssreader.parsexml;

public class Feed {
	private String imageUrl;
	private String title;
	private String description;
	private String pubDate;

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageLink) {
		this.imageUrl = imageLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
