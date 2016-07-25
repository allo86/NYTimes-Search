package com.allo.nyt.ui.search;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allo.nyt.R;
import com.allo.nyt.model.Article;
import com.allo.nyt.model.Multimedia;
import com.allo.nyt.ui.utils.DynamicHeightImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SearchAdapter
 * <p/>
 * Created by ALLO on 24/7/16.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG_LOG = SearchAdapter.class.getCanonicalName();

    public interface OnArticlesAdapterListener {
        void didSelectArticle(Article article);
    }

    private OnArticlesAdapterListener mListener;

    private ArrayList<Article> mArticles;

    public SearchAdapter(ArrayList<Article> articles, OnArticlesAdapterListener listener) {
        this.mArticles = articles;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articles, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArticleViewHolder) {
            ((ArticleViewHolder) holder).configureViewWithArticle(mArticles.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return this.mArticles != null ? this.mArticles.size() : 0;
    }

    public void notifyDataSetChanged(ArrayList<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private Article article;

        @BindView(R.id.iv_photo)
        DynamicHeightImageView ivPhoto;

        @BindView(R.id.pb_image)
        ProgressBar pbImage;

        @BindView(R.id.tv_headline)
        TextView tvHeadline;

        public ArticleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            this.view = view;
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) mListener.didSelectArticle(article);
                }
            });
        }

        public void configureViewWithArticle(Article article) {
            this.article = article;

            tvHeadline.setText(article.getHeadline().getTitle());

            pbImage.setVisibility(View.VISIBLE);
            ivPhoto.setVisibility(View.VISIBLE);
            ivPhoto.setImageDrawable(null);
            if (article.hasImages()) {
                Multimedia photo = article.getFirstImage();
                ivPhoto.setHeightRatio(((double) photo.getHeight()) / photo.getWidth());
                Picasso.with(view.getContext()).load(article.getFirstImage().getUrl())
                        .into(ivPhoto, new Callback() {
                            @Override
                            public void onSuccess() {
                                pbImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                Log.d(TAG_LOG, "error");
                            }
                        });
            } else {
                pbImage.setVisibility(View.GONE);
                ivPhoto.setVisibility(View.GONE);
            }
        }
    }
}
