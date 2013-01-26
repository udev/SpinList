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

import android.graphics.Bitmap;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;

/**
 * Data-holder for user instances. In context of this application can hold
 * arbitrary info. Fb API was chosen for access simplicity.
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
                this.picture = object.getJSONObject("picture").getJSONObject("data").getString("url");
                this.name = object.getString("name");
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

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
