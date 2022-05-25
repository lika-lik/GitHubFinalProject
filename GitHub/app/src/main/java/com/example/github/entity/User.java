package com.example.github.entity;

import androidx.annotation.NonNull;

public class User {
    private String login;
    private int id;
    private String avatar_url;

    public User(String login, int id, String avatar_url) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" + "login=" + login + "id=" + id + "avatar_url" + avatar_url + "}";
    }
}
