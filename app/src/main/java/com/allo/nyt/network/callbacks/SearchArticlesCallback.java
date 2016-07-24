package com.allo.nyt.network.callbacks;

import com.allo.nyt.network.model.response.SearchArticlesResponse;

/**
 * SearchArticlesCallback
 * <p/>
 * Created by ALLO on 24/7/16.
 */
public interface SearchArticlesCallback {

    void onSuccess(SearchArticlesResponse response);

    void onError(Error error);

}
