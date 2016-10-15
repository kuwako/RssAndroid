package jp.kuwako.masaki.rssandroid.model;

import jp.kuwako.masaki.rssandroid.entity.Article;
import jp.kuwako.masaki.rssandroid.setting.Constants;
import jp.kuwako.masaki.rssandroid.util.StringUtil;

/**
 * Created by m_kuwako on 2016/10/12.
 */

public class ArticleModel extends Article {
    public ArticleModel(String title, String link, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getFormatedTitle() {
        return StringUtil.cutText(this.title, Constants.MAX_TITLE_LENGTH);
    }

    public String getFormatedDescription() {
        String desc;
        desc = StringUtil.htmlTagRemover(this.description);
        desc = StringUtil.cutText(desc, Constants.MAX_DISCRIPTION_LENGTH);

        return desc;
    }

    public String getFormatedPubDate() {
        return StringUtil.jstPubDate(this.pubDate);
    }
}
