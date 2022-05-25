package com.example.github.network;

import android.net.Uri;

import com.example.github.entity.Repository;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpClient {

    private static final String GITHUB_BASE_URL = "https://api.github.com";
    private static final String REPOSITORIES = "repositories";
    private static final String REPOS = "repos";
    private static final String SEARCH = "search";
    private static final String Q_PARAM = "q";


    public Repository getRepository(String repoName, String userLogin) throws IOException, JSONException {
        String requestUrl = Uri
                .parse(GITHUB_BASE_URL)
                .buildUpon()
                .appendPath(REPOS)
                .appendPath(userLogin)
                .appendPath(repoName)
                .build()
                .toString();
        String response = getResponse(requestUrl);

        return JsonParser.getRepository(response);
    }

    public ArrayList<Repository> getRepositories(String query) throws IOException, JSONException {
        String requestUrl = Uri
                .parse(GITHUB_BASE_URL)
                .buildUpon()
                .appendPath(SEARCH)
                .appendPath(REPOSITORIES)
                .appendQueryParameter(Q_PARAM, query)
                .build()
                .toString();

        String response = getResponse(requestUrl);

        return JsonParser.getRepositories(response);
    }

    private String getResponse(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.connect();
            InputStream stream;
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
            } else {
                stream = connection.getErrorStream();
            }
            return convertStreamToString(stream);
        } finally {
            connection.disconnect();
        }
    }

    private String convertStreamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null){
            builder.append(line).append("\n");
        }
        stream.close();

        return builder.toString();
    }
}
