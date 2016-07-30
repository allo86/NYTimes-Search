package com.allo.nyt.network;

import android.content.Context;
import android.util.Log;

import com.allo.nyt.Application;
import com.allo.nyt.R;
import com.allo.nyt.network.callbacks.SearchArticlesCallback;
import com.allo.nyt.network.model.request.SearchArticlesRequest;
import com.allo.nyt.network.model.response.SearchArticlesResponse;
import com.allo.nyt.network.utils.NetworkUtils;
import com.allo.nyt.ui.filter.model.Filter;
import com.allo.nyt.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * REST Client implementation
 * <p/>
 * Created by ALLO on 18/7/16.
 */
public class NYTRestClientImplementation {

    private static final String TAG_LOG = NYTRestClientImplementation.class.getCanonicalName();

    public static void getArticles(SearchArticlesRequest request,
                                   final SearchArticlesCallback callback) {

        if (!NetworkUtils.isOnline()) {
            callback.onError(new Error(getContext().getString(R.string.no_available_connection)));
            return;
        }
        /*
        RequestParams params = new RequestParams();
        params.put("page", request.getPage());
        params.put("q", request.getQuery());
        if (request.getFilter() != null) {
            Filter filter = request.getFilter();
            if (filter.getBeginDate() != null)
                params.put("begin_date", Utils.formatDate(filter.getBeginDate()));
            if (filter.getEndDate() != null)
                params.put("end_date", Utils.formatDate(filter.getEndDate()));
            if (filter.getSort() != null && !"".equals(filter.getSort()))
                params.put("sort", filter.getSort());
            if (filter.getNewsDesk() != null && filter.getNewsDesk().size() > 0) {
                params.put("fq", filter.getFormattedNewsDesk());
            }
        }

        NYTRestClient.get("articlesearch.json", params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject jsonObject = parser.parse(response).getAsJsonObject();

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Date.class, new DateDeserializer())
                            .registerTypeAdapter(ByLine.class, new ByLineDeserializer())
                            .create();
                    SearchArticlesResponse searchArticlesResponse = gson.fromJson(jsonObject.get("response"), SearchArticlesResponse.class);
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
        */

        String beginDate = null;
        String endDate = null;
        String sort = null;
        String newsDesk = null;
        if (request.getFilter() != null) {
            Filter filter = request.getFilter();
            if (filter.getBeginDate() != null) beginDate = Utils.formatDate(filter.getBeginDate());
            if (filter.getEndDate() != null) endDate = Utils.formatDate(filter.getEndDate());
            if (filter.getSort() != null && !"".equals(filter.getSort())) sort = filter.getSort();
            if (filter.getNewsDesk() != null && filter.getNewsDesk().size() > 0)
                newsDesk = filter.getFormattedNewsDesk();
        }

        NYTRetrofitEndpointInterface apiClient = NYTRetrofitEndpointInterface.retrofit.create(NYTRetrofitEndpointInterface.class);
        Call<SearchArticlesResponse> call = apiClient.getArticles(request.getPage(),
                request.getQuery(),
                beginDate,
                endDate,
                sort,
                newsDesk);
        call.enqueue(new Callback<SearchArticlesResponse>() {
            @Override
            public void onResponse(Call<SearchArticlesResponse> call, Response<SearchArticlesResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SearchArticlesResponse> call, Throwable t) {
                Log.e(TAG_LOG, t.toString());
                callback.onError(new Error(t.getMessage()));
            }
        });
    }

    private static Context getContext() {
        return Application.sharedInstance().getApplicationContext();
    }

}
