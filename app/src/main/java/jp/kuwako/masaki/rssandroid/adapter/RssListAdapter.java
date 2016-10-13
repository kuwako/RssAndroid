package jp.kuwako.masaki.rssandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.kuwako.masaki.rssandroid.R;
import jp.kuwako.masaki.rssandroid.entity.Article;
import jp.kuwako.masaki.rssandroid.model.ArticleModel;

/**
 * Created by m_kuwako on 2016/10/13.
 */

public class RssListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ArticleModel> mArticleList;
    private TextView tvTitle;
    private TextView tvDescription;

    public RssListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setArticleList(List<ArticleModel> articleList) {
        this.mArticleList = articleList;
    }
    @Override
    public int getCount() {
        return mArticleList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArticleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.article_row, parent, false);
        }

        ArticleModel article = mArticleList.get(position);
        if (article != null) {
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvTitle.setText(article.getTitle());
            tvDescription = (TextView) view.findViewById(R.id.description);
            tvDescription.setText(article.getDescription());
        }

        return view;
    }
}
