package com.allo.nyt.network;

import android.util.Log;

import com.allo.nyt.network.callbacks.SearchArticlesCallback;
import com.allo.nyt.network.deserializer.DateDeserializer;
import com.allo.nyt.network.model.request.SearchArticlesRequest;
import com.allo.nyt.network.model.response.SearchArticlesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ALLO on 18/7/16.
 */
public class NYTRestClientImplementation {

    private static final String TAG_LOG = NYTRestClientImplementation.class.getCanonicalName();

    public static void getArticles(SearchArticlesRequest request,
                                   final SearchArticlesCallback callback) {
        RequestParams params = new RequestParams();
        params.put("page", request.getPage());

        NYTRestClient.get("articlesearch.json", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Date.class, new DateDeserializer())
                            .create();
                    SearchArticlesResponse searchArticlesResponse = gson.fromJson(response, SearchArticlesResponse.class);
                    callback.onSuccess(searchArticlesResponse);
                } catch (JsonSyntaxException ex) {
                    Log.e(TAG_LOG, ex.toString());
                    callback.onError(new java.lang.Error(ex.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onError(new Error(throwable.getMessage()));
            }
        });
    }

}
