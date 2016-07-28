package com.allo.nyt.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.allo.nyt.R;
import com.allo.nyt.base.BaseActivity;
import com.allo.nyt.model.Article;
import com.allo.nyt.network.NYTRestClientImplementation;
import com.allo.nyt.network.callbacks.SearchArticlesCallback;
import com.allo.nyt.network.model.request.SearchArticlesRequest;
import com.allo.nyt.network.model.response.SearchArticlesResponse;
import com.allo.nyt.ui.article.ArticleActivity;
import com.allo.nyt.ui.filter.FilterActivity;
import com.allo.nyt.ui.utils.EndlessRecyclerViewScrollListener;
import com.allo.nyt.ui.utils.SpacesItemDecoration;
import com.allo.nyt.utils.Preferences;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * SearchActivity
 */
public class SearchActivity extends BaseActivity implements SearchAdapter.OnArticlesAdapterListener {

    @BindView(R.id.rv_articles)
    RecyclerView mRecyclerView;

    SearchAdapter mAdapter;
    ArrayList<Article> mArticles;

    @State
    String mTextFilter;

    private SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // User pressed the search button
                mTextFilter = query;
                mArticles = new ArrayList<>();
                loadMoreArticles(0);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Return true to collapse action view
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Update text
                searchView.setQuery(mTextFilter, false);
                // Return true to expand action view
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initializeUI() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(16));
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.d(TAG_LOG, "loadNextPage: " + String.valueOf(page));
                if (page > 100) {
                    Snackbar.make(mRecyclerView,
                            getString(R.string.error_max_results),
                            Snackbar.LENGTH_LONG).show();
                } else {
                    loadMoreArticles(page);
                }
            }
        });

        mAdapter = new SearchAdapter(new ArrayList<Article>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initializeDataFromIntentBundle(Bundle extras) {

    }

    @Override
    protected void showData() {
        if (mArticles != null) {
            mAdapter.notifyDataSetChanged(mArticles);
        } else {
            loadMoreArticles(0);
        }
    }

    private void loadMoreArticles(final int page) {
        // Perform request if user has entered text
        if (mTextFilter != null && !"".equals(mTextFilter)) {
            // Build filter params
            SearchArticlesRequest request = new SearchArticlesRequest();
            request.setPage(page);
            request.setQuery(mTextFilter);
            // Add filter
            request.setFilter(Preferences.sharedInstance().getFilter());

            NYTRestClientImplementation.getArticles(request, new SearchArticlesCallback() {
                @Override
                public void onSuccess(SearchArticlesResponse response) {
                    if (response.getArticles() != null && response.getArticles().size() > 0) {
                        if (mArticles == null) {
                            mArticles = new ArrayList<>();
                        }
                        mArticles.addAll(response.getArticles());
                        mAdapter.notifyDataSetChanged(mArticles);
                    }
                }

                @Override
                public void onError(Error error) {
                    Snackbar.make(mRecyclerView,
                            error.getMessage(),
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void didSelectArticle(Article article) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra(ArticleActivity.ARTICLE, Parcels.wrap(article));
        startActivity(intent);
    }

    @OnClick(R.id.fab_filter)
    public void goToFilter() {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, FilterActivity.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FilterActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Start new search, page 0
                mArticles = new ArrayList<>();
                loadMoreArticles(0);
            }
        }
    }
}
