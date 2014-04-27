package com.example.rssreader;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.rssreader.RssReaderFragment.TaskCallbacks;
import com.example.rssreader.parse.Feed;

public class RssReader extends FragmentActivity implements TaskCallbacks {

	private ListView listViewRss;
	private List<Feed> feedsList;
	private ProgressBar mProgressBar;
	private RssReaderFragment readerFragment;
	private static final String TAG_TASK_FRAGMENT = "task_fragment";

	// fragment?
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_list);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		listViewRss = (ListView) findViewById(R.id.listViewRss);

		FragmentManager fm = getSupportFragmentManager();
		readerFragment = (RssReaderFragment) fm
				.findFragmentByTag(TAG_TASK_FRAGMENT);

		if (readerFragment == null) {
			readerFragment = new RssReaderFragment();
			fm.beginTransaction().add(readerFragment, TAG_TASK_FRAGMENT)
					.commit();

		} else if (readerFragment.isDowbloaded()) {

			setProgressBarInvisibility();
			onDisplayRss();
		}
	}

	private void setProgressBarInvisibility() {
		mProgressBar.setVisibility(4);
	}

	private void onDisplayRss() {

		feedsList = readerFragment.getFeedList();
		CustomListAdapter listAdapterRss = new CustomListAdapter(this,
				feedsList);

		listViewRss.setAdapter(listAdapterRss);
		listViewRss.setOnItemClickListener(new ListListener(feedsList, this));

	}

	@Override
	public void onPostExecute() {

		setProgressBarInvisibility();
		onDisplayRss();
	}
}
