package com.example.rssreader;

import java.util.List;

import com.example.rssreader.parse.RssItem;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListListener implements OnItemClickListener {
	private List<RssItem> listItems;
	private Activity activity;
	private static final String DESCRIPTION = "description";

	public ListListener(List<RssItem> listItems, Activity activity) {
		this.listItems = listItems;
		this.activity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {

		Intent i = new Intent(activity, DescriptionScreenActivity.class);
		i.putExtra(DESCRIPTION, listItems.get(pos).getDescription());
		activity.startActivity(i);
	}
}
