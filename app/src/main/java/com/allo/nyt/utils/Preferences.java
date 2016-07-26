package com.allo.nyt.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.allo.nyt.Application;
import com.allo.nyt.ui.filter.model.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Preferences
 * <p/>
 * Created by ALLO on 25/7/16.
 */
public class Preferences {

    private static Preferences mINSTANCE = null;

    private SharedPreferences mSharedPreferences;

    public static synchronized Preferences sharedInstance() {
        if (mINSTANCE == null) {
            mINSTANCE = new Preferences();
        }

        return mINSTANCE;
    }

    private Preferences() {
        this.mSharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static final String PREFS_NAME = "NYT_Preferences";

    public void saveFilter(Filter filter) {
        putString("sort", filter.getSort());
        putLong("beginDate", filter.getBeginDate() != null ? filter.getBeginDate().getTime() : -1L);
        putLong("endDate", filter.getEndDate() != null ? filter.getEndDate().getTime() : -1L);
        putListString("newsDesk", filter.getNewsDesk());
    }

    public Filter getFilter() {
        Filter filter = new Filter();
        filter.setSort(getString("sort"));
        Long beginDate = getLong("beginDate", -1L);
        filter.setBeginDate(beginDate == -1L ? null : new Date(beginDate));
        Long endDate = getLong("endDate", -1L);
        filter.setEndDate(endDate == -1L ? null : new Date(endDate));
        filter.setNewsDesk(getListString("newsDesk"));
        return filter;
    }

    private void putString(String key, String value) {
        //checkForNullKey(key);
        //checkForNullValue(value);
        this.mSharedPreferences.edit().putString(key, value).apply();
    }

    private String getString(String key) {
        return this.mSharedPreferences.getString(key, "");
    }

    public void putLong(String key, long value) {
        //checkForNullKey(key);
        this.mSharedPreferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return this.mSharedPreferences.getLong(key, defaultValue);
    }

    private void putListString(String key, ArrayList<String> stringList) {
        //checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        this.mSharedPreferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    private ArrayList<String> getListString(String key) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(this.mSharedPreferences.getString(key, ""), "‚‗‚")));
    }

    /*
    private void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }
    */

    private Context getContext() {
        return Application.sharedInstance().getApplicationContext();
    }

}
