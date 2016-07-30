package com.allo.nyt.network;

import com.allo.nyt.network.model.response.SearchArticlesResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * NYTRetrofitEndpointInterface
 * <p/>
 * Created by ALLO on 29/7/16.
 */
public interface NYTRetrofitEndpointInterface {

    @GET("articlesearch.json")
    Call<SearchArticlesResponse> getArticles(@Query("page") int page,
                                             @Query("q") String query,
                                             @Query("begin_date") String beginDate,
                                             @Query("end_date") String endDate,
                                             @Query("sort") String sort,
                                             @Query("fq") String fq);

    Retrofit retrofit = NYTRetrofit.sharedInstance().getRetrofit();

}
