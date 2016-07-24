package com.allo.nyt.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.allo.nyt.R;
import com.allo.nyt.base.BaseActivity;
import com.allo.nyt.model.Article;
import com.allo.nyt.network.NYTRestClientImplementation;
import com.allo.nyt.network.callbacks.SearchArticlesCallback;
import com.allo.nyt.network.model.request.SearchArticlesRequest;
import com.allo.nyt.network.model.response.SearchArticlesResponse;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * SearchActivity
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.rv_articles)
    RecyclerView mRecyclerView;

    ArrayList<Article> mArticles;

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    private void showData() {
        if (mArticles != null) {

        } else {
            loadMoreArticles();
        }
    }

    private void loadMoreArticles() {
        SearchArticlesRequest request = new SearchArticlesRequest();
        NYTRestClientImplementation.getArticles(request, new SearchArticlesCallback() {
            @Override
            public void onSuccess(SearchArticlesResponse response) {

            }

            @Override
            public void onError(Error error) {

            }
        });
    }
}
