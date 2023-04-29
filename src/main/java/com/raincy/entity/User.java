package com.raincy.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    public String username;
    public List<Article> articleList = new ArrayList<>();

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User insertArticle(String title, Integer likes) {
        this.articleList.add(new Article(title, likes));
        return this;
    }

    public Article findArticle(String title) {
        for (Article article : articleList) {
            if (article.articleTitle.equals(username)) {
                return article;
            }
        }
        return null;
    }

    public String toString() {
        return "User" + "{"
                + "username: " + this.username
                + "}";
    }
}
