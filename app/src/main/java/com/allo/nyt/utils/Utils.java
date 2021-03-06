package com.allo.nyt.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ALLO on 20/7/16.
 */
public class Utils {

    public static float convertDPToPixels(Resources resources, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static void dismissKeyboard(View v) {
        ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return df.format(date);
    }

    public static String formatDateShort(Date date) {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

}
