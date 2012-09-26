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

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * BaseAdapter extension with lazy loading. Item template is designed in
 * res/layout/item_user.xml
 * 
 */
public class LazyAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater layoutInflater;
	private ArrayList<String> names;
	private FbUser[] folks; // array for caching users

	/**
	 * 
	 * Loading of all necessary objects: activity and user list. Getting the
	 * inflater.
	 */
	public LazyAdapter(Activity activity, ArrayList<String> names) {
		super();
		this.activity = activity;
		this.names = names;
		this.folks = new FbUser[this.names.size()];
		layoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 
	 * Using ViewHolder patter we save time by avoiding calling findViewById
	 * each time we need a view.
	 * 
	 */
	private static class ViewHolder {
		public TextView textView;
		public ProgressBar spinner;
	}

	/**
	 * Method for processing ListView items. Inflates the item layout (if it's
	 * null) and passes the View to AyncTask processing.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		FbUser cachedUser = folks[position]; // trying to get a user from cache

		View item = convertView;
		ViewHolder viewHolder;

		/**
		 * If item has not been created yet, we inflate it and pass its personal
		 * ViewHolder as a tag parameter. Otherwise we just get an existing
		 * ViewHolder.
		 */
		if (item == null) {
			item = layoutInflater.inflate(R.layout.item_user, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) item
					.findViewById(R.id.textViewUser);
			viewHolder.spinner = (ProgressBar) item
					.findViewById(R.id.progressBar);
			item.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) item.getTag();
		}

		/**
		 * If the current user is not cached yet, we execute async task for
		 * retreiving the information. Otherwise we just load data from the
		 * cached object.
		 */
		if (cachedUser == null) {
			viewHolder.textView.setText("");
			viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
					null,
					null,
					activity.getResources().getDrawable(
							R.drawable.facebook_icon), null);
			viewHolder.spinner.setVisibility(View.VISIBLE);
			new FetchDataTask(activity, this, position, viewHolder.textView,
					viewHolder.spinner, names.get(position))
					.execute(new Void[] {});
		} else {
			viewHolder.textView.setText(cachedUser.getName());
			viewHolder.spinner.setVisibility(View.GONE);
			viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
					new BitmapDrawable(activity.getResources(), cachedUser
							.getBitmap()), null, activity.getResources()
							.getDrawable(R.drawable.facebook_icon), null);
		}
		return item;
	}

	@Override
	public int getCount() {
		return names.size();
	}

	@Override
	public Object getItem(int position) {
		return names.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public FbUser[] getFolks() {
		return folks;
	}
}