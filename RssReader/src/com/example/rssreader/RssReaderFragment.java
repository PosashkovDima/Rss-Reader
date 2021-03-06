package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.rssreader.parsexml.Feed;
import com.example.rssreader.parsexml.HandleXmlRbk;

public class RssReaderFragment extends Fragment {
	private TaskCallbacks callbacks;
	private List<Feed> feedsList;
	private boolean isDowbloaded;

	public static interface TaskCallbacks {

		void onPostExecute();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		callbacks = (TaskCallbacks) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		AsyncLoadXmlFeed asyncLoadXml;
		isDowbloaded = false;
		asyncLoadXml = new AsyncLoadXmlFeed();
		asyncLoadXml.execute();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		callbacks = null;
	}

	public List<Feed> getFeedList() {
		return feedsList;
	}

	public boolean isDowbloaded() {
		return isDowbloaded;

	}

	private class AsyncLoadXmlFeed extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			HandleXmlRbk handleRbkRss;
			handleRbkRss = new HandleXmlRbk(
					"http://static.feed.rbc.ru/rbc/internal/rss.rbc.ru/rbc.ru/news.rss");
			feedsList = handleRbkRss.fetchFeeds();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (callbacks != null) {
				callbacks.onPostExecute();
				isDowbloaded = true;
			}
		}
	}
}
