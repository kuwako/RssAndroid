package jp.kuwako.masaki.rssandroid.activty;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.kuwako.masaki.rssandroid.R;
import jp.kuwako.masaki.rssandroid.adapter.RssListAdapter;
import jp.kuwako.masaki.rssandroid.model.ArticleModel;
import jp.kuwako.masaki.rssandroid.setting.Url;
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

    private void setRssFeed() {
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                try {
                    // urlListの要素はRSS2.0を想定。RSS1.0だとタグ名が違うため日付が表示されないが、今回は非実装予定
                    List<String> urlList = Url.urlList;
                    for (int i = 0; i < urlList.size(); i++) {
                        res = run(urlList.get(i));
                        // XMLをパース
                        parseXml(res);
                    }
                    // 日付でソート
                    Collections.sort(mList, new ArticleComp());
                    Collections.reverse(mList);

                    mAdapter.notifyDataSetChanged();
                } catch(IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return res;
            }
        }.execute();
    }

    private void parseXml(String xmlStr) throws XmlPullParserException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new StringReader(xmlStr));
        int eventType;
        String tagName;
        String title = "";
        String link = "";
        String description = "";
        String pubDate = "";

        try {
            while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
                tagName = xmlPullParser.getName();
                if (eventType == XmlPullParser.START_TAG && "item".equals(tagName)) {
                    // 初期化
                    title = "";
                    link = "";
                    description = "";
                    pubDate = "";
                } else if (eventType == xmlPullParser.START_TAG && "title".equals(tagName)) {
                    title = xmlPullParser.nextText();
                } else if (eventType == xmlPullParser.START_TAG && "link".equals(tagName)) {
                    link = xmlPullParser.nextText();
                } else if (eventType == xmlPullParser.START_TAG && "description".equals(tagName)) {
                    description = xmlPullParser.nextText();
                } else if (eventType == xmlPullParser.START_TAG && "pubDate".equals(tagName)) {
                    pubDate = xmlPullParser.nextText();
                } else if (eventType == XmlPullParser.END_TAG && "item".equals(tagName)) {
                    ArticleModel am = new ArticleModel(title, link, description, pubDate);
                    mList.add(am);
                }
            }
            mAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, String> {
        public MyAsyncTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }
    }

    private static final class ArticleComp implements Comparator<ArticleModel> {
        @Override
        public int compare(ArticleModel o1, ArticleModel o2) {
            return o1.getFormatedPubDate().compareTo(o2.getFormatedPubDate());
        }
    }

}
