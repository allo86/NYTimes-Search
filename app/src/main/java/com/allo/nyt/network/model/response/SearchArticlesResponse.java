package com.allo.nyt.network.model.response;

import com.allo.nyt.model.Article;
import com.allo.nyt.model.Meta;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * SearchArticlesResponse
 * <p/>
 * Created by ALLO on 24/7/16.
 */
@Parcel
public class SearchArticlesResponse {

    @SerializedName("meta")
    Meta meta;

    @SerializedName("docs")
    ArrayList<Article> articles;

    public SearchArticlesResponse() {

    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
