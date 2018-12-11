package com.ni3bobade.newsappstagetwo;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    // tag for log messages
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // private constructor
    private QueryUtils() {

    }

    /**
     * Query the news dataset and return a list of {@link News} objects.
     */

    public static List<News> fetchNewsData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }

        // Extract relevant fields from the JSON response and create a list of {@link News}
        List<News> newsList = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link News}
        return newsList;

    }

    /**
     * Returns new URL object from the given string URL.
     */

    private static URL createUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

        } finally {

            if (urlConnection != null) {

                urlConnection.disconnect();
            }

            if (inputStream != null) {

                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();

            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * <p>
     * whole JSON response from the server.
     */

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {

                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    // return a list of News objects built by parsing the JSON response
    public static List<News> extractFeatureFromJson(String newsJson) {
        // if the JSON string is empty or null, return early
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }

        // create an empty ArrayList to which news can be added
        List<News> newsList = new ArrayList<>();

        // try to parse the JSON response
        try {
            // create a JSONObject from JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            // extract the JSONObject associated with key called "response"
            JSONObject newsResponseJsonObject = baseJsonResponse.getJSONObject("response");

            // extract the JSONArray associated with key called "results"
            JSONArray newsResultsJsonArray = newsResponseJsonObject.getJSONArray("results");

            for (int i = 0; i < newsResultsJsonArray.length(); i++) {

                JSONObject currentNewsJsonObject = newsResultsJsonArray.getJSONObject(i);

                // extract the value for key called "sectionName"
                String newsSectionName = currentNewsJsonObject.getString("sectionName");

                // extract the value for key called "webTitle"
                String newsWebTitle = currentNewsJsonObject.getString("webTitle");

                // extract the value for key called "webPublicationDate"
                String newsWebPublicationDate = currentNewsJsonObject.getString("webPublicationDate");

                // extract the value for key called "webUrl"
                String newsWebUrl = currentNewsJsonObject.getString("webUrl");

                JSONArray newsTagsJsonArray = currentNewsJsonObject.getJSONArray("tags");

                String newsAuthorName;
                if (newsTagsJsonArray.length() != 0) {

                    JSONObject currentTagsJSONObject = newsTagsJsonArray.getJSONObject(0);

                    // extract value for key called "webTitle"
                    newsAuthorName = currentTagsJSONObject.getString("webTitle");
                } else {
                    // if no author name is found make it null
                    newsAuthorName = null;
                }

                // create a new News object
                News news = new News(newsSectionName, newsWebTitle, newsAuthorName, newsWebPublicationDate, newsWebUrl);

                // add the new News to the newsList
                newsList.add(news);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // return the list of news
        return newsList;
    }

}

