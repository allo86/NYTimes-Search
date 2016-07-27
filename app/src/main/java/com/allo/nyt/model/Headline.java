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

    @SerializedName("name")
    String name;

    @SerializedName("print_headline")
    String printHeadline;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintHeadline() {
        return printHeadline;
    }

    public void setPrintHeadline(String printHeadline) {
        this.printHeadline = printHeadline;
    }

    public String getTitle() {
        if (getMain() != null && !"".equals(getMain())) {
            return getMain();
        } else if (getPrintHeadline() != null && !"".equals(getPrintHeadline())) {
            return getPrintHeadline();
        } else {
            return getName();
        }
    }
}
