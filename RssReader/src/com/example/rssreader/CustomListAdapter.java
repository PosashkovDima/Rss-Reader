package com.example.rssreader;

import java.util.List;

import com.example.rssreader.parsexml.Feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<Feed> {
	private final Context context;
	private List<Feed> rssItems;

	private TextView textViewTitle;
	private TextView textViewPubDate;

	public CustomListAdapter(Context context, List<Feed> rssItems) {
		super(context, R.layout.list_item, rssItems);
		this.context = context;
		this.rssItems = rssItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);

		textViewTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
		textViewPubDate = (TextView) rowView.findViewById(R.id.textViewPubDate);

		textViewPubDate.setText(rssItems.get(position).getPubDate());
		textViewTitle.setText(rssItems.get(position).getTitle());

		return rowView;
	}
}
