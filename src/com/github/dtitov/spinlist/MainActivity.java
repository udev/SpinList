package com.github.dtitov.spinlist;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Create and fill list of usernames.
		 */
		ArrayList<String> users = new ArrayList<String>();
		users.add("adele");
		users.add("coldplay");
		users.add("gotye");
		users.add("kasabian");
		users.add("lanadelrey");
		users.add("officialplacebo");

		/**
		 * Create and initialize our own adapter.
		 */
		LazyAdapter lazyAdapter = new LazyAdapter(this, users);
		setListAdapter(lazyAdapter);
	}
}
