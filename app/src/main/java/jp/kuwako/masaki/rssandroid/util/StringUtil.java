package jp.kuwako.masaki.rssandroid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by m_kuwako on 2016/10/11.
 */

public class StringUtil {
    /**
     * 指定された文字数 + ... を返す
     *
     * @param text
     * @param maxLength
     * @return convertedText
     */
    public static String cutText(String text, int maxLength) {
        text = text.substring(0, maxLength);

        return text + "...";
    }

    /**
     * htmlタグの削除、改行文字の削除、空白文字の削除
     *
     * @param str
     * @return
     */
    public static String htmlTagRemover(String str) {
        str = str.replaceAll("<.+?>", "");
        str = str.replaceAll("\n", "");
        str = str.trim();
        return str;
    }

    /**
     * Thu, 21 Jul 2016 10:29:54 +0900の形式で渡される。
     * @param pubDate
     * @return
     */
    public static String jstPubDate(String pubDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date gmtDate = sdf.parse(pubDate);

            sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("JST"));

            return sdf.format(gmtDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pubDate;
    }
}
