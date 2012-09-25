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
	private TextView textView;
	private ProgressBar spinner;
	private String id;

	/**
	 * Get necessary UI objects.
	 */
	public FetchDataTask(Activity activity, TextView textView,
			ProgressBar spinner, String id) {
		this.activity = activity;
		this.textView = textView;
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
		textView.setText(result.getName());
		spinner.setVisibility(View.GONE);
		textView.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(
				activity.getResources(), result.getBitmap()), null, activity
				.getResources().getDrawable(R.drawable.facebook_icon), null);
	}
}
