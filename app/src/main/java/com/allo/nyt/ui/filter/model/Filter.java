package com.allo.nyt.ui.filter.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ALLO on 25/7/16.
 */
@Parcel
public class Filter {

    Date beginDate;

    Date endDate;

    String sort;

    ArrayList<String> newsDesk;

    public Filter() {

    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public ArrayList<String> getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(ArrayList<String> newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getFormattedNewsDesk() {
        StringBuilder sb = new StringBuilder();
        sb.append("news_desk:(");
        for (String value : newsDesk) {
            sb.append("\"").append(value).append("\" ");
        }
        // Remove last character
        sb.setLength(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }
}
