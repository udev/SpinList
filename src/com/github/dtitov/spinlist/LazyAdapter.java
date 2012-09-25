package com.github.dtitov.spinlist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
	private ArrayList<String> folks;

	/**
	 * 
	 * Loading of all necessary objects: activity and user list. Getting the
	 * inflater.
	 */
	public LazyAdapter(Activity activity, ArrayList<String> folks) {
		super();
		this.activity = activity;
		this.folks = folks;
		layoutInflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Method for processing ListView items. Inflates the item layout (if it's
	 * null) and passes the View to AyncTask processing.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if (item == null) {
			item = layoutInflater.inflate(R.layout.item_user, null);
			TextView textViewMate = (TextView) item
					.findViewById(R.id.textViewUser);
			ProgressBar spinner = (ProgressBar) item
					.findViewById(R.id.progressBar);
			new FetchDataTask(activity, textViewMate, spinner,
					folks.get(position)).execute(new Void[] {});
		}
		return item;
	}

	@Override
	public int getCount() {
		return folks.size();
	}

	@Override
	public Object getItem(int position) {
		return folks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}