package com.ni3bobade.newsappstagetwo;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    // query url
    private String url;

    // constructor
    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (url == null) {
            return null;
        }

        // perform the network request, parse the response, and extract the list of news
        return QueryUtils.fetchNewsData(url);
    }
}
