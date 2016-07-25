package com.allo.nyt.network;

import android.content.Context;

import com.allo.nyt.Application;
import com.allo.nyt.R;
import com.allo.nyt.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * REST Client
 * <p/>
 * Created by ALLO on 18/7/16.
 */
public class NYTRestClient {

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";

    private static final String API_KEY = "a2a8ed7806b044ae9d70c40e052528ef";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (!Utils.hasInternetConnection(getContext())) {
            Error error = new Error(getContext().getString(R.string.no_available_connection));
            responseHandler.onFailure(0, null, null, error);
            return;
        }

        if (params == null) {
            params = new RequestParams();
        }
        params.put("api-key", API_KEY);

        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (!Utils.hasInternetConnection(getContext())) {
            Error error = new Error(getContext().getString(R.string.no_available_connection));
            responseHandler.onFailure(0, null, null, error);
            return;
        }

        if (params == null) {
            params = new RequestParams();
        }
        params.put("api_key", API_KEY);

        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static Context getContext() {
        return Application.sharedInstance().getApplicationContext();
    }
}
