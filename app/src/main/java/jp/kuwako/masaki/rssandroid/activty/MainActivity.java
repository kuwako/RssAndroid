package jp.kuwako.masaki.rssandroid.activty;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.kuwako.masaki.rssandroid.R;
import jp.kuwako.masaki.rssandroid.activty.BaseActivity;
import jp.kuwako.masaki.rssandroid.adapter.RssListAdapter;
import jp.kuwako.masaki.rssandroid.model.ArticleModel;

public class MainActivity extends BaseActivity {
    private ListView lvArticleList;
    private TextView tvNoData;

    private List<ArticleModel> mList;
    private RssListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvArticleList = (ListView) findViewById(R.id.article_list);
        tvNoData = (TextView) findViewById(R.id.no_data);
        mList = new ArrayList<>();
        mAdapter = new RssListAdapter(MainActivity.this, mList) {
        };

        lvArticleList.setAdapter(mAdapter);
        // TODO URLに接続し、RSS取得
        // TODO ButtenKnife入れる
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
