package com.raincy.entity;

import lombok.Data;

@Data
public class Article {
    public String articleTitle;
    public Integer likes;

    public Article(String title, Integer likes) {
        this.articleTitle = title;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Article" + "{"
                + "Title: " + this.articleTitle
                + "Likes: " + this.likes
                + "}";
    }

    public Integer compareTo(Article a) {
        return likes > a.likes ? -1 : 1;
    }
}
