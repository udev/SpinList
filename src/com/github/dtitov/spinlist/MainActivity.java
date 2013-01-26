/*
 * Copyright 2012 Dmytro Titov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		users.add("huskyrescueofficial");
		users.add("kasabian");
		users.add("lanadelrey");
		users.add("muse");
		users.add("officialplacebo");
		users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");
        users.add("theraveonettes");


		/**
		 * Create and initialize our own adapter.
		 */
		LazyAdapter lazyAdapter = new LazyAdapter(this, users);
		setListAdapter(lazyAdapter);
	}
}
