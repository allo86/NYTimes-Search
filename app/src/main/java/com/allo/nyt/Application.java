package com.allo.nyt;

import android.util.Log;

/**
 * Created by ALLO on 24/7/16.
 */
public class Application extends android.app.Application {

    private String TAG_LOG = getClass().getCanonicalName();

    private static Application mSharedInstance;

    public static Application sharedInstance() {
        return mSharedInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG_LOG, "onCreate Application");

        mSharedInstance = this;
    }
}
