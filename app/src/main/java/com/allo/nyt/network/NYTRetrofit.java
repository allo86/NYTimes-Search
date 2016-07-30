package com.allo.nyt.network;

import com.allo.nyt.network.deserializer.SearchArticlesResponseDeserializer;
import com.allo.nyt.network.model.response.SearchArticlesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit
 * <p/>
 * Created by ALLO on 29/7/16.
 */
public class NYTRetrofit {

    private static final NYTRetrofit instance = new NYTRetrofit();

    protected NYTRetrofit() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SearchArticlesResponse.class, new SearchArticlesResponseDeserializer())
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request();
                                HttpUrl url = request.url()
                                        .newBuilder()
                                        .addQueryParameter(NetworkConstants.API_KEY_PARAM, NetworkConstants.API_KEY_VALUE)
                                        .build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            }
                        })
                .addInterceptor(httpLoggingInterceptor);
        OkHttpClient client = builder.build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NYTRetrofit sharedInstance() {
        return instance;
    }

    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
