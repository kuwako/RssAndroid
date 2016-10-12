package jp.kuwako.masaki.rssandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.kuwako.masaki.rssandroid.R;
import jp.kuwako.masaki.rssandroid.entity.Article;
import jp.kuwako.masaki.rssandroid.model.ArticleModel;

/**
 * Created by m_kuwako on 2016/10/13.
 */

public class RssListAdapter extends ArrayAdapter<ArticleModel> {
    private LayoutInflater mInflater;
    private TextView tvTitle;
    private TextView tvDescription;

    public RssListAdapter(Context context, List<ArticleModel> articleList) {
        super(context, 0, articleList);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = converView;

        if (converView == null) {
            view = mInflater.inflate(R.layout.article_row, parent);
        }

        ArticleModel article = this.getItem(position);
        if (article != null) {
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvTitle.setText(article.getTitle());
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            tvDescription.setText(article.getDescription());
        }

        return view;
    }
}
