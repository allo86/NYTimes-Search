package com.allo.nyt.ui.search;

import android.os.Bundle;

import com.allo.nyt.model.Article;

import org.parceler.Parcels;

import java.util.ArrayList;

import icepick.Bundler;

/**
 * Created by ALLO on 31/7/16.
 */
public class SearchActivityBundler implements Bundler<ArrayList<Article>> {
    @Override
    public void put(String s, ArrayList<Article> articles, Bundle bundle) {
        if (articles != null && articles.size() > 0) {
            bundle.putInt("articles_count", articles.size());
            for (int i = 0; i < articles.size(); i++) {
                bundle.putParcelable("article_" + String.valueOf(i), Parcels.wrap(articles.get(i)));
            }
        }
    }

    @Override
    public ArrayList<Article> get(String s, Bundle bundle) {
        ArrayList<Article> articles = new ArrayList<>();
        int articlesCount = bundle.getInt("articles_count");
        for (int i = 0; i < articlesCount; i++) {
            Article article = Parcels.unwrap(bundle.getParcelable("article_" + String.valueOf(i)));
            articles.add(article);
        }
        return articles;
    }
}
