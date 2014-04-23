package com.example.rssreader;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class ListAdapter extends ArrayAdapter<RssItem> {
	private final Context context;
	private List<RssItem> rssItems;

	public ListAdapter(Context context, List<RssItem> rssItems) {
		super(context, R.layout.rowlayout, rssItems);
		this.context = context;
		this.rssItems = rssItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		EditText editTextTitle = (EditText) rowView
				.findViewById(R.id.editTextTitle);
		EditText editTextLink = (EditText) rowView
				.findViewById(R.id.editTextLink);
		EditText editTextDescription = (EditText) rowView
				.findViewById(R.id.editTextDescription);
		editTextTitle.setText(rssItems.get(position).getTitle());
		editTextLink.setText(rssItems.get(position).getLink());
		editTextDescription.setText(rssItems.get(position).getDescription());
		return rowView;
	}
}
