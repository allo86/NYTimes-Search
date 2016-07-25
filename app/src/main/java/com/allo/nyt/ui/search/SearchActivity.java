package com.allo.nyt.ui.search;

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
import com.allo.nyt.ui.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.BindView;
import icepick.State;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * SearchActivity
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, SearchAdapter.OnArticlesAdapterListener {

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
        searchView.setOnQueryTextListener(this);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mAdapter = new SearchAdapter(new ArrayList<Article>(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void showData() {
        if (mArticles != null) {
            mAdapter.notifyDataSetChanged(mArticles);
        } else {
            loadMoreArticles(0);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        mTextFilter = query;
        loadMoreArticles(0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }

    private void loadMoreArticles(int page) {
        // Perform request if user has entered text
        if (mTextFilter != null && !"".equals(mTextFilter)) {
            // Build filter params
            SearchArticlesRequest request = new SearchArticlesRequest();
            request.setPage(page);
            request.setQuery(mTextFilter);
            // Add filter if informed

            NYTRestClientImplementation.getArticles(request, new SearchArticlesCallback() {
                @Override
                public void onSuccess(SearchArticlesResponse response) {
                    if (mArticles == null) {
                        mArticles = new ArrayList<>();
                    }
                    mArticles.addAll(response.getArticles());
                    mAdapter.notifyDataSetChanged(mArticles);
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

    }
}
