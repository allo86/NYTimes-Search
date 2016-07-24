package com.allo.nyt.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by ALLO on 18/7/16.
 */
public class NYTRestClient {

    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";

    private static final String API_KEY = "a2a8ed7806b044ae9d70c40e052528ef";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (params == null) {
            params = new RequestParams();
        }
        params.put("api-key", API_KEY);

        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (params == null) {
            params = new RequestParams();
        }
        params.put("api_key", API_KEY);

        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
