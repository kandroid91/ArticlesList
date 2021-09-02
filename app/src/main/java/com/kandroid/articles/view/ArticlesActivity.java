package com.kandroid.articles.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.kandroid.articles.CommonFunc;
import com.kandroid.articles.R;
import com.kandroid.articles.adapter.ArticleAdapter;
import com.kandroid.articles.databinding.ActivityArticlesBinding;
import com.kandroid.articles.model.Article;
import com.kandroid.articles.model.ArticleResponse;
import com.kandroid.articles.view_model.ArticleViewModel;

import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity {
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private ArticleViewModel articleViewModel;
    private ActivityArticlesBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view
        viewBinding = ActivityArticlesBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        //view model
        articleViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ArticleViewModel.class);

        //Activity title
        viewBinding.toolbar.setTitle(R.string.app_name);
        setSupportActionBar(viewBinding.toolbar);

        setupArticleRecyclerView();
        setEventListener();

        //get live data from view_mode and and start timer when activity is launched
        getTimerValue();
    }

    private void setEventListener() {
        viewBinding.fabResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start timer
                articleViewModel.startTimer(60000);
            }
        });
    }

    private void setupArticleRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticlesActivity.this);
        viewBinding.rvArticles.setLayoutManager(linearLayoutManager);
        viewBinding.rvArticles.setHasFixedSize(true);
        articleAdapter = new ArticleAdapter(ArticlesActivity.this, articleArrayList);
        viewBinding.rvArticles.setAdapter(articleAdapter);
    }

    public void getArticleListing() {
        articleViewModel.getArticleListing().observe(this, new Observer<ArticleResponse>() {
            @Override
            public void onChanged(ArticleResponse articleResponse) {
                viewBinding.progressBar.setVisibility(View.GONE);
                if (articleResponse != null) {
                    if (articleResponse.getResult() != null) {
                        articleArrayList.clear();
                        articleArrayList.addAll(articleResponse.getResult().getArticle());
                        articleAdapter.notifyItemChanged(0);
                        viewBinding.rvArticles.setVisibility(View.VISIBLE);
                    } else {
                        viewBinding.tvErrorMessage.setVisibility(View.VISIBLE);
                        viewBinding.tvErrorMessage.setText(getResources().getString(R.string.no_article));
                    }
                }
            }
        });
    }

    private void getTimerValue() {
        articleViewModel.getTimerValue().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //call api when timer is finished other wise update timer value to user
                if (s.equals("00:00")) {
                    viewBinding.fabResetTimer.setEnabled(true);
                    //check internet connection before making network call
                    if (CommonFunc.hasInternetConnection(ArticlesActivity.this)) {
                        viewBinding.progressBar.setVisibility(View.VISIBLE);
                        viewBinding.tvErrorMessage.setVisibility(View.GONE);
                        getArticleListing();
                    } else {
                        viewBinding.tvErrorMessage.setText(R.string.no_internet_message);
                        viewBinding.tvErrorMessage.setVisibility(View.VISIBLE);
                    }
                    viewBinding.tvTimerMessage.setVisibility(View.GONE);
                    viewBinding.tvTimer.setVisibility(View.GONE);
                } else {
                    viewBinding.fabResetTimer.setEnabled(false);
                    viewBinding.tvTimerMessage.setVisibility(View.VISIBLE);
                    viewBinding.tvTimer.setVisibility(View.VISIBLE);
                    viewBinding.tvErrorMessage.setVisibility(View.GONE);
                    viewBinding.rvArticles.setVisibility(View.GONE);
                    viewBinding.tvTimer.setText(s);
                }
            }
        });
    }
}
