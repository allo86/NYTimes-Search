package com.allo.nyt.network.model.request;

/**
 * SearchArticlesRequest
 * <p/>
 * Created by ALLO on 24/7/16.
 */
public class SearchArticlesRequest {

    private int page;

    private String query;

    /**
     * Format: YYYYMMDD
     */
    private String beginDate;

    /**
     * Format: YYYYMMDD
     */
    private String endDate;

    /**
     * Values: newest, oldest
     */
    private String sort;

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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
