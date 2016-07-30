package com.allo.nyt.network;

import android.content.Context;
import android.util.Log;

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

    private static final String TAG_LOG = NYTRestClient.class.getCanonicalName();

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
        params.put(NetworkConstants.API_KEY_PARAM, NetworkConstants.API_KEY_VALUE);

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
        params.put(NetworkConstants.API_KEY_PARAM, NetworkConstants.API_KEY_VALUE);

        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String url = NetworkConstants.BASE_URL + relativeUrl;
        Log.d(TAG_LOG, url);
        return url;
    }

    private static Context getContext() {
        return Application.sharedInstance().getApplicationContext();
    }
}
