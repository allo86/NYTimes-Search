package com.allo.nyt.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ALLO on 24/7/16.
 */
@Parcel
public class Keyword {

    @SerializedName("rank")
    String rank;

    @SerializedName("is_major")
    String isMajor;

    @SerializedName("name")
    String name;

    @SerializedName("value")
    String value;

    public Keyword() {

    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(String isMajor) {
        this.isMajor = isMajor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
