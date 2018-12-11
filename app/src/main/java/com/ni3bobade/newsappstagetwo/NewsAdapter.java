package com.ni3bobade.newsappstagetwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    // constructor
    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        super(context, 0, newsArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // check if the existing view is being reused
        View newsListItemView = convertView;
        if (newsListItemView == null) {
            newsListItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        // get the News object
        News currentNews = getItem(position);

        // news section
        TextView newsSectionTextView = newsListItemView.findViewById(R.id.newsSectionTextView);
        newsSectionTextView.setText(currentNews.getNewsSectionName());

        // news title
        TextView newsTitleTextView = newsListItemView.findViewById(R.id.newsTitleTextView);
        newsTitleTextView.setText(currentNews.getNewsTitle());

        // news author name
        TextView newsAuthorNameTextView = newsListItemView.findViewById(R.id.newsAuthorNameTextView);
        newsAuthorNameTextView.setText(currentNews.getNewsAuthorName());

        // news published date
        TextView newsPublishedDateTextView = newsListItemView.findViewById(R.id.newsPublishedDateTextView);
        newsPublishedDateTextView.setText(currentNews.getNewsPublishedDate());

        // return the news list item view
        return newsListItemView;

    }
}

