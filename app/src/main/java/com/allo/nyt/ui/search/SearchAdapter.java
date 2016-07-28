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
import com.allo.nyt.utils.Utils;
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

    public static int ARTICLE_THUMBNAIL = 1;
    public static int ARTICLE_HEADLINE = 2;

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
        if (viewType == ARTICLE_THUMBNAIL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articles_multimedia, parent, false);
            return new ArticleMultimediaViewHolder(view);
        } else if (viewType == ARTICLE_HEADLINE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articles_headline, parent, false);
            return new ArticleHeadlineViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof ArticleMultimediaViewHolder) {
                ((ArticleMultimediaViewHolder) holder).configureViewWithArticle(mArticles.get(position));
            } else if (holder instanceof ArticleHeadlineViewHolder) {
                ((ArticleHeadlineViewHolder) holder).configureViewWithArticle(mArticles.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.mArticles != null ? this.mArticles.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        Article article = mArticles.get(position);
        if (article.hasImages()) {
            return ARTICLE_THUMBNAIL;
        } else {
            return ARTICLE_HEADLINE;
        }
    }

    public void notifyDataSetChanged(ArrayList<Article> articles) {
        this.mArticles = new ArrayList<>(articles);
        notifyDataSetChanged();
    }

    class ArticleMultimediaViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private Article article;

        @BindView(R.id.iv_photo)
        DynamicHeightImageView ivPhoto;

        @BindView(R.id.pb_image)
        ProgressBar pbImage;

        @BindView(R.id.tv_headline)
        TextView tvHeadline;

        public ArticleMultimediaViewHolder(View view) {
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

    class ArticleHeadlineViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private Article article;

        @BindView(R.id.tv_headline)
        TextView tvHeadline;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.tv_author)
        TextView tvAuthor;

        public ArticleHeadlineViewHolder(View view) {
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
            if (article.getPubDate() != null) {
                tvDate.setVisibility(View.VISIBLE);
                tvDate.setText(Utils.formatDateShort(article.getPubDate()));
            } else {
                tvDate.setVisibility(View.GONE);
            }

            if (article.getByLine() != null) {
                tvAuthor.setVisibility(View.VISIBLE);
                if (tvDate.getVisibility() == View.VISIBLE) {
                    tvAuthor.setText(tvAuthor.getContext().getString(R.string.author_with_date, article.getByLine().getOriginal()));
                } else {
                    tvAuthor.setText(article.getByLine().getOriginal());
                }
            } else {
                tvAuthor.setVisibility(View.GONE);
            }
        }
    }
}
