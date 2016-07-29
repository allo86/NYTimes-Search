package com.allo.nyt.ui.article;

import android.view.View;

public class ArticleFullScreenActivity extends ArticleActivity {

    @Override
    protected void initializeUI() {
        super.initializeUI();

        if (toolbar != null) toolbar.setVisibility(View.GONE);
        btShare.setVisibility(View.VISIBLE);
    }
}
