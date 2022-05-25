package com.example.github.entity;

import androidx.annotation.NonNull;

import java.util.Date;

public class Repository {
    private int id;
    private String name;
    private String description;
    private String created_at;
    private String updated_at;
    private int stargazers_count;
    private String language;
    private int forks_count;
    private User owner;

    public Repository(int id, String name, String description, String created_at, String updated_at, int stargazers_count, String language, int forks_count, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.stargazers_count = stargazers_count;
        this.language = language;
        this.forks_count = forks_count;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public String getLanguage() {
        return language;
    }

    public int getForks_count() {
        return forks_count;
    }

    public User getOwner() {
        return owner;
    }

    @NonNull
    @Override
    public String toString() {
        return "Repository{" + "id=" + id + " name=" + name + " description=" + description + " created_at=" + created_at + " updated_at=" + updated_at +
                " stargazers_count=" + stargazers_count + " language=" + language + " forks_count=" + forks_count + " owner=" + owner.toString() + "}";
    }
}
