package com.kandroid.articles.repository;

import com.kandroid.articles.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface APIRequest {
    @POST("getArticleListing")
    Call <ArticleResponse> getArticleListing();
}
