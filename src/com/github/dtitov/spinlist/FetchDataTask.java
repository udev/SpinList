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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * Class for asynchronous launching the process of retrieving information over
 * HTTP. The fetching of data is processed on the background thread and the
 * result is passed to the UI thread.
 * 
 */
public class FetchDataTask extends AsyncTask<Void, Void, FbUser> {
	private Activity activity;
	private LazyAdapter adapter;
	private int position;
	private TextView textViewMate;
	private ProgressBar spinner;
	private String id;

	/**
	 * Get necessary UI objects.
	 */
	public FetchDataTask(Activity activity, LazyAdapter adapter, int position,
			TextView textView, ProgressBar spinner, String id) {
		this.activity = activity;
		this.adapter = adapter;
		this.position = position;
		this.textViewMate = textView;
		this.spinner = spinner;
		this.id = id;
	}

	/**
	 * Background bitmap fetching and pasting it into FbUser.
	 */
	@Override
	protected FbUser doInBackground(Void... params) {
		FbUser user = new FbUser(id);

		Bitmap bitmap = null;
		try {
			HttpURLConnection httpUrlConnection;
			httpUrlConnection = (HttpURLConnection) new java.net.URL(
					user.getPicture()).openConnection();
			httpUrlConnection.setReadTimeout(10000);
			httpUrlConnection.setConnectTimeout(10000);
			InputStream inputStream = httpUrlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					inputStream);
			bitmap = BitmapFactory.decodeStream(bufferedInputStream);
			bufferedInputStream.close();
			inputStream.close();
			httpUrlConnection.disconnect();

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

		user.setBitmap(bitmap);

		return user;
	}

	/**
	 * Setting the UI: hide progress bar, display both images (userpic on the
	 * left and tiny icon on the right) and user's full name
	 */
	@Override
	protected void onPostExecute(FbUser result) {
		super.onPostExecute(result);
		adapter.getFolks()[position] = result; // caching current user by it's
												// position
		textViewMate.setText(result.getName());
		spinner.setVisibility(View.GONE);
		textViewMate
				.setCompoundDrawablesWithIntrinsicBounds(
						new BitmapDrawable(activity.getResources(), result
								.getBitmap()),
						null,
						activity.getResources().getDrawable(
								R.drawable.facebook_icon), null);
	}
}
