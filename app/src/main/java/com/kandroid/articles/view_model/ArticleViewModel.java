package com.kandroid.articles.view_model;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kandroid.articles.model.ArticleResponse;
import com.kandroid.articles.repository.ArticleRepository;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ArticleViewModel extends AndroidViewModel {
    private final ArticleRepository articleRepository;
    private MutableLiveData<String> timerMutableLiveData;
    private LiveData<ArticleResponse> articleResponseLiveData;

    public ArticleViewModel(Application application) {
        super(application);
        articleRepository = new ArticleRepository();
    }

    public LiveData<ArticleResponse> getArticleListing() {
        return articleResponseLiveData;
    }

    public LiveData<String> getTimerValue() {
        if (timerMutableLiveData == null) {
            //start the timer when the very first time counter Mutable data is initialised i.e. at the time of screen launch
            timerMutableLiveData = new MutableLiveData<>();
            startTimer(10000);
        }
        return timerMutableLiveData;
    }

    public void startTimer(long millisInFuture) {
        new CountDownTimer(millisInFuture, 1000) {
            public void onTick(long millisUntilFinished) {
                // formatting in 2 digits
                NumberFormat f = new DecimalFormat("00");
                long minute = (millisUntilFinished / 60000) % 60;
                long second = (millisUntilFinished / 1000) % 60;
                String timerText = f.format(minute) + ":" + f.format(second);
                //if time is ending, make api call
                if (timerText.equals("00:00"))
                    articleResponseLiveData = articleRepository.getArticleListing();
                timerMutableLiveData.setValue(timerText);
            }

            public void onFinish() {
            }
        }.start();
    }
}