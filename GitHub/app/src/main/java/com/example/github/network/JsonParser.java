package com.example.github.network;

import com.example.github.entity.Repository;
import com.example.github.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public static Repository getRepository(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return getRepository(jsonObject);
    }

    public static ArrayList<Repository> getRepositories(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        ArrayList<Repository> repos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRepo = jsonArray.getJSONObject(i);
            repos.add(getRepository(jsonRepo));
        }
        return repos;
    }

    private static Repository getRepository(JSONObject object) throws JSONException {
        JSONObject jsonOwner = object.getJSONObject("owner");
        User owner = new User(jsonOwner.getString("login"),
                jsonOwner.getInt("id"),
                jsonOwner.getString("avatar_url"));

        Repository repository = new Repository(object.getInt("id"),
                object.getString("name"),
                object.getString("description"),
                object.getString("created_at"),
                object.getString("updated_at"),
                object.getInt("stargazers_count"),
                object.getString("language"),
                object.getInt("forks_count"),
                owner
        );
        return repository;
    }
}
