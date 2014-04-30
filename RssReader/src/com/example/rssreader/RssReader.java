package com.example.rssreader;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rssreader.RssReaderFragment.TaskCallbacks;
import com.example.rssreader.parsexml.Feed;

public class RssReader extends FragmentActivity implements TaskCallbacks {

	private ListView listViewRss;
	private List<Feed> feedsList;
	private ProgressBar progressBar;
	private RssReaderFragment rssReaderFragment;
	private static final String TAG_TASK_FRAGMENT = "task_fragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_home);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		listViewRss = (ListView) findViewById(R.id.list_view_rss);

		FragmentManager fm = getSupportFragmentManager();
		rssReaderFragment = (RssReaderFragment) fm
				.findFragmentByTag(TAG_TASK_FRAGMENT);

		if (rssReaderFragment == null) {
			rssReaderFragment = new RssReaderFragment();
			fm.beginTransaction().add(rssReaderFragment, TAG_TASK_FRAGMENT)
					.commit();

		} else if (rssReaderFragment.isDowbloaded()) {

			setProgressBarInvisibility();
			onDisplayRss();
		}
	}

	private void setProgressBarInvisibility() {
		progressBar.setVisibility(View.INVISIBLE);
	}

	private void onDisplayRss() {

		feedsList = rssReaderFragment.getFeedList();
		if (feedsList == null) {
			Toast.makeText(getApplicationContext(), "Wrong, try again later.",
					Toast.LENGTH_LONG).show();
		} else {
			ListAdapter listAdapterRss = new ListAdapter(this, feedsList);

			listViewRss.setAdapter(listAdapterRss);
			listViewRss
					.setOnItemClickListener(new ListListener(feedsList, this));
		}
	}

	@Override
	public void onPostExecute() {

		setProgressBarInvisibility();
		onDisplayRss();
	}
}
