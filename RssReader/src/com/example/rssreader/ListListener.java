package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.rssreader.parsexml.Feed;

public class ListListener implements OnItemClickListener {
	private List<Feed> feedsList;
	private Activity activity;
	private static final String EXTRA_DESCRIPTION = "description";

	private static final String EXTRA_IMAGE_URL = "image_url";

	public ListListener(List<Feed> listItems, Activity activity) {
		this.feedsList = listItems;
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {

		Intent intent = new Intent(activity, DescriptionActivity.class);

		intent.putExtra(EXTRA_DESCRIPTION, feedsList.get(pos).getDescription());

		intent.putExtra(EXTRA_IMAGE_URL, feedsList.get(pos).getImageUrl());

		activity.startActivity(intent);
	}
}
