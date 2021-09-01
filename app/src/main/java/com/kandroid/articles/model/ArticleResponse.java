package com.kandroid.articles.model;

import com.google.gson.annotations.Expose;

public class ArticleResponse {
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
