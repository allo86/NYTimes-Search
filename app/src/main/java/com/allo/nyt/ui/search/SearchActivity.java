package com.allo.nyt.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.allo.nyt.R;
import com.allo.nyt.base.BaseActivity;
import com.allo.nyt.model.Article;
import com.allo.nyt.network.NYTRestClientImplementation;
import com.allo.nyt.network.callbacks.SearchArticlesCallback;
import com.allo.nyt.network.model.request.SearchArticlesRequest;
import com.allo.nyt.network.model.response.SearchArticlesResponse;
import com.allo.nyt.ui.article.ArticleActivity;
import com.allo.nyt.ui.article.ArticleFullScreenActivity;
import com.allo.nyt.ui.filter.FilterActivity;
import com.allo.nyt.ui.filter.FilterFragment;
import com.allo.nyt.ui.filter.model.Filter;
import com.allo.nyt.ui.settings.SettingsFragment;
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
public class SearchActivity extends BaseActivity implements SearchAdapter.OnArticlesAdapterListener,
        FilterFragment.OnFilterFragmentListener {

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeToRefresh;

    @BindView(R.id.rv_articles)
    RecyclerView mRecyclerView;

    SearchAdapter mAdapter;
    EndlessRecyclerViewScrollListener mEndlessListener;
    StaggeredGridLayoutManager mGridLayoutManager;

    @State(SearchActivityBundler.class)
    ArrayList<Article> mArticles;

    @State
    String mTextFilter;

    @State
    int mCurrentPage;

    @State
    boolean didStartedActivityForResult;

    private SearchView searchView;
    private MenuItem progressItem;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        progressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(progressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // User pressed the search button
                mTextFilter = query;
                searchView.clearFocus();
                //searchItem.collapseActionView();
                setTitle(query);
                startNewSearch();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings();
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
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(16));
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mEndlessListener = new EndlessRecyclerViewScrollListener(mGridLayoutManager) {
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
        };
        mRecyclerView.addOnScrollListener(mEndlessListener);

        mAdapter = new SearchAdapter(new ArrayList<Article>(), this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startNewSearch();
            }
        });
        // Configure the refreshing colors
        mSwipeToRefresh.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);

        updateToolbarBehaviour();
    }

    @Override
    protected void initializeDataFromIntentBundle(Bundle extras) {

    }

    @Override
    protected void showData() {
        if (mTextFilter != null && !"".equals(mTextFilter)) {
            setTitle(mTextFilter);
            if (searchView != null) searchView.setQuery(mTextFilter, false);
        }

        if (mArticles != null && !didStartedActivityForResult) {
            mAdapter.notifyDataSetChanged(mArticles);
            mEndlessListener.setCurrentPage(mCurrentPage);
        }
        /*
        else {
            loadMoreArticles(0);
        }
        */
    }

    private void loadMoreArticles(final int page) {
        // Perform request if user has entered text
        if (mTextFilter != null && !"".equals(mTextFilter)) {
            showProgressBar();

            // Build filter params
            SearchArticlesRequest request = new SearchArticlesRequest();
            request.setPage(page);
            request.setQuery(mTextFilter);
            // Add filter
            request.setFilter(Preferences.sharedInstance().getFilter());

            NYTRestClientImplementation.getArticles(request, new SearchArticlesCallback() {
                @Override
                public void onSuccess(SearchArticlesResponse response) {
                    if (response.getArticles() != null) {
                        if (response.getArticles().size() > 0) {
                            mCurrentPage = page;
                        }
                        if (mArticles == null) {
                            mArticles = new ArrayList<>();
                        }
                        mArticles.addAll(response.getArticles());
                        mAdapter.notifyDataSetChanged(mArticles);

                        if (page == 0) {
                            mRecyclerView.scrollToPosition(0);
                        }
                    }

                    updateToolbarBehaviour();

                    hideProgressBar();

                    // Now we call setRefreshing(false) to signal refresh has finished
                    mSwipeToRefresh.setRefreshing(false);

                }

                @Override
                public void onError(Error error) {
                    Snackbar.make(mRecyclerView,
                            error.getMessage(),
                            Snackbar.LENGTH_LONG).show();

                    updateToolbarBehaviour();

                    hideProgressBar();

                    // Now we call setRefreshing(false) to signal refresh has finished
                    mSwipeToRefresh.setRefreshing(false);
                }
            });
        } else {
            // Now we call setRefreshing(false) to signal refresh has finished
            mSwipeToRefresh.setRefreshing(false);
        }
    }

    private void updateToolbarBehaviour() {
        int[] lastItems = mGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[mGridLayoutManager.getSpanCount()]);
        int lastItem = Math.abs(lastItems[lastItems.length - 1]);
        int count = mAdapter.getItemCount();
        boolean enabled = lastItem < count - 1;
        if (enabled) {
            turnOnToolbarScrolling();
        } else {
            turnOffToolbarScrolling();
        }
    }

    private void goToSettings() {
        FragmentManager fm = getSupportFragmentManager();
        SettingsFragment fragment = new SettingsFragment();
        fragment.show(fm, "settings");
    }

    @Override
    public void didSelectArticle(Article article) {
        Intent intent;
        if (Preferences.sharedInstance().getBoolean(Preferences.SHOW_ARTICLE_FULLSCREEN, false)) {
            intent = new Intent(this, ArticleFullScreenActivity.class);
            intent.putExtra(ArticleActivity.ARTICLE, Parcels.wrap(article));
            startActivity(intent);
        } else {
            intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(ArticleActivity.ARTICLE, Parcels.wrap(article));
            startActivity(intent);
        }
    }

    @OnClick(R.id.fab_filter)
    public void goToFilter() {
        if (Preferences.sharedInstance().getBoolean(Preferences.SHOW_FILTER_DIALOG, false)) {
            FragmentManager fm = getSupportFragmentManager();
            FilterFragment fragment = new FilterFragment();
            fragment.show(fm, "filter");
        } else {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivityForResult(intent, FilterActivity.REQUEST_CODE);
            didStartedActivityForResult = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FilterActivity.REQUEST_CODE) {
            didStartedActivityForResult = false;
            if (resultCode == RESULT_OK) {
                // Start new search, page 0
                startNewSearch();
            }
        }
    }

    @Override
    public void onFilterSaved(Filter filter) {
        startNewSearch();
    }

    private void startNewSearch() {
        // Start new search, page 0
        mCurrentPage = 0;
        //mRecyclerView.scrollToPosition(0);
        mArticles = new ArrayList<>();
        mAdapter.notifyDataSetChanged(mArticles);
        loadMoreArticles(0);
    }

    private void showProgressBar() {
        // Show progress item
        if (progressItem != null) progressItem.setVisible(true);
    }

    private void hideProgressBar() {
        // Hide progress item
        if (progressItem != null) progressItem.setVisible(false);
    }
}
