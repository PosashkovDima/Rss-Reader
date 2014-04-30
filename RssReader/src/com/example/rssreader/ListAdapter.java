package com.example.rssreader;

import java.util.List;

import com.example.rssreader.parsexml.Feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Feed> {
	private final Context context;
	private List<Feed> feedsList;

	private TextView textViewTitle;
	private TextView textViewPubDate;

	public ListAdapter(Context context, List<Feed> rssItems) {
		super(context, R.layout.i_list_item, rssItems);
		this.context = context;
		this.feedsList = rssItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.i_list_item, parent, false);

		textViewTitle = (TextView) rowView.findViewById(R.id.text_view_title);
		textViewPubDate = (TextView) rowView.findViewById(R.id.text_view_pub_date);

		textViewPubDate.setText(feedsList.get(position).getPubDate());
		textViewTitle.setText(feedsList.get(position).getTitle());

		return rowView;
	}
}
