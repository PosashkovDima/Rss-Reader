package com.example.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionScreen extends Activity {

	private static final String DESCRIPTION = "description";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description_activity);
		TextView tv = (TextView) findViewById(R.id.textViewDescription);
		tv.setText(getIntent().getStringExtra(DESCRIPTION));
	}

}
