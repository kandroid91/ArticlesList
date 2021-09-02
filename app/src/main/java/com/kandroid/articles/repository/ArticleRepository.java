package com.kandroid.articles.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.kandroid.articles.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private static final String TAG = ArticleRepository.class.getSimpleName();
    private MutableLiveData<ArticleResponse> articleResponseMutableLiveData;
    private final APIRequest apiRequest;


    public ArticleRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(APIRequest.class);
    }

    public LiveData<ArticleResponse> getArticleListing() {
        articleResponseMutableLiveData = new MutableLiveData<>();
        apiRequest.getArticleListing().enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {
                Log.d(TAG, "API success");
                Log.d(TAG, new Gson().toJson(response.body()));
                if (response.body() != null) {
                    articleResponseMutableLiveData.setValue(response.body());
                } else {
                    articleResponseMutableLiveData.setValue(new ArticleResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "API failed");
                Log.d(TAG, t.getMessage());
                articleResponseMutableLiveData.setValue(new ArticleResponse());
            }
        });
        return articleResponseMutableLiveData;
    }
}
