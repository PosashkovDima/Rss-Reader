package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.rssreader.parse.HandleXmlRbk;
import com.example.rssreader.parse.RssFeed;
import com.example.rssreader.parse.RssItem;

public class RssReader extends Activity {

	private ListView listViewRss;
	private List<RssItem> rssItems;
	private RssFeed feed;
	private HandleXmlRbk myHandleRbkRss;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_list);

		new AsyncLoadXMLFeed().execute();
	}

	private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			myHandleRbkRss = new HandleXmlRbk();
			feed = myHandleRbkRss.fetchXml();
			rssItems = feed.getItems();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			onDisplayRss();
		}
	}

	private void onDisplayRss() {
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		listViewRss = (ListView) findViewById(R.id.listViewRss);

		mProgressBar.setVisibility(4);
		CustomListAdapter listAdapterRss = new CustomListAdapter(this, rssItems);

		listViewRss.setAdapter(listAdapterRss);
		listViewRss.setOnItemClickListener(new ListListener(rssItems, this));

	}
}
