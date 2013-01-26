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

import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * BaseAdapter extension with lazy loading. Item template is designed in
 * res/layout/item_user.xml
 */
public class LazyAdapter extends BaseAdapter {
    private ListActivity activity;
    private LayoutInflater layoutInflater;
    private ArrayList<String> names;
    private FbUser[] folks;
    private int folksNumber = 0;

    /**
     * Loading of all necessary objects: activity and user list. Getting the
     * inflater.
     */
    public LazyAdapter(ListActivity activity, ArrayList<String> names) {
        super();
        this.activity = activity;
        this.names = names;
        this.folks = new FbUser[this.names.size()];
        layoutInflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        new FetchDataTask(this, 0).execute(new Void[]{});
    }

    /**
     * Method for processing ListView items. Inflates the item layout (if it's
     * null) and passes the View to AsyncTask processing.
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
            viewHolder.textView = (TextView) item.findViewById(R.id.textViewUser);
            viewHolder.spinner = (ProgressBar) item.findViewById(R.id.progressBar);
            item.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) item.getTag();
        }

        /**
         * If the current user has been fetched we put cached data to the view.
         * Otherwise we clear the view.
         */
        if (cachedUser != null) {
            viewHolder.textView.setText(cachedUser.getName());
            viewHolder.spinner.setVisibility(View.GONE);
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
                    new BitmapDrawable(activity.getResources(), cachedUser
                            .getBitmap()), null, activity.getResources()
                    .getDrawable(R.drawable.facebook_icon), null);
        } else {
            viewHolder.textView.setText("");
            viewHolder.spinner.setVisibility(View.VISIBLE);
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(
                    null, null, null, null);
        }

        return item;
    }

    /**
     * Get count implementation.
     *
     * @return Number of items in the collection.
     */
    @Override
    public int getCount() {
        return names.size();
    }

    /**
     * Get item implementation.
     *
     * @param position Position of requested object.
     * @return Requested object.
     */
    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    /**
     * Get item ID implementation.
     *
     * @param position Position of requested ID.
     * @return Requested ID.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Here we cache each next user.
     *
     * @param user Incoming (from AsyncTask) user to cache.
     */
    public void cacheUser(FbUser user) {
        folks[folksNumber] = user;
        folksNumber++;
        notifyDataSetChanged(); //reload ListView
    }

    /**
     * Using ViewHolder patter we save time by avoiding calling findViewById
     * each time we need a view.
     */
    private static class ViewHolder {
        public TextView textView;
        public ProgressBar spinner;
    }
}