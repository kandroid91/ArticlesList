package com.kandroid.articles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kandroid.articles.CommonFunc;
import com.kandroid.articles.R;
import com.kandroid.articles.databinding.ArticlesRvItemBinding;
import com.kandroid.articles.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Article> articleArrayList;

    public ArticleAdapter(Context context, ArrayList<Article> articleArrayList) {
        this.context = context;
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticlesRvItemBinding itemBinding = ArticlesRvItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleArrayList.get(position);
        holder.itemBinding.tvName.setText(article.getName());
        holder.itemBinding.tvAuthor.setText(article.getAuthor());
        holder.itemBinding.tvCategory.setText(article.getCategoryName());
        Picasso.with(context)
                .load(article.getImage())
                .resize(CommonFunc.dpToPx(context, 120), CommonFunc.dpToPx(context, 120))
                .error(R.drawable.image_placeholder)
                .into(holder.itemBinding.ivArticleImage);
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ArticlesRvItemBinding itemBinding;

        public ViewHolder(ArticlesRvItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
