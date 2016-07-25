package com.allo.nyt.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.allo.nyt.R;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * BaseActivity
 * <p/>
 * Created by ALLO on 24/7/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG_LOG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceID());

        Icepick.restoreInstanceState(this, savedInstanceState);

        initializeUI();

        showData();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        customizeToolbar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void customizeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) setSupportActionBar(toolbar);
    }

    protected abstract int getLayoutResourceID();

    protected abstract void initializeUI();

    protected abstract void showData();
}
