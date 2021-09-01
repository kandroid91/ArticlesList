package com.kandroid.articles.model;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Result {
    @Expose
    private List<Article> article;
    @Expose
    private List<Category> category;

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
