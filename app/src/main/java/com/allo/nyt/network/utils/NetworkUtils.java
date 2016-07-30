package com.allo.nyt.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

/**
 * NetworkUtils
 * <p/>
 * Created by ALLO on 29/7/16.
 */
public class NetworkUtils {

    private static final String TAG_LOG = NetworkUtils.class.getCanonicalName();

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            Log.e(TAG_LOG, "IOException: " + e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG_LOG, "InterruptedException: " + e.getMessage());
        }
        return false;
    }

}
