# RssAndroid
### 注意
gitignoreしているので app/src/main/java/jp/kuwako/masaki/rssandroid/setting にUrlクラスを作成し、
```java
public final class Url {
    // この変数にrss登録するURLを追記
    // RSS2.0のみ対応中
    public static List<String> urlList = Arrays.asList(
            "http://xxx.jp/rss",
            "http://xxx.com/rss"
    );
}
```
を作成する必要がある。
