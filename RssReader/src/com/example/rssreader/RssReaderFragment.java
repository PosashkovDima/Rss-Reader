package com.example.rssreader;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.rssreader.parse.Feed;
import com.example.rssreader.parse.HandleXmlRbk;

public class RssReaderFragment extends Fragment {
	private TaskCallbacks mCallbacks;
	private AsyncLoadXmlFeed asyncLoadXml;
	private List<Feed> feedsList;
	private HandleXmlRbk myHandleRbkRss;
	private boolean isDowbloaded;

	public static interface TaskCallbacks {

		void onPostExecute();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mCallbacks = (TaskCallbacks) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
		isDowbloaded = false;
		asyncLoadXml = new AsyncLoadXmlFeed();
		asyncLoadXml.execute();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
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
			Log.e("asdas", "doInBackground()");
			myHandleRbkRss = new HandleXmlRbk();
			feedsList = myHandleRbkRss.fetchFeeds();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (mCallbacks != null) {
				mCallbacks.onPostExecute();
				isDowbloaded = true;
			}
		}
	}
}
