package com.github.dtitov.spinlist;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.graphics.Bitmap;

/**
 * 
 * Data-holder for user instances. In context of this application can hold
 * arbitrary info. Fb API was chosen for access simplicity.
 * 
 */
public class FbUser {
	private final String GRAPH_API_URL = "https://graph.facebook.com/user?fields=picture,name";
	private String name;
	private String picture;
	private Bitmap bitmap;

	public FbUser(String id) {
		/**
		 * Retrieving information about user from Graph API by parsing JSON
		 * responce. Downloading it's userpic.
		 */
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(new HttpGet(
					GRAPH_API_URL.replace("user", id)));
			StatusLine statusLine = httpResponse.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				httpResponse.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();

				JSONObject object = (JSONObject) new JSONTokener(responseString)
						.nextValue();
				this.name = object.getString("name");
				this.picture = object.getString("picture");
			} else {
				httpResponse.getEntity().getContent().close();
			}
		} catch (Exception e) {
		}
	}

	public String getName() {
		return name;
	}

	public String getPicture() {
		return picture;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return this.bitmap;
	}
}
