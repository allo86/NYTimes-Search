package com.allo.nyt.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ALLO on 24/7/16.
 */
@Parcel
public class Legacy {

    @SerializedName("wide")
    String url;

    @SerializedName("wideheight")
    int width;

    @SerializedName("widewidth")
    int height;

    public Legacy() {

    }

    public String getUrl() {
        return "http://www.nytimes.com/" + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
