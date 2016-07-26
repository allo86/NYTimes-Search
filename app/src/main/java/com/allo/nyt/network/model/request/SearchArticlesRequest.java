package com.allo.nyt.network.model.request;

import com.allo.nyt.ui.filter.model.Filter;

/**
 * SearchArticlesRequest
 * <p/>
 * Created by ALLO on 24/7/16.
 */
public class SearchArticlesRequest {

    private int page;

    private String query;

    private Filter filter;

    public SearchArticlesRequest() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
