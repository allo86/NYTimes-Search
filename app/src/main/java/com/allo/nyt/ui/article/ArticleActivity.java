package com.allo.nyt.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.allo.nyt.R;
import com.allo.nyt.base.BaseActivity;
import com.allo.nyt.model.Article;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.OnClick;

public class ArticleActivity extends BaseActivity {

    public static final String ARTICLE = "ARTICLE";

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.pb_horizontal)
    ProgressBar mProgressBar;

    @BindView(R.id.fab_share)
    FloatingActionButton btShare;

    private Article mArticle;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_article;
    }

    @Override
    protected void initializeUI() {
        // Configure related browser settings
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Configure the client to use when opening URLs
        mWebView.setWebViewClient(new MyBrowser());
        // Chrome client
        mWebView.setWebChromeClient(new MyChromeBrowser());

        // Enable responsive layout
        mWebView.getSettings().setUseWideViewPort(true);
        // Zoom out if the content width is greater than the width of the veiwport
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        mWebView.getSettings().setDisplayZoomControls(false); // disable the default zoom controls on the page

        btShare.setVisibility(View.GONE);
    }

    @Override
    protected void initializeDataFromIntentBundle(Bundle extras) {
        mArticle = Parcels.unwrap(extras.getParcelable(ARTICLE));
    }

    @Override
    protected void showData() {
        // Title
        setTitle(mArticle.getSource());
        // Load the initial URL
        mWebView.loadUrl(mArticle.getWebUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);

        MenuItem item = menu.findItem(R.id.share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mArticle.getWebUrl());
        shareActionProvider.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.share:
                share(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */

    @OnClick(R.id.fab_share)
    public void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mArticle.getWebUrl());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)));
    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyChromeBrowser extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress < 100 && mProgressBar.getVisibility() == ProgressBar.GONE) {
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            mProgressBar.setProgress(progress);
            if (progress == 100) {
                mProgressBar.setVisibility(ProgressBar.GONE);
            }
        }
    }
}
