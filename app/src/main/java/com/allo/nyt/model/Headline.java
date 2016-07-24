package com.allo.nyt.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ALLO on 24/7/16.
 */
@Parcel
public class Headline {

    @SerializedName("main")
    String main;

    @SerializedName("kicker")
    String kicker;

    public Headline() {

    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }
}
