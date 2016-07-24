package com.allo.nyt.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Meta
 * <p/>
 * Created by ALLO on 24/7/16.
 */
@Parcel
public class Meta {

    @SerializedName("hits")
    int hits;

    @SerializedName("time")
    int time;

    @SerializedName("offset")
    int offset;

    public Meta() {

    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
