package jp.kuwako.masaki.rssandroid.activty;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.kuwako.masaki.rssandroid.R;
import jp.kuwako.masaki.rssandroid.adapter.RssListAdapter;
import jp.kuwako.masaki.rssandroid.model.ArticleModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.article_list)
    ListView lvArticleList;
    @BindView(R.id.no_data)
    TextView tvNoData;

    private List<ArticleModel> mList;
    private RssListAdapter mAdapter;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        lvArticleList = (ListView) findViewById(R.id.article_list);
        tvNoData = (TextView) findViewById(R.id.no_data);
        mList = new ArrayList<>();
        mAdapter = new RssListAdapter(MainActivity.this);
        mAdapter.setArticleList(mList);

        lvArticleList.setAdapter(mAdapter);
        // URLに接続し、RSS取得&リストviewにセット
        setRssFeed();

        // TODO サンプル用。削除。
        ArticleModel am = new ArticleModel("title", "link", "description", "2016-10-13 23:20:00");
        mList.add(0, am);
        mAdapter.notifyDataSetChanged();
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

//    @OnClick(R.id.article_list)
//    public void onClick() {
//    }

    private void setRssFeed() {
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                try {
                    // TODO 別ファイルから取ってくるように改修
                    String result = run("http://tech.uzabase.com/rss");
                    Log.d("@@@rss", result);

                } catch(IOException e) {
                    e.printStackTrace();
                }
                return res;
            }
        }.execute();
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, String> {
        public MyAsyncTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }
    }

}
